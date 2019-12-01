package edu.mit.compilers.liveness;

import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.cfg.CFNode;
import edu.mit.compilers.cfg.OuterCFNode;
import edu.mit.compilers.cfg.innercfg.CFAssign;
import edu.mit.compilers.cfg.innercfg.CFStatement;
import edu.mit.compilers.cfg.innercfg.InnerCFBlock;
import edu.mit.compilers.cfg.innercfg.InnerCFConditional;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.cfg.innercfg.InnerCFNop;
import edu.mit.compilers.cfg.innercfg.TopologicalSort;
import edu.mit.compilers.visitor.MiniCFVisitor;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class LocalLivenessAnalyzer implements MiniCFVisitor {
    /**
     * visit() in this visitor should NOT traverse the graph --
     * traversal is done in a static method and visit() is a helper used to handle
     * variants
     */

    private Set<AssemblyVariable> globalIn;
    private final Set<AssemblyVariable> globalOut;
    private final HashMap<CFNode, Set<AssemblyVariable>> in = new HashMap<>();
    private final HashMap<CFNode, Set<AssemblyVariable>> out = new HashMap<>();


    LocalLivenessAnalyzer(InnerCFNode miniCFGStart, Set<AssemblyVariable> out) {
        globalOut = out;
        LinkedList<InnerCFNode> ts = new TopologicalSort(miniCFGStart).getTopologicalSort();
        // Avoid disrupting caching during .reverse() mutation
        ts = new LinkedList<>(ts);
        Collections.reverse(ts);

        computeLiveVariables(ts);
        // We could do ts.getLast() here to calculate globalIn, but better
        // to calculate as we go, because otherwise we would have to switch on type
        // (globalIn could be based on the InnerCFConditional or InnerCFNop or CFStatement)
    }

    private void computeLiveVariables(LinkedList<InnerCFNode> orderCfNodes) {
        // iterate backwards, filling in in/out for CFStatement, InnerCFConditional, and InnerCFNop
        if (orderCfNodes.size() <= 1) {
            globalIn = globalOut;
            return;
        }

        for (InnerCFNode cfNode : orderCfNodes) {
            cfNode.accept(this);
        }
    }

    public Set<AssemblyVariable> getGlobalIn() {
        return globalIn;
    }

    public HashMap<CFNode, Set<AssemblyVariable>> getIn() {
        return in;
    }

    public HashMap<CFNode, Set<AssemblyVariable>> getOut() {
        return out;
    }

    @Override
    public void visit(InnerCFBlock cfBlock) {
        Set<AssemblyVariable> successorsIn;

        List<InnerCFNode> successors = cfBlock.dfsTraverse();
        if (successors.isEmpty()) {
            // end of miniCFG
            successorsIn = globalOut;
        } else {
            // OUT = U(in of successors)
            successorsIn = new HashSet<>(in.get(successors.iterator().next()));
            for (InnerCFNode successor : successors) {
                successorsIn.addAll(in.get(successor));
            }
        }

        // iterate backwards
        ListIterator<CFStatement> listIterator = cfBlock.getCfStatements().listIterator(cfBlock.getCfStatements().size());

        while (listIterator.hasPrevious()) {
            CFStatement cfStatement = listIterator.previous();

            out.put(cfStatement, successorsIn);

            // IN = OUT U (USE - DEF)
            Set<AssemblyVariable> statementIn = new HashSet<>(cfStatement.getUsed());
            statementIn.removeAll(cfStatement.getDefined());
            statementIn.addAll(successorsIn);
            in.put(cfStatement, statementIn);

            // This copy may not be necessary, but add it for safety
            statementIn = new HashSet<>(statementIn);

            successorsIn = statementIn;
        }

        // reached start of block
        globalIn = successorsIn;
    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        genericComputeLiveVariables(cfConditional);
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        genericComputeLiveVariables(cfNop);
    }

    private void genericComputeLiveVariables(InnerCFNode cfNode) {
        List<InnerCFNode> successors = cfNode.dfsTraverse();
        if (successors.isEmpty()) {
            // end of miniCFG
            out.put(cfNode, globalOut);
        } else {
            // OUT = U(in of successors)
            Set<AssemblyVariable> cfNodeOut = new HashSet<>(in.get(successors.iterator().next()));
            for (InnerCFNode successor : successors) {
                cfNodeOut.addAll(in.get(successor));
            }
            out.put(cfNode, cfNodeOut);
        }
        // IN = OUT U (USE - DEF)
        Set<AssemblyVariable> cfNodeIn = new HashSet<AssemblyVariable>();
        cfNodeIn.addAll(cfNode.getUsed());
        cfNodeIn.removeAll(cfNode.getDefined());
        cfNodeIn.addAll(out.get(cfNode));
        in.put(cfNode, cfNodeIn);

        // copy may not be necessary, but better safe than sorry
        globalIn = new HashSet<>(in.get(cfNode));
    }
}
