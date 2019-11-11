package edu.mit.compilers.inter;

import edu.mit.compilers.parser.*;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.ASTVisitor;

import java.util.Stack;

public class EliminateShadowingVisitor implements ASTVisitor {

    private final Stack<LocalTable> localTableStack = new Stack<>();

    EliminateShadowingVisitor(ProgramDescriptor programDescriptor) {
        for (MethodDescriptor methodDescriptor: programDescriptor.methodTable.values()) {
            methodDescriptor.getMethodBlock().accept(this);
        }
    }



    @Override
    public void visit(Program program) {

    }

    @Override
    public void visit(ImportDeclaration importDeclaration) {

    }

    @Override
    public void visit(FieldDeclaration fieldDeclaration) {

    }

    @Override
    public void visit(MethodDeclaration methodDeclaration) {

    }

    @Override
    public void visit(Expr expr) {
        switch (expr.exprType) {
            case Expr.MINUS:
            case Expr.NOT:
                expr.expr.accept(this);
                break;
            case Expr.LOC:
                expr.loc.accept(this);
                break;
            case Expr.METHOD_CALL:
                expr.methodCall.accept(this);
                break;
            case Expr.BIN_OP:
                expr.expr.accept(this);
                expr.binOpExpr.accept(this);
                break;
            case Expr.LIT:
                break;
            case Expr.LEN:
                expr.id.accept(this);
                break;

        }
    }

    @Override
    public void visit(Statement statement) {
        switch (statement.statementType) {
            case Statement.LOC_ASSIGN:
                statement.loc.accept(this);
                statement.assignExpr.accept(this);
                break;
            case Statement.METHOD_CALL:
                statement.methodCall.accept(this);
                break;
            case Statement.IF:
                statement.expr.accept(this);
                statement.ifBlock.accept(this);
                if (statement.elseBlock != null) statement.elseBlock.accept(this);
                break;
            case Statement.FOR:
                statement.id.accept(this);
                statement.initExpr.accept(this);
                statement.exitExpr.accept(this);
                statement.loc.accept(this);
                if (statement.expr != null) statement.expr.accept(this);
                statement.block.accept(this);
                break;
            case Statement.WHILE:
                statement.expr.accept(this);
                statement.block.accept(this);
                break;
            case Statement.RETURN:
                if (statement.expr != null) statement.expr.accept(this);
                break;
        }
    }

    @Override
    public void visit(AssignExpr assignExpr) {
        if (assignExpr.assignExprOp != null) assignExpr.accept(this);
    }

    @Override
    public void visit(BinOp binOp) {

    }

    @Override
    public void visit(Block block) {
        localTableStack.push(block.localTable);
        for (Statement statement: block.statements) {
            statement.accept(this);
        }
        localTableStack.pop();
    }

    @Override
    public void visit(DecLit decLit) {

    }

    @Override
    public void visit(HexLit hexLit) {

    }

    @Override
    public void visit(Id id) {
        id.setDeclarationScope(localTableStack.peek().getDeclarationScope(id.getName()));
    }

    @Override
    public void visit(IntLit intLit) {

    }

    @Override
    public void visit(Lit lit) {

    }

    @Override
    public void visit(Loc loc) {
        loc.id.accept(this);
        if (loc.expr != null) loc.expr.accept(this);
    }

    @Override
    public void visit(MethodCall methodCall) {
        for (Pair<Expr, StringLit> argument : methodCall.arguments) {
            if (argument.getKey() != null) {
                argument.getKey().accept(this);
            }
        }
    }

    @Override
    public void visit(StringLit stringLit) {

    }

    @Override
    public void visit(Type type) {

    }
}
