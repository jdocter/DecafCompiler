package edu.mit.compilers.parser;

import java.util.List;
import java.util.Optional;

import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.inter.TypeDescriptor;
import edu.mit.compilers.visitor.ASTVisitor;

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
    public BinOp binOp;
    public Expr binOpExpr;
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

    Expr(Expr left, Expr right, BinOp binOp) {
        this.expr = left;
        this.binOpExpr = right;
        this.binOp = binOp;
        exprType = BIN_OP;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    @Override public String toString() {
        switch (exprType) {
            case Expr.METHOD_CALL:
                return "METHOD_CALL[" + methodCall + "]";
            case Expr.MINUS:
                return "MINUS[" + expr + "]";
            case Expr.NOT:
                return "NOT[" + expr + "]";
            case Expr.LOC:
                return "" + loc;
            case Expr.BIN_OP:
                return "(" + expr + ") " + binOp + " (" + binOpExpr + ")";
            case Expr.LIT:
                return "" + lit;
            case Expr.LEN:
                return "len(" + id + ")";
            default:
                throw new RuntimeException("Unknown exprType: " + expr.exprType);
        }
    }

    public static Expr makeTrueExpr() {
        return new Expr(new Lit(true));
    }

    public static Expr makeFalseExpr() {
        return new Expr(new Lit(false));
    }
}
