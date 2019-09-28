package edu.mit.compilers.parser;


public class AssignExpr extends Node {
    public static final String ASSIGN = "=";
    public static final String PEQ = "+=";
    public static final String MEQ = "-=";
    public static final String INC = "++";
    public static final String DEC = "--";

    public final String type;
    public Expr mExpr;
    AssignExpr(String increment) {
        type = increment;
    }

    AssignExpr(String assignOp, Expr expr) {
        type = assignOp;
        mExpr = expr;
    }
}
