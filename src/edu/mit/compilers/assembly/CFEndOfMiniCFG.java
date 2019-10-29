package edu.mit.compilers.assembly;

import java.util.ArrayList;
import java.util.List;

public class CFEndOfMiniCFG extends CFNop {
    private CFNode enclosingNode;

    public CFEndOfMiniCFG(CFNode enclosingNode) {
        this.enclosingNode = enclosingNode;
    }

    @Override
    public List<String> toAssembly() {
        List<String> assembly = new ArrayList<>();

        List<String> body = new ArrayList<>();
        body.add("jmp " + enclosingNode.getEndOfMiniCFGLabel());

        assembly.add(getAssemblyLabel() + ":");
        assembly.addAll(AssemblyFactory.indent(body));
        return assembly;
    }

}
