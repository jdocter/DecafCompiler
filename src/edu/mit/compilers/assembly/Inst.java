package edu.mit.compilers.assembly;

public enum Inst {

    RET("ret"),
    TRUE("0x01"),
    FALSE("0x0");


    private final String instText;

    private Inst(String s) {
        instText = s;
    }

    @Override
    public String toString() {
        return instText;
    }
}
