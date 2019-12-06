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
    R15("%r15"),

    AL("%al"),
    CL("%cl"),
    DL("%dl"),
    BL("%bl"),
    SIL("%sil"),
    DIL("%dil"),
    SPL("%spl"),
    BPL("%bpl"),
    R8B("%r8b"),
    R9B("%r9b"),
    R10B("%r10b"),
    R11B("%r11b"),
    R12B("%r12b"),
    R13B("%r13b"),
    R14B("%r14b"),
    R15B("%r15Bb");

    private final String reg;

    private Reg(String s) {
        reg = s;
    }

    public Reg byte0() {
        switch (this) {
            case RAX: return AL;
            case RBX: return BL;
            case RCX: return CL;
            case RDX: return DL;
            case RDI: return DIL;
            case RSI: return SIL;
            case RBP: return BPL;
            case RSP: return SPL;
            case R8: return R8B;
            case R9: return R9B;
            case R10: return R10B;
            case R11: return R11B;
            case R12: return R12B;
            case R13: return R13B;
            case R14: return R14B;
            case R15: return R15B;
            default: throw new RuntimeException("unimpleented");
        }
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
