package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.ASTVisitor;

public class StringLit extends Node {

    public final String mString;
    public String label;

    StringLit(String s) {
        mString = s;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    @Override public String toString() {
        return mString;
    }
}
