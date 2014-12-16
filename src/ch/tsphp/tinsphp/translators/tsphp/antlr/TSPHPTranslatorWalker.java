// $ANTLR 3.5.2-including-157 D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g 2014-12-16 21:00:05

/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.antlr;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.tinsphp.common.translation.IPrecedenceHelper;
import ch.tsphp.tinsphp.translators.tsphp.antlrmod.ParameterDto;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.RuleReturnScope;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;
import org.antlr.runtime.tree.TreeRuleReturnScope;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:54:1: compilationUnit : ( (n+= namespace )+ ) ->
    // file(namespaces=$n);
    public final TSPHPTranslatorWalker.compilationUnit_return compilationUnit() throws RecognitionException {
        TSPHPTranslatorWalker.compilationUnit_return retval = new TSPHPTranslatorWalker.compilationUnit_return();
        retval.start = input.LT(1);

		List<Object> list_n=null;
		RuleReturnScope n = null;
		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:55:5: ( ( (n+= namespace )+ ) -> file
            // (namespaces=$n))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:55:9: ( (n+= namespace )+ )
            {
                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:55:9: ( (n+= namespace )+ )
                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:55:10: (n+= namespace )+
                {
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:55:11: (n+= namespace )+
                    int cnt1 = 0;
                    loop1:
                    while (true) {
                        int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==Namespace) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:55:11: n+= namespace
                {
                    pushFollow(FOLLOW_namespace_in_compilationUnit80);
                    n = namespace();
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
                // 55:25: -> file(namespaces=$n)
                {
                    retval.st = templateLib.getInstanceOf("file", new STAttrMap().put("namespaces", list_n));
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:58:1: namespace : ^( 'namespace' (name= TYPE_NAME |
    // DEFAULT_NAMESPACE ) namespaceBody ) -> namespace(name=namespaceNamebody=$namespaceBody.st);
    public final TSPHPTranslatorWalker.namespace_return namespace() throws RecognitionException {
        TSPHPTranslatorWalker.namespace_return retval = new TSPHPTranslatorWalker.namespace_return();
        retval.start = input.LT(1);

		ITSPHPAst name=null;
		TreeRuleReturnScope namespaceBody1 =null;

		String namespaceName = null;
		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:60:5: ( ^( 'namespace' (name= TYPE_NAME |
            // DEFAULT_NAMESPACE ) namespaceBody ) -> namespace(name=namespaceNamebody=$namespaceBody.st))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:60:9: ^( 'namespace' (name= TYPE_NAME |
            // DEFAULT_NAMESPACE ) namespaceBody )
            {
                match(input, Namespace, FOLLOW_Namespace_in_namespace119);
                match(input, Token.DOWN, null);
                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:60:23: (name= TYPE_NAME |
                // DEFAULT_NAMESPACE )
                int alt2 = 2;
                int LA2_0 = input.LA(1);
                if ((LA2_0 == TYPE_NAME)) {
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
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:60:24: name= TYPE_NAME
                {
                    name = (ITSPHPAst) match(input, TYPE_NAME, FOLLOW_TYPE_NAME_in_namespace124);
                }
                break;
				case 2 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:60:39: DEFAULT_NAMESPACE
                {
                    match(input, DEFAULT_NAMESPACE, FOLLOW_DEFAULT_NAMESPACE_in_namespace126);
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
                // 67:9: -> namespace(name=namespaceNamebody=$namespaceBody.st)
                {
                    retval.st = templateLib.getInstanceOf("namespace", new STAttrMap().put("name",
                            namespaceName).put("body", (namespaceBody1 != null ? ((StringTemplate) namespaceBody1
                            .getTemplate()) : null)));
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:70:1: namespaceBody : ( ^( NAMESPACE_BODY
    // (statements+= statement )* ) -> body(statements=$statements)| NAMESPACE_BODY -> body(statements=null));
    public final TSPHPTranslatorWalker.namespaceBody_return namespaceBody() throws RecognitionException {
        TSPHPTranslatorWalker.namespaceBody_return retval = new TSPHPTranslatorWalker.namespaceBody_return();
        retval.start = input.LT(1);

		List<Object> list_statements=null;
		RuleReturnScope statements = null;
		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:71:5: ( ^( NAMESPACE_BODY (statements+=
            // statement )* ) -> body(statements=$statements)| NAMESPACE_BODY -> body(statements=null))
            int alt4 = 2;
            int LA4_0 = input.LA(1);
            if ((LA4_0 == NAMESPACE_BODY)) {
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
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:71:9: ^( NAMESPACE_BODY
                    // (statements+= statement )* )
                {
                    match(input, NAMESPACE_BODY, FOLLOW_NAMESPACE_BODY_in_namespaceBody186);
                    if (input.LA(1) == Token.DOWN) {
                        match(input, Token.DOWN, null);
                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:71:36: (statements+= statement )*
                        loop3:
                        while (true) {
                            int alt3 = 2;
                            int LA3_0 = input.LA(1);
                            if ((LA3_0 == CONSTANT_DECLARATION_LIST || LA3_0 == Function || LA3_0 == Use)) {
                                alt3 = 1;
                            }

							switch (alt3) {
							case 1 :
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:71:36: statements+=
                                // statement
                            {
                                pushFollow(FOLLOW_statement_in_namespaceBody190);
                                statements = statement();
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
                    // 71:50: -> body(statements=$statements)
                    {
                        retval.st = templateLib.getInstanceOf("body", new STAttrMap().put("statements",
                                list_statements));
                    }



					}
					break;
				case 2 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:72:9: NAMESPACE_BODY
                {
                    match(input, NAMESPACE_BODY, FOLLOW_NAMESPACE_BODY_in_namespaceBody211);
                    // TEMPLATE REWRITE
                    // 72:24: -> body(statements=null)
                    {
                        retval.st = templateLib.getInstanceOf("body", new STAttrMap().put("statements", null));
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:75:1: statement : ( useDeclarationList ->
    // {$useDeclarationList.st}| definition -> {$definition.st});
    public final TSPHPTranslatorWalker.statement_return statement() throws RecognitionException {
        TSPHPTranslatorWalker.statement_return retval = new TSPHPTranslatorWalker.statement_return();
        retval.start = input.LT(1);

		TreeRuleReturnScope useDeclarationList2 =null;
		TreeRuleReturnScope definition3 =null;

		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:76:5: ( useDeclarationList ->
            // {$useDeclarationList.st}| definition -> {$definition.st})
            int alt5 = 2;
            int LA5_0 = input.LA(1);
            if ((LA5_0 == Use)) {
                alt5=1;
            } else if ((LA5_0 == CONSTANT_DECLARATION_LIST || LA5_0 == Function)) {
                alt5 = 2;
            }

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}

			switch (alt5) {
				case 1 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:76:9: useDeclarationList
                {
                    pushFollow(FOLLOW_useDeclarationList_in_statement239);
                    useDeclarationList2 = useDeclarationList();
                    state._fsp--;

					// TEMPLATE REWRITE
                    // 76:28: -> {$useDeclarationList.st}
                    {
                        retval.st = (useDeclarationList2 != null ? ((StringTemplate) useDeclarationList2.getTemplate())
                                : null);
                    }



					}
					break;
				case 2 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:77:9: definition
                {
                    pushFollow(FOLLOW_definition_in_statement253);
                    definition3 = definition();
                    state._fsp--;

					// TEMPLATE REWRITE
                    // 77:20: -> {$definition.st}
                    {
                        retval.st = (definition3 != null ? ((StringTemplate) definition3.getTemplate()) : null);
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:82:1: useDeclarationList : ^( 'use' (declarations+=
    // useDeclaration )+ ) -> useDeclarationList(useDeclarations=$declarations);
    public final TSPHPTranslatorWalker.useDeclarationList_return useDeclarationList() throws RecognitionException {
        TSPHPTranslatorWalker.useDeclarationList_return retval = new TSPHPTranslatorWalker.useDeclarationList_return();
        retval.start = input.LT(1);

		List<Object> list_declarations=null;
		RuleReturnScope declarations = null;
		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:83:5: ( ^( 'use' (declarations+=
            // useDeclaration )+ ) -> useDeclarationList(useDeclarations=$declarations))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:83:9: ^( 'use' (declarations+=
            // useDeclaration )+ )
            {
                match(input, Use, FOLLOW_Use_in_useDeclarationList287);
                match(input, Token.DOWN, null);
                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:83:29: (declarations+= useDeclaration )+
                int cnt6 = 0;
                loop6:
                while (true) {
                    int alt6=2;
				int LA6_0 = input.LA(1);
				if ( (LA6_0==USE_DECLARATION) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:83:29: declarations+= useDeclaration
                {
                    pushFollow(FOLLOW_useDeclaration_in_useDeclarationList291);
                    declarations = useDeclaration();
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
                // 84:9: -> useDeclarationList(useDeclarations=$declarations)
                {
                    retval.st = templateLib.getInstanceOf("useDeclarationList", new STAttrMap().put("useDeclarations",
                            list_declarations));
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:87:1: useDeclaration : ^( USE_DECLARATION TYPE_NAME
    // Identifier ) -> useDeclaration(type=$TYPE_NAMEalias=$Identifier);
    public final TSPHPTranslatorWalker.useDeclaration_return useDeclaration() throws RecognitionException {
        TSPHPTranslatorWalker.useDeclaration_return retval = new TSPHPTranslatorWalker.useDeclaration_return();
        retval.start = input.LT(1);

		ITSPHPAst TYPE_NAME4=null;
		ITSPHPAst Identifier5=null;

		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:88:5: ( ^( USE_DECLARATION TYPE_NAME
            // Identifier ) -> useDeclaration(type=$TYPE_NAMEalias=$Identifier))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:88:9: ^( USE_DECLARATION TYPE_NAME
            // Identifier )
            {
                match(input, USE_DECLARATION, FOLLOW_USE_DECLARATION_in_useDeclaration330);
                match(input, Token.DOWN, null);
                TYPE_NAME4=(ITSPHPAst)match(input,TYPE_NAME,FOLLOW_TYPE_NAME_in_useDeclaration332);
			Identifier5=(ITSPHPAst)match(input,Identifier,FOLLOW_Identifier_in_useDeclaration334); 
			match(input, Token.UP, null); 

			// TEMPLATE REWRITE
                // 89:9: -> useDeclaration(type=$TYPE_NAMEalias=$Identifier)
                {
                    retval.st = templateLib.getInstanceOf("useDeclaration", new STAttrMap().put("type",
                            TYPE_NAME4).put("alias", Identifier5));
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:92:1: definition : ( functionDeclaration ->
    // {$functionDeclaration.st}| constDeclarationList -> {$constDeclarationList.st});
    public final TSPHPTranslatorWalker.definition_return definition() throws RecognitionException {
        TSPHPTranslatorWalker.definition_return retval = new TSPHPTranslatorWalker.definition_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope functionDeclaration6 = null;
        TreeRuleReturnScope constDeclarationList7 = null;

		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:93:5: ( functionDeclaration ->
            // {$functionDeclaration.st}| constDeclarationList -> {$constDeclarationList.st})
            int alt7 = 2;
            int LA7_0 = input.LA(1);
            if ((LA7_0 == Function)) {
                alt7 = 1;
            } else if ((LA7_0 == CONSTANT_DECLARATION_LIST)) {
                alt7 = 2;
            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 7, 0, input);
                throw nvae;
            }

            switch (alt7) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:97:9: functionDeclaration
                {
                    pushFollow(FOLLOW_functionDeclaration_in_definition408);
                    functionDeclaration6 = functionDeclaration();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 97:33: -> {$functionDeclaration.st}
                    {
                        retval.st = (functionDeclaration6 != null ? ((StringTemplate) functionDeclaration6
                                .getTemplate()) : null);
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:98:9: constDeclarationList
                {
                    pushFollow(FOLLOW_constDeclarationList_in_definition426);
                    constDeclarationList7 = constDeclarationList();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 98:33: -> {$constDeclarationList.st}
                    {
                        retval.st = (constDeclarationList7 != null ? ((StringTemplate) constDeclarationList7
                                .getTemplate()) : null);
                    }


                }
                break;

            }
        } catch (RecognitionException re) {
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:152:1: constDeclarationList : ^(
    // CONSTANT_DECLARATION_LIST ^( TYPE ^( TYPE_MODIFIER Public Static Final ) scalarTypesOrUnknown ) (identifiers+=
    // constDeclaration )+ ) -> const(type=$scalarTypesOrUnknown.stidentifiers=$identifiers);

    public final TSPHPTranslatorWalker.constDeclarationList_return constDeclarationList() throws RecognitionException {
        TSPHPTranslatorWalker.constDeclarationList_return retval = new TSPHPTranslatorWalker
                .constDeclarationList_return();
        retval.start = input.LT(1);

		List<Object> list_identifiers=null;
        TreeRuleReturnScope scalarTypesOrUnknown8 = null;
        RuleReturnScope identifiers = null;
        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:153:5: ( ^( CONSTANT_DECLARATION_LIST ^(
            // TYPE ^( TYPE_MODIFIER Public Static Final ) scalarTypesOrUnknown ) (identifiers+= constDeclaration )+
            // ) -> const(type=$scalarTypesOrUnknown.stidentifiers=$identifiers))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:153:9: ^( CONSTANT_DECLARATION_LIST ^( TYPE
            // ^( TYPE_MODIFIER Public Static Final ) scalarTypesOrUnknown ) (identifiers+= constDeclaration )+ )
            {
                match(input, CONSTANT_DECLARATION_LIST, FOLLOW_CONSTANT_DECLARATION_LIST_in_constDeclarationList462);
                match(input, Token.DOWN, null);
                match(input, TYPE, FOLLOW_TYPE_in_constDeclarationList477);
                match(input, Token.DOWN, null);
                match(input, TYPE_MODIFIER, FOLLOW_TYPE_MODIFIER_in_constDeclarationList480);
                match(input, Token.DOWN, null);
                match(input, Public, FOLLOW_Public_in_constDeclarationList482);
                match(input, Static, FOLLOW_Static_in_constDeclarationList484);
                match(input, Final, FOLLOW_Final_in_constDeclarationList486);
                match(input, Token.UP, null);

                pushFollow(FOLLOW_scalarTypesOrUnknown_in_constDeclarationList489);
                scalarTypesOrUnknown8 = scalarTypesOrUnknown();
                state._fsp--;

                match(input, Token.UP, null);

                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:155:24: (identifiers+= constDeclaration )+
                int cnt8 = 0;
                loop8:
                while (true) {
                    int alt8 = 2;
                    int LA8_0 = input.LA(1);
                    if ((LA8_0 == Identifier)) {
                        alt8 = 1;
                    }

                    switch (alt8) {
                        case 1:
                            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:155:24: identifiers+=
                            // constDeclaration
                        {
                            pushFollow(FOLLOW_constDeclaration_in_constDeclarationList506);
                            identifiers = constDeclaration();
                            state._fsp--;

					if (list_identifiers==null) list_identifiers=new ArrayList<Object>();
					list_identifiers.add(identifiers.getTemplate());
					}
					break;

				default :
                    if (cnt8 >= 1) {
                        break loop8;
                    }
                    EarlyExitException eee = new EarlyExitException(8, input);
                    throw eee;
                    }
                    cnt8++;
                }

			match(input, Token.UP, null); 

			// TEMPLATE REWRITE
                // 157:9: -> const(type=$scalarTypesOrUnknown.stidentifiers=$identifiers)
                {
                    retval.st = templateLib.getInstanceOf("const", new STAttrMap().put("type",
                            (scalarTypesOrUnknown8 != null ? ((StringTemplate) scalarTypesOrUnknown8.getTemplate()) :
                                    null)).put("identifiers", list_identifiers));
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:160:1: constDeclaration : ^( Identifier
    // unaryPrimitiveAtom ) -> assign(id=$Identifier.text.substring(0,$Identifier.text.length()-1)
    // value=$unaryPrimitiveAtom.st);
    public final TSPHPTranslatorWalker.constDeclaration_return constDeclaration() throws RecognitionException {
        TSPHPTranslatorWalker.constDeclaration_return retval = new TSPHPTranslatorWalker.constDeclaration_return();
        retval.start = input.LT(1);

        ITSPHPAst Identifier9 = null;
        TreeRuleReturnScope unaryPrimitiveAtom10 = null;

		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:161:5: ( ^( Identifier unaryPrimitiveAtom )
            // -> assign(id=$Identifier.text.substring(0,$Identifier.text.length()-1)value=$unaryPrimitiveAtom.st))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:161:9: ^( Identifier unaryPrimitiveAtom )
            {
                Identifier9 = (ITSPHPAst) match(input, Identifier, FOLLOW_Identifier_in_constDeclaration564);
                match(input, Token.DOWN, null);
                pushFollow(FOLLOW_unaryPrimitiveAtom_in_constDeclaration566);
                unaryPrimitiveAtom10 = unaryPrimitiveAtom();
                state._fsp--;

			match(input, Token.UP, null); 

			// TEMPLATE REWRITE
                // 162:9: -> assign(id=$Identifier.text.substring(0,$Identifier.text.length()-1)
                // value=$unaryPrimitiveAtom.st)
                {
                    retval.st = templateLib.getInstanceOf("assign", new STAttrMap().put("id",
                            (Identifier9 != null ? Identifier9.getText() : null).substring(0,
                                    (Identifier9 != null ? Identifier9.getText() : null).length() - 1)).put("value",
                            (unaryPrimitiveAtom10 != null ? ((StringTemplate) unaryPrimitiveAtom10.getTemplate()) : null)));
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:165:1: unaryPrimitiveAtom : (
    // primitiveAtomWithConstant -> {$primitiveAtomWithConstant.st}| ^( ( UNARY_MINUS | UNARY_PLUS )
    // primitiveAtomWithConstant ) -> unaryPreOperator(operator=unaryexpression=$primitiveAtomWithConstant.st));
    public final TSPHPTranslatorWalker.unaryPrimitiveAtom_return unaryPrimitiveAtom() throws RecognitionException {
        TSPHPTranslatorWalker.unaryPrimitiveAtom_return retval = new TSPHPTranslatorWalker.unaryPrimitiveAtom_return();
        retval.start = input.LT(1);

		TreeRuleReturnScope primitiveAtomWithConstant11 =null;
        TreeRuleReturnScope primitiveAtomWithConstant12 = null;


		    String unary="";

		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:169:5: ( primitiveAtomWithConstant ->
            // {$primitiveAtomWithConstant.st}| ^( ( UNARY_MINUS | UNARY_PLUS ) primitiveAtomWithConstant ) ->
            // unaryPreOperator(operator=unaryexpression=$primitiveAtomWithConstant.st))
            int alt10 = 2;
            int LA10_0 = input.LA(1);
            if (((LA10_0 >= Bool && LA10_0 <= Null) || LA10_0 == CONSTANT)) {
                alt10 = 1;
            } else if (((LA10_0 >= UNARY_MINUS && LA10_0 <= UNARY_PLUS))) {
                alt10 = 2;
            }

			else {
				NoViableAltException nvae =
                        new NoViableAltException("", 10, 0, input);
                throw nvae;
            }

            switch (alt10) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:169:9: primitiveAtomWithConstant
                {
                    pushFollow(FOLLOW_primitiveAtomWithConstant_in_unaryPrimitiveAtom616);
                    primitiveAtomWithConstant11 = primitiveAtomWithConstant();
                    state._fsp--;

					// TEMPLATE REWRITE
                    // 170:9: -> {$primitiveAtomWithConstant.st}
                    {
                        retval.st = (primitiveAtomWithConstant11 != null ? ((StringTemplate)
                                primitiveAtomWithConstant11.getTemplate()) : null);
                    }



					}
					break;
				case 2 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:172:9: ^( ( UNARY_MINUS |
                    // UNARY_PLUS ) primitiveAtomWithConstant )
                {
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:172:13: ( UNARY_MINUS |
                    // UNARY_PLUS )
                    int alt9 = 2;
                    int LA9_0 = input.LA(1);
                    if ((LA9_0 == UNARY_MINUS)) {
                        alt9 = 1;
                    } else if ((LA9_0 == UNARY_PLUS)) {
                        alt9 = 2;
                    }

					else {
						NoViableAltException nvae =
                                new NoViableAltException("", 9, 0, input);
                        throw nvae;
                    }

                    switch (alt9) {
                        case 1:
                            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:172:18: UNARY_MINUS
                        {
                            match(input, UNARY_MINUS, FOLLOW_UNARY_MINUS_in_unaryPrimitiveAtom647);
                            unary = "-";
                        }
                        break;
                        case 2 :
                            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:173:18: UNARY_PLUS
                        {
                            match(input, UNARY_PLUS, FOLLOW_UNARY_PLUS_in_unaryPrimitiveAtom668);
                            unary = "+";
                        }
                        break;

					}

                    match(input, Token.DOWN, null);
                    pushFollow(FOLLOW_primitiveAtomWithConstant_in_unaryPrimitiveAtom686);
                    primitiveAtomWithConstant12 = primitiveAtomWithConstant();
                    state._fsp--;

					match(input, Token.UP, null); 

					// TEMPLATE REWRITE
                    // 176:9: -> unaryPreOperator(operator=unaryexpression=$primitiveAtomWithConstant.st)
                    {
                        retval.st = templateLib.getInstanceOf("unaryPreOperator", new STAttrMap().put("operator",
                                unary).put("expression", (primitiveAtomWithConstant12 != null ? ((StringTemplate)
                                primitiveAtomWithConstant12.getTemplate()) : null)));
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


    public static class typeModifier_return extends TreeRuleReturnScope
    {
        public Set<String> prefixModifiers;
        public Set<String> suffixModifiers;
        public StringTemplate st;

        public Object getTemplate() {
            return st;
        }

        public String toString() {
            return st == null ? null : st.toString();
        }
    }

    ;


    // $ANTLR start "typeModifier"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:204:1: typeModifier returns [Set<String>
    // prefixModifiers, Set<String> suffixModifiers] : ( ^( TYPE_MODIFIER ( 'cast' )? ( '!' )? ( '?' )? ) |
    // TYPE_MODIFIER );
    public final TSPHPTranslatorWalker.typeModifier_return typeModifier() throws RecognitionException {
        TSPHPTranslatorWalker.typeModifier_return retval = new TSPHPTranslatorWalker.typeModifier_return();
        retval.start = input.LT(1);


        retval.prefixModifiers = new HashSet<String>();
        retval.suffixModifiers = new HashSet<String>();

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:209:5: ( ^( TYPE_MODIFIER ( 'cast' )? ( '!'
            // )? ( '?' )? ) | TYPE_MODIFIER )
            int alt14 = 2;
            int LA14_0 = input.LA(1);
            if ((LA14_0 == TYPE_MODIFIER)) {
                int LA14_1 = input.LA(2);
                if ((LA14_1 == DOWN)) {
                    alt14 = 1;
                } else if ((LA14_1 == QuestionMark || LA14_1 == TypeArray || LA14_1 == TYPE_NAME || (LA14_1 >=
                        TypeBool && LA14_1 <= TypeMixed) || (LA14_1 >= TypeResource && LA14_1 <= TypeString))) {
                    alt14 = 2;
                } else {
                    int nvaeMark = input.mark();
                    try {
                        input.consume();
                        NoViableAltException nvae =
                                new NoViableAltException("", 14, 1, input);
                        throw nvae;
                    } finally {
                        input.rewind(nvaeMark);
                    }
                }

            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 14, 0, input);
                throw nvae;
            }

            switch (alt14) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:209:9: ^( TYPE_MODIFIER ( 'cast' )?
                    // ( '!' )? ( '?' )? )
                {
                    match(input, TYPE_MODIFIER, FOLLOW_TYPE_MODIFIER_in_typeModifier769);
                    if (input.LA(1) == Token.DOWN) {
                        match(input, Token.DOWN, null);
                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:210:13: ( 'cast' )?
                        int alt11 = 2;
                        int LA11_0 = input.LA(1);
                        if ((LA11_0 == Cast)) {
                            alt11 = 1;
                        }
                        switch (alt11) {
                            case 1:
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:210:14: 'cast'
                            {
                                match(input, Cast, FOLLOW_Cast_in_typeModifier784);
                                retval.prefixModifiers.add("cast");
                            }
                            break;

                        }

                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:211:13: ( '!' )?
                        int alt12 = 2;
                        int LA12_0 = input.LA(1);
                        if ((LA12_0 == LogicNot)) {
                            alt12 = 1;
                        }
                        switch (alt12) {
                            case 1:
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:211:14: '!'
                            {
                                match(input, LogicNot, FOLLOW_LogicNot_in_typeModifier803);
                                retval.suffixModifiers.add("!");
                            }
                            break;

                        }

                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:212:13: ( '?' )?
                        int alt13 = 2;
                        int LA13_0 = input.LA(1);
                        if ((LA13_0 == QuestionMark)) {
                            alt13 = 1;
                        }
                        switch (alt13) {
                            case 1:
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:212:14: '?'
                            {
                                match(input, QuestionMark, FOLLOW_QuestionMark_in_typeModifier826);
                                retval.suffixModifiers.add("?");
                            }
                            break;

                        }

                        match(input, Token.UP, null);
                    }

                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:214:9: TYPE_MODIFIER
                {
                    match(input, TYPE_MODIFIER, FOLLOW_TYPE_MODIFIER_in_typeModifier853);
                }
                break;

            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "typeModifier"


    public static class returnTypesOrUnknown_return extends TreeRuleReturnScope
    {
        public StringTemplate st;

        public Object getTemplate() {
            return st;
        }

        public String toString() {
            return st == null ? null : st.toString();
        }
    }

    ;


    // $ANTLR start "returnTypesOrUnknown"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:232:1: returnTypesOrUnknown : allTypesOrUnknown ->
    // {$allTypesOrUnknown.st};
    public final TSPHPTranslatorWalker.returnTypesOrUnknown_return returnTypesOrUnknown() throws RecognitionException {
        TSPHPTranslatorWalker.returnTypesOrUnknown_return retval = new TSPHPTranslatorWalker
                .returnTypesOrUnknown_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope allTypesOrUnknown13 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:233:5: ( allTypesOrUnknown ->
            // {$allTypesOrUnknown.st})
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:233:9: allTypesOrUnknown
            {
                pushFollow(FOLLOW_allTypesOrUnknown_in_returnTypesOrUnknown875);
                allTypesOrUnknown13 = allTypesOrUnknown();
                state._fsp--;

                // TEMPLATE REWRITE
                // 233:27: -> {$allTypesOrUnknown.st}
                {
                    retval.st = (allTypesOrUnknown13 != null ? ((StringTemplate) allTypesOrUnknown13.getTemplate()) :
                            null);
                }


            }

        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "returnTypesOrUnknown"


    public static class allTypesOrUnknown_return extends TreeRuleReturnScope
    {
        public StringTemplate st;

        public Object getTemplate() {
            return st;
        }

        public String toString() {
            return st == null ? null : st.toString();
        }
    }

    ;


    // $ANTLR start "allTypesOrUnknown"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:238:1: allTypesOrUnknown : ( allTypes -> {$allTypes
    // .st}| '?' -> {%{\"?\"}});
    public final TSPHPTranslatorWalker.allTypesOrUnknown_return allTypesOrUnknown() throws RecognitionException {
        TSPHPTranslatorWalker.allTypesOrUnknown_return retval = new TSPHPTranslatorWalker.allTypesOrUnknown_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope allTypes14 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:239:5: ( allTypes -> {$allTypes.st}| '?' ->
            // {%{\"?\"}})
            int alt15 = 2;
            int LA15_0 = input.LA(1);
            if ((LA15_0 == TypeArray || LA15_0 == TYPE_NAME || (LA15_0 >= TypeBool && LA15_0 <= TypeMixed) || (LA15_0
                    >= TypeResource && LA15_0 <= TypeString))) {
                alt15 = 1;
            } else if ((LA15_0 == QuestionMark)) {
                alt15 = 2;
            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 15, 0, input);
                throw nvae;
            }

            switch (alt15) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:239:9: allTypes
                {
                    pushFollow(FOLLOW_allTypes_in_allTypesOrUnknown912);
                    allTypes14 = allTypes();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 239:18: -> {$allTypes.st}
                    {
                        retval.st = (allTypes14 != null ? ((StringTemplate) allTypes14.getTemplate()) : null);
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:240:9: '?'
                {
                    match(input, QuestionMark, FOLLOW_QuestionMark_in_allTypesOrUnknown926);
                    // TEMPLATE REWRITE
                    // 240:18: -> {%{\"?\"}}
                    {
                        retval.st = new StringTemplate(templateLib, "?");
                    }


                }
                break;

            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "allTypesOrUnknown"


    public static class allTypes_return extends TreeRuleReturnScope
    {
        public StringTemplate st;

        public Object getTemplate() {
            return st;
        }

        public String toString() {
            return st == null ? null : st.toString();
        }
    }

    ;


    // $ANTLR start "allTypes"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:243:1: allTypes : ( primitiveTypes ->
    // {$primitiveTypes.st}| TYPE_NAME -> {%{$TYPE_NAME.text}});
    public final TSPHPTranslatorWalker.allTypes_return allTypes() throws RecognitionException {
        TSPHPTranslatorWalker.allTypes_return retval = new TSPHPTranslatorWalker.allTypes_return();
        retval.start = input.LT(1);

        ITSPHPAst TYPE_NAME16 = null;
        TreeRuleReturnScope primitiveTypes15 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:244:5: ( primitiveTypes -> {$primitiveTypes
            // .st}| TYPE_NAME -> {%{$TYPE_NAME.text}})
            int alt16 = 2;
            int LA16_0 = input.LA(1);
            if ((LA16_0 == TypeArray || (LA16_0 >= TypeBool && LA16_0 <= TypeMixed) || (LA16_0 >= TypeResource &&
                    LA16_0 <= TypeString))) {
                alt16 = 1;
            } else if ((LA16_0 == TYPE_NAME)) {
                alt16 = 2;
            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 16, 0, input);
                throw nvae;
            }

            switch (alt16) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:244:9: primitiveTypes
                {
                    pushFollow(FOLLOW_primitiveTypes_in_allTypes954);
                    primitiveTypes15 = primitiveTypes();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 244:24: -> {$primitiveTypes.st}
                    {
                        retval.st = (primitiveTypes15 != null ? ((StringTemplate) primitiveTypes15.getTemplate()) :
                                null);
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:245:9: TYPE_NAME
                {
                    TYPE_NAME16 = (ITSPHPAst) match(input, TYPE_NAME, FOLLOW_TYPE_NAME_in_allTypes969);
                    // TEMPLATE REWRITE
                    // 245:24: -> {%{$TYPE_NAME.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TYPE_NAME16 != null ? TYPE_NAME16.getText() :
                                null));
                    }


                }
                break;

            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "allTypes"


    public static class primitiveTypes_return extends TreeRuleReturnScope
    {
        public StringTemplate st;

        public Object getTemplate() {
            return st;
        }

        public String toString() {
            return st == null ? null : st.toString();
        }
    }

    ;


    // $ANTLR start "primitiveTypes"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:248:1: primitiveTypes : ( scalarTypes ->
    // {$scalarTypes.st}| TypeArray -> {%{$TypeArray.text}}| TypeResource -> {%{$TypeResource.text}}| TypeMixed ->
    // {%{$TypeMixed.text}});
    public final TSPHPTranslatorWalker.primitiveTypes_return primitiveTypes() throws RecognitionException {
        TSPHPTranslatorWalker.primitiveTypes_return retval = new TSPHPTranslatorWalker.primitiveTypes_return();
        retval.start = input.LT(1);

        ITSPHPAst TypeArray18 = null;
        ITSPHPAst TypeResource19 = null;
        ITSPHPAst TypeMixed20 = null;
        TreeRuleReturnScope scalarTypes17 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:249:5: ( scalarTypes -> {$scalarTypes.st}|
            // TypeArray -> {%{$TypeArray.text}}| TypeResource -> {%{$TypeResource.text}}| TypeMixed -> {%{$TypeMixed
            // .text}})
            int alt17 = 4;
            switch (input.LA(1)) {
                case TypeBool:
                case TypeFloat:
                case TypeInt:
                case TypeString: {
                    alt17 = 1;
                }
                break;
                case TypeArray: {
                    alt17 = 2;
                }
                break;
                case TypeResource: {
                    alt17 = 3;
                }
                break;
                case TypeMixed: {
                    alt17 = 4;
                }
                break;
                default:
                    NoViableAltException nvae =
                            new NoViableAltException("", 17, 0, input);
                    throw nvae;
            }
            switch (alt17) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:249:9: scalarTypes
                {
                    pushFollow(FOLLOW_scalarTypes_in_primitiveTypes1001);
                    scalarTypes17 = scalarTypes();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 249:22: -> {$scalarTypes.st}
                    {
                        retval.st = (scalarTypes17 != null ? ((StringTemplate) scalarTypes17.getTemplate()) : null);
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:250:9: TypeArray
                {
                    TypeArray18 = (ITSPHPAst) match(input, TypeArray, FOLLOW_TypeArray_in_primitiveTypes1016);
                    // TEMPLATE REWRITE
                    // 250:22: -> {%{$TypeArray.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeArray18 != null ? TypeArray18.getText() :
                                null));
                    }


                }
                break;
                case 3:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:251:9: TypeResource
                {
                    TypeResource19 = (ITSPHPAst) match(input, TypeResource, FOLLOW_TypeResource_in_primitiveTypes1033);
                    // TEMPLATE REWRITE
                    // 251:22: -> {%{$TypeResource.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeResource19 != null ? TypeResource19.getText
                                () : null));
                    }


                }
                break;
                case 4:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:252:9: TypeMixed
                {
                    TypeMixed20 = (ITSPHPAst) match(input, TypeMixed, FOLLOW_TypeMixed_in_primitiveTypes1047);
                    // TEMPLATE REWRITE
                    // 252:22: -> {%{$TypeMixed.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeMixed20 != null ? TypeMixed20.getText() :
                                null));
                    }


                }
                break;

            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "primitiveTypes"


	public static class scalarTypesOrUnknown_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "scalarTypesOrUnknown"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:261:1: scalarTypesOrUnknown : ( scalarTypes ->
    // {$scalarTypes.st}| '?' -> {%{\"?\"}});
    public final TSPHPTranslatorWalker.scalarTypesOrUnknown_return scalarTypesOrUnknown() throws RecognitionException {
        TSPHPTranslatorWalker.scalarTypesOrUnknown_return retval = new TSPHPTranslatorWalker
                .scalarTypesOrUnknown_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope scalarTypes21 = null;

		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:262:5: ( scalarTypes -> {$scalarTypes.st}|
            // '?' -> {%{\"?\"}})
            int alt18 = 2;
            int LA18_0 = input.LA(1);
            if (((LA18_0 >= TypeBool && LA18_0 <= TypeInt) || LA18_0 == TypeString)) {
                alt18 = 1;
            } else if ((LA18_0 == QuestionMark)) {
                alt18 = 2;
            }

			else {
				NoViableAltException nvae =
                        new NoViableAltException("", 18, 0, input);
                throw nvae;
            }

            switch (alt18) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:262:9: scalarTypes
                {
                    pushFollow(FOLLOW_scalarTypes_in_scalarTypesOrUnknown1074);
                    scalarTypes21 = scalarTypes();
                    state._fsp--;

					// TEMPLATE REWRITE
                    // 262:21: -> {$scalarTypes.st}
                    {
                        retval.st = (scalarTypes21 != null ? ((StringTemplate) scalarTypes21.getTemplate()) : null);
                    }



					}
					break;
				case 2 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:263:9: '?'
                {
                    match(input, QuestionMark, FOLLOW_QuestionMark_in_scalarTypesOrUnknown1088);
                    // TEMPLATE REWRITE
                    // 263:21: -> {%{\"?\"}}
                    {
                        retval.st = new StringTemplate(templateLib, "?");
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:266:1: scalarTypes : ( TypeBool -> {%{$TypeBool
    // .text}}| TypeInt -> {%{$TypeInt.text}}| TypeFloat -> {%{$TypeFloat.text}}| TypeString -> {%{$TypeString.text}});
    public final TSPHPTranslatorWalker.scalarTypes_return scalarTypes() throws RecognitionException {
        TSPHPTranslatorWalker.scalarTypes_return retval = new TSPHPTranslatorWalker.scalarTypes_return();
        retval.start = input.LT(1);

        ITSPHPAst TypeBool22 = null;
        ITSPHPAst TypeInt23 = null;
        ITSPHPAst TypeFloat24 = null;
        ITSPHPAst TypeString25 = null;

		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:267:5: ( TypeBool -> {%{$TypeBool.text}}|
            // TypeInt -> {%{$TypeInt.text}}| TypeFloat -> {%{$TypeFloat.text}}| TypeString -> {%{$TypeString.text}})
            int alt19 = 4;
            switch (input.LA(1)) {
                case TypeBool: {
                    alt19 = 1;
                }
                break;
                case TypeInt: {
                    alt19 = 2;
                }
                break;
                case TypeFloat: {
                    alt19 = 3;
                }
                break;
                case TypeString: {
                    alt19 = 4;
                }
                break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 19, 0, input);
                    throw nvae;
            }
            switch (alt19) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:267:9: TypeBool
                {
                    TypeBool22 = (ITSPHPAst) match(input, TypeBool, FOLLOW_TypeBool_in_scalarTypes1119);
                    // TEMPLATE REWRITE
                    // 267:21: -> {%{$TypeBool.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeBool22 != null ? TypeBool22.getText() : null));
                    }



					}
					break;
				case 2 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:268:9: TypeInt
                {
                    TypeInt23 = (ITSPHPAst) match(input, TypeInt, FOLLOW_TypeInt_in_scalarTypes1136);
                    // TEMPLATE REWRITE
                    // 268:21: -> {%{$TypeInt.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeInt23 != null ? TypeInt23.getText() : null));
                    }



					}
					break;
				case 3 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:269:9: TypeFloat
                {
                    TypeFloat24 = (ITSPHPAst) match(input, TypeFloat, FOLLOW_TypeFloat_in_scalarTypes1154);
                    // TEMPLATE REWRITE
                    // 269:21: -> {%{$TypeFloat.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeFloat24 != null ? TypeFloat24.getText() :
                                null));
                    }



					}
					break;
				case 4 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:270:9: TypeString
                {
                    TypeString25 = (ITSPHPAst) match(input, TypeString, FOLLOW_TypeString_in_scalarTypes1170);
                    // TEMPLATE REWRITE
                    // 270:21: -> {%{$TypeString.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeString25 != null ? TypeString25.getText() :
                                null));
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


    public static class formalParameters_return extends TreeRuleReturnScope
    {
        public StringTemplate st;

        public Object getTemplate() {
            return st;
        }

        public String toString() {
            return st == null ? null : st.toString();
        }
    }

    ;


    // $ANTLR start "formalParameters"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:379:1: formalParameters : ( ^( PARAMETER_LIST
    // (param= paramDeclaration )+ ) -> parameterList(declarations=declarations)| PARAMETER_LIST -> {null});
    public final TSPHPTranslatorWalker.formalParameters_return formalParameters() throws RecognitionException {
        TSPHPTranslatorWalker.formalParameters_return retval = new TSPHPTranslatorWalker.formalParameters_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope param = null;


        List<ParameterDto> parameterDtos = new ArrayList<>();
        List<StringTemplate> declarations = new ArrayList<>();

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:384:5: ( ^( PARAMETER_LIST (param=
            // paramDeclaration )+ ) -> parameterList(declarations=declarations)| PARAMETER_LIST -> {null})
            int alt21 = 2;
            int LA21_0 = input.LA(1);
            if ((LA21_0 == PARAMETER_LIST)) {
                int LA21_1 = input.LA(2);
                if ((LA21_1 == DOWN)) {
                    alt21 = 1;
                } else if ((LA21_1 == BLOCK)) {
                    alt21 = 2;
                } else {
                    int nvaeMark = input.mark();
                    try {
                        input.consume();
                        NoViableAltException nvae =
                                new NoViableAltException("", 21, 1, input);
                        throw nvae;
                    } finally {
                        input.rewind(nvaeMark);
                    }
                }

            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 21, 0, input);
                throw nvae;
            }

            switch (alt21) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:384:9: ^( PARAMETER_LIST (param=
                    // paramDeclaration )+ )
                {
                    match(input, PARAMETER_LIST, FOLLOW_PARAMETER_LIST_in_formalParameters1207);
                    match(input, Token.DOWN, null);
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:384:26: (param= paramDeclaration )+
                    int cnt20 = 0;
                    loop20:
                    while (true) {
                        int alt20 = 2;
                        int LA20_0 = input.LA(1);
                        if ((LA20_0 == PARAMETER_DECLARATION)) {
                            alt20 = 1;
                        }

                        switch (alt20) {
                            case 1:
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:384:27: param=
                                // paramDeclaration
                            {
                                pushFollow(FOLLOW_paramDeclaration_in_formalParameters1212);
                                param = paramDeclaration();
                                state._fsp--;

                                parameterDtos.add((param != null ? ((TSPHPTranslatorWalker.paramDeclaration_return)
                                        param).parameterDto : null));
                            }
                            break;

                            default:
                                if (cnt20 >= 1) {
                                    break loop20;
                                }
                                EarlyExitException eee = new EarlyExitException(20, input);
                                throw eee;
                        }
                        cnt20++;
                    }

                    match(input, Token.UP, null);


                    boolean canBeDefaultValue = true;
                    for (int i = parameterDtos.size() - 1; i >= 0; --i) {
                        ParameterDto dto = parameterDtos.get(i);
                        String defaultValue = canBeDefaultValue ? dto.defaultValue : null;
                        canBeDefaultValue &= defaultValue != null;
                        if (!canBeDefaultValue && !dto.suffixModifiers.contains("?") && dto.defaultValue != null &&
                                dto.defaultValue.toLowerCase().equals("null")) {
                            dto.suffixModifiers.add("?");
                        }
                        StringTemplate type = templateLib.getInstanceOf("type",
                                new STAttrMap().put("prefixModifiers", dto.prefixModifiers).put("type",
                                        dto.type).put("suffixModifiers", dto.suffixModifiers));
                        declarations.add(0, templateLib.getInstanceOf("parameter", new STAttrMap().put("type",
                                type).put("variableId", dto.variableId).put("defaultValue", defaultValue)));
                    }

                    // TEMPLATE REWRITE
                    // 407:6: -> parameterList(declarations=declarations)
                    {
                        retval.st = templateLib.getInstanceOf("parameterList", new STAttrMap().put("declarations",
                                declarations));
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:408:9: PARAMETER_LIST
                {
                    match(input, PARAMETER_LIST, FOLLOW_PARAMETER_LIST_in_formalParameters1249);
                    // TEMPLATE REWRITE
                    // 408:24: -> {null}
                    {
                        retval.st = null;
                    }


                }
                break;

            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "formalParameters"


    public static class block_return extends TreeRuleReturnScope
    {
        public List<Object> instructions;
        public StringTemplate st;

        public Object getTemplate() {
            return st;
        }

        public String toString() {
            return st == null ? null : st.toString();
        }
    }

    ;


    // $ANTLR start "block"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:412:1: block returns [List<Object> instructions] :
    // ( ^( BLOCK (instr+= instruction )+ ) | BLOCK );
    public final TSPHPTranslatorWalker.block_return block() throws RecognitionException {
        TSPHPTranslatorWalker.block_return retval = new TSPHPTranslatorWalker.block_return();
        retval.start = input.LT(1);

        List<Object> list_instr = null;
        RuleReturnScope instr = null;
        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:413:5: ( ^( BLOCK (instr+= instruction )+ )
            // | BLOCK )
            int alt23 = 2;
            int LA23_0 = input.LA(1);
            if ((LA23_0 == BLOCK)) {
                int LA23_1 = input.LA(2);
                if ((LA23_1 == DOWN)) {
                    alt23 = 1;
                } else if ((LA23_1 == UP)) {
                    alt23 = 2;
                } else {
                    int nvaeMark = input.mark();
                    try {
                        input.consume();
                        NoViableAltException nvae =
                                new NoViableAltException("", 23, 1, input);
                        throw nvae;
                    } finally {
                        input.rewind(nvaeMark);
                    }
                }

            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 23, 0, input);
                throw nvae;
            }

            switch (alt23) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:413:9: ^( BLOCK (instr+=
                    // instruction )+ )
                {
                    match(input, BLOCK, FOLLOW_BLOCK_in_block1277);
                    match(input, Token.DOWN, null);
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:413:22: (instr+= instruction )+
                    int cnt22 = 0;
                    loop22:
                    while (true) {
                        int alt22 = 2;
                        int LA22_0 = input.LA(1);
                        if ((LA22_0 == EXPRESSION)) {
                            alt22 = 1;
                        }

                        switch (alt22) {
                            case 1:
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:413:22: instr+=
                                // instruction
                            {
                                pushFollow(FOLLOW_instruction_in_block1281);
                                instr = instruction();
                                state._fsp--;

                                if (list_instr == null) {
                                    list_instr = new ArrayList<Object>();
                                }
                                list_instr.add(instr.getTemplate());
                            }
                            break;

                            default:
                                if (cnt22 >= 1) {
                                    break loop22;
                                }
                                EarlyExitException eee = new EarlyExitException(22, input);
                                throw eee;
                        }
                        cnt22++;
                    }

                    match(input, Token.UP, null);

                    retval.instructions = list_instr;
                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:414:9: BLOCK
                {
                    match(input, BLOCK, FOLLOW_BLOCK_in_block1295);
                }
                break;

            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "block"


    public static class paramDeclaration_return extends TreeRuleReturnScope
    {
        public ParameterDto parameterDto;
        public StringTemplate st;

        public Object getTemplate() {
            return st;
        }

        public String toString() {
            return st == null ? null : st.toString();
        }
    }

    ;


    // $ANTLR start "paramDeclaration"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:474:1: paramDeclaration returns [ParameterDto
    // parameterDto] : ^( PARAMETER_DECLARATION ^( TYPE typeModifier allTypesOrUnknown ) (varId= VariableId | ^
    // (varId= VariableId defaultValue= unaryPrimitiveAtom ) ) ) ;
    public final TSPHPTranslatorWalker.paramDeclaration_return paramDeclaration() throws RecognitionException {
        TSPHPTranslatorWalker.paramDeclaration_return retval = new TSPHPTranslatorWalker.paramDeclaration_return();
        retval.start = input.LT(1);

        ITSPHPAst varId = null;
        TreeRuleReturnScope defaultValue = null;
        TreeRuleReturnScope typeModifier26 = null;
        TreeRuleReturnScope allTypesOrUnknown27 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:475:5: ( ^( PARAMETER_DECLARATION ^( TYPE
            // typeModifier allTypesOrUnknown ) (varId= VariableId | ^(varId= VariableId defaultValue=
            // unaryPrimitiveAtom ) ) ) )
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:475:9: ^( PARAMETER_DECLARATION ^( TYPE
            // typeModifier allTypesOrUnknown ) (varId= VariableId | ^(varId= VariableId defaultValue=
            // unaryPrimitiveAtom ) ) )
            {
                match(input, PARAMETER_DECLARATION, FOLLOW_PARAMETER_DECLARATION_in_paramDeclaration1322);
                match(input, Token.DOWN, null);
                match(input, TYPE, FOLLOW_TYPE_in_paramDeclaration1337);
                match(input, Token.DOWN, null);
                pushFollow(FOLLOW_typeModifier_in_paramDeclaration1339);
                typeModifier26 = typeModifier();
                state._fsp--;

                pushFollow(FOLLOW_allTypesOrUnknown_in_paramDeclaration1341);
                allTypesOrUnknown27 = allTypesOrUnknown();
                state._fsp--;

                match(input, Token.UP, null);

                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:477:13: (varId= VariableId | ^(varId=
                // VariableId defaultValue= unaryPrimitiveAtom ) )
                int alt24 = 2;
                int LA24_0 = input.LA(1);
                if ((LA24_0 == VariableId)) {
                    int LA24_1 = input.LA(2);
                    if ((LA24_1 == DOWN)) {
                        alt24 = 2;
                    } else if ((LA24_1 == UP)) {
                        alt24 = 1;
                    } else {
                        int nvaeMark = input.mark();
                        try {
                            input.consume();
                            NoViableAltException nvae =
                                    new NoViableAltException("", 24, 1, input);
                            throw nvae;
                        } finally {
                            input.rewind(nvaeMark);
                        }
                    }

                } else {
                    NoViableAltException nvae =
                            new NoViableAltException("", 24, 0, input);
                    throw nvae;
                }

                switch (alt24) {
                    case 1:
                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:477:17: varId= VariableId
                    {
                        varId = (ITSPHPAst) match(input, VariableId, FOLLOW_VariableId_in_paramDeclaration1362);
                    }
                    break;
                    case 2:
                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:478:17: ^(varId= VariableId
                        // defaultValue= unaryPrimitiveAtom )
                    {
                        varId = (ITSPHPAst) match(input, VariableId, FOLLOW_VariableId_in_paramDeclaration1383);
                        match(input, Token.DOWN, null);
                        pushFollow(FOLLOW_unaryPrimitiveAtom_in_paramDeclaration1387);
                        defaultValue = unaryPrimitiveAtom();
                        state._fsp--;

                        match(input, Token.UP, null);

                    }
                    break;

                }

                match(input, Token.UP, null);


                retval.parameterDto = new ParameterDto(
                        (typeModifier26 != null ? ((TSPHPTranslatorWalker.typeModifier_return) typeModifier26)
                                .prefixModifiers : null),
                        (allTypesOrUnknown27 != null ? ((StringTemplate) allTypesOrUnknown27.getTemplate()) : null),
                        (typeModifier26 != null ? ((TSPHPTranslatorWalker.typeModifier_return) typeModifier26)
                                .suffixModifiers : null),
                        (varId != null ? varId.getText() : null),
                        (defaultValue != null ? (input.getTokenStream().toString(input.getTreeAdaptor()
                                .getTokenStartIndex(defaultValue.start), input.getTreeAdaptor().getTokenStopIndex
                                (defaultValue.start))) : null));

            }

        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "paramDeclaration"


    public static class functionDeclaration_return extends TreeRuleReturnScope
    {
        public StringTemplate st;

        public Object getTemplate() {
            return st;
        }

        public String toString() {
            return st == null ? null : st.toString();
        }
    }

    ;


    // $ANTLR start "functionDeclaration"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:491:1: functionDeclaration : ^( 'function'
    // FUNCTION_MODIFIER ^( TYPE typeModifier returnTypesOrUnknown ) Identifier formalParameters block ) -> method
    // (modifier=nullreturnType=%type(\r\n prefixModifiers={$typeModifier.prefixModifiers},\r\n
    // type={$returnTypesOrUnknown.st},\r\n suffixModifiers={$typeModifier.suffixModifiers}\r\n )
    // identifier=getMethodName($Identifier.text)params=$formalParameters.stbody=$block.instructions);
    public final TSPHPTranslatorWalker.functionDeclaration_return functionDeclaration() throws RecognitionException {
        TSPHPTranslatorWalker.functionDeclaration_return retval = new TSPHPTranslatorWalker
                .functionDeclaration_return();
        retval.start = input.LT(1);

        ITSPHPAst Identifier30 = null;
        TreeRuleReturnScope typeModifier28 = null;
        TreeRuleReturnScope returnTypesOrUnknown29 = null;
        TreeRuleReturnScope formalParameters31 = null;
        TreeRuleReturnScope block32 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:492:5: ( ^( 'function' FUNCTION_MODIFIER ^(
            // TYPE typeModifier returnTypesOrUnknown ) Identifier formalParameters block ) -> method
            // (modifier=nullreturnType=%type(\r\n prefixModifiers={$typeModifier.prefixModifiers},
            // \r\n type={$returnTypesOrUnknown.st},\r\n suffixModifiers={$typeModifier.suffixModifiers}\r\n )
            // identifier=getMethodName($Identifier.text)params=$formalParameters.stbody=$block.instructions))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:492:9: ^( 'function' FUNCTION_MODIFIER ^(
            // TYPE typeModifier returnTypesOrUnknown ) Identifier formalParameters block )
            {
                match(input, Function, FOLLOW_Function_in_functionDeclaration1442);
                match(input, Token.DOWN, null);
                match(input, FUNCTION_MODIFIER, FOLLOW_FUNCTION_MODIFIER_in_functionDeclaration1456);
                match(input, TYPE, FOLLOW_TYPE_in_functionDeclaration1471);
                match(input, Token.DOWN, null);
                pushFollow(FOLLOW_typeModifier_in_functionDeclaration1473);
                typeModifier28 = typeModifier();
                state._fsp--;

                pushFollow(FOLLOW_returnTypesOrUnknown_in_functionDeclaration1475);
                returnTypesOrUnknown29 = returnTypesOrUnknown();
                state._fsp--;

                match(input, Token.UP, null);

                Identifier30 = (ITSPHPAst) match(input, Identifier, FOLLOW_Identifier_in_functionDeclaration1490);
                pushFollow(FOLLOW_formalParameters_in_functionDeclaration1504);
                formalParameters31 = formalParameters();
                state._fsp--;

                pushFollow(FOLLOW_block_in_functionDeclaration1518);
                block32 = block();
                state._fsp--;

                match(input, Token.UP, null);

                // TEMPLATE REWRITE
                // 499:9: -> method(modifier=nullreturnType=%type(\r\n prefixModifiers={$typeModifier
                // .prefixModifiers},\r\n type={$returnTypesOrUnknown.st},\r\n suffixModifiers={$typeModifier
                // .suffixModifiers}\r\n )identifier=getMethodName($Identifier.text)params=$formalParameters
                // .stbody=$block.instructions)
                {
                    retval.st = templateLib.getInstanceOf("method", new STAttrMap().put("modifier",
                            null).put("returnType", templateLib.getInstanceOf("type",
                            new STAttrMap().put("prefixModifiers", (typeModifier28 != null ? ((TSPHPTranslatorWalker
                                    .typeModifier_return) typeModifier28).prefixModifiers : null)).put("type",
                                    (returnTypesOrUnknown29 != null ? ((StringTemplate) returnTypesOrUnknown29
                                            .getTemplate()) : null)).put("suffixModifiers",
                                    (typeModifier28 != null ? ((TSPHPTranslatorWalker.typeModifier_return)
                                            typeModifier28).suffixModifiers : null)))).put("identifier",
                            getMethodName((Identifier30 != null ? Identifier30.getText() : null))).put("params",
                            (formalParameters31 != null ? ((StringTemplate) formalParameters31.getTemplate()) : null)
                    ).put("body", (block32 != null ? ((TSPHPTranslatorWalker.block_return) block32).instructions :
                            null)));
                }


            }

        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "functionDeclaration"


    public static class instruction_return extends TreeRuleReturnScope
    {
        public StringTemplate st;

        public Object getTemplate() {
            return st;
        }

        public String toString() {
            return st == null ? null : st.toString();
        }
    }

    ;


    // $ANTLR start "instruction"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:512:1: instruction : ^( EXPRESSION ( expression )?
    // ) -> expression(expression=$expression.st);
    public final TSPHPTranslatorWalker.instruction_return instruction() throws RecognitionException {
        TSPHPTranslatorWalker.instruction_return retval = new TSPHPTranslatorWalker.instruction_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope expression33 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:514:5: ( ^( EXPRESSION ( expression )? ) ->
            // expression(expression=$expression.st))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:523:8: ^( EXPRESSION ( expression )? )
            {
                match(input, EXPRESSION, FOLLOW_EXPRESSION_in_instruction1720);
                if (input.LA(1) == Token.DOWN) {
                    match(input, Token.DOWN, null);
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:523:21: ( expression )?
                    int alt25 = 2;
                    int LA25_0 = input.LA(1);
                    if (((LA25_0 >= Bool && LA25_0 <= Null) || LA25_0 == CONSTANT)) {
                        alt25 = 1;
                    }
                    switch (alt25) {
                        case 1:
                            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:523:21: expression
                        {
                            pushFollow(FOLLOW_expression_in_instruction1722);
                            expression33 = expression();
                            state._fsp--;

                        }
                        break;

                    }

                    match(input, Token.UP, null);
                }

                // TEMPLATE REWRITE
                // 523:40: -> expression(expression=$expression.st)
                {
                    retval.st = templateLib.getInstanceOf("expression", new STAttrMap().put("expression",
                            (expression33 != null ? ((StringTemplate) expression33.getTemplate()) : null)));
                }


            }

        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "instruction"


	public static class expression_return extends TreeRuleReturnScope {
		public StringTemplate st;
		public Object getTemplate() { return st; }
		public String toString() { return st==null?null:st.toString(); }
	};


	// $ANTLR start "expression"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:624:1: expression : atom -> {$atom.st};
    public final TSPHPTranslatorWalker.expression_return expression() throws RecognitionException {
        TSPHPTranslatorWalker.expression_return retval = new TSPHPTranslatorWalker.expression_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope atom34 = null;

		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:625:5: ( atom -> {$atom.st})
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:625:9: atom
            {
                pushFollow(FOLLOW_atom_in_expression1803);
                atom34 = atom();
                state._fsp--;

			// TEMPLATE REWRITE
                // 625:33: -> {$atom.st}
                {
                    retval.st = (atom34 != null ? ((StringTemplate) atom34.getTemplate()) : null);
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:639:1: atom : primitiveAtomWithConstant ->
    // {$primitiveAtomWithConstant.st};
    public final TSPHPTranslatorWalker.atom_return atom() throws RecognitionException {
        TSPHPTranslatorWalker.atom_return retval = new TSPHPTranslatorWalker.atom_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope primitiveAtomWithConstant35 = null;

		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:640:5: ( primitiveAtomWithConstant ->
            // {$primitiveAtomWithConstant.st})
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:640:9: primitiveAtomWithConstant
            {
                pushFollow(FOLLOW_primitiveAtomWithConstant_in_atom1858);
                primitiveAtomWithConstant35 = primitiveAtomWithConstant();
                state._fsp--;

			// TEMPLATE REWRITE
                // 640:37: -> {$primitiveAtomWithConstant.st}
                {
                    retval.st = (primitiveAtomWithConstant35 != null ? ((StringTemplate) primitiveAtomWithConstant35
                            .getTemplate()) : null);
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:649:1: primitiveAtomWithConstant : ( Bool ->
    // {%{$Bool.text}}| Int -> {%{$Int.text}}| Float -> {%{$Float.text}}| String -> {%{$String.text}}| Null ->
    // {%{$Null.text}}| ^( TypeArray (keyValuePairs+= arrayKeyValue )* ) -> array(content=$keyValuePairs)| CONSTANT
    // -> {%{$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)}});
    public final TSPHPTranslatorWalker.primitiveAtomWithConstant_return primitiveAtomWithConstant() throws
            RecognitionException {
        TSPHPTranslatorWalker.primitiveAtomWithConstant_return retval = new TSPHPTranslatorWalker
                .primitiveAtomWithConstant_return();
        retval.start = input.LT(1);

        ITSPHPAst Bool36 = null;
        ITSPHPAst Int37 = null;
        ITSPHPAst Float38 = null;
        ITSPHPAst String39 = null;
        ITSPHPAst Null40 = null;
        ITSPHPAst CONSTANT41 = null;
        List<Object> list_keyValuePairs = null;
        RuleReturnScope keyValuePairs = null;
        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:650:5: ( Bool -> {%{$Bool.text}}| Int ->
            // {%{$Int.text}}| Float -> {%{$Float.text}}| String -> {%{$String.text}}| Null -> {%{$Null.text}}| ^(
            // TypeArray (keyValuePairs+= arrayKeyValue )* ) -> array(content=$keyValuePairs)| CONSTANT ->
            // {%{$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)}})
            int alt27 = 7;
            switch (input.LA(1)) {
                case Bool: {
                    alt27 = 1;
                }
                break;
                case Int: {
                    alt27 = 2;
                }
                break;
                case Float: {
                    alt27 = 3;
                }
                break;
                case String: {
                    alt27 = 4;
                }
                break;
                case Null: {
                    alt27 = 5;
                }
                break;
                case TypeArray: {
                    alt27 = 6;
                }
                break;
                case CONSTANT: {
                    alt27 = 7;
                }
                break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 27, 0, input);
                    throw nvae;
            }
            switch (alt27) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:650:9: Bool
                {
                    Bool36 = (ITSPHPAst) match(input, Bool, FOLLOW_Bool_in_primitiveAtomWithConstant1906);
                    // TEMPLATE REWRITE
                    // 650:53: -> {%{$Bool.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (Bool36 != null ? Bool36.getText() : null));
                    }



					}
					break;
				case 2 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:651:9: Int
                {
                    Int37 = (ITSPHPAst) match(input, Int, FOLLOW_Int_in_primitiveAtomWithConstant1959);
                    // TEMPLATE REWRITE
                    // 651:53: -> {%{$Int.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (Int37 != null ? Int37.getText() : null));
                    }



					}
					break;
				case 3 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:652:9: Float
                {
                    Float38 = (ITSPHPAst) match(input, Float, FOLLOW_Float_in_primitiveAtomWithConstant2013);
                    // TEMPLATE REWRITE
                    // 652:53: -> {%{$Float.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (Float38 != null ? Float38.getText() : null));
                    }



					}
					break;
				case 4 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:653:9: String
                {
                    String39 = (ITSPHPAst) match(input, String, FOLLOW_String_in_primitiveAtomWithConstant2065);
                    // TEMPLATE REWRITE
                    // 653:53: -> {%{$String.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (String39 != null ? String39.getText() : null));
                    }



					}
					break;
				case 5 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:654:9: Null
                {
                    Null40 = (ITSPHPAst) match(input, Null, FOLLOW_Null_in_primitiveAtomWithConstant2116);
                    // TEMPLATE REWRITE
                    // 654:53: -> {%{$Null.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (Null40 != null ? Null40.getText() : null));
                    }



					}
					break;
				case 6 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:655:9: ^( TypeArray
                    // (keyValuePairs+= arrayKeyValue )* )
                {
                    match(input, TypeArray, FOLLOW_TypeArray_in_primitiveAtomWithConstant2170);
                    if (input.LA(1) == Token.DOWN) {
                        match(input, Token.DOWN, null);
                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:655:34: (keyValuePairs+=
                        // arrayKeyValue )*
                        loop26:
                        while (true) {
                            int alt26 = 2;
                            int LA26_0 = input.LA(1);
                            if (((LA26_0 >= Bool && LA26_0 <= Null) || LA26_0 == CONSTANT || LA26_0 == Arrow)) {
                                alt26 = 1;
                            }

                            switch (alt26) {
                                case 1:
                                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:655:34:
                                    // keyValuePairs+=
                                    // arrayKeyValue
                                {
                                    pushFollow(FOLLOW_arrayKeyValue_in_primitiveAtomWithConstant2174);
                                    keyValuePairs = arrayKeyValue();
                                    state._fsp--;

								if (list_keyValuePairs==null) list_keyValuePairs=new ArrayList<Object>();
								list_keyValuePairs.add(keyValuePairs.getTemplate());
								}
								break;

							default :
                                break loop26;
                            }
                        }

						match(input, Token.UP, null); 
					}

					// TEMPLATE REWRITE
                    // 655:53: -> array(content=$keyValuePairs)
                    {
                        retval.st = templateLib.getInstanceOf("array", new STAttrMap().put("content",
                                list_keyValuePairs));
                    }



					}
					break;
				case 7 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:656:9: CONSTANT
                {
                    CONSTANT41 = (ITSPHPAst) match(input, CONSTANT, FOLLOW_CONSTANT_in_primitiveAtomWithConstant2197);
                    // TEMPLATE REWRITE
                    // 656:53: -> {%{$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)}}
                    {
                        retval.st = new StringTemplate(templateLib, (CONSTANT41 != null ? CONSTANT41.getText() : null).substring(0, (CONSTANT41 != null ? CONSTANT41.getText() : null).length() - 1));
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:665:1: arrayKeyValue : ( ^( '=>' key= expression
    // value= expression ) -> keyValue(key=$key.stvalue=$value.st)| expression -> {$expression.st});
    public final TSPHPTranslatorWalker.arrayKeyValue_return arrayKeyValue() throws RecognitionException {
        TSPHPTranslatorWalker.arrayKeyValue_return retval = new TSPHPTranslatorWalker.arrayKeyValue_return();
        retval.start = input.LT(1);

		TreeRuleReturnScope key =null;
		TreeRuleReturnScope value =null;
        TreeRuleReturnScope expression42 = null;

		try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:666:5: ( ^( '=>' key= expression value=
            // expression ) -> keyValue(key=$key.stvalue=$value.st)| expression -> {$expression.st})
            int alt28 = 2;
            int LA28_0 = input.LA(1);
            if ((LA28_0 == Arrow)) {
                alt28 = 1;
            } else if (((LA28_0 >= Bool && LA28_0 <= Null) || LA28_0 == CONSTANT)) {
                alt28 = 2;
            }

			else {
				NoViableAltException nvae =
                        new NoViableAltException("", 28, 0, input);
                throw nvae;
            }

            switch (alt28) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:666:9: ^( '=>' key= expression
                    // value= expression )
                {
                    match(input, Arrow, FOLLOW_Arrow_in_arrayKeyValue2286);
                    match(input, Token.DOWN, null);
                    pushFollow(FOLLOW_expression_in_arrayKeyValue2290);
                    key = expression();
                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_arrayKeyValue2294);
                    value = expression();
                    state._fsp--;

					match(input, Token.UP, null); 

					// TEMPLATE REWRITE
                    // 666:49: -> keyValue(key=$key.stvalue=$value.st)
                    {
                        retval.st = templateLib.getInstanceOf("keyValue", new STAttrMap().put("key",
                                (key != null ? ((StringTemplate) key.getTemplate()) : null)).put("value",
                                (value != null ? ((StringTemplate) value.getTemplate()) : null)));
                    }



					}
					break;
				case 2 :
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:667:9: expression
                {
                    pushFollow(FOLLOW_expression_in_arrayKeyValue2319);
                    expression42 = expression();
                    state._fsp--;

					// TEMPLATE REWRITE
                    // 667:20: -> {$expression.st}
                    {
                        retval.st = (expression42 != null ? ((StringTemplate) expression42.getTemplate()) : null);
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
    public static final BitSet FOLLOW_DEFAULT_NAMESPACE_in_namespace126 = new BitSet(new long[]{0x0000000000000000L,
            0x8000000000000000L});
    public static final BitSet FOLLOW_namespaceBody_in_namespace129 = new BitSet(new long[]{0x0000000000000008L});
	public static final BitSet FOLLOW_NAMESPACE_BODY_in_namespaceBody186 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_statement_in_namespaceBody190 = new BitSet(new long[]{0x0000000000000008L,
            0x0004000000100000L, 0x0000080000000000L});
    public static final BitSet FOLLOW_NAMESPACE_BODY_in_namespaceBody211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_useDeclarationList_in_statement239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_definition_in_statement253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Use_in_useDeclarationList287 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_useDeclaration_in_useDeclarationList291 = new BitSet(new
            long[]{0x0000000000000008L, 0x0000000000000000L, 0x0000040000000000L});
    public static final BitSet FOLLOW_USE_DECLARATION_in_useDeclaration330 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_TYPE_NAME_in_useDeclaration332 = new BitSet(new long[]{0x0000000000000000L,
            0x0020000000000000L});
    public static final BitSet FOLLOW_Identifier_in_useDeclaration334 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_functionDeclaration_in_definition408 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constDeclarationList_in_definition426 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONSTANT_DECLARATION_LIST_in_constDeclarationList462 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_TYPE_in_constDeclarationList477 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_TYPE_MODIFIER_in_constDeclarationList480 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Public_in_constDeclarationList482 = new BitSet(new long[]{0x0000000000000000L,
            0x0000000000000000L, 0x0000000001000000L});
    public static final BitSet FOLLOW_Static_in_constDeclarationList484 = new BitSet(new long[]{0x0000000000000000L,
            0x0000800000000000L});
    public static final BitSet FOLLOW_Final_in_constDeclarationList486 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_scalarTypesOrUnknown_in_constDeclarationList489 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constDeclaration_in_constDeclarationList506 = new BitSet(new
            long[]{0x0000000000000008L, 0x0020000000000000L});
    public static final BitSet FOLLOW_Identifier_in_constDeclaration564 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_unaryPrimitiveAtom_in_constDeclaration566 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_primitiveAtomWithConstant_in_unaryPrimitiveAtom616 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UNARY_MINUS_in_unaryPrimitiveAtom647 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_UNARY_PLUS_in_unaryPrimitiveAtom668 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_primitiveAtomWithConstant_in_unaryPrimitiveAtom686 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TYPE_MODIFIER_in_typeModifier769 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Cast_in_typeModifier784 = new BitSet(new long[]{0x0001000000100008L});
    public static final BitSet FOLLOW_LogicNot_in_typeModifier803 = new BitSet(new long[]{0x0000000000100008L});
    public static final BitSet FOLLOW_QuestionMark_in_typeModifier826 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TYPE_MODIFIER_in_typeModifier853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_allTypesOrUnknown_in_returnTypesOrUnknown875 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_allTypes_in_allTypesOrUnknown912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QuestionMark_in_allTypesOrUnknown926 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitiveTypes_in_allTypes954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TYPE_NAME_in_allTypes969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scalarTypes_in_primitiveTypes1001 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeArray_in_primitiveTypes1016 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeResource_in_primitiveTypes1033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeMixed_in_primitiveTypes1047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scalarTypes_in_scalarTypesOrUnknown1074 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QuestionMark_in_scalarTypesOrUnknown1088 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeBool_in_scalarTypes1119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeInt_in_scalarTypes1136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeFloat_in_scalarTypes1154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeString_in_scalarTypes1170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PARAMETER_LIST_in_formalParameters1207 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_paramDeclaration_in_formalParameters1212 = new BitSet(new
            long[]{0x0000000000000008L, 0x0000000000000000L, 0x0000000000000010L});
    public static final BitSet FOLLOW_PARAMETER_LIST_in_formalParameters1249 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BLOCK_in_block1277 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_instruction_in_block1281 = new BitSet(new long[]{0x0000000000000008L,
            0x0000008000000000L});
    public static final BitSet FOLLOW_BLOCK_in_block1295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PARAMETER_DECLARATION_in_paramDeclaration1322 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_TYPE_in_paramDeclaration1337 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeModifier_in_paramDeclaration1339 = new BitSet(new
            long[]{0x0000000000100000L, 0x0000000000000008L, 0x0000037810000000L});
    public static final BitSet FOLLOW_allTypesOrUnknown_in_paramDeclaration1341 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_VariableId_in_paramDeclaration1362 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_VariableId_in_paramDeclaration1383 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_unaryPrimitiveAtom_in_paramDeclaration1387 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Function_in_functionDeclaration1442 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_FUNCTION_MODIFIER_in_functionDeclaration1456 = new BitSet(new
            long[]{0x0000000000000000L, 0x0000000000000000L, 0x0000000004000000L});
    public static final BitSet FOLLOW_TYPE_in_functionDeclaration1471 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeModifier_in_functionDeclaration1473 = new BitSet(new
            long[]{0x0000000000100000L, 0x0000000000000008L, 0x0000037810000000L});
    public static final BitSet FOLLOW_returnTypesOrUnknown_in_functionDeclaration1475 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Identifier_in_functionDeclaration1490 = new BitSet(new
            long[]{0x0000000000000000L, 0x0000000000000000L, 0x0000000000000020L});
    public static final BitSet FOLLOW_formalParameters_in_functionDeclaration1504 = new BitSet(new
            long[]{0x0000000000000000L, 0x0000000000001000L});
    public static final BitSet FOLLOW_block_in_functionDeclaration1518 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXPRESSION_in_instruction1720 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_instruction1722 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_atom_in_expression1803 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitiveAtomWithConstant_in_atom1858 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Bool_in_primitiveAtomWithConstant1906 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Int_in_primitiveAtomWithConstant1959 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Float_in_primitiveAtomWithConstant2013 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_String_in_primitiveAtomWithConstant2065 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Null_in_primitiveAtomWithConstant2116 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeArray_in_primitiveAtomWithConstant2170 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_arrayKeyValue_in_primitiveAtomWithConstant2174 = new BitSet(new
            long[]{0x8000000000000008L, 0x000000000000025FL});
    public static final BitSet FOLLOW_CONSTANT_in_primitiveAtomWithConstant2197 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Arrow_in_arrayKeyValue2286 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_arrayKeyValue2290 = new BitSet(new long[]{0x8000000000000000L,
            0x000000000000005FL});
    public static final BitSet FOLLOW_expression_in_arrayKeyValue2294 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expression_in_arrayKeyValue2319 = new BitSet(new long[]{0x0000000000000002L});
}
