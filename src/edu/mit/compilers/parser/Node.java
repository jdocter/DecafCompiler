package edu.mit.compilers.parser;

abstract class Node {
    private int mLine;
    public void setLineNumber(int line) {
        mLine = line;
    }

    public int getLineNumber() {
        return mLine;
    }
    
    abstract public void accept(Visitor v);
}