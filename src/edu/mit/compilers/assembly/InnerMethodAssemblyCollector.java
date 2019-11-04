package edu.mit.compilers.assembly;

import edu.mit.compilers.assembly.*;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MiniCFVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InnerMethodAssemblyCollector implements MiniCFVisitor {

    private final Set<InnerCFNode> visited = new HashSet<>();

    private final List<String> instructions = new ArrayList<>();
    private final InnerCFNode cfMethodStart;

    private ImportTable importTable;

    public InnerMethodAssemblyCollector(InnerCFNode cfMethodStart, ImportTable importTable) {
        this.cfMethodStart = cfMethodStart;
        this.importTable = importTable;
        cfMethodStart.accept(this);
    }

    public List<String> getInstructions() {
        return instructions;
    }


    @Override
    public void visit(InnerCFBlock cfBlock) {
        if (visited.contains(cfBlock)) return;
        else visited.add(cfBlock);
        instructions.addAll(cfBlock.toAssembly(importTable));
        for (InnerCFNode child: cfBlock.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        if (visited.contains(cfConditional)) return;
        else visited.add(cfConditional);
        instructions.addAll(cfConditional.toAssembly(importTable));
        for (InnerCFNode child: cfConditional.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        if (visited.contains(cfNop)) return;
        else visited.add(cfNop);
        instructions.addAll(cfNop.toAssembly(importTable));
        for (InnerCFNode child: cfNop.dfsTraverse()) {
            child.accept(this);
        }
    }
}
