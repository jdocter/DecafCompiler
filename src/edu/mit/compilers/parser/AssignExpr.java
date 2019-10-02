package edu.mit.compilers.parser;


public class AssignExpr extends Node {
    public static final String ASSIGN = "=";
    public static final String PEQ = "+=";
    public static final String MEQ = "-=";
    public static final String INC = "++";
    public static final String DEC = "--";

    public final String assignExprType;
    public Expr mExpr;
    AssignExpr(String increment) {
        assignExprType = increment;
    }

    AssignExpr(String assignOp, Expr expr) {
        assignExprType = assignOp;
        mExpr = expr;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
