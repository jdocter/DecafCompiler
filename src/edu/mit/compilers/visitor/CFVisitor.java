package edu.mit.compilers.visitor;

import edu.mit.compilers.assembly.*;
import edu.mit.compilers.cfg.CFBlock;
import edu.mit.compilers.cfg.CFConditional;
import edu.mit.compilers.cfg.CFNop;
import edu.mit.compilers.cfg.CFReturn;

public interface CFVisitor {
    void visit(CFBlock cfBlock);
    void visit(CFConditional cfConditional);
    void visit(CFNop cfNop);
    void visit(CFReturn cfReturn);
}
