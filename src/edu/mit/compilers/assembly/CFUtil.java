package edu.mit.compilers.assembly;

import edu.mit.compilers.parser.Expr;

public class CFUtil {


    public static CFBlock shortCircuit(Expr condition, CFBlock ifTrue, CFBlock ifFalse) {
        // alias
        return new CFBlock(condition, ifTrue, ifFalse);
    }
}
