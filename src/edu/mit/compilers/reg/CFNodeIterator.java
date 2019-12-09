package edu.mit.compilers.reg;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.mit.compilers.cfg.CFBlock;
import edu.mit.compilers.cfg.CFConditional;
import edu.mit.compilers.cfg.CFNode;
import edu.mit.compilers.cfg.CFNop;
import edu.mit.compilers.cfg.CFReturn;
import edu.mit.compilers.cfg.OuterCFNode;
import edu.mit.compilers.cfg.innercfg.CFStatement;
import edu.mit.compilers.cfg.innercfg.InnerCFBlock;
import edu.mit.compilers.cfg.innercfg.InnerCFConditional;
import edu.mit.compilers.cfg.innercfg.InnerCFEndOfMiniCFG;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.cfg.innercfg.InnerCFNop;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MiniCFVisitor;


public class CFNodeIterator implements CFVisitor, MiniCFVisitor {
    /*
     * A CFNodeIterator is an abstraction layer to a CFG.
     * It gives you CFNodes in their execution order.  A CFNode is
     * a fragment of a CFG containing either a USE or a DEF:
     *
     * - CFAssign
     * - CFMethodCall
     * - InnerCFConditional
     * - CFConditional (appears in the iterator after its miniCFG)
     * - CFReturn (appears in the iterator after its miniCFG)
     *
     * A CFNodeIterator performs DFS in an unspecified order in the tree.
     * It will never visit the same CFNode twice.  It also supports other
     * helpful operations, by keeping an internal record of the CFNodes
     * it has previously iterated through and the branches it has taken
     * in the CFG.  You can retreat to a previous branch point (discarding
     * accumulated CFNodes), and you can query for accumulated CFNodes.
     *
     * Note: visit() implementations are meant for internal use only.
     */

    private enum IteratorAction {
        PEEK, ADVANCE
    }

    private enum IteratorInstruction {
        BEGIN_MINICFG, END_MINICFG, NEXT_MINICFG,
    }

    private class IteratorLocation {
        OuterCFNode outerCFNode;
        boolean finishedMiniCFG;
        InnerCFNode innerCFNode;
        int innerCFBlockStatement;

        /**
         * Create a new Iterator location.  Contains all information necessary to determine next CFNode.
         * @param outerCFNode
         * @param finishedWithOuter true if next() == outerCFNode
         *          false if next() is in outerCFNode's miniCFG (or empty miniCFG)
         * @param innerCFNode
         *          if innerCFNode == null && !finishedWithOuter, signifies have not
         *              started iterating the miniCFG of outerCFNode (next == outerCFNode.getMiniCFG)
         *          if innerCFNode == null && finishedWithOuter, signifies we have
         *              finished iterating the miniCFG of outerCFNode (next == outerCFNode)
         * @param finishedWithInner
         *          true if next() == InnerCFConditional or CFStatement for innerCFNode's children
         *          false if next() == CFStatement
         * @param innerCFBlockStatement
         *          index into InnerCFBlock's statements if innerCFNode.instanceof () == CFBlock
         *
         */
        public IteratorLocation(OuterCFNode outerCFNode, boolean finishedWithOuter, InnerCFNode innerCFNode,
                boolean finishedWithInner, int innerCFBlockStatement) {
            this.outerCFNode = outerCFNode;
            this.finishedMiniCFG = finishedWithOuter;
            this.innerCFNode = innerCFNode;
            this.innerCFBlockStatement = innerCFBlockStatement;
        }

        /**
         * Copy the other IteratorLocation
         * @param location
         */
        public IteratorLocation(IteratorLocation location) {
            this.outerCFNode = location.outerCFNode;
            this.finishedMiniCFG = location.finishedMiniCFG;
            this.innerCFNode = location.innerCFNode;
            this.innerCFBlockStatement = location.innerCFBlockStatement;
        }
    }

    /*
     * history is a Stack
     * activePath[i].dfsTraverse().contains(activePath[i+1])
     */
    List<CFNode> activePath = new ArrayList<>();
    /*
     * branchesNotTaken indexes into activePath with the alternate branch.
     * also a Stack -- branchesNotTaken[i][0] ≤ branchesNotTaken[i+1][0]
     *
     * branchesNotTaken[i][0] ≤ activePath.size() + 1 -- may look ahead to a branch during peek(),
     * we save branches not taken so that we always take the same branch when peeking multiple times.
     */
    List<Pair<Integer, IteratorLocation>> branchesNotTaken = new ArrayList<>();

    IteratorLocation location;
    IteratorAction currentAction;
    IteratorInstruction currentInstruction;
    Optional<CFNode> instructionResult;

    Set<CFNode> visited = new HashSet<>();

    public CFNodeIterator(OuterCFNode methodCFG) {
        this.location = new IteratorLocation(methodCFG, false, null, false, -1);
        this.activePath.add(peek().get());
    }

    /**
     * Make a new iterator, with the same location as iterator, but
     * with all other attributes cleared, like any visited nodes, any
     * stored branches, etc.
     *
     * @param iterator
     */
    public CFNodeIterator(CFNodeIterator iterator) {
        this.location = new IteratorLocation(iterator.location);
        this.activePath.add(peek().get());
    }

    public boolean hasNext() {
        Optional<CFNode> next = peek();
        return next.isPresent() && !visited.contains(next.get());
    }

    /**
     * if next location is a CFNode, return that.
     * return notPresent if at a CFG end.
     */
    private Optional<CFNode> peek() {
        currentAction = IteratorAction.PEEK;

        if (location.finishedMiniCFG) {
            // TODO
        } else {
            if (location.innerCFNode != null) {
                currentInstruction = IteratorInstruction.NEXT_MINICFG;
                location.innerCFNode.accept(this);
            } else {
                currentInstruction = IteratorInstruction.BEGIN_MINICFG;
                location.outerCFNode.accept(this);
            }
        }
        return instructionResult;
    }

    /**
     *
     * @return false if location not advanced due to end of CFG.
     */
    private boolean advanceLocation() {
        currentAction = IteratorAction.ADVANCE;
        // TODO similar to peek()
        return false;
    }

    public CFNode next() {
        CFNode toReturn = peek().get();
        visited.add(toReturn);
        activePath.add(toReturn);
        advanceLocation();
        return toReturn;
    }

    /**
     * Don't include the beginning nodes.
     *
     * @return
     */
    public Set<CFNode> getActivePath() {
        // TODO Auto-generated method stub
        return null;
    }

    public void backtrackToLastBranchPoint() {
        // TODO Auto-generated method stub

    }

    public DeadEndType deadEndType() {
        Optional<CFNode> next = peek();
        if (!next.isPresent()) {
            return DeadEndType.END;
        }
        if (!visited.contains(next.get())) {
            return DeadEndType.VISITED;
        }
        return DeadEndType.ALIVE;
    }


    public CFNode deadEndNode() {
        return peek().get();
    }

    public boolean alive() {
        return !activePath.isEmpty();
    }

    @Override
    public void visit(InnerCFBlock innerCFBlock) {
        switch (currentAction) {
        case PEEK:
            switch (currentInstruction) {
            case BEGIN_MINICFG:
                if (innerCFBlock.getCfStatements().isEmpty()) {
                    throw new RuntimeException("Hopefully no empty InnerCFBlocks");
                }
                instructionResult = Optional.of(innerCFBlock.getCfStatements().get(0));
                break;
            case NEXT_MINICFG:
                // TODO
                break;
            case END_MINICFG:
                throw new RuntimeException("Unexpected END_MINICFG");
            default:
                throw new RuntimeException("Unrecognized instruction: " + currentInstruction);
            }
            break;
        case ADVANCE:
            break;
        default:
            throw new RuntimeException("Unrecognized action: " + currentAction);
        }
    }

    @Override
    public void visit(InnerCFConditional innerCFConditional) {
        switch (currentAction) {
        case PEEK:
            switch (currentInstruction) {
            case BEGIN_MINICFG:
                instructionResult = Optional.of(innerCFConditional);
                break;
            case NEXT_MINICFG:
                // TODO
                break;
            case END_MINICFG:
                throw new RuntimeException("Unexpected END_MINICFG");
            default:
                throw new RuntimeException("Unrecognized instruction: " + currentInstruction);
            }
            break;
        case ADVANCE:
            break;
        default:
            throw new RuntimeException("Unrecognized action: " + currentAction);
        }
    }

    @Override
    public void visit(InnerCFNop innerCFNop) {
        throw new RuntimeException("Expected non-endOfCFG Nops to be peephole removed from MiniCFGs: " + location.outerCFNode);
    }

    public void visit(InnerCFEndOfMiniCFG innerCFEndOfMiniCFG) {
        switch (currentAction) {
        case PEEK:
            switch (currentInstruction) {
            case BEGIN_MINICFG:
                currentInstruction = IteratorInstruction.END_MINICFG;
                innerCFEndOfMiniCFG.getEnclosingNode().getMiniCFGStart().accept(this);
                break;
            case NEXT_MINICFG:
                throw new RuntimeException("Stable Location should never be on a CFEndOfMiniCFG");
            case END_MINICFG:
                throw new RuntimeException("Unexpected END_MINICFG");
            default:
                throw new RuntimeException("Unrecognized instruction: " + currentInstruction);
            }
            break;
        case ADVANCE:
            break;
        default:
            throw new RuntimeException("Unrecognized action: " + currentAction);
        }
    }

    @Override
    public void visit(CFBlock cfBlock) {
        switch (currentAction) {
        case PEEK:
            switch (currentInstruction) {
            case BEGIN_MINICFG:
                cfBlock.getMiniCFGStart().accept(this);
                break;
            case NEXT_MINICFG:
                throw new RuntimeException("NEXT_MINICFG on an OuterCFNode");
            case END_MINICFG:
                instructionResult = Optional.of(cfBlock);
                break;
            default:
                throw new RuntimeException("Unrecognized instruction: " + currentInstruction);
            }
            break;
        case ADVANCE:
            break;
        default:
            throw new RuntimeException("Unrecognized action: " + currentAction);
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        // TODO
    }

    @Override
    public void visit(CFNop cfNop) {
        // TODO
    }

    @Override
    public void visit(CFReturn cfReturn) {
        // TODO
    }
}
