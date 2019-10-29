package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.*;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.StringLit;
import edu.mit.compilers.parser.Type;
import edu.mit.compilers.util.Pair;

import java.util.ArrayList;
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
        List<String> assembly = new ArrayList<>();

        // string literals
        for (StringLit stringLit: programDescriptor.stringLits) {
            // _string_name:
            //      .string "name"
            //      .align 16
            assembly.addAll(List.of("_string_"+stringLit+":", "\t.string \"" + stringLit +"\"", "\t.align 16"));
        }

        // global fields
        for (FieldDescriptor fieldDescriptor: programDescriptor.fieldTable.values()) {
            // .comm _global_fieldname, bytes, alignment
            assembly.addAll(List.of(".comm _global_"+fieldDescriptor.getName() +", "
                    + fieldDescriptor.getTypeDescriptor().getMemoryLength() + ", 16"));
        }

        // something about main function
        for (MethodDescriptor methodDescriptor: programDescriptor.methodTable.values()) {
            assembly.add("\n");
            assembly.addAll(methodAssemblyGen(methodDescriptor));
        }
        return assembly;
    }

    private static List<String> methodAssemblyGen(MethodDescriptor methodDescriptor) {
        List<String> assembly = new ArrayList<>();
        // populate stack offset and get count (in methodDescriptor.block)
        long totalLocalBytes = new MethodStackOffsetsPopulator(methodDescriptor).populate();

        // populate temp offsets
        TempOffsetAssigner tempOffsetAssigner = new TempOffsetAssigner(totalLocalBytes);
        CFNode methodCFG = methodDescriptor.getMethodCFG();
        methodCFG.accept(tempOffsetAssigner);

        // LABEL
        if (methodDescriptor.getMethodName().equals("main")) {
            assembly.add("\t.globl main");
            assembly.add("main:");
        } else {
            assembly.add("_method_" + methodDescriptor.getMethodName()+":");
        }


        List<String> prologue = new ArrayList<>();

        // allocate space rsp
        long totalMethodBytes = tempOffsetAssigner.maxTempOffset;
        long methodOffset = (long) Math.ceil(totalMethodBytes/16.0) * 16;

        // move parameters
        prologue.add("enter $("+methodOffset+"), $0");
        LocalTable localTable = methodDescriptor.getLocalTable();
        for (int p = 0; p < methodDescriptor.getParams().size(); p++) {
            String paramName = methodDescriptor.getParams().get(p).getValue().getName();
            long paramOffset = localTable.get(paramName).getStackOffset();
            if (p < 6) {
                // move params 1-6 from registers onto stack
                prologue.add("movq "+Reg.methodParam(p+1)+ ", -" + paramOffset + "(%rbp)");
            } else {
                // if more params, move params 7... onto stack, access from rbp
                long argOffset = (p+1)*8 + 8;
                prologue.add("movq "+ argOffset+"(%rbp), %rax");
                prologue.add("movq %rax, -" + paramOffset + "(%rbp)");
            }
        }
        // indent prologue and add to assembly

        // assemblyGen for CFMethodStart
        List<String> methodBodyAssembly = new MethodAssemblyCollector(methodCFG).getInstructions();
        assembly.addAll(methodBodyAssembly);

        // do we need leave? ret?

        return null;
    }

}
