package edu.mit.compilers.assembly;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.cfg.innercfg.*;
import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MiniCFVisitor;

import java.util.HashSet;
import java.util.Set;

public class SharedTempOffsetAssigner implements CFVisitor, MiniCFVisitor {


    public long sharedTempOffsetStart;
    public long sharedTempOffsetCount;
    private CFNode methodCFGStart;
    Set<SharedTemp> sharedTemps = new HashSet<>();

    private final Set<CFNode> visited = new HashSet<>();

    public SharedTempOffsetAssigner(MethodDescriptor methodDescriptor, long maxMethodStackOffset) {
        sharedTempOffsetStart = maxMethodStackOffset;
        sharedTempOffsetCount = sharedTempOffsetStart;
        this.methodCFGStart = methodDescriptor.getMethodCFG();
    }

    public long populate() {
        methodCFGStart.accept(this);
        return sharedTempOffsetCount;
    }

    @Override
    public void visit(CFBlock cfBlock) {
        if (visited.contains(cfBlock)) return;
        else visited.add(cfBlock);
        cfBlock.getMiniCFGStart().accept(this);

        for (CFNode child : cfBlock.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        if (visited.contains(cfConditional)) return;
        else visited.add(cfConditional);
        cfConditional.getMiniCFGStart().accept(this);

        for (CFNode child : cfConditional.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFNop cfNop) {
        if (visited.contains(cfNop)) return;
        else visited.add(cfNop);

        for (CFNode child : cfNop.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
        if (visited.contains(cfReturn)) return;
        else visited.add(cfReturn);
        cfReturn.getMiniCFGStart().accept(this);

        for (CFNode child : cfReturn.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(InnerCFBlock cfBlock) {
        for (CFStatement cfStatement : cfBlock.getCfStatements()) {
            for (SharedTemp sharedTemp : cfStatement.getSharedTemps()) {
                if (!sharedTemps.contains(sharedTemp)) {
                    sharedTemps.add(sharedTemp);
                    sharedTempOffsetCount += sharedTemp.getElementSize();
                    sharedTemp.setOffset(sharedTempOffsetCount);
                }
            }
        }
    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        for (InnerCFNode child : cfConditional.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        for (InnerCFNode child : cfNop.dfsTraverse()) {
            child.accept(this);
        }
    }
}
