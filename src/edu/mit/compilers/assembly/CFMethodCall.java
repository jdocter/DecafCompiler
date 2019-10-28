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
        return null;
    }

    @Override public String toString() {
        return "" + methodName + arguments;
    }
}
