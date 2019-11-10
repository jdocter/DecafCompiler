package edu.mit.compilers.parser;

import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.inter.FieldDescriptor;
import edu.mit.compilers.inter.TypeDescriptor;
import edu.mit.compilers.inter.VariableDescriptor;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.visitor.ASTVisitor;

public class Id extends Node implements AssemblyVariable {
    private final String id;
    public Id(String id) {
        this.id = id;
    }

    public String getName() {
        return id;
    }

    @Override
    public String getGlobalLabel(VariableTable variableTable) {
        VariableDescriptor variableDescriptor = variableTable.getDescriptor(id);
        return ((FieldDescriptor) variableDescriptor).getGlobalLabel();
    }

    @Override
    public long getArrayLength(VariableTable variableTable) {
        VariableDescriptor variableDescriptor = variableTable.getDescriptor(id);
        TypeDescriptor typeDescriptor = variableDescriptor.getTypeDescriptor();
        return typeDescriptor.getLength();
    }

    @Override
    public int getElementSize(VariableTable variableTable) {
        return variableTable.getDescriptor(id).getTypeDescriptor().elementSize();
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    @Override public String toString() {
        return id;
    }

    @Override
    public long getStackOffset(VariableTable variableTable) {
        return variableTable.getDescriptor(id).getStackOffset();
    }

    @Override
    public boolean isGlobal(VariableTable variableTable) {
        return variableTable.getDescriptor(id).isGlobal();
    }

    @Override
    public boolean isArray(VariableTable variableTable) {
        return  variableTable.getDescriptor(id).getTypeDescriptor().isArray();
    }
}
