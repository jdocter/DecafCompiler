package edu.mit.compilers.cse;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.cfg.innercfg.*;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.visitor.MiniCFVisitor;

import java.util.*;

public class LocalCommonSubExpressionEliminator implements MiniCFVisitor {

    /**
     * Use MiniCFVisitor to avoid switching on instance type
     */

    Set<Expr> allExprs;
    private final LinkedList<InnerCFNode> ts;

    private final Map<Expr, AssemblyVariable> sharedExpressionsMap;

    // maps cfnode to set of expressions available to it
    Map<InnerCFNode, Set<Expr>> availableExprs = new HashMap<>();

    public LocalCommonSubExpressionEliminator(InnerCFNode miniCFGStart, Set<Expr> blockExprs, Map<Expr, SharedTemp> sharedExpressionsMap) {
        this.allExprs = blockExprs;
        this.sharedExpressionsMap = new HashMap<>(sharedExpressionsMap);
        this.ts = new TopologicalSort(miniCFGStart).getTopologicalSort();

        computeAvailableExprs();
        eliminateCommonExprs();
    }

    private void eliminateCommonExprs() {
        for (InnerCFNode cfNode: ts) {
            cfNode.accept(this);
        }
    }

    private Set<Expr> calculateParentOut(InnerCFNode parent) {
        Set<Expr> out = new HashSet<>();

        // (IN - KILL) U GEN
        out.addAll(availableExprs.get(parent));
        out.removeAll(parent.killedExprs(allExprs));
        out.addAll(parent.generatedExprs(allExprs));

        return out;
    }

    /**
     * Computes available expressions within block
     * No need for fixed point algo because no loops
     */
    private void computeAvailableExprs() {
        for (InnerCFNode cfNode : ts) {
            Set<Expr> in = new HashSet<>();
            Set<InnerCFNode> parents = cfNode.parents();

            if (parents.isEmpty()) {
                availableExprs.put(cfNode, Set.of());
                return;
            }

            in.addAll(calculateParentOut(parents.iterator().next()));
            for (InnerCFNode pred : parents) {
                in.retainAll(calculateParentOut(pred));
            }

            availableExprs.put(cfNode, in);
        }
    }

    /**
     * Save expression of CFAssign
     * @param cfAssign
     * @param previousStatements
     * @param parents
     * @return
     */
    private void handleCommonExpr(CFAssign cfAssign, ListIterator<CFStatement> previousStatements, Set<InnerCFNode> parents) {
        Expr wanted = cfAssign.getRHS();

        AssemblyVariable e;
        if (sharedExpressionsMap.containsKey(wanted)) {
            e = sharedExpressionsMap.get(cfAssign.getRHS());
        } else {
            e = new Temp();
            sharedExpressionsMap.put(wanted, e);
        }

        if (cfAssign.srcOptionalCSE != null) {
            assert cfAssign.srcOptionalCSE == e : "shared temp expected to be consistent for an expression";
            return; // already existing save location
        }

        while (previousStatements.hasPrevious()) {
            CFStatement statement = previousStatements.previous();
            if (statement.generatedExpr().isPresent() && statement.generatedExpr().get().equals(wanted)) {
                CFAssign previousAssign = (CFAssign) statement;
                if (previousAssign.dstOptionalCSE != null) {
                    assert previousAssign.dstOptionalCSE == e : "shared temp expected to be consistent for an expression";
                } else {
                    // save and use expression
                    previousAssign.setAdditionalDestination(e);
                }
                cfAssign.setAlternativeSource(e);
                return;
            }
        }
        // expression only available from earlier InnerCFNode
        for (InnerCFNode parent : parents) {
            new ExpressionSaver(parent, wanted, e).saveExpressions();
        }
        cfAssign.setAlternativeSource(e);
    }

    @Override
    public void visit(InnerCFBlock cfBlock) {

        ListIterator<CFStatement> statements = cfBlock.getCfStatements().listIterator(0);

        Set<Expr> in = new HashSet<>(availableExprs.get(cfBlock));

        while (statements.hasNext()) {
            CFStatement statement = statements.next();

            Expr rhs = statement.getRHS();
            if (rhs !=null && in.contains(rhs)) {
                // save expression
                ListIterator<CFStatement> backTrack = cfBlock.getCfStatements().listIterator(statements.previousIndex());
                handleCommonExpr((CFAssign) statement, backTrack, cfBlock.parents());
            }

            // update available expressions for next statement in inner block
            in.removeAll(statement.killedExprs(allExprs));
            if (statement.generatedExpr().isPresent()) in.add(statement.generatedExpr().get());
        }

    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        // do nothing?
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        // do nothing?
    }

}
