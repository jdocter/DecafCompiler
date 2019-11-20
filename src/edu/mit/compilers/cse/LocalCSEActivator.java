package edu.mit.compilers.cse;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.cfg.innercfg.TopologicalSort;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LocalCSEActivator implements CFVisitor {

    /**
     * Does DFS on nodes and activates LocalCommonSubExpressionEliminator on all of them.
     */

    private final Set<CFNode> visited = new HashSet<>();

    public LocalCSEActivator(Map<Expr, SharedTemp> sharedExpressionsMap) {
        // TODO can we usefully use sharedExpressionMap???
    }

    @Override
    public void visit(CFBlock cfBlock) {
        if (visited.contains(cfBlock)) return;
        else visited.add(cfBlock);
        new LocalCommonSubExpressionEliminator(new TopologicalSort(cfBlock.getMiniCFGStart()).getTopologicalSort(),
                cfBlock.getSubExpressions());
        for (CFNode child: cfBlock.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        if (visited.contains(cfConditional)) return;
        else visited.add(cfConditional);
        new LocalCommonSubExpressionEliminator(new TopologicalSort(cfConditional.getMiniCFGStart()).getTopologicalSort(),
                cfConditional.getSubExpressions());
        for (CFNode child: cfConditional.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFNop cfNop) {
        if (visited.contains(cfNop)) return;
        else visited.add(cfNop);
        for (CFNode child: cfNop.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
        if (visited.contains(cfReturn)) return;
        else visited.add(cfReturn);
        new LocalCommonSubExpressionEliminator(new TopologicalSort(cfReturn.getMiniCFGStart()).getTopologicalSort(),
                cfReturn.getSubExpressions());
    }
}
