package edu.mit.compilers.cfg.innercfg;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import edu.mit.compilers.cfg.SharedTemp;
import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;

public interface CFStatement {
    /**
     * Generates functional assembly code representing CFStatement
     * @param variableTable
     * @param importTable
     * @return
     */
    List<String> toAssembly(VariableTable variableTable, ImportTable importTable);

    /**
     * @return expression that is available at the end of this CFStatement
     */
    Optional<Expr> generatedExpr();

    /**
     * @param exprs list of all expressions that could potentially be killed
     * @return subset of exprs that are killed by this CFStatement
     */
    Set<Expr> killedExprs(Set<Expr> exprs);

    int getUID();

    /**
     * @return Pair<TempUpdated, TempsUsed>, one pair for each statement
     */
    Pair<Temp, List<Temp>> getTemps();

    Expr getRHS();

    Set<SharedTemp> getSharedTemps();
}
