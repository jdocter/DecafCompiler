package edu.mit.compilers.liveness;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.HashSet;

public class AssemblyVariablesCollector implements CFVisitor {

    private final HashSet<AssemblyVariable> assemblyVariables = new HashSet<>();
    private final HashSet<OuterCFNode> visited = new HashSet<>();
    private final OuterCFNode start;


    AssemblyVariablesCollector(OuterCFNode start) {
        this.start= start;
    }

    public HashSet<AssemblyVariable> getAssemblyVariables() {
        assemblyVariables.clear();
        visited.clear();
        start.accept(this);
        return assemblyVariables;
    }

    private void visit(InnerCFNode innerCFNode) {
        assemblyVariables.addAll(innerCFNode.getLocalAssemblyVariables());
        for (InnerCFNode child: innerCFNode.dfsTraverse()) {
            visit(innerCFNode);
        }
    }
    @Override
    public void visit(CFBlock cfBlock) {
        if (visited.contains(cfBlock)) return;
        visited.add(cfBlock);
        assemblyVariables.addAll(cfBlock.getOuterAssemblyVariables());
        visit(cfBlock.getMiniCFGStart());
        for (OuterCFNode child: cfBlock.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        if (visited.contains(cfConditional)) return;
        visited.add(cfConditional);
        assemblyVariables.addAll(cfConditional.getOuterAssemblyVariables());
        visit(cfConditional.getMiniCFGStart());
        for (OuterCFNode child: cfConditional.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFNop cfNop) {
        if (visited.contains(cfNop)) return;
        visited.add(cfNop);
        for (OuterCFNode child: cfNop.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
        if (visited.contains(cfReturn)) return;
        visited.add(cfReturn);
        assemblyVariables.addAll(cfReturn.getOuterAssemblyVariables());
        visit(cfReturn.getMiniCFGStart());
        for (OuterCFNode child: cfReturn.dfsTraverse()) {
            child.accept(this);
        }
    }
}
