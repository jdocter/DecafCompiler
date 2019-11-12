package edu.mit.compilers.cfg.innercfg;

import edu.mit.compilers.visitor.MiniCFVisitor;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class TopologicalSort implements MiniCFVisitor {

    public LinkedList<InnerCFNode> ts = new LinkedList<>();
    private Set<InnerCFNode> visited = new HashSet<>();
    private Set<InnerCFNode> visiting = new HashSet<>();

    public TopologicalSort(InnerCFNode start) {
        start.accept(this);
    }

    public LinkedList<InnerCFNode> getTopologicalSort() {
        return ts;
    }

    private void doTS(InnerCFNode cfNode) {
        // https://en.wikipedia.org/wiki/Topological_sorting#Depth-first_search
        if (visited.contains(cfNode)) return;
        if (visiting.contains(cfNode)) throw new RuntimeException("MiniCFG has a cycle containing " + cfNode);

        visiting.add(cfNode);
        for (InnerCFNode neighbor : cfNode.dfsTraverse()) {
            neighbor.accept(this);
        }
        visiting.remove(cfNode);
        visited.add(cfNode);
        ts.addFirst(cfNode);
    }

    @Override
    public void visit(InnerCFBlock cfBlock) {
        doTS(cfBlock);
    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        doTS(cfConditional);
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        doTS(cfNop);
    }
}
