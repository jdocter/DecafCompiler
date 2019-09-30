package edu.mit.compilers.inter;

import java.util.HashMap;
import edu.mit.compilers.parser.Type;

public class TypeTable extends HashMap<Integer, TypeDescriptor> {

    public TypeTable() {
        // default TypeTable for Decaf
        this.put(Type.INT, new TypeDescriptor()); // int
        this.put(Type.INT + Type.ARRAY, new TypeDescriptor()); // int[]
        this.put(Type.BOOL, new TypeDescriptor()); // bool
        this.put(Type.BOOL + Type.ARRAY, new TypeDescriptor()); // bool[]
        this.put(Type.STRING, new TypeDescriptor()); // string
        this.put(Type.STRING + Type.ARRAY, new TypeDescriptor()); // string[]
    }
}
