package edu.mit.compilers.cfg;

import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.assembly.TempCollector;
import edu.mit.compilers.cfg.innercfg.InnerCFNode;
import edu.mit.compilers.cfg.innercfg.InnerCollectSubExpressions;
import edu.mit.compilers.cfg.innercfg.InnerMethodAssemblyCollector;
import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.BinOp;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFConditional extends UIDObject implements CFNode {
    @Override public String toString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MethodCFGFactory.dfsPrint(miniCFG, new HashSet<Integer>(), new PrintStream(baos));
        return "\nMiniCFG: " + baos.toString() + "\n" +
        "UID " + UID + " CFConditional [ miniCFG=" + miniCFG.getUID() + ", boolTemp=" + boolTemp + ", ifTrue=" + ifTrue.getUID() + ", ifFalse=" + ifFalse.getUID() + "], Scope = " + variableTable.getUID();
    }

    private InnerCFNode miniCFG;
    private Expr boolExpr;
    private CFNode ifTrue;
    private CFNode ifFalse;
    private Set<CFNode> parents = new HashSet<CFNode>();
    private final VariableTable variableTable;

    private Temp boolTemp; // Call TempifySubExpressions to populate

    public CFConditional(Expr expr, CFNode ifTrue, CFNode ifFalse, VariableTable variableTable) {
        this.boolExpr = expr;
        this.ifTrue= ifTrue;
        ifTrue.addParent(this);

        this.ifFalse = ifFalse;
        ifFalse.addParent(this);
        this.variableTable = variableTable;
    }

    public Expr getBoolExpr() {
        return boolExpr;
    }

    public void setMiniCFG(InnerCFNode miniCFG) {
        this.miniCFG = miniCFG;
    }

    public void replaceExpr(Temp temp) {
        this.boolTemp = temp;
        this.boolExpr = null;
    }

    @Override
    public List<String> toAssembly(ImportTable importTable) {
        List<String> assembly = new ArrayList<>();

        assembly.add(getAssemblyLabel() + ":");
        assembly.addAll(new InnerMethodAssemblyCollector(miniCFG, importTable).getInstructions());
        assembly.add(getEndOfMiniCFGLabel() + ":");

        List<String> body = new ArrayList<>();

        if (this.boolTemp == null) {
            throw new UnsupportedOperationException("You must call TempifySubExpressions on a CFConditional before collecting Assembly code!");
        }

        body.add("cmpq $1, -" + boolTemp.getOffset() + "(%rbp) # true = " + boolTemp);
        body.add("jne " + ifFalse.getAssemblyLabel() + " # ifFalse");
        body.add("jmp " + ifTrue.getAssemblyLabel() + " # ifTrue");

        assembly.addAll(AssemblyFactory.indent(body));
        return assembly;
    }

    @Override
    public Set<CFNode> parents() {
        return this.parents ;
    }

    @Override
    public void setNext(CFNode next) {
        throw new UnsupportedOperationException("CFConditional doesn't support setNext");
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
        TempCollector collector = new TempCollector();
        miniCFG.accept(collector);
        return collector.temps;
    }


    @Override
    public Set<Expr> getSubExpressions() {
        InnerCollectSubExpressions collector = new InnerCollectSubExpressions();
        this.miniCFG.accept(collector);
        return collector.subExpressions;
    }

    @Override
    public String getAssemblyLabel() {
        return "_conditional_" + UID;
    }

    @Override
    public String getEndOfMiniCFGLabel() {
        return "_end_of_conditional_" + UID;
    }
}
