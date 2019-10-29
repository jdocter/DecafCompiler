package edu.mit.compilers.inter;


import edu.mit.compilers.util.UIDObject;

public class LocalDescriptor extends UIDObject implements VariableDescriptor {
    private final TypeDescriptor type;
    private long stackOffset;
    private boolean offsetDeclared = false;

    LocalDescriptor(TypeDescriptor type) {
        this.type = type;
    }

    public void setStackOffset(long stackOffset) {
        this.stackOffset = stackOffset;
        this.offsetDeclared = true;
    }

    public long getStackOffset() {
        if (!offsetDeclared) throw new RuntimeException("stack offset must be set before it can be accessed");
        return stackOffset;
    }

    @Override
    public TypeDescriptor getTypeDescriptor() {
        return type;
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
