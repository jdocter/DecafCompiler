package edu.mit.compilers.parser;

import java.util.Map;

import edu.mit.compilers.visitor.Visitor;

public class BinOp extends Node {
    public static final String AND = "&&";
    public static final String OR = "||";
    public static final String EQ = "==";
    public static final String NEQ = "!=";
    public static final String LT = "<";
    public static final String GT = ">";
    public static final String LEQ = "<=";
    public static final String GEQ = ">=";
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MUL = "*";
    public static final String DIV = "/";
    public static final String MOD = "%";

    private static Map<String, Integer> precedence = Map.ofEntries(
            Map.entry(BinOp.MUL, 1),
            Map.entry(BinOp.DIV, 1),
            Map.entry(BinOp.MOD, 1),
            Map.entry(BinOp.PLUS, 2),
            Map.entry(BinOp.MINUS, 2),
            Map.entry(BinOp.LT, 3),
            Map.entry(BinOp.GT, 3),
            Map.entry(BinOp.LEQ, 3),
            Map.entry(BinOp.GEQ, 3),
            Map.entry(BinOp.EQ, 4),
            Map.entry(BinOp.NEQ, 4),
            Map.entry(BinOp.AND, 5),
            Map.entry(BinOp.OR, 6)
            );

    public final String binOp;
    BinOp(String s) {
        binOp = s;
    }

    @Override public String toString() {
        return binOp;
    }

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((binOp == null) ? 0 : binOp.hashCode());
        return result;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BinOp)) {
            return false;
        }
        BinOp other = (BinOp) obj;
        if (binOp == null) {
            if (other.binOp != null) {
                return false;
            }
        } else if (!binOp.equals(other.binOp)) {
            return false;
        }
        return true;
    }

    public static BinOp weakerPrecedence(BinOp left, BinOp right) {
        if (precedence.get(right.binOp) > precedence.get(left.binOp)) {
            return right;
        } else if (precedence.get(left.binOp) > precedence.get(right.binOp)) {
            return left;
        } else {
            return right;
        }
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
