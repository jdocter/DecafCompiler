package edu.mit.compilers.inter;

import edu.mit.compilers.parser.FieldDeclaration;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.IntLit;
import edu.mit.compilers.parser.Type;
import edu.mit.compilers.util.Pair;

import java.util.HashMap;
import java.util.List;

public class LocalTable extends HashMap<String, LocalDescriptor> implements VariableTable {

    public final VariableTable parentTable;

    public static int counter = 1;
    private int UID;

    public LocalTable(List<FieldDeclaration> fieldDeclarations, VariableTable parentTable) throws SemanticException {
        this.UID = counter;
        counter++;
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

                this.put(field.getName(), new LocalDescriptor(new TypeDescriptor(fieldDeclaration.type, size.posInteger()))); // TODO
            }
        }
    }

    public void putParams(List<Pair<Type, Id>> params) throws SemanticException {
        for (Pair<Type, Id> param: params) {
            if (this.containsKey(param.getValue().getName())) {
                throw new SemanticException(param.getValue().getLineNumber(), "Identifier '" + param.getValue().getName() + "' declared twice in the same scope.");
            }
            this.put(param.getValue().getName(), new LocalDescriptor(new TypeDescriptor(param.getKey())));
        }
    }

    @Override
    public boolean isDeclared(String id) {
        return this.containsKey(id) || parentTable.isDeclared(id);
    }

    @Override
    public VariableDescriptor getDescriptor(String id) {
        if (this.containsKey(id)) {
            return this.get(id);
        }
        return parentTable.getDescriptor(id);
    }

    @Override
    public int getUID() {
        return UID;
    }
}
