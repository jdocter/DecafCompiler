package edu.mit.compilers.cfg;

import java.util.List;
import java.util.Set;

import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.CFVisitor;

public abstract class OuterCFNode extends CFNode {
    public abstract String getAssemblyLabel();
    public abstract String getEndOfMiniCFGLabel();

    public abstract InnerCFNode getMiniCFGStart();
    public abstract InnerCFNode getMiniCFGEnd();

    /*
     * Invariant:
     * dfsTraverse() is the inverse of parents(), aka:
     * node.dfsTraverse().contains(succ) iff succ.parents().contains(node)
     */
    public abstract Set<OuterCFNode> parents();
    public abstract void addParent(OuterCFNode parent);
    public abstract void removeParent(OuterCFNode parent);

    /*
     * Return in arbitrary order, but must be the same order every time.
     */
    public abstract List<OuterCFNode> dfsTraverse(); // different from getNext for CFConditional, for example.
    public abstract void setNext(OuterCFNode next);
    public abstract OuterCFNode getNext();

    public abstract VariableTable getVariableTable();

    /*
     * WARNING!!! Doesn't automatically maintain parent pointers invariant for original!
     * This is because parents is a set, so there may be multiple
     * ways to get to a node from the parent.  Caller is responsible for fixing the parent pointers on original.
     */
    public abstract void replacePointers(OuterCFNode original, OuterCFNode replacement);

    public abstract void accept(CFVisitor v);

    /**
     * @return List< Pair<TempUpdated, TempsUsed> >, one pair for each statement
     */
    public abstract List<Pair<List<Temp>, List<Temp>>> getTemps();

    public abstract Set<Expr> getNonMethodCallSubExpressions();
    public abstract Set<Expr> generatedExprs(Set<Expr> allExprs);
    public abstract Set<Expr> killedExprs(Set<Expr> allExprs);

    /** set of LOCAL AssemblyVariables that are used only in the outer CFG*/
    @Override
    public abstract Set<AssemblyVariable> getUsed();
    /** set of LOCAL AssemblyVariables that are defined only in the outer CFG*/
    @Override
    public abstract Set<AssemblyVariable> getDefined();

}
