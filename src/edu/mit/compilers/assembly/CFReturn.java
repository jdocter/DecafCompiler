package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFReturn extends UIDObject implements CFNode {


    @Override public String toString() {
        return "UID " + UID + " CFReturn [returnExpr=" + returnExpr + "]";
    }

    private final List<CFStatement> statements = new ArrayList<CFStatement>();
    private CFNode next;
    boolean isEnd; // end of function
    private Expr returnExpr;
    private Temp returnTemp;
    private final Set<CFNode> parents = new HashSet<CFNode>();
    private final VariableTable variableTable;
    private boolean isVoid;
    private CFNode miniCFG;


    public CFReturn(Expr returnExpr, VariableTable variableTable) {
        this.returnExpr = returnExpr;
        this.variableTable = variableTable;
        isVoid = returnExpr == null ? true : false;
    }

    public Expr getReturnExpr() {
        return returnExpr;
    }

    public void replaceExpr(Temp temp) {
        this.returnExpr = null;
        this.returnTemp = temp;
    }

    public void setMiniCFG(CFNode miniCFG) {
        this.miniCFG = miniCFG;
    }

    public CFNode getMiniCFG() {
        return miniCFG;
    }

    @Override
    public List<String> toAssembly() {
        return null;
    }

    @Override
    public CFNode getNext() {
        return next;
    }

    @Override
    public Set<CFNode> parents() {
        return this.parents;
    }

    @Override
    public void setNext(CFNode next) {
        throw new UnsupportedOperationException("Tried setNext on a Return.  a return should not go anywhere");
    }

    @Override
    public void addParent(CFNode parent) {
        this.parents.add(parent);
    }

    @Override
    public List<CFNode> dfsTraverse() {
        return List.of();
    }

    @Override
    public void removeParent(CFNode parent) {
        this.parents.remove(parent);
    }

    @Override
    public void replacePointers(CFNode original, CFNode replacement) {
        if (this.next == original) {
            this.setNext(replacement);
        }
    }

    @Override
    public VariableTable getVariableTable() {
        return variableTable;
    }

    @Override
    public void accept(CFVisitor v) {
        v.visit(this);
    }
}
