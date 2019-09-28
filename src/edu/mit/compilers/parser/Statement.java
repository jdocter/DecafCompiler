package edu.mit.compilers.parser;


public class Statement {
    public static final int LOC_ASSIGN = 0;
    public static final int METHOD_CALL = 1;
    public static final int IF = 2;
    public static final int FOR = 3;
    public static final int WHILE = 4;
    public static final int RETURN = 5;
    public static final int BREAK = 6;
    public static final int CONTINUE = 7;

    Statement(Loc loc, AssignExpr assignExpr) {}
    Statement(MethodCall methodCall) {}
    Statement(Expr expr, Block ifBlock, Block elseBlock) {}
    Statement(Id id, Expr init, Expr exit, Loc loc, String inc, Block block) {}
    Statement(Id id, Expr init, Expr exit, Loc loc, String assignOp, Expr expr, Block block) {}
    Statement(Expr expr, Block block) {}
    Statement(int statementType) {}

    public void addReturnExpr(Expr expr) {

    }

}
