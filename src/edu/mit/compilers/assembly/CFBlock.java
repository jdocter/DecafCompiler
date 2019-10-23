package edu.mit.compilers.assembly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.inter.LocalTable;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;


public class CFBlock extends UIDObject implements CFNode {

    // Should all be either CFAssign or CFMethodCall
    List<CFStatement> statements = new ArrayList<CFStatement>();
    CFNode next;

    boolean isEnd; // end of function
    private Set<CFNode> parents = new HashSet<CFNode>();
    private final VariableTable variableTable;

    public CFBlock(CFAssign cfAssign, VariableTable variableTable) {
        this.statements.add(cfAssign);
        this.variableTable = variableTable;
    }

    public CFBlock(CFMethodCall methodCall, VariableTable variableTable) {
        this.statements.add(methodCall);
        this.variableTable = variableTable;
    }

    /**
     * Prepend the block if possible, or else return False for failure.
     */
    public boolean tryPrependBlock(CFBlock beforeBlock) {
        if (this.variableTable != beforeBlock.variableTable) {
            return false;
        }
        List<CFStatement> newStatements = new ArrayList<>(beforeBlock.statements); // defensive copy
        newStatements.addAll(statements);
        statements = newStatements;
        return true;
    }

    @Override
    public void setNext(CFNode next) {
        isEnd = false;
        this.next = next;
        next.addParent(this);
    }

    @Override
    public CFNode getNext() {
        return next;
    }

    @Override
    public List<String> toAssembly(MethodTable methodTable) {
        // TODO Auto-generated method stub

        // Visitor?
        return List.of();
    }

    @Override
    public Set<CFNode> parents() {
        return this.parents;
    }

    @Override
    public void addParent(CFNode parent) {
        this.parents.add(parent);
    }

    @Override public String toString() {
        if (isEnd) return "UID " + UID + " CFBlock [" + statements + "], Scope = " + variableTable.getUID();
        return "UID " + UID + " CFBlock [" + statements + ", next=" + next.getUID() + "], Scope = " + variableTable.getUID();
    }

    @Override
    public List<CFNode> dfsTraverse() {
        if (isEnd) return List.of();
        return List.of(next);
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
        if (this.next == original) {
            this.setNext(replacement);
        }
    }
}
