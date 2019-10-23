package edu.mit.compilers.inter;


import java.util.ArrayList;
import java.util.List;

import edu.mit.compilers.parser.*;
import edu.mit.compilers.util.Pair;

public class MethodDescriptor {

    private final boolean returnVoid;
    private TypeDescriptor returnType = null;
    private List<Pair<Type, Id>> params = new ArrayList<>();

    private final LocalTable localTable;
    private final Block block;
    public final int declarationLineNumber;

    MethodDescriptor(MethodDeclaration methodDeclaration, FieldTable globalFieldTable) throws SemanticException {
        if (methodDeclaration.returnType != null) {
            returnType = new TypeDescriptor(methodDeclaration.returnType);
            returnVoid = false;
        } else {
            returnVoid = true;
        }
        
        declarationLineNumber = methodDeclaration.methodName.getLineNumber();
        block = methodDeclaration.mBlock;
        localTable = new LocalTable(block.fieldDeclarations, globalFieldTable);

        this.params = methodDeclaration.params;
        localTable.putParams(methodDeclaration.params);

        buildLocalTable(block, localTable);
    }


    static private void buildLocalTable(Block block, LocalTable parentTable) throws SemanticException {
        block.localTable = new LocalTable(block.fieldDeclarations, parentTable);
        for (Statement statement: block.statements) {
            switch (statement.statementType) {
                case Statement.LOC_ASSIGN:
                case Statement.METHOD_CALL:
                case Statement.RETURN:
                case Statement.BREAK:
                case Statement.CONTINUE:
                    break;
                case Statement.IF:
                    buildLocalTable(statement.ifBlock, block.localTable);
                    if (statement.elseBlock != null) buildLocalTable(statement.elseBlock, block.localTable);
                    break;
                case Statement.FOR:
                case Statement.WHILE:
                    buildLocalTable(statement.block, block.localTable);
                    break;
            }
        }
    }


    public boolean isVoid() {
        return returnVoid;
    }

    public TypeDescriptor getReturnType() {
        return returnType;
    }

    public List<Pair<Type, Id>> getParams() {
        return this.params; // in order
    }

    public Block getMethodBlock() { return block; }

//    private void attachLocalTable(Expr expr, LocalTable localTable) throws SemanticException {
//        expr.localTable = localTable;
//        for (Expr innerExpr: expr.exprs) attachLocalTable(innerExpr, localTable);
//        attachLocalTable(expr.expr, localTable);
//        attachLocalTable(expr.loc.expr, localTable);
//        // also method calls have exprs
//    }
}
