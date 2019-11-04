package edu.mit.compilers.cfg;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.assembly.TempCollector;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.cfg.innercfg.InnerMethodAssemblyCollector;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Statement;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;


public class CFBlock extends UIDObject implements CFNode {

    private InnerCFNode miniCFG;
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

    public void setMiniCFG(InnerCFNode miniCFG) {
        this.statements.clear();
        this.miniCFG = miniCFG;
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
        assembly.addAll(new InnerMethodAssemblyCollector(miniCFG, importTable).getInstructions());
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
        MethodCFGFactory.dfsPrint(miniCFG, new HashSet<Integer>(), new PrintStream(baos));
        return "\nMiniCFG: " + baos.toString() + "\n" +
        "UID " + UID + " CFBlock [ miniCFG=" + miniCFG.getUID() + ", next=" + next.getUID() + "], Scope = " + variableTable.getUID();
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
        miniCFG.accept(collector);
        return collector.temps;
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
