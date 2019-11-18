package edu.mit.compilers.cfg.innercfg;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.cfg.SharedTemp;
import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.cfg.Variable;
import edu.mit.compilers.inter.*;

import edu.mit.compilers.parser.BinOp;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.Lit;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;

import java.util.*;

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

    // Destination variables
    public AssemblyVariable dstArrayOrLoc;
    public AssemblyVariable dstArrayOffset;

    // assignment type
    public String assignOp;

    // Source Variables and Literals
    public AssemblyVariable srcLeftOrSingle;
    public BinOp srcBinOp;
    public AssemblyVariable srcRight;
    public Variable srcId;
    public Variable srcArray;
    public AssemblyVariable srcArrayOffset;
    public Lit srcLit;

    // Expression that destination will hold
    public Expr canonicalExpr;

    // Optional additional destination for expression, to be used for Common Subexpression Elimination
    public SharedTemp dstOptionalCSE;
    // Optional additional source for expression, to be used for Common Subexpression Elimination
    public SharedTemp srcOptionalCSE;

    public static final String ASSIGN = "=";
    public static final String PEQ = "+=";
    public static final String MEQ = "-=";
    public static final String INC = "++";
    public static final String DEC = "--";

    private CFAssign(Expr canonicalExpr) {this.canonicalExpr = canonicalExpr;};

    public static CFAssign makeSimple(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, String assignExprOp, AssemblyVariable assignExpr, Expr canonicalExpr) {
        CFAssign result = new CFAssign(canonicalExpr);
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.assignOp = assignExprOp;
        result.srcLeftOrSingle = assignExpr;
        result.type = SIMPLE;
        return result;
    }


    public static CFAssign makeMinus(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, AssemblyVariable operand, Expr canonicalExpr) {
        CFAssign result = new CFAssign(canonicalExpr);
        result.type = MINUS;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLeftOrSingle = operand;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign makeLen(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Variable id, Expr canonicalExpr) {
        CFAssign result = new CFAssign(canonicalExpr);
        result.type = LEN;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcId = id;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign makeLit(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Lit lit, Expr canonicalExpr) {
        CFAssign result = new CFAssign(canonicalExpr);
        result.type = LIT;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLit = lit;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign makeArrayLocAssign(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Variable srcArray, AssemblyVariable srcArrayOffset, Expr canonicalExpr) {
        CFAssign result = new CFAssign(canonicalExpr);
        result.type = ARRAY_LOC;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcArray = srcArray;
        result.srcArrayOffset = srcArrayOffset;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign makeLocAssign(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Variable src, Expr canonicalExpr) {
        CFAssign result = new CFAssign(canonicalExpr);
        result.type = SIMPLE;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLeftOrSingle = src;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign makeLoadRax(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Expr canonicalExpr) {
        CFAssign result = new CFAssign(canonicalExpr);
        result.type = METHOD_CALL;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.assignOp = ASSIGN;
        // expr = "load %rax"
        return result;
    }

    public static CFAssign makeNot(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, AssemblyVariable notOperand, Expr canonicalExpr) {
        CFAssign result = new CFAssign(canonicalExpr);
        result.type = MINUS;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLeftOrSingle = notOperand;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign assignTrue(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Expr canonicalExpr) {
        CFAssign result = new CFAssign(canonicalExpr);
        result.type = TRUE;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign assignFalse(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Expr canonicalExpr) {
        CFAssign result = new CFAssign(canonicalExpr);
        result.type = FALSE;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign assignBinOp(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, AssemblyVariable left, BinOp binOp, AssemblyVariable right, Expr canonicalExpr) {
        CFAssign result = new CFAssign(canonicalExpr);
        result.type = BIN_OP;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLeftOrSingle = left;
        result.srcBinOp = binOp;
        result.srcRight = right;
        result.assignOp = ASSIGN;
        return result;
    }

    /**
     * Produces assembly for destination of assignment. May mutate %rcx, %rdi
     * @param variableTable
     * @return string of destination in assembly, memory access, may use %rcx and %rdi
     */
    private String dstToAssembly(VariableTable variableTable) {
        String dst;
        if (dstArrayOrLoc.isGlobal(variableTable)) {
            if (dstArrayOffset == null) {
                dst = dstArrayOrLoc.getGlobalLabel(variableTable) + "(%rip)";
            } else {
                assembly.add("movq -" + dstArrayOffset.getStackOffset(variableTable) +"(%rbp), %rdi"); // val of temp into rax

                // array out of bounds
                assembly.add("cmpq $" + dstArrayOrLoc.getArrayLength(variableTable) +", %rdi");
                assembly.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                assembly.add("cmpq $0, %rdi");
                assembly.add("jl " + AssemblyFactory.METHOD_EXIT_1);

                // get dest
                assembly.add("leaq 0(,%rdi," + dstArrayOrLoc.getElementSize(variableTable) + "), %rcx"); // temp * element size
                assembly.add("leaq " + dstArrayOrLoc.getGlobalLabel(variableTable) + ", %rdi"); // address of base of global array
                dst = "(%rcx,%rdi)";
            }
        } else {
            if (dstArrayOffset == null) {
                dst = "-"+ dstArrayOrLoc.getStackOffset(variableTable)+"(%rbp)";
            } else {
                assembly.add("movq -" + dstArrayOffset.getStackOffset(variableTable) +"(%rbp), %rdi"); // val of temp into rdi

                // array out of bounds
                assembly.add("cmpq $" + dstArrayOrLoc.getArrayLength(variableTable) +", %rdi");
                assembly.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                assembly.add("cmpq $0, %rdi");
                assembly.add("jl " + AssemblyFactory.METHOD_EXIT_1);

                // get dest
                dst = "-" + dstArrayOrLoc.getStackOffset(variableTable) +"(%rbp,%rdi,"+dstArrayOrLoc.getElementSize(variableTable)+")";
            }
        }
        return dst;
    }

    private void srcToAssembly(VariableTable variableTable, String dst) {
        if (srcOptionalCSE != null) {
            if (!assignOp.equals(ASSIGN)) throw new RuntimeException("CSE error");
            assembly.add("movq -" + srcOptionalCSE.getStackOffset(variableTable) +  "(%rbp), %rax # %rax = " + srcOptionalCSE.toString());
            assembly.add("movq %rax, " + dst);
            return;
        }

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
                    additionalDestinationToAssembly(variableTable, "$" + srcId.getArrayLength(variableTable));
                } else {
                    throw new RuntimeException("Failed semantic checks");
                }
                break;
            case MINUS:
                assembly.add("movq -" + srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax # " + this.toString());
                assembly.add("negq %rax");
                assembly.add("movq %rax, " + dst);
                additionalDestinationToAssembly(variableTable, "%rax");
                break;
            case NOT:
                assembly.add("movq -" + srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax # " + this.toString());
                assembly.add("xorq $1, %rax");
                assembly.add("movq %rax, " + dst);
                additionalDestinationToAssembly(variableTable, "%rax");
                break;
            case ARRAY_LOC:
                String arrayLoc;
                assembly.add("movq -" +srcArrayOffset.getStackOffset(variableTable)+"(%rbp), %rax # " + this.toString()); // val of temp into rax
                // array out of bounds
                assembly.add("cmpq $" + srcArray.getArrayLength(variableTable) +", %rax");
                assembly.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                assembly.add("cmpq $0, %rax");
                assembly.add("jl " + AssemblyFactory.METHOD_EXIT_1);
                if (srcArray.isGlobal(variableTable)) {
                    assembly.add("leaq 0(,%rax," + srcArray.getElementSize(variableTable) + "), %rdx"); // temp * element size
                    assembly.add("leaq " + srcArray.getGlobalLabel(variableTable) + "(%rip), %rax"); // address of base of global array
                    arrayLoc = "(%rdx,%rax)";

                } else {
                    arrayLoc = "-"+srcArray.getStackOffset(variableTable)+"(%rbp,%rax,"+srcArray.getElementSize(variableTable)+")";
                }
                assembly.add("movq " + arrayLoc + ", %rsi # " + this.toString()); // is this ok?
                assembly.add("movq %rsi, " + dst);
                additionalDestinationToAssembly(variableTable, "%rsi");

                break;
            case SIMPLE:
                switch (assignOp) { // TODO rax srcTemp?
                    case MEQ:
                        assembly.add(srcLeftOrSingle.isGlobal(variableTable) ?
                                "movq " + srcLeftOrSingle.getGlobalLabel(variableTable) + "(%rip), %rax" :
                                "movq -" + srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add("subq %rax, " + dst);
                        // can't have additional destination?
                        return;
                    case PEQ:
                        assembly.add(srcLeftOrSingle.isGlobal(variableTable) ?
                                "movq " + srcLeftOrSingle.getGlobalLabel(variableTable) + "(%rip), %rax" :
                                "movq -" + srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add("addq %rax, " + dst);
                        // can't have additional destination?
                        return;
                    case INC: assembly.add("incq " + dst); return;
                    case DEC: assembly.add("decq " + dst); return;
                    case ASSIGN:
                        assembly.add(srcLeftOrSingle.isGlobal(variableTable) ?
                                "movq " + srcLeftOrSingle.getGlobalLabel(variableTable) + "(%rip), %rax" :
                                "movq -" + srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp), %rax");
                        assembly.add("movq %rax, " + dst);
                        additionalDestinationToAssembly(variableTable, "%rax");
                        return;
                }
                break;
            case METHOD_CALL:
                assembly.add("");
                assembly.add("movq %rax, " + dst + " # " + this.toString());
                additionalDestinationToAssembly(variableTable, "%rax");
                assembly.add("");
                break;
            case BIN_OP:
                final String srcLeftString = srcLeftOrSingle.isGlobal(variableTable) ?
                            srcLeftOrSingle.getGlobalLabel(variableTable) + "(%rip)":
                            "-" + srcLeftOrSingle.getStackOffset(variableTable) + "(%rbp)";
                final String srcRightString = srcRight.isGlobal(variableTable) ?
                            srcRight.getGlobalLabel(variableTable) + "(%rip)":
                            "-" + srcRight.getStackOffset(variableTable) + "(%rbp)";
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
                        assembly.add("movq " + srcLeftString + ", %rax");
                        assembly.add("cmpq " + srcRightString + ", %rax");
                        assembly.add(getBinopCommand() + " %al");
                        assembly.add("movzbq %al, %rax");
                        assembly.add("movq %rax, " + dst);
                        additionalDestinationToAssembly(variableTable, "%rax");
                        break;
                    case BinOp.MINUS:
                        assembly.add("movq " + srcLeftString + ", %rax");
                        assembly.add("subq " + srcRightString + ", %rax");
                        assembly.add("movq %rax, " + dst);
                        additionalDestinationToAssembly(variableTable, "%rax");
                        break;
                    case BinOp.PLUS:
                        assembly.add("movq " + srcLeftString + ", %rax");
                        assembly.add("addq " + srcRightString + ", %rax");
                        assembly.add("movq %rax, " + dst);
                        additionalDestinationToAssembly(variableTable, "%rax");
                        break;
                    case BinOp.MOD:
                        assembly.add("movq " + srcLeftString + ", %rax");
                        assembly.add("cqto"); // sign extend
                        assembly.add("idivq "+srcRightString);
                        assembly.add("movq %rdx, " + dst);
                        additionalDestinationToAssembly(variableTable, "%rdx");
                        break;
                    case BinOp.MUL:
                        assembly.add("movq " + srcLeftString + ", %rax");
                        assembly.add("imulq " + srcRightString + ", %rax");
                        assembly.add("movq %rax, " + dst); // TODO overflow?
                        additionalDestinationToAssembly(variableTable, "%rax");
                        break;
                    case BinOp.DIV:
                        assembly.add("movq " + srcLeftString + ", %rax");
                        assembly.add("cqto"); // sign extend
                        assembly.add("idivq " + srcRightString);
                        assembly.add("movq %rax, " + dst);
                        additionalDestinationToAssembly(variableTable, "%rax");
                        break;
                }
                break;
            case LIT:
                // TODO runtime checks
                assembly.add("movq " + srcLit.toAssembly() + ", " + dst + " # " + this.toString());
                additionalDestinationToAssembly(variableTable, srcLit.toAssembly());
                break;
            case TRUE:
                assembly.add("movq $1, " + dst + " # " + this.toString());
                additionalDestinationToAssembly(variableTable, "$1"); // seems unnecessary?
                break;
            case FALSE:
                assembly.add("movq $0, " + dst + " # " + this.toString());
                additionalDestinationToAssembly(variableTable, "$0"); // seems unnecessary?
                break;
            default: throw new RuntimeException("Temp has no type: impossible to reach...");
        }
    }

    private void additionalDestinationToAssembly(VariableTable variableTable, String src) {
        if (dstOptionalCSE != null) {
            assembly.add("movq " + src + ", -" + dstOptionalCSE.getStackOffset(variableTable) + "(%rbp)");
        }
    }

    /**
     * Add another destination variable for the RHS of this assign such
     * that toAssembly includes code for storing the RHS value in dst
     * @param dst SharedTemp that the RHS should also be stored in
     */
    public void additionalDestination(SharedTemp dst) {
        dstOptionalCSE = dst;
    }

    /**
     * Add an alternative source variable for the RHS of this assign such
     * that toAssembly includes code for retrieving the RHS value from src
     * instead of calculating it again.
     * @param src SharedTemp that the RHS should be retried from
     */
    public void alternativeSource(SharedTemp src) {
        srcOptionalCSE = src;
    }

    @Override
    public List<String> toAssembly(VariableTable variableTable, ImportTable importTable) {
        assembly.clear();
        String dst = dstToAssembly(variableTable);
        srcToAssembly(variableTable, dst);

        return AssemblyFactory.indent(assembly);
    }

    @Override
    public Optional<Expr> generatedExpr() {
        if (assignOp != ASSIGN) return Optional.empty();
        if (dstArrayOffset == null && !dstArrayOrLoc.isTemporary() // check that it is type Id
                && canonicalExpr.getIds().contains(((Variable)dstArrayOrLoc).getId())) return Optional.empty();
        return Optional.of(canonicalExpr);
    }

    @Override
    public Set<Expr> killedExprs(Set<Expr> exprs) {
        Set<Expr> killed = new HashSet<>();
        if (dstArrayOffset == null && !dstArrayOrLoc.isTemporary()) {
            for (Expr subExpr : exprs) {
                if (subExpr.getIds().contains(((Variable)dstArrayOrLoc).getId())) {
                    killed.add(subExpr);
                }
            }
        }
        // System.err.println("KILLED FOR " + this + " : " + killed);
        return killed;
    }

    @Override
    public Expr getRHS() {
        return this.canonicalExpr;
    }

    @Override public String toString() {
        String offsetStr = dstArrayOffset == null ? "" : "[" + dstArrayOffset + "]";
        String dst =  "" + dstArrayOrLoc + offsetStr + " {canonical: " + canonicalExpr + "}";

        if (srcOptionalCSE != null) {
            return dst + " = " + srcOptionalCSE;
        }

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
            case SIMPLE: return dst + " = " + srcLeftOrSingle;
            case ARRAY_LOC: return dst + " = " + srcArray + "[" + srcArrayOffset + "]";
            default: throw new RuntimeException("Temp has no type: impossible to reach...");
        }
    }

    @Override
    public Pair<Temp, List<Temp>> getTemps() {
        Temp left = dstArrayOrLoc instanceof Temp ? (Temp) dstArrayOrLoc : null;
        List<Temp> right = new ArrayList<Temp>();
        if (dstArrayOffset != null && dstArrayOffset instanceof Temp) right.add((Temp) dstArrayOffset);
        if (srcLeftOrSingle != null && srcLeftOrSingle instanceof Temp) right.add((Temp) srcLeftOrSingle);
        if (srcRight != null && srcRight instanceof Temp) right.add((Temp) srcRight);
        if (srcArrayOffset != null && srcArrayOffset instanceof Temp) right.add((Temp) srcArrayOffset);
        return new Pair<Temp, List<Temp>>(left,right);
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
