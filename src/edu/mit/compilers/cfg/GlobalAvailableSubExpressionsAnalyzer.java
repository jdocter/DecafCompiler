package edu.mit.compilers.cfg;

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
        changed = new HashSet<>(gen.keySet());
        for (CFNode node: gen.keySet()) {
          out.put(node, subExpressions);
        }
        changed.removeAll(Set.of(methodCFG));
        runFixedPointAlgorithm();
    }

    private void runFixedPointAlgorithm() {
        // TODO
        while (!changed.isEmpty()) {
          CFNode currentNode; //TODO figure out a way to pick an item out of the changed set.
          changed = changed.remove(currentNode);
          in.put(currentNode, new HashSet<>(subExpressions)); //TODO subExpressions is not available here
          for (CFNode pred: currentNode.parents()) {
            in.get(currentNode).retainAll(out.get(pred));
          }

          Set<Expr> oldOut = out.get(currentNode);
          out.put(currentNode, new HashSet<>(gen.get(currentNode)));
          Set<Expr> inCopy = new HashSet<>(in.get(currentNode));
          inCopy.removeAll(kill.get(currentNode));
          out.get(currentNode).addAll(inCopy);

          if (!oldOut.equals(out.get(currentNode))) {
            changed.addAll(currentNode.successors()); // TODO replace successors with something that actually does something
          }
        }


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
