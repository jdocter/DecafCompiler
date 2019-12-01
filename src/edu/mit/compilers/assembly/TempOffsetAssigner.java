package edu.mit.compilers.assembly;

import java.util.HashSet;
import java.util.Set;

import java.util.List;

import edu.mit.compilers.cfg.OuterCFNode;
import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.util.Pair;


public class TempOffsetAssigner {
    /**
     * Assigns every temp a different offset for now.
     *
     * When we rewrite this, we can make it better
     */

    public long tempOffsetStart;
    public long maxTempOffset;
    private OuterCFNode methodCFGStart;

    private final Set<OuterCFNode> visited = new HashSet<>();

    public TempOffsetAssigner(MethodDescriptor methodDescriptor, long maxMethodStackOffset) {
        tempOffsetStart = maxMethodStackOffset;
        maxTempOffset = tempOffsetStart;
        this.methodCFGStart = methodDescriptor.getMethodCFG();
    }

    public long populate() {
        visitNode(methodCFGStart);
        return maxTempOffset;
    }

    private void visitNode(OuterCFNode node) {
        if (visited.contains(node)) return;
        else visited.add(node);

        Set<Temp> tempsSeen = new HashSet<>();
        long blockTempOffset = tempOffsetStart;
        for (Pair<List<Temp>, List<Temp>> temps : node.getTemps()) {
            List<Temp> dests = temps.getKey();
            for (Temp dest : dests) {
                if (!tempsSeen.contains(dest)) {
                    tempsSeen.add(dest);
                    long newOffset = blockTempOffset + Temp.TEMP_SIZE;
                    dest.setOffset(newOffset);
                    blockTempOffset = newOffset;
                }
            }
            // temps never used before they are assigned
            for (Temp other : temps.getValue()) {
                // System.err.println("Seen: " + tempsSeen + "\nOther: " + other + "\nNode: " + node);
                assert tempsSeen.contains(other);
            }

        }
        if (blockTempOffset > maxTempOffset) {
            maxTempOffset = blockTempOffset;
        }

        for (OuterCFNode child: node.dfsTraverse()) {
            visitNode(child);
        }
    }
}
