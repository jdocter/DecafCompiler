package edu.mit.compilers.inter;

import java.util.HashMap;
import java.util.List;

import edu.mit.compilers.parser.ImportDeclaration;
import edu.mit.compilers.parser.Method;

public class MethodTable extends HashMap<String, MethodDescriptor> {

    public MethodTable(List<?> methodsOrImps, TypeTable typeTable) {
        if (methodsOrImps.isEmpty()) {
            return;
        }

        Object elem = methodsOrImps.get(0);

        if (elem instanceof Method) {
            // build method table
        } else if (elem instanceof ImportDeclaration) {
            // build method table but with foreign == true
        } else {
            throw new UnsupportedOperationException("Trying to build method table with something that is not a method or import");
        }
    }

}
