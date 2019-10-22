package edu.mit.compilers.assembly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Statement;
import edu.mit.compilers.util.UIDObject;


public class CFBlock extends UIDObject implements CFNode {

    // Should all be either LOC_ASSIGN, METHOD_CALL
    List<Statement> statements = new ArrayList<Statement>();
    CFNode next;

    boolean isEnd; // end of function
    private Set<CFNode> parents = new HashSet<CFNode>();

    public CFBlock(Statement statement) {
        int type = statement.statementType;
        assert type == Statement.LOC_ASSIGN ||
                type == Statement.METHOD_CALL;
        statements.add(statement);
        isEnd = true;
    }

    public CFBlock(CFAssign cfAssign) {

    }

    public CFBlock(CFMethodCall methodCall) {

    }

    @Override
    public void setNext(CFNode next) {
        isEnd = false;
        this.next = next;
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
}
