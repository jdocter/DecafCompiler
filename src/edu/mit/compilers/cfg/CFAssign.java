package edu.mit.compilers.cfg;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.inter.*;

import edu.mit.compilers.parser.BinOp;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.Lit;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;

import java.util.ArrayList;
import java.util.List;

public class CFAssign extends UIDObject implements CFStatement {

    private int type;
    private final List<String> assembly = new ArrayList<>();

    public static final int LEN = 0;
    public static final int MINUS = 1;
    public static final int NOT = 2;
    public static final int ARRAY_LOC = 3;
    public static final int SIMPLE = 9;
    public static final int METHOD_CALL = 4;
    public static final int BIN_OP = 5;
    public static final int LIT = 6;
    public static final int TRUE = 7;
    public static final int FALSE = 8;



    public AssemblyVariable dstArrayOrLoc;
    public AssemblyVariable dstArrayOffset;

    public String assignOp;

    public AssemblyVariable srcLeftOrSingle;
    public BinOp srcBinOp;
    public AssemblyVariable srcRight;
    public Id srcId;
    public Id srcArray;
    public AssemblyVariable srcArrayOffset;
    public Lit srcLit;

    public static final String ASSIGN = "=";
    public static final String PEQ = "+=";
    public static final String MEQ = "-=";
    public static final String INC = "++";
    public static final String DEC = "--";


    public CFAssign(Id id, Temp tempLocExpr, String assignExprOp, AssemblyVariable assignExpr) {
        this.dstArrayOrLoc = id;
        this.dstArrayOffset = tempLocExpr;
        this.assignOp = assignExprOp;
        this.srcLeftOrSingle = assignExpr;
        this.type = SIMPLE;
    }
    private CFAssign() {};

    public static CFAssign makeSimple(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, String assignExprOp, AssemblyVariable assignExpr) {
        CFAssign result = new CFAssign();
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.assignOp = assignExprOp;
        result.srcLeftOrSingle = assignExpr;
        result.type = SIMPLE;
        return result;
    }


    public static CFAssign makeMinus(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, AssemblyVariable operand) {
        CFAssign result = new CFAssign();
        result.type = MINUS;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLeftOrSingle = operand;
        return result;
    }

    public static CFAssign makeLen(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Id id) {
        CFAssign result = new CFAssign();
        result.type = LEN;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcId = id;
        return result;
    }

    public static CFAssign makeLit(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Lit lit) {
        CFAssign result = new CFAssign();
        result.type = LIT;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLit = lit;
        return result;
    }

    public static CFAssign makeArrayLocAssign(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Id srcArray, AssemblyVariable srcArrayOffset) {
        CFAssign result = new CFAssign();
        result.type = ARRAY_LOC;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcArray = srcArray;
        result.srcArrayOffset = srcArrayOffset;
        return result;
    }

    public static CFAssign makeLocAssign(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Id src) {
        CFAssign result = new CFAssign();
        result.type = SIMPLE;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.assignOp = ASSIGN;
        result.srcId = src;
        return result;
    }

    public static CFAssign makeLoadRax(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset) {
        CFAssign result = new CFAssign();
        result.type = METHOD_CALL;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        // expr = "load %rax"
        return result;
    }

    public static CFAssign makeNot(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, AssemblyVariable notOperand) {
        CFAssign result = new CFAssign();
        result.type = MINUS;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLeftOrSingle = notOperand;
        return result;
    }

    public static CFAssign assignTrue(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset) {
        CFAssign result = new CFAssign();
        result.type = TRUE;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        return result;
    }

    public static CFAssign assignFalse(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset) {
        CFAssign result = new CFAssign();
        result.type = FALSE;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        return result;
    }

    public static CFAssign assignBinOp(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, AssemblyVariable left, BinOp binOp, AssemblyVariable right) {
        CFAssign result = new CFAssign();
        result.type = BIN_OP;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLeftOrSingle = left;
        result.srcBinOp = binOp;
        result.srcRight = right;
        return result;
    }

    /**
     * Produces assembly for destination of assignment. May mutate %rcx, %rdx
     * @param variableTable
     * @return string of destination in assembly, memory access, may use %rcx and %rdx
     */
    private String dstToAssembly(VariableTable variableTable) {
        String dst;
        if (dstArrayOrLoc.isGlobal(variableTable)) {
            if (dstArrayOffset == null) {
                dst = dstArrayOrLoc.getGlobalLabel(variableTable) + "(%rip)";
            } else {
                assembly.add("movq -" + dstArrayOffset.getStackOffset(variableTable) +"(%rbp), %rdx"); // val of temp into rax

                // array out of bounds
                assembly.add("cmpq $" + dstArrayOrLoc.getArrayLength(variableTable) +", %rdx");
                assembly.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                assembly.add("cmpq $0, %rdx");
                assembly.add("jl " + AssemblyFactory.METHOD_EXIT_1);

                // get dest
                assembly.add("leaq 0(,%rdx," + dstArrayOrLoc.getElementSize(variableTable) + "), %rcx"); // temp * element size
                assembly.add("leaq " + dstArrayOrLoc.getGlobalLabel(variableTable) + ", %rdx"); // address of base of global array
                dst = "(%rcx,%rdx)";
            }
        } else {
            if (dstArrayOffset == null) {
                dst = "-"+ dstArrayOrLoc.getStackOffset(variableTable)+"(%rbp)";
            } else {
                assembly.add("movq -" + dstArrayOffset.getStackOffset(variableTable) +"(%rbp), %rdx"); // val of temp into rax

                // array out of bounds
                assembly.add("cmpq $" + dstArrayOrLoc.getArrayLength(variableTable) +", %rdx");
                assembly.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                assembly.add("cmpq $0, %rdx");
                assembly.add("jl " + AssemblyFactory.METHOD_EXIT_1);

                // get dest
                dst = "-" + dstArrayOrLoc.getStackOffset(variableTable) +"(%rbp,%rdx,"+dstArrayOrLoc.getElementSize(variableTable)+")";
            }
        }
        return dst;
    }

    private void srcToAssembly(VariableTable variableTable, String dst) {
        switch (assignOp) { // TODO rax srcTemp?
            case MEQ:
                assembly.add("movq -" + srcLeftOrSingle.getStackOffset(variableTable) +  "(%rbp), %rax");
                assembly.add("subq %rax, " + dst);
                return;
            case PEQ:
                assembly.add("movq -" + srcLeftOrSingle.getStackOffset(variableTable) +  "(%rbp), %rax");
                assembly.add("addq %rax, " + dst);
                return;
            case INC: assembly.add("incq " + dst); return;
            case DEC: assembly.add("decq " + dst); return;
            case ASSIGN: break; // everything else should be assign
        }


        switch (type) {
            case LEN:
                if (srcId.isArray(variableTable)) {
                    assembly.add("movq $" + srcId.getArrayLength(variableTable) + ", " + dst + " # " + this.toString());
                } else {
                    throw new RuntimeException("Failed semantic checks");
                }
                break;
            case MINUS:
                assembly.add("movq -" + srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax # " + this.toString());
                assembly.add("negq %rax");
                assembly.add("movq %rax, -" + dst);
                break;
            case NOT:
                assembly.add("movq -" + srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax # " + this.toString());
                assembly.add("xorq $1, %rax");
                assembly.add("movq %rax, " + dst);
                break;
            case ARRAY_LOC:
                String arrayLoc;
                assembly.add("movq -" +srcArrayOffset.getStackOffset(variableTable)+"(%rbp), %rax # " + this.toString()); // val of temp into rdx
                // array out of bounds
                assembly.add("cmpq $" + srcArray.getArrayLength(variableTable) +", %rax");
                assembly.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                assembly.add("cmpq $0, %rax");
                assembly.add("jl " + AssemblyFactory.METHOD_EXIT_1);
                if (srcArray.isGlobal(variableTable)) {
                    assembly.add("leaq 0(,%rax," + srcArray.getElementSize(variableTable) + "), %rdi"); // temp * element size
                    assembly.add("leaq " + srcArray.getGlobalLabel(variableTable) + "(%rip), %rax"); // address of base of global array
                    arrayLoc = "(%rdi,%rax)";

                } else {
                    arrayLoc = "-"+srcArray.getStackOffset(variableTable)+"(%rbp,%rax,"+srcArray.getElementSize(variableTable)+")";
                }
                assembly.add("movq " + arrayLoc + ", %rsi # " + this.toString()); // is this ok?
                assembly.add("movq %rsi, " + dst);

                break;
            case SIMPLE:
                assembly.add(srcLeftOrSingle.isGlobal(variableTable) ?
                        "movq " + srcLeftOrSingle.getGlobalLabel(variableTable) + "(%rip), rax" :
                        "movq -" + srcId.getStackOffset(variableTable) + "(%rbp), %rax");

                switch (assignOp) { // TODO rax srcTemp?
                    case MEQ:
                        assembly.add(srcLeftOrSingle.isGlobal(variableTable) ?
                                "movq " + srcLeftOrSingle.getGlobalLabel(variableTable) + "(%rip), rax" :
                                "movq -" + srcId.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add("subq %rax, " + dst);
                        return;
                    case PEQ:
                        assembly.add(srcLeftOrSingle.isGlobal(variableTable) ?
                                "movq " + srcLeftOrSingle.getGlobalLabel(variableTable) + "(%rip), rax" :
                                "movq -" + srcId.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add("addq %rax, " + dst);
                        return;
                    case INC: assembly.add("incq " + dst); return;
                    case DEC: assembly.add("decq " + dst); return;
                    case ASSIGN: assembly.add("movq " + dst); return;
                }
                break;
            case METHOD_CALL:
                assembly.add("");
                assembly.add("movq %rax, " + dst + " # " + this.toString());
                assembly.add("");
                break;
            case BIN_OP:
                assembly.add("# "+this.toString());
                switch (srcBinOp.binOp) {
                    case BinOp.AND:
                    case BinOp.OR:
                        throw new UnsupportedOperationException("Expected to short circuit AND/OR binops, not cmp them");
                    case BinOp.EQ:
                    case BinOp.NEQ:
                    case BinOp.GEQ:
                    case BinOp.LEQ:
                    case BinOp.GT:
                    case BinOp.LT:
                        assembly.add("movq -"+ srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add("cmpq -" + srcRight.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add(getBinopCommand() + " %al");
                        assembly.add("movzbq %al, %rax");
                        assembly.add("movq %rax, " + dst);
                        break;
                    case BinOp.MINUS:
                        assembly.add("movq -"+ srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add("subq -"+srcRight.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add("movq %rax, " + dst);
                        break;
                    case BinOp.PLUS:
                        assembly.add("movq -"+ srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add("addq -"+srcRight.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add("movq %rax, " + dst);
                        break;
                    case BinOp.MOD:
                        assembly.add("movq -"+ srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add("cqto"); // sign extend
                        assembly.add("idivq -"+srcRight.getStackOffset(variableTable) + "(%rbp)");
                        assembly.add("movq %rdx, " + dst);
                        break;
                    case BinOp.MUL:
                        assembly.add("movq -"+ srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add("imulq -"+srcRight.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add("movq %rax, " + dst); // TODO overflow?
                        break;
                    case BinOp.DIV:
                        assembly.add("movq -"+srcRight.getStackOffset(variableTable)+ "(%rbp), %rax");
                        assembly.add("cqto"); // sign extend
                        assembly.add("idivq -"+srcRight.getStackOffset(variableTable) + "(%rbp)");
                        assembly.add("movq %rax, " + dst);
                        break;
                }
                break;
            case LIT:
                // TODO runtime checks
                assembly.add("movq " + srcLit.toAssembly() + ", " + dst + " # " + this.toString());
                break;
            case TRUE:
                assembly.add("movq $1, " + dst + " # " + this.toString());
                break;
            case FALSE:
                assembly.add("movq $0, " + dst + " # " + this.toString());
                break;
            default: throw new RuntimeException("Temp has no type: impossible to reach...");
        }
    }

    @Override
    public List<String> toAssembly(VariableTable variableTable, ImportTable importTable) {
        assembly.clear();
        String dst = dstToAssembly(variableTable);
        srcToAssembly(variableTable, dst);

        return AssemblyFactory.indent(assembly);
    }

    @Override public String toString() {
        String offsetStr = dstArrayOffset == null ? "" : "[" + dstArrayOffset + "]";
//        if (srcExpr == null) {
//            return "" + dstArrayOrLoc + offsetStr + assignOp;
//        }
        String dst =  "" + dstArrayOrLoc + offsetStr;

        switch (assignOp) {
            case MEQ:
            case PEQ:
                return dst + " "+ assignOp + " " + srcLeftOrSingle;
            case INC:
            case DEC:
                return dst + " "+ assignOp;
            case ASSIGN: break; // everything else should be assign
        }
        switch (type) {
            case LEN: return dst + " = len(" + srcId + ")";
            case MINUS: return dst + " = -"+ srcLeftOrSingle;
            case NOT: return dst + " = !" + srcLeftOrSingle;
            case METHOD_CALL: return dst + " = load %rax";
            case BIN_OP: return dst + " = " + srcLeftOrSingle + " " + srcBinOp + " " + srcRight;
            case LIT: return dst + " = " + srcLit;
            case TRUE: return dst + " = true";
            case FALSE: return dst + " = false";
            case SIMPLE: return dst + " = " + srcId;
            case ARRAY_LOC: return dst + " = " + srcArray + "[" + srcArrayOffset + "]";
            default: throw new RuntimeException("Temp has no type: impossible to reach...");
        }
    }

    @Override
    public Pair<Temp, List<Temp>> getTemps() {
        List<Temp> right = new ArrayList();
        if (dstArrayOffset != null && dstArrayOffset instanceof Temp) right.add((Temp) dstArrayOffset);
        if (srcLeftOrSingle != null && srcLeftOrSingle instanceof Temp) right.add((Temp) srcLeftOrSingle);
        if (srcRight != null && srcRight instanceof Temp) right.add((Temp) srcRight);
        if (srcArrayOffset != null && srcArrayOffset instanceof Temp) right.add((Temp) srcArrayOffset);
        return new Pair(null,right);
    }

    private String getBinopCommand() {
        switch (srcBinOp.binOp) {
            case BinOp.AND:
            case BinOp.OR:
                throw new UnsupportedOperationException("Expected to short circuit AND/OR binops, not cmp them");
            case BinOp.PLUS:
            case BinOp.MINUS:
            case BinOp.MUL:
            case BinOp.DIV:
            case BinOp.MOD:
                throw new RuntimeException("Bad semantic checks");
            case BinOp.EQ: return "sete";
            case BinOp.NEQ: return "setne";
            case BinOp.LT: return "setl";
            case BinOp.GT: return "setg";
            case BinOp.LEQ: return "setle";
            case BinOp.GEQ: return "setge";
            default:
                throw new RuntimeException("Unrecognized binOp: " + srcBinOp.binOp);
        }
    }

    public boolean isTempAssignment() {
        return dstArrayOrLoc instanceof Temp;
    }
}
