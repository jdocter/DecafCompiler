package edu.mit.compilers.liveness;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.visitor.CFVisitor;

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

        doGlobalLivenessAnalysis();

        doLocalLivenessAnalysis();
    }

    private void doGlobalLivenessAnalysis() {
        globalAnalysisPrep(methodCFGStart);

        // initialize changed to all nodes except end nodes
        Set<OuterCFNode> changed = new HashSet<>(visited);
        changed.remove(methodCFGEnds);

        // calculate in for all end nodes
        for (OuterCFNode end: methodCFGEnds) {
            globalIn.put(end, new LocalLivenessAnalyzer(end.getMiniCFGStart(), end.getOuterUsed()).getGlobalIn());
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

            // in = used U (out - def) :::: first handle outer statments (CFConditional, CFReturn)
            Set<AssemblyVariable> miniCFGOut = new HashSet<>(globalOut.get(currentNode));
            miniCFGOut.removeAll(currentNode.getOuterDefined());
            miniCFGOut.addAll(currentNode.getOuterUsed());

            // in = used U (out - def) :::: use local liveness analyzer to calculate in for the whole current CFNode
            Set<AssemblyVariable> newIn = new LocalLivenessAnalyzer(currentNode.getMiniCFGStart(), miniCFGOut).getGlobalIn();

            if (!newIn.equals(globalIn.get(currentNode))) {
                globalIn.put(currentNode, newIn);
                changed.addAll(currentNode.parents());
            }
        }
    }

    private void globalAnalysisPrep(OuterCFNode cfNode) {
        if (!visited.contains(cfNode)) {
            visited.add(cfNode);

            globalIn.put(cfNode, new HashSet<>());
            globalOut.put(cfNode, new HashSet<>());

            if (cfNode.dfsTraverse().isEmpty()) {
                methodCFGEnds.add(cfNode);
            }
            for (OuterCFNode neighbor : cfNode.dfsTraverse()) {
                globalAnalysisPrep(neighbor);
            }
        }
    }

    private void doLocalLivenessAnalysis() {
        for (OuterCFNode cfNode: visited) {
            // calculate out for the miniCFG necessary for CFConditional & CFReturn
            Set<AssemblyVariable> miniCFGOut = new HashSet<>(globalOut.get(cfNode));
            miniCFGOut.removeAll(cfNode.getOuterDefined());
            miniCFGOut.addAll(cfNode.getOuterUsed());

            // do out and in for outer CFG, unnecessary for CFNop and CFBlock but not necessarily bad
            out.put(cfNode, globalOut.get(cfNode));
            in.put(cfNode, new HashSet<>(miniCFGOut));

            // do out and in for outer CFG, unnecessary for CFNop not necessarily bad?
            LocalLivenessAnalyzer localLivenessAnalyzer = new LocalLivenessAnalyzer(cfNode.getMiniCFGStart(), miniCFGOut);
            in.putAll(localLivenessAnalyzer.getIn());
            out.putAll(localLivenessAnalyzer.getOut());
        }
    }
}
