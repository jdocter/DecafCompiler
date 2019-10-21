package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;

import java.util.List;
import java.util.Set;

public class CFAssign implements CFNode {


    @Override
    public List<String> toAssembly(VariableTable variableTable, MethodTable methodTable) {
        return null;
    }

    @Override
    public Set<CFNode> parents() {
        return null;
    }

    @Override
    public void addParent(CFNode parent) {

    }

    @Override
    public void setNext(CFNode next) {

    }

    @Override
    public CFNode getNext() {
        return null;
    }

    @Override
    public List<CFNode> dfsTraverse() {
        return null;
    }

    @Override
    public int getUID() {
        return 0;
    }
}
