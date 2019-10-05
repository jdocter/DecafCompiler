package edu.mit.compilers.inter;

import edu.mit.compilers.parser.FieldDeclaration;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.IntLit;
import edu.mit.compilers.parser.Type;
import edu.mit.compilers.util.Pair;

import java.util.HashMap;
import java.util.List;

public class LocalTable extends HashMap<String, LocalDescriptor> implements VariableTable {

    private final VariableTable parentTable;

    public LocalTable(List<FieldDeclaration> fieldDeclarations, VariableTable parentTable) throws SemanticException {
        this.parentTable = parentTable;
        for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
            for (Id field : fieldDeclaration.fields) {
                if (this.containsKey(field.getName())) {
                    throw new SemanticException(field.getLineNumber(), "Identifier '" + field.getName() + "' declared twice in the same scope.");
                }

                this.put(field.getName(), new LocalDescriptor(new TypeDescriptor(fieldDeclaration.type)));
            }

            for (Pair<Id, IntLit> fieldArray : fieldDeclaration.fieldArrays) {
                Id field = fieldArray.getKey();
                IntLit size = fieldArray.getValue();
                if (this.containsKey(field.getName())) {
                    throw new SemanticException(field.getLineNumber(), "Identifier '" + field.getName() + "' declared twice in the same scope.");
                }

                this.put(field.getName(), new LocalDescriptor(new TypeDescriptor(fieldDeclaration.type, size.integer()))); // TODO
            }
        }
    }

    public void putParams(List<Pair<Type, Id>> params) throws SemanticException {
        for (Pair<Type, Id> param: params) {
            this.put(param.getValue().getName(), new LocalDescriptor(new TypeDescriptor(param.getKey())));
        }
    }

    @Override
    public boolean contains(String id) {
        return this.containsKey(id) || parentTable.contains(id);
    }
}
