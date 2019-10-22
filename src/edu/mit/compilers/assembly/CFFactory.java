package edu.mit.compilers.assembly;

import edu.mit.compilers.parser.*;
import edu.mit.compilers.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CFFactory {

    public static CFNode makeBlockCFG(Block block) {
        CFNode contLoop = new CFNop(); // dummy node
        CFNode endBlock = new CFNop();
        return makeBlockCFG(block, endBlock, contLoop, endBlock);
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
    public static CFNode makeBlockCFG(Block block, CFNode endBlock, CFNode contLoop, CFNode breakLoop) {
        final CFNode startBlock = new CFNop();
        CFNode previousCFNode = startBlock;
        for (Statement statement : block.statements) {
            switch (statement.statementType) {
                case Statement.LOC_ASSIGN:
                    final CFNode endAssign = new CFNop();
                    final CFNode cfBlockAssign = makeAssignCFG(new CFAssign(statement.loc,
                            statement.assignExpr.assignExprOp, statement.assignExpr.expr), endAssign);
                    previousCFNode.setNext(cfBlockAssign);
                    previousCFNode = endAssign;
                    break;
                case Statement.METHOD_CALL:
                    final CFNode endMethodCall = new CFNop();
                    final CFNode cfBlockMethodCall = makeMethodCFG(new CFMethodCall(statement.methodCall.methodName,
                                    statement.methodCall.arguments), endMethodCall);
                    previousCFNode.setNext(cfBlockMethodCall);
                    previousCFNode = endMethodCall;
                    break;
                case Statement.RETURN:
                    final CFNode cfReturn;
                    if(statement.expr != null && hasPotentialToSC(statement.expr)) {
                        final cfReturnTrue = new CFReturn(Expr.makeTrueExpr());
                        final cfReturnFalse = new CFReturn(Expr.makeFalseExpr());
                        cfReturn = shortCircuit(statement.expr, cfReturnTrue, cfReturnFalse);
                    } else {
                      cfReturn = new CFReturn(statement.expr);                      
                    }
                    previousCFNode.setNext(cfReturn);
                    return startBlock;

                case Statement.IF:
                    final CFNode endIf = new CFNop();
                    final CFNode cfIfBlock = makeBlockCFG(statement.ifBlock, endIf, contLoop, breakLoop);
                    final CFNode startIf;
                    if (statement.elseBlock == null) {
                        startIf = shortCircuit(statement.expr, cfIfBlock, endIf);
                    } else {
                        final CFNode cfElseBlock = makeBlockCFG(statement.elseBlock, endIf, contLoop, breakLoop);
                        startIf = shortCircuit(statement.expr, cfIfBlock, cfElseBlock);
                    }
                    previousCFNode.setNext(startIf);
                    previousCFNode = endIf;
                    break;
                case Statement.FOR:
                    final CFNode endFor = new CFNop();
                    final CFNode initAssignment = new CFBlock(new CFAssign(statement.initAssignment));
                    final CFNode contFor = new CFBlock(new CFAssign(statement.updateAssignment));
                    final CFNode cfForBlock = makeBlockCFG(statement.block, contFor, contFor, endFor);
                    final CFNode startFor = shortCircuit(statement.exitExpr, cfForBlock, endFor);
                    previousCFNode.setNext(initAssignment);
                    initAssignment.setNext(startFor);
                    contFor.setNext(startFor);
                    previousCFNode = endFor;
                    break;
                case Statement.WHILE:
                    final CFNode endWhile = new CFNop();
                    final CFNode contWhile = new CFNop();
                    final CFNode cfWhileBlock = makeBlockCFG(statement.block, contWhile, contWhile, endWhile);
                    contWhile.setNext(shortCircuit(statement.expr, cfWhileBlock, endWhile));
                    previousCFNode.setNext(contWhile);
                    previousCFNode = endWhile;
                    break;
                case Statement.BREAK:
                    final CFNode cfBreak = new CFBreak();
                    previousCFNode.setNext(cfBreak);
                    cfBreak.setNext(breakLoop);
                    return startBlock;
                case Statement.CONTINUE:
                    final CFNode cfContinue = new CFContinue();
                    previousCFNode.setNext(cfContinue);
                    cfContinue.setNext(contLoop);
                    return startBlock;
            }
        }
        // note that if we reach this point, CFNode cannot be a CFBreak, CFContinue, or CFReturn
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
    public static CFNode makeAssignCFG(CFAssign cfAssign, CFNode endAssign) {
        if (cfAssign.expr != null && hasPotentialToSC(cfAssign.expr)) {
            final CFNode cfBlockAssignTrue = new CFBlock(new CFAssign(cfAssign.loc, cfAssign.assignOp, Expr.makeTrueExpr()));
            final CFNode cfBlockAssignFalse = new CFBlock(new CFAssign(cfAssign.loc, cfAssign.assignOp, Expr.makeTrueExpr()));
            cfBlockAssignTrue.setNext(endAssign);
            cfBlockAssignFalse.setNext(endAssign);
            return shortCircuit(cfAssign.expr, cfBlockAssignTrue, cfBlockAssignFalse);
        } else {
            final CFNode cfBlockAssign = new CFBlock(cfAssign);
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
     * @param cfMethodCall
     * @param end
     * @return
     */
    public static CFNode makeMethodCFG(CFMethodCall cfMethodCall, CFNode end) {
        for (int i = 0; i < cfMethodCall.arguments.size(); i++) {
            Expr arg = cfMethodCall.arguments.get(i).getKey();
            if (arg != null && hasPotentialToSC(arg)) {
                final List<Pair<Expr,StringLit>> iTrueArgs = new ArrayList<>(cfMethodCall.arguments);
                iTrueArgs.set(i, new Pair<>(Expr.makeTrueExpr(), null));
                final List<Pair<Expr,StringLit>> iFalseArgs = new ArrayList<>(cfMethodCall.arguments);
                iFalseArgs.set(i, new Pair<>(Expr.makeFalseExpr(), null));
                final CFNode iTrueMethodCall = makeMethodCFG(new CFMethodCall(cfMethodCall.methodName, iTrueArgs),end);
                final CFNode iFalseMethodCall = makeMethodCFG(new CFMethodCall(cfMethodCall.methodName, iFalseArgs),end);
                return shortCircuit(arg, iTrueMethodCall, iFalseMethodCall);
            }
        }
        final CFNode finalMethodCall = new CFBlock(cfMethodCall);
        finalMethodCall.setNext(end);
        return finalMethodCall;
    }

    /**
     * @param expr
     * @param ifTrue
     * @param ifFalse
     * @return CFNode representing conditional evaluation of the expr
     */
    public static CFNode shortCircuit(Expr expr, CFNode ifTrue, CFNode ifFalse) {
        switch (expr.exprType) {
            case Expr.LEN:
            case Expr.MINUS:
                throw new UnsupportedOperationException("Error in semantic checking");
            case Expr.LOC:
            case Expr.METHOD_CALL:
            case Expr.LIT: // assume bool
                return new CFConditional(expr, ifTrue, ifFalse);
            case Expr.NOT:
                return shortCircuit(expr, ifFalse, ifTrue);
            case Expr.BIN_OP:
                switch (expr.binOp.binOp) {
                    case BinOp.AND:
                        CFNode rightAnd = shortCircuit(expr.binOpExpr,ifTrue, ifFalse);
                        CFNode leftAnd = shortCircuit(expr.expr, rightAnd, ifFalse);
                        return leftAnd;
                    case BinOp.OR:
                        CFNode rightOr = shortCircuit(expr.binOpExpr,ifTrue, ifFalse);
                        CFNode leftOr = shortCircuit(expr.expr, ifTrue, rightOr);
                        return leftOr;
                    case BinOp.EQ:
                        // if either left or right side of EQ may short circuit, we must expand CFG
                        if (hasPotentialToSC(expr.expr) || hasPotentialToSC(expr.binOpExpr)) {
                            CFNode rightEqTrue = shortCircuit(expr.binOpExpr, ifTrue, ifFalse);
                            CFNode rightEqFalse = shortCircuit(expr.binOpExpr, ifFalse, ifTrue);
                            CFNode leftEq = shortCircuit(expr.expr, rightEqTrue, rightEqFalse);
                            return leftEq;
                        } else {
                            return new CFConditional(expr, ifTrue, ifFalse);
                        }
                    case BinOp.NEQ:
                        // if either left or right side of EQ may short circuit, we must expand CFG
                        if (hasPotentialToSC(expr.expr) || hasPotentialToSC(expr.binOpExpr)) {
                            CFNode rightNeqTrue = shortCircuit(expr.binOpExpr, ifTrue, ifFalse);
                            CFNode rightNeqFalse = shortCircuit(expr.binOpExpr, ifFalse, ifTrue);
                            CFNode leftNeq = shortCircuit(expr.expr, rightNeqFalse, rightNeqTrue);
                            return leftNeq;
                        } else {
                            return new CFConditional(expr, ifTrue, ifFalse);
                        }
                    case BinOp.GEQ:
                    case BinOp.LEQ:
                    case BinOp.GT:
                    case BinOp.LT:
                        // for these comparisons, both arguments of the binOp can and should be evaluated
                        return new CFConditional(expr, ifTrue, ifFalse);
                    case BinOp.MINUS:
                    case BinOp.MOD:
                    case BinOp.MUL:
                    case BinOp.PLUS:
                    case BinOp.DIV:
                        // shouldn't ever get here if semantic checks done correctly
                        throw new UnsupportedOperationException("Error in semantic checking");
                }
        }
        // default for both switch statements
        throw new UnsupportedOperationException("Error in semantic checking");
    }

    /**
     * @param expr
     * @return true if expr has potential to short circuit (NOT, AND, OR, EQ, NEQ)
     */
    private static boolean hasPotentialToSC(Expr expr) {
        return expr.exprType == Expr.NOT ||
                (expr.exprType == Expr.BIN_OP && Set.of(BinOp.AND, BinOp.OR, BinOp.EQ, BinOp.NEQ).contains(expr.binOp));
    }
}
