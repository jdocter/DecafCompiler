package edu.mit.compilers.assembly;

import java.util.List;
import java.util.Set;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;

public interface CFNode {
    List<String> toAssembly(VariableTable variableTable, MethodTable methodTable);

    Set<CFNode> parents();

    void addParent(CFNode parent);

    void setNext(CFNode next);

    CFNode getNext();
}
