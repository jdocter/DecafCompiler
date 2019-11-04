package edu.mit.compilers.cfg;

import edu.mit.compilers.cfg.innercfg.InnerCFBlock;
import edu.mit.compilers.cfg.innercfg.InnerCFConditional;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.cfg.innercfg.InnerCFNop;
import edu.mit.compilers.cfg.innercfg.InnerMergeBasicBlocksAndRemoveNops;
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
            InnerCFNode startNode = new InnerCFNop();
            InnerCFNode endNode = new InnerCFNop();
            InnerCFNode prevNode = startNode;
            for (Statement statement: cfBlock.getStatements()) {
                if (statement.statementType == Statement.LOC_ASSIGN) {
                    Pair<InnerCFNode, InnerCFNode> la = destructStatementLocAssign(statement, cfBlock.getVariableTable());
                    prevNode.setNext(la.getKey());
                    prevNode = la.getValue();
                } else if (statement.statementType == Statement.METHOD_CALL) {
                    Pair<InnerCFNode, InnerCFNode> mc = destructMethodCall(statement.methodCall, cfBlock.getVariableTable());
                    prevNode.setNext(mc.getKey());
                    prevNode = mc.getValue();
                } else {
                    throw new RuntimeException("impossible to reach...");
                }
            }
            prevNode.setNext(endNode);
            InnerMergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new InnerMergeBasicBlocksAndRemoveNops(cfBlock);
            startNode.accept(mergeBasicBlocksAndRemoveNops);
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
            Pair<InnerCFNode, InnerCFNode> ct = destructExprAssignTemp(cfConditional.getBoolExpr(), cond, cfConditional.getVariableTable());
            ct.getValue().setNext(new InnerCFNop()); // want to be able to replace with a CFEndOfMiniCFG
            InnerMergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new InnerMergeBasicBlocksAndRemoveNops(cfConditional);
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
            if (cfReturn.getReturnExpr() != null) {
                Temp t = new Temp();
                Pair<InnerCFNode, InnerCFNode> ct = destructExprAssignTemp(cfReturn.getReturnExpr(), t, cfReturn.getVariableTable());
                ct.getValue().setNext(new InnerCFNop()); // want to be able to replace with a CFEndOfMiniCFG
                InnerMergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new InnerMergeBasicBlocksAndRemoveNops(cfReturn);
                ct.getKey().accept(mergeBasicBlocksAndRemoveNops);
                cfReturn.replaceExpr(t);
                cfReturn.setMiniCFG(ct.getKey());
            }


            visited.add(cfReturn);
            for (CFNode neighbor : cfReturn.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }



    private static Pair<InnerCFNode, InnerCFNode> destructStatementLocAssign(Statement statement, VariableTable locals) {
        if (statement.statementType != Statement.LOC_ASSIGN) throw new RuntimeException("Expected statement of type LOC_ASSIGN");

        final Temp tempLocExpr = new Temp();
        final Pair<InnerCFNode, InnerCFNode> locExprCFG = statement.loc.expr != null ?
                destructExprAssignTemp(statement.loc.expr, tempLocExpr, locals) : null;
        final Temp tempAssignExpr = new Temp();
        Pair<InnerCFNode, InnerCFNode> assignExprCFG = statement.assignExpr.expr != null ?
                destructExprAssignTemp(statement.assignExpr.expr, tempAssignExpr, locals) : null;
        final InnerCFNode cfBlockAssign;
        if (statement.loc.expr != null && statement.assignExpr.expr != null) {
            // a[b] += c
            cfBlockAssign = new InnerCFBlock(new CFAssign(statement.loc.id, tempLocExpr, statement.assignExpr.assignExprOp, tempAssignExpr), locals);
            locExprCFG.getValue().setNext(assignExprCFG.getKey());
            assignExprCFG.getValue().setNext(cfBlockAssign);
            return new Pair<InnerCFNode, InnerCFNode>(locExprCFG.getKey(),cfBlockAssign);
        } else if (statement.loc.expr == null && statement.assignExpr.expr == null){
            // a++
            cfBlockAssign = new InnerCFBlock(new CFAssign(statement.loc.id, null, statement.assignExpr.assignExprOp, null),
                    locals);
            return new Pair<InnerCFNode, InnerCFNode>(cfBlockAssign,cfBlockAssign);
        } else if (statement.loc.expr != null && statement.assignExpr.expr == null) {
            // a[b] ++
            cfBlockAssign = new InnerCFBlock(new CFAssign(statement.loc.id, tempLocExpr, statement.assignExpr.assignExprOp, null), locals);
            locExprCFG.getValue().setNext(cfBlockAssign);
            return new Pair<InnerCFNode, InnerCFNode>(assignExprCFG.getKey(),cfBlockAssign);
        } else if (statement.loc.expr == null && statement.assignExpr.expr != null) {
            // a += b
            cfBlockAssign = new InnerCFBlock(new CFAssign(statement.loc.id, null, statement.assignExpr.assignExprOp, tempAssignExpr), locals);
            assignExprCFG.getValue().setNext(cfBlockAssign);
            return new Pair<InnerCFNode, InnerCFNode>(assignExprCFG.getKey(),cfBlockAssign);
        } else {
            throw new RuntimeException("destructStatemensLocAssign: Impossible to reach...");
        }
    }

    private static Pair<InnerCFNode,InnerCFNode> destructExprAssignTemp(Expr expr, Temp temp, VariableTable locals) {
        switch (expr.exprType) {
            case Expr.LEN:
                InnerCFBlock cfBlockLen = new InnerCFBlock(CFTempAssign.makeLen(temp, expr.id), locals);
                return new Pair<InnerCFNode, InnerCFNode>(cfBlockLen, cfBlockLen);
            case Expr.LIT:
                InnerCFBlock cfBlockLit = new InnerCFBlock(CFTempAssign.makeLit(temp, expr.lit), locals);
                return new Pair<InnerCFNode, InnerCFNode>(cfBlockLit, cfBlockLit);
            case Expr.MINUS:
                Temp tempMinusOperand = new Temp();
                Pair<InnerCFNode, InnerCFNode> minusOperandCFG = destructExprAssignTemp(expr.expr, tempMinusOperand, locals);
                InnerCFBlock cfBlockMinus = new InnerCFBlock(CFTempAssign.makeMinus(temp, tempMinusOperand), locals);
                minusOperandCFG.getValue().setNext(cfBlockMinus);
                return new Pair<InnerCFNode, InnerCFNode>(minusOperandCFG.getKey(), cfBlockMinus);
            case Expr.LOC:
                final Temp tempLocExpr = new Temp();
                if (expr.loc.expr != null) {
                    final Pair<InnerCFNode, InnerCFNode> locExprCFG = destructExprAssignTemp(expr.loc.expr, tempLocExpr, locals);
                    InnerCFBlock cfBlockTempAssign = new InnerCFBlock(CFTempAssign.makeLoc(temp, expr.loc.id, tempLocExpr), locals);
                    locExprCFG.getValue().setNext(cfBlockTempAssign);
                    return new Pair<>(locExprCFG.getKey(),cfBlockTempAssign);
                } else {
                    InnerCFBlock cfBlockTempAssign = new InnerCFBlock(CFTempAssign.makeLoc(temp, expr.loc.id), locals);
                    return new Pair<>(cfBlockTempAssign, cfBlockTempAssign);
                }
            case Expr.METHOD_CALL:
                Pair<InnerCFNode, InnerCFNode> methodCallCFG = destructMethodCall(expr.methodCall, locals);
                InnerCFBlock cfBlockLoadRax = new InnerCFBlock(CFTempAssign.makeLoadRax(temp), locals);
                methodCallCFG.getValue().setNext(cfBlockLoadRax);
                return new Pair<InnerCFNode, InnerCFNode>(methodCallCFG.getKey(), cfBlockLoadRax);
            case Expr.NOT:
                Temp tempNotOperand = new Temp();
                Pair<InnerCFNode, InnerCFNode> notOperandCFG = destructExprAssignTemp(expr.expr, tempNotOperand, locals);
                InnerCFBlock cfBlockNot = new InnerCFBlock(CFTempAssign.makeNot(temp, tempNotOperand), locals);
                notOperandCFG.getValue().setNext(cfBlockNot);
                return new Pair<InnerCFNode, InnerCFNode>(notOperandCFG.getKey(), cfBlockNot);
            case Expr.BIN_OP:
                Temp left = new Temp();
                Temp right = new Temp();
                Pair<InnerCFNode, InnerCFNode> leftCFG = destructExprAssignTemp(expr.expr, left, locals);
                Pair<InnerCFNode, InnerCFNode> rightCFG = destructExprAssignTemp(expr.binOpExpr, right, locals);

                // for shortcircuiting
                InnerCFBlock assignTrue = new InnerCFBlock(CFTempAssign.assignTrue(temp), locals);
                InnerCFBlock assignFalse = new InnerCFBlock(CFTempAssign.assignFalse(temp), locals);
                InnerCFNode end = new InnerCFNop();
                assignTrue.setNext(end);
                assignFalse.setNext(end);

                switch (expr.binOp.binOp) {
                    case BinOp.AND:
                        InnerCFNode rightAnd = new InnerCFConditional(right, assignTrue, assignFalse, locals);
                        InnerCFNode leftAnd = new InnerCFConditional(left, rightCFG.getKey(), assignFalse, locals);
                        rightCFG.getValue().setNext(rightAnd);
                        leftCFG.getValue().setNext(leftAnd);
                        return new Pair<InnerCFNode, InnerCFNode>(leftCFG.getKey(), end);
                    case BinOp.OR:
                        InnerCFNode rightOr = new InnerCFConditional(right, assignTrue, assignFalse, locals);
                        InnerCFNode leftOr = new InnerCFConditional(left, assignTrue, rightCFG.getKey(), locals);
                        rightCFG.getValue().setNext(rightOr);
                        leftCFG.getValue().setNext(leftOr);
                        return new Pair<InnerCFNode, InnerCFNode>(leftCFG.getKey(), end);
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
                        InnerCFBlock assignBinOp = new InnerCFBlock(CFTempAssign.assignBinOp(temp, left, expr.binOp, right), locals);
                        rightCFG.getValue().setNext(assignBinOp);
                        return new Pair<>(leftCFG.getKey(), assignBinOp);
                    default:
                        throw new RuntimeException("Unknown BinOp: " + expr.binOp.binOp);

                }
            default:
                throw new RuntimeException("Unknown Expr " + expr.exprType);
        }
    }

    private static Pair<InnerCFNode,InnerCFNode> destructMethodCall(MethodCall methodCall, VariableTable locals) {
        List<Pair<Temp, StringLit>> argTemps = new ArrayList<>();
        InnerCFNode methodCallStart = new InnerCFNop();
        InnerCFNode prev = methodCallStart;
        for (Pair<Expr,StringLit> arg: methodCall.arguments) {
            if (arg.getKey() != null) {
                // expr
                Temp argTemp = new Temp();
                Pair<InnerCFNode, InnerCFNode> argPrep = destructExprAssignTemp(arg.getKey(), argTemp, locals);
                prev.setNext(argPrep.getKey());
                prev = argPrep.getValue();
                argTemps.add(new Pair<Temp, StringLit>(argTemp, null));
            } else {
                argTemps.add(new Pair<Temp, StringLit>(null, arg.getValue()));
            }

        }
        InnerCFBlock cfBlockMethodCall = new InnerCFBlock(new CFMethodCall(methodCall.methodName, argTemps), locals);
        prev.setNext(cfBlockMethodCall);
        return new Pair<InnerCFNode, InnerCFNode>(methodCallStart, cfBlockMethodCall);
    }

}
