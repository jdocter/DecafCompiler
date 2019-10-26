package edu.mit.compilers.visitor;

import edu.mit.compilers.assembly.*;

import java.util.ArrayList;
import java.util.List;

public class AssemblyCollector implements CFVisitor {

    private final List<String> instructions = new ArrayList<>();
    private final CFNode cfMethodStart;

    public AssemblyCollector(CFNode cfMethodStart) {
        this.cfMethodStart = cfMethodStart;
    }

    public List<String> getInstructions() {
        cfMethodStart.accept(this);
        return instructions;
    }

    @Override
    public void visit(CFBlock cfBlock) {
        instructions.addAll(cfBlock.toAssembly());
        cfBlock.getNext().accept(this);
    }

    @Override
    public void visit(CFConditional cfConditional) {
        instructions.addAll(cfConditional.toAssembly());
        cfConditional.getIfTrue().accept(this);
        cfConditional.getIfFalse().accept(this);
    }

    @Override
    public void visit(CFNop cfNop) {
        if (cfNop.getNext() != null) {
            cfNop.getNext().accept(this);
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
        instructions.addAll(cfReturn.toAssembly());
    }
}
