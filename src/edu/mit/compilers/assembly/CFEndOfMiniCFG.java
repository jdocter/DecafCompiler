package edu.mit.compilers.assembly;

import java.util.ArrayList;
import java.util.List;

import edu.mit.compilers.inter.ImportTable;

public class CFEndOfMiniCFG extends CFNop {
    private CFNode enclosingNode;

    public CFEndOfMiniCFG(CFNode enclosingNode) {
        this.enclosingNode = enclosingNode;
    }

    @Override
    public List<String> toAssembly(ImportTable importTable) {
        List<String> assembly = new ArrayList<>();

        List<String> body = new ArrayList<>();
        body.add("jmp " + enclosingNode.getEndOfMiniCFGLabel());

        assembly.add(getAssemblyLabel() + ":");
        assembly.addAll(AssemblyFactory.indent(body));
        return assembly;
    }

    @Override
    public String toString() {
        return "UID " + UID + " CFEndOfMiniCFG [next=" + enclosingNode.getUID() + "]";
    }

}
