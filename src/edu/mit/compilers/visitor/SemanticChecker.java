package edu.mit.compilers.visitor;

import java.util.List;

import edu.mit.compilers.inter.SemanticException;

public interface SemanticChecker extends ASTVisitor {

    List<SemanticException> getSemanticExceptions();

}
