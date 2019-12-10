package edu.mit.compilers;

import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import antlr.Token;
import edu.mit.compilers.cse.CommonSubExpressionEliminator;
import edu.mit.compilers.cse.GlobalAvailableSubExpressionsAnalyzer;
import edu.mit.compilers.cse.LocalCommonSubExpressionEliminator;
import edu.mit.compilers.inter.*;
import edu.mit.compilers.tools.CLI;
import edu.mit.compilers.tools.CLI.Action;
import edu.mit.compilers.visitor.*;
import edu.mit.compilers.assembly.AssemblyFactory;
import edu.mit.compilers.assembly.Reg;
import edu.mit.compilers.cfg.MethodCFGFactory;
/*
 * You have to include this line, or else when you ant clean,
 * `ant` won't work on the second try.
 * import edu.mit.compilers.grammar.*;
 */
import edu.mit.compilers.grammar.*;
import edu.mit.compilers.parser.*;
import edu.mit.compilers.reg.InterferenceGraph;
import edu.mit.compilers.reg.RegisterAllocator;
import edu.mit.compilers.semantics.BreakAndContinueInAnyLoop;
import edu.mit.compilers.semantics.CheckIdDeclared;
import edu.mit.compilers.semantics.CheckTypes;
import edu.mit.compilers.semantics.IntLiteralInRange;
import edu.mit.compilers.semantics.UniqueGlobalIds;
import edu.mit.compilers.semantics.VoidMainNoArgs;

public class Main {

    public static final String[] optnames = {"cse", "reg"};

  public static void main(String[] args) {
    try {
      CLI.parse(args, optnames);
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
                     new CheckIdDeclared(table),
                     new UniqueGlobalIds(table),
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
                     outputStream.println(e.getMessage());
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
                       new CheckIdDeclared(table),
                       new UniqueGlobalIds(table),
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
                       outputStream.println(e.getMessage());
                   }
                   System.exit(1);
               }

               // set Declaration Scopes on all Id's
               new EliminateShadowingVisitor(table);

               MethodCFGFactory.makeAndSetMethodCFGs(table);

               if (CLI.opts[0]) { // CSE
                   for (MethodDescriptor methodDescriptor: table.methodTable.values()) {
                       new CommonSubExpressionEliminator(methodDescriptor.getMethodCFG());
                       // copy propagate?
                       // dead code?
                   }
               }

               for (MethodDescriptor methodDescriptor : table.methodTable.values()) {
                   outputStream.println("CFG for " + methodDescriptor.getMethodName());
                   outputStream.println("----------");
                   MethodCFGFactory.dfsPrint(methodDescriptor.getMethodCFG(), new HashSet<Integer>(), outputStream);
                   outputStream.println("----------");
               }
           } catch (DecafParseException e) {
               e.printStackTrace();
               System.exit(1);
           } catch (SemanticException e) {
               // exception while building symbol tables
               e.printStackTrace();
               System.exit(1);
           }
      } else if (CLI.target == Action.ASSEMBLY) {
        DecafScanner scanner =
            new DecafScanner(new DataInputStream(inputStream));
        Parser myParser = new Parser(scanner);
        try {
            Program p = myParser.parse();

            ProgramDescriptor table = new ProgramDescriptor(p);

            List<SemanticException> semanticExceptions = new ArrayList<>();

            SemanticChecker[] semanticCheckers = {
                    new CheckIdDeclared(table),
                    new UniqueGlobalIds(table),
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
                    outputStream.println(e.getMessage());
                }
                System.exit(1);
            }

            // set Declaration Scopes on all Id's
            new EliminateShadowingVisitor(table);

            MethodCFGFactory.makeAndSetMethodCFGs(table);

            if (CLI.opts[0]) { // CSE
                for (MethodDescriptor methodDescriptor: table.methodTable.values()) {
                    new CommonSubExpressionEliminator(methodDescriptor.getMethodCFG());
                    // copy propagate?
                    // dead code?
                }
            }

            if (CLI.opts[1]) { // REG
                for (MethodDescriptor methodDescriptor: table.methodTable.values()) {
                    InterferenceGraph graph = new InterferenceGraph(methodDescriptor.getMethodCFG());
                    new RegisterAllocator(graph.getAdjList(), Set.of(Reg.RBX, Reg.R12, Reg.R13, Reg.R14, Reg.R15));
                }
            }

            List<String> assembly = AssemblyFactory.programAssemblyGen(table);

            for (String line : assembly) {
                outputStream.println(line);
            }
        } catch (DecafParseException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SemanticException e) {
            // exception while building symbol tables
            e.printStackTrace();
            System.exit(1);
        }
      } else if (CLI.target == Action.WEB) {
          DecafScanner scanner =
                  new DecafScanner(new DataInputStream(inputStream));
              Parser myParser = new Parser(scanner);
              try {
                  Program p = myParser.parse();

                  ProgramDescriptor table = new ProgramDescriptor(p);

                  List<SemanticException> semanticExceptions = new ArrayList<>();

                  SemanticChecker[] semanticCheckers = {
                          new CheckIdDeclared(table),
                          new UniqueGlobalIds(table),
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
                          outputStream.println(e.getMessage());
                      }
                      System.exit(1);
                  }

                  // set Declaration Scopes on all Id's
                  new EliminateShadowingVisitor(table);

                  MethodCFGFactory.makeAndSetMethodCFGs(table);

                  if (CLI.opts[0]) { // CSE
                      for (MethodDescriptor methodDescriptor: table.methodTable.values()) {
                          new CommonSubExpressionEliminator(methodDescriptor.getMethodCFG());
                          // copy propagate?
                          // dead code?
                      }
                  }

                  for (MethodDescriptor methodDescriptor: table.methodTable.values()) {
                      outputStream.println("CFG for " + methodDescriptor.getMethodName());
                      outputStream.println("----------");
                      MethodCFGFactory.dfsPrint(methodDescriptor.getMethodCFG(), new HashSet<Integer>(), outputStream);

                      boolean debugInterferenceGraph = CLI.debug;
                      InterferenceGraph graph = new InterferenceGraph(methodDescriptor.getMethodCFG(), debugInterferenceGraph);
                      outputStream.println(graph);
                      outputStream.println("----------");
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

      outputStream.close();
    } catch(Exception e) {
      // print the error:
        e.printStackTrace();
      System.err.println(CLI.infile+" "+e);
      System.exit(1);
    }
  }
}
