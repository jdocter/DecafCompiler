package edu.mit.compilers.parser;

import java.util.ArrayList;
import java.util.List;

public class Block extends Node  {

    public final List<Statement> mStatements = new ArrayList<>();
    public final List<FieldDeclaration> mFields = new ArrayList<>();
    Block () { }

    public void addStatement(Statement statement) {
        mStatements.add(statement);
    }

    public void addFieldDeclaration(FieldDeclaration field) {
        mFields.add(field);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
