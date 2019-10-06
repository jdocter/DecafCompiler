package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

public class HexLit extends Node {
    public final String hex;

    HexLit(String s) {
        hex = s.substring(2);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
    @Override
    public String toString() {
        return "0x"+hex;
    }

    public String negHex() {
        return "-" + hex;
    }
}
