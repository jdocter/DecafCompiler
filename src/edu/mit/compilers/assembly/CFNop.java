package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;

import java.util.List;

public class CFNop implements CFNode {
    private CFNode next;
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
        this.next = next;
    }
}
