package edu.mit.compilers.cfg;

import edu.mit.compilers.assembly.Reg;
import edu.mit.compilers.util.UIDObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class CFNode extends UIDObject {

    private final HashMap<AssemblyVariable, Reg> registerAssignmentsForDefs = new HashMap<>();
    private final HashMap<AssemblyVariable, Reg> registerAssignmentsForUses = new HashMap<>();

    public void assignRegisterDef(AssemblyVariable v, Reg r) {
        registerAssignmentsForDefs.put(v, r);
        checkRegisterAssignments();
    }

    public void assignRegisterUse(AssemblyVariable v, Reg r) {
        registerAssignmentsForUses.put(v, r);
        checkRegisterAssignments();
    }

//    public void unassignRegister(AssemblyVariable v) {
//        if (!registerAssignments.containsKey(v)) throw new RuntimeException("Register cannot be unassigned from Assembly Variable "
//                + v+ " which does not have an existing assignment");
//        registerAssignments.remove(v);
//        checkRegisterAssignments();
//    }
//
//    public void assignRegisters(HashMap<AssemblyVariable, Reg> registerAssignments) {
//        registerAssignments.putAll(registerAssignments);
//        checkRegisterAssignments();
//    }


    public Set<Reg> getRegisters() {
        Set<Reg> regs = new HashSet(registerAssignmentsForUses.values());
        regs.addAll(registerAssignmentsForDefs.values());
        return regs;
    }

    public boolean hasRegisterAssignmentForUse(AssemblyVariable v) {
        return registerAssignmentsForUses.containsKey(v);
    }
    public boolean hasRegisterAssignmentForDef(AssemblyVariable v) {
        return registerAssignmentsForDefs.containsKey(v);
    }

    public Reg getRegisterAssignmentForUse(AssemblyVariable v) {
        if (registerAssignmentsForUses.containsKey(v)) throw new RuntimeException("Assembly Variable " + v + " does not have a register assignment");
        return registerAssignmentsForUses.get(v);
    }

    public Reg getRegisterAssignmentForDef(AssemblyVariable v) {
        if (registerAssignmentsForDefs.containsKey(v)) throw new RuntimeException("Assembly Variable " + v + " does not have a register assignment");
        return registerAssignmentsForDefs.get(v);
    }

    private void checkRegisterAssignments() {
        assert registerAssignmentsForDefs.size() == Set.of(registerAssignmentsForDefs.values()).size() : "Expected one unique register assignments";
        assert registerAssignmentsForUses.size() == Set.of(registerAssignmentsForUses.values()).size() : "Expected one unique register assignments";
    }

    public abstract Set<AssemblyVariable> getDefined();
    public abstract Set<AssemblyVariable> getUsed();

    public abstract String toWebString();
}
