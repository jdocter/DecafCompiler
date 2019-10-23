package edu.mit.compilers.visitor;

import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.ProgramDescriptor;
import edu.mit.compilers.parser.*;

public class PopulateMethodStackOffsets implements ASTVisitor {

    
    private final MethodDescriptor methodDescriptor;
    private int count = 0;

    public PopulateMethodStackOffsets(MethodDescriptor methodDescriptor) {
        this.methodDescriptor = methodDescriptor;
    }

    public void populate() {
        methodDescriptor.getMethodBlock().accept(this);
    }
    @Override
    public void visit(Program program) {

    }

    @Override
    public void visit(ImportDeclaration importDeclaration) {

    }

    @Override
    public void visit(FieldDeclaration fieldDeclaration) {

    }

    @Override
    public void visit(MethodDeclaration methodDeclaration) {

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
