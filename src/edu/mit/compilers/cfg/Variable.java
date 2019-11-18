package edu.mit.compilers.cfg;

import edu.mit.compilers.inter.FieldDescriptor;
import edu.mit.compilers.inter.TypeDescriptor;
import edu.mit.compilers.inter.VariableDescriptor;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Id;

public class Variable implements AssemblyVariable {

    private Id id;
    public Variable(Id id) {this.id = id;}

    @Override
    public String toString() {
        return id.toString();
    }

    public Id getId() {
        return id;
    }

    @Override
    public String getGlobalLabel(VariableTable variableTable) {
        VariableDescriptor variableDescriptor = variableTable.getDescriptor(id.getName());
        return ((FieldDescriptor) variableDescriptor).getGlobalLabel();
    }

    @Override
    public long getArrayLength(VariableTable variableTable) {
        VariableDescriptor variableDescriptor = variableTable.getDescriptor(id.getName());
        TypeDescriptor typeDescriptor = variableDescriptor.getTypeDescriptor();
        return typeDescriptor.getLength();
    }

    @Override
    public int getElementSize(VariableTable variableTable) {
        return variableTable.getDescriptor(id.getName()).getTypeDescriptor().elementSize();
    }

    @Override
    public long getStackOffset(VariableTable variableTable) {
        return variableTable.getDescriptor(id.getName()).getStackOffset();
    }

    @Override
    public boolean isGlobal(VariableTable variableTable) {
        return variableTable.getDescriptor(id.getName()).isGlobal();
    }

    @Override
    public String getName() {
        return id.getName();
    }

    @Override
    public boolean isArray(VariableTable variableTable) {
        return  variableTable.getDescriptor(id.getName()).getTypeDescriptor().isArray();
    }

    @Override
    public boolean isTemporary() {
        return false;
    }
}
