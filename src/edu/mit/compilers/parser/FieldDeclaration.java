package edu.mit.compilers.parser;

import java.util.ArrayList;
import java.util.List;

import edu.mit.compilers.util.Pair;

public class FieldDeclaration extends Node {
    public final Type mType;
    public final List<Pair<Id,IntLit>> fieldArrays = new ArrayList<>();
    public final List<Id> fields = new ArrayList<>();

    FieldDeclaration(Type type, Id id) {
        mType = type;
        fields.add(id);
    }
    FieldDeclaration(Type type, Id id, IntLit intLit) {
        mType = type;
        fieldArrays.add(new Pair<Id, IntLit>(id,intLit));
    }

    public void addArg(Id id) {
        fields.add(id);
    }

    public void addArg(Id id, IntLit intLit) {
        fieldArrays.add(new Pair<Id, IntLit>(id,intLit));
    }
}
