package edu.mit.compilers.cfg;

import edu.mit.compilers.assembly.Reg;

import java.util.HashMap;
import java.util.Set;

public abstract class CFNode {

    private final HashMap<AssemblyVariable, Reg> registerAssignments = new HashMap<>();

    public void assignRegister(AssemblyVariable v, Reg r) {
        registerAssignments.put(v, r);
        checkRegisterAssignments();
    }

    public void unassignRegister(AssemblyVariable v) {
        if (!registerAssignments.containsKey(v)) throw new RuntimeException("Register cannot be unassigned from Assembly Variable "
                + v+ " which does not have an existing assignment");
        registerAssignments.remove(v);
        checkRegisterAssignments();
    }

    public void assignRegisters(HashMap<AssemblyVariable, Reg> registerAssignments) {
        registerAssignments.putAll(registerAssignments);
        checkRegisterAssignments();
    }

    public boolean isAssignedRegister(AssemblyVariable v) {
        return registerAssignments.containsKey(v);
    }

    public Reg getRegisterAssignment(AssemblyVariable v) {
        if (registerAssignments.containsKey(v)) throw new RuntimeException("Assembly Variable " + v + " does not have a register assignment");
        return registerAssignments.get(v);
    }

    private void checkRegisterAssignments() {
        assert registerAssignments.size() == Set.of(registerAssignments.values()).size() : "Expected one unique register assignments";
    }

    /** set of LOCAL AssemblyVariables or temps that are used in the node*/
    abstract Set<AssemblyVariable> getUsed();
    /** set of LOCAL AssemblyVariables or temps that are defined in the node*/
    abstract Set<AssemblyVariable> getDefined();

    abstract void setRegister();
}
