header {
package edu.mit.compilers.grammar;
}

options
{
  mangleLiteralPrefix = "TK_";
  language = "Java";
}

{@SuppressWarnings("unchecked")}
class DecafScanner extends Lexer;
options
{
  k = 2;
}

tokens
{
  BOOL="bool";
  BREAK="break";
  IMPORT="import";
  CONTINUE="continue";
  ELSE="else";
  FALSE="false";
  FOR="for";
  WHILE="while";
  IF="if";
  INT="int";
  RETURN="return";
  LEN="len";
  TRUE="true";
  VOID="void";
}

// Selectively turns on debug tracing mode.
// You can insert arbitrary Java code into your parser/lexer this way.
{
  /** Whether to display debug information. */
  private boolean trace = false;

  public void setTrace(boolean shouldTrace) {
    trace = shouldTrace;
  }
  @Override
  public void traceIn(String rname) throws CharStreamException {
    if (trace) {
      super.traceIn(rname);
    }
  }
  @Override
  public void traceOut(String rname) throws CharStreamException {
    if (trace) {
      super.traceOut(rname);
    }
  }
}

LCURLY options { paraphrase = "{"; } : "{";
RCURLY options { paraphrase = "}"; } : "}";
RBRACK : "]";
LBRACK : "[";
LPAREN : "(";
RPAREN : ")";
COMMA : ",";
SEMICOL : ";";
LEQ : "<=";
GEQ : ">=";
INC : "++";
DEC : "--";
PEQ : "+=";
MEQ : "-=";
AND : "&&";
OR : "||";
EQ : "==";
NEQ : "!=";
MINUS : "-";
PLUS : "+";
MUL : "*";
DIV : "/";
MOD : "%";
LT : "<";
GT : ">";
ASSIGN : "=";
NOT : "!";

ID options { paraphrase = "an identifier"; } : 
  ALPHA (options {greedy=true;} : (ALPHA_NUM))*;

// Note that here, the {} syntax allows you to literally command the lexer
// to skip mark this token as skipped, or to advance to the next line
// by directly adding Java commands.
WS_ : ('\t' | ' ' | '\n' {newline();}) {_ttype = Token.SKIP; };
SL_COMMENT : "//" (~'\n')* '\n' {_ttype = Token.SKIP; newline (); };
BLOCK_COMMENT : "/*" (options {greedy=false;} : (BLOCK_COMMENT |~'\n' | '\n' {newline();} ))* "*/" {_ttype = Token.SKIP; };

CHAR_LIT : '\'' (CHAR) '\'';
STRING_LIT : '"' (CHAR)* '"';
HEX_LIT : "0x" (HEX_DIGIT)+;
DEC_LIT : (DIGIT)+;

//ARITH_OP : '+' | '-' | '*' | '/' | '%' ;
//REL_OP : '<'|'>'|"<="|">=";
//EQ_OP : "=="|"!=";
//COND_OP : "&&"|"||";
//ASSIGN_OP : "=";
//COMPOUND_ASSIGN_OP : "+=" | "-=";
// INC : "++" | "--";
// KEYWORD : "bool"|"break"|"import"|"continue"|"else"|"false"|"for"|"while"|"if"|"int"|"return"|"len"|"true"|"void";

protected ESC :  '\\' ('n'|'"'|'\''|'t'|'\\');
protected ALPHA_NUM : (ALPHA | DIGIT);
protected ALPHA : ('a'..'z'|'A'..'Z'|'_');
protected HEX_DIGIT : DIGIT | 'a'..'f' | 'A'..'F';
protected DIGIT : '0'..'9';
protected CHAR : (ESC|~('\n'|'"'|'\t'|'\''|'\\'));
