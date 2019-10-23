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


public class PeepholeRemoveNops implements CFVisitor {

    Set<CFNode> visited = new HashSet<>();

    private void visitNeighbors(CFNode node) {
        if (!visited.contains(node)) {
            visited.add(node);
            for (CFNode neighbor : node.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }

    @Override
    public void visit(CFBlock cfBlock) {
        visitNeighbors(cfBlock);
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

        Set<CFNode> parents = cfNop.parents();
        // System.out.println("parents of " + cfNop.getUID() + ": " + parents);
        CFNode next = cfNop.getNext();
        for (CFNode parent : parents) {
            parent.replacePointers(cfNop, next);
        }
        // Don't care about parents for cfNop because it's removed from the graph
        next.removeParent(cfNop);
        next.accept(this);
    }

    @Override
    public void visit(CFReturn cfReturn) {
        visitNeighbors(cfReturn);
    }

}
