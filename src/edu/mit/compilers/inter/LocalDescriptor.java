package edu.mit.compilers.inter;


import edu.mit.compilers.util.UIDObject;

public class LocalDescriptor extends UIDObject implements VariableDescriptor {
    private final TypeDescriptor type;
    private boolean isFormalParam;
    private long stackOffset;
    private boolean offsetDeclared = false;

    LocalDescriptor(TypeDescriptor type, boolean isFormalParam) {
        this.type = type;
        this.isFormalParam = isFormalParam;
    }

    public void setStackOffset(long stackOffset) {
        this.stackOffset = stackOffset;
        this.offsetDeclared = true;
    }

    @Override
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

    @Override
    public boolean canAssignRegister() {
        return !isFormalParam;
    }
}
