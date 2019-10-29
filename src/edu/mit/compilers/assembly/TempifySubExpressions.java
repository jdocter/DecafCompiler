package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.*;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TempifySubExpressions implements CFVisitor {

    Set<CFNode> visited = new HashSet<>();

    @Override
    public void visit(CFBlock cfBlock) {
        if (!visited.contains(cfBlock)) {
            CFNode startNode = new CFNop();
            CFNode prevNode = startNode;
            for (Statement statement: cfBlock.getStatements()) {
                if (statement.statementType == Statement.LOC_ASSIGN) {
                    Pair<CFNode, CFNode> la = destructStatementLocAssign(statement, cfBlock.getVariableTable());
                    prevNode.setNext(la.getKey());
                    prevNode = la.getValue();
                } else if (statement.statementType == Statement.METHOD_CALL) {
                    Pair<CFNode, CFNode> mc = destructMethodCall(statement.methodCall, cfBlock.getVariableTable());
                    prevNode.setNext(mc.getKey());
                    prevNode = mc.getValue();
                } else {
                    throw new RuntimeException("impossible to reach...");
                }
            }
            MergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new MergeBasicBlocksAndRemoveNops(false);
            prevNode.accept(mergeBasicBlocksAndRemoveNops);
            cfBlock.setMiniCFG(startNode);
            visited.add(cfBlock);
            for (CFNode neighbor : cfBlock.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        if (!visited.contains(cfConditional)) {
            Temp cond = new Temp();
            Pair<CFNode, CFNode> ct = destructExprAssignTemp(cfConditional.getBoolExpr(), cond, cfConditional.getVariableTable());
            MergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new MergeBasicBlocksAndRemoveNops(false);
            ct.getKey().accept(mergeBasicBlocksAndRemoveNops);
            cfConditional.replaceExpr(cond);
            cfConditional.setMiniCFG(ct.getKey());

            visited.add(cfConditional);
            for (CFNode neighbor : cfConditional.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }

    @Override
    public void visit(CFNop cfNop) {
        if (!visited.contains(cfNop)) {
            visited.add(cfNop);
            for (CFNode neighbor : cfNop.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
        if (!visited.contains(cfReturn)) {
            Temp cond = new Temp();
            if (cfReturn.getReturnExpr() != null) {
                Temp t = new Temp();
                Pair<CFNode, CFNode> ct = destructExprAssignTemp(cfReturn.getReturnExpr(), t, cfReturn.getVariableTable());
                MergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new MergeBasicBlocksAndRemoveNops(false);
                ct.getKey().accept(mergeBasicBlocksAndRemoveNops);
                cfReturn.replaceExpr(cond);
                cfReturn.setMiniCFG(ct.getKey());
            }


            visited.add(cfReturn);
            for (CFNode neighbor : cfReturn.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }



    private static Pair<CFNode, CFNode> destructStatementLocAssign(Statement statement, VariableTable locals) {
        if (statement.statementType != Statement.LOC_ASSIGN) throw new RuntimeException("Expected statement of type LOC_ASSIGN");

        final Temp tempLocExpr = new Temp();
        final Pair<CFNode, CFNode> locExprCFG = statement.loc.expr != null ?
                destructExprAssignTemp(statement.loc.expr, tempLocExpr, locals) : null;
        final Temp tempAssignExpr = new Temp();
        Pair<CFNode, CFNode> assignExprCFG = statement.assignExpr.expr != null ?
                destructExprAssignTemp(statement.assignExpr.expr, tempAssignExpr, locals) : null;
        final CFNode cfBlockAssign;
        if (statement.loc.expr != null && statement.assignExpr.expr != null) {
            // a[b] += c
            cfBlockAssign = new CFBlock(new CFAssign(statement.loc.id, tempLocExpr, statement.assignExpr.assignExprOp, tempAssignExpr), locals);
            locExprCFG.getValue().setNext(assignExprCFG.getKey());
            assignExprCFG.getValue().setNext(cfBlockAssign);
            return new Pair<CFNode, CFNode>(locExprCFG.getKey(),cfBlockAssign);
        } else if (statement.loc.expr == null && statement.assignExpr.expr == null){
            // a++
            cfBlockAssign = new CFBlock(new CFAssign(statement.loc.id, null, statement.assignExpr.assignExprOp, null),
                    locals);
            return new Pair<CFNode, CFNode>(cfBlockAssign,cfBlockAssign);
        } else if (statement.loc.expr != null && statement.assignExpr.expr == null) {
            // a[b] ++
            cfBlockAssign = new CFBlock(new CFAssign(statement.loc.id, tempLocExpr, statement.assignExpr.assignExprOp, null), locals);
            locExprCFG.getValue().setNext(cfBlockAssign);
            return new Pair<CFNode, CFNode>(assignExprCFG.getKey(),cfBlockAssign);
        } else if (statement.loc.expr == null && statement.assignExpr.expr != null) {
            // a += b
            cfBlockAssign = new CFBlock(new CFAssign(statement.loc.id, null, statement.assignExpr.assignExprOp, tempAssignExpr), locals);
            assignExprCFG.getValue().setNext(cfBlockAssign);
            return new Pair<CFNode, CFNode>(assignExprCFG.getKey(),cfBlockAssign);
        } else {
            throw new RuntimeException("destructStatemensLocAssign: Impossible to reach...");
        }
    }

    private static Pair<CFNode,CFNode> destructExprAssignTemp(Expr expr, Temp temp, VariableTable locals) {
        switch (expr.exprType) {
            case Expr.LEN:
                CFBlock cfBlockLen = new CFBlock(CFTempAssign.makeLen(temp, expr.id), locals);
                return new Pair<CFNode, CFNode>(cfBlockLen, cfBlockLen);
            case Expr.LIT:
                CFBlock cfBlockLit = new CFBlock(CFTempAssign.makeLit(temp, expr.lit), locals);
                return new Pair<CFNode, CFNode>(cfBlockLit, cfBlockLit);
            case Expr.MINUS:
                Temp tempMinusOperand = new Temp();
                Pair<CFNode, CFNode> minusOperandCFG = destructExprAssignTemp(expr.expr, tempMinusOperand, locals);
                CFBlock cfBlockMinus = new CFBlock(CFTempAssign.makeMinus(temp, tempMinusOperand), locals);
                minusOperandCFG.getValue().setNext(cfBlockMinus);
                return new Pair<CFNode, CFNode>(minusOperandCFG.getKey(), cfBlockMinus);
            case Expr.LOC:
                final Temp tempLocExpr = new Temp();
                if (expr.loc.expr != null) {
                    final Pair<CFNode, CFNode> locExprCFG = destructExprAssignTemp(expr.loc.expr, tempLocExpr, locals);
                    CFBlock cfBlockTempAssign = new CFBlock(CFTempAssign.makeLoc(temp, expr.loc.id, tempLocExpr), locals);
                    locExprCFG.getValue().setNext(cfBlockTempAssign);
                    return new Pair<>(locExprCFG.getKey(),cfBlockTempAssign);
                } else {
                    CFBlock cfBlockTempAssign = new CFBlock(CFTempAssign.makeLoc(temp, expr.loc.id), locals);
                    return new Pair<>(cfBlockTempAssign, cfBlockTempAssign);
                }
            case Expr.METHOD_CALL:
                Pair<CFNode, CFNode> methodCallCFG = destructMethodCall(expr.methodCall, locals);
                CFBlock cfBlockLoadRax = new CFBlock(CFTempAssign.makeLoadRax(temp), locals);
                methodCallCFG.getValue().setNext(cfBlockLoadRax);
                return new Pair<CFNode, CFNode>(methodCallCFG.getKey(), cfBlockLoadRax);
            case Expr.NOT:
                Temp tempNotOperand = new Temp();
                Pair<CFNode, CFNode> notOperandCFG = destructExprAssignTemp(expr.expr, tempNotOperand, locals);
                CFBlock cfBlockNot = new CFBlock(CFTempAssign.makeNot(temp, tempNotOperand), locals);
                notOperandCFG.getValue().setNext(cfBlockNot);
                return new Pair<CFNode, CFNode>(notOperandCFG.getKey(), cfBlockNot);
            case Expr.BIN_OP:
                Temp left = new Temp();
                Temp right = new Temp();
                Pair<CFNode, CFNode> leftCFG = destructExprAssignTemp(expr.expr, left, locals);
                Pair<CFNode, CFNode> rightCFG = destructExprAssignTemp(expr.binOpExpr, right, locals);

                // for shortcircuiting
                CFBlock assignTrue = new CFBlock(CFTempAssign.assignTrue(temp), locals);
                CFBlock assignFalse = new CFBlock(CFTempAssign.assignFalse(temp), locals);
                CFNode end = new CFNop();
                assignTrue.setNext(end);
                assignFalse.setNext(end);

                switch (expr.binOp.binOp) {
                    case BinOp.AND:
                        CFNode rightAnd = new CFConditional(right, assignTrue, assignFalse, locals);
                        CFNode leftAnd = new CFConditional(left, rightCFG.getKey(), assignFalse, locals);
                        rightCFG.getValue().setNext(rightAnd);
                        leftCFG.getValue().setNext(leftAnd);
                        return new Pair<CFNode, CFNode>(leftCFG.getKey(), end);
                    case BinOp.OR:
                        CFNode rightOr = new CFConditional(right, assignTrue, assignFalse, locals);
                        CFNode leftOr = new CFConditional(left, assignTrue, rightCFG.getKey(), locals);
                        rightCFG.getValue().setNext(rightOr);
                        leftCFG.getValue().setNext(leftOr);
                        return new Pair<CFNode, CFNode>(leftCFG.getKey(), end);
                    case BinOp.EQ:
                    case BinOp.NEQ:
                    case BinOp.GEQ:
                    case BinOp.LEQ:
                    case BinOp.GT:
                    case BinOp.LT:
                    case BinOp.MINUS:
                    case BinOp.MOD:
                    case BinOp.MUL:
                    case BinOp.PLUS:
                    case BinOp.DIV:
                        leftCFG.getValue().setNext(rightCFG.getKey());
                        CFBlock assignBinOp = new CFBlock(CFTempAssign.assignBinOp(temp, left, expr.binOp, right), locals);
                        rightCFG.getValue().setNext(assignBinOp);
                        return new Pair<>(leftCFG.getKey(), assignBinOp);
                    default:
                        throw new RuntimeException("Unknown BinOp: " + expr.binOp.binOp);

                }
            default:
                throw new RuntimeException("Unknown Expr " + expr.exprType);
        }
    }

    private static Pair<CFNode,CFNode> destructMethodCall(MethodCall methodCall, VariableTable locals) {
        List<Pair<Temp, StringLit>> argTemps = new ArrayList<>();
        CFNode methodCallStart = new CFNop();
        CFNode prev = methodCallStart;
        for (Pair<Expr,StringLit> arg: methodCall.arguments) {
            if (arg.getKey() != null) {
                // expr
                Temp argTemp = new Temp();
                Pair<CFNode, CFNode> argPrep = destructExprAssignTemp(arg.getKey(), argTemp, locals);
                prev.setNext(argPrep.getKey());
                prev = argPrep.getValue();
                argTemps.add(new Pair<Temp, StringLit>(argTemp, null));
            } else {
                argTemps.add(new Pair<Temp, StringLit>(null, arg.getValue()));
            }

        }
        CFBlock cfBlockMethodCall = new CFBlock(new CFMethodCall(methodCall.methodName, argTemps), locals);
        prev.setNext(cfBlockMethodCall);
        return new Pair<CFNode, CFNode>(methodCallStart, cfBlockMethodCall);
    }

}
