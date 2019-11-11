package edu.mit.compilers.parser;

import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.inter.FieldDescriptor;
import edu.mit.compilers.inter.TypeDescriptor;
import edu.mit.compilers.inter.VariableDescriptor;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.visitor.ASTVisitor;

public class Id extends Node {
    private final String id;
    private int declarationScope;

    Id(String id) {
        this.id = id;
    }

    public String getName() {
        return id;
    }

    public void setDeclarationScope(int declarationScope) {
        this.declarationScope = declarationScope;
    }

    public int getDeclarationScope() {
        return this.declarationScope;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    @Override public String toString() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode() + declarationScope;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Id && id.equals(((Id) obj).id) && declarationScope == ((Id) obj).declarationScope;
    }
}
