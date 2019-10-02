package edu.mit.compilers.parser;

public class HexLit extends Node {
    public final int mHex;

    HexLit(String s) {
        mHex = Integer.parseInt(s,2,s.length(),16);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
