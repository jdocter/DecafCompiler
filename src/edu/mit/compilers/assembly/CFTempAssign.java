package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.VariableTable;

import java.util.List;

public class CFTempAssign implements CFStatement {

    private Temp temp;

    public static CFTempAssign makeMinus(Temp dest, Temp operand) {
        return new CFTempAssign();
    }

    @Override
    public List<String> toAssembly(VariableTable variableTable) {
        return null;
    }

    @Override
    public int getUID() {
        return 0;
    }
}
