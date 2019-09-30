package edu.mit.compilers.parser;

import javafx.util.Pair;

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
        arguments.add(new Pair(expr,null));
    }

    public void addStringLit(StringLit stringLit) {
        arguments.add(new Pair(null, stringLit));
    }
}
