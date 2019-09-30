package edu.mit.compilers.parser;

import java.util.ArrayList;
import java.util.List;

public class Program extends Node {

    public final List<Method> methods = new ArrayList<>();
    public final List<Field> fields = new ArrayList<>();
    public final List<Imp> imps = new ArrayList<>();
    Program() {

    }
    public void addMethod(Method method) {
        methods.add(method);
    }
    public void addField(Field field) {
        fields.add(field);
    }
    public void addImp(Imp imp) {
        imps.add(imp);
    }
}
