package edu.mit.compilers.inter;

import edu.mit.compilers.parser.ImportDeclaration;

import java.util.HashMap;
import java.util.List;

public class ImportTable extends HashMap<String, ImportDescriptor> {
    public ImportTable(List<ImportDeclaration> importDeclarations) throws SemanticException {
        for (ImportDeclaration importDeclaration : importDeclarations) {
            put(importDeclaration.mId.mId, new ImportDescriptor(importDeclaration));

        }
    }

}
