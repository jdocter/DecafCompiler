package edu.mit.compilers.parser;

import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.visitor.SemanticChecker;
import edu.mit.compilers.visitor.Visitor;

abstract class Node {
    private int mLine;
    public void setLineNumber(int line) {
        mLine = line;
    }

    public int getLineNumber() {
        return mLine;
    }
    
    abstract public void accept(Visitor v);

    abstract public void accept(SemanticChecker semanticChecker) throws SemanticException;
}