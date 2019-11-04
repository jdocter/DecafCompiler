package edu.mit.compilers.cfg.innercfg;

import java.util.List;
import java.util.Set;

import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.MiniCFVisitor;

public interface InnerCFNode {
    List<String> toAssembly(ImportTable importTable);
    String getAssemblyLabel();
    String getEndOfMiniCFGLabel();

    /*
     * Invariant:
     * dfsTraverse() is the inverse of parents(), aka:
     * node.dfsTraverse().contains(succ) iff succ.parents().contains(node)
     */
    Set<InnerCFNode> parents();
    void addParent(InnerCFNode parent);
    void removeParent(InnerCFNode parent);

    List<InnerCFNode> dfsTraverse(); // different from getNext for CFConditional, for example.
    void setNext(InnerCFNode next);
    InnerCFNode getNext();

    VariableTable getVariableTable();

    /*
     * WARNING!!! Doesn't automatically maintain parent pointers invariant for original!
     * This is because parents is a set, so there may be multiple
     * ways to get to a node from the parent.  Caller is responsible for fixing the parent pointers on original.
     */
    void replacePointers(InnerCFNode original, InnerCFNode replacement);

    int getUID();

    void accept(MiniCFVisitor v);

    /**
     * @return List< Pair<TempUpdated, TempsUsed> >, one pair for each statement
     */
    List<Pair<Temp, List<Temp>>> getTemps();
}
