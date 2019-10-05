package edu.mit.compilers.visitor;

import edu.mit.compilers.inter.LocalTable;
import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.parser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Semantic Check 2
 * Checks that all Identifiers are declared before their use
 */
public class CheckIdDefined implements Visitor {

    private final Stack<LocalTable> localTableStack = new Stack<>();
    private final List<SemanticException> semanticExceptions = new ArrayList<>();

    @Override
    public void visit(Program program) {
        for (MethodDeclaration methodDeclaration: program.methodDeclarations) {
            methodDeclaration.accept(this);
        }
    }

    @Override
    public void visit(Type type) {

    }

    @Override
    public void visit(StringLit stringLit) {

    }

    @Override
    public void visit(Statement statement) {

        if (!localTableStack.peek().isDeclared(statement.id.getName()))
            semanticExceptions.add(new SemanticException(statement.id.getLineNumber(),"Identifier '" + statement.id.getName()
                    + "' referenced before assignment"));
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
                statement.expr.accept(this);
                break;
            case Statement.WHILE:
                statement.expr.accept(this);
                statement.block.accept(this);
                break;
            case Statement.RETURN:
                if (statement.expr != null) statement.expr.accept(this);
                break;
        }
        for (Block block: statement.getBlocks()) {
            block.accept(this);
        }
    }

    @Override
    public void visit(MethodCall methodCall) {

    }

    @Override
    public void visit(MethodDeclaration method) {
        method.mBlock.accept(this);
    }

    @Override
    public void visit(Loc loc) {
        
    }

    @Override
    public void visit(Lit lit) {

    }

    @Override
    public void visit(IntLit intLit) {

    }

    @Override
    public void visit(ImportDeclaration importDeclaration) {

    }

    @Override
    public void visit(Id id) {

    }

    @Override
    public void visit(HexLit hexLit) {

    }

    @Override
    public void visit(FieldDeclaration fieldDeclaration) {

    }

    @Override
    public void visit(Expr expr) {

    }

    @Override
    public void visit(DecLit decLit) {

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
    public void visit(BinOp binOp) {

    }

    @Override
    public void visit(AssignExpr assignExpr) {

    }
}
