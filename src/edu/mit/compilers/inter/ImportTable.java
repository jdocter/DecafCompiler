package edu.mit.compilers.inter;

import edu.mit.compilers.parser.ImportDeclaration;

import java.util.HashMap;
import java.util.List;

public class ImportTable extends HashMap<String, ImportDescriptor> {
    public ImportTable(List<ImportDeclaration> importDeclarations) throws SemanticException {
        for (ImportDeclaration importDeclaration : importDeclarations) {
            if (this.containsKey(importDeclaration.id.getName()))
                throw new SemanticException(importDeclaration.id.getLineNumber(), "Identifier '"
                        + importDeclaration.id.getName() + "' declared twice in the same scope.");

            put(importDeclaration.id.getName(), new ImportDescriptor(importDeclaration));
        }
    }

}
