package edu.mit.compilers.inter;

import edu.mit.compilers.parser.*;
import edu.mit.compilers.semantics.StringLitCollector;

import java.util.List;

/**
 * Symbol Table for the whole program.
 *
 * You can get a field or a method.
 *
 * You must ask for your symbol from the tightest possible scope (i.e., when
 * within a method, you cannot call getField within the ProgramTable, you must
 * call it on the MethodTable.).
 *
 */
public class ProgramDescriptor {

    public FieldTable fieldTable;
    public MethodTable methodTable;
    public ImportTable importTable;
    public List<StringLit> stringLits;

    public ProgramDescriptor(Program p) throws SemanticException {
        fieldTable = new FieldTable(p.fieldDeclarations);
        methodTable = new MethodTable(p.methodDeclarations, fieldTable);
        importTable = new ImportTable(p.importDeclarations);
        stringLits = new StringLitCollector(p).getStringLits();
        for (int i = 0; i < stringLits.size(); i++) {
            stringLits.get(i).setLabel("_string_" + i);
        }
    }

    public void typeCheck(Program p) throws SemanticException {
        throw new UnsupportedOperationException("No Implementation");
    }

    public FieldDescriptor getField(String f) {
        throw new UnsupportedOperationException("No Implementation");
    }

    public MethodDescriptor getMethod(String m) {
        throw new UnsupportedOperationException("No Implementation");
    }
}
