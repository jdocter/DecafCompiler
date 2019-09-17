package edu.mit.compilers.parser;

public class AssignExpr {
    public static final int ASSIGN = 0;
    public static final int PEQ = 1;
    public static final int MEQ = 2;
    public static final int INC = 3;
    public static final int DEC = 4;

    AssignExpr(String increment) {

    }

    AssignExpr(String assignOp, Expr expr) {

    }
}
