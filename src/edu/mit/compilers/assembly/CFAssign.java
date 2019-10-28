package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.*;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.Loc;
import edu.mit.compilers.parser.Statement;
import edu.mit.compilers.util.Pair;
import edu.mit.compilers.util.UIDObject;

import java.util.ArrayList;
import java.util.List;

public class CFAssign extends UIDObject implements CFStatement {

    public final Id arrayOrLoc;
    public final Temp arrayOffset;
    public final String assignOp;
    public final Temp expr;

    public static final String ASSIGN = "=";
    public static final String PEQ = "+=";
    public static final String MEQ = "-=";
    public static final String INC = "++";
    public static final String DEC = "--";

    CFAssign(Id id, String assignOp, Temp expr) {
        this.arrayOrLoc = id;
        this.arrayOffset = null;
        this.assignOp = assignOp;
        this.expr = expr;
    }

    public CFAssign(Id id, Temp tempLocExpr, String assignExprOp, Temp tempAssignExpr) {
        this.arrayOrLoc = id;
        this.arrayOffset = tempLocExpr;
        this.assignOp = assignExprOp;
        this.expr = tempAssignExpr;

    }

    @Override
    public List<String> toAssembly(VariableTable variableTable) {
        final List<String> assembly = new ArrayList<>();
        if (expr != null) {
            assembly.add("movq -" + expr.getStackOffset()+"(%rbp), %rdx");
        }


        VariableDescriptor variableDescriptor = variableTable.getDescriptor(arrayOrLoc.getName());
        TypeDescriptor typeDescriptor = variableDescriptor.getTypeDescriptor();
        String dest;
        if (variableDescriptor.isGlobal()) {
            if (arrayOffset == null) {
                dest = "l(%rip)";
            } else {
                assembly.add("movq -" +arrayOffset.getStackOffset()+"(%rbp), %rax"); // val of temp into rax
                assembly.add("leaq 0(,%rax," + typeDescriptor.elementSize() + "), %rcx"); // temp * element size
                assembly.add("leaq " + arrayOrLoc.getName() + "(%rip), %rax"); // address of base of global array
                dest = "(%rcx,%rax)";
            }
        } else {
            LocalDescriptor localDescriptor = (LocalDescriptor) variableDescriptor;
            if (arrayOffset == null) {
                dest = "-"+ localDescriptor.getStackOffset()+"(%rbp)";
            } else {
                dest = "-"+localDescriptor.getStackOffset()+"(%rbp,%rax,"+localDescriptor.getTypeDescriptor().elementSize()+")";
            }
        }
        switch (assignOp) {
            case ASSIGN: assembly.add("movq %rax, " + dest); break;
            case MEQ: assembly.add("subq %rax, " + dest); break;
            case PEQ: assembly.add("addq %rax, " + dest); break;
            case INC: assembly.add("incq " + dest); break;
            case DEC: assembly.add("decq " + dest); break;
        }
        return assembly;
    }

    @Override public String toString() {
        String offsetStr = arrayOffset == null ? "" : "[" + arrayOffset + "]";
        if (expr == null) {
            return "" + arrayOrLoc + offsetStr + assignOp;
        }
        return "" + arrayOrLoc + offsetStr + " " + assignOp + " " + expr;
    }

    @Override
    public Pair<Temp, List<Temp>> getTemps() {
        if (arrayOffset == null) {
            return new Pair(null, List.of(expr));
        } else {
            return new Pair(null, List.of(expr, arrayOffset));
        }
    }
}
