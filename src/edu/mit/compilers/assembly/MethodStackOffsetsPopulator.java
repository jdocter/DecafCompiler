package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.LocalDescriptor;
import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.parser.*;

public class MethodStackOffsetsPopulator {
    /**
     * Basically extends ASTVisitor
     */


    private final MethodDescriptor methodDescriptor;
    private long offsetCount = 0;

    /**
     * Create new PopulateMethodStackOffsets - Object that populates method stack offsets for variables
     * Requires that all Blocks have nonnull references to local tables
     * @param methodDescriptor
     */
    MethodStackOffsetsPopulator(MethodDescriptor methodDescriptor) {
        this.methodDescriptor = methodDescriptor;
    }

    /**
     * perform stack offset population
     * @return total number of variables in this method (including parameters and locals)
     *         where a shadowed variable is considered unique
     */
    long populate() {
        populate(methodDescriptor.getMethodBlock());
        return offsetCount;
    }


    private void populate(Statement statement) {
        switch (statement.statementType) {
            case Statement.IF:
                populate(statement.ifBlock);
                if (statement.elseBlock != null) {
                    populate(statement.elseBlock);
                }
                break;
            case Statement.FOR:
            case Statement.WHILE:
                populate(statement.block);
                break;
            case Statement.RETURN:
            case Statement.LOC_ASSIGN:
            case Statement.METHOD_CALL:
                break;
        }
    }

    private void populate(Block block) {
        for (LocalDescriptor localDescriptor: block.localTable.values()) {
            offsetCount += localDescriptor.getTypeDescriptor().getMemoryLength();
            localDescriptor.setStackOffset(offsetCount);
        }
        for (Statement statement: block.statements) {
            populate(statement);
        }
    }
}
