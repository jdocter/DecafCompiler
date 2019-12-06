package edu.mit.compilers.cfg.innercfg;

import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.cfg.SharedTemp;
import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.cfg.Variable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.BinOp;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Lit;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.StatementCFVisitor;

import java.util.*;

public class CFAssign extends CFStatement {

    private int type;
    private final VariableTable variableTable;

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
    public AssemblyVariable dstOptionalCSE;
    // Optional additional source for expression, to be used for Common Subexpression Elimination
    public AssemblyVariable srcOptionalCSE;

    public static final String ASSIGN = "=";
    public static final String PEQ = "+=";
    public static final String MEQ = "-=";
    public static final String INC = "++";
    public static final String DEC = "--";

    private CFAssign(Expr canonicalExpr, VariableTable variableTable) {
        this.canonicalExpr = canonicalExpr;
        this.variableTable = variableTable;
    }

    ;

    public static CFAssign makeSimple(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, String assignExprOp, AssemblyVariable assignExpr, Expr canonicalExpr, VariableTable variableTable) {
        CFAssign result = new CFAssign(canonicalExpr, variableTable);
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.assignOp = assignExprOp;
        result.srcLeftOrSingle = assignExpr;
        result.type = SIMPLE;
        return result;
    }


    public static CFAssign makeMinus(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, AssemblyVariable operand, Expr canonicalExpr, VariableTable variableTable) {
        CFAssign result = new CFAssign(canonicalExpr, variableTable);
        result.type = MINUS;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLeftOrSingle = operand;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign makeLen(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Variable id, Expr canonicalExpr, VariableTable variableTable) {
        CFAssign result = new CFAssign(canonicalExpr, variableTable);
        result.type = LEN;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcId = id;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign makeLit(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Lit lit, Expr canonicalExpr, VariableTable variableTable) {
        CFAssign result = new CFAssign(canonicalExpr, variableTable);
        result.type = LIT;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLit = lit;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign makeArrayLocAssign(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Variable srcArray, AssemblyVariable srcArrayOffset, Expr canonicalExpr, VariableTable variableTable) {
        CFAssign result = new CFAssign(canonicalExpr, variableTable);
        result.type = ARRAY_LOC;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcArray = srcArray;
        result.srcArrayOffset = srcArrayOffset;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign makeLocAssign(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Variable src, Expr canonicalExpr, VariableTable variableTable) {
        CFAssign result = new CFAssign(canonicalExpr, variableTable);
        result.type = SIMPLE;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLeftOrSingle = src;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign makeLoadRax(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Expr canonicalExpr, VariableTable variableTable) {
        CFAssign result = new CFAssign(canonicalExpr, variableTable);
        result.type = METHOD_CALL;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.assignOp = ASSIGN;
        // expr = "load %rax"
        return result;
    }

    public static CFAssign makeNot(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, AssemblyVariable notOperand, Expr canonicalExpr, VariableTable variableTable) {
        CFAssign result = new CFAssign(canonicalExpr, variableTable);
        result.type = MINUS;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLeftOrSingle = notOperand;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign assignTrue(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Expr canonicalExpr, VariableTable variableTable) {
        CFAssign result = new CFAssign(canonicalExpr, variableTable);
        result.type = TRUE;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign assignFalse(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, Expr canonicalExpr, VariableTable variableTable) {
        CFAssign result = new CFAssign(canonicalExpr, variableTable);
        result.type = FALSE;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.assignOp = ASSIGN;
        return result;
    }

    public static CFAssign assignBinOp(AssemblyVariable dstArrayOrLoc, AssemblyVariable arrayOffset, AssemblyVariable left, BinOp binOp, AssemblyVariable right, Expr canonicalExpr, VariableTable variableTable) {
        CFAssign result = new CFAssign(canonicalExpr, variableTable);
        result.type = BIN_OP;
        result.dstArrayOrLoc = dstArrayOrLoc;
        result.dstArrayOffset = arrayOffset;
        result.srcLeftOrSingle = left;
        result.srcBinOp = binOp;
        result.srcRight = right;
        result.assignOp = ASSIGN;
        return result;
    }

    public int getType() {
        return type;
    }

    /**
     * Add another destination variable for the RHS of this assign such
     * that toAssembly includes code for storing the RHS value in dst
     *
     * @param dst variable that the RHS should also be stored in
     */
    public void setAdditionalDestination(AssemblyVariable dst) {
        dstOptionalCSE = dst;
    }

    /**
     * Add an alternative source variable for the RHS of this assign such
     * that toAssembly includes code for retrieving the RHS value from src
     * instead of calculating it again.
     *
     * @param src variable that the RHS should be retried from
     */
    public void setAlternativeSource(AssemblyVariable src) {
        srcLeftOrSingle = null;
        srcBinOp = null;
        srcRight = null;
        srcId = null;
        srcArray = null;
        srcArrayOffset = null;
        srcLit = null;
        srcOptionalCSE = src;
    }


    @Override
    public Optional<Expr> generatedExpr() {
        if (assignOp != ASSIGN) return Optional.empty();
        if (dstArrayOffset == null && !dstArrayOrLoc.isTemporary() // check that it is type Id
                && canonicalExpr.getIds().contains(((Variable)dstArrayOrLoc).getId())) return Optional.empty();
        if (canonicalExpr.containsMethodCall()) return Optional.empty();
        return Optional.of(canonicalExpr);
    }

    @Override
    public Set<Expr> killedExprs(Set<Expr> exprs) {
        Set<Expr> killed = new HashSet<>();
        // if is an array, should kill all usages of that array
        // if not an array, should kill all usages of that ID
        // Either way, dstArrayOrLoc must not be temporary
        // (for arrays, it is never temporary)
        if (!dstArrayOrLoc.isTemporary()) {
            for (Expr subExpr : exprs) {
                if (subExpr.getIds().contains(((Variable) dstArrayOrLoc).getId())) {
                    killed.add(subExpr);
                }
            }
        }
        return killed;
    }

    @Override
    public Expr getRHS() {
        return this.canonicalExpr;
    }

    @Override
    public Set<SharedTemp> getSharedTemps() {
        Set<SharedTemp> result = new HashSet<>();

        if (dstOptionalCSE != null && dstOptionalCSE instanceof SharedTemp) result.add((SharedTemp) dstOptionalCSE);
        if (srcOptionalCSE != null && srcOptionalCSE instanceof SharedTemp) result.add((SharedTemp) srcOptionalCSE);
        return result;
    }

    @Override
    public void accept(StatementCFVisitor v) {
        v.visit(this);
    }

    @Override
    public Set<AssemblyVariable> getLocalAssemblyVariables() {
        HashSet<AssemblyVariable> assemblyVariables = new HashSet<>();

        if (null != dstArrayOffset) assemblyVariables.add(dstArrayOffset);
        if (null != dstArrayOrLoc) assemblyVariables.add(dstArrayOrLoc);
        if (null != dstOptionalCSE) assemblyVariables.add(dstOptionalCSE);
        if (null != srcOptionalCSE) {
            assemblyVariables.add(srcOptionalCSE);
            return assemblyVariables;
        }
        if (null != srcLeftOrSingle) assemblyVariables.add(srcLeftOrSingle);
        if (null != srcRight) assemblyVariables.add(srcRight);
        if (null != srcId) assemblyVariables.add(srcId);
        if (null != srcArray) assemblyVariables.add(srcArray);
        if (null != srcArrayOffset) assemblyVariables.add(srcArrayOffset);
        return assemblyVariables;
    }

    public VariableTable getVariableTable() {
        return variableTable;
    }

    @Override
    public Set<AssemblyVariable> getDefined() {
        HashSet<AssemblyVariable> assemblyVariables = new HashSet<>();
        // TODO exclude global?
        // if (null != dstArrayOrLoc && null == dstArrayOffset && !dstArrayOrLoc.isGlobal(variableTable)) assemblyVariables.add(dstArrayOrLoc);
        if (null != dstArrayOrLoc && null == dstArrayOffset) assemblyVariables.add(dstArrayOrLoc);
        if (null != dstOptionalCSE) assemblyVariables.add(dstOptionalCSE);
        return assemblyVariables;
    }

    @Override
    public Set<AssemblyVariable> getUsed() {
        HashSet<AssemblyVariable> assemblyVariables = new HashSet<>();

        if (!assignOp.equals(ASSIGN) && null != dstArrayOrLoc && null == dstArrayOffset)
            assemblyVariables.add(dstArrayOrLoc);
        if (null != dstArrayOffset) assemblyVariables.add(dstArrayOffset);
        if (null != srcOptionalCSE) {
            assemblyVariables.add(srcOptionalCSE);
            return assemblyVariables;
        }
        if (null != srcLeftOrSingle) assemblyVariables.add(srcLeftOrSingle);
        if (null != srcRight) assemblyVariables.add(srcRight);
        if (null != srcId) assemblyVariables.add(srcId);
        if (null != srcArray) assemblyVariables.add(srcArray);
        if (null != srcArrayOffset) assemblyVariables.add(srcArrayOffset);
        return assemblyVariables;
    }

    @Override
    public String toString() {
        String offsetStr = dstArrayOffset == null ? "" : "[" + dstArrayOffset + "]";
	    String additionalDst = dstOptionalCSE == null ? "" : dstOptionalCSE + ", ";
        String dst = additionalDst + dstArrayOrLoc + offsetStr;

        if (srcOptionalCSE != null) {
            return dst + " " + assignOp + " " + srcOptionalCSE + " {canonical: " + canonicalExpr + "}";
        }

        switch (assignOp) {
            case MEQ:
            case PEQ:
                return dst + " " + assignOp + " " + srcLeftOrSingle;
            case INC:
            case DEC:
                return dst + " " + assignOp;
            case ASSIGN:
                break; // everything else should be assign
        }
        switch (type) {
            case LEN:
                return dst + " = len(" + srcId + ")";
            case MINUS:
                return dst + " = -" + srcLeftOrSingle;
            case NOT:
                return dst + " = !" + srcLeftOrSingle;
            case METHOD_CALL:
                return dst + " = load %rax";
            case BIN_OP:
                return dst + " = " + srcLeftOrSingle + " " + srcBinOp + " " + srcRight + " {canonical: " + canonicalExpr + "}";
            case LIT:
                return dst + " = " + srcLit;
            case TRUE:
                return dst + " = true";
            case FALSE:
                return dst + " = false";
            case SIMPLE:
                return dst + " = " + srcLeftOrSingle + " {canonical: " + canonicalExpr + "}";
            case ARRAY_LOC:
                return dst + " = " + srcArray + "[" + srcArrayOffset + "]";
            default:
                throw new RuntimeException("Temp has no type: impossible to reach...");
        }
    }

    @Override
    public Pair<List<Temp>, List<Temp>> getTemps() {
        List<Temp> left = new ArrayList<>();
        if (dstArrayOrLoc instanceof Temp) {
            left.add((Temp) dstArrayOrLoc);
        }
        if (dstOptionalCSE != null && dstOptionalCSE instanceof Temp) {
            left.add((Temp) dstOptionalCSE);
        }
        List<Temp> right = new ArrayList<Temp>();
        if (srcOptionalCSE != null && srcOptionalCSE instanceof Temp) {
            right.add((Temp) srcOptionalCSE);
        } else {
            if (dstArrayOffset != null && dstArrayOffset instanceof Temp) right.add((Temp) dstArrayOffset);
            if (srcLeftOrSingle != null && srcLeftOrSingle instanceof Temp) right.add((Temp) srcLeftOrSingle);
            if (srcRight != null && srcRight instanceof Temp) right.add((Temp) srcRight);
            if (srcArrayOffset != null && srcArrayOffset instanceof Temp) right.add((Temp) srcArrayOffset);
        }
        return new Pair<List<Temp>, List<Temp>>(left, right);
    }

    public String getBinopCommand() {
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
            case BinOp.EQ:
                return "sete";
            case BinOp.NEQ:
                return "setne";
            case BinOp.LT:
                return "setl";
            case BinOp.GT:
                return "setg";
            case BinOp.LEQ:
                return "setle";
            case BinOp.GEQ:
                return "setge";
            default:
                throw new RuntimeException("Unrecognized binOp: " + srcBinOp.binOp);
        }
    }

}
