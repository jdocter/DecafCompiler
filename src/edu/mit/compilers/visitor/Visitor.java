package edu.mit.compilers.visitor;

import edu.mit.compilers.parser.*;

public interface Visitor {
    void visit(Program program);
    void visit(Type type);
    void visit(StringLit stringLit);
    void visit(Statement statement);
    void visit(MethodCall methodCall);
    void visit(MethodDeclaration method);
    void visit(Loc loc);
    void visit(Lit lit);
    void visit(IntLit intLit);
    void visit(ImportDeclaration importDeclaration);
    void visit(Id id);
    void visit(HexLit hexLit);
    void visit(FieldDeclaration fieldDeclaration);
    void visit(Expr expr);
    void visit(DecLit decLit);
    void visit(Block block);
    void visit(BinOp binOp);
    void visit(AssignExpr assignExpr);
}
