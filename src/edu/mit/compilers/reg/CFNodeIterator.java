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
import edu.mit.compilers.cfg.innercfg.InnerCFBlock;
import edu.mit.compilers.cfg.innercfg.InnerCFConditional;
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

    private class IteratorLocation {
        OuterCFNode outerCFNode;
        boolean finishedWithOuter;
        InnerCFNode innerCFNode;
        boolean finishedWithInner;
        int innerCFBlockStatement;

        public IteratorLocation(OuterCFNode outerCFNode, boolean finishedWithOuter, InnerCFNode innerCFNode,
                boolean finishedWithInner, int innerCFBlockStatement) {
            this.outerCFNode = outerCFNode;
            this.finishedWithOuter = finishedWithOuter;
            this.innerCFNode = innerCFNode;
            this.finishedWithInner = finishedWithInner;
            this.innerCFBlockStatement = innerCFBlockStatement;
        }

        public IteratorLocation(IteratorLocation location) {
            this.outerCFNode = location.outerCFNode;
            this.finishedWithOuter = location.finishedWithOuter;
            this.innerCFNode = location.innerCFNode;
            this.finishedWithInner = location.finishedWithInner;
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
     */
    List<Pair<Integer, IteratorLocation>> branchesNotTaken = new ArrayList<>();

    IteratorLocation location;
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
        return next.isPresent() && visited.contains(next.get());
    }

    /**
     * if next location is a CFNode, return that.
     * return notPresent if at a CFG end.
     */
    private Optional<CFNode> peek() {
        if (location.finishedWithOuter) {
            // TODO
        } // TODO
        return Optional.empty();
    }

    /**
     *
     * @return false if location not advanced due to end of CFG.
     */
    private boolean advanceLocation() {
        // TODO
        return false;
    }

    public CFNode next() {
        CFNode toReturn = peek().get();
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
    public void visit(InnerCFBlock cfBlock) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(InnerCFNop cfNop) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(CFBlock cfBlock) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(CFConditional cfConditional) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(CFNop cfNop) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(CFReturn cfReturn) {
        // TODO Auto-generated method stub

    }
}
