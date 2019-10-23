package edu.mit.compilers.visitor;

import edu.mit.compilers.inter.ProgramDescriptor;
import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.parser.*;
import edu.mit.compilers.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class UniqueGlobalIds implements SemanticChecker {

    private final List<SemanticException> semanticExceptions = new ArrayList<>();

    private final ProgramDescriptor programDescriptor;

    public UniqueGlobalIds(ProgramDescriptor programDescriptor) {
        this.programDescriptor = programDescriptor;
    }
    @Override
    public List<SemanticException> getSemanticExceptions() {
        return this.semanticExceptions;
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
    public void visit(ImportDeclaration importDeclaration) {

    }

    @Override
    public void visit(FieldDeclaration fieldDeclaration) {
        for (Id fieldId : fieldDeclaration.fields) {
            if (programDescriptor.importTable.containsKey(fieldId.getName())) {
                semanticExceptions.add(new SemanticException(fieldId.getLineNumber(), "Identifier '"
                        + fieldId.getName() + "' cannot be declared twice in the same scope."));
            }
        }

        for (Pair<Id, IntLit> fieldArray : fieldDeclaration.fieldArrays) {
            if (programDescriptor.importTable.containsKey(fieldArray.getKey().getName())) {
                semanticExceptions.add(new SemanticException(fieldArray.getKey().getLineNumber(), "Identifier '"
                        + fieldArray.getKey().getName() + "' cannot be declared twice in the same scope."));
            }
        }

    }

    @Override
    public void visit(MethodDeclaration methodDeclaration) {
        if (programDescriptor.importTable.containsKey(methodDeclaration.methodName.getName())) {
            semanticExceptions.add(new SemanticException(methodDeclaration.methodName.getLineNumber(), "Identifier '"
                    + methodDeclaration.methodName.getName() + "' cannot be declared twice in the same scope."));
        }
        if (programDescriptor.fieldTable.containsKey(methodDeclaration.methodName.getName())) {
            semanticExceptions.add(new SemanticException(methodDeclaration.methodName.getLineNumber(), "Identifier '"
                    + methodDeclaration.methodName.getName() + "' cannot be declared twice in the same scope."));
        }
    }

    @Override
    public void visit(Expr expr) {

    }

    @Override
    public void visit(Statement statement) {

    }

    @Override
    public void visit(AssignExpr assignExpr) {

    }

    @Override
    public void visit(BinOp binOp) {

    }

    @Override
    public void visit(Block block) {

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

    }

    @Override
    public void visit(MethodCall methodCall) {

    }

    @Override
    public void visit(StringLit stringLit) {

    }

    @Override
    public void visit(Type type) {

    }
}
