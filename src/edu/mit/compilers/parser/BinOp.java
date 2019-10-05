package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

public class BinOp extends Node {
    public final String binOp;
    BinOp(String s) {
        binOp = s;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

}
