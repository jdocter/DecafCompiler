package edu.mit.compilers.visitor;

import edu.mit.compilers.inter.MethodDescriptor;
import java.util.ArrayList;
import java.util.List;

import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.ProgramDescriptor;
import edu.mit.compilers.inter.SemanticException;
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


public class VoidMainNoArgs implements SemanticChecker {


    private List<SemanticException> semanticExceptions = new ArrayList<>();
    private MethodTable methodTable;


    public VoidMainNoArgs(ProgramDescriptor table) {
        this.methodTable = table.methodTable;
    }

    @Override
    public List<SemanticException> getSemanticExceptions() {
        return this.semanticExceptions;
    }

    @Override
    public void visit(AssignExpr assignExpr) {}

    @Override
    public void visit(BinOp binOp) {}

    @Override
    public void visit(Block block) {}

    @Override
    public void visit(DecLit decLit) {}

    @Override
    public void visit(Expr expr) {}

    @Override
    public void visit(FieldDeclaration fieldDeclaration) {}

    @Override
    public void visit(HexLit hexLit) {}

    @Override
    public void visit(Id id) {}

    @Override
    public void visit(ImportDeclaration importDeclaration) {}

    @Override
    public void visit(IntLit intLit) {}

    @Override
    public void visit(Lit lit) {}

    @Override
    public void visit(Loc loc) {}

    @Override
    public void visit(MethodCall methodCall) {}

    @Override
    public void visit(MethodDeclaration methodDeclaration) {    }

    @Override
    public void visit(Program program) {
        if (!methodTable.containsKey("main")) {
            // Rule 3
            semanticExceptions.add(new SemanticException(1, // first line
                    "Program does not contain a definition for 'main'"));
        } else {
            MethodDescriptor method = methodTable.get("main");

            if (method.getParams().size() > 0) {
                // Rule 3
                semanticExceptions.add(new SemanticException(method.declarationLineNumber,
                        "'main' must take no parameters"));
            }
            if (!method.isVoid()) {
                // Rule 3
                semanticExceptions.add(new SemanticException(method.declarationLineNumber,
                        "'main' must have void return value"));
            }
        }
    }

    @Override
    public void visit(Statement statement) {}

    @Override
    public void visit(StringLit stringLit) {}

    @Override
    public void visit(Type type) {}

}
