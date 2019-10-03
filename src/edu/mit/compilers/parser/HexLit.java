package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

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
