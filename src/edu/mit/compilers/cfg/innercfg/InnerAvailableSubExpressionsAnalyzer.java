package edu.mit.compilers.cfg.innercfg;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InnerAvailableSubExpressionsAnalyzer implements MiniCFVisitor {

    final Set<Expr> subExpressions;
    Set<InnerCFNode> visited = new HashSet<>(); // redundant with in/out/gen/kill, but decoupling code intent

    Map<InnerCFNode, Set<Expr>> in = new HashMap<>();
    Map<InnerCFNode, Set<Expr>> out = new HashMap<>();
    Map<InnerCFNode, Set<Expr>> gen = new HashMap<>();
    Map<InnerCFNode, Set<Expr>> kill = new HashMap<>();

    public InnerAvailableSubExpressionsAnalyzer(CFNode methodCFG) {
        CollectSubExpressions collector = new CollectSubExpressions();
        methodCFG.accept(collector);
        subExpressions = collector.subExpressions;

        // calculate gen
        // calculate kill
        // initialize ins and outs
        methodCFG.accept(this);

        // initialize methodCFG
        in.put(methodCFG, Set.of());
        out.put(methodCFG, gen.get(methodCFG));

        System.err.println("Done calculating gen and kill");

        // fixed point algorithm
        runFixedPointAlgorithm();
    }

    private void runFixedPointAlgorithm() {
        // TODO
        throw new RuntimeException("not implemented");
    }

    private void calculateAndVisitNeighbors(CFNode cfNode) {
        if (!visited.contains(cfNode)) {
            visited.add(cfNode);

            gen.put(cfNode, cfNode.generatedExprs());
            kill.put(cfNode, cfNode.killedExprs(subExpressions));
            in.put(cfNode, subExpressions);
            out.put(cfNode, subExpressions);

            for (CFNode neighbor : cfNode.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }

    @Override
    public void visit(CFBlock cfBlock) {
        calculateAndVisitNeighbors(cfBlock);
    }

    @Override
    public void visit(CFConditional cfConditional) {
        calculateAndVisitNeighbors(cfConditional);
    }

    @Override
    public void visit(CFNop cfNop) {
        calculateAndVisitNeighbors(cfNop);
    }

    @Override
    public void visit(CFReturn cfReturn) {
        calculateAndVisitNeighbors(cfReturn);
    }
}
