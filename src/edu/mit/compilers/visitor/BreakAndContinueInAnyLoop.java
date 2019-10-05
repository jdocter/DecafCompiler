package edu.mit.compilers.visitor;

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

import java.util.ArrayList;
import java.util.List;

import edu.mit.compilers.inter.SemanticException;

public class BreakAndContinueInAnyLoop implements Visitor, SemanticChecker {
    /**
     * Break and continue occur inside of a for/while  (Semantic Rule 20)
     */

    private int loopDepth; //+ 1 when entering for or while, -1 when exiting
    private List<SemanticException> semanticExceptions = new ArrayList<>();

    @Override
    public void visit(Program program) {
        loopDepth = 0;
        for (MethodDeclaration methodDeclaration: program.methodDeclarations) {
            methodDeclaration.accept(this);
        }
    }

    @Override
    public void visit(Type type) {}

    @Override
    public void visit(StringLit stringLit) {}

    @Override
    public void visit(Statement statement) {
        switch (statement.statementType) {
            case Statement.LOC_ASSIGN:
            case Statement.METHOD_CALL:
            case Statement.RETURN:
                return;
            case Statement.IF:
                statement.ifBlock.accept(this);
                if (statement.elseBlock != null) statement.elseBlock.accept(this);
                break;
            case Statement.FOR:
            case Statement.WHILE:
                loopDepth++;
                statement.block.accept(this);
                loopDepth--;
                break;
            case Statement.BREAK:
                if (loopDepth == 0) {
                    semanticExceptions.add(new SemanticException(statement.getLineNumber(),
                            "'break' found outside of a 'for' or 'while'"));
                }
                break;
            case Statement.CONTINUE:
                if (loopDepth == 0) {
                    semanticExceptions.add(new SemanticException(statement.getLineNumber(),
                            "'continue' found outside of a 'for' or 'while'"));
                }
                break;
        }
    }

    @Override
    public void visit(MethodCall methodCall) {}

    @Override
    public void visit(MethodDeclaration method) {
        method.mBlock.accept(this);
    }

    @Override
    public void visit(Loc loc) {}

    @Override
    public void visit(Lit lit) {}

    @Override
    public void visit(IntLit intLit) {}

    @Override
    public void visit(ImportDeclaration importDeclaration) {}

    @Override
    public void visit(Id id) {}

    @Override
    public void visit(HexLit hexLit) {}

    @Override
    public void visit(FieldDeclaration fieldDeclaration) {}

    @Override
    public void visit(Expr expr) {}

    @Override
    public void visit(DecLit decLit) {}

    @Override
    public void visit(Block block) {
        for (Statement statement : block.statements) {
            statement.accept(this);
        }
    }

    @Override
    public void visit(BinOp binOp) {}

    @Override
    public void visit(AssignExpr assignExpr) {}

    @Override
    public List<SemanticException> getSemanticExceptions() {
        return this.semanticExceptions;
    }

}
