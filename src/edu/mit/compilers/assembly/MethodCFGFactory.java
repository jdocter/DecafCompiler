package edu.mit.compilers.assembly;

import java.util.Set;

import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.ProgramDescriptor;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.*;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.MergeBasicBlocksAndRemoveNops;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MethodCFGFactory {

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
        CFNode methodCFG = makeBlockCFG(block, endBlock, contLoop, endBlock);
        MergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new MergeBasicBlocksAndRemoveNops();
        methodCFG.accept(mergeBasicBlocksAndRemoveNops);
        return methodCFG;
    }

    /**
     * make a CFG of a decaf block
     *
     * @param block     block to make CFG of
     * @param endBlock  the CFNode that all sequential flow should end at
     * @param contLoop  the CFNode that control should flow towards to continue a loop
     * @param breakLoop the CFNode that control should flow towards to break out of a loop
     * @return the start CFNode of the CFG that represents block
     */
    private static CFNode makeBlockCFG(Block block, CFNode endBlock, CFNode contLoop, CFNode breakLoop) {
        final CFNode startBlock = new CFNop();
        CFNode previousCFNode = startBlock;
        for (Statement statement : block.statements) {
            switch (statement.statementType) {
                case Statement.LOC_ASSIGN:
                    final CFNode endAssign = new CFNop();
                    final CFNode cfBlockAssign = makeAssignCFG(new CFAssign(statement), endAssign, block.localTable);
                    previousCFNode.setNext(cfBlockAssign);
                    previousCFNode = endAssign;
                    break;
                case Statement.METHOD_CALL:
                    final CFNode endMethodCall = new CFNop();
                    final CFNode cfBlockMethodCall = makeMethodCFG(new CFMethodCall(statement.methodCall.methodName, statement.methodCall.arguments), endMethodCall, block.localTable);
                    previousCFNode.setNext(cfBlockMethodCall);
                    previousCFNode = endMethodCall;
                    break;
                case Statement.IF:
                    final CFNode endIf = new CFNop();
                    final CFNode cfIfBlock = makeBlockCFG(statement.ifBlock, endIf, contLoop, breakLoop);
                    final CFNode startIf;
                    if (statement.elseBlock == null) {
                        startIf = shortCircuit(statement.expr, cfIfBlock, endIf, block.localTable);
                    } else {
                        final CFNode cfElseBlock = makeBlockCFG(statement.elseBlock, endIf, contLoop, breakLoop);
                        startIf = shortCircuit(statement.expr, cfIfBlock, cfElseBlock, block.localTable);
                    }
                    previousCFNode.setNext(startIf);
                    previousCFNode = endIf;
                    break;
                case Statement.FOR:
                    final CFNode endFor = new CFNop();
                    final CFNode initAssignment = new CFBlock(new CFAssign(statement.initAssignment),block.localTable);
                    final CFNode contFor = new CFBlock(new CFAssign(statement.updateAssignment), block.localTable);
                    final CFNode cfForBlock = makeBlockCFG(statement.block, contFor, contFor, endFor);
                    final CFNode startFor = shortCircuit(statement.exitExpr, cfForBlock, endFor, block.localTable);
                    previousCFNode.setNext(initAssignment);
                    initAssignment.setNext(startFor);
                    contFor.setNext(startFor);
                    previousCFNode = endFor;
                    break;
                case Statement.WHILE:
                    final CFNode endWhile = new CFNop();
                    final CFNode contWhile = new CFNop();
                    final CFNode cfWhileBlock = makeBlockCFG(statement.block, contWhile, contWhile, endWhile);
                    contWhile.setNext(shortCircuit(statement.expr, cfWhileBlock, endWhile, block.localTable));
                    previousCFNode.setNext(contWhile);
                    previousCFNode = endWhile;
                    break;
                case Statement.RETURN:
                    final CFNode cfReturn;
                    if(statement.expr != null && hasPotentialToSC(statement.expr)) {
                        final CFNode cfReturnTrue = new CFReturn(Expr.makeTrueExpr(), block.localTable);
                        final CFNode cfReturnFalse = new CFReturn(Expr.makeFalseExpr(), block.localTable);
                        cfReturn = shortCircuit(statement.expr, cfReturnTrue, cfReturnFalse, block.localTable);
                    } else {
                      cfReturn = new CFReturn(statement.expr, block.localTable);
                    }
                    previousCFNode.setNext(cfReturn);
                    return startBlock;
                case Statement.BREAK:
                    previousCFNode.setNext(breakLoop);
                    return startBlock;
                case Statement.CONTINUE:
                    previousCFNode.setNext(contLoop);
                    return startBlock;
            }
        }
        // note that if we reach this point, previousCFNode must
        // be a CFNop, and so it supports setNext.
        previousCFNode.setNext(endBlock);
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
    private static CFNode makeMethodCFG(CFMethodCall cfMethodCall, CFNode end, VariableTable variableTable) {
        for (int i = 0; i < cfMethodCall.arguments.size(); i++) {
            Expr arg = cfMethodCall.arguments.get(i).getKey();
            if (arg != null && hasPotentialToSC(arg)) {
                final List<Pair<Expr,StringLit>> iTrueArgs = new ArrayList<>(cfMethodCall.arguments);
                iTrueArgs.set(i, new Pair<>(Expr.makeTrueExpr(), null));
                final List<Pair<Expr,StringLit>> iFalseArgs = new ArrayList<>(cfMethodCall.arguments);
                iFalseArgs.set(i, new Pair<>(Expr.makeFalseExpr(), null));
                final CFNode iTrueMethodCall = makeMethodCFG(new CFMethodCall(cfMethodCall.methodName, iTrueArgs), end, variableTable);
                final CFNode iFalseMethodCall = makeMethodCFG(new CFMethodCall(cfMethodCall.methodName, iFalseArgs), end, variableTable);
                return shortCircuit(arg, iTrueMethodCall, iFalseMethodCall, variableTable);
            }
        }
        final CFNode finalMethodCall = new CFBlock(cfMethodCall, variableTable);
        finalMethodCall.setNext(end);
        return finalMethodCall;
    }

    /**
     * @param expr
     * @param ifTrue
     * @param ifFalse
     * @return CFNode representing conditional evaluation of the expr
     */
    private static CFNode shortCircuit(Expr expr, CFNode ifTrue, CFNode ifFalse, VariableTable variableTable) {
        switch (expr.exprType) {
            case Expr.LEN:
            case Expr.MINUS:
                throw new RuntimeException("Incorrect semantic checking, tried to shortCircuit a non-boolean");
            case Expr.LOC:
            case Expr.METHOD_CALL:
            case Expr.LIT: // assume bool
                return new CFConditional(expr, ifTrue, ifFalse, variableTable);
            case Expr.NOT:
                return shortCircuit(expr.expr, ifFalse, ifTrue, variableTable);
            case Expr.BIN_OP:
                switch (expr.binOp.binOp) {
                    case BinOp.AND:
                        CFNode rightAnd = shortCircuit(expr.binOpExpr,ifTrue, ifFalse, variableTable);
                        CFNode leftAnd = shortCircuit(expr.expr, rightAnd, ifFalse, variableTable);
                        return leftAnd;
                    case BinOp.OR:
                        CFNode rightOr = shortCircuit(expr.binOpExpr,ifTrue, ifFalse, variableTable);
                        CFNode leftOr = shortCircuit(expr.expr, ifTrue, rightOr, variableTable);
                        return leftOr;
                    case BinOp.EQ:
                        // if either left or right side of EQ may short circuit, we must expand CFG
                        if (hasPotentialToSC(expr.expr) || hasPotentialToSC(expr.binOpExpr)) {
                            CFNode scIfRightEvaluatesTrue = shortCircuit(expr.binOpExpr, ifTrue, ifFalse, variableTable);
                            CFNode scIfRightEvaluatesFalse = shortCircuit(expr.binOpExpr, ifFalse, ifTrue, variableTable);
                            CFNode leftEq = shortCircuit(expr.expr, scIfRightEvaluatesTrue, scIfRightEvaluatesFalse, variableTable);
                            return leftEq;
                        } else {
                            return new CFConditional(expr, ifTrue, ifFalse, variableTable);
                        }
                    case BinOp.NEQ:
                        // if either left or right side of EQ may short circuit, we must expand CFG
                        if (hasPotentialToSC(expr.expr) || hasPotentialToSC(expr.binOpExpr)) {
                            CFNode scIfRightEvaluatesTrue = shortCircuit(expr.binOpExpr, ifTrue, ifFalse, variableTable);
                            CFNode scIfRightEvaluatesFalse = shortCircuit(expr.binOpExpr, ifFalse, ifTrue, variableTable);
                            CFNode leftNeq = shortCircuit(expr.expr, scIfRightEvaluatesFalse, scIfRightEvaluatesTrue, variableTable);
                            return leftNeq;
                        } else {
                            return new CFConditional(expr, ifTrue, ifFalse, variableTable);
                        }
                    case BinOp.GEQ:
                    case BinOp.LEQ:
                    case BinOp.GT:
                    case BinOp.LT:
                        // for these comparisons, both arguments of the binOp can and should be evaluated
                        return new CFConditional(expr, ifTrue, ifFalse, variableTable);
                    case BinOp.MINUS:
                    case BinOp.MOD:
                    case BinOp.MUL:
                    case BinOp.PLUS:
                    case BinOp.DIV:
                        // shouldn't ever get here if semantic checks done correctly
                        throw new RuntimeException("Incorrect semantic checking, tried to shortCircuit a non-boolean");
                    default:
                        throw new RuntimeException("Unknown BinOp: " + expr.binOp.binOp);

                }
            default:
                throw new RuntimeException("Unknown exprType: " + expr.exprType);
        }
    }

    /**
     * @param expr
     * @return true if expr or child of expr has potential to short circuit (NOT, AND, OR, EQ, NEQ)
     */
    private static boolean hasPotentialToSC(Expr expr) {
        return expr.exprType == Expr.NOT ||
                (expr.exprType == Expr.BIN_OP && Set.of(BinOp.AND, BinOp.OR, BinOp.EQ, BinOp.NEQ).contains(expr.binOp));
    }
}
