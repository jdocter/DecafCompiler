package edu.mit.compilers.inter;

import java.util.HashMap;
import java.util.List;

import edu.mit.compilers.parser.FieldDeclaration;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.IntLit;
import edu.mit.compilers.util.Pair;


public class FieldTable extends HashMap<String, FieldDescriptor> implements VariableTable {

    public FieldTable(List<FieldDeclaration> fieldDeclarations) throws SemanticException {
        for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
            for (Id field : fieldDeclaration.fields) {
                if (this.containsKey(field.getName())) {
                    throw new SemanticException(field.getLineNumber(), "Identifier '" + field.getName() + "' declared twice in the same scope.");
                }

                this.put(field.getName(), new FieldDescriptor(new TypeDescriptor(fieldDeclaration.type))); // TODO
            }

            for (Pair<Id, IntLit> fieldArray : fieldDeclaration.fieldArrays) {
                Id field = fieldArray.getKey();
                IntLit size = fieldArray.getValue();
                if (this.containsKey(field.getName())) {
                    throw new SemanticException(field.getLineNumber(), "Identifier '" + field.getName() + "' declared twice in the same scope.");
                }

                this.put(field.getName(), new FieldDescriptor(new TypeDescriptor(fieldDeclaration.type, size.integer()))); // TODO
            }
        }
    }

    @Override
    public boolean isDeclared(String id) {
        return this.containsKey(id);
    }

    @Override
    public VariableDescriptor getDescriptor(String id) {
        return this.get(id);
    }
}
