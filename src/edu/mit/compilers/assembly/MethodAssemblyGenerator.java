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

    private final Set<OuterCFNode> visited = new HashSet<>();

    private final Set<InnerCFNode> innerVisited = new HashSet<>();

    private Optional<VariableTable> variableTable = Optional.empty();

    private final List<String> instructions = new ArrayList<>();
    private final List<String> cfAssignTemporaryInstructions = new ArrayList<>();

    private ImportTable importTable;

    public MethodAssemblyGenerator(OuterCFNode cfMethodStart, ImportTable importTable) {
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

        for (OuterCFNode child: cfBlock.dfsTraverse()) {
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

        if (cfConditional.hasRegisterAssignment(cfConditional.getBoolTemp())) {
            body.add("cmpq $1, " + cfConditional.getRegisterAssignment(cfConditional.getBoolTemp()) + " # true = " + cfConditional.getBoolTemp());
        } else {
            body.add("cmpq $1, -" + cfConditional.getBoolTemp().getOffset() + "(%rbp) # true = " + cfConditional.getBoolTemp());
        }
        body.add("jne " + cfConditional.getIfFalse().getAssemblyLabel() + " # ifFalse");
        body.add("jmp " + cfConditional.getIfTrue().getAssemblyLabel() + " # ifTrue");

        instructions.addAll(AssemblyFactory.indent(body));


        for (OuterCFNode child: cfConditional.dfsTraverse()) {
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

        for (OuterCFNode child: cfNop.dfsTraverse()) {
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
            if (cfReturn.hasRegisterAssignment(cfReturn.getReturnTemp())) {
                instructions.add(AssemblyFactory.indent("movq " + cfReturn.getRegisterAssignment(cfReturn.getReturnTemp()) + ", %rax"));
            } else {
                instructions.add(AssemblyFactory.indent("movq -" + cfReturn.getReturnTemp().getOffset() + "(%rbp), %rax"));
            }
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
            if (cfConditional.hasRegisterAssignment(cfConditional.getBoolTemp())) {
                body.add("cmpq $1, " + cfConditional.getRegisterAssignment(cfConditional.getBoolTemp()) + " # true = " + cfConditional.getVariableTable());

            } else {
                body.add("cmpq $1, -" + cfConditional.getBoolTemp().getStackOffset(variableTable) + "(%rbp) # true = " + cfConditional.getVariableTable());
            }
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
        cfAssignTemporaryInstructions.clear();
        Operand dst = dstToAssembly(cfAssign, variableTable.get());
        srcToAssembly(cfAssign, variableTable.get(), dst);

        instructions.addAll(AssemblyFactory.indent(cfAssignTemporaryInstructions));
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
                    // TODO global
                    if (cfMethodCall.hasRegisterAssignment(param.getKey())) {
                        body.add("movq "+cfMethodCall.getRegisterAssignment(param.getKey()) + ", " + target);
                    } else {
                        body.add("movq -" + param.getKey().getOffset() + "(%rbp), " + target);
                    }
                } else {
                    body.add("leaq " + param.getValue().getLabel() + "(%rip), " + target);
                }
            } else {
                if (param.getKey() != null) {
                    // no globals
                    if (cfMethodCall.hasRegisterAssignment(param.getKey())) {
                        body.add("push " + cfMethodCall.getRegisterAssignment(param.getKey()));
                    } else {
                        body.add("push -" + param.getKey().getOffset() + "(%rbp)");
                    }
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
    private Operand dstToAssembly(CFAssign cfAssign, VariableTable variableTable) {

        Operand dst;
        if (cfAssign.dstArrayOrLoc.isGlobal(variableTable)) {
            if (cfAssign.dstArrayOffset == null) {
                dst = Operand.makeGlobalAccess(cfAssign.dstArrayOrLoc.getGlobalLabel(variableTable));
            } else {
                Reg arrayOffset;
                if (cfAssign.hasRegisterAssignment(cfAssign.dstArrayOffset)) {
                    arrayOffset = cfAssign.getRegisterAssignment(cfAssign.dstArrayOffset);
                } else {
                    arrayOffset = Reg.RDI;
                    cfAssignTemporaryInstructions.add("movq -" + cfAssign.dstArrayOffset.getStackOffset(variableTable) + "(%rbp), %rdi"); // val of temp into rdi
                }

                // array out of bounds
                cfAssignTemporaryInstructions.add("cmpq $" + cfAssign.dstArrayOrLoc.getArrayLength(variableTable) +", %rdi");
                cfAssignTemporaryInstructions.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                cfAssignTemporaryInstructions.add("cmpq $0, %rdi");
                cfAssignTemporaryInstructions.add("jl " + AssemblyFactory.METHOD_EXIT_1);

                // get dest
                cfAssignTemporaryInstructions.add("leaq 0(," + arrayOffset +"," + cfAssign.dstArrayOrLoc.getElementSize(variableTable) + "), %rcx"); // temp * element size
                cfAssignTemporaryInstructions.add("leaq " + cfAssign.dstArrayOrLoc.getGlobalLabel(variableTable) + ", %rdi"); // address of base of global array
                cfAssignTemporaryInstructions.add("add %rcx, %rdi");
                dst = Operand.makeMemoryAccess(Reg.RDI);
            }
        } else {
            if (cfAssign.dstArrayOffset == null) {
                if (cfAssign.hasRegisterAssignment(cfAssign.dstArrayOrLoc)) {
                    dst = Operand.makeReg(cfAssign.getRegisterAssignment(cfAssign.dstArrayOrLoc));
                } else {
                    dst = Operand.makeMemoryAccess(cfAssign.dstArrayOrLoc.getStackOffset(variableTable),Reg.RBP);
                }
            } else {
                Reg arrayOffset;
                if (cfAssign.hasRegisterAssignment(cfAssign.dstArrayOffset)) {
                    arrayOffset = cfAssign.getRegisterAssignment(cfAssign.dstArrayOffset);
                } else {
                    arrayOffset = Reg.RDI;
                    cfAssignTemporaryInstructions.add("movq -" + cfAssign.dstArrayOffset.getStackOffset(variableTable) + "(%rbp), %rdi"); // val of temp into rdi
                }
                // array out of bounds
                cfAssignTemporaryInstructions.add("cmpq $" + cfAssign.dstArrayOrLoc.getArrayLength(variableTable) +", %rdi");
                cfAssignTemporaryInstructions.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                cfAssignTemporaryInstructions.add("cmpq $0, %rdi");
                cfAssignTemporaryInstructions.add("jl " + AssemblyFactory.METHOD_EXIT_1);

                // get dest
                dst = Operand.makeMemoryAccess(cfAssign.dstArrayOrLoc.getStackOffset(variableTable), Reg.RBP, arrayOffset,cfAssign.dstArrayOrLoc.getElementSize(variableTable));
            }
        }
        return dst;
    }

    private void srcToAssembly(CFAssign cfAssign, VariableTable variableTable, Operand dst) {
        // First handle all cases with optional source
        switch (cfAssign.assignOp) {
            case CFAssign.MEQ:
                if (cfAssign.srcOptionalCSE == null) {
                    if (cfAssign.hasRegisterAssignment(cfAssign.srcLeftOrSingle)) {
                        cfAssignTemporaryInstructions.add("subq " +cfAssign.getRegisterAssignment(cfAssign.srcLeftOrSingle) + ", " + dst);
                    } else if (dst.isReg()) {
                        cfAssignTemporaryInstructions.add("subq -" + cfAssign.srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), "  + dst +" # " + cfAssign.toString());
                    } else {
                        cfAssignTemporaryInstructions.add("movq -" + cfAssign.srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax # " + cfAssign.toString());
                        cfAssignTemporaryInstructions.add("subq %rax, " + dst);
                    }
                } else {
                    if (cfAssign.hasRegisterAssignment(cfAssign.srcOptionalCSE)) {
                        cfAssignTemporaryInstructions.add("subq " +cfAssign.getRegisterAssignment(cfAssign.srcOptionalCSE) + ", " + dst);
                    } else if (dst.isReg()) {
                        cfAssignTemporaryInstructions.add("subq -" + cfAssign.srcOptionalCSE.getStackOffset(variableTable) + "(%rbp), " + dst +" # " + cfAssign.toString());
                    } else {
                        cfAssignTemporaryInstructions.add("movq -" + cfAssign.srcOptionalCSE.getStackOffset(variableTable) + "(%rbp), %rax # " + cfAssign.toString());
                        cfAssignTemporaryInstructions.add("subq %rax, " + dst);
                    }
                }
                return;
            case CFAssign.PEQ:
                if (cfAssign.srcOptionalCSE == null) {
                    if (cfAssign.hasRegisterAssignment(cfAssign.srcLeftOrSingle)) {
                        cfAssignTemporaryInstructions.add("addq " +cfAssign.getRegisterAssignment(cfAssign.srcLeftOrSingle) + ", " + dst);
                    } else if (dst.isReg()) {
                        cfAssignTemporaryInstructions.add("addq -" + cfAssign.srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), "  + dst +" # " + cfAssign.toString());
                    } else {
                        cfAssignTemporaryInstructions.add("movq -" + cfAssign.srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax # " + cfAssign.toString());
                        cfAssignTemporaryInstructions.add("addq %rax, " + dst);
                    }
                } else {
                    if (cfAssign.hasRegisterAssignment(cfAssign.srcOptionalCSE)) {
                        cfAssignTemporaryInstructions.add("addq " +cfAssign.getRegisterAssignment(cfAssign.srcOptionalCSE) + ", " + dst);
                    } else if (dst.isReg()) {
                        cfAssignTemporaryInstructions.add("addq -" + cfAssign.srcOptionalCSE.getStackOffset(variableTable) + "(%rbp), "  + dst +" # " + cfAssign.toString());
                    } else {
                        cfAssignTemporaryInstructions.add("movq -" + cfAssign.srcOptionalCSE.getStackOffset(variableTable) + "(%rbp), %rax # " + cfAssign.toString());
                        cfAssignTemporaryInstructions.add("addq %rax, " + dst);
                    }
                }
                return;
            case CFAssign.INC: cfAssignTemporaryInstructions.add("incq " + dst + " # " + cfAssign.toString()); return;
            case CFAssign.DEC: cfAssignTemporaryInstructions.add("decq " + dst + " # " + cfAssign.toString()); return;
            case CFAssign.ASSIGN:
                if (cfAssign.srcOptionalCSE != null) {
                    if (cfAssign.hasRegisterAssignment(cfAssign.srcOptionalCSE)) {
                        if (!dst.equals(cfAssign.getRegisterAssignment(cfAssign.srcOptionalCSE))) {
                            cfAssignTemporaryInstructions.add("movq " + cfAssign.getRegisterAssignment(cfAssign.srcOptionalCSE) + ", " + dst);
                        }
                    } else {
                        if (dst.isReg()) {
                            cfAssignTemporaryInstructions.add("movq -" + cfAssign.srcOptionalCSE.getStackOffset(variableTable) + "(%rbp)" + dst + " # " + cfAssign.toString());
                        } else {
                        cfAssignTemporaryInstructions.add("movq -" + cfAssign.srcOptionalCSE.getStackOffset(variableTable) + "(%rbp), %rax # " + cfAssign.toString());
                        cfAssignTemporaryInstructions.add("movq %rax, " + dst);
                        }
                    }
                    return;
                }
                break; // everything else should be assign
        }

        switch (cfAssign.getType()) {
            case CFAssign.LEN:
                if (cfAssign.srcId.isArray(variableTable)) {
                    cfAssignTemporaryInstructions.add("movq $" + cfAssign.srcId.getArrayLength(variableTable) + ", " + dst + " # " + cfAssign.toString());
                    if (cfAssign.dstOptionalCSE != null) throw new RuntimeException("Why are we saving an immediate, thats wack\nLen expression has additional destination");
                } else {
                    throw new RuntimeException("Failed semantic checks");
                }
                break;
            case CFAssign.MINUS:
                Reg minusOperand = handleIntoRegister(cfAssign, cfAssign.srcLeftOrSingle); // check for existing register assignment
                cfAssignTemporaryInstructions.add("negq " + minusOperand);
                if (!dst.equals(minusOperand)) { // touching webs
                    cfAssignTemporaryInstructions.add("movq " + minusOperand + ", " + dst);
                }
                cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, minusOperand));
                break;
            case CFAssign.NOT:
                Reg notOperand = handleIntoRegister(cfAssign, cfAssign.srcLeftOrSingle);  // check for existing register assignment
                cfAssignTemporaryInstructions.add("xorq $1, " + notOperand);
                if (!dst.equals(notOperand)) { // touching webs
                    cfAssignTemporaryInstructions.add("movq " + notOperand + ", " + dst);
                }
                cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, notOperand));
                break;
            case CFAssign.ARRAY_LOC:
                Operand arrayLocOperand;
                Reg arrayOffsetOperand = handleIntoRegister(cfAssign, cfAssign.srcArrayOffset);
                cfAssignTemporaryInstructions.add("movq -" +cfAssign.srcArrayOffset.getStackOffset(variableTable)+"(%rbp), %rax # " + cfAssign); // val of temp into rax
                // array out of bounds
                cfAssignTemporaryInstructions.add("cmpq $" + cfAssign.srcArray.getArrayLength(variableTable) +", " + arrayOffsetOperand);
                cfAssignTemporaryInstructions.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                cfAssignTemporaryInstructions.add("cmpq $0, " + arrayOffsetOperand);
                cfAssignTemporaryInstructions.add("jl " + AssemblyFactory.METHOD_EXIT_1);
                if (cfAssign.srcArray.isGlobal(variableTable)) {
                    cfAssignTemporaryInstructions.add("leaq " + cfAssign.srcArray.getGlobalLabel(variableTable) + "(%rip), %rax"); // address of base of global array
                    arrayLocOperand = Operand.makeMemoryAccess(Reg.RAX, arrayOffsetOperand, cfAssign.srcArray.getElementSize(variableTable));

                } else {
                    arrayLocOperand = Operand.makeMemoryAccess(cfAssign.srcArray.getStackOffset(variableTable),
                            Reg.RBP, arrayOffsetOperand,cfAssign.srcArray.getElementSize(variableTable));
                }
                cfAssignTemporaryInstructions.add("movq " + arrayLocOperand + ", %rdx # " + cfAssign); // is this ok?
                cfAssignTemporaryInstructions.add("movq %rdx, " + dst);
                cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, Reg.RDX));

                break;
            case CFAssign.SIMPLE:
                assert cfAssign.assignOp == CFAssign.ASSIGN; // all other cases should have been handled above ...
                Reg assignOperand = handleIntoRegister(cfAssign, cfAssign.srcLeftOrSingle);  // check for existing register assignment
                cfAssignTemporaryInstructions.add("movq " + assignOperand +", " + dst);
                cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, assignOperand));
                return;
            case CFAssign.METHOD_CALL:
                cfAssignTemporaryInstructions.add("");
                cfAssignTemporaryInstructions.add("movq %rax, " + dst + " # " + cfAssign.toString());
                cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, Reg.RAX));
                cfAssignTemporaryInstructions.add("");
                break;
            case CFAssign.BIN_OP:
                final Reg srcLeftReg;
                final Reg srcRightReg;
                final Operand srcLeftOperand;
                final String srcOperand;
                cfAssignTemporaryInstructions.add("# "+cfAssign.toString());
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
                        srcLeftReg = handleIntoRegister(cfAssign, cfAssign.srcLeftOrSingle);
                        srcLeftOperand = getOperand(cfAssign, cfAssign.srcRight);
                        cfAssignTemporaryInstructions.add("cmpq " + srcLeftOperand + ", " + srcLeftReg);
                        if (dst.isReg()) {
                            cfAssignTemporaryInstructions.add(cfAssign.getBinopCommand() + " " + dst.getReg1().byte0());
                            cfAssignTemporaryInstructions.add("movzbq " + dst.getReg1().byte0() + ", " + dst.getReg1());
                        } else {
                            cfAssignTemporaryInstructions.add(cfAssign.getBinopCommand() + " %al");
                            cfAssignTemporaryInstructions.add("movzbq %al, %rax");
                            cfAssignTemporaryInstructions.add("movq %rax, " + dst);
                        }
                        cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, Reg.RAX));
                        break;
                    case BinOp.MINUS:
                        if (dst.isReg()) {
                            cfAssignTemporaryInstructions.add("movq " + getOperand(cfAssign, cfAssign.srcLeftOrSingle) + ", "+dst);
                            cfAssignTemporaryInstructions.add("subq " + getOperand(cfAssign, cfAssign.srcRight) + ", " +dst);
                            cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, dst.getReg1()));
                        } else {
                            cfAssignTemporaryInstructions.add("movq " + getOperand(cfAssign, cfAssign.srcLeftOrSingle) + ", %rax");
                            cfAssignTemporaryInstructions.add("subq " + getOperand(cfAssign, cfAssign.srcRight) + ", %rax");
                            cfAssignTemporaryInstructions.add("movq %rax, "+dst);
                            cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, Reg.RAX));
                        }
                        break;
                    case BinOp.PLUS:
                        if (dst.isReg()) {
                            cfAssignTemporaryInstructions.add("movq " + getOperand(cfAssign, cfAssign.srcLeftOrSingle) + ", "+dst);
                            cfAssignTemporaryInstructions.add("addq " + getOperand(cfAssign, cfAssign.srcRight) + ", " +dst);
                            cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, dst.getReg1()));
                        } else {
                            cfAssignTemporaryInstructions.add("movq " + getOperand(cfAssign, cfAssign.srcLeftOrSingle) + ", %rax");
                            cfAssignTemporaryInstructions.add("addq " + getOperand(cfAssign, cfAssign.srcRight) + ", %rax");
                            cfAssignTemporaryInstructions.add("movq %rax, "+dst);
                            cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, Reg.RAX));
                        }
                        break;
                    case BinOp.MOD:
                        cfAssignTemporaryInstructions.add("movq " + getOperand(cfAssign, cfAssign.srcLeftOrSingle) + ", %rax");
                        cfAssignTemporaryInstructions.add("cqto"); // sign extend
                        cfAssignTemporaryInstructions.add("idivq "+ getOperand(cfAssign, cfAssign.srcRight));
                        cfAssignTemporaryInstructions.add("movq %rdx, " + dst);
                        cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, Reg.RDX));
                        break;
                    case BinOp.MUL:
                        if (dst.isReg()) {
                            cfAssignTemporaryInstructions.add("movq " + getOperand(cfAssign, cfAssign.srcLeftOrSingle) + ", "+dst);
                            cfAssignTemporaryInstructions.add("imulq " + getOperand(cfAssign, cfAssign.srcRight) + ", " +dst);
                            cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, dst.getReg1()));
                        } else {
                            cfAssignTemporaryInstructions.add("movq " + getOperand(cfAssign, cfAssign.srcLeftOrSingle) + ", %rax");
                            cfAssignTemporaryInstructions.add("imulq " + getOperand(cfAssign, cfAssign.srcRight) + ", %rax");
                            cfAssignTemporaryInstructions.add("movq %rax, "+dst);
                            cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, Reg.RAX));
                        }
                        break;
                    case BinOp.DIV:
                        cfAssignTemporaryInstructions.add("movq " + getOperand(cfAssign, cfAssign.srcLeftOrSingle) + ", %rax");
                        cfAssignTemporaryInstructions.add("cqto"); // sign extend
                        cfAssignTemporaryInstructions.add("idivq " + getOperand(cfAssign, cfAssign.srcRight));
                        cfAssignTemporaryInstructions.add("movq %rax, " + dst);
                        cfAssignTemporaryInstructions.addAll(additionalDestinationToAssembly(cfAssign, variableTable, Reg.RAX));
                        break;
                }
                break;
            case CFAssign.LIT:
                cfAssignTemporaryInstructions.add("movq " + cfAssign.srcLit.toAssembly() + ", " + dst + " # " + cfAssign);
                if (cfAssign.dstOptionalCSE != null) throw new RuntimeException("Why are we saving an immediate, thats wack\nLen expression has additional destination");
                break;
            case CFAssign.TRUE:
                cfAssignTemporaryInstructions.add("movq $1, " + dst + " # " + cfAssign);
                if (cfAssign.dstOptionalCSE != null) throw new RuntimeException("Why are we saving an immediate, thats wack\nLen expression has additional destination");
                break;
            case CFAssign.FALSE:
                cfAssignTemporaryInstructions.add("movq $0, " + dst + " # " + cfAssign);
                if (cfAssign.dstOptionalCSE != null) throw new RuntimeException("Why are we saving an immediate, thats wack\nLen expression has additional destination");
                break;
            default: throw new RuntimeException("Temp has no type: impossible to reach...");
        }
    }

    /**
     * put assembly variable into register if not existing register allocated
     * return the register that the assembly variable is in
     */
    private Reg handleIntoRegister(CFAssign cfAssign, AssemblyVariable assemblyVariable) {
        if (assemblyVariable.isGlobal(cfAssign.getVariableTable())) {
            cfAssignTemporaryInstructions.add("movq " + assemblyVariable.getGlobalLabel(cfAssign.getVariableTable()) + "(%rip), %rax" + " # " + cfAssign.toString());
            return Reg.RAX;
        } else if (cfAssign.hasRegisterAssignment(assemblyVariable)) {
            return cfAssign.getRegisterAssignment(assemblyVariable);
        } else {
            cfAssignTemporaryInstructions.add("movq -" + assemblyVariable.getStackOffset(cfAssign.getVariableTable()) + "(%rbp), %rax # " + cfAssign);
            return Reg.RAX;
        }
    }

    private Operand getOperand(CFAssign cfAssign, AssemblyVariable assemblyVariable) {
        if (assemblyVariable.isGlobal(cfAssign.getVariableTable())) {
            return Operand.makeGlobalAccess(assemblyVariable.getGlobalLabel(cfAssign.getVariableTable()));
        } else if (cfAssign.hasRegisterAssignment(assemblyVariable)) {
            return Operand.makeReg(cfAssign.getRegisterAssignment(assemblyVariable));
        } else {
            return Operand.makeMemoryAccess(assemblyVariable.getStackOffset(cfAssign.getVariableTable()));
        }
    }


    private static List<String> additionalDestinationToAssembly(CFAssign cfAssign, VariableTable variableTable, Reg src) {
        if (cfAssign.dstOptionalCSE != null) {
            if (cfAssign.hasRegisterAssignment(cfAssign.dstOptionalCSE)) {
                if (cfAssign.getRegisterAssignment(cfAssign.dstOptionalCSE).equals(src))
                    return List.of();
                else
                    return List.of("movq " + src + ", " + cfAssign.getRegisterAssignment(cfAssign.dstOptionalCSE) + " # " + cfAssign.dstOptionalCSE + " = " + cfAssign.canonicalExpr);
            } else {
                return List.of("movq " + src + ", -" + cfAssign.dstOptionalCSE.getStackOffset(variableTable) + "(%rbp) # " + cfAssign.dstOptionalCSE + " = " + cfAssign.canonicalExpr);
            }
        } else {
            return List.of();
        }
    }

}
