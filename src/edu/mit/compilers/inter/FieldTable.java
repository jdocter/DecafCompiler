package edu.mit.compilers.inter;

import java.util.HashMap;
import java.util.List;

import edu.mit.compilers.parser.FieldDeclaration;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.IntLit;
import edu.mit.compilers.util.Pair;

public class FieldTable extends HashMap<String, FieldDescriptor> {

    public FieldTable(List<FieldDeclaration> fieldDeclarations, TypeTable typeTable) throws SemanticException {
        for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
            for (Id field : fieldDeclaration.fields) {
                if (this.containsKey(field.mId)) {
                    throw new SemanticException(field.getLineNumber(), "Identifier '" + field.mId + "' declared twice in the same scope.");
                }

                this.put(field.mId, new FieldDescriptor()); // TODO
            }

            for (Pair<Id, IntLit> fieldArray : fieldDeclaration.fieldArrays) {
                Id field = fieldArray.getKey();
                IntLit size = fieldArray.getValue();
                if (this.containsKey(field.mId)) {
                    throw new SemanticException(field.getLineNumber(), "Identifier '" + field.mId + "' declared twice in the same scope.");
                }

                this.put(field.mId, new FieldDescriptor()); // TODO
            }
        }
    }

}
