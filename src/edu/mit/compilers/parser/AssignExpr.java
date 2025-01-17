package edu.mit.compilers.parser;


import edu.mit.compilers.visitor.ASTVisitor;

public class AssignExpr extends Node {
    public static final String ASSIGN = "=";
    public static final String PEQ = "+=";
    public static final String MEQ = "-=";
    public static final String INC = "++";
    public static final String DEC = "--";

    public final String assignExprOp;
    public Expr expr;
    AssignExpr(String increment) {
        assignExprOp = increment;
    }

    AssignExpr(String assignOp, Expr expr) {
        assignExprOp = assignOp;
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    @Override public String toString() {
        if (expr == null) return assignExprOp + "";
        return assignExprOp + " " + expr;
    }
}
