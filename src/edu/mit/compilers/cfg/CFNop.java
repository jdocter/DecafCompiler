package edu.mit.compilers.cfg;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.cfg.innercfg.InnerCollectSubExpressions;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFNop extends UIDObject implements CFNode {
    @Override public String toString() {
        if (isEnd()) return "UID " + UID + " CFNop";
        return "UID " + UID + " CFNop [next=" + next.getUID() + "]";
    }

    CFNode next;
    private boolean isEnd = true; // end of function
    private Set<CFNode> parents = new HashSet<CFNode>();

    @Override
    public List<String> toAssembly(ImportTable importTable) {
        List<String> assembly = new ArrayList<>();

        List<String> body = new ArrayList<>();
        if (!isEnd) {
            body.add("jmp " + next.getAssemblyLabel());
        } else {
            // should have been end of MiniCFG
            throw new UnsupportedOperationException("CFNop should have been constructed as a CFEndOfMiniCFG");
        }

        assembly.add(getAssemblyLabel() + ":");
        assembly.addAll(AssemblyFactory.indent(body));
        return assembly;
    }

    @Override
    public Set<CFNode> parents() {
        return this.parents;
    }

    @Override
    public void setNext(CFNode next) {
        setEnd(false);
        this.next = next;
        next.addParent(this);
    }

    @Override
    public void addParent(CFNode parent) {
        this.parents.add(parent);
    }

    @Override
    public CFNode getNext() {
        return next;
    }

    @Override
    public List<CFNode> dfsTraverse() {
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
    public void removeParent(CFNode parent) {
        this.parents.remove(parent);
    }

    @Override
    public void replacePointers(CFNode original, CFNode replacement) {
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
    public String getAssemblyLabel() {
        return "_nop_" + UID;
    }

    @Override
    public String getEndOfMiniCFGLabel() {
        throw new UnsupportedOperationException("Nops don't have Mini CFGs");
    }
}
