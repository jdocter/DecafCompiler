package edu.mit.compilers.visitor;

import edu.mit.compilers.parser.*;

public interface Visitor {
    void visit(AssignExpr assignExpr);
    void visit(BinOp binOp);
    void visit(Block block);
    void visit(DecLit decLit);
    void visit(Expr expr);
    void visit(FieldDeclaration fieldDeclaration);
    void visit(HexLit hexLit);
    void visit(Id id);
    void visit(ImportDeclaration importDeclaration);
    void visit(IntLit intLit);
    void visit(Lit lit);
    void visit(Loc loc);
    void visit(MethodCall methodCall);
    void visit(MethodDeclaration method);
    void visit(Program program);
    void visit(Statement statement);
    void visit(StringLit stringLit);
    void visit(Type type);
}
