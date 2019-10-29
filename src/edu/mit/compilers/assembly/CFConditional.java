package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.BinOp;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFConditional extends UIDObject implements CFNode {
    @Override public String toString() {
        return "UID " + UID + " CFConditional [boolExpr=" + boolExpr + ", ifTrue=" + ifTrue.getUID() + ", ifFalse=" + ifFalse.getUID() + "], Scope = " + variableTable.getUID();
    }

    private CFNode miniCFG;
    private final List<CFStatement> cfStatements = new ArrayList<CFStatement>();
    private final boolean isOuter;
    private Expr boolExpr;
    private CFNode ifTrue;
    private CFNode ifFalse;
    private Set<CFNode> parents = new HashSet<CFNode>();
    private final VariableTable variableTable;

    public static final int SINGLE_TEMP = 0;
    public static final int CMP = 1;
    private int type;

    private Temp boolTemp;

    private Temp left;
    private BinOp binOp;
    private Temp right;

    public CFConditional(Expr expr, CFNode ifTrue, CFNode ifFalse, VariableTable variableTable) {
        this.boolExpr = expr;
        this.ifTrue= ifTrue;
        ifTrue.addParent(this);

        this.ifFalse = ifFalse;
        ifFalse.addParent(this);
        this.variableTable = variableTable;
        isOuter = true;
    }

    public CFConditional(Temp temp, CFNode ifTrue, CFNode ifFalse, VariableTable variableTable) {
        this.type = SINGLE_TEMP;
        this.boolTemp = temp;
        this.left = null;
        this.binOp = null;
        this.right = null;

        this.ifTrue= ifTrue;
        ifTrue.addParent(this);

        this.ifFalse = ifFalse;
        ifFalse.addParent(this);
        this.variableTable = variableTable;

        isOuter = false;
    }

    public CFConditional(Temp left, BinOp binOp, Temp right, CFNode ifTrue, CFNode ifFalse, VariableTable variableTable) {
        this.type = CMP;
        this.boolTemp = null;
        this.left = left;
        this.binOp = binOp;
        this.right = right;

        this.ifTrue= ifTrue;
        ifTrue.addParent(this);

        this.ifFalse = ifFalse;
        ifFalse.addParent(this);
        this.variableTable = variableTable;

        isOuter = false;
    }

    public Expr getBoolExpr() {
        if (!isOuter) throw new RuntimeException("inner");
        return boolExpr;
    }

    public void setMiniCFG(CFNode miniCFG) {
        if (!isOuter) throw new RuntimeException("inner");
        this.miniCFG = miniCFG;
    }

    public void replaceExpr(Temp temp) {
        if (!isOuter) throw new RuntimeException("inner");
        this.type = SINGLE_TEMP;
        this.boolTemp = temp;
        this.boolExpr = null;
    }

    @Override
    public List<String> toAssembly(ImportTable importTable) {
        // Trying not to share code between isOuter and !isOuter
        if (isOuter) {
            List<String> assembly = new ArrayList<>();

            assembly.add(getAssemblyLabel() + ":");
            assembly.addAll(new MethodAssemblyCollector(miniCFG, importTable).getInstructions());
            assembly.add(getEndOfMiniCFGLabel() + ":");

            List<String> body = new ArrayList<>();
            if (this.type == SINGLE_TEMP) {
                body.add("cmp $1, -" + boolTemp.getOffset() + "(%rbp) # true = " + boolTemp);
                body.add("jne " + ifFalse.getAssemblyLabel() + " # ifFalse");
                body.add("jmp " + ifTrue.getAssemblyLabel() + " # ifTrue");
            } else {
                throw new UnsupportedOperationException("Outer Conditionals should be SINGLE_TEMP");
            }
            assembly.addAll(AssemblyFactory.indent(body));
            return assembly;
        } else {
            List<String> assembly = new ArrayList<>();

            assembly.add(getAssemblyLabel() + ":");
            List<String> body = new ArrayList<>();

            switch (type) {
                case SINGLE_TEMP:
                    body.add("cmpq $1, -" + boolTemp.getOffset() + "(%rbp) # true = " + boolTemp);
                    body.add("jne " + ifFalse.getAssemblyLabel() + " # ifFalse");
                    body.add("jmp " + ifTrue.getAssemblyLabel() + " # ifTrue");
                    break;
                case CMP:
                    body.add("movq -" + left.getOffset() + "(%rbp), %rax");
                    /*
                     * WARNING: confusing quirk of AT&T syntax
                     * to test a < b
                     * Do cmp b, a
                     * jl ifTrue # jump if a < b
                     */
                    body.add("cmpq -" + right.getOffset() + "(%rbp), %rax # minuend on the right: " + left + " " + binOp + " " + right);
                    body.add(getOppositeJumpCommand() + " " + ifFalse.getAssemblyLabel() + " # ifFalse");
                    body.add("jmp " + ifTrue.getAssemblyLabel() + " # ifTrue");
                    break;
                default:
                    throw new RuntimeException("Unrecognized type: " + type);
            }

            assembly.addAll(AssemblyFactory.indent(body));
            return assembly;
        }
    }

    private String getOppositeJumpCommand() {
        switch (binOp.binOp) {
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
                return "jne";
            case BinOp.NEQ:
                return "je";
            case BinOp.LT:
                return "jge";
            case BinOp.GT:
                return "jle";
            case BinOp.LEQ:
                return "jg";
            case BinOp.GEQ:
                return "jl";
            default:
                throw new RuntimeException("Unrecognized binOp: " + binOp.binOp);
        }
    }

    @Override
    public Set<CFNode> parents() {
        return this.parents ;
    }

    @Override
    public void setNext(CFNode next) {
        throw new UnsupportedOperationException("CFConditional doesn't support setNext");
//        if (this.ifTrue != this) {
//            this.ifTrue.setNext(next);
//        }
//        if (this.ifFalse != this) {
//            this.ifFalse.setNext(next);
//        }
    }

    @Override
    public void addParent(CFNode parent) {
        this.parents.add(parent);
    }

    @Override
    public CFNode getNext() {
        throw new UnsupportedOperationException("Don't know how to find NEXT for a CFConditional");
    }

    @Override
    public List<CFNode> dfsTraverse() {
        return List.of(ifTrue, ifFalse);
    }

    @Override
    public void accept(CFVisitor v) {
        v.visit(this);
    }

    @Override
    public void removeParent(CFNode parent) {
        this.parents.remove(parent);
    }

    @Override
    public VariableTable getVariableTable() {
        return variableTable;
    }

    @Override
    public void replacePointers(CFNode original, CFNode replacement) {
        if (this.ifFalse == original) {
            this.ifFalse = replacement;
            this.ifFalse.addParent(this);
        }
        if (this.ifTrue == original) {
            this.ifTrue = replacement;
            this.ifTrue.addParent(this);
        }
    }

    public CFNode getIfTrue() {
        return ifTrue;
    }

    public CFNode getIfFalse() {
        return ifFalse;
    }

    @Override
    public List<Pair<Temp, List<Temp>>> getTemps() {
        if (isOuter) {
            TempCollector collector = new TempCollector();
            miniCFG.accept(collector);
            return collector.temps;
        }
        switch (type) {
            case SINGLE_TEMP:
                return List.of(new Pair<Temp, List<Temp>>(null, List.of(boolTemp)));
            case CMP:
                return List.of(new Pair<Temp, List<Temp>>(null, List.of(left, right)));
            default:
                throw new RuntimeException("Unrecognized CFConditional type: " + type);
        }
    }

    @Override
    public String getAssemblyLabel() {
        return "_conditional_" + UID;
    }

    @Override
    public String getEndOfMiniCFGLabel() {
        if (isOuter) return "_end_of_conditional_" + UID;
        throw new UnsupportedOperationException("Inner Conditionals don't have mini CFGs");
    }
}
