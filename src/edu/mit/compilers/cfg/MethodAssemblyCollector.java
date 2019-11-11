package edu.mit.compilers.cfg;

import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.visitor.CFVisitor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MethodAssemblyCollector implements CFVisitor {

    private final Set<CFNode> visited = new HashSet<>();

    private final List<String> instructions = new ArrayList<>();

    private ImportTable importTable;

    public MethodAssemblyCollector(CFNode cfMethodStart, ImportTable importTable) {
        this.importTable = importTable;
        cfMethodStart.accept(this);
    }

    public List<String> getInstructions() {
        return instructions;
    }


    @Override
    public void visit(CFBlock cfBlock) {
        if (visited.contains(cfBlock)) return;
        else visited.add(cfBlock);
        instructions.addAll(cfBlock.toAssembly(importTable));
        for (CFNode child: cfBlock.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        if (visited.contains(cfConditional)) return;
        else visited.add(cfConditional);
        instructions.addAll(cfConditional.toAssembly(importTable));
        for (CFNode child: cfConditional.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFNop cfNop) {
        if (visited.contains(cfNop)) return;
        else visited.add(cfNop);
        instructions.addAll(cfNop.toAssembly(importTable));
        for (CFNode child: cfNop.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
        if (visited.contains(cfReturn)) return;
        else visited.add(cfReturn);
        instructions.addAll(cfReturn.toAssembly(importTable));
    }
}
