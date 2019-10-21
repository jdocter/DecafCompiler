package edu.mit.compilers.assembly;

import edu.mit.compilers.parser.*;

import java.util.Set;

public class CFFactory {

    public static CFNode makeCFG(Block block) {
        CFNode contLoop = new CFNop(); // dummy node
        CFNode endBlock = new CFNop();
        return makeCFG(block, endBlock, contLoop, endBlock);
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
    public static CFNode makeCFG(Block block, CFNode endBlock, CFNode contLoop, CFNode breakLoop) {
        final CFNode startBlock = new CFNop();
        CFNode previousCFNode = startBlock;
        for (Statement statement : block.statements) {
            switch (statement.statementType) {
                case Statement.LOC_ASSIGN:
                    if (statement.assignExpr.expr != null && hasPotentialToSC(statement.assignExpr.expr)) {

                    }
                case Statement.METHOD_CALL:
                    CFNode cfBlock = new CFBlock(statement);
                    previousCFNode.setNext(cfBlock);
                    previousCFNode = cfBlock;
                    break;
                case Statement.RETURN:
                    CFNode cfReturn = new CFReturn(statement.expr);
                    previousCFNode.setNext(cfReturn);
                    return startBlock;
                case Statement.IF:
                    CFNode endIf = new CFNop();
                    CFNode cfIfBlock = makeCFG(statement.ifBlock, endIf, contLoop, breakLoop);
                    CFNode startIf;
                    if (statement.elseBlock == null) {
                        startIf = shortCircuit(statement.expr, cfIfBlock, endIf);
                    } else {
                        CFNode cfElseBlock = makeCFG(statement.elseBlock, endIf, contLoop, breakLoop);
                        startIf = shortCircuit(statement.expr, cfIfBlock, cfElseBlock);
                    }
                    previousCFNode.setNext(startIf);
                    previousCFNode = endIf;
                    break;
                case Statement.FOR:
                    CFNode endFor = new CFNop();
                    CFNode initAssignment = new CFBlock(statement.initAssignment);
                    CFNode contFor = new CFBlock(statement.updateAssignment);
                    CFNode cfForBlock = makeCFG(statement.block, contFor, contFor, endFor);
                    CFNode startFor = shortCircuit(statement.exitExpr, cfForBlock, endFor);
                    previousCFNode.setNext(initAssignment);
                    initAssignment.setNext(startFor);
                    contFor.setNext(startFor);
                    previousCFNode = endFor;
                    break;
                case Statement.WHILE:
                    CFNode endWhile = new CFNop();
                    CFNode contWhile = new CFNop();
                    CFNode cfWhileBlock = makeCFG(statement.block, contWhile, contWhile, endWhile);
                    contWhile.setNext(shortCircuit(statement.expr, cfWhileBlock, endWhile));
                    previousCFNode.setNext(contWhile);
                    previousCFNode = endWhile;
                    break;
                case Statement.BREAK:
                    CFNode cfBreak = new CFBreak();
                    previousCFNode.setNext(cfBreak);
                    cfBreak.setNext(breakLoop);
                    return startBlock;
                case Statement.CONTINUE:
                    CFNode cfContinue = new CFContinue();
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
