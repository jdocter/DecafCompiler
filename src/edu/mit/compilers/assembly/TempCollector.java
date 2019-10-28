package edu.mit.compilers.assembly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.MiniCFVisitor;


public class TempCollector implements MiniCFVisitor {

    Set<CFNode> visited = new HashSet<>();
    public List<Pair<Temp, List<Temp>>> temps = new ArrayList<>();

    private void visitNode(CFNode node) {
        if (!visited.contains(node)) {
            visited.add(node);
            temps.addAll(node.getTemps());
            for (CFNode neighbor : node.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }

    @Override
    public void visit(CFBlock cfBlock) {
        visitNode(cfBlock);
    }

    @Override
    public void visit(CFConditional cfConditional) {
        visitNode(cfConditional);
    }

    @Override
    public void visit(CFNop cfNop) {
        visitNode(cfNop);
    }

    @Override
    public void visit(CFReturn cfReturn) {
        visitNode(cfReturn);
    }

}
