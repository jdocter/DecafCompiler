package edu.mit.compilers.parser;

import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class MethodCall extends Node {

    public final Id methodName;
    // every pair will contain one null item, purpose is to preserve order of arguments
    public final List<Pair<Expr,StringLit>> arguments = new ArrayList<>();

    MethodCall(Id id) {
        methodName = id;
    }
    public void addExpr(Expr expr) {
        arguments.add(new Pair<Expr, StringLit>(expr,null));
    }

    public void addStringLit(StringLit stringLit) {
        arguments.add(new Pair<Expr, StringLit>(null, stringLit));
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
