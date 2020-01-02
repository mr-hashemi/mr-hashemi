// Generated from language/src/main/java/ninja/soroosh/hashem/lang/parser/HashemLanguage.g4 by ANTLR 4.7.1
package ninja.soroosh.hashem.lang.parser;

// DO NOT MODIFY - generated from HashemLanguage.g4

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HashemLanguageLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, WS=33, COMMENT=34, LINE_COMMENT=35, UNTERMINATED_STRING_LITERAL=36, 
		STRING_LITERAL=37, IDENTIFIER=38, NUMERIC_LITERAL=39;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "WS", "COMMENT", 
		"LINE_COMMENT", "LETTER", "NON_ZERO_DIGIT", "DIGIT", "HEX_DIGIT", "OCT_DIGIT", 
		"BINARY_DIGIT", "TAB", "STRING_CHAR", "UNTERMINATED_STRING_LITERAL", "STRING_LITERAL", 
		"IDENTIFIER", "NUMERIC_LITERAL"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'bebin'", "'('", "','", "')'", "'{'", "'}'", "'khob'", "';'", "'badi'", 
		"'debugger'", "'ta'", "') bood'", "'age'", "'na?'", "'bede'", "'||'", 
		"'&&'", "'<'", "'<='", "'>'", "'>='", "'=='", "'!='", "'+'", "'-'", "'*'", 
		"'/'", "'%'", "'='", "'.'", "'['", "']'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, "WS", "COMMENT", 
		"LINE_COMMENT", "UNTERMINATED_STRING_LITERAL", "STRING_LITERAL", "IDENTIFIER", 
		"NUMERIC_LITERAL"
	};
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


	public HashemLanguageLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "HashemLanguage.g4"; }

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
		case 44:
			STRING_LITERAL_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void STRING_LITERAL_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			setText(getText().substring(2, getText().length()-2));
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2)\u0129\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3"+
		"\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n"+
		"\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20\3\20"+
		"\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\24"+
		"\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\32"+
		"\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3"+
		"\"\6\"\u00c7\n\"\r\"\16\"\u00c8\3\"\3\"\3#\3#\3#\3#\7#\u00d1\n#\f#\16"+
		"#\u00d4\13#\3#\3#\3#\3#\3#\3$\3$\3$\3$\7$\u00df\n$\f$\16$\u00e2\13$\3"+
		"$\3$\3%\5%\u00e7\n%\3&\3&\3\'\3\'\3(\5(\u00ee\n(\3)\3)\3*\3*\3+\3+\3,"+
		"\3,\3-\3-\3-\3-\3-\5-\u00fd\n-\7-\u00ff\n-\f-\16-\u0102\13-\3.\3.\3.\3"+
		".\3.\7.\u0109\n.\f.\16.\u010c\13.\3.\3.\3.\3.\3.\3.\3.\3.\5.\u0116\n."+
		"\3/\3/\3/\7/\u011b\n/\f/\16/\u011e\13/\3\60\3\60\3\60\7\60\u0123\n\60"+
		"\f\60\16\60\u0126\13\60\5\60\u0128\n\60\4\u00d2\u010a\2\61\3\3\5\4\7\5"+
		"\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23"+
		"%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G"+
		"%I\2K\2M\2O\2Q\2S\2U\2W\2Y&[\'](_)\3\2\n\5\2\13\f\16\17\"\"\4\2\f\f\17"+
		"\17\6\2&&C\\aac|\3\2\63;\3\2\62;\5\2\62;CHch\3\2\629\6\2\f\f\17\17$$^"+
		"^\2\u012c\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2"+
		"\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2"+
		"\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2"+
		"\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2"+
		"\2G\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\3a\3\2\2\2\5g"+
		"\3\2\2\2\7i\3\2\2\2\tk\3\2\2\2\13m\3\2\2\2\ro\3\2\2\2\17q\3\2\2\2\21v"+
		"\3\2\2\2\23x\3\2\2\2\25}\3\2\2\2\27\u0086\3\2\2\2\31\u0089\3\2\2\2\33"+
		"\u0090\3\2\2\2\35\u0094\3\2\2\2\37\u0098\3\2\2\2!\u009d\3\2\2\2#\u00a0"+
		"\3\2\2\2%\u00a3\3\2\2\2\'\u00a5\3\2\2\2)\u00a8\3\2\2\2+\u00aa\3\2\2\2"+
		"-\u00ad\3\2\2\2/\u00b0\3\2\2\2\61\u00b3\3\2\2\2\63\u00b5\3\2\2\2\65\u00b7"+
		"\3\2\2\2\67\u00b9\3\2\2\29\u00bb\3\2\2\2;\u00bd\3\2\2\2=\u00bf\3\2\2\2"+
		"?\u00c1\3\2\2\2A\u00c3\3\2\2\2C\u00c6\3\2\2\2E\u00cc\3\2\2\2G\u00da\3"+
		"\2\2\2I\u00e6\3\2\2\2K\u00e8\3\2\2\2M\u00ea\3\2\2\2O\u00ed\3\2\2\2Q\u00ef"+
		"\3\2\2\2S\u00f1\3\2\2\2U\u00f3\3\2\2\2W\u00f5\3\2\2\2Y\u00f7\3\2\2\2["+
		"\u0115\3\2\2\2]\u0117\3\2\2\2_\u0127\3\2\2\2ab\7d\2\2bc\7g\2\2cd\7d\2"+
		"\2de\7k\2\2ef\7p\2\2f\4\3\2\2\2gh\7*\2\2h\6\3\2\2\2ij\7.\2\2j\b\3\2\2"+
		"\2kl\7+\2\2l\n\3\2\2\2mn\7}\2\2n\f\3\2\2\2op\7\177\2\2p\16\3\2\2\2qr\7"+
		"m\2\2rs\7j\2\2st\7q\2\2tu\7d\2\2u\20\3\2\2\2vw\7=\2\2w\22\3\2\2\2xy\7"+
		"d\2\2yz\7c\2\2z{\7f\2\2{|\7k\2\2|\24\3\2\2\2}~\7f\2\2~\177\7g\2\2\177"+
		"\u0080\7d\2\2\u0080\u0081\7w\2\2\u0081\u0082\7i\2\2\u0082\u0083\7i\2\2"+
		"\u0083\u0084\7g\2\2\u0084\u0085\7t\2\2\u0085\26\3\2\2\2\u0086\u0087\7"+
		"v\2\2\u0087\u0088\7c\2\2\u0088\30\3\2\2\2\u0089\u008a\7+\2\2\u008a\u008b"+
		"\7\"\2\2\u008b\u008c\7d\2\2\u008c\u008d\7q\2\2\u008d\u008e\7q\2\2\u008e"+
		"\u008f\7f\2\2\u008f\32\3\2\2\2\u0090\u0091\7c\2\2\u0091\u0092\7i\2\2\u0092"+
		"\u0093\7g\2\2\u0093\34\3\2\2\2\u0094\u0095\7p\2\2\u0095\u0096\7c\2\2\u0096"+
		"\u0097\7A\2\2\u0097\36\3\2\2\2\u0098\u0099\7d\2\2\u0099\u009a\7g\2\2\u009a"+
		"\u009b\7f\2\2\u009b\u009c\7g\2\2\u009c \3\2\2\2\u009d\u009e\7~\2\2\u009e"+
		"\u009f\7~\2\2\u009f\"\3\2\2\2\u00a0\u00a1\7(\2\2\u00a1\u00a2\7(\2\2\u00a2"+
		"$\3\2\2\2\u00a3\u00a4\7>\2\2\u00a4&\3\2\2\2\u00a5\u00a6\7>\2\2\u00a6\u00a7"+
		"\7?\2\2\u00a7(\3\2\2\2\u00a8\u00a9\7@\2\2\u00a9*\3\2\2\2\u00aa\u00ab\7"+
		"@\2\2\u00ab\u00ac\7?\2\2\u00ac,\3\2\2\2\u00ad\u00ae\7?\2\2\u00ae\u00af"+
		"\7?\2\2\u00af.\3\2\2\2\u00b0\u00b1\7#\2\2\u00b1\u00b2\7?\2\2\u00b2\60"+
		"\3\2\2\2\u00b3\u00b4\7-\2\2\u00b4\62\3\2\2\2\u00b5\u00b6\7/\2\2\u00b6"+
		"\64\3\2\2\2\u00b7\u00b8\7,\2\2\u00b8\66\3\2\2\2\u00b9\u00ba\7\61\2\2\u00ba"+
		"8\3\2\2\2\u00bb\u00bc\7\'\2\2\u00bc:\3\2\2\2\u00bd\u00be\7?\2\2\u00be"+
		"<\3\2\2\2\u00bf\u00c0\7\60\2\2\u00c0>\3\2\2\2\u00c1\u00c2\7]\2\2\u00c2"+
		"@\3\2\2\2\u00c3\u00c4\7_\2\2\u00c4B\3\2\2\2\u00c5\u00c7\t\2\2\2\u00c6"+
		"\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00c6\3\2\2\2\u00c8\u00c9\3\2"+
		"\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cb\b\"\2\2\u00cbD\3\2\2\2\u00cc\u00cd"+
		"\7\61\2\2\u00cd\u00ce\7,\2\2\u00ce\u00d2\3\2\2\2\u00cf\u00d1\13\2\2\2"+
		"\u00d0\u00cf\3\2\2\2\u00d1\u00d4\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d2\u00d0"+
		"\3\2\2\2\u00d3\u00d5\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d5\u00d6\7,\2\2\u00d6"+
		"\u00d7\7\61\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00d9\b#\2\2\u00d9F\3\2\2\2"+
		"\u00da\u00db\7\61\2\2\u00db\u00dc\7\61\2\2\u00dc\u00e0\3\2\2\2\u00dd\u00df"+
		"\n\3\2\2\u00de\u00dd\3\2\2\2\u00df\u00e2\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0"+
		"\u00e1\3\2\2\2\u00e1\u00e3\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e3\u00e4\b$"+
		"\2\2\u00e4H\3\2\2\2\u00e5\u00e7\t\4\2\2\u00e6\u00e5\3\2\2\2\u00e7J\3\2"+
		"\2\2\u00e8\u00e9\t\5\2\2\u00e9L\3\2\2\2\u00ea\u00eb\t\6\2\2\u00ebN\3\2"+
		"\2\2\u00ec\u00ee\t\7\2\2\u00ed\u00ec\3\2\2\2\u00eeP\3\2\2\2\u00ef\u00f0"+
		"\t\b\2\2\u00f0R\3\2\2\2\u00f1\u00f2\4\62\63\2\u00f2T\3\2\2\2\u00f3\u00f4"+
		"\7\13\2\2\u00f4V\3\2\2\2\u00f5\u00f6\n\3\2\2\u00f6X\3\2\2\2\u00f7\u0100"+
		"\7$\2\2\u00f8\u00ff\n\t\2\2\u00f9\u00fc\7^\2\2\u00fa\u00fd\13\2\2\2\u00fb"+
		"\u00fd\7\2\2\3\u00fc\u00fa\3\2\2\2\u00fc\u00fb\3\2\2\2\u00fd\u00ff\3\2"+
		"\2\2\u00fe\u00f8\3\2\2\2\u00fe\u00f9\3\2\2\2\u00ff\u0102\3\2\2\2\u0100"+
		"\u00fe\3\2\2\2\u0100\u0101\3\2\2\2\u0101Z\3\2\2\2\u0102\u0100\3\2\2\2"+
		"\u0103\u0104\7$\2\2\u0104\u0105\7$\2\2\u0105\u0106\7$\2\2\u0106\u010a"+
		"\3\2\2\2\u0107\u0109\13\2\2\2\u0108\u0107\3\2\2\2\u0109\u010c\3\2\2\2"+
		"\u010a\u010b\3\2\2\2\u010a\u0108\3\2\2\2\u010b\u010d\3\2\2\2\u010c\u010a"+
		"\3\2\2\2\u010d\u010e\7$\2\2\u010e\u010f\7$\2\2\u010f\u0110\7$\2\2\u0110"+
		"\u0111\3\2\2\2\u0111\u0116\b.\3\2\u0112\u0113\5Y-\2\u0113\u0114\7$\2\2"+
		"\u0114\u0116\3\2\2\2\u0115\u0103\3\2\2\2\u0115\u0112\3\2\2\2\u0116\\\3"+
		"\2\2\2\u0117\u011c\5I%\2\u0118\u011b\5I%\2\u0119\u011b\5M\'\2\u011a\u0118"+
		"\3\2\2\2\u011a\u0119\3\2\2\2\u011b\u011e\3\2\2\2\u011c\u011a\3\2\2\2\u011c"+
		"\u011d\3\2\2\2\u011d^\3\2\2\2\u011e\u011c\3\2\2\2\u011f\u0128\7\62\2\2"+
		"\u0120\u0124\5K&\2\u0121\u0123\5M\'\2\u0122\u0121\3\2\2\2\u0123\u0126"+
		"\3\2\2\2\u0124\u0122\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0128\3\2\2\2\u0126"+
		"\u0124\3\2\2\2\u0127\u011f\3\2\2\2\u0127\u0120\3\2\2\2\u0128`\3\2\2\2"+
		"\21\2\u00c8\u00d2\u00e0\u00e6\u00ed\u00fc\u00fe\u0100\u010a\u0115\u011a"+
		"\u011c\u0124\u0127\4\b\2\2\3.\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}