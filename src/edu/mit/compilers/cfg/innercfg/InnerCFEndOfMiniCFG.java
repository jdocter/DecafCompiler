package edu.mit.compilers.cfg.innercfg;

import edu.mit.compilers.cfg.OuterCFNode;

public class InnerCFEndOfMiniCFG extends InnerCFNop {
    private OuterCFNode enclosingNode;

    public InnerCFEndOfMiniCFG(OuterCFNode enclosingNode) {
        this.enclosingNode = enclosingNode;
    }

    public OuterCFNode getEnclosingNode() {
        return enclosingNode;
    }

    @Override
    public String toString() {
        return "UID " + UID + " CFEndOfMiniCFG [next=" + enclosingNode.getUID() + "]";
    }

}
