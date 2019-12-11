package edu.mit.compilers.reg;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.cfg.CFBlock;
import edu.mit.compilers.cfg.CFConditional;
import edu.mit.compilers.cfg.CFNode;
import edu.mit.compilers.cfg.CFNop;
import edu.mit.compilers.cfg.CFReturn;
import edu.mit.compilers.cfg.OuterCFNode;
import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.cfg.innercfg.CFAssign;
import edu.mit.compilers.cfg.innercfg.InnerCFBlock;
import edu.mit.compilers.cfg.innercfg.InnerCFConditional;
import edu.mit.compilers.cfg.innercfg.InnerCFNop;
import edu.mit.compilers.liveness.LivenessAnalyzer;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MiniCFVisitor;

public class InterferenceGraph {

    /*
     * RI:
     * - webs == adjList.keySet();
     * - adjList.get(A).contains(B) iff adjList.get(B).contains(A)
     */
    Set<Web> webs = new HashSet<>();
    Map<Web, Set<Web>> adjList = new HashMap<>();
    /*
     * Key: CFNodes containing USE statements
     * Value: Map(variables used -> web)
     */
    Map<CFNode, Map<AssemblyVariable, Web>> useStatementsToContainingWebs = new HashMap<>();
    LivenessAnalyzer analysis;

    final boolean debug;

    public InterferenceGraph(OuterCFNode methodCFG) {
        this(methodCFG, false);
    }

    public InterferenceGraph(OuterCFNode methodCFG, boolean debugInterferenceGraph) {
        debug = debugInterferenceGraph;
        analysis = new LivenessAnalyzer(methodCFG);
        // Algorithm description:

        CFNodeIterator iterator = new CFNodeIterator(methodCFG);
        while (iterator.alive()) {
            while (iterator.hasNext()) {
                CFNode nextNode = iterator.next();
                // System.err.println(nextNode + " USED: " + nextNode.getUsed() + " \t\tDEFINED: " + nextNode.getDefined());
                // Unleash a new web builder that builds webs for each def in this node
                for (AssemblyVariable target : nextNode.getDefined()) {
                    CFNodeIterator webBuildingIterator = new CFNodeIterator(iterator);
                    buildWeb(target, webBuildingIterator, nextNode);
                }
            }
            iterator.backtrackToLastBranchPoint();
        }

        injectImplicitInitializations(methodCFG);
    }

    private void debugPrint(String message) {
        if (debug) System.err.println(message);
    }

    public Set<Web> getAdj(Web w) {
        return adjList.get(w);
    }

    @SuppressWarnings("static-method")
    private void injectImplicitInitializations(OuterCFNode methodCFG) {
        // Finally:
        // for each USE not in a web, that means it was depending on a default value of 0;
        // inject def 0 in beginning of CFG and build webs from those
        // Skipped because this is a rare and confusing use case.
        return;
    }

    /**
     * Iterate through CFNodes.
     * for each DEF, search forward until next USE, DEF, or dead.
     *      when encountering a USE, add to current web, and union webs of same variable.  keep going
     *      when encountering a DEF and/or dead, clear away web since last USE.  stop
     *
     * @param target
     * @param iterator
     * @param def
     */
    private void buildWeb(AssemblyVariable target, CFNodeIterator iterator, CFNode def) {
        Web web = new Web(analysis, def, target);
        boolean addedToGraph = false;

        if (compoundAssignOnTarget(def, target)) {
            Web other = useStatementsToContainingWebs.get(def).get(target);
            if (other == null) {
                // we encountered a USE without a preceding DEF"
                System.err.println("WARNING: " + def + " is a USE without a preceding DEF!");
            } else {
                debugPrint("Merging new Web with ending web for " + def.toWebString());
                mergeWebs(web, other);
                addedToGraph = true;
            }
        }

        debugPrint("START BUILD WEB " + web.getUID() + ": " + target + " @ " + def.toWebString());

        CFNode nextNode = def;
        while (iterator.alive()) {
            if (iterator.hasNext()) {
                nextNode = iterator.next();
                debugPrint("Consider " + nextNode.toWebString());
                if (nextNode.getUsed().contains(target)) {
                    Set<CFNode> betweenDefAndNext = iterator.getActivePath();
                    debugPrint("Extend " + betweenDefAndNext.stream().map(CFNode::toWebString).collect(Collectors.joining(", ")));
                    web.extendWeb(betweenDefAndNext);

                    if (useStatementsToContainingWebs.containsKey(nextNode) &&
                            useStatementsToContainingWebs.get(nextNode).containsKey(target)) {
                        Web other = useStatementsToContainingWebs.get(nextNode).get(target);

                        // System.err.println(web.getUID() + " merge with " + other.getUID());
                        if (web.equals(other)) {
                            assert addedToGraph;
                        } else {
                            mergeWebs(web, other);
                            addedToGraph = true;
                        }

                        iterator.backtrackToLastBranchPoint(); // other web should have already explored forward.
                        continue;
                    }
                }
                // not "else if" because if nextNode is a = a; then it's equivalent to
                // "USE a; DEF a;"
                if (!analysis.getOut(nextNode).contains(target) ||
                        nextNode.getDefined().contains(target)
                        ) {
                    debugPrint("Backtrack because dead or defined");
                    debugPrint("OUT: " + analysis.getOut(nextNode));
                    debugPrint("DEF Target: " + nextNode.getDefined().contains(target));
                    iterator.backtrackToLastBranchPoint();
                }
            } else {
                switch (iterator.deadEndType()) {
                    case END:
                        // Interestingly, it is possible to fall off a CFG without a variable dying,
                        // if a CFConditional has a void return on one branch, for example.

//                        throw new RuntimeException("While building Web, fell off a CFG without the variable dying.\n"
//                                + "CFG: " + nextNode + "\n"
//                                + "Liveness: " + analysis.getOut(nextNode) + "\n"
//                                + "Variable of interest: " + target);
                        debugPrint("Backtrack because fell off CFG without variable dying (must be a Nop branch)");
                        iterator.backtrackToLastBranchPoint();
                        break;
                    case VISITED:
                        CFNode visited = iterator.deadEndNode();
                        if (web.spanningStatements.contains(visited)) {
                            Set<CFNode> toExtend = iterator.getActivePath();
                            debugPrint("Extend because reached node in previously merged web");
                            debugPrint("Extend " + toExtend.stream().map(CFNode::toWebString).collect(Collectors.joining(", ")));
                            web.extendWeb(toExtend);
                        } else {
                            Set<CFNode> toExtend = iterator.getActivePath();
                            debugPrint("Extend because looped back while searching for USE");
                            /*
                             * Could theoretically put off extension until confirmation
                             * of a USE in the loop at visited.
                             *
                             * This creates extra interferences -- should be conservative.
                             */
                            debugPrint("Extend " + toExtend.stream().map(CFNode::toWebString).collect(Collectors.joining(", ")));
                            web.extendWeb(toExtend);
                        }
                        debugPrint("Backtrack because reached visited");
                        iterator.backtrackToLastBranchPoint();
                        break;
                    default:
                        throw new RuntimeException("Unknown DeadEndType: " + iterator.deadEndType());
                }
            }
        }

        if (web.uses.isEmpty()) {
            debugPrint("No web for " + target + " at " + def.toWebString());
            return;
        }
        if (!addedToGraph) {
            debugPrint("Web for " + target + " at " + def.toWebString());
            addWeb(web, target);
        } else {
            // TODO dead code eliminate the DEF
            debugPrint("Updated web for " + target + " at " + def.toWebString());
        }
        updateWeb(web, target);
    }

    private void updateWeb(Web web, AssemblyVariable target) {
        for (Web possibleInterferer : webs) {
            // don't interfere with self
            if (possibleInterferer == web) continue;
            // we can never lose interference (never remove spanning statements)
            if (adjList.get(possibleInterferer).contains(web)) continue;

            Set<CFNode> interferingStatements = new HashSet<>(possibleInterferer.spanningStatements);
            interferingStatements.retainAll(web.spanningStatements);

            // We were going to make a = b + c; not cause a and b to interfere,
            // but it makes assembly generation harder (can't do a = b; a = a + c;).

            /*
            Set<CFNode> webNonInterfering = new HashSet<>(web.defs);
            // a = a; in a loop would technically be an interfering statement.  Imagine the following
            // pathological example.
            // for (int a = 0; b < 4; a++) { a = a; }
            // where every statement is a DEF.  The correct behavior is an infinite loop
            // but if we don't include a = a; as an interfering statement this could result in
            // a terminating program, if a and b are assigned to the same register.
            webNonInterfering.removeAll(web.uses);

            Set<CFNode> interfererNonInterfering = new HashSet<>(possibleInterferer.defs);
            interfererNonInterfering.removeAll(possibleInterferer.uses);

            interferingStatements.removeAll(webNonInterfering);
            interferingStatements.removeAll(interfererNonInterfering);
            */

            if (!interferingStatements.isEmpty()) {
                adjList.get(web).add(possibleInterferer);
                adjList.get(possibleInterferer).add(web);
            }
        }

        for (CFNode statement : web.uses) {
            Map<AssemblyVariable, Web> priorWebs = useStatementsToContainingWebs.getOrDefault(statement, new HashMap<>());
            priorWebs.put(target, web); // might already exist
            useStatementsToContainingWebs.put(statement, priorWebs);
        }
    }

    private boolean compoundAssignOnTarget(CFNode def, AssemblyVariable target) {
        if (!(def instanceof CFAssign)) return false;
        CFAssign cfAssign = (CFAssign) def;

        return cfAssign.dstArrayOrLoc.equals(target) &&
                Set.of(CFAssign.PEQ, CFAssign.MEQ, CFAssign.INC, CFAssign.DEC)
                    .contains(cfAssign.assignOp);
    }

    private void addWeb(Web web, AssemblyVariable target) {
        adjList.put(web, new HashSet<>());
        webs.add(web);
    }

    /**
     * Replace all references in InterferenceGraph: old -> new,
     * and augment new with all of old's information.
     *
     * @param newWeb the newer web, which is currently being built
     * @param oldWeb the older web, which is already stable (no
     *      other USEs reachable from its DEF)
     */
    private void mergeWebs(Web newWeb, Web oldWeb) {
        Set<Web> neighbors = adjList.get(oldWeb);
        for (Web neighbor : neighbors) {
            adjList.get(neighbor).remove(oldWeb);
            adjList.get(neighbor).add(newWeb);
        }
        adjList.remove(oldWeb);
        adjList.put(newWeb, neighbors);

        webs.remove(oldWeb);
        webs.add(newWeb);

        newWeb.merge(oldWeb);

        for (CFNode statement : oldWeb.uses) {
            Map<AssemblyVariable, Web> priorWebs = useStatementsToContainingWebs.get(statement);
            assert priorWebs.put(oldWeb.targetVariable, newWeb) != null; // assert prior web for this target, else shouldn't have merged
            useStatementsToContainingWebs.put(statement, priorWebs);
        }

        oldWeb.release();
    }

    private String webToIDAndTarget(Web web) {
	String regInfo;
        if (web.isSpilled()) {
            regInfo = "(spilled)";
        } else if (web.hasRegisterAssignment()) {
            regInfo = "(" + web.getRegisterAssignment() + ")";
        } else {
            regInfo = "";
        }
        return web.getUID() + " " + regInfo;
    }

    private String adjListToUIDString() {
        StringBuilder output = new StringBuilder();
        for (Web key : adjList.keySet()) {
            output.append(webToIDAndTarget(key));

            if (adjList.get(key).isEmpty()) {
                output.append(": no neighbors\n\t");
                continue;
            }

            output.append(": {");
            for (Web value : adjList.get(key)) {
                output.append(webToIDAndTarget(value));
                output.append(", ");
            }
            output.replace(output.length() - 2, output.length(), "}\n\t");
        }
        return output.toString();
    }

    @Override
    public String toString() {
        return "Webs:\n\t" + webs.stream().map(Web::toString).collect(Collectors.joining("\n\t")) + "\n"
                + "Adjacencies:\n\t" + adjListToUIDString();
    }

    public Map<Web, Set<Web>> getAdjList() {
        return new HashMap<>(adjList);
    }
}
