package edu.mit.compilers.cfg.innercfg;

import edu.mit.compilers.visitor.MiniCFVisitor;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class TopologicalSort {

    public LinkedList<InnerCFNode> ts = new LinkedList<>();
    private Set<InnerCFNode> visited = new HashSet<>();
    private Set<InnerCFNode> visiting = new HashSet<>();

    public TopologicalSort(InnerCFNode start) {
        doTS(start);
        // System.err.println("TS: " + ts.stream().map(InnerCFNode::getUID).collect(Collectors.toList()));
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
            doTS(neighbor);
        }
        visiting.remove(cfNode);
        visited.add(cfNode);
        ts.addFirst(cfNode);
    }
}
