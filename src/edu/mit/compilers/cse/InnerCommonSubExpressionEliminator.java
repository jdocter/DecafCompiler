package edu.mit.compilers.cse;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.cfg.innercfg.*;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MiniCFVisitor;

import java.util.HashSet;
import java.util.Set;

public class InnerCommonSubExpressionEliminator implements MiniCFVisitor {

    final Set<Expr> available;
    Set<InnerCFNode> visited = new HashSet<>();

    Set<Expr> used;

    public InnerCommonSubExpressionEliminator(InnerCFNode start, Set<Expr> available) {
        this.available = new HashSet<>(available); // defensive copy
        this.used = new HashSet<>();

        // go forward, use saved expressions -- whenever using, report use
        start.accept(this);
    }

    @Override
    public void visit(InnerCFBlock cfBlock) {
        if (visited.contains(cfBlock)) return;
        visited.add(cfBlock);


        for (CFStatement statement : cfBlock.getCfStatements()) {

        }

        for (InnerCFNode neighbor : cfBlock.dfsTraverse()) {
            neighbor.accept(this);
        }
    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        if (visited.contains(cfConditional)) return;
        visited.add(cfConditional);

        for (InnerCFNode neighbor : cfConditional.dfsTraverse()) {
            neighbor.accept(this);
        }
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        if (visited.contains(cfNop)) return;
        visited.add(cfNop);

        for (InnerCFNode neighbor : cfNop.dfsTraverse()) {
            neighbor.accept(this);
        }
    }
}
