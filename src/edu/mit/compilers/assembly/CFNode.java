package edu.mit.compilers.assembly;

import java.util.List;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;

public interface CFNode {
    List<String> toAssembly(VariableTable variableTable, MethodTable methodTable);

    List<CFNode> parents();

    void setNext(CFNode next);
}
