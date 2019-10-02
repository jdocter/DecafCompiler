package edu.mit.compilers.inter;


public class FieldDescriptor {
    public final String name;
    public final TypeDescriptor type;
    FieldDescriptor(TypeDescriptor type, String name, boolean array) {
        this.name = name;
        this.type = type;
    }
}
