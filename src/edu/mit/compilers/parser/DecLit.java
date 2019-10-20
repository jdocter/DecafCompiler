package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.ASTVisitor;

public class DecLit extends Node {
    public final String dec;
    DecLit(String s) { dec = s;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    public String negDec() {
        return "-" + dec;
    }

    @Override public String toString() {
        return "DecLit [dec=" + dec + "]";
    }

}
