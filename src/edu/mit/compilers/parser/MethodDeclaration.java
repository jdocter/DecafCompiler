package edu.mit.compilers.parser;

import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

public class MethodDeclaration extends Node {

    public final Type returnType;
    public final Id methodName;
    public final List<Pair<Type,Id>> params = new ArrayList<>();
    public Block mBlock;

    MethodDeclaration(Type type, Id id) {
        returnType = type; // may be null??
        methodName = id;
    }

    public void addParam(Type type, Id id) {
        params.add(new Pair<Type, Id>(type,id));
    }

    public void addBlock(Block block) {
        mBlock = block;
    }

    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
    }

    @Override public String toString() {
        return "MethodDeclaration [returnType=" + returnType + ", methodName=" + methodName + ", params=" + params
                + ", mBlock=" + mBlock + "]";
    }
}
