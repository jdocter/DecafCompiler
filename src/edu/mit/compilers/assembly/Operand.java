package edu.mit.compilers.assembly;

public class Operand {
    private long offset;
    private int imm;
    private Reg reg1;
    private Reg reg2;
    private int scaledIndex;
    private String globalLabel;
    private boolean isMemoryAccess = false;
    private boolean isReg = false;
    private boolean isImm = false;
    private boolean isGlobalAccess = false;
    private final String s;

    private Operand(String s) {
        this.s = s;
    }

    static Operand makeImmediate(int imm) {
        Operand operand = new Operand("$" + imm);
        operand.imm = imm;
        operand.isImm = true;
        return operand;

    }

    static Operand makeGlobalAccess(String globalLabel) {
        Operand operand = new Operand(globalLabel + "(%rip)");
        operand.globalLabel = globalLabel;
        operand.isGlobalAccess = true;
        return operand;
    }
    static Operand makeMemoryAccess(Reg reg1) {
        Operand operand = new Operand("(" +reg1+")");
        operand.reg1 = reg1;
        operand.isMemoryAccess = true;
        return operand;
    }

    /** assume rbp offset */
    static Operand makeMemoryAccess(long offset) {
        return makeMemoryAccess(offset, Reg.RBP);
    }

    static Operand makeMemoryAccess(long offset, Reg reg1) {
        Operand operand = new Operand("-"+offset+"(" +reg1+")");
        operand.offset = offset;
        operand.reg1 = reg1;
        operand.isMemoryAccess = true;
        return operand;
    }

    static Operand makeMemoryAccess(long offset, Reg reg1, Reg reg2, int scaledIndex) {
        Operand operand = new Operand("-" + offset + "(" + reg1 +"," + reg2 + "," +scaledIndex +")");
        operand.offset = offset;
        operand.reg1 = reg1;
        operand.reg2 = reg2;
        operand.isMemoryAccess = true;
        operand.scaledIndex = scaledIndex;
        return operand;
    }

    static Operand makeReg(Reg reg) {
        Operand operand = new Operand(reg.toString());
        operand.isReg = true;
        operand.reg1 = reg;
        return operand;
    }


    public boolean isMemoryAccess() {
        return isMemoryAccess;
    }

    public boolean isReg() {
        return !isMemoryAccess;
    }

    public boolean isImm() {
        return isImm;
    }

    public boolean isGlobalAccess() {
        return isGlobalAccess;
    }

    public int getImm() {
        return imm;
    }

    public int getScaledIndex() {
        return scaledIndex;
    }

    public Reg getReg1() {
        return reg1;
    }

    public Reg getReg2() {
        return reg2;
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Reg) return this.isReg && this.reg1 == (Reg) obj;
        if (! (obj instanceof Operand) ) return false;
        Operand that = (Operand) obj;
        return this.imm == that.imm
                && this.globalLabel.equals(that.globalLabel)
                && this.reg1 == that.reg1
                && this.reg2 == that.reg2
                && this.offset == that.offset
                && this.scaledIndex == that.scaledIndex;
    }
    @Override
    public String toString() {
        return s;


//        if (!isMemoryAccess) return reg1.toString();
//        if (reg2 != null) {
//            if (offset != 0) {
//                return "-" + offset + "("
//            } else {
//                return
//            }
//        } else {
//            if (offset != 0) {
//
//            } else {
//
//            }
//        }

    }
}
