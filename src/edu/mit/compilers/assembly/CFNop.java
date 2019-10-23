package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.util.UIDObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFNop extends UIDObject implements CFNode {
    @Override public String toString() {
        if (isEnd) return "UID " + UID + " CFNop";
        return "UID " + UID + " CFNop [next=" + next.getUID() + "]";
    }

    CFNode next;
    boolean isEnd = true; // end of function
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
        isEnd = false;
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
}
