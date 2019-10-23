package edu.mit.compilers.parser;

import edu.mit.compilers.visitor.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

public class Program extends Node {

    public final List<MethodDeclaration> methodDeclarations = new ArrayList<>();
    public final List<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    public final List<ImportDeclaration> importDeclarations = new ArrayList<>();

    Program() {

    }
    public void addMethod(MethodDeclaration method) {
        methodDeclarations.add(method);
    }
    public void addFieldDeclaration(FieldDeclaration fieldDeclaration) {
        fieldDeclarations.add(fieldDeclaration);
    }
    public void addImportDeclaration(ImportDeclaration importDeclaration) {
        importDeclarations.add(importDeclaration);
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }
    @Override public String toString() {
        return "Program [methodDeclarations=" + methodDeclarations + ", fieldDeclarations=" + fieldDeclarations
                + ", importDeclarations=" + importDeclarations + "]";
    }
}
