package edu.mit.compilers.assembly;

import java.util.List;
import java.util.Set;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.visitor.CFVisitor;

public interface CFNode {
    List<String> toAssembly(VariableTable variableTable, MethodTable methodTable);

    Set<CFNode> parents();

    void addParent(CFNode parent);

    void setNext(CFNode next);

    CFNode getNext();

    List<CFNode> dfsTraverse(); // different from getNext for CFConditional, for example.

    int getUID();

    void accept(CFVisitor v);
}
