package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Loc;
import edu.mit.compilers.parser.Statement;
import edu.mit.compilers.util.UIDObject;

import java.util.List;

public class CFAssign extends UIDObject implements CFStatement {

    public final Loc loc;
    public final String assignOp;
    public final Expr expr;

    public static final String ASSIGN = "=";
    public static final String PEQ = "+=";
    public static final String MEQ = "-=";
    public static final String INC = "++";
    public static final String DEC = "--";

    CFAssign(Loc loc, String assignOp, Expr expr) {
        this.loc = loc;
        this.assignOp = assignOp;
        this.expr = expr;
    }

    CFAssign(Statement statement) {
        assert statement.statementType == Statement.LOC_ASSIGN;
        this.loc = statement.loc;
        this.assignOp = statement.assignExpr.assignExprOp;
        this.expr = statement.assignExpr.expr;
    }

    @Override
    public List<String> toAssembly(VariableTable variableTable, MethodTable methodTable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public String toString() {
        if (expr == null) {
            return "" + loc + assignOp;
        }
        return "" + loc + " " + assignOp + " " + expr;
    }
}
