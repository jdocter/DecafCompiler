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

    public String toAssembly() {
        switch (litType) {
            case BOOL: return mBool ? "$1" : "$0";
            case INT: return "$" + mIntLit.toString();
            case CHAR: return "$" + (int) mChar;
            default: throw new RuntimeException("Lit.toAssembly: impossible to reach");
        }
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Lit)) return false;
        Lit that = (Lit) obj;
        switch (litType) {
            case BOOL:
                return mBool == that.mBool;
            case CHAR:
                return mChar == that.mChar;
            case INT:
                return mIntLit.toString().equals(that.mIntLit);
            default:
                throw new RuntimeException("Unknown Lit type: " + litType);
        }

    }
}
