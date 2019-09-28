package edu.mit.compilers.parser;

public class DecLit extends Node {
    public final int mDec;
    DecLit(String s) {
        mDec = Integer.valueOf(s);
    }
}
