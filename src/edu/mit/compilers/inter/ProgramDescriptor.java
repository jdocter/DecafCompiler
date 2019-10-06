package edu.mit.compilers.inter;

import edu.mit.compilers.parser.*;

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

    public ProgramDescriptor(Program p) throws SemanticException {
        fieldTable = new FieldTable(p.fieldDeclarations);
        methodTable = new MethodTable(p.methodDeclarations, fieldTable);
        importTable = new ImportTable(p.importDeclarations);
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
