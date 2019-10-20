package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.ASTVisitor;

public class Type extends Node {
    public static final int BOOL = 1;
    public static final int INT = 2;

    public final int mType;
    Type(int i) {
        mType = i;
    }

    @Override public String toString() {
        switch (mType) {
            case BOOL:
                return "BOOL";
            case INT:
                return "INT";
            default:
                throw new RuntimeException("Unrecognized type: " + mType);
        }
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}
