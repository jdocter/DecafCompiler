package edu.mit.compilers.inter;


import edu.mit.compilers.util.UIDObject;

public class FieldDescriptor extends UIDObject implements VariableDescriptor {
    public final TypeDescriptor type;

    FieldDescriptor(TypeDescriptor type) {
        this.type = type;
    }

    @Override
    public TypeDescriptor getTypeDescriptor() {
        return type;
    }

    @Override
    public boolean isGlobal() {
        return true;
    }


}
