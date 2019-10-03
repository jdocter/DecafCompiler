package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

public class DecLit extends Node {
    public final int mDec;
    DecLit(String s) {
        mDec = Integer.valueOf(s);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
