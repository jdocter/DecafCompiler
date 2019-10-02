package edu.mit.compilers.parser;

import edu.mit.compilers.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Method extends Node {

    public final Type returnType;
    public final Id methodName;
    public final List<Pair<Type,Id>> params = new ArrayList<>();
    public Block mBlock;

    Method(Type type, Id id) {
        returnType = type;
        methodName = id;
    }

    public void addParam(Type type, Id id) {
        params.add(new Pair<Type, Id>(type,id));
    }

    public void addBlock(Block block) {
        mBlock = block;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
