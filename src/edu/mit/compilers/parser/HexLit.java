package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.ASTVisitor;

public class HexLit extends Node {
    public final String hex;

    HexLit(String s) {
        hex = s.substring(2);
    }

    @Override
    public void accept(ASTVisitor v) {
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
