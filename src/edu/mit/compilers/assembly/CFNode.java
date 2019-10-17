package edu.mit.compilers.assembly;

import java.util.List;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;

public interface CFNode {
    List<String> getAssemblyCode(VariableTable variableTable, MethodTable methodTable);

    List<CFNode> parents();

    boolean isSplitPoint();

    void setNext(CFNode next);
}
