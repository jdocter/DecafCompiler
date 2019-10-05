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

    public int integer() {
        if (hexLit != null) {
            return hexLit.hex;
        } else {
            return decLit.dec;
        }
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
