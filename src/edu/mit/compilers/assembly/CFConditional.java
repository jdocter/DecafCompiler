package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFConditional implements CFNode {
    private final Expr boolExpr;
    private final CFNode ifTrue;
    private final CFNode ifFalse;
    private Set<CFNode> parents = new HashSet<CFNode>();

    public CFConditional(Expr expr, CFNode ifTrue, CFNode ifFalse) {
        this.boolExpr = expr;
        this.ifTrue= ifTrue;
        this.ifFalse = ifFalse;
    }

    @Override
    public List<String> toAssembly(VariableTable variableTable, MethodTable methodTable) {
        return null;
    }

    @Override
    public Set<CFNode> parents() {
        return this.parents ;
    }

    @Override
    public void setNext(CFNode next) {
        if (this.ifTrue != this) {
            this.ifTrue.setNext(next);
        }
        if (this.ifFalse != this) {
            this.ifFalse.setNext(next);
        }
    }

    @Override
    public void addParent(CFNode parent) {
        this.parents.add(parent);
    }

    @Override
    public CFNode getNext() {
        throw new UnsupportedOperationException("Don't know how to find NEXT for a CFConditional");
    }
}
