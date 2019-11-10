package edu.mit.compilers.inter;


import edu.mit.compilers.util.UIDObject;

public class FieldDescriptor extends UIDObject implements VariableDescriptor {
    private final TypeDescriptor type;
    private final String name;

    FieldDescriptor(String name, TypeDescriptor type) {
        this.type = type;
        this.name = name;
    }

    @Override
    public TypeDescriptor getTypeDescriptor() {
        return type;
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

    @Override
    public long getStackOffset() {
        throw new RuntimeException("Global fields don't have stack offsets");
    }

    public String getName() {
        return name;
    }

    public String getGlobalLabel() {
        return "_global_" + name;
    }
}
