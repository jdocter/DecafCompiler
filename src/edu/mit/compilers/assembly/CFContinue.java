package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.util.UIDObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFContinue extends UIDObject implements CFNode {

    @Override public String toString() {
        return "UID " + UID + " CFContinue [next=" + next.getUID() + "]";
    }

    CFNode next;
    private Set<CFNode> parents = new HashSet<CFNode>();

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
}
