package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

public class Loc extends Node {

    public final Id id;
    public Expr expr;

    Loc(Id id) {
            this.id = id;
        }

    Loc (Id id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override public String toString() {
        if (expr != null) {
            return id + "[" + expr + "]";
        } else {
            return "" + id;
        }
    }
}
