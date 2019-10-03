package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

public class BinOp extends Node {
    public final String mBinOp;
    BinOp(String s) {
        mBinOp = s;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
