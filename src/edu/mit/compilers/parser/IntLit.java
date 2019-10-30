package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.ASTVisitor;

public class IntLit extends Node {

    public HexLit hexLit;
    public DecLit decLit;
    IntLit(HexLit hexLit) {
        this.hexLit = hexLit;
    }
    IntLit(DecLit decLit) {
        this.decLit = decLit;
    }

    public long posInteger() {
        if (hexLit != null) {
            return Long.parseLong(hexLit.hex,16);
        } else {
            return Long.parseLong(decLit.dec);
        }
    }

    public String posString() {
        if (hexLit != null) {
            return hexLit.toString();
        } else {
            return decLit.dec;
        }
    }

    @Override public String toString() {
        return posString();
    }

    public long negInteger() {
        if (hexLit != null) {
            return Long.parseLong(hexLit.negHex(),16);
        } else {
            return Long.parseLong(decLit.negDec());
        }
    }

    public String negString() {
        if (hexLit != null) {
            return "-" + hexLit.toString();
        } else {
            return "-" + decLit.dec;
        }
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}
