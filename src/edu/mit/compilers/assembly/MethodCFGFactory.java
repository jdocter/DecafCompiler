package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.ProgramDescriptor;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.*;
<<<<<<< HEAD
||||||| merged common ancestors
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.MergeBasicBlocksAndRemoveNops;

import java.util.ArrayList;
import java.util.List;
=======
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.MergeBasicBlocksAndRemoveNops;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
>>>>>>> 59c2ceb1ce97a899a653b4ad9d9dafc412033a2b

public class MethodCFGFactory2 {

    /**
     * Creates CFG for all methods,
     * Cleans CFG for all methods (removal of Nops and Merging Basic Blocks)
     * Sets CFG as in all MethodDescriptors (mutates programDescriptor)
     * @param programDescriptor
     */
    public static void makeAndSetMethodCFGs(ProgramDescriptor programDescriptor) {
        for (MethodDescriptor methodDescriptor: programDescriptor.methodTable.values()) {
<<<<<<< HEAD
            methodDescriptor.setMethodCFG(makeBlockCFG(methodDescriptor.getMethodBlock(), methodDescriptor));
||||||| merged common ancestors
            methodDescriptor.setMethodCFG(makeBlockCFG(methodDescriptor.getMethodBlock()));
=======
            methodDescriptor.setMethodCFG(makeMethodCFG(methodDescriptor.getMethodBlock()));
>>>>>>> 59c2ceb1ce97a899a653b4ad9d9dafc412033a2b
        }
    }

<<<<<<< HEAD
    private static CFNode makeBlockCFG(Block block, MethodDescriptor methodDescriptor) {
||||||| merged common ancestors
    private static CFNode makeBlockCFG(Block block) {
=======
    private static CFNode makeMethodCFG(Block block) {
>>>>>>> 59c2ceb1ce97a899a653b4ad9d9dafc412033a2b
        CFNode contLoop = new CFNop(); // dummy node
        CFNode endBlock = new CFNop();
<<<<<<< HEAD
        CFNode methodCFG = makeBlockCFG(block, endBlock, contLoop, endBlock, methodDescriptor);
        MergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new MergeBasicBlocksAndRemoveNops(methodDescriptor);
||||||| merged common ancestors
        CFNode methodCFG = makeBlockCFG(block, endBlock, contLoop, endBlock);
        MergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new MergeBasicBlocksAndRemoveNops();
=======
        Pair<CFNode,CFNode> methodCFG = destructBlock(block, contLoop, endBlock);
        MergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new MergeBasicBlocksAndRemoveNops();
>>>>>>> 59c2ceb1ce97a899a653b4ad9d9dafc412033a2b
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
<<<<<<< HEAD
    private static CFNode makeBlockCFG(Block block, CFNode endBlock, CFNode contLoop, CFNode breakLoop, MethodDescriptor methodDescriptor) {
||||||| merged common ancestors
    private static CFNode makeBlockCFG(Block block, CFNode endBlock, CFNode contLoop, CFNode breakLoop) {
=======
    private static Pair<CFNode,CFNode> destructBlock(Block block, CFNode contLoop, CFNode breakLoop) {
>>>>>>> 59c2ceb1ce97a899a653b4ad9d9dafc412033a2b
        final CFNode startBlock = new CFNop();
        final CFNode endBlock = new CFNop();
        CFNode previousCFNode = startBlock;
        for (Statement statement : block.statements) {
            switch (statement.statementType) {
                case Statement.LOC_ASSIGN:
<<<<<<< HEAD
||||||| merged common ancestors
                    final CFNode endAssign = new CFNop();
                    final CFNode cfBlockAssign = makeAssignCFG(new CFAssign(statement), endAssign, block.localTable);
                    previousCFNode.setNext(cfBlockAssign);
                    previousCFNode = endAssign;
                    break;
=======
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
>>>>>>> 59c2ceb1ce97a899a653b4ad9d9dafc412033a2b
                case Statement.METHOD_CALL:
<<<<<<< HEAD
                    final CFNode cfBlock = new CFBlock(statement, block.localTable);
                    previousCFNode.setNext(cfBlock);
                    previousCFNode = cfBlock;
||||||| merged common ancestors
                    final CFNode endMethodCall = new CFNop();
                    final CFNode cfBlockMethodCall = makeMethodCallCFG(new CFMethodCall(statement.methodCall.methodName, statement.methodCall.arguments), endMethodCall, block.localTable);
                    previousCFNode.setNext(cfBlockMethodCall);
                    previousCFNode = endMethodCall;
=======
                    Pair<CFNode, CFNode> methodCallCFG = destructMethodCall(statement.methodCall, block.localTable);
                    previousCFNode.setNext(methodCallCFG.getKey());
                    previousCFNode = methodCallCFG.getValue();
>>>>>>> 59c2ceb1ce97a899a653b4ad9d9dafc412033a2b
                    break;
                case Statement.IF: // TODO
                    final CFNode endIf = new CFNop();
<<<<<<< HEAD
                    final CFNode cfIfBlock = makeBlockCFG(statement.ifBlock, endIf, contLoop, breakLoop, methodDescriptor);
                    final CFNode startIf;
||||||| merged common ancestors
                    final CFNode cfIfBlock = makeBlockCFG(statement.ifBlock, endIf, contLoop, breakLoop);
                    final CFNode startIf;
=======
                    final Pair<CFNode, CFNode> cfIfBlock = destructBlock(statement.ifBlock, contLoop, breakLoop);
                    cfIfBlock.getValue().setNext(endIf);
                    final CFNode cfElseBlockStart;
>>>>>>> 59c2ceb1ce97a899a653b4ad9d9dafc412033a2b
                    if (statement.elseBlock == null) {
                        cfElseBlockStart = new CFNop();
                        cfElseBlockStart.setNext(endIf);
                    } else {
<<<<<<< HEAD
                        final CFNode cfElseBlock = makeBlockCFG(statement.elseBlock, endIf, contLoop, breakLoop, methodDescriptor);
                        startIf = shortCircuit(statement.expr, cfIfBlock, cfElseBlock, block.localTable);
||||||| merged common ancestors
                        final CFNode cfElseBlock = makeBlockCFG(statement.elseBlock, endIf, contLoop, breakLoop);
                        startIf = shortCircuit(statement.expr, cfIfBlock, cfElseBlock, block.localTable);
=======
                        final Pair<CFNode, CFNode> cfElseBlock = destructBlock(statement.elseBlock, contLoop, breakLoop);
                        cfElseBlockStart = cfElseBlock.getKey();
                        cfElseBlock.getValue().setNext(endIf);
>>>>>>> 59c2ceb1ce97a899a653b4ad9d9dafc412033a2b
                    }
                    final CFNode startIf = shortCircuit(statement.expr, cfIfBlock.getKey(), cfElseBlockStart, block.localTable);
                    previousCFNode.setNext(startIf);
                    previousCFNode = endIf;
                    break;
                case Statement.FOR: // TODO
                    final CFNode endFor = new CFNop();
<<<<<<< HEAD
                    final CFNode initAssignment = new CFBlock(statement.initAssignment, block.localTable);
                    final CFNode contFor = new CFBlock(statement.updateAssignment, block.localTable);
                    final CFNode cfForBlock = makeBlockCFG(statement.block, contFor, contFor, endFor, methodDescriptor);
                    final CFNode startFor = shortCircuit(statement.exitExpr, cfForBlock, endFor, block.localTable);
||||||| merged common ancestors
                    final CFNode initAssignment = new CFBlock(new CFAssign(statement.initAssignment),block.localTable);
                    final CFNode contFor = new CFBlock(new CFAssign(statement.updateAssignment), block.localTable);
                    final CFNode cfForBlock = makeBlockCFG(statement.block, contFor, contFor, endFor);
                    final CFNode startFor = shortCircuit(statement.exitExpr, cfForBlock, endFor, block.localTable);
=======
                    final CFNode initAssignment = new CFBlock(new CFAssign(statement.initAssignment),block.localTable);
                    final CFNode contFor = new CFBlock(new CFAssign(statement.updateAssignment), block.localTable);
                    final Pair<CFNode,CFNode> cfForBlock = destructBlock(statement.block, contFor, endFor);
                    cfForBlock.getValue().setNext(contFor);
                    final CFNode startFor = shortCircuit(statement.exitExpr, cfForBlock.getKey(), endFor, block.localTable);
>>>>>>> 59c2ceb1ce97a899a653b4ad9d9dafc412033a2b
                    previousCFNode.setNext(initAssignment);
                    initAssignment.setNext(startFor);
                    contFor.setNext(startFor);
                    previousCFNode = endFor;
                    break;
                case Statement.WHILE: // TODO
                    final CFNode endWhile = new CFNop();
                    final CFNode contWhile = new CFNop();
<<<<<<< HEAD
                    final CFNode cfWhileBlock = makeBlockCFG(statement.block, contWhile, contWhile, endWhile, methodDescriptor);
                    contWhile.setNext(shortCircuit(statement.expr, cfWhileBlock, endWhile, block.localTable));
||||||| merged common ancestors
                    final CFNode cfWhileBlock = makeBlockCFG(statement.block, contWhile, contWhile, endWhile);
                    contWhile.setNext(shortCircuit(statement.expr, cfWhileBlock, endWhile, block.localTable));
=======
                    final Pair<CFNode,CFNode> cfWhileBlock = destructBlock(statement.block, contWhile, endWhile);
                    cfWhileBlock.getValue().setNext(contWhile);
                    contWhile.setNext(shortCircuit(statement.expr, cfWhileBlock.getKey(), endWhile, block.localTable));
>>>>>>> 59c2ceb1ce97a899a653b4ad9d9dafc412033a2b
                    previousCFNode.setNext(contWhile);
                    previousCFNode = endWhile;
                    break;
<<<<<<< HEAD
                case Statement.RETURN:
                    final CFNode cfReturn = new CFReturn(statement.expr, block.localTable, methodDescriptor);
||||||| merged common ancestors
                case Statement.RETURN:
                    final CFNode cfReturn;
                    if(statement.expr != null && hasPotentialToSC(statement.expr)) {
                        final CFNode cfReturnTrue = new CFReturn(Expr.makeTrueExpr(), block.localTable);
                        final CFNode cfReturnFalse = new CFReturn(Expr.makeFalseExpr(), block.localTable);
                        cfReturn = shortCircuit(statement.expr, cfReturnTrue, cfReturnFalse, block.localTable);
                    } else {
                      cfReturn = new CFReturn(statement.expr, block.localTable);
                    }
=======
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
>>>>>>> 59c2ceb1ce97a899a653b4ad9d9dafc412033a2b
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
<<<<<<< HEAD
        return startBlock;
    }

||||||| merged common ancestors
        return startBlock;
    }

    /**
     * determines if CFAssign has potential to short circuit and handle accordingly
     *
     * @param cfAssign
     * @param endAssign
     * @return
     */
    private static CFNode makeAssignCFG(CFAssign cfAssign, CFNode endAssign, VariableTable variableTable) {
        if (cfAssign.expr != null && hasPotentialToSC(cfAssign.expr)) {
            final CFNode cfBlockAssignTrue = new CFBlock(new CFAssign(cfAssign.loc, cfAssign.assignOp, Expr.makeTrueExpr()), variableTable);
            final CFNode cfBlockAssignFalse = new CFBlock(new CFAssign(cfAssign.loc, cfAssign.assignOp, Expr.makeFalseExpr()), variableTable);
            cfBlockAssignTrue.setNext(endAssign);
            cfBlockAssignFalse.setNext(endAssign);
            return shortCircuit(cfAssign.expr, cfBlockAssignTrue, cfBlockAssignFalse, variableTable);
        } else {
            final CFNode cfBlockAssign = new CFBlock(cfAssign, variableTable);
            cfBlockAssign.setNext(endAssign);
            return cfBlockAssign;
        }
    }

    /**
     * searches through arguments of method, if argument has potential to short circuit, then
     *      set up short circuited method calls (recursively) for false argument and true argument
     *      return short circuit based on the argument
     * otherwise
     *      return CFNode of methodCall
     *
     * this runs in n^2 time where n is number of arguments
     *
     * Also note that the compiled code will only take a path of up to length n.
     * O(n^2) space is required so O(n^2) time to build is required
     *
     * @param cfMethodCall
     * @param end
     * @return
     */
    private static CFNode makeMethodCallCFG(CFMethodCall cfMethodCall, CFNode end, VariableTable variableTable) {
        for (int i = 0; i < cfMethodCall.arguments.size(); i++) {
            Expr arg = cfMethodCall.arguments.get(i).getKey();
            if (arg != null && hasPotentialToSC(arg)) {
                final List<Pair<Expr,StringLit>> iTrueArgs = new ArrayList<>(cfMethodCall.arguments);
                iTrueArgs.set(i, new Pair<>(Expr.makeTrueExpr(), null));
                final List<Pair<Expr,StringLit>> iFalseArgs = new ArrayList<>(cfMethodCall.arguments);
                iFalseArgs.set(i, new Pair<>(Expr.makeFalseExpr(), null));
                final CFNode iTrueMethodCall = makeMethodCallCFG(new CFMethodCall(cfMethodCall.methodName, iTrueArgs), end, variableTable);
                final CFNode iFalseMethodCall = makeMethodCallCFG(new CFMethodCall(cfMethodCall.methodName, iFalseArgs), end, variableTable);
                return shortCircuit(arg, iTrueMethodCall, iFalseMethodCall, variableTable);
            }
        }
        final CFNode finalMethodCall = new CFBlock(cfMethodCall, variableTable);
        finalMethodCall.setNext(end);
        return finalMethodCall;
    }
=======
        return new Pair(startBlock, endBlock);
    }
>>>>>>> 59c2ceb1ce97a899a653b4ad9d9dafc412033a2b

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
