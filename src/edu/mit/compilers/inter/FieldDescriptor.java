package edu.mit.compilers.inter;


public class FieldDescriptor implements VariableDescriptor {
    public final TypeDescriptor type;
    public int memoryOffset;
    FieldDescriptor(TypeDescriptor type) {
        this.type = type;
    }

    @Override
    public TypeDescriptor getTypeDescriptor() {
        return type;
    }

}
