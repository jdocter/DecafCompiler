package edu.mit.compilers.cfg;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.assembly.TempCollector;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.cfg.innercfg.InnerCFNop;
import edu.mit.compilers.cfg.innercfg.InnerCollectSubExpressions;
import edu.mit.compilers.cfg.innercfg.TopologicalSort;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;

public class CFReturn extends UIDObject implements CFNode {


    @Override public String toString() {
        if (isVoid) {
            return "UID " + UID + " CFReturn";
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MethodCFGFactory.dfsPrint(miniCFGStart, new HashSet<Integer>(), new PrintStream(baos));
        return "\nMiniCFG: " + baos.toString() + "\n" +
        "UID " + UID + " CFReturn [miniCFG=" + miniCFGStart.getUID() + ", returnTemp=" + returnTemp + "]";
    }

    private CFNode next;
    boolean isEnd; // end of function
    private Expr returnExpr;
    private Temp returnTemp;
    private final Set<CFNode> parents = new HashSet<CFNode>();
    private final VariableTable variableTable;
    private boolean isVoid;
    private MethodDescriptor methodDescriptor;
    private InnerCFNode miniCFGStart;
    private InnerCFNode miniCFGEnd;

    public CFReturn(Expr returnExpr, VariableTable variableTable, MethodDescriptor methodDescriptor) {
        this.returnExpr = returnExpr;
        this.variableTable = variableTable;
        isVoid = returnExpr == null ? true : false;
        this.methodDescriptor = methodDescriptor;
    }

    public Expr getReturnExpr() {
        return returnExpr;
    }

    public Temp getReturnTemp() {
        return returnTemp;
    }

    public void replaceExpr(Temp temp) {
        if (isVoid) throw new UnsupportedOperationException("Tried to give temp to void return");
        this.returnExpr = null;
        this.returnTemp = temp;
    }

    public void setMiniCFG(InnerCFNode miniCFGStart, InnerCFNode miniCFGEnd) {
        if (isVoid) throw new UnsupportedOperationException("Tried to give miniCFG to void return");
        this.miniCFGStart = miniCFGStart;
        this.miniCFGEnd = miniCFGEnd;
    }

    public InnerCFNode getMiniCFGEnd() {
        if (isVoid) return new InnerCFNop();
        return miniCFGEnd;
    }

    public InnerCFNode getMiniCFGStart() {
        if (isVoid) return new InnerCFNop();
        return miniCFGStart;
    }

    public boolean isVoid() {
        return isVoid;
    }

    public boolean shouldReturnVoid() {
        return methodDescriptor.isVoid();
    }

    @Override
    public CFNode getNext() {
        return next;
    }

    @Override
    public Set<CFNode> parents() {
        return this.parents;
    }

    @Override
    public void setNext(CFNode next) {
        throw new UnsupportedOperationException("Tried setNext on a Return.  a return should not go anywhere");
    }

    @Override
    public void addParent(CFNode parent) {
        this.parents.add(parent);
    }

    @Override
    public List<CFNode> dfsTraverse() {
        return List.of();
    }

    @Override
    public void removeParent(CFNode parent) {
        this.parents.remove(parent);
    }

    @Override
    public void replacePointers(CFNode original, CFNode replacement) {
        if (this.next == original) {
            this.setNext(replacement);
        }
    }

    @Override
    public VariableTable getVariableTable() {
        return variableTable;
    }

    @Override
    public void accept(CFVisitor v) {
        v.visit(this);
    }

    @Override
    public List<Pair<List<Temp>, List<Temp>>> getTemps() {
        if (isVoid) return List.of();
        TempCollector collector = new TempCollector();
        List<Pair<List<Temp>, List<Temp>>> temps = new ArrayList<Pair<List<Temp>, List<Temp>>>();
        miniCFGStart.accept(collector);
        temps.addAll(collector.temps);
        temps.add(new Pair<List<Temp>, List<Temp>>(List.of(returnTemp), List.of()));
        return temps;
    }

    @Override
    public Set<Expr> getSubExpressions() {
        if (isVoid) return Set.of();

        InnerCollectSubExpressions collector = new InnerCollectSubExpressions();
        this.miniCFGStart.accept(collector);
        return collector.subExpressions;
    }

    private LinkedList<InnerCFNode> miniCFGTSCached;
    public LinkedList<InnerCFNode> getTS() {
        if (miniCFGTSCached != null) return miniCFGTSCached;
        if (isVoid) {
            return new LinkedList<InnerCFNode>();
        }
        LinkedList<InnerCFNode> ts = new TopologicalSort(miniCFGStart).getTopologicalSort();
        miniCFGTSCached = ts;
        return ts;
    }

    @Override
    public Set<Expr> generatedExprs(Set<Expr> allExprs) {
        if (isVoid) return Set.of();

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
        Set<Expr> killed = new HashSet<>();

        return killed;
    }

    @Override
    public String getAssemblyLabel() {
        return "_return_" + UID;
    }

    @Override
    public String getEndOfMiniCFGLabel() {
        return "_end_of_return_" + UID;
    }
}
