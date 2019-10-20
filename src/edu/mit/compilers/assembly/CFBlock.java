package edu.mit.compilers.assembly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Statement;


public class CFBlock implements CFNode {

    // Should all be either LOC_ASSIGN, METHOD_CALL, or RETURN
    List<Statement> statements = new ArrayList<Statement>();
    CFNode next;

    boolean isEnd; // end of function
    private Set<CFNode> parents;

    public CFBlock(Statement statement) {
        int type = statement.statementType;
        assert type == Statement.LOC_ASSIGN ||
                type == Statement.METHOD_CALL ||
                type == Statement.RETURN;
        statements.add(statement);
        isEnd = true;
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
}
