package edu.mit.compilers.inter;


public class LocalDescriptor {
    private final TypeDescriptor type;
    private long stackOffset;

    LocalDescriptor(TypeDescriptor type) {
        this.type = type;
    }
}
