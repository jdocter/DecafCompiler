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
public class ProgramTable {

    private FieldTable fieldTable;
    private MethodTable methodTable;
    private MethodTable importTable;
    private TypeTable typeTable;

    public ProgramTable(Program p) throws SemanticException {
        typeTable = new TypeTable();
        fieldTable = new FieldTable(p.fieldDeclarations, typeTable);
        methodTable = new MethodTable(p.methods, typeTable);
        importTable = new MethodTable(p.importDeclarations, typeTable);
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
