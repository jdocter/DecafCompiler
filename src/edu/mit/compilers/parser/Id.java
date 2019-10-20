package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

public class Id extends Node {
    private final String id;
    public Id(String id) {
        this.id = id;
    }

    public String getName() {
        return id;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override public String toString() {
        return id;
    }
}
