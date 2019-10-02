package edu.mit.compilers.parser;

public class Id extends Node {
    public final String mId;
    public Id(String id) {
        mId = id;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
