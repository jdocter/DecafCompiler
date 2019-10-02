package edu.mit.compilers.parser;

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
    public static final int EXPR = 7;

    public int exprType;
    public Expr mExpr;
    public final List<Expr> mExprs = new ArrayList<>();
    public final List<BinOp> mBinOps = new ArrayList<>();
    public Id mId;
    public MethodCall mMethodCall;
    public Loc mLoc;
    public Lit mLit;

    Expr(int pre, Expr expr) {
        exprType = pre;
        mExpr = expr;
    }

    Expr(Id id) {
        mId = id;
        exprType = LEN;
    }

    Expr(MethodCall methodCall) {
        mMethodCall = methodCall;
        exprType = METHOD_CALL;
    }

    Expr(Loc loc) {
        mLoc = loc;
        exprType = LOC;
    }

    Expr(Lit lit) {
        mLit = lit;
        exprType = LIT;
    }

    public void addExpr(BinOp binOp, Expr expr) {
        exprType = BIN_OP;
        mExprs.add(expr);
        mBinOps.add(binOp);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
