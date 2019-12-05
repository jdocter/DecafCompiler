package edu.mit.compilers.cfg;

import edu.mit.compilers.assembly.Reg;
import edu.mit.compilers.util.UIDObject;

import java.util.HashMap;
import java.util.Set;

public abstract class CFNode extends UIDObject {

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

    public boolean hasRegisterAssignment(AssemblyVariable v) {
        return registerAssignments.containsKey(v);
    }

    public Reg getRegisterAssignment(AssemblyVariable v) {
        if (registerAssignments.containsKey(v)) throw new RuntimeException("Assembly Variable " + v + " does not have a register assignment");
        return registerAssignments.get(v);
    }

    private void checkRegisterAssignments() {
        assert registerAssignments.size() == Set.of(registerAssignments.values()).size() : "Expected one unique register assignments";
    }
}
