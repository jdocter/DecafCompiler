package edu.mit.compilers.cfg;

import java.util.HashSet;
import java.util.Set;

import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.visitor.CFVisitor;


public class MergeBasicBlocksAndRemoveNops implements CFVisitor {
    /**
     * Similar to InnerMergeBasicBlocksAndRemoveNops
     */

    Set<CFNode> visited = new HashSet<>();

    CFBlock lastSeenCFBlock;
    private MethodDescriptor methodDescriptor;

    public MergeBasicBlocksAndRemoveNops(MethodDescriptor methodDescriptor) {
        this.methodDescriptor = methodDescriptor;
    }

    private void visitNeighbors(CFNode node) {
        if (!visited.contains(node)) {
             visited.add(node);
            for (CFNode neighbor : node.dfsTraverse()) {
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
    private static boolean peepholeRemove(CFNode toRemove) {
        Set<CFNode> parents = toRemove.parents();
        if (parents.isEmpty()) return false;

        // System.out.println("removing " + toRemove.getUID());
        CFNode next = toRemove.getNext();
        for (CFNode parent : parents) {
            parent.replacePointers(toRemove, next);
        }
        // Don't care about parents for toRemove because it's removed from the graph
        next.removeParent(toRemove);
        return true;
    }

    @Override
    public void visit(CFBlock cfBlock) {
        if (!visited.contains(cfBlock)) {
            visited.add(cfBlock);

            Set<CFNode> cfBlockParents = cfBlock.parents();
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
    public void visit(CFConditional cfConditional) {
        visitNeighbors(cfConditional);
    }

    @Override
    public void visit(CFNop cfNop) {
        if (visited.contains(cfNop)) return;

        visited.add(cfNop);
        if (cfNop.isEnd()) {
            // ending Nops should be replaced with returns in the large CFG
            cfNop.setNext(new CFReturn(null, null, methodDescriptor));
            peepholeRemove(cfNop);
            return;
        }

        peepholeRemove(cfNop);
        cfNop.getNext().accept(this);
    }

    @Override
    public void visit(CFReturn cfReturn) {
        visitNeighbors(cfReturn);
    }

}
