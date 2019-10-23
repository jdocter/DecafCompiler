package edu.mit.compilers.parser;

import java.util.ArrayList;
import java.util.List;

import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.ASTVisitor;

public class FieldDeclaration extends Node {
    public final Type type;
    public final List<Pair<Id,IntLit>> fieldArrays = new ArrayList<>();
    public final List<Id> fields = new ArrayList<>();

    FieldDeclaration(Type type, Id id) {
        this.type = type;
        fields.add(id);
    }
    FieldDeclaration(Type type, Id id, IntLit intLit) {
        this.type = type;
        fieldArrays.add(new Pair<Id, IntLit>(id,intLit));
    }

    public void addArg(Id id) {
        fields.add(id);
    }

    public void addArg(Id id, IntLit intLit) {
        fieldArrays.add(new Pair<Id, IntLit>(id,intLit));
    }

    @Override public String toString() {
        return "FieldDeclaration [type=" + type + ", fieldArrays=" + fieldArrays + ", fields=" + fields + "]";
    }
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
}
