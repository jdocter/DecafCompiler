package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

public class Loc extends Node {

    public final Id mId;
    private Expr mExpr;

    Loc(Id id) {
            mId = id;
        }

    Loc (Id id, Expr expr) {
        mId = id;
        mExpr = expr;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
