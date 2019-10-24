package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.ProgramDescriptor;
import edu.mit.compilers.parser.Block;
import edu.mit.compilers.parser.MethodDeclaration;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MergeBasicBlocksAndRemoveNops;

import java.util.List;

public class AssemblyFactory {

    /**
     *
     * @param programDescriptor that has populated MethodCFGs
     * @return
     */
    public static List<String> programAssemblyGen(ProgramDescriptor programDescriptor) {
        for (MethodDescriptor methodDescriptor: programDescriptor.methodTable.values()) {
            if (methodDescriptor.getMethodCFG() == null) {
                throw new RuntimeException("Method CFGs must be created before converting to assembly");
            }
        }
        // global fields
        // something about main function
        for (MethodDescriptor methodDescriptor: programDescriptor.methodTable.values()) {

            // methodAssemblyGen(methodDescriptor);

        }
        return null;
    }

    private static List<String> methodAssemblyGen(MethodDescriptor methodDescriptor) {
        // populate stack offset and get count (in methodDescriptor.block)
        long variablesCount = new MethodStackOffsetsPopulator(methodDescriptor).populate();
        // possibly get largest temp depth?
        // LABEL
        // allocate space rsp
        // move params 1-6 from registers onto stack!!!! important
        // if more params, move params 7... onto stack, access from rbp
        // assemblyGen for CFMethodStart
        return null;
    }
}
