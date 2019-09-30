package edu.mit.compilers.parser;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Field extends Node {
    public final Type mType;
    public final List<Pair<Id,IntLit>> fieldArrays = new ArrayList<>();
    public final List<Id> fields = new ArrayList<>();

    Field(Type type, Id id) {
        mType = type;
        fields.add(id);
    }
    Field(Type type, Id id, IntLit intLit) {
        mType = type;
        fieldArrays.add(new Pair(id,intLit));
    }

    public void addArg(Id id) {
        fields.add(id);
    }

    public void addArg(Id id, IntLit intLit) {
        fieldArrays.add(new Pair(id,intLit));
    }
}
