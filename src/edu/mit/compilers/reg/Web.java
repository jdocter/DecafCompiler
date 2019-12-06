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
    Set<CFNode> beginningDefs = new HashSet<>();
    Set<CFNode> endingUses = new HashSet<>();
    final AssemblyVariable targetVariable;

    public Web(LivenessAnalyzer liveness, CFNode def, AssemblyVariable variable) {
        targetVariable = variable;
        this.liveness = liveness;
        extendWed(def);
        // endingUses unchanged because a = a + 1 <=> use a; def a; -- not def a; use a;
    }

    void addDef(CFNode def) {
        assert liveness.getOut(def).contains(targetVariable);
        spanningStatements.add(def);
        beginningDefs.add(def);
    }

    void extendWed(CFNode cfNode) {
        if (liveness.getIn(cfNode).contains(targetVariable) && liveness.getOut(cfNode).contains(targetVariable)) {
            spanningStatements.add(cfNode);
        } else if (liveness.getIn(cfNode).contains(targetVariable) && !liveness.getOut(cfNode).contains(targetVariable)) {
            spanningStatements.add(cfNode);
            endingUses.add(cfNode);
        } else if (!liveness.getIn(cfNode).contains(targetVariable) && liveness.getOut(cfNode).contains(targetVariable)) {
            spanningStatements.add(cfNode);
            beginningDefs.add(cfNode);
        } else {
            throw new RuntimeException("Node " + cfNode+ "should not be part of this web");
        }
    }

    /**
     * Extend web by these spanning nodes, merging webs if necessary
     * Guarantee that the spanning nodes end with a USE (not a DEF or a dead)
     */
    void extendWeb(Set<CFNode> nodes) {
        for (CFNode cfNode: nodes) {
            extendWed(cfNode);
        }
    }

    void assignRegister(Reg reg) {
        for (CFNode cfNode: spanningStatements) {
            cfNode.assignRegister(targetVariable, reg);
        }
    }

}
