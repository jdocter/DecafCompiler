package edu.mit.compilers.reg;


import edu.mit.compilers.assembly.Reg;

import java.util.*;

public class RegisterAllocator {

    private final Map<Web, Set<Web>> interferenceGraph;
    private final Map<Web, Set<Web>> chaitinInterferenceGraph = new HashMap<>();
    private final Set<Reg> availableRegisters;
    private final int k;

    private final Stack<Web> chaitinStack = new Stack<>();

    private final Comparator<Web> chaitinAdjsComparator = new Comparator<Web>() {
        @Override
        public int compare(Web wa, Web wb) {
            return chaitinInterferenceGraph.get(wa).size() - chaitinInterferenceGraph.get(wb).size();
        }
    };

    private final Comparator<Web> spillComparator = new Comparator<Web>() {
        @Override
        public int compare(Web wa, Web wb) {
            return wa.spillCost() - wb.spillCost();
        }
    };

    /**
     *
     * @param interferenceGraph requires that graph is undirected
     * @param availableRegisters
     */
    public RegisterAllocator(Map<Web,Set<Web>> interferenceGraph, Set<Reg> availableRegisters) {
        this.interferenceGraph = interferenceGraph;
        this.availableRegisters = availableRegisters;
        k = this.availableRegisters.size();

        for (Web web : interferenceGraph.keySet()) {
            chaitinInterferenceGraph.put(web, new HashSet<>(interferenceGraph.get(web)));
        }

        allocateRegistersChaitin();
    }

    private void allocateRegistersChaitin() {
        while (!chaitinInterferenceGraph.isEmpty()) {
            Web minAdjs = Collections.min(chaitinInterferenceGraph.keySet(), chaitinAdjsComparator);
            if (chaitinInterferenceGraph.get(minAdjs).size() < k) {
                // push all nodes with deg < k onto stack and remove from graph
                removeFromChaitinGraph(minAdjs);
                chaitinStack.push(minAdjs);
            } else {
                // need to spill, heuristic : spill node with minimum spill cost... ?
                Web minSpillCost = Collections.min(chaitinInterferenceGraph.keySet(), spillComparator);
                minSpillCost.spill();
                removeFromChaitinGraph(minSpillCost);
            }
        }

        while (chaitinStack.peek() != null) {
            Web web = chaitinStack.pop();
            web.assignRegister(getAvailableReg(web));
        }
    }

    private void removeFromChaitinGraph(Web web) {
        Set<Web> adjs = chaitinInterferenceGraph.get(web);
        chaitinInterferenceGraph.remove(web);
        for (Web adj : adjs) {
            chaitinInterferenceGraph.get(adj).remove(web);
        }
    }

    /**
     * helper function for assigning registers after running chaitins
     * chooses random reg from set of registers that adjacents haven't already been assigned
     */
    private Reg getAvailableReg(Web web) {
        Set<Reg> webAvailableRegisters = new HashSet<>(availableRegisters);
        for (Web adj : interferenceGraph.get(web)) {
            if (adj.hasRegisterAssignment()) webAvailableRegisters.remove(adj.getRegisterAssignment());
        }
        assert webAvailableRegisters.iterator().hasNext() : "something wrong with graph coloring to be completed";
        return webAvailableRegisters.iterator().next();
    }

    private void allocateRegistersSpillCost() {
        // order webs in ascending order of spill cost
        // while not k colorable (any web has deg >= k):
        //      remove lowest spill cost web with deg >=k
    }


}
