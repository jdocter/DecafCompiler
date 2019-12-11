package edu.mit.compilers.cfg;

import java.util.HashSet;
import java.util.Set;

import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.visitor.CFVisitor;


public class MergeBasicBlocksAndRemoveNops implements CFVisitor {

    /**
     * To be called BEFORE TempifySubExpressions
     * Similar to InnerMergeBasicBlocksAndRemoveNops
     */

    Set<OuterCFNode> visited = new HashSet<>();

    CFBlock lastSeenCFBlock;
    private MethodDescriptor methodDescriptor;

    private OuterCFNode firstNodeOfCFG;

    public MergeBasicBlocksAndRemoveNops(MethodDescriptor methodDescriptor) {
        this.methodDescriptor = methodDescriptor;
    }

    private void visitNeighbors(OuterCFNode node) {
        if (!visited.contains(node)) {
             visited.add(node);
            for (OuterCFNode neighbor : node.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }

    /**
     * @param toRemove
     */
    private void peepholeRemove(OuterCFNode toRemove) {
        Set<OuterCFNode> parents = toRemove.parents();

        // System.out.println("removing " + toRemove.getUID());
        OuterCFNode next = toRemove.getNext();
        for (OuterCFNode parent : parents) {
            parent.replacePointers(toRemove, next);
        }
        // Don't care about parents for toRemove because it's removed from the graph
        next.removeParent(toRemove);
        if (getFirstNodeOfCFG() == toRemove) setFirstNodeOfCFG(next);
    }

    @Override
    public void visit(CFBlock cfBlock) {
        if (getFirstNodeOfCFG() == null) setFirstNodeOfCFG(cfBlock);
        if (!visited.contains(cfBlock)) {
            visited.add(cfBlock);

            Set<OuterCFNode> cfBlockParents = cfBlock.parents();
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
        if (getFirstNodeOfCFG() == null) setFirstNodeOfCFG(cfConditional);
        visitNeighbors(cfConditional);
    }

    @Override
    public void visit(CFNop cfNop) {
        if (getFirstNodeOfCFG() == null) setFirstNodeOfCFG(cfNop);
        if (visited.contains(cfNop)) return;

        visited.add(cfNop);
        if (cfNop.isEnd()) {
            // ending Nops should be replaced with returns in the large CFG
            OuterCFNode replacement = new CFReturn(null, null, methodDescriptor);
            cfNop.setNext(replacement);
            peepholeRemove(cfNop);
            return;
        }

        peepholeRemove(cfNop);
        cfNop.getNext().accept(this);
    }

    @Override
    public void visit(CFReturn cfReturn) {
        if (getFirstNodeOfCFG() == null) setFirstNodeOfCFG(cfReturn);
        visitNeighbors(cfReturn);
    }

    public OuterCFNode getFirstNodeOfCFG() {
        return firstNodeOfCFG;
    }

    public void setFirstNodeOfCFG(OuterCFNode firstNodeOfCFG) {
        this.firstNodeOfCFG = firstNodeOfCFG;
    }

}
