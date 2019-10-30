package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.*;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.StringLit;
import edu.mit.compilers.parser.Type;
import edu.mit.compilers.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AssemblyFactory {

    public static String INDENTATION = "\t";

    public static List<String> indent(List<String> body) {
        return body.stream()
                .map((String line) -> {return INDENTATION + line;})
                .collect(Collectors.toList());
    }

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
            assembly.addAll(List.of(stringLit.getLabel()+":",
				    INDENTATION + ".string " + stringLit,
				    INDENTATION + ".align 16"));
        }

        // global fields
        for (FieldDescriptor fieldDescriptor: programDescriptor.fieldTable.values()) {
            // .comm _global_fieldname, bytes, alignment
            assembly.addAll(List.of(".comm " + fieldDescriptor.getGlobalLabel() +", "
                    + fieldDescriptor.getTypeDescriptor().getMemoryLength() + ", 16"));
        }

        // runtime exception strings
        for (MethodDescriptor methodDescriptor : programDescriptor.methodTable.values()) {
            String methodName = methodDescriptor.getMethodName();
            assembly.add("_sp_field_runtime_error_2_" + methodName + ":");
            assembly.add(INDENTATION
                    + ".string \"*** RUNTIME ERROR ***: No return value from non-void method \\\""
                    + methodName
                    + "\\\"\"");
            assembly.add(INDENTATION + ".align 16");
            assembly.add("");
        }

        ImportTable importTable = programDescriptor.importTable;
        // something about main function
        for (MethodDescriptor methodDescriptor: programDescriptor.methodTable.values()) {
            assembly.add("");
            assembly.addAll(methodAssemblyGen(methodDescriptor, importTable));
        }
        return assembly;
    }

    private static List<String> methodAssemblyGen(MethodDescriptor methodDescriptor, ImportTable importTable) {
        List<String> assembly = new ArrayList<>();
        // populate stack offset and get count (in methodDescriptor.block)
        long totalLocalBytes = new MethodStackOffsetsPopulator(methodDescriptor).populate();

        // populate temp offsets
        TempOffsetAssigner tempOffsetAssigner = new TempOffsetAssigner(totalLocalBytes);
        CFNode methodCFG = methodDescriptor.getMethodCFG();
        methodCFG.accept(tempOffsetAssigner);

        // LABEL
        if (methodDescriptor.getMethodName().equals("main")) {
            assembly.add(INDENTATION + ".globl main");
            assembly.add("main:");
        } else {
            assembly.add("_method_" + methodDescriptor.getMethodName()+":");
        }


        List<String> prologue = new ArrayList<>();

        // allocate space rsp
        long totalMethodBytes = tempOffsetAssigner.maxTempOffset;
        long methodOffset = (long) Math.ceil(totalMethodBytes/16.0) * 16;

        prologue.add("enter $("+methodOffset+"), $0");

        // initialize all locals to 0
        for (int b = 8; b <= totalMethodBytes; b+= 8) {
            prologue.add("movq $0, -" +b +"(%rbp)");
        }

        // move parameters
        LocalTable localTable = methodDescriptor.getLocalTable();
        for (int p = 0; p < methodDescriptor.getParams().size(); p++) {
            String paramName = methodDescriptor.getParams().get(p).getValue().getName();
            long paramOffset = localTable.get(paramName).getStackOffset();
            if (p < 6) {
                // move params 1-6 from registers onto stack
                prologue.add("movq "+Reg.methodParam(p+1)+ ", -" + paramOffset + "(%rbp)");
            } else {
                // if more params, move params 7... onto stack, access from rbp
                long argOffset = (p - 6)*8 + 16; // param 7 found at 16(%rbp)
                prologue.add("movq "+ argOffset+"(%rbp), %rax");
                prologue.add("movq %rax, -" + paramOffset + "(%rbp)");
            }
        }
        // indent prologue and add to assembly
        assembly.addAll(AssemblyFactory.indent(prologue));

        // assemblyGen for CFMethodStart
        List<String> methodBodyAssembly = new MethodAssemblyCollector(methodCFG, importTable).getInstructions();
        assembly.addAll(methodBodyAssembly);

        return assembly;
    }

}
