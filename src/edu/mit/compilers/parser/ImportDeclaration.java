package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.ASTVisitor;

public class ImportDeclaration extends Node {
    public final Id id;
    public ImportDeclaration(Id id) {
        this.id = id;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    @Override public String toString() {
        return "ImportDeclaration [id=" + id + "]";
    }
}
