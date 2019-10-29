package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.FieldDescriptor;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.LocalDescriptor;
import edu.mit.compilers.inter.TypeDescriptor;
import edu.mit.compilers.inter.VariableDescriptor;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.BinOp;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.Lit;
import edu.mit.compilers.parser.Loc;
import edu.mit.compilers.parser.MethodCall;
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


    private Temp dest;

    public Temp leftOrSingleTemp;
    public BinOp binOp;
    public Temp right;
    public Id id;
    public Id arrayName;
    public Temp arrayOffset;
    public Lit lit;

    @Override
    public List<String> toAssembly(VariableTable variableTable, ImportTable importTable) {
        final List<String> assembly = new ArrayList<>();

        List<String> body = new ArrayList<>();
        switch (type) {
            case LEN:
                TypeDescriptor argType = variableTable.getDescriptor(id.getName()).getTypeDescriptor();

                if (argType.isArray()) {
                    body.add("movq $" + argType.getLength() + ", -" + dest.getOffset() + "(%rbp) # len(" + dest + ")");
                } else {
                    throw new RuntimeException("Failed semantic checks");
                }
                break;
            case MINUS:
                body.add("movq -" + leftOrSingleTemp.getOffset() + "(%rbp), %rax # " + dest + " = -" + leftOrSingleTemp);
                body.add("negq %rax");
                body.add("movq %rax, -" + dest.getOffset() + "(%rbp)");
                break;
            case NOT:
                body.add("movq -" + leftOrSingleTemp.getOffset() + "(%rbp), %rax # " + dest + " = !" + leftOrSingleTemp);
                body.add("xorq $1, %rax");
                body.add("movq %rax, -" + dest.getOffset() + "(%rbp)");
                break;
            case ARRAY_LOC:
                VariableDescriptor arrayDescriptor = variableTable.getDescriptor(id.getName());
                TypeDescriptor arrayTypeDescriptor = arrayDescriptor.getTypeDescriptor();
                String arrayLoc;
                if (arrayDescriptor.isGlobal()) {
                    assembly.add("movq -" +arrayOffset.getOffset()+"(%rbp), %rax"); // val of temp into rax
                    assembly.add("leaq 0(,%rax," + arrayTypeDescriptor.elementSize() + "), %rcx"); // temp * element size
                    assembly.add("leaq " + id.getName() + "(%rip), %rax"); // address of base of global array
                    arrayLoc = "(%rcx,%rax)";

                } else {
                    LocalDescriptor localDescriptor = (LocalDescriptor) arrayDescriptor;
                    arrayLoc = "-"+localDescriptor.getStackOffset()+"(%rbp,%rax,"+localDescriptor.getTypeDescriptor().elementSize()+")";
                }

                body.add("movq " + arrayLoc + ", %rax");
                body.add("movq %rax, -" + dest.getOffset() + "(%rbp)");

                break;
            case SINGLE_LOC:
                VariableDescriptor varDescriptor = variableTable.getDescriptor(id.getName());
                TypeDescriptor varTypeDescriptor = varDescriptor.getTypeDescriptor();
                String varLoc;
                if (varDescriptor.isGlobal()) {
                    varLoc = "_global_" + ((FieldDescriptor) varDescriptor).getName() + "(%rip)";
                } else {
                    varLoc = "-"+ ((LocalDescriptor) varDescriptor).getStackOffset()+"(%rbp)";
                }

                body.add("movq " + varLoc + ", %rax");
                body.add("movq %rax, -" + dest.getOffset() + "(%rbp)");

                break;
            case METHOD_CALL:
                body.add("");
                body.add("movq %rax, -" + dest.getOffset() + "(%rbp)");
                body.add("");
                break;
            case BIN_OP:
                switch (binOp.binOp) {
                    case BinOp.AND:
                    case BinOp.OR:
                        throw new RuntimeException("Didn't expect binOp of type " + binOp.binOp);
                    case BinOp.EQ:
                    case BinOp.NEQ:
                    case BinOp.GEQ:
                    case BinOp.LEQ:
                    case BinOp.GT:
                    case BinOp.LT:

                    case BinOp.MINUS:
                    case BinOp.MOD:
                    case BinOp.MUL:
                    case BinOp.PLUS:
                    case BinOp.DIV:
                }
                break;
            case LIT:
                // TODO runtime checks
                body.add("movq " + lit.toAssembly() + " -" + dest.getOffset() + "(%rbp) # len(" + dest + "");
                break;
            case TRUE:
                body.add("movq $1, -" + dest.getOffset() + "(%rbp) # true -> " + dest);
                break;
            case FALSE:
                body.add("movq $0, -" + dest.getOffset() + "(%rbp) # false -> " + dest);
                break;
            default: throw new RuntimeException("Temp has no type: impossible to reach...");
        }

        assembly.addAll(AssemblyFactory.indent(body));
        return assembly;
    }

    @Override
    public int getUID() {
        return 0;
    }

    public static CFTempAssign makeMinus(Temp dest, Temp operand) {
        CFTempAssign result = new CFTempAssign();
        result.type = MINUS;
        result.dest = dest;
        result.leftOrSingleTemp = operand;
        return result;
    }

    public static CFTempAssign makeLen(Temp temp2, Id id) {
        CFTempAssign result = new CFTempAssign();
        result.type = LEN;
        result.dest = temp2;
        result.id = id;
        return result;
    }

    public static CFTempAssign makeLit(Temp temp, Lit lit2) {
        CFTempAssign result = new CFTempAssign();
        result.type = LIT;
        result.dest = temp;
        result.lit = lit2;
        return result;
    }

    public static CFStatement makeLoc(Temp temp, Id id2, Temp tempLocExpr) {
        CFTempAssign result = new CFTempAssign();
        result.type = ARRAY_LOC;
        result.dest = temp;
        result.arrayName = id2;
        result.arrayOffset = tempLocExpr;
        return result;
    }

    public static CFStatement makeLoc(Temp temp, Id id2) {
        CFTempAssign result = new CFTempAssign();
        result.type = SINGLE_LOC;
        result.dest = temp;
        result.id = id2;
        return result;
    }

    public static CFStatement makeLoadRax(Temp temp) {
        CFTempAssign result = new CFTempAssign();
        result.type = METHOD_CALL;
        result.dest = temp;
        // expr = "load %rax"
        return result;
    }

    public static CFStatement makeNot(Temp temp, Temp tempNotOperand) {
        CFTempAssign result = new CFTempAssign();
        result.type = MINUS;
        result.dest = temp;
        result.leftOrSingleTemp = tempNotOperand;
        return result;
    }

    public static CFStatement assignTrue(Temp temp) {
        CFTempAssign result = new CFTempAssign();
        result.type = TRUE;
        result.dest = temp;
        return result;
    }

    public static CFStatement assignFalse(Temp temp) {
        CFTempAssign result = new CFTempAssign();
        result.type = FALSE;
        result.dest = temp;
        return result;
    }

    public static CFStatement assignBinOp(Temp temp, Temp left, BinOp binOp2, Temp right) {
        CFTempAssign result = new CFTempAssign();
        result.type = BIN_OP;
        result.dest = temp;
        result.leftOrSingleTemp = left;
        result.binOp = binOp2;
        result.right = right;
        return result;
    }

    @Override
    public String toString() {
        switch (type) {
            case LEN: return dest + " = len(" + id + ")";
            case MINUS: return dest + " = -"+ leftOrSingleTemp;
            case NOT: return dest + " = !" + leftOrSingleTemp;
            case LOC: return arrayOffset == null ? dest + " = " + id :
                    dest + " = " + arrayName + "[" + arrayOffset +"]";
            case METHOD_CALL: return dest + " = load %rax";
            case BIN_OP: return dest + " = " + leftOrSingleTemp + " " + binOp + " " + right;
            case LIT: return dest + " = " + lit;
            case TRUE: return dest + " = true";
            case FALSE: return dest + " = false";
            default: throw new RuntimeException("Temp has no type: impossible to reach...");
        }
    }

    @Override
    public Pair<Temp, List<Temp>> getTemps() {
        switch (type) {
            case LEN: return new Pair(dest, List.of());
            case MINUS: return new Pair(dest, List.of(leftOrSingleTemp));
            case NOT: return new Pair(dest, List.of(leftOrSingleTemp));
            case LOC: return arrayOffset == null ?  new Pair(dest, List.of()) :
                new Pair(dest, List.of(arrayOffset));
            case METHOD_CALL: return new Pair(dest, List.of());
            case BIN_OP: return new Pair(dest, List.of(leftOrSingleTemp, right));
            case LIT: return new Pair(dest, List.of());
            case TRUE: return new Pair(dest, List.of());
            case FALSE: return new Pair(dest, List.of());
            default: throw new RuntimeException("Temp has no type: impossible to reach...");
        }
    }
}