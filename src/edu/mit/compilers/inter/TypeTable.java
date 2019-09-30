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

    private static int switchType(String type) {
        int typeInt;
        switch (type) {
            case "int":
                typeInt = Type.INT;
                break;
            case "int[]":
                typeInt = Type.INT + Type.ARRAY;
                break;
            case "bool":
                typeInt = Type.BOOL;
                break;
            case "bool[]":
                typeInt = Type.BOOL + Type.ARRAY;
                break;
            case "string":
                typeInt = Type.STRING;
                break;
            case "string[]":
                typeInt = Type.STRING + Type.ARRAY;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported type: " + type);
        }
        return typeInt;
    }

    public TypeDescriptor get(String type) {
        int typeInt = switchType(type);
        return this.get(typeInt);
    }

    public TypeDescriptor put(String type, TypeDescriptor descriptor) {
        int typeInt = switchType(type);
        return this.put(typeInt, descriptor);
    }
}
