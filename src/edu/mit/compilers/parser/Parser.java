package edu.mit.compilers.parser;

import edu.mit.compilers.grammar.*; // Use compiled files in grammar
import java.util.*;
import antlr.Token;
import edu.mit.compilers.tools.CLI;
import java.util.ArrayList;

public class Parser {
    private final List<Token> tokens = new ArrayList<>();
    private int currentTok = 0;

    public Parser(DecafScanner scanner) {
        Token token;
        try {
            for (token = scanner.nextToken();
            token.getType() != DecafScannerTokenTypes.EOF;
            token = scanner.nextToken()) {
                tokens.add(token);
            }
            tokens.add(token);
        } catch(Exception e) {
            // print the error:
            System.err.println(CLI.infile + " " + e);
            System.exit(1);
        }
    }

    public Program parse() throws DecafParseException {
        try {
            return parseProgram();
        } catch (ArrayIndexOutOfBoundsException e) {
            // may occur while doing token lookahead
            throw new DecafParseException("Premature program end", e);
        }
    }

    private Program parseProgram() throws DecafParseException {
        Program program = new Program();
        while (isType(DecafScannerTokenTypes.IMPORT)) {
            ImportDeclaration imp = parseImp();
            program.addImportDeclaration(imp);
        }
        while (!(isType(DecafScannerTokenTypes.EOF) ||
                isType(DecafScannerTokenTypes.LPAREN,2))) {
            // not method or EOF so assume field
           FieldDeclaration field = parseField();
           program.addFieldDeclaration(field);
        }
        while (!isType(DecafScannerTokenTypes.EOF)) {
            MethodDeclaration method = parseMethodDeclaration();
            program.addMethod(method);
        }

        assertIsType(DecafScannerTokenTypes.EOF, "");
        return program;
    }

    private ImportDeclaration parseImp() throws DecafParseException {
        assertIsType(DecafScannerTokenTypes.IMPORT, "");
        Id id = parseId();
        assertIsType(DecafScannerTokenTypes.SEMICOL, "");
        return new ImportDeclaration(id);
    }

    private MethodDeclaration parseMethodDeclaration() throws DecafParseException {
        Type methodType;
        if (isType(DecafScannerTokenTypes.VOID)) {
            next();
            methodType = null; // TODO don't use null
        } else {
            methodType = parseType();
        }
        Id methodId = parseId();
        final MethodDeclaration method = new MethodDeclaration(methodType, methodId);
        assertIsType(DecafScannerTokenTypes.LPAREN, "");
        boolean firstParam = true;
        while (!isType(DecafScannerTokenTypes.RPAREN)) {
            if (!firstParam) {
                assertIsType(DecafScannerTokenTypes.COMMA, "");
            }
            Type paramType = parseType();
            Id paramId = parseId();
            method.addParam(paramType, paramId);
            firstParam = false;
        }
        assertIsType(DecafScannerTokenTypes.RPAREN, "");
        method.addBlock(parseBlock());
        return method;
    }

    private FieldDeclaration parseField() throws DecafParseException {
        Type type = parseType();
        Id id = parseId();
        final FieldDeclaration field;
        if (isType(DecafScannerTokenTypes.LBRACK)) {
            next();
            IntLit intLit = parseIntLit();
            assertIsType(DecafScannerTokenTypes.RBRACK, "");
            field = new FieldDeclaration(type, id, intLit);
        } else {
            field = new FieldDeclaration(type, id);
        }
        while (!isType(DecafScannerTokenTypes.SEMICOL)) {
            assertIsType(DecafScannerTokenTypes.COMMA, "");
            id = parseId();
            if (isType(DecafScannerTokenTypes.LBRACK)) {
                next();
                IntLit intLit = parseIntLit();
                assertIsType(DecafScannerTokenTypes.RBRACK, "");
                field.addArg(id, intLit);
            } else {
                field.addArg(id);
            }
        }
        assertIsType(DecafScannerTokenTypes.SEMICOL, "");
        return field;
    }

    private Block parseBlock() throws DecafParseException{
        Block block = new Block();
        assertIsType(DecafScannerTokenTypes.LCURLY, "");
        while (isType(DecafScannerTokenTypes.BOOL) ||
                isType(DecafScannerTokenTypes.INT)) {
            // field declaration
            block.addFieldDeclaration(parseField());
        }

        while (!isType(DecafScannerTokenTypes.RCURLY)) {
            // statement
            block.addStatement(parseStatement());
        }
        assertIsType(DecafScannerTokenTypes.RCURLY, "");
        return block;
    }

    private Statement parseStatement() throws DecafParseException {
        if (isType(DecafScannerTokenTypes.ID) &&
                isType(DecafScannerTokenTypes.LPAREN,1)) {
            // method call
            MethodCall methodCall = parseMethodCall();
            assertIsType(DecafScannerTokenTypes.SEMICOL, "");
            return new Statement(methodCall);
        } else if (isType(DecafScannerTokenTypes.ID)) {
            // location
            Loc loc = parseLoc();
            AssignExpr assignExpr = parseAssignExpr();
            assertIsType(DecafScannerTokenTypes.SEMICOL, "");
            return new Statement(loc, assignExpr);
        } else if (isType(DecafScannerTokenTypes.IF)) {
            int line = line();
            next();
            assertIsType(DecafScannerTokenTypes.LPAREN,"");
            Expr expr = parseExpr();
            assertIsType(DecafScannerTokenTypes.RPAREN, "");
            Block ifBlock = parseBlock();
            Block elseBlock = null;
            if (isType(DecafScannerTokenTypes.ELSE)) {
                next();
                 elseBlock = parseBlock();
            }
            Statement statement = new Statement(expr, ifBlock, elseBlock);
            statement.setLineNumber(line);
            return statement;
        } else if (isType(DecafScannerTokenTypes.FOR)) {
            int line = line();
            next();
            assertIsType(DecafScannerTokenTypes.LPAREN, "");
            Id id = parseId();
            assertIsType(DecafScannerTokenTypes.ASSIGN, "");
            Expr init = parseExpr();
            assertIsType(DecafScannerTokenTypes.SEMICOL, "");
            Expr exit = parseExpr();
            assertIsType(DecafScannerTokenTypes.SEMICOL, "");
            Loc loc = parseLoc();
            Statement statement;
            if (isType(DecafScannerTokenTypes.INC) ||
                isType(DecafScannerTokenTypes.DEC)) {
                String inc = text();
                next();
                assertIsType(DecafScannerTokenTypes.RPAREN, "");
                Block block = parseBlock();
                statement = new Statement(id, init, exit, loc, inc, block);
            } else if (isType(DecafScannerTokenTypes.MEQ) ||
                    isType(DecafScannerTokenTypes.PEQ)) {
                String assignOp = text();
                next();
                Expr expr = parseExpr();
                assertIsType(DecafScannerTokenTypes.RPAREN, "");
                Block block = parseBlock();
                statement = new Statement(id, init, exit, loc, assignOp, expr, block);
            } else {
                throw new DecafParseException("");
            }
            statement.setLineNumber(line);
            return statement;

        } else if (isType(DecafScannerTokenTypes.WHILE)) {
            int line = line();
            next();
            assertIsType(DecafScannerTokenTypes.LPAREN, "");
            Expr expr = parseExpr();
            assertIsType(DecafScannerTokenTypes.RPAREN, "");
            Block block = parseBlock();
            Statement statement = new Statement(expr, block);
            statement.setLineNumber(line);
            return statement;
        } else if (isType(DecafScannerTokenTypes.RETURN)) {
            Statement statement = new Statement(Statement.RETURN);
            statement.setLineNumber(line());
            next();
            if (!isType(DecafScannerTokenTypes.SEMICOL)) {
                statement.addReturnExpr(parseExpr());
            }
            assertIsType(DecafScannerTokenTypes.SEMICOL, "");
            return statement;
        } else if (isType(DecafScannerTokenTypes.BREAK)) {
            Statement statement = new Statement(Statement.BREAK);
            statement.setLineNumber(line());
            next();
            assertIsType(DecafScannerTokenTypes.SEMICOL, "");
            return statement;
        } else if (isType(DecafScannerTokenTypes.CONTINUE)) {
            Statement statement = new Statement(Statement.CONTINUE);
            statement.setLineNumber(line());
            next();
            assertIsType(DecafScannerTokenTypes.SEMICOL, "");
            return statement;
        } else {
          throw new DecafParseException("");
        }
    }

    private MethodCall parseMethodCall() throws DecafParseException {
        MethodCall methodCall = new MethodCall(parseId());
        assertIsType(DecafScannerTokenTypes.LPAREN, "");
        while (!isType(DecafScannerTokenTypes.RPAREN)) {
            if (isType(DecafScannerTokenTypes.STRING_LIT)) {
                methodCall.addStringLit(parseStringLit());
            } else {
                methodCall.addExpr(parseExpr());
            }
            if (!isType(DecafScannerTokenTypes.RPAREN)) {
                assertIsType(DecafScannerTokenTypes.COMMA, "");
            }
        }
        assertIsType(DecafScannerTokenTypes.RPAREN, "");
        methodCall.setLineNumber(line());
        return methodCall;
    }

    private Loc parseLoc() throws DecafParseException {
        Id id = parseId();
        if (isType(DecafScannerTokenTypes.LBRACK)) {
            next();
            Expr expr = parseExpr();
            assertIsType(DecafScannerTokenTypes.RBRACK, "");
            return new Loc(id, expr);
        }
        return new Loc(id);

    }

    /**
     * @param binOps list of BinOp
     * @param binOpExprs list of expressions that are arguments of the binary operators in binOps respectively
     * @return Expr that reflects the decaf precedence rules of binary operators
     */
    private static Expr organizeExprs(List<BinOp> binOps, List<Expr> binOpExprs) {
        if (binOps.size() == 0) {
            return binOpExprs.get(0);
        }
        BinOp weakest = binOps.get(0);
        for (BinOp binOp : binOps) {
            weakest = BinOp.weakerPrecedence(weakest, binOp);
        }
        int weakestIndex = binOps.lastIndexOf(weakest); // last is weaker when evaluating from left to right
        // passes view of binOps, but this is OK because this is a private method that we know we are not mutating
        Expr left = organizeExprs(binOps.subList(0,weakestIndex), binOpExprs.subList(0,weakestIndex+1));
        Expr right = organizeExprs(binOps.subList(weakestIndex+1, binOps.size()),
                binOpExprs.subList(weakestIndex+1,binOpExprs.size()));
        return new Expr(left,right,weakest);
    }

    private Expr parseExpr() throws DecafParseException {
        Expr smolExpr = parseSmolExpr();

        final List<BinOp> binOps= new ArrayList<>();
        final List<Expr> binOpExprs= new ArrayList<>();
        binOpExprs.add(smolExpr);

        // assume sequence of bin ops and expressions, then handle redundancy in return statement
        while(true) {
            BinOp binOp;
            try {
              binOp = parseBinOp();
            } catch (DecafParseException e) {
              break;
            }
            Expr next = parseSmolExpr();
            binOps.add(binOp);
            binOpExprs.add(next);
        }

        return organizeExprs(binOps,binOpExprs);
    }

    private Expr parseSmolExpr() throws DecafParseException {
        if (isType(DecafScannerTokenTypes.ID) && isType(DecafScannerTokenTypes.LPAREN,1)) {
            return new Expr(parseMethodCall());
        } else if (isType(DecafScannerTokenTypes.ID)) {
            return new Expr(parseLoc());
        } else if (isType(DecafScannerTokenTypes.LEN)) {
            next();
            assertIsType(DecafScannerTokenTypes.LPAREN, "");
            Id id = parseId();
            assertIsType(DecafScannerTokenTypes.RPAREN, "");
            return new Expr(id);
        } else if (isType(DecafScannerTokenTypes.NOT)) {
            int line = line();
            next();
            Expr nextExpr = parseSmolExpr();
            Expr notExpr = new Expr(Expr.NOT,nextExpr);
            notExpr.setLineNumber(line);
            return notExpr;
        } else if (isType(DecafScannerTokenTypes.MINUS)) {
            int line = line();
            next();
            Expr nextExpr = parseSmolExpr();
            nextExpr.setLineNumber(line);
            Expr minusExpr = new Expr(Expr.MINUS, nextExpr);
            minusExpr.setLineNumber(line);
            return minusExpr;
        }
        else if (isType(DecafScannerTokenTypes.DEC)) {
            // don't optimize bc need for type checking
            int line = line();
            next();
            Expr nextExpr = parseSmolExpr();
            nextExpr.setLineNumber(line);
            Expr minusExpr = new Expr(Expr.MINUS, nextExpr);
            minusExpr.setLineNumber(line);
            Expr doubleMinusExpr = new Expr(Expr.MINUS, minusExpr);
            doubleMinusExpr.setLineNumber(line);
            return doubleMinusExpr;
        } else if (isType(DecafScannerTokenTypes.LPAREN)) {
            next();
            Expr nextExpr = parseExpr();
            assertIsType(DecafScannerTokenTypes.RPAREN, "");
            return nextExpr;
        } else {
            return new Expr(parseLit());
        }
    }

    private Type parseType() throws DecafParseException{
        if (isType(DecafScannerTokenTypes.INT)) {
            next();
            return new Type(Type.INT);
        } else if (isType(DecafScannerTokenTypes.BOOL)) {
            next();
            return new Type(Type.BOOL);
        } else {
            throw new DecafParseException("");
        }
    }

    private AssignExpr parseAssignExpr() throws DecafParseException {
        AssignExpr result;
        int line;
        if (isType(DecafScannerTokenTypes.INC) ||
            isType(DecafScannerTokenTypes.DEC)) {
            String inc = text();
            line = line();
            next();
            result = new AssignExpr(inc);
        } else if (isType(DecafScannerTokenTypes.ASSIGN) ||
                isType(DecafScannerTokenTypes.MEQ) ||
                isType(DecafScannerTokenTypes.PEQ)) {
            String assignOp = text();
            line = line();
            next();
            result = new AssignExpr(assignOp, parseExpr());
        } else {
            throw new DecafParseException("");
        }

        result.setLineNumber(line);
        return result;
    }

    private Id parseId() throws DecafParseException{
        if (isType(DecafScannerTokenTypes.ID)) {
            Id id = new Id(text());
            id.setLineNumber(line());
            next();
            return id;
        } else {
            throw new DecafParseException("");
        }

    }

    private StringLit parseStringLit() throws DecafParseException {
        if (isType(DecafScannerTokenTypes.STRING_LIT)) {
            StringLit stringLit = new StringLit(text());
            next();
            return stringLit;
        } else {
            throw new DecafParseException("");
        }
    }

    private  Lit parseLit() throws DecafParseException {
        if (isType(DecafScannerTokenTypes.TRUE) ||
            isType(DecafScannerTokenTypes.FALSE)) {
            Lit lit = new Lit(isType(DecafScannerTokenTypes.TRUE));
            next();
            return lit;
        } else if (isType(DecafScannerTokenTypes.CHAR_LIT)) {
            Lit lit = new Lit(text().charAt(1)); // charAt(0) == charAt(2) == '
            next();
            return lit;
        } else if (isType(DecafScannerTokenTypes.HEX_LIT) ||
                isType(DecafScannerTokenTypes.DEC_LIT)) {
            IntLit intLit = parseIntLit();
            return new Lit(intLit);
        } else {
            throw new DecafParseException("");
        }
    }

    public IntLit parseIntLit() throws DecafParseException {
        if (isType(DecafScannerTokenTypes.HEX_LIT)) {
            IntLit intLit = new IntLit(new HexLit(text()));
            intLit.setLineNumber(line());
            next();
            return intLit;
        } else if (isType(DecafScannerTokenTypes.DEC_LIT)) {
            IntLit intLit = new IntLit(new DecLit(text()));
            intLit.setLineNumber(line());
            next();
            return intLit;
        } else {
            throw new DecafParseException("");
        }
    }

//    private HexLit parseHexLit() throws DecafParseException {
//        if (isType(DecafScannerTokenTypes.HEX_LIT)) {
//            HexLit hexLit = new HexLit(text());
//            next();
//            return hexLit;
//        } else {
//            throw new DecafParseException("");
//        }
//    }
//
//    private DecLit parseDecLit() throws DecafParseException {
//        if (isType(DecafScannerTokenTypes.HEX_LIT)) {
//            DecLit decLit = new DecLit(text());
//            next();
//            return decLit;
//        } else {
//            throw new DecafParseException("");
//        }
//    }

    private BinOp parseBinOp() throws DecafParseException {
        if (isType(DecafScannerTokenTypes.AND) ||
                isType(DecafScannerTokenTypes.OR) ||
                isType(DecafScannerTokenTypes.EQ) ||
                isType(DecafScannerTokenTypes.NEQ) ||
                isType(DecafScannerTokenTypes.LT) ||
                isType(DecafScannerTokenTypes.GT) ||
                isType(DecafScannerTokenTypes.LEQ) ||
                isType(DecafScannerTokenTypes.GEQ) ||
                isType(DecafScannerTokenTypes.PLUS) ||
                isType(DecafScannerTokenTypes.MINUS) ||
                isType(DecafScannerTokenTypes.MUL) ||
                isType(DecafScannerTokenTypes.DIV) ||
                isType(DecafScannerTokenTypes.MOD)) {
            BinOp binOp = new BinOp(text());
            binOp.setLineNumber(line());
            next();
            return binOp;
        } else {
            throw new DecafParseException("");
        }
    }


    private String text() {
        return tokens.get(currentTok).getText();
    }

    private int line() {
        return tokens.get(currentTok).getLine();
    }

    private void assertIsType(int type, String str) throws DecafParseException {
        if (isType(type)) {
            next();
            return;
        } else {
            throw new DecafParseException(str);
        }
    }

    private void next() {
        currentTok ++;
    }

    private Boolean isType(int type) {
        return tokens.get(currentTok).getType() == type;
    }

    private Boolean isType(int type, int lookAhead) {
        return tokens.get(currentTok+lookAhead).getType() == type;
    }

}
