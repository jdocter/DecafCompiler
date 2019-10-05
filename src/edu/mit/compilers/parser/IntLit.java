package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

public class IntLit extends Node {

    public HexLit hexLit;
    public DecLit decLit;
    IntLit(HexLit hexLit) {
        this.hexLit = hexLit;
    }
    IntLit(DecLit decLit) {
        this.decLit = decLit;
    }

    public long integer() {
        if (hexLit != null) {
            return Long.parseLong(hexLit.hex,16);
        } else {
            return Long.parseLong(decLit.dec);
        }
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
