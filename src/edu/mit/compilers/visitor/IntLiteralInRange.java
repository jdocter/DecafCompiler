package edu.mit.compilers.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.LocalTable;
import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.ProgramDescriptor;
import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.inter.TypeDescriptor;
import edu.mit.compilers.parser.AssignExpr;
import edu.mit.compilers.parser.BinOp;
import edu.mit.compilers.parser.Block;
import edu.mit.compilers.parser.DecLit;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.FieldDeclaration;
import edu.mit.compilers.parser.HexLit;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.ImportDeclaration;
import edu.mit.compilers.parser.IntLit;
import edu.mit.compilers.parser.Lit;
import edu.mit.compilers.parser.Loc;
import edu.mit.compilers.parser.MethodCall;
import edu.mit.compilers.parser.MethodDeclaration;
import edu.mit.compilers.parser.Program;
import edu.mit.compilers.parser.Statement;
import edu.mit.compilers.parser.StringLit;
import edu.mit.compilers.parser.Type;
import edu.mit.compilers.util.Pair;


public class IntLiteralInRange implements SemanticChecker {

    private List<SemanticException> semanticExceptions = new ArrayList<>();
    private int numMinuses = 0;

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
    public void visit(BinOp binOp) {}

    @Override
    public void visit(Block block) {
        for (FieldDeclaration fieldDeclaration : block.fieldDeclarations) {
            fieldDeclaration.accept(this);
        }
        for (Statement statement : block.statements) {
            statement.accept(this);
        }
    }

    @Override
    public void visit(DecLit decLit) {} // unreachable

    @Override
    public void visit(Expr expr) {
        int beforeNumMinuses = numMinuses;
        switch (expr.exprType) {
            case Expr.METHOD_CALL:
                numMinuses = 0;

                expr.methodCall.accept(this);

                numMinuses = beforeNumMinuses;
                break;
            case Expr.MINUS:
                numMinuses += 1;
                expr.expr.accept(this);
                numMinuses -= 1;
                break;
            case Expr.NOT:
                numMinuses = 0;

                expr.expr.accept(this);

                numMinuses = beforeNumMinuses;
                break;
            case Expr.LOC:
                numMinuses = 0;

                expr.loc.accept(this);

                numMinuses = beforeNumMinuses;
                break;
            case Expr.BIN_OP:
                numMinuses = 0;

                for (Expr innerExpr : expr.binOpExprs) {
                    innerExpr.accept(this);
                }

                numMinuses = beforeNumMinuses;
                break;
            case Expr.LIT:
                expr.lit.accept(this);
                break;
            case Expr.LEN:
                break;
        }
    }

    @Override
    public void visit(FieldDeclaration fieldDeclaration) {
        for (Pair<Id, IntLit> arrayDeclaration : fieldDeclaration.fieldArrays) {
            IntLit intLit = arrayDeclaration.getValue();
            intLit.accept(this);
            // String equals is easier than catching another parse exception
            if (intLit.posString().equals("0")) {
                // Rule 4
                Id id = arrayDeclaration.getKey();
                semanticExceptions.add(new SemanticException(id.getLineNumber(),
                        "Array '" + id.getName() + "' must have size greater than 0."));
            } // impossible for intLit to be negative
        }
    }

    @Override
    public void visit(HexLit hexLit) {} // unreachable

    @Override
    public void visit(Id id) {}

    @Override
    public void visit(ImportDeclaration importDeclaration) {}

    @Override
    public void visit(IntLit intLit) {
        try {
            if (numMinuses % 2 == 0) {
                intLit.posInteger();
            } else {
                // negative version
                intLit.negInteger();
            }
        } catch (NumberFormatException e) {
            if (numMinuses % 2 == 0) {
                semanticExceptions.add(new SemanticException(intLit.getLineNumber(),
                        "integer literal not in range: " + intLit.posString()));
            } else {
                // negative version
                semanticExceptions.add(new SemanticException(intLit.getLineNumber(),
                        "integer literal not in range: " + intLit.negString()));
            }
        }
    }

    @Override
    public void visit(Lit lit) {
        switch (lit.litType) {
            case Lit.INT:
                lit.mIntLit.accept(this);
                break;
            case Lit.CHAR:
            case Lit.BOOL:
                break;
        }
    }

    @Override
    public void visit(Loc loc) {
        if (loc.expr != null) {
            loc.expr.accept(this);
        }
    }

    @Override
    public void visit(MethodCall methodCall) {
        for (Pair<Expr, StringLit> pair : methodCall.arguments) {
            Expr expr = pair.getKey();
            if (expr != null) {
                expr.accept(this);
            }
        }
    }

    @Override
    public void visit(MethodDeclaration methodDeclaration) {
        methodDeclaration.mBlock.accept(this);
    }

    @Override
    public void visit(Program program) {
        for (FieldDeclaration fieldDeclaration : program.fieldDeclarations) {
            fieldDeclaration.accept(this);
        }
        for (MethodDeclaration methodDeclaration : program.methodDeclarations) {
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
                statement.initExpr.accept(this);
                statement.exitExpr.accept(this);
                statement.loc.accept(this);
                if (statement.expr != null) {
                    statement.expr.accept(this);
                }
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
    public void visit(StringLit stringLit) {}

    @Override
    public void visit(Type type) {}

}
