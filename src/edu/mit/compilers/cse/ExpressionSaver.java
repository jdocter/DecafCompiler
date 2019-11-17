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
 */
public class ExpressionSaver implements CFVisitor, MiniCFVisitor {

    private final CFNode start;
    private final Expr expr;
    private final SharedTemp sharedTemp;

    ExpressionSaver(CFNode start, Expr expr, SharedTemp sharedTemp) {
        this.start = start;
        this.expr = expr;
        this.sharedTemp = sharedTemp;
    }

    public void saveExpressions() {
        this.start.accept(this);
    }

    @Override
    public void visit(CFBlock cfBlock) {
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
        // do nothing
        for (CFNode cfNode: cfNop.parents()) {
            cfNode.accept(this);
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
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
            if (cfStatement.generatedExprs().contains(expr)) {
                // must be CFAssign
                ((CFAssign) cfStatement).additionalDestination(sharedTemp);
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
