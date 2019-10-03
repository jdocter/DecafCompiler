package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

public class Type extends Node {
    public static final int BOOL = 0;
    public static final int INT = 1;
    public static final int VOID = 2;

    public final int mType;
    Type(int i) {
        mType = i;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
