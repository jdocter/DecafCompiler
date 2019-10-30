package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.*;

import edu.mit.compilers.parser.Id;
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
    public List<String> toAssembly(VariableTable variableTable, ImportTable importTable) {
        final List<String> body = new ArrayList<>();
        if (expr != null) {
            body.add("movq -" + expr.getOffset()+"(%rbp), %rdx # " + this.toString());
        }

        VariableDescriptor variableDescriptor = variableTable.getDescriptor(arrayOrLoc.getName());
        TypeDescriptor typeDescriptor = variableDescriptor.getTypeDescriptor();
        String dest;
        if (variableDescriptor.isGlobal()) {
            if (arrayOffset == null) {
                dest = ((FieldDescriptor)variableDescriptor).getGlobalLabel() + "(%rip)";
            } else {
                body.add("movq -" + arrayOffset.getOffset() +"(%rbp), %rax"); // val of temp into rax
                
                // array out of bounds
                body.add("cmpq $" + typeDescriptor.getLength() +", %rax");
                body.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                body.add("cmpq $0, %rax");
                body.add("jl " + AssemblyFactory.METHOD_EXIT_1);

                // get dest
                body.add("leaq 0(,%rax," + typeDescriptor.elementSize() + "), %rcx"); // temp * element size
                body.add("leaq " + ((FieldDescriptor)variableDescriptor).getGlobalLabel() + ", %rax"); // address of base of global array
                dest = "(%rcx,%rax)";
            }
        } else {
            LocalDescriptor localDescriptor = (LocalDescriptor) variableDescriptor;
            if (arrayOffset == null) {
                dest = "-"+ localDescriptor.getStackOffset()+"(%rbp)";
            } else {
                body.add("movq -" + arrayOffset.getOffset() +"(%rbp), %rax"); // val of temp into rax
                
                // array out of bounds
                body.add("cmpq $" + typeDescriptor.getLength() +", %rax");
                body.add("jge "+ AssemblyFactory.METHOD_EXIT_1);
                body.add("cmpq $0, %rax");
                body.add("jl " + AssemblyFactory.METHOD_EXIT_1);

                // get dest
                dest = "-" + localDescriptor.getStackOffset() +"(%rbp,%rax,"+localDescriptor.getTypeDescriptor().elementSize()+")";
            }
        }

        switch (assignOp) {
            case ASSIGN: body.add("movq %rdx, " + dest); break;
            case MEQ: body.add("subq %rdx, " + dest); break;
            case PEQ: body.add("addq %rdx, " + dest); break;
            case INC: body.add("incq " + dest); break;
            case DEC: body.add("decq " + dest); break;
        }

        return AssemblyFactory.indent(body);
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
        List<Temp> right = new ArrayList();
        if (arrayOffset != null) {
            right.add(arrayOffset);
        } else if (expr != null) {
            right.add(expr);
        } 
        return new Pair(null,right);
    }
}
