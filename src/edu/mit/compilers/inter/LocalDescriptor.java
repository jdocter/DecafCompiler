package edu.mit.compilers.inter;


public class LocalDescriptor implements VariableDescriptor {
    private final TypeDescriptor type;
    private long stackOffset;

    LocalDescriptor(TypeDescriptor type) {
        this.type = type;
    }

    @Override
    public TypeDescriptor getTypeDescriptor() {
        return type;
    }
}
