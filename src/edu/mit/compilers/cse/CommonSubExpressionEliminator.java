package edu.mit.compilers.cse;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.cfg.innercfg.*;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.*;

public class CommonSubExpressionEliminator implements CFVisitor {

    private final GlobalAvailableSubExpressionsAnalyzer analysis;
    private final Set<CFNode> visited = new HashSet<>();

    // To avoid re-saving expressions every time, map the same expr to the same shared temp.
    private final HashMap<Expr, SharedTemp> sharedExpressionsMap = new HashMap<>();

    public CommonSubExpressionEliminator(CFNode methodCFG) {
        analysis = new GlobalAvailableSubExpressionsAnalyzer(methodCFG);

        // go forward using saved expressions -- whenever using, check backwards to save previous use
        methodCFG.accept(this);

        methodCFG.accept(new LocalCSEActivator(sharedExpressionsMap));
    }

    private void eliminateExprsInnerCFNode(Set<Expr> available, InnerCFNode node, Set<CFNode> parents) {
        Set<Expr> remainingAvailable = new HashSet<>(node.getSubExpressions());
        remainingAvailable.retainAll(available);

        // TODO: register allocation -- sometimes this introduces more register pressure
        // We could do an heuristic here like stopping after 4-6 CSEs or something

        if (remainingAvailable.isEmpty()) return;
        // Some used are available
        // Only InnerCFBlock have usable sub-expressions
        for (CFStatement statement : ((InnerCFBlock) node).getCfStatements()) {
            Optional<Expr> exprNeeded = statement.generatedExpr();
            if (!exprNeeded.isPresent()) continue; // next statement

            Expr rhs = exprNeeded.get();
            if (!remainingAvailable.contains(rhs)) continue; // next statement

            // Could add this here, but that would be a local CSE so it would be tricky
            // to figure out when we have to do statement.additionalDestination(rhs);
            // remainingAvailable.add(rhs);
            // statement.additionalDestination(rhs);

            // Only CFAssign need exprs
            CFAssign cfAssign = (CFAssign) statement;

            SharedTemp newSrc;
            if (sharedExpressionsMap.containsKey(rhs)) {
                newSrc = sharedExpressionsMap.get(rhs);
            } else {
                newSrc = new SharedTemp();
                sharedExpressionsMap.put(rhs, newSrc);
            }

            // have to mark as used first before saving so that if we loop
            // back, we don't insert an extra save after this statement
            cfAssign.alternativeSource(newSrc);
            for (CFNode parent : parents) {
                new ExpressionSaver(parent, rhs, newSrc).saveExpressions();
            }

            // Don't re-use exprs that have been killed in an earlier statement in this InnerCFNode
            remainingAvailable.removeAll(statement.killedExprs(remainingAvailable));
        }
    }

    @Override
    public void visit(CFBlock cfBlock) {
        if (visited.contains(cfBlock)) return;
        visited.add(cfBlock);

        Set<Expr> available = new HashSet<>(analysis.in.get(cfBlock));

        LinkedList<InnerCFNode> ts = cfBlock.getTS();
        for (InnerCFNode node : ts) {
            eliminateExprsInnerCFNode(available, node, cfBlock.parents());
        }

        for (CFNode neighbor : cfBlock.dfsTraverse()) {
            neighbor.accept(this);
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        if (visited.contains(cfConditional)) return;
        visited.add(cfConditional);

        Set<Expr> available = new HashSet<>(analysis.in.get(cfConditional));

        LinkedList<InnerCFNode> ts = cfConditional.getTS();
        for (InnerCFNode node : ts) {
            eliminateExprsInnerCFNode(available, node, cfConditional.parents());
        }

        for (CFNode neighbor : cfConditional.dfsTraverse()) {
            neighbor.accept(this);
        }
    }

    @Override
    public void visit(CFNop cfNop) {
        if (visited.contains(cfNop)) return;
        visited.add(cfNop);

        for (CFNode neighbor : cfNop.dfsTraverse()) {
            neighbor.accept(this);
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
        if (visited.contains(cfReturn)) return;
        visited.add(cfReturn);

        Set<Expr> available = new HashSet<>(analysis.in.get(cfReturn));

        LinkedList<InnerCFNode> ts = cfReturn.getTS();
        for (InnerCFNode node : ts) {
            eliminateExprsInnerCFNode(available, node, cfReturn.parents());
        }

        for (CFNode neighbor : cfReturn.dfsTraverse()) {
            neighbor.accept(this);
        }
    }
}
