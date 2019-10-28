package edu.mit.compilers.assembly;

import edu.mit.compilers.util.UIDObject;

public class Temp extends UIDObject {

    public static final int TEMP_SIZE = 8;

    private final String name;
    private long offset;

    Temp() {
        name = "t"+getUID();
    }

    public String getName() {
        return name;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getOffset() {
        return this.offset;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Temp && super.equals(obj);
    }

    @Override
    public String toString() {
        return name;
    }
}
