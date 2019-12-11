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

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Variable)) {
            return false;
        }
        Variable other = (Variable) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
