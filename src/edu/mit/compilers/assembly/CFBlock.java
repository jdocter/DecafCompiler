package edu.mit.compilers.assembly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.util.UIDObject;
import edu.mit.compilers.visitor.CFVisitor;


public class CFBlock extends UIDObject implements CFNode {

    // Should all be either CFAssign or CFMethodCall
    List<CFStatement> statements = new ArrayList<CFStatement>();
    CFNode next;

    boolean isEnd; // end of function
    private Set<CFNode> parents = new HashSet<CFNode>();

    public CFBlock(CFAssign cfAssign) {
        this.statements.add(cfAssign);
    }

    public CFBlock(CFMethodCall methodCall) {
        this.statements.add(methodCall);
    }

    public void prependBlock(CFBlock beforeBlock) {
        List<CFStatement> newStatements = new ArrayList<>(beforeBlock.statements); // defensive copy
        newStatements.addAll(statements);
        statements = newStatements;
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
    public List<String> toAssembly(VariableTable variableTable, MethodTable methodTable) {
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
        if (isEnd) return "UID " + UID + " CFBlock [" + statements + "]";
        return "UID " + UID + " CFBlock [" + statements + ", next=" + next.getUID() + "]";
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
    public void replacePointers(CFNode original, CFNode replacement) {
        if (this.next == original) {
            this.setNext(replacement);
        }
    }
}
