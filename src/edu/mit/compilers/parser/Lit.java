package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.ASTVisitor;

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

    @Override public String toString() {
        switch (litType) {
            case BOOL:
                return "" + mBool;
            case CHAR:
                return "" + mChar;
            case INT:
                return "" + mIntLit;
            default:
                throw new RuntimeException("Unknown Lit type: " + litType);
        }
    }
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}
