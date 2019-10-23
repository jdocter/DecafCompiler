package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFConditional extends UIDObject implements CFNode {
    @Override public String toString() {
        return "UID " + UID + " CFConditional [boolExpr=" + boolExpr + ", ifTrue=" + ifTrue.getUID() + ", ifFalse=" + ifFalse.getUID() + "], Scope = " + variableTable.getUID();
    }

    private final Expr boolExpr;
    private CFNode ifTrue;
    private CFNode ifFalse;
    private Set<CFNode> parents = new HashSet<CFNode>();
    private final VariableTable variableTable;

    public CFConditional(Expr expr, CFNode ifTrue, CFNode ifFalse, VariableTable variableTable) {
        this.boolExpr = expr;
        this.ifTrue= ifTrue;
        ifTrue.addParent(this);

        this.ifFalse = ifFalse;
        ifFalse.addParent(this);
        this.variableTable = variableTable;
    }

    @Override
    public List<String> toAssembly(MethodTable methodTable) {
        return null;
    }

    @Override
    public Set<CFNode> parents() {
        return this.parents ;
    }

    @Override
    public void setNext(CFNode next) {
        throw new UnsupportedOperationException("CFConditional doesn't support setNext");
//        if (this.ifTrue != this) {
//            this.ifTrue.setNext(next);
//        }
//        if (this.ifFalse != this) {
//            this.ifFalse.setNext(next);
//        }
    }

    @Override
    public void addParent(CFNode parent) {
        this.parents.add(parent);
    }

    @Override
    public CFNode getNext() {
        throw new UnsupportedOperationException("Don't know how to find NEXT for a CFConditional");
    }

    @Override
    public List<CFNode> dfsTraverse() {
        return List.of(ifTrue, ifFalse);
    }

    @Override
    public void accept(CFVisitor v) {
        v.visit(this);
    }

    @Override
    public void removeParent(CFNode parent) {
        this.parents.remove(parent);
    }

    @Override
    public VariableTable getVariableTable() {
        return variableTable;
    }

    @Override
    public void replacePointers(CFNode original, CFNode replacement) {
        if (this.ifFalse == original) {
            this.ifFalse = replacement;
            this.ifFalse.addParent(this);
        }
        if (this.ifTrue == original) {
            this.ifTrue = replacement;
            this.ifTrue.addParent(this);
        }
    }
}
