package edu.mit.compilers.cfg.innercfg;

import java.util.HashSet;
import java.util.Set;

import edu.mit.compilers.cfg.CFNode;
import edu.mit.compilers.visitor.MiniCFVisitor;


public class InnerMergeBasicBlocksAndRemoveNops implements MiniCFVisitor {
    /**
     * Similar to MergeBasicBlocksAndRemoveNops
     */

    Set<InnerCFNode> visited = new HashSet<>();

    InnerCFBlock lastSeenCFBlock;
    private CFNode enclosingCFNode;

    public InnerMergeBasicBlocksAndRemoveNops(CFNode enclosingCFNode) {
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
     * True if successful, false if failure
     *
     * Failure means the node to remove was the head of the CFG.
     *
     * @param toRemove
     * @return true if success
     */
    private static boolean peepholeRemove(InnerCFNode toRemove) {
        Set<InnerCFNode> parents = toRemove.parents();
        if (parents.isEmpty()) return false;

        // System.out.println("removing " + toRemove.getUID());
        InnerCFNode next = toRemove.getNext();
        for (InnerCFNode parent : parents) {
            parent.replacePointers(toRemove, next);
        }
        // Don't care about parents for toRemove because it's removed from the graph
        next.removeParent(toRemove);
        return true;
    }

    @Override
    public void visit(InnerCFBlock cfBlock) {
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
        visitNeighbors(cfConditional);
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        if (visited.contains(cfNop)) return;

        visited.add(cfNop);
        if (cfNop.isEnd()) {
            // ending Nops should be replaced with jmps in the mini CFG
            cfNop.setNext(new InnerCFEndOfMiniCFG(enclosingCFNode));
            peepholeRemove(cfNop);
            return;
        }

        peepholeRemove(cfNop);
        cfNop.getNext().accept(this);
    }

}
