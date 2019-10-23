package edu.mit.compilers;

import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import antlr.Token;
import edu.mit.compilers.inter.*;
import edu.mit.compilers.tools.CLI;
import edu.mit.compilers.tools.CLI.Action;
import edu.mit.compilers.visitor.*;
import edu.mit.compilers.assembly.CFFactory;
import edu.mit.compilers.assembly.CFNode;
/*
 * You have to include this line, or else when you ant clean,
 * `ant` won't work on the second try.
 */
import edu.mit.compilers.grammar.*; // MANUALLY ADDED see note above; use compiled files
import edu.mit.compilers.parser.*;

class Main {
  public static void main(String[] args) {
    try {
      CLI.parse(args, new String[0]);
      InputStream inputStream = args.length == 0 ?
          System.in : new java.io.FileInputStream(CLI.infile);
      PrintStream outputStream = CLI.outfile == null ? System.out : new java.io.PrintStream(new java.io.FileOutputStream(CLI.outfile));
      if (CLI.target == Action.SCAN) {
        DecafScanner scanner =
            new DecafScanner(new DataInputStream(inputStream));
        scanner.setTrace(CLI.debug);
        Token token;
        boolean done = false;
        while (!done) {
          try {
            for (token = scanner.nextToken();
                 token.getType() != DecafScannerTokenTypes.EOF;
                 token = scanner.nextToken()) {
              String type = "";
              String text = token.getText();
              switch (token.getType()) {
               // TODO: add strings for the other types here...
               case DecafScannerTokenTypes.ID:
                type = " IDENTIFIER";
                break;
               case DecafScannerTokenTypes.CHAR_LIT:
                type = " CHARLITERAL";
                break;
               case DecafScannerTokenTypes.STRING_LIT:
                type = " STRINGLITERAL";
                break;
               case DecafScannerTokenTypes.HEX_LIT:
               case DecafScannerTokenTypes.DEC_LIT:
                type = " INTLITERAL";
                break;
               case DecafScannerTokenTypes.TRUE:
               case DecafScannerTokenTypes.FALSE:
                type = " BOOLEANLITERAL";
                break;
              }
              outputStream.println(token.getLine() + type + " " + text);
            }
            done = true;
          } catch(Exception e) {
            // print the error:
            System.err.println(CLI.infile + " " + e);
            scanner.consume();
            System.exit(1);
          }
        }
      } else if (CLI.target == Action.PARSE ||
                 CLI.target == Action.DEFAULT) {
        DecafScanner scanner =
            new DecafScanner(new DataInputStream(inputStream));
        Parser myParser = new Parser(scanner);
        try {
            myParser.parse();
        } catch (DecafParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
      } else if (CLI.target == Action.INTER) {
         DecafScanner scanner =
             new DecafScanner(new DataInputStream(inputStream));
         Parser myParser = new Parser(scanner);
         try {
             Program p = myParser.parse();

             ProgramDescriptor table = new ProgramDescriptor(p);

             List<SemanticException> semanticExceptions = new ArrayList<>();

             SemanticChecker[] visitors = {
                     new CheckIdDeclared(p, table),
                     new UniqueGlobalIds(p, table),
                     new VoidMainNoArgs(table),
                     new BreakAndContinueInAnyLoop(),
                     new CheckTypes(table),
                     new IntLiteralInRange(),
             };

             for (SemanticChecker checker : visitors) {
                 p.accept(checker);
                 semanticExceptions.addAll(checker.getSemanticExceptions());
             }
             if (!semanticExceptions.isEmpty()) {
                 for (SemanticException e : semanticExceptions) {
                     System.out.println(e.getMessage());
                 }
                 System.exit(1);
             }
         } catch (DecafParseException e) {
             e.printStackTrace();
             System.exit(1);
         } catch (SemanticException e) {
             // exception while building symbol tables
             e.printStackTrace();
             System.exit(1);
         }
       } else if (CLI.target == Action.CFG) {
           DecafScanner scanner =
               new DecafScanner(new DataInputStream(inputStream));
           Parser myParser = new Parser(scanner);
           try {
               Program p = myParser.parse();

               ProgramDescriptor table = new ProgramDescriptor(p);

               List<SemanticException> semanticExceptions = new ArrayList<>();

               SemanticChecker[] semanticCheckers = {
                       new CheckIdDeclared(p, table),
                       new UniqueGlobalIds(p, table),
                       new VoidMainNoArgs(table),
                       new BreakAndContinueInAnyLoop(),
                       new CheckTypes(table),
                       new IntLiteralInRange(),
               };

               for (SemanticChecker checker : semanticCheckers) {
                   p.accept(checker);
                   semanticExceptions.addAll(checker.getSemanticExceptions());
               }
               if (!semanticExceptions.isEmpty()) {
                   for (SemanticException e : semanticExceptions) {
                       System.out.println(e.getMessage());
                   }
                   System.exit(1);
               }

               for (MethodDeclaration methodDeclaration : p.methodDeclarations) {
                   CFNode cfg = CFFactory.makeBlockCFG(methodDeclaration.mBlock);

                   CFVisitor[] cfVisitors = {
                           new PeepholeRemoveNops(),
                   };

                   for (CFVisitor cfVisitor : cfVisitors) {

                       if (CLI.debug) {
                           System.out.println("CFG before visitor " + cfVisitor + " for " + methodDeclaration.methodName.getName());
                           System.out.println("D---------");
                           dfsPrint(cfg, new HashSet<Integer>());
                           System.out.println("D---------");
                       }
                       cfg.accept(cfVisitor);
                   }

                   System.out.println("CFG for " + methodDeclaration.methodName.getName());
                   System.out.println("----------");
                   dfsPrint(cfg, new HashSet<Integer>());
                   System.out.println("----------");
               }
           } catch (DecafParseException e) {
               e.printStackTrace();
               System.exit(1);
           } catch (SemanticException e) {
               // exception while building symbol tables
               e.printStackTrace();
               System.exit(1);
           }
      }
    } catch(Exception e) {
      // print the error:
        e.printStackTrace();
      System.err.println(CLI.infile+" "+e);
    }
  }

    private static void dfsPrint(CFNode cfg, Set<Integer> visited) {
        int cfgID = cfg.getUID();
        if (!visited.contains(cfgID)) {
            visited.add(cfgID);
            System.out.println(cfg.toString());
            for (CFNode neighbor : cfg.dfsTraverse()) {
                dfsPrint(neighbor, visited);
            }
        }
    }
}
