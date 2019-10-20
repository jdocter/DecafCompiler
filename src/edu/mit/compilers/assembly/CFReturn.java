package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.UIDObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFReturn extends UIDObject implements CFNode {


    @Override public String toString() {
        return "UID " + UID + " CFReturn [returnExpr=" + returnExpr + "]";
    }

    private CFNode next;
    boolean isEnd; // end of function
    private Expr returnExpr;
    private Set<CFNode> parents = new HashSet<CFNode>();

    public CFReturn(Expr returnExpr) {
        this.returnExpr = returnExpr;
    }

    @Override
    public List<String> toAssembly(VariableTable variableTable, MethodTable methodTable) {
        return null;
    }

    @Override
    public CFNode getNext() {
        return next;
    }

    @Override
    public Set<CFNode> parents() {
        return this.parents;
    }

    @Override
    public void setNext(CFNode next) {
        throw new UnsupportedOperationException("Tried setNext on a Return.  a return should not go anywhere");
    }

    @Override
    public void addParent(CFNode parent) {
        this.parents.add(parent);
    }

    @Override
    public List<CFNode> dfsTraverse() {
        return List.of();
    }
}
