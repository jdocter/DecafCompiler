package edu.mit.compilers.parser;

public class IntLit extends Node {

    public HexLit mHexLit;
    public DecLit mDecLit;
    IntLit(HexLit hexLit) {
        mHexLit = hexLit;
    }
    IntLit(DecLit decLit) {
        mDecLit = decLit;
    }
}
