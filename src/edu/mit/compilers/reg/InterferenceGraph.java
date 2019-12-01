package edu.mit.compilers.reg;

import java.util.Map;
import java.util.Set;

import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.cfg.CFNode;

public class InterferenceGraph {

    Set<Set<CFNode>> webs;
    Map<Set<CFNode>, AssemblyVariable> webToVariable;
    Map<Set<CFNode>, Set<Set<CFNode>>> adjList;

    public InterferenceGraph(CFNode methodCFG) {
        buildNodes(methodCFG);
        buildEdges(methodCFG);
    }

    private void buildNodes(CFNode methodCFG) {

    }

    private void buildEdges(CFNode methodCFG) {

    }

}
