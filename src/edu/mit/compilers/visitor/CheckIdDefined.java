package edu.mit.compilers.visitor;

import edu.mit.compilers.inter.LocalTable;
import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.parser.*;

import java.util.Stack;

/**
 * Semantic Check 2
 * Checks that all Identifiers are declared before their use
 */
public class CheckIdDefined implements SemanticChecker {

    private final Stack<LocalTable> localTableStack = new Stack<>();

    @Override
    public void check(Program program) throws SemanticException {
        for (MethodDeclaration methodDeclaration: program.methodDeclarations) {
            methodDeclaration.accept(this);
        }
    }

    @Override
    public void check(Type type) throws SemanticException {

    }

    @Override
    public void check(StringLit stringLit) throws SemanticException {

    }

    @Override
    public void check(Statement statement) throws SemanticException {

        if (!localTableStack.peek().contains(statement.id.getName()))
            throw new SemanticException(statement.id.getLineNumber(),"Identifier '" + statement.id.getName()
                    + "' referenced before assignment");
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
    public void check(MethodCall methodCall) throws SemanticException {

    }

    @Override
    public void check(MethodDeclaration method) throws SemanticException {
        method.mBlock.accept(this);
    }

    @Override
    public void check(Loc loc) throws SemanticException {

    }

    @Override
    public void check(Lit lit) throws SemanticException {

    }

    @Override
    public void check(IntLit intLit) throws SemanticException {

    }

    @Override
    public void check(ImportDeclaration importDeclaration) throws SemanticException {

    }

    @Override
    public void check(Id id) throws SemanticException {

    }

    @Override
    public void check(HexLit hexLit) throws SemanticException {

    }

    @Override
    public void check(FieldDeclaration fieldDeclaration) throws SemanticException {

    }

    @Override
    public void check(Expr expr) throws SemanticException {

    }

    @Override
    public void check(DecLit decLit) throws SemanticException {

    }

    @Override
    public void check(Block block) throws SemanticException {
        localTableStack.push(block.localTable);
        for (Statement statement: block.statements) {
            statement.accept(this);
        }
        localTableStack.pop();
    }

    @Override
    public void check(BinOp binOp) throws SemanticException {

    }

    @Override
    public void check(AssignExpr assignExpr) throws SemanticException {

    }

}
