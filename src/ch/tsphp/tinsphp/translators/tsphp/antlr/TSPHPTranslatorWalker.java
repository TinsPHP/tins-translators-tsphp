// $ANTLR 3.5.2-including-157 D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g 2014-12-15 08:16:24

/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.antlr;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.translation.IPrecedenceHelper;



import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.stringtemplate.*;
import org.antlr.stringtemplate.language.*;
import java.util.HashMap;
@SuppressWarnings("all")
public class TSPHPTranslatorWalker extends TreeParser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "LogicXorWeak", "LogicOrWeak", 
		"LogicAndWeak", "Assign", "BitwiseAndAssign", "BitwiseOrAssign", "BitwiseXorAssign", 
		"PlusAssign", "MinusAssign", "DotAssign", "MultiplyAssign", "DivideAssign", 
		"ModuloAssign", "ShiftLeftAssign", "ShiftRightAssign", "CAST_ASSIGN", 
		"QuestionMark", "LogicOr", "LogicAnd", "BitwiseOr", "BitwiseXor", "BitwiseAnd", 
		"Equal", "Identical", "NotEqual", "NotIdentical", "GreaterEqualThan", 
		"GreaterThan", "LessEqualThan", "LessThan", "ShiftLeft", "ShiftRight", 
		"Minus", "Plus", "Dot", "Multiply", "Divide", "Modulo", "Instanceof", 
		"CAST", "PRE_DECREMENT", "PRE_INCREMENT", "At", "BitwiseNot", "LogicNot", 
		"UNARY_MINUS", "UNARY_PLUS", "New", "Clone", "POST_DECREMENT", "POST_INCREMENT", 
		"ARRAY_ACCESS", "FIELD_ACCESS", "CLASS_STATIC_ACCESS", "FUNCTION_CALL", 
		"METHOD_CALL", "METHOD_CALL_POSTFIX", "METHOD_CALL_STATIC", "Exit", "Bool", 
		"Int", "Float", "String", "TypeArray", "Null", "This", "CONSTANT", "ACTUAL_PARAMETERS", 
		"Abstract", "Arrow", "As", "BINARY", "BLOCK", "BLOCK_CONDITIONAL", "Backslash", 
		"Break", "CLASS_BODY", "CLASS_MODIFIER", "CLASS_STATIC_ACCESS_VARIABLE_ID", 
		"CONSTANT_DECLARATION", "CONSTANT_DECLARATION_LIST", "Case", "Cast", "Catch", 
		"Class", "Colon", "Comma", "Comment", "Const", "Construct", "Continue", 
		"DECIMAL", "DEFAULT_NAMESPACE", "Default", "Destruct", "Do", "Dollar", 
		"DoubleColon", "EXPONENT", "EXPRESSION", "EXPRESSION_LIST", "Echo", "Else", 
		"Extends", "FIELD", "FIELD_MODIFIER", "FUNCTION_MODIFIER", "Final", "For", 
		"Foreach", "Function", "HEXADECIMAL", "INTERFACE_BODY", "Identifier", 
		"If", "Implements", "Interface", "LeftCurlyBrace", "LeftParenthesis", 
		"LeftSquareBrace", "METHOD_DECLARATION", "METHOD_MODIFIER", "MinusMinus", 
		"NAMESPACE_BODY", "Namespace", "NotEqualAlternative", "OCTAL", "ObjectOperator", 
		"PARAMETER_DECLARATION", "PARAMETER_LIST", "PARAMETER_TYPE", "Parent", 
		"ParentColonColon", "PlusPlus", "Private", "ProtectThis", "Protected", 
		"Public", "Return", "RightCurlyBrace", "RightParenthesis", "RightSquareBrace", 
		"STRING_DOUBLE_QUOTED", "STRING_SINGLE_QUOTED", "SWITCH_CASES", "Self", 
		"SelfColonColon", "Semicolon", "Static", "Switch", "TYPE", "TYPE_MODIFIER", 
		"TYPE_NAME", "Throw", "Try", "TypeAliasBool", "TypeAliasFloat", "TypeAliasFloat2", 
		"TypeAliasInt", "TypeBool", "TypeFloat", "TypeInt", "TypeMixed", "TypeObject", 
		"TypeResource", "TypeString", "USE_DECLARATION", "Use", "VARIABLE_DECLARATION", 
		"VARIABLE_DECLARATION_LIST", "VariableId", "Void", "While", "Whitespace", 
		"PhpEnd", "PhpStart"
	};
	public static final int EOF=-1;
	public static final int LogicXorWeak=4;
	public static final int LogicOrWeak=5;
	public static final int LogicAndWeak=6;
	public static final int Assign=7;
	public static final int BitwiseAndAssign=8;
	public static final int BitwiseOrAssign=9;
	public static final int BitwiseXorAssign=10;
	public static final int PlusAssign=11;
	public static final int MinusAssign=12;
	public static final int DotAssign=13;
	public static final int MultiplyAssign=14;
	public static final int DivideAssign=15;
	public static final int ModuloAssign=16;
	public static final int ShiftLeftAssign=17;
	public static final int ShiftRightAssign=18;
	public static final int CAST_ASSIGN=19;
	public static final int QuestionMark=20;
	public static final int LogicOr=21;
	public static final int LogicAnd=22;
	public static final int BitwiseOr=23;
	public static final int BitwiseXor=24;
	public static final int BitwiseAnd=25;
	public static final int Equal=26;
	public static final int Identical=27;
	public static final int NotEqual=28;
	public static final int NotIdentical=29;
	public static final int GreaterEqualThan=30;
	public static final int GreaterThan=31;
	public static final int LessEqualThan=32;
	public static final int LessThan=33;
	public static final int ShiftLeft=34;
	public static final int ShiftRight=35;
	public static final int Minus=36;
	public static final int Plus=37;
	public static final int Dot=38;
	public static final int Multiply=39;
	public static final int Divide=40;
	public static final int Modulo=41;
	public static final int Instanceof=42;
	public static final int CAST=43;
	public static final int PRE_DECREMENT=44;
	public static final int PRE_INCREMENT=45;
	public static final int At=46;
	public static final int BitwiseNot=47;
	public static final int LogicNot=48;
	public static final int UNARY_MINUS=49;
	public static final int UNARY_PLUS=50;
	public static final int New=51;
	public static final int Clone=52;
	public static final int POST_DECREMENT=53;
	public static final int POST_INCREMENT=54;
	public static final int ARRAY_ACCESS=55;
	public static final int FIELD_ACCESS=56;
	public static final int CLASS_STATIC_ACCESS=57;
	public static final int FUNCTION_CALL=58;
	public static final int METHOD_CALL=59;
	public static final int METHOD_CALL_POSTFIX=60;
	public static final int METHOD_CALL_STATIC=61;
	public static final int Exit=62;
	public static final int Bool=63;
	public static final int Int=64;
	public static final int Float=65;
	public static final int String=66;
	public static final int TypeArray=67;
	public static final int Null=68;
	public static final int This=69;
	public static final int CONSTANT=70;
	public static final int ACTUAL_PARAMETERS=71;
	public static final int Abstract=72;
	public static final int Arrow=73;
	public static final int As=74;
	public static final int BINARY=75;
	public static final int BLOCK=76;
	public static final int BLOCK_CONDITIONAL=77;
	public static final int Backslash=78;
	public static final int Break=79;
	public static final int CLASS_BODY=80;
	public static final int CLASS_MODIFIER=81;
	public static final int CLASS_STATIC_ACCESS_VARIABLE_ID=82;
	public static final int CONSTANT_DECLARATION=83;
	public static final int CONSTANT_DECLARATION_LIST=84;
	public static final int Case=85;
	public static final int Cast=86;
	public static final int Catch=87;
	public static final int Class=88;
	public static final int Colon=89;
	public static final int Comma=90;
	public static final int Comment=91;
	public static final int Const=92;
	public static final int Construct=93;
	public static final int Continue=94;
	public static final int DECIMAL=95;
	public static final int DEFAULT_NAMESPACE=96;
	public static final int Default=97;
	public static final int Destruct=98;
	public static final int Do=99;
	public static final int Dollar=100;
	public static final int DoubleColon=101;
	public static final int EXPONENT=102;
	public static final int EXPRESSION=103;
	public static final int EXPRESSION_LIST=104;
	public static final int Echo=105;
	public static final int Else=106;
	public static final int Extends=107;
	public static final int FIELD=108;
	public static final int FIELD_MODIFIER=109;
	public static final int FUNCTION_MODIFIER=110;
	public static final int Final=111;
	public static final int For=112;
	public static final int Foreach=113;
	public static final int Function=114;
	public static final int HEXADECIMAL=115;
	public static final int INTERFACE_BODY=116;
	public static final int Identifier=117;
	public static final int If=118;
	public static final int Implements=119;
	public static final int Interface=120;
	public static final int LeftCurlyBrace=121;
	public static final int LeftParenthesis=122;
	public static final int LeftSquareBrace=123;
	public static final int METHOD_DECLARATION=124;
	public static final int METHOD_MODIFIER=125;
	public static final int MinusMinus=126;
	public static final int NAMESPACE_BODY=127;
	public static final int Namespace=128;
	public static final int NotEqualAlternative=129;
	public static final int OCTAL=130;
	public static final int ObjectOperator=131;
	public static final int PARAMETER_DECLARATION=132;
	public static final int PARAMETER_LIST=133;
	public static final int PARAMETER_TYPE=134;
	public static final int Parent=135;
	public static final int ParentColonColon=136;
	public static final int PlusPlus=137;
	public static final int Private=138;
	public static final int ProtectThis=139;
	public static final int Protected=140;
	public static final int Public=141;
	public static final int Return=142;
	public static final int RightCurlyBrace=143;
	public static final int RightParenthesis=144;
	public static final int RightSquareBrace=145;
	public static final int STRING_DOUBLE_QUOTED=146;
	public static final int STRING_SINGLE_QUOTED=147;
	public static final int SWITCH_CASES=148;
	public static final int Self=149;
	public static final int SelfColonColon=150;
	public static final int Semicolon=151;
	public static final int Static=152;
	public static final int Switch=153;
	public static final int TYPE=154;
	public static final int TYPE_MODIFIER=155;
	public static final int TYPE_NAME=156;
	public static final int Throw=157;
	public static final int Try=158;
	public static final int TypeAliasBool=159;
	public static final int TypeAliasFloat=160;
	public static final int TypeAliasFloat2=161;
	public static final int TypeAliasInt=162;
	public static final int TypeBool=163;
	public static final int TypeFloat=164;
	public static final int TypeInt=165;
	public static final int TypeMixed=166;
	public static final int TypeObject=167;
	public static final int TypeResource=168;
	public static final int TypeString=169;
	public static final int USE_DECLARATION=170;
	public static final int Use=171;
	public static final int VARIABLE_DECLARATION=172;
	public static final int VARIABLE_DECLARATION_LIST=173;
	public static final int VariableId=174;
	public static final int Void=175;
	public static final int While=176;
	public static final int Whitespace=177;
	public static final int PhpEnd=178;
	public static final int PhpStart=179;

	// delegates
	public TreeParser[] getDelegates() {
		return new TreeParser[] {};
	}

	// delegators


	public TSPHPTranslatorWalker(TreeNodeStream input) {
		this(input, new RecognizerSharedState());
	}
	public TSPHPTranslatorWalker(TreeNodeStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected StringTemplateGroup templateLib =
	  new StringTemplateGroup("TSPHPTranslatorWalkerTemplates", AngleBracketTemplateLexer.class);

	public void setTemplateLib(StringTemplateGroup templateLib) {
	  this.templateLib = templateLib;
	}
	public StringTemplateGroup getTemplateLib() {
	  return templateLib;
	}
	/** allows convenient multi-value initialization:
	 *  "new STAttrMap().put(...).put(...)"
	 */
	@SuppressWarnings("serial")
	public static class STAttrMap extends HashMap<String, Object> {
		public STAttrMap put(String attrName, Object value) {
			super.put(attrName, value);
			return this;
		}
	}
	@Override public String[] getTokenNames() { return TSPHPTranslatorWalker.tokenNames; }
	@Override public String getGrammarFileName() { return "D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g"; }


	private IPrecedenceHelper precedenceHelper;

	public TSPHPTranslatorWalker(TreeNodeStream input, IPrecedenceHelper thePrecedenceHelper) {
	    this(input);
	    precedenceHelper = thePrecedenceHelper;
	}

	private String getMethodName(String name) {
	    return name.substring(0, name.length() - 2);
	}



	public static class compilationUnit_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "compilationUnit"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:51:1: compilationUnit : ( (n+= namespace )+ ) -> file(namespaces=$n);
	public final TSPHPTranslatorWalker.compilationUnit_return compilationUnit() throws RecognitionException {
		TSPHPTranslatorWalker.compilationUnit_return retval = new TSPHPTranslatorWalker.compilationUnit_return();
		retval.start = input.LT(1);

		List<Object> list_n=null;
		RuleReturnScope n = null;
		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:52:5: ( ( (n+= namespace )+ ) -> file(namespaces=$n))
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:52:9: ( (n+= namespace )+ )
			{
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:52:9: ( (n+= namespace )+ )
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:52:10: (n+= namespace )+
			{
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:52:11: (n+= namespace )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==Namespace) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:52:11: n+= namespace
					{
					pushFollow(FOLLOW_namespace_in_compilationUnit80);
					n=namespace();
					state._fsp--;

					if (list_n==null) list_n=new ArrayList<Object>();
					list_n.add(n.getTemplate());
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			}

			// TEMPLATE REWRITE
			// 52:25: -> file(namespaces=$n)
			{
				retval.st = templateLib.getInstanceOf("file",new STAttrMap().put("namespaces", list_n));
			}



			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "compilationUnit"


	public static class namespace_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "namespace"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:55:1: namespace : ^( 'namespace' (name= TYPE_NAME | DEFAULT_NAMESPACE ) namespaceBody ) -> namespace(name=namespaceNamebody=$namespaceBody.st);
	public final TSPHPTranslatorWalker.namespace_return namespace() throws RecognitionException {
		TSPHPTranslatorWalker.namespace_return retval = new TSPHPTranslatorWalker.namespace_return();
		retval.start = input.LT(1);

		ITSPHPAst name=null;
		TreeRuleReturnScope namespaceBody1 =null;

		String namespaceName = null;
		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:57:5: ( ^( 'namespace' (name= TYPE_NAME | DEFAULT_NAMESPACE ) namespaceBody ) -> namespace(name=namespaceNamebody=$namespaceBody.st))
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:57:9: ^( 'namespace' (name= TYPE_NAME | DEFAULT_NAMESPACE ) namespaceBody )
			{
			match(input,Namespace,FOLLOW_Namespace_in_namespace119); 
			match(input, Token.DOWN, null); 
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:57:23: (name= TYPE_NAME | DEFAULT_NAMESPACE )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==TYPE_NAME) ) {
				alt2=1;
			}
			else if ( (LA2_0==DEFAULT_NAMESPACE) ) {
				alt2=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:57:24: name= TYPE_NAME
					{
					name=(ITSPHPAst)match(input,TYPE_NAME,FOLLOW_TYPE_NAME_in_namespace124); 
					}
					break;
				case 2 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:57:39: DEFAULT_NAMESPACE
					{
					match(input,DEFAULT_NAMESPACE,FOLLOW_DEFAULT_NAMESPACE_in_namespace126); 
					}
					break;

			}

			pushFollow(FOLLOW_namespaceBody_in_namespace129);
			namespaceBody1=namespaceBody();
			state._fsp--;

			match(input, Token.UP, null); 


			            if(name!=null){
			                namespaceName=name.getText().substring(1,name.getText().length()-1);
			            }
			        
			// TEMPLATE REWRITE
			// 64:9: -> namespace(name=namespaceNamebody=$namespaceBody.st)
			{
				retval.st = templateLib.getInstanceOf("namespace",new STAttrMap().put("name", namespaceName).put("body", (namespaceBody1!=null?((StringTemplate)namespaceBody1.getTemplate()):null)));
			}



			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "namespace"


	public static class namespaceBody_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "namespaceBody"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:67:1: namespaceBody : ( ^( NAMESPACE_BODY (statements+= statement )* ) -> body(statements=$statements)| NAMESPACE_BODY -> body(statements=null));
	public final TSPHPTranslatorWalker.namespaceBody_return namespaceBody() throws RecognitionException {
		TSPHPTranslatorWalker.namespaceBody_return retval = new TSPHPTranslatorWalker.namespaceBody_return();
		retval.start = input.LT(1);

		List<Object> list_statements=null;
		RuleReturnScope statements = null;
		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:68:5: ( ^( NAMESPACE_BODY (statements+= statement )* ) -> body(statements=$statements)| NAMESPACE_BODY -> body(statements=null))
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==NAMESPACE_BODY) ) {
				int LA4_1 = input.LA(2);
				if ( (LA4_1==DOWN) ) {
					alt4=1;
				}
				else if ( (LA4_1==UP) ) {
					alt4=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}

			switch (alt4) {
				case 1 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:68:9: ^( NAMESPACE_BODY (statements+= statement )* )
					{
					match(input,NAMESPACE_BODY,FOLLOW_NAMESPACE_BODY_in_namespaceBody186); 
					if ( input.LA(1)==Token.DOWN ) {
						match(input, Token.DOWN, null); 
						// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:68:36: (statements+= statement )*
						loop3:
						while (true) {
							int alt3=2;
							int LA3_0 = input.LA(1);
							if ( (LA3_0==CONSTANT_DECLARATION_LIST||LA3_0==Use) ) {
								alt3=1;
							}

							switch (alt3) {
							case 1 :
								// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:68:36: statements+= statement
								{
								pushFollow(FOLLOW_statement_in_namespaceBody190);
								statements=statement();
								state._fsp--;

								if (list_statements==null) list_statements=new ArrayList<Object>();
								list_statements.add(statements.getTemplate());
								}
								break;

							default :
								break loop3;
							}
						}

						match(input, Token.UP, null); 
					}

					// TEMPLATE REWRITE
					// 68:50: -> body(statements=$statements)
					{
						retval.st = templateLib.getInstanceOf("body",new STAttrMap().put("statements", list_statements));
					}



					}
					break;
				case 2 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:69:9: NAMESPACE_BODY
					{
					match(input,NAMESPACE_BODY,FOLLOW_NAMESPACE_BODY_in_namespaceBody211); 
					// TEMPLATE REWRITE
					// 69:24: -> body(statements=null)
					{
						retval.st = templateLib.getInstanceOf("body",new STAttrMap().put("statements", null));
					}



					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "namespaceBody"


	public static class statement_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "statement"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:72:1: statement : ( useDeclarationList -> {$useDeclarationList.st}| definition -> {$definition.st});
	public final TSPHPTranslatorWalker.statement_return statement() throws RecognitionException {
		TSPHPTranslatorWalker.statement_return retval = new TSPHPTranslatorWalker.statement_return();
		retval.start = input.LT(1);

		TreeRuleReturnScope useDeclarationList2 =null;
		TreeRuleReturnScope definition3 =null;

		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:73:5: ( useDeclarationList -> {$useDeclarationList.st}| definition -> {$definition.st})
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==Use) ) {
				alt5=1;
			}
			else if ( (LA5_0==CONSTANT_DECLARATION_LIST) ) {
				alt5=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}

			switch (alt5) {
				case 1 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:73:9: useDeclarationList
					{
					pushFollow(FOLLOW_useDeclarationList_in_statement239);
					useDeclarationList2=useDeclarationList();
					state._fsp--;

					// TEMPLATE REWRITE
					// 73:28: -> {$useDeclarationList.st}
					{
						retval.st = (useDeclarationList2!=null?((StringTemplate)useDeclarationList2.getTemplate()):null);
					}



					}
					break;
				case 2 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:74:9: definition
					{
					pushFollow(FOLLOW_definition_in_statement253);
					definition3=definition();
					state._fsp--;

					// TEMPLATE REWRITE
					// 74:20: -> {$definition.st}
					{
						retval.st = (definition3!=null?((StringTemplate)definition3.getTemplate()):null);
					}



					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "statement"


	public static class useDeclarationList_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "useDeclarationList"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:79:1: useDeclarationList : ^( 'use' (declarations+= useDeclaration )+ ) -> useDeclarationList(useDeclarations=$declarations);
	public final TSPHPTranslatorWalker.useDeclarationList_return useDeclarationList() throws RecognitionException {
		TSPHPTranslatorWalker.useDeclarationList_return retval = new TSPHPTranslatorWalker.useDeclarationList_return();
		retval.start = input.LT(1);

		List<Object> list_declarations=null;
		RuleReturnScope declarations = null;
		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:80:5: ( ^( 'use' (declarations+= useDeclaration )+ ) -> useDeclarationList(useDeclarations=$declarations))
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:80:9: ^( 'use' (declarations+= useDeclaration )+ )
			{
			match(input,Use,FOLLOW_Use_in_useDeclarationList287); 
			match(input, Token.DOWN, null); 
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:80:29: (declarations+= useDeclaration )+
			int cnt6=0;
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( (LA6_0==USE_DECLARATION) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:80:29: declarations+= useDeclaration
					{
					pushFollow(FOLLOW_useDeclaration_in_useDeclarationList291);
					declarations=useDeclaration();
					state._fsp--;

					if (list_declarations==null) list_declarations=new ArrayList<Object>();
					list_declarations.add(declarations.getTemplate());
					}
					break;

				default :
					if ( cnt6 >= 1 ) break loop6;
					EarlyExitException eee = new EarlyExitException(6, input);
					throw eee;
				}
				cnt6++;
			}

			match(input, Token.UP, null); 

			// TEMPLATE REWRITE
			// 81:9: -> useDeclarationList(useDeclarations=$declarations)
			{
				retval.st = templateLib.getInstanceOf("useDeclarationList",new STAttrMap().put("useDeclarations", list_declarations));
			}



			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "useDeclarationList"


	public static class useDeclaration_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "useDeclaration"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:84:1: useDeclaration : ^( USE_DECLARATION TYPE_NAME Identifier ) -> useDeclaration(type=$TYPE_NAMEalias=$Identifier);
	public final TSPHPTranslatorWalker.useDeclaration_return useDeclaration() throws RecognitionException {
		TSPHPTranslatorWalker.useDeclaration_return retval = new TSPHPTranslatorWalker.useDeclaration_return();
		retval.start = input.LT(1);

		ITSPHPAst TYPE_NAME4=null;
		ITSPHPAst Identifier5=null;

		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:85:5: ( ^( USE_DECLARATION TYPE_NAME Identifier ) -> useDeclaration(type=$TYPE_NAMEalias=$Identifier))
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:85:9: ^( USE_DECLARATION TYPE_NAME Identifier )
			{
			match(input,USE_DECLARATION,FOLLOW_USE_DECLARATION_in_useDeclaration330); 
			match(input, Token.DOWN, null); 
			TYPE_NAME4=(ITSPHPAst)match(input,TYPE_NAME,FOLLOW_TYPE_NAME_in_useDeclaration332); 
			Identifier5=(ITSPHPAst)match(input,Identifier,FOLLOW_Identifier_in_useDeclaration334); 
			match(input, Token.UP, null); 

			// TEMPLATE REWRITE
			// 86:9: -> useDeclaration(type=$TYPE_NAMEalias=$Identifier)
			{
				retval.st = templateLib.getInstanceOf("useDeclaration",new STAttrMap().put("type", TYPE_NAME4).put("alias", Identifier5));
			}



			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "useDeclaration"


	public static class definition_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "definition"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:89:1: definition : constDeclarationList -> {$constDeclarationList.st};
	public final TSPHPTranslatorWalker.definition_return definition() throws RecognitionException {
		TSPHPTranslatorWalker.definition_return retval = new TSPHPTranslatorWalker.definition_return();
		retval.start = input.LT(1);

		TreeRuleReturnScope constDeclarationList6 =null;

		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:90:5: ( constDeclarationList -> {$constDeclarationList.st})
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:96:8: constDeclarationList
			{
			pushFollow(FOLLOW_constDeclarationList_in_definition417);
			constDeclarationList6=constDeclarationList();
			state._fsp--;

			// TEMPLATE REWRITE
			// 96:32: -> {$constDeclarationList.st}
			{
				retval.st = (constDeclarationList6!=null?((StringTemplate)constDeclarationList6.getTemplate()):null);
			}



			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "definition"


	public static class constDeclarationList_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "constDeclarationList"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:150:1: constDeclarationList : ^( CONSTANT_DECLARATION_LIST ^( TYPE ^( TYPE_MODIFIER Public Static Final ) scalarTypesOrUnknown ) (identifiers+= constDeclaration )+ ) -> const(type=$scalarTypesOrUnknown.stidentifiers=$identifiers);
	public final TSPHPTranslatorWalker.constDeclarationList_return constDeclarationList() throws RecognitionException {
		TSPHPTranslatorWalker.constDeclarationList_return retval = new TSPHPTranslatorWalker.constDeclarationList_return();
		retval.start = input.LT(1);

		List<Object> list_identifiers=null;
		TreeRuleReturnScope scalarTypesOrUnknown7 =null;
		RuleReturnScope identifiers = null;
		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:151:5: ( ^( CONSTANT_DECLARATION_LIST ^( TYPE ^( TYPE_MODIFIER Public Static Final ) scalarTypesOrUnknown ) (identifiers+= constDeclaration )+ ) -> const(type=$scalarTypesOrUnknown.stidentifiers=$identifiers))
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:151:9: ^( CONSTANT_DECLARATION_LIST ^( TYPE ^( TYPE_MODIFIER Public Static Final ) scalarTypesOrUnknown ) (identifiers+= constDeclaration )+ )
			{
			match(input,CONSTANT_DECLARATION_LIST,FOLLOW_CONSTANT_DECLARATION_LIST_in_constDeclarationList453); 
			match(input, Token.DOWN, null); 
			match(input,TYPE,FOLLOW_TYPE_in_constDeclarationList468); 
			match(input, Token.DOWN, null); 
			match(input,TYPE_MODIFIER,FOLLOW_TYPE_MODIFIER_in_constDeclarationList471); 
			match(input, Token.DOWN, null); 
			match(input,Public,FOLLOW_Public_in_constDeclarationList473); 
			match(input,Static,FOLLOW_Static_in_constDeclarationList475); 
			match(input,Final,FOLLOW_Final_in_constDeclarationList477); 
			match(input, Token.UP, null); 

			pushFollow(FOLLOW_scalarTypesOrUnknown_in_constDeclarationList480);
			scalarTypesOrUnknown7=scalarTypesOrUnknown();
			state._fsp--;

			match(input, Token.UP, null); 

			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:153:24: (identifiers+= constDeclaration )+
			int cnt7=0;
			loop7:
			while (true) {
				int alt7=2;
				int LA7_0 = input.LA(1);
				if ( (LA7_0==Identifier) ) {
					alt7=1;
				}

				switch (alt7) {
				case 1 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:153:24: identifiers+= constDeclaration
					{
					pushFollow(FOLLOW_constDeclaration_in_constDeclarationList497);
					identifiers=constDeclaration();
					state._fsp--;

					if (list_identifiers==null) list_identifiers=new ArrayList<Object>();
					list_identifiers.add(identifiers.getTemplate());
					}
					break;

				default :
					if ( cnt7 >= 1 ) break loop7;
					EarlyExitException eee = new EarlyExitException(7, input);
					throw eee;
				}
				cnt7++;
			}

			match(input, Token.UP, null); 

			// TEMPLATE REWRITE
			// 155:9: -> const(type=$scalarTypesOrUnknown.stidentifiers=$identifiers)
			{
				retval.st = templateLib.getInstanceOf("const",new STAttrMap().put("type", (scalarTypesOrUnknown7!=null?((StringTemplate)scalarTypesOrUnknown7.getTemplate()):null)).put("identifiers", list_identifiers));
			}



			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "constDeclarationList"


	public static class constDeclaration_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "constDeclaration"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:158:1: constDeclaration : ^( Identifier unaryPrimitiveAtom ) -> assign(id=$Identifier.text.substring(0,$Identifier.text.length()-1)value=$unaryPrimitiveAtom.st);
	public final TSPHPTranslatorWalker.constDeclaration_return constDeclaration() throws RecognitionException {
		TSPHPTranslatorWalker.constDeclaration_return retval = new TSPHPTranslatorWalker.constDeclaration_return();
		retval.start = input.LT(1);

		ITSPHPAst Identifier8=null;
		TreeRuleReturnScope unaryPrimitiveAtom9 =null;

		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:159:5: ( ^( Identifier unaryPrimitiveAtom ) -> assign(id=$Identifier.text.substring(0,$Identifier.text.length()-1)value=$unaryPrimitiveAtom.st))
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:159:9: ^( Identifier unaryPrimitiveAtom )
			{
			Identifier8=(ITSPHPAst)match(input,Identifier,FOLLOW_Identifier_in_constDeclaration555); 
			match(input, Token.DOWN, null); 
			pushFollow(FOLLOW_unaryPrimitiveAtom_in_constDeclaration557);
			unaryPrimitiveAtom9=unaryPrimitiveAtom();
			state._fsp--;

			match(input, Token.UP, null); 

			// TEMPLATE REWRITE
			// 160:9: -> assign(id=$Identifier.text.substring(0,$Identifier.text.length()-1)value=$unaryPrimitiveAtom.st)
			{
				retval.st = templateLib.getInstanceOf("assign",new STAttrMap().put("id", (Identifier8!=null?Identifier8.getText():null).substring(0,(Identifier8!=null?Identifier8.getText():null).length()-1)).put("value", (unaryPrimitiveAtom9!=null?((StringTemplate)unaryPrimitiveAtom9.getTemplate()):null)));
			}



			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "constDeclaration"


	public static class unaryPrimitiveAtom_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "unaryPrimitiveAtom"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:163:1: unaryPrimitiveAtom : ( primitiveAtomWithConstant -> {$primitiveAtomWithConstant.st}| ^( ( UNARY_MINUS | UNARY_PLUS ) primitiveAtomWithConstant ) -> unaryPreOperator(operator=unaryexpression=$primitiveAtomWithConstant.st));
	public final TSPHPTranslatorWalker.unaryPrimitiveAtom_return unaryPrimitiveAtom() throws RecognitionException {
		TSPHPTranslatorWalker.unaryPrimitiveAtom_return retval = new TSPHPTranslatorWalker.unaryPrimitiveAtom_return();
		retval.start = input.LT(1);

		TreeRuleReturnScope primitiveAtomWithConstant10 =null;
		TreeRuleReturnScope primitiveAtomWithConstant11 =null;


		    String unary="";

		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:167:5: ( primitiveAtomWithConstant -> {$primitiveAtomWithConstant.st}| ^( ( UNARY_MINUS | UNARY_PLUS ) primitiveAtomWithConstant ) -> unaryPreOperator(operator=unaryexpression=$primitiveAtomWithConstant.st))
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( ((LA9_0 >= Bool && LA9_0 <= Null)||LA9_0==CONSTANT) ) {
				alt9=1;
			}
			else if ( ((LA9_0 >= UNARY_MINUS && LA9_0 <= UNARY_PLUS)) ) {
				alt9=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 9, 0, input);
				throw nvae;
			}

			switch (alt9) {
				case 1 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:167:9: primitiveAtomWithConstant
					{
					pushFollow(FOLLOW_primitiveAtomWithConstant_in_unaryPrimitiveAtom607);
					primitiveAtomWithConstant10=primitiveAtomWithConstant();
					state._fsp--;

					// TEMPLATE REWRITE
					// 168:9: -> {$primitiveAtomWithConstant.st}
					{
						retval.st = (primitiveAtomWithConstant10!=null?((StringTemplate)primitiveAtomWithConstant10.getTemplate()):null);
					}



					}
					break;
				case 2 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:170:9: ^( ( UNARY_MINUS | UNARY_PLUS ) primitiveAtomWithConstant )
					{
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:170:13: ( UNARY_MINUS | UNARY_PLUS )
					int alt8=2;
					int LA8_0 = input.LA(1);
					if ( (LA8_0==UNARY_MINUS) ) {
						alt8=1;
					}
					else if ( (LA8_0==UNARY_PLUS) ) {
						alt8=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 8, 0, input);
						throw nvae;
					}

					switch (alt8) {
						case 1 :
							// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:170:18: UNARY_MINUS
							{
							match(input,UNARY_MINUS,FOLLOW_UNARY_MINUS_in_unaryPrimitiveAtom638); 
							unary="-";
							}
							break;
						case 2 :
							// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:171:18: UNARY_PLUS
							{
							match(input,UNARY_PLUS,FOLLOW_UNARY_PLUS_in_unaryPrimitiveAtom659); 
							unary="+";
							}
							break;

					}

					match(input, Token.DOWN, null); 
					pushFollow(FOLLOW_primitiveAtomWithConstant_in_unaryPrimitiveAtom677);
					primitiveAtomWithConstant11=primitiveAtomWithConstant();
					state._fsp--;

					match(input, Token.UP, null); 

					// TEMPLATE REWRITE
					// 174:9: -> unaryPreOperator(operator=unaryexpression=$primitiveAtomWithConstant.st)
					{
						retval.st = templateLib.getInstanceOf("unaryPreOperator",new STAttrMap().put("operator", unary).put("expression", (primitiveAtomWithConstant11!=null?((StringTemplate)primitiveAtomWithConstant11.getTemplate()):null)));
					}



					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "unaryPrimitiveAtom"


	public static class scalarTypesOrUnknown_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "scalarTypesOrUnknown"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:270:1: scalarTypesOrUnknown : ( scalarTypes -> {$scalarTypes.st}| '?' -> {%{\"?\"}});
	public final TSPHPTranslatorWalker.scalarTypesOrUnknown_return scalarTypesOrUnknown() throws RecognitionException {
		TSPHPTranslatorWalker.scalarTypesOrUnknown_return retval = new TSPHPTranslatorWalker.scalarTypesOrUnknown_return();
		retval.start = input.LT(1);

		TreeRuleReturnScope scalarTypes12 =null;

		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:271:5: ( scalarTypes -> {$scalarTypes.st}| '?' -> {%{\"?\"}})
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( ((LA10_0 >= TypeBool && LA10_0 <= TypeInt)||LA10_0==TypeString) ) {
				alt10=1;
			}
			else if ( (LA10_0==QuestionMark) ) {
				alt10=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 10, 0, input);
				throw nvae;
			}

			switch (alt10) {
				case 1 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:271:9: scalarTypes
					{
					pushFollow(FOLLOW_scalarTypes_in_scalarTypesOrUnknown743);
					scalarTypes12=scalarTypes();
					state._fsp--;

					// TEMPLATE REWRITE
					// 271:21: -> {$scalarTypes.st}
					{
						retval.st = (scalarTypes12!=null?((StringTemplate)scalarTypes12.getTemplate()):null);
					}



					}
					break;
				case 2 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:272:9: '?'
					{
					match(input,QuestionMark,FOLLOW_QuestionMark_in_scalarTypesOrUnknown757); 
					// TEMPLATE REWRITE
					// 272:21: -> {%{\"?\"}}
					{
						retval.st = new StringTemplate(templateLib,"?");
					}



					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "scalarTypesOrUnknown"


	public static class scalarTypes_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "scalarTypes"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:275:1: scalarTypes : ( TypeBool -> {%{$TypeBool.text}}| TypeInt -> {%{$TypeInt.text}}| TypeFloat -> {%{$TypeFloat.text}}| TypeString -> {%{$TypeString.text}});
	public final TSPHPTranslatorWalker.scalarTypes_return scalarTypes() throws RecognitionException {
		TSPHPTranslatorWalker.scalarTypes_return retval = new TSPHPTranslatorWalker.scalarTypes_return();
		retval.start = input.LT(1);

		ITSPHPAst TypeBool13=null;
		ITSPHPAst TypeInt14=null;
		ITSPHPAst TypeFloat15=null;
		ITSPHPAst TypeString16=null;

		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:276:5: ( TypeBool -> {%{$TypeBool.text}}| TypeInt -> {%{$TypeInt.text}}| TypeFloat -> {%{$TypeFloat.text}}| TypeString -> {%{$TypeString.text}})
			int alt11=4;
			switch ( input.LA(1) ) {
			case TypeBool:
				{
				alt11=1;
				}
				break;
			case TypeInt:
				{
				alt11=2;
				}
				break;
			case TypeFloat:
				{
				alt11=3;
				}
				break;
			case TypeString:
				{
				alt11=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 11, 0, input);
				throw nvae;
			}
			switch (alt11) {
				case 1 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:276:9: TypeBool
					{
					TypeBool13=(ITSPHPAst)match(input,TypeBool,FOLLOW_TypeBool_in_scalarTypes788); 
					// TEMPLATE REWRITE
					// 276:21: -> {%{$TypeBool.text}}
					{
						retval.st = new StringTemplate(templateLib,(TypeBool13!=null?TypeBool13.getText():null));
					}



					}
					break;
				case 2 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:277:9: TypeInt
					{
					TypeInt14=(ITSPHPAst)match(input,TypeInt,FOLLOW_TypeInt_in_scalarTypes805); 
					// TEMPLATE REWRITE
					// 277:21: -> {%{$TypeInt.text}}
					{
						retval.st = new StringTemplate(templateLib,(TypeInt14!=null?TypeInt14.getText():null));
					}



					}
					break;
				case 3 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:278:9: TypeFloat
					{
					TypeFloat15=(ITSPHPAst)match(input,TypeFloat,FOLLOW_TypeFloat_in_scalarTypes823); 
					// TEMPLATE REWRITE
					// 278:21: -> {%{$TypeFloat.text}}
					{
						retval.st = new StringTemplate(templateLib,(TypeFloat15!=null?TypeFloat15.getText():null));
					}



					}
					break;
				case 4 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:279:9: TypeString
					{
					TypeString16=(ITSPHPAst)match(input,TypeString,FOLLOW_TypeString_in_scalarTypes839); 
					// TEMPLATE REWRITE
					// 279:21: -> {%{$TypeString.text}}
					{
						retval.st = new StringTemplate(templateLib,(TypeString16!=null?TypeString16.getText():null));
					}



					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "scalarTypes"


	public static class expression_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "expression"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:632:1: expression : atom -> {$atom.st};
	public final TSPHPTranslatorWalker.expression_return expression() throws RecognitionException {
		TSPHPTranslatorWalker.expression_return retval = new TSPHPTranslatorWalker.expression_return();
		retval.start = input.LT(1);

		TreeRuleReturnScope atom17 =null;

		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:633:5: ( atom -> {$atom.st})
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:633:9: atom
			{
			pushFollow(FOLLOW_atom_in_expression884);
			atom17=atom();
			state._fsp--;

			// TEMPLATE REWRITE
			// 633:33: -> {$atom.st}
			{
				retval.st = (atom17!=null?((StringTemplate)atom17.getTemplate()):null);
			}



			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expression"


	public static class atom_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "atom"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:647:1: atom : primitiveAtomWithConstant -> {$primitiveAtomWithConstant.st};
	public final TSPHPTranslatorWalker.atom_return atom() throws RecognitionException {
		TSPHPTranslatorWalker.atom_return retval = new TSPHPTranslatorWalker.atom_return();
		retval.start = input.LT(1);

		TreeRuleReturnScope primitiveAtomWithConstant18 =null;

		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:648:5: ( primitiveAtomWithConstant -> {$primitiveAtomWithConstant.st})
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:648:9: primitiveAtomWithConstant
			{
			pushFollow(FOLLOW_primitiveAtomWithConstant_in_atom939);
			primitiveAtomWithConstant18=primitiveAtomWithConstant();
			state._fsp--;

			// TEMPLATE REWRITE
			// 648:37: -> {$primitiveAtomWithConstant.st}
			{
				retval.st = (primitiveAtomWithConstant18!=null?((StringTemplate)primitiveAtomWithConstant18.getTemplate()):null);
			}



			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "atom"


	public static class primitiveAtomWithConstant_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "primitiveAtomWithConstant"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:657:1: primitiveAtomWithConstant : ( Bool -> {%{$Bool.text}}| Int -> {%{$Int.text}}| Float -> {%{$Float.text}}| String -> {%{$String.text}}| Null -> {%{$Null.text}}| ^( TypeArray (keyValuePairs+= arrayKeyValue )* ) -> array(content=$keyValuePairs)| CONSTANT -> {%{$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)}});
	public final TSPHPTranslatorWalker.primitiveAtomWithConstant_return primitiveAtomWithConstant() throws RecognitionException {
		TSPHPTranslatorWalker.primitiveAtomWithConstant_return retval = new TSPHPTranslatorWalker.primitiveAtomWithConstant_return();
		retval.start = input.LT(1);

		ITSPHPAst Bool19=null;
		ITSPHPAst Int20=null;
		ITSPHPAst Float21=null;
		ITSPHPAst String22=null;
		ITSPHPAst Null23=null;
		ITSPHPAst CONSTANT24=null;
		List<Object> list_keyValuePairs=null;
		RuleReturnScope keyValuePairs = null;
		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:658:5: ( Bool -> {%{$Bool.text}}| Int -> {%{$Int.text}}| Float -> {%{$Float.text}}| String -> {%{$String.text}}| Null -> {%{$Null.text}}| ^( TypeArray (keyValuePairs+= arrayKeyValue )* ) -> array(content=$keyValuePairs)| CONSTANT -> {%{$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)}})
			int alt13=7;
			switch ( input.LA(1) ) {
			case Bool:
				{
				alt13=1;
				}
				break;
			case Int:
				{
				alt13=2;
				}
				break;
			case Float:
				{
				alt13=3;
				}
				break;
			case String:
				{
				alt13=4;
				}
				break;
			case Null:
				{
				alt13=5;
				}
				break;
			case TypeArray:
				{
				alt13=6;
				}
				break;
			case CONSTANT:
				{
				alt13=7;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 13, 0, input);
				throw nvae;
			}
			switch (alt13) {
				case 1 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:658:9: Bool
					{
					Bool19=(ITSPHPAst)match(input,Bool,FOLLOW_Bool_in_primitiveAtomWithConstant987); 
					// TEMPLATE REWRITE
					// 658:53: -> {%{$Bool.text}}
					{
						retval.st = new StringTemplate(templateLib,(Bool19!=null?Bool19.getText():null));
					}



					}
					break;
				case 2 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:659:9: Int
					{
					Int20=(ITSPHPAst)match(input,Int,FOLLOW_Int_in_primitiveAtomWithConstant1040); 
					// TEMPLATE REWRITE
					// 659:53: -> {%{$Int.text}}
					{
						retval.st = new StringTemplate(templateLib,(Int20!=null?Int20.getText():null));
					}



					}
					break;
				case 3 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:660:9: Float
					{
					Float21=(ITSPHPAst)match(input,Float,FOLLOW_Float_in_primitiveAtomWithConstant1094); 
					// TEMPLATE REWRITE
					// 660:53: -> {%{$Float.text}}
					{
						retval.st = new StringTemplate(templateLib,(Float21!=null?Float21.getText():null));
					}



					}
					break;
				case 4 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:661:9: String
					{
					String22=(ITSPHPAst)match(input,String,FOLLOW_String_in_primitiveAtomWithConstant1146); 
					// TEMPLATE REWRITE
					// 661:53: -> {%{$String.text}}
					{
						retval.st = new StringTemplate(templateLib,(String22!=null?String22.getText():null));
					}



					}
					break;
				case 5 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:662:9: Null
					{
					Null23=(ITSPHPAst)match(input,Null,FOLLOW_Null_in_primitiveAtomWithConstant1197); 
					// TEMPLATE REWRITE
					// 662:53: -> {%{$Null.text}}
					{
						retval.st = new StringTemplate(templateLib,(Null23!=null?Null23.getText():null));
					}



					}
					break;
				case 6 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:663:9: ^( TypeArray (keyValuePairs+= arrayKeyValue )* )
					{
					match(input,TypeArray,FOLLOW_TypeArray_in_primitiveAtomWithConstant1251); 
					if ( input.LA(1)==Token.DOWN ) {
						match(input, Token.DOWN, null); 
						// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:663:34: (keyValuePairs+= arrayKeyValue )*
						loop12:
						while (true) {
							int alt12=2;
							int LA12_0 = input.LA(1);
							if ( ((LA12_0 >= Bool && LA12_0 <= Null)||LA12_0==CONSTANT||LA12_0==Arrow) ) {
								alt12=1;
							}

							switch (alt12) {
							case 1 :
								// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:663:34: keyValuePairs+= arrayKeyValue
								{
								pushFollow(FOLLOW_arrayKeyValue_in_primitiveAtomWithConstant1255);
								keyValuePairs=arrayKeyValue();
								state._fsp--;

								if (list_keyValuePairs==null) list_keyValuePairs=new ArrayList<Object>();
								list_keyValuePairs.add(keyValuePairs.getTemplate());
								}
								break;

							default :
								break loop12;
							}
						}

						match(input, Token.UP, null); 
					}

					// TEMPLATE REWRITE
					// 663:53: -> array(content=$keyValuePairs)
					{
						retval.st = templateLib.getInstanceOf("array",new STAttrMap().put("content", list_keyValuePairs));
					}



					}
					break;
				case 7 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:664:9: CONSTANT
					{
					CONSTANT24=(ITSPHPAst)match(input,CONSTANT,FOLLOW_CONSTANT_in_primitiveAtomWithConstant1278); 
					// TEMPLATE REWRITE
					// 664:53: -> {%{$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)}}
					{
						retval.st = new StringTemplate(templateLib,(CONSTANT24!=null?CONSTANT24.getText():null).substring(0,(CONSTANT24!=null?CONSTANT24.getText():null).length()-1));
					}



					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "primitiveAtomWithConstant"


	public static class arrayKeyValue_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "arrayKeyValue"
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:673:1: arrayKeyValue : ( ^( '=>' key= expression value= expression ) -> keyValue(key=$key.stvalue=$value.st)| expression -> {$expression.st});
	public final TSPHPTranslatorWalker.arrayKeyValue_return arrayKeyValue() throws RecognitionException {
		TSPHPTranslatorWalker.arrayKeyValue_return retval = new TSPHPTranslatorWalker.arrayKeyValue_return();
		retval.start = input.LT(1);

		TreeRuleReturnScope key =null;
		TreeRuleReturnScope value =null;
		TreeRuleReturnScope expression25 =null;

		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:674:5: ( ^( '=>' key= expression value= expression ) -> keyValue(key=$key.stvalue=$value.st)| expression -> {$expression.st})
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==Arrow) ) {
				alt14=1;
			}
			else if ( ((LA14_0 >= Bool && LA14_0 <= Null)||LA14_0==CONSTANT) ) {
				alt14=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 14, 0, input);
				throw nvae;
			}

			switch (alt14) {
				case 1 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:674:9: ^( '=>' key= expression value= expression )
					{
					match(input,Arrow,FOLLOW_Arrow_in_arrayKeyValue1367); 
					match(input, Token.DOWN, null); 
					pushFollow(FOLLOW_expression_in_arrayKeyValue1371);
					key=expression();
					state._fsp--;

					pushFollow(FOLLOW_expression_in_arrayKeyValue1375);
					value=expression();
					state._fsp--;

					match(input, Token.UP, null); 

					// TEMPLATE REWRITE
					// 674:49: -> keyValue(key=$key.stvalue=$value.st)
					{
						retval.st = templateLib.getInstanceOf("keyValue",new STAttrMap().put("key", (key!=null?((StringTemplate)key.getTemplate()):null)).put("value", (value!=null?((StringTemplate)value.getTemplate()):null)));
					}



					}
					break;
				case 2 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:675:9: expression
					{
					pushFollow(FOLLOW_expression_in_arrayKeyValue1400);
					expression25=expression();
					state._fsp--;

					// TEMPLATE REWRITE
					// 675:20: -> {$expression.st}
					{
						retval.st = (expression25!=null?((StringTemplate)expression25.getTemplate()):null);
					}



					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "arrayKeyValue"

	// Delegated rules



	public static final BitSet FOLLOW_namespace_in_compilationUnit80 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_Namespace_in_namespace119 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_TYPE_NAME_in_namespace124 = new BitSet(new long[]{0x0000000000000000L,0x8000000000000000L});
	public static final BitSet FOLLOW_DEFAULT_NAMESPACE_in_namespace126 = new BitSet(new long[]{0x0000000000000000L,0x8000000000000000L});
	public static final BitSet FOLLOW_namespaceBody_in_namespace129 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_NAMESPACE_BODY_in_namespaceBody186 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_statement_in_namespaceBody190 = new BitSet(new long[]{0x0000000000000008L,0x0000000000100000L,0x0000080000000000L});
	public static final BitSet FOLLOW_NAMESPACE_BODY_in_namespaceBody211 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_useDeclarationList_in_statement239 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_definition_in_statement253 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_Use_in_useDeclarationList287 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_useDeclaration_in_useDeclarationList291 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000040000000000L});
	public static final BitSet FOLLOW_USE_DECLARATION_in_useDeclaration330 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_TYPE_NAME_in_useDeclaration332 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
	public static final BitSet FOLLOW_Identifier_in_useDeclaration334 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_constDeclarationList_in_definition417 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CONSTANT_DECLARATION_LIST_in_constDeclarationList453 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_TYPE_in_constDeclarationList468 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_TYPE_MODIFIER_in_constDeclarationList471 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_Public_in_constDeclarationList473 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000001000000L});
	public static final BitSet FOLLOW_Static_in_constDeclarationList475 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_Final_in_constDeclarationList477 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_scalarTypesOrUnknown_in_constDeclarationList480 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_constDeclaration_in_constDeclarationList497 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
	public static final BitSet FOLLOW_Identifier_in_constDeclaration555 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_unaryPrimitiveAtom_in_constDeclaration557 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_primitiveAtomWithConstant_in_unaryPrimitiveAtom607 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_UNARY_MINUS_in_unaryPrimitiveAtom638 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_UNARY_PLUS_in_unaryPrimitiveAtom659 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_primitiveAtomWithConstant_in_unaryPrimitiveAtom677 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_scalarTypes_in_scalarTypesOrUnknown743 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QuestionMark_in_scalarTypesOrUnknown757 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TypeBool_in_scalarTypes788 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TypeInt_in_scalarTypes805 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TypeFloat_in_scalarTypes823 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TypeString_in_scalarTypes839 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_atom_in_expression884 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_primitiveAtomWithConstant_in_atom939 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_Bool_in_primitiveAtomWithConstant987 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_Int_in_primitiveAtomWithConstant1040 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_Float_in_primitiveAtomWithConstant1094 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_String_in_primitiveAtomWithConstant1146 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_Null_in_primitiveAtomWithConstant1197 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TypeArray_in_primitiveAtomWithConstant1251 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_arrayKeyValue_in_primitiveAtomWithConstant1255 = new BitSet(new long[]{0x8000000000000008L,0x000000000000025FL});
	public static final BitSet FOLLOW_CONSTANT_in_primitiveAtomWithConstant1278 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_Arrow_in_arrayKeyValue1367 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_expression_in_arrayKeyValue1371 = new BitSet(new long[]{0x8000000000000000L,0x000000000000005FL});
	public static final BitSet FOLLOW_expression_in_arrayKeyValue1375 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_expression_in_arrayKeyValue1400 = new BitSet(new long[]{0x0000000000000002L});
}
