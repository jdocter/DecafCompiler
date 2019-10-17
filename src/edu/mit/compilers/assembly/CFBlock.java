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
    CFBlock successor;

    Expr splitCondition; // if !isSplitPoint: condition is null
    CFBlock successorIfTrue; // if !isSplitPoint: successorIfTrue is null
    CFBlock successorIfFalse; // if !isSplitPoint: successorIfFalse is null

    Set<CFBlock> parents = new HashSet<CFBlock>();

    boolean isEnd; // end of function
    private boolean isSplitPoint;

    public CFBlock(Statement statement) {
        int type = statement.statementType;
        assert type == Statement.LOC_ASSIGN ||
                type == Statement.METHOD_CALL ||
                type == Statement.RETURN;
        statements.add(statement);
        isEnd = true;
    }

    public CFBlock(Expr condition, CFBlock ifTrue, CFBlock ifFalse) {
        splitCondition = condition;

        successorIfTrue = ifTrue;
        ifTrue.parents.add(this);
        successorIfFalse = ifFalse;
        ifFalse.parents.add(this);

        isEnd = false;
    }

    void setNext(CFBlock next) {
        isEnd = false;
    }

    @Override
    public List<String> getAssemblyCode(VariableTable variableTable, MethodTable methodTable) {
        // TODO Auto-generated method stub

        // Visitor?
        return List.of();
    }

    @Override
    public List<CFNode> parents() {
        // TODO Auto-generated method stub
        return parents;
    }

    @Override
    public boolean isSplitPoint() {
        // TODO Auto-generated method stub
        return isSplitPoint;
    }

}
