package edu.mit.compilers.cfg;

import java.util.List;
import java.util.Set;

import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.CFVisitor;

public interface CFNode {
    String getAssemblyLabel();
    String getEndOfMiniCFGLabel();

    /*
     * Invariant:
     * dfsTraverse() is the inverse of parents(), aka:
     * node.dfsTraverse().contains(succ) iff succ.parents().contains(node)
     */
    Set<CFNode> parents();
    void addParent(CFNode parent);
    void removeParent(CFNode parent);

    List<CFNode> dfsTraverse(); // different from getNext for CFConditional, for example.
    void setNext(CFNode next);
    CFNode getNext();

    VariableTable getVariableTable();

    /*
     * WARNING!!! Doesn't automatically maintain parent pointers invariant for original!
     * This is because parents is a set, so there may be multiple
     * ways to get to a node from the parent.  Caller is responsible for fixing the parent pointers on original.
     */
    void replacePointers(CFNode original, CFNode replacement);

    int getUID();

    void accept(CFVisitor v);

    /**
     * @return List< Pair<TempUpdated, TempsUsed> >, one pair for each statement
     */
    List<Pair<List<Temp>, List<Temp>>> getTemps();

    Set<Expr> getNonMethodCallSubExpressions();

    Set<Expr> generatedExprs(Set<Expr> allExprs);
    Set<Expr> killedExprs(Set<Expr> allExprs);
}
