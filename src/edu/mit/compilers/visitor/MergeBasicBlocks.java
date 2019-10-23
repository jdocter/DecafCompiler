package edu.mit.compilers.visitor;

import java.util.HashSet;
import java.util.Set;

import edu.mit.compilers.assembly.CFBlock;
import edu.mit.compilers.assembly.CFBreak;
import edu.mit.compilers.assembly.CFConditional;
import edu.mit.compilers.assembly.CFContinue;
import edu.mit.compilers.assembly.CFNode;
import edu.mit.compilers.assembly.CFNop;
import edu.mit.compilers.assembly.CFReturn;


public class MergeBasicBlocks implements CFVisitor {

    Set<CFNode> visited = new HashSet<>();

    @Override
    public void visit(CFBlock cfBlock) {
        // TODO
    }

    @Override
    public void visit(CFBreak cfBreak) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(CFConditional cfConditional) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(CFContinue cfContinue) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(CFNop cfNop) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(CFReturn cfReturn) {
        // TODO Auto-generated method stub

    }

}
