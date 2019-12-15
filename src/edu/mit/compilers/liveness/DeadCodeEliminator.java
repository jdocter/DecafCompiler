package edu.mit.compilers.liveness;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.cfg.innercfg.*;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MiniCFVisitor;

import java.util.*;

public class DeadCodeEliminator implements CFVisitor, MiniCFVisitor {

    private final LivenessAnalyzer analysis;
    private final Set<OuterCFNode> visited = new HashSet<>();
    private final VariableTable table;

    public DeadCodeEliminator(OuterCFNode methodCFG) {
        analysis = new LivenessAnalyzer(methodCFG);
        this.table = methodCFG.getVariableTable();

        methodCFG.accept(this);
    }

    private void eliminateDeadCodeInnerCFBlock(InnerCFBlock node) {
        // Only InnerCFBlock could have dead expressions
        for (CFStatement statement : node.getCfStatements()) {
            for (AssemblyVariable defined : statement.getDefined()) {
                if (!analysis.getOut(statement).contains(defined)
                        && !defined.isGlobal(table)) {
                    // defined is dead
                    statement.markDead(defined);
                }
            }
        }
    }

    @Override
    public void visit(CFBlock cfBlock) {
        if (visited.contains(cfBlock)) return;
        visited.add(cfBlock);

        LinkedList<InnerCFNode> ts = cfBlock.getTS();
        for (InnerCFNode node : ts) {
            node.accept(this);
        }

        for (OuterCFNode neighbor : cfBlock.dfsTraverse()) {
            neighbor.accept(this);
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        if (visited.contains(cfConditional)) return;
        visited.add(cfConditional);

        LinkedList<InnerCFNode> ts = cfConditional.getTS();
        for (InnerCFNode node : ts) {
            node.accept(this);
        }

        for (OuterCFNode neighbor : cfConditional.dfsTraverse()) {
            neighbor.accept(this);
        }
    }

    @Override
    public void visit(CFNop cfNop) {
        if (visited.contains(cfNop)) return;
        visited.add(cfNop);

        for (OuterCFNode neighbor : cfNop.dfsTraverse()) {
            neighbor.accept(this);
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
        if (visited.contains(cfReturn)) return;
        visited.add(cfReturn);

        LinkedList<InnerCFNode> ts = cfReturn.getTS();
        for (InnerCFNode node : ts) {
            node.accept(this);
        }

        for (OuterCFNode neighbor : cfReturn.dfsTraverse()) {
            neighbor.accept(this);
        }
    }

    @Override
    public void visit(InnerCFBlock cfBlock) {
        eliminateDeadCodeInnerCFBlock(cfBlock);
    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
    }

    @Override
    public void visit(InnerCFNop cfNop) {
    }
}
