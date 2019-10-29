package edu.mit.compilers.assembly;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Statement;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;


public class CFBlock extends UIDObject implements CFNode {

    private CFNode miniCFG;
    // Should all be either CFAssign or CFMethodCall
    private final List<Statement> statements = new ArrayList<>();
    private final List<CFStatement> cfStatements = new ArrayList<>();
    private final boolean isOuter;
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
        isOuter = true;
    }

    public CFBlock(CFStatement statement, VariableTable variableTable) {
        // super();
        // System.err.println("Should be making a new inner CFBLock " + UID);
        this.cfStatements.add(statement);
        this.variableTable = variableTable;
        isOuter = false;
    }

    public void setMiniCFG(CFNode miniCFG) {
        if (!isOuter) throw new RuntimeException("outer");
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
        // Trying not to share code between isOuter and !isOuter
        if (isOuter) {
            List<String> assembly = new ArrayList<>();

            assembly.add(getAssemblyLabel() + ":");
            assembly.addAll(new MethodAssemblyCollector(miniCFG, importTable).getInstructions());
            assembly.add(getEndOfMiniCFGLabel() + ":");

            List<String> body = new ArrayList<>();
            if (!isEnd) {
                body.add("jmp " + next.getAssemblyLabel());
            } else {
                throw new RuntimeException("Didn't expect a CFBlock to end the CFG");
            }

            assembly.addAll(AssemblyFactory.indent(body));
            return assembly;
        } else {
            List<String> assembly = new ArrayList<>();

            assembly.add(getAssemblyLabel() + ":");
            for (CFStatement cfStatement : cfStatements) {
                assembly.addAll(cfStatement.toAssembly(variableTable, importTable));
            }

            List<String> body = new ArrayList<>();
            if (!isEnd) {
                body.add("jmp " + next.getAssemblyLabel());
            } else {
                throw new RuntimeException("Didn't expect a CFBlock to end the CFG");
            }

            assembly.addAll(AssemblyFactory.indent(body));
            return assembly;
        }
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
        if (isOuter) {
            // System.err.println("Thinks " + UID + " isOuter");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MethodCFGFactory.dfsPrint(miniCFG, new HashSet<Integer>(), new PrintStream(baos));
            return "\nMiniCFG: " + baos.toString() + "\n" +
            "UID " + UID + " CFBlock [ miniCFG=" + miniCFG.getUID() + ", next=" + next.getUID() + "], Scope = " + variableTable.getUID();
        }
        if (isEnd) return "UID " + UID + " CFBlock [" + cfStatements + "], Scope = " + variableTable.getUID();
        return "UID " + UID + " CFBlock [" + cfStatements + ", next=" + next.getUID() + "], Scope = " + variableTable.getUID();
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

    public List<CFStatement> getCfStatements() {
        if (isOuter) throw new RuntimeException("outer");
        return cfStatements;
    }

    public List<Statement> getStatements() {
        if (!isOuter) throw new RuntimeException("inner");
        return statements;
    }

    /**
     * List< Pair<TempsUpdated, TempsUsed> >
     * @return
     */
    @Override
    public List<Pair<Temp, List<Temp>>> getTemps() {
        if (isOuter) {
            TempCollector collector = new TempCollector();
            miniCFG.accept(collector);
            return collector.temps;
        }
        List<Pair<Temp, List<Temp>>> temps = new ArrayList<>();
        for (CFStatement statement : this.cfStatements) {
            temps.add(statement.getTemps());
        }
        return temps;
    }

    public void prependAllStatements(CFBlock block) {
        if (isOuter) {
            if (!block.isOuter) throw new RuntimeException("Expected outer block but got inner");
            List<Statement> thisCopy = new ArrayList<>(this.statements);
            this.statements.clear();
            this.statements.addAll(block.statements);
            this.statements.addAll(thisCopy);
        } else {
            if (block.isOuter) throw new RuntimeException("Expected inner block but got outer");
            List<CFStatement> thisCopy = new ArrayList<>(this.cfStatements);
            this.cfStatements.clear();
            this.cfStatements.addAll(block.cfStatements);
            this.cfStatements.addAll(thisCopy);
        }
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
        if (isOuter) return "_end_of_block_" + UID;
        throw new UnsupportedOperationException("Inner Blocks don't have mini CFGs");
    }
}
