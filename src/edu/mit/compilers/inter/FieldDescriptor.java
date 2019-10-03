package edu.mit.compilers.inter;


public class FieldDescriptor {
    public final TypeDescriptor type;
    public int memoryOffset;
    FieldDescriptor(TypeDescriptor type) {
        this.type = type;
    }
}
