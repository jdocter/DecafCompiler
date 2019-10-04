package edu.mit.compilers.parser;

import edu.mit.compilers.inter.LocalTable;
import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.visitor.SemanticChecker;
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
    public static final int EXPR = 7;

    public int exprType;
    public Expr expr;
    public final List<Expr> exprs = new ArrayList<>();
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

    public void addExpr(BinOp binOp, Expr expr) {
        exprType = BIN_OP;
        exprs.add(expr);
        binOps.add(binOp);
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
