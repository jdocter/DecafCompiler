package edu.mit.compilers.inter;


public interface VariableDescriptor {
    public TypeDescriptor getTypeDescriptor();
    public boolean isGlobal();
    public boolean canAssignRegister();
    long getStackOffset();

}
