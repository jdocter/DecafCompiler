package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;

import java.util.List;

public class CFContinue implements CFNode {
    @Override
    public List<String> getAssemblyCode(VariableTable variableTable, MethodTable methodTable) {
        return null;
    }

    @Override
    public List<CFNode> parents() {
        return null;
    }

    @Override
    public boolean isSplitPoint() {
        return false;
    }

    @Override
    public void setNext(CFNode next) {

    }
}
