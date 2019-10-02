package edu.mit.compilers.parser;

public class StringLit extends Node {

    public final String mString;
    StringLit(String s) {
        mString = s;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
