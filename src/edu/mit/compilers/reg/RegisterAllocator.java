package edu.mit.compilers.reg;


import edu.mit.compilers.assembly.Reg;

import java.util.Set;

public class RegisterAllocator {

    final InterferenceGraph graph;
    final Set<Reg> availableRegisters;

    RegisterAllocator(InterferenceGraph graph, Set<Reg> availableRegisters) {
        this.graph = graph;
        this.availableRegisters = availableRegisters;
    }

    private void allocateRegistersChaitin() {

    }

    private void allocateRegistersSpillCost() {
        // order webs in ascending order of spill cost
        // while not k colorable (any web has deg >= k):
        //      remove lowest spill cost web with deg >=k
    }

    
}
