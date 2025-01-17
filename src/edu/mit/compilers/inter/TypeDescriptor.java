package edu.mit.compilers.inter;

import edu.mit.compilers.parser.Type;

/**
 * Only four types in decaf language
 */
public class TypeDescriptor {
    public static final int INT = Type.INT;
    public static final int BOOL = Type.BOOL;
    public static final int INT_ARRAY = Type.INT + 2;
    public static final int BOOL_ARRAY = Type.BOOL + 2;
    public static final int STRING = 10;

    private long length = 1;
    public final int type;

    @Override public String toString() {
        switch (type) {
            case INT:
                return "INT";
            case BOOL:
                return "BOOL";
            case INT_ARRAY:
                return "INTARRAY";
            case BOOL_ARRAY:
                return "BOOLARRAY";
            case STRING:
                return "STRING";
            default:
                throw new RuntimeException("Unsupported type: " + type);
        }
    }

    public long getLength() {
        return length;
    }

    /**
     * For now 8 bytes for everything
     * @return number of bytes required to store this type in memory
     */
    public long getMemoryLength() {
        switch (type) {
            case INT: return 8;
            case INT_ARRAY: return 8 * length;
            case BOOL: return 8;
            case BOOL_ARRAY: return 8 * length;
            default: throw new RuntimeException("Unimplmented");
        }
    }

    /**
     *
     * @return number of bytes required to
     */
    public int elementSize() {
        switch (type) {
            case INT:
            case INT_ARRAY:
                return 8;
            case BOOL:
            case BOOL_ARRAY:
                return 8; // for now
            default:
                throw new RuntimeException("Unimplmented");
        }
    }

    public boolean isArray() {
        return type == INT_ARRAY || type == BOOL_ARRAY;
    }

    public TypeDescriptor(int type) {
        assert type >= 0 && type <= 3;
        this.type = type;
    }

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

    TypeDescriptor(Type arrayType, long length) throws SemanticException {
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
