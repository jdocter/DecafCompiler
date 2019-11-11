package edu.mit.compilers.cfg.innercfg;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.assembly.Reg;
import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.cfg.Variable;
import edu.mit.compilers.inter.FieldTable;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.LocalTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.StringLit;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFMethodCall extends UIDObject implements CFStatement {

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
    public List<String> toAssembly(VariableTable variableTable, ImportTable importTable) {
        // push stack according to size of arguments
        List<String> assembly = new ArrayList<>();

        List<String> body = new ArrayList<>();
        body.add("# " + this.toString());

        int stackArgs = arguments.size() - 6;

        // this has to be done before pushing args onto stack
        // maintain 16-byte alignment -- assuming 16 aligned before call
        if (stackArgs > 0 && stackArgs % 2 != 0) {
            stackArgs++;
            body.add("push %rax # dummy argument used to maintain 16-byte alignment");
        }

        for (int p = arguments.size(); p > 0; p--) {

            Pair<Temp, StringLit> param = arguments.get(p - 1);
            // push arguments in reverse order
            if (p <= 6) {
                Reg target = Reg.methodParam(p);
                if (param.getKey() != null) {
                    body.add("movq -" + param.getKey().getOffset() + "(%rbp), " + target);
                } else {
                    body.add("leaq " + param.getValue().getLabel() + "(%rip), " + target);
                }
            } else {
                if (param.getKey() != null) {
                    body.add("push -" + param.getKey().getOffset() + "(%rbp)");
                } else {
                    body.add("leaq " + param.getValue().getLabel() + "(%rip), %rax");
                    body.add("push %rax");
                }
            }
        }

        String callTarget;
        if (methodName.getName().equals("main") || importTable.containsKey(methodName.getName())) {
            callTarget = methodName.getName();
        } else {
            callTarget = "_method_" + methodName.getName();
        }
        body.add("call " + callTarget);

        if (stackArgs > 0) {
            body.add("addq $" + stackArgs * 8 + ", %rsp");
        }

        body.add("cmpq $0, " + AssemblyFactory.GLOBAL_EXIT_CODE);
        body.add("jne " + AssemblyFactory.METHOD_EXIT + " # premature exit if the call resulted in runtime exception");

        assembly.addAll(AssemblyFactory.indent(body));
        return assembly;
    }

    @Override
    public Set<Expr> generatedExprs(Set<Expr> exprs) {
        return new HashSet<>();
    }

    @Override
    public Set<Expr> killedExprs(Set<Expr> exprs) {
        Set<Expr> killed = new HashSet<>(exprs);
        for (Expr expr: exprs) {
            for (Id id: expr.getIds()) {
                if (id.getDeclarationScope() == VariableTable.GLOBAL_SCOPE_UID) {
                    killed.remove(expr);
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
    public Pair<Temp, List<Temp>> getTemps() {
        List<Temp> allTempsNeeded = new ArrayList<>();
        for (Pair<Temp, StringLit> arg : arguments) {
            if (arg.getKey() != null) allTempsNeeded.add(arg.getKey());
        }
        return new Pair<Temp, List<Temp>>(null, allTempsNeeded);
    }

    @Override
    public Expr getRHS() {
        return null; // not tracking Method Call exprs bc they don't need to be eliminated
    }
}
