package edu.mit.compilers.parser;

import edu.mit.compilers.inter.LocalTable;
import edu.mit.compilers.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Block extends Node  {

    public final List<Statement> statements = new ArrayList<>();
    public final List<FieldDeclaration> fieldDeclarations = new ArrayList<>();

    public LocalTable localTable;

    Block () { }

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    public void addFieldDeclaration(FieldDeclaration fieldDeclaration) {
        fieldDeclarations.add(fieldDeclaration);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override public String toString() {
        return "Block [" + statements + ", fieldDeclarations=" + fieldDeclarations + "]";
    }
}
