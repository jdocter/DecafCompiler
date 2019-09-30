package edu.mit.compilers.parser;

public class Lit extends Node {

    public IntLit mIntLit;
    public char mChar;
    public boolean mBool;

    Lit(IntLit intLit) {
        mIntLit = intLit;
    }
    Lit(char c) {
        mChar = c;
    }
    Lit(boolean bool) {
        mBool = bool;
    }
}
