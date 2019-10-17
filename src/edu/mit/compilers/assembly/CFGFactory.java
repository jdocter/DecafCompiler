package edu.mit.compilers.assembly;

import edu.mit.compilers.parser.Block;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Loc;
import edu.mit.compilers.parser.Statement;
import edu.mit.compilers.util.Pair;

public class CFGFactory {

    public static CFNode makeCFG(Block block) {
        CFNode contLoop = new CFNop(); // dummy node
        CFNode endBlock = new CFNop();
        return makeCFG(block, endBlock, contLoop, endBlock);
    }

    /**
     * make a CFG of a decaf block
     * @param block block to make CFG of
     * @param endBlock the CFNode that all sequential flow should end at
     * @param contLoop the CFNode that control should flow towards to continue a loop
     * @param breakLoop the CFNode that control should flow towards to break out of a loop
     * @return the start CFNode of the CFG that represents block
     */
    public static CFNode makeCFG(Block block, CFNode endBlock, CFNode contLoop, CFNode breakLoop) {
        final CFNode startBlock = new CFNop();
        CFNode previousCFNode = startBlock;
        for (Statement statement: block.statements) {
            switch (statement.statementType) {
                case Statement.LOC_ASSIGN:
                case Statement.METHOD_CALL:
                    CFNode cfBlock = new CFBlock(statement);
                    previousCFNode.setNext(cfBlock);
                    previousCFNode = cfBlock;
                    break;
                case Statement.RETURN:
                    CFNode cfReturn = new CFReturn(statement.expr); // TODO
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
                    CFNode initAssignment = new CFBlock(/* initial assignment */); // TODO
                    CFNode contFor = new CFBlock(/* iteration expression */); // TODO
                    CFNode cfForBlock = makeCFG(statement.block, contFor, contFor, endFor);
                    CFNode startFor = shortCircuit(statement.exitExpr, cfForBlock, endFor);
                    previousCFNode.setNext(initAssignment);
                    initAssignment.setNext(startFor);
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
                    CFNode cfContinue = new CFNop();
                    previousCFNode.setNext(cfContinue);
                    cfContinue.setNext(contLoop);
                    return startBlock;
            }
        }
        previousCFNode.setNext(endBlock);
        return startBlock;
    }

    /**
     *
     * @param expr
     * @param ifTrue
     * @param ifFalse
     * @return
     */
    public static CFNode shortCircuit(Expr expr, CFNode ifTrue, CFNode ifFalse) {
        // TODO
    }
}
