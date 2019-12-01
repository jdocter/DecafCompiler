package edu.mit.compilers.liveness;

import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.cfg.CFNode;
import edu.mit.compilers.cfg.OuterCFNode;
import edu.mit.compilers.parser.Expr;

import java.util.*;

public class LivenessAnalyzer {
    /**
     * To be used for dead code elimination or register allocation
     */


    Set<OuterCFNode> visited = new HashSet<>(); // redundant with in/out/gen/kill, but decoupling code intent
    private final OuterCFNode methodCFGStart;
    Set<OuterCFNode> methodCFGEnds = new HashSet<>();

    // for global analysis
    Map<OuterCFNode, Set<AssemblyVariable>> globalIn = new HashMap<>();
    Map<OuterCFNode, Set<AssemblyVariable>> globalOut = new HashMap<>();


    // for all analysis
    Map<CFNode, Set<AssemblyVariable>> in = new HashMap<>();
    Map<CFNode, Set<AssemblyVariable>> out = new HashMap<>();


    public LivenessAnalyzer(OuterCFNode methodCFG) {
        this.methodCFGStart = methodCFG;

        // fixed point algorithm
        doGlobalLivenessAnalysis(methodCFG);

        // local liveness analysis
        // use local liveness analyzer as a subproblem
    }

    private void doGlobalLivenessAnalysis(OuterCFNode methodCFG) {
        prepNeighbors(methodCFG);

        // initialize changed to all nodes except end nodes
        Set<OuterCFNode> changed = new HashSet<>(visited);
        changed.remove(methodCFGEnds);

        // calculate in for all end nodes
        for (OuterCFNode end: methodCFGEnds) {
            globalIn.put(end, new LocalLivenessAnalyzer(Set.of()).getGlobalIn());
        }
        while (!changed.isEmpty()) {
            // fixed point
            OuterCFNode currentNode = changed.iterator().next();
            changed.remove(currentNode);

            // out = U in
            globalOut.get(currentNode).clear();
            for (OuterCFNode succ: currentNode.dfsTraverse()) {
                globalOut.get(currentNode).addAll(globalIn.get(succ));
            }


            // in = used U (out - def)
            Set<AssemblyVariable> newIn = new LocalLivenessAnalyzer(globalOut.get(currentNode)).getGlobalIn();

            if (!newIn.equals(globalIn.get(currentNode))) {
                globalIn.put(currentNode, newIn);
                changed.addAll(currentNode.parents());
            }
        }
    }

    private void prepNeighbors(OuterCFNode cfNode) {
        if (!visited.contains(cfNode)) {
            visited.add(cfNode);

            globalIn.put(cfNode, new HashSet<>());
            globalOut.put(cfNode, new HashSet<>());

            if (cfNode.dfsTraverse().isEmpty()) {
                methodCFGEnds.add(cfNode);
            }
            for (OuterCFNode neighbor : cfNode.dfsTraverse()) {
                prepNeighbors(neighbor);
            }
        }
    }
}
