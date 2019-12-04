package edu.mit.compilers.cfg.innercfg;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.assembly.Reg;
import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.cfg.SharedTemp;
import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.cfg.Variable;
import edu.mit.compilers.inter.FieldTable;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.LocalTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.Statement;
import edu.mit.compilers.parser.StringLit;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.StatementCFVisitor;

import java.util.*;

public class CFMethodCall extends CFStatement {

    public final Id methodName;
    // every pair will contain one null item, purpose is to preserve order of arguments
    public final List<Pair<Temp,StringLit>> arguments;

    public final VariableTable variableTable;

    public CFMethodCall(Id id, List<Pair<Temp,StringLit>> arguments, VariableTable variableTable) {
        methodName = id;
        this.arguments = arguments;
        this.variableTable = variableTable;
    }

    @Override
    public void accept(StatementCFVisitor v) {
        v.visit(this);
    }

    @Override
    public Set<AssemblyVariable> getLocalAssemblyVariables() {
        return getUsed();
    }

    @Override
    public Set<AssemblyVariable> getDefined() {
        return Set.of();
    }

    @Override
    public Set<AssemblyVariable> getUsed() {
        HashSet<AssemblyVariable> assemblyVariables = new HashSet<>();
        for (Pair<Temp, StringLit> arg : arguments) {
            // TODO exclude global?
            if (arg.getKey() != null) assemblyVariables.add(arg.getKey());
        }
        return assemblyVariables;
    }

    @Override
    public Optional<Expr> generatedExpr() {
        return Optional.empty();
    }

    @Override
    public Set<Expr> killedExprs(Set<Expr> exprs) {
        Set<Expr> killed = new HashSet<>();
        for (Expr expr: exprs) {
            for (Id id: expr.getIds()) {
                // System.err.println("Considering killing " + expr + ", scope = " + id.getDeclarationScope());
                if (id.getDeclarationScope() == VariableTable.GLOBAL_SCOPE_UID) {
                    // System.err.println(this.toString() + "kills " + expr);
                    killed.add(expr);
                    break;
                }
            }
        }
        return killed;
    }

    @Override public String toString() {
        return "" + methodName + arguments;
    }

    @Override
    public Pair<List<Temp>, List<Temp>> getTemps() {
        List<Temp> allTempsNeeded = new ArrayList<>();
        for (Pair<Temp, StringLit> arg : arguments) {
            if (arg.getKey() != null) allTempsNeeded.add(arg.getKey());
        }
        return new Pair<List<Temp>, List<Temp>>(List.of(), allTempsNeeded);
    }

    @Override
    public Expr getRHS() {
        return null; // not tracking Method Call exprs bc they don't need to be eliminated
    }

    @Override
    public Set<SharedTemp> getSharedTemps() {
        return Set.of();
    }
}
