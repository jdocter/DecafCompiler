package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

public class StringLit extends Node {

    public final String mString;
    StringLit(String s) {
        mString = s;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override public String toString() {
        return mString;
    }
}
