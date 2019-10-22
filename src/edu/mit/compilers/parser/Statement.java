package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.Visitor;
import java.util.ArrayList;
import java.util.List;

public class Statement extends Node {
    public static final int LOC_ASSIGN = 0;
    public static final int METHOD_CALL = 1;
    public static final int IF = 2;
    public static final int FOR = 3;
    public static final int WHILE = 4;
    public static final int RETURN = 5;
    public static final int BREAK = 6;
    public static final int CONTINUE = 7;

    public static final String INC = "++";
    public static final String DEC = "--";
    public static final String MEQ = "-=";
    public static final String PEQ = "+=";

    // for private fields, if statement type has unique private field use field with prefix "m"
    // otherwise use descriptive fields
    public final int statementType;
    public Loc loc;
    public AssignExpr assignExpr;
    public Id id;
    public Statement initAssignment; // redundant info for FOR loop
    public Statement updateAssignment;
    public Expr initExpr;
    public Expr exitExpr;
    public Expr expr;
    public Block ifBlock;
    public Block elseBlock;
    public Block block;
    public MethodCall methodCall;
    public String assignOp;


    Statement(Loc loc, AssignExpr assignExpr) {
        statementType = LOC_ASSIGN;
        this.assignExpr = assignExpr;
        this.loc = loc;
    }
    Statement(MethodCall methodCall) {
        statementType = METHOD_CALL;
        this.methodCall = methodCall;
    }
    Statement(Expr expr, Block ifBlock, Block elseBlock) {
        // else block may be null
        statementType = IF;
        this.expr = expr;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }
    Statement(Id id, Expr initExpr, Expr exitExpr, Loc loc, String inc, Block block) {
        statementType = FOR;
        this.id = id;
        this.initExpr = initExpr;
        this.exitExpr = exitExpr;
        this.loc = loc;
        this.assignOp = inc;
        this.block = block;
        this.initAssignment = new Statement(new Loc(id), new AssignExpr(AssignExpr.ASSIGN, initExpr));
        this.updateAssignment = new Statement(loc, new AssignExpr(inc));
    }
    Statement(Id id, Expr initExpr, Expr exitExpr, Loc loc, String assignOp, Expr expr, Block block) {
        statementType = FOR;
        this.id = id;
        this.initExpr = initExpr;
        this.exitExpr = exitExpr;
        this.loc = loc;
        this.assignOp = assignOp;
        this.expr = expr;
        this.block = block;
        this.initAssignment = new Statement(new Loc(id), new AssignExpr(AssignExpr.ASSIGN, initExpr));
        this.updateAssignment = new Statement(loc, new AssignExpr(assignOp, expr));
    }
    Statement(Expr expr, Block block) {
        statementType = WHILE;
        this.expr = expr;
        this.block = block;
    }

    Statement(int statementType) {
        this.statementType = statementType;
    }

    public void addReturnExpr(Expr expr) {
        this.expr = expr;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public void setLineNumber(int line) {
        super.setLineNumber(line);
        if (this.initAssignment != null) {
            this.initAssignment.setLineNumber(line);
        }
        if (this.updateAssignment != null) {
            this.updateAssignment.setLineNumber(line);
        }
    }
}
