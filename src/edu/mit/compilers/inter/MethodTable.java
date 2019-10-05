package edu.mit.compilers.inter;

import java.util.HashMap;
import java.util.List;

import edu.mit.compilers.parser.MethodDeclaration;

public class MethodTable extends HashMap<String, MethodDescriptor> {

    public MethodTable(List<MethodDeclaration> methods, FieldTable fieldTable) throws SemanticException {
        for (MethodDeclaration method: methods) {
            this.put(method.methodName.getName(), new MethodDescriptor(method, fieldTable));
        }

    }

}
