package edu.mit.compilers.parser;

class Node {
    private int mLine;
    public void setLineNumber(int line) {
        mLine = line;
    }

    public int getLineNumber(int line) {
        return mLine;
    }
}