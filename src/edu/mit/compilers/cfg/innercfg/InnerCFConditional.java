package edu.mit.compilers.cfg.innercfg;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.BinOp;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MiniCFVisitor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InnerCFConditional extends UIDObject implements InnerCFNode {
    @Override public String toString() {
        return "UID " + UID + " CFConditional [ifTrue=" + ifTrue.getUID() + ", ifFalse=" + ifFalse.getUID() + "], Scope = " + variableTable.getUID();
    }

    private InnerCFNode ifTrue;
    private InnerCFNode ifFalse;
    private Set<InnerCFNode> parents = new HashSet<InnerCFNode>();
    private final VariableTable variableTable;

    private int type;

    private Temp boolTemp;

    private Temp left;
    private BinOp binOp;
    private Temp right;

    public InnerCFConditional(Temp temp, InnerCFNode ifTrue, InnerCFNode ifFalse, VariableTable variableTable) {
        this.boolTemp = temp;
        this.left = null;
        this.binOp = null;
        this.right = null;

        this.ifTrue= ifTrue;
        ifTrue.addParent(this);

        this.ifFalse = ifFalse;
        ifFalse.addParent(this);
        this.variableTable = variableTable;
    }

    @Override
    public List<String> toAssembly(ImportTable importTable) {
        List<String> assembly = new ArrayList<>();

        assembly.add(getAssemblyLabel() + ":");
        List<String> body = new ArrayList<>();
        body.add("cmpq $1, -" + boolTemp.getOffset() + "(%rbp) # true = " + boolTemp);
        body.add("jne " + ifFalse.getAssemblyLabel() + " # ifFalse");
        body.add("jmp " + ifTrue.getAssemblyLabel() + " # ifTrue");

        assembly.addAll(AssemblyFactory.indent(body));
        return assembly;
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
    public Set<InnerCFNode> parents() {
        return this.parents ;
    }

    @Override
    public void setNext(InnerCFNode next) {
        throw new UnsupportedOperationException("CFConditional doesn't support setNext");
    }

    @Override
    public void addParent(InnerCFNode parent) {
        this.parents.add(parent);
    }

    @Override
    public InnerCFNode getNext() {
        throw new UnsupportedOperationException("Don't know how to find NEXT for a CFConditional");
    }

    @Override
    public List<InnerCFNode> dfsTraverse() {
        return List.of(ifTrue, ifFalse);
    }

    @Override
    public void accept(MiniCFVisitor v) {
        v.visit(this);
    }

    @Override
    public void removeParent(InnerCFNode parent) {
        this.parents.remove(parent);
    }

    @Override
    public VariableTable getVariableTable() {
        return variableTable;
    }

    @Override
    public void replacePointers(InnerCFNode original, InnerCFNode replacement) {
        if (this.ifFalse == original) {
            this.ifFalse = replacement;
            this.ifFalse.addParent(this);
        }
        if (this.ifTrue == original) {
            this.ifTrue = replacement;
            this.ifTrue.addParent(this);
        }
    }

    public InnerCFNode getIfTrue() {
        return ifTrue;
    }

    public InnerCFNode getIfFalse() {
        return ifFalse;
    }

    @Override
    public List<Pair<Temp, List<Temp>>> getTemps() {
        return List.of(new Pair<Temp, List<Temp>>(null, List.of(boolTemp)));
    }

    @Override
    public Set<Expr> getSubExpressions() {
        return Set.of();
    }

    @Override
    public Set<Expr> generatedExprs(Set<Expr> allExprs) {
        return Set.of();
    }

    @Override
    public Set<Expr> killedExprs(Set<Expr> allExprs) { return  Set.of(); }

    @Override
    public String getAssemblyLabel() {
        return "_conditional_" + UID;
    }

    @Override
    public String getEndOfMiniCFGLabel() {
        throw new UnsupportedOperationException("Inner Conditionals don't have mini CFGs");
    }
}
