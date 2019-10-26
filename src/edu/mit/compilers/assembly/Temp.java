package edu.mit.compilers.assembly;

import edu.mit.compilers.util.UIDObject;

public class Temp extends UIDObject {

    private final String name;

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
}
