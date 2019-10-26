package edu.mit.compilers.inter;

public interface VariableTable {
    boolean isDeclared(String id);
    VariableDescriptor getDescriptor(String id);
    int getUID();
}
