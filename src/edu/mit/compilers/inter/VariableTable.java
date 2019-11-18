package edu.mit.compilers.inter;

public interface VariableTable {
    public static final int GLOBAL_SCOPE_UID = 0;
    boolean isDeclared(String id);
    VariableDescriptor getDescriptor(String id);
    int getUID();
    FieldTable getFieldTable();
    int getDeclarationScope(String id);
}
