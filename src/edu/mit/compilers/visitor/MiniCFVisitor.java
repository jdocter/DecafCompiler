package edu.mit.compilers.visitor;

import edu.mit.compilers.cfg.innercfg.InnerCFBlock;
import edu.mit.compilers.cfg.innercfg.InnerCFConditional;
import edu.mit.compilers.cfg.innercfg.InnerCFNop;

public interface MiniCFVisitor {
    /**
     * Similar to CFVisitor, but for miniCFGs (which only have CFBlock, CFConditional, and CFNop)
     */
    void visit(InnerCFBlock cfBlock);
    void visit(InnerCFConditional cfConditional);
    void visit(InnerCFNop cfNop);

}
