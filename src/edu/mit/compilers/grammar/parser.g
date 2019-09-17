header {
package edu.mit.compilers.grammar;
}

options
{
  mangleLiteralPrefix = "TK_";
  language = "Java";
}

class DecafParser extends Parser;
options
{
  importVocab = DecafScanner;
  k = 3;
  buildAST = true;
}

// Java glue code that makes error reporting easier.
// You can insert arbitrary Java code into your parser/lexer this way.
{
  // Do our own reporting of errors so the parser can return a non-zero status
  // if any errors are detected.
  /** Reports if any errors were reported during parse. */
  private boolean error;

  @Override
  public void reportError (RecognitionException ex) {
    // Print the error via some kind of error reporting mechanism.
    error = true;
  }
  @Override
  public void reportError (String s) {
    // Print the error via some kind of error reporting mechanism.
    error = true;
  }
  public boolean getError () {
    return error;
  }

  // Selectively turns on debug mode.

  /** Whether to display debug information. */
  private boolean trace = false;

  public void setTrace(boolean shouldTrace) {
    trace = shouldTrace;
  }
  @Override
  public void traceIn(String rname) throws TokenStreamException {
    if (trace) {
      super.traceIn(rname);
    }
  }
  @Override
  public void traceOut(String rname) throws TokenStreamException {
    if (trace) {
      super.traceOut(rname);
    }
  }
}

program : (import_decl)* (field_decl)* (method_decl)* EOF;
import_decl : TK_IMPORT ID SEMICOL;
field_decl : type (ID | ID LBRACK int_lit RBRACK) (COMMA (ID | ID LBRACK
              int_lit RBRACK))* SEMICOL;
method_decl : (type | TK_VOID) ID LPAREN ((type ID COMMA)* type ID)? RPAREN block;
block : LCURLY (field_decl)* (statement)* RCURLY;
statement : location assign_expr SEMICOL
        | method_call SEMICOL
        | TK_IF LPAREN expr RPAREN block (TK_ELSE block)?
        | TK_FOR LPAREN ID ASSIGN SEMICOL expr SEMICOL expr location assign_expr
            RPAREN block
        | TK_WHILE LPAREN expr RPAREN block
        | TK_RETURN (expr)? SEMICOL
        | TK_BREAK SEMICOL
        | TK_CONTINUE SEMICOL;
assign_expr : assign_op expr | increment;
assign_op : ASSIGN | comp_assign_op;
comp_assign_op : PEQ | MEQ;
increment : INC | DEC;
method_call : method_name LPAREN (expr (COMMA expr)*)? RPAREN
          | method_name LPAREN (import_arg (COMMA import_arg)*)? RPAREN;
method_name : ID;
location : ID | ID LBRACK expr RBRACK;

expr : location expr_bin
    | method_call expr_bin
    | lit expr_bin
    | TK_LEN LPAREN ID RPAREN expr_bin
    | MINUS expr expr_bin
    | NOT expr expr_bin
    | LPAREN expr RPAREN expr_bin;
expr_bin : bin_op expr expr_bin | ;
import_arg: expr | STRING_LIT;
bin_op : ARITH_OP | REL_OP | EQ_OP | COND_OP;
arith_op : MINUS | PLUS | MUL | DIV | MOD;
rel_op : LT | GT | LEQ | GEQ;
eq_op : EQ |NEQ;
cond_op : AND | OR;
type : TK_INT | TK_BOOL;
lit: int_lit | CHAR_LIT | bool_lit;
int_lit: DEC_LIT | HEX_LIT;
bool_lit : TK_TRUE | TK_FALSE;
