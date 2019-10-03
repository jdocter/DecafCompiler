package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

public class IntLit extends Node {

    public HexLit mHexLit;
    public DecLit mDecLit;
    IntLit(HexLit hexLit) {
        mHexLit = hexLit;
    }
    IntLit(DecLit decLit) {
        mDecLit = decLit;
    }

    public int integer() {
        if (mHexLit != null) {
            return mHexLit.mHex;
        } else {
            return mDecLit.mDec;
        }
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
