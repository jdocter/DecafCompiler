package edu.mit.compilers.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.inter.TypeDescriptor;
import edu.mit.compilers.util.Pair;
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

    public Set<Id> getIds() {
        Set<Id> ids = new HashSet<>();
        switch (exprType) {
            case Expr.METHOD_CALL:
                for (Pair<Expr, StringLit> argument : methodCall.arguments) {
                    if (argument.getKey() != null) {
                        ids.addAll(argument.getKey().getIds());
                    }
                }
                break;
            case Expr.MINUS:
            case Expr.NOT:
                ids.addAll(expr.getIds());
                break;
            case Expr.LOC:
                ids.add(loc.id);
                if (loc.expr != null) ids.addAll(loc.expr.getIds());
                break;
            case Expr.BIN_OP:
                ids.addAll(expr.getIds());
                ids.addAll(binOpExpr.getIds());
                break;
            case Expr.LIT:
                break;
            case Expr.LEN:
                ids.add(id);
            default:
                throw new RuntimeException("Unknown exprType: " + expr.exprType);
        }
        return ids;
    }

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((binOp == null) ? 0 : binOp.hashCode());
        result = prime * result + ((binOpExpr == null) ? 0 : binOpExpr.hashCode());
        result = prime * result + ((expr == null) ? 0 : expr.hashCode());
        result = prime * result + exprType;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lit == null) ? 0 : lit.hashCode());
        result = prime * result + ((loc == null) ? 0 : loc.hashCode());
        result = prime * result + ((methodCall == null) ? 0 : methodCall.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Expr)) return false;
        Expr that = (Expr) obj;
        if (this.exprType != that.exprType) return false;
        switch (exprType) {
            case Expr.METHOD_CALL:
                return false;
            case Expr.MINUS:
            case Expr.NOT:
                return this.expr.equals(that.expr);
            case Expr.LOC:
                return loc.equals(that.loc);
            case Expr.BIN_OP:
                return binOp.equals(that.binOp) && this.expr.equals(that.expr) && this.binOpExpr.equals(that.binOpExpr);
            case Expr.LIT:
                return this.lit.equals(that.lit);
            case Expr.LEN:
                return this.id.equals(that.id);
            default:
                throw new RuntimeException("Unknown exprType: " + expr.exprType);
        }
    }
}
