package edu.mit.compilers.assembly;

import edu.mit.compilers.parser.AssignExpr;
import edu.mit.compilers.parser.BinOp;
import edu.mit.compilers.parser.Block;
import edu.mit.compilers.parser.DecLit;
import edu.mit.compilers.parser.Expr;
import edu.mit.compilers.parser.FieldDeclaration;
import edu.mit.compilers.parser.HexLit;
import edu.mit.compilers.parser.Id;
import edu.mit.compilers.parser.ImportDeclaration;
import edu.mit.compilers.parser.IntLit;
import edu.mit.compilers.parser.Lit;
import edu.mit.compilers.parser.Loc;
import edu.mit.compilers.parser.MethodCall;
import edu.mit.compilers.parser.MethodDeclaration;
import edu.mit.compilers.parser.Program;
import edu.mit.compilers.parser.Statement;
import edu.mit.compilers.parser.StringLit;
import edu.mit.compilers.parser.Type;
import edu.mit.compilers.visitor.Visitor;

import java.util.*;


public class CFBuilder implements Visitor {
    private HashMap<String,CFNode> methodCFGs = new HashMap<>();
    Optional<CFNode> nextNode = Optional.empty();
    Stack<CFNode> CFNodes = new Stack<>();

    public void visit(Program program) {
        for (MethodDeclaration methodDeclaration: program.methodDeclarations) {
            methodDeclaration.accept(this);
        }
    }

    public void visit(ImportDeclaration importDeclaration) {
        // TODO Auto-generated method stub

    }

    public void visit(FieldDeclaration fieldDeclaration) {
        // TODO Auto-generated method stub

    }

    public void visit(MethodDeclaration methodDeclaration) {
        // TODO Auto-generated method stub
        methodDeclaration.mBlock.accept(this);
    }

    public void visit(Expr expr) {
        // TODO Auto-generated method stub

    }

    public void visit(Statement statement) {
        // TODO Auto-generated method stub
    }

    public void visit(AssignExpr assignExpr) {
        // TODO Auto-generated method stub

    }

    public void visit(BinOp binOp) {
        // TODO Auto-generated method stub

    }

    public void visit(Block block) {
        // TODO Auto-generated method stub
        for (Statement statement: block.statements) {
            statement.accept(this);
        }
    }

    public void visit(DecLit decLit) {
        // TODO Auto-generated method stub

    }

    public void visit(HexLit hexLit) {
        // TODO Auto-generated method stub

    }

    public void visit(Id id) {
        // TODO Auto-generated method stub

    }

    public void visit(IntLit intLit) {
        // TODO Auto-generated method stub

    }

    public void visit(Lit lit) {
        // TODO Auto-generated method stub

    }

    public void visit(Loc loc) {
        // TODO Auto-generated method stub

    }

    public void visit(MethodCall methodCall) {
        // TODO Auto-generated method stub

    }

    public void visit(StringLit stringLit) {
        // TODO Auto-generated method stub

    }

    public void visit(Type type) {
        // TODO Auto-generated method stub

    }

}
