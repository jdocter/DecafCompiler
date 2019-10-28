package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.ProgramDescriptor;

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

        CFNode methodCFG = methodDescriptor.getMethodCFG();

        TempOffsetAssigner tempOffsetAssigner = new TempOffsetAssigner(variablesCount);
        methodCFG.accept(tempOffsetAssigner);
        // LABEL
        // allocate space rsp
        long tempsAndVariablesCount = tempOffsetAssigner.maxTempOffset;
        // move params 1-6 from registers onto stack!!!! important
        // if more params, move params 7... onto stack, access from rbp
        // assemblyGen for CFMethodStart
        List<String> instructions = new MethodAssemblyCollector(methodCFG).getInstructions();
        return null;
    }

}
