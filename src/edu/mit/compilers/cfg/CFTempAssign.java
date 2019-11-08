package edu.mit.compilers.cfg;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.TypeDescriptor;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.*;
import edu.mit.compilers.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CFTempAssign implements CFStatement {

    private int type;
    public static final int LEN = 0;
    public static final int MINUS = 1;
    public static final int NOT = 2;
    public static final int ARRAY_LOC = 3;
    public static final int SINGLE_LOC = 9;
    public static final int METHOD_CALL = 4;
    public static final int BIN_OP = 5;
    public static final int LIT = 6;
    public static final int TRUE = 7;
    public static final int FALSE = 8;


    private Temp dst;

    public AssemblyVariable srcLeftOrSingle;
    public BinOp srcBinOp;
    public AssemblyVariable srcRight;
    public Id srcId;
    public Id srcArray;
    public AssemblyVariable srcArrayOffset;
    public Lit srcLit;

    @Override
    public List<String> toAssembly(VariableTable variableTable, ImportTable importTable) {

        List<String> body = new ArrayList<>();
        switch (type) {
            case LEN:
                TypeDescriptor argType = variableTable.getDescriptor(srcId.getName()).getTypeDescriptor();

                if (srcId.isArray(variableTable)) {
                    body.add("movq $" + srcId.getArrayLength(variableTable) + ", -" + dst.getOffset() + "(%rbp) # " + this.toString());
                } else {
                    throw new RuntimeException("Failed semantic checks");
                }
                break;
            case MINUS:
                body.add("movq -" + srcLeftOrSingle.getStackOffset(variableTable)+ "(%rbp), %rax # " + this.toString());
                body.add("negq %rax");
                body.add("movq %rax, -" + dst.getOffset() + "(%rbp)");
                break;
            case NOT:
                body.add("movq -" + srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax # " + this.toString());
                body.add("xorq $1, %rax");
                body.add("movq %rax, -" + dst.getOffset() + "(%rbp)");
                break;
            case ARRAY_LOC:
                String arrayLoc;
                body.add("movq -" + srcArrayOffset.getStackOffset(variableTable)+"(%rbp), %rax # " + this.toString()); // val of temp into rax
                // array out of bounds
                body.add("cmpq $" + srcArray.getArrayLength(variableTable) +", %rax");
                body.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                body.add("cmpq $0, %rax");
                body.add("jl " + AssemblyFactory.METHOD_EXIT_1);
                if (srcArray.isGlobal(variableTable)) {
                    body.add("leaq 0(,%rax," + srcArray.getElementSize(variableTable)+ "), %rcx"); // temp * element size
                    body.add("leaq " + srcArray.getGlobalLabel(variableTable) + "(%rip), %rax"); // address of base of global array
                    arrayLoc = "(%rcx,%rax)";

                } else {
                    arrayLoc = "-"+ srcArray.getStackOffset(variableTable)+"(%rbp,%rax,"+ srcArray.getElementSize(variableTable)+")";
                }
                body.add("movq " + arrayLoc + ", %rax # " + this.toString());
                body.add("movq %rax, -" + dst.getOffset() + "(%rbp)");

                break;
            case SINGLE_LOC:
                String varAccessLoc;
                if (srcId.isGlobal(variableTable)) {
                    varAccessLoc = "movq " + srcId.getGlobalLabel(variableTable) + "(%rip)";
                } else {
                    varAccessLoc = "movq -"+ srcId.getStackOffset(variableTable)+"(%rbp)";
                }

                body.add(varAccessLoc + ", %rax # " + this.toString());
                body.add("movq %rax, -" + dst.getOffset() + "(%rbp)");

                break;
            case METHOD_CALL:
                body.add("");
                body.add("movq %rax, -" + dst.getOffset() + "(%rbp) # " + this.toString());
                body.add("");
                break;
            case BIN_OP:
                body.add("# "+this.toString());
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
                        body.add("movq -"+ srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        body.add("cmpq -" + srcRight.getStackOffset(variableTable) + "(%rbp), %rax");
                        body.add(getBinopCommand() + " %cl");
                        body.add("movzbq %cl, %rcx");
                        body.add("movq %rcx, -" + dst.getOffset() + "(%rbp)");
                        break;
                    case BinOp.MINUS:
                        body.add("movq -"+ srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        body.add("subq -"+ srcRight.getStackOffset(variableTable) + "(%rbp), %rax");
                        body.add("movq %rax, -"+ dst.getOffset()+"(%rbp)");
                        break;
                    case BinOp.PLUS:
                        body.add("movq -"+ srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        body.add("addq -"+ srcRight.getStackOffset(variableTable) + "(%rbp), %rax");
                        body.add("movq %rax, -"+ dst.getOffset()+"(%rbp)");
                        break;
                    case BinOp.MOD:
                        body.add("movq -"+ srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        body.add("cqto"); // sign extend
                        body.add("idivq -"+ srcRight.getStackOffset(variableTable) + "(%rbp)");
                        body.add("movq %rdx, -"+ dst.getOffset()+"(%rbp)");
                        break;
                    case BinOp.MUL:
                        body.add("movq -"+ srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        body.add("imulq -"+ srcRight.getStackOffset(variableTable) + "(%rbp), %rax");
                        body.add("movq %rax, -"+ dst.getOffset()+"(%rbp)"); // TODO overflow?
                        break;
                    case BinOp.DIV:
                        body.add("movq -"+ srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        body.add("cqto"); // sign extend
                        body.add("idivq -"+ srcRight.getStackOffset(variableTable) + "(%rbp)");
                        body.add("movq %rax, -"+ dst.getOffset()+"(%rbp)");
                        break;
                }
                break;
            case LIT:
                // TODO runtime checks
                body.add("movq " + srcLit.toAssembly() + ", -" + dst.getOffset() + "(%rbp) # " + this.toString());
                break;
            case TRUE:
                body.add("movq $1, -" + dst.getOffset() + "(%rbp) # " + this.toString());
                break;
            case FALSE:
                body.add("movq $0, -" + dst.getOffset() + "(%rbp) # " + this.toString());
                break;
            default: throw new RuntimeException("Temp has no type: impossible to reach...");
        }

        return AssemblyFactory.indent(body);
    }

    @Override
    public int getUID() {
        return 0;
    }

    public static CFTempAssign makeMinus(Temp dest, Temp operand) {
        CFTempAssign result = new CFTempAssign();
        result.type = MINUS;
        result.dst = dest;
        result.srcLeftOrSingle = operand;
        return result;
    }

    public static CFTempAssign makeLen(Temp temp2, Id id) {
        CFTempAssign result = new CFTempAssign();
        result.type = LEN;
        result.dst = temp2;
        result.srcId = id;
        return result;
    }

    public static CFTempAssign makeLit(Temp temp, Lit lit2) {
        CFTempAssign result = new CFTempAssign();
        result.type = LIT;
        result.dst = temp;
        result.srcLit = lit2;
        return result;
    }

    public static CFStatement makeLoc(Temp temp, Id id2, Temp tempLocExpr) {
        CFTempAssign result = new CFTempAssign();
        result.type = ARRAY_LOC;
        result.dst = temp;
        result.srcArray = id2;
        result.srcArrayOffset = tempLocExpr;
        return result;
    }

    public static CFStatement makeLoc(Temp temp, Id id2) {
        CFTempAssign result = new CFTempAssign();
        result.type = SINGLE_LOC;
        result.dst = temp;
        result.srcId = id2;
        return result;
    }

    public static CFStatement makeLoadRax(Temp temp) {
        CFTempAssign result = new CFTempAssign();
        result.type = METHOD_CALL;
        result.dst = temp;
        // expr = "load %rax"
        return result;
    }

    public static CFStatement makeNot(Temp temp, Temp tempNotOperand) {
        CFTempAssign result = new CFTempAssign();
        result.type = MINUS;
        result.dst = temp;
        result.srcLeftOrSingle = tempNotOperand;
        return result;
    }

    public static CFStatement assignTrue(Temp temp) {
        CFTempAssign result = new CFTempAssign();
        result.type = TRUE;
        result.dst = temp;
        return result;
    }

    public static CFStatement assignFalse(Temp temp) {
        CFTempAssign result = new CFTempAssign();
        result.type = FALSE;
        result.dst = temp;
        return result;
    }

    public static CFStatement assignBinOp(Temp temp, Temp left, BinOp binOp2, Temp right) {
        CFTempAssign result = new CFTempAssign();
        result.type = BIN_OP;
        result.dst = temp;
        result.srcLeftOrSingle = left;
        result.srcBinOp = binOp2;
        result.srcRight = right;
        return result;
    }

    @Override
    public String toString() {
        switch (type) {
            case LEN: return dst + " = len(" + srcId + ")";
            case MINUS: return dst + " = -"+ srcLeftOrSingle;
            case NOT: return dst + " = !" + srcLeftOrSingle;
            case METHOD_CALL: return dst + " = load %rax";
            case BIN_OP: return dst + " = " + srcLeftOrSingle + " " + srcBinOp + " " + srcRight;
            case LIT: return dst + " = " + srcLit;
            case TRUE: return dst + " = true";
            case FALSE: return dst + " = false";
            case SINGLE_LOC: return dst + " = " + srcId;
            case ARRAY_LOC: return dst + " = " + srcArray + "[" + srcArrayOffset + "]";
            default: throw new RuntimeException("Temp has no type: impossible to reach...");
        }
    }

    @Override
    public Pair<Temp, List<Temp>> getTemps() {
        List<Temp> right = new ArrayList();
        if (srcLeftOrSingle != null && srcLeftOrSingle instanceof Temp) right.add((Temp) srcLeftOrSingle);
        if (srcRight != null && srcRight instanceof Temp) right.add((Temp) srcRight);
        if (srcArrayOffset != null && srcArrayOffset instanceof Temp) right.add((Temp) srcArrayOffset);
        return new Pair(dst,right);
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
}
