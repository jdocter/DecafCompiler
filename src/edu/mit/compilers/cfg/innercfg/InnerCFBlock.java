package edu.mit.compilers.cfg.innercfg;

import java.util.*;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.MiniCFVisitor;


public class InnerCFBlock extends UIDObject implements InnerCFNode {

    private final List<CFStatement> cfStatements = new ArrayList<>();
    InnerCFNode next;

    boolean isEnd; // end of function
    private Set<InnerCFNode> parents = new HashSet<InnerCFNode>();
    private final VariableTable variableTable;

    public InnerCFBlock(CFStatement statement, VariableTable variableTable) {
        // super();
        // System.err.println("Should be making a new inner CFBLock " + UID);
        this.cfStatements.add(statement);
        this.variableTable = variableTable;
    }

    @Override
    public void setNext(InnerCFNode next) {
        isEnd = false;
        this.next = next;
        next.addParent(this);
    }

    @Override
    public InnerCFNode getNext() {
        return next;
    }

    @Override
    public List<String> toAssembly(ImportTable importTable) {
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

    @Override
    public Set<InnerCFNode> parents() {
        return this.parents;
    }

    @Override
    public void addParent(InnerCFNode parent) {
        this.parents.add(parent);
    }

    @Override public String toString() {
        if (isEnd) return "UID " + UID + " CFBlock [" + cfStatements + "], Scope = " + variableTable.getUID();
        return "UID " + UID + " CFBlock [" + cfStatements + ", next=" + next.getUID() + "], Scope = " + variableTable.getUID();
    }

    @Override
    public List<InnerCFNode> dfsTraverse() {
        if (isEnd) return List.of();
        return List.of(next);
    }

    @Override
    public void accept(MiniCFVisitor v) {
        v.visit(this);
    }

    @Override
    public void removeParent(InnerCFNode parent) {
        this.parents.remove(parent);
    }

    @Override
    public VariableTable getVariableTable() {
        return variableTable;
    }

    @Override
    public void replacePointers(InnerCFNode original, InnerCFNode replacement) {
        if (this.next == original) {
            this.setNext(replacement);
        }
    }

    public List<CFStatement> getCfStatements() {
        return cfStatements;
    }

    /**
     * List< Pair<TempsUpdated, TempsUsed> >
     * @return
     */
    @Override
    public List<Pair<Temp, List<Temp>>> getTemps() {
        List<Pair<Temp, List<Temp>>> temps = new ArrayList<>();
        for (CFStatement statement : this.cfStatements) {
            temps.add(statement.getTemps());
        }
        return temps;
    }

    @Override
    public Set<Expr> getSubExpressions() {
        Set<Expr> exprs = new HashSet<>();
        for (CFStatement cfStatement : cfStatements) {
            Expr rhs = cfStatement.getRHS();
            if (rhs != null) exprs.add(rhs);
        }
        return exprs;
    }


    @Override
    public Set<Expr> generatedExprs(Set<Expr> allExprs) {
        Set<Expr> generated = new HashSet<>();
        for (CFStatement cfStatement: cfStatements) {
            Optional<Expr> statementGen = cfStatement.generatedExpr();
            if (statementGen.isPresent()) {
                generated.add(statementGen.get());
            }
            // System.err.println(getUID() + " GENERATED AFTER " + cfStatement + " : " + generated);
            generated.removeAll(cfStatement.killedExprs(allExprs));
            // System.err.println(getUID() + " GENERATED AFTER REMOVING " + cfStatement + " : " + generated);
        }
        return generated;
    }

    @Override
    public Set<Expr> killedExprs(Set<Expr> allExprs) {
        Set<Expr> killed = new HashSet<>();
        for (CFStatement cfStatement: cfStatements) {
            killed.addAll(cfStatement.killedExprs(allExprs));
            Optional<Expr> statementGen = cfStatement.generatedExpr();
            if (statementGen.isPresent()) {
                killed.remove(statementGen.get());
            }
        }
        return killed;
    }

    public void prependAllStatements(InnerCFBlock block) {
        List<CFStatement> thisCopy = new ArrayList<>(this.cfStatements);
        this.cfStatements.clear();
        this.cfStatements.addAll(block.cfStatements);
        this.cfStatements.addAll(thisCopy);
    }

    public boolean isSameScope(InnerCFBlock other) {
        return !(variableTable == null) && !(other.variableTable == null) && variableTable == other.variableTable;
    }

    @Override
    public String getAssemblyLabel() {
        return "_block_" + UID;
    }

    @Override
    public String getEndOfMiniCFGLabel() {
        throw new UnsupportedOperationException("Inner Blocks don't have mini CFGs");
    }
}