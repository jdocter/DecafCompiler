package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.ASTVisitor;

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
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    @Override public String toString() {
        if (expr != null) {
            return id + "[" + expr + "]";
        } else {
            return "" + id;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Loc)) return false;
        Loc that = (Loc) obj;
        return expr == null && that.expr == null && this.id.equals(that.id);
    }
}
