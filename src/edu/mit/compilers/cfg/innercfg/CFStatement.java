package edu.mit.compilers.cfg.innercfg;

import java.util.List;
import java.util.Set;

import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;

public interface CFStatement {
    List<String> toAssembly(VariableTable variableTable, ImportTable importTable);

    Set<Expr> generatedExprs();
    Set<Expr> killedExprs(Set<Expr> exprs);

    int getUID();

    /**
     * @return Pair<TempUpdated, TempsUsed>, one pair for each statement
     */
    Pair<Temp, List<Temp>> getTemps();

    Expr getRHS();
}
