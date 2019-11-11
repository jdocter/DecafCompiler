package edu.mit.compilers.cfg;

import edu.mit.compilers.assembly.*;
import edu.mit.compilers.cfg.innercfg.InnerCFBlock;
import edu.mit.compilers.cfg.innercfg.InnerCFConditional;
import edu.mit.compilers.cfg.innercfg.InnerCFNop;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MiniCFVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MethodAssemblyCollector implements CFVisitor {
    /**
     * To be called AFTER TempifySubExpressions
     */

    private final Set<CFNode> visited = new HashSet<>();

    private final List<String> instructions = new ArrayList<>();
    private final CFNode cfMethodStart;

    private ImportTable importTable;

    public MethodAssemblyCollector(CFNode cfMethodStart, ImportTable importTable) {
        this.cfMethodStart = cfMethodStart;
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
