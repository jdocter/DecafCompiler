package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFReturn implements CFNode {


    private CFNode next;
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
}
