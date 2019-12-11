package edu.mit.compilers.cfg;

import java.io.PrintStream;
import java.util.Set;

import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.ProgramDescriptor;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.*;

public class MethodCFGFactory {

    /**
     * Creates CFG for all methods,
     * Cleans CFG for all methods (removal of Nops and Merging Basic Blocks)
     * Sets CFG as in all MethodDescriptors (mutates programDescriptor)
     * @param programDescriptor
     */
    public static void makeAndSetMethodCFGs(ProgramDescriptor programDescriptor) {
        for (MethodDescriptor methodDescriptor: programDescriptor.methodTable.values()) {
            methodDescriptor.setMethodCFG(makeBlockCFG(methodDescriptor.getMethodBlock(), methodDescriptor));
        }
    }

    private static OuterCFNode makeBlockCFG(Block block, MethodDescriptor methodDescriptor) {
        OuterCFNode contLoop = new CFNop(); // dummy node
        OuterCFNode endBlock = new CFNop();
        OuterCFNode methodCFG = makeBlockCFG(block, endBlock, contLoop, endBlock, methodDescriptor);
        MergeBasicBlocksAndRemoveNops mergeBasicBlocksAndRemoveNops = new MergeBasicBlocksAndRemoveNops(methodDescriptor);
        methodCFG.accept(mergeBasicBlocksAndRemoveNops);

        methodCFG = mergeBasicBlocksAndRemoveNops.getFirstNodeOfCFG(); // replace with new first node of CFG
        TempifySubExpressions tempifySubExpressions = new TempifySubExpressions();
        methodCFG.accept(tempifySubExpressions);
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
    private static OuterCFNode makeBlockCFG(Block block, OuterCFNode endBlock, OuterCFNode contLoop, OuterCFNode breakLoop, MethodDescriptor methodDescriptor) {
        final OuterCFNode startBlock = new CFNop();
        OuterCFNode previousCFNode = startBlock;
        for (Statement statement : block.statements) {
            switch (statement.statementType) {
                case Statement.LOC_ASSIGN:
                case Statement.METHOD_CALL:
                    final OuterCFNode cfBlock = new CFBlock(statement, block.localTable);
                    previousCFNode.setNext(cfBlock);
                    previousCFNode = cfBlock;
                    break;
                case Statement.IF:
                    final OuterCFNode endIf = new CFNop();
                    final OuterCFNode cfIfBlock = makeBlockCFG(statement.ifBlock, endIf, contLoop, breakLoop, methodDescriptor);
                    final OuterCFNode startIf;
                    if (statement.elseBlock == null) {
                        startIf = shortCircuit(statement.expr, cfIfBlock, endIf, block.localTable);
                    } else {
                        final OuterCFNode cfElseBlock = makeBlockCFG(statement.elseBlock, endIf, contLoop, breakLoop, methodDescriptor);
                        startIf = shortCircuit(statement.expr, cfIfBlock, cfElseBlock, block.localTable);
                    }
                    previousCFNode.setNext(startIf);
                    previousCFNode = endIf;
                    break;
                case Statement.FOR:
                    final OuterCFNode endFor = new CFNop();
                    final OuterCFNode initAssignment = new CFBlock(statement.initAssignment, block.localTable);
                    final OuterCFNode contFor = new CFBlock(statement.updateAssignment, block.localTable);
                    final OuterCFNode cfForBlock = makeBlockCFG(statement.block, contFor, contFor, endFor, methodDescriptor);
                    final OuterCFNode startFor = shortCircuit(statement.exitExpr, cfForBlock, endFor, block.localTable);
                    previousCFNode.setNext(initAssignment);
                    initAssignment.setNext(startFor);
                    contFor.setNext(startFor);
                    previousCFNode = endFor;
                    break;
                case Statement.WHILE:
                    final OuterCFNode endWhile = new CFNop();
                    final OuterCFNode contWhile = new CFNop();
                    final OuterCFNode cfWhileBlock = makeBlockCFG(statement.block, contWhile, contWhile, endWhile, methodDescriptor);
                    contWhile.setNext(shortCircuit(statement.expr, cfWhileBlock, endWhile, block.localTable));
                    previousCFNode.setNext(contWhile);
                    previousCFNode = endWhile;
                    break;
                case Statement.RETURN:
                    final OuterCFNode cfReturn = new CFReturn(statement.expr, block.localTable, methodDescriptor);
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
     * @param expr
     * @param ifTrue
     * @param ifFalse
     * @return CFNode representing conditional evaluation of the expr
     */
    private static OuterCFNode shortCircuit(Expr expr, OuterCFNode ifTrue, OuterCFNode ifFalse, VariableTable variableTable) {
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
                        OuterCFNode rightAnd = shortCircuit(expr.binOpExpr,ifTrue, ifFalse, variableTable);
                        OuterCFNode leftAnd = shortCircuit(expr.expr, rightAnd, ifFalse, variableTable);
                        return leftAnd;
                    case BinOp.OR:
                        OuterCFNode rightOr = shortCircuit(expr.binOpExpr,ifTrue, ifFalse, variableTable);
                        OuterCFNode leftOr = shortCircuit(expr.expr, ifTrue, rightOr, variableTable);
                        return leftOr;
                    case BinOp.EQ:
                    case BinOp.NEQ:
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

    public static void dfsPrint(OuterCFNode cfg, Set<Integer> visited, PrintStream outputStream) {
        int cfgID = cfg.getUID();
        if (!visited.contains(cfgID)) {
            visited.add(cfgID);
            outputStream.println(cfg.toString());
            for (OuterCFNode neighbor : cfg.dfsTraverse()) {
                dfsPrint(neighbor, visited, outputStream);
            }
        }
    }

    public static void dfsPrint(InnerCFNode miniCFG, Set<Integer> visited, PrintStream outputStream) {
        int cfgID = miniCFG.getUID();
        if (!visited.contains(cfgID)) {
            visited.add(cfgID);
            outputStream.println(miniCFG.toString());
            for (InnerCFNode neighbor : miniCFG.dfsTraverse()) {
                dfsPrint(neighbor, visited, outputStream);
            }
        }
    }
}
