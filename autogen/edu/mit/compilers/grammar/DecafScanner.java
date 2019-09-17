// $ANTLR 2.7.7 (2006-11-01): "scanner.g" -> "DecafScanner.java"$

package edu.mit.compilers.grammar;

import java.io.InputStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.ANTLRException;
import java.io.Reader;
import java.util.Hashtable;
import antlr.CharScanner;
import antlr.InputBuffer;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.Token;
import antlr.CommonToken;
import antlr.RecognitionException;
import antlr.NoViableAltForCharException;
import antlr.MismatchedCharException;
import antlr.TokenStream;
import antlr.ANTLRHashString;
import antlr.LexerSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.SemanticException;
@SuppressWarnings("unchecked")
public class DecafScanner extends antlr.CharScanner implements DecafScannerTokenTypes, TokenStream
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
public DecafScanner(InputStream in) {
	this(new ByteBuffer(in));
}
public DecafScanner(Reader in) {
	this(new CharBuffer(in));
}
public DecafScanner(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public DecafScanner(LexerSharedInputState state) {
	super(state);
	caseSensitiveLiterals = true;
	setCaseSensitive(true);
	literals = new Hashtable();
	literals.put(new ANTLRHashString("len", this), new Integer(15));
	literals.put(new ANTLRHashString("for", this), new Integer(10));
	literals.put(new ANTLRHashString("if", this), new Integer(12));
	literals.put(new ANTLRHashString("while", this), new Integer(11));
	literals.put(new ANTLRHashString("break", this), new Integer(5));
	literals.put(new ANTLRHashString("else", this), new Integer(8));
	literals.put(new ANTLRHashString("continue", this), new Integer(7));
	literals.put(new ANTLRHashString("void", this), new Integer(17));
	literals.put(new ANTLRHashString("import", this), new Integer(6));
	literals.put(new ANTLRHashString("true", this), new Integer(16));
	literals.put(new ANTLRHashString("bool", this), new Integer(4));
	literals.put(new ANTLRHashString("int", this), new Integer(13));
	literals.put(new ANTLRHashString("false", this), new Integer(9));
	literals.put(new ANTLRHashString("return", this), new Integer(14));
}

public Token nextToken() throws TokenStreamException {
	Token theRetToken=null;
tryAgain:
	for (;;) {
		Token _token = null;
		int _ttype = Token.INVALID_TYPE;
		resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				switch ( LA(1)) {
				case '{':
				{
					mLCURLY(true);
					theRetToken=_returnToken;
					break;
				}
				case '}':
				{
					mRCURLY(true);
					theRetToken=_returnToken;
					break;
				}
				case ']':
				{
					mRBRACK(true);
					theRetToken=_returnToken;
					break;
				}
				case '[':
				{
					mLBRACK(true);
					theRetToken=_returnToken;
					break;
				}
				case '(':
				{
					mLPAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case ')':
				{
					mRPAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case ',':
				{
					mCOMMA(true);
					theRetToken=_returnToken;
					break;
				}
				case ';':
				{
					mSEMICOL(true);
					theRetToken=_returnToken;
					break;
				}
				case '&':
				{
					mAND(true);
					theRetToken=_returnToken;
					break;
				}
				case '|':
				{
					mOR(true);
					theRetToken=_returnToken;
					break;
				}
				case '*':
				{
					mMUL(true);
					theRetToken=_returnToken;
					break;
				}
				case '%':
				{
					mMOD(true);
					theRetToken=_returnToken;
					break;
				}
				case 'A':  case 'B':  case 'C':  case 'D':
				case 'E':  case 'F':  case 'G':  case 'H':
				case 'I':  case 'J':  case 'K':  case 'L':
				case 'M':  case 'N':  case 'O':  case 'P':
				case 'Q':  case 'R':  case 'S':  case 'T':
				case 'U':  case 'V':  case 'W':  case 'X':
				case 'Y':  case 'Z':  case '_':  case 'a':
				case 'b':  case 'c':  case 'd':  case 'e':
				case 'f':  case 'g':  case 'h':  case 'i':
				case 'j':  case 'k':  case 'l':  case 'm':
				case 'n':  case 'o':  case 'p':  case 'q':
				case 'r':  case 's':  case 't':  case 'u':
				case 'v':  case 'w':  case 'x':  case 'y':
				case 'z':
				{
					mID(true);
					theRetToken=_returnToken;
					break;
				}
				case '\t':  case '\n':  case ' ':
				{
					mWS_(true);
					theRetToken=_returnToken;
					break;
				}
				case '\'':
				{
					mCHAR_LIT(true);
					theRetToken=_returnToken;
					break;
				}
				case '"':
				{
					mSTRING_LIT(true);
					theRetToken=_returnToken;
					break;
				}
				default:
					if ((LA(1)=='<') && (LA(2)=='=')) {
						mLEQ(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='>') && (LA(2)=='=')) {
						mGEQ(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='+') && (LA(2)=='+')) {
						mINC(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='-') && (LA(2)=='-')) {
						mDEC(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='+') && (LA(2)=='=')) {
						mPEQ(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='-') && (LA(2)=='=')) {
						mMEQ(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='=') && (LA(2)=='=')) {
						mEQ(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='!') && (LA(2)=='=')) {
						mNEQ(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='/') && (LA(2)=='/')) {
						mSL_COMMENT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='/') && (LA(2)=='*')) {
						mBLOCK_COMMENT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='0') && (LA(2)=='x')) {
						mHEX_LIT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='-') && (true)) {
						mMINUS(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='+') && (true)) {
						mPLUS(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='/') && (true)) {
						mDIV(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='<') && (true)) {
						mLT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='>') && (true)) {
						mGT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='=') && (true)) {
						mASSIGN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='!') && (true)) {
						mNOT(true);
						theRetToken=_returnToken;
					}
					else if (((LA(1) >= '0' && LA(1) <= '9')) && (true)) {
						mDEC_LIT(true);
						theRetToken=_returnToken;
					}
				else {
					if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}
				else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				}
				if ( _returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = _returnToken.getType();
				_ttype = testLiteralsTable(_ttype);
				_returnToken.setType(_ttype);
				return _returnToken;
			}
			catch (RecognitionException e) {
				throw new TokenStreamRecognitionException(e);
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException ) {
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			}
			else {
				throw new TokenStreamException(cse.getMessage());
			}
		}
	}
}

	public final void mLCURLY(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mLCURLY");
		_ttype = LCURLY;
		int _saveIndex;
		try { // debugging
			
			match("{");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mLCURLY");
		}
	}
	
	public final void mRCURLY(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mRCURLY");
		_ttype = RCURLY;
		int _saveIndex;
		try { // debugging
			
			match("}");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mRCURLY");
		}
	}
	
	public final void mRBRACK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mRBRACK");
		_ttype = RBRACK;
		int _saveIndex;
		try { // debugging
			
			match("]");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mRBRACK");
		}
	}
	
	public final void mLBRACK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mLBRACK");
		_ttype = LBRACK;
		int _saveIndex;
		try { // debugging
			
			match("[");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mLBRACK");
		}
	}
	
	public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mLPAREN");
		_ttype = LPAREN;
		int _saveIndex;
		try { // debugging
			
			match("(");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mLPAREN");
		}
	}
	
	public final void mRPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mRPAREN");
		_ttype = RPAREN;
		int _saveIndex;
		try { // debugging
			
			match(")");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mRPAREN");
		}
	}
	
	public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mCOMMA");
		_ttype = COMMA;
		int _saveIndex;
		try { // debugging
			
			match(",");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mCOMMA");
		}
	}
	
	public final void mSEMICOL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mSEMICOL");
		_ttype = SEMICOL;
		int _saveIndex;
		try { // debugging
			
			match(";");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mSEMICOL");
		}
	}
	
	public final void mLEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mLEQ");
		_ttype = LEQ;
		int _saveIndex;
		try { // debugging
			
			match("<=");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mLEQ");
		}
	}
	
	public final void mGEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mGEQ");
		_ttype = GEQ;
		int _saveIndex;
		try { // debugging
			
			match(">=");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mGEQ");
		}
	}
	
	public final void mINC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mINC");
		_ttype = INC;
		int _saveIndex;
		try { // debugging
			
			match("++");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mINC");
		}
	}
	
	public final void mDEC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mDEC");
		_ttype = DEC;
		int _saveIndex;
		try { // debugging
			
			match("--");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mDEC");
		}
	}
	
	public final void mPEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mPEQ");
		_ttype = PEQ;
		int _saveIndex;
		try { // debugging
			
			match("+=");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mPEQ");
		}
	}
	
	public final void mMEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mMEQ");
		_ttype = MEQ;
		int _saveIndex;
		try { // debugging
			
			match("-=");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mMEQ");
		}
	}
	
	public final void mAND(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mAND");
		_ttype = AND;
		int _saveIndex;
		try { // debugging
			
			match("&&");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mAND");
		}
	}
	
	public final void mOR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mOR");
		_ttype = OR;
		int _saveIndex;
		try { // debugging
			
			match("||");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mOR");
		}
	}
	
	public final void mEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mEQ");
		_ttype = EQ;
		int _saveIndex;
		try { // debugging
			
			match("==");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mEQ");
		}
	}
	
	public final void mNEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mNEQ");
		_ttype = NEQ;
		int _saveIndex;
		try { // debugging
			
			match("!=");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mNEQ");
		}
	}
	
	public final void mMINUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mMINUS");
		_ttype = MINUS;
		int _saveIndex;
		try { // debugging
			
			match("-");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mMINUS");
		}
	}
	
	public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mPLUS");
		_ttype = PLUS;
		int _saveIndex;
		try { // debugging
			
			match("+");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mPLUS");
		}
	}
	
	public final void mMUL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mMUL");
		_ttype = MUL;
		int _saveIndex;
		try { // debugging
			
			match("*");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mMUL");
		}
	}
	
	public final void mDIV(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mDIV");
		_ttype = DIV;
		int _saveIndex;
		try { // debugging
			
			match("/");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mDIV");
		}
	}
	
	public final void mMOD(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mMOD");
		_ttype = MOD;
		int _saveIndex;
		try { // debugging
			
			match("%");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mMOD");
		}
	}
	
	public final void mLT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mLT");
		_ttype = LT;
		int _saveIndex;
		try { // debugging
			
			match("<");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mLT");
		}
	}
	
	public final void mGT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mGT");
		_ttype = GT;
		int _saveIndex;
		try { // debugging
			
			match(">");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mGT");
		}
	}
	
	public final void mASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mASSIGN");
		_ttype = ASSIGN;
		int _saveIndex;
		try { // debugging
			
			match("=");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mASSIGN");
		}
	}
	
	public final void mNOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mNOT");
		_ttype = NOT;
		int _saveIndex;
		try { // debugging
			
			match("!");
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mNOT");
		}
	}
	
	public final void mID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mID");
		_ttype = ID;
		int _saveIndex;
		try { // debugging
			
			mALPHA(false);
			{
			_loop31:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					{
					mALPHA_NUM(false);
					}
				}
				else {
					break _loop31;
				}
				
			} while (true);
			}
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mID");
		}
	}
	
	protected final void mALPHA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mALPHA");
		_ttype = ALPHA;
		int _saveIndex;
		try { // debugging
			
			{
			switch ( LA(1)) {
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':  case 'g':  case 'h':
			case 'i':  case 'j':  case 'k':  case 'l':
			case 'm':  case 'n':  case 'o':  case 'p':
			case 'q':  case 'r':  case 's':  case 't':
			case 'u':  case 'v':  case 'w':  case 'x':
			case 'y':  case 'z':
			{
				matchRange('a','z');
				break;
			}
			case 'A':  case 'B':  case 'C':  case 'D':
			case 'E':  case 'F':  case 'G':  case 'H':
			case 'I':  case 'J':  case 'K':  case 'L':
			case 'M':  case 'N':  case 'O':  case 'P':
			case 'Q':  case 'R':  case 'S':  case 'T':
			case 'U':  case 'V':  case 'W':  case 'X':
			case 'Y':  case 'Z':
			{
				matchRange('A','Z');
				break;
			}
			case '_':
			{
				match('_');
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mALPHA");
		}
	}
	
	protected final void mALPHA_NUM(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mALPHA_NUM");
		_ttype = ALPHA_NUM;
		int _saveIndex;
		try { // debugging
			
			{
			switch ( LA(1)) {
			case 'A':  case 'B':  case 'C':  case 'D':
			case 'E':  case 'F':  case 'G':  case 'H':
			case 'I':  case 'J':  case 'K':  case 'L':
			case 'M':  case 'N':  case 'O':  case 'P':
			case 'Q':  case 'R':  case 'S':  case 'T':
			case 'U':  case 'V':  case 'W':  case 'X':
			case 'Y':  case 'Z':  case '_':  case 'a':
			case 'b':  case 'c':  case 'd':  case 'e':
			case 'f':  case 'g':  case 'h':  case 'i':
			case 'j':  case 'k':  case 'l':  case 'm':
			case 'n':  case 'o':  case 'p':  case 'q':
			case 'r':  case 's':  case 't':  case 'u':
			case 'v':  case 'w':  case 'x':  case 'y':
			case 'z':
			{
				mALPHA(false);
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				mDIGIT(false);
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mALPHA_NUM");
		}
	}
	
	public final void mWS_(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mWS_");
		_ttype = WS_;
		int _saveIndex;
		try { // debugging
			
			{
			switch ( LA(1)) {
			case '\t':
			{
				match('\t');
				break;
			}
			case ' ':
			{
				match(' ');
				break;
			}
			case '\n':
			{
				match('\n');
				newline();
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			_ttype = Token.SKIP;
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mWS_");
		}
	}
	
	public final void mSL_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mSL_COMMENT");
		_ttype = SL_COMMENT;
		int _saveIndex;
		try { // debugging
			
			match("//");
			{
			_loop36:
			do {
				if ((_tokenSet_1.member(LA(1)))) {
					matchNot('\n');
				}
				else {
					break _loop36;
				}
				
			} while (true);
			}
			match('\n');
			_ttype = Token.SKIP; newline ();
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mSL_COMMENT");
		}
	}
	
	public final void mBLOCK_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mBLOCK_COMMENT");
		_ttype = BLOCK_COMMENT;
		int _saveIndex;
		try { // debugging
			
			match("/*");
			{
			_loop40:
			do {
				// nongreedy exit test
				if ((LA(1)=='*') && (LA(2)=='/')) break _loop40;
				if (((LA(1) >= '\u0000' && LA(1) <= '\u007f')) && ((LA(2) >= '\u0000' && LA(2) <= '\u007f'))) {
					{
					switch ( LA(1)) {
					case '/':
					{
						mBLOCK_COMMENT(false);
						break;
					}
					case '\u0000':  case '\u0001':  case '\u0002':  case '\u0003':
					case '\u0004':  case '\u0005':  case '\u0006':  case '\u0007':
					case '\u0008':  case '\t':  case '\u000b':  case '\u000c':
					case '\r':  case '\u000e':  case '\u000f':  case '\u0010':
					case '\u0011':  case '\u0012':  case '\u0013':  case '\u0014':
					case '\u0015':  case '\u0016':  case '\u0017':  case '\u0018':
					case '\u0019':  case '\u001a':  case '\u001b':  case '\u001c':
					case '\u001d':  case '\u001e':  case '\u001f':  case ' ':
					case '!':  case '"':  case '#':  case '$':
					case '%':  case '&':  case '\'':  case '(':
					case ')':  case '*':  case '+':  case ',':
					case '-':  case '.':  case '0':  case '1':
					case '2':  case '3':  case '4':  case '5':
					case '6':  case '7':  case '8':  case '9':
					case ':':  case ';':  case '<':  case '=':
					case '>':  case '?':  case '@':  case 'A':
					case 'B':  case 'C':  case 'D':  case 'E':
					case 'F':  case 'G':  case 'H':  case 'I':
					case 'J':  case 'K':  case 'L':  case 'M':
					case 'N':  case 'O':  case 'P':  case 'Q':
					case 'R':  case 'S':  case 'T':  case 'U':
					case 'V':  case 'W':  case 'X':  case 'Y':
					case 'Z':  case '[':  case '\\':  case ']':
					case '^':  case '_':  case '`':  case 'a':
					case 'b':  case 'c':  case 'd':  case 'e':
					case 'f':  case 'g':  case 'h':  case 'i':
					case 'j':  case 'k':  case 'l':  case 'm':
					case 'n':  case 'o':  case 'p':  case 'q':
					case 'r':  case 's':  case 't':  case 'u':
					case 'v':  case 'w':  case 'x':  case 'y':
					case 'z':  case '{':  case '|':  case '}':
					case '~':  case '\u007f':
					{
						matchNot('\n');
						break;
					}
					case '\n':
					{
						match('\n');
						newline();
						break;
					}
					default:
					{
						throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
					}
					}
					}
				}
				else {
					break _loop40;
				}
				
			} while (true);
			}
			match("*/");
			_ttype = Token.SKIP;
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mBLOCK_COMMENT");
		}
	}
	
	public final void mCHAR_LIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mCHAR_LIT");
		_ttype = CHAR_LIT;
		int _saveIndex;
		try { // debugging
			
			match('\'');
			{
			mCHAR(false);
			}
			match('\'');
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mCHAR_LIT");
		}
	}
	
	protected final void mCHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mCHAR");
		_ttype = CHAR;
		int _saveIndex;
		try { // debugging
			
			{
			switch ( LA(1)) {
			case '\\':
			{
				mESC(false);
				break;
			}
			case '\u0000':  case '\u0001':  case '\u0002':  case '\u0003':
			case '\u0004':  case '\u0005':  case '\u0006':  case '\u0007':
			case '\u0008':  case '\u000b':  case '\u000c':  case '\r':
			case '\u000e':  case '\u000f':  case '\u0010':  case '\u0011':
			case '\u0012':  case '\u0013':  case '\u0014':  case '\u0015':
			case '\u0016':  case '\u0017':  case '\u0018':  case '\u0019':
			case '\u001a':  case '\u001b':  case '\u001c':  case '\u001d':
			case '\u001e':  case '\u001f':  case ' ':  case '!':
			case '#':  case '$':  case '%':  case '&':
			case '(':  case ')':  case '*':  case '+':
			case ',':  case '-':  case '.':  case '/':
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':  case ':':  case ';':
			case '<':  case '=':  case '>':  case '?':
			case '@':  case 'A':  case 'B':  case 'C':
			case 'D':  case 'E':  case 'F':  case 'G':
			case 'H':  case 'I':  case 'J':  case 'K':
			case 'L':  case 'M':  case 'N':  case 'O':
			case 'P':  case 'Q':  case 'R':  case 'S':
			case 'T':  case 'U':  case 'V':  case 'W':
			case 'X':  case 'Y':  case 'Z':  case '[':
			case ']':  case '^':  case '_':  case '`':
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':  case 'g':  case 'h':
			case 'i':  case 'j':  case 'k':  case 'l':
			case 'm':  case 'n':  case 'o':  case 'p':
			case 'q':  case 'r':  case 's':  case 't':
			case 'u':  case 'v':  case 'w':  case 'x':
			case 'y':  case 'z':  case '{':  case '|':
			case '}':  case '~':  case '\u007f':
			{
				{
				match(_tokenSet_2);
				}
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mCHAR");
		}
	}
	
	public final void mSTRING_LIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mSTRING_LIT");
		_ttype = STRING_LIT;
		int _saveIndex;
		try { // debugging
			
			match('"');
			{
			_loop45:
			do {
				if ((_tokenSet_3.member(LA(1)))) {
					mCHAR(false);
				}
				else {
					break _loop45;
				}
				
			} while (true);
			}
			match('"');
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mSTRING_LIT");
		}
	}
	
	public final void mHEX_LIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mHEX_LIT");
		_ttype = HEX_LIT;
		int _saveIndex;
		try { // debugging
			
			match("0x");
			{
			int _cnt48=0;
			_loop48:
			do {
				if ((_tokenSet_4.member(LA(1)))) {
					mHEX_DIGIT(false);
				}
				else {
					if ( _cnt48>=1 ) { break _loop48; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				
				_cnt48++;
			} while (true);
			}
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mHEX_LIT");
		}
	}
	
	protected final void mHEX_DIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mHEX_DIGIT");
		_ttype = HEX_DIGIT;
		int _saveIndex;
		try { // debugging
			
			switch ( LA(1)) {
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				mDIGIT(false);
				break;
			}
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':
			{
				matchRange('a','f');
				break;
			}
			case 'A':  case 'B':  case 'C':  case 'D':
			case 'E':  case 'F':
			{
				matchRange('A','F');
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mHEX_DIGIT");
		}
	}
	
	public final void mDEC_LIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mDEC_LIT");
		_ttype = DEC_LIT;
		int _saveIndex;
		try { // debugging
			
			{
			int _cnt51=0;
			_loop51:
			do {
				if (((LA(1) >= '0' && LA(1) <= '9'))) {
					mDIGIT(false);
				}
				else {
					if ( _cnt51>=1 ) { break _loop51; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				
				_cnt51++;
			} while (true);
			}
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mDEC_LIT");
		}
	}
	
	protected final void mDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mDIGIT");
		_ttype = DIGIT;
		int _saveIndex;
		try { // debugging
			
			matchRange('0','9');
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mDIGIT");
		}
	}
	
	protected final void mESC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		traceIn("mESC");
		_ttype = ESC;
		int _saveIndex;
		try { // debugging
			
			match('\\');
			{
			switch ( LA(1)) {
			case 'n':
			{
				match('n');
				break;
			}
			case '"':
			{
				match('"');
				break;
			}
			case '\'':
			{
				match('\'');
				break;
			}
			case 't':
			{
				match('t');
				break;
			}
			case '\\':
			{
				match('\\');
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		} finally { // debugging
			traceOut("mESC");
		}
	}
	
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 287948901175001088L, 576460745995190270L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { -1025L, -1L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { -566935684609L, -268435457L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { -566935684609L, -1L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 287948901175001088L, 541165879422L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	
	}
