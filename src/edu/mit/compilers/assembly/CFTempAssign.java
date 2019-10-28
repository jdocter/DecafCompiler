package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.BinOp;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.Lit;
import edu.mit.compilers.parser.Loc;
import edu.mit.compilers.parser.MethodCall;

import java.util.List;

public class CFTempAssign implements CFStatement {

    private int type;
    public static final int LEN = 0;
    public static final int MINUS = 1;
    public static final int NOT = 2;
    public static final int LOC = 3;
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
    public List<String> toAssembly(VariableTable variableTable) {
        return null;
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
        result.type = LOC;
        result.dest = temp;
        result.arrayName = id2;
        result.arrayOffset = tempLocExpr;
        return result;
    }

    public static CFStatement makeLoc(Temp temp, Id id2) {
        CFTempAssign result = new CFTempAssign();
        result.type = LOC;
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
}