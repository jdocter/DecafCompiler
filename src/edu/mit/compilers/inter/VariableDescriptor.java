package edu.mit.compilers.inter;


public interface VariableDescriptor {
    public TypeDescriptor getTypeDescriptor();
    public boolean isGlobal();
    long getStackOffset();

}
