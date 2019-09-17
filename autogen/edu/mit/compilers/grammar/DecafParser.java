// $ANTLR 2.7.7 (2006-11-01): "parser.g" -> "DecafParser.java"$

package edu.mit.compilers.grammar;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class DecafParser extends antlr.LLkParser       implements DecafParserTokenTypes
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

protected DecafParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public DecafParser(TokenBuffer tokenBuf) {
  this(tokenBuf,3);
}

protected DecafParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public DecafParser(TokenStream lexer) {
  this(lexer,3);
}

public DecafParser(ParserSharedInputState state) {
  super(state,3);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void program() throws RecognitionException, TokenStreamException {
		
		traceIn("program");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST program_AST = null;
			
			try {      // for error handling
				{
				_loop3:
				do {
					if ((LA(1)==TK_IMPORT)) {
						import_decl();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop3;
					}
					
				} while (true);
				}
				{
				_loop5:
				do {
					if ((LA(1)==TK_INT||LA(1)==TK_BOOL) && (LA(2)==ID) && (LA(3)==LBRACK||LA(3)==COMMA||LA(3)==SEMICOL)) {
						field_decl();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop5;
					}
					
				} while (true);
				}
				{
				_loop7:
				do {
					if ((LA(1)==TK_VOID||LA(1)==TK_INT||LA(1)==TK_BOOL)) {
						method_decl();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop7;
					}
					
				} while (true);
				}
				AST tmp1_AST = null;
				tmp1_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp1_AST);
				match(Token.EOF_TYPE);
				program_AST = (AST)currentAST.root;
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			}
			returnAST = program_AST;
		} finally { // debugging
			traceOut("program");
		}
	}
	
	public final void import_decl() throws RecognitionException, TokenStreamException {
		
		traceIn("import_decl");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST import_decl_AST = null;
			
			try {      // for error handling
				AST tmp2_AST = null;
				tmp2_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp2_AST);
				match(TK_IMPORT);
				AST tmp3_AST = null;
				tmp3_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp3_AST);
				match(ID);
				AST tmp4_AST = null;
				tmp4_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp4_AST);
				match(SEMICOL);
				import_decl_AST = (AST)currentAST.root;
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			}
			returnAST = import_decl_AST;
		} finally { // debugging
			traceOut("import_decl");
		}
	}
	
	public final void field_decl() throws RecognitionException, TokenStreamException {
		
		traceIn("field_decl");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST field_decl_AST = null;
			
			try {      // for error handling
				type();
				astFactory.addASTChild(currentAST, returnAST);
				{
				if ((LA(1)==ID) && (LA(2)==COMMA||LA(2)==SEMICOL)) {
					AST tmp5_AST = null;
					tmp5_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp5_AST);
					match(ID);
				}
				else if ((LA(1)==ID) && (LA(2)==LBRACK)) {
					AST tmp6_AST = null;
					tmp6_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp6_AST);
					match(ID);
					AST tmp7_AST = null;
					tmp7_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp7_AST);
					match(LBRACK);
					int_lit();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp8_AST = null;
					tmp8_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp8_AST);
					match(RBRACK);
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
				{
				_loop13:
				do {
					if ((LA(1)==COMMA)) {
						AST tmp9_AST = null;
						tmp9_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp9_AST);
						match(COMMA);
						{
						if ((LA(1)==ID) && (LA(2)==COMMA||LA(2)==SEMICOL)) {
							AST tmp10_AST = null;
							tmp10_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp10_AST);
							match(ID);
						}
						else if ((LA(1)==ID) && (LA(2)==LBRACK)) {
							AST tmp11_AST = null;
							tmp11_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp11_AST);
							match(ID);
							AST tmp12_AST = null;
							tmp12_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp12_AST);
							match(LBRACK);
							int_lit();
							astFactory.addASTChild(currentAST, returnAST);
							AST tmp13_AST = null;
							tmp13_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp13_AST);
							match(RBRACK);
						}
						else {
							throw new NoViableAltException(LT(1), getFilename());
						}
						
						}
					}
					else {
						break _loop13;
					}
					
				} while (true);
				}
				AST tmp14_AST = null;
				tmp14_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp14_AST);
				match(SEMICOL);
				field_decl_AST = (AST)currentAST.root;
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_2);
			}
			returnAST = field_decl_AST;
		} finally { // debugging
			traceOut("field_decl");
		}
	}
	
	public final void method_decl() throws RecognitionException, TokenStreamException {
		
		traceIn("method_decl");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST method_decl_AST = null;
			
			try {      // for error handling
				{
				switch ( LA(1)) {
				case TK_INT:
				case TK_BOOL:
				{
					type();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case TK_VOID:
				{
					AST tmp15_AST = null;
					tmp15_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp15_AST);
					match(TK_VOID);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp16_AST = null;
				tmp16_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp16_AST);
				match(ID);
				AST tmp17_AST = null;
				tmp17_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp17_AST);
				match(LPAREN);
				{
				switch ( LA(1)) {
				case TK_INT:
				case TK_BOOL:
				{
					{
					_loop18:
					do {
						if ((LA(1)==TK_INT||LA(1)==TK_BOOL) && (LA(2)==ID) && (LA(3)==COMMA)) {
							type();
							astFactory.addASTChild(currentAST, returnAST);
							AST tmp18_AST = null;
							tmp18_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp18_AST);
							match(ID);
							AST tmp19_AST = null;
							tmp19_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp19_AST);
							match(COMMA);
						}
						else {
							break _loop18;
						}
						
					} while (true);
					}
					type();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp20_AST = null;
					tmp20_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp20_AST);
					match(ID);
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp21_AST = null;
				tmp21_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp21_AST);
				match(RPAREN);
				block();
				astFactory.addASTChild(currentAST, returnAST);
				method_decl_AST = (AST)currentAST.root;
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			}
			returnAST = method_decl_AST;
		} finally { // debugging
			traceOut("method_decl");
		}
	}
	
	public final void type() throws RecognitionException, TokenStreamException {
		
		traceIn("type");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST type_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case TK_INT:
				{
					AST tmp22_AST = null;
					tmp22_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp22_AST);
					match(TK_INT);
					type_AST = (AST)currentAST.root;
					break;
				}
				case TK_BOOL:
				{
					AST tmp23_AST = null;
					tmp23_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp23_AST);
					match(TK_BOOL);
					type_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_4);
			}
			returnAST = type_AST;
		} finally { // debugging
			traceOut("type");
		}
	}
	
	public final void int_lit() throws RecognitionException, TokenStreamException {
		
		traceIn("int_lit");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST int_lit_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case DEC_LIT:
				{
					AST tmp24_AST = null;
					tmp24_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp24_AST);
					match(DEC_LIT);
					int_lit_AST = (AST)currentAST.root;
					break;
				}
				case HEX_LIT:
				{
					AST tmp25_AST = null;
					tmp25_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp25_AST);
					match(HEX_LIT);
					int_lit_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_5);
			}
			returnAST = int_lit_AST;
		} finally { // debugging
			traceOut("int_lit");
		}
	}
	
	public final void block() throws RecognitionException, TokenStreamException {
		
		traceIn("block");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST block_AST = null;
			
			try {      // for error handling
				AST tmp26_AST = null;
				tmp26_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp26_AST);
				match(LCURLY);
				{
				_loop21:
				do {
					if ((LA(1)==TK_INT||LA(1)==TK_BOOL)) {
						field_decl();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop21;
					}
					
				} while (true);
				}
				{
				_loop23:
				do {
					if ((_tokenSet_6.member(LA(1)))) {
						statement();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop23;
					}
					
				} while (true);
				}
				AST tmp27_AST = null;
				tmp27_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp27_AST);
				match(RCURLY);
				block_AST = (AST)currentAST.root;
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_7);
			}
			returnAST = block_AST;
		} finally { // debugging
			traceOut("block");
		}
	}
	
	public final void statement() throws RecognitionException, TokenStreamException {
		
		traceIn("statement");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST statement_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case TK_IF:
				{
					AST tmp28_AST = null;
					tmp28_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp28_AST);
					match(TK_IF);
					AST tmp29_AST = null;
					tmp29_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp29_AST);
					match(LPAREN);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp30_AST = null;
					tmp30_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp30_AST);
					match(RPAREN);
					block();
					astFactory.addASTChild(currentAST, returnAST);
					{
					switch ( LA(1)) {
					case TK_ELSE:
					{
						AST tmp31_AST = null;
						tmp31_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp31_AST);
						match(TK_ELSE);
						block();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case RCURLY:
					case ID:
					case TK_IF:
					case TK_FOR:
					case TK_WHILE:
					case TK_RETURN:
					case TK_BREAK:
					case TK_CONTINUE:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					statement_AST = (AST)currentAST.root;
					break;
				}
				case TK_FOR:
				{
					AST tmp32_AST = null;
					tmp32_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp32_AST);
					match(TK_FOR);
					AST tmp33_AST = null;
					tmp33_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp33_AST);
					match(LPAREN);
					AST tmp34_AST = null;
					tmp34_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp34_AST);
					match(ID);
					AST tmp35_AST = null;
					tmp35_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp35_AST);
					match(ASSIGN);
					AST tmp36_AST = null;
					tmp36_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp36_AST);
					match(SEMICOL);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp37_AST = null;
					tmp37_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp37_AST);
					match(SEMICOL);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					location();
					astFactory.addASTChild(currentAST, returnAST);
					assign_expr();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp38_AST = null;
					tmp38_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp38_AST);
					match(RPAREN);
					block();
					astFactory.addASTChild(currentAST, returnAST);
					statement_AST = (AST)currentAST.root;
					break;
				}
				case TK_WHILE:
				{
					AST tmp39_AST = null;
					tmp39_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp39_AST);
					match(TK_WHILE);
					AST tmp40_AST = null;
					tmp40_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp40_AST);
					match(LPAREN);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp41_AST = null;
					tmp41_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp41_AST);
					match(RPAREN);
					block();
					astFactory.addASTChild(currentAST, returnAST);
					statement_AST = (AST)currentAST.root;
					break;
				}
				case TK_RETURN:
				{
					AST tmp42_AST = null;
					tmp42_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp42_AST);
					match(TK_RETURN);
					{
					switch ( LA(1)) {
					case LPAREN:
					case MINUS:
					case NOT:
					case ID:
					case CHAR_LIT:
					case HEX_LIT:
					case DEC_LIT:
					case TK_LEN:
					case TK_TRUE:
					case TK_FALSE:
					{
						expr();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case SEMICOL:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					AST tmp43_AST = null;
					tmp43_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp43_AST);
					match(SEMICOL);
					statement_AST = (AST)currentAST.root;
					break;
				}
				case TK_BREAK:
				{
					AST tmp44_AST = null;
					tmp44_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp44_AST);
					match(TK_BREAK);
					AST tmp45_AST = null;
					tmp45_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp45_AST);
					match(SEMICOL);
					statement_AST = (AST)currentAST.root;
					break;
				}
				case TK_CONTINUE:
				{
					AST tmp46_AST = null;
					tmp46_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp46_AST);
					match(TK_CONTINUE);
					AST tmp47_AST = null;
					tmp47_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp47_AST);
					match(SEMICOL);
					statement_AST = (AST)currentAST.root;
					break;
				}
				default:
					if ((LA(1)==ID) && (_tokenSet_8.member(LA(2)))) {
						location();
						astFactory.addASTChild(currentAST, returnAST);
						assign_expr();
						astFactory.addASTChild(currentAST, returnAST);
						AST tmp48_AST = null;
						tmp48_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp48_AST);
						match(SEMICOL);
						statement_AST = (AST)currentAST.root;
					}
					else if ((LA(1)==ID) && (LA(2)==LPAREN)) {
						method_call();
						astFactory.addASTChild(currentAST, returnAST);
						AST tmp49_AST = null;
						tmp49_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp49_AST);
						match(SEMICOL);
						statement_AST = (AST)currentAST.root;
					}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			}
			returnAST = statement_AST;
		} finally { // debugging
			traceOut("statement");
		}
	}
	
	public final void location() throws RecognitionException, TokenStreamException {
		
		traceIn("location");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST location_AST = null;
			
			try {      // for error handling
				if ((LA(1)==ID) && (_tokenSet_10.member(LA(2)))) {
					AST tmp50_AST = null;
					tmp50_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp50_AST);
					match(ID);
					location_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ID) && (LA(2)==LBRACK)) {
					AST tmp51_AST = null;
					tmp51_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp51_AST);
					match(ID);
					AST tmp52_AST = null;
					tmp52_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp52_AST);
					match(LBRACK);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp53_AST = null;
					tmp53_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp53_AST);
					match(RBRACK);
					location_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			}
			returnAST = location_AST;
		} finally { // debugging
			traceOut("location");
		}
	}
	
	public final void assign_expr() throws RecognitionException, TokenStreamException {
		
		traceIn("assign_expr");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST assign_expr_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case PEQ:
				case MEQ:
				case ASSIGN:
				{
					assign_op();
					astFactory.addASTChild(currentAST, returnAST);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					assign_expr_AST = (AST)currentAST.root;
					break;
				}
				case INC:
				case DEC:
				{
					increment();
					astFactory.addASTChild(currentAST, returnAST);
					assign_expr_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_11);
			}
			returnAST = assign_expr_AST;
		} finally { // debugging
			traceOut("assign_expr");
		}
	}
	
	public final void method_call() throws RecognitionException, TokenStreamException {
		
		traceIn("method_call");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST method_call_AST = null;
			
			try {      // for error handling
				if ((LA(1)==ID) && (LA(2)==LPAREN) && (_tokenSet_12.member(LA(3)))) {
					method_name();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp54_AST = null;
					tmp54_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp54_AST);
					match(LPAREN);
					{
					switch ( LA(1)) {
					case LPAREN:
					case MINUS:
					case NOT:
					case ID:
					case CHAR_LIT:
					case HEX_LIT:
					case DEC_LIT:
					case TK_LEN:
					case TK_TRUE:
					case TK_FALSE:
					{
						expr();
						astFactory.addASTChild(currentAST, returnAST);
						{
						_loop34:
						do {
							if ((LA(1)==COMMA)) {
								AST tmp55_AST = null;
								tmp55_AST = astFactory.create(LT(1));
								astFactory.addASTChild(currentAST, tmp55_AST);
								match(COMMA);
								expr();
								astFactory.addASTChild(currentAST, returnAST);
							}
							else {
								break _loop34;
							}
							
						} while (true);
						}
						break;
					}
					case RPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					AST tmp56_AST = null;
					tmp56_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp56_AST);
					match(RPAREN);
					method_call_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ID) && (LA(2)==LPAREN) && (_tokenSet_13.member(LA(3)))) {
					method_name();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp57_AST = null;
					tmp57_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp57_AST);
					match(LPAREN);
					{
					switch ( LA(1)) {
					case LPAREN:
					case MINUS:
					case NOT:
					case ID:
					case CHAR_LIT:
					case STRING_LIT:
					case HEX_LIT:
					case DEC_LIT:
					case TK_LEN:
					case TK_TRUE:
					case TK_FALSE:
					{
						import_arg();
						astFactory.addASTChild(currentAST, returnAST);
						{
						_loop37:
						do {
							if ((LA(1)==COMMA)) {
								AST tmp58_AST = null;
								tmp58_AST = astFactory.create(LT(1));
								astFactory.addASTChild(currentAST, tmp58_AST);
								match(COMMA);
								import_arg();
								astFactory.addASTChild(currentAST, returnAST);
							}
							else {
								break _loop37;
							}
							
						} while (true);
						}
						break;
					}
					case RPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					AST tmp59_AST = null;
					tmp59_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp59_AST);
					match(RPAREN);
					method_call_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_5);
			}
			returnAST = method_call_AST;
		} finally { // debugging
			traceOut("method_call");
		}
	}
	
	public final void expr() throws RecognitionException, TokenStreamException {
		
		traceIn("expr");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST expr_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case CHAR_LIT:
				case HEX_LIT:
				case DEC_LIT:
				case TK_TRUE:
				case TK_FALSE:
				{
					lit();
					astFactory.addASTChild(currentAST, returnAST);
					expr_bin();
					astFactory.addASTChild(currentAST, returnAST);
					expr_AST = (AST)currentAST.root;
					break;
				}
				case TK_LEN:
				{
					AST tmp60_AST = null;
					tmp60_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp60_AST);
					match(TK_LEN);
					AST tmp61_AST = null;
					tmp61_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp61_AST);
					match(LPAREN);
					AST tmp62_AST = null;
					tmp62_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp62_AST);
					match(ID);
					AST tmp63_AST = null;
					tmp63_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp63_AST);
					match(RPAREN);
					expr_bin();
					astFactory.addASTChild(currentAST, returnAST);
					expr_AST = (AST)currentAST.root;
					break;
				}
				case MINUS:
				{
					AST tmp64_AST = null;
					tmp64_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp64_AST);
					match(MINUS);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					expr_bin();
					astFactory.addASTChild(currentAST, returnAST);
					expr_AST = (AST)currentAST.root;
					break;
				}
				case NOT:
				{
					AST tmp65_AST = null;
					tmp65_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp65_AST);
					match(NOT);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					expr_bin();
					astFactory.addASTChild(currentAST, returnAST);
					expr_AST = (AST)currentAST.root;
					break;
				}
				case LPAREN:
				{
					AST tmp66_AST = null;
					tmp66_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp66_AST);
					match(LPAREN);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp67_AST = null;
					tmp67_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp67_AST);
					match(RPAREN);
					expr_bin();
					astFactory.addASTChild(currentAST, returnAST);
					expr_AST = (AST)currentAST.root;
					break;
				}
				default:
					if ((LA(1)==ID) && (_tokenSet_14.member(LA(2)))) {
						location();
						astFactory.addASTChild(currentAST, returnAST);
						expr_bin();
						astFactory.addASTChild(currentAST, returnAST);
						expr_AST = (AST)currentAST.root;
					}
					else if ((LA(1)==ID) && (LA(2)==LPAREN)) {
						method_call();
						astFactory.addASTChild(currentAST, returnAST);
						expr_bin();
						astFactory.addASTChild(currentAST, returnAST);
						expr_AST = (AST)currentAST.root;
					}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_5);
			}
			returnAST = expr_AST;
		} finally { // debugging
			traceOut("expr");
		}
	}
	
	public final void assign_op() throws RecognitionException, TokenStreamException {
		
		traceIn("assign_op");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST assign_op_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case ASSIGN:
				{
					AST tmp68_AST = null;
					tmp68_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp68_AST);
					match(ASSIGN);
					assign_op_AST = (AST)currentAST.root;
					break;
				}
				case PEQ:
				case MEQ:
				{
					comp_assign_op();
					astFactory.addASTChild(currentAST, returnAST);
					assign_op_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_15);
			}
			returnAST = assign_op_AST;
		} finally { // debugging
			traceOut("assign_op");
		}
	}
	
	public final void increment() throws RecognitionException, TokenStreamException {
		
		traceIn("increment");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST increment_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case INC:
				{
					AST tmp69_AST = null;
					tmp69_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp69_AST);
					match(INC);
					increment_AST = (AST)currentAST.root;
					break;
				}
				case DEC:
				{
					AST tmp70_AST = null;
					tmp70_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp70_AST);
					match(DEC);
					increment_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_11);
			}
			returnAST = increment_AST;
		} finally { // debugging
			traceOut("increment");
		}
	}
	
	public final void comp_assign_op() throws RecognitionException, TokenStreamException {
		
		traceIn("comp_assign_op");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST comp_assign_op_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case PEQ:
				{
					AST tmp71_AST = null;
					tmp71_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp71_AST);
					match(PEQ);
					comp_assign_op_AST = (AST)currentAST.root;
					break;
				}
				case MEQ:
				{
					AST tmp72_AST = null;
					tmp72_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp72_AST);
					match(MEQ);
					comp_assign_op_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_15);
			}
			returnAST = comp_assign_op_AST;
		} finally { // debugging
			traceOut("comp_assign_op");
		}
	}
	
	public final void method_name() throws RecognitionException, TokenStreamException {
		
		traceIn("method_name");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST method_name_AST = null;
			
			try {      // for error handling
				AST tmp73_AST = null;
				tmp73_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp73_AST);
				match(ID);
				method_name_AST = (AST)currentAST.root;
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_16);
			}
			returnAST = method_name_AST;
		} finally { // debugging
			traceOut("method_name");
		}
	}
	
	public final void import_arg() throws RecognitionException, TokenStreamException {
		
		traceIn("import_arg");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST import_arg_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case LPAREN:
				case MINUS:
				case NOT:
				case ID:
				case CHAR_LIT:
				case HEX_LIT:
				case DEC_LIT:
				case TK_LEN:
				case TK_TRUE:
				case TK_FALSE:
				{
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					import_arg_AST = (AST)currentAST.root;
					break;
				}
				case STRING_LIT:
				{
					AST tmp74_AST = null;
					tmp74_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp74_AST);
					match(STRING_LIT);
					import_arg_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_17);
			}
			returnAST = import_arg_AST;
		} finally { // debugging
			traceOut("import_arg");
		}
	}
	
	public final void expr_bin() throws RecognitionException, TokenStreamException {
		
		traceIn("expr_bin");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST expr_bin_AST = null;
			
			try {      // for error handling
				if (((LA(1) >= ARITH_OP && LA(1) <= COND_OP)) && (_tokenSet_15.member(LA(2))) && (_tokenSet_18.member(LA(3)))) {
					bin_op();
					astFactory.addASTChild(currentAST, returnAST);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					expr_bin();
					astFactory.addASTChild(currentAST, returnAST);
					expr_bin_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_5.member(LA(1))) && (_tokenSet_19.member(LA(2))) && (_tokenSet_20.member(LA(3)))) {
					expr_bin_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_5);
			}
			returnAST = expr_bin_AST;
		} finally { // debugging
			traceOut("expr_bin");
		}
	}
	
	public final void lit() throws RecognitionException, TokenStreamException {
		
		traceIn("lit");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST lit_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case HEX_LIT:
				case DEC_LIT:
				{
					int_lit();
					astFactory.addASTChild(currentAST, returnAST);
					lit_AST = (AST)currentAST.root;
					break;
				}
				case CHAR_LIT:
				{
					AST tmp75_AST = null;
					tmp75_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp75_AST);
					match(CHAR_LIT);
					lit_AST = (AST)currentAST.root;
					break;
				}
				case TK_TRUE:
				case TK_FALSE:
				{
					bool_lit();
					astFactory.addASTChild(currentAST, returnAST);
					lit_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_5);
			}
			returnAST = lit_AST;
		} finally { // debugging
			traceOut("lit");
		}
	}
	
	public final void bin_op() throws RecognitionException, TokenStreamException {
		
		traceIn("bin_op");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST bin_op_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case ARITH_OP:
				{
					AST tmp76_AST = null;
					tmp76_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp76_AST);
					match(ARITH_OP);
					bin_op_AST = (AST)currentAST.root;
					break;
				}
				case REL_OP:
				{
					AST tmp77_AST = null;
					tmp77_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp77_AST);
					match(REL_OP);
					bin_op_AST = (AST)currentAST.root;
					break;
				}
				case EQ_OP:
				{
					AST tmp78_AST = null;
					tmp78_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp78_AST);
					match(EQ_OP);
					bin_op_AST = (AST)currentAST.root;
					break;
				}
				case COND_OP:
				{
					AST tmp79_AST = null;
					tmp79_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp79_AST);
					match(COND_OP);
					bin_op_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_15);
			}
			returnAST = bin_op_AST;
		} finally { // debugging
			traceOut("bin_op");
		}
	}
	
	public final void arith_op() throws RecognitionException, TokenStreamException {
		
		traceIn("arith_op");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST arith_op_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case MINUS:
				{
					AST tmp80_AST = null;
					tmp80_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp80_AST);
					match(MINUS);
					arith_op_AST = (AST)currentAST.root;
					break;
				}
				case PLUS:
				{
					AST tmp81_AST = null;
					tmp81_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp81_AST);
					match(PLUS);
					arith_op_AST = (AST)currentAST.root;
					break;
				}
				case MUL:
				{
					AST tmp82_AST = null;
					tmp82_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp82_AST);
					match(MUL);
					arith_op_AST = (AST)currentAST.root;
					break;
				}
				case DIV:
				{
					AST tmp83_AST = null;
					tmp83_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp83_AST);
					match(DIV);
					arith_op_AST = (AST)currentAST.root;
					break;
				}
				case MOD:
				{
					AST tmp84_AST = null;
					tmp84_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp84_AST);
					match(MOD);
					arith_op_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			}
			returnAST = arith_op_AST;
		} finally { // debugging
			traceOut("arith_op");
		}
	}
	
	public final void rel_op() throws RecognitionException, TokenStreamException {
		
		traceIn("rel_op");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST rel_op_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case LT:
				{
					AST tmp85_AST = null;
					tmp85_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp85_AST);
					match(LT);
					rel_op_AST = (AST)currentAST.root;
					break;
				}
				case GT:
				{
					AST tmp86_AST = null;
					tmp86_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp86_AST);
					match(GT);
					rel_op_AST = (AST)currentAST.root;
					break;
				}
				case LEQ:
				{
					AST tmp87_AST = null;
					tmp87_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp87_AST);
					match(LEQ);
					rel_op_AST = (AST)currentAST.root;
					break;
				}
				case GEQ:
				{
					AST tmp88_AST = null;
					tmp88_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp88_AST);
					match(GEQ);
					rel_op_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			}
			returnAST = rel_op_AST;
		} finally { // debugging
			traceOut("rel_op");
		}
	}
	
	public final void eq_op() throws RecognitionException, TokenStreamException {
		
		traceIn("eq_op");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST eq_op_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case EQ:
				{
					AST tmp89_AST = null;
					tmp89_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp89_AST);
					match(EQ);
					eq_op_AST = (AST)currentAST.root;
					break;
				}
				case NEQ:
				{
					AST tmp90_AST = null;
					tmp90_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp90_AST);
					match(NEQ);
					eq_op_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			}
			returnAST = eq_op_AST;
		} finally { // debugging
			traceOut("eq_op");
		}
	}
	
	public final void cond_op() throws RecognitionException, TokenStreamException {
		
		traceIn("cond_op");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST cond_op_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case AND:
				{
					AST tmp91_AST = null;
					tmp91_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp91_AST);
					match(AND);
					cond_op_AST = (AST)currentAST.root;
					break;
				}
				case OR:
				{
					AST tmp92_AST = null;
					tmp92_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp92_AST);
					match(OR);
					cond_op_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			}
			returnAST = cond_op_AST;
		} finally { // debugging
			traceOut("cond_op");
		}
	}
	
	public final void bool_lit() throws RecognitionException, TokenStreamException {
		
		traceIn("bool_lit");
		try { // debugging
			returnAST = null;
			ASTPair currentAST = new ASTPair();
			AST bool_lit_AST = null;
			
			try {      // for error handling
				switch ( LA(1)) {
				case TK_TRUE:
				{
					AST tmp93_AST = null;
					tmp93_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp93_AST);
					match(TK_TRUE);
					bool_lit_AST = (AST)currentAST.root;
					break;
				}
				case TK_FALSE:
				{
					AST tmp94_AST = null;
					tmp94_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp94_AST);
					match(TK_FALSE);
					bool_lit_AST = (AST)currentAST.root;
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				reportError(ex);
				recover(ex,_tokenSet_5);
			}
			returnAST = bool_lit_AST;
		} finally { // debugging
			traceOut("bool_lit");
		}
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"bool\"",
		"\"break\"",
		"\"import\"",
		"\"continue\"",
		"\"else\"",
		"\"false\"",
		"\"for\"",
		"\"while\"",
		"\"if\"",
		"\"int\"",
		"\"return\"",
		"\"len\"",
		"\"true\"",
		"\"void\"",
		"{",
		"}",
		"RBRACK",
		"LBRACK",
		"LPAREN",
		"RPAREN",
		"COMMA",
		"SEMICOL",
		"LEQ",
		"GEQ",
		"INC",
		"DEC",
		"PEQ",
		"MEQ",
		"AND",
		"OR",
		"EQ",
		"NEQ",
		"MINUS",
		"PLUS",
		"MUL",
		"DIV",
		"MOD",
		"LT",
		"GT",
		"ASSIGN",
		"NOT",
		"an identifier",
		"WS_",
		"SL_COMMENT",
		"BLOCK_COMMENT",
		"CHAR_LIT",
		"STRING_LIT",
		"HEX_LIT",
		"DEC_LIT",
		"ESC",
		"ALPHA_NUM",
		"ALPHA",
		"HEX_DIGIT",
		"DIGIT",
		"CHAR",
		"TK_IMPORT",
		"TK_VOID",
		"TK_IF",
		"TK_ELSE",
		"TK_FOR",
		"TK_WHILE",
		"TK_RETURN",
		"TK_BREAK",
		"TK_CONTINUE",
		"TK_LEN",
		"ARITH_OP",
		"REL_OP",
		"EQ_OP",
		"COND_OP",
		"TK_INT",
		"TK_BOOL",
		"TK_TRUE",
		"TK_FALSE"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 1729382256910270466L, 1536L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { -5764572338661621758L, 1551L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 1152921504606846978L, 1536L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 35184372088832L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 35184431857664L, 480L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { -6917493843268993024L, 15L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { -1152886320234233854L, 1551L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 8800121651200L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { -6917493843268468736L, 15L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 43984551411712L, 480L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 41943040L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 7371194684669952L, 6160L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { 8497094591512576L, 6160L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { 35184433954816L, 480L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { 7371194676281344L, 6160L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { 4194304L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 25165824L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { 7371194738147328L, 6640L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { -6909023132875751424L, 6655L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = { -1144415609841516542L, 8191L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	
	}
