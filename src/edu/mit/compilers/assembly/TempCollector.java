package edu.mit.compilers.assembly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.cfg.innercfg.InnerCFBlock;
import edu.mit.compilers.cfg.innercfg.InnerCFConditional;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.cfg.innercfg.InnerCFNop;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.MiniCFVisitor;


public class TempCollector implements MiniCFVisitor {

    Set<InnerCFNode> visited = new HashSet<>();
    public List<Pair<Temp, List<Temp>>> temps = new ArrayList<>();

    private void visitNode(InnerCFNode node) {
        if (!visited.contains(node)) {
            visited.add(node);
            temps.addAll(node.getTemps());
            for (InnerCFNode neighbor : node.dfsTraverse()) {
                neighbor.accept(this);
            }
        }
    }

    @Override
    public void visit(InnerCFBlock cfBlock) {
        visitNode(cfBlock);
    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        visitNode(cfConditional);
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        visitNode(cfNop);
    }

}
