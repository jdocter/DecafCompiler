package edu.mit.compilers.cfg;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.assembly.TempCollector;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.cfg.innercfg.InnerCollectSubExpressions;
import edu.mit.compilers.cfg.innercfg.InnerMethodAssemblyCollector;
import edu.mit.compilers.cfg.innercfg.TopologicalSort;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Statement;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;


public class CFBlock extends UIDObject implements CFNode {

    private InnerCFNode miniCFGStart;
    private InnerCFNode miniCFGEnd;
    // Should all be either CFAssign or CFMethodCall
    private final List<Statement> statements = new ArrayList<>();
    CFNode next;

    boolean isEnd; // end of function
    private Set<CFNode> parents = new HashSet<CFNode>();
    private final VariableTable variableTable;

    public CFBlock(Statement statement, VariableTable variableTable) {
        if (!(statement.statementType == Statement.LOC_ASSIGN || statement.statementType == Statement.METHOD_CALL)) {
            throw new RuntimeException("Expected Loc assign or Method call");
        }
        this.statements.add(statement);
        this.variableTable = variableTable;
    }

    public void setMiniCFG(InnerCFNode miniCFGStart, InnerCFNode miniCFGEnd) {
        this.statements.clear();
        this.miniCFGStart = miniCFGStart;
        this.miniCFGEnd = miniCFGEnd;
    }

    public InnerCFNode getMiniCFGEnd() {
        return miniCFGEnd;
    }

    @Override
    public void setNext(CFNode next) {
        isEnd = false;
        this.next = next;
        next.addParent(this);
    }

    @Override
    public CFNode getNext() {
        return next;
    }

    @Override
    public List<String> toAssembly(ImportTable importTable) {
        List<String> assembly = new ArrayList<>();

        assembly.add(getAssemblyLabel() + ":");
        assembly.addAll(new InnerMethodAssemblyCollector(miniCFGStart, importTable).getInstructions());
        assembly.add(getEndOfMiniCFGLabel() + ":");

        List<String> body = new ArrayList<>();
        if (!isEnd) {
            body.add("jmp " + next.getAssemblyLabel());
        } else {
            throw new RuntimeException("Didn't expect a CFBlock to end the CFG");
        }

        assembly.addAll(AssemblyFactory.indent(body));
        return assembly;
    }

    @Override
    public Set<CFNode> parents() {
        return this.parents;
    }

    @Override
    public void addParent(CFNode parent) {
        this.parents.add(parent);
    }

    @Override public String toString() {
        // System.err.println("Thinks " + UID + " isOuter");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MethodCFGFactory.dfsPrint(miniCFGStart, new HashSet<Integer>(), new PrintStream(baos));
        return "\nMiniCFG: " + baos.toString() + "\n" +
        "UID " + UID + " CFBlock [ miniCFG=" + miniCFGStart.getUID() + ", next=" + next.getUID() + "], Scope = " + variableTable.getUID();
    }

    @Override
    public List<CFNode> dfsTraverse() {
        if (isEnd) return List.of();
        return List.of(next);
    }

    @Override
    public void accept(CFVisitor v) {
        v.visit(this);
    }

    @Override
    public void removeParent(CFNode parent) {
        this.parents.remove(parent);
    }

    @Override
    public VariableTable getVariableTable() {
        return variableTable;
    }

    @Override
    public void replacePointers(CFNode original, CFNode replacement) {
        if (this.next == original) {
            this.setNext(replacement);
        }
    }

    protected List<Statement> getStatements() {
        return statements;
    }

    /**
     * List< Pair<TempsUpdated, TempsUsed> >
     * @return
     */
    @Override
    public List<Pair<Temp, List<Temp>>> getTemps() {
        TempCollector collector = new TempCollector();
        miniCFGStart.accept(collector);
        return collector.temps;
    }

    private Set<Expr> subExpressionsCached;
    @Override
    public Set<Expr> getSubExpressions() {
        if (subExpressionsCached != null) return subExpressionsCached;
        InnerCollectSubExpressions collector = new InnerCollectSubExpressions();
        this.miniCFGStart.accept(collector);
        subExpressionsCached = collector.subExpressions;
        return subExpressionsCached;
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
        LinkedList<InnerCFNode> ts = getTS();
        return ts.stream()
                .flatMap(node -> node.killedExprs(allExprs).stream())
                .collect(Collectors.toSet());
    }

    public void prependAllStatements(CFBlock block) {
        List<Statement> thisCopy = new ArrayList<>(this.statements);
        this.statements.clear();
        this.statements.addAll(block.statements);
        this.statements.addAll(thisCopy);
    }

    public boolean isSameScope(CFBlock other) {
        return !(variableTable == null) && !(other.variableTable == null) && variableTable == other.variableTable;
    }

    @Override
    public String getAssemblyLabel() {
        return "_block_" + UID;
    }

    @Override
    public String getEndOfMiniCFGLabel() {
        return "_end_of_block_" + UID;
    }
}
