package edu.mit.compilers.assembly;

public enum Reg {
    RAX("%rax"),
    RBX("%rbx"),
    RCX("%rcx"),
    RDX("%rdx"),
    RDI("%rdi"),
    RSI("%rsi"),
    RBP("%rbp"),
    RSP("%rsp"),
    R8("%r8"),
    R9("%r9"),
    R10("%r10"),
    R11("%r11"),
    R12("%r12"),
    R13("%r13"),
    R14("%r14"),
    R15("%r15");

    private final String reg;

    private Reg(String s) {
        reg = s;
    }

    @Override
    public String toString() {
        return reg;
    }

    public static Reg methodParam(int i) {
        switch (i) {
            case 1: return Reg.RDI;
            case 2: return Reg.RSI;
            case 3: return Reg.RDX;
            case 4: return Reg.RCX;
            case 5: return Reg.R8;
            case 6: return Reg.R9;
            default: throw new RuntimeException("Parameter " + i + " is not passed by register");
        }
    }
}
