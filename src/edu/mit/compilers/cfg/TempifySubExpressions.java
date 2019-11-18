package edu.mit.compilers.cfg;

import edu.mit.compilers.cfg.innercfg.*;
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

            // replace with new first node of CFG
            cfBlock.setMiniCFG(mergeBasicBlocksAndRemoveNops.getFirstNodeOfCFG(),
                    mergeBasicBlocksAndRemoveNops.getLastNodeOfCFG());
            visited.add(cfBlock);
            for (CFNode neighbor : cfBlock.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        if (!visited.contains(cfConditional)) {
            Expr toTempify = cfConditional.getBoolExpr();
            Temp cond = new Temp();
            Pair<InnerCFNode, InnerCFNode> ct = destructExprAssignTemp(toTempify, cond, null, cfConditional.getVariableTable());
            ct.getValue().setNext(new InnerCFNop()); // want to be able to replace with a CFEndOfMiniCFG
            InnerMergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new InnerMergeBasicBlocksAndRemoveNops(cfConditional);
            ct.getKey().accept(mergeBasicBlocksAndRemoveNops);
            cfConditional.replaceExpr(cond);
            cfConditional.setMiniCFG(mergeBasicBlocksAndRemoveNops.getFirstNodeOfCFG(),
                    mergeBasicBlocksAndRemoveNops.getLastNodeOfCFG());

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
                Expr toTempify = cfReturn.getReturnExpr();
                Temp t = new Temp();
                Pair<InnerCFNode, InnerCFNode> ct = destructExprAssignTemp(toTempify, t, null, cfReturn.getVariableTable());
                ct.getValue().setNext(new InnerCFNop()); // want to be able to replace with a CFEndOfMiniCFG
                InnerMergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new InnerMergeBasicBlocksAndRemoveNops(cfReturn);
                ct.getKey().accept(mergeBasicBlocksAndRemoveNops);
                cfReturn.replaceExpr(t);
                cfReturn.setMiniCFG(mergeBasicBlocksAndRemoveNops.getFirstNodeOfCFG(),
                        mergeBasicBlocksAndRemoveNops.getLastNodeOfCFG());
            }


            visited.add(cfReturn);
            for (CFNode neighbor : cfReturn.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }

    /**
     * eg:
     *      a ++
     *      a[b] ++
     *      a += c[d]
     *      a[b] += c[d]
     *
     * @param statement
     * @param locals
     * @return
     */
    private static Pair<InnerCFNode, InnerCFNode> destructStatementLocAssign(Statement statement, VariableTable locals) {
        if (statement.statementType != Statement.LOC_ASSIGN) throw new RuntimeException("Expected statement of type LOC_ASSIGN");

        // precompute b in location a[b]
        Expr locExpr = statement.loc.expr;
        final Temp tempLocExpr = new Temp();
        final Pair<InnerCFNode, InnerCFNode> locExprCFG = locExpr != null ?
                destructExprAssignTemp(locExpr, tempLocExpr, null, locals) : null;

        Expr assignExpr = statement.assignExpr.expr;
        final Variable locId = new Variable(statement.loc.id);

        // handle "=" operations because not as many intermediate temps
        if (statement.assignExpr.assignExprOp.equals(AssignExpr.ASSIGN)) {
            if (statement.loc.expr != null) {
                Pair<InnerCFNode, InnerCFNode> statementCFG = destructExprAssignTemp(assignExpr, locId, tempLocExpr, locals);
                locExprCFG.getValue().setNext(statementCFG.getKey());
                return new Pair(locExprCFG.getKey(), statementCFG.getValue());
            } else {
                Pair<InnerCFNode, InnerCFNode> statementCFG = destructExprAssignTemp(assignExpr, locId, null, locals);
                return statementCFG;
            }
        }

        // handle "+=" "-=" "++" "--" operations because CFAssign does not support direct methods yet 
        final Temp tempAssignExpr = new Temp();
        Pair<InnerCFNode, InnerCFNode> assignExprCFG = assignExpr != null ?
                destructExprAssignTemp(assignExpr, tempAssignExpr, null, locals) : null;

        final InnerCFNode cfBlockAssign;

        if (statement.loc.expr != null && statement.assignExpr.expr != null) {
            // a[b] += c
            // canonicalExpr = c
            cfBlockAssign = new InnerCFBlock(CFAssign.makeSimple(locId, tempLocExpr, statement.assignExpr.assignExprOp, tempAssignExpr, assignExpr), locals);
            locExprCFG.getValue().setNext(assignExprCFG.getKey());
            assignExprCFG.getValue().setNext(cfBlockAssign);
            return new Pair<InnerCFNode, InnerCFNode>(locExprCFG.getKey(),cfBlockAssign);
        } else if (statement.loc.expr == null && statement.assignExpr.expr == null){
            // a++
            // canonicalExpr = null
            cfBlockAssign = new InnerCFBlock(CFAssign.makeSimple(locId, null, statement.assignExpr.assignExprOp, null, null),
                    locals);
            return new Pair<InnerCFNode, InnerCFNode>(cfBlockAssign,cfBlockAssign);
        } else if (statement.loc.expr != null && statement.assignExpr.expr == null) {
            // a[b] ++
            // canonicalExpr = null
            cfBlockAssign = new InnerCFBlock(CFAssign.makeSimple(locId, tempLocExpr, statement.assignExpr.assignExprOp, null, null), locals);
            locExprCFG.getValue().setNext(cfBlockAssign);
            return new Pair<InnerCFNode, InnerCFNode>(assignExprCFG.getKey(),cfBlockAssign);
        } else if (statement.loc.expr == null && statement.assignExpr.expr != null) {
            // a += c
            // canonicalExpr = c
            cfBlockAssign = new InnerCFBlock(CFAssign.makeSimple(locId, null, statement.assignExpr.assignExprOp, tempAssignExpr, assignExpr), locals);
            assignExprCFG.getValue().setNext(cfBlockAssign);
            return new Pair<InnerCFNode, InnerCFNode>(assignExprCFG.getKey(),cfBlockAssign);
        } else {
            throw new RuntimeException("destructStatemensLocAssign: Impossible to reach...");
        }
    }

    private static Pair<InnerCFNode,InnerCFNode> destructExprAssignTemp(Expr expr, AssemblyVariable arrayOrLoc, AssemblyVariable arrayOffset, VariableTable locals) {
        switch (expr.exprType) {
            case Expr.LEN:
                InnerCFBlock cfBlockLen = new InnerCFBlock(CFAssign.makeLen(arrayOrLoc, arrayOffset, new Variable(expr.id), expr), locals);
                return new Pair<InnerCFNode, InnerCFNode>(cfBlockLen, cfBlockLen);
            case Expr.LIT:
                InnerCFBlock cfBlockLit = new InnerCFBlock(CFAssign.makeLit(arrayOrLoc, arrayOffset, expr.lit, expr), locals);
                return new Pair<InnerCFNode, InnerCFNode>(cfBlockLit, cfBlockLit);
            case Expr.MINUS:
                Temp tempMinusOperand = new Temp();
                Pair<InnerCFNode, InnerCFNode> minusOperandCFG = destructExprAssignTemp(expr.expr, tempMinusOperand, null, locals);
                InnerCFBlock cfBlockMinus = new InnerCFBlock(CFAssign.makeMinus(arrayOrLoc, arrayOffset, tempMinusOperand, expr), locals);
                minusOperandCFG.getValue().setNext(cfBlockMinus);
                return new Pair<InnerCFNode, InnerCFNode>(minusOperandCFG.getKey(), cfBlockMinus);
            case Expr.LOC:
                final Temp tempLocExpr = new Temp();
                final Variable locId = new Variable(expr.loc.id);
                if (expr.loc.expr != null) {
                    final Pair<InnerCFNode, InnerCFNode> locExprCFG = destructExprAssignTemp(expr.loc.expr, tempLocExpr, null, locals);
                    InnerCFBlock cfBlockTempAssign = new InnerCFBlock(CFAssign.makeArrayLocAssign(arrayOrLoc, arrayOffset, locId, tempLocExpr, expr), locals);
                    locExprCFG.getValue().setNext(cfBlockTempAssign);
                    return new Pair<>(locExprCFG.getKey(),cfBlockTempAssign);
                } else {
                    InnerCFBlock cfBlockTempAssign = new InnerCFBlock(CFAssign.makeLocAssign(arrayOrLoc, arrayOffset, locId, expr), locals);
                    return new Pair<>(cfBlockTempAssign, cfBlockTempAssign);
                }
            case Expr.METHOD_CALL:
                Pair<InnerCFNode, InnerCFNode> methodCallCFG = destructMethodCall(expr.methodCall, locals);
                InnerCFBlock cfBlockLoadRax = new InnerCFBlock(CFAssign.makeLoadRax(arrayOrLoc, arrayOffset, expr), locals);
                methodCallCFG.getValue().setNext(cfBlockLoadRax);
                return new Pair<InnerCFNode, InnerCFNode>(methodCallCFG.getKey(), cfBlockLoadRax);
            case Expr.NOT:
                Temp tempNotOperand = new Temp();
                Pair<InnerCFNode, InnerCFNode> notOperandCFG = destructExprAssignTemp(expr.expr, tempNotOperand, null, locals);
                InnerCFBlock cfBlockNot = new InnerCFBlock(CFAssign.makeNot(arrayOrLoc, arrayOffset, tempNotOperand, expr), locals);
                notOperandCFG.getValue().setNext(cfBlockNot);
                return new Pair<InnerCFNode, InnerCFNode>(notOperandCFG.getKey(), cfBlockNot);
            case Expr.BIN_OP:
                AssemblyVariable left;
                AssemblyVariable right;
                Pair<InnerCFNode, InnerCFNode> leftCFG;
                Pair<InnerCFNode, InnerCFNode> rightCFG;
                if (expr.expr.exprType == Expr.LOC && expr.expr.loc.expr == null) {
                    left = new Variable(expr.expr.loc.id);
                    InnerCFNop nop = new InnerCFNop();
                    leftCFG = new Pair(nop, nop);
                } else {
                    left = new Temp();
                    leftCFG = destructExprAssignTemp(expr.expr, left, null, locals);
                }
                if (expr.binOpExpr.exprType == Expr.LOC && expr.binOpExpr.loc.expr == null) {
                    right = new Variable(expr.binOpExpr.loc.id);
                    InnerCFNop nop = new InnerCFNop();
                    rightCFG = new Pair(nop, nop);
                } else {
                    right = new Temp();
                    rightCFG = destructExprAssignTemp(expr.binOpExpr, right, null, locals);
                }

                // for shortcircuiting
                InnerCFBlock assignTrue = new InnerCFBlock(CFAssign.assignTrue(arrayOrLoc, arrayOffset, expr), locals);
                InnerCFBlock assignFalse = new InnerCFBlock(CFAssign.assignFalse(arrayOrLoc, arrayOffset, expr), locals);
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
                        InnerCFBlock assignBinOp = new InnerCFBlock(CFAssign.assignBinOp(arrayOrLoc, arrayOffset, left, expr.binOp, right, expr), locals);
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
                Pair<InnerCFNode, InnerCFNode> argPrep = destructExprAssignTemp(arg.getKey(), argTemp, null, locals);
                prev.setNext(argPrep.getKey());
                prev = argPrep.getValue();
                argTemps.add(new Pair<Temp, StringLit>(argTemp, null));
            } else {
                argTemps.add(new Pair<Temp, StringLit>(null, arg.getValue()));
            }

        }
        InnerCFBlock cfBlockMethodCall = new InnerCFBlock(new CFMethodCall(methodCall.methodName, argTemps, locals), locals);
        prev.setNext(cfBlockMethodCall);
        return new Pair<InnerCFNode, InnerCFNode>(methodCallStart, cfBlockMethodCall);
    }

}
