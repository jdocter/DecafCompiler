package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.ProgramDescriptor;
import edu.mit.compilers.parser.Block;
import edu.mit.compilers.parser.MethodDeclaration;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MergeBasicBlocksAndRemoveNops;

import java.util.List;

public class AssemblyGen {

    public List<String> programAssemblyGen(ProgramDescriptor programDescriptor) {
        // global fields
        // something about main function
        for (MethodDescriptor methodDescriptor: programDescriptor.methodTable.values()) {
            // maybe create cfg here? maybe not and just pass in as param

            // methodAssemblyGen(methodDescriptor);

        }
        return null;
    }
    public List<String> methodAssemblyGen(MethodDescriptor methodDescriptor) {
        CFNode methodCFGStart = MethodCFGFactory.makeMethodCFG(methodDescriptor.getMethodBlock());
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
