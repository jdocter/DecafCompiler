package edu.mit.compilers.cfg.innercfg;

import edu.mit.compilers.cfg.CFNode;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.visitor.MiniCFVisitor;

import java.util.HashSet;
import java.util.Set;

public class InnerCollectSubExpressions implements MiniCFVisitor {
    /**
     * To be called AFTER TempifySubExpressions
     */

    Set<InnerCFNode> visited = new HashSet<>();

    public Set<Expr> subExpressions = new HashSet<>();

    @Override
    public void visit(InnerCFBlock cfBlock) {
        if (visited.contains(cfBlock)) return;
        else visited.add(cfBlock);
        subExpressions.addAll(cfBlock.getNonMethodCallSubExpressions());
        for (InnerCFNode child: cfBlock.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        if (visited.contains(cfConditional)) return;
        else visited.add(cfConditional);
        subExpressions.addAll(cfConditional.getNonMethodCallSubExpressions());
        for (InnerCFNode child: cfConditional.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        if (visited.contains(cfNop)) return;
        else visited.add(cfNop);
        subExpressions.addAll(cfNop.getNonMethodCallSubExpressions());
        for (InnerCFNode child: cfNop.dfsTraverse()) {
            child.accept(this);
        }
    }
}
