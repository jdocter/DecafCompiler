package edu.mit.compilers.reg;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.mit.compilers.assembly.Reg;
import edu.mit.compilers.cfg.CFNode;
import edu.mit.compilers.cfg.OuterCFNode;
import edu.mit.compilers.cfg.innercfg.CFMethodCall;

public class AvailableRegistersHeuristic {

    int countMethodCalls;
    int countCFNodes;
    OuterCFNode originalMethodCFG;

    public AvailableRegistersHeuristic(OuterCFNode methodCFG) {
        originalMethodCFG = methodCFG;
        countMethodCalls = 0;
        countCFNodes = 0;
        CFNodeIterator iterator = new CFNodeIterator(methodCFG);
        while (iterator.alive()) {
            while (iterator.hasNext()) {
                CFNode nextNode = iterator.next();
                countCFNodes += 1;
                if (nextNode instanceof CFMethodCall) countMethodCalls += 1;
            }
            iterator.backtrackToLastBranchPoint();
        }
    }

    public int getMethodCallCount() {
        return countMethodCalls;
    }

    public int getTotalCount() {
        return countCFNodes;
    }

    public Set<Reg> recommendedRegisters() {
        Set<Reg> toInclude = new HashSet<>();

        List<Reg> callee = Reg.usableCalleeSaved;
        List<Reg> caller = Reg.usableCallerSaved;

        int calleeRegStatistic = getTotalCount();
        if (calleeRegStatistic > 5) {
            toInclude.add(callee.get(0));
        }
        if (calleeRegStatistic > 10) {
            toInclude.add(callee.get(1));
        }
        if (calleeRegStatistic > 15) {
            toInclude.add(callee.get(2));
        }
        if (calleeRegStatistic > 20) {
            toInclude.add(callee.get(3));
        }
        if (calleeRegStatistic > 25) {
            toInclude.add(callee.get(4));
        }

        int callerRegHeuristic;
        if (getMethodCallCount() == 0) {
            callerRegHeuristic = Integer.MAX_VALUE;
        }

        callerRegHeuristic = getTotalCount() / getMethodCallCount();
        if (callerRegHeuristic > 0) {
            toInclude.add(caller.get(0));
        }
        if (callerRegHeuristic > 1) {
            toInclude.add(caller.get(1));
            toInclude.add(caller.get(2));
        }
        if (callerRegHeuristic > 2) {
            toInclude.add(caller.get(3));
            toInclude.add(caller.get(4));
        }

        return toInclude;
    }
}
