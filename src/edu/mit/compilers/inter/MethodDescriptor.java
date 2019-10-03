package edu.mit.compilers.inter;


import edu.mit.compilers.parser.*;
import edu.mit.compilers.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

public class MethodDescriptor {

    private boolean foreign;
    private TypeDescriptor returnType = null;
    private final LocalTable localTable;
    private final Block block;

    MethodDescriptor(MethodDeclaration methodDeclaration, FieldTable globalFieldTable) throws SemanticException {
        if (methodDeclaration.returnType != null) {
            returnType = new TypeDescriptor(methodDeclaration.returnType);
        }
        block = methodDeclaration.mBlock;
        localTable = new LocalTable(block.mFields, globalFieldTable);
        localTable.putParams(methodDeclaration.params);

        buildLocalTable(block, localTable);
    }


    private void buildLocalTable(Block block, LocalTable parentTable) throws SemanticException {
        block.localTable = new LocalTable(block.mFields, parentTable);
        for (Statement statement: block.mStatements) {
            if (statement.mBlock != null) {
                buildLocalTable(statement.mBlock, block.localTable);
            }
            if (statement.mIfBlock != null) {
                buildLocalTable(statement.mIfBlock, block.localTable);
            }
            if (statement.mElseBlock != null) {
                buildLocalTable(statement.mElseBlock, block.localTable);
            }
        }
    }
}