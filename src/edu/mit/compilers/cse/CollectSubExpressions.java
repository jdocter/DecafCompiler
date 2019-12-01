package edu.mit.compilers.cse;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.HashSet;
import java.util.Set;

public class CollectSubExpressions implements CFVisitor {
    /**
     * To be called AFTER TempifySubExpressions
     */

    Set<OuterCFNode> visited = new HashSet<>();

    Set<Expr> subExpressions = new HashSet<>();

    @Override
    public void visit(CFBlock cfBlock) {
        if (visited.contains(cfBlock)) return;
        else visited.add(cfBlock);
        subExpressions.addAll(cfBlock.getSubExpressions());
        for (OuterCFNode child: cfBlock.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        if (visited.contains(cfConditional)) return;
        else visited.add(cfConditional);
        subExpressions.addAll(cfConditional.getSubExpressions());
        for (OuterCFNode child: cfConditional.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFNop cfNop) {
        if (visited.contains(cfNop)) return;
        else visited.add(cfNop);
        subExpressions.addAll(cfNop.getSubExpressions());
        for (OuterCFNode child: cfNop.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
        if (visited.contains(cfReturn)) return;
        else visited.add(cfReturn);
        subExpressions.addAll(cfReturn.getSubExpressions());
    }
}
