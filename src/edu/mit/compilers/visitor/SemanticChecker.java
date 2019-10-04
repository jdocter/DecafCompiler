package edu.mit.compilers.visitor;

import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.parser.*;

public interface SemanticChecker {

    void check(Program program) throws SemanticException;
    void check(Type type) throws SemanticException;
    void check(StringLit stringLit) throws SemanticException;
    void check(Statement statement) throws SemanticException;
    void check(MethodCall methodCall) throws SemanticException;
    void check(MethodDeclaration method) throws SemanticException;
    void check(Loc loc) throws SemanticException;
    void check(Lit lit) throws SemanticException;
    void check(IntLit intLit) throws SemanticException;
    void check(ImportDeclaration importDeclaration) throws SemanticException;
    void check(Id id) throws SemanticException;
    void check(HexLit hexLit) throws SemanticException;
    void check(FieldDeclaration fieldDeclaration) throws SemanticException;
    void check(Expr expr) throws SemanticException;
    void check(DecLit decLit) throws SemanticException;
    void check(Block block) throws SemanticException;
    void check(BinOp binOp) throws SemanticException;
    void check(AssignExpr assignExpr) throws SemanticException;

}
