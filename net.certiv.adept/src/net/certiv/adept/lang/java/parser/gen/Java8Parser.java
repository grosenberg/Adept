// Generated from D:/DevFiles/Eclipse/Adept/net.certiv.adept/src/net/certiv/adept/lang/java/parser/Java8Parser.g4 by ANTLR 4.7.1

	package net.certiv.adept.lang.java.parser.gen;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Java8Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ABSTRACT=1, ASSERT=2, BOOLEAN=3, BREAK=4, BYTE=5, CASE=6, CATCH=7, CHAR=8, 
		CLASS=9, CONST=10, CONTINUE=11, DEFAULT=12, DO=13, DOUBLE=14, ELSE=15, 
		ENUM=16, EXTENDS=17, FINAL=18, FINALLY=19, FLOAT=20, FOR=21, IF=22, GOTO=23, 
		IMPLEMENTS=24, IMPORT=25, INSTANCEOF=26, INT=27, INTERFACE=28, LONG=29, 
		NATIVE=30, NEW=31, PACKAGE=32, PRIVATE=33, PROTECTED=34, PUBLIC=35, RETURN=36, 
		SHORT=37, STATIC=38, STRICTFP=39, SUPER=40, SWITCH=41, SYNCHRONIZED=42, 
		THIS=43, THROW=44, THROWS=45, TRANSIENT=46, TRY=47, VOID=48, VOLATILE=49, 
		WHILE=50, IntegerLiteral=51, FloatingPointLiteral=52, BooleanLiteral=53, 
		CharacterLiteral=54, StringLiteral=55, NullLiteral=56, LPAREN=57, RPAREN=58, 
		LBRACE=59, RBRACE=60, LBRACK=61, RBRACK=62, SEMI=63, COMMA=64, DOT=65, 
		ASSIGN=66, GT=67, LT=68, BANG=69, TILDE=70, QMARK=71, COLON=72, DCOLON=73, 
		EQUAL=74, LE=75, GE=76, NOTEQUAL=77, AND=78, OR=79, INC=80, DEC=81, ADD=82, 
		SUB=83, STAR=84, DIV=85, BITAND=86, BITOR=87, CARET=88, MOD=89, ARROW=90, 
		ADD_ASSIGN=91, SUB_ASSIGN=92, MUL_ASSIGN=93, DIV_ASSIGN=94, AND_ASSIGN=95, 
		OR_ASSIGN=96, XOR_ASSIGN=97, MOD_ASSIGN=98, LSHIFT_ASSIGN=99, RSHIFT_ASSIGN=100, 
		URSHIFT_ASSIGN=101, AT=102, ELLIPSIS=103, Identifier=104, HWS=105, VWS=106, 
		BLOCKCOMMENT=107, LINECOMMENT=108;
	public static final int
		RULE_compilationUnit = 0, RULE_packageDeclaration = 1, RULE_importDeclaration = 2, 
		RULE_typeDeclaration = 3, RULE_classModifier = 4, RULE_qualifiedName = 5, 
		RULE_classDeclaration = 6, RULE_typeParameters = 7, RULE_classTypeList = 8, 
		RULE_classBody = 9, RULE_classBodyDeclaration = 10, RULE_classMemberDeclaration = 11, 
		RULE_fieldDeclaration = 12, RULE_fieldModifier = 13, RULE_variableDeclaratorList = 14, 
		RULE_variableDeclarator = 15, RULE_variableInitializer = 16, RULE_literal = 17, 
		RULE_baseType = 18, RULE_primitiveType = 19, RULE_numericType = 20, RULE_referenceType = 21, 
		RULE_classType = 22, RULE_classOrInterfaceType = 23, RULE_arrayType = 24, 
		RULE_dims = 25, RULE_typeParameter = 26, RULE_typeBound = 27, RULE_additionalBound = 28, 
		RULE_typeArguments = 29, RULE_typeArgumentList = 30, RULE_typeArgument = 31, 
		RULE_wildcard = 32, RULE_methodDeclaration = 33, RULE_methodModifier = 34, 
		RULE_methodHeader = 35, RULE_result = 36, RULE_methodDeclarator = 37, 
		RULE_formalParameterList = 38, RULE_formalParameters = 39, RULE_formalParameter = 40, 
		RULE_lastFormalParameter = 41, RULE_receiverParameter = 42, RULE_throws_ = 43, 
		RULE_exceptionTypeList = 44, RULE_exceptionType = 45, RULE_methodBody = 46, 
		RULE_constructorDeclaration = 47, RULE_constructorModifier = 48, RULE_constructorDeclarator = 49, 
		RULE_constructorBody = 50, RULE_explicitConstructorInvocation = 51, RULE_enumDeclaration = 52, 
		RULE_enumBody = 53, RULE_enumConstantList = 54, RULE_enumConstant = 55, 
		RULE_interfaceDeclaration = 56, RULE_interfaceBody = 57, RULE_interfaceMemberDeclaration = 58, 
		RULE_constantDeclaration = 59, RULE_constantModifier = 60, RULE_interfaceMethodDeclaration = 61, 
		RULE_interfaceMethodModifier = 62, RULE_annotationTypeDeclaration = 63, 
		RULE_annotationTypeBody = 64, RULE_annotationTypeMemberDeclaration = 65, 
		RULE_annotationTypeElementDeclaration = 66, RULE_defaultValue = 67, RULE_annotation = 68, 
		RULE_normalAnnotation = 69, RULE_elementValuePairList = 70, RULE_elementValuePair = 71, 
		RULE_elementValue = 72, RULE_elementValueArrayInitializer = 73, RULE_elementValueList = 74, 
		RULE_markerAnnotation = 75, RULE_singleElementAnnotation = 76, RULE_arrayInitializer = 77, 
		RULE_variableInitializerList = 78, RULE_block = 79, RULE_blockStatement = 80, 
		RULE_localVariableDeclaration = 81, RULE_statement = 82, RULE_statementExpression = 83, 
		RULE_switchBlock = 84, RULE_switchBlockStatementGroup = 85, RULE_switchLabel = 86, 
		RULE_tryStatement = 87, RULE_forControl = 88, RULE_forInit = 89, RULE_statementExpressionList = 90, 
		RULE_catches = 91, RULE_catchFormalParameter = 92, RULE_catchType = 93, 
		RULE_resourceList = 94, RULE_resource = 95, RULE_primary = 96, RULE_primaryNoNewArray = 97, 
		RULE_primaryNoNewArray_lfno_arrayAccess = 98, RULE_primaryNoNewArray_lf_primary = 99, 
		RULE_primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary = 100, RULE_primaryNoNewArray_lfno_primary = 101, 
		RULE_primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary = 102, 
		RULE_arrayCreationExpression = 103, RULE_arrayAccess = 104, RULE_arrayAccess_lf_primary = 105, 
		RULE_arrayAccess_lfno_primary = 106, RULE_arrayAccessExpression = 107, 
		RULE_classInstanceCreationExpression = 108, RULE_classInstanceCreationExpression_lf_primary = 109, 
		RULE_classInstanceCreationExpression_lfno_primary = 110, RULE_instanceCreationExpression = 111, 
		RULE_typeArgumentsOrDiamond = 112, RULE_fieldAccess = 113, RULE_fieldAccess_lf_primary = 114, 
		RULE_fieldAccess_lfno_primary = 115, RULE_methodInvocation = 116, RULE_methodInvocation_lfno_primary = 117, 
		RULE_argumentList = 118, RULE_methodReference = 119, RULE_methodReference_lfno_primary = 120, 
		RULE_dimExpr = 121, RULE_expression = 122, RULE_assignment = 123, RULE_castExpression = 124, 
		RULE_lambdaExpression = 125, RULE_lambdaParameters = 126, RULE_unaryExpression = 127, 
		RULE_postfixExpression = 128;
	public static final String[] ruleNames = {
		"compilationUnit", "packageDeclaration", "importDeclaration", "typeDeclaration", 
		"classModifier", "qualifiedName", "classDeclaration", "typeParameters", 
		"classTypeList", "classBody", "classBodyDeclaration", "classMemberDeclaration", 
		"fieldDeclaration", "fieldModifier", "variableDeclaratorList", "variableDeclarator", 
		"variableInitializer", "literal", "baseType", "primitiveType", "numericType", 
		"referenceType", "classType", "classOrInterfaceType", "arrayType", "dims", 
		"typeParameter", "typeBound", "additionalBound", "typeArguments", "typeArgumentList", 
		"typeArgument", "wildcard", "methodDeclaration", "methodModifier", "methodHeader", 
		"result", "methodDeclarator", "formalParameterList", "formalParameters", 
		"formalParameter", "lastFormalParameter", "receiverParameter", "throws_", 
		"exceptionTypeList", "exceptionType", "methodBody", "constructorDeclaration", 
		"constructorModifier", "constructorDeclarator", "constructorBody", "explicitConstructorInvocation", 
		"enumDeclaration", "enumBody", "enumConstantList", "enumConstant", "interfaceDeclaration", 
		"interfaceBody", "interfaceMemberDeclaration", "constantDeclaration", 
		"constantModifier", "interfaceMethodDeclaration", "interfaceMethodModifier", 
		"annotationTypeDeclaration", "annotationTypeBody", "annotationTypeMemberDeclaration", 
		"annotationTypeElementDeclaration", "defaultValue", "annotation", "normalAnnotation", 
		"elementValuePairList", "elementValuePair", "elementValue", "elementValueArrayInitializer", 
		"elementValueList", "markerAnnotation", "singleElementAnnotation", "arrayInitializer", 
		"variableInitializerList", "block", "blockStatement", "localVariableDeclaration", 
		"statement", "statementExpression", "switchBlock", "switchBlockStatementGroup", 
		"switchLabel", "tryStatement", "forControl", "forInit", "statementExpressionList", 
		"catches", "catchFormalParameter", "catchType", "resourceList", "resource", 
		"primary", "primaryNoNewArray", "primaryNoNewArray_lfno_arrayAccess", 
		"primaryNoNewArray_lf_primary", "primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary", 
		"primaryNoNewArray_lfno_primary", "primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary", 
		"arrayCreationExpression", "arrayAccess", "arrayAccess_lf_primary", "arrayAccess_lfno_primary", 
		"arrayAccessExpression", "classInstanceCreationExpression", "classInstanceCreationExpression_lf_primary", 
		"classInstanceCreationExpression_lfno_primary", "instanceCreationExpression", 
		"typeArgumentsOrDiamond", "fieldAccess", "fieldAccess_lf_primary", "fieldAccess_lfno_primary", 
		"methodInvocation", "methodInvocation_lfno_primary", "argumentList", "methodReference", 
		"methodReference_lfno_primary", "dimExpr", "expression", "assignment", 
		"castExpression", "lambdaExpression", "lambdaParameters", "unaryExpression", 
		"postfixExpression"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "ABSTRACT", "ASSERT", "BOOLEAN", "BREAK", "BYTE", "CASE", "CATCH", 
		"CHAR", "CLASS", "CONST", "CONTINUE", "DEFAULT", "DO", "DOUBLE", "ELSE", 
		"ENUM", "EXTENDS", "FINAL", "FINALLY", "FLOAT", "FOR", "IF", "GOTO", "IMPLEMENTS", 
		"IMPORT", "INSTANCEOF", "INT", "INTERFACE", "LONG", "NATIVE", "NEW", "PACKAGE", 
		"PRIVATE", "PROTECTED", "PUBLIC", "RETURN", "SHORT", "STATIC", "STRICTFP", 
		"SUPER", "SWITCH", "SYNCHRONIZED", "THIS", "THROW", "THROWS", "TRANSIENT", 
		"TRY", "VOID", "VOLATILE", "WHILE", "IntegerLiteral", "FloatingPointLiteral", 
		"BooleanLiteral", "CharacterLiteral", "StringLiteral", "NullLiteral", 
		"LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "SEMI", "COMMA", 
		"DOT", "ASSIGN", "GT", "LT", "BANG", "TILDE", "QMARK", "COLON", "DCOLON", 
		"EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "INC", "DEC", "ADD", "SUB", 
		"STAR", "DIV", "BITAND", "BITOR", "CARET", "MOD", "ARROW", "ADD_ASSIGN", 
		"SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", 
		"MOD_ASSIGN", "LSHIFT_ASSIGN", "RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "AT", 
		"ELLIPSIS", "Identifier", "HWS", "VWS", "BLOCKCOMMENT", "LINECOMMENT"
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

	@Override
	public String getGrammarFileName() { return "Java8Parser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public Java8Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class CompilationUnitContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(Java8Parser.EOF, 0); }
		public PackageDeclarationContext packageDeclaration() {
			return getRuleContext(PackageDeclarationContext.class,0);
		}
		public List<ImportDeclarationContext> importDeclaration() {
			return getRuleContexts(ImportDeclarationContext.class);
		}
		public ImportDeclarationContext importDeclaration(int i) {
			return getRuleContext(ImportDeclarationContext.class,i);
		}
		public List<TypeDeclarationContext> typeDeclaration() {
			return getRuleContexts(TypeDeclarationContext.class);
		}
		public TypeDeclarationContext typeDeclaration(int i) {
			return getRuleContext(TypeDeclarationContext.class,i);
		}
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterCompilationUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitCompilationUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitCompilationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilationUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(258);
				packageDeclaration();
				}
				break;
			}
			setState(264);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(261);
				importDeclaration();
				}
				}
				setState(266);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(270);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << CLASS) | (1L << ENUM) | (1L << FINAL) | (1L << INTERFACE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << STRICTFP) | (1L << SEMI))) != 0) || _la==AT) {
				{
				{
				setState(267);
				typeDeclaration();
				}
				}
				setState(272);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(273);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PackageDeclarationContext extends ParserRuleContext {
		public TerminalNode PACKAGE() { return getToken(Java8Parser.PACKAGE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public PackageDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_packageDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterPackageDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitPackageDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitPackageDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PackageDeclarationContext packageDeclaration() throws RecognitionException {
		PackageDeclarationContext _localctx = new PackageDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_packageDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(278);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(275);
				annotation();
				}
				}
				setState(280);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(281);
			match(PACKAGE);
			setState(282);
			qualifiedName();
			setState(283);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportDeclarationContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(Java8Parser.IMPORT, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public TerminalNode STATIC() { return getToken(Java8Parser.STATIC, 0); }
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public TerminalNode STAR() { return getToken(Java8Parser.STAR, 0); }
		public ImportDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterImportDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitImportDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitImportDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportDeclarationContext importDeclaration() throws RecognitionException {
		ImportDeclarationContext _localctx = new ImportDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_importDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			match(IMPORT);
			setState(287);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STATIC) {
				{
				setState(286);
				match(STATIC);
				}
			}

			setState(289);
			qualifiedName();
			setState(292);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DOT) {
				{
				setState(290);
				match(DOT);
				setState(291);
				match(STAR);
				}
			}

			setState(294);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeDeclarationContext extends ParserRuleContext {
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public AnnotationTypeDeclarationContext annotationTypeDeclaration() {
			return getRuleContext(AnnotationTypeDeclarationContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public TypeDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterTypeDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitTypeDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitTypeDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDeclarationContext typeDeclaration() throws RecognitionException {
		TypeDeclarationContext _localctx = new TypeDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_typeDeclaration);
		try {
			setState(301);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(296);
				classDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(297);
				enumDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(298);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(299);
				annotationTypeDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(300);
				match(SEMI);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassModifierContext extends ParserRuleContext {
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public TerminalNode PUBLIC() { return getToken(Java8Parser.PUBLIC, 0); }
		public TerminalNode PROTECTED() { return getToken(Java8Parser.PROTECTED, 0); }
		public TerminalNode PRIVATE() { return getToken(Java8Parser.PRIVATE, 0); }
		public TerminalNode ABSTRACT() { return getToken(Java8Parser.ABSTRACT, 0); }
		public TerminalNode STATIC() { return getToken(Java8Parser.STATIC, 0); }
		public TerminalNode FINAL() { return getToken(Java8Parser.FINAL, 0); }
		public TerminalNode STRICTFP() { return getToken(Java8Parser.STRICTFP, 0); }
		public ClassModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterClassModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitClassModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitClassModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassModifierContext classModifier() throws RecognitionException {
		ClassModifierContext _localctx = new ClassModifierContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_classModifier);
		try {
			setState(311);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(303);
				annotation();
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 2);
				{
				setState(304);
				match(PUBLIC);
				}
				break;
			case PROTECTED:
				enterOuterAlt(_localctx, 3);
				{
				setState(305);
				match(PROTECTED);
				}
				break;
			case PRIVATE:
				enterOuterAlt(_localctx, 4);
				{
				setState(306);
				match(PRIVATE);
				}
				break;
			case ABSTRACT:
				enterOuterAlt(_localctx, 5);
				{
				setState(307);
				match(ABSTRACT);
				}
				break;
			case STATIC:
				enterOuterAlt(_localctx, 6);
				{
				setState(308);
				match(STATIC);
				}
				break;
			case FINAL:
				enterOuterAlt(_localctx, 7);
				{
				setState(309);
				match(FINAL);
				}
				break;
			case STRICTFP:
				enterOuterAlt(_localctx, 8);
				{
				setState(310);
				match(STRICTFP);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QualifiedNameContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(Java8Parser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(Java8Parser.Identifier, i);
		}
		public List<TerminalNode> DOT() { return getTokens(Java8Parser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(Java8Parser.DOT, i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterQualifiedName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitQualifiedName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_qualifiedName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			match(Identifier);
			setState(318);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(314);
					match(DOT);
					setState(315);
					match(Identifier);
					}
					} 
				}
				setState(320);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public TerminalNode CLASS() { return getToken(Java8Parser.CLASS, 0); }
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public List<ClassModifierContext> classModifier() {
			return getRuleContexts(ClassModifierContext.class);
		}
		public ClassModifierContext classModifier(int i) {
			return getRuleContext(ClassModifierContext.class,i);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public TerminalNode EXTENDS() { return getToken(Java8Parser.EXTENDS, 0); }
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public TerminalNode IMPLEMENTS() { return getToken(Java8Parser.IMPLEMENTS, 0); }
		public ClassTypeListContext classTypeList() {
			return getRuleContext(ClassTypeListContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterClassDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitClassDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_classDeclaration);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << STRICTFP))) != 0) || _la==AT) {
				{
				{
				setState(321);
				classModifier();
				}
				}
				setState(326);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(327);
			match(CLASS);
			setState(328);
			match(Identifier);
			setState(330);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(329);
				typeParameters();
				}
			}

			setState(340);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(332);
				match(EXTENDS);
				setState(336);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(333);
						annotation();
						}
						} 
					}
					setState(338);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				}
				setState(339);
				classType();
				}
			}

			setState(344);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(342);
				match(IMPLEMENTS);
				setState(343);
				classTypeList();
				}
			}

			setState(346);
			classBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeParametersContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(Java8Parser.LT, 0); }
		public List<TypeParameterContext> typeParameter() {
			return getRuleContexts(TypeParameterContext.class);
		}
		public TypeParameterContext typeParameter(int i) {
			return getRuleContext(TypeParameterContext.class,i);
		}
		public TerminalNode GT() { return getToken(Java8Parser.GT, 0); }
		public List<TerminalNode> COMMA() { return getTokens(Java8Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Java8Parser.COMMA, i);
		}
		public TypeParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterTypeParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitTypeParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitTypeParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeParametersContext typeParameters() throws RecognitionException {
		TypeParametersContext _localctx = new TypeParametersContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_typeParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(348);
			match(LT);
			setState(349);
			typeParameter();
			setState(354);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(350);
				match(COMMA);
				setState(351);
				typeParameter();
				}
				}
				setState(356);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(357);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassTypeListContext extends ParserRuleContext {
		public List<ClassTypeContext> classType() {
			return getRuleContexts(ClassTypeContext.class);
		}
		public ClassTypeContext classType(int i) {
			return getRuleContext(ClassTypeContext.class,i);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Java8Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Java8Parser.COMMA, i);
		}
		public ClassTypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classTypeList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterClassTypeList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitClassTypeList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitClassTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassTypeListContext classTypeList() throws RecognitionException {
		ClassTypeListContext _localctx = new ClassTypeListContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_classTypeList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(362);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(359);
					annotation();
					}
					} 
				}
				setState(364);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			setState(365);
			classType();
			setState(376);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(366);
				match(COMMA);
				setState(370);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(367);
						annotation();
						}
						} 
					}
					setState(372);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
				}
				setState(373);
				classType();
				}
				}
				setState(378);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassBodyContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(Java8Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(Java8Parser.RBRACE, 0); }
		public List<ClassBodyDeclarationContext> classBodyDeclaration() {
			return getRuleContexts(ClassBodyDeclarationContext.class);
		}
		public ClassBodyDeclarationContext classBodyDeclaration(int i) {
			return getRuleContext(ClassBodyDeclarationContext.class,i);
		}
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterClassBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitClassBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(379);
			match(LBRACE);
			setState(383);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << CLASS) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FLOAT) | (1L << INT) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SHORT) | (1L << STATIC) | (1L << STRICTFP) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << VOID) | (1L << VOLATILE) | (1L << LBRACE) | (1L << SEMI))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (LT - 68)) | (1L << (AT - 68)) | (1L << (Identifier - 68)))) != 0)) {
				{
				{
				setState(380);
				classBodyDeclaration();
				}
				}
				setState(385);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(386);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassBodyDeclarationContext extends ParserRuleContext {
		public ClassMemberDeclarationContext classMemberDeclaration() {
			return getRuleContext(ClassMemberDeclarationContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode STATIC() { return getToken(Java8Parser.STATIC, 0); }
		public ConstructorDeclarationContext constructorDeclaration() {
			return getRuleContext(ConstructorDeclarationContext.class,0);
		}
		public ClassBodyDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBodyDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterClassBodyDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitClassBodyDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitClassBodyDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyDeclarationContext classBodyDeclaration() throws RecognitionException {
		ClassBodyDeclarationContext _localctx = new ClassBodyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_classBodyDeclaration);
		int _la;
		try {
			setState(394);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(388);
				classMemberDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(390);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(389);
					match(STATIC);
					}
				}

				setState(392);
				block();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(393);
				constructorDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassMemberDeclarationContext extends ParserRuleContext {
		public FieldDeclarationContext fieldDeclaration() {
			return getRuleContext(FieldDeclarationContext.class,0);
		}
		public MethodDeclarationContext methodDeclaration() {
			return getRuleContext(MethodDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public ClassMemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classMemberDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterClassMemberDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitClassMemberDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitClassMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassMemberDeclarationContext classMemberDeclaration() throws RecognitionException {
		ClassMemberDeclarationContext _localctx = new ClassMemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_classMemberDeclaration);
		try {
			setState(402);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(396);
				fieldDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(397);
				methodDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(398);
				enumDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(399);
				classDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(400);
				interfaceDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(401);
				match(SEMI);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldDeclarationContext extends ParserRuleContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public VariableDeclaratorListContext variableDeclaratorList() {
			return getRuleContext(VariableDeclaratorListContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public List<FieldModifierContext> fieldModifier() {
			return getRuleContexts(FieldModifierContext.class);
		}
		public FieldModifierContext fieldModifier(int i) {
			return getRuleContext(FieldModifierContext.class,i);
		}
		public FieldDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterFieldDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitFieldDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitFieldDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldDeclarationContext fieldDeclaration() throws RecognitionException {
		FieldDeclarationContext _localctx = new FieldDeclarationContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_fieldDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(407);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TRANSIENT) | (1L << VOLATILE))) != 0) || _la==AT) {
				{
				{
				setState(404);
				fieldModifier();
				}
				}
				setState(409);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(410);
			baseType();
			setState(411);
			variableDeclaratorList();
			setState(412);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldModifierContext extends ParserRuleContext {
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public TerminalNode PUBLIC() { return getToken(Java8Parser.PUBLIC, 0); }
		public TerminalNode PROTECTED() { return getToken(Java8Parser.PROTECTED, 0); }
		public TerminalNode PRIVATE() { return getToken(Java8Parser.PRIVATE, 0); }
		public TerminalNode STATIC() { return getToken(Java8Parser.STATIC, 0); }
		public TerminalNode FINAL() { return getToken(Java8Parser.FINAL, 0); }
		public TerminalNode TRANSIENT() { return getToken(Java8Parser.TRANSIENT, 0); }
		public TerminalNode VOLATILE() { return getToken(Java8Parser.VOLATILE, 0); }
		public FieldModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterFieldModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitFieldModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitFieldModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldModifierContext fieldModifier() throws RecognitionException {
		FieldModifierContext _localctx = new FieldModifierContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_fieldModifier);
		try {
			setState(422);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(414);
				annotation();
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 2);
				{
				setState(415);
				match(PUBLIC);
				}
				break;
			case PROTECTED:
				enterOuterAlt(_localctx, 3);
				{
				setState(416);
				match(PROTECTED);
				}
				break;
			case PRIVATE:
				enterOuterAlt(_localctx, 4);
				{
				setState(417);
				match(PRIVATE);
				}
				break;
			case STATIC:
				enterOuterAlt(_localctx, 5);
				{
				setState(418);
				match(STATIC);
				}
				break;
			case FINAL:
				enterOuterAlt(_localctx, 6);
				{
				setState(419);
				match(FINAL);
				}
				break;
			case TRANSIENT:
				enterOuterAlt(_localctx, 7);
				{
				setState(420);
				match(TRANSIENT);
				}
				break;
			case VOLATILE:
				enterOuterAlt(_localctx, 8);
				{
				setState(421);
				match(VOLATILE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclaratorListContext extends ParserRuleContext {
		public List<VariableDeclaratorContext> variableDeclarator() {
			return getRuleContexts(VariableDeclaratorContext.class);
		}
		public VariableDeclaratorContext variableDeclarator(int i) {
			return getRuleContext(VariableDeclaratorContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Java8Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Java8Parser.COMMA, i);
		}
		public VariableDeclaratorListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaratorList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterVariableDeclaratorList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitVariableDeclaratorList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitVariableDeclaratorList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclaratorListContext variableDeclaratorList() throws RecognitionException {
		VariableDeclaratorListContext _localctx = new VariableDeclaratorListContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_variableDeclaratorList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(424);
			variableDeclarator();
			setState(429);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(425);
				match(COMMA);
				setState(426);
				variableDeclarator();
				}
				}
				setState(431);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclaratorContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public DimsContext dims() {
			return getRuleContext(DimsContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(Java8Parser.ASSIGN, 0); }
		public VariableInitializerContext variableInitializer() {
			return getRuleContext(VariableInitializerContext.class,0);
		}
		public VariableDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterVariableDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitVariableDeclarator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitVariableDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclaratorContext variableDeclarator() throws RecognitionException {
		VariableDeclaratorContext _localctx = new VariableDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_variableDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(432);
			match(Identifier);
			setState(434);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACK || _la==AT) {
				{
				setState(433);
				dims();
				}
			}

			setState(438);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(436);
				match(ASSIGN);
				setState(437);
				variableInitializer();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableInitializerContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArrayInitializerContext arrayInitializer() {
			return getRuleContext(ArrayInitializerContext.class,0);
		}
		public VariableInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableInitializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterVariableInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitVariableInitializer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitVariableInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableInitializerContext variableInitializer() throws RecognitionException {
		VariableInitializerContext _localctx = new VariableInitializerContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_variableInitializer);
		try {
			setState(442);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case NEW:
			case SHORT:
			case SUPER:
			case THIS:
			case VOID:
			case IntegerLiteral:
			case FloatingPointLiteral:
			case BooleanLiteral:
			case CharacterLiteral:
			case StringLiteral:
			case NullLiteral:
			case LPAREN:
			case BANG:
			case TILDE:
			case INC:
			case DEC:
			case ADD:
			case SUB:
			case AT:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(440);
				expression(0);
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 2);
				{
				setState(441);
				arrayInitializer();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode IntegerLiteral() { return getToken(Java8Parser.IntegerLiteral, 0); }
		public TerminalNode FloatingPointLiteral() { return getToken(Java8Parser.FloatingPointLiteral, 0); }
		public TerminalNode BooleanLiteral() { return getToken(Java8Parser.BooleanLiteral, 0); }
		public TerminalNode CharacterLiteral() { return getToken(Java8Parser.CharacterLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(Java8Parser.StringLiteral, 0); }
		public TerminalNode NullLiteral() { return getToken(Java8Parser.NullLiteral, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(444);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BaseTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ReferenceTypeContext referenceType() {
			return getRuleContext(ReferenceTypeContext.class,0);
		}
		public BaseTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterBaseType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitBaseType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitBaseType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseTypeContext baseType() throws RecognitionException {
		BaseTypeContext _localctx = new BaseTypeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_baseType);
		try {
			setState(448);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(446);
				primitiveType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(447);
				referenceType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimitiveTypeContext extends ParserRuleContext {
		public NumericTypeContext numericType() {
			return getRuleContext(NumericTypeContext.class,0);
		}
		public TerminalNode BOOLEAN() { return getToken(Java8Parser.BOOLEAN, 0); }
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterPrimitiveType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitPrimitiveType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException {
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_primitiveType);
		try {
			setState(452);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case SHORT:
				enterOuterAlt(_localctx, 1);
				{
				setState(450);
				numericType();
				}
				break;
			case BOOLEAN:
				enterOuterAlt(_localctx, 2);
				{
				setState(451);
				match(BOOLEAN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumericTypeContext extends ParserRuleContext {
		public TerminalNode CHAR() { return getToken(Java8Parser.CHAR, 0); }
		public TerminalNode BYTE() { return getToken(Java8Parser.BYTE, 0); }
		public TerminalNode SHORT() { return getToken(Java8Parser.SHORT, 0); }
		public TerminalNode INT() { return getToken(Java8Parser.INT, 0); }
		public TerminalNode LONG() { return getToken(Java8Parser.LONG, 0); }
		public TerminalNode FLOAT() { return getToken(Java8Parser.FLOAT, 0); }
		public TerminalNode DOUBLE() { return getToken(Java8Parser.DOUBLE, 0); }
		public NumericTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterNumericType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitNumericType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitNumericType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericTypeContext numericType() throws RecognitionException {
		NumericTypeContext _localctx = new NumericTypeContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_numericType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(454);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << SHORT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReferenceTypeContext extends ParserRuleContext {
		public ClassOrInterfaceTypeContext classOrInterfaceType() {
			return getRuleContext(ClassOrInterfaceTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public ReferenceTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_referenceType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterReferenceType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitReferenceType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitReferenceType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReferenceTypeContext referenceType() throws RecognitionException {
		ReferenceTypeContext _localctx = new ReferenceTypeContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_referenceType);
		try {
			setState(459);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(456);
				classOrInterfaceType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(457);
				match(Identifier);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(458);
				arrayType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassTypeContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public ClassOrInterfaceTypeContext classOrInterfaceType() {
			return getRuleContext(ClassOrInterfaceTypeContext.class,0);
		}
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ClassTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterClassType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitClassType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitClassType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassTypeContext classType() throws RecognitionException {
		ClassTypeContext _localctx = new ClassTypeContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_classType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(464);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(461);
				classOrInterfaceType();
				setState(462);
				match(DOT);
				}
				break;
			}
			setState(469);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(466);
				annotation();
				}
				}
				setState(471);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(472);
			match(Identifier);
			setState(474);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(473);
				typeArguments();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassOrInterfaceTypeContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(Java8Parser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(Java8Parser.Identifier, i);
		}
		public List<TypeArgumentsContext> typeArguments() {
			return getRuleContexts(TypeArgumentsContext.class);
		}
		public TypeArgumentsContext typeArguments(int i) {
			return getRuleContext(TypeArgumentsContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(Java8Parser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(Java8Parser.DOT, i);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public ClassOrInterfaceTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classOrInterfaceType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterClassOrInterfaceType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitClassOrInterfaceType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitClassOrInterfaceType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassOrInterfaceTypeContext classOrInterfaceType() throws RecognitionException {
		ClassOrInterfaceTypeContext _localctx = new ClassOrInterfaceTypeContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_classOrInterfaceType);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(476);
			match(Identifier);
			setState(478);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				{
				setState(477);
				typeArguments();
				}
				break;
			}
			setState(493);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(480);
					match(DOT);
					setState(484);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==AT) {
						{
						{
						setState(481);
						annotation();
						}
						}
						setState(486);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(487);
					match(Identifier);
					setState(489);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
					case 1:
						{
						setState(488);
						typeArguments();
						}
						break;
					}
					}
					} 
				}
				setState(495);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public DimsContext dims() {
			return getRuleContext(DimsContext.class,0);
		}
		public ClassOrInterfaceTypeContext classOrInterfaceType() {
			return getRuleContext(ClassOrInterfaceTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterArrayType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitArrayType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_arrayType);
		try {
			setState(504);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(496);
				primitiveType();
				setState(497);
				dims();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(499);
				classOrInterfaceType();
				setState(500);
				dims();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(502);
				match(Identifier);
				setState(503);
				dims();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DimsContext extends ParserRuleContext {
		public List<TerminalNode> LBRACK() { return getTokens(Java8Parser.LBRACK); }
		public TerminalNode LBRACK(int i) {
			return getToken(Java8Parser.LBRACK, i);
		}
		public List<TerminalNode> RBRACK() { return getTokens(Java8Parser.RBRACK); }
		public TerminalNode RBRACK(int i) {
			return getToken(Java8Parser.RBRACK, i);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public DimsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dims; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterDims(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitDims(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitDims(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DimsContext dims() throws RecognitionException {
		DimsContext _localctx = new DimsContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_dims);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(509);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(506);
				annotation();
				}
				}
				setState(511);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(512);
			match(LBRACK);
			setState(513);
			match(RBRACK);
			setState(524);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(517);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==AT) {
						{
						{
						setState(514);
						annotation();
						}
						}
						setState(519);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(520);
					match(LBRACK);
					setState(521);
					match(RBRACK);
					}
					} 
				}
				setState(526);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeParameterContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public TypeBoundContext typeBound() {
			return getRuleContext(TypeBoundContext.class,0);
		}
		public TypeParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterTypeParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitTypeParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitTypeParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeParameterContext typeParameter() throws RecognitionException {
		TypeParameterContext _localctx = new TypeParameterContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_typeParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(530);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(527);
				annotation();
				}
				}
				setState(532);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(533);
			match(Identifier);
			setState(535);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(534);
				typeBound();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeBoundContext extends ParserRuleContext {
		public TerminalNode EXTENDS() { return getToken(Java8Parser.EXTENDS, 0); }
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public ClassOrInterfaceTypeContext classOrInterfaceType() {
			return getRuleContext(ClassOrInterfaceTypeContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public List<AdditionalBoundContext> additionalBound() {
			return getRuleContexts(AdditionalBoundContext.class);
		}
		public AdditionalBoundContext additionalBound(int i) {
			return getRuleContext(AdditionalBoundContext.class,i);
		}
		public TypeBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeBound; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterTypeBound(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitTypeBound(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitTypeBound(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeBoundContext typeBound() throws RecognitionException {
		TypeBoundContext _localctx = new TypeBoundContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_typeBound);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(537);
			match(EXTENDS);
			setState(558);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				{
				setState(541);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(538);
					annotation();
					}
					}
					setState(543);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(544);
				match(Identifier);
				}
				break;
			case 2:
				{
				setState(548);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(545);
					annotation();
					}
					}
					setState(550);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(551);
				classOrInterfaceType();
				setState(555);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==BITAND) {
					{
					{
					setState(552);
					additionalBound();
					}
					}
					setState(557);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AdditionalBoundContext extends ParserRuleContext {
		public TerminalNode BITAND() { return getToken(Java8Parser.BITAND, 0); }
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public AdditionalBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additionalBound; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterAdditionalBound(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitAdditionalBound(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitAdditionalBound(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditionalBoundContext additionalBound() throws RecognitionException {
		AdditionalBoundContext _localctx = new AdditionalBoundContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_additionalBound);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(560);
			match(BITAND);
			setState(564);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(561);
					annotation();
					}
					} 
				}
				setState(566);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			}
			setState(567);
			classType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeArgumentsContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(Java8Parser.LT, 0); }
		public TypeArgumentListContext typeArgumentList() {
			return getRuleContext(TypeArgumentListContext.class,0);
		}
		public TerminalNode GT() { return getToken(Java8Parser.GT, 0); }
		public TypeArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterTypeArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitTypeArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitTypeArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentsContext typeArguments() throws RecognitionException {
		TypeArgumentsContext _localctx = new TypeArgumentsContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_typeArguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(569);
			match(LT);
			setState(570);
			typeArgumentList();
			setState(571);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeArgumentListContext extends ParserRuleContext {
		public List<TypeArgumentContext> typeArgument() {
			return getRuleContexts(TypeArgumentContext.class);
		}
		public TypeArgumentContext typeArgument(int i) {
			return getRuleContext(TypeArgumentContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Java8Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Java8Parser.COMMA, i);
		}
		public TypeArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArgumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterTypeArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitTypeArgumentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitTypeArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentListContext typeArgumentList() throws RecognitionException {
		TypeArgumentListContext _localctx = new TypeArgumentListContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_typeArgumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(573);
			typeArgument();
			setState(578);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(574);
				match(COMMA);
				setState(575);
				typeArgument();
				}
				}
				setState(580);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeArgumentContext extends ParserRuleContext {
		public ReferenceTypeContext referenceType() {
			return getRuleContext(ReferenceTypeContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public WildcardContext wildcard() {
			return getRuleContext(WildcardContext.class,0);
		}
		public TypeArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterTypeArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitTypeArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitTypeArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentContext typeArgument() throws RecognitionException {
		TypeArgumentContext _localctx = new TypeArgumentContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_typeArgument);
		int _la;
		try {
			setState(589);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(584);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(581);
					annotation();
					}
					}
					setState(586);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(587);
				referenceType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(588);
				wildcard();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WildcardContext extends ParserRuleContext {
		public TerminalNode QMARK() { return getToken(Java8Parser.QMARK, 0); }
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public TerminalNode EXTENDS() { return getToken(Java8Parser.EXTENDS, 0); }
		public ReferenceTypeContext referenceType() {
			return getRuleContext(ReferenceTypeContext.class,0);
		}
		public TerminalNode SUPER() { return getToken(Java8Parser.SUPER, 0); }
		public WildcardContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_wildcard; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterWildcard(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitWildcard(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitWildcard(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WildcardContext wildcard() throws RecognitionException {
		WildcardContext _localctx = new WildcardContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_wildcard);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(594);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(591);
				annotation();
				}
				}
				setState(596);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(597);
			match(QMARK);
			setState(614);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EXTENDS:
				{
				setState(598);
				match(EXTENDS);
				setState(602);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(599);
					annotation();
					}
					}
					setState(604);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(605);
				referenceType();
				}
				break;
			case SUPER:
				{
				setState(606);
				match(SUPER);
				setState(610);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(607);
					annotation();
					}
					}
					setState(612);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(613);
				referenceType();
				}
				break;
			case COMMA:
			case GT:
				break;
			default:
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDeclarationContext extends ParserRuleContext {
		public MethodHeaderContext methodHeader() {
			return getRuleContext(MethodHeaderContext.class,0);
		}
		public MethodBodyContext methodBody() {
			return getRuleContext(MethodBodyContext.class,0);
		}
		public List<MethodModifierContext> methodModifier() {
			return getRuleContexts(MethodModifierContext.class);
		}
		public MethodModifierContext methodModifier(int i) {
			return getRuleContext(MethodModifierContext.class,i);
		}
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterMethodDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitMethodDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_methodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(619);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << NATIVE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << STRICTFP) | (1L << SYNCHRONIZED))) != 0) || _la==AT) {
				{
				{
				setState(616);
				methodModifier();
				}
				}
				setState(621);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(622);
			methodHeader();
			setState(623);
			methodBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodModifierContext extends ParserRuleContext {
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public TerminalNode PUBLIC() { return getToken(Java8Parser.PUBLIC, 0); }
		public TerminalNode PROTECTED() { return getToken(Java8Parser.PROTECTED, 0); }
		public TerminalNode PRIVATE() { return getToken(Java8Parser.PRIVATE, 0); }
		public TerminalNode ABSTRACT() { return getToken(Java8Parser.ABSTRACT, 0); }
		public TerminalNode STATIC() { return getToken(Java8Parser.STATIC, 0); }
		public TerminalNode FINAL() { return getToken(Java8Parser.FINAL, 0); }
		public TerminalNode SYNCHRONIZED() { return getToken(Java8Parser.SYNCHRONIZED, 0); }
		public TerminalNode NATIVE() { return getToken(Java8Parser.NATIVE, 0); }
		public TerminalNode STRICTFP() { return getToken(Java8Parser.STRICTFP, 0); }
		public MethodModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterMethodModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitMethodModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitMethodModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodModifierContext methodModifier() throws RecognitionException {
		MethodModifierContext _localctx = new MethodModifierContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_methodModifier);
		try {
			setState(635);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(625);
				annotation();
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 2);
				{
				setState(626);
				match(PUBLIC);
				}
				break;
			case PROTECTED:
				enterOuterAlt(_localctx, 3);
				{
				setState(627);
				match(PROTECTED);
				}
				break;
			case PRIVATE:
				enterOuterAlt(_localctx, 4);
				{
				setState(628);
				match(PRIVATE);
				}
				break;
			case ABSTRACT:
				enterOuterAlt(_localctx, 5);
				{
				setState(629);
				match(ABSTRACT);
				}
				break;
			case STATIC:
				enterOuterAlt(_localctx, 6);
				{
				setState(630);
				match(STATIC);
				}
				break;
			case FINAL:
				enterOuterAlt(_localctx, 7);
				{
				setState(631);
				match(FINAL);
				}
				break;
			case SYNCHRONIZED:
				enterOuterAlt(_localctx, 8);
				{
				setState(632);
				match(SYNCHRONIZED);
				}
				break;
			case NATIVE:
				enterOuterAlt(_localctx, 9);
				{
				setState(633);
				match(NATIVE);
				}
				break;
			case STRICTFP:
				enterOuterAlt(_localctx, 10);
				{
				setState(634);
				match(STRICTFP);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodHeaderContext extends ParserRuleContext {
		public ResultContext result() {
			return getRuleContext(ResultContext.class,0);
		}
		public MethodDeclaratorContext methodDeclarator() {
			return getRuleContext(MethodDeclaratorContext.class,0);
		}
		public Throws_Context throws_() {
			return getRuleContext(Throws_Context.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public MethodHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterMethodHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitMethodHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitMethodHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodHeaderContext methodHeader() throws RecognitionException {
		MethodHeaderContext _localctx = new MethodHeaderContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_methodHeader);
		int _la;
		try {
			setState(654);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case SHORT:
			case VOID:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(637);
				result();
				setState(638);
				methodDeclarator();
				setState(640);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==THROWS) {
					{
					setState(639);
					throws_();
					}
				}

				}
				break;
			case LT:
				enterOuterAlt(_localctx, 2);
				{
				setState(642);
				typeParameters();
				setState(646);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(643);
					annotation();
					}
					}
					setState(648);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(649);
				result();
				setState(650);
				methodDeclarator();
				setState(652);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==THROWS) {
					{
					setState(651);
					throws_();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ResultContext extends ParserRuleContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public TerminalNode VOID() { return getToken(Java8Parser.VOID, 0); }
		public ResultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_result; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterResult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitResult(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitResult(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResultContext result() throws RecognitionException {
		ResultContext _localctx = new ResultContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_result);
		try {
			setState(658);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case SHORT:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(656);
				baseType();
				}
				break;
			case VOID:
				enterOuterAlt(_localctx, 2);
				{
				setState(657);
				match(VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDeclaratorContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public FormalParameterListContext formalParameterList() {
			return getRuleContext(FormalParameterListContext.class,0);
		}
		public DimsContext dims() {
			return getRuleContext(DimsContext.class,0);
		}
		public MethodDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterMethodDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitMethodDeclarator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitMethodDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclaratorContext methodDeclarator() throws RecognitionException {
		MethodDeclaratorContext _localctx = new MethodDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_methodDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(660);
			match(Identifier);
			setState(661);
			match(LPAREN);
			setState(663);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FINAL) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << SHORT))) != 0) || _la==AT || _la==Identifier) {
				{
				setState(662);
				formalParameterList();
				}
			}

			setState(665);
			match(RPAREN);
			setState(667);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACK || _la==AT) {
				{
				setState(666);
				dims();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalParameterListContext extends ParserRuleContext {
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(Java8Parser.COMMA, 0); }
		public LastFormalParameterContext lastFormalParameter() {
			return getRuleContext(LastFormalParameterContext.class,0);
		}
		public FormalParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterFormalParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitFormalParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitFormalParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParameterListContext formalParameterList() throws RecognitionException {
		FormalParameterListContext _localctx = new FormalParameterListContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_formalParameterList);
		try {
			setState(674);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(669);
				formalParameters();
				setState(670);
				match(COMMA);
				setState(671);
				lastFormalParameter();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(673);
				lastFormalParameter();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalParametersContext extends ParserRuleContext {
		public List<FormalParameterContext> formalParameter() {
			return getRuleContexts(FormalParameterContext.class);
		}
		public FormalParameterContext formalParameter(int i) {
			return getRuleContext(FormalParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Java8Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Java8Parser.COMMA, i);
		}
		public ReceiverParameterContext receiverParameter() {
			return getRuleContext(ReceiverParameterContext.class,0);
		}
		public FormalParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterFormalParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitFormalParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitFormalParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParametersContext formalParameters() throws RecognitionException {
		FormalParametersContext _localctx = new FormalParametersContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_formalParameters);
		try {
			int _alt;
			setState(692);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,68,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(676);
				formalParameter();
				setState(681);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(677);
						match(COMMA);
						setState(678);
						formalParameter();
						}
						} 
					}
					setState(683);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(684);
				receiverParameter();
				setState(689);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,67,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(685);
						match(COMMA);
						setState(686);
						formalParameter();
						}
						} 
					}
					setState(691);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,67,_ctx);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalParameterContext extends ParserRuleContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public List<TerminalNode> FINAL() { return getTokens(Java8Parser.FINAL); }
		public TerminalNode FINAL(int i) {
			return getToken(Java8Parser.FINAL, i);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public DimsContext dims() {
			return getRuleContext(DimsContext.class,0);
		}
		public FormalParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterFormalParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitFormalParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitFormalParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParameterContext formalParameter() throws RecognitionException {
		FormalParameterContext _localctx = new FormalParameterContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_formalParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(698);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==AT) {
				{
				setState(696);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case FINAL:
					{
					setState(694);
					match(FINAL);
					}
					break;
				case AT:
					{
					setState(695);
					annotation();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(700);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(701);
			baseType();
			setState(702);
			match(Identifier);
			setState(704);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACK || _la==AT) {
				{
				setState(703);
				dims();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LastFormalParameterContext extends ParserRuleContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public TerminalNode ELLIPSIS() { return getToken(Java8Parser.ELLIPSIS, 0); }
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public List<TerminalNode> FINAL() { return getTokens(Java8Parser.FINAL); }
		public TerminalNode FINAL(int i) {
			return getToken(Java8Parser.FINAL, i);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public DimsContext dims() {
			return getRuleContext(DimsContext.class,0);
		}
		public FormalParameterContext formalParameter() {
			return getRuleContext(FormalParameterContext.class,0);
		}
		public LastFormalParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lastFormalParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterLastFormalParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitLastFormalParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitLastFormalParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LastFormalParameterContext lastFormalParameter() throws RecognitionException {
		LastFormalParameterContext _localctx = new LastFormalParameterContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_lastFormalParameter);
		int _la;
		try {
			setState(726);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,76,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(710);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==FINAL || _la==AT) {
					{
					setState(708);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case FINAL:
						{
						setState(706);
						match(FINAL);
						}
						break;
					case AT:
						{
						setState(707);
						annotation();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(712);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(713);
				baseType();
				setState(717);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(714);
					annotation();
					}
					}
					setState(719);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(720);
				match(ELLIPSIS);
				setState(721);
				match(Identifier);
				setState(723);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LBRACK || _la==AT) {
					{
					setState(722);
					dims();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(725);
				formalParameter();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReceiverParameterContext extends ParserRuleContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public TerminalNode THIS() { return getToken(Java8Parser.THIS, 0); }
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public ReceiverParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_receiverParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterReceiverParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitReceiverParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitReceiverParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReceiverParameterContext receiverParameter() throws RecognitionException {
		ReceiverParameterContext _localctx = new ReceiverParameterContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_receiverParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(731);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(728);
				annotation();
				}
				}
				setState(733);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(734);
			baseType();
			setState(737);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(735);
				match(Identifier);
				setState(736);
				match(DOT);
				}
			}

			setState(739);
			match(THIS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Throws_Context extends ParserRuleContext {
		public TerminalNode THROWS() { return getToken(Java8Parser.THROWS, 0); }
		public ExceptionTypeListContext exceptionTypeList() {
			return getRuleContext(ExceptionTypeListContext.class,0);
		}
		public Throws_Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_throws_; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterThrows_(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitThrows_(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitThrows_(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Throws_Context throws_() throws RecognitionException {
		Throws_Context _localctx = new Throws_Context(_ctx, getState());
		enterRule(_localctx, 86, RULE_throws_);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(741);
			match(THROWS);
			setState(742);
			exceptionTypeList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExceptionTypeListContext extends ParserRuleContext {
		public List<ExceptionTypeContext> exceptionType() {
			return getRuleContexts(ExceptionTypeContext.class);
		}
		public ExceptionTypeContext exceptionType(int i) {
			return getRuleContext(ExceptionTypeContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Java8Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Java8Parser.COMMA, i);
		}
		public ExceptionTypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exceptionTypeList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterExceptionTypeList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitExceptionTypeList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitExceptionTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExceptionTypeListContext exceptionTypeList() throws RecognitionException {
		ExceptionTypeListContext _localctx = new ExceptionTypeListContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_exceptionTypeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(744);
			exceptionType();
			setState(749);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(745);
				match(COMMA);
				setState(746);
				exceptionType();
				}
				}
				setState(751);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExceptionTypeContext extends ParserRuleContext {
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public ExceptionTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exceptionType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterExceptionType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitExceptionType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitExceptionType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExceptionTypeContext exceptionType() throws RecognitionException {
		ExceptionTypeContext _localctx = new ExceptionTypeContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_exceptionType);
		int _la;
		try {
			int _alt;
			setState(766);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,82,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(755);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,80,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(752);
						annotation();
						}
						} 
					}
					setState(757);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,80,_ctx);
				}
				setState(758);
				classType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(762);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(759);
					annotation();
					}
					}
					setState(764);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(765);
				match(Identifier);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodBodyContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public MethodBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterMethodBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitMethodBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitMethodBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodBodyContext methodBody() throws RecognitionException {
		MethodBodyContext _localctx = new MethodBodyContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_methodBody);
		try {
			setState(770);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(768);
				block();
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 2);
				{
				setState(769);
				match(SEMI);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorDeclarationContext extends ParserRuleContext {
		public ConstructorDeclaratorContext constructorDeclarator() {
			return getRuleContext(ConstructorDeclaratorContext.class,0);
		}
		public ConstructorBodyContext constructorBody() {
			return getRuleContext(ConstructorBodyContext.class,0);
		}
		public List<ConstructorModifierContext> constructorModifier() {
			return getRuleContexts(ConstructorModifierContext.class);
		}
		public ConstructorModifierContext constructorModifier(int i) {
			return getRuleContext(ConstructorModifierContext.class,i);
		}
		public Throws_Context throws_() {
			return getRuleContext(Throws_Context.class,0);
		}
		public ConstructorDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterConstructorDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitConstructorDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitConstructorDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDeclarationContext constructorDeclaration() throws RecognitionException {
		ConstructorDeclarationContext _localctx = new ConstructorDeclarationContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_constructorDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(775);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC))) != 0) || _la==AT) {
				{
				{
				setState(772);
				constructorModifier();
				}
				}
				setState(777);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(778);
			constructorDeclarator();
			setState(780);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==THROWS) {
				{
				setState(779);
				throws_();
				}
			}

			setState(782);
			constructorBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorModifierContext extends ParserRuleContext {
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public TerminalNode PUBLIC() { return getToken(Java8Parser.PUBLIC, 0); }
		public TerminalNode PROTECTED() { return getToken(Java8Parser.PROTECTED, 0); }
		public TerminalNode PRIVATE() { return getToken(Java8Parser.PRIVATE, 0); }
		public ConstructorModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterConstructorModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitConstructorModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitConstructorModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorModifierContext constructorModifier() throws RecognitionException {
		ConstructorModifierContext _localctx = new ConstructorModifierContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_constructorModifier);
		try {
			setState(788);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(784);
				annotation();
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 2);
				{
				setState(785);
				match(PUBLIC);
				}
				break;
			case PROTECTED:
				enterOuterAlt(_localctx, 3);
				{
				setState(786);
				match(PROTECTED);
				}
				break;
			case PRIVATE:
				enterOuterAlt(_localctx, 4);
				{
				setState(787);
				match(PRIVATE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorDeclaratorContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public FormalParameterListContext formalParameterList() {
			return getRuleContext(FormalParameterListContext.class,0);
		}
		public ConstructorDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDeclarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterConstructorDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitConstructorDeclarator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitConstructorDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDeclaratorContext constructorDeclarator() throws RecognitionException {
		ConstructorDeclaratorContext _localctx = new ConstructorDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_constructorDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(791);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(790);
				typeParameters();
				}
			}

			setState(793);
			match(Identifier);
			setState(794);
			match(LPAREN);
			setState(796);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FINAL) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << SHORT))) != 0) || _la==AT || _la==Identifier) {
				{
				setState(795);
				formalParameterList();
				}
			}

			setState(798);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorBodyContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(Java8Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(Java8Parser.RBRACE, 0); }
		public ExplicitConstructorInvocationContext explicitConstructorInvocation() {
			return getRuleContext(ExplicitConstructorInvocationContext.class,0);
		}
		public List<BlockStatementContext> blockStatement() {
			return getRuleContexts(BlockStatementContext.class);
		}
		public BlockStatementContext blockStatement(int i) {
			return getRuleContext(BlockStatementContext.class,i);
		}
		public ConstructorBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterConstructorBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitConstructorBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitConstructorBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorBodyContext constructorBody() throws RecognitionException {
		ConstructorBodyContext _localctx = new ConstructorBodyContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_constructorBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(800);
			match(LBRACE);
			setState(802);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,89,_ctx) ) {
			case 1:
				{
				setState(801);
				explicitConstructorInvocation();
				}
				break;
			}
			setState(807);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << ASSERT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CHAR) | (1L << CLASS) | (1L << CONTINUE) | (1L << DO) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << INT) | (1L << INTERFACE) | (1L << LONG) | (1L << NEW) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << STRICTFP) | (1L << SUPER) | (1L << SWITCH) | (1L << SYNCHRONIZED) | (1L << THIS) | (1L << THROW) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN) | (1L << LBRACE) | (1L << SEMI))) != 0) || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (INC - 80)) | (1L << (DEC - 80)) | (1L << (AT - 80)) | (1L << (Identifier - 80)))) != 0)) {
				{
				{
				setState(804);
				blockStatement();
				}
				}
				setState(809);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(810);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExplicitConstructorInvocationContext extends ParserRuleContext {
		public TerminalNode THIS() { return getToken(Java8Parser.THIS, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public TerminalNode SUPER() { return getToken(Java8Parser.SUPER, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public ExplicitConstructorInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_explicitConstructorInvocation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterExplicitConstructorInvocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitExplicitConstructorInvocation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitExplicitConstructorInvocation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExplicitConstructorInvocationContext explicitConstructorInvocation() throws RecognitionException {
		ExplicitConstructorInvocationContext _localctx = new ExplicitConstructorInvocationContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_explicitConstructorInvocation);
		int _la;
		try {
			setState(858);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,99,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(813);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(812);
					typeArguments();
					}
				}

				setState(815);
				match(THIS);
				setState(816);
				match(LPAREN);
				setState(818);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(817);
					argumentList();
					}
				}

				setState(820);
				match(RPAREN);
				setState(821);
				match(SEMI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(823);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(822);
					typeArguments();
					}
				}

				setState(825);
				match(SUPER);
				setState(826);
				match(LPAREN);
				setState(828);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(827);
					argumentList();
					}
				}

				setState(830);
				match(RPAREN);
				setState(831);
				match(SEMI);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(832);
				qualifiedName();
				setState(833);
				match(DOT);
				setState(835);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(834);
					typeArguments();
					}
				}

				setState(837);
				match(SUPER);
				setState(838);
				match(LPAREN);
				setState(840);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(839);
					argumentList();
					}
				}

				setState(842);
				match(RPAREN);
				setState(843);
				match(SEMI);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(845);
				primary();
				setState(846);
				match(DOT);
				setState(848);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(847);
					typeArguments();
					}
				}

				setState(850);
				match(SUPER);
				setState(851);
				match(LPAREN);
				setState(853);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(852);
					argumentList();
					}
				}

				setState(855);
				match(RPAREN);
				setState(856);
				match(SEMI);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumDeclarationContext extends ParserRuleContext {
		public TerminalNode ENUM() { return getToken(Java8Parser.ENUM, 0); }
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public EnumBodyContext enumBody() {
			return getRuleContext(EnumBodyContext.class,0);
		}
		public List<ClassModifierContext> classModifier() {
			return getRuleContexts(ClassModifierContext.class);
		}
		public ClassModifierContext classModifier(int i) {
			return getRuleContext(ClassModifierContext.class,i);
		}
		public TerminalNode IMPLEMENTS() { return getToken(Java8Parser.IMPLEMENTS, 0); }
		public ClassTypeListContext classTypeList() {
			return getRuleContext(ClassTypeListContext.class,0);
		}
		public EnumDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterEnumDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitEnumDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitEnumDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumDeclarationContext enumDeclaration() throws RecognitionException {
		EnumDeclarationContext _localctx = new EnumDeclarationContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_enumDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(863);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << STRICTFP))) != 0) || _la==AT) {
				{
				{
				setState(860);
				classModifier();
				}
				}
				setState(865);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(866);
			match(ENUM);
			setState(867);
			match(Identifier);
			setState(870);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(868);
				match(IMPLEMENTS);
				setState(869);
				classTypeList();
				}
			}

			setState(872);
			enumBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumBodyContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(Java8Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(Java8Parser.RBRACE, 0); }
		public EnumConstantListContext enumConstantList() {
			return getRuleContext(EnumConstantListContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(Java8Parser.COMMA, 0); }
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public List<ClassBodyDeclarationContext> classBodyDeclaration() {
			return getRuleContexts(ClassBodyDeclarationContext.class);
		}
		public ClassBodyDeclarationContext classBodyDeclaration(int i) {
			return getRuleContext(ClassBodyDeclarationContext.class,i);
		}
		public EnumBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterEnumBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitEnumBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitEnumBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumBodyContext enumBody() throws RecognitionException {
		EnumBodyContext _localctx = new EnumBodyContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_enumBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(874);
			match(LBRACE);
			setState(876);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AT || _la==Identifier) {
				{
				setState(875);
				enumConstantList();
				}
			}

			setState(879);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(878);
				match(COMMA);
				}
			}

			setState(888);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(881);
				match(SEMI);
				setState(885);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << CLASS) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FLOAT) | (1L << INT) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SHORT) | (1L << STATIC) | (1L << STRICTFP) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << VOID) | (1L << VOLATILE) | (1L << LBRACE) | (1L << SEMI))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (LT - 68)) | (1L << (AT - 68)) | (1L << (Identifier - 68)))) != 0)) {
					{
					{
					setState(882);
					classBodyDeclaration();
					}
					}
					setState(887);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(890);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumConstantListContext extends ParserRuleContext {
		public List<EnumConstantContext> enumConstant() {
			return getRuleContexts(EnumConstantContext.class);
		}
		public EnumConstantContext enumConstant(int i) {
			return getRuleContext(EnumConstantContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Java8Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Java8Parser.COMMA, i);
		}
		public EnumConstantListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumConstantList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterEnumConstantList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitEnumConstantList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitEnumConstantList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumConstantListContext enumConstantList() throws RecognitionException {
		EnumConstantListContext _localctx = new EnumConstantListContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_enumConstantList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(892);
			enumConstant();
			setState(897);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,106,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(893);
					match(COMMA);
					setState(894);
					enumConstant();
					}
					} 
				}
				setState(899);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,106,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumConstantContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public EnumConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumConstant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterEnumConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitEnumConstant(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitEnumConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumConstantContext enumConstant() throws RecognitionException {
		EnumConstantContext _localctx = new EnumConstantContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_enumConstant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(903);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(900);
				annotation();
				}
				}
				setState(905);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(906);
			match(Identifier);
			setState(912);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(907);
				match(LPAREN);
				setState(909);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(908);
					argumentList();
					}
				}

				setState(911);
				match(RPAREN);
				}
			}

			setState(915);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACE) {
				{
				setState(914);
				classBody();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceDeclarationContext extends ParserRuleContext {
		public TerminalNode INTERFACE() { return getToken(Java8Parser.INTERFACE, 0); }
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public InterfaceBodyContext interfaceBody() {
			return getRuleContext(InterfaceBodyContext.class,0);
		}
		public List<ClassModifierContext> classModifier() {
			return getRuleContexts(ClassModifierContext.class);
		}
		public ClassModifierContext classModifier(int i) {
			return getRuleContext(ClassModifierContext.class,i);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public TerminalNode EXTENDS() { return getToken(Java8Parser.EXTENDS, 0); }
		public ClassTypeListContext classTypeList() {
			return getRuleContext(ClassTypeListContext.class,0);
		}
		public InterfaceDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterInterfaceDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitInterfaceDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitInterfaceDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceDeclarationContext interfaceDeclaration() throws RecognitionException {
		InterfaceDeclarationContext _localctx = new InterfaceDeclarationContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_interfaceDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(920);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << STRICTFP))) != 0) || _la==AT) {
				{
				{
				setState(917);
				classModifier();
				}
				}
				setState(922);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(923);
			match(INTERFACE);
			setState(924);
			match(Identifier);
			setState(926);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(925);
				typeParameters();
				}
			}

			setState(930);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(928);
				match(EXTENDS);
				setState(929);
				classTypeList();
				}
			}

			setState(932);
			interfaceBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceBodyContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(Java8Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(Java8Parser.RBRACE, 0); }
		public List<InterfaceMemberDeclarationContext> interfaceMemberDeclaration() {
			return getRuleContexts(InterfaceMemberDeclarationContext.class);
		}
		public InterfaceMemberDeclarationContext interfaceMemberDeclaration(int i) {
			return getRuleContext(InterfaceMemberDeclarationContext.class,i);
		}
		public InterfaceBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterInterfaceBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitInterfaceBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitInterfaceBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceBodyContext interfaceBody() throws RecognitionException {
		InterfaceBodyContext _localctx = new InterfaceBodyContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_interfaceBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(934);
			match(LBRACE);
			setState(938);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << CLASS) | (1L << DEFAULT) | (1L << DOUBLE) | (1L << FINAL) | (1L << FLOAT) | (1L << INT) | (1L << INTERFACE) | (1L << LONG) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SHORT) | (1L << STATIC) | (1L << STRICTFP) | (1L << VOID) | (1L << SEMI))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (LT - 68)) | (1L << (AT - 68)) | (1L << (Identifier - 68)))) != 0)) {
				{
				{
				setState(935);
				interfaceMemberDeclaration();
				}
				}
				setState(940);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(941);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceMemberDeclarationContext extends ParserRuleContext {
		public ConstantDeclarationContext constantDeclaration() {
			return getRuleContext(ConstantDeclarationContext.class,0);
		}
		public InterfaceMethodDeclarationContext interfaceMethodDeclaration() {
			return getRuleContext(InterfaceMethodDeclarationContext.class,0);
		}
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public InterfaceMemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceMemberDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterInterfaceMemberDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitInterfaceMemberDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitInterfaceMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceMemberDeclarationContext interfaceMemberDeclaration() throws RecognitionException {
		InterfaceMemberDeclarationContext _localctx = new InterfaceMemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_interfaceMemberDeclaration);
		try {
			setState(948);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,115,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(943);
				constantDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(944);
				interfaceMethodDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(945);
				classDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(946);
				interfaceDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(947);
				match(SEMI);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantDeclarationContext extends ParserRuleContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public VariableDeclaratorListContext variableDeclaratorList() {
			return getRuleContext(VariableDeclaratorListContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public List<ConstantModifierContext> constantModifier() {
			return getRuleContexts(ConstantModifierContext.class);
		}
		public ConstantModifierContext constantModifier(int i) {
			return getRuleContext(ConstantModifierContext.class,i);
		}
		public ConstantDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterConstantDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitConstantDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitConstantDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantDeclarationContext constantDeclaration() throws RecognitionException {
		ConstantDeclarationContext _localctx = new ConstantDeclarationContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_constantDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(953);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FINAL) | (1L << PUBLIC) | (1L << STATIC))) != 0) || _la==AT) {
				{
				{
				setState(950);
				constantModifier();
				}
				}
				setState(955);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(956);
			baseType();
			setState(957);
			variableDeclaratorList();
			setState(958);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantModifierContext extends ParserRuleContext {
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public TerminalNode PUBLIC() { return getToken(Java8Parser.PUBLIC, 0); }
		public TerminalNode STATIC() { return getToken(Java8Parser.STATIC, 0); }
		public TerminalNode FINAL() { return getToken(Java8Parser.FINAL, 0); }
		public ConstantModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterConstantModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitConstantModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitConstantModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantModifierContext constantModifier() throws RecognitionException {
		ConstantModifierContext _localctx = new ConstantModifierContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_constantModifier);
		try {
			setState(964);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(960);
				annotation();
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 2);
				{
				setState(961);
				match(PUBLIC);
				}
				break;
			case STATIC:
				enterOuterAlt(_localctx, 3);
				{
				setState(962);
				match(STATIC);
				}
				break;
			case FINAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(963);
				match(FINAL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceMethodDeclarationContext extends ParserRuleContext {
		public MethodHeaderContext methodHeader() {
			return getRuleContext(MethodHeaderContext.class,0);
		}
		public MethodBodyContext methodBody() {
			return getRuleContext(MethodBodyContext.class,0);
		}
		public List<InterfaceMethodModifierContext> interfaceMethodModifier() {
			return getRuleContexts(InterfaceMethodModifierContext.class);
		}
		public InterfaceMethodModifierContext interfaceMethodModifier(int i) {
			return getRuleContext(InterfaceMethodModifierContext.class,i);
		}
		public InterfaceMethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceMethodDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterInterfaceMethodDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitInterfaceMethodDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitInterfaceMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceMethodDeclarationContext interfaceMethodDeclaration() throws RecognitionException {
		InterfaceMethodDeclarationContext _localctx = new InterfaceMethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_interfaceMethodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(969);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << DEFAULT) | (1L << PUBLIC) | (1L << STATIC) | (1L << STRICTFP))) != 0) || _la==AT) {
				{
				{
				setState(966);
				interfaceMethodModifier();
				}
				}
				setState(971);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(972);
			methodHeader();
			setState(973);
			methodBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceMethodModifierContext extends ParserRuleContext {
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public TerminalNode PUBLIC() { return getToken(Java8Parser.PUBLIC, 0); }
		public TerminalNode ABSTRACT() { return getToken(Java8Parser.ABSTRACT, 0); }
		public TerminalNode DEFAULT() { return getToken(Java8Parser.DEFAULT, 0); }
		public TerminalNode STATIC() { return getToken(Java8Parser.STATIC, 0); }
		public TerminalNode STRICTFP() { return getToken(Java8Parser.STRICTFP, 0); }
		public InterfaceMethodModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceMethodModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterInterfaceMethodModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitInterfaceMethodModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitInterfaceMethodModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceMethodModifierContext interfaceMethodModifier() throws RecognitionException {
		InterfaceMethodModifierContext _localctx = new InterfaceMethodModifierContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_interfaceMethodModifier);
		try {
			setState(981);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(975);
				annotation();
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 2);
				{
				setState(976);
				match(PUBLIC);
				}
				break;
			case ABSTRACT:
				enterOuterAlt(_localctx, 3);
				{
				setState(977);
				match(ABSTRACT);
				}
				break;
			case DEFAULT:
				enterOuterAlt(_localctx, 4);
				{
				setState(978);
				match(DEFAULT);
				}
				break;
			case STATIC:
				enterOuterAlt(_localctx, 5);
				{
				setState(979);
				match(STATIC);
				}
				break;
			case STRICTFP:
				enterOuterAlt(_localctx, 6);
				{
				setState(980);
				match(STRICTFP);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotationTypeDeclarationContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(Java8Parser.AT, 0); }
		public TerminalNode INTERFACE() { return getToken(Java8Parser.INTERFACE, 0); }
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public AnnotationTypeBodyContext annotationTypeBody() {
			return getRuleContext(AnnotationTypeBodyContext.class,0);
		}
		public List<ClassModifierContext> classModifier() {
			return getRuleContexts(ClassModifierContext.class);
		}
		public ClassModifierContext classModifier(int i) {
			return getRuleContext(ClassModifierContext.class,i);
		}
		public AnnotationTypeDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotationTypeDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterAnnotationTypeDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitAnnotationTypeDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitAnnotationTypeDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationTypeDeclarationContext annotationTypeDeclaration() throws RecognitionException {
		AnnotationTypeDeclarationContext _localctx = new AnnotationTypeDeclarationContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_annotationTypeDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(986);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,120,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(983);
					classModifier();
					}
					} 
				}
				setState(988);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,120,_ctx);
			}
			setState(989);
			match(AT);
			setState(990);
			match(INTERFACE);
			setState(991);
			match(Identifier);
			setState(992);
			annotationTypeBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotationTypeBodyContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(Java8Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(Java8Parser.RBRACE, 0); }
		public List<AnnotationTypeMemberDeclarationContext> annotationTypeMemberDeclaration() {
			return getRuleContexts(AnnotationTypeMemberDeclarationContext.class);
		}
		public AnnotationTypeMemberDeclarationContext annotationTypeMemberDeclaration(int i) {
			return getRuleContext(AnnotationTypeMemberDeclarationContext.class,i);
		}
		public AnnotationTypeBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotationTypeBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterAnnotationTypeBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitAnnotationTypeBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitAnnotationTypeBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationTypeBodyContext annotationTypeBody() throws RecognitionException {
		AnnotationTypeBodyContext _localctx = new AnnotationTypeBodyContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_annotationTypeBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(994);
			match(LBRACE);
			setState(998);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << CLASS) | (1L << DOUBLE) | (1L << FINAL) | (1L << FLOAT) | (1L << INT) | (1L << INTERFACE) | (1L << LONG) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SHORT) | (1L << STATIC) | (1L << STRICTFP) | (1L << SEMI))) != 0) || _la==AT || _la==Identifier) {
				{
				{
				setState(995);
				annotationTypeMemberDeclaration();
				}
				}
				setState(1000);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1001);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotationTypeMemberDeclarationContext extends ParserRuleContext {
		public AnnotationTypeElementDeclarationContext annotationTypeElementDeclaration() {
			return getRuleContext(AnnotationTypeElementDeclarationContext.class,0);
		}
		public ConstantDeclarationContext constantDeclaration() {
			return getRuleContext(ConstantDeclarationContext.class,0);
		}
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public AnnotationTypeMemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotationTypeMemberDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterAnnotationTypeMemberDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitAnnotationTypeMemberDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitAnnotationTypeMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationTypeMemberDeclarationContext annotationTypeMemberDeclaration() throws RecognitionException {
		AnnotationTypeMemberDeclarationContext _localctx = new AnnotationTypeMemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_annotationTypeMemberDeclaration);
		try {
			setState(1008);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,122,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1003);
				annotationTypeElementDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1004);
				constantDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1005);
				classDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1006);
				interfaceDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1007);
				match(SEMI);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotationTypeElementDeclarationContext extends ParserRuleContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public DimsContext dims() {
			return getRuleContext(DimsContext.class,0);
		}
		public DefaultValueContext defaultValue() {
			return getRuleContext(DefaultValueContext.class,0);
		}
		public List<TerminalNode> PUBLIC() { return getTokens(Java8Parser.PUBLIC); }
		public TerminalNode PUBLIC(int i) {
			return getToken(Java8Parser.PUBLIC, i);
		}
		public List<TerminalNode> ABSTRACT() { return getTokens(Java8Parser.ABSTRACT); }
		public TerminalNode ABSTRACT(int i) {
			return getToken(Java8Parser.ABSTRACT, i);
		}
		public AnnotationTypeElementDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotationTypeElementDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterAnnotationTypeElementDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitAnnotationTypeElementDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitAnnotationTypeElementDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationTypeElementDeclarationContext annotationTypeElementDeclaration() throws RecognitionException {
		AnnotationTypeElementDeclarationContext _localctx = new AnnotationTypeElementDeclarationContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_annotationTypeElementDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1013);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ABSTRACT || _la==PUBLIC) {
				{
				{
				setState(1010);
				_la = _input.LA(1);
				if ( !(_la==ABSTRACT || _la==PUBLIC) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(1015);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1019);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(1016);
				annotation();
				}
				}
				setState(1021);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1022);
			baseType();
			setState(1023);
			match(Identifier);
			setState(1024);
			match(LPAREN);
			setState(1025);
			match(RPAREN);
			setState(1027);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACK || _la==AT) {
				{
				setState(1026);
				dims();
				}
			}

			setState(1030);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEFAULT) {
				{
				setState(1029);
				defaultValue();
				}
			}

			setState(1032);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefaultValueContext extends ParserRuleContext {
		public TerminalNode DEFAULT() { return getToken(Java8Parser.DEFAULT, 0); }
		public ElementValueContext elementValue() {
			return getRuleContext(ElementValueContext.class,0);
		}
		public DefaultValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterDefaultValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitDefaultValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitDefaultValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultValueContext defaultValue() throws RecognitionException {
		DefaultValueContext _localctx = new DefaultValueContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_defaultValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1034);
			match(DEFAULT);
			setState(1035);
			elementValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotationContext extends ParserRuleContext {
		public NormalAnnotationContext normalAnnotation() {
			return getRuleContext(NormalAnnotationContext.class,0);
		}
		public MarkerAnnotationContext markerAnnotation() {
			return getRuleContext(MarkerAnnotationContext.class,0);
		}
		public SingleElementAnnotationContext singleElementAnnotation() {
			return getRuleContext(SingleElementAnnotationContext.class,0);
		}
		public AnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationContext annotation() throws RecognitionException {
		AnnotationContext _localctx = new AnnotationContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_annotation);
		try {
			setState(1040);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,127,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1037);
				normalAnnotation();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1038);
				markerAnnotation();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1039);
				singleElementAnnotation();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NormalAnnotationContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(Java8Parser.AT, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public ElementValuePairListContext elementValuePairList() {
			return getRuleContext(ElementValuePairListContext.class,0);
		}
		public NormalAnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_normalAnnotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterNormalAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitNormalAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitNormalAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NormalAnnotationContext normalAnnotation() throws RecognitionException {
		NormalAnnotationContext _localctx = new NormalAnnotationContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_normalAnnotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1042);
			match(AT);
			setState(1043);
			qualifiedName();
			setState(1044);
			match(LPAREN);
			setState(1046);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(1045);
				elementValuePairList();
				}
			}

			setState(1048);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementValuePairListContext extends ParserRuleContext {
		public List<ElementValuePairContext> elementValuePair() {
			return getRuleContexts(ElementValuePairContext.class);
		}
		public ElementValuePairContext elementValuePair(int i) {
			return getRuleContext(ElementValuePairContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Java8Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Java8Parser.COMMA, i);
		}
		public ElementValuePairListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValuePairList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterElementValuePairList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitElementValuePairList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitElementValuePairList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValuePairListContext elementValuePairList() throws RecognitionException {
		ElementValuePairListContext _localctx = new ElementValuePairListContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_elementValuePairList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1050);
			elementValuePair();
			setState(1055);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1051);
				match(COMMA);
				setState(1052);
				elementValuePair();
				}
				}
				setState(1057);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementValuePairContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode ASSIGN() { return getToken(Java8Parser.ASSIGN, 0); }
		public ElementValueContext elementValue() {
			return getRuleContext(ElementValueContext.class,0);
		}
		public ElementValuePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValuePair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterElementValuePair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitElementValuePair(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitElementValuePair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValuePairContext elementValuePair() throws RecognitionException {
		ElementValuePairContext _localctx = new ElementValuePairContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_elementValuePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1058);
			match(Identifier);
			setState(1059);
			match(ASSIGN);
			setState(1060);
			elementValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementValueContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ElementValueArrayInitializerContext elementValueArrayInitializer() {
			return getRuleContext(ElementValueArrayInitializerContext.class,0);
		}
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public ElementValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterElementValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitElementValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitElementValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValueContext elementValue() throws RecognitionException {
		ElementValueContext _localctx = new ElementValueContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_elementValue);
		try {
			setState(1065);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,130,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1062);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1063);
				elementValueArrayInitializer();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1064);
				annotation();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementValueArrayInitializerContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(Java8Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(Java8Parser.RBRACE, 0); }
		public ElementValueListContext elementValueList() {
			return getRuleContext(ElementValueListContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(Java8Parser.COMMA, 0); }
		public ElementValueArrayInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValueArrayInitializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterElementValueArrayInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitElementValueArrayInitializer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitElementValueArrayInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValueArrayInitializerContext elementValueArrayInitializer() throws RecognitionException {
		ElementValueArrayInitializerContext _localctx = new ElementValueArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_elementValueArrayInitializer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1067);
			match(LBRACE);
			setState(1069);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN) | (1L << LBRACE))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
				{
				setState(1068);
				elementValueList();
				}
			}

			setState(1072);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(1071);
				match(COMMA);
				}
			}

			setState(1074);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementValueListContext extends ParserRuleContext {
		public List<ElementValueContext> elementValue() {
			return getRuleContexts(ElementValueContext.class);
		}
		public ElementValueContext elementValue(int i) {
			return getRuleContext(ElementValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Java8Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Java8Parser.COMMA, i);
		}
		public ElementValueListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValueList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterElementValueList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitElementValueList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitElementValueList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValueListContext elementValueList() throws RecognitionException {
		ElementValueListContext _localctx = new ElementValueListContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_elementValueList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1076);
			elementValue();
			setState(1081);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,133,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1077);
					match(COMMA);
					setState(1078);
					elementValue();
					}
					} 
				}
				setState(1083);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,133,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MarkerAnnotationContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(Java8Parser.AT, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public MarkerAnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_markerAnnotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterMarkerAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitMarkerAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitMarkerAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MarkerAnnotationContext markerAnnotation() throws RecognitionException {
		MarkerAnnotationContext _localctx = new MarkerAnnotationContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_markerAnnotation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1084);
			match(AT);
			setState(1085);
			qualifiedName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleElementAnnotationContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(Java8Parser.AT, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public ElementValueContext elementValue() {
			return getRuleContext(ElementValueContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public SingleElementAnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleElementAnnotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterSingleElementAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitSingleElementAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitSingleElementAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleElementAnnotationContext singleElementAnnotation() throws RecognitionException {
		SingleElementAnnotationContext _localctx = new SingleElementAnnotationContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_singleElementAnnotation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1087);
			match(AT);
			setState(1088);
			qualifiedName();
			setState(1089);
			match(LPAREN);
			setState(1090);
			elementValue();
			setState(1091);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayInitializerContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(Java8Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(Java8Parser.RBRACE, 0); }
		public VariableInitializerListContext variableInitializerList() {
			return getRuleContext(VariableInitializerListContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(Java8Parser.COMMA, 0); }
		public ArrayInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayInitializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterArrayInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitArrayInitializer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitArrayInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayInitializerContext arrayInitializer() throws RecognitionException {
		ArrayInitializerContext _localctx = new ArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_arrayInitializer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1093);
			match(LBRACE);
			setState(1095);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN) | (1L << LBRACE))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
				{
				setState(1094);
				variableInitializerList();
				}
			}

			setState(1098);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(1097);
				match(COMMA);
				}
			}

			setState(1100);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableInitializerListContext extends ParserRuleContext {
		public List<VariableInitializerContext> variableInitializer() {
			return getRuleContexts(VariableInitializerContext.class);
		}
		public VariableInitializerContext variableInitializer(int i) {
			return getRuleContext(VariableInitializerContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Java8Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Java8Parser.COMMA, i);
		}
		public VariableInitializerListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableInitializerList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterVariableInitializerList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitVariableInitializerList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitVariableInitializerList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableInitializerListContext variableInitializerList() throws RecognitionException {
		VariableInitializerListContext _localctx = new VariableInitializerListContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_variableInitializerList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1102);
			variableInitializer();
			setState(1107);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,136,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1103);
					match(COMMA);
					setState(1104);
					variableInitializer();
					}
					} 
				}
				setState(1109);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,136,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(Java8Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(Java8Parser.RBRACE, 0); }
		public List<BlockStatementContext> blockStatement() {
			return getRuleContexts(BlockStatementContext.class);
		}
		public BlockStatementContext blockStatement(int i) {
			return getRuleContext(BlockStatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1110);
			match(LBRACE);
			setState(1114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << ASSERT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CHAR) | (1L << CLASS) | (1L << CONTINUE) | (1L << DO) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << INT) | (1L << INTERFACE) | (1L << LONG) | (1L << NEW) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << STRICTFP) | (1L << SUPER) | (1L << SWITCH) | (1L << SYNCHRONIZED) | (1L << THIS) | (1L << THROW) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN) | (1L << LBRACE) | (1L << SEMI))) != 0) || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (INC - 80)) | (1L << (DEC - 80)) | (1L << (AT - 80)) | (1L << (Identifier - 80)))) != 0)) {
				{
				{
				setState(1111);
				blockStatement();
				}
				}
				setState(1116);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1117);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockStatementContext extends ParserRuleContext {
		public LocalVariableDeclarationContext localVariableDeclaration() {
			return getRuleContext(LocalVariableDeclarationContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public BlockStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterBlockStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitBlockStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitBlockStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockStatementContext blockStatement() throws RecognitionException {
		BlockStatementContext _localctx = new BlockStatementContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_blockStatement);
		try {
			setState(1126);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,138,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1119);
				localVariableDeclaration();
				setState(1120);
				match(SEMI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1122);
				classDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1123);
				enumDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1124);
				interfaceDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1125);
				statement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LocalVariableDeclarationContext extends ParserRuleContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public VariableDeclaratorListContext variableDeclaratorList() {
			return getRuleContext(VariableDeclaratorListContext.class,0);
		}
		public List<TerminalNode> FINAL() { return getTokens(Java8Parser.FINAL); }
		public TerminalNode FINAL(int i) {
			return getToken(Java8Parser.FINAL, i);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public LocalVariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localVariableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterLocalVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitLocalVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitLocalVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LocalVariableDeclarationContext localVariableDeclaration() throws RecognitionException {
		LocalVariableDeclarationContext _localctx = new LocalVariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_localVariableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1132);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==AT) {
				{
				setState(1130);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case FINAL:
					{
					setState(1128);
					match(FINAL);
					}
					break;
				case AT:
					{
					setState(1129);
					annotation();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(1134);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1135);
			baseType();
			setState(1136);
			variableDeclaratorList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public StatementExpressionContext statementExpression() {
			return getRuleContext(StatementExpressionContext.class,0);
		}
		public TerminalNode ASSERT() { return getToken(Java8Parser.ASSERT, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode COLON() { return getToken(Java8Parser.COLON, 0); }
		public TerminalNode SWITCH() { return getToken(Java8Parser.SWITCH, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public SwitchBlockContext switchBlock() {
			return getRuleContext(SwitchBlockContext.class,0);
		}
		public TerminalNode DO() { return getToken(Java8Parser.DO, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode WHILE() { return getToken(Java8Parser.WHILE, 0); }
		public TerminalNode BREAK() { return getToken(Java8Parser.BREAK, 0); }
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode CONTINUE() { return getToken(Java8Parser.CONTINUE, 0); }
		public TerminalNode RETURN() { return getToken(Java8Parser.RETURN, 0); }
		public TerminalNode SYNCHRONIZED() { return getToken(Java8Parser.SYNCHRONIZED, 0); }
		public TerminalNode THROW() { return getToken(Java8Parser.THROW, 0); }
		public TryStatementContext tryStatement() {
			return getRuleContext(TryStatementContext.class,0);
		}
		public TerminalNode IF() { return getToken(Java8Parser.IF, 0); }
		public TerminalNode ELSE() { return getToken(Java8Parser.ELSE, 0); }
		public TerminalNode FOR() { return getToken(Java8Parser.FOR, 0); }
		public ForControlContext forControl() {
			return getRuleContext(ForControlContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_statement);
		int _la;
		try {
			setState(1215);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,146,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1138);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1139);
				match(SEMI);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1140);
				statementExpression();
				setState(1141);
				match(SEMI);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1143);
				match(ASSERT);
				setState(1144);
				expression(0);
				setState(1147);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COLON) {
					{
					setState(1145);
					match(COLON);
					setState(1146);
					expression(0);
					}
				}

				setState(1149);
				match(SEMI);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1151);
				match(SWITCH);
				setState(1152);
				match(LPAREN);
				setState(1153);
				expression(0);
				setState(1154);
				match(RPAREN);
				setState(1155);
				switchBlock();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1157);
				match(DO);
				setState(1158);
				statement();
				setState(1159);
				match(WHILE);
				setState(1160);
				match(LPAREN);
				setState(1161);
				expression(0);
				setState(1162);
				match(RPAREN);
				setState(1163);
				match(SEMI);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1165);
				match(BREAK);
				setState(1167);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Identifier) {
					{
					setState(1166);
					match(Identifier);
					}
				}

				setState(1169);
				match(SEMI);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1170);
				match(CONTINUE);
				setState(1172);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Identifier) {
					{
					setState(1171);
					match(Identifier);
					}
				}

				setState(1174);
				match(SEMI);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1175);
				match(RETURN);
				setState(1177);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(1176);
					expression(0);
					}
				}

				setState(1179);
				match(SEMI);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(1180);
				match(SYNCHRONIZED);
				setState(1181);
				match(LPAREN);
				setState(1182);
				expression(0);
				setState(1183);
				match(RPAREN);
				setState(1184);
				block();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(1186);
				match(THROW);
				setState(1187);
				expression(0);
				setState(1188);
				match(SEMI);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(1190);
				tryStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(1191);
				match(Identifier);
				setState(1192);
				match(COLON);
				setState(1193);
				statement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(1194);
				match(IF);
				setState(1195);
				match(LPAREN);
				setState(1196);
				expression(0);
				setState(1197);
				match(RPAREN);
				setState(1198);
				statement();
				setState(1201);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,145,_ctx) ) {
				case 1:
					{
					setState(1199);
					match(ELSE);
					setState(1200);
					statement();
					}
					break;
				}
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(1203);
				match(WHILE);
				setState(1204);
				match(LPAREN);
				setState(1205);
				expression(0);
				setState(1206);
				match(RPAREN);
				setState(1207);
				statement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(1209);
				match(FOR);
				setState(1210);
				match(LPAREN);
				setState(1211);
				forControl();
				setState(1212);
				match(RPAREN);
				setState(1213);
				statement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementExpressionContext extends ParserRuleContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public TerminalNode INC() { return getToken(Java8Parser.INC, 0); }
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public TerminalNode DEC() { return getToken(Java8Parser.DEC, 0); }
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public MethodInvocationContext methodInvocation() {
			return getRuleContext(MethodInvocationContext.class,0);
		}
		public ClassInstanceCreationExpressionContext classInstanceCreationExpression() {
			return getRuleContext(ClassInstanceCreationExpressionContext.class,0);
		}
		public StatementExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterStatementExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitStatementExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitStatementExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementExpressionContext statementExpression() throws RecognitionException {
		StatementExpressionContext _localctx = new StatementExpressionContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_statementExpression);
		try {
			setState(1230);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,147,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1217);
				assignment();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1218);
				match(INC);
				setState(1219);
				unaryExpression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1220);
				match(DEC);
				setState(1221);
				unaryExpression();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1222);
				postfixExpression();
				setState(1223);
				match(INC);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1225);
				postfixExpression();
				setState(1226);
				match(DEC);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1228);
				methodInvocation();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1229);
				classInstanceCreationExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SwitchBlockContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(Java8Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(Java8Parser.RBRACE, 0); }
		public List<SwitchBlockStatementGroupContext> switchBlockStatementGroup() {
			return getRuleContexts(SwitchBlockStatementGroupContext.class);
		}
		public SwitchBlockStatementGroupContext switchBlockStatementGroup(int i) {
			return getRuleContext(SwitchBlockStatementGroupContext.class,i);
		}
		public List<SwitchLabelContext> switchLabel() {
			return getRuleContexts(SwitchLabelContext.class);
		}
		public SwitchLabelContext switchLabel(int i) {
			return getRuleContext(SwitchLabelContext.class,i);
		}
		public SwitchBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterSwitchBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitSwitchBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitSwitchBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchBlockContext switchBlock() throws RecognitionException {
		SwitchBlockContext _localctx = new SwitchBlockContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_switchBlock);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1232);
			match(LBRACE);
			setState(1236);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,148,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1233);
					switchBlockStatementGroup();
					}
					} 
				}
				setState(1238);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,148,_ctx);
			}
			setState(1242);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE || _la==DEFAULT) {
				{
				{
				setState(1239);
				switchLabel();
				}
				}
				setState(1244);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1245);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SwitchBlockStatementGroupContext extends ParserRuleContext {
		public List<SwitchLabelContext> switchLabel() {
			return getRuleContexts(SwitchLabelContext.class);
		}
		public SwitchLabelContext switchLabel(int i) {
			return getRuleContext(SwitchLabelContext.class,i);
		}
		public List<BlockStatementContext> blockStatement() {
			return getRuleContexts(BlockStatementContext.class);
		}
		public BlockStatementContext blockStatement(int i) {
			return getRuleContext(BlockStatementContext.class,i);
		}
		public SwitchBlockStatementGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchBlockStatementGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterSwitchBlockStatementGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitSwitchBlockStatementGroup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitSwitchBlockStatementGroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchBlockStatementGroupContext switchBlockStatementGroup() throws RecognitionException {
		SwitchBlockStatementGroupContext _localctx = new SwitchBlockStatementGroupContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_switchBlockStatementGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1248); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(1247);
				switchLabel();
				}
				}
				setState(1250); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CASE || _la==DEFAULT );
			setState(1253); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(1252);
				blockStatement();
				}
				}
				setState(1255); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << ASSERT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CHAR) | (1L << CLASS) | (1L << CONTINUE) | (1L << DO) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << INT) | (1L << INTERFACE) | (1L << LONG) | (1L << NEW) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << STRICTFP) | (1L << SUPER) | (1L << SWITCH) | (1L << SYNCHRONIZED) | (1L << THIS) | (1L << THROW) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN) | (1L << LBRACE) | (1L << SEMI))) != 0) || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (INC - 80)) | (1L << (DEC - 80)) | (1L << (AT - 80)) | (1L << (Identifier - 80)))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SwitchLabelContext extends ParserRuleContext {
		public TerminalNode CASE() { return getToken(Java8Parser.CASE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode COLON() { return getToken(Java8Parser.COLON, 0); }
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode DEFAULT() { return getToken(Java8Parser.DEFAULT, 0); }
		public SwitchLabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchLabel; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterSwitchLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitSwitchLabel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitSwitchLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchLabelContext switchLabel() throws RecognitionException {
		SwitchLabelContext _localctx = new SwitchLabelContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_switchLabel);
		try {
			setState(1266);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,152,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1257);
				match(CASE);
				setState(1258);
				expression(0);
				setState(1259);
				match(COLON);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1261);
				match(CASE);
				setState(1262);
				match(Identifier);
				setState(1263);
				match(COLON);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1264);
				match(DEFAULT);
				setState(1265);
				match(COLON);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TryStatementContext extends ParserRuleContext {
		public TerminalNode TRY() { return getToken(Java8Parser.TRY, 0); }
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public CatchesContext catches() {
			return getRuleContext(CatchesContext.class,0);
		}
		public TerminalNode FINALLY() { return getToken(Java8Parser.FINALLY, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public ResourceListContext resourceList() {
			return getRuleContext(ResourceListContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public TerminalNode SEMI() { return getToken(Java8Parser.SEMI, 0); }
		public TryStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tryStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterTryStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitTryStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitTryStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TryStatementContext tryStatement() throws RecognitionException {
		TryStatementContext _localctx = new TryStatementContext(_ctx, getState());
		enterRule(_localctx, 174, RULE_tryStatement);
		int _la;
		try {
			setState(1295);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,157,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1268);
				match(TRY);
				setState(1269);
				block();
				setState(1270);
				catches();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1272);
				match(TRY);
				setState(1273);
				block();
				setState(1275);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==CATCH) {
					{
					setState(1274);
					catches();
					}
				}

				setState(1277);
				match(FINALLY);
				setState(1278);
				block();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1280);
				match(TRY);
				setState(1281);
				match(LPAREN);
				setState(1282);
				resourceList();
				setState(1284);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMI) {
					{
					setState(1283);
					match(SEMI);
					}
				}

				setState(1286);
				match(RPAREN);
				setState(1287);
				block();
				setState(1289);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==CATCH) {
					{
					setState(1288);
					catches();
					}
				}

				setState(1293);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FINALLY) {
					{
					setState(1291);
					match(FINALLY);
					setState(1292);
					block();
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForControlContext extends ParserRuleContext {
		public List<TerminalNode> SEMI() { return getTokens(Java8Parser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(Java8Parser.SEMI, i);
		}
		public ForInitContext forInit() {
			return getRuleContext(ForInitContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementExpressionListContext statementExpressionList() {
			return getRuleContext(StatementExpressionListContext.class,0);
		}
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode COLON() { return getToken(Java8Parser.COLON, 0); }
		public TerminalNode FINAL() { return getToken(Java8Parser.FINAL, 0); }
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public DimsContext dims() {
			return getRuleContext(DimsContext.class,0);
		}
		public ForControlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forControl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterForControl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitForControl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitForControl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForControlContext forControl() throws RecognitionException {
		ForControlContext _localctx = new ForControlContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_forControl);
		int _la;
		try {
			setState(1325);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,164,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1298);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FINAL) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (INC - 80)) | (1L << (DEC - 80)) | (1L << (AT - 80)) | (1L << (Identifier - 80)))) != 0)) {
					{
					setState(1297);
					forInit();
					}
				}

				setState(1300);
				match(SEMI);
				setState(1302);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(1301);
					expression(0);
					}
				}

				setState(1304);
				match(SEMI);
				setState(1306);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (INC - 80)) | (1L << (DEC - 80)) | (1L << (AT - 80)) | (1L << (Identifier - 80)))) != 0)) {
					{
					setState(1305);
					statementExpressionList();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1309);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FINAL) {
					{
					setState(1308);
					match(FINAL);
					}
				}

				setState(1314);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(1311);
					annotation();
					}
					}
					setState(1316);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1317);
				baseType();
				setState(1318);
				match(Identifier);
				setState(1320);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LBRACK || _la==AT) {
					{
					setState(1319);
					dims();
					}
				}

				setState(1322);
				match(COLON);
				setState(1323);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForInitContext extends ParserRuleContext {
		public StatementExpressionListContext statementExpressionList() {
			return getRuleContext(StatementExpressionListContext.class,0);
		}
		public LocalVariableDeclarationContext localVariableDeclaration() {
			return getRuleContext(LocalVariableDeclarationContext.class,0);
		}
		public ForInitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterForInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitForInit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitForInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitContext forInit() throws RecognitionException {
		ForInitContext _localctx = new ForInitContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_forInit);
		try {
			setState(1329);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,165,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1327);
				statementExpressionList();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1328);
				localVariableDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementExpressionListContext extends ParserRuleContext {
		public List<StatementExpressionContext> statementExpression() {
			return getRuleContexts(StatementExpressionContext.class);
		}
		public StatementExpressionContext statementExpression(int i) {
			return getRuleContext(StatementExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Java8Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Java8Parser.COMMA, i);
		}
		public StatementExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementExpressionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterStatementExpressionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitStatementExpressionList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitStatementExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementExpressionListContext statementExpressionList() throws RecognitionException {
		StatementExpressionListContext _localctx = new StatementExpressionListContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_statementExpressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1331);
			statementExpression();
			setState(1336);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1332);
				match(COMMA);
				setState(1333);
				statementExpression();
				}
				}
				setState(1338);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CatchesContext extends ParserRuleContext {
		public List<TerminalNode> CATCH() { return getTokens(Java8Parser.CATCH); }
		public TerminalNode CATCH(int i) {
			return getToken(Java8Parser.CATCH, i);
		}
		public List<TerminalNode> LPAREN() { return getTokens(Java8Parser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(Java8Parser.LPAREN, i);
		}
		public List<CatchFormalParameterContext> catchFormalParameter() {
			return getRuleContexts(CatchFormalParameterContext.class);
		}
		public CatchFormalParameterContext catchFormalParameter(int i) {
			return getRuleContext(CatchFormalParameterContext.class,i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(Java8Parser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(Java8Parser.RPAREN, i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public CatchesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catches; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterCatches(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitCatches(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitCatches(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CatchesContext catches() throws RecognitionException {
		CatchesContext _localctx = new CatchesContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_catches);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1345); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(1339);
				match(CATCH);
				setState(1340);
				match(LPAREN);
				setState(1341);
				catchFormalParameter();
				setState(1342);
				match(RPAREN);
				setState(1343);
				block();
				}
				}
				setState(1347); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CATCH );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CatchFormalParameterContext extends ParserRuleContext {
		public CatchTypeContext catchType() {
			return getRuleContext(CatchTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode FINAL() { return getToken(Java8Parser.FINAL, 0); }
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public DimsContext dims() {
			return getRuleContext(DimsContext.class,0);
		}
		public CatchFormalParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catchFormalParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterCatchFormalParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitCatchFormalParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitCatchFormalParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CatchFormalParameterContext catchFormalParameter() throws RecognitionException {
		CatchFormalParameterContext _localctx = new CatchFormalParameterContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_catchFormalParameter);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1350);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FINAL) {
				{
				setState(1349);
				match(FINAL);
				}
			}

			setState(1355);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,169,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1352);
					annotation();
					}
					} 
				}
				setState(1357);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,169,_ctx);
			}
			setState(1358);
			catchType();
			setState(1359);
			match(Identifier);
			setState(1361);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACK || _la==AT) {
				{
				setState(1360);
				dims();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CatchTypeContext extends ParserRuleContext {
		public List<ClassTypeContext> classType() {
			return getRuleContexts(ClassTypeContext.class);
		}
		public ClassTypeContext classType(int i) {
			return getRuleContext(ClassTypeContext.class,i);
		}
		public List<TerminalNode> BITOR() { return getTokens(Java8Parser.BITOR); }
		public TerminalNode BITOR(int i) {
			return getToken(Java8Parser.BITOR, i);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public CatchTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catchType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterCatchType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitCatchType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitCatchType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CatchTypeContext catchType() throws RecognitionException {
		CatchTypeContext _localctx = new CatchTypeContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_catchType);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1363);
			classType();
			setState(1374);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BITOR) {
				{
				{
				setState(1364);
				match(BITOR);
				setState(1368);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,171,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1365);
						annotation();
						}
						} 
					}
					setState(1370);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,171,_ctx);
				}
				setState(1371);
				classType();
				}
				}
				setState(1376);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ResourceListContext extends ParserRuleContext {
		public List<ResourceContext> resource() {
			return getRuleContexts(ResourceContext.class);
		}
		public ResourceContext resource(int i) {
			return getRuleContext(ResourceContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(Java8Parser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(Java8Parser.SEMI, i);
		}
		public ResourceListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resourceList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterResourceList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitResourceList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitResourceList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResourceListContext resourceList() throws RecognitionException {
		ResourceListContext _localctx = new ResourceListContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_resourceList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1377);
			resource();
			setState(1382);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,173,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1378);
					match(SEMI);
					setState(1379);
					resource();
					}
					} 
				}
				setState(1384);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,173,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ResourceContext extends ParserRuleContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode ASSIGN() { return getToken(Java8Parser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<TerminalNode> FINAL() { return getTokens(Java8Parser.FINAL); }
		public TerminalNode FINAL(int i) {
			return getToken(Java8Parser.FINAL, i);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public DimsContext dims() {
			return getRuleContext(DimsContext.class,0);
		}
		public ResourceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resource; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterResource(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitResource(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitResource(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResourceContext resource() throws RecognitionException {
		ResourceContext _localctx = new ResourceContext(_ctx, getState());
		enterRule(_localctx, 190, RULE_resource);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1389);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==AT) {
				{
				setState(1387);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case FINAL:
					{
					setState(1385);
					match(FINAL);
					}
					break;
				case AT:
					{
					setState(1386);
					annotation();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(1391);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1392);
			baseType();
			setState(1393);
			match(Identifier);
			setState(1395);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACK || _la==AT) {
				{
				setState(1394);
				dims();
				}
			}

			setState(1397);
			match(ASSIGN);
			setState(1398);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryContext extends ParserRuleContext {
		public PrimaryNoNewArray_lfno_primaryContext primaryNoNewArray_lfno_primary() {
			return getRuleContext(PrimaryNoNewArray_lfno_primaryContext.class,0);
		}
		public ArrayCreationExpressionContext arrayCreationExpression() {
			return getRuleContext(ArrayCreationExpressionContext.class,0);
		}
		public List<PrimaryNoNewArray_lf_primaryContext> primaryNoNewArray_lf_primary() {
			return getRuleContexts(PrimaryNoNewArray_lf_primaryContext.class);
		}
		public PrimaryNoNewArray_lf_primaryContext primaryNoNewArray_lf_primary(int i) {
			return getRuleContext(PrimaryNoNewArray_lf_primaryContext.class,i);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_primary);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1402);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,177,_ctx) ) {
			case 1:
				{
				setState(1400);
				primaryNoNewArray_lfno_primary();
				}
				break;
			case 2:
				{
				setState(1401);
				arrayCreationExpression();
				}
				break;
			}
			setState(1407);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,178,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1404);
					primaryNoNewArray_lf_primary();
					}
					} 
				}
				setState(1409);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,178,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryNoNewArrayContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public TerminalNode CLASS() { return getToken(Java8Parser.CLASS, 0); }
		public List<TerminalNode> LBRACK() { return getTokens(Java8Parser.LBRACK); }
		public TerminalNode LBRACK(int i) {
			return getToken(Java8Parser.LBRACK, i);
		}
		public List<TerminalNode> RBRACK() { return getTokens(Java8Parser.RBRACK); }
		public TerminalNode RBRACK(int i) {
			return getToken(Java8Parser.RBRACK, i);
		}
		public TerminalNode VOID() { return getToken(Java8Parser.VOID, 0); }
		public TerminalNode THIS() { return getToken(Java8Parser.THIS, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public ClassInstanceCreationExpressionContext classInstanceCreationExpression() {
			return getRuleContext(ClassInstanceCreationExpressionContext.class,0);
		}
		public FieldAccessContext fieldAccess() {
			return getRuleContext(FieldAccessContext.class,0);
		}
		public ArrayAccessContext arrayAccess() {
			return getRuleContext(ArrayAccessContext.class,0);
		}
		public MethodInvocationContext methodInvocation() {
			return getRuleContext(MethodInvocationContext.class,0);
		}
		public MethodReferenceContext methodReference() {
			return getRuleContext(MethodReferenceContext.class,0);
		}
		public PrimaryNoNewArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryNoNewArray; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterPrimaryNoNewArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitPrimaryNoNewArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitPrimaryNoNewArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryNoNewArrayContext primaryNoNewArray() throws RecognitionException {
		PrimaryNoNewArrayContext _localctx = new PrimaryNoNewArrayContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_primaryNoNewArray);
		int _la;
		try {
			setState(1439);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,180,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1410);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1411);
				qualifiedName();
				setState(1416);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==LBRACK) {
					{
					{
					setState(1412);
					match(LBRACK);
					setState(1413);
					match(RBRACK);
					}
					}
					setState(1418);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1419);
				match(DOT);
				setState(1420);
				match(CLASS);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1422);
				match(VOID);
				setState(1423);
				match(DOT);
				setState(1424);
				match(CLASS);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1425);
				match(THIS);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1426);
				qualifiedName();
				setState(1427);
				match(DOT);
				setState(1428);
				match(THIS);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1430);
				match(LPAREN);
				setState(1431);
				expression(0);
				setState(1432);
				match(RPAREN);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1434);
				classInstanceCreationExpression();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1435);
				fieldAccess();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1436);
				arrayAccess();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(1437);
				methodInvocation();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(1438);
				methodReference();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryNoNewArray_lfno_arrayAccessContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public TerminalNode CLASS() { return getToken(Java8Parser.CLASS, 0); }
		public List<TerminalNode> LBRACK() { return getTokens(Java8Parser.LBRACK); }
		public TerminalNode LBRACK(int i) {
			return getToken(Java8Parser.LBRACK, i);
		}
		public List<TerminalNode> RBRACK() { return getTokens(Java8Parser.RBRACK); }
		public TerminalNode RBRACK(int i) {
			return getToken(Java8Parser.RBRACK, i);
		}
		public TerminalNode VOID() { return getToken(Java8Parser.VOID, 0); }
		public TerminalNode THIS() { return getToken(Java8Parser.THIS, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public ClassInstanceCreationExpressionContext classInstanceCreationExpression() {
			return getRuleContext(ClassInstanceCreationExpressionContext.class,0);
		}
		public FieldAccessContext fieldAccess() {
			return getRuleContext(FieldAccessContext.class,0);
		}
		public MethodInvocationContext methodInvocation() {
			return getRuleContext(MethodInvocationContext.class,0);
		}
		public MethodReferenceContext methodReference() {
			return getRuleContext(MethodReferenceContext.class,0);
		}
		public PrimaryNoNewArray_lfno_arrayAccessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryNoNewArray_lfno_arrayAccess; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterPrimaryNoNewArray_lfno_arrayAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitPrimaryNoNewArray_lfno_arrayAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitPrimaryNoNewArray_lfno_arrayAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryNoNewArray_lfno_arrayAccessContext primaryNoNewArray_lfno_arrayAccess() throws RecognitionException {
		PrimaryNoNewArray_lfno_arrayAccessContext _localctx = new PrimaryNoNewArray_lfno_arrayAccessContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_primaryNoNewArray_lfno_arrayAccess);
		int _la;
		try {
			setState(1469);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,182,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1441);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1442);
				qualifiedName();
				setState(1447);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==LBRACK) {
					{
					{
					setState(1443);
					match(LBRACK);
					setState(1444);
					match(RBRACK);
					}
					}
					setState(1449);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1450);
				match(DOT);
				setState(1451);
				match(CLASS);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1453);
				match(VOID);
				setState(1454);
				match(DOT);
				setState(1455);
				match(CLASS);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1456);
				match(THIS);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1457);
				qualifiedName();
				setState(1458);
				match(DOT);
				setState(1459);
				match(THIS);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1461);
				match(LPAREN);
				setState(1462);
				expression(0);
				setState(1463);
				match(RPAREN);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1465);
				classInstanceCreationExpression();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1466);
				fieldAccess();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1467);
				methodInvocation();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(1468);
				methodReference();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryNoNewArray_lf_primaryContext extends ParserRuleContext {
		public ClassInstanceCreationExpression_lf_primaryContext classInstanceCreationExpression_lf_primary() {
			return getRuleContext(ClassInstanceCreationExpression_lf_primaryContext.class,0);
		}
		public FieldAccess_lf_primaryContext fieldAccess_lf_primary() {
			return getRuleContext(FieldAccess_lf_primaryContext.class,0);
		}
		public ArrayAccess_lf_primaryContext arrayAccess_lf_primary() {
			return getRuleContext(ArrayAccess_lf_primaryContext.class,0);
		}
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public TerminalNode DCOLON() { return getToken(Java8Parser.DCOLON, 0); }
		public PrimaryNoNewArray_lf_primaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryNoNewArray_lf_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterPrimaryNoNewArray_lf_primary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitPrimaryNoNewArray_lf_primary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitPrimaryNoNewArray_lf_primary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryNoNewArray_lf_primaryContext primaryNoNewArray_lf_primary() throws RecognitionException {
		PrimaryNoNewArray_lf_primaryContext _localctx = new PrimaryNoNewArray_lf_primaryContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_primaryNoNewArray_lf_primary);
		int _la;
		try {
			setState(1489);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,186,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1471);
				classInstanceCreationExpression_lf_primary();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1472);
				fieldAccess_lf_primary();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1473);
				arrayAccess_lf_primary();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1474);
				match(DOT);
				setState(1476);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1475);
					typeArguments();
					}
				}

				setState(1478);
				match(Identifier);
				setState(1479);
				match(LPAREN);
				setState(1481);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(1480);
					argumentList();
					}
				}

				setState(1483);
				match(RPAREN);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1484);
				match(DCOLON);
				setState(1486);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1485);
					typeArguments();
					}
				}

				setState(1488);
				match(Identifier);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext extends ParserRuleContext {
		public ClassInstanceCreationExpression_lf_primaryContext classInstanceCreationExpression_lf_primary() {
			return getRuleContext(ClassInstanceCreationExpression_lf_primaryContext.class,0);
		}
		public FieldAccess_lf_primaryContext fieldAccess_lf_primary() {
			return getRuleContext(FieldAccess_lf_primaryContext.class,0);
		}
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public TerminalNode DCOLON() { return getToken(Java8Parser.DCOLON, 0); }
		public PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterPrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitPrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitPrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary() throws RecognitionException {
		PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext _localctx = new PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary);
		int _la;
		try {
			setState(1508);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,190,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1491);
				classInstanceCreationExpression_lf_primary();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1492);
				fieldAccess_lf_primary();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1493);
				match(DOT);
				setState(1495);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1494);
					typeArguments();
					}
				}

				setState(1497);
				match(Identifier);
				setState(1498);
				match(LPAREN);
				setState(1500);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(1499);
					argumentList();
					}
				}

				setState(1502);
				match(RPAREN);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1503);
				match(DCOLON);
				setState(1505);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1504);
					typeArguments();
					}
				}

				setState(1507);
				match(Identifier);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryNoNewArray_lfno_primaryContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public TerminalNode CLASS() { return getToken(Java8Parser.CLASS, 0); }
		public List<TerminalNode> LBRACK() { return getTokens(Java8Parser.LBRACK); }
		public TerminalNode LBRACK(int i) {
			return getToken(Java8Parser.LBRACK, i);
		}
		public List<TerminalNode> RBRACK() { return getTokens(Java8Parser.RBRACK); }
		public TerminalNode RBRACK(int i) {
			return getToken(Java8Parser.RBRACK, i);
		}
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public TerminalNode VOID() { return getToken(Java8Parser.VOID, 0); }
		public TerminalNode THIS() { return getToken(Java8Parser.THIS, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public ClassInstanceCreationExpression_lfno_primaryContext classInstanceCreationExpression_lfno_primary() {
			return getRuleContext(ClassInstanceCreationExpression_lfno_primaryContext.class,0);
		}
		public FieldAccess_lfno_primaryContext fieldAccess_lfno_primary() {
			return getRuleContext(FieldAccess_lfno_primaryContext.class,0);
		}
		public ArrayAccess_lfno_primaryContext arrayAccess_lfno_primary() {
			return getRuleContext(ArrayAccess_lfno_primaryContext.class,0);
		}
		public MethodInvocation_lfno_primaryContext methodInvocation_lfno_primary() {
			return getRuleContext(MethodInvocation_lfno_primaryContext.class,0);
		}
		public MethodReference_lfno_primaryContext methodReference_lfno_primary() {
			return getRuleContext(MethodReference_lfno_primaryContext.class,0);
		}
		public PrimaryNoNewArray_lfno_primaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryNoNewArray_lfno_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterPrimaryNoNewArray_lfno_primary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitPrimaryNoNewArray_lfno_primary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitPrimaryNoNewArray_lfno_primary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryNoNewArray_lfno_primaryContext primaryNoNewArray_lfno_primary() throws RecognitionException {
		PrimaryNoNewArray_lfno_primaryContext _localctx = new PrimaryNoNewArray_lfno_primaryContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_primaryNoNewArray_lfno_primary);
		int _la;
		try {
			setState(1550);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,193,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1510);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1511);
				qualifiedName();
				setState(1516);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==LBRACK) {
					{
					{
					setState(1512);
					match(LBRACK);
					setState(1513);
					match(RBRACK);
					}
					}
					setState(1518);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1519);
				match(DOT);
				setState(1520);
				match(CLASS);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1522);
				primitiveType();
				setState(1527);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==LBRACK) {
					{
					{
					setState(1523);
					match(LBRACK);
					setState(1524);
					match(RBRACK);
					}
					}
					setState(1529);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1530);
				match(DOT);
				setState(1531);
				match(CLASS);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1533);
				match(VOID);
				setState(1534);
				match(DOT);
				setState(1535);
				match(CLASS);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1536);
				match(THIS);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1537);
				qualifiedName();
				setState(1538);
				match(DOT);
				setState(1539);
				match(THIS);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1541);
				match(LPAREN);
				setState(1542);
				expression(0);
				setState(1543);
				match(RPAREN);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1545);
				classInstanceCreationExpression_lfno_primary();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1546);
				fieldAccess_lfno_primary();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(1547);
				arrayAccess_lfno_primary();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(1548);
				methodInvocation_lfno_primary();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(1549);
				methodReference_lfno_primary();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public TerminalNode CLASS() { return getToken(Java8Parser.CLASS, 0); }
		public List<TerminalNode> LBRACK() { return getTokens(Java8Parser.LBRACK); }
		public TerminalNode LBRACK(int i) {
			return getToken(Java8Parser.LBRACK, i);
		}
		public List<TerminalNode> RBRACK() { return getTokens(Java8Parser.RBRACK); }
		public TerminalNode RBRACK(int i) {
			return getToken(Java8Parser.RBRACK, i);
		}
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public TerminalNode VOID() { return getToken(Java8Parser.VOID, 0); }
		public TerminalNode THIS() { return getToken(Java8Parser.THIS, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public ClassInstanceCreationExpression_lfno_primaryContext classInstanceCreationExpression_lfno_primary() {
			return getRuleContext(ClassInstanceCreationExpression_lfno_primaryContext.class,0);
		}
		public FieldAccess_lfno_primaryContext fieldAccess_lfno_primary() {
			return getRuleContext(FieldAccess_lfno_primaryContext.class,0);
		}
		public MethodInvocation_lfno_primaryContext methodInvocation_lfno_primary() {
			return getRuleContext(MethodInvocation_lfno_primaryContext.class,0);
		}
		public MethodReference_lfno_primaryContext methodReference_lfno_primary() {
			return getRuleContext(MethodReference_lfno_primaryContext.class,0);
		}
		public PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterPrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitPrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitPrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary() throws RecognitionException {
		PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext _localctx = new PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary);
		int _la;
		try {
			setState(1591);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,196,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1552);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1553);
				qualifiedName();
				setState(1558);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==LBRACK) {
					{
					{
					setState(1554);
					match(LBRACK);
					setState(1555);
					match(RBRACK);
					}
					}
					setState(1560);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1561);
				match(DOT);
				setState(1562);
				match(CLASS);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1564);
				primitiveType();
				setState(1569);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==LBRACK) {
					{
					{
					setState(1565);
					match(LBRACK);
					setState(1566);
					match(RBRACK);
					}
					}
					setState(1571);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1572);
				match(DOT);
				setState(1573);
				match(CLASS);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1575);
				match(VOID);
				setState(1576);
				match(DOT);
				setState(1577);
				match(CLASS);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1578);
				match(THIS);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1579);
				qualifiedName();
				setState(1580);
				match(DOT);
				setState(1581);
				match(THIS);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1583);
				match(LPAREN);
				setState(1584);
				expression(0);
				setState(1585);
				match(RPAREN);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1587);
				classInstanceCreationExpression_lfno_primary();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1588);
				fieldAccess_lfno_primary();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(1589);
				methodInvocation_lfno_primary();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(1590);
				methodReference_lfno_primary();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayCreationExpressionContext extends ParserRuleContext {
		public TerminalNode NEW() { return getToken(Java8Parser.NEW, 0); }
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ClassOrInterfaceTypeContext classOrInterfaceType() {
			return getRuleContext(ClassOrInterfaceTypeContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public DimsContext dims() {
			return getRuleContext(DimsContext.class,0);
		}
		public ArrayInitializerContext arrayInitializer() {
			return getRuleContext(ArrayInitializerContext.class,0);
		}
		public List<DimExprContext> dimExpr() {
			return getRuleContexts(DimExprContext.class);
		}
		public DimExprContext dimExpr(int i) {
			return getRuleContext(DimExprContext.class,i);
		}
		public ArrayCreationExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayCreationExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterArrayCreationExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitArrayCreationExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitArrayCreationExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayCreationExpressionContext arrayCreationExpression() throws RecognitionException {
		ArrayCreationExpressionContext _localctx = new ArrayCreationExpressionContext(_ctx, getState());
		enterRule(_localctx, 206, RULE_arrayCreationExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1593);
			match(NEW);
			setState(1597);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(1594);
				annotation();
				}
				}
				setState(1599);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1628);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case SHORT:
				{
				setState(1600);
				primitiveType();
				setState(1612);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,200,_ctx) ) {
				case 1:
					{
					setState(1602); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(1601);
							dimExpr();
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(1604); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,198,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(1607);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,199,_ctx) ) {
					case 1:
						{
						setState(1606);
						dims();
						}
						break;
					}
					}
					break;
				case 2:
					{
					setState(1609);
					dims();
					setState(1610);
					arrayInitializer();
					}
					break;
				}
				}
				break;
			case Identifier:
				{
				setState(1614);
				classOrInterfaceType();
				setState(1626);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,203,_ctx) ) {
				case 1:
					{
					setState(1616); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(1615);
							dimExpr();
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(1618); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,201,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(1621);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,202,_ctx) ) {
					case 1:
						{
						setState(1620);
						dims();
						}
						break;
					}
					}
					break;
				case 2:
					{
					setState(1623);
					dims();
					setState(1624);
					arrayInitializer();
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayAccessContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public PrimaryNoNewArray_lfno_arrayAccessContext primaryNoNewArray_lfno_arrayAccess() {
			return getRuleContext(PrimaryNoNewArray_lfno_arrayAccessContext.class,0);
		}
		public List<ArrayAccessExpressionContext> arrayAccessExpression() {
			return getRuleContexts(ArrayAccessExpressionContext.class);
		}
		public ArrayAccessExpressionContext arrayAccessExpression(int i) {
			return getRuleContext(ArrayAccessExpressionContext.class,i);
		}
		public ArrayAccessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayAccess; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterArrayAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitArrayAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitArrayAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayAccessContext arrayAccess() throws RecognitionException {
		ArrayAccessContext _localctx = new ArrayAccessContext(_ctx, getState());
		enterRule(_localctx, 208, RULE_arrayAccess);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1632);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,205,_ctx) ) {
			case 1:
				{
				setState(1630);
				qualifiedName();
				}
				break;
			case 2:
				{
				setState(1631);
				primaryNoNewArray_lfno_arrayAccess();
				}
				break;
			}
			setState(1635); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(1634);
				arrayAccessExpression();
				}
				}
				setState(1637); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==LBRACK );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayAccess_lf_primaryContext extends ParserRuleContext {
		public PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary() {
			return getRuleContext(PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext.class,0);
		}
		public List<ArrayAccessExpressionContext> arrayAccessExpression() {
			return getRuleContexts(ArrayAccessExpressionContext.class);
		}
		public ArrayAccessExpressionContext arrayAccessExpression(int i) {
			return getRuleContext(ArrayAccessExpressionContext.class,i);
		}
		public ArrayAccess_lf_primaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayAccess_lf_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterArrayAccess_lf_primary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitArrayAccess_lf_primary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitArrayAccess_lf_primary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayAccess_lf_primaryContext arrayAccess_lf_primary() throws RecognitionException {
		ArrayAccess_lf_primaryContext _localctx = new ArrayAccess_lf_primaryContext(_ctx, getState());
		enterRule(_localctx, 210, RULE_arrayAccess_lf_primary);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1640);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DOT || _la==DCOLON) {
				{
				setState(1639);
				primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary();
				}
			}

			setState(1643); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(1642);
					arrayAccessExpression();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1645); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,208,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayAccess_lfno_primaryContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary() {
			return getRuleContext(PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext.class,0);
		}
		public List<ArrayAccessExpressionContext> arrayAccessExpression() {
			return getRuleContexts(ArrayAccessExpressionContext.class);
		}
		public ArrayAccessExpressionContext arrayAccessExpression(int i) {
			return getRuleContext(ArrayAccessExpressionContext.class,i);
		}
		public ArrayAccess_lfno_primaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayAccess_lfno_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterArrayAccess_lfno_primary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitArrayAccess_lfno_primary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitArrayAccess_lfno_primary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayAccess_lfno_primaryContext arrayAccess_lfno_primary() throws RecognitionException {
		ArrayAccess_lfno_primaryContext _localctx = new ArrayAccess_lfno_primaryContext(_ctx, getState());
		enterRule(_localctx, 212, RULE_arrayAccess_lfno_primary);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1649);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,209,_ctx) ) {
			case 1:
				{
				setState(1647);
				qualifiedName();
				}
				break;
			case 2:
				{
				setState(1648);
				primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary();
				}
				break;
			}
			setState(1652); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(1651);
					arrayAccessExpression();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1654); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,210,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayAccessExpressionContext extends ParserRuleContext {
		public List<TerminalNode> LBRACK() { return getTokens(Java8Parser.LBRACK); }
		public TerminalNode LBRACK(int i) {
			return getToken(Java8Parser.LBRACK, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> RBRACK() { return getTokens(Java8Parser.RBRACK); }
		public TerminalNode RBRACK(int i) {
			return getToken(Java8Parser.RBRACK, i);
		}
		public ArrayAccessExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayAccessExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterArrayAccessExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitArrayAccessExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitArrayAccessExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayAccessExpressionContext arrayAccessExpression() throws RecognitionException {
		ArrayAccessExpressionContext _localctx = new ArrayAccessExpressionContext(_ctx, getState());
		enterRule(_localctx, 214, RULE_arrayAccessExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1660); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(1656);
					match(LBRACK);
					setState(1657);
					expression(0);
					setState(1658);
					match(RBRACK);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1662); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,211,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassInstanceCreationExpressionContext extends ParserRuleContext {
		public InstanceCreationExpressionContext instanceCreationExpression() {
			return getRuleContext(InstanceCreationExpressionContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public ClassInstanceCreationExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classInstanceCreationExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterClassInstanceCreationExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitClassInstanceCreationExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitClassInstanceCreationExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassInstanceCreationExpressionContext classInstanceCreationExpression() throws RecognitionException {
		ClassInstanceCreationExpressionContext _localctx = new ClassInstanceCreationExpressionContext(_ctx, getState());
		enterRule(_localctx, 216, RULE_classInstanceCreationExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1670);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,212,_ctx) ) {
			case 1:
				{
				setState(1664);
				qualifiedName();
				setState(1665);
				match(DOT);
				}
				break;
			case 2:
				{
				setState(1667);
				primary();
				setState(1668);
				match(DOT);
				}
				break;
			}
			setState(1672);
			instanceCreationExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassInstanceCreationExpression_lf_primaryContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public InstanceCreationExpressionContext instanceCreationExpression() {
			return getRuleContext(InstanceCreationExpressionContext.class,0);
		}
		public ClassInstanceCreationExpression_lf_primaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classInstanceCreationExpression_lf_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterClassInstanceCreationExpression_lf_primary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitClassInstanceCreationExpression_lf_primary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitClassInstanceCreationExpression_lf_primary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassInstanceCreationExpression_lf_primaryContext classInstanceCreationExpression_lf_primary() throws RecognitionException {
		ClassInstanceCreationExpression_lf_primaryContext _localctx = new ClassInstanceCreationExpression_lf_primaryContext(_ctx, getState());
		enterRule(_localctx, 218, RULE_classInstanceCreationExpression_lf_primary);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1674);
			match(DOT);
			setState(1675);
			instanceCreationExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassInstanceCreationExpression_lfno_primaryContext extends ParserRuleContext {
		public InstanceCreationExpressionContext instanceCreationExpression() {
			return getRuleContext(InstanceCreationExpressionContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public ClassInstanceCreationExpression_lfno_primaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classInstanceCreationExpression_lfno_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterClassInstanceCreationExpression_lfno_primary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitClassInstanceCreationExpression_lfno_primary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitClassInstanceCreationExpression_lfno_primary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassInstanceCreationExpression_lfno_primaryContext classInstanceCreationExpression_lfno_primary() throws RecognitionException {
		ClassInstanceCreationExpression_lfno_primaryContext _localctx = new ClassInstanceCreationExpression_lfno_primaryContext(_ctx, getState());
		enterRule(_localctx, 220, RULE_classInstanceCreationExpression_lfno_primary);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1680);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(1677);
				qualifiedName();
				setState(1678);
				match(DOT);
				}
			}

			setState(1682);
			instanceCreationExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstanceCreationExpressionContext extends ParserRuleContext {
		public TerminalNode NEW() { return getToken(Java8Parser.NEW, 0); }
		public List<TerminalNode> Identifier() { return getTokens(Java8Parser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(Java8Parser.Identifier, i);
		}
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(Java8Parser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(Java8Parser.DOT, i);
		}
		public TypeArgumentsOrDiamondContext typeArgumentsOrDiamond() {
			return getRuleContext(TypeArgumentsOrDiamondContext.class,0);
		}
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public InstanceCreationExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instanceCreationExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterInstanceCreationExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitInstanceCreationExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitInstanceCreationExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstanceCreationExpressionContext instanceCreationExpression() throws RecognitionException {
		InstanceCreationExpressionContext _localctx = new InstanceCreationExpressionContext(_ctx, getState());
		enterRule(_localctx, 222, RULE_instanceCreationExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1684);
			match(NEW);
			setState(1686);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(1685);
				typeArguments();
				}
			}

			setState(1691);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(1688);
				annotation();
				}
				}
				setState(1693);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1694);
			match(Identifier);
			setState(1705);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(1695);
				match(DOT);
				setState(1699);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(1696);
					annotation();
					}
					}
					setState(1701);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1702);
				match(Identifier);
				}
				}
				setState(1707);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1709);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(1708);
				typeArgumentsOrDiamond();
				}
			}

			setState(1711);
			match(LPAREN);
			setState(1713);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
				{
				setState(1712);
				argumentList();
				}
			}

			setState(1715);
			match(RPAREN);
			setState(1717);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,220,_ctx) ) {
			case 1:
				{
				setState(1716);
				classBody();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeArgumentsOrDiamondContext extends ParserRuleContext {
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public TerminalNode LT() { return getToken(Java8Parser.LT, 0); }
		public TerminalNode GT() { return getToken(Java8Parser.GT, 0); }
		public TypeArgumentsOrDiamondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArgumentsOrDiamond; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterTypeArgumentsOrDiamond(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitTypeArgumentsOrDiamond(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitTypeArgumentsOrDiamond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentsOrDiamondContext typeArgumentsOrDiamond() throws RecognitionException {
		TypeArgumentsOrDiamondContext _localctx = new TypeArgumentsOrDiamondContext(_ctx, getState());
		enterRule(_localctx, 224, RULE_typeArgumentsOrDiamond);
		try {
			setState(1722);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,221,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1719);
				typeArguments();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1720);
				match(LT);
				setState(1721);
				match(GT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldAccessContext extends ParserRuleContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public List<TerminalNode> DOT() { return getTokens(Java8Parser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(Java8Parser.DOT, i);
		}
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode SUPER() { return getToken(Java8Parser.SUPER, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public FieldAccessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldAccess; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterFieldAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitFieldAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitFieldAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldAccessContext fieldAccess() throws RecognitionException {
		FieldAccessContext _localctx = new FieldAccessContext(_ctx, getState());
		enterRule(_localctx, 226, RULE_fieldAccess);
		try {
			setState(1737);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,222,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1724);
				primary();
				setState(1725);
				match(DOT);
				setState(1726);
				match(Identifier);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1728);
				match(SUPER);
				setState(1729);
				match(DOT);
				setState(1730);
				match(Identifier);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1731);
				qualifiedName();
				setState(1732);
				match(DOT);
				setState(1733);
				match(SUPER);
				setState(1734);
				match(DOT);
				setState(1735);
				match(Identifier);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldAccess_lf_primaryContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public FieldAccess_lf_primaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldAccess_lf_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterFieldAccess_lf_primary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitFieldAccess_lf_primary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitFieldAccess_lf_primary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldAccess_lf_primaryContext fieldAccess_lf_primary() throws RecognitionException {
		FieldAccess_lf_primaryContext _localctx = new FieldAccess_lf_primaryContext(_ctx, getState());
		enterRule(_localctx, 228, RULE_fieldAccess_lf_primary);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1739);
			match(DOT);
			setState(1740);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldAccess_lfno_primaryContext extends ParserRuleContext {
		public TerminalNode SUPER() { return getToken(Java8Parser.SUPER, 0); }
		public List<TerminalNode> DOT() { return getTokens(Java8Parser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(Java8Parser.DOT, i);
		}
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public FieldAccess_lfno_primaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldAccess_lfno_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterFieldAccess_lfno_primary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitFieldAccess_lfno_primary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitFieldAccess_lfno_primary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldAccess_lfno_primaryContext fieldAccess_lfno_primary() throws RecognitionException {
		FieldAccess_lfno_primaryContext _localctx = new FieldAccess_lfno_primaryContext(_ctx, getState());
		enterRule(_localctx, 230, RULE_fieldAccess_lfno_primary);
		try {
			setState(1751);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SUPER:
				enterOuterAlt(_localctx, 1);
				{
				setState(1742);
				match(SUPER);
				setState(1743);
				match(DOT);
				setState(1744);
				match(Identifier);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(1745);
				qualifiedName();
				setState(1746);
				match(DOT);
				setState(1747);
				match(SUPER);
				setState(1748);
				match(DOT);
				setState(1749);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodInvocationContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public List<TerminalNode> DOT() { return getTokens(Java8Parser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(Java8Parser.DOT, i);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public TerminalNode SUPER() { return getToken(Java8Parser.SUPER, 0); }
		public MethodInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodInvocation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterMethodInvocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitMethodInvocation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitMethodInvocation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodInvocationContext methodInvocation() throws RecognitionException {
		MethodInvocationContext _localctx = new MethodInvocationContext(_ctx, getState());
		enterRule(_localctx, 232, RULE_methodInvocation);
		int _la;
		try {
			setState(1808);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,233,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1753);
				match(Identifier);
				setState(1754);
				match(LPAREN);
				setState(1756);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(1755);
					argumentList();
					}
				}

				setState(1758);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1759);
				qualifiedName();
				setState(1760);
				match(DOT);
				setState(1762);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1761);
					typeArguments();
					}
				}

				setState(1764);
				match(Identifier);
				setState(1765);
				match(LPAREN);
				setState(1767);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(1766);
					argumentList();
					}
				}

				setState(1769);
				match(RPAREN);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1771);
				primary();
				setState(1772);
				match(DOT);
				setState(1774);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1773);
					typeArguments();
					}
				}

				setState(1776);
				match(Identifier);
				setState(1777);
				match(LPAREN);
				setState(1779);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(1778);
					argumentList();
					}
				}

				setState(1781);
				match(RPAREN);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1783);
				match(SUPER);
				setState(1784);
				match(DOT);
				setState(1786);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1785);
					typeArguments();
					}
				}

				setState(1788);
				match(Identifier);
				setState(1789);
				match(LPAREN);
				setState(1791);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(1790);
					argumentList();
					}
				}

				setState(1793);
				match(RPAREN);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1794);
				qualifiedName();
				setState(1795);
				match(DOT);
				setState(1796);
				match(SUPER);
				setState(1797);
				match(DOT);
				setState(1799);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1798);
					typeArguments();
					}
				}

				setState(1801);
				match(Identifier);
				setState(1802);
				match(LPAREN);
				setState(1804);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(1803);
					argumentList();
					}
				}

				setState(1806);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodInvocation_lfno_primaryContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public List<TerminalNode> DOT() { return getTokens(Java8Parser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(Java8Parser.DOT, i);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public TerminalNode SUPER() { return getToken(Java8Parser.SUPER, 0); }
		public MethodInvocation_lfno_primaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodInvocation_lfno_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterMethodInvocation_lfno_primary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitMethodInvocation_lfno_primary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitMethodInvocation_lfno_primary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodInvocation_lfno_primaryContext methodInvocation_lfno_primary() throws RecognitionException {
		MethodInvocation_lfno_primaryContext _localctx = new MethodInvocation_lfno_primaryContext(_ctx, getState());
		enterRule(_localctx, 234, RULE_methodInvocation_lfno_primary);
		int _la;
		try {
			setState(1853);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,241,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1810);
				match(Identifier);
				setState(1811);
				match(LPAREN);
				setState(1813);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(1812);
					argumentList();
					}
				}

				setState(1815);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1816);
				qualifiedName();
				setState(1817);
				match(DOT);
				setState(1819);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1818);
					typeArguments();
					}
				}

				setState(1821);
				match(Identifier);
				setState(1822);
				match(LPAREN);
				setState(1824);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(1823);
					argumentList();
					}
				}

				setState(1826);
				match(RPAREN);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1828);
				match(SUPER);
				setState(1829);
				match(DOT);
				setState(1831);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1830);
					typeArguments();
					}
				}

				setState(1833);
				match(Identifier);
				setState(1834);
				match(LPAREN);
				setState(1836);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(1835);
					argumentList();
					}
				}

				setState(1838);
				match(RPAREN);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1839);
				qualifiedName();
				setState(1840);
				match(DOT);
				setState(1841);
				match(SUPER);
				setState(1842);
				match(DOT);
				setState(1844);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1843);
					typeArguments();
					}
				}

				setState(1846);
				match(Identifier);
				setState(1847);
				match(LPAREN);
				setState(1849);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << NEW) | (1L << SHORT) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << LPAREN))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (BANG - 69)) | (1L << (TILDE - 69)) | (1L << (INC - 69)) | (1L << (DEC - 69)) | (1L << (ADD - 69)) | (1L << (SUB - 69)) | (1L << (AT - 69)) | (1L << (Identifier - 69)))) != 0)) {
					{
					setState(1848);
					argumentList();
					}
				}

				setState(1851);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Java8Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Java8Parser.COMMA, i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitArgumentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 236, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1855);
			expression(0);
			setState(1860);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1856);
				match(COMMA);
				setState(1857);
				expression(0);
				}
				}
				setState(1862);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodReferenceContext extends ParserRuleContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public TerminalNode DCOLON() { return getToken(Java8Parser.DCOLON, 0); }
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public ReferenceTypeContext referenceType() {
			return getRuleContext(ReferenceTypeContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public TerminalNode SUPER() { return getToken(Java8Parser.SUPER, 0); }
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public TerminalNode NEW() { return getToken(Java8Parser.NEW, 0); }
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public MethodReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodReference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterMethodReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitMethodReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitMethodReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodReferenceContext methodReference() throws RecognitionException {
		MethodReferenceContext _localctx = new MethodReferenceContext(_ctx, getState());
		enterRule(_localctx, 238, RULE_methodReference);
		int _la;
		try {
			int _alt;
			setState(1928);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,252,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1863);
				primary();
				setState(1864);
				match(DCOLON);
				setState(1866);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1865);
					typeArguments();
					}
				}

				setState(1868);
				match(Identifier);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1870);
				qualifiedName();
				setState(1871);
				match(DCOLON);
				setState(1873);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1872);
					typeArguments();
					}
				}

				setState(1875);
				match(Identifier);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1880);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(1877);
					annotation();
					}
					}
					setState(1882);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1883);
				referenceType();
				setState(1884);
				match(DCOLON);
				setState(1886);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1885);
					typeArguments();
					}
				}

				setState(1888);
				match(Identifier);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1890);
				match(SUPER);
				setState(1891);
				match(DCOLON);
				setState(1893);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1892);
					typeArguments();
					}
				}

				setState(1895);
				match(Identifier);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1896);
				qualifiedName();
				setState(1897);
				match(DOT);
				setState(1898);
				match(SUPER);
				setState(1899);
				match(DCOLON);
				setState(1901);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1900);
					typeArguments();
					}
				}

				setState(1903);
				match(Identifier);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1908);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,249,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1905);
						annotation();
						}
						} 
					}
					setState(1910);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,249,_ctx);
				}
				setState(1911);
				classType();
				setState(1912);
				match(DCOLON);
				setState(1914);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1913);
					typeArguments();
					}
				}

				setState(1916);
				match(NEW);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1921);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(1918);
					annotation();
					}
					}
					setState(1923);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1924);
				arrayType();
				setState(1925);
				match(DCOLON);
				setState(1926);
				match(NEW);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodReference_lfno_primaryContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DCOLON() { return getToken(Java8Parser.DCOLON, 0); }
		public TerminalNode Identifier() { return getToken(Java8Parser.Identifier, 0); }
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ReferenceTypeContext referenceType() {
			return getRuleContext(ReferenceTypeContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public TerminalNode SUPER() { return getToken(Java8Parser.SUPER, 0); }
		public TerminalNode DOT() { return getToken(Java8Parser.DOT, 0); }
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public TerminalNode NEW() { return getToken(Java8Parser.NEW, 0); }
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public MethodReference_lfno_primaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodReference_lfno_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterMethodReference_lfno_primary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitMethodReference_lfno_primary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitMethodReference_lfno_primary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodReference_lfno_primaryContext methodReference_lfno_primary() throws RecognitionException {
		MethodReference_lfno_primaryContext _localctx = new MethodReference_lfno_primaryContext(_ctx, getState());
		enterRule(_localctx, 240, RULE_methodReference_lfno_primary);
		int _la;
		try {
			int _alt;
			setState(1988);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,261,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1930);
				qualifiedName();
				setState(1931);
				match(DCOLON);
				setState(1933);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1932);
					typeArguments();
					}
				}

				setState(1935);
				match(Identifier);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1940);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(1937);
					annotation();
					}
					}
					setState(1942);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1943);
				referenceType();
				setState(1944);
				match(DCOLON);
				setState(1946);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1945);
					typeArguments();
					}
				}

				setState(1948);
				match(Identifier);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1950);
				match(SUPER);
				setState(1951);
				match(DCOLON);
				setState(1953);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1952);
					typeArguments();
					}
				}

				setState(1955);
				match(Identifier);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1956);
				qualifiedName();
				setState(1957);
				match(DOT);
				setState(1958);
				match(SUPER);
				setState(1959);
				match(DCOLON);
				setState(1961);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1960);
					typeArguments();
					}
				}

				setState(1963);
				match(Identifier);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1968);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,258,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1965);
						annotation();
						}
						} 
					}
					setState(1970);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,258,_ctx);
				}
				setState(1971);
				classType();
				setState(1972);
				match(DCOLON);
				setState(1974);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1973);
					typeArguments();
					}
				}

				setState(1976);
				match(NEW);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1981);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(1978);
					annotation();
					}
					}
					setState(1983);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1984);
				arrayType();
				setState(1985);
				match(DCOLON);
				setState(1986);
				match(NEW);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DimExprContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(Java8Parser.LBRACK, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RBRACK() { return getToken(Java8Parser.RBRACK, 0); }
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public DimExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dimExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterDimExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitDimExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitDimExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DimExprContext dimExpr() throws RecognitionException {
		DimExprContext _localctx = new DimExprContext(_ctx, getState());
		enterRule(_localctx, 242, RULE_dimExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1993);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(1990);
				annotation();
				}
				}
				setState(1995);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1996);
			match(LBRACK);
			setState(1997);
			expression(0);
			setState(1998);
			match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public Token op;
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public CastExpressionContext castExpression() {
			return getRuleContext(CastExpressionContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public LambdaExpressionContext lambdaExpression() {
			return getRuleContext(LambdaExpressionContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ADD() { return getToken(Java8Parser.ADD, 0); }
		public TerminalNode SUB() { return getToken(Java8Parser.SUB, 0); }
		public TerminalNode STAR() { return getToken(Java8Parser.STAR, 0); }
		public TerminalNode DIV() { return getToken(Java8Parser.DIV, 0); }
		public TerminalNode MOD() { return getToken(Java8Parser.MOD, 0); }
		public TerminalNode LE() { return getToken(Java8Parser.LE, 0); }
		public TerminalNode GE() { return getToken(Java8Parser.GE, 0); }
		public List<TerminalNode> LT() { return getTokens(Java8Parser.LT); }
		public TerminalNode LT(int i) {
			return getToken(Java8Parser.LT, i);
		}
		public List<TerminalNode> GT() { return getTokens(Java8Parser.GT); }
		public TerminalNode GT(int i) {
			return getToken(Java8Parser.GT, i);
		}
		public TerminalNode AND() { return getToken(Java8Parser.AND, 0); }
		public TerminalNode OR() { return getToken(Java8Parser.OR, 0); }
		public TerminalNode BITAND() { return getToken(Java8Parser.BITAND, 0); }
		public TerminalNode BITOR() { return getToken(Java8Parser.BITOR, 0); }
		public TerminalNode CARET() { return getToken(Java8Parser.CARET, 0); }
		public TerminalNode EQUAL() { return getToken(Java8Parser.EQUAL, 0); }
		public TerminalNode NOTEQUAL() { return getToken(Java8Parser.NOTEQUAL, 0); }
		public TerminalNode COLON() { return getToken(Java8Parser.COLON, 0); }
		public TerminalNode QMARK() { return getToken(Java8Parser.QMARK, 0); }
		public TerminalNode INSTANCEOF() { return getToken(Java8Parser.INSTANCEOF, 0); }
		public ReferenceTypeContext referenceType() {
			return getRuleContext(ReferenceTypeContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 244;
		enterRecursionRule(_localctx, 244, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2007);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,263,_ctx) ) {
			case 1:
				{
				setState(2001);
				literal();
				}
				break;
			case 2:
				{
				setState(2002);
				assignment();
				}
				break;
			case 3:
				{
				setState(2003);
				castExpression();
				}
				break;
			case 4:
				{
				setState(2004);
				qualifiedName();
				}
				break;
			case 5:
				{
				setState(2005);
				unaryExpression();
				}
				break;
			case 6:
				{
				setState(2006);
				lambdaExpression();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(2046);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,267,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(2044);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,266,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(2009);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(2010);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & ((1L << (ADD - 82)) | (1L << (SUB - 82)) | (1L << (STAR - 82)) | (1L << (DIV - 82)) | (1L << (MOD - 82)))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(2011);
						expression(8);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(2012);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(2013);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (GT - 67)) | (1L << (LT - 67)) | (1L << (LE - 67)) | (1L << (GE - 67)))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(2014);
						expression(7);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(2015);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(2023);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,264,_ctx) ) {
						case 1:
							{
							setState(2016);
							match(LT);
							setState(2017);
							match(LT);
							}
							break;
						case 2:
							{
							setState(2018);
							match(GT);
							setState(2019);
							match(GT);
							setState(2020);
							match(GT);
							}
							break;
						case 3:
							{
							setState(2021);
							match(GT);
							setState(2022);
							match(GT);
							}
							break;
						}
						setState(2025);
						expression(6);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(2026);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(2027);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 74)) & ~0x3f) == 0 && ((1L << (_la - 74)) & ((1L << (EQUAL - 74)) | (1L << (NOTEQUAL - 74)) | (1L << (AND - 74)) | (1L << (OR - 74)) | (1L << (BITAND - 74)) | (1L << (BITOR - 74)) | (1L << (CARET - 74)))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(2028);
						expression(5);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(2029);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(2030);
						((ExpressionContext)_localctx).op = match(QMARK);
						setState(2031);
						expression(0);
						setState(2032);
						match(COLON);
						setState(2033);
						expression(4);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(2035);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(2036);
						match(INSTANCEOF);
						setState(2040);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==AT) {
							{
							{
							setState(2037);
							annotation();
							}
							}
							setState(2042);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(2043);
						referenceType();
						}
						break;
					}
					} 
				}
				setState(2048);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,267,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public FieldAccessContext fieldAccess() {
			return getRuleContext(FieldAccessContext.class,0);
		}
		public ArrayAccessContext arrayAccess() {
			return getRuleContext(ArrayAccessContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(Java8Parser.ASSIGN, 0); }
		public TerminalNode ADD_ASSIGN() { return getToken(Java8Parser.ADD_ASSIGN, 0); }
		public TerminalNode SUB_ASSIGN() { return getToken(Java8Parser.SUB_ASSIGN, 0); }
		public TerminalNode MUL_ASSIGN() { return getToken(Java8Parser.MUL_ASSIGN, 0); }
		public TerminalNode DIV_ASSIGN() { return getToken(Java8Parser.DIV_ASSIGN, 0); }
		public TerminalNode AND_ASSIGN() { return getToken(Java8Parser.AND_ASSIGN, 0); }
		public TerminalNode OR_ASSIGN() { return getToken(Java8Parser.OR_ASSIGN, 0); }
		public TerminalNode XOR_ASSIGN() { return getToken(Java8Parser.XOR_ASSIGN, 0); }
		public TerminalNode RSHIFT_ASSIGN() { return getToken(Java8Parser.RSHIFT_ASSIGN, 0); }
		public TerminalNode URSHIFT_ASSIGN() { return getToken(Java8Parser.URSHIFT_ASSIGN, 0); }
		public TerminalNode LSHIFT_ASSIGN() { return getToken(Java8Parser.LSHIFT_ASSIGN, 0); }
		public TerminalNode MOD_ASSIGN() { return getToken(Java8Parser.MOD_ASSIGN, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 246, RULE_assignment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2052);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,268,_ctx) ) {
			case 1:
				{
				setState(2049);
				qualifiedName();
				}
				break;
			case 2:
				{
				setState(2050);
				fieldAccess();
				}
				break;
			case 3:
				{
				setState(2051);
				arrayAccess();
				}
				break;
			}
			setState(2054);
			((AssignmentContext)_localctx).op = _input.LT(1);
			_la = _input.LA(1);
			if ( !(((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (ASSIGN - 66)) | (1L << (ADD_ASSIGN - 66)) | (1L << (SUB_ASSIGN - 66)) | (1L << (MUL_ASSIGN - 66)) | (1L << (DIV_ASSIGN - 66)) | (1L << (AND_ASSIGN - 66)) | (1L << (OR_ASSIGN - 66)) | (1L << (XOR_ASSIGN - 66)) | (1L << (MOD_ASSIGN - 66)) | (1L << (LSHIFT_ASSIGN - 66)) | (1L << (RSHIFT_ASSIGN - 66)) | (1L << (URSHIFT_ASSIGN - 66)))) != 0)) ) {
				((AssignmentContext)_localctx).op = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(2055);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CastExpressionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public ReferenceTypeContext referenceType() {
			return getRuleContext(ReferenceTypeContext.class,0);
		}
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public LambdaExpressionContext lambdaExpression() {
			return getRuleContext(LambdaExpressionContext.class,0);
		}
		public List<AdditionalBoundContext> additionalBound() {
			return getRuleContexts(AdditionalBoundContext.class);
		}
		public AdditionalBoundContext additionalBound(int i) {
			return getRuleContext(AdditionalBoundContext.class,i);
		}
		public CastExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_castExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterCastExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitCastExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitCastExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CastExpressionContext castExpression() throws RecognitionException {
		CastExpressionContext _localctx = new CastExpressionContext(_ctx, getState());
		enterRule(_localctx, 248, RULE_castExpression);
		int _la;
		try {
			setState(2087);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,273,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2057);
				match(LPAREN);
				setState(2061);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(2058);
					annotation();
					}
					}
					setState(2063);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2064);
				primitiveType();
				setState(2065);
				match(RPAREN);
				setState(2066);
				unaryExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2068);
				match(LPAREN);
				setState(2072);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(2069);
					annotation();
					}
					}
					setState(2074);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2075);
				referenceType();
				setState(2079);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==BITAND) {
					{
					{
					setState(2076);
					additionalBound();
					}
					}
					setState(2081);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2082);
				match(RPAREN);
				setState(2085);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,272,_ctx) ) {
				case 1:
					{
					setState(2083);
					postfixExpression();
					}
					break;
				case 2:
					{
					setState(2084);
					lambdaExpression();
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LambdaExpressionContext extends ParserRuleContext {
		public LambdaParametersContext lambdaParameters() {
			return getRuleContext(LambdaParametersContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(Java8Parser.ARROW, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public LambdaExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterLambdaExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitLambdaExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitLambdaExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdaExpressionContext lambdaExpression() throws RecognitionException {
		LambdaExpressionContext _localctx = new LambdaExpressionContext(_ctx, getState());
		enterRule(_localctx, 250, RULE_lambdaExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2089);
			lambdaParameters();
			setState(2090);
			match(ARROW);
			setState(2093);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case NEW:
			case SHORT:
			case SUPER:
			case THIS:
			case VOID:
			case IntegerLiteral:
			case FloatingPointLiteral:
			case BooleanLiteral:
			case CharacterLiteral:
			case StringLiteral:
			case NullLiteral:
			case LPAREN:
			case BANG:
			case TILDE:
			case INC:
			case DEC:
			case ADD:
			case SUB:
			case AT:
			case Identifier:
				{
				setState(2091);
				expression(0);
				}
				break;
			case LBRACE:
				{
				setState(2092);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LambdaParametersContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(Java8Parser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(Java8Parser.Identifier, i);
		}
		public TerminalNode LPAREN() { return getToken(Java8Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Java8Parser.RPAREN, 0); }
		public FormalParameterListContext formalParameterList() {
			return getRuleContext(FormalParameterListContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(Java8Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Java8Parser.COMMA, i);
		}
		public LambdaParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterLambdaParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitLambdaParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitLambdaParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdaParametersContext lambdaParameters() throws RecognitionException {
		LambdaParametersContext _localctx = new LambdaParametersContext(_ctx, getState());
		enterRule(_localctx, 252, RULE_lambdaParameters);
		int _la;
		try {
			setState(2111);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,277,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2095);
				match(Identifier);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2096);
				match(LPAREN);
				setState(2098);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BYTE) | (1L << CHAR) | (1L << DOUBLE) | (1L << FINAL) | (1L << FLOAT) | (1L << INT) | (1L << LONG) | (1L << SHORT))) != 0) || _la==AT || _la==Identifier) {
					{
					setState(2097);
					formalParameterList();
					}
				}

				setState(2100);
				match(RPAREN);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2101);
				match(LPAREN);
				setState(2102);
				match(Identifier);
				setState(2107);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2103);
					match(COMMA);
					setState(2104);
					match(Identifier);
					}
					}
					setState(2109);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2110);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryExpressionContext extends ParserRuleContext {
		public Token op;
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public TerminalNode INC() { return getToken(Java8Parser.INC, 0); }
		public TerminalNode DEC() { return getToken(Java8Parser.DEC, 0); }
		public TerminalNode ADD() { return getToken(Java8Parser.ADD, 0); }
		public TerminalNode SUB() { return getToken(Java8Parser.SUB, 0); }
		public TerminalNode TILDE() { return getToken(Java8Parser.TILDE, 0); }
		public TerminalNode BANG() { return getToken(Java8Parser.BANG, 0); }
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public UnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExpressionContext unaryExpression() throws RecognitionException {
		UnaryExpressionContext _localctx = new UnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 254, RULE_unaryExpression);
		int _la;
		try {
			setState(2118);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INC:
			case DEC:
			case ADD:
			case SUB:
				enterOuterAlt(_localctx, 1);
				{
				setState(2113);
				((UnaryExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (INC - 80)) | (1L << (DEC - 80)) | (1L << (ADD - 80)) | (1L << (SUB - 80)))) != 0)) ) {
					((UnaryExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2114);
				unaryExpression();
				}
				break;
			case BANG:
			case TILDE:
				enterOuterAlt(_localctx, 2);
				{
				setState(2115);
				((UnaryExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==BANG || _la==TILDE) ) {
					((UnaryExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2116);
				unaryExpression();
				}
				break;
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case NEW:
			case SHORT:
			case SUPER:
			case THIS:
			case VOID:
			case IntegerLiteral:
			case FloatingPointLiteral:
			case BooleanLiteral:
			case CharacterLiteral:
			case StringLiteral:
			case NullLiteral:
			case LPAREN:
			case AT:
			case Identifier:
				enterOuterAlt(_localctx, 3);
				{
				setState(2117);
				postfixExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PostfixExpressionContext extends ParserRuleContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public List<TerminalNode> INC() { return getTokens(Java8Parser.INC); }
		public TerminalNode INC(int i) {
			return getToken(Java8Parser.INC, i);
		}
		public List<TerminalNode> DEC() { return getTokens(Java8Parser.DEC); }
		public TerminalNode DEC(int i) {
			return getToken(Java8Parser.DEC, i);
		}
		public PostfixExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).enterPostfixExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Java8ParserListener ) ((Java8ParserListener)listener).exitPostfixExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Java8ParserVisitor ) return ((Java8ParserVisitor<? extends T>)visitor).visitPostfixExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PostfixExpressionContext postfixExpression() throws RecognitionException {
		PostfixExpressionContext _localctx = new PostfixExpressionContext(_ctx, getState());
		enterRule(_localctx, 256, RULE_postfixExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2122);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,279,_ctx) ) {
			case 1:
				{
				setState(2120);
				primary();
				}
				break;
			case 2:
				{
				setState(2121);
				qualifiedName();
				}
				break;
			}
			setState(2127);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,280,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(2124);
					_la = _input.LA(1);
					if ( !(_la==INC || _la==DEC) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					} 
				}
				setState(2129);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,280,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 122:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 6);
		case 2:
			return precpred(_ctx, 5);
		case 3:
			return precpred(_ctx, 4);
		case 4:
			return precpred(_ctx, 3);
		case 5:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3n\u0855\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\4k\t"+
		"k\4l\tl\4m\tm\4n\tn\4o\to\4p\tp\4q\tq\4r\tr\4s\ts\4t\tt\4u\tu\4v\tv\4"+
		"w\tw\4x\tx\4y\ty\4z\tz\4{\t{\4|\t|\4}\t}\4~\t~\4\177\t\177\4\u0080\t\u0080"+
		"\4\u0081\t\u0081\4\u0082\t\u0082\3\2\5\2\u0106\n\2\3\2\7\2\u0109\n\2\f"+
		"\2\16\2\u010c\13\2\3\2\7\2\u010f\n\2\f\2\16\2\u0112\13\2\3\2\3\2\3\3\7"+
		"\3\u0117\n\3\f\3\16\3\u011a\13\3\3\3\3\3\3\3\3\3\3\4\3\4\5\4\u0122\n\4"+
		"\3\4\3\4\3\4\5\4\u0127\n\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\5\5\u0130\n\5\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u013a\n\6\3\7\3\7\3\7\7\7\u013f\n\7"+
		"\f\7\16\7\u0142\13\7\3\b\7\b\u0145\n\b\f\b\16\b\u0148\13\b\3\b\3\b\3\b"+
		"\5\b\u014d\n\b\3\b\3\b\7\b\u0151\n\b\f\b\16\b\u0154\13\b\3\b\5\b\u0157"+
		"\n\b\3\b\3\b\5\b\u015b\n\b\3\b\3\b\3\t\3\t\3\t\3\t\7\t\u0163\n\t\f\t\16"+
		"\t\u0166\13\t\3\t\3\t\3\n\7\n\u016b\n\n\f\n\16\n\u016e\13\n\3\n\3\n\3"+
		"\n\7\n\u0173\n\n\f\n\16\n\u0176\13\n\3\n\7\n\u0179\n\n\f\n\16\n\u017c"+
		"\13\n\3\13\3\13\7\13\u0180\n\13\f\13\16\13\u0183\13\13\3\13\3\13\3\f\3"+
		"\f\5\f\u0189\n\f\3\f\3\f\5\f\u018d\n\f\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0195"+
		"\n\r\3\16\7\16\u0198\n\16\f\16\16\16\u019b\13\16\3\16\3\16\3\16\3\16\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u01a9\n\17\3\20\3\20\3\20"+
		"\7\20\u01ae\n\20\f\20\16\20\u01b1\13\20\3\21\3\21\5\21\u01b5\n\21\3\21"+
		"\3\21\5\21\u01b9\n\21\3\22\3\22\5\22\u01bd\n\22\3\23\3\23\3\24\3\24\5"+
		"\24\u01c3\n\24\3\25\3\25\5\25\u01c7\n\25\3\26\3\26\3\27\3\27\3\27\5\27"+
		"\u01ce\n\27\3\30\3\30\3\30\5\30\u01d3\n\30\3\30\7\30\u01d6\n\30\f\30\16"+
		"\30\u01d9\13\30\3\30\3\30\5\30\u01dd\n\30\3\31\3\31\5\31\u01e1\n\31\3"+
		"\31\3\31\7\31\u01e5\n\31\f\31\16\31\u01e8\13\31\3\31\3\31\5\31\u01ec\n"+
		"\31\7\31\u01ee\n\31\f\31\16\31\u01f1\13\31\3\32\3\32\3\32\3\32\3\32\3"+
		"\32\3\32\3\32\5\32\u01fb\n\32\3\33\7\33\u01fe\n\33\f\33\16\33\u0201\13"+
		"\33\3\33\3\33\3\33\7\33\u0206\n\33\f\33\16\33\u0209\13\33\3\33\3\33\7"+
		"\33\u020d\n\33\f\33\16\33\u0210\13\33\3\34\7\34\u0213\n\34\f\34\16\34"+
		"\u0216\13\34\3\34\3\34\5\34\u021a\n\34\3\35\3\35\7\35\u021e\n\35\f\35"+
		"\16\35\u0221\13\35\3\35\3\35\7\35\u0225\n\35\f\35\16\35\u0228\13\35\3"+
		"\35\3\35\7\35\u022c\n\35\f\35\16\35\u022f\13\35\5\35\u0231\n\35\3\36\3"+
		"\36\7\36\u0235\n\36\f\36\16\36\u0238\13\36\3\36\3\36\3\37\3\37\3\37\3"+
		"\37\3 \3 \3 \7 \u0243\n \f \16 \u0246\13 \3!\7!\u0249\n!\f!\16!\u024c"+
		"\13!\3!\3!\5!\u0250\n!\3\"\7\"\u0253\n\"\f\"\16\"\u0256\13\"\3\"\3\"\3"+
		"\"\7\"\u025b\n\"\f\"\16\"\u025e\13\"\3\"\3\"\3\"\7\"\u0263\n\"\f\"\16"+
		"\"\u0266\13\"\3\"\5\"\u0269\n\"\3#\7#\u026c\n#\f#\16#\u026f\13#\3#\3#"+
		"\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\5$\u027e\n$\3%\3%\3%\5%\u0283\n%\3%"+
		"\3%\7%\u0287\n%\f%\16%\u028a\13%\3%\3%\3%\5%\u028f\n%\5%\u0291\n%\3&\3"+
		"&\5&\u0295\n&\3\'\3\'\3\'\5\'\u029a\n\'\3\'\3\'\5\'\u029e\n\'\3(\3(\3"+
		"(\3(\3(\5(\u02a5\n(\3)\3)\3)\7)\u02aa\n)\f)\16)\u02ad\13)\3)\3)\3)\7)"+
		"\u02b2\n)\f)\16)\u02b5\13)\5)\u02b7\n)\3*\3*\7*\u02bb\n*\f*\16*\u02be"+
		"\13*\3*\3*\3*\5*\u02c3\n*\3+\3+\7+\u02c7\n+\f+\16+\u02ca\13+\3+\3+\7+"+
		"\u02ce\n+\f+\16+\u02d1\13+\3+\3+\3+\5+\u02d6\n+\3+\5+\u02d9\n+\3,\7,\u02dc"+
		"\n,\f,\16,\u02df\13,\3,\3,\3,\5,\u02e4\n,\3,\3,\3-\3-\3-\3.\3.\3.\7.\u02ee"+
		"\n.\f.\16.\u02f1\13.\3/\7/\u02f4\n/\f/\16/\u02f7\13/\3/\3/\7/\u02fb\n"+
		"/\f/\16/\u02fe\13/\3/\5/\u0301\n/\3\60\3\60\5\60\u0305\n\60\3\61\7\61"+
		"\u0308\n\61\f\61\16\61\u030b\13\61\3\61\3\61\5\61\u030f\n\61\3\61\3\61"+
		"\3\62\3\62\3\62\3\62\5\62\u0317\n\62\3\63\5\63\u031a\n\63\3\63\3\63\3"+
		"\63\5\63\u031f\n\63\3\63\3\63\3\64\3\64\5\64\u0325\n\64\3\64\7\64\u0328"+
		"\n\64\f\64\16\64\u032b\13\64\3\64\3\64\3\65\5\65\u0330\n\65\3\65\3\65"+
		"\3\65\5\65\u0335\n\65\3\65\3\65\3\65\5\65\u033a\n\65\3\65\3\65\3\65\5"+
		"\65\u033f\n\65\3\65\3\65\3\65\3\65\3\65\5\65\u0346\n\65\3\65\3\65\3\65"+
		"\5\65\u034b\n\65\3\65\3\65\3\65\3\65\3\65\3\65\5\65\u0353\n\65\3\65\3"+
		"\65\3\65\5\65\u0358\n\65\3\65\3\65\3\65\5\65\u035d\n\65\3\66\7\66\u0360"+
		"\n\66\f\66\16\66\u0363\13\66\3\66\3\66\3\66\3\66\5\66\u0369\n\66\3\66"+
		"\3\66\3\67\3\67\5\67\u036f\n\67\3\67\5\67\u0372\n\67\3\67\3\67\7\67\u0376"+
		"\n\67\f\67\16\67\u0379\13\67\5\67\u037b\n\67\3\67\3\67\38\38\38\78\u0382"+
		"\n8\f8\168\u0385\138\39\79\u0388\n9\f9\169\u038b\139\39\39\39\59\u0390"+
		"\n9\39\59\u0393\n9\39\59\u0396\n9\3:\7:\u0399\n:\f:\16:\u039c\13:\3:\3"+
		":\3:\5:\u03a1\n:\3:\3:\5:\u03a5\n:\3:\3:\3;\3;\7;\u03ab\n;\f;\16;\u03ae"+
		"\13;\3;\3;\3<\3<\3<\3<\3<\5<\u03b7\n<\3=\7=\u03ba\n=\f=\16=\u03bd\13="+
		"\3=\3=\3=\3=\3>\3>\3>\3>\5>\u03c7\n>\3?\7?\u03ca\n?\f?\16?\u03cd\13?\3"+
		"?\3?\3?\3@\3@\3@\3@\3@\3@\5@\u03d8\n@\3A\7A\u03db\nA\fA\16A\u03de\13A"+
		"\3A\3A\3A\3A\3A\3B\3B\7B\u03e7\nB\fB\16B\u03ea\13B\3B\3B\3C\3C\3C\3C\3"+
		"C\5C\u03f3\nC\3D\7D\u03f6\nD\fD\16D\u03f9\13D\3D\7D\u03fc\nD\fD\16D\u03ff"+
		"\13D\3D\3D\3D\3D\3D\5D\u0406\nD\3D\5D\u0409\nD\3D\3D\3E\3E\3E\3F\3F\3"+
		"F\5F\u0413\nF\3G\3G\3G\3G\5G\u0419\nG\3G\3G\3H\3H\3H\7H\u0420\nH\fH\16"+
		"H\u0423\13H\3I\3I\3I\3I\3J\3J\3J\5J\u042c\nJ\3K\3K\5K\u0430\nK\3K\5K\u0433"+
		"\nK\3K\3K\3L\3L\3L\7L\u043a\nL\fL\16L\u043d\13L\3M\3M\3M\3N\3N\3N\3N\3"+
		"N\3N\3O\3O\5O\u044a\nO\3O\5O\u044d\nO\3O\3O\3P\3P\3P\7P\u0454\nP\fP\16"+
		"P\u0457\13P\3Q\3Q\7Q\u045b\nQ\fQ\16Q\u045e\13Q\3Q\3Q\3R\3R\3R\3R\3R\3"+
		"R\3R\5R\u0469\nR\3S\3S\7S\u046d\nS\fS\16S\u0470\13S\3S\3S\3S\3T\3T\3T"+
		"\3T\3T\3T\3T\3T\3T\5T\u047e\nT\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T"+
		"\3T\3T\3T\3T\3T\5T\u0492\nT\3T\3T\3T\5T\u0497\nT\3T\3T\3T\5T\u049c\nT"+
		"\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\5T"+
		"\u04b4\nT\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\5T\u04c2\nT\3U\3U\3U\3U"+
		"\3U\3U\3U\3U\3U\3U\3U\3U\3U\5U\u04d1\nU\3V\3V\7V\u04d5\nV\fV\16V\u04d8"+
		"\13V\3V\7V\u04db\nV\fV\16V\u04de\13V\3V\3V\3W\6W\u04e3\nW\rW\16W\u04e4"+
		"\3W\6W\u04e8\nW\rW\16W\u04e9\3X\3X\3X\3X\3X\3X\3X\3X\3X\5X\u04f5\nX\3"+
		"Y\3Y\3Y\3Y\3Y\3Y\3Y\5Y\u04fe\nY\3Y\3Y\3Y\3Y\3Y\3Y\3Y\5Y\u0507\nY\3Y\3"+
		"Y\3Y\5Y\u050c\nY\3Y\3Y\5Y\u0510\nY\5Y\u0512\nY\3Z\5Z\u0515\nZ\3Z\3Z\5"+
		"Z\u0519\nZ\3Z\3Z\5Z\u051d\nZ\3Z\5Z\u0520\nZ\3Z\7Z\u0523\nZ\fZ\16Z\u0526"+
		"\13Z\3Z\3Z\3Z\5Z\u052b\nZ\3Z\3Z\3Z\5Z\u0530\nZ\3[\3[\5[\u0534\n[\3\\\3"+
		"\\\3\\\7\\\u0539\n\\\f\\\16\\\u053c\13\\\3]\3]\3]\3]\3]\3]\6]\u0544\n"+
		"]\r]\16]\u0545\3^\5^\u0549\n^\3^\7^\u054c\n^\f^\16^\u054f\13^\3^\3^\3"+
		"^\5^\u0554\n^\3_\3_\3_\7_\u0559\n_\f_\16_\u055c\13_\3_\7_\u055f\n_\f_"+
		"\16_\u0562\13_\3`\3`\3`\7`\u0567\n`\f`\16`\u056a\13`\3a\3a\7a\u056e\n"+
		"a\fa\16a\u0571\13a\3a\3a\3a\5a\u0576\na\3a\3a\3a\3b\3b\5b\u057d\nb\3b"+
		"\7b\u0580\nb\fb\16b\u0583\13b\3c\3c\3c\3c\7c\u0589\nc\fc\16c\u058c\13"+
		"c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\3c\5c\u05a2"+
		"\nc\3d\3d\3d\3d\7d\u05a8\nd\fd\16d\u05ab\13d\3d\3d\3d\3d\3d\3d\3d\3d\3"+
		"d\3d\3d\3d\3d\3d\3d\3d\3d\3d\3d\5d\u05c0\nd\3e\3e\3e\3e\3e\5e\u05c7\n"+
		"e\3e\3e\3e\5e\u05cc\ne\3e\3e\3e\5e\u05d1\ne\3e\5e\u05d4\ne\3f\3f\3f\3"+
		"f\5f\u05da\nf\3f\3f\3f\5f\u05df\nf\3f\3f\3f\5f\u05e4\nf\3f\5f\u05e7\n"+
		"f\3g\3g\3g\3g\7g\u05ed\ng\fg\16g\u05f0\13g\3g\3g\3g\3g\3g\3g\7g\u05f8"+
		"\ng\fg\16g\u05fb\13g\3g\3g\3g\3g\3g\3g\3g\3g\3g\3g\3g\3g\3g\3g\3g\3g\3"+
		"g\3g\3g\3g\5g\u0611\ng\3h\3h\3h\3h\7h\u0617\nh\fh\16h\u061a\13h\3h\3h"+
		"\3h\3h\3h\3h\7h\u0622\nh\fh\16h\u0625\13h\3h\3h\3h\3h\3h\3h\3h\3h\3h\3"+
		"h\3h\3h\3h\3h\3h\3h\3h\3h\3h\5h\u063a\nh\3i\3i\7i\u063e\ni\fi\16i\u0641"+
		"\13i\3i\3i\6i\u0645\ni\ri\16i\u0646\3i\5i\u064a\ni\3i\3i\3i\5i\u064f\n"+
		"i\3i\3i\6i\u0653\ni\ri\16i\u0654\3i\5i\u0658\ni\3i\3i\3i\5i\u065d\ni\5"+
		"i\u065f\ni\3j\3j\5j\u0663\nj\3j\6j\u0666\nj\rj\16j\u0667\3k\5k\u066b\n"+
		"k\3k\6k\u066e\nk\rk\16k\u066f\3l\3l\5l\u0674\nl\3l\6l\u0677\nl\rl\16l"+
		"\u0678\3m\3m\3m\3m\6m\u067f\nm\rm\16m\u0680\3n\3n\3n\3n\3n\3n\5n\u0689"+
		"\nn\3n\3n\3o\3o\3o\3p\3p\3p\5p\u0693\np\3p\3p\3q\3q\5q\u0699\nq\3q\7q"+
		"\u069c\nq\fq\16q\u069f\13q\3q\3q\3q\7q\u06a4\nq\fq\16q\u06a7\13q\3q\7"+
		"q\u06aa\nq\fq\16q\u06ad\13q\3q\5q\u06b0\nq\3q\3q\5q\u06b4\nq\3q\3q\5q"+
		"\u06b8\nq\3r\3r\3r\5r\u06bd\nr\3s\3s\3s\3s\3s\3s\3s\3s\3s\3s\3s\3s\3s"+
		"\5s\u06cc\ns\3t\3t\3t\3u\3u\3u\3u\3u\3u\3u\3u\3u\5u\u06da\nu\3v\3v\3v"+
		"\5v\u06df\nv\3v\3v\3v\3v\5v\u06e5\nv\3v\3v\3v\5v\u06ea\nv\3v\3v\3v\3v"+
		"\3v\5v\u06f1\nv\3v\3v\3v\5v\u06f6\nv\3v\3v\3v\3v\3v\5v\u06fd\nv\3v\3v"+
		"\3v\5v\u0702\nv\3v\3v\3v\3v\3v\3v\5v\u070a\nv\3v\3v\3v\5v\u070f\nv\3v"+
		"\3v\5v\u0713\nv\3w\3w\3w\5w\u0718\nw\3w\3w\3w\3w\5w\u071e\nw\3w\3w\3w"+
		"\5w\u0723\nw\3w\3w\3w\3w\3w\5w\u072a\nw\3w\3w\3w\5w\u072f\nw\3w\3w\3w"+
		"\3w\3w\3w\5w\u0737\nw\3w\3w\3w\5w\u073c\nw\3w\3w\5w\u0740\nw\3x\3x\3x"+
		"\7x\u0745\nx\fx\16x\u0748\13x\3y\3y\3y\5y\u074d\ny\3y\3y\3y\3y\3y\5y\u0754"+
		"\ny\3y\3y\3y\7y\u0759\ny\fy\16y\u075c\13y\3y\3y\3y\5y\u0761\ny\3y\3y\3"+
		"y\3y\3y\5y\u0768\ny\3y\3y\3y\3y\3y\3y\5y\u0770\ny\3y\3y\3y\7y\u0775\n"+
		"y\fy\16y\u0778\13y\3y\3y\3y\5y\u077d\ny\3y\3y\3y\7y\u0782\ny\fy\16y\u0785"+
		"\13y\3y\3y\3y\3y\5y\u078b\ny\3z\3z\3z\5z\u0790\nz\3z\3z\3z\7z\u0795\n"+
		"z\fz\16z\u0798\13z\3z\3z\3z\5z\u079d\nz\3z\3z\3z\3z\3z\5z\u07a4\nz\3z"+
		"\3z\3z\3z\3z\3z\5z\u07ac\nz\3z\3z\3z\7z\u07b1\nz\fz\16z\u07b4\13z\3z\3"+
		"z\3z\5z\u07b9\nz\3z\3z\3z\7z\u07be\nz\fz\16z\u07c1\13z\3z\3z\3z\3z\5z"+
		"\u07c7\nz\3{\7{\u07ca\n{\f{\16{\u07cd\13{\3{\3{\3{\3{\3|\3|\3|\3|\3|\3"+
		"|\3|\5|\u07da\n|\3|\3|\3|\3|\3|\3|\3|\3|\3|\3|\3|\3|\3|\3|\5|\u07ea\n"+
		"|\3|\3|\3|\3|\3|\3|\3|\3|\3|\3|\3|\3|\3|\7|\u07f9\n|\f|\16|\u07fc\13|"+
		"\3|\7|\u07ff\n|\f|\16|\u0802\13|\3}\3}\3}\5}\u0807\n}\3}\3}\3}\3~\3~\7"+
		"~\u080e\n~\f~\16~\u0811\13~\3~\3~\3~\3~\3~\3~\7~\u0819\n~\f~\16~\u081c"+
		"\13~\3~\3~\7~\u0820\n~\f~\16~\u0823\13~\3~\3~\3~\5~\u0828\n~\5~\u082a"+
		"\n~\3\177\3\177\3\177\3\177\5\177\u0830\n\177\3\u0080\3\u0080\3\u0080"+
		"\5\u0080\u0835\n\u0080\3\u0080\3\u0080\3\u0080\3\u0080\3\u0080\7\u0080"+
		"\u083c\n\u0080\f\u0080\16\u0080\u083f\13\u0080\3\u0080\5\u0080\u0842\n"+
		"\u0080\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\5\u0081\u0849\n\u0081\3"+
		"\u0082\3\u0082\5\u0082\u084d\n\u0082\3\u0082\7\u0082\u0850\n\u0082\f\u0082"+
		"\16\u0082\u0853\13\u0082\3\u0082\2\3\u00f6\u0083\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnp"+
		"rtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094"+
		"\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac"+
		"\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8\u00ba\u00bc\u00be\u00c0\u00c2\u00c4"+
		"\u00c6\u00c8\u00ca\u00cc\u00ce\u00d0\u00d2\u00d4\u00d6\u00d8\u00da\u00dc"+
		"\u00de\u00e0\u00e2\u00e4\u00e6\u00e8\u00ea\u00ec\u00ee\u00f0\u00f2\u00f4"+
		"\u00f6\u00f8\u00fa\u00fc\u00fe\u0100\u0102\2\f\3\2\65:\t\2\7\7\n\n\20"+
		"\20\26\26\35\35\37\37\'\'\4\2\3\3%%\4\2TW[[\4\2EFMN\5\2LLOQXZ\4\2DD]g"+
		"\3\2RU\3\2GH\3\2RS\2\u097a\2\u0105\3\2\2\2\4\u0118\3\2\2\2\6\u011f\3\2"+
		"\2\2\b\u012f\3\2\2\2\n\u0139\3\2\2\2\f\u013b\3\2\2\2\16\u0146\3\2\2\2"+
		"\20\u015e\3\2\2\2\22\u016c\3\2\2\2\24\u017d\3\2\2\2\26\u018c\3\2\2\2\30"+
		"\u0194\3\2\2\2\32\u0199\3\2\2\2\34\u01a8\3\2\2\2\36\u01aa\3\2\2\2 \u01b2"+
		"\3\2\2\2\"\u01bc\3\2\2\2$\u01be\3\2\2\2&\u01c2\3\2\2\2(\u01c6\3\2\2\2"+
		"*\u01c8\3\2\2\2,\u01cd\3\2\2\2.\u01d2\3\2\2\2\60\u01de\3\2\2\2\62\u01fa"+
		"\3\2\2\2\64\u01ff\3\2\2\2\66\u0214\3\2\2\28\u021b\3\2\2\2:\u0232\3\2\2"+
		"\2<\u023b\3\2\2\2>\u023f\3\2\2\2@\u024f\3\2\2\2B\u0254\3\2\2\2D\u026d"+
		"\3\2\2\2F\u027d\3\2\2\2H\u0290\3\2\2\2J\u0294\3\2\2\2L\u0296\3\2\2\2N"+
		"\u02a4\3\2\2\2P\u02b6\3\2\2\2R\u02bc\3\2\2\2T\u02d8\3\2\2\2V\u02dd\3\2"+
		"\2\2X\u02e7\3\2\2\2Z\u02ea\3\2\2\2\\\u0300\3\2\2\2^\u0304\3\2\2\2`\u0309"+
		"\3\2\2\2b\u0316\3\2\2\2d\u0319\3\2\2\2f\u0322\3\2\2\2h\u035c\3\2\2\2j"+
		"\u0361\3\2\2\2l\u036c\3\2\2\2n\u037e\3\2\2\2p\u0389\3\2\2\2r\u039a\3\2"+
		"\2\2t\u03a8\3\2\2\2v\u03b6\3\2\2\2x\u03bb\3\2\2\2z\u03c6\3\2\2\2|\u03cb"+
		"\3\2\2\2~\u03d7\3\2\2\2\u0080\u03dc\3\2\2\2\u0082\u03e4\3\2\2\2\u0084"+
		"\u03f2\3\2\2\2\u0086\u03f7\3\2\2\2\u0088\u040c\3\2\2\2\u008a\u0412\3\2"+
		"\2\2\u008c\u0414\3\2\2\2\u008e\u041c\3\2\2\2\u0090\u0424\3\2\2\2\u0092"+
		"\u042b\3\2\2\2\u0094\u042d\3\2\2\2\u0096\u0436\3\2\2\2\u0098\u043e\3\2"+
		"\2\2\u009a\u0441\3\2\2\2\u009c\u0447\3\2\2\2\u009e\u0450\3\2\2\2\u00a0"+
		"\u0458\3\2\2\2\u00a2\u0468\3\2\2\2\u00a4\u046e\3\2\2\2\u00a6\u04c1\3\2"+
		"\2\2\u00a8\u04d0\3\2\2\2\u00aa\u04d2\3\2\2\2\u00ac\u04e2\3\2\2\2\u00ae"+
		"\u04f4\3\2\2\2\u00b0\u0511\3\2\2\2\u00b2\u052f\3\2\2\2\u00b4\u0533\3\2"+
		"\2\2\u00b6\u0535\3\2\2\2\u00b8\u0543\3\2\2\2\u00ba\u0548\3\2\2\2\u00bc"+
		"\u0555\3\2\2\2\u00be\u0563\3\2\2\2\u00c0\u056f\3\2\2\2\u00c2\u057c\3\2"+
		"\2\2\u00c4\u05a1\3\2\2\2\u00c6\u05bf\3\2\2\2\u00c8\u05d3\3\2\2\2\u00ca"+
		"\u05e6\3\2\2\2\u00cc\u0610\3\2\2\2\u00ce\u0639\3\2\2\2\u00d0\u063b\3\2"+
		"\2\2\u00d2\u0662\3\2\2\2\u00d4\u066a\3\2\2\2\u00d6\u0673\3\2\2\2\u00d8"+
		"\u067e\3\2\2\2\u00da\u0688\3\2\2\2\u00dc\u068c\3\2\2\2\u00de\u0692\3\2"+
		"\2\2\u00e0\u0696\3\2\2\2\u00e2\u06bc\3\2\2\2\u00e4\u06cb\3\2\2\2\u00e6"+
		"\u06cd\3\2\2\2\u00e8\u06d9\3\2\2\2\u00ea\u0712\3\2\2\2\u00ec\u073f\3\2"+
		"\2\2\u00ee\u0741\3\2\2\2\u00f0\u078a\3\2\2\2\u00f2\u07c6\3\2\2\2\u00f4"+
		"\u07cb\3\2\2\2\u00f6\u07d9\3\2\2\2\u00f8\u0806\3\2\2\2\u00fa\u0829\3\2"+
		"\2\2\u00fc\u082b\3\2\2\2\u00fe\u0841\3\2\2\2\u0100\u0848\3\2\2\2\u0102"+
		"\u084c\3\2\2\2\u0104\u0106\5\4\3\2\u0105\u0104\3\2\2\2\u0105\u0106\3\2"+
		"\2\2\u0106\u010a\3\2\2\2\u0107\u0109\5\6\4\2\u0108\u0107\3\2\2\2\u0109"+
		"\u010c\3\2\2\2\u010a\u0108\3\2\2\2\u010a\u010b\3\2\2\2\u010b\u0110\3\2"+
		"\2\2\u010c\u010a\3\2\2\2\u010d\u010f\5\b\5\2\u010e\u010d\3\2\2\2\u010f"+
		"\u0112\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111\u0113\3\2"+
		"\2\2\u0112\u0110\3\2\2\2\u0113\u0114\7\2\2\3\u0114\3\3\2\2\2\u0115\u0117"+
		"\5\u008aF\2\u0116\u0115\3\2\2\2\u0117\u011a\3\2\2\2\u0118\u0116\3\2\2"+
		"\2\u0118\u0119\3\2\2\2\u0119\u011b\3\2\2\2\u011a\u0118\3\2\2\2\u011b\u011c"+
		"\7\"\2\2\u011c\u011d\5\f\7\2\u011d\u011e\7A\2\2\u011e\5\3\2\2\2\u011f"+
		"\u0121\7\33\2\2\u0120\u0122\7(\2\2\u0121\u0120\3\2\2\2\u0121\u0122\3\2"+
		"\2\2\u0122\u0123\3\2\2\2\u0123\u0126\5\f\7\2\u0124\u0125\7C\2\2\u0125"+
		"\u0127\7V\2\2\u0126\u0124\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u0128\3\2"+
		"\2\2\u0128\u0129\7A\2\2\u0129\7\3\2\2\2\u012a\u0130\5\16\b\2\u012b\u0130"+
		"\5j\66\2\u012c\u0130\5r:\2\u012d\u0130\5\u0080A\2\u012e\u0130\7A\2\2\u012f"+
		"\u012a\3\2\2\2\u012f\u012b\3\2\2\2\u012f\u012c\3\2\2\2\u012f\u012d\3\2"+
		"\2\2\u012f\u012e\3\2\2\2\u0130\t\3\2\2\2\u0131\u013a\5\u008aF\2\u0132"+
		"\u013a\7%\2\2\u0133\u013a\7$\2\2\u0134\u013a\7#\2\2\u0135\u013a\7\3\2"+
		"\2\u0136\u013a\7(\2\2\u0137\u013a\7\24\2\2\u0138\u013a\7)\2\2\u0139\u0131"+
		"\3\2\2\2\u0139\u0132\3\2\2\2\u0139\u0133\3\2\2\2\u0139\u0134\3\2\2\2\u0139"+
		"\u0135\3\2\2\2\u0139\u0136\3\2\2\2\u0139\u0137\3\2\2\2\u0139\u0138\3\2"+
		"\2\2\u013a\13\3\2\2\2\u013b\u0140\7j\2\2\u013c\u013d\7C\2\2\u013d\u013f"+
		"\7j\2\2\u013e\u013c\3\2\2\2\u013f\u0142\3\2\2\2\u0140\u013e\3\2\2\2\u0140"+
		"\u0141\3\2\2\2\u0141\r\3\2\2\2\u0142\u0140\3\2\2\2\u0143\u0145\5\n\6\2"+
		"\u0144\u0143\3\2\2\2\u0145\u0148\3\2\2\2\u0146\u0144\3\2\2\2\u0146\u0147"+
		"\3\2\2\2\u0147\u0149\3\2\2\2\u0148\u0146\3\2\2\2\u0149\u014a\7\13\2\2"+
		"\u014a\u014c\7j\2\2\u014b\u014d\5\20\t\2\u014c\u014b\3\2\2\2\u014c\u014d"+
		"\3\2\2\2\u014d\u0156\3\2\2\2\u014e\u0152\7\23\2\2\u014f\u0151\5\u008a"+
		"F\2\u0150\u014f\3\2\2\2\u0151\u0154\3\2\2\2\u0152\u0150\3\2\2\2\u0152"+
		"\u0153\3\2\2\2\u0153\u0155\3\2\2\2\u0154\u0152\3\2\2\2\u0155\u0157\5."+
		"\30\2\u0156\u014e\3\2\2\2\u0156\u0157\3\2\2\2\u0157\u015a\3\2\2\2\u0158"+
		"\u0159\7\32\2\2\u0159\u015b\5\22\n\2\u015a\u0158\3\2\2\2\u015a\u015b\3"+
		"\2\2\2\u015b\u015c\3\2\2\2\u015c\u015d\5\24\13\2\u015d\17\3\2\2\2\u015e"+
		"\u015f\7F\2\2\u015f\u0164\5\66\34\2\u0160\u0161\7B\2\2\u0161\u0163\5\66"+
		"\34\2\u0162\u0160\3\2\2\2\u0163\u0166\3\2\2\2\u0164\u0162\3\2\2\2\u0164"+
		"\u0165\3\2\2\2\u0165\u0167\3\2\2\2\u0166\u0164\3\2\2\2\u0167\u0168\7E"+
		"\2\2\u0168\21\3\2\2\2\u0169\u016b\5\u008aF\2\u016a\u0169\3\2\2\2\u016b"+
		"\u016e\3\2\2\2\u016c\u016a\3\2\2\2\u016c\u016d\3\2\2\2\u016d\u016f\3\2"+
		"\2\2\u016e\u016c\3\2\2\2\u016f\u017a\5.\30\2\u0170\u0174\7B\2\2\u0171"+
		"\u0173\5\u008aF\2\u0172\u0171\3\2\2\2\u0173\u0176\3\2\2\2\u0174\u0172"+
		"\3\2\2\2\u0174\u0175\3\2\2\2\u0175\u0177\3\2\2\2\u0176\u0174\3\2\2\2\u0177"+
		"\u0179\5.\30\2\u0178\u0170\3\2\2\2\u0179\u017c\3\2\2\2\u017a\u0178\3\2"+
		"\2\2\u017a\u017b\3\2\2\2\u017b\23\3\2\2\2\u017c\u017a\3\2\2\2\u017d\u0181"+
		"\7=\2\2\u017e\u0180\5\26\f\2\u017f\u017e\3\2\2\2\u0180\u0183\3\2\2\2\u0181"+
		"\u017f\3\2\2\2\u0181\u0182\3\2\2\2\u0182\u0184\3\2\2\2\u0183\u0181\3\2"+
		"\2\2\u0184\u0185\7>\2\2\u0185\25\3\2\2\2\u0186\u018d\5\30\r\2\u0187\u0189"+
		"\7(\2\2\u0188\u0187\3\2\2\2\u0188\u0189\3\2\2\2\u0189\u018a\3\2\2\2\u018a"+
		"\u018d\5\u00a0Q\2\u018b\u018d\5`\61\2\u018c\u0186\3\2\2\2\u018c\u0188"+
		"\3\2\2\2\u018c\u018b\3\2\2\2\u018d\27\3\2\2\2\u018e\u0195\5\32\16\2\u018f"+
		"\u0195\5D#\2\u0190\u0195\5j\66\2\u0191\u0195\5\16\b\2\u0192\u0195\5r:"+
		"\2\u0193\u0195\7A\2\2\u0194\u018e\3\2\2\2\u0194\u018f\3\2\2\2\u0194\u0190"+
		"\3\2\2\2\u0194\u0191\3\2\2\2\u0194\u0192\3\2\2\2\u0194\u0193\3\2\2\2\u0195"+
		"\31\3\2\2\2\u0196\u0198\5\34\17\2\u0197\u0196\3\2\2\2\u0198\u019b\3\2"+
		"\2\2\u0199\u0197\3\2\2\2\u0199\u019a\3\2\2\2\u019a\u019c\3\2\2\2\u019b"+
		"\u0199\3\2\2\2\u019c\u019d\5&\24\2\u019d\u019e\5\36\20\2\u019e\u019f\7"+
		"A\2\2\u019f\33\3\2\2\2\u01a0\u01a9\5\u008aF\2\u01a1\u01a9\7%\2\2\u01a2"+
		"\u01a9\7$\2\2\u01a3\u01a9\7#\2\2\u01a4\u01a9\7(\2\2\u01a5\u01a9\7\24\2"+
		"\2\u01a6\u01a9\7\60\2\2\u01a7\u01a9\7\63\2\2\u01a8\u01a0\3\2\2\2\u01a8"+
		"\u01a1\3\2\2\2\u01a8\u01a2\3\2\2\2\u01a8\u01a3\3\2\2\2\u01a8\u01a4\3\2"+
		"\2\2\u01a8\u01a5\3\2\2\2\u01a8\u01a6\3\2\2\2\u01a8\u01a7\3\2\2\2\u01a9"+
		"\35\3\2\2\2\u01aa\u01af\5 \21\2\u01ab\u01ac\7B\2\2\u01ac\u01ae\5 \21\2"+
		"\u01ad\u01ab\3\2\2\2\u01ae\u01b1\3\2\2\2\u01af\u01ad\3\2\2\2\u01af\u01b0"+
		"\3\2\2\2\u01b0\37\3\2\2\2\u01b1\u01af\3\2\2\2\u01b2\u01b4\7j\2\2\u01b3"+
		"\u01b5\5\64\33\2\u01b4\u01b3\3\2\2\2\u01b4\u01b5\3\2\2\2\u01b5\u01b8\3"+
		"\2\2\2\u01b6\u01b7\7D\2\2\u01b7\u01b9\5\"\22\2\u01b8\u01b6\3\2\2\2\u01b8"+
		"\u01b9\3\2\2\2\u01b9!\3\2\2\2\u01ba\u01bd\5\u00f6|\2\u01bb\u01bd\5\u009c"+
		"O\2\u01bc\u01ba\3\2\2\2\u01bc\u01bb\3\2\2\2\u01bd#\3\2\2\2\u01be\u01bf"+
		"\t\2\2\2\u01bf%\3\2\2\2\u01c0\u01c3\5(\25\2\u01c1\u01c3\5,\27\2\u01c2"+
		"\u01c0\3\2\2\2\u01c2\u01c1\3\2\2\2\u01c3\'\3\2\2\2\u01c4\u01c7\5*\26\2"+
		"\u01c5\u01c7\7\5\2\2\u01c6\u01c4\3\2\2\2\u01c6\u01c5\3\2\2\2\u01c7)\3"+
		"\2\2\2\u01c8\u01c9\t\3\2\2\u01c9+\3\2\2\2\u01ca\u01ce\5\60\31\2\u01cb"+
		"\u01ce\7j\2\2\u01cc\u01ce\5\62\32\2\u01cd\u01ca\3\2\2\2\u01cd\u01cb\3"+
		"\2\2\2\u01cd\u01cc\3\2\2\2\u01ce-\3\2\2\2\u01cf\u01d0\5\60\31\2\u01d0"+
		"\u01d1\7C\2\2\u01d1\u01d3\3\2\2\2\u01d2\u01cf\3\2\2\2\u01d2\u01d3\3\2"+
		"\2\2\u01d3\u01d7\3\2\2\2\u01d4\u01d6\5\u008aF\2\u01d5\u01d4\3\2\2\2\u01d6"+
		"\u01d9\3\2\2\2\u01d7\u01d5\3\2\2\2\u01d7\u01d8\3\2\2\2\u01d8\u01da\3\2"+
		"\2\2\u01d9\u01d7\3\2\2\2\u01da\u01dc\7j\2\2\u01db\u01dd\5<\37\2\u01dc"+
		"\u01db\3\2\2\2\u01dc\u01dd\3\2\2\2\u01dd/\3\2\2\2\u01de\u01e0\7j\2\2\u01df"+
		"\u01e1\5<\37\2\u01e0\u01df\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1\u01ef\3\2"+
		"\2\2\u01e2\u01e6\7C\2\2\u01e3\u01e5\5\u008aF\2\u01e4\u01e3\3\2\2\2\u01e5"+
		"\u01e8\3\2\2\2\u01e6\u01e4\3\2\2\2\u01e6\u01e7\3\2\2\2\u01e7\u01e9\3\2"+
		"\2\2\u01e8\u01e6\3\2\2\2\u01e9\u01eb\7j\2\2\u01ea\u01ec\5<\37\2\u01eb"+
		"\u01ea\3\2\2\2\u01eb\u01ec\3\2\2\2\u01ec\u01ee\3\2\2\2\u01ed\u01e2\3\2"+
		"\2\2\u01ee\u01f1\3\2\2\2\u01ef\u01ed\3\2\2\2\u01ef\u01f0\3\2\2\2\u01f0"+
		"\61\3\2\2\2\u01f1\u01ef\3\2\2\2\u01f2\u01f3\5(\25\2\u01f3\u01f4\5\64\33"+
		"\2\u01f4\u01fb\3\2\2\2\u01f5\u01f6\5\60\31\2\u01f6\u01f7\5\64\33\2\u01f7"+
		"\u01fb\3\2\2\2\u01f8\u01f9\7j\2\2\u01f9\u01fb\5\64\33\2\u01fa\u01f2\3"+
		"\2\2\2\u01fa\u01f5\3\2\2\2\u01fa\u01f8\3\2\2\2\u01fb\63\3\2\2\2\u01fc"+
		"\u01fe\5\u008aF\2\u01fd\u01fc\3\2\2\2\u01fe\u0201\3\2\2\2\u01ff\u01fd"+
		"\3\2\2\2\u01ff\u0200\3\2\2\2\u0200\u0202\3\2\2\2\u0201\u01ff\3\2\2\2\u0202"+
		"\u0203\7?\2\2\u0203\u020e\7@\2\2\u0204\u0206\5\u008aF\2\u0205\u0204\3"+
		"\2\2\2\u0206\u0209\3\2\2\2\u0207\u0205\3\2\2\2\u0207\u0208\3\2\2\2\u0208"+
		"\u020a\3\2\2\2\u0209\u0207\3\2\2\2\u020a\u020b\7?\2\2\u020b\u020d\7@\2"+
		"\2\u020c\u0207\3\2\2\2\u020d\u0210\3\2\2\2\u020e\u020c\3\2\2\2\u020e\u020f"+
		"\3\2\2\2\u020f\65\3\2\2\2\u0210\u020e\3\2\2\2\u0211\u0213\5\u008aF\2\u0212"+
		"\u0211\3\2\2\2\u0213\u0216\3\2\2\2\u0214\u0212\3\2\2\2\u0214\u0215\3\2"+
		"\2\2\u0215\u0217\3\2\2\2\u0216\u0214\3\2\2\2\u0217\u0219\7j\2\2\u0218"+
		"\u021a\58\35\2\u0219\u0218\3\2\2\2\u0219\u021a\3\2\2\2\u021a\67\3\2\2"+
		"\2\u021b\u0230\7\23\2\2\u021c\u021e\5\u008aF\2\u021d\u021c\3\2\2\2\u021e"+
		"\u0221\3\2\2\2\u021f\u021d\3\2\2\2\u021f\u0220\3\2\2\2\u0220\u0222\3\2"+
		"\2\2\u0221\u021f\3\2\2\2\u0222\u0231\7j\2\2\u0223\u0225\5\u008aF\2\u0224"+
		"\u0223\3\2\2\2\u0225\u0228\3\2\2\2\u0226\u0224\3\2\2\2\u0226\u0227\3\2"+
		"\2\2\u0227\u0229\3\2\2\2\u0228\u0226\3\2\2\2\u0229\u022d\5\60\31\2\u022a"+
		"\u022c\5:\36\2\u022b\u022a\3\2\2\2\u022c\u022f\3\2\2\2\u022d\u022b\3\2"+
		"\2\2\u022d\u022e\3\2\2\2\u022e\u0231\3\2\2\2\u022f\u022d\3\2\2\2\u0230"+
		"\u021f\3\2\2\2\u0230\u0226\3\2\2\2\u02319\3\2\2\2\u0232\u0236\7X\2\2\u0233"+
		"\u0235\5\u008aF\2\u0234\u0233\3\2\2\2\u0235\u0238\3\2\2\2\u0236\u0234"+
		"\3\2\2\2\u0236\u0237\3\2\2\2\u0237\u0239\3\2\2\2\u0238\u0236\3\2\2\2\u0239"+
		"\u023a\5.\30\2\u023a;\3\2\2\2\u023b\u023c\7F\2\2\u023c\u023d\5> \2\u023d"+
		"\u023e\7E\2\2\u023e=\3\2\2\2\u023f\u0244\5@!\2\u0240\u0241\7B\2\2\u0241"+
		"\u0243\5@!\2\u0242\u0240\3\2\2\2\u0243\u0246\3\2\2\2\u0244\u0242\3\2\2"+
		"\2\u0244\u0245\3\2\2\2\u0245?\3\2\2\2\u0246\u0244\3\2\2\2\u0247\u0249"+
		"\5\u008aF\2\u0248\u0247\3\2\2\2\u0249\u024c\3\2\2\2\u024a\u0248\3\2\2"+
		"\2\u024a\u024b\3\2\2\2\u024b\u024d\3\2\2\2\u024c\u024a\3\2\2\2\u024d\u0250"+
		"\5,\27\2\u024e\u0250\5B\"\2\u024f\u024a\3\2\2\2\u024f\u024e\3\2\2\2\u0250"+
		"A\3\2\2\2\u0251\u0253\5\u008aF\2\u0252\u0251\3\2\2\2\u0253\u0256\3\2\2"+
		"\2\u0254\u0252\3\2\2\2\u0254\u0255\3\2\2\2\u0255\u0257\3\2\2\2\u0256\u0254"+
		"\3\2\2\2\u0257\u0268\7I\2\2\u0258\u025c\7\23\2\2\u0259\u025b\5\u008aF"+
		"\2\u025a\u0259\3\2\2\2\u025b\u025e\3\2\2\2\u025c\u025a\3\2\2\2\u025c\u025d"+
		"\3\2\2\2\u025d\u025f\3\2\2\2\u025e\u025c\3\2\2\2\u025f\u0269\5,\27\2\u0260"+
		"\u0264\7*\2\2\u0261\u0263\5\u008aF\2\u0262\u0261\3\2\2\2\u0263\u0266\3"+
		"\2\2\2\u0264\u0262\3\2\2\2\u0264\u0265\3\2\2\2\u0265\u0267\3\2\2\2\u0266"+
		"\u0264\3\2\2\2\u0267\u0269\5,\27\2\u0268\u0258\3\2\2\2\u0268\u0260\3\2"+
		"\2\2\u0268\u0269\3\2\2\2\u0269C\3\2\2\2\u026a\u026c\5F$\2\u026b\u026a"+
		"\3\2\2\2\u026c\u026f\3\2\2\2\u026d\u026b\3\2\2\2\u026d\u026e\3\2\2\2\u026e"+
		"\u0270\3\2\2\2\u026f\u026d\3\2\2\2\u0270\u0271\5H%\2\u0271\u0272\5^\60"+
		"\2\u0272E\3\2\2\2\u0273\u027e\5\u008aF\2\u0274\u027e\7%\2\2\u0275\u027e"+
		"\7$\2\2\u0276\u027e\7#\2\2\u0277\u027e\7\3\2\2\u0278\u027e\7(\2\2\u0279"+
		"\u027e\7\24\2\2\u027a\u027e\7,\2\2\u027b\u027e\7 \2\2\u027c\u027e\7)\2"+
		"\2\u027d\u0273\3\2\2\2\u027d\u0274\3\2\2\2\u027d\u0275\3\2\2\2\u027d\u0276"+
		"\3\2\2\2\u027d\u0277\3\2\2\2\u027d\u0278\3\2\2\2\u027d\u0279\3\2\2\2\u027d"+
		"\u027a\3\2\2\2\u027d\u027b\3\2\2\2\u027d\u027c\3\2\2\2\u027eG\3\2\2\2"+
		"\u027f\u0280\5J&\2\u0280\u0282\5L\'\2\u0281\u0283\5X-\2\u0282\u0281\3"+
		"\2\2\2\u0282\u0283\3\2\2\2\u0283\u0291\3\2\2\2\u0284\u0288\5\20\t\2\u0285"+
		"\u0287\5\u008aF\2\u0286\u0285\3\2\2\2\u0287\u028a\3\2\2\2\u0288\u0286"+
		"\3\2\2\2\u0288\u0289\3\2\2\2\u0289\u028b\3\2\2\2\u028a\u0288\3\2\2\2\u028b"+
		"\u028c\5J&\2\u028c\u028e\5L\'\2\u028d\u028f\5X-\2\u028e\u028d\3\2\2\2"+
		"\u028e\u028f\3\2\2\2\u028f\u0291\3\2\2\2\u0290\u027f\3\2\2\2\u0290\u0284"+
		"\3\2\2\2\u0291I\3\2\2\2\u0292\u0295\5&\24\2\u0293\u0295\7\62\2\2\u0294"+
		"\u0292\3\2\2\2\u0294\u0293\3\2\2\2\u0295K\3\2\2\2\u0296\u0297\7j\2\2\u0297"+
		"\u0299\7;\2\2\u0298\u029a\5N(\2\u0299\u0298\3\2\2\2\u0299\u029a\3\2\2"+
		"\2\u029a\u029b\3\2\2\2\u029b\u029d\7<\2\2\u029c\u029e\5\64\33\2\u029d"+
		"\u029c\3\2\2\2\u029d\u029e\3\2\2\2\u029eM\3\2\2\2\u029f\u02a0\5P)\2\u02a0"+
		"\u02a1\7B\2\2\u02a1\u02a2\5T+\2\u02a2\u02a5\3\2\2\2\u02a3\u02a5\5T+\2"+
		"\u02a4\u029f\3\2\2\2\u02a4\u02a3\3\2\2\2\u02a5O\3\2\2\2\u02a6\u02ab\5"+
		"R*\2\u02a7\u02a8\7B\2\2\u02a8\u02aa\5R*\2\u02a9\u02a7\3\2\2\2\u02aa\u02ad"+
		"\3\2\2\2\u02ab\u02a9\3\2\2\2\u02ab\u02ac\3\2\2\2\u02ac\u02b7\3\2\2\2\u02ad"+
		"\u02ab\3\2\2\2\u02ae\u02b3\5V,\2\u02af\u02b0\7B\2\2\u02b0\u02b2\5R*\2"+
		"\u02b1\u02af\3\2\2\2\u02b2\u02b5\3\2\2\2\u02b3\u02b1\3\2\2\2\u02b3\u02b4"+
		"\3\2\2\2\u02b4\u02b7\3\2\2\2\u02b5\u02b3\3\2\2\2\u02b6\u02a6\3\2\2\2\u02b6"+
		"\u02ae\3\2\2\2\u02b7Q\3\2\2\2\u02b8\u02bb\7\24\2\2\u02b9\u02bb\5\u008a"+
		"F\2\u02ba\u02b8\3\2\2\2\u02ba\u02b9\3\2\2\2\u02bb\u02be\3\2\2\2\u02bc"+
		"\u02ba\3\2\2\2\u02bc\u02bd\3\2\2\2\u02bd\u02bf\3\2\2\2\u02be\u02bc\3\2"+
		"\2\2\u02bf\u02c0\5&\24\2\u02c0\u02c2\7j\2\2\u02c1\u02c3\5\64\33\2\u02c2"+
		"\u02c1\3\2\2\2\u02c2\u02c3\3\2\2\2\u02c3S\3\2\2\2\u02c4\u02c7\7\24\2\2"+
		"\u02c5\u02c7\5\u008aF\2\u02c6\u02c4\3\2\2\2\u02c6\u02c5\3\2\2\2\u02c7"+
		"\u02ca\3\2\2\2\u02c8\u02c6\3\2\2\2\u02c8\u02c9\3\2\2\2\u02c9\u02cb\3\2"+
		"\2\2\u02ca\u02c8\3\2\2\2\u02cb\u02cf\5&\24\2\u02cc\u02ce\5\u008aF\2\u02cd"+
		"\u02cc\3\2\2\2\u02ce\u02d1\3\2\2\2\u02cf\u02cd\3\2\2\2\u02cf\u02d0\3\2"+
		"\2\2\u02d0\u02d2\3\2\2\2\u02d1\u02cf\3\2\2\2\u02d2\u02d3\7i\2\2\u02d3"+
		"\u02d5\7j\2\2\u02d4\u02d6\5\64\33\2\u02d5\u02d4\3\2\2\2\u02d5\u02d6\3"+
		"\2\2\2\u02d6\u02d9\3\2\2\2\u02d7\u02d9\5R*\2\u02d8\u02c8\3\2\2\2\u02d8"+
		"\u02d7\3\2\2\2\u02d9U\3\2\2\2\u02da\u02dc\5\u008aF\2\u02db\u02da\3\2\2"+
		"\2\u02dc\u02df\3\2\2\2\u02dd\u02db\3\2\2\2\u02dd\u02de\3\2\2\2\u02de\u02e0"+
		"\3\2\2\2\u02df\u02dd\3\2\2\2\u02e0\u02e3\5&\24\2\u02e1\u02e2\7j\2\2\u02e2"+
		"\u02e4\7C\2\2\u02e3\u02e1\3\2\2\2\u02e3\u02e4\3\2\2\2\u02e4\u02e5\3\2"+
		"\2\2\u02e5\u02e6\7-\2\2\u02e6W\3\2\2\2\u02e7\u02e8\7/\2\2\u02e8\u02e9"+
		"\5Z.\2\u02e9Y\3\2\2\2\u02ea\u02ef\5\\/\2\u02eb\u02ec\7B\2\2\u02ec\u02ee"+
		"\5\\/\2\u02ed\u02eb\3\2\2\2\u02ee\u02f1\3\2\2\2\u02ef\u02ed\3\2\2\2\u02ef"+
		"\u02f0\3\2\2\2\u02f0[\3\2\2\2\u02f1\u02ef\3\2\2\2\u02f2\u02f4\5\u008a"+
		"F\2\u02f3\u02f2\3\2\2\2\u02f4\u02f7\3\2\2\2\u02f5\u02f3\3\2\2\2\u02f5"+
		"\u02f6\3\2\2\2\u02f6\u02f8\3\2\2\2\u02f7\u02f5\3\2\2\2\u02f8\u0301\5."+
		"\30\2\u02f9\u02fb\5\u008aF\2\u02fa\u02f9\3\2\2\2\u02fb\u02fe\3\2\2\2\u02fc"+
		"\u02fa\3\2\2\2\u02fc\u02fd\3\2\2\2\u02fd\u02ff\3\2\2\2\u02fe\u02fc\3\2"+
		"\2\2\u02ff\u0301\7j\2\2\u0300\u02f5\3\2\2\2\u0300\u02fc\3\2\2\2\u0301"+
		"]\3\2\2\2\u0302\u0305\5\u00a0Q\2\u0303\u0305\7A\2\2\u0304\u0302\3\2\2"+
		"\2\u0304\u0303\3\2\2\2\u0305_\3\2\2\2\u0306\u0308\5b\62\2\u0307\u0306"+
		"\3\2\2\2\u0308\u030b\3\2\2\2\u0309\u0307\3\2\2\2\u0309\u030a\3\2\2\2\u030a"+
		"\u030c\3\2\2\2\u030b\u0309\3\2\2\2\u030c\u030e\5d\63\2\u030d\u030f\5X"+
		"-\2\u030e\u030d\3\2\2\2\u030e\u030f\3\2\2\2\u030f\u0310\3\2\2\2\u0310"+
		"\u0311\5f\64\2\u0311a\3\2\2\2\u0312\u0317\5\u008aF\2\u0313\u0317\7%\2"+
		"\2\u0314\u0317\7$\2\2\u0315\u0317\7#\2\2\u0316\u0312\3\2\2\2\u0316\u0313"+
		"\3\2\2\2\u0316\u0314\3\2\2\2\u0316\u0315\3\2\2\2\u0317c\3\2\2\2\u0318"+
		"\u031a\5\20\t\2\u0319\u0318\3\2\2\2\u0319\u031a\3\2\2\2\u031a\u031b\3"+
		"\2\2\2\u031b\u031c\7j\2\2\u031c\u031e\7;\2\2\u031d\u031f\5N(\2\u031e\u031d"+
		"\3\2\2\2\u031e\u031f\3\2\2\2\u031f\u0320\3\2\2\2\u0320\u0321\7<\2\2\u0321"+
		"e\3\2\2\2\u0322\u0324\7=\2\2\u0323\u0325\5h\65\2\u0324\u0323\3\2\2\2\u0324"+
		"\u0325\3\2\2\2\u0325\u0329\3\2\2\2\u0326\u0328\5\u00a2R\2\u0327\u0326"+
		"\3\2\2\2\u0328\u032b\3\2\2\2\u0329\u0327\3\2\2\2\u0329\u032a\3\2\2\2\u032a"+
		"\u032c\3\2\2\2\u032b\u0329\3\2\2\2\u032c\u032d\7>\2\2\u032dg\3\2\2\2\u032e"+
		"\u0330\5<\37\2\u032f\u032e\3\2\2\2\u032f\u0330\3\2\2\2\u0330\u0331\3\2"+
		"\2\2\u0331\u0332\7-\2\2\u0332\u0334\7;\2\2\u0333\u0335\5\u00eex\2\u0334"+
		"\u0333\3\2\2\2\u0334\u0335\3\2\2\2\u0335\u0336\3\2\2\2\u0336\u0337\7<"+
		"\2\2\u0337\u035d\7A\2\2\u0338\u033a\5<\37\2\u0339\u0338\3\2\2\2\u0339"+
		"\u033a\3\2\2\2\u033a\u033b\3\2\2\2\u033b\u033c\7*\2\2\u033c\u033e\7;\2"+
		"\2\u033d\u033f\5\u00eex\2\u033e\u033d\3\2\2\2\u033e\u033f\3\2\2\2\u033f"+
		"\u0340\3\2\2\2\u0340\u0341\7<\2\2\u0341\u035d\7A\2\2\u0342\u0343\5\f\7"+
		"\2\u0343\u0345\7C\2\2\u0344\u0346\5<\37\2\u0345\u0344\3\2\2\2\u0345\u0346"+
		"\3\2\2\2\u0346\u0347\3\2\2\2\u0347\u0348\7*\2\2\u0348\u034a\7;\2\2\u0349"+
		"\u034b\5\u00eex\2\u034a\u0349\3\2\2\2\u034a\u034b\3\2\2\2\u034b\u034c"+
		"\3\2\2\2\u034c\u034d\7<\2\2\u034d\u034e\7A\2\2\u034e\u035d\3\2\2\2\u034f"+
		"\u0350\5\u00c2b\2\u0350\u0352\7C\2\2\u0351\u0353\5<\37\2\u0352\u0351\3"+
		"\2\2\2\u0352\u0353\3\2\2\2\u0353\u0354\3\2\2\2\u0354\u0355\7*\2\2\u0355"+
		"\u0357\7;\2\2\u0356\u0358\5\u00eex\2\u0357\u0356\3\2\2\2\u0357\u0358\3"+
		"\2\2\2\u0358\u0359\3\2\2\2\u0359\u035a\7<\2\2\u035a\u035b\7A\2\2\u035b"+
		"\u035d\3\2\2\2\u035c\u032f\3\2\2\2\u035c\u0339\3\2\2\2\u035c\u0342\3\2"+
		"\2\2\u035c\u034f\3\2\2\2\u035di\3\2\2\2\u035e\u0360\5\n\6\2\u035f\u035e"+
		"\3\2\2\2\u0360\u0363\3\2\2\2\u0361\u035f\3\2\2\2\u0361\u0362\3\2\2\2\u0362"+
		"\u0364\3\2\2\2\u0363\u0361\3\2\2\2\u0364\u0365\7\22\2\2\u0365\u0368\7"+
		"j\2\2\u0366\u0367\7\32\2\2\u0367\u0369\5\22\n\2\u0368\u0366\3\2\2\2\u0368"+
		"\u0369\3\2\2\2\u0369\u036a\3\2\2\2\u036a\u036b\5l\67\2\u036bk\3\2\2\2"+
		"\u036c\u036e\7=\2\2\u036d\u036f\5n8\2\u036e\u036d\3\2\2\2\u036e\u036f"+
		"\3\2\2\2\u036f\u0371\3\2\2\2\u0370\u0372\7B\2\2\u0371\u0370\3\2\2\2\u0371"+
		"\u0372\3\2\2\2\u0372\u037a\3\2\2\2\u0373\u0377\7A\2\2\u0374\u0376\5\26"+
		"\f\2\u0375\u0374\3\2\2\2\u0376\u0379\3\2\2\2\u0377\u0375\3\2\2\2\u0377"+
		"\u0378\3\2\2\2\u0378\u037b\3\2\2\2\u0379\u0377\3\2\2\2\u037a\u0373\3\2"+
		"\2\2\u037a\u037b\3\2\2\2\u037b\u037c\3\2\2\2\u037c\u037d\7>\2\2\u037d"+
		"m\3\2\2\2\u037e\u0383\5p9\2\u037f\u0380\7B\2\2\u0380\u0382\5p9\2\u0381"+
		"\u037f\3\2\2\2\u0382\u0385\3\2\2\2\u0383\u0381\3\2\2\2\u0383\u0384\3\2"+
		"\2\2\u0384o\3\2\2\2\u0385\u0383\3\2\2\2\u0386\u0388\5\u008aF\2\u0387\u0386"+
		"\3\2\2\2\u0388\u038b\3\2\2\2\u0389\u0387\3\2\2\2\u0389\u038a\3\2\2\2\u038a"+
		"\u038c\3\2\2\2\u038b\u0389\3\2\2\2\u038c\u0392\7j\2\2\u038d\u038f\7;\2"+
		"\2\u038e\u0390\5\u00eex\2\u038f\u038e\3\2\2\2\u038f\u0390\3\2\2\2\u0390"+
		"\u0391\3\2\2\2\u0391\u0393\7<\2\2\u0392\u038d\3\2\2\2\u0392\u0393\3\2"+
		"\2\2\u0393\u0395\3\2\2\2\u0394\u0396\5\24\13\2\u0395\u0394\3\2\2\2\u0395"+
		"\u0396\3\2\2\2\u0396q\3\2\2\2\u0397\u0399\5\n\6\2\u0398\u0397\3\2\2\2"+
		"\u0399\u039c\3\2\2\2\u039a\u0398\3\2\2\2\u039a\u039b\3\2\2\2\u039b\u039d"+
		"\3\2\2\2\u039c\u039a\3\2\2\2\u039d\u039e\7\36\2\2\u039e\u03a0\7j\2\2\u039f"+
		"\u03a1\5\20\t\2\u03a0\u039f\3\2\2\2\u03a0\u03a1\3\2\2\2\u03a1\u03a4\3"+
		"\2\2\2\u03a2\u03a3\7\23\2\2\u03a3\u03a5\5\22\n\2\u03a4\u03a2\3\2\2\2\u03a4"+
		"\u03a5\3\2\2\2\u03a5\u03a6\3\2\2\2\u03a6\u03a7\5t;\2\u03a7s\3\2\2\2\u03a8"+
		"\u03ac\7=\2\2\u03a9\u03ab\5v<\2\u03aa\u03a9\3\2\2\2\u03ab\u03ae\3\2\2"+
		"\2\u03ac\u03aa\3\2\2\2\u03ac\u03ad\3\2\2\2\u03ad\u03af\3\2\2\2\u03ae\u03ac"+
		"\3\2\2\2\u03af\u03b0\7>\2\2\u03b0u\3\2\2\2\u03b1\u03b7\5x=\2\u03b2\u03b7"+
		"\5|?\2\u03b3\u03b7\5\16\b\2\u03b4\u03b7\5r:\2\u03b5\u03b7\7A\2\2\u03b6"+
		"\u03b1\3\2\2\2\u03b6\u03b2\3\2\2\2\u03b6\u03b3\3\2\2\2\u03b6\u03b4\3\2"+
		"\2\2\u03b6\u03b5\3\2\2\2\u03b7w\3\2\2\2\u03b8\u03ba\5z>\2\u03b9\u03b8"+
		"\3\2\2\2\u03ba\u03bd\3\2\2\2\u03bb\u03b9\3\2\2\2\u03bb\u03bc\3\2\2\2\u03bc"+
		"\u03be\3\2\2\2\u03bd\u03bb\3\2\2\2\u03be\u03bf\5&\24\2\u03bf\u03c0\5\36"+
		"\20\2\u03c0\u03c1\7A\2\2\u03c1y\3\2\2\2\u03c2\u03c7\5\u008aF\2\u03c3\u03c7"+
		"\7%\2\2\u03c4\u03c7\7(\2\2\u03c5\u03c7\7\24\2\2\u03c6\u03c2\3\2\2\2\u03c6"+
		"\u03c3\3\2\2\2\u03c6\u03c4\3\2\2\2\u03c6\u03c5\3\2\2\2\u03c7{\3\2\2\2"+
		"\u03c8\u03ca\5~@\2\u03c9\u03c8\3\2\2\2\u03ca\u03cd\3\2\2\2\u03cb\u03c9"+
		"\3\2\2\2\u03cb\u03cc\3\2\2\2\u03cc\u03ce\3\2\2\2\u03cd\u03cb\3\2\2\2\u03ce"+
		"\u03cf\5H%\2\u03cf\u03d0\5^\60\2\u03d0}\3\2\2\2\u03d1\u03d8\5\u008aF\2"+
		"\u03d2\u03d8\7%\2\2\u03d3\u03d8\7\3\2\2\u03d4\u03d8\7\16\2\2\u03d5\u03d8"+
		"\7(\2\2\u03d6\u03d8\7)\2\2\u03d7\u03d1\3\2\2\2\u03d7\u03d2\3\2\2\2\u03d7"+
		"\u03d3\3\2\2\2\u03d7\u03d4\3\2\2\2\u03d7\u03d5\3\2\2\2\u03d7\u03d6\3\2"+
		"\2\2\u03d8\177\3\2\2\2\u03d9\u03db\5\n\6\2\u03da\u03d9\3\2\2\2\u03db\u03de"+
		"\3\2\2\2\u03dc\u03da\3\2\2\2\u03dc\u03dd\3\2\2\2\u03dd\u03df\3\2\2\2\u03de"+
		"\u03dc\3\2\2\2\u03df\u03e0\7h\2\2\u03e0\u03e1\7\36\2\2\u03e1\u03e2\7j"+
		"\2\2\u03e2\u03e3\5\u0082B\2\u03e3\u0081\3\2\2\2\u03e4\u03e8\7=\2\2\u03e5"+
		"\u03e7\5\u0084C\2\u03e6\u03e5\3\2\2\2\u03e7\u03ea\3\2\2\2\u03e8\u03e6"+
		"\3\2\2\2\u03e8\u03e9\3\2\2\2\u03e9\u03eb\3\2\2\2\u03ea\u03e8\3\2\2\2\u03eb"+
		"\u03ec\7>\2\2\u03ec\u0083\3\2\2\2\u03ed\u03f3\5\u0086D\2\u03ee\u03f3\5"+
		"x=\2\u03ef\u03f3\5\16\b\2\u03f0\u03f3\5r:\2\u03f1\u03f3\7A\2\2\u03f2\u03ed"+
		"\3\2\2\2\u03f2\u03ee\3\2\2\2\u03f2\u03ef\3\2\2\2\u03f2\u03f0\3\2\2\2\u03f2"+
		"\u03f1\3\2\2\2\u03f3\u0085\3\2\2\2\u03f4\u03f6\t\4\2\2\u03f5\u03f4\3\2"+
		"\2\2\u03f6\u03f9\3\2\2\2\u03f7\u03f5\3\2\2\2\u03f7\u03f8\3\2\2\2\u03f8"+
		"\u03fd\3\2\2\2\u03f9\u03f7\3\2\2\2\u03fa\u03fc\5\u008aF\2\u03fb\u03fa"+
		"\3\2\2\2\u03fc\u03ff\3\2\2\2\u03fd\u03fb\3\2\2\2\u03fd\u03fe\3\2\2\2\u03fe"+
		"\u0400\3\2\2\2\u03ff\u03fd\3\2\2\2\u0400\u0401\5&\24\2\u0401\u0402\7j"+
		"\2\2\u0402\u0403\7;\2\2\u0403\u0405\7<\2\2\u0404\u0406\5\64\33\2\u0405"+
		"\u0404\3\2\2\2\u0405\u0406\3\2\2\2\u0406\u0408\3\2\2\2\u0407\u0409\5\u0088"+
		"E\2\u0408\u0407\3\2\2\2\u0408\u0409\3\2\2\2\u0409\u040a\3\2\2\2\u040a"+
		"\u040b\7A\2\2\u040b\u0087\3\2\2\2\u040c\u040d\7\16\2\2\u040d\u040e\5\u0092"+
		"J\2\u040e\u0089\3\2\2\2\u040f\u0413\5\u008cG\2\u0410\u0413\5\u0098M\2"+
		"\u0411\u0413\5\u009aN\2\u0412\u040f\3\2\2\2\u0412\u0410\3\2\2\2\u0412"+
		"\u0411\3\2\2\2\u0413\u008b\3\2\2\2\u0414\u0415\7h\2\2\u0415\u0416\5\f"+
		"\7\2\u0416\u0418\7;\2\2\u0417\u0419\5\u008eH\2\u0418\u0417\3\2\2\2\u0418"+
		"\u0419\3\2\2\2\u0419\u041a\3\2\2\2\u041a\u041b\7<\2\2\u041b\u008d\3\2"+
		"\2\2\u041c\u0421\5\u0090I\2\u041d\u041e\7B\2\2\u041e\u0420\5\u0090I\2"+
		"\u041f\u041d\3\2\2\2\u0420\u0423\3\2\2\2\u0421\u041f\3\2\2\2\u0421\u0422"+
		"\3\2\2\2\u0422\u008f\3\2\2\2\u0423\u0421\3\2\2\2\u0424\u0425\7j\2\2\u0425"+
		"\u0426\7D\2\2\u0426\u0427\5\u0092J\2\u0427\u0091\3\2\2\2\u0428\u042c\5"+
		"\u00f6|\2\u0429\u042c\5\u0094K\2\u042a\u042c\5\u008aF\2\u042b\u0428\3"+
		"\2\2\2\u042b\u0429\3\2\2\2\u042b\u042a\3\2\2\2\u042c\u0093\3\2\2\2\u042d"+
		"\u042f\7=\2\2\u042e\u0430\5\u0096L\2\u042f\u042e\3\2\2\2\u042f\u0430\3"+
		"\2\2\2\u0430\u0432\3\2\2\2\u0431\u0433\7B\2\2\u0432\u0431\3\2\2\2\u0432"+
		"\u0433\3\2\2\2\u0433\u0434\3\2\2\2\u0434\u0435\7>\2\2\u0435\u0095\3\2"+
		"\2\2\u0436\u043b\5\u0092J\2\u0437\u0438\7B\2\2\u0438\u043a\5\u0092J\2"+
		"\u0439\u0437\3\2\2\2\u043a\u043d\3\2\2\2\u043b\u0439\3\2\2\2\u043b\u043c"+
		"\3\2\2\2\u043c\u0097\3\2\2\2\u043d\u043b\3\2\2\2\u043e\u043f\7h\2\2\u043f"+
		"\u0440\5\f\7\2\u0440\u0099\3\2\2\2\u0441\u0442\7h\2\2\u0442\u0443\5\f"+
		"\7\2\u0443\u0444\7;\2\2\u0444\u0445\5\u0092J\2\u0445\u0446\7<\2\2\u0446"+
		"\u009b\3\2\2\2\u0447\u0449\7=\2\2\u0448\u044a\5\u009eP\2\u0449\u0448\3"+
		"\2\2\2\u0449\u044a\3\2\2\2\u044a\u044c\3\2\2\2\u044b\u044d\7B\2\2\u044c"+
		"\u044b\3\2\2\2\u044c\u044d\3\2\2\2\u044d\u044e\3\2\2\2\u044e\u044f\7>"+
		"\2\2\u044f\u009d\3\2\2\2\u0450\u0455\5\"\22\2\u0451\u0452\7B\2\2\u0452"+
		"\u0454\5\"\22\2\u0453\u0451\3\2\2\2\u0454\u0457\3\2\2\2\u0455\u0453\3"+
		"\2\2\2\u0455\u0456\3\2\2\2\u0456\u009f\3\2\2\2\u0457\u0455\3\2\2\2\u0458"+
		"\u045c\7=\2\2\u0459\u045b\5\u00a2R\2\u045a\u0459\3\2\2\2\u045b\u045e\3"+
		"\2\2\2\u045c\u045a\3\2\2\2\u045c\u045d\3\2\2\2\u045d\u045f\3\2\2\2\u045e"+
		"\u045c\3\2\2\2\u045f\u0460\7>\2\2\u0460\u00a1\3\2\2\2\u0461\u0462\5\u00a4"+
		"S\2\u0462\u0463\7A\2\2\u0463\u0469\3\2\2\2\u0464\u0469\5\16\b\2\u0465"+
		"\u0469\5j\66\2\u0466\u0469\5r:\2\u0467\u0469\5\u00a6T\2\u0468\u0461\3"+
		"\2\2\2\u0468\u0464\3\2\2\2\u0468\u0465\3\2\2\2\u0468\u0466\3\2\2\2\u0468"+
		"\u0467\3\2\2\2\u0469\u00a3\3\2\2\2\u046a\u046d\7\24\2\2\u046b\u046d\5"+
		"\u008aF\2\u046c\u046a\3\2\2\2\u046c\u046b\3\2\2\2\u046d\u0470\3\2\2\2"+
		"\u046e\u046c\3\2\2\2\u046e\u046f\3\2\2\2\u046f\u0471\3\2\2\2\u0470\u046e"+
		"\3\2\2\2\u0471\u0472\5&\24\2\u0472\u0473\5\36\20\2\u0473\u00a5\3\2\2\2"+
		"\u0474\u04c2\5\u00a0Q\2\u0475\u04c2\7A\2\2\u0476\u0477\5\u00a8U\2\u0477"+
		"\u0478\7A\2\2\u0478\u04c2\3\2\2\2\u0479\u047a\7\4\2\2\u047a\u047d\5\u00f6"+
		"|\2\u047b\u047c\7J\2\2\u047c\u047e\5\u00f6|\2\u047d\u047b\3\2\2\2\u047d"+
		"\u047e\3\2\2\2\u047e\u047f\3\2\2\2\u047f\u0480\7A\2\2\u0480\u04c2\3\2"+
		"\2\2\u0481\u0482\7+\2\2\u0482\u0483\7;\2\2\u0483\u0484\5\u00f6|\2\u0484"+
		"\u0485\7<\2\2\u0485\u0486\5\u00aaV\2\u0486\u04c2\3\2\2\2\u0487\u0488\7"+
		"\17\2\2\u0488\u0489\5\u00a6T\2\u0489\u048a\7\64\2\2\u048a\u048b\7;\2\2"+
		"\u048b\u048c\5\u00f6|\2\u048c\u048d\7<\2\2\u048d\u048e\7A\2\2\u048e\u04c2"+
		"\3\2\2\2\u048f\u0491\7\6\2\2\u0490\u0492\7j\2\2\u0491\u0490\3\2\2\2\u0491"+
		"\u0492\3\2\2\2\u0492\u0493\3\2\2\2\u0493\u04c2\7A\2\2\u0494\u0496\7\r"+
		"\2\2\u0495\u0497\7j\2\2\u0496\u0495\3\2\2\2\u0496\u0497\3\2\2\2\u0497"+
		"\u0498\3\2\2\2\u0498\u04c2\7A\2\2\u0499\u049b\7&\2\2\u049a\u049c\5\u00f6"+
		"|\2\u049b\u049a\3\2\2\2\u049b\u049c\3\2\2\2\u049c\u049d\3\2\2\2\u049d"+
		"\u04c2\7A\2\2\u049e\u049f\7,\2\2\u049f\u04a0\7;\2\2\u04a0\u04a1\5\u00f6"+
		"|\2\u04a1\u04a2\7<\2\2\u04a2\u04a3\5\u00a0Q\2\u04a3\u04c2\3\2\2\2\u04a4"+
		"\u04a5\7.\2\2\u04a5\u04a6\5\u00f6|\2\u04a6\u04a7\7A\2\2\u04a7\u04c2\3"+
		"\2\2\2\u04a8\u04c2\5\u00b0Y\2\u04a9\u04aa\7j\2\2\u04aa\u04ab\7J\2\2\u04ab"+
		"\u04c2\5\u00a6T\2\u04ac\u04ad\7\30\2\2\u04ad\u04ae\7;\2\2\u04ae\u04af"+
		"\5\u00f6|\2\u04af\u04b0\7<\2\2\u04b0\u04b3\5\u00a6T\2\u04b1\u04b2\7\21"+
		"\2\2\u04b2\u04b4\5\u00a6T\2\u04b3\u04b1\3\2\2\2\u04b3\u04b4\3\2\2\2\u04b4"+
		"\u04c2\3\2\2\2\u04b5\u04b6\7\64\2\2\u04b6\u04b7\7;\2\2\u04b7\u04b8\5\u00f6"+
		"|\2\u04b8\u04b9\7<\2\2\u04b9\u04ba\5\u00a6T\2\u04ba\u04c2\3\2\2\2\u04bb"+
		"\u04bc\7\27\2\2\u04bc\u04bd\7;\2\2\u04bd\u04be\5\u00b2Z\2\u04be\u04bf"+
		"\7<\2\2\u04bf\u04c0\5\u00a6T\2\u04c0\u04c2\3\2\2\2\u04c1\u0474\3\2\2\2"+
		"\u04c1\u0475\3\2\2\2\u04c1\u0476\3\2\2\2\u04c1\u0479\3\2\2\2\u04c1\u0481"+
		"\3\2\2\2\u04c1\u0487\3\2\2\2\u04c1\u048f\3\2\2\2\u04c1\u0494\3\2\2\2\u04c1"+
		"\u0499\3\2\2\2\u04c1\u049e\3\2\2\2\u04c1\u04a4\3\2\2\2\u04c1\u04a8\3\2"+
		"\2\2\u04c1\u04a9\3\2\2\2\u04c1\u04ac\3\2\2\2\u04c1\u04b5\3\2\2\2\u04c1"+
		"\u04bb\3\2\2\2\u04c2\u00a7\3\2\2\2\u04c3\u04d1\5\u00f8}\2\u04c4\u04c5"+
		"\7R\2\2\u04c5\u04d1\5\u0100\u0081\2\u04c6\u04c7\7S\2\2\u04c7\u04d1\5\u0100"+
		"\u0081\2\u04c8\u04c9\5\u0102\u0082\2\u04c9\u04ca\7R\2\2\u04ca\u04d1\3"+
		"\2\2\2\u04cb\u04cc\5\u0102\u0082\2\u04cc\u04cd\7S\2\2\u04cd\u04d1\3\2"+
		"\2\2\u04ce\u04d1\5\u00eav\2\u04cf\u04d1\5\u00dan\2\u04d0\u04c3\3\2\2\2"+
		"\u04d0\u04c4\3\2\2\2\u04d0\u04c6\3\2\2\2\u04d0\u04c8\3\2\2\2\u04d0\u04cb"+
		"\3\2\2\2\u04d0\u04ce\3\2\2\2\u04d0\u04cf\3\2\2\2\u04d1\u00a9\3\2\2\2\u04d2"+
		"\u04d6\7=\2\2\u04d3\u04d5\5\u00acW\2\u04d4\u04d3\3\2\2\2\u04d5\u04d8\3"+
		"\2\2\2\u04d6\u04d4\3\2\2\2\u04d6\u04d7\3\2\2\2\u04d7\u04dc\3\2\2\2\u04d8"+
		"\u04d6\3\2\2\2\u04d9\u04db\5\u00aeX\2\u04da\u04d9\3\2\2\2\u04db\u04de"+
		"\3\2\2\2\u04dc\u04da\3\2\2\2\u04dc\u04dd\3\2\2\2\u04dd\u04df\3\2\2\2\u04de"+
		"\u04dc\3\2\2\2\u04df\u04e0\7>\2\2\u04e0\u00ab\3\2\2\2\u04e1\u04e3\5\u00ae"+
		"X\2\u04e2\u04e1\3\2\2\2\u04e3\u04e4\3\2\2\2\u04e4\u04e2\3\2\2\2\u04e4"+
		"\u04e5\3\2\2\2\u04e5\u04e7\3\2\2\2\u04e6\u04e8\5\u00a2R\2\u04e7\u04e6"+
		"\3\2\2\2\u04e8\u04e9\3\2\2\2\u04e9\u04e7\3\2\2\2\u04e9\u04ea\3\2\2\2\u04ea"+
		"\u00ad\3\2\2\2\u04eb\u04ec\7\b\2\2\u04ec\u04ed\5\u00f6|\2\u04ed\u04ee"+
		"\7J\2\2\u04ee\u04f5\3\2\2\2\u04ef\u04f0\7\b\2\2\u04f0\u04f1\7j\2\2\u04f1"+
		"\u04f5\7J\2\2\u04f2\u04f3\7\16\2\2\u04f3\u04f5\7J\2\2\u04f4\u04eb\3\2"+
		"\2\2\u04f4\u04ef\3\2\2\2\u04f4\u04f2\3\2\2\2\u04f5\u00af\3\2\2\2\u04f6"+
		"\u04f7\7\61\2\2\u04f7\u04f8\5\u00a0Q\2\u04f8\u04f9\5\u00b8]\2\u04f9\u0512"+
		"\3\2\2\2\u04fa\u04fb\7\61\2\2\u04fb\u04fd\5\u00a0Q\2\u04fc\u04fe\5\u00b8"+
		"]\2\u04fd\u04fc\3\2\2\2\u04fd\u04fe\3\2\2\2\u04fe\u04ff\3\2\2\2\u04ff"+
		"\u0500\7\25\2\2\u0500\u0501\5\u00a0Q\2\u0501\u0512\3\2\2\2\u0502\u0503"+
		"\7\61\2\2\u0503\u0504\7;\2\2\u0504\u0506\5\u00be`\2\u0505\u0507\7A\2\2"+
		"\u0506\u0505\3\2\2\2\u0506\u0507\3\2\2\2\u0507\u0508\3\2\2\2\u0508\u0509"+
		"\7<\2\2\u0509\u050b\5\u00a0Q\2\u050a\u050c\5\u00b8]\2\u050b\u050a\3\2"+
		"\2\2\u050b\u050c\3\2\2\2\u050c\u050f\3\2\2\2\u050d\u050e\7\25\2\2\u050e"+
		"\u0510\5\u00a0Q\2\u050f\u050d\3\2\2\2\u050f\u0510\3\2\2\2\u0510\u0512"+
		"\3\2\2\2\u0511\u04f6\3\2\2\2\u0511\u04fa\3\2\2\2\u0511\u0502\3\2\2\2\u0512"+
		"\u00b1\3\2\2\2\u0513\u0515\5\u00b4[\2\u0514\u0513\3\2\2\2\u0514\u0515"+
		"\3\2\2\2\u0515\u0516\3\2\2\2\u0516\u0518\7A\2\2\u0517\u0519\5\u00f6|\2"+
		"\u0518\u0517\3\2\2\2\u0518\u0519\3\2\2\2\u0519\u051a\3\2\2\2\u051a\u051c"+
		"\7A\2\2\u051b\u051d\5\u00b6\\\2\u051c\u051b\3\2\2\2\u051c\u051d\3\2\2"+
		"\2\u051d\u0530\3\2\2\2\u051e\u0520\7\24\2\2\u051f\u051e\3\2\2\2\u051f"+
		"\u0520\3\2\2\2\u0520\u0524\3\2\2\2\u0521\u0523\5\u008aF\2\u0522\u0521"+
		"\3\2\2\2\u0523\u0526\3\2\2\2\u0524\u0522\3\2\2\2\u0524\u0525\3\2\2\2\u0525"+
		"\u0527\3\2\2\2\u0526\u0524\3\2\2\2\u0527\u0528\5&\24\2\u0528\u052a\7j"+
		"\2\2\u0529\u052b\5\64\33\2\u052a\u0529\3\2\2\2\u052a\u052b\3\2\2\2\u052b"+
		"\u052c\3\2\2\2\u052c\u052d\7J\2\2\u052d\u052e\5\u00f6|\2\u052e\u0530\3"+
		"\2\2\2\u052f\u0514\3\2\2\2\u052f\u051f\3\2\2\2\u0530\u00b3\3\2\2\2\u0531"+
		"\u0534\5\u00b6\\\2\u0532\u0534\5\u00a4S\2\u0533\u0531\3\2\2\2\u0533\u0532"+
		"\3\2\2\2\u0534\u00b5\3\2\2\2\u0535\u053a\5\u00a8U\2\u0536\u0537\7B\2\2"+
		"\u0537\u0539\5\u00a8U\2\u0538\u0536\3\2\2\2\u0539\u053c\3\2\2\2\u053a"+
		"\u0538\3\2\2\2\u053a\u053b\3\2\2\2\u053b\u00b7\3\2\2\2\u053c\u053a\3\2"+
		"\2\2\u053d\u053e\7\t\2\2\u053e\u053f\7;\2\2\u053f\u0540\5\u00ba^\2\u0540"+
		"\u0541\7<\2\2\u0541\u0542\5\u00a0Q\2\u0542\u0544\3\2\2\2\u0543\u053d\3"+
		"\2\2\2\u0544\u0545\3\2\2\2\u0545\u0543\3\2\2\2\u0545\u0546\3\2\2\2\u0546"+
		"\u00b9\3\2\2\2\u0547\u0549\7\24\2\2\u0548\u0547\3\2\2\2\u0548\u0549\3"+
		"\2\2\2\u0549\u054d\3\2\2\2\u054a\u054c\5\u008aF\2\u054b\u054a\3\2\2\2"+
		"\u054c\u054f\3\2\2\2\u054d\u054b\3\2\2\2\u054d\u054e\3\2\2\2\u054e\u0550"+
		"\3\2\2\2\u054f\u054d\3\2\2\2\u0550\u0551\5\u00bc_\2\u0551\u0553\7j\2\2"+
		"\u0552\u0554\5\64\33\2\u0553\u0552\3\2\2\2\u0553\u0554\3\2\2\2\u0554\u00bb"+
		"\3\2\2\2\u0555\u0560\5.\30\2\u0556\u055a\7Y\2\2\u0557\u0559\5\u008aF\2"+
		"\u0558\u0557\3\2\2\2\u0559\u055c\3\2\2\2\u055a\u0558\3\2\2\2\u055a\u055b"+
		"\3\2\2\2\u055b\u055d\3\2\2\2\u055c\u055a\3\2\2\2\u055d\u055f\5.\30\2\u055e"+
		"\u0556\3\2\2\2\u055f\u0562\3\2\2\2\u0560\u055e\3\2\2\2\u0560\u0561\3\2"+
		"\2\2\u0561\u00bd\3\2\2\2\u0562\u0560\3\2\2\2\u0563\u0568\5\u00c0a\2\u0564"+
		"\u0565\7A\2\2\u0565\u0567\5\u00c0a\2\u0566\u0564\3\2\2\2\u0567\u056a\3"+
		"\2\2\2\u0568\u0566\3\2\2\2\u0568\u0569\3\2\2\2\u0569\u00bf\3\2\2\2\u056a"+
		"\u0568\3\2\2\2\u056b\u056e\7\24\2\2\u056c\u056e\5\u008aF\2\u056d\u056b"+
		"\3\2\2\2\u056d\u056c\3\2\2\2\u056e\u0571\3\2\2\2\u056f\u056d\3\2\2\2\u056f"+
		"\u0570\3\2\2\2\u0570\u0572\3\2\2\2\u0571\u056f\3\2\2\2\u0572\u0573\5&"+
		"\24\2\u0573\u0575\7j\2\2\u0574\u0576\5\64\33\2\u0575\u0574\3\2\2\2\u0575"+
		"\u0576\3\2\2\2\u0576\u0577\3\2\2\2\u0577\u0578\7D\2\2\u0578\u0579\5\u00f6"+
		"|\2\u0579\u00c1\3\2\2\2\u057a\u057d\5\u00ccg\2\u057b\u057d\5\u00d0i\2"+
		"\u057c\u057a\3\2\2\2\u057c\u057b\3\2\2\2\u057d\u0581\3\2\2\2\u057e\u0580"+
		"\5\u00c8e\2\u057f\u057e\3\2\2\2\u0580\u0583\3\2\2\2\u0581\u057f\3\2\2"+
		"\2\u0581\u0582\3\2\2\2\u0582\u00c3\3\2\2\2\u0583\u0581\3\2\2\2\u0584\u05a2"+
		"\5$\23\2\u0585\u058a\5\f\7\2\u0586\u0587\7?\2\2\u0587\u0589\7@\2\2\u0588"+
		"\u0586\3\2\2\2\u0589\u058c\3\2\2\2\u058a\u0588\3\2\2\2\u058a\u058b\3\2"+
		"\2\2\u058b\u058d\3\2\2\2\u058c\u058a\3\2\2\2\u058d\u058e\7C\2\2\u058e"+
		"\u058f\7\13\2\2\u058f\u05a2\3\2\2\2\u0590\u0591\7\62\2\2\u0591\u0592\7"+
		"C\2\2\u0592\u05a2\7\13\2\2\u0593\u05a2\7-\2\2\u0594\u0595\5\f\7\2\u0595"+
		"\u0596\7C\2\2\u0596\u0597\7-\2\2\u0597\u05a2\3\2\2\2\u0598\u0599\7;\2"+
		"\2\u0599\u059a\5\u00f6|\2\u059a\u059b\7<\2\2\u059b\u05a2\3\2\2\2\u059c"+
		"\u05a2\5\u00dan\2\u059d\u05a2\5\u00e4s\2\u059e\u05a2\5\u00d2j\2\u059f"+
		"\u05a2\5\u00eav\2\u05a0\u05a2\5\u00f0y\2\u05a1\u0584\3\2\2\2\u05a1\u0585"+
		"\3\2\2\2\u05a1\u0590\3\2\2\2\u05a1\u0593\3\2\2\2\u05a1\u0594\3\2\2\2\u05a1"+
		"\u0598\3\2\2\2\u05a1\u059c\3\2\2\2\u05a1\u059d\3\2\2\2\u05a1\u059e\3\2"+
		"\2\2\u05a1\u059f\3\2\2\2\u05a1\u05a0\3\2\2\2\u05a2\u00c5\3\2\2\2\u05a3"+
		"\u05c0\5$\23\2\u05a4\u05a9\5\f\7\2\u05a5\u05a6\7?\2\2\u05a6\u05a8\7@\2"+
		"\2\u05a7\u05a5\3\2\2\2\u05a8\u05ab\3\2\2\2\u05a9\u05a7\3\2\2\2\u05a9\u05aa"+
		"\3\2\2\2\u05aa\u05ac\3\2\2\2\u05ab\u05a9\3\2\2\2\u05ac\u05ad\7C\2\2\u05ad"+
		"\u05ae\7\13\2\2\u05ae\u05c0\3\2\2\2\u05af\u05b0\7\62\2\2\u05b0\u05b1\7"+
		"C\2\2\u05b1\u05c0\7\13\2\2\u05b2\u05c0\7-\2\2\u05b3\u05b4\5\f\7\2\u05b4"+
		"\u05b5\7C\2\2\u05b5\u05b6\7-\2\2\u05b6\u05c0\3\2\2\2\u05b7\u05b8\7;\2"+
		"\2\u05b8\u05b9\5\u00f6|\2\u05b9\u05ba\7<\2\2\u05ba\u05c0\3\2\2\2\u05bb"+
		"\u05c0\5\u00dan\2\u05bc\u05c0\5\u00e4s\2\u05bd\u05c0\5\u00eav\2\u05be"+
		"\u05c0\5\u00f0y\2\u05bf\u05a3\3\2\2\2\u05bf\u05a4\3\2\2\2\u05bf\u05af"+
		"\3\2\2\2\u05bf\u05b2\3\2\2\2\u05bf\u05b3\3\2\2\2\u05bf\u05b7\3\2\2\2\u05bf"+
		"\u05bb\3\2\2\2\u05bf\u05bc\3\2\2\2\u05bf\u05bd\3\2\2\2\u05bf\u05be\3\2"+
		"\2\2\u05c0\u00c7\3\2\2\2\u05c1\u05d4\5\u00dco\2\u05c2\u05d4\5\u00e6t\2"+
		"\u05c3\u05d4\5\u00d4k\2\u05c4\u05c6\7C\2\2\u05c5\u05c7\5<\37\2\u05c6\u05c5"+
		"\3\2\2\2\u05c6\u05c7\3\2\2\2\u05c7\u05c8\3\2\2\2\u05c8\u05c9\7j\2\2\u05c9"+
		"\u05cb\7;\2\2\u05ca\u05cc\5\u00eex\2\u05cb\u05ca\3\2\2\2\u05cb\u05cc\3"+
		"\2\2\2\u05cc\u05cd\3\2\2\2\u05cd\u05d4\7<\2\2\u05ce\u05d0\7K\2\2\u05cf"+
		"\u05d1\5<\37\2\u05d0\u05cf\3\2\2\2\u05d0\u05d1\3\2\2\2\u05d1\u05d2\3\2"+
		"\2\2\u05d2\u05d4\7j\2\2\u05d3\u05c1\3\2\2\2\u05d3\u05c2\3\2\2\2\u05d3"+
		"\u05c3\3\2\2\2\u05d3\u05c4\3\2\2\2\u05d3\u05ce\3\2\2\2\u05d4\u00c9\3\2"+
		"\2\2\u05d5\u05e7\5\u00dco\2\u05d6\u05e7\5\u00e6t\2\u05d7\u05d9\7C\2\2"+
		"\u05d8\u05da\5<\37\2\u05d9\u05d8\3\2\2\2\u05d9\u05da\3\2\2\2\u05da\u05db"+
		"\3\2\2\2\u05db\u05dc\7j\2\2\u05dc\u05de\7;\2\2\u05dd\u05df\5\u00eex\2"+
		"\u05de\u05dd\3\2\2\2\u05de\u05df\3\2\2\2\u05df\u05e0\3\2\2\2\u05e0\u05e7"+
		"\7<\2\2\u05e1\u05e3\7K\2\2\u05e2\u05e4\5<\37\2\u05e3\u05e2\3\2\2\2\u05e3"+
		"\u05e4\3\2\2\2\u05e4\u05e5\3\2\2\2\u05e5\u05e7\7j\2\2\u05e6\u05d5\3\2"+
		"\2\2\u05e6\u05d6\3\2\2\2\u05e6\u05d7\3\2\2\2\u05e6\u05e1\3\2\2\2\u05e7"+
		"\u00cb\3\2\2\2\u05e8\u0611\5$\23\2\u05e9\u05ee\5\f\7\2\u05ea\u05eb\7?"+
		"\2\2\u05eb\u05ed\7@\2\2\u05ec\u05ea\3\2\2\2\u05ed\u05f0\3\2\2\2\u05ee"+
		"\u05ec\3\2\2\2\u05ee\u05ef\3\2\2\2\u05ef\u05f1\3\2\2\2\u05f0\u05ee\3\2"+
		"\2\2\u05f1\u05f2\7C\2\2\u05f2\u05f3\7\13\2\2\u05f3\u0611\3\2\2\2\u05f4"+
		"\u05f9\5(\25\2\u05f5\u05f6\7?\2\2\u05f6\u05f8\7@\2\2\u05f7\u05f5\3\2\2"+
		"\2\u05f8\u05fb\3\2\2\2\u05f9\u05f7\3\2\2\2\u05f9\u05fa\3\2\2\2\u05fa\u05fc"+
		"\3\2\2\2\u05fb\u05f9\3\2\2\2\u05fc\u05fd\7C\2\2\u05fd\u05fe\7\13\2\2\u05fe"+
		"\u0611\3\2\2\2\u05ff\u0600\7\62\2\2\u0600\u0601\7C\2\2\u0601\u0611\7\13"+
		"\2\2\u0602\u0611\7-\2\2\u0603\u0604\5\f\7\2\u0604\u0605\7C\2\2\u0605\u0606"+
		"\7-\2\2\u0606\u0611\3\2\2\2\u0607\u0608\7;\2\2\u0608\u0609\5\u00f6|\2"+
		"\u0609\u060a\7<\2\2\u060a\u0611\3\2\2\2\u060b\u0611\5\u00dep\2\u060c\u0611"+
		"\5\u00e8u\2\u060d\u0611\5\u00d6l\2\u060e\u0611\5\u00ecw\2\u060f\u0611"+
		"\5\u00f2z\2\u0610\u05e8\3\2\2\2\u0610\u05e9\3\2\2\2\u0610\u05f4\3\2\2"+
		"\2\u0610\u05ff\3\2\2\2\u0610\u0602\3\2\2\2\u0610\u0603\3\2\2\2\u0610\u0607"+
		"\3\2\2\2\u0610\u060b\3\2\2\2\u0610\u060c\3\2\2\2\u0610\u060d\3\2\2\2\u0610"+
		"\u060e\3\2\2\2\u0610\u060f\3\2\2\2\u0611\u00cd\3\2\2\2\u0612\u063a\5$"+
		"\23\2\u0613\u0618\5\f\7\2\u0614\u0615\7?\2\2\u0615\u0617\7@\2\2\u0616"+
		"\u0614\3\2\2\2\u0617\u061a\3\2\2\2\u0618\u0616\3\2\2\2\u0618\u0619\3\2"+
		"\2\2\u0619\u061b\3\2\2\2\u061a\u0618\3\2\2\2\u061b\u061c\7C\2\2\u061c"+
		"\u061d\7\13\2\2\u061d\u063a\3\2\2\2\u061e\u0623\5(\25\2\u061f\u0620\7"+
		"?\2\2\u0620\u0622\7@\2\2\u0621\u061f\3\2\2\2\u0622\u0625\3\2\2\2\u0623"+
		"\u0621\3\2\2\2\u0623\u0624\3\2\2\2\u0624\u0626\3\2\2\2\u0625\u0623\3\2"+
		"\2\2\u0626\u0627\7C\2\2\u0627\u0628\7\13\2\2\u0628\u063a\3\2\2\2\u0629"+
		"\u062a\7\62\2\2\u062a\u062b\7C\2\2\u062b\u063a\7\13\2\2\u062c\u063a\7"+
		"-\2\2\u062d\u062e\5\f\7\2\u062e\u062f\7C\2\2\u062f\u0630\7-\2\2\u0630"+
		"\u063a\3\2\2\2\u0631\u0632\7;\2\2\u0632\u0633\5\u00f6|\2\u0633\u0634\7"+
		"<\2\2\u0634\u063a\3\2\2\2\u0635\u063a\5\u00dep\2\u0636\u063a\5\u00e8u"+
		"\2\u0637\u063a\5\u00ecw\2\u0638\u063a\5\u00f2z\2\u0639\u0612\3\2\2\2\u0639"+
		"\u0613\3\2\2\2\u0639\u061e\3\2\2\2\u0639\u0629\3\2\2\2\u0639\u062c\3\2"+
		"\2\2\u0639\u062d\3\2\2\2\u0639\u0631\3\2\2\2\u0639\u0635\3\2\2\2\u0639"+
		"\u0636\3\2\2\2\u0639\u0637\3\2\2\2\u0639\u0638\3\2\2\2\u063a\u00cf\3\2"+
		"\2\2\u063b\u063f\7!\2\2\u063c\u063e\5\u008aF\2\u063d\u063c\3\2\2\2\u063e"+
		"\u0641\3\2\2\2\u063f\u063d\3\2\2\2\u063f\u0640\3\2\2\2\u0640\u065e\3\2"+
		"\2\2\u0641\u063f\3\2\2\2\u0642\u064e\5(\25\2\u0643\u0645\5\u00f4{\2\u0644"+
		"\u0643\3\2\2\2\u0645\u0646\3\2\2\2\u0646\u0644\3\2\2\2\u0646\u0647\3\2"+
		"\2\2\u0647\u0649\3\2\2\2\u0648\u064a\5\64\33\2\u0649\u0648\3\2\2\2\u0649"+
		"\u064a\3\2\2\2\u064a\u064f\3\2\2\2\u064b\u064c\5\64\33\2\u064c\u064d\5"+
		"\u009cO\2\u064d\u064f\3\2\2\2\u064e\u0644\3\2\2\2\u064e\u064b\3\2\2\2"+
		"\u064f\u065f\3\2\2\2\u0650\u065c\5\60\31\2\u0651\u0653\5\u00f4{\2\u0652"+
		"\u0651\3\2\2\2\u0653\u0654\3\2\2\2\u0654\u0652\3\2\2\2\u0654\u0655\3\2"+
		"\2\2\u0655\u0657\3\2\2\2\u0656\u0658\5\64\33\2\u0657\u0656\3\2\2\2\u0657"+
		"\u0658\3\2\2\2\u0658\u065d\3\2\2\2\u0659\u065a\5\64\33\2\u065a\u065b\5"+
		"\u009cO\2\u065b\u065d\3\2\2\2\u065c\u0652\3\2\2\2\u065c\u0659\3\2\2\2"+
		"\u065d\u065f\3\2\2\2\u065e\u0642\3\2\2\2\u065e\u0650\3\2\2\2\u065f\u00d1"+
		"\3\2\2\2\u0660\u0663\5\f\7\2\u0661\u0663\5\u00c6d\2\u0662\u0660\3\2\2"+
		"\2\u0662\u0661\3\2\2\2\u0663\u0665\3\2\2\2\u0664\u0666\5\u00d8m\2\u0665"+
		"\u0664\3\2\2\2\u0666\u0667\3\2\2\2\u0667\u0665\3\2\2\2\u0667\u0668\3\2"+
		"\2\2\u0668\u00d3\3\2\2\2\u0669\u066b\5\u00caf\2\u066a\u0669\3\2\2\2\u066a"+
		"\u066b\3\2\2\2\u066b\u066d\3\2\2\2\u066c\u066e\5\u00d8m\2\u066d\u066c"+
		"\3\2\2\2\u066e\u066f\3\2\2\2\u066f\u066d\3\2\2\2\u066f\u0670\3\2\2\2\u0670"+
		"\u00d5\3\2\2\2\u0671\u0674\5\f\7\2\u0672\u0674\5\u00ceh\2\u0673\u0671"+
		"\3\2\2\2\u0673\u0672\3\2\2\2\u0674\u0676\3\2\2\2\u0675\u0677\5\u00d8m"+
		"\2\u0676\u0675\3\2\2\2\u0677\u0678\3\2\2\2\u0678\u0676\3\2\2\2\u0678\u0679"+
		"\3\2\2\2\u0679\u00d7\3\2\2\2\u067a\u067b\7?\2\2\u067b\u067c\5\u00f6|\2"+
		"\u067c\u067d\7@\2\2\u067d\u067f\3\2\2\2\u067e\u067a\3\2\2\2\u067f\u0680"+
		"\3\2\2\2\u0680\u067e\3\2\2\2\u0680\u0681\3\2\2\2\u0681\u00d9\3\2\2\2\u0682"+
		"\u0683\5\f\7\2\u0683\u0684\7C\2\2\u0684\u0689\3\2\2\2\u0685\u0686\5\u00c2"+
		"b\2\u0686\u0687\7C\2\2\u0687\u0689\3\2\2\2\u0688\u0682\3\2\2\2\u0688\u0685"+
		"\3\2\2\2\u0688\u0689\3\2\2\2\u0689\u068a\3\2\2\2\u068a\u068b\5\u00e0q"+
		"\2\u068b\u00db\3\2\2\2\u068c\u068d\7C\2\2\u068d\u068e\5\u00e0q\2\u068e"+
		"\u00dd\3\2\2\2\u068f\u0690\5\f\7\2\u0690\u0691\7C\2\2\u0691\u0693\3\2"+
		"\2\2\u0692\u068f\3\2\2\2\u0692\u0693\3\2\2\2\u0693\u0694\3\2\2\2\u0694"+
		"\u0695\5\u00e0q\2\u0695\u00df\3\2\2\2\u0696\u0698\7!\2\2\u0697\u0699\5"+
		"<\37\2\u0698\u0697\3\2\2\2\u0698\u0699\3\2\2\2\u0699\u069d\3\2\2\2\u069a"+
		"\u069c\5\u008aF\2\u069b\u069a\3\2\2\2\u069c\u069f\3\2\2\2\u069d\u069b"+
		"\3\2\2\2\u069d\u069e\3\2\2\2\u069e\u06a0\3\2\2\2\u069f\u069d\3\2\2\2\u06a0"+
		"\u06ab\7j\2\2\u06a1\u06a5\7C\2\2\u06a2\u06a4\5\u008aF\2\u06a3\u06a2\3"+
		"\2\2\2\u06a4\u06a7\3\2\2\2\u06a5\u06a3\3\2\2\2\u06a5\u06a6\3\2\2\2\u06a6"+
		"\u06a8\3\2\2\2\u06a7\u06a5\3\2\2\2\u06a8\u06aa\7j\2\2\u06a9\u06a1\3\2"+
		"\2\2\u06aa\u06ad\3\2\2\2\u06ab\u06a9\3\2\2\2\u06ab\u06ac\3\2\2\2\u06ac"+
		"\u06af\3\2\2\2\u06ad\u06ab\3\2\2\2\u06ae\u06b0\5\u00e2r\2\u06af\u06ae"+
		"\3\2\2\2\u06af\u06b0\3\2\2\2\u06b0\u06b1\3\2\2\2\u06b1\u06b3\7;\2\2\u06b2"+
		"\u06b4\5\u00eex\2\u06b3\u06b2\3\2\2\2\u06b3\u06b4\3\2\2\2\u06b4\u06b5"+
		"\3\2\2\2\u06b5\u06b7\7<\2\2\u06b6\u06b8\5\24\13\2\u06b7\u06b6\3\2\2\2"+
		"\u06b7\u06b8\3\2\2\2\u06b8\u00e1\3\2\2\2\u06b9\u06bd\5<\37\2\u06ba\u06bb"+
		"\7F\2\2\u06bb\u06bd\7E\2\2\u06bc\u06b9\3\2\2\2\u06bc\u06ba\3\2\2\2\u06bd"+
		"\u00e3\3\2\2\2\u06be\u06bf\5\u00c2b\2\u06bf\u06c0\7C\2\2\u06c0\u06c1\7"+
		"j\2\2\u06c1\u06cc\3\2\2\2\u06c2\u06c3\7*\2\2\u06c3\u06c4\7C\2\2\u06c4"+
		"\u06cc\7j\2\2\u06c5\u06c6\5\f\7\2\u06c6\u06c7\7C\2\2\u06c7\u06c8\7*\2"+
		"\2\u06c8\u06c9\7C\2\2\u06c9\u06ca\7j\2\2\u06ca\u06cc\3\2\2\2\u06cb\u06be"+
		"\3\2\2\2\u06cb\u06c2\3\2\2\2\u06cb\u06c5\3\2\2\2\u06cc\u00e5\3\2\2\2\u06cd"+
		"\u06ce\7C\2\2\u06ce\u06cf\7j\2\2\u06cf\u00e7\3\2\2\2\u06d0\u06d1\7*\2"+
		"\2\u06d1\u06d2\7C\2\2\u06d2\u06da\7j\2\2\u06d3\u06d4\5\f\7\2\u06d4\u06d5"+
		"\7C\2\2\u06d5\u06d6\7*\2\2\u06d6\u06d7\7C\2\2\u06d7\u06d8\7j\2\2\u06d8"+
		"\u06da\3\2\2\2\u06d9\u06d0\3\2\2\2\u06d9\u06d3\3\2\2\2\u06da\u00e9\3\2"+
		"\2\2\u06db\u06dc\7j\2\2\u06dc\u06de\7;\2\2\u06dd\u06df\5\u00eex\2\u06de"+
		"\u06dd\3\2\2\2\u06de\u06df\3\2\2\2\u06df\u06e0\3\2\2\2\u06e0\u0713\7<"+
		"\2\2\u06e1\u06e2\5\f\7\2\u06e2\u06e4\7C\2\2\u06e3\u06e5\5<\37\2\u06e4"+
		"\u06e3\3\2\2\2\u06e4\u06e5\3\2\2\2\u06e5\u06e6\3\2\2\2\u06e6\u06e7\7j"+
		"\2\2\u06e7\u06e9\7;\2\2\u06e8\u06ea\5\u00eex\2\u06e9\u06e8\3\2\2\2\u06e9"+
		"\u06ea\3\2\2\2\u06ea\u06eb\3\2\2\2\u06eb\u06ec\7<\2\2\u06ec\u0713\3\2"+
		"\2\2\u06ed\u06ee\5\u00c2b\2\u06ee\u06f0\7C\2\2\u06ef\u06f1\5<\37\2\u06f0"+
		"\u06ef\3\2\2\2\u06f0\u06f1\3\2\2\2\u06f1\u06f2\3\2\2\2\u06f2\u06f3\7j"+
		"\2\2\u06f3\u06f5\7;\2\2\u06f4\u06f6\5\u00eex\2\u06f5\u06f4\3\2\2\2\u06f5"+
		"\u06f6\3\2\2\2\u06f6\u06f7\3\2\2\2\u06f7\u06f8\7<\2\2\u06f8\u0713\3\2"+
		"\2\2\u06f9\u06fa\7*\2\2\u06fa\u06fc\7C\2\2\u06fb\u06fd\5<\37\2\u06fc\u06fb"+
		"\3\2\2\2\u06fc\u06fd\3\2\2\2\u06fd\u06fe\3\2\2\2\u06fe\u06ff\7j\2\2\u06ff"+
		"\u0701\7;\2\2\u0700\u0702\5\u00eex\2\u0701\u0700\3\2\2\2\u0701\u0702\3"+
		"\2\2\2\u0702\u0703\3\2\2\2\u0703\u0713\7<\2\2\u0704\u0705\5\f\7\2\u0705"+
		"\u0706\7C\2\2\u0706\u0707\7*\2\2\u0707\u0709\7C\2\2\u0708\u070a\5<\37"+
		"\2\u0709\u0708\3\2\2\2\u0709\u070a\3\2\2\2\u070a\u070b\3\2\2\2\u070b\u070c"+
		"\7j\2\2\u070c\u070e\7;\2\2\u070d\u070f\5\u00eex\2\u070e\u070d\3\2\2\2"+
		"\u070e\u070f\3\2\2\2\u070f\u0710\3\2\2\2\u0710\u0711\7<\2\2\u0711\u0713"+
		"\3\2\2\2\u0712\u06db\3\2\2\2\u0712\u06e1\3\2\2\2\u0712\u06ed\3\2\2\2\u0712"+
		"\u06f9\3\2\2\2\u0712\u0704\3\2\2\2\u0713\u00eb\3\2\2\2\u0714\u0715\7j"+
		"\2\2\u0715\u0717\7;\2\2\u0716\u0718\5\u00eex\2\u0717\u0716\3\2\2\2\u0717"+
		"\u0718\3\2\2\2\u0718\u0719\3\2\2\2\u0719\u0740\7<\2\2\u071a\u071b\5\f"+
		"\7\2\u071b\u071d\7C\2\2\u071c\u071e\5<\37\2\u071d\u071c\3\2\2\2\u071d"+
		"\u071e\3\2\2\2\u071e\u071f\3\2\2\2\u071f\u0720\7j\2\2\u0720\u0722\7;\2"+
		"\2\u0721\u0723\5\u00eex\2\u0722\u0721\3\2\2\2\u0722\u0723\3\2\2\2\u0723"+
		"\u0724\3\2\2\2\u0724\u0725\7<\2\2\u0725\u0740\3\2\2\2\u0726\u0727\7*\2"+
		"\2\u0727\u0729\7C\2\2\u0728\u072a\5<\37\2\u0729\u0728\3\2\2\2\u0729\u072a"+
		"\3\2\2\2\u072a\u072b\3\2\2\2\u072b\u072c\7j\2\2\u072c\u072e\7;\2\2\u072d"+
		"\u072f\5\u00eex\2\u072e\u072d\3\2\2\2\u072e\u072f\3\2\2\2\u072f\u0730"+
		"\3\2\2\2\u0730\u0740\7<\2\2\u0731\u0732\5\f\7\2\u0732\u0733\7C\2\2\u0733"+
		"\u0734\7*\2\2\u0734\u0736\7C\2\2\u0735\u0737\5<\37\2\u0736\u0735\3\2\2"+
		"\2\u0736\u0737\3\2\2\2\u0737\u0738\3\2\2\2\u0738\u0739\7j\2\2\u0739\u073b"+
		"\7;\2\2\u073a\u073c\5\u00eex\2\u073b\u073a\3\2\2\2\u073b\u073c\3\2\2\2"+
		"\u073c\u073d\3\2\2\2\u073d\u073e\7<\2\2\u073e\u0740\3\2\2\2\u073f\u0714"+
		"\3\2\2\2\u073f\u071a\3\2\2\2\u073f\u0726\3\2\2\2\u073f\u0731\3\2\2\2\u0740"+
		"\u00ed\3\2\2\2\u0741\u0746\5\u00f6|\2\u0742\u0743\7B\2\2\u0743\u0745\5"+
		"\u00f6|\2\u0744\u0742\3\2\2\2\u0745\u0748\3\2\2\2\u0746\u0744\3\2\2\2"+
		"\u0746\u0747\3\2\2\2\u0747\u00ef\3\2\2\2\u0748\u0746\3\2\2\2\u0749\u074a"+
		"\5\u00c2b\2\u074a\u074c\7K\2\2\u074b\u074d\5<\37\2\u074c\u074b\3\2\2\2"+
		"\u074c\u074d\3\2\2\2\u074d\u074e\3\2\2\2\u074e\u074f\7j\2\2\u074f\u078b"+
		"\3\2\2\2\u0750\u0751\5\f\7\2\u0751\u0753\7K\2\2\u0752\u0754\5<\37\2\u0753"+
		"\u0752\3\2\2\2\u0753\u0754\3\2\2\2\u0754\u0755\3\2\2\2\u0755\u0756\7j"+
		"\2\2\u0756\u078b\3\2\2\2\u0757\u0759\5\u008aF\2\u0758\u0757\3\2\2\2\u0759"+
		"\u075c\3\2\2\2\u075a\u0758\3\2\2\2\u075a\u075b\3\2\2\2\u075b\u075d\3\2"+
		"\2\2\u075c\u075a\3\2\2\2\u075d\u075e\5,\27\2\u075e\u0760\7K\2\2\u075f"+
		"\u0761\5<\37\2\u0760\u075f\3\2\2\2\u0760\u0761\3\2\2\2\u0761\u0762\3\2"+
		"\2\2\u0762\u0763\7j\2\2\u0763\u078b\3\2\2\2\u0764\u0765\7*\2\2\u0765\u0767"+
		"\7K\2\2\u0766\u0768\5<\37\2\u0767\u0766\3\2\2\2\u0767\u0768\3\2\2\2\u0768"+
		"\u0769\3\2\2\2\u0769\u078b\7j\2\2\u076a\u076b\5\f\7\2\u076b\u076c\7C\2"+
		"\2\u076c\u076d\7*\2\2\u076d\u076f\7K\2\2\u076e\u0770\5<\37\2\u076f\u076e"+
		"\3\2\2\2\u076f\u0770\3\2\2\2\u0770\u0771\3\2\2\2\u0771\u0772\7j\2\2\u0772"+
		"\u078b\3\2\2\2\u0773\u0775\5\u008aF\2\u0774\u0773\3\2\2\2\u0775\u0778"+
		"\3\2\2\2\u0776\u0774\3\2\2\2\u0776\u0777\3\2\2\2\u0777\u0779\3\2\2\2\u0778"+
		"\u0776\3\2\2\2\u0779\u077a\5.\30\2\u077a\u077c\7K\2\2\u077b\u077d\5<\37"+
		"\2\u077c\u077b\3\2\2\2\u077c\u077d\3\2\2\2\u077d\u077e\3\2\2\2\u077e\u077f"+
		"\7!\2\2\u077f\u078b\3\2\2\2\u0780\u0782\5\u008aF\2\u0781\u0780\3\2\2\2"+
		"\u0782\u0785\3\2\2\2\u0783\u0781\3\2\2\2\u0783\u0784\3\2\2\2\u0784\u0786"+
		"\3\2\2\2\u0785\u0783\3\2\2\2\u0786\u0787\5\62\32\2\u0787\u0788\7K\2\2"+
		"\u0788\u0789\7!\2\2\u0789\u078b\3\2\2\2\u078a\u0749\3\2\2\2\u078a\u0750"+
		"\3\2\2\2\u078a\u075a\3\2\2\2\u078a\u0764\3\2\2\2\u078a\u076a\3\2\2\2\u078a"+
		"\u0776\3\2\2\2\u078a\u0783\3\2\2\2\u078b\u00f1\3\2\2\2\u078c\u078d\5\f"+
		"\7\2\u078d\u078f\7K\2\2\u078e\u0790\5<\37\2\u078f\u078e\3\2\2\2\u078f"+
		"\u0790\3\2\2\2\u0790\u0791\3\2\2\2\u0791\u0792\7j\2\2\u0792\u07c7\3\2"+
		"\2\2\u0793\u0795\5\u008aF\2\u0794\u0793\3\2\2\2\u0795\u0798\3\2\2\2\u0796"+
		"\u0794\3\2\2\2\u0796\u0797\3\2\2\2\u0797\u0799\3\2\2\2\u0798\u0796\3\2"+
		"\2\2\u0799\u079a\5,\27\2\u079a\u079c\7K\2\2\u079b\u079d\5<\37\2\u079c"+
		"\u079b\3\2\2\2\u079c\u079d\3\2\2\2\u079d\u079e\3\2\2\2\u079e\u079f\7j"+
		"\2\2\u079f\u07c7\3\2\2\2\u07a0\u07a1\7*\2\2\u07a1\u07a3\7K\2\2\u07a2\u07a4"+
		"\5<\37\2\u07a3\u07a2\3\2\2\2\u07a3\u07a4\3\2\2\2\u07a4\u07a5\3\2\2\2\u07a5"+
		"\u07c7\7j\2\2\u07a6\u07a7\5\f\7\2\u07a7\u07a8\7C\2\2\u07a8\u07a9\7*\2"+
		"\2\u07a9\u07ab\7K\2\2\u07aa\u07ac\5<\37\2\u07ab\u07aa\3\2\2\2\u07ab\u07ac"+
		"\3\2\2\2\u07ac\u07ad\3\2\2\2\u07ad\u07ae\7j\2\2\u07ae\u07c7\3\2\2\2\u07af"+
		"\u07b1\5\u008aF\2\u07b0\u07af\3\2\2\2\u07b1\u07b4\3\2\2\2\u07b2\u07b0"+
		"\3\2\2\2\u07b2\u07b3\3\2\2\2\u07b3\u07b5\3\2\2\2\u07b4\u07b2\3\2\2\2\u07b5"+
		"\u07b6\5.\30\2\u07b6\u07b8\7K\2\2\u07b7\u07b9\5<\37\2\u07b8\u07b7\3\2"+
		"\2\2\u07b8\u07b9\3\2\2\2\u07b9\u07ba\3\2\2\2\u07ba\u07bb\7!\2\2\u07bb"+
		"\u07c7\3\2\2\2\u07bc\u07be\5\u008aF\2\u07bd\u07bc\3\2\2\2\u07be\u07c1"+
		"\3\2\2\2\u07bf\u07bd\3\2\2\2\u07bf\u07c0\3\2\2\2\u07c0\u07c2\3\2\2\2\u07c1"+
		"\u07bf\3\2\2\2\u07c2\u07c3\5\62\32\2\u07c3\u07c4\7K\2\2\u07c4\u07c5\7"+
		"!\2\2\u07c5\u07c7\3\2\2\2\u07c6\u078c\3\2\2\2\u07c6\u0796\3\2\2\2\u07c6"+
		"\u07a0\3\2\2\2\u07c6\u07a6\3\2\2\2\u07c6\u07b2\3\2\2\2\u07c6\u07bf\3\2"+
		"\2\2\u07c7\u00f3\3\2\2\2\u07c8\u07ca\5\u008aF\2\u07c9\u07c8\3\2\2\2\u07ca"+
		"\u07cd\3\2\2\2\u07cb\u07c9\3\2\2\2\u07cb\u07cc\3\2\2\2\u07cc\u07ce\3\2"+
		"\2\2\u07cd\u07cb\3\2\2\2\u07ce\u07cf\7?\2\2\u07cf\u07d0\5\u00f6|\2\u07d0"+
		"\u07d1\7@\2\2\u07d1\u00f5\3\2\2\2\u07d2\u07d3\b|\1\2\u07d3\u07da\5$\23"+
		"\2\u07d4\u07da\5\u00f8}\2\u07d5\u07da\5\u00fa~\2\u07d6\u07da\5\f\7\2\u07d7"+
		"\u07da\5\u0100\u0081\2\u07d8\u07da\5\u00fc\177\2\u07d9\u07d2\3\2\2\2\u07d9"+
		"\u07d4\3\2\2\2\u07d9\u07d5\3\2\2\2\u07d9\u07d6\3\2\2\2\u07d9\u07d7\3\2"+
		"\2\2\u07d9\u07d8\3\2\2\2\u07da\u0800\3\2\2\2\u07db\u07dc\f\t\2\2\u07dc"+
		"\u07dd\t\5\2\2\u07dd\u07ff\5\u00f6|\n\u07de\u07df\f\b\2\2\u07df\u07e0"+
		"\t\6\2\2\u07e0\u07ff\5\u00f6|\t\u07e1\u07e9\f\7\2\2\u07e2\u07e3\7F\2\2"+
		"\u07e3\u07ea\7F\2\2\u07e4\u07e5\7E\2\2\u07e5\u07e6\7E\2\2\u07e6\u07ea"+
		"\7E\2\2\u07e7\u07e8\7E\2\2\u07e8\u07ea\7E\2\2\u07e9\u07e2\3\2\2\2\u07e9"+
		"\u07e4\3\2\2\2\u07e9\u07e7\3\2\2\2\u07ea\u07eb\3\2\2\2\u07eb\u07ff\5\u00f6"+
		"|\b\u07ec\u07ed\f\6\2\2\u07ed\u07ee\t\7\2\2\u07ee\u07ff\5\u00f6|\7\u07ef"+
		"\u07f0\f\5\2\2\u07f0\u07f1\7I\2\2\u07f1\u07f2\5\u00f6|\2\u07f2\u07f3\7"+
		"J\2\2\u07f3\u07f4\5\u00f6|\6\u07f4\u07ff\3\2\2\2\u07f5\u07f6\f\4\2\2\u07f6"+
		"\u07fa\7\34\2\2\u07f7\u07f9\5\u008aF\2\u07f8\u07f7\3\2\2\2\u07f9\u07fc"+
		"\3\2\2\2\u07fa\u07f8\3\2\2\2\u07fa\u07fb\3\2\2\2\u07fb\u07fd\3\2\2\2\u07fc"+
		"\u07fa\3\2\2\2\u07fd\u07ff\5,\27\2\u07fe\u07db\3\2\2\2\u07fe\u07de\3\2"+
		"\2\2\u07fe\u07e1\3\2\2\2\u07fe\u07ec\3\2\2\2\u07fe\u07ef\3\2\2\2\u07fe"+
		"\u07f5\3\2\2\2\u07ff\u0802\3\2\2\2\u0800\u07fe\3\2\2\2\u0800\u0801\3\2"+
		"\2\2\u0801\u00f7\3\2\2\2\u0802\u0800\3\2\2\2\u0803\u0807\5\f\7\2\u0804"+
		"\u0807\5\u00e4s\2\u0805\u0807\5\u00d2j\2\u0806\u0803\3\2\2\2\u0806\u0804"+
		"\3\2\2\2\u0806\u0805\3\2\2\2\u0807\u0808\3\2\2\2\u0808\u0809\t\b\2\2\u0809"+
		"\u080a\5\u00f6|\2\u080a\u00f9\3\2\2\2\u080b\u080f\7;\2\2\u080c\u080e\5"+
		"\u008aF\2\u080d\u080c\3\2\2\2\u080e\u0811\3\2\2\2\u080f\u080d\3\2\2\2"+
		"\u080f\u0810\3\2\2\2\u0810\u0812\3\2\2\2\u0811\u080f\3\2\2\2\u0812\u0813"+
		"\5(\25\2\u0813\u0814\7<\2\2\u0814\u0815\5\u0100\u0081\2\u0815\u082a\3"+
		"\2\2\2\u0816\u081a\7;\2\2\u0817\u0819\5\u008aF\2\u0818\u0817\3\2\2\2\u0819"+
		"\u081c\3\2\2\2\u081a\u0818\3\2\2\2\u081a\u081b\3\2\2\2\u081b\u081d\3\2"+
		"\2\2\u081c\u081a\3\2\2\2\u081d\u0821\5,\27\2\u081e\u0820\5:\36\2\u081f"+
		"\u081e\3\2\2\2\u0820\u0823\3\2\2\2\u0821\u081f\3\2\2\2\u0821\u0822\3\2"+
		"\2\2\u0822\u0824\3\2\2\2\u0823\u0821\3\2\2\2\u0824\u0827\7<\2\2\u0825"+
		"\u0828\5\u0102\u0082\2\u0826\u0828\5\u00fc\177\2\u0827\u0825\3\2\2\2\u0827"+
		"\u0826\3\2\2\2\u0828\u082a\3\2\2\2\u0829\u080b\3\2\2\2\u0829\u0816\3\2"+
		"\2\2\u082a\u00fb\3\2\2\2\u082b\u082c\5\u00fe\u0080\2\u082c\u082f\7\\\2"+
		"\2\u082d\u0830\5\u00f6|\2\u082e\u0830\5\u00a0Q\2\u082f\u082d\3\2\2\2\u082f"+
		"\u082e\3\2\2\2\u0830\u00fd\3\2\2\2\u0831\u0842\7j\2\2\u0832\u0834\7;\2"+
		"\2\u0833\u0835\5N(\2\u0834\u0833\3\2\2\2\u0834\u0835\3\2\2\2\u0835\u0836"+
		"\3\2\2\2\u0836\u0842\7<\2\2\u0837\u0838\7;\2\2\u0838\u083d\7j\2\2\u0839"+
		"\u083a\7B\2\2\u083a\u083c\7j\2\2\u083b\u0839\3\2\2\2\u083c\u083f\3\2\2"+
		"\2\u083d\u083b\3\2\2\2\u083d\u083e\3\2\2\2\u083e\u0840\3\2\2\2\u083f\u083d"+
		"\3\2\2\2\u0840\u0842\7<\2\2\u0841\u0831\3\2\2\2\u0841\u0832\3\2\2\2\u0841"+
		"\u0837\3\2\2\2\u0842\u00ff\3\2\2\2\u0843\u0844\t\t\2\2\u0844\u0849\5\u0100"+
		"\u0081\2\u0845\u0846\t\n\2\2\u0846\u0849\5\u0100\u0081\2\u0847\u0849\5"+
		"\u0102\u0082\2\u0848\u0843\3\2\2\2\u0848\u0845\3\2\2\2\u0848\u0847\3\2"+
		"\2\2\u0849\u0101\3\2\2\2\u084a\u084d\5\u00c2b\2\u084b\u084d\5\f\7\2\u084c"+
		"\u084a\3\2\2\2\u084c\u084b\3\2\2\2\u084d\u0851\3\2\2\2\u084e\u0850\t\13"+
		"\2\2\u084f\u084e\3\2\2\2\u0850\u0853\3\2\2\2\u0851\u084f\3\2\2\2\u0851"+
		"\u0852\3\2\2\2\u0852\u0103\3\2\2\2\u0853\u0851\3\2\2\2\u011b\u0105\u010a"+
		"\u0110\u0118\u0121\u0126\u012f\u0139\u0140\u0146\u014c\u0152\u0156\u015a"+
		"\u0164\u016c\u0174\u017a\u0181\u0188\u018c\u0194\u0199\u01a8\u01af\u01b4"+
		"\u01b8\u01bc\u01c2\u01c6\u01cd\u01d2\u01d7\u01dc\u01e0\u01e6\u01eb\u01ef"+
		"\u01fa\u01ff\u0207\u020e\u0214\u0219\u021f\u0226\u022d\u0230\u0236\u0244"+
		"\u024a\u024f\u0254\u025c\u0264\u0268\u026d\u027d\u0282\u0288\u028e\u0290"+
		"\u0294\u0299\u029d\u02a4\u02ab\u02b3\u02b6\u02ba\u02bc\u02c2\u02c6\u02c8"+
		"\u02cf\u02d5\u02d8\u02dd\u02e3\u02ef\u02f5\u02fc\u0300\u0304\u0309\u030e"+
		"\u0316\u0319\u031e\u0324\u0329\u032f\u0334\u0339\u033e\u0345\u034a\u0352"+
		"\u0357\u035c\u0361\u0368\u036e\u0371\u0377\u037a\u0383\u0389\u038f\u0392"+
		"\u0395\u039a\u03a0\u03a4\u03ac\u03b6\u03bb\u03c6\u03cb\u03d7\u03dc\u03e8"+
		"\u03f2\u03f7\u03fd\u0405\u0408\u0412\u0418\u0421\u042b\u042f\u0432\u043b"+
		"\u0449\u044c\u0455\u045c\u0468\u046c\u046e\u047d\u0491\u0496\u049b\u04b3"+
		"\u04c1\u04d0\u04d6\u04dc\u04e4\u04e9\u04f4\u04fd\u0506\u050b\u050f\u0511"+
		"\u0514\u0518\u051c\u051f\u0524\u052a\u052f\u0533\u053a\u0545\u0548\u054d"+
		"\u0553\u055a\u0560\u0568\u056d\u056f\u0575\u057c\u0581\u058a\u05a1\u05a9"+
		"\u05bf\u05c6\u05cb\u05d0\u05d3\u05d9\u05de\u05e3\u05e6\u05ee\u05f9\u0610"+
		"\u0618\u0623\u0639\u063f\u0646\u0649\u064e\u0654\u0657\u065c\u065e\u0662"+
		"\u0667\u066a\u066f\u0673\u0678\u0680\u0688\u0692\u0698\u069d\u06a5\u06ab"+
		"\u06af\u06b3\u06b7\u06bc\u06cb\u06d9\u06de\u06e4\u06e9\u06f0\u06f5\u06fc"+
		"\u0701\u0709\u070e\u0712\u0717\u071d\u0722\u0729\u072e\u0736\u073b\u073f"+
		"\u0746\u074c\u0753\u075a\u0760\u0767\u076f\u0776\u077c\u0783\u078a\u078f"+
		"\u0796\u079c\u07a3\u07ab\u07b2\u07b8\u07bf\u07c6\u07cb\u07d9\u07e9\u07fa"+
		"\u07fe\u0800\u0806\u080f\u081a\u0821\u0827\u0829\u082f\u0834\u083d\u0841"+
		"\u0848\u084c\u0851";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}