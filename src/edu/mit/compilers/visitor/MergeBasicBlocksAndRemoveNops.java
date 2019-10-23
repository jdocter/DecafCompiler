package edu.mit.compilers.visitor;

import java.util.HashSet;
import java.util.Set;

import edu.mit.compilers.assembly.CFBlock;
import edu.mit.compilers.assembly.CFBreak;
import edu.mit.compilers.assembly.CFConditional;
import edu.mit.compilers.assembly.CFContinue;
import edu.mit.compilers.assembly.CFNode;
import edu.mit.compilers.assembly.CFNop;
import edu.mit.compilers.assembly.CFReturn;


public class MergeBasicBlocksAndRemoveNops implements CFVisitor {

    Set<CFNode> visited = new HashSet<>();

    CFBlock lastSeenCFBlock;

    private void visitNeighbors(CFNode node) {
        if (!visited.contains(node)) {
            visited.add(node);
            for (CFNode neighbor : node.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }

    private static void peepholeRemove(CFNode toRemove) {
        Set<CFNode> parents = toRemove.parents();
        // System.out.println("parents of " + cfNop.getUID() + ": " + parents);
        CFNode next = toRemove.getNext();
        for (CFNode parent : parents) {
            parent.replacePointers(toRemove, next);
        }
        // Don't care about parents for toRemove because it's removed from the graph
        next.removeParent(toRemove);
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
                    peepholeRemove(lastSeenCFBlock);
                    cfBlock.prependBlock(lastSeenCFBlock);
                }
            }

            lastSeenCFBlock = cfBlock;
            cfBlock.getNext().accept(this);
        }
    }

    @Override
    public void visit(CFBreak cfBreak) {
        visitNeighbors(cfBreak);
    }

    @Override
    public void visit(CFConditional cfConditional) {
        visitNeighbors(cfConditional);
    }

    @Override
    public void visit(CFContinue cfContinue) {
        visitNeighbors(cfContinue);
    }

    @Override
    public void visit(CFNop cfNop) {
        if (visited.contains(cfNop)) return;

        visited.add(cfNop);
        if (cfNop.isEnd()) return;

        peepholeRemove(cfNop);
        cfNop.getNext().accept(this);
    }

    @Override
    public void visit(CFReturn cfReturn) {
        visitNeighbors(cfReturn);
    }

}
