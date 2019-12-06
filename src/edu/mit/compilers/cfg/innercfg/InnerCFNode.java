package edu.mit.compilers.cfg.innercfg;

import java.util.List;
import java.util.Set;

import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.cfg.CFNode;
import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.MiniCFVisitor;

public abstract class InnerCFNode extends CFNode {
    public abstract String getAssemblyLabel();
    public abstract String getEndOfMiniCFGLabel();

    /*
     * Invariant:
     * dfsTraverse() is the inverse of parents(), aka:
     * node.dfsTraverse().contains(succ) iff succ.parents().contains(node)
     */
    public abstract Set<InnerCFNode> parents();
    public abstract void addParent(InnerCFNode parent);
    public abstract void removeParent(InnerCFNode parent);

    public abstract List<InnerCFNode> dfsTraverse(); // different from getNext for CFConditional, for example.
    public abstract void setNext(InnerCFNode next);
    public abstract InnerCFNode getNext();

    public abstract VariableTable getVariableTable();

    /*
     * WARNING!!! Doesn't automatically maintain parent pointers invariant for original!
     * This is because parents is a set, so there may be multiple
     * ways to get to a node from the parent.  Caller is responsible for fixing the parent pointers on original.
     */
    public abstract void replacePointers(InnerCFNode original, InnerCFNode replacement);

    public abstract void accept(MiniCFVisitor v);

    /**
     * @return List< Pair<TempUpdated, TempsUsed> >, one pair for each statement
     */
    public abstract List<Pair<List<Temp>, List<Temp>>> getTemps();

    public abstract Set<Expr> getNonMethodCallSubExpressions();

    /**
     * @param allExprs list of all expressions that should be considered -- necessary
     *                 parameter because
     * @return list of expressions that are available at the end of this CFStatement
     */
    public abstract Set<Expr> generatedExprs(Set<Expr> allExprs);

    /**
     * @param allExprs list of all expressions that should be considered
     * @return subset of exprs that are killed by this CFStatement
     */
    public abstract Set<Expr> killedExprs(Set<Expr> allExprs);

    public abstract Set<AssemblyVariable> getLocalAssemblyVariables();
    public abstract Set<AssemblyVariable> getDefined();
    public abstract Set<AssemblyVariable> getUsed();
}
