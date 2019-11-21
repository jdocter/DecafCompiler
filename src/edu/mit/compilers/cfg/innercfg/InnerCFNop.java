package edu.mit.compilers.cfg.innercfg;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.MiniCFVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InnerCFNop extends UIDObject implements InnerCFNode {
    @Override public String toString() {
        if (isEnd()) return "UID " + UID + " CFNop";
        return "UID " + UID + " CFNop [next=" + next.getUID() + "]";
    }

    InnerCFNode next;
    private boolean isEnd = true; // end of function
    private Set<InnerCFNode> parents = new HashSet<InnerCFNode>();

    @Override
    public Set<InnerCFNode> parents() {
        return this.parents;
    }

    @Override
    public void setNext(InnerCFNode next) {
        setEnd(false);
        this.next = next;
        next.addParent(this);
    }

    @Override
    public void addParent(InnerCFNode parent) {
        this.parents.add(parent);
    }

    @Override
    public InnerCFNode getNext() {
        return next;
    }

    @Override
    public List<InnerCFNode> dfsTraverse() {
        if (next == null) return List.of();
        return List.of(next);
    }

    @Override
    public void accept(MiniCFVisitor v) {
        v.visit(this);
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    @Override
    public void removeParent(InnerCFNode parent) {
        this.parents.remove(parent);
    }

    @Override
    public void replacePointers(InnerCFNode original, InnerCFNode replacement) {
        if (this.next == original) {
            this.setNext(replacement);
        }
    }

    @Override
    public VariableTable getVariableTable() {
        return null;
    }

    @Override
    public List<Pair<Temp, List<Temp>>> getTemps() {
        return List.of();
    }

    @Override
    public Set<Expr> getSubExpressions() {
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
    public String getAssemblyLabel() {
        return "_nop_" + UID;
    }

    @Override
    public String getEndOfMiniCFGLabel() {
        throw new UnsupportedOperationException("Nops don't have Mini CFGs");
    }
}
