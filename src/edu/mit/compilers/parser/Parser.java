package edu.mit.compilers;

import java.io.*;

public class Parser {
    private final Scanner mScanner;

    Parser(Scanner scanner) {
      mScanner = scanner;
    }
    
    public __ parse() throws ParseException {
      Token token = mScanner.nextToken();
      switch(token.getType()) {
        case DecafScannerTokenTypes.ID:
          break;
        case DecafScannerTokenTypes.IMPORT:
          break;
        case DecafScannerTokenTypes.VOID:
          break;
        case DecafScannerTokenTypes.INT:
          break;
        case DecafScannerTokenTypes.BOOL:
          break;
        case DecafScannerTokenTypes.IF:
          break;
        case DecafScannerTokenTypes.ELSE:
          break;
        case DecafScannerTokenTypes.FOR:
          break;
        case DecafScannerTokenTypes.WHILE:
          break;
    }

    public __ parseProgram() {
      if (token.getType() == IMPORT) {
        __ importt = parseImport();
        __ program = parseProgram();
        return Program(Import(id),program);
      } else if (Type.first(token.getType())) {
        __ methodOrField = parseMethodOrField;
        __ program = parseProgram();
        return Program(methodOrField,program);
      } else if (token.getType() == VOID) {
        __ method = parseMethod();
        __ program = parseProgram();
        return Program(method,program);
      } else {
        // throw exception
      }
    }

    public __ parseImport() {
      if (token.getType() == IMPORT) {
        token = mScanner.nextToken();
        __ id = parseImport();
        if (token.getType() == SEMI) {
          token = mScanner.nextToken();
          return Import(id);
        } else {
        }
      } else {
      }
    }

    public __ parseFieldOrMethod() {
       
    }

    public __ parseMethod() {
      // void, int, bool, id 
      // block

    }

    public __ parseType() {
      if (token.getType() == INT || token.getType() == BOOL) {
        
        return
      }
    }
    

    public __ parseStringLit() {
      if (token.getType() == DecafScannerTokenTypes.STRING_LIT) {
        return StringLit(token.getText());
      } // else error?
    }

    public __ parseCharLit() {
      if (token.getType() == DecafScannerTokenTypes.CHAR_LIT) {
        return CharLit(token.getText());
      }
    }
    
    public __ parseBool() {
      if (token.getType() == DecafScannerTokenTypes.TRUE || 
          token.getType() == DecafScannerTokenTypes.FALSE) {
        return BoolLit(token.getText());
      }
    }

    public __ parseIntLit() {
      if (token.getType() == DecafScannerTokenTypes.HEX_LIT) {
        return IntLit(HexLit());
      } else if (token.getType() == DecafScannerTokenTypes.DEC_LIT) {
        return IntLit(DecLit());
      }
    }

    public __ parseHexLit() {
      if (token.getType() == DecafScannerTokenTypes.HEX_LIT) {
        return HexLit(token.getText());
      }
    }

    public __ parseDecLit() {
      if (token.getType() == DecafScannerTokenTypes.DEC_LIT) {
        return DecLit(token.getText());
      }
    }

    private void next() {
      token = mScanner.nextToken()
    }

}
