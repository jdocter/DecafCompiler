package edu.mit.compilers.reg;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

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
import jdk.jshell.spi.ExecutionControl.NotImplementedException;


/**
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
 *
 * Pre-condition to all substantive methods is that the iterator must be alive().
 */
public class CFNodeIterator implements CFVisitor, MiniCFVisitor {
    /*
     */

    private enum IteratorAction {
        PEEK, ADVANCE
    }

    private enum IteratorInstruction {
        FIRST_AVAILABLE_CFNODE, END_MINICFG, NEXT_MINICFG, NEXT_OUTERCFNODE
    }

    private class IteratorLocation {
        OuterCFNode outerCFNode;
        boolean finishedMiniCFG;
        InnerCFNode innerCFNode;
        int innerCFBlockStatement;
        int nextBranchChoice;

        /**
         * Create a new Iterator location.  Contains all information necessary to determine next CFNode.
         * @param outerCFNode basic location in global CF graph
         * @param finishedWithOuter true if next() is in outerCFNode.dfsTraverse()'s miniCFG
         *          false if next() is in outerCFNode's miniCFG (or empty miniCFG)
         * @param innerCFNode
         *          if innerCFNode == null && !finishedWithOuter, signifies have not
         *              started iterating the miniCFG of outerCFNode (next == outerCFNode.getMiniCFG)
         *          if innerCFNode == null && finishedWithOuter, signifies we have
         *              finished iterating the miniCFG of outerCFNode (next == outerCFNode)
         * @param innerCFBlockStatement
         *          index into InnerCFBlock's statements if innerCFNode.instanceof () == CFBlock
         *
         */
        public IteratorLocation(OuterCFNode outerCFNode, boolean finishedWithOuter, InnerCFNode innerCFNode,
                int innerCFBlockStatement, int nextBranchChoice) {
            this.outerCFNode = outerCFNode;
            this.finishedMiniCFG = finishedWithOuter;
            this.innerCFNode = innerCFNode;
            this.innerCFBlockStatement = innerCFBlockStatement;
            this.nextBranchChoice = nextBranchChoice;
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
            this.nextBranchChoice = location.nextBranchChoice;
        }

        public IteratorLocation(OuterCFNode outerCFNode, boolean finishedWithOuter, InnerCFNode innerCFNode,
                int innerCFBlockStatement) {
            this(outerCFNode, finishedWithOuter, innerCFNode, innerCFBlockStatement, 0);
        }

        public void incrementBranchChoice() {
            nextBranchChoice++;
        }
    }

    final IteratorLocation originalLocation;
    /*
     * history is a Stack
     * activePath[i].dfsTraverse().contains(activePath[i+1])
     */
    List<CFNode> activePath = new ArrayList<>();
    /*
     * branchesNotTaken indexes into activePath with the previous location.
     * also a Stack -- branchesNotTaken[i][0] ≤ branchesNotTaken[i+1][0]
     *
     * Assume upon returning that we have taken dfsTraverse[0] and the path
     * not taken is dfsTraverse[1].  (Note this assumes dfsTraverse both
     * returns elems in the same order each time, and has ≤ 2 elems.)
     *
     * i is the first node in activePath to be deleted when taking the alternative branch.
     * when this iterator was at location branchesNotTaken[i][1].
     */
    Stack<Pair<Integer, IteratorLocation>> branchesNotTaken = new Stack<>();

    IteratorLocation location;
    IteratorAction currentAction;
    IteratorInstruction currentInstruction;
    Optional<CFNode> instructionResult;

    Set<CFNode> visited = new HashSet<>();

    public CFNodeIterator(OuterCFNode methodCFG) {
        this.location = new IteratorLocation(methodCFG, false, null, -1, 0);
        this.originalLocation = new IteratorLocation(this.location);
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
        this.originalLocation = new IteratorLocation(this.location);
        this.activePath.add(peek().get());
    }

    public boolean hasNext() {
        Optional<CFNode> next = peek();
        return next.isPresent() && !visited.contains(next.get());
    }

    /**
     * if next location is a CFNode, return that.
     * At a branch point, take the appropriate branch depending on
     * current location.nextBranchPoint.
     *
     * return notPresent if at a CFG end.
     */
    private Optional<CFNode> peek() {
        currentAction = IteratorAction.PEEK;
        instructionDispatchBasedOnLocation();
        return instructionResult;
    }

    /**
     * if next location is a CFNode, move to it.
     * At a branch point, take the appropriate branch depending on
     * current location.nextBranchPoint.
     *
     * Give up (stay same location) if at a CFG end.
     */
    private void advanceLocation() {
        currentAction = IteratorAction.ADVANCE;
        instructionDispatchBasedOnLocation();
    }

    /**
     * @return instruction result --
     */
    private void instructionDispatchBasedOnLocation() {
        // first node
        if (!location.finishedMiniCFG && location.innerCFNode == null) {
            currentInstruction = IteratorInstruction.FIRST_AVAILABLE_CFNODE;
            location.outerCFNode.accept(this);
            return;
        }

        // else
        if (location.finishedMiniCFG) {
            currentInstruction = IteratorInstruction.NEXT_OUTERCFNODE;
            location.outerCFNode.accept(this);
        } else {
            currentInstruction = IteratorInstruction.NEXT_MINICFG;
            location.innerCFNode.accept(this);
        }
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
        if (activePath.isEmpty()) {
            // Theoretically possible if function is just a "return;"
            return Set.of();
        }
        return new HashSet<>(activePath.subList(1, activePath.size()));
    }

    /**
     * Backtrack to the last branch point, if it exists.
     * If it doesn't exist, kill this iterator.
     */
    public void backtrackToLastBranchPoint() {
        if (branchesNotTaken.isEmpty()) {
            this.location = new IteratorLocation(this.originalLocation);
            activePath.clear();
            return;
        }

        Pair<Integer, IteratorLocation> lastBranchPoint = branchesNotTaken.pop();
        this.location = lastBranchPoint.getValue();
        activePath.subList(lastBranchPoint.getKey(), activePath.size()).clear();
    }

    /**
     * Push a branch not taken, if it exists.
     * Assume we are at a location that has 2 branch choices.
     */
    private void pushBranchNotTaken() {
        if (location.nextBranchChoice == 0) {
            IteratorLocation returnLocation = new IteratorLocation(this.location);
            returnLocation.incrementBranchChoice();
            branchesNotTaken.push(new Pair<>(activePath.size(), returnLocation));
        } // else == 1 so no more branches needed
    }

    /**
     * Report on why hasNext() is false.
     *
     * END: we have reached the end of a CFG.
     * VISITED: we have reached a CFNode that was already visited.
     * HAS_NEXT: we have not yet reached a dead end.
     *
     * @return dead end type
     */
    public DeadEndType deadEndType() {
        Optional<CFNode> next = peek();
        if (!next.isPresent()) {
            return DeadEndType.END;
        }
        if (!visited.contains(next.get())) {
            return DeadEndType.VISITED;
        }
        return DeadEndType.HAS_NEXT;
    }

    /**
     * Get what would have been the next node if we have reached
     * a dead end due to next() already being visited.
     *
     * Pre-condition: deadEndType() == DeadEndType.VISITED.
     *
     * @return next node
     */
    public CFNode deadEndNode() {
        return peek().get();
    }

    /**
     * Get whether this Iterator can continue.  It can continue if it hasNext(), or
     * if we can reach an untaken branch by doing backtrackToLastBranchPoint.
     *
     * @return whether the iterator can continue.
     */
    public boolean alive() {
        return !activePath.isEmpty();
    }

    @Override
    public void visit(InnerCFBlock innerCFBlock) {
        switch (currentInstruction) {
        case FIRST_AVAILABLE_CFNODE:
            if (innerCFBlock.getCfStatements().isEmpty()) {
                throw new RuntimeException("Hopefully no empty InnerCFBlocks");
            }
            if (currentAction == IteratorAction.PEEK) {
                instructionResult = Optional.of(innerCFBlock.getCfStatements().get(0));
            } else if (currentAction == IteratorAction.ADVANCE) {
                this.location = new IteratorLocation(location.outerCFNode, false, innerCFBlock, 0);
            } else {
                throw new RuntimeException("Unrecognized IteratorAction: " + currentAction);
            }
            break;
        case NEXT_MINICFG:
            currentInstruction = IteratorInstruction.FIRST_AVAILABLE_CFNODE;
            assert location.nextBranchChoice == 0; // cfBlocks only have one branch
            innerCFBlock.dfsTraverse().get(location.nextBranchChoice).accept(this);
            break;
        case END_MINICFG:
            throw new RuntimeException("Unexpected END_MINICFG");
        case NEXT_OUTERCFNODE:
            throw new RuntimeException("NEXT_OUTERCFNODE in an InnerCFNode");
        default:
            throw new RuntimeException("Unrecognized instruction: " + currentInstruction);
        }
    }

    @Override
    public void visit(InnerCFConditional innerCFConditional) {
        switch (currentInstruction) {
        case FIRST_AVAILABLE_CFNODE:
            if (currentAction == IteratorAction.PEEK) {
                instructionResult = Optional.of(innerCFConditional);
            } else if (currentAction == IteratorAction.ADVANCE) {
                this.location = new IteratorLocation(
                        location.outerCFNode,
                        false,
                        innerCFConditional,
                        -1);
            } else {
                throw new RuntimeException("Unexpected IteratorAction: " + currentAction);
            }
            break;
        case NEXT_MINICFG:
            currentInstruction = IteratorInstruction.FIRST_AVAILABLE_CFNODE;
            assert location.nextBranchChoice <= 1; // cfConditionals have 2 branches
            if (currentAction == IteratorAction.ADVANCE) {
                pushBranchNotTaken();
            }
            innerCFConditional.dfsTraverse().get(location.nextBranchChoice).accept(this);
            break;
        case END_MINICFG:
            throw new RuntimeException("Unexpected END_MINICFG");
        case NEXT_OUTERCFNODE:
            throw new RuntimeException("NEXT_OUTERCFNODE in an InnerCFNode");
        default:
            throw new RuntimeException("Unrecognized instruction: " + currentInstruction);
        }
    }

    @Override
    public void visit(InnerCFNop innerCFNop) {
        throw new RuntimeException("Expected non-endOfCFG Nops to be peephole removed from MiniCFGs: " + location.outerCFNode);
    }

    public void visit(InnerCFEndOfMiniCFG innerCFEndOfMiniCFG) {
        switch (currentInstruction) {
        case FIRST_AVAILABLE_CFNODE:
            currentInstruction = IteratorInstruction.END_MINICFG;
            innerCFEndOfMiniCFG.getEnclosingNode().accept(this);
            break;
        case NEXT_MINICFG:
            throw new RuntimeException("Stable Location should never be on a CFEndOfMiniCFG");
        case END_MINICFG:
            throw new RuntimeException("Unexpected END_MINICFG");
        case NEXT_OUTERCFNODE:
            throw new RuntimeException("NEXT_OUTERCFNODE in an InnerCFNode");
        default:
            throw new RuntimeException("Unrecognized instruction: " + currentInstruction);
        }
    }

    @Override
    public void visit(CFBlock cfBlock) {
        switch (currentInstruction) {
        case FIRST_AVAILABLE_CFNODE:
            cfBlock.getMiniCFGStart().accept(this);
            break;
        case NEXT_MINICFG:
            throw new RuntimeException("NEXT_MINICFG on an OuterCFNode");
        case END_MINICFG: // cfBlock is not a CFNode, so traverse outer CFG
        case NEXT_OUTERCFNODE:
            currentInstruction = IteratorInstruction.FIRST_AVAILABLE_CFNODE;
            assert location.nextBranchChoice == 0; // CFBlocks have only one successor
            cfBlock.dfsTraverse().get(location.nextBranchChoice).accept(this);
        default:
            throw new RuntimeException("Unrecognized instruction: " + currentInstruction);
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        switch (currentInstruction) {
        case FIRST_AVAILABLE_CFNODE:
            cfConditional.getMiniCFGStart().accept(this);
            break;
        case NEXT_MINICFG:
            throw new RuntimeException("NEXT_MINICFG on an OuterCFNode");
        case END_MINICFG:
            if (currentAction == IteratorAction.PEEK) {
                instructionResult = Optional.of(cfConditional);
            } else if (currentAction == IteratorAction.ADVANCE) {
                this.location = new IteratorLocation(cfConditional, true, null, -1);
            } else {
                throw new RuntimeException("Unexpected IteratorAction: " + currentAction);
            }
            break;
        case NEXT_OUTERCFNODE:
            currentInstruction = IteratorInstruction.FIRST_AVAILABLE_CFNODE;
            assert location.nextBranchChoice <= 1; // CFConditionals have two successors
            if (currentAction == IteratorAction.ADVANCE) {
                pushBranchNotTaken();
            }
            cfConditional.dfsTraverse().get(location.nextBranchChoice).accept(this);
        default:
            throw new RuntimeException("Unrecognized instruction: " + currentInstruction);
        }
    }

    @Override
    public void visit(CFNop cfNop) {
        throw new RuntimeException("Expected Outer CFNops to be peephole removed from CFGs: " + location.outerCFNode);
    }

    @Override
    public void visit(CFReturn cfReturn) {
        switch (currentInstruction) {
        case FIRST_AVAILABLE_CFNODE:
            if (cfReturn.isVoid()) {
                if (currentAction == IteratorAction.PEEK) {
                    instructionResult = Optional.empty();
                }
            } else {
                cfReturn.getMiniCFGStart().accept(this);
            }
            break;
        case NEXT_MINICFG:
            throw new RuntimeException("NEXT_MINICFG on an OuterCFNode");
        case END_MINICFG:
            if (cfReturn.isVoid()) {
                throw new RuntimeException("END_MINICFG on a void Return");
            } else {
                if (currentAction == IteratorAction.PEEK) {
                    instructionResult = Optional.of(cfReturn);
                } else if (currentAction == IteratorAction.ADVANCE) {
                    this.location = new IteratorLocation(cfReturn, true, null, -1);
                } else {
                    throw new RuntimeException("Unexpected IteratorAction: " + currentAction);
                }
            }
            break;
        case NEXT_OUTERCFNODE:
            if (currentAction == IteratorAction.PEEK) {
                instructionResult = Optional.empty();
            }
            break;
        default:
            throw new RuntimeException("Unrecognized instruction: " + currentInstruction);
        }
    }
}
