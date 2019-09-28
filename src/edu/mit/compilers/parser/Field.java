package edu.mit.compilers.parser;

public class Field extends Node {
    public final Type mType;
    Field(Type type, Id id) {
        mType = type;
    }
    Field(Type type, Id id, IntLit intLit) {
        mType = type;
    }

    public void addArg(Id id) {}
    public void addArg(Id id, IntLit intLit) {}
}
