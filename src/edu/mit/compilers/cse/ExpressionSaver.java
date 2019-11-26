package edu.mit.compilers.cse;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.cfg.innercfg.*;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MiniCFVisitor;

import java.util.*;

/**
 * Alters CFG representation such that the value in *expr* is available
 *      at the CFNode *start* in the variable *sharedTemp*
 * Traverses backwards from *start* until reach first appearance of *expr*
 *      in all paths and inserts additional assembly destinations
 * Assumes that *expr* is available from all parent paths at *start*
 *
 * IMPORTANT: requires that given additional destinations are consistent within the program
 */
public class ExpressionSaver implements CFVisitor, MiniCFVisitor {

    private final CFNode start;
    private final InnerCFNode innerStart;

    private final Expr expr;
    private final AssemblyVariable additionalDst;

    Set<CFNode> visited = new HashSet<>();


    ExpressionSaver(CFNode start, Expr expr, SharedTemp sharedTemp) {
        this.start = start;
        this.innerStart = null;
        this.expr = expr;
        this.additionalDst = sharedTemp;
    }

    ExpressionSaver(InnerCFNode start, Expr expr, AssemblyVariable newDst) {
        this.start = null;
        this.innerStart = start;
        this.expr = expr;
        this.additionalDst = newDst;
    }

    public void saveExpressions() {
        // Must make sure to visit start node because it
        // visited.add(start);
        if (this.innerStart != null) {
            this.innerStart.accept(this);
        } else {
            this.start.accept(this);
        }
    }

    @Override
    public void visit(CFBlock cfBlock) {
        if (visited.contains(cfBlock)) return;
        visited.add(cfBlock);

        if (cfBlock.getSubExpressions().contains(expr)) {
            cfBlock.getMiniCFGEnd().accept(this);
        } else {
            for (CFNode cfNode : cfBlock.parents()) {
                cfNode.accept(this);
            }
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        if (visited.contains(cfConditional)) return;
        visited.add(cfConditional);

        if (cfConditional.getSubExpressions().contains(expr)) {
            cfConditional.getMiniCFGEnd().accept(this);
        } else {
            for (CFNode cfNode : cfConditional.parents()) {
                cfNode.accept(this);
            }
        }
    }

    @Override
    public void visit(CFNop cfNop) {
        if (visited.contains(cfNop)) return;
        visited.add(cfNop);

        // do nothing
        for (CFNode cfNode: cfNop.parents()) {
            cfNode.accept(this);
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
        if (visited.contains(cfReturn)) return;
        visited.add(cfReturn);

        if (cfReturn.getSubExpressions().contains(expr)) {
            cfReturn.getMiniCFGEnd().accept(this);
        } else {
            for (CFNode cfNode : cfReturn.parents()) {
                cfNode.accept(this);
            }
        }
    }

    @Override
    public void visit(InnerCFBlock cfBlock) {
        // iterate backwards
        ListIterator<CFStatement> listIterator = cfBlock.getCfStatements().listIterator(cfBlock.getCfStatements().size());

        while (listIterator.hasPrevious()) {
            CFStatement cfStatement = listIterator.previous();
            if (cfStatement.generatedExpr().isPresent() && cfStatement.generatedExpr().get().equals(expr)) {
                // must be CFAssign
                CFAssign cfAssign = ((CFAssign) cfStatement);

                if (cfAssign.srcOptionalCSE != null) {
                    assert cfAssign.srcOptionalCSE == additionalDst : "shared temp expected to be consistent for an expression";
                    // INVARIANT: must save expression RIGHT AFTER setting optional CSE
                    // consequence: don't need to save again, it's still available

                    // Bug prevention argument: consider case IN 1; OUT 1 but Common-subexpr is killed in the middle
                    // IN 1; a = b + c; b = 2; d = b + c; OUT 1
                    // ^ in this case only a = b + c would have srcOptionalCSE and not d = b + c.
                    return;
                }
                cfAssign.setAdditionalDestination(additionalDst);
                return;
            }
        }
        for (InnerCFNode innerCFNode: cfBlock.parents()) {
            innerCFNode.accept(this);
        }
    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        // do nothing
        for (InnerCFNode innerCFNode: cfConditional.parents()) {
            innerCFNode.accept(this);
        }
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        // do nothing
        for (InnerCFNode innerCFNode: cfNop.parents()) {
            innerCFNode.accept(this);
        }
    }
}
