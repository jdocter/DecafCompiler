package edu.mit.compilers.liveness;

import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.cfg.CFNode;
import edu.mit.compilers.cfg.OuterCFNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LivenessAnalyzer {
    /**
     * To be used for dead code elimination or register allocation
     *
     */
    Set<OuterCFNode> visited = new HashSet<>(); // redundant with in/out/gen/kill, but decoupling code intent

    // for global analysis
    Map<OuterCFNode, Set<AssemblyVariable>> globalIn = new HashMap<>();
    Map<OuterCFNode, Set<AssemblyVariable>> globalOut = new HashMap<>();
    Map<OuterCFNode, Set<AssemblyVariable>> globalUse = new HashMap<>();
    Map<OuterCFNode, Set<AssemblyVariable>> globalDef = new HashMap<>();

    Set<OuterCFNode> changed;

    // for all analysis
    Map<CFNode, Set<AssemblyVariable>> in = new HashMap<>();
    Map<CFNode, Set<AssemblyVariable>> out = new HashMap<>();


    public LivenessAnalyzer(OuterCFNode methodCFG) {

        // global liveness analysis
        prepNeighbors(methodCFG);


        // fixed point algorithm
        changed = new HashSet<>(visited);
        changed.remove(methodCFG);
        runFixedPointAlgorithm();


        // local liveness analysis
        // use local liveness analyzer as a subproblem
    }

    private void runFixedPointAlgorithm() {

        while (!changed.isEmpty()) {
            // fixed point
            // start from exit, make sure to initialize in for last correctly
        }
    }

    private void prepNeighbors(OuterCFNode cfNode) {
        if (!visited.contains(cfNode)) {
            visited.add(cfNode);

            globalUse.put(cfNode, cfNode.getUsed());
            globalDef.put(cfNode, cfNode.getDefined());
            globalIn.put(cfNode, new HashSet<>());
            globalOut.put(cfNode, new HashSet<>());

            for (OuterCFNode neighbor : cfNode.dfsTraverse()) {
                prepNeighbors(neighbor);
            }
        }
    }
}
