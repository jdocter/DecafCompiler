package edu.mit.compilers.cfg;

import java.util.List;
import java.util.Set;

import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.CFVisitor;

public interface OuterCFNode extends CFNode {
    String getAssemblyLabel();
    String getEndOfMiniCFGLabel();

    InnerCFNode getMiniCFGStart();
    InnerCFNode getMiniCFGEnd();

    /*
     * Invariant:
     * dfsTraverse() is the inverse of parents(), aka:
     * node.dfsTraverse().contains(succ) iff succ.parents().contains(node)
     */
    Set<OuterCFNode> parents();
    void addParent(OuterCFNode parent);
    void removeParent(OuterCFNode parent);

    List<OuterCFNode> dfsTraverse(); // different from getNext for CFConditional, for example.
    void setNext(OuterCFNode next);
    OuterCFNode getNext();

    VariableTable getVariableTable();

    /*
     * WARNING!!! Doesn't automatically maintain parent pointers invariant for original!
     * This is because parents is a set, so there may be multiple
     * ways to get to a node from the parent.  Caller is responsible for fixing the parent pointers on original.
     */
    void replacePointers(OuterCFNode original, OuterCFNode replacement);

    int getUID();

    void accept(CFVisitor v);

    /**
     * @return List< Pair<TempUpdated, TempsUsed> >, one pair for each statement
     */
    List<Pair<List<Temp>, List<Temp>>> getTemps();

    Set<Expr> getSubExpressions();

    Set<Expr> generatedExprs(Set<Expr> allExprs);
    Set<Expr> killedExprs(Set<Expr> allExprs);

    /** set of LOCAL AssemblyVariables that are used only in the outer CFG*/
    Set<AssemblyVariable> getOuterUsed();
    /** set of LOCAL AssemblyVariables that are defined only in the outer CFG*/
    Set<AssemblyVariable> getOuterDefined();


}
