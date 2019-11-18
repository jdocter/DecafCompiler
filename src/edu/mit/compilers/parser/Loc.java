package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.ASTVisitor;

public class Loc extends Node {

    public final Id id;
    public Expr expr;

    Loc(Id id) {
            this.id = id;
        }

    Loc (Id id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    @Override public String toString() {
        if (expr != null) {
            return id + "[" + expr + "]";
        } else {
            return "" + id;
        }
    }

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expr == null) ? 0 : expr.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Loc)) {
            return false;
        }
        Loc other = (Loc) obj;
        if (expr == null) {
            if (other.expr != null) {
                return false;
            }
        } else if (!expr.equals(other.expr)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
