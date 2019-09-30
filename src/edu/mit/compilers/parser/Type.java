package edu.mit.compilers.parser;

public class Type extends Node {
    public static final int BOOL = 0;
    public static final int INT = 1;
    public static final int STRING = 2;
    public static final int ARRAY = 10;

    public final int mType;
    Type(int i) {
        mType = i;
    }
}
