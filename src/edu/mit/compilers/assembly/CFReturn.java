package edu.mit.compilers.assembly;

import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.LocalDescriptor;
import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.TypeDescriptor;
import edu.mit.compilers.inter.VariableDescriptor;
import edu.mit.compilers.inter.VariableTable;
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

public class CFReturn extends UIDObject implements CFNode {


    @Override public String toString() {
        if (isVoid) {
            return "UID " + UID + " CFReturn";
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MethodCFGFactory.dfsPrint(miniCFG, new HashSet<Integer>(), new PrintStream(baos));
        return "\nMiniCFG: " + baos.toString() + "\n" +
        "UID " + UID + " CFReturn [miniCFG=" + miniCFG.getUID() + ", returnTemp=" + returnTemp + "]";
    }

    private final List<CFStatement> statements = new ArrayList<CFStatement>();
    private CFNode next;
    boolean isEnd; // end of function
    private Expr returnExpr;
    private Temp returnTemp;
    private final Set<CFNode> parents = new HashSet<CFNode>();
    private final VariableTable variableTable;
    private boolean isVoid;
    private MethodDescriptor methodDescriptor;
    private CFNode miniCFG;


    public CFReturn(Expr returnExpr, VariableTable variableTable, MethodDescriptor methodDescriptor) {
        this.returnExpr = returnExpr;
        this.variableTable = variableTable;
        isVoid = returnExpr == null ? true : false;
        this.methodDescriptor = methodDescriptor;
    }

    public Expr getReturnExpr() {
        return returnExpr;
    }

    public void replaceExpr(Temp temp) {
        if (isVoid) throw new UnsupportedOperationException("Tried to give temp to void return");
        this.returnExpr = null;
        this.returnTemp = temp;
    }

    public void setMiniCFG(CFNode miniCFG) {
        if (isVoid) throw new UnsupportedOperationException("Tried to give miniCFG to void return");
        this.miniCFG = miniCFG;
    }

    public CFNode getMiniCFG() {
        return miniCFG;
    }

    @Override
    public List<String> toAssembly(ImportTable importTable) {
        final List<String> assembly = new ArrayList<>();

        List<String> body = new ArrayList<>();
        boolean shouldReturnVoid = methodDescriptor.isVoid();
        if (isVoid) {
            if (!shouldReturnVoid) {
                // generate runtime error
                body.add("# returning void in a function supposed to return a value");
                body.add("leaq _sp_field_runtime_error_2_" + methodDescriptor.getMethodName() + "(%rip), %rdi");
                body.add("call printf");
                body.add("");
                body.add("movq $2, %rax"); // return code 2
            } else {
                // return
                body.add("# return void");
                body.add("movq $0, %rax");
            }
        } else {
            if (shouldReturnVoid) throw new RuntimeException("semantic checks failed");
            // calculate expr and return it
            body.add("# calculating return Expr");
            body.addAll(new MethodAssemblyCollector(miniCFG, importTable).getInstructions());
            body.add(getEndOfMiniCFGLabel() + ":");
            body.add("");
            body.add("movq -" + returnTemp.getOffset() + "(%rbp), %rax");
        }
        body.add("");
        body.add("leave");
        body.add("ret");

        assembly.add(getAssemblyLabel() + ":");
        assembly.addAll(AssemblyFactory.indent(body));
        return assembly;
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

    @Override
    public List<Pair<Temp, List<Temp>>> getTemps() {
        if (isVoid) return List.of();
        TempCollector collector = new TempCollector();
        List<Pair<Temp, List<Temp>>> temps = new ArrayList();
        miniCFG.accept(collector);
        temps.addAll(collector.temps);
        temps.add(new Pair(returnTemp, List.of()));
        return temps;
    }

    @Override
    public String getAssemblyLabel() {
        return "_return_" + UID;
    }

    @Override
    public String getEndOfMiniCFGLabel() {
        return "_end_of_return_" + UID;
    }
}
