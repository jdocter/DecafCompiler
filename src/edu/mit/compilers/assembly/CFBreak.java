package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFBreak extends UIDObject implements CFNode {

    @Override public String toString() {
        return "UID " + UID + " CFBreak [next=" + next.getUID() + "]";
    }

    private Set<CFNode> parents = new HashSet<CFNode>();
    CFNode next;

    @Override
    public List<String> toAssembly(VariableTable variableTable, MethodTable methodTable) {
        return null;
    }

    @Override
    public Set<CFNode> parents() {
        return this.parents;
    }

    @Override
    public void setNext(CFNode next) {
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
        return List.of(next);
    }

    @Override
    public void accept(CFVisitor v) {
        v.visit(this);
    }
}
