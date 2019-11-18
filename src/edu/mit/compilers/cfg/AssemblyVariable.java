package edu.mit.compilers.cfg;

import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;

public interface AssemblyVariable {

    /**
     * should be used when appropriate (i.e. when isGlobal returns false)
     * @param variableTable
     * @return
     */
    long getStackOffset(VariableTable variableTable);


    boolean isGlobal(VariableTable variableTable);

    String getName();

    String getGlobalLabel(VariableTable variableTable);

    boolean isArray(VariableTable variableTable);

    long getArrayLength(VariableTable variableTable);

    int getElementSize(VariableTable variableTable);

    boolean isTemporary();
}
