package edu.mit.compilers.parser;

import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.visitor.SemanticChecker;
import edu.mit.compilers.visitor.Visitor;

public class Type extends Node {
    public static final int BOOL = 0;
    public static final int INT = 1;

    public final int mType;
    Type(int i) {
        mType = i;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public void accept(SemanticChecker semanticChecker) throws SemanticException {
        semanticChecker.check(this);
    }
}
