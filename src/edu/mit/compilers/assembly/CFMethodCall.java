package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.Statement;
import edu.mit.compilers.parser.StringLit;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.Triad;
import edu.mit.compilers.util.UIDObject;

import java.util.ArrayList;
import java.util.List;

public class CFMethodCall extends UIDObject implements CFStatement {

    public final Id methodName;
    // every pair will contain one null item, purpose is to preserve order of arguments
    public final List<Pair<Temp,StringLit>> arguments;

    CFMethodCall(Id id, List<Pair<Temp,StringLit>> arguments) {
        methodName = id;
        this.arguments = arguments;
    }

    @Override
    public List<String> toAssembly(VariableTable variableTable) {
        // push stack according to size of arguments
        List<String> assembly = new ArrayList<>();

        List<String> body = new ArrayList<>();
        for (int p = arguments.size(); p >= 0; p--) {

            Pair<Temp, StringLit> param = arguments.get(p - 1);
            // push arguments in reverse order
            if (p <= 6) {
                Reg target = Reg.methodParam(p);
                if (param.getKey() != null) {
                    body.add("movq -" + param.getKey().getOffset() + "(%rbp), " + target);
                } else {
                    body.add("movq _string_" + param.getValue() + "(%rip), " + target);
                }
            } else {
                if (param.getKey() != null) {
                    body.add("push -" + param.getKey().getOffset() + "(%rbp)");
                } else {
                    body.add("push _string_" + param.getValue() + "(%rip)");
                }
            }
        }

        String callTarget;
        if (methodName.getName().equals("main") || isImport(methodName)) {
            callTarget = methodName.getName();
        } else {
            callTarget = "_method_" + methodName.getName();
        }
        body.add("call " + callTarget);

        int stackArgs = arguments.size() - 6;
        if (stackArgs > 0) {
            body.add("addq $" + stackArgs * 8 + ", %rsp");
        }

        assembly.addAll(AssemblyFactory.indent(body));
        return assembly;
    }

    @Override public String toString() {
        return "" + methodName + arguments;
    }

    @Override
    public Pair<Temp, List<Temp>> getTemps() {
        throw new RuntimeException("Not implemented -- We planned on implementing method calls using CFTempAssigns");
    }
}
