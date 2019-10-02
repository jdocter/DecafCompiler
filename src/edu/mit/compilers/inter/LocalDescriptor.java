package edu.mit.compilers.inter;


public class LocalDescriptor {
    private final String name;
    private final TypeDescriptor type;
    private long stackOffset;

    LocalDescriptor(TypeDescriptor type, String name) {
        this.name = name;
        this.type = type;
    }
}
