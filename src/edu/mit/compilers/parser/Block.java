package edu.mit.compilers.parser;

import java.util.ArrayList;
import java.util.List;

public class Block extends Node  {

    public final List<Statement> mStatements = new ArrayList<>();
    public final List<Field> mFields = new ArrayList<>();
    Block () { }

    public void addStatement(Statement statement) {
        mStatements.add(statement);
    }

    public void addField(Field field) {
        mFields.add(field);
    }
}
