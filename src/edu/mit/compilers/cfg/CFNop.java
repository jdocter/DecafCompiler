package edu.mit.compilers.cfg;

import edu.mit.compilers.cfg.innercfg.InnerCFEndOfMiniCFG;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.cfg.innercfg.InnerCFNop;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFNop extends OuterCFNode {
    @Override public String toString() {
        if (isEnd()) return "UID " + UID + " CFNop";
        return "UID " + UID + " CFNop [next=" + next.getUID() + "]";
    }

    OuterCFNode next;
    private boolean isEnd = true; // end of function
    private Set<OuterCFNode> parents = new HashSet<OuterCFNode>();

    @Override
    public Set<OuterCFNode> parents() {
        return this.parents;
    }

    @Override
    public void setNext(OuterCFNode next) {
        setEnd(false);
        this.next = next;
        next.addParent(this);
    }

    @Override
    public void addParent(OuterCFNode parent) {
        this.parents.add(parent);
    }

    @Override
    public OuterCFNode getNext() {
        return next;
    }

    @Override
    public List<OuterCFNode> dfsTraverse() {
        if (next == null) return List.of();
        return List.of(next);
    }

    @Override
    public void accept(CFVisitor v) {
        v.visit(this);
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    @Override
    public void removeParent(OuterCFNode parent) {
        this.parents.remove(parent);
    }

    @Override
    public void replacePointers(OuterCFNode original, OuterCFNode replacement) {
        if (this.next == original) {
            this.setNext(replacement);
        }
    }

    @Override
    public VariableTable getVariableTable() {
        return null;
    }

    @Override
    public List<Pair<List<Temp>, List<Temp>>> getTemps() {
        return List.of();
    }

    @Override
    public Set<Expr> getNonMethodCallSubExpressions() {
        return Set.of();
    }

    @Override
    public Set<Expr> generatedExprs(Set<Expr> allExprs) {
        return Set.of();
    }

    @Override
    public Set<Expr> killedExprs(Set<Expr> allExprs) {
        return Set.of();
    }

    @Override
    public Set<AssemblyVariable> getUsed() {
        return Set.of();
    }

    @Override
    public Set<AssemblyVariable> getDefined() {
        return Set.of();
    }

    @Override
    public String getAssemblyLabel() {
        return "_nop_" + UID;
    }

    @Override
    public String getEndOfMiniCFGLabel() {
        throw new UnsupportedOperationException("Nops don't have Mini CFGs");
    }

    @Override
    public InnerCFNode getMiniCFGStart() {
        return new InnerCFEndOfMiniCFG(this);
    }

    @Override
    public InnerCFNode getMiniCFGEnd() {
        return new InnerCFEndOfMiniCFG(this);
    }

    @Override
    public String toWebString() {
        throw new RuntimeException("not a CFNode");
    }
}
