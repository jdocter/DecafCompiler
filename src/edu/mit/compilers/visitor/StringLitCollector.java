package edu.mit.compilers.visitor;

import edu.mit.compilers.parser.*;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.ASTVisitor;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

public class StringLitCollector implements ASTVisitor {
    private List<StringLit> stringLits = new ArrayList<>();

    public StringLitCollector(Program program) {
        program.accept(this);
    }

    public List<StringLit> getStringLits() {
        return stringLits;
    }

    @Override
    public void visit(Program program) {
        for (MethodDeclaration methodDeclaration: program.methodDeclarations) {
            methodDeclaration.accept(this);
        }
    }

    @Override
    public void visit(ImportDeclaration importDeclaration) {

    }

    @Override
    public void visit(FieldDeclaration fieldDeclaration) {

    }

    @Override
    public void visit(MethodDeclaration methodDeclaration) {
        methodDeclaration.mBlock.accept(this);
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
            case Expr.LEN:
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
                statement.initExpr.accept(this);
                statement.exitExpr.accept(this);
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
        if (assignExpr.expr != null ) assignExpr.expr.accept(this);

    }

    @Override
    public void visit(BinOp binOp) {

    }

    @Override
    public void visit(Block block) {
        for (Statement statement: block.statements) {
            statement.accept(this);
        }
    }

    @Override
    public void visit(DecLit decLit) {

    }

    @Override
    public void visit(HexLit hexLit) {

    }

    @Override
    public void visit(Id id) {

    }

    @Override
    public void visit(IntLit intLit) {

    }

    @Override
    public void visit(Lit lit) {

    }

    @Override
    public void visit(Loc loc) {
        if (loc.expr != null) {
            loc.expr.accept(this);
        }
    }

    @Override
    public void visit(MethodCall methodCall) {
        for (Pair<Expr,StringLit> arg: methodCall.arguments) {
            if (arg.getKey() != null) {
                arg.getKey().accept(this);
            } else {
                stringLits.add(arg.getValue());
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
