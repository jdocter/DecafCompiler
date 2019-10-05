package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Expr extends Node {
    public static final int LEN = 0;
    public static final int MINUS = 1;
    public static final int NOT = 2;
    public static final int LOC = 3;
    public static final int METHOD_CALL = 4;
    public static final int BIN_OP = 5;
    public static final int LIT = 6;

    public int exprType;
    public Expr expr;
    public final List<Expr> binOpExprs = new ArrayList<>();
    public final List<BinOp> binOps = new ArrayList<>();
    public Id id;
    public MethodCall methodCall;
    public Loc loc;
    public Lit lit;

    Expr(int exprType, Expr expr) {
        this.exprType = exprType;
        this.expr = expr;
    }

    Expr(Id id) {
        this.id = id;
        exprType = LEN;
    }

    Expr(MethodCall methodCall) {
        this.methodCall = methodCall;
        exprType = METHOD_CALL;
    }

    Expr(Loc loc) {
        this.loc = loc;
        exprType = LOC;
    }

    Expr(Lit lit) {
        this.lit = lit;
        exprType = LIT;
    }

    Expr(List<Expr> binOpExprs, List<BinOp> binOps) {
        this.binOpExprs.addAll(binOpExprs);
        this.binOps.addAll(binOps);
        exprType = BIN_OP;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
