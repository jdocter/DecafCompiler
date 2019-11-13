package edu.mit.compilers.cfg;

import edu.mit.compilers.cfg.innercfg.InnerCFBlock;
import edu.mit.compilers.cfg.innercfg.InnerCFConditional;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.cfg.innercfg.InnerCFNop;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MiniCFVisitor;

public class ExpressionSaver implements CFVisitor, MiniCFVisitor {

    private final CFNode start;
    private final Expr expr;
    private final SharedTemp sharedTemp;

    ExpressionSaver(CFNode start, Expr expr, SharedTemp sharedTemp) {
        this.start = start;
        this.expr = expr;
        this.sharedTemp = sharedTemp;
    }

    public void saveExpressions() {
        this.start.accept(this);
    }

    @Override
    public void visit(CFBlock cfBlock) {
        // TODO
        for (CFNode cfNode: cfBlock.parents()) {
            cfNode.accept(this);
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        // TODO
        for (CFNode cfNode: cfConditional.parents()) {
            cfNode.accept(this);
        }
    }

    @Override
    public void visit(CFNop cfNop) {
        for (CFNode cfNode: cfNop.parents()) {
            cfNode.accept(this);
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
        // TODO
        for (CFNode cfNode: cfReturn.parents()) {
            cfNode.accept(this);
        }
    }

    @Override
    public void visit(InnerCFBlock cfBlock) {
        // TODO
        for (InnerCFNode innerCFNode: cfBlock.parents()) {
            innerCFNode.accept(this);
        }
    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        // TODO
        for (InnerCFNode innerCFNode: cfConditional.parents()) {
            innerCFNode.accept(this);
        }
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        for (InnerCFNode innerCFNode: cfNop.parents()) {
            innerCFNode.accept(this);
        }
    }
}
