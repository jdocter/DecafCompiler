package edu.mit.compilers.visitor;

import edu.mit.compilers.cfg.innercfg.*;

public interface StatementCFVisitor {

    /**
     * Similar to CFVisitor, but for CFStatements
     */
    void visit(CFAssign cfAssign);
    void visit(CFMethodCall cfMethodCall);
}
