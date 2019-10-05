package edu.mit.compilers.parser;

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
}
