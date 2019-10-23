package edu.mit.compilers.visitor;

import edu.mit.compilers.assembly.*;

public interface CFVisitor {
    void visit(CFBlock cfBlock);
    void visit(CFBreak cfBreak);
    void visit(CFConditional cfConditional);
    void visit(CFContinue cfContinue);
    void visit(CFNop cfNop);
    void visit(CFReturn cfReturn);
}
