package edu.mit.compilers.reg;

import java.util.HashSet;
import java.util.Set;

import edu.mit.compilers.assembly.Reg;
import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.cfg.CFNode;
import edu.mit.compilers.liveness.LivenessAnalyzer;

public class Web {

    // granular analysis, may include CFStatements, OuterCFNodes, InnerCFNodes
    private final LivenessAnalyzer liveness;

    Set<CFNode> spanningStatements = new HashSet<>();
    Set<CFNode> defs = new HashSet<>();
    Set<CFNode> uses = new HashSet<>();
    final AssemblyVariable targetVariable;

    public Web(LivenessAnalyzer liveness, CFNode def, AssemblyVariable variable) {
        targetVariable = variable;
        this.liveness = liveness;
        addDef(def);
    }

    boolean contains(CFNode node) {
        return spanningStatements.contains(node);
    }

    void addDef(CFNode def) {
        assert liveness.getOut(def).contains(targetVariable);
        spanningStatements.add(def);
        defs.add(def);
        // uses unchanged because a = a + 1 <=> use a; def a; -- not def a; use a;
    }

    void singleExtendWeb(CFNode cfNode) {
        if (cfNode.getUsed().contains(targetVariable)) {
            uses.add(cfNode);
        }
        spanningStatements.add(cfNode);

//        if (liveness.getIn(cfNode).contains(targetVariable) && liveness.getOut(cfNode).contains(targetVariable)) {
//            spanningStatements.add(cfNode);
//        } else if (liveness.getIn(cfNode).contains(targetVariable) && !liveness.getOut(cfNode).contains(targetVariable)) {
//            spanningStatements.add(cfNode);
//            uses.add(cfNode);
//        } else if (!liveness.getIn(cfNode).contains(targetVariable) && liveness.getOut(cfNode).contains(targetVariable)) {
//            spanningStatements.add(cfNode);
//            defs.add(cfNode);
//        } else {
//            throw new RuntimeException("Node " + cfNode+ "should not be part of this web");
//        }
    }

    /**
     * Extend web by these spanning nodes
     * Pre-condition: the spanning nodes end with a USE (not a DEF or a dead)
     *
     * Does NOT automatically merge webs.
     */
    void extendWeb(Set<CFNode> nodes) {
        for (CFNode cfNode: nodes) {
            singleExtendWeb(cfNode);
        }
    }

    void assignRegister(Reg reg) {
        for (CFNode cfNode: spanningStatements) {
            cfNode.assignRegister(targetVariable, reg);
        }
    }

    public void merge(Web other) {
        if (!other.targetVariable.equals(this.targetVariable)) {
            throw new RuntimeException("Tried to merge webs with different variables");
        }
        spanningStatements.addAll(other.spanningStatements);
        // Nicely, defs of one Web cannot be contained
        // in spanningStatements of the other, due to the algorithm.
        defs.addAll(other.defs);
        uses.addAll(other.uses);
    }

    public void release() {
        spanningStatements = null;
        defs = null;
        uses = null;
    }

}
