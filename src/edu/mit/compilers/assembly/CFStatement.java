package edu.mit.compilers.assembly;

import java.util.List;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.util.Pair;

public interface CFStatement {
    List<String> toAssembly(VariableTable variableTable);

    int getUID();

    /**
     * @return Pair<TempUpdated, TempsUsed>, one pair for each statement
     */
    Pair<Temp, List<Temp>> getTemps();
}
