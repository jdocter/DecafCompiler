package edu.mit.compilers.visitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

import edu.mit.compilers.inter.ImportTable;
import edu.mit.compilers.inter.LocalTable;
import edu.mit.compilers.inter.MethodDescriptor;
import edu.mit.compilers.inter.MethodTable;
import edu.mit.compilers.inter.ProgramDescriptor;
import edu.mit.compilers.inter.SemanticException;
import edu.mit.compilers.inter.TypeDescriptor;
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
import edu.mit.compilers.util.Pair;


public class CheckTypes implements SemanticChecker {

    private List<SemanticException> semanticExceptions = new ArrayList<>();
    private final Stack<LocalTable> localTableStack = new Stack<>();
    private MethodTable methodTable;
    private ImportTable importTable;

    /**
     * When finishing a visit to a node with possible type,
     * change lastVisitedType to be the type of that node.
     * Unchecked type (i.e. foreign call) is represented using null.
     * Otherwise, do not change lastVisitedType.
     */
    private Optional<TypeDescriptor> lastVisitedType = Optional.empty();
    private Type enclosingMethodReturnType;

    public CheckTypes(ProgramDescriptor table) {
        this.methodTable = table.methodTable;
        this.importTable = table.importTable;
    }

    @Override
    public List<SemanticException> getSemanticExceptions() {
        return this.semanticExceptions;
    }

    @Override
    public void visit(AssignExpr assignExpr) {
        switch (assignExpr.assignExprOp) {
            case AssignExpr.ASSIGN:
                assignExpr.expr.accept(this);
                // lastVisitedType = lastVisitedType; // pass it up
                break;
            case AssignExpr.PEQ:
            case AssignExpr.MEQ:
            case AssignExpr.INC:
            case AssignExpr.DEC:
                lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.INT));
                break;
        }
    }

    @Override
    public void visit(BinOp binOp) {}

    @Override
    public void visit(Block block) {
        localTableStack.push(block.localTable);
        Optional<TypeDescriptor> visitedTypeBefore = lastVisitedType;
        lastVisitedType = Optional.empty();

        for (Statement statement : block.statements) {
            statement.accept(this);
        }

        lastVisitedType = visitedTypeBefore;
        localTableStack.pop();
    }

    @Override
    public void visit(DecLit decLit) {
        lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.INT));
    }

    private void handleBinOpExpr(Expr bigExpr) {
        // Could be better with a Visitor pattern on expr parse tree.

        List<Expr> binOpExprs = bigExpr.binOpExprs;
        List<BinOp> binOps = bigExpr.binOps;

        for (Expr smolExpr : binOpExprs) {
            smolExpr.accept(this);
        }

        BinOp outermostBinOp = binOps.get(0); // guaranteed one, or else it would be a smolExpr

        for (BinOp binOp : binOps) {
            outermostBinOp = BinOp.weakerPrecedence(outermostBinOp, binOp);
        }  // weakest precedence gives you outermost binop

        switch (outermostBinOp.binOp) {
            case BinOp.AND:
            case BinOp.OR:
            case BinOp.EQ:
            case BinOp.NEQ:
            case BinOp.LT:
            case BinOp.GT:
            case BinOp.LEQ:
            case BinOp.GEQ:
                lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.BOOL));
                break;
            case BinOp.PLUS:
            case BinOp.MINUS:
            case BinOp.MUL:
            case BinOp.DIV:
            case BinOp.MOD:
                lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.INT));
                break;
        }
    }

    @Override
    public void visit(Expr expr) {
        switch (expr.exprType) {
            case Expr.METHOD_CALL:
                MethodCall methodCall = expr.methodCall;
                String method = methodCall.methodName.getName();
                if (importTable.containsKey(method)) {
                    // no typechecking
                    break;
                } else if (methodTable.containsKey(method)) {
                    MethodDescriptor descriptor = methodTable.get(method);
                    if (descriptor.isVoid()) { // Rule 6
                        semanticExceptions.add(new SemanticException(methodCall.getLineNumber(),
                                "Method '" + method + "' is used as expression but does not return a result"));
                    } else {
                        methodCall.accept(this);
                    }
                }
                break;
            case Expr.MINUS:
                expr.expr.accept(this);
                if (!lastVisitedType.isPresent() || lastVisitedType.get().type != TypeDescriptor.INT) {
                    semanticExceptions.add(new SemanticException(expr.getLineNumber(),
                            "The '-' operator expected operand to be of type 'INT', but was " + lastVisitedType.get()));
                }
                lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.INT));
                break;
            case Expr.NOT:
                expr.expr.accept(this);
                if (lastVisitedType.isPresent() && lastVisitedType.get().type != TypeDescriptor.BOOL) {
                    semanticExceptions.add(new SemanticException(expr.getLineNumber(),
                            "The '!' operator operator expected operand of type BOOL," +
                                    " but found " +lastVisitedType.get()));
                }
                lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.BOOL));
                break;
            case Expr.LOC:
                expr.loc.accept(this);
                break;
            case Expr.BIN_OP:
                switch (expr.binOp.binOp) {
                    case BinOp.OR:
                    case BinOp.AND:
                        for (Expr binOpExpr : List.of(expr.expr, expr.binOpExpr)) {
                            binOpExpr.accept(this);
                            if (lastVisitedType.isPresent() && lastVisitedType.get().type != TypeDescriptor.BOOL) {
                                semanticExceptions.add(new SemanticException(binOpExpr.getLineNumber(),
                                        "The '" + expr.binOp + "' operator expected operand of type BOOL," +
                                                " but found " +lastVisitedType.get()));
                                lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.BOOL));
                                break;
                            }
                        }
                        lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.BOOL));
                        break;
                    case BinOp.DIV:
                    case BinOp.MINUS:
                    case BinOp.MOD:
                    case BinOp.GEQ:
                    case BinOp.LEQ:
                    case BinOp.PLUS:
                    case BinOp.MUL:
                    case BinOp.LT:
                    case BinOp.GT:
                        for (Expr binOpExpr : List.of(expr.expr, expr.binOpExpr)) {
                            binOpExpr.accept(this);
                            if (lastVisitedType.isPresent() && lastVisitedType.get().type != TypeDescriptor.INT) {
                                semanticExceptions.add(new SemanticException(binOpExpr.getLineNumber(),
                                        "The '" + expr.binOp + "' operator expected operand of type INT," +
                                                " but found " +lastVisitedType.get()));
                                lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.INT));
                                break;
                            }
                        }
                        lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.INT));
                        break;
                    case BinOp.EQ:
                    case BinOp.NEQ:
                        expr.expr.accept(this);
                        final Optional<TypeDescriptor> leftExprType = lastVisitedType.isPresent() ?
                                Optional.of(lastVisitedType.get()) : Optional.empty();
                        expr.expr.accept(this);
                        if (lastVisitedType.isPresent() && leftExprType.isPresent() &&
                                lastVisitedType.get() != leftExprType.get()) {
                            semanticExceptions.add(new SemanticException(expr.binOp.getLineNumber(),
                                    "The '" + expr.binOp + "' operator expects operands of same type," +
                                            " but the left operand was type " +leftExprType.get() +
                                            " while the right operand was type " + lastVisitedType.get()));
                            // type is ambiguous
                            lastVisitedType = Optional.empty();
                        } else if (lastVisitedType.isPresent()) {
                            // ok as is
                        } else if (leftExprType.isPresent()) {
                            lastVisitedType = Optional.of(leftExprType.get());
                        } else {
                            lastVisitedType = Optional.empty();
                        }
                        break;
                }
            case Expr.LIT:
                expr.lit.accept(this);
                break;
            case Expr.LEN:
                expr.id.accept(this);

                if (!lastVisitedType.isPresent()) {
                    lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.INT));
                    break;
                }
                switch (lastVisitedType.get().type) {
                    case TypeDescriptor.INT_ARRAY:
                    case TypeDescriptor.BOOL_ARRAY:
                        lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.INT));
                        break;
                    case TypeDescriptor.INT:
                    case TypeDescriptor.BOOL:
                    case TypeDescriptor.STRING:
                        // Rule 12 (12a)
                        semanticExceptions.add(new SemanticException(expr.id.getLineNumber(),
                                "Identifier '" + expr.id.getName() + "' does not support the 'len' operation: "
                                        + "expected an array type, got " + lastVisitedType.get()));
                        lastVisitedType = Optional.empty(); // lenient type checking
                        break;
                }
                break;

        }
    }

    @Override
    public void visit(FieldDeclaration fieldDeclaration) {}

    @Override
    public void visit(HexLit hexLit) {
        lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.INT));
    }

    @Override
    public void visit(Id id) {
        LocalTable scope = localTableStack.peek();
        if (scope.isDeclared(id.getName())) {
            lastVisitedType = Optional.of(scope.getDescriptor(id.getName()).getTypeDescriptor());
        } else {
            lastVisitedType = Optional.empty();
        }
    }

    @Override
    public void visit(ImportDeclaration importDeclaration) {}

    @Override
    public void visit(IntLit intLit) {
        // Shouldn't be reached
        lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.INT));
    }

    @Override
    public void visit(Lit lit) {
        switch (lit.litType) {
            case Lit.INT:
            case Lit.CHAR:
                lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.INT));
                break;
            case Lit.BOOL:
                lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.BOOL));
                break;
        }
    }

    @Override
    public void visit(Loc loc) {
        if (loc.expr != null) {
            // array checking
            loc.expr.accept(this);
            if (lastVisitedType.isPresent() && lastVisitedType.get().type != TypeDescriptor.INT) {
                // Rule 12 (12b)
                semanticExceptions.add(new SemanticException(loc.id.getLineNumber(),
                        "Array index of incorrect type: expected INT, but found " + lastVisitedType.get()));
            }

            loc.id.accept(this);
            if (!lastVisitedType.isPresent()) {
                return;
            }
            switch (lastVisitedType.get().type) {
                case TypeDescriptor.INT_ARRAY:
                    lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.INT));
                    break;
                case TypeDescriptor.BOOL_ARRAY:
                    lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.BOOL));
                    break;
                case TypeDescriptor.INT:
                case TypeDescriptor.BOOL:
                case TypeDescriptor.STRING:
                    // Rule 12 (12a)
                    semanticExceptions.add(new SemanticException(loc.id.getLineNumber(),
                            "Identifier '" + loc.id.getName() + "' does not support indexing: "
                                    + "expected an array type, got " + lastVisitedType.get()));
                    lastVisitedType = Optional.empty(); // lenient type checking
                    break;
            }
        } else {
            loc.id.accept(this);
            // lastVisitedType = lastVisitedType; // share return type with id
        }
    }

    @Override
    public void visit(MethodCall methodCall) {
        String method = methodCall.methodName.getName();
        if (methodTable.containsKey(method)) {
            MethodDescriptor descriptor = methodTable.get(method);
            List<Pair<Type, Id>> signatureParams = descriptor.getParams();
            int callLength = methodCall.arguments.size();
            int signatureLength = signatureParams.size();
            if (callLength != signatureLength) {
                semanticExceptions.add(new SemanticException(methodCall.getLineNumber(),
                        "Incorrect number of arguments to method '" + method + "': expected " +
                                signatureLength + ", but found " + callLength));
            } else {
                for (int i = 0; i < callLength; i++) {
                    Pair<Expr, StringLit> arg = methodCall.arguments.get(i);
                    Pair<Type, Id> formalParam = signatureParams.get(i);

                    if (arg.getValue() != null) {
                        // Rule 7
                        semanticExceptions.add(new SemanticException(methodCall.getLineNumber(),
                                "Incorrect type for param '" + formalParam.getValue().getName() + "': expected " +
                                        formalParam.getKey().toString() + ", but found STRINGLITERAL"));
                        continue;
                    }

                    arg.getKey().accept(this); // set lastVisitedType
                    if (lastVisitedType.isPresent() && formalParam.getKey().mType != lastVisitedType.get().type) {
                        // Rule 5
                        semanticExceptions.add(new SemanticException(methodCall.getLineNumber(),
                                "Incorrect type for param '" + formalParam.getValue().getName() + "': expected " +
                                        formalParam.getKey() + ", but found " + lastVisitedType.get()));
                        continue;
                    }
                }
            }

            if (!descriptor.isVoid()) {
                lastVisitedType = Optional.of(descriptor.getReturnType());
                return;
            }
        }
        lastVisitedType = Optional.empty(); // no typechecking for foreign methods
    }

    @Override
    public void visit(MethodDeclaration method) {
        Optional<TypeDescriptor> visitedTypeBefore = lastVisitedType;
        lastVisitedType = Optional.empty();

        enclosingMethodReturnType = method.returnType;

        method.mBlock.accept(this);

        lastVisitedType = visitedTypeBefore;
    }

    @Override
    public void visit(Program program) {
        for (MethodDeclaration methodDeclaration : program.methodDeclarations) {
            methodDeclaration.accept(this);
        }
    }

    @Override
    public void visit(Statement statement) {
        Optional<TypeDescriptor> visitedTypeBefore = lastVisitedType;
        lastVisitedType = Optional.empty();

        switch (statement.statementType) {
            case Statement.LOC_ASSIGN:
                statement.loc.accept(this);
                Optional<TypeDescriptor> locType = lastVisitedType;
                statement.assignExpr.accept(this);
                Optional<TypeDescriptor> assignExprType = lastVisitedType;
                if (locType.isPresent() && assignExprType.isPresent()) {
                    if (locType.get().type != assignExprType.get().type) {
                        // Rule 19
                        semanticExceptions.add(new SemanticException(statement.assignExpr.getLineNumber(),
                                "Mismatched types in an assignment: " + locType.get() + " and " + assignExprType.get()));
                    }
                }
                break;
            case Statement.METHOD_CALL:
                statement.methodCall.accept(this);
                break;
            case Statement.IF:
                statement.expr.accept(this);
                if (lastVisitedType.isPresent() && lastVisitedType.get().type != TypeDescriptor.BOOL) {
                    // Rule 14
                    semanticExceptions.add(new SemanticException(statement.getLineNumber(),
                            "'if' condition of incorrect type: expected BOOL but got " + lastVisitedType.get()));
                }
                statement.ifBlock.accept(this);
                if (statement.elseBlock != null) statement.elseBlock.accept(this);
                break;
            case Statement.FOR:
                statement.id.accept(this);
                Optional<TypeDescriptor> forIdType = lastVisitedType;
                statement.initExpr.accept(this);
                Optional<TypeDescriptor> forInitType = lastVisitedType;
                if (forIdType.isPresent() && forInitType.isPresent()) {
                    if (forIdType.get().type != forInitType.get().type) {
                        // Rule 19
                        semanticExceptions.add(new SemanticException(statement.getLineNumber(),
                                "Mismatched types in an assignment: " + forIdType.get() + " and " + forInitType.get()));
                    }
                }

                statement.exitExpr.accept(this);
                if (lastVisitedType.isPresent() && lastVisitedType.get().type != TypeDescriptor.BOOL) {
                    // Rule 14
                    semanticExceptions.add(new SemanticException(statement.getLineNumber(),
                            "'for' exit condition of incorrect type: expected BOOL but got " + lastVisitedType.get()));
                }
                statement.loc.accept(this);
                if (lastVisitedType.isPresent() && lastVisitedType.get().type != TypeDescriptor.INT) {
                    // Rule 19
                    semanticExceptions.add(new SemanticException(statement.getLineNumber(),
                            "Wrong type before +=, -=, ++, or --: expected INT but got " + lastVisitedType.get()));
                }
                if (statement.expr != null) {
                    statement.expr.accept(this);
                    if (lastVisitedType.isPresent() && lastVisitedType.get().type != TypeDescriptor.INT) {
                        // Rule 19
                        semanticExceptions.add(new SemanticException(statement.getLineNumber(),
                                "Mismatched types in an assignment: INT and " + lastVisitedType.get()));
                    }
                }
                statement.block.accept(this);
                break;
            case Statement.WHILE:
                statement.expr.accept(this);
                if (lastVisitedType.isPresent() && lastVisitedType.get().type != TypeDescriptor.BOOL) {
                    // Rule 14
                    semanticExceptions.add(new SemanticException(statement.getLineNumber(),
                            "'while' condition of incorrect type: expected BOOL but got " + lastVisitedType.get()));
                }
                statement.block.accept(this);
                break;
            case Statement.RETURN:
                if (enclosingMethodReturnType == null) {
                    if (statement.expr != null) {
                        // Rule 8
                        semanticExceptions.add(new SemanticException(statement.getLineNumber(),
                                "Return value in the body of a void method"));
                    }
                } else {
                    if (statement.expr == null) {
                        // Rule 9
                        semanticExceptions.add(new SemanticException(statement.getLineNumber(),
                                "Void return value for method with return type " + enclosingMethodReturnType));
                    } else {
                        statement.expr.accept(this);
                        if (lastVisitedType.isPresent() && lastVisitedType.get().type != enclosingMethodReturnType.mType) {
                            // Rule 9
                            semanticExceptions.add(new SemanticException(statement.getLineNumber(),
                                    "Returning incorrect type: expected " + enclosingMethodReturnType +
                                    " but got " + lastVisitedType.get()));
                        }
                    }
                }
                break;
        }

        lastVisitedType = visitedTypeBefore;
    }

    @Override
    public void visit(StringLit stringLit) {
        lastVisitedType = Optional.of(new TypeDescriptor(TypeDescriptor.STRING));
    }

    @Override
    public void visit(Type type) {
        lastVisitedType = Optional.of(new TypeDescriptor(type.mType));
    }

}
