package edu.mit.compilers.parser;

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
