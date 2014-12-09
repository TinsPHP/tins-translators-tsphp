// $ANTLR 3.5.2-including-157 D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g 2014-12-09 15:29:37

/*
 * This file is part of the TSPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TSPHP/License
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
							if ( (LA3_0==Use) ) {
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
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:72:1: statement : useDeclarationList -> {$useDeclarationList.st};
	public final TSPHPTranslatorWalker.statement_return statement() throws RecognitionException {
		TSPHPTranslatorWalker.statement_return retval = new TSPHPTranslatorWalker.statement_return();
		retval.start = input.LT(1);

		TreeRuleReturnScope useDeclarationList2 =null;

		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:73:5: ( useDeclarationList -> {$useDeclarationList.st})
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
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:80:1: useDeclarationList : ^( 'use' (declarations+= useDeclaration )+ ) -> useDeclarationList(useDeclarations=$declarations);
	public final TSPHPTranslatorWalker.useDeclarationList_return useDeclarationList() throws RecognitionException {
		TSPHPTranslatorWalker.useDeclarationList_return retval = new TSPHPTranslatorWalker.useDeclarationList_return();
		retval.start = input.LT(1);

		List<Object> list_declarations=null;
		RuleReturnScope declarations = null;
		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:81:5: ( ^( 'use' (declarations+= useDeclaration )+ ) -> useDeclarationList(useDeclarations=$declarations))
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:81:9: ^( 'use' (declarations+= useDeclaration )+ )
			{
			match(input,Use,FOLLOW_Use_in_useDeclarationList287); 
			match(input, Token.DOWN, null); 
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:81:29: (declarations+= useDeclaration )+
			int cnt5=0;
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( (LA5_0==USE_DECLARATION) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:81:29: declarations+= useDeclaration
					{
					pushFollow(FOLLOW_useDeclaration_in_useDeclarationList291);
					declarations=useDeclaration();
					state._fsp--;

					if (list_declarations==null) list_declarations=new ArrayList<Object>();
					list_declarations.add(declarations.getTemplate());
					}
					break;

				default :
					if ( cnt5 >= 1 ) break loop5;
					EarlyExitException eee = new EarlyExitException(5, input);
					throw eee;
				}
				cnt5++;
			}

			match(input, Token.UP, null); 

			// TEMPLATE REWRITE
			// 82:9: -> useDeclarationList(useDeclarations=$declarations)
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
	// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:85:1: useDeclaration : ^( USE_DECLARATION TYPE_NAME Identifier ) -> useDeclaration(type=$TYPE_NAMEalias=$Identifier);
	public final TSPHPTranslatorWalker.useDeclaration_return useDeclaration() throws RecognitionException {
		TSPHPTranslatorWalker.useDeclaration_return retval = new TSPHPTranslatorWalker.useDeclaration_return();
		retval.start = input.LT(1);

		ITSPHPAst TYPE_NAME3=null;
		ITSPHPAst Identifier4=null;

		try {
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:86:5: ( ^( USE_DECLARATION TYPE_NAME Identifier ) -> useDeclaration(type=$TYPE_NAMEalias=$Identifier))
			// D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:86:9: ^( USE_DECLARATION TYPE_NAME Identifier )
			{
			match(input,USE_DECLARATION,FOLLOW_USE_DECLARATION_in_useDeclaration330); 
			match(input, Token.DOWN, null); 
			TYPE_NAME3=(ITSPHPAst)match(input,TYPE_NAME,FOLLOW_TYPE_NAME_in_useDeclaration332); 
			Identifier4=(ITSPHPAst)match(input,Identifier,FOLLOW_Identifier_in_useDeclaration334); 
			match(input, Token.UP, null); 

			// TEMPLATE REWRITE
			// 87:9: -> useDeclaration(type=$TYPE_NAMEalias=$Identifier)
			{
				retval.st = templateLib.getInstanceOf("useDeclaration",new STAttrMap().put("type", TYPE_NAME3).put("alias", Identifier4));
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

	// Delegated rules



	public static final BitSet FOLLOW_namespace_in_compilationUnit80 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_Namespace_in_namespace119 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_TYPE_NAME_in_namespace124 = new BitSet(new long[]{0x0000000000000000L,0x8000000000000000L});
	public static final BitSet FOLLOW_DEFAULT_NAMESPACE_in_namespace126 = new BitSet(new long[]{0x0000000000000000L,0x8000000000000000L});
	public static final BitSet FOLLOW_namespaceBody_in_namespace129 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_NAMESPACE_BODY_in_namespaceBody186 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_statement_in_namespaceBody190 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000080000000000L});
	public static final BitSet FOLLOW_NAMESPACE_BODY_in_namespaceBody211 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_useDeclarationList_in_statement239 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_Use_in_useDeclarationList287 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_useDeclaration_in_useDeclarationList291 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000040000000000L});
	public static final BitSet FOLLOW_USE_DECLARATION_in_useDeclaration330 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_TYPE_NAME_in_useDeclaration332 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
	public static final BitSet FOLLOW_Identifier_in_useDeclaration334 = new BitSet(new long[]{0x0000000000000008L});
}
