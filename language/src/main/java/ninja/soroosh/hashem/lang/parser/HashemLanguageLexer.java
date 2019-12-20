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
		WS=32, COMMENT=33, LINE_COMMENT=34, IDENTIFIER=35, STRING_LITERAL=36, 
		NUMERIC_LITERAL=37;
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
		"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "WS", "COMMENT", 
		"LINE_COMMENT", "LETTER", "NON_ZERO_DIGIT", "DIGIT", "HEX_DIGIT", "OCT_DIGIT", 
		"BINARY_DIGIT", "TAB", "STRING_CHAR", "IDENTIFIER", "STRING_LITERAL", 
		"NUMERIC_LITERAL"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'bebin'", "'('", "','", "')'", "'{'", "'}'", "'khob'", "';'", "'badi'", 
		"'debugger'", "'ta'", "') bood'", "'age'", "'na?'", "'bede'", "'||'", 
		"'&&'", "'<'", "'<='", "'>'", "'>='", "'=='", "'!='", "'+'", "'-'", "'*'", 
		"'/'", "'='", "'.'", "'['", "']'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, "WS", "COMMENT", "LINE_COMMENT", 
		"IDENTIFIER", "STRING_LITERAL", "NUMERIC_LITERAL"
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\'\u010c\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3"+
		"\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33"+
		"\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\6!\u00c1\n!\r!\16!\u00c2"+
		"\3!\3!\3\"\3\"\3\"\3\"\7\"\u00cb\n\"\f\"\16\"\u00ce\13\"\3\"\3\"\3\"\3"+
		"\"\3\"\3#\3#\3#\3#\7#\u00d9\n#\f#\16#\u00dc\13#\3#\3#\3$\5$\u00e1\n$\3"+
		"%\3%\3&\3&\3\'\5\'\u00e8\n\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3,\7,\u00f5"+
		"\n,\f,\16,\u00f8\13,\3-\3-\7-\u00fc\n-\f-\16-\u00ff\13-\3-\3-\3.\3.\3"+
		".\7.\u0106\n.\f.\16.\u0109\13.\5.\u010b\n.\3\u00cc\2/\3\3\5\4\7\5\t\6"+
		"\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24"+
		"\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G\2I\2"+
		"K\2M\2O\2Q\2S\2U\2W%Y&[\'\3\2\n\5\2\13\f\16\17\"\"\4\2\f\f\17\17\6\2&"+
		"&C\\aac|\3\2\63;\3\2\62;\5\2\62;CHch\3\2\629\6\2\f\f\17\17$$^^\2\u010b"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2W\3\2\2"+
		"\2\2Y\3\2\2\2\2[\3\2\2\2\3]\3\2\2\2\5c\3\2\2\2\7e\3\2\2\2\tg\3\2\2\2\13"+
		"i\3\2\2\2\rk\3\2\2\2\17m\3\2\2\2\21r\3\2\2\2\23t\3\2\2\2\25y\3\2\2\2\27"+
		"\u0082\3\2\2\2\31\u0085\3\2\2\2\33\u008c\3\2\2\2\35\u0090\3\2\2\2\37\u0094"+
		"\3\2\2\2!\u0099\3\2\2\2#\u009c\3\2\2\2%\u009f\3\2\2\2\'\u00a1\3\2\2\2"+
		")\u00a4\3\2\2\2+\u00a6\3\2\2\2-\u00a9\3\2\2\2/\u00ac\3\2\2\2\61\u00af"+
		"\3\2\2\2\63\u00b1\3\2\2\2\65\u00b3\3\2\2\2\67\u00b5\3\2\2\29\u00b7\3\2"+
		"\2\2;\u00b9\3\2\2\2=\u00bb\3\2\2\2?\u00bd\3\2\2\2A\u00c0\3\2\2\2C\u00c6"+
		"\3\2\2\2E\u00d4\3\2\2\2G\u00e0\3\2\2\2I\u00e2\3\2\2\2K\u00e4\3\2\2\2M"+
		"\u00e7\3\2\2\2O\u00e9\3\2\2\2Q\u00eb\3\2\2\2S\u00ed\3\2\2\2U\u00ef\3\2"+
		"\2\2W\u00f1\3\2\2\2Y\u00f9\3\2\2\2[\u010a\3\2\2\2]^\7d\2\2^_\7g\2\2_`"+
		"\7d\2\2`a\7k\2\2ab\7p\2\2b\4\3\2\2\2cd\7*\2\2d\6\3\2\2\2ef\7.\2\2f\b\3"+
		"\2\2\2gh\7+\2\2h\n\3\2\2\2ij\7}\2\2j\f\3\2\2\2kl\7\177\2\2l\16\3\2\2\2"+
		"mn\7m\2\2no\7j\2\2op\7q\2\2pq\7d\2\2q\20\3\2\2\2rs\7=\2\2s\22\3\2\2\2"+
		"tu\7d\2\2uv\7c\2\2vw\7f\2\2wx\7k\2\2x\24\3\2\2\2yz\7f\2\2z{\7g\2\2{|\7"+
		"d\2\2|}\7w\2\2}~\7i\2\2~\177\7i\2\2\177\u0080\7g\2\2\u0080\u0081\7t\2"+
		"\2\u0081\26\3\2\2\2\u0082\u0083\7v\2\2\u0083\u0084\7c\2\2\u0084\30\3\2"+
		"\2\2\u0085\u0086\7+\2\2\u0086\u0087\7\"\2\2\u0087\u0088\7d\2\2\u0088\u0089"+
		"\7q\2\2\u0089\u008a\7q\2\2\u008a\u008b\7f\2\2\u008b\32\3\2\2\2\u008c\u008d"+
		"\7c\2\2\u008d\u008e\7i\2\2\u008e\u008f\7g\2\2\u008f\34\3\2\2\2\u0090\u0091"+
		"\7p\2\2\u0091\u0092\7c\2\2\u0092\u0093\7A\2\2\u0093\36\3\2\2\2\u0094\u0095"+
		"\7d\2\2\u0095\u0096\7g\2\2\u0096\u0097\7f\2\2\u0097\u0098\7g\2\2\u0098"+
		" \3\2\2\2\u0099\u009a\7~\2\2\u009a\u009b\7~\2\2\u009b\"\3\2\2\2\u009c"+
		"\u009d\7(\2\2\u009d\u009e\7(\2\2\u009e$\3\2\2\2\u009f\u00a0\7>\2\2\u00a0"+
		"&\3\2\2\2\u00a1\u00a2\7>\2\2\u00a2\u00a3\7?\2\2\u00a3(\3\2\2\2\u00a4\u00a5"+
		"\7@\2\2\u00a5*\3\2\2\2\u00a6\u00a7\7@\2\2\u00a7\u00a8\7?\2\2\u00a8,\3"+
		"\2\2\2\u00a9\u00aa\7?\2\2\u00aa\u00ab\7?\2\2\u00ab.\3\2\2\2\u00ac\u00ad"+
		"\7#\2\2\u00ad\u00ae\7?\2\2\u00ae\60\3\2\2\2\u00af\u00b0\7-\2\2\u00b0\62"+
		"\3\2\2\2\u00b1\u00b2\7/\2\2\u00b2\64\3\2\2\2\u00b3\u00b4\7,\2\2\u00b4"+
		"\66\3\2\2\2\u00b5\u00b6\7\61\2\2\u00b68\3\2\2\2\u00b7\u00b8\7?\2\2\u00b8"+
		":\3\2\2\2\u00b9\u00ba\7\60\2\2\u00ba<\3\2\2\2\u00bb\u00bc\7]\2\2\u00bc"+
		">\3\2\2\2\u00bd\u00be\7_\2\2\u00be@\3\2\2\2\u00bf\u00c1\t\2\2\2\u00c0"+
		"\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3\3\2"+
		"\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c5\b!\2\2\u00c5B\3\2\2\2\u00c6\u00c7"+
		"\7\61\2\2\u00c7\u00c8\7,\2\2\u00c8\u00cc\3\2\2\2\u00c9\u00cb\13\2\2\2"+
		"\u00ca\u00c9\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cc\u00ca"+
		"\3\2\2\2\u00cd\u00cf\3\2\2\2\u00ce\u00cc\3\2\2\2\u00cf\u00d0\7,\2\2\u00d0"+
		"\u00d1\7\61\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d3\b\"\2\2\u00d3D\3\2\2\2"+
		"\u00d4\u00d5\7\61\2\2\u00d5\u00d6\7\61\2\2\u00d6\u00da\3\2\2\2\u00d7\u00d9"+
		"\n\3\2\2\u00d8\u00d7\3\2\2\2\u00d9\u00dc\3\2\2\2\u00da\u00d8\3\2\2\2\u00da"+
		"\u00db\3\2\2\2\u00db\u00dd\3\2\2\2\u00dc\u00da\3\2\2\2\u00dd\u00de\b#"+
		"\2\2\u00deF\3\2\2\2\u00df\u00e1\t\4\2\2\u00e0\u00df\3\2\2\2\u00e1H\3\2"+
		"\2\2\u00e2\u00e3\t\5\2\2\u00e3J\3\2\2\2\u00e4\u00e5\t\6\2\2\u00e5L\3\2"+
		"\2\2\u00e6\u00e8\t\7\2\2\u00e7\u00e6\3\2\2\2\u00e8N\3\2\2\2\u00e9\u00ea"+
		"\t\b\2\2\u00eaP\3\2\2\2\u00eb\u00ec\4\62\63\2\u00ecR\3\2\2\2\u00ed\u00ee"+
		"\7\13\2\2\u00eeT\3\2\2\2\u00ef\u00f0\n\t\2\2\u00f0V\3\2\2\2\u00f1\u00f6"+
		"\5G$\2\u00f2\u00f5\5G$\2\u00f3\u00f5\5K&\2\u00f4\u00f2\3\2\2\2\u00f4\u00f3"+
		"\3\2\2\2\u00f5\u00f8\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7"+
		"X\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f9\u00fd\7$\2\2\u00fa\u00fc\5U+\2\u00fb"+
		"\u00fa\3\2\2\2\u00fc\u00ff\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fd\u00fe\3\2"+
		"\2\2\u00fe\u0100\3\2\2\2\u00ff\u00fd\3\2\2\2\u0100\u0101\7$\2\2\u0101"+
		"Z\3\2\2\2\u0102\u010b\7\62\2\2\u0103\u0107\5I%\2\u0104\u0106\5K&\2\u0105"+
		"\u0104\3\2\2\2\u0106\u0109\3\2\2\2\u0107\u0105\3\2\2\2\u0107\u0108\3\2"+
		"\2\2\u0108\u010b\3\2\2\2\u0109\u0107\3\2\2\2\u010a\u0102\3\2\2\2\u010a"+
		"\u0103\3\2\2\2\u010b\\\3\2\2\2\r\2\u00c2\u00cc\u00da\u00e0\u00e7\u00f4"+
		"\u00f6\u00fd\u0107\u010a\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}