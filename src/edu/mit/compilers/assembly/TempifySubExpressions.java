package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.BinOp;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.MethodCall;
import edu.mit.compilers.parser.StringLit;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.ArrayList;
import java.util.List;

public class TempifySubExpressions implements CFVisitor {

    @Override
    public void visit(CFBlock cfBlock) {
        List<CFStatement> newStatements = new ArrayList<>();
    }

    @Override
    public void visit(CFConditional cfConditional) {

    }

    @Override
    public void visit(CFNop cfNop) {

    }

    @Override
    public void visit(CFReturn cfReturn) {

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
