package edu.mit.compilers.assembly;

import edu.mit.compilers.cfg.*;
import edu.mit.compilers.cfg.innercfg.*;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.BinOp;
import edu.mit.compilers.parser.StringLit;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MiniCFVisitor;
import edu.mit.compilers.visitor.StatementCFVisitor;

import java.util.*;

public class MethodAssemblyGenerator implements CFVisitor, MiniCFVisitor, StatementCFVisitor {
    /**
     * To be called AFTER TempifySubExpressions
     */

    private final Set<CFNode> visited = new HashSet<>();

    private final Set<InnerCFNode> innerVisited = new HashSet<>();

    private Optional<VariableTable> variableTable = Optional.empty();

    private final List<String> instructions = new ArrayList<>();

    private ImportTable importTable;

    public MethodAssemblyGenerator(CFNode cfMethodStart, ImportTable importTable) {
        this.importTable = importTable;
        cfMethodStart.accept(this);
    }

    public List<String> getInstructions() {
        return instructions;
    }


    @Override
    public void visit(CFBlock cfBlock) {
        if (visited.contains(cfBlock)) return;
        else visited.add(cfBlock);

        instructions.add(cfBlock.getAssemblyLabel() + ":");
        cfBlock.getMiniCFGStart().accept(this);
        instructions.add(cfBlock.getEndOfMiniCFGLabel() + ":");

        List<String> body = new ArrayList<>();
        if (!cfBlock.isEnd()) {
            body.add("jmp " + cfBlock.getNext().getAssemblyLabel());
        } else {
            throw new RuntimeException("Didn't expect a CFBlock to end the CFG");
        }

        instructions.addAll(AssemblyFactory.indent(body));

        for (CFNode child: cfBlock.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFConditional cfConditional) {
        if (visited.contains(cfConditional)) return;
        else visited.add(cfConditional);


        instructions.add(cfConditional.getAssemblyLabel() + ":");
        cfConditional.getMiniCFGStart().accept(this);
        instructions.add(cfConditional.getEndOfMiniCFGLabel() + ":");

        List<String> body = new ArrayList<>();

        if (cfConditional.getBoolTemp() == null) {
            throw new UnsupportedOperationException("You must call TempifySubExpressions on a CFConditional before collecting Assembly code!");
        }

        body.add("cmpq $1, -" + cfConditional.getBoolTemp().getOffset() + "(%rbp) # true = " + cfConditional.getBoolTemp());
        body.add("jne " + cfConditional.getIfFalse().getAssemblyLabel() + " # ifFalse");
        body.add("jmp " + cfConditional.getIfTrue().getAssemblyLabel() + " # ifTrue");

        instructions.addAll(AssemblyFactory.indent(body));


        for (CFNode child: cfConditional.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFNop cfNop) {
        if (visited.contains(cfNop)) return;
        else visited.add(cfNop);

        List<String> body = new ArrayList<>();
        if (!cfNop.isEnd()) {
            body.add("jmp " + cfNop.getNext().getAssemblyLabel());
        } else {
            // should have been end of MiniCFG
            throw new UnsupportedOperationException("CFNop should have been constructed as a CFEndOfMiniCFG");
        }

        instructions.add(cfNop.getAssemblyLabel() + ":");
        instructions.addAll(AssemblyFactory.indent(body));

        for (CFNode child: cfNop.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFReturn cfReturn) {
        if (visited.contains(cfReturn)) return;
        else visited.add(cfReturn);

        List<String> body = new ArrayList<>();
        boolean shouldReturnVoid = cfReturn.shouldReturnVoid();
        instructions.add(cfReturn.getAssemblyLabel() + ":");
        if (cfReturn.isVoid()) {
            if (!shouldReturnVoid) {
                // generate runtime error
                instructions.add(AssemblyFactory.indent(""));
                instructions.add(AssemblyFactory.indent("jmp " + AssemblyFactory.METHOD_EXIT_2 + " # runtime error: 2")); // return code 2
                return;
            } else {
                // return
                instructions.add(AssemblyFactory.indent("# return void"));
                instructions.add(AssemblyFactory.indent("movq $0, %rax"));
            }
        } else {
            if (shouldReturnVoid) throw new RuntimeException("semantic checks failed");
            // calculate expr and return it
            instructions.add(AssemblyFactory.indent("# calculating return Expr"));
            cfReturn.getMiniCFGStart().accept(this);
            instructions.add(cfReturn.getEndOfMiniCFGLabel() + ":");
            instructions.add(AssemblyFactory.indent(""));
            instructions.add(AssemblyFactory.indent("movq -" + cfReturn.getReturnTemp().getOffset() + "(%rbp), %rax"));
        }

        instructions.add(AssemblyFactory.indent(""));
        instructions.add(AssemblyFactory.indent("leave"));
        instructions.add(AssemblyFactory.indent("ret"));

    }

    @Override
    public void visit(InnerCFBlock cfBlock) {
        if (innerVisited.contains(cfBlock)) return;
        else innerVisited.add(cfBlock);

        instructions.add(cfBlock.getAssemblyLabel() + ":");
        variableTable = Optional.of(cfBlock.getVariableTable());
        for (CFStatement cfStatement : cfBlock.getCfStatements()) {
            cfStatement.accept(this);
        }
        variableTable = Optional.empty();

        if (!cfBlock.isEnd()) {
            instructions.add(AssemblyFactory.indent("jmp " + cfBlock.getNext().getAssemblyLabel()));
        } else {
            throw new RuntimeException("Didn't expect a CFBlock to end the CFG");
        }


        for (InnerCFNode child: cfBlock.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(InnerCFConditional cfConditional) {
        if (innerVisited.contains(cfConditional)) return;
        else innerVisited.add(cfConditional);

        VariableTable variableTable = cfConditional.getVariableTable();
        instructions.add(cfConditional.getAssemblyLabel() + ":");
        List<String> body = new ArrayList<>();
        if (cfConditional.getBoolTemp().isGlobal(variableTable)) {
            // TODO not tested
            body.add("cmpq $1, " + cfConditional.getBoolTemp().getGlobalLabel(variableTable) + "(%rip) # true = " + cfConditional.getVariableTable());
        } else {
            body.add("cmpq $1, -" + cfConditional.getBoolTemp().getStackOffset(variableTable) + "(%rbp) # true = " + cfConditional.getVariableTable());
        }
        body.add("jne " + cfConditional.getIfFalse().getAssemblyLabel() + " # ifFalse");
        body.add("jmp " + cfConditional.getIfTrue().getAssemblyLabel() + " # ifTrue");

        instructions.addAll(AssemblyFactory.indent(body));

        for (InnerCFNode child: cfConditional.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(InnerCFNop cfNop) {
        if (innerVisited.contains(cfNop)) return;
        else innerVisited.add(cfNop);

        List<String> body = new ArrayList<>();
        instructions.add(cfNop.getAssemblyLabel() + ":");
        if (!cfNop.isEnd()) {
          instructions.add(AssemblyFactory.indent("jmp " + cfNop.getNext().getAssemblyLabel()));
        } else if (cfNop instanceof InnerCFEndOfMiniCFG) {
          instructions.add(AssemblyFactory.indent("jmp " + ((InnerCFEndOfMiniCFG) cfNop).getEnclosingNode().getEndOfMiniCFGLabel()));
        } else {
            // should have been end of MiniCFG
            throw new UnsupportedOperationException("CFNop should have been constructed as a CFEndOfMiniCFG");
        }

        for (InnerCFNode child: cfNop.dfsTraverse()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(CFAssign cfAssign) {
        List<String> assembly = new ArrayList<>();
        Pair<List<String>, String> dst = dstToAssembly(cfAssign, variableTable.get());
        assembly.addAll(dst.getKey());
        assembly.addAll(srcToAssembly(cfAssign, variableTable.get(), dst.getValue()));

        instructions.addAll(AssemblyFactory.indent(assembly));
    }

    @Override
    public void visit(CFMethodCall cfMethodCall) {
        // push stack according to size of arguments

        List<String> body = new ArrayList<>();
        body.add("# " + cfMethodCall.toString());

        int stackArgs = cfMethodCall.arguments.size() - 6;

        // this has to be done before pushing args onto stack
        // maintain 16-byte alignment -- assuming 16 aligned before call
        if (stackArgs > 0 && stackArgs % 2 != 0) {
            stackArgs++;
            body.add("push %rax # dummy argument used to maintain 16-byte alignment");
        }

        for (int p = cfMethodCall.arguments.size(); p > 0; p--) {

            Pair<Temp, StringLit> param = cfMethodCall.arguments.get(p - 1);
            // push arguments in reverse order
            if (p <= 6) {
                Reg target = Reg.methodParam(p);
                if (param.getKey() != null) {
                    body.add("movq -" + param.getKey().getOffset() + "(%rbp), " + target);
                } else {
                    body.add("leaq " + param.getValue().getLabel() + "(%rip), " + target);
                }
            } else {
                if (param.getKey() != null) {
                    body.add("push -" + param.getKey().getOffset() + "(%rbp)");
                } else {
                    body.add("leaq " + param.getValue().getLabel() + "(%rip), %rax");
                    body.add("push %rax");
                }
            }
        }

        String callTarget;
        if (cfMethodCall.methodName.getName().equals("main") || importTable.containsKey(cfMethodCall.methodName.getName())) {
            callTarget = cfMethodCall.methodName.getName();
        } else {
            callTarget = "_method_" + cfMethodCall.methodName.getName();
        }
        body.add("call " + callTarget);

        if (stackArgs > 0) {
            body.add("addq $" + stackArgs * 8 + ", %rsp");
        }

        body.add("cmpq $0, " + AssemblyFactory.GLOBAL_EXIT_CODE);
        body.add("jne " + AssemblyFactory.METHOD_EXIT + " # premature exit if the call resulted in runtime exception");

        instructions.addAll(AssemblyFactory.indent(body));
    }


    /**
     * Produces assembly for destination of assignment. May mutate %rcx, %rdi
     * @param variableTable
     * @return string of destination in assembly, memory access, may use %rcx and %rdi
     */
    private static Pair<List<String>, String> dstToAssembly(CFAssign cfAssign, VariableTable variableTable) {

        List<String> assembly = new ArrayList<>();
        String dst;
        if (cfAssign.dstArrayOrLoc.isGlobal(variableTable)) {
            if (cfAssign.dstArrayOffset == null) {
                dst = cfAssign.dstArrayOrLoc.getGlobalLabel(variableTable) + "(%rip)";
            } else {
                assembly.add("movq -" + cfAssign.dstArrayOffset.getStackOffset(variableTable) +"(%rbp), %rdi"); // val of temp into rax

                // array out of bounds
                assembly.add("cmpq $" + cfAssign.dstArrayOrLoc.getArrayLength(variableTable) +", %rdi");
                assembly.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                assembly.add("cmpq $0, %rdi");
                assembly.add("jl " + AssemblyFactory.METHOD_EXIT_1);

                // get dest
                assembly.add("leaq 0(,%rdi," + cfAssign.dstArrayOrLoc.getElementSize(variableTable) + "), %rcx"); // temp * element size
                assembly.add("leaq " + cfAssign.dstArrayOrLoc.getGlobalLabel(variableTable) + ", %rdi"); // address of base of global array
                dst = "(%rcx,%rdi)";
            }
        } else {
            if (cfAssign.dstArrayOffset == null) {
                dst = "-"+ cfAssign.dstArrayOrLoc.getStackOffset(variableTable)+"(%rbp)";
            } else {
                assembly.add("movq -" + cfAssign.dstArrayOffset.getStackOffset(variableTable) +"(%rbp), %rdi"); // val of temp into rdi

                // array out of bounds
                assembly.add("cmpq $" + cfAssign.dstArrayOrLoc.getArrayLength(variableTable) +", %rdi");
                assembly.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                assembly.add("cmpq $0, %rdi");
                assembly.add("jl " + AssemblyFactory.METHOD_EXIT_1);

                // get dest
                dst = "-" + cfAssign.dstArrayOrLoc.getStackOffset(variableTable) +"(%rbp,%rdi,"+cfAssign.dstArrayOrLoc.getElementSize(variableTable)+")";
            }
        }
        return new Pair(assembly, dst);
    }

    private static List<String> srcToAssembly(CFAssign cfAssign, VariableTable variableTable, String dst) {
        List<String> assembly = new ArrayList<>();

        switch (cfAssign.assignOp) { // TODO rax srcTemp?
            case CFAssign.MEQ:
                if (cfAssign.srcOptionalCSE == null) assembly.add("movq -" + cfAssign.srcLeftOrSingle.getStackOffset(variableTable) +  "(%rbp), %rax # " + cfAssign.toString());
                else assembly.add("movq -" + cfAssign.srcOptionalCSE.getStackOffset(variableTable) +  "(%rbp), %rax # " + cfAssign.toString());
                assembly.add("subq %rax, " + dst);
                return assembly;
            case CFAssign.PEQ:
                if (cfAssign.srcOptionalCSE == null) assembly.add("movq -" + cfAssign.srcLeftOrSingle.getStackOffset(variableTable) +  "(%rbp), %rax # " + cfAssign.toString());
                else assembly.add("movq -" + cfAssign.srcOptionalCSE.getStackOffset(variableTable) +  "(%rbp), %rax # " + cfAssign.toString());
                assembly.add("addq %rax, " + dst);
                return assembly;
            case CFAssign.INC: assembly.add("incq " + dst + " # " + cfAssign.toString()); return assembly;
            case CFAssign.DEC: assembly.add("decq " + dst + " # " + cfAssign.toString()); return assembly;
            case CFAssign.ASSIGN:
                if (cfAssign.srcOptionalCSE != null) {
                    assembly.add("movq -" + cfAssign.srcOptionalCSE.getStackOffset(variableTable) +  "(%rbp), %rax # " + cfAssign.toString());
                    assembly.add("movq %rax, " + dst);
                    return assembly;
                }
                break; // everything else should be assign
        }


        switch (cfAssign.getType()) {
            case CFAssign.LEN:
                if (cfAssign.srcId.isArray(variableTable)) {
                    assembly.add("movq $" + cfAssign.srcId.getArrayLength(variableTable) + ", " + dst + " # " + cfAssign.toString());
                    assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "$" + cfAssign.srcId.getArrayLength(variableTable)));
                } else {
                    throw new RuntimeException("Failed semantic checks");
                }
                break;
            case CFAssign.MINUS:
                assembly.add("movq -" + cfAssign.srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax # " + cfAssign);
                assembly.add("negq %rax");
                assembly.add("movq %rax, " + dst);
                assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "%rax"));
                break;
            case CFAssign.NOT:
                assembly.add("movq -" + cfAssign.srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax # " + cfAssign);
                assembly.add("xorq $1, %rax");
                assembly.add("movq %rax, " + dst);
                assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "%rax"));
                break;
            case CFAssign.ARRAY_LOC:
                String arrayLoc;
                assembly.add("movq -" +cfAssign.srcArrayOffset.getStackOffset(variableTable)+"(%rbp), %rax # " + cfAssign); // val of temp into rax
                // array out of bounds
                assembly.add("cmpq $" + cfAssign.srcArray.getArrayLength(variableTable) +", %rax");
                assembly.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                assembly.add("cmpq $0, %rax");
                assembly.add("jl " + AssemblyFactory.METHOD_EXIT_1);
                if (cfAssign.srcArray.isGlobal(variableTable)) {
                    assembly.add("leaq 0(,%rax," + cfAssign.srcArray.getElementSize(variableTable) + "), %rdx"); // temp * element size
                    assembly.add("leaq " + cfAssign.srcArray.getGlobalLabel(variableTable) + "(%rip), %rax"); // address of base of global array
                    arrayLoc = "(%rdx,%rax)";

                } else {
                    arrayLoc = "-"+cfAssign.srcArray.getStackOffset(variableTable)+"(%rbp,%rax,"+cfAssign.srcArray.getElementSize(variableTable)+")";
                }
                assembly.add("movq " + arrayLoc + ", %rsi # " + cfAssign); // is this ok?
                assembly.add("movq %rsi, " + dst);
                assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "%rsi"));

                break;
            case CFAssign.SIMPLE:
                switch (cfAssign.assignOp) { // TODO rax srcTemp?
                    case CFAssign.MEQ:
                        assembly.add(cfAssign.srcLeftOrSingle.isGlobal(variableTable) ?
                                "movq " + cfAssign.srcLeftOrSingle.getGlobalLabel(variableTable) + "(%rip), %rax" + " # " + cfAssign.toString() :
                                "movq -" + cfAssign.srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax" + " # " + cfAssign.toString());
                        assembly.add("subq %rax, " + dst);
                        // can't have additional destination?
                        return assembly;
                    case CFAssign.PEQ:
                        assembly.add(cfAssign.srcLeftOrSingle.isGlobal(variableTable) ?
                                "movq " + cfAssign.srcLeftOrSingle.getGlobalLabel(variableTable) + "(%rip), %rax" + " # " + cfAssign.toString() :
                                "movq -" + cfAssign.srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax" + " # " + cfAssign.toString());
                        assembly.add("addq %rax, " + dst);
                        // can't have additional destination?
                        return assembly;
                    case CFAssign.INC: assembly.add("incq " + dst + " # " + cfAssign.toString()); return assembly;
                    case CFAssign.DEC: assembly.add("decq " + dst + " # " + cfAssign.toString()); return assembly;
                    case CFAssign.ASSIGN:
                        assembly.add(cfAssign.srcLeftOrSingle.isGlobal(variableTable) ?
                                "movq " + cfAssign.srcLeftOrSingle.getGlobalLabel(variableTable) + "(%rip), %rax" + " # " + cfAssign.toString() :
                                "movq -" + cfAssign.srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax" + " # " + cfAssign.toString());
                        assembly.add("movq %rax, " + dst);
                        assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "%rax"));
                        return assembly;
                }
                break;
            case CFAssign.METHOD_CALL:
                assembly.add("");
                assembly.add("movq %rax, " + dst + " # " + cfAssign.toString());
                assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "%rax"));
                assembly.add("");
                break;
            case CFAssign.BIN_OP:
                final String srcLeftString = cfAssign.srcLeftOrSingle.isGlobal(variableTable) ?
                        cfAssign.srcLeftOrSingle.getGlobalLabel(variableTable) + "(%rip)":
                        "-" + cfAssign.srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp)";
                final String srcRightString = cfAssign.srcRight.isGlobal(variableTable) ?
                        cfAssign.srcRight.getGlobalLabel(variableTable) + "(%rip)":
                        "-" + cfAssign.srcRight.getStackOffset(variableTable) + "(%rbp)";
                assembly.add("# "+cfAssign.toString());
                switch (cfAssign.srcBinOp.binOp) {
                    case BinOp.AND:
                    case BinOp.OR:
                        throw new UnsupportedOperationException("Expected to short circuit AND/OR binops, not cmp them");
                    case BinOp.EQ:
                    case BinOp.NEQ:
                    case BinOp.GEQ:
                    case BinOp.LEQ:
                    case BinOp.GT:
                    case BinOp.LT:
                        assembly.add("movq " + srcLeftString + ", %rax");
                        assembly.add("cmpq " + srcRightString + ", %rax");
                        assembly.add(cfAssign.getBinopCommand() + " %al");
                        assembly.add("movzbq %al, %rax");
                        assembly.add("movq %rax, " + dst);
                        assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "%rax"));
                        break;
                    case BinOp.MINUS:
                        assembly.add("movq " + srcLeftString + ", %rax");
                        assembly.add("subq " + srcRightString + ", %rax");
                        assembly.add("movq %rax, " + dst);
                        assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "%rax"));
                        break;
                    case BinOp.PLUS:
                        assembly.add("movq " + srcLeftString + ", %rax");
                        assembly.add("addq " + srcRightString + ", %rax");
                        assembly.add("movq %rax, " + dst);
                        assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "%rax"));
                        break;
                    case BinOp.MOD:
                        assembly.add("movq " + srcLeftString + ", %rax");
                        assembly.add("cqto"); // sign extend
                        assembly.add("idivq "+srcRightString);
                        assembly.add("movq %rdx, " + dst);
                        assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "%rdx"));
                        break;
                    case BinOp.MUL:
                        assembly.add("movq " + srcLeftString + ", %rax");
                        assembly.add("imulq " + srcRightString + ", %rax");
                        assembly.add("movq %rax, " + dst); // TODO overflow?
                        assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "%rax"));
                        break;
                    case BinOp.DIV:
                        assembly.add("movq " + srcLeftString + ", %rax");
                        assembly.add("cqto"); // sign extend
                        assembly.add("idivq " + srcRightString);
                        assembly.add("movq %rax, " + dst);
                        assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "%rax"));
                        break;
                }
                break;
            case CFAssign.LIT:
                // TODO runtime checks
                assembly.add("movq " + cfAssign.srcLit.toAssembly() + ", " + dst + " # " + cfAssign);
                assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, cfAssign.srcLit.toAssembly()));
                break;
            case CFAssign.TRUE:
                assembly.add("movq $1, " + dst + " # " + cfAssign);
                assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "$1")); // seems unnecessary?
                break;
            case CFAssign.FALSE:
                assembly.add("movq $0, " + dst + " # " + cfAssign);
                assembly.addAll(additionalDestinationToAssembly(cfAssign, variableTable, "$0")); // seems unnecessary?
                break;
            default: throw new RuntimeException("Temp has no type: impossible to reach...");
        }
        return assembly;
    }

    private static List<String> additionalDestinationToAssembly(CFAssign cfAssign, VariableTable variableTable, String src) {
        if (cfAssign.dstOptionalCSE != null) {
            return List.of("movq " + src + ", -" + cfAssign.dstOptionalCSE.getStackOffset(variableTable) + "(%rbp) # " + cfAssign.dstOptionalCSE + " = " + cfAssign.canonicalExpr);
        } else {
            return List.of();
        }
    }

}
