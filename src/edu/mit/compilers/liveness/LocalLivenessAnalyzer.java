package edu.mit.compilers.liveness;

import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.cfg.CFNode;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;

import java.util.HashMap;
import java.util.Set;

public class LocalLivenessAnalyzer {

    private final Set<AssemblyVariable> globalIn;
    private final Set<AssemblyVariable> globalOut;
    private final HashMap<CFNode, Set<AssemblyVariable>> in = new HashMap<>();
    private final HashMap<CFNode, Set<AssemblyVariable>> out = new HashMap<>();


    LocalLivenessAnalyzer(Set<AssemblyVariable> in, Set<AssemblyVariable> out) {
        globalIn = in;
        globalOut = out;
    }
    private void runLocalFixedPointAlgorithm(InnerCFNode miniCFG) {
        // fixed point algo for miniCFG
        // use topo sort to get all statements?

    }

    public HashMap<CFNode, Set<AssemblyVariable>> getIn() {
        return in;
    }

    public HashMap<CFNode, Set<AssemblyVariable>> getOut() {
        return out;
    }
}
