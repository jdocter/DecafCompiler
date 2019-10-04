package edu.mit.compilers.parser;


import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.visitor.SemanticChecker;
import edu.mit.compilers.visitor.Visitor;

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
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public void accept(SemanticChecker semanticChecker) throws SemanticException {
        semanticChecker.check(this);
    }
}
