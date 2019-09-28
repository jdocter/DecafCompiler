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

    public int type;
    public Expr mExpr;
    public final List<Expr> mExprs = new ArrayList<>();
    public final List<BinOp> mBinOps = new ArrayList<>();
    public Id mId;
    public MethodCall mMethodCall;
    public Loc mLoc;
    public Lit mLit;

    Expr(int pre, Expr expr) {
        type = pre;
    }

    Expr(Id id) {
        mId = id;
        type = LEN;
    }

    Expr(MethodCall methodCall) {
        mMethodCall = methodCall;
        type = METHOD_CALL;
    }

    Expr(Loc loc) {
        mLoc = loc;
        type = LOC;
    }

    Expr(Lit lit) {
        mLit = lit;
        type = LIT;
    }

    public void addExpr(BinOp binOp, Expr expr) {
        type = BIN_OP;
        mExprs.add(expr);
        mBinOps.add(binOp);
    }
}
