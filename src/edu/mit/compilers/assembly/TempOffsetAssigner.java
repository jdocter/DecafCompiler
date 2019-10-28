package edu.mit.compilers.assembly;

import java.util.HashSet;
import java.util.Set;

import java.util.List;

import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.CFVisitor;


public class TempOffsetAssigner implements CFVisitor {
    /**
     * Assigns every temp a different offset for now.
     *
     * When we rewrite this, we can make it better
     */

    public long tempOffsetStart;
    public long maxTempOffset;

    private final Set<CFNode> visited = new HashSet<>();

    public TempOffsetAssigner(long maxMethodStackOffset) {
        tempOffsetStart = maxMethodStackOffset;
        maxTempOffset = tempOffsetStart;
    }

    private void visitNode(CFNode node) {
        if (visited.contains(node)) return;
        else visited.add(node);

        Set<Temp> tempsSeen = new HashSet<>();
        long blockTempOffset = tempOffsetStart;
        for (Pair<Temp, List<Temp>> temps : node.getTemps()) {
            Temp dest = temps.getKey();
            if (!tempsSeen.contains(dest)) {
                tempsSeen.add(dest);
                long newOffset = blockTempOffset + Temp.TEMP_SIZE;
                dest.setOffset(newOffset);
                blockTempOffset = newOffset;
            }
            // temps never used before they are assigned
            for (Temp other : temps.getValue()) assert tempsSeen.contains(other);
        }
        if (blockTempOffset > maxTempOffset) {
            maxTempOffset = blockTempOffset;
        }

        for (CFNode child: node.dfsTraverse()) {
            child.accept(this);
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
