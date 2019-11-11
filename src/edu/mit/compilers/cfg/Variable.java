package edu.mit.compilers.cfg;

import edu.mit.compilers.inter.FieldDescriptor;
import edu.mit.compilers.inter.TypeDescriptor;
import edu.mit.compilers.inter.VariableDescriptor;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Id;

public class Variable implements AssemblyVariable {

    private String id;
    public Variable(Id id) {this.id = id.getName();}

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
    public long getStackOffset(VariableTable variableTable) {
        return variableTable.getDescriptor(id).getStackOffset();
    }

    @Override
    public boolean isGlobal(VariableTable variableTable) {
        return variableTable.getDescriptor(id).isGlobal();
    }

    @Override
    public String getName() {
        return id;
    }

    @Override
    public boolean isArray(VariableTable variableTable) {
        return  variableTable.getDescriptor(id).getTypeDescriptor().isArray();
    }
}
