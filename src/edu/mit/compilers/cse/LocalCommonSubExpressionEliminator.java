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
    ListIterator<InnerCFNode> iterator;

    Map<InnerCFNode, Set<Expr>> innerOut = new HashMap<>();
    // in, gen, kill unnecessary

    public LocalCommonSubExpressionEliminator(LinkedList<InnerCFNode> ts, Set<Expr> allExprs) {
        this.allExprs = allExprs;

        ListIterator<InnerCFNode> listIterator = ts.listIterator(0);
        this.iterator = listIterator;

        if (iterator.hasNext()) iterator.next().accept(this);
    }

    private Set<Expr> getIn(InnerCFNode cfNode) {
        Set<Expr> in = new HashSet<>();
        Set<InnerCFNode> parents = cfNode.parents();
        if (!parents.isEmpty()) {
            in.addAll(innerOut.get(parents.iterator().next()));
            for (InnerCFNode pred : parents) {
                in.retainAll(innerOut.get(pred));
            }
        }
        return in;
    }

    private AssemblyVariable saveExpr(CFAssign cfAssign, ListIterator<CFStatement> previousStatements, Set<InnerCFNode> parents) {
        if (cfAssign.srcOptionalCSE != null) {
            return cfAssign.dstOptionalCSE; // already existing save location
        }
        if (cfAssign.dstArrayOffset != null) {
            throw new RuntimeException("Please run Local CSE before peephole-removing {t = op; a[b] = t}" + "\nStatement: " + cfAssign);
        }


        Expr wanted = cfAssign.getRHS();

        while (previousStatements.hasPrevious()) {
            CFStatement statement = previousStatements.previous();
            if (statement.generatedExpr().isPresent() && statement.generatedExpr().get().equals(wanted)) {
                CFAssign previousAssign = (CFAssign) statement;
                if (previousAssign.dstOptionalCSE != null) {
                    return previousAssign.dstOptionalCSE;
                } else {
                    AssemblyVariable newDst;
                    if (cfAssign.dstArrayOrLoc.isTemporary()) {
                        newDst = cfAssign.dstArrayOrLoc;
                    } else {
                        newDst = new SharedTemp(); // could use normal temp?
                    }
                    previousAssign.additionalDestination(newDst);
                    cfAssign.alternativeSource(newDst);
                    return newDst;
                }
            }
        }
        // expression only available from earlier InnerCFNode
        AssemblyVariable newDst;
        if (cfAssign.dstArrayOrLoc.isTemporary()) {
            newDst = cfAssign.dstArrayOrLoc;
        } else {
            newDst = new SharedTemp(); // could use normal temp?
        }
        for (InnerCFNode parent : parents) {
            ExpressionSaver saver = new ExpressionSaver(parent, wanted, newDst);
            // TODO
            // Have to consider saver.finalDst
            // Must set cfAssign.alternativeSource with something
        }
        return ;// TODO choose variable to return
    }


    @Override
    public void visit(InnerCFBlock cfBlock) {
        ListIterator<CFStatement> statements = cfBlock.getCfStatements().listIterator(0);

        Set<Expr> in = getIn(cfBlock);

        while (statements.hasNext()) {
            CFStatement statement = statements.next();
            Optional<Expr> generatedExpr = statement.generatedExpr();
            if (generatedExpr.isPresent()) {
                Expr expr = generatedExpr.get();
                if (in.contains(expr)) {
                    CFAssign cfAssign = (CFAssign) statement;
                    // Save expr
                    ListIterator<CFStatement> backTrack = cfBlock.getCfStatements().listIterator(statements.previousIndex() - 1);
                    AssemblyVariable src = saveExpr(cfAssign, backTrack, cfBlock.parents());
                    cfAssign.alternativeSource(src);

                    // extract
                    // TODO
                    // don't remember what I meant by "extract"
                }
                in.add(expr);
            }
            in.removeAll(statement.killedExprs(in));
        }

        innerOut.put(cfBlock, in);
    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        Set<Expr> in = getIn(cfConditional);
        innerOut.put(cfConditional, in);
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        Set<Expr> in = getIn(cfNop);
        innerOut.put(cfNop, in);
    }
}
