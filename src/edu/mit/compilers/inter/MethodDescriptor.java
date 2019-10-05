package edu.mit.compilers.inter;


import edu.mit.compilers.parser.*;

public class MethodDescriptor {

    private final boolean returnVoid;
    private TypeDescriptor returnType = null;
    private final LocalTable localTable;
    private final Block block;

    MethodDescriptor(MethodDeclaration methodDeclaration, FieldTable globalFieldTable) throws SemanticException {
        if (methodDeclaration.returnType != null) {
            returnType = new TypeDescriptor(methodDeclaration.returnType);
            returnVoid = false;
        } else {
            returnVoid = true;
        }

        block = methodDeclaration.mBlock;
        localTable = new LocalTable(block.fieldDeclarations, globalFieldTable);
        localTable.putParams(methodDeclaration.params);

        buildLocalTable(block, localTable);
    }


    private void buildLocalTable(Block block, LocalTable parentTable) throws SemanticException {
        block.localTable = new LocalTable(block.fieldDeclarations, parentTable);
        for (Statement statement: block.statements) {
            for (Block innerBlock: statement.getBlocks())
                buildLocalTable(innerBlock, localTable);
        }
    }

//    private void attachLocalTable(Expr expr, LocalTable localTable) throws SemanticException {
//        expr.localTable = localTable;
//        for (Expr innerExpr: expr.exprs) attachLocalTable(innerExpr, localTable);
//        attachLocalTable(expr.expr, localTable);
//        attachLocalTable(expr.loc.expr, localTable);
//        // also method calls have exprs
//    }
}