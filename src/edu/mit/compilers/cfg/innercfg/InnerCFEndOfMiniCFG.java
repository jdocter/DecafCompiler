package edu.mit.compilers.cfg.innercfg;

import edu.mit.compilers.cfg.CFNode;

public class InnerCFEndOfMiniCFG extends InnerCFNop {
    private CFNode enclosingNode;

    public InnerCFEndOfMiniCFG(CFNode enclosingNode) {
        this.enclosingNode = enclosingNode;
    }

    public CFNode getEnclosingNode() {
        return enclosingNode;
    }

    @Override
    public String toString() {
        return "UID " + UID + " CFEndOfMiniCFG [next=" + enclosingNode.getUID() + "]";
    }

}
