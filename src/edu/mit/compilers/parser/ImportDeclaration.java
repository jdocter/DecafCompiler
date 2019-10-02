package edu.mit.compilers.parser;

public class ImportDeclaration extends Node {
    public final Id mId;
    public ImportDeclaration(Id id) {
        mId = id;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
