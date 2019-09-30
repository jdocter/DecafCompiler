package edu.mit.compilers.parser;

import java.util.ArrayList;
import java.util.List;

public class Program extends Node {

    public final List<Method> methods = new ArrayList<>();
    public final List<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    public final List<ImportDeclaration> importDeclarations = new ArrayList<>();
    Program() {

    }
    public void addMethod(Method method) {
        methods.add(method);
    }
    public void addFieldDeclaration(FieldDeclaration fieldDeclaration) {
        fieldDeclarations.add(fieldDeclaration);
    }
    public void addImportDeclaration(ImportDeclaration importDeclaration) {
        importDeclarations.add(importDeclaration);
    }
}
