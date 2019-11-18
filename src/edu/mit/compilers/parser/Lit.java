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

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + litType;
        result = prime * result + (mBool ? 1231 : 1237);
        result = prime * result + mChar;
        result = prime * result + ((mIntLit == null) ? 0 : mIntLit.hashCode());
        return result;
    }
    @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Lit)) {
            return false;
        }
        Lit other = (Lit) obj;
        if (litType != other.litType) {
            return false;
        }
        if (mBool != other.mBool) {
            return false;
        }
        if (mChar != other.mChar) {
            return false;
        }
        if (mIntLit == null) {
            if (other.mIntLit != null) {
                return false;
            }
        } else if (!mIntLit.equals(other.mIntLit)) {
            return false;
        }
        return true;
    }
}
