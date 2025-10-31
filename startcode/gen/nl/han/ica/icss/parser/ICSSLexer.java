// Generated from C:/Users/ricks/OneDrive/Bureaublad/Alles/SCHOOL/BioscoopKaartjes/icss2022-sep/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.13.2
package nl.han.ica.icss.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class ICSSLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IF=1, ELSE=2, TRUE=3, FALSE=4, PIXELSIZE=5, PERCENTAGE=6, SCALAR=7, COLOR=8, 
		ID_IDENT=9, CLASS_IDENT=10, LOWER_IDENT=11, CAPITAL_IDENT=12, OPEN_BRACE=13, 
		CLOSE_BRACE=14, BOX_BRACKET_OPEN=15, BOX_BRACKET_CLOSE=16, SEMICOLON=17, 
		COLON=18, PLUS=19, MIN=20, MUL=21, ASSIGNMENT_OPERATOR=22, LINE_COMMENT=23, 
		COMMENT=24, WS=25;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"IF", "ELSE", "TRUE", "FALSE", "PIXELSIZE", "PERCENTAGE", "SCALAR", "COLOR", 
			"ID_IDENT", "CLASS_IDENT", "LOWER_IDENT", "CAPITAL_IDENT", "OPEN_BRACE", 
			"CLOSE_BRACE", "BOX_BRACKET_OPEN", "BOX_BRACKET_CLOSE", "SEMICOLON", 
			"COLON", "PLUS", "MIN", "MUL", "ASSIGNMENT_OPERATOR", "LINE_COMMENT", 
			"COMMENT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'else'", "'TRUE'", "'FALSE'", null, null, null, null, 
			null, null, null, null, "'{'", "'}'", "'['", "']'", "';'", "':'", "'+'", 
			"'-'", "'*'", "':='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "IF", "ELSE", "TRUE", "FALSE", "PIXELSIZE", "PERCENTAGE", "SCALAR", 
			"COLOR", "ID_IDENT", "CLASS_IDENT", "LOWER_IDENT", "CAPITAL_IDENT", "OPEN_BRACE", 
			"CLOSE_BRACE", "BOX_BRACKET_OPEN", "BOX_BRACKET_CLOSE", "SEMICOLON", 
			"COLON", "PLUS", "MIN", "MUL", "ASSIGNMENT_OPERATOR", "LINE_COMMENT", 
			"COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public ICSSLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ICSS.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0019\u00b1\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0004\u0004H\b\u0004"+
		"\u000b\u0004\f\u0004I\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0004\u0005P\b\u0005\u000b\u0005\f\u0005Q\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0004\u0006W\b\u0006\u000b\u0006\f\u0006X\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\b\u0001\b\u0004\be\b\b\u000b\b\f\bf\u0001\t\u0001\t\u0004\tk\b"+
		"\t\u000b\t\f\tl\u0001\n\u0001\n\u0005\nq\b\n\n\n\f\nt\t\n\u0001\u000b"+
		"\u0001\u000b\u0005\u000bx\b\u000b\n\u000b\f\u000b{\t\u000b\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0005\u0016\u0096"+
		"\b\u0016\n\u0016\f\u0016\u0099\t\u0016\u0001\u0016\u0001\u0016\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0005\u0017\u00a1\b\u0017\n\u0017"+
		"\f\u0017\u00a4\t\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0018\u0004\u0018\u00ac\b\u0018\u000b\u0018\f\u0018"+
		"\u00ad\u0001\u0018\u0001\u0018\u0001\u00a2\u0000\u0019\u0001\u0001\u0003"+
		"\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011"+
		"\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010"+
		"!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017/\u00181\u0019\u0001"+
		"\u0000\b\u0001\u000009\u0003\u000009AFaf\u0003\u0000--09az\u0001\u0000"+
		"az\u0001\u0000AZ\u0004\u000009AZ__az\u0002\u0000\n\n\r\r\u0003\u0000\t"+
		"\n\r\r  \u00ba\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000"+
		"\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000"+
		"\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000"+
		"\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000"+
		"\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000"+
		"\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000"+
		"\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000"+
		"\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000"+
		"\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%"+
		"\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001"+
		"\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000"+
		"\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u0001"+
		"3\u0001\u0000\u0000\u0000\u00036\u0001\u0000\u0000\u0000\u0005;\u0001"+
		"\u0000\u0000\u0000\u0007@\u0001\u0000\u0000\u0000\tG\u0001\u0000\u0000"+
		"\u0000\u000bO\u0001\u0000\u0000\u0000\rV\u0001\u0000\u0000\u0000\u000f"+
		"Z\u0001\u0000\u0000\u0000\u0011b\u0001\u0000\u0000\u0000\u0013h\u0001"+
		"\u0000\u0000\u0000\u0015n\u0001\u0000\u0000\u0000\u0017u\u0001\u0000\u0000"+
		"\u0000\u0019|\u0001\u0000\u0000\u0000\u001b~\u0001\u0000\u0000\u0000\u001d"+
		"\u0080\u0001\u0000\u0000\u0000\u001f\u0082\u0001\u0000\u0000\u0000!\u0084"+
		"\u0001\u0000\u0000\u0000#\u0086\u0001\u0000\u0000\u0000%\u0088\u0001\u0000"+
		"\u0000\u0000\'\u008a\u0001\u0000\u0000\u0000)\u008c\u0001\u0000\u0000"+
		"\u0000+\u008e\u0001\u0000\u0000\u0000-\u0091\u0001\u0000\u0000\u0000/"+
		"\u009c\u0001\u0000\u0000\u00001\u00ab\u0001\u0000\u0000\u000034\u0005"+
		"i\u0000\u000045\u0005f\u0000\u00005\u0002\u0001\u0000\u0000\u000067\u0005"+
		"e\u0000\u000078\u0005l\u0000\u000089\u0005s\u0000\u00009:\u0005e\u0000"+
		"\u0000:\u0004\u0001\u0000\u0000\u0000;<\u0005T\u0000\u0000<=\u0005R\u0000"+
		"\u0000=>\u0005U\u0000\u0000>?\u0005E\u0000\u0000?\u0006\u0001\u0000\u0000"+
		"\u0000@A\u0005F\u0000\u0000AB\u0005A\u0000\u0000BC\u0005L\u0000\u0000"+
		"CD\u0005S\u0000\u0000DE\u0005E\u0000\u0000E\b\u0001\u0000\u0000\u0000"+
		"FH\u0007\u0000\u0000\u0000GF\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000"+
		"\u0000IG\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000JK\u0001\u0000"+
		"\u0000\u0000KL\u0005p\u0000\u0000LM\u0005x\u0000\u0000M\n\u0001\u0000"+
		"\u0000\u0000NP\u0007\u0000\u0000\u0000ON\u0001\u0000\u0000\u0000PQ\u0001"+
		"\u0000\u0000\u0000QO\u0001\u0000\u0000\u0000QR\u0001\u0000\u0000\u0000"+
		"RS\u0001\u0000\u0000\u0000ST\u0005%\u0000\u0000T\f\u0001\u0000\u0000\u0000"+
		"UW\u0007\u0000\u0000\u0000VU\u0001\u0000\u0000\u0000WX\u0001\u0000\u0000"+
		"\u0000XV\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000Y\u000e\u0001"+
		"\u0000\u0000\u0000Z[\u0005#\u0000\u0000[\\\u0007\u0001\u0000\u0000\\]"+
		"\u0007\u0001\u0000\u0000]^\u0007\u0001\u0000\u0000^_\u0007\u0001\u0000"+
		"\u0000_`\u0007\u0001\u0000\u0000`a\u0007\u0001\u0000\u0000a\u0010\u0001"+
		"\u0000\u0000\u0000bd\u0005#\u0000\u0000ce\u0007\u0002\u0000\u0000dc\u0001"+
		"\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000fd\u0001\u0000\u0000\u0000"+
		"fg\u0001\u0000\u0000\u0000g\u0012\u0001\u0000\u0000\u0000hj\u0005.\u0000"+
		"\u0000ik\u0007\u0002\u0000\u0000ji\u0001\u0000\u0000\u0000kl\u0001\u0000"+
		"\u0000\u0000lj\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000m\u0014"+
		"\u0001\u0000\u0000\u0000nr\u0007\u0003\u0000\u0000oq\u0007\u0002\u0000"+
		"\u0000po\u0001\u0000\u0000\u0000qt\u0001\u0000\u0000\u0000rp\u0001\u0000"+
		"\u0000\u0000rs\u0001\u0000\u0000\u0000s\u0016\u0001\u0000\u0000\u0000"+
		"tr\u0001\u0000\u0000\u0000uy\u0007\u0004\u0000\u0000vx\u0007\u0005\u0000"+
		"\u0000wv\u0001\u0000\u0000\u0000x{\u0001\u0000\u0000\u0000yw\u0001\u0000"+
		"\u0000\u0000yz\u0001\u0000\u0000\u0000z\u0018\u0001\u0000\u0000\u0000"+
		"{y\u0001\u0000\u0000\u0000|}\u0005{\u0000\u0000}\u001a\u0001\u0000\u0000"+
		"\u0000~\u007f\u0005}\u0000\u0000\u007f\u001c\u0001\u0000\u0000\u0000\u0080"+
		"\u0081\u0005[\u0000\u0000\u0081\u001e\u0001\u0000\u0000\u0000\u0082\u0083"+
		"\u0005]\u0000\u0000\u0083 \u0001\u0000\u0000\u0000\u0084\u0085\u0005;"+
		"\u0000\u0000\u0085\"\u0001\u0000\u0000\u0000\u0086\u0087\u0005:\u0000"+
		"\u0000\u0087$\u0001\u0000\u0000\u0000\u0088\u0089\u0005+\u0000\u0000\u0089"+
		"&\u0001\u0000\u0000\u0000\u008a\u008b\u0005-\u0000\u0000\u008b(\u0001"+
		"\u0000\u0000\u0000\u008c\u008d\u0005*\u0000\u0000\u008d*\u0001\u0000\u0000"+
		"\u0000\u008e\u008f\u0005:\u0000\u0000\u008f\u0090\u0005=\u0000\u0000\u0090"+
		",\u0001\u0000\u0000\u0000\u0091\u0092\u0005/\u0000\u0000\u0092\u0093\u0005"+
		"/\u0000\u0000\u0093\u0097\u0001\u0000\u0000\u0000\u0094\u0096\b\u0006"+
		"\u0000\u0000\u0095\u0094\u0001\u0000\u0000\u0000\u0096\u0099\u0001\u0000"+
		"\u0000\u0000\u0097\u0095\u0001\u0000\u0000\u0000\u0097\u0098\u0001\u0000"+
		"\u0000\u0000\u0098\u009a\u0001\u0000\u0000\u0000\u0099\u0097\u0001\u0000"+
		"\u0000\u0000\u009a\u009b\u0006\u0016\u0000\u0000\u009b.\u0001\u0000\u0000"+
		"\u0000\u009c\u009d\u0005/\u0000\u0000\u009d\u009e\u0005*\u0000\u0000\u009e"+
		"\u00a2\u0001\u0000\u0000\u0000\u009f\u00a1\t\u0000\u0000\u0000\u00a0\u009f"+
		"\u0001\u0000\u0000\u0000\u00a1\u00a4\u0001\u0000\u0000\u0000\u00a2\u00a3"+
		"\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000\u00a3\u00a5"+
		"\u0001\u0000\u0000\u0000\u00a4\u00a2\u0001\u0000\u0000\u0000\u00a5\u00a6"+
		"\u0005*\u0000\u0000\u00a6\u00a7\u0005/\u0000\u0000\u00a7\u00a8\u0001\u0000"+
		"\u0000\u0000\u00a8\u00a9\u0006\u0017\u0000\u0000\u00a90\u0001\u0000\u0000"+
		"\u0000\u00aa\u00ac\u0007\u0007\u0000\u0000\u00ab\u00aa\u0001\u0000\u0000"+
		"\u0000\u00ac\u00ad\u0001\u0000\u0000\u0000\u00ad\u00ab\u0001\u0000\u0000"+
		"\u0000\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae\u00af\u0001\u0000\u0000"+
		"\u0000\u00af\u00b0\u0006\u0018\u0000\u0000\u00b02\u0001\u0000\u0000\u0000"+
		"\u000b\u0000IQXflry\u0097\u00a2\u00ad\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}