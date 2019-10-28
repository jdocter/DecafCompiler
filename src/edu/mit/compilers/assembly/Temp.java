package edu.mit.compilers.assembly;

import edu.mit.compilers.util.UIDObject;

public class Temp extends UIDObject {

    private final String name;
    private long stackOffset;
    private boolean offsetDeclared = false;

    Temp() {
        name = "t"+getUID();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Temp && super.equals(obj);
    }

    @Override
    public String toString() {
        return name;
    }

    public void setStackOffset(long stackOffset) {
        this.stackOffset = stackOffset;
        this.offsetDeclared = true;
    }

    public long getStackOffset() {
        if (!offsetDeclared) throw new RuntimeException("stack offset must be set before it can be accessed");
        return stackOffset;
    }
}
