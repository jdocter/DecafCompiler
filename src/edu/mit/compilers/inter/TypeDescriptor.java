package edu.mit.compilers.inter;

import edu.mit.compilers.parser.Type;

/**
 * Only four types in decaf language
 */
public class TypeDescriptor {
    public static final int INT = 0;
    public static final int BOOL = 1;
    public static final int INT_ARRAY = 2;
    public static final int BOOL_ARRAY = 3;

    private long length;
    public final int type;

    TypeDescriptor(Type type) throws SemanticException {
        switch (type.mType) {
            case Type.BOOL:
                this.type = BOOL;
                break;
            case Type.INT:
                this.type = INT;
                break;
            default: throw new SemanticException(type.getLineNumber(),""); // TODO not sure about line number
        }
    }

    TypeDescriptor(Type arrayType, int length) throws SemanticException {
        switch (arrayType.mType) {
            case Type.BOOL:
                this.type = BOOL_ARRAY;
                break;
            case Type.INT:
                this.type = INT_ARRAY;
                break;
            default: throw new SemanticException(arrayType.getLineNumber(),""); // TODO not sure about line number
        }
        this.length = length;
    }
}
