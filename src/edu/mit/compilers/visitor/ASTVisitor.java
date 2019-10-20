package edu.mit.compilers.visitor;

import edu.mit.compilers.parser.*;

public interface ASTVisitor {
    void visit(Program program);
    void visit(ImportDeclaration importDeclaration);
    void visit(FieldDeclaration fieldDeclaration);
    void visit(MethodDeclaration methodDeclaration);
    void visit(Expr expr);
    void visit(Statement statement);
    void visit(AssignExpr assignExpr);
    void visit(BinOp binOp);
    void visit(Block block);
    void visit(DecLit decLit);
    void visit(HexLit hexLit);
    void visit(Id id);
    void visit(IntLit intLit);
    void visit(Lit lit);
    void visit(Loc loc);
    void visit(MethodCall methodCall);
    void visit(StringLit stringLit);
    void visit(Type type);
}
