package edu.mit.compilers.cse;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.cse.CollectSubExpressions;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GlobalAvailableSubExpressionsAnalyzer implements CFVisitor {

    final Set<Expr> subExpressions;
    Set<CFNode> visited = new HashSet<>(); // redundant with in/out/gen/kill, but decoupling code intent

    Map<CFNode, Set<Expr>> in = new HashMap<>();
    Map<CFNode, Set<Expr>> out = new HashMap<>();
    Map<CFNode, Set<Expr>> gen = new HashMap<>();
    Map<CFNode, Set<Expr>> kill = new HashMap<>();

    Set<CFNode> changed;

    public GlobalAvailableSubExpressionsAnalyzer(CFNode methodCFG) {
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
        changed = new HashSet<>(visited);
        changed.remove(methodCFG);
        runFixedPointAlgorithm();
    }

    private void runFixedPointAlgorithm() {

        while (!changed.isEmpty()) {
          CFNode currentNode = changed.iterator().next();
          changed.remove(currentNode);
          in.put(currentNode, new HashSet<>(subExpressions));
          for (CFNode pred: currentNode.parents()) {
            in.get(currentNode).retainAll(out.get(pred));
          }

          Set<Expr> newOut = new HashSet<>(in.get(currentNode));
          newOut.removeAll(kill.get(currentNode));
          newOut.addAll(gen.get(currentNode));

          if (!newOut.equals(out.get(currentNode))) {
            out.put(currentNode, newOut);
            changed.addAll(currentNode.dfsTraverse());
          }
        }


    }

    private void calculateAndVisitNeighbors(CFNode cfNode) {
        if (!visited.contains(cfNode)) {
            visited.add(cfNode);

            gen.put(cfNode, cfNode.generatedExprs(subExpressions));
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
