package edu.mit.compilers.assembly;

import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.StringLit;
import edu.mit.compilers.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CFMethodCall {

    public final Id methodName;
    // every pair will contain one null item, purpose is to preserve order of arguments
    public final List<Pair<Expr, StringLit>> arguments;

    CFMethodCall(Id id, List<Pair<Expr, StringLit>> arguments) {
        methodName = id;
        this.arguments = arguments;
    }
}
