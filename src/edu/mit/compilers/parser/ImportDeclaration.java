package edu.mit.compilers.parser;

import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.visitor.SemanticChecker;
import edu.mit.compilers.visitor.Visitor;

public class ImportDeclaration extends Node {
    public final Id id;
    public ImportDeclaration(Id id) {
        this.id = id;
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
