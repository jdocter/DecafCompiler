package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.ProgramDescriptor;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.*;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.MergeBasicBlocksAndRemoveNops;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MethodCFGFactory2 {

    /**
     * Creates CFG for all methods,
     * Cleans CFG for all methods (removal of Nops and Merging Basic Blocks)
     * Sets CFG as in all MethodDescriptors (mutates programDescriptor)
     * @param programDescriptor
     */
    public static void makeAndSetMethodCFGs(ProgramDescriptor programDescriptor) {
        for (MethodDescriptor methodDescriptor: programDescriptor.methodTable.values()) {
            methodDescriptor.setMethodCFG(makeMethodCFG(methodDescriptor.getMethodBlock()));
        }
    }

    private static CFNode makeMethodCFG(Block block) {
        CFNode contLoop = new CFNop(); // dummy node
        CFNode endBlock = new CFNop();
        Pair<CFNode,CFNode> methodCFG = destructBlock(block, contLoop, endBlock);
        MergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new MergeBasicBlocksAndRemoveNops();
        methodCFG.accept(mergeBasicBlocksAndRemoveNops);
        return methodCFG;
    }

    /**
     * make a CFG of a decaf block
     *
     * @param block     block to make CFG of
     * @param contLoop  the CFNode that control should flow towards to continue a loop
     * @param breakLoop the CFNode that control should flow towards to break out of a loop
     * @return the start CFNode of the CFG that represents block
     */
    private static Pair<CFNode,CFNode> destructBlock(Block block, CFNode contLoop, CFNode breakLoop) {
        final CFNode startBlock = new CFNop();
        final CFNode endBlock = new CFNop();
        CFNode previousCFNode = startBlock;
        for (Statement statement : block.statements) {
            switch (statement.statementType) {
                case Statement.LOC_ASSIGN:
                    final Temp tempLocExpr = new Temp();
                    final Pair<CFNode, CFNode> locExprCFG = statement.loc.expr != null ?
                            destructExprAssignTemp(statement.loc.expr, tempLocExpr, block.localTable) : null;
                    final Temp tempAssignExpr = new Temp();
                    Pair<CFNode, CFNode> assignExprCFG = statement.assignExpr.expr != null ?
                            destructExprAssignTemp(statement.assignExpr.expr, tempAssignExpr, block.localTable) : null;
                    final CFNode cfBlockAssign;
                    if (statement.loc.expr != null && statement.assignExpr.expr != null) {
                        // a[b] += c
                        cfBlockAssign = new CFBlock(new CFAssign(statement.loc.id, tempLocExpr, statement.assignExpr.assignExprOp, tempAssignExpr), block.localTable);
                        locExprCFG.getValue().setNext(assignExprCFG.getKey());
                        assignExprCFG.getValue().setNext(cfBlockAssign);
                        previousCFNode.setNext(locExprCFG.getKey());
                        previousCFNode = cfBlockAssign;
                    } else if (statement.loc.expr == null && statement.assignExpr.expr == null){
                        // a++
                        cfBlockAssign = new CFBlock(new CFAssign(statement.loc.id, null, statement.assignExpr.assignExprOp, null),
                                block.localTable);
                        previousCFNode.setNext(cfBlockAssign);
                        previousCFNode = cfBlockAssign;
                    } else if (statement.loc.expr != null && statement.assignExpr.expr == null) {
                        // a[b] ++
                        cfBlockAssign = new CFBlock(new CFAssign(statement.loc.id, tempLocExpr, statement.assignExpr.assignExprOp, null), block.localTable);
                        locExprCFG.getValue().setNext(cfBlockAssign);
                        previousCFNode.setNext(locExprCFG.getKey());
                        previousCFNode = cfBlockAssign;
                    } else if (statement.loc.expr == null && statement.assignExpr.expr != null) {
                        // a += b
                        cfBlockAssign = new CFBlock(new CFAssign(statement.loc.id, null, statement.assignExpr.assignExprOp, tempAssignExpr), block.localTable);
                        assignExprCFG.getValue().setNext(cfBlockAssign);
                        previousCFNode.setNext(assignExprCFG.getKey());
                        previousCFNode = cfBlockAssign;
                    }
                    break;
                case Statement.METHOD_CALL:
                    Pair<CFNode, CFNode> methodCallCFG = destructMethodCall(statement.methodCall, block.localTable);
                    previousCFNode.setNext(methodCallCFG.getKey());
                    previousCFNode = methodCallCFG.getValue();
                    break;
                case Statement.IF: // TODO
                    final CFNode endIf = new CFNop();
                    final Pair<CFNode, CFNode> cfIfBlock = destructBlock(statement.ifBlock, contLoop, breakLoop);
                    cfIfBlock.getValue().setNext(endIf);
                    final CFNode cfElseBlockStart;
                    if (statement.elseBlock == null) {
                        cfElseBlockStart = new CFNop();
                        cfElseBlockStart.setNext(endIf);
                    } else {
                        final Pair<CFNode, CFNode> cfElseBlock = destructBlock(statement.elseBlock, contLoop, breakLoop);
                        cfElseBlockStart = cfElseBlock.getKey();
                        cfElseBlock.getValue().setNext(endIf);
                    }
                    final CFNode startIf = shortCircuit(statement.expr, cfIfBlock.getKey(), cfElseBlockStart, block.localTable);
                    previousCFNode.setNext(startIf);
                    previousCFNode = endIf;
                    break;
                case Statement.FOR: // TODO
                    final CFNode endFor = new CFNop();
                    final CFNode initAssignment = new CFBlock(new CFAssign(statement.initAssignment),block.localTable);
                    final CFNode contFor = new CFBlock(new CFAssign(statement.updateAssignment), block.localTable);
                    final Pair<CFNode,CFNode> cfForBlock = destructBlock(statement.block, contFor, endFor);
                    cfForBlock.getValue().setNext(contFor);
                    final CFNode startFor = shortCircuit(statement.exitExpr, cfForBlock.getKey(), endFor, block.localTable);
                    previousCFNode.setNext(initAssignment);
                    initAssignment.setNext(startFor);
                    contFor.setNext(startFor);
                    previousCFNode = endFor;
                    break;
                case Statement.WHILE: // TODO
                    final CFNode endWhile = new CFNop();
                    final CFNode contWhile = new CFNop();
                    final Pair<CFNode,CFNode> cfWhileBlock = destructBlock(statement.block, contWhile, endWhile);
                    cfWhileBlock.getValue().setNext(contWhile);
                    contWhile.setNext(shortCircuit(statement.expr, cfWhileBlock.getKey(), endWhile, block.localTable));
                    previousCFNode.setNext(contWhile);
                    previousCFNode = endWhile;
                    break;
                case Statement.RETURN: // TODO
                    final CFNode cfReturn;
                    if(statement.expr != null) {
                        final Temp returnTemp = new Temp();
                        final Pair<CFNode, CFNode> cfReturnExpr = destructExprAssignTemp(statement.expr, returnTemp, block.localTable);
                        final CFNode cfReturnTrue = new CFReturn(Expr.makeTrueExpr(), block.localTable);
                        final CFNode cfReturnFalse = new CFReturn(Expr.makeFalseExpr(), block.localTable);
                        cfReturn = shortCircuit(statement.expr, cfReturnTrue, cfReturnFalse, block.localTable);
                    } else {
                        cfReturn = new CFReturn(statement.expr, block.localTable);
                    }
                    previousCFNode.setNext(cfReturn);
                    return new Pair(startBlock, endBlock);
                case Statement.BREAK:
                    previousCFNode.setNext(breakLoop);
                    return new Pair(startBlock, endBlock);
                case Statement.CONTINUE:
                    previousCFNode.setNext(contLoop);
                    return new Pair(startBlock, endBlock);
            }
        }
        // note that if we reach this point, previousCFNode must
        // be a CFNop, and so it supports setNext.
        previousCFNode.setNext(endBlock);
        return new Pair(startBlock, endBlock);
    }

    private static Pair<CFNode,CFNode> destructExprAssignTemp(Expr expr, Temp temp, VariableTable locals) {
        switch (expr.exprType) {
            case Expr.LEN:
                CFBlock cfBlockLen = CFTempAssign.makeLen(temp, expr.id);
                return new Pair(cfBlockLen, cfBlockLen);
            case Expr.LIT:
                CFBlock cfBlockLit = CFTempAssign.makeLit(temp, expr.lit);
                return new Pair(cfBlockLit, cfBlockLit);
            case Expr.MINUS:
                Temp tempMinusOperand = new Temp();
                Pair<CFNode, CFNode> minusOperandCFG = destructExprAssignTemp(expr.expr, tempMinusOperand, locals);
                CFBlock cfBlockMinus = new CFBlock(CFTempAssign.makeMinus(temp, tempMinusOperand), locals);
                minusOperandCFG.getValue().setNext(cfBlockMinus);
                return new Pair(minusOperandCFG.getKey(), cfBlockMinus);
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
                return new Pair(methodCallCFG.getKey(), cfBlockLoadRax);
            case Expr.NOT:
                Temp tempNotOperand = new Temp();
                Pair<CFNode, CFNode> notOperandCFG = destructExprAssignTemp(expr.expr, tempNotOperand, locals);
                CFBlock cfBlockNot = new CFBlock(CFTempAssign.makeNot(temp, tempNotOperand), locals);
                notOperandCFG.getValue().setNext(cfBlockNot);
                return new Pair(notOperandCFG.getKey(), cfBlockNot);
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
                        return new Pair(leftCFG.getKey(), end);
                    case BinOp.OR:
                        CFNode rightOr = new CFConditional(right, assignTrue, assignFalse, locals);
                        CFNode leftOr = new CFConditional(left, assignTrue, rightCFG.getKey(), locals);
                        rightCFG.getValue().setNext(rightOr);
                        leftCFG.getValue().setNext(leftOr);
                        return new Pair(leftCFG.getKey(), end);
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
                argTemps.add(new Pair(argTemp, null));
            } else {
                argTemps.add(new Pair(null, arg.getValue()));
            }

        }
        CFBlock cfBlockMethodCall = new CFBlock(new CFMethodCall(methodCall.methodName, argTemps), locals);
        prev.setNext(cfBlockMethodCall);
        return new Pair(methodCallStart, cfBlockMethodCall);
    }

}
