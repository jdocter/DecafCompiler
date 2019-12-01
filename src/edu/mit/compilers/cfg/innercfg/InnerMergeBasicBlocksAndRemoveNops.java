package edu.mit.compilers.cfg.innercfg;

import java.util.HashSet;
import java.util.Set;

import edu.mit.compilers.cfg.OuterCFNode;
import edu.mit.compilers.visitor.MiniCFVisitor;


public class InnerMergeBasicBlocksAndRemoveNops implements MiniCFVisitor {
    /**
     * Similar to MergeBasicBlocksAndRemoveNops
     */

    Set<InnerCFNode> visited = new HashSet<>();

    InnerCFBlock lastSeenCFBlock;
    private OuterCFNode enclosingCFNode;

    private InnerCFNode firstNodeOfCFG;
    private InnerCFNode lastNodeOfCFG;

    public InnerMergeBasicBlocksAndRemoveNops(OuterCFNode enclosingCFNode) {
        this.enclosingCFNode = enclosingCFNode;
    }

    private void visitNeighbors(InnerCFNode node) {
        if (!visited.contains(node)) {
             visited.add(node);
            for (InnerCFNode neighbor : node.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }

    /**
     * @param toRemove
     */
    private void peepholeRemove(InnerCFNode toRemove) {
        Set<InnerCFNode> parents = toRemove.parents();

        // System.out.println("removing " + toRemove.getUID());
        InnerCFNode next = toRemove.getNext();
        for (InnerCFNode parent : parents) {
            parent.replacePointers(toRemove, next);
        }
        // Don't care about parents for toRemove because it's removed from the graph
        next.removeParent(toRemove);
        if (getFirstNodeOfCFG() == toRemove) setFirstNodeOfCFG(next);
    }

    @Override
    public void visit(InnerCFBlock cfBlock) {
        if (getFirstNodeOfCFG() == null) setFirstNodeOfCFG(cfBlock);
        if (!visited.contains(cfBlock)) {
            visited.add(cfBlock);

            Set<InnerCFNode> cfBlockParents = cfBlock.parents();
            // Any execution of parent implies execution of cfBlock
            if (lastSeenCFBlock != null && cfBlockParents.contains(lastSeenCFBlock)) {
                // any execution of cfBlock implies execution of parent
                if (cfBlockParents.size() == 1) {
                    // check variable tables are same
                    if (lastSeenCFBlock.isSameScope(cfBlock)) {
                        cfBlock.prependAllStatements(lastSeenCFBlock);
                        peepholeRemove(lastSeenCFBlock);
                    }
                }
            }

            lastSeenCFBlock = cfBlock;
            cfBlock.getNext().accept(this);
        }
    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        if (getFirstNodeOfCFG() == null) setFirstNodeOfCFG(cfConditional);
        visitNeighbors(cfConditional);
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        if (getFirstNodeOfCFG() == null) setFirstNodeOfCFG(cfNop);
        if (visited.contains(cfNop)) return;

        visited.add(cfNop);
        if (cfNop.isEnd()) {
            // ending Nops should be replaced with jmps in the mini CFG
            InnerCFNode replacement = new InnerCFEndOfMiniCFG(enclosingCFNode);
            lastNodeOfCFG = replacement;
            cfNop.setNext(replacement);
            peepholeRemove(cfNop);
            return;
        }

        peepholeRemove(cfNop);
        cfNop.getNext().accept(this);
    }

    public InnerCFNode getFirstNodeOfCFG() {
        return firstNodeOfCFG;
    }

    public InnerCFNode getLastNodeOfCFG() { return lastNodeOfCFG; }

    private void setFirstNodeOfCFG(InnerCFNode firstNodeOfCFG) {
        this.firstNodeOfCFG = firstNodeOfCFG;
    }

}
