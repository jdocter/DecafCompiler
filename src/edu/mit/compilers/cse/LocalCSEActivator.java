package edu.mit.compilers.cse;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LocalCSEActivator implements CFVisitor {

    /**
     * Does DFS on nodes and activates LocalCommonSubExpressionEliminator on all of them.
     */

    private final Set<OuterCFNode> visited = new HashSet<>();

    private final Map<Expr, SharedTemp> sharedExpressionsMap;

    public LocalCSEActivator(Map<Expr, SharedTemp> sharedExpressionsMap) {
        this.sharedExpressionsMap = sharedExpressionsMap;
    }

    @Override
    public void visit(CFBlock cfBlock) {
        if (visited.contains(cfBlock)) return;
        else visited.add(cfBlock);
        new LocalCommonSubExpressionEliminator(cfBlock.getMiniCFGStart(), cfBlock.getSubExpressions(), sharedExpressionsMap);
        for (OuterCFNode child: cfBlock.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        if (visited.contains(cfConditional)) return;
        else visited.add(cfConditional);
        new LocalCommonSubExpressionEliminator(cfConditional.getMiniCFGStart(), cfConditional.getSubExpressions(), sharedExpressionsMap);
        for (OuterCFNode child: cfConditional.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFNop cfNop) {
        if (visited.contains(cfNop)) return;
        else visited.add(cfNop);
        for (OuterCFNode child: cfNop.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
        if (visited.contains(cfReturn)) return;
        else visited.add(cfReturn);
        new LocalCommonSubExpressionEliminator(cfReturn.getMiniCFGStart(), cfReturn.getSubExpressions(), sharedExpressionsMap);
    }
}
