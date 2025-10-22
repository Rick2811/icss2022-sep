// Generated from C:/Users/ricks/OneDrive/Bureaublad/Alles/SCHOOL/BioscoopKaartjes/icss2022-sep/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.13.2
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
		ID_IDENT=9, CLASS_IDENT=10, LOWER_IDENT=11, CAPITAL_IDENT=12, LPAREN=13, 
		RPAREN=14, BOX_BRACKET_OPEN=15, BOX_BRACKET_CLOSE=16, OPEN_BRACE=17, CLOSE_BRACE=18, 
		SEMICOLON=19, COLON=20, PLUS=21, MIN=22, MUL=23, ASSIGNMENT_OPERATOR=24, 
		LINE_COMMENT=25, COMMENT=26, WS=27;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"IF", "ELSE", "TRUE", "FALSE", "PIXELSIZE", "PERCENTAGE", "SCALAR", "COLOR", 
			"ID_IDENT", "CLASS_IDENT", "LOWER_IDENT", "CAPITAL_IDENT", "LPAREN", 
			"RPAREN", "BOX_BRACKET_OPEN", "BOX_BRACKET_CLOSE", "OPEN_BRACE", "CLOSE_BRACE", 
			"SEMICOLON", "COLON", "PLUS", "MIN", "MUL", "ASSIGNMENT_OPERATOR", "LINE_COMMENT", 
			"COMMENT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'else'", "'TRUE'", "'FALSE'", null, null, null, null, 
			null, null, null, null, "'('", "')'", "'['", "']'", "'{'", "'}'", "';'", 
			"':'", "'+'", "'-'", "'*'", "':='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "IF", "ELSE", "TRUE", "FALSE", "PIXELSIZE", "PERCENTAGE", "SCALAR", 
			"COLOR", "ID_IDENT", "CLASS_IDENT", "LOWER_IDENT", "CAPITAL_IDENT", "LPAREN", 
			"RPAREN", "BOX_BRACKET_OPEN", "BOX_BRACKET_CLOSE", "OPEN_BRACE", "CLOSE_BRACE", 
			"SEMICOLON", "COLON", "PLUS", "MIN", "MUL", "ASSIGNMENT_OPERATOR", "LINE_COMMENT", 
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

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 7:
			COLOR_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void COLOR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			6
			break;
		}
	}

	public static final String _serializedATN =
		"\u0004\u0000\u001b\u00b5\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0004\u0004L\b\u0004\u000b\u0004\f\u0004M\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0004\u0005T\b\u0005\u000b"+
		"\u0005\f\u0005U\u0001\u0005\u0001\u0005\u0001\u0006\u0004\u0006[\b\u0006"+
		"\u000b\u0006\f\u0006\\\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\b\u0001\b\u0004\be\b\b\u000b\b\f\bf\u0001\t\u0001\t\u0004\tk\b"+
		"\t\u000b\t\f\tl\u0001\n\u0001\n\u0005\nq\b\n\n\n\f\nt\t\n\u0001\u000b"+
		"\u0001\u000b\u0005\u000bx\b\u000b\n\u000b\f\u000b{\t\u000b\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001"+
		"\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u009a\b\u0018\n\u0018\f\u0018"+
		"\u009d\t\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0005\u0019\u00a5\b\u0019\n\u0019\f\u0019\u00a8\t\u0019\u0001"+
		"\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0004"+
		"\u001a\u00b0\b\u001a\u000b\u001a\f\u001a\u00b1\u0001\u001a\u0001\u001a"+
		"\u0001\u00a6\u0000\u001b\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004"+
		"\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017"+
		"\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'"+
		"\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b\u0001\u0000\b"+
		"\u0001\u000009\u0003\u000009AFaf\u0003\u0000--09az\u0001\u0000az\u0001"+
		"\u0000AZ\u0004\u000009AZ__az\u0002\u0000\n\n\r\r\u0003\u0000\t\n\r\r "+
		" \u00be\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000"+
		"\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000"+
		"\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000"+
		"\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000"+
		"\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000"+
		"\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000"+
		"\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000"+
		"\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000"+
		"!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001"+
		"\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001\u0000"+
		"\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000\u0000"+
		"\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u00003"+
		"\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00017\u0001\u0000"+
		"\u0000\u0000\u0003:\u0001\u0000\u0000\u0000\u0005?\u0001\u0000\u0000\u0000"+
		"\u0007D\u0001\u0000\u0000\u0000\tK\u0001\u0000\u0000\u0000\u000bS\u0001"+
		"\u0000\u0000\u0000\rZ\u0001\u0000\u0000\u0000\u000f^\u0001\u0000\u0000"+
		"\u0000\u0011b\u0001\u0000\u0000\u0000\u0013h\u0001\u0000\u0000\u0000\u0015"+
		"n\u0001\u0000\u0000\u0000\u0017u\u0001\u0000\u0000\u0000\u0019|\u0001"+
		"\u0000\u0000\u0000\u001b~\u0001\u0000\u0000\u0000\u001d\u0080\u0001\u0000"+
		"\u0000\u0000\u001f\u0082\u0001\u0000\u0000\u0000!\u0084\u0001\u0000\u0000"+
		"\u0000#\u0086\u0001\u0000\u0000\u0000%\u0088\u0001\u0000\u0000\u0000\'"+
		"\u008a\u0001\u0000\u0000\u0000)\u008c\u0001\u0000\u0000\u0000+\u008e\u0001"+
		"\u0000\u0000\u0000-\u0090\u0001\u0000\u0000\u0000/\u0092\u0001\u0000\u0000"+
		"\u00001\u0095\u0001\u0000\u0000\u00003\u00a0\u0001\u0000\u0000\u00005"+
		"\u00af\u0001\u0000\u0000\u000078\u0005i\u0000\u000089\u0005f\u0000\u0000"+
		"9\u0002\u0001\u0000\u0000\u0000:;\u0005e\u0000\u0000;<\u0005l\u0000\u0000"+
		"<=\u0005s\u0000\u0000=>\u0005e\u0000\u0000>\u0004\u0001\u0000\u0000\u0000"+
		"?@\u0005T\u0000\u0000@A\u0005R\u0000\u0000AB\u0005U\u0000\u0000BC\u0005"+
		"E\u0000\u0000C\u0006\u0001\u0000\u0000\u0000DE\u0005F\u0000\u0000EF\u0005"+
		"A\u0000\u0000FG\u0005L\u0000\u0000GH\u0005S\u0000\u0000HI\u0005E\u0000"+
		"\u0000I\b\u0001\u0000\u0000\u0000JL\u0007\u0000\u0000\u0000KJ\u0001\u0000"+
		"\u0000\u0000LM\u0001\u0000\u0000\u0000MK\u0001\u0000\u0000\u0000MN\u0001"+
		"\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000OP\u0005p\u0000\u0000PQ\u0005"+
		"x\u0000\u0000Q\n\u0001\u0000\u0000\u0000RT\u0007\u0000\u0000\u0000SR\u0001"+
		"\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000US\u0001\u0000\u0000\u0000"+
		"UV\u0001\u0000\u0000\u0000VW\u0001\u0000\u0000\u0000WX\u0005%\u0000\u0000"+
		"X\f\u0001\u0000\u0000\u0000Y[\u0007\u0000\u0000\u0000ZY\u0001\u0000\u0000"+
		"\u0000[\\\u0001\u0000\u0000\u0000\\Z\u0001\u0000\u0000\u0000\\]\u0001"+
		"\u0000\u0000\u0000]\u000e\u0001\u0000\u0000\u0000^_\u0005#\u0000\u0000"+
		"_`\u0007\u0001\u0000\u0000`a\u0006\u0007\u0000\u0000a\u0010\u0001\u0000"+
		"\u0000\u0000bd\u0005#\u0000\u0000ce\u0007\u0002\u0000\u0000dc\u0001\u0000"+
		"\u0000\u0000ef\u0001\u0000\u0000\u0000fd\u0001\u0000\u0000\u0000fg\u0001"+
		"\u0000\u0000\u0000g\u0012\u0001\u0000\u0000\u0000hj\u0005.\u0000\u0000"+
		"ik\u0007\u0002\u0000\u0000ji\u0001\u0000\u0000\u0000kl\u0001\u0000\u0000"+
		"\u0000lj\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000m\u0014\u0001"+
		"\u0000\u0000\u0000nr\u0007\u0003\u0000\u0000oq\u0007\u0002\u0000\u0000"+
		"po\u0001\u0000\u0000\u0000qt\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000"+
		"\u0000rs\u0001\u0000\u0000\u0000s\u0016\u0001\u0000\u0000\u0000tr\u0001"+
		"\u0000\u0000\u0000uy\u0007\u0004\u0000\u0000vx\u0007\u0005\u0000\u0000"+
		"wv\u0001\u0000\u0000\u0000x{\u0001\u0000\u0000\u0000yw\u0001\u0000\u0000"+
		"\u0000yz\u0001\u0000\u0000\u0000z\u0018\u0001\u0000\u0000\u0000{y\u0001"+
		"\u0000\u0000\u0000|}\u0005(\u0000\u0000}\u001a\u0001\u0000\u0000\u0000"+
		"~\u007f\u0005)\u0000\u0000\u007f\u001c\u0001\u0000\u0000\u0000\u0080\u0081"+
		"\u0005[\u0000\u0000\u0081\u001e\u0001\u0000\u0000\u0000\u0082\u0083\u0005"+
		"]\u0000\u0000\u0083 \u0001\u0000\u0000\u0000\u0084\u0085\u0005{\u0000"+
		"\u0000\u0085\"\u0001\u0000\u0000\u0000\u0086\u0087\u0005}\u0000\u0000"+
		"\u0087$\u0001\u0000\u0000\u0000\u0088\u0089\u0005;\u0000\u0000\u0089&"+
		"\u0001\u0000\u0000\u0000\u008a\u008b\u0005:\u0000\u0000\u008b(\u0001\u0000"+
		"\u0000\u0000\u008c\u008d\u0005+\u0000\u0000\u008d*\u0001\u0000\u0000\u0000"+
		"\u008e\u008f\u0005-\u0000\u0000\u008f,\u0001\u0000\u0000\u0000\u0090\u0091"+
		"\u0005*\u0000\u0000\u0091.\u0001\u0000\u0000\u0000\u0092\u0093\u0005:"+
		"\u0000\u0000\u0093\u0094\u0005=\u0000\u0000\u00940\u0001\u0000\u0000\u0000"+
		"\u0095\u0096\u0005/\u0000\u0000\u0096\u0097\u0005/\u0000\u0000\u0097\u009b"+
		"\u0001\u0000\u0000\u0000\u0098\u009a\b\u0006\u0000\u0000\u0099\u0098\u0001"+
		"\u0000\u0000\u0000\u009a\u009d\u0001\u0000\u0000\u0000\u009b\u0099\u0001"+
		"\u0000\u0000\u0000\u009b\u009c\u0001\u0000\u0000\u0000\u009c\u009e\u0001"+
		"\u0000\u0000\u0000\u009d\u009b\u0001\u0000\u0000\u0000\u009e\u009f\u0006"+
		"\u0018\u0001\u0000\u009f2\u0001\u0000\u0000\u0000\u00a0\u00a1\u0005/\u0000"+
		"\u0000\u00a1\u00a2\u0005*\u0000\u0000\u00a2\u00a6\u0001\u0000\u0000\u0000"+
		"\u00a3\u00a5\t\u0000\u0000\u0000\u00a4\u00a3\u0001\u0000\u0000\u0000\u00a5"+
		"\u00a8\u0001\u0000\u0000\u0000\u00a6\u00a7\u0001\u0000\u0000\u0000\u00a6"+
		"\u00a4\u0001\u0000\u0000\u0000\u00a7\u00a9\u0001\u0000\u0000\u0000\u00a8"+
		"\u00a6\u0001\u0000\u0000\u0000\u00a9\u00aa\u0005*\u0000\u0000\u00aa\u00ab"+
		"\u0005/\u0000\u0000\u00ab\u00ac\u0001\u0000\u0000\u0000\u00ac\u00ad\u0006"+
		"\u0019\u0001\u0000\u00ad4\u0001\u0000\u0000\u0000\u00ae\u00b0\u0007\u0007"+
		"\u0000\u0000\u00af\u00ae\u0001\u0000\u0000\u0000\u00b0\u00b1\u0001\u0000"+
		"\u0000\u0000\u00b1\u00af\u0001\u0000\u0000\u0000\u00b1\u00b2\u0001\u0000"+
		"\u0000\u0000\u00b2\u00b3\u0001\u0000\u0000\u0000\u00b3\u00b4\u0006\u001a"+
		"\u0001\u0000\u00b46\u0001\u0000\u0000\u0000\u000b\u0000MU\\flry\u009b"+
		"\u00a6\u00b1\u0002\u0001\u0007\u0000\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}