package edu.mit.compilers.parser;

public class Expr {
    public static final int LEN = 0;
    public static final int MINUS = 1;
    public static final int NOT = 2;
    public static final int LOC = 3;
    public static final int METHOD_CALL = 4;
    public static final int BIN_OP = 5;
    public static final int LIT = 6;
    public static final int EXPR = 2;


    Expr(int pre, Expr expr) {

    }

    Expr(Id id) {

    }

    Expr(MethodCall methodCall) {

    }

    Expr(Loc loc) {

    }

    Expr(Lit lit) {

    }

    public void addExpr(BinOp binOp, Expr expr) {

    }
}
