package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

public class Lit extends Node {
    public static final int BOOL = 0;
    public static final int INT = 1;
    public static final int CHAR = 2;

    public int litType;
    public IntLit mIntLit;
    public char mChar;
    public boolean mBool;

    Lit(IntLit intLit) {
        litType = INT;
        mIntLit = intLit;

    }
    Lit(char c) {
        litType = CHAR;
        mChar = c;
    }
    Lit(boolean bool) {
        litType = BOOL;
        mBool = bool;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
