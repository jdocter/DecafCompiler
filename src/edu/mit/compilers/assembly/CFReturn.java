package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;

import java.util.List;

public class CFReturn implements CFNode {

    public CFReturn(Expr returnExpr) {

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
        // a return should not go anywhere
    }
}
