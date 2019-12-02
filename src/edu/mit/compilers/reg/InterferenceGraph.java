package edu.mit.compilers.reg;

import java.util.Map;
import java.util.Set;

import edu.mit.compilers.cfg.AssemblyVariable;
import edu.mit.compilers.cfg.CFBlock;
import edu.mit.compilers.cfg.CFConditional;
import edu.mit.compilers.cfg.CFNode;
import edu.mit.compilers.cfg.CFNop;
import edu.mit.compilers.cfg.CFReturn;
import edu.mit.compilers.cfg.OuterCFNode;
import edu.mit.compilers.cfg.Temp;
import edu.mit.compilers.cfg.innercfg.InnerCFBlock;
import edu.mit.compilers.cfg.innercfg.InnerCFConditional;
import edu.mit.compilers.cfg.innercfg.InnerCFNop;
import edu.mit.compilers.liveness.LivenessAnalyzer;
import edu.mit.compilers.visitor.CFVisitor;
import edu.mit.compilers.visitor.MiniCFVisitor;
import sun.jvm.hotspot.utilities.LivenessAnalysis;

public class InterferenceGraph {

    class Builder implements CFVisitor, MiniCFVisitor {

        @Override
        public void visit(InnerCFBlock cfBlock) {

        }

        @Override
        public void visit(InnerCFConditional cfConditional) {
            // TODO Auto-generated method stub

        }

        @Override
        public void visit(InnerCFNop cfNop) {
            // TODO Auto-generated method stub

        }

        @Override
        public void visit(CFBlock cfBlock) {
            // TODO Auto-generated method stub

        }

        @Override
        public void visit(CFConditional cfConditional) {
            // TODO Auto-generated method stub

        }

        @Override
        public void visit(CFNop cfNop) {
            // TODO Auto-generated method stub

        }

        @Override
        public void visit(CFReturn cfReturn) {
            // TODO Auto-generated method stub

        }

    }

    Set<Web> webs;
    Map<Web, Set<Web>> adjList;
    Map<CFNode, Set<Web>> statementToContainingWebs;
    LivenessAnalyzer analysis;

    public InterferenceGraph(OuterCFNode methodCFG) {
        analysis = new LivenessAnalyzer(methodCFG);
        buildNodes(methodCFG); // build webs using union find
        buildEdges(methodCFG);
    }

    private void buildNodes(OuterCFNode methodCFG) {
        // Algorithm description:

        // Iterate through CFNodes.
        // for each DEF, search forward until next USE, DEF, or dead.
        //      when encountering a USE, add to current web, and union webs of same variable.  keep going
        //      when encountering a DEF and/or dead, clear away web since last USE.  stop

        Set<OuterCFNode> visited = new HashSet<>();
        // traverse and unleash Builder, which searches forward until next USE, DEF, or dead



        // Finally:
        // for each USE not in a web, that means it was depending on a default value of 0;
        // inject def 0 in beginning of CFG and build webs from those
    }

    private void addToWeb(CFNode toAdd, Web currentWeb) {

    }

    private void buildEdges(OuterCFNode methodCFG) {

    }

}
