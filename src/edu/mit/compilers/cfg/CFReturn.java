package edu.mit.compilers.cfg;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.assembly.TempCollector;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.cfg.innercfg.InnerCollectSubExpressions;
import edu.mit.compilers.cfg.innercfg.InnerMethodAssemblyCollector;
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
        return miniCFGEnd;
    }

    public InnerCFNode getMiniCFGStart() {
        return miniCFGStart;
    }

    @Override
    public List<String> toAssembly(ImportTable importTable) {
        final List<String> assembly = new ArrayList<>();

        List<String> body = new ArrayList<>();
        boolean shouldReturnVoid = methodDescriptor.isVoid();
        if (isVoid) {
            if (!shouldReturnVoid) {
                // generate runtime error
                body.add("");
                body.add("jmp " + AssemblyFactory.METHOD_EXIT_2 + " # runtime error: 2"); // return code 2

                assembly.add(getAssemblyLabel() + ":");
                assembly.addAll(AssemblyFactory.indent(body));
                return assembly;
            } else {
                // return
                body.add("# return void");
                body.add("movq $0, %rax");
            }
        } else {
            if (shouldReturnVoid) throw new RuntimeException("semantic checks failed");
            // calculate expr and return it
            body.add("# calculating return Expr");
            body.addAll(new InnerMethodAssemblyCollector(miniCFGStart, importTable).getInstructions());
            body.add(getEndOfMiniCFGLabel() + ":");
            body.add("");
            body.add("movq -" + returnTemp.getOffset() + "(%rbp), %rax");
        }
        body.add("");
        body.add("leave");
        body.add("ret");

        assembly.add(getAssemblyLabel() + ":");
        assembly.addAll(AssemblyFactory.indent(body));
        return assembly;
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
    public List<Pair<Temp, List<Temp>>> getTemps() {
        if (isVoid) return List.of();
        TempCollector collector = new TempCollector();
        List<Pair<Temp, List<Temp>>> temps = new ArrayList<Pair<Temp, List<Temp>>>();
        miniCFGStart.accept(collector);
        temps.addAll(collector.temps);
        temps.add(new Pair<Temp, List<Temp>>(returnTemp, List.of()));
        return temps;
    }

    @Override
    public Set<Expr> getSubExpressions() {
        InnerCollectSubExpressions collector = new InnerCollectSubExpressions();
        this.miniCFGStart.accept(collector);
        return collector.subExpressions;
    }

    private LinkedList<InnerCFNode> miniCFGTSCached;
    private LinkedList<InnerCFNode> getTS() {
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
            if (parents.isEmpty()) {
                gens.put(node, node.generatedExprs(allExprs));
                break;
            }

            // GEN = Intersect(parents) - KILL + thisGen
            Set<InnerCFNode> parents = node.parents();
            Set<Expr> thisGen = new HashSet<>(gens.get(parents.iterator().next())); // initialize with copy of one parent
            for (InnerCFNode parent : parents) {
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
