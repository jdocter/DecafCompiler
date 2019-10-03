package edu.mit.compilers.parser;


import edu.mit.compilers.visitor.Visitor;

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
    public final int mStatementType;
    public Loc mLoc;
    public AssignExpr mAssignExpr;
    public Id mId;
    public Expr mInitExpr;
    public Expr mExitExpr;
    public Expr mExpr;
    public Block mIfBlock;
    public Block mElseBlock;
    public Block mBlock;
    public MethodCall mMethodCall;
    public String mInc;
    public String mAssignOp;


    Statement(Loc loc, AssignExpr assignExpr) {
        mStatementType = LOC_ASSIGN;
        mAssignExpr = assignExpr;
        mLoc = loc;
    }
    Statement(MethodCall methodCall) {
        mStatementType = METHOD_CALL;
        mMethodCall = methodCall;
    }
    Statement(Expr expr, Block ifBlock, Block elseBlock) {
        // else block may be null
        mStatementType = IF;
        mExpr = expr;
        mIfBlock = ifBlock;
        mElseBlock = elseBlock;
    }
    Statement(Id id, Expr init, Expr exit, Loc loc, String inc, Block block) {
        mStatementType = FOR;
        mId = id;
        mInitExpr = init;
        mExitExpr = exit;
        mLoc = loc;
        mInc = inc;
        mBlock = block;
    }
    Statement(Id id, Expr init, Expr exit, Loc loc, String assignOp, Expr expr, Block block) {
        mStatementType = FOR;
        mId = id;
        mInitExpr = init;
        mExitExpr = exit;
        mLoc = loc;
        mAssignOp = assignOp;
        mExpr = expr;
        mBlock = block;
    }
    Statement(Expr expr, Block block) {
        mStatementType = WHILE;
        mExpr = expr;
        mBlock = block;
    }
    Statement(int statementType) {
        mStatementType = statementType;
    }

    public void addReturnExpr(Expr expr) {
        mExpr = expr;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

}
