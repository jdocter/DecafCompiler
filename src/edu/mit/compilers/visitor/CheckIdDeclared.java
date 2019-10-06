package edu.mit.compilers.visitor;

import edu.mit.compilers.inter.LocalTable;
import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.parser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Semantic Check 2, 10
 * Checks that all Identifiers are declared before their use
 */
public class CheckIdDeclared implements SemanticChecker {

    private final Stack<LocalTable> localTableStack = new Stack<>();
    private final List<SemanticException> semanticExceptions = new ArrayList<>();

    public CheckIdDeclared() {

    }

    @Override
    public List<SemanticException> getSemanticExceptions() {
        return this.semanticExceptions;
    }

    @Override
    public void visit(AssignExpr assignExpr) {
        switch (assignExpr.assignExprOp) {
            case AssignExpr.ASSIGN:
                assignExpr.expr.accept(this);
                break;
            case AssignExpr.PEQ:
            case AssignExpr.MEQ:
            case AssignExpr.INC:
            case AssignExpr.DEC:
                break;
        }
    }

    @Override
    public void visit(BinOp binOp) { }

    @Override
    public void visit(Block block) {
        localTableStack.push(block.localTable);
        for (Statement statement: block.statements) {
            statement.accept(this);
        }
        localTableStack.pop();
    }

    @Override
    public void visit(DecLit decLit) { }

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
                for (Expr binOpExpr : expr.binOpExprs) {
                    binOpExpr.accept(this);
                }
                break;
            case Expr.LIT:
                break;
            case Expr.LEN:
                expr.id.accept(this);
                break;

        }
    }

    @Override
    public void visit(FieldDeclaration fieldDeclaration) { }

    @Override
    public void visit(HexLit hexLit) { }

    @Override
    public void visit(Id id) {
        if (!localTableStack.peek().isDeclared(id.getName())) {
//            System.out.println("HERE IS A LOCAL TABLE STACK's parents DURING AN EXCEPTION at " + id.getLineNumber());
//            System.out.println(localTableStack.peek().toString());
//            System.out.println(localTableStack.peek().parentTable.toString());
//            System.out.println(((LocalTable)localTableStack.peek().parentTable).parentTable.toString());
//            System.out.println("----");
            semanticExceptions.add(new SemanticException(id.getLineNumber(),"Identifier '" + id.getName()
                    + "' referenced before declaration"));
        }
    }

    @Override
    public void visit(ImportDeclaration importDeclaration) { }

    @Override
    public void visit(IntLit intLit) { }

    @Override
    public void visit(Lit lit) { }

    @Override
    public void visit(Loc loc) {
        loc.id.accept(this);
    }

    @Override
    public void visit(MethodCall methodCall) { }

    @Override
    public void visit(MethodDeclaration method) {
        method.mBlock.accept(this);
    }

    @Override
    public void visit(Program program) {
        for (MethodDeclaration methodDeclaration: program.methodDeclarations) {
            methodDeclaration.accept(this);
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
//                System.out.println("HERE IS A LOCAL TABLE STACK at " + statement.getLineNumber());
//                System.out.println(localTableStack.toString());
//                System.out.println("----");
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
    public void visit(StringLit stringLit) { }

    @Override
    public void visit(Type type) { }
}
