package edu.mit.compilers.cfg;

import edu.mit.compilers.assembly.TempCollector;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.cfg.innercfg.InnerCollectSubExpressions;
import edu.mit.compilers.cfg.innercfg.TopologicalSort;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public class CFConditional extends UIDObject implements OuterCFNode {
    @Override public String toString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MethodCFGFactory.dfsPrint(miniCFGStart, new HashSet<Integer>(), new PrintStream(baos));
        return "\nMiniCFG: " + baos.toString() + "\n" +
        "UID " + UID + " CFConditional [ miniCFG=" + miniCFGStart.getUID() + ", boolTemp=" + boolTemp + ", ifTrue=" + ifTrue.getUID() + ", ifFalse=" + ifFalse.getUID() + "], Scope = " + variableTable.getUID();
    }

    private InnerCFNode miniCFGStart;
    private InnerCFNode miniCFGEnd;
    private Expr boolExpr;
    private OuterCFNode ifTrue;
    private OuterCFNode ifFalse;
    private Set<OuterCFNode> parents = new HashSet<OuterCFNode>();
    private final VariableTable variableTable;

    private Temp boolTemp; // Call TempifySubExpressions to populate

    public CFConditional(Expr expr, OuterCFNode ifTrue, OuterCFNode ifFalse, VariableTable variableTable) {
        this.boolExpr = expr;
        this.ifTrue= ifTrue;
        ifTrue.addParent(this);

        this.ifFalse = ifFalse;
        ifFalse.addParent(this);
        this.variableTable = variableTable;
    }

    public Temp getBoolTemp() {
        return boolTemp;
    }

    public Expr getBoolExpr() {
        return boolExpr;
    }

    public void setMiniCFG(InnerCFNode miniCFGStart, InnerCFNode miniCFGEnd) {

        this.miniCFGStart = miniCFGStart;
        this.miniCFGEnd = miniCFGEnd;
    }

    @Override
    public InnerCFNode getMiniCFGEnd() {
        return miniCFGEnd;
    }

    @Override
    public InnerCFNode getMiniCFGStart() {
        return miniCFGStart;
    }

    public void replaceExpr(Temp temp) {
        this.boolTemp = temp;
        this.boolExpr = null;
    }

    @Override
    public Set<OuterCFNode> parents() {
        return this.parents ;
    }

    @Override
    public void setNext(OuterCFNode next) {
        throw new UnsupportedOperationException("CFConditional doesn't support setNext");
    }

    @Override
    public void addParent(OuterCFNode parent) {
        this.parents.add(parent);
    }

    @Override
    public OuterCFNode getNext() {
        throw new UnsupportedOperationException("Don't know how to find NEXT for a CFConditional");
    }

    @Override
    public List<OuterCFNode> dfsTraverse() {
        return List.of(ifTrue, ifFalse);
    }

    @Override
    public void accept(CFVisitor v) {
        v.visit(this);
    }

    @Override
    public void removeParent(OuterCFNode parent) {
        this.parents.remove(parent);
    }

    @Override
    public VariableTable getVariableTable() {
        return variableTable;
    }

    @Override
    public void replacePointers(OuterCFNode original, OuterCFNode replacement) {
        if (this.ifFalse == original) {
            this.ifFalse = replacement;
            this.ifFalse.addParent(this);
        }
        if (this.ifTrue == original) {
            this.ifTrue = replacement;
            this.ifTrue.addParent(this);
        }
    }

    public OuterCFNode getIfTrue() {
        return ifTrue;
    }

    public OuterCFNode getIfFalse() {
        return ifFalse;
    }

    @Override
    public List<Pair<List<Temp>, List<Temp>>> getTemps() {
        TempCollector collector = new TempCollector();
        miniCFGStart.accept(collector);
        return collector.temps;
    }


    @Override
    public Set<Expr> getSubExpressions() {
        InnerCollectSubExpressions collector = new InnerCollectSubExpressions();
        this.miniCFGStart.accept(collector);
        return collector.subExpressions;
    }

    private LinkedList<InnerCFNode> miniCFGTSCached;
    public LinkedList<InnerCFNode> getTS() {
        if (miniCFGTSCached != null) return miniCFGTSCached;
        LinkedList<InnerCFNode> ts = new TopologicalSort(miniCFGStart).getTopologicalSort();
        miniCFGTSCached = ts;
        return ts;
    }

    @Override
    public Set<Expr> generatedExprs(Set<Expr> allExprs) {
        LinkedList<InnerCFNode> ts = getTS();
        Map<InnerCFNode, Set<Expr>> gens = new HashMap<>();
        for (InnerCFNode node : ts) {
            Set<InnerCFNode> innerParents = node.parents();
            if (innerParents.isEmpty()) {
                gens.put(node, node.generatedExprs(allExprs));
                continue;
            }

            // GEN = Intersect(parents) - KILL + thisGen
            Set<Expr> thisGen = new HashSet<>(gens.get(innerParents.iterator().next())); // initialize with copy of one parent
            for (InnerCFNode parent : innerParents) {
                thisGen.retainAll(gens.get(parent));
            }
            thisGen.removeAll(node.killedExprs(this.getSubExpressions()));
            thisGen.addAll(node.generatedExprs(allExprs));
            gens.put(node, thisGen);
        }

        return gens.get(ts.getLast());
    }

    @Override
    public Set<Expr> killedExprs(Set<Expr> allExprs) {
        LinkedList<InnerCFNode> ts = getTS();
        return ts.stream()
                .flatMap(node -> node.killedExprs(allExprs).stream())
                .collect(Collectors.toSet());
    }

    @Override
    public String getAssemblyLabel() {
        return "_conditional_" + UID;
    }

    @Override
    public String getEndOfMiniCFGLabel() {
        return "_end_of_conditional_" + UID;
    }
}
