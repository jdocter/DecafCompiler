package edu.mit.compilers.parser;

import java.util.ArrayList;
import java.util.List;

import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.SemanticChecker;
import edu.mit.compilers.visitor.Visitor;

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

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public void accept(SemanticChecker semanticChecker) throws SemanticException {
        semanticChecker.check(this);
    }
}
