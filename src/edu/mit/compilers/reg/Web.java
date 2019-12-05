package edu.mit.compilers.reg;

import java.util.HashSet;
import java.util.Set;

import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.cfg.CFNode;

public class Web {

    Set<CFNode> spanningStatements = new HashSet<>();
    Set<CFNode> beginningDefs = new HashSet<>();
    Set<CFNode> endingUses = new HashSet<>();
    final AssemblyVariable targetVariable;

    public Web(CFNode def, AssemblyVariable variable) {
        spanningStatements.add(def);
        beginningDefs.add(def);
        // endingUses unchanged because a = a + 1 <=> use a; def a; -- not def a; use a;
        targetVariable = variable;
    }

    /**
     * Extend web by these spanning nodes, merging webs if necessary
     * Guarantee that the spanning nodes end with a USE (not a DEF or a dead)
     */
    void extendWeb(Set<CFNode> nodes) {
        spanningStatements.addAll(nodes);
        // TODO merge webs
    }
}
