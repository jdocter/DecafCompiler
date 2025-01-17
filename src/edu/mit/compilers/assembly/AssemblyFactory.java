package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.*;
import edu.mit.compilers.parser.StringLit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AssemblyFactory {

    public static String INDENTATION = "\t";
    public static String GLOBAL_EXIT_CODE = "_sp_exit_code";
    public static String METHOD_EXIT ="_sp_method_premature_return";
    public static String METHOD_EXIT_1 ="_sp_method_exit_with_status_1";
    public static String METHOD_EXIT_2 ="_sp_method_exit_with_status_2";

    public static List<String> indent(List<String> body) {
        return body.stream()
                .map((String line) -> {return INDENTATION + line;})
                .collect(Collectors.toList());
    }
    public static String indent(String s) {
        return INDENTATION + s;
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

        // flag for exit with error code in rax
        assembly.add(".comm " + GLOBAL_EXIT_CODE + ", 8, 16");

        //  METHOD_EXIT, leave, ret
        assembly.add(METHOD_EXIT+":");
        assembly.add(INDENTATION + "leave");
        assembly.add(INDENTATION + "ret");
        assembly.add("");

        // METHOD_EXIT_1
        assembly.add(METHOD_EXIT_1+":");
        assembly.addAll(indent(List.of(
                "# arraybounds check fail",
                "leaq _sp_field_runtime_error_1(%rip), %rdi",
                "call printf",
                "",
                "movq $1, "+GLOBAL_EXIT_CODE + "(%rip)",
                "movq $1, %rax",
                "leave",
                "ret",
                ""
                )));

        // METHOD_EXIT_2

        assembly.add(METHOD_EXIT_2+":");
        assembly.addAll(indent(List.of(
                "# returning void in a function supposed to return a value",
                "leaq _sp_field_runtime_error_2(%rip), %rdi",
                "call printf",
                "",
                "movq $2, "+GLOBAL_EXIT_CODE + "(%rip)",
                "movq $2, %rax",
                "leave",
                "ret",
                ""
                )));

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
        assembly.add("_sp_field_runtime_error_1:");
        assembly.add(INDENTATION
                + ".string \"*** RUNTIME ERROR ***: Array index out of bounds\\n\"");
        assembly.add(INDENTATION + ".align 16");
        assembly.add("");

        assembly.add("_sp_field_runtime_error_2:");
        assembly.add(INDENTATION
                + ".string \"*** RUNTIME ERROR ***: Control fell off of a non-void method\\n\"");
        assembly.add(INDENTATION + ".align 16");
        assembly.add("");

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
        long localVariableOffset = new MethodStackOffsetsPopulator(methodDescriptor).populate();

        // assign stack offset for shared temps and get running stack count
        long sharedTempOffset = new SharedTempOffsetAssigner(methodDescriptor, localVariableOffset).populate();

        // populate temp offsets
        long maxTempOffset = new TempOffsetAssigner(methodDescriptor, sharedTempOffset).populate();


        // LABEL
        if (methodDescriptor.getMethodName().equals("main")) {
            assembly.add(INDENTATION + ".globl main");
            assembly.add("main:");
        } else {
            assembly.add("_method_" + methodDescriptor.getMethodName()+":");
        }


        List<String> prologue = new ArrayList<>();

        // allocate space rsp
        long totalMethodBytes = maxTempOffset;
        long methodOffset = (long) Math.ceil(totalMethodBytes/16.0) * 16;

        prologue.add("enter $("+methodOffset+"), $0");

        // initialize all locals to 0
        for (int b = 8; b <= totalMethodBytes; b+= 8) {
            prologue.add("movq $0, -" +b +"(%rbp)");
        }

        // save callee-saved registers so that we can
        // use them for register allocation
        prologue.add("# save callee-save registers");
        for (Reg callee : Reg.usableCalleeSaved) {
            prologue.add("pushq " + callee.toString());
            prologue.add("movq $0, " + callee.toString());
        }
        prologue.add("pushq " + Reg.R15 + " # Dummy push to maintain 16-alignment");

        // move parameters
        LocalTable localTable = methodDescriptor.getLocalTable();
        for (int p = 0; p < methodDescriptor.getParams().size(); p++) {
            String paramName = methodDescriptor.getParams().get(p).getValue().getName();
            long paramOffset = localTable.get(paramName).getStackOffset(); // no array parameters
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
        List<String> methodBodyAssembly = new MethodAssemblyGenerator(methodDescriptor.getMethodCFG(), importTable).getInstructions();
        assembly.addAll(methodBodyAssembly);

        return assembly;
    }

}
