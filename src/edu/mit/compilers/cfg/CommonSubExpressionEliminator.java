package edu.mit.compilers.cfg;

import edu.mit.compilers.parser.Expr;

import java.util.Set;

public class CommonSubExpressionEliminator {

    final Set<Expr> subExpressions;

    public CommonSubExpressionEliminator(CFNode methodCFG) {
        CollectSubExpressions collector = new CollectSubExpressions();
        methodCFG.accept(collector);
        subExpressions = collector.subExpressions;

        // TODO
        // calculate gen
        // calculate kill
        // initialize ins and outs
        // fixed point algorithm
    }
}
