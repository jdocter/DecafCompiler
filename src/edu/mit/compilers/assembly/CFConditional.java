package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;

import java.util.List;

public class CFConditional implements CFNode {
    private final Expr boolExpr;
    private final CFNode ifTrue;
    private final CFNode ifFalse;

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
    public List<CFNode> parents() {
        return null;
    }

    @Override
    public void setNext(CFNode next) {

    }
}
