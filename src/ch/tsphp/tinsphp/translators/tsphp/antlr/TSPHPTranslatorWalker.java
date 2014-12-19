// $ANTLR 3.5.2-including-157 D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g 2014-12-19 14:07:42

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
import org.antlr.runtime.MismatchedSetException;
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
public class TSPHPTranslatorWalker extends TreeParser
{
    public static final String[] tokenNames = new String[]{
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
    public static final int EOF = -1;
    public static final int LogicXorWeak = 4;
    public static final int LogicOrWeak = 5;
    public static final int LogicAndWeak = 6;
    public static final int Assign = 7;
    public static final int BitwiseAndAssign = 8;
    public static final int BitwiseOrAssign = 9;
    public static final int BitwiseXorAssign = 10;
    public static final int PlusAssign = 11;
    public static final int MinusAssign = 12;
    public static final int DotAssign = 13;
    public static final int MultiplyAssign = 14;
    public static final int DivideAssign = 15;
    public static final int ModuloAssign = 16;
    public static final int ShiftLeftAssign = 17;
    public static final int ShiftRightAssign = 18;
    public static final int CAST_ASSIGN = 19;
    public static final int QuestionMark = 20;
    public static final int LogicOr = 21;
    public static final int LogicAnd = 22;
    public static final int BitwiseOr = 23;
    public static final int BitwiseXor = 24;
    public static final int BitwiseAnd = 25;
    public static final int Equal = 26;
    public static final int Identical = 27;
    public static final int NotEqual = 28;
    public static final int NotIdentical = 29;
    public static final int GreaterEqualThan = 30;
    public static final int GreaterThan = 31;
    public static final int LessEqualThan = 32;
    public static final int LessThan = 33;
    public static final int ShiftLeft = 34;
    public static final int ShiftRight = 35;
    public static final int Minus = 36;
    public static final int Plus = 37;
    public static final int Dot = 38;
    public static final int Multiply = 39;
    public static final int Divide = 40;
    public static final int Modulo = 41;
    public static final int Instanceof = 42;
    public static final int CAST = 43;
    public static final int PRE_DECREMENT = 44;
    public static final int PRE_INCREMENT = 45;
    public static final int At = 46;
    public static final int BitwiseNot = 47;
    public static final int LogicNot = 48;
    public static final int UNARY_MINUS = 49;
    public static final int UNARY_PLUS = 50;
    public static final int New = 51;
    public static final int Clone = 52;
    public static final int POST_DECREMENT = 53;
    public static final int POST_INCREMENT = 54;
    public static final int ARRAY_ACCESS = 55;
    public static final int FIELD_ACCESS = 56;
    public static final int CLASS_STATIC_ACCESS = 57;
    public static final int FUNCTION_CALL = 58;
    public static final int METHOD_CALL = 59;
    public static final int METHOD_CALL_POSTFIX = 60;
    public static final int METHOD_CALL_STATIC = 61;
    public static final int Exit = 62;
    public static final int Bool = 63;
    public static final int Int = 64;
    public static final int Float = 65;
    public static final int String = 66;
    public static final int TypeArray = 67;
    public static final int Null = 68;
    public static final int This = 69;
    public static final int CONSTANT = 70;
    public static final int ACTUAL_PARAMETERS = 71;
    public static final int Abstract = 72;
    public static final int Arrow = 73;
    public static final int As = 74;
    public static final int BINARY = 75;
    public static final int BLOCK = 76;
    public static final int BLOCK_CONDITIONAL = 77;
    public static final int Backslash = 78;
    public static final int Break = 79;
    public static final int CLASS_BODY = 80;
    public static final int CLASS_MODIFIER = 81;
    public static final int CLASS_STATIC_ACCESS_VARIABLE_ID = 82;
    public static final int CONSTANT_DECLARATION = 83;
    public static final int CONSTANT_DECLARATION_LIST = 84;
    public static final int Case = 85;
    public static final int Cast = 86;
    public static final int Catch = 87;
    public static final int Class = 88;
    public static final int Colon = 89;
    public static final int Comma = 90;
    public static final int Comment = 91;
    public static final int Const = 92;
    public static final int Construct = 93;
    public static final int Continue = 94;
    public static final int DECIMAL = 95;
    public static final int DEFAULT_NAMESPACE = 96;
    public static final int Default = 97;
    public static final int Destruct = 98;
    public static final int Do = 99;
    public static final int Dollar = 100;
    public static final int DoubleColon = 101;
    public static final int EXPONENT = 102;
    public static final int EXPRESSION = 103;
    public static final int EXPRESSION_LIST = 104;
    public static final int Echo = 105;
    public static final int Else = 106;
    public static final int Extends = 107;
    public static final int FIELD = 108;
    public static final int FIELD_MODIFIER = 109;
    public static final int FUNCTION_MODIFIER = 110;
    public static final int Final = 111;
    public static final int For = 112;
    public static final int Foreach = 113;
    public static final int Function = 114;
    public static final int HEXADECIMAL = 115;
    public static final int INTERFACE_BODY = 116;
    public static final int Identifier = 117;
    public static final int If = 118;
    public static final int Implements = 119;
    public static final int Interface = 120;
    public static final int LeftCurlyBrace = 121;
    public static final int LeftParenthesis = 122;
    public static final int LeftSquareBrace = 123;
    public static final int METHOD_DECLARATION = 124;
    public static final int METHOD_MODIFIER = 125;
    public static final int MinusMinus = 126;
    public static final int NAMESPACE_BODY = 127;
    public static final int Namespace = 128;
    public static final int NotEqualAlternative = 129;
    public static final int OCTAL = 130;
    public static final int ObjectOperator = 131;
    public static final int PARAMETER_DECLARATION = 132;
    public static final int PARAMETER_LIST = 133;
    public static final int PARAMETER_TYPE = 134;
    public static final int Parent = 135;
    public static final int ParentColonColon = 136;
    public static final int PlusPlus = 137;
    public static final int Private = 138;
    public static final int ProtectThis = 139;
    public static final int Protected = 140;
    public static final int Public = 141;
    public static final int Return = 142;
    public static final int RightCurlyBrace = 143;
    public static final int RightParenthesis = 144;
    public static final int RightSquareBrace = 145;
    public static final int STRING_DOUBLE_QUOTED = 146;
    public static final int STRING_SINGLE_QUOTED = 147;
    public static final int SWITCH_CASES = 148;
    public static final int Self = 149;
    public static final int SelfColonColon = 150;
    public static final int Semicolon = 151;
    public static final int Static = 152;
    public static final int Switch = 153;
    public static final int TYPE = 154;
    public static final int TYPE_MODIFIER = 155;
    public static final int TYPE_NAME = 156;
    public static final int Throw = 157;
    public static final int Try = 158;
    public static final int TypeAliasBool = 159;
    public static final int TypeAliasFloat = 160;
    public static final int TypeAliasFloat2 = 161;
    public static final int TypeAliasInt = 162;
    public static final int TypeBool = 163;
    public static final int TypeFloat = 164;
    public static final int TypeInt = 165;
    public static final int TypeMixed = 166;
    public static final int TypeObject = 167;
    public static final int TypeResource = 168;
    public static final int TypeString = 169;
    public static final int USE_DECLARATION = 170;
    public static final int Use = 171;
    public static final int VARIABLE_DECLARATION = 172;
    public static final int VARIABLE_DECLARATION_LIST = 173;
    public static final int VariableId = 174;
    public static final int Void = 175;
    public static final int While = 176;
    public static final int Whitespace = 177;
    public static final int PhpEnd = 178;
    public static final int PhpStart = 179;

    // delegates
    public TreeParser[] getDelegates() {
        return new TreeParser[]{};
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

    /**
     * allows convenient multi-value initialization:
     * "new STAttrMap().put(...).put(...)"
     */
    @SuppressWarnings("serial")
    public static class STAttrMap extends HashMap<String, Object>
    {
        public STAttrMap put(String attrName, Object value) {
            super.put(attrName, value);
            return this;
        }
    }

    @Override
    public String[] getTokenNames() {
        return TSPHPTranslatorWalker.tokenNames;
    }

    @Override
    public String getGrammarFileName() {
        return "D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g";
    }


    private IPrecedenceHelper precedenceHelper;

    public TSPHPTranslatorWalker(TreeNodeStream input, IPrecedenceHelper thePrecedenceHelper) {
        this(input);
        precedenceHelper = thePrecedenceHelper;
    }

    private String getMethodName(String name) {
        return name.substring(0, name.length() - 2);
    }


    public static class compilationUnit_return extends TreeRuleReturnScope
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


    // $ANTLR start "compilationUnit"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:54:1: compilationUnit : ( (n+= namespace )+ ) ->
    // file(namespaces=$n);
    public final TSPHPTranslatorWalker.compilationUnit_return compilationUnit() throws RecognitionException {
        TSPHPTranslatorWalker.compilationUnit_return retval = new TSPHPTranslatorWalker.compilationUnit_return();
        retval.start = input.LT(1);

        List<Object> list_n = null;
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
                        int alt1 = 2;
                        int LA1_0 = input.LA(1);
                        if ((LA1_0 == Namespace)) {
                            alt1 = 1;
                        }

                        switch (alt1) {
                            case 1:
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:55:11: n+= namespace
                            {
                                pushFollow(FOLLOW_namespace_in_compilationUnit80);
                                n = namespace();
                                state._fsp--;

                                if (list_n == null) {
                                    list_n = new ArrayList<Object>();
                                }
                                list_n.add(n.getTemplate());
                            }
                            break;

                            default:
                                if (cnt1 >= 1) {
                                    break loop1;
                                }
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

        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "compilationUnit"


    public static class namespace_return extends TreeRuleReturnScope
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


    // $ANTLR start "namespace"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:58:1: namespace : ^( 'namespace' (name= TYPE_NAME |
    // DEFAULT_NAMESPACE ) namespaceBody ) -> namespace(name=namespaceNamebody=$namespaceBody.st);
    public final TSPHPTranslatorWalker.namespace_return namespace() throws RecognitionException {
        TSPHPTranslatorWalker.namespace_return retval = new TSPHPTranslatorWalker.namespace_return();
        retval.start = input.LT(1);

        ITSPHPAst name = null;
        TreeRuleReturnScope namespaceBody1 = null;

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
                    alt2 = 1;
                } else if ((LA2_0 == DEFAULT_NAMESPACE)) {
                    alt2 = 2;
                } else {
                    NoViableAltException nvae =
                            new NoViableAltException("", 2, 0, input);
                    throw nvae;
                }

                switch (alt2) {
                    case 1:
                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:60:24: name= TYPE_NAME
                    {
                        name = (ITSPHPAst) match(input, TYPE_NAME, FOLLOW_TYPE_NAME_in_namespace124);
                    }
                    break;
                    case 2:
                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:60:39: DEFAULT_NAMESPACE
                    {
                        match(input, DEFAULT_NAMESPACE, FOLLOW_DEFAULT_NAMESPACE_in_namespace126);
                    }
                    break;

                }

                pushFollow(FOLLOW_namespaceBody_in_namespace129);
                namespaceBody1 = namespaceBody();
                state._fsp--;

                match(input, Token.UP, null);


                if (name != null) {
                    namespaceName = name.getText().substring(1, name.getText().length() - 1);
                }

                // TEMPLATE REWRITE
                // 67:9: -> namespace(name=namespaceNamebody=$namespaceBody.st)
                {
                    retval.st = templateLib.getInstanceOf("namespace", new STAttrMap().put("name",
                            namespaceName).put("body", (namespaceBody1 != null ? ((StringTemplate) namespaceBody1
                            .getTemplate()) : null)));
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
    // $ANTLR end "namespace"


    public static class namespaceBody_return extends TreeRuleReturnScope
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


    // $ANTLR start "namespaceBody"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:70:1: namespaceBody : ( ^( NAMESPACE_BODY
    // (statements+= statement )* ) -> body(statements=$statements)| NAMESPACE_BODY -> body(statements=null));
    public final TSPHPTranslatorWalker.namespaceBody_return namespaceBody() throws RecognitionException {
        TSPHPTranslatorWalker.namespaceBody_return retval = new TSPHPTranslatorWalker.namespaceBody_return();
        retval.start = input.LT(1);

        List<Object> list_statements = null;
        RuleReturnScope statements = null;
        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:71:5: ( ^( NAMESPACE_BODY (statements+=
            // statement )* ) -> body(statements=$statements)| NAMESPACE_BODY -> body(statements=null))
            int alt4 = 2;
            int LA4_0 = input.LA(1);
            if ((LA4_0 == NAMESPACE_BODY)) {
                int LA4_1 = input.LA(2);
                if ((LA4_1 == DOWN)) {
                    alt4 = 1;
                } else if ((LA4_1 == UP)) {
                    alt4 = 2;
                } else {
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

            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 4, 0, input);
                throw nvae;
            }

            switch (alt4) {
                case 1:
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
                            if ((LA3_0 == CONSTANT_DECLARATION_LIST || LA3_0 == EXPRESSION || LA3_0 == Function ||
                                    LA3_0 == If || LA3_0 == Switch || LA3_0 == Use || LA3_0 ==
                                    VARIABLE_DECLARATION_LIST)) {
                                alt3 = 1;
                            }

                            switch (alt3) {
                                case 1:
                                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:71:36: statements+=
                                    // statement
                                {
                                    pushFollow(FOLLOW_statement_in_namespaceBody190);
                                    statements = statement();
                                    state._fsp--;

                                    if (list_statements == null) {
                                        list_statements = new ArrayList<Object>();
                                    }
                                    list_statements.add(statements.getTemplate());
                                }
                                break;

                                default:
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
                case 2:
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
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "namespaceBody"


    public static class statement_return extends TreeRuleReturnScope
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


    // $ANTLR start "statement"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:75:1: statement : ( useDeclarationList ->
    // {$useDeclarationList.st}| definition -> {$definition.st}| instruction -> {$instruction.st});
    public final TSPHPTranslatorWalker.statement_return statement() throws RecognitionException {
        TSPHPTranslatorWalker.statement_return retval = new TSPHPTranslatorWalker.statement_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope useDeclarationList2 = null;
        TreeRuleReturnScope definition3 = null;
        TreeRuleReturnScope instruction4 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:76:5: ( useDeclarationList ->
            // {$useDeclarationList.st}| definition -> {$definition.st}| instruction -> {$instruction.st})
            int alt5 = 3;
            switch (input.LA(1)) {
                case Use: {
                    alt5 = 1;
                }
                break;
                case CONSTANT_DECLARATION_LIST:
                case Function: {
                    alt5 = 2;
                }
                break;
                case EXPRESSION:
                case If:
                case Switch:
                case VARIABLE_DECLARATION_LIST: {
                    alt5 = 3;
                }
                break;
                default:
                    NoViableAltException nvae =
                            new NoViableAltException("", 5, 0, input);
                    throw nvae;
            }
            switch (alt5) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:76:9: useDeclarationList
                {
                    pushFollow(FOLLOW_useDeclarationList_in_statement239);
                    useDeclarationList2 = useDeclarationList();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 76:28: -> {$useDeclarationList.st}
                    {
                        retval.st = (useDeclarationList2 != null ? ((StringTemplate) useDeclarationList2.getTemplate
                                ()) : null);
                    }


                }
                break;
                case 2:
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
                case 3:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:78:9: instruction
                {
                    pushFollow(FOLLOW_instruction_in_statement267);
                    instruction4 = instruction();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 78:21: -> {$instruction.st}
                    {
                        retval.st = (instruction4 != null ? ((StringTemplate) instruction4.getTemplate()) : null);
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
    // $ANTLR end "statement"


    public static class useDeclarationList_return extends TreeRuleReturnScope
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


    // $ANTLR start "useDeclarationList"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:81:1: useDeclarationList : ^( 'use' (declarations+=
    // useDeclaration )+ ) -> useDeclarationList(useDeclarations=$declarations);
    public final TSPHPTranslatorWalker.useDeclarationList_return useDeclarationList() throws RecognitionException {
        TSPHPTranslatorWalker.useDeclarationList_return retval = new TSPHPTranslatorWalker.useDeclarationList_return();
        retval.start = input.LT(1);

        List<Object> list_declarations = null;
        RuleReturnScope declarations = null;
        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:82:5: ( ^( 'use' (declarations+=
            // useDeclaration )+ ) -> useDeclarationList(useDeclarations=$declarations))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:82:9: ^( 'use' (declarations+=
            // useDeclaration )+ )
            {
                match(input, Use, FOLLOW_Use_in_useDeclarationList291);
                match(input, Token.DOWN, null);
                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:82:29: (declarations+= useDeclaration )+
                int cnt6 = 0;
                loop6:
                while (true) {
                    int alt6 = 2;
                    int LA6_0 = input.LA(1);
                    if ((LA6_0 == USE_DECLARATION)) {
                        alt6 = 1;
                    }

                    switch (alt6) {
                        case 1:
                            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:82:29: declarations+=
                            // useDeclaration
                        {
                            pushFollow(FOLLOW_useDeclaration_in_useDeclarationList295);
                            declarations = useDeclaration();
                            state._fsp--;

                            if (list_declarations == null) {
                                list_declarations = new ArrayList<Object>();
                            }
                            list_declarations.add(declarations.getTemplate());
                        }
                        break;

                        default:
                            if (cnt6 >= 1) {
                                break loop6;
                            }
                            EarlyExitException eee = new EarlyExitException(6, input);
                            throw eee;
                    }
                    cnt6++;
                }

                match(input, Token.UP, null);

                // TEMPLATE REWRITE
                // 83:9: -> useDeclarationList(useDeclarations=$declarations)
                {
                    retval.st = templateLib.getInstanceOf("useDeclarationList",
                            new STAttrMap().put("useDeclarations", list_declarations));
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
    // $ANTLR end "useDeclarationList"


    public static class useDeclaration_return extends TreeRuleReturnScope
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


    // $ANTLR start "useDeclaration"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:86:1: useDeclaration : ^( USE_DECLARATION TYPE_NAME
    // Identifier ) -> useDeclaration(type=$TYPE_NAMEalias=$Identifier);
    public final TSPHPTranslatorWalker.useDeclaration_return useDeclaration() throws RecognitionException {
        TSPHPTranslatorWalker.useDeclaration_return retval = new TSPHPTranslatorWalker.useDeclaration_return();
        retval.start = input.LT(1);

        ITSPHPAst TYPE_NAME5 = null;
        ITSPHPAst Identifier6 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:87:5: ( ^( USE_DECLARATION TYPE_NAME
            // Identifier ) -> useDeclaration(type=$TYPE_NAMEalias=$Identifier))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:87:9: ^( USE_DECLARATION TYPE_NAME
            // Identifier )
            {
                match(input, USE_DECLARATION, FOLLOW_USE_DECLARATION_in_useDeclaration334);
                match(input, Token.DOWN, null);
                TYPE_NAME5 = (ITSPHPAst) match(input, TYPE_NAME, FOLLOW_TYPE_NAME_in_useDeclaration336);
                Identifier6 = (ITSPHPAst) match(input, Identifier, FOLLOW_Identifier_in_useDeclaration338);
                match(input, Token.UP, null);

                // TEMPLATE REWRITE
                // 88:9: -> useDeclaration(type=$TYPE_NAMEalias=$Identifier)
                {
                    retval.st = templateLib.getInstanceOf("useDeclaration", new STAttrMap().put("type",
                            TYPE_NAME5).put("alias", Identifier6));
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
    // $ANTLR end "useDeclaration"


    public static class definition_return extends TreeRuleReturnScope
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


    // $ANTLR start "definition"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:91:1: definition : ( functionDeclaration ->
    // {$functionDeclaration.st}| constDeclarationList -> {$constDeclarationList.st});
    public final TSPHPTranslatorWalker.definition_return definition() throws RecognitionException {
        TSPHPTranslatorWalker.definition_return retval = new TSPHPTranslatorWalker.definition_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope functionDeclaration7 = null;
        TreeRuleReturnScope constDeclarationList8 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:92:5: ( functionDeclaration ->
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
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:96:9: functionDeclaration
                {
                    pushFollow(FOLLOW_functionDeclaration_in_definition412);
                    functionDeclaration7 = functionDeclaration();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 96:33: -> {$functionDeclaration.st}
                    {
                        retval.st = (functionDeclaration7 != null ? ((StringTemplate) functionDeclaration7
                                .getTemplate()) : null);
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:97:9: constDeclarationList
                {
                    pushFollow(FOLLOW_constDeclarationList_in_definition430);
                    constDeclarationList8 = constDeclarationList();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 97:33: -> {$constDeclarationList.st}
                    {
                        retval.st = (constDeclarationList8 != null ? ((StringTemplate) constDeclarationList8
                                .getTemplate()) : null);
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
    // $ANTLR end "definition"


    public static class constDeclarationList_return extends TreeRuleReturnScope
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


    // $ANTLR start "constDeclarationList"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:151:1: constDeclarationList : ^(
    // CONSTANT_DECLARATION_LIST ^( TYPE ^( TYPE_MODIFIER Public Static Final ) scalarTypesOrUnknown ) (identifiers+=
    // constDeclaration )+ ) -> const(type=$scalarTypesOrUnknown.stidentifiers=$identifiers);
    public final TSPHPTranslatorWalker.constDeclarationList_return constDeclarationList() throws RecognitionException {
        TSPHPTranslatorWalker.constDeclarationList_return retval = new TSPHPTranslatorWalker
                .constDeclarationList_return();
        retval.start = input.LT(1);

        List<Object> list_identifiers = null;
        TreeRuleReturnScope scalarTypesOrUnknown9 = null;
        RuleReturnScope identifiers = null;
        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:152:5: ( ^( CONSTANT_DECLARATION_LIST ^(
            // TYPE ^( TYPE_MODIFIER Public Static Final ) scalarTypesOrUnknown ) (identifiers+= constDeclaration )+
            // ) -> const(type=$scalarTypesOrUnknown.stidentifiers=$identifiers))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:152:9: ^( CONSTANT_DECLARATION_LIST ^( TYPE
            // ^( TYPE_MODIFIER Public Static Final ) scalarTypesOrUnknown ) (identifiers+= constDeclaration )+ )
            {
                match(input, CONSTANT_DECLARATION_LIST, FOLLOW_CONSTANT_DECLARATION_LIST_in_constDeclarationList466);
                match(input, Token.DOWN, null);
                match(input, TYPE, FOLLOW_TYPE_in_constDeclarationList481);
                match(input, Token.DOWN, null);
                match(input, TYPE_MODIFIER, FOLLOW_TYPE_MODIFIER_in_constDeclarationList484);
                match(input, Token.DOWN, null);
                match(input, Public, FOLLOW_Public_in_constDeclarationList486);
                match(input, Static, FOLLOW_Static_in_constDeclarationList488);
                match(input, Final, FOLLOW_Final_in_constDeclarationList490);
                match(input, Token.UP, null);

                pushFollow(FOLLOW_scalarTypesOrUnknown_in_constDeclarationList493);
                scalarTypesOrUnknown9 = scalarTypesOrUnknown();
                state._fsp--;

                match(input, Token.UP, null);

                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:154:24: (identifiers+= constDeclaration )+
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
                            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:154:24: identifiers+=
                            // constDeclaration
                        {
                            pushFollow(FOLLOW_constDeclaration_in_constDeclarationList510);
                            identifiers = constDeclaration();
                            state._fsp--;

                            if (list_identifiers == null) {
                                list_identifiers = new ArrayList<Object>();
                            }
                            list_identifiers.add(identifiers.getTemplate());
                        }
                        break;

                        default:
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
                // 156:9: -> const(type=$scalarTypesOrUnknown.stidentifiers=$identifiers)
                {
                    retval.st = templateLib.getInstanceOf("const", new STAttrMap().put("type",
                            (scalarTypesOrUnknown9 != null ? ((StringTemplate) scalarTypesOrUnknown9.getTemplate()) :
                                    null)).put("identifiers", list_identifiers));
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
    // $ANTLR end "constDeclarationList"


    public static class constDeclaration_return extends TreeRuleReturnScope
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


    // $ANTLR start "constDeclaration"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:159:1: constDeclaration : ^( Identifier
    // unaryPrimitiveAtom ) -> assign(id=$Identifier.text.substring(0,$Identifier.text.length()-1)
    // value=$unaryPrimitiveAtom.st);
    public final TSPHPTranslatorWalker.constDeclaration_return constDeclaration() throws RecognitionException {
        TSPHPTranslatorWalker.constDeclaration_return retval = new TSPHPTranslatorWalker.constDeclaration_return();
        retval.start = input.LT(1);

        ITSPHPAst Identifier10 = null;
        TreeRuleReturnScope unaryPrimitiveAtom11 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:160:5: ( ^( Identifier unaryPrimitiveAtom )
            // -> assign(id=$Identifier.text.substring(0,$Identifier.text.length()-1)value=$unaryPrimitiveAtom.st))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:160:9: ^( Identifier unaryPrimitiveAtom )
            {
                Identifier10 = (ITSPHPAst) match(input, Identifier, FOLLOW_Identifier_in_constDeclaration568);
                match(input, Token.DOWN, null);
                pushFollow(FOLLOW_unaryPrimitiveAtom_in_constDeclaration570);
                unaryPrimitiveAtom11 = unaryPrimitiveAtom();
                state._fsp--;

                match(input, Token.UP, null);

                // TEMPLATE REWRITE
                // 161:9: -> assign(id=$Identifier.text.substring(0,$Identifier.text.length()-1)
                // value=$unaryPrimitiveAtom.st)
                {
                    retval.st = templateLib.getInstanceOf("assign", new STAttrMap().put("id",
                            (Identifier10 != null ? Identifier10.getText() : null).substring(0,
                                    (Identifier10 != null ? Identifier10.getText() : null).length() - 1)).put
                            ("value", (unaryPrimitiveAtom11 != null ? ((StringTemplate) unaryPrimitiveAtom11
                                    .getTemplate()) : null)));
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
    // $ANTLR end "constDeclaration"


    public static class unaryPrimitiveAtom_return extends TreeRuleReturnScope
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


    // $ANTLR start "unaryPrimitiveAtom"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:164:1: unaryPrimitiveAtom : (
    // primitiveAtomWithConstant -> {$primitiveAtomWithConstant.st}| ^( ( UNARY_MINUS | UNARY_PLUS )
    // primitiveAtomWithConstant ) -> unaryPreOperator(operator=unaryexpression=$primitiveAtomWithConstant.st));
    public final TSPHPTranslatorWalker.unaryPrimitiveAtom_return unaryPrimitiveAtom() throws RecognitionException {
        TSPHPTranslatorWalker.unaryPrimitiveAtom_return retval = new TSPHPTranslatorWalker.unaryPrimitiveAtom_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope primitiveAtomWithConstant12 = null;
        TreeRuleReturnScope primitiveAtomWithConstant13 = null;


        String unary = "";

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:168:5: ( primitiveAtomWithConstant ->
            // {$primitiveAtomWithConstant.st}| ^( ( UNARY_MINUS | UNARY_PLUS ) primitiveAtomWithConstant ) ->
            // unaryPreOperator(operator=unaryexpression=$primitiveAtomWithConstant.st))
            int alt10 = 2;
            int LA10_0 = input.LA(1);
            if (((LA10_0 >= Bool && LA10_0 <= Null) || LA10_0 == CONSTANT)) {
                alt10 = 1;
            } else if (((LA10_0 >= UNARY_MINUS && LA10_0 <= UNARY_PLUS))) {
                alt10 = 2;
            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 10, 0, input);
                throw nvae;
            }

            switch (alt10) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:168:9: primitiveAtomWithConstant
                {
                    pushFollow(FOLLOW_primitiveAtomWithConstant_in_unaryPrimitiveAtom620);
                    primitiveAtomWithConstant12 = primitiveAtomWithConstant();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 169:9: -> {$primitiveAtomWithConstant.st}
                    {
                        retval.st = (primitiveAtomWithConstant12 != null ? ((StringTemplate)
                                primitiveAtomWithConstant12.getTemplate()) : null);
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:171:9: ^( ( UNARY_MINUS |
                    // UNARY_PLUS ) primitiveAtomWithConstant )
                {
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:171:13: ( UNARY_MINUS | UNARY_PLUS )
                    int alt9 = 2;
                    int LA9_0 = input.LA(1);
                    if ((LA9_0 == UNARY_MINUS)) {
                        alt9 = 1;
                    } else if ((LA9_0 == UNARY_PLUS)) {
                        alt9 = 2;
                    } else {
                        NoViableAltException nvae =
                                new NoViableAltException("", 9, 0, input);
                        throw nvae;
                    }

                    switch (alt9) {
                        case 1:
                            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:171:18: UNARY_MINUS
                        {
                            match(input, UNARY_MINUS, FOLLOW_UNARY_MINUS_in_unaryPrimitiveAtom651);
                            unary = "-";
                        }
                        break;
                        case 2:
                            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:172:18: UNARY_PLUS
                        {
                            match(input, UNARY_PLUS, FOLLOW_UNARY_PLUS_in_unaryPrimitiveAtom672);
                            unary = "+";
                        }
                        break;

                    }

                    match(input, Token.DOWN, null);
                    pushFollow(FOLLOW_primitiveAtomWithConstant_in_unaryPrimitiveAtom690);
                    primitiveAtomWithConstant13 = primitiveAtomWithConstant();
                    state._fsp--;

                    match(input, Token.UP, null);

                    // TEMPLATE REWRITE
                    // 175:9: -> unaryPreOperator(operator=unaryexpression=$primitiveAtomWithConstant.st)
                    {
                        retval.st = templateLib.getInstanceOf("unaryPreOperator", new STAttrMap().put("operator",
                                unary).put("expression", (primitiveAtomWithConstant13 != null ? ((StringTemplate)
                                primitiveAtomWithConstant13.getTemplate()) : null)));
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
    // $ANTLR end "unaryPrimitiveAtom"


    public static class localVariableDeclarationList_return extends TreeRuleReturnScope
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


    // $ANTLR start "localVariableDeclarationList"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:197:1: localVariableDeclarationList : ^(
    // VARIABLE_DECLARATION_LIST ^( TYPE typeModifier allTypesOrUnknown ) (variables+= localVariableDeclaration )+ )
    // -> localVariableDeclarationList(type=%type(\r\n prefixModifiers={$typeModifier.prefixModifiers},
    // \r\n type={$allTypesOrUnknown.st},\r\n suffixModifiers={$typeModifier.suffixModifiers}\r\n )
    // variables=$variables);
    public final TSPHPTranslatorWalker.localVariableDeclarationList_return localVariableDeclarationList() throws
            RecognitionException {
        TSPHPTranslatorWalker.localVariableDeclarationList_return retval = new TSPHPTranslatorWalker
                .localVariableDeclarationList_return();
        retval.start = input.LT(1);

        List<Object> list_variables = null;
        TreeRuleReturnScope typeModifier14 = null;
        TreeRuleReturnScope allTypesOrUnknown15 = null;
        RuleReturnScope variables = null;
        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:198:5: ( ^( VARIABLE_DECLARATION_LIST ^(
            // TYPE typeModifier allTypesOrUnknown ) (variables+= localVariableDeclaration )+ ) ->
            // localVariableDeclarationList(type=%type(\r\n prefixModifiers={$typeModifier.prefixModifiers},
            // \r\n type={$allTypesOrUnknown.st},\r\n suffixModifiers={$typeModifier.suffixModifiers}\r\n )
            // variables=$variables))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:198:9: ^( VARIABLE_DECLARATION_LIST ^( TYPE
            // typeModifier allTypesOrUnknown ) (variables+= localVariableDeclaration )+ )
            {
                match(input, VARIABLE_DECLARATION_LIST,
                        FOLLOW_VARIABLE_DECLARATION_LIST_in_localVariableDeclarationList755);
                match(input, Token.DOWN, null);
                match(input, TYPE, FOLLOW_TYPE_in_localVariableDeclarationList770);
                match(input, Token.DOWN, null);
                pushFollow(FOLLOW_typeModifier_in_localVariableDeclarationList772);
                typeModifier14 = typeModifier();
                state._fsp--;

                pushFollow(FOLLOW_allTypesOrUnknown_in_localVariableDeclarationList774);
                allTypesOrUnknown15 = allTypesOrUnknown();
                state._fsp--;

                match(input, Token.UP, null);

                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:200:22: (variables+=
                // localVariableDeclaration )+
                int cnt11 = 0;
                loop11:
                while (true) {
                    int alt11 = 2;
                    int LA11_0 = input.LA(1);
                    if ((LA11_0 == VariableId)) {
                        alt11 = 1;
                    }

                    switch (alt11) {
                        case 1:
                            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:200:22: variables+=
                            // localVariableDeclaration
                        {
                            pushFollow(FOLLOW_localVariableDeclaration_in_localVariableDeclarationList791);
                            variables = localVariableDeclaration();
                            state._fsp--;

                            if (list_variables == null) {
                                list_variables = new ArrayList<Object>();
                            }
                            list_variables.add(variables.getTemplate());
                        }
                        break;

                        default:
                            if (cnt11 >= 1) {
                                break loop11;
                            }
                            EarlyExitException eee = new EarlyExitException(11, input);
                            throw eee;
                    }
                    cnt11++;
                }

                match(input, Token.UP, null);

                // TEMPLATE REWRITE
                // 202:9: -> localVariableDeclarationList(type=%type(\r\n prefixModifiers={$typeModifier
                // .prefixModifiers},\r\n type={$allTypesOrUnknown.st},\r\n suffixModifiers={$typeModifier
                // .suffixModifiers}\r\n )variables=$variables)
                {
                    retval.st = templateLib.getInstanceOf("localVariableDeclarationList", new STAttrMap().put("type",
                            templateLib.getInstanceOf("type", new STAttrMap().put("prefixModifiers",
                                    (typeModifier14 != null ? ((TSPHPTranslatorWalker.typeModifier_return)
                                            typeModifier14).prefixModifiers : null)).put("type",
                                    (allTypesOrUnknown15 != null ? ((StringTemplate) allTypesOrUnknown15.getTemplate
                                            ()) : null)).put("suffixModifiers",
                                    (typeModifier14 != null ? ((TSPHPTranslatorWalker.typeModifier_return)
                                            typeModifier14).suffixModifiers : null)))).put("variables",
                            list_variables));
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
    // $ANTLR end "localVariableDeclarationList"


    public static class localVariableDeclaration_return extends TreeRuleReturnScope
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


    // $ANTLR start "localVariableDeclaration"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:211:1: localVariableDeclaration : ( VariableId ->
    // {%{$VariableId.text}}| ^( VariableId defaultValue= unaryPrimitiveAtom ) -> assign(id=$VariableId
    // .textvalue=$unaryPrimitiveAtom.st));
    public final TSPHPTranslatorWalker.localVariableDeclaration_return localVariableDeclaration() throws
            RecognitionException {
        TSPHPTranslatorWalker.localVariableDeclaration_return retval = new TSPHPTranslatorWalker
                .localVariableDeclaration_return();
        retval.start = input.LT(1);

        ITSPHPAst VariableId16 = null;
        ITSPHPAst VariableId17 = null;
        TreeRuleReturnScope defaultValue = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:212:5: ( VariableId -> {%{$VariableId
            // .text}}| ^( VariableId defaultValue= unaryPrimitiveAtom ) -> assign(id=$VariableId
            // .textvalue=$unaryPrimitiveAtom.st))
            int alt12 = 2;
            int LA12_0 = input.LA(1);
            if ((LA12_0 == VariableId)) {
                int LA12_1 = input.LA(2);
                if ((LA12_1 == DOWN)) {
                    alt12 = 2;
                } else if ((LA12_1 == UP || LA12_1 == VariableId)) {
                    alt12 = 1;
                } else {
                    int nvaeMark = input.mark();
                    try {
                        input.consume();
                        NoViableAltException nvae =
                                new NoViableAltException("", 12, 1, input);
                        throw nvae;
                    } finally {
                        input.rewind(nvaeMark);
                    }
                }

            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 12, 0, input);
                throw nvae;
            }

            switch (alt12) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:212:9: VariableId
                {
                    VariableId16 = (ITSPHPAst) match(input, VariableId,
                            FOLLOW_VariableId_in_localVariableDeclaration870);
                    // TEMPLATE REWRITE
                    // 213:9: -> {%{$VariableId.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (VariableId16 != null ? VariableId16.getText() :
                                null));
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:214:9: ^( VariableId defaultValue=
                    // unaryPrimitiveAtom )
                {
                    VariableId17 = (ITSPHPAst) match(input, VariableId,
                            FOLLOW_VariableId_in_localVariableDeclaration893);
                    match(input, Token.DOWN, null);
                    pushFollow(FOLLOW_unaryPrimitiveAtom_in_localVariableDeclaration897);
                    defaultValue = unaryPrimitiveAtom();
                    state._fsp--;

                    match(input, Token.UP, null);

                    // TEMPLATE REWRITE
                    // 215:10: -> assign(id=$VariableId.textvalue=$unaryPrimitiveAtom.st)
                    {
                        retval.st = templateLib.getInstanceOf("assign", new STAttrMap().put("id",
                                (VariableId17 != null ? VariableId17.getText() : null)).put("value",
                                (defaultValue != null ? ((StringTemplate) defaultValue.getTemplate()) : null)));
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
    // $ANTLR end "localVariableDeclaration"


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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:218:1: typeModifier returns [Set<String>
    // prefixModifiers, Set<String> suffixModifiers] : ( ^( TYPE_MODIFIER ( 'cast' )? ( '!' )? ( '?' )? ) |
    // TYPE_MODIFIER );
    public final TSPHPTranslatorWalker.typeModifier_return typeModifier() throws RecognitionException {
        TSPHPTranslatorWalker.typeModifier_return retval = new TSPHPTranslatorWalker.typeModifier_return();
        retval.start = input.LT(1);


        retval.prefixModifiers = new HashSet<String>();
        retval.suffixModifiers = new HashSet<String>();

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:223:5: ( ^( TYPE_MODIFIER ( 'cast' )? ( '!'
            // )? ( '?' )? ) | TYPE_MODIFIER )
            int alt16 = 2;
            int LA16_0 = input.LA(1);
            if ((LA16_0 == TYPE_MODIFIER)) {
                int LA16_1 = input.LA(2);
                if ((LA16_1 == DOWN)) {
                    alt16 = 1;
                } else if ((LA16_1 == QuestionMark || LA16_1 == TypeArray || LA16_1 == TYPE_NAME || (LA16_1 >=
                        TypeBool && LA16_1 <= TypeMixed) || (LA16_1 >= TypeResource && LA16_1 <= TypeString))) {
                    alt16 = 2;
                } else {
                    int nvaeMark = input.mark();
                    try {
                        input.consume();
                        NoViableAltException nvae =
                                new NoViableAltException("", 16, 1, input);
                        throw nvae;
                    } finally {
                        input.rewind(nvaeMark);
                    }
                }

            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 16, 0, input);
                throw nvae;
            }

            switch (alt16) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:223:9: ^( TYPE_MODIFIER ( 'cast' )?
                    // ( '!' )? ( '?' )? )
                {
                    match(input, TYPE_MODIFIER, FOLLOW_TYPE_MODIFIER_in_typeModifier952);
                    if (input.LA(1) == Token.DOWN) {
                        match(input, Token.DOWN, null);
                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:224:13: ( 'cast' )?
                        int alt13 = 2;
                        int LA13_0 = input.LA(1);
                        if ((LA13_0 == Cast)) {
                            alt13 = 1;
                        }
                        switch (alt13) {
                            case 1:
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:224:14: 'cast'
                            {
                                match(input, Cast, FOLLOW_Cast_in_typeModifier967);
                                retval.prefixModifiers.add("cast");
                            }
                            break;

                        }

                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:225:13: ( '!' )?
                        int alt14 = 2;
                        int LA14_0 = input.LA(1);
                        if ((LA14_0 == LogicNot)) {
                            alt14 = 1;
                        }
                        switch (alt14) {
                            case 1:
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:225:14: '!'
                            {
                                match(input, LogicNot, FOLLOW_LogicNot_in_typeModifier986);
                                retval.suffixModifiers.add("!");
                            }
                            break;

                        }

                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:226:13: ( '?' )?
                        int alt15 = 2;
                        int LA15_0 = input.LA(1);
                        if ((LA15_0 == QuestionMark)) {
                            alt15 = 1;
                        }
                        switch (alt15) {
                            case 1:
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:226:14: '?'
                            {
                                match(input, QuestionMark, FOLLOW_QuestionMark_in_typeModifier1009);
                                retval.suffixModifiers.add("?");
                            }
                            break;

                        }

                        match(input, Token.UP, null);
                    }

                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:228:9: TYPE_MODIFIER
                {
                    match(input, TYPE_MODIFIER, FOLLOW_TYPE_MODIFIER_in_typeModifier1036);
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:231:1: returnTypesOrUnknown : allTypesOrUnknown ->
    // {$allTypesOrUnknown.st};
    public final TSPHPTranslatorWalker.returnTypesOrUnknown_return returnTypesOrUnknown() throws RecognitionException {
        TSPHPTranslatorWalker.returnTypesOrUnknown_return retval = new TSPHPTranslatorWalker
                .returnTypesOrUnknown_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope allTypesOrUnknown18 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:232:5: ( allTypesOrUnknown ->
            // {$allTypesOrUnknown.st})
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:232:9: allTypesOrUnknown
            {
                pushFollow(FOLLOW_allTypesOrUnknown_in_returnTypesOrUnknown1057);
                allTypesOrUnknown18 = allTypesOrUnknown();
                state._fsp--;

                // TEMPLATE REWRITE
                // 232:27: -> {$allTypesOrUnknown.st}
                {
                    retval.st = (allTypesOrUnknown18 != null ? ((StringTemplate) allTypesOrUnknown18.getTemplate()) :
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:237:1: allTypesOrUnknown : ( allTypes -> {$allTypes
    // .st}| '?' -> {%{\"?\"}});
    public final TSPHPTranslatorWalker.allTypesOrUnknown_return allTypesOrUnknown() throws RecognitionException {
        TSPHPTranslatorWalker.allTypesOrUnknown_return retval = new TSPHPTranslatorWalker.allTypesOrUnknown_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope allTypes19 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:238:5: ( allTypes -> {$allTypes.st}| '?' ->
            // {%{\"?\"}})
            int alt17 = 2;
            int LA17_0 = input.LA(1);
            if ((LA17_0 == TypeArray || LA17_0 == TYPE_NAME || (LA17_0 >= TypeBool && LA17_0 <= TypeMixed) || (LA17_0
                    >= TypeResource && LA17_0 <= TypeString))) {
                alt17 = 1;
            } else if ((LA17_0 == QuestionMark)) {
                alt17 = 2;
            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 17, 0, input);
                throw nvae;
            }

            switch (alt17) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:238:9: allTypes
                {
                    pushFollow(FOLLOW_allTypes_in_allTypesOrUnknown1094);
                    allTypes19 = allTypes();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 238:18: -> {$allTypes.st}
                    {
                        retval.st = (allTypes19 != null ? ((StringTemplate) allTypes19.getTemplate()) : null);
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:239:9: '?'
                {
                    match(input, QuestionMark, FOLLOW_QuestionMark_in_allTypesOrUnknown1108);
                    // TEMPLATE REWRITE
                    // 239:18: -> {%{\"?\"}}
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:242:1: allTypes : ( primitiveTypes ->
    // {$primitiveTypes.st}| TYPE_NAME -> {%{$TYPE_NAME.text}});
    public final TSPHPTranslatorWalker.allTypes_return allTypes() throws RecognitionException {
        TSPHPTranslatorWalker.allTypes_return retval = new TSPHPTranslatorWalker.allTypes_return();
        retval.start = input.LT(1);

        ITSPHPAst TYPE_NAME21 = null;
        TreeRuleReturnScope primitiveTypes20 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:243:5: ( primitiveTypes -> {$primitiveTypes
            // .st}| TYPE_NAME -> {%{$TYPE_NAME.text}})
            int alt18 = 2;
            int LA18_0 = input.LA(1);
            if ((LA18_0 == TypeArray || (LA18_0 >= TypeBool && LA18_0 <= TypeMixed) || (LA18_0 >= TypeResource &&
                    LA18_0 <= TypeString))) {
                alt18 = 1;
            } else if ((LA18_0 == TYPE_NAME)) {
                alt18 = 2;
            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 18, 0, input);
                throw nvae;
            }

            switch (alt18) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:243:9: primitiveTypes
                {
                    pushFollow(FOLLOW_primitiveTypes_in_allTypes1136);
                    primitiveTypes20 = primitiveTypes();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 243:24: -> {$primitiveTypes.st}
                    {
                        retval.st = (primitiveTypes20 != null ? ((StringTemplate) primitiveTypes20.getTemplate()) :
                                null);
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:244:9: TYPE_NAME
                {
                    TYPE_NAME21 = (ITSPHPAst) match(input, TYPE_NAME, FOLLOW_TYPE_NAME_in_allTypes1151);
                    // TEMPLATE REWRITE
                    // 244:24: -> {%{$TYPE_NAME.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TYPE_NAME21 != null ? TYPE_NAME21.getText() :
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:247:1: primitiveTypes : ( scalarTypes ->
    // {$scalarTypes.st}| TypeArray -> {%{$TypeArray.text}}| TypeResource -> {%{$TypeResource.text}}| TypeMixed ->
    // {%{$TypeMixed.text}});
    public final TSPHPTranslatorWalker.primitiveTypes_return primitiveTypes() throws RecognitionException {
        TSPHPTranslatorWalker.primitiveTypes_return retval = new TSPHPTranslatorWalker.primitiveTypes_return();
        retval.start = input.LT(1);

        ITSPHPAst TypeArray23 = null;
        ITSPHPAst TypeResource24 = null;
        ITSPHPAst TypeMixed25 = null;
        TreeRuleReturnScope scalarTypes22 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:248:5: ( scalarTypes -> {$scalarTypes.st}|
            // TypeArray -> {%{$TypeArray.text}}| TypeResource -> {%{$TypeResource.text}}| TypeMixed -> {%{$TypeMixed
            // .text}})
            int alt19 = 4;
            switch (input.LA(1)) {
                case TypeBool:
                case TypeFloat:
                case TypeInt:
                case TypeString: {
                    alt19 = 1;
                }
                break;
                case TypeArray: {
                    alt19 = 2;
                }
                break;
                case TypeResource: {
                    alt19 = 3;
                }
                break;
                case TypeMixed: {
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
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:248:9: scalarTypes
                {
                    pushFollow(FOLLOW_scalarTypes_in_primitiveTypes1183);
                    scalarTypes22 = scalarTypes();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 248:22: -> {$scalarTypes.st}
                    {
                        retval.st = (scalarTypes22 != null ? ((StringTemplate) scalarTypes22.getTemplate()) : null);
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:249:9: TypeArray
                {
                    TypeArray23 = (ITSPHPAst) match(input, TypeArray, FOLLOW_TypeArray_in_primitiveTypes1198);
                    // TEMPLATE REWRITE
                    // 249:22: -> {%{$TypeArray.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeArray23 != null ? TypeArray23.getText() :
                                null));
                    }


                }
                break;
                case 3:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:250:9: TypeResource
                {
                    TypeResource24 = (ITSPHPAst) match(input, TypeResource, FOLLOW_TypeResource_in_primitiveTypes1215);
                    // TEMPLATE REWRITE
                    // 250:22: -> {%{$TypeResource.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeResource24 != null ? TypeResource24.getText
                                () : null));
                    }


                }
                break;
                case 4:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:251:9: TypeMixed
                {
                    TypeMixed25 = (ITSPHPAst) match(input, TypeMixed, FOLLOW_TypeMixed_in_primitiveTypes1229);
                    // TEMPLATE REWRITE
                    // 251:22: -> {%{$TypeMixed.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeMixed25 != null ? TypeMixed25.getText() :
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


    public static class scalarTypesOrUnknown_return extends TreeRuleReturnScope
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


    // $ANTLR start "scalarTypesOrUnknown"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:260:1: scalarTypesOrUnknown : ( scalarTypes ->
    // {$scalarTypes.st}| '?' -> {%{\"?\"}});
    public final TSPHPTranslatorWalker.scalarTypesOrUnknown_return scalarTypesOrUnknown() throws RecognitionException {
        TSPHPTranslatorWalker.scalarTypesOrUnknown_return retval = new TSPHPTranslatorWalker
                .scalarTypesOrUnknown_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope scalarTypes26 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:261:5: ( scalarTypes -> {$scalarTypes.st}|
            // '?' -> {%{\"?\"}})
            int alt20 = 2;
            int LA20_0 = input.LA(1);
            if (((LA20_0 >= TypeBool && LA20_0 <= TypeInt) || LA20_0 == TypeString)) {
                alt20 = 1;
            } else if ((LA20_0 == QuestionMark)) {
                alt20 = 2;
            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 20, 0, input);
                throw nvae;
            }

            switch (alt20) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:261:9: scalarTypes
                {
                    pushFollow(FOLLOW_scalarTypes_in_scalarTypesOrUnknown1256);
                    scalarTypes26 = scalarTypes();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 261:21: -> {$scalarTypes.st}
                    {
                        retval.st = (scalarTypes26 != null ? ((StringTemplate) scalarTypes26.getTemplate()) : null);
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:262:9: '?'
                {
                    match(input, QuestionMark, FOLLOW_QuestionMark_in_scalarTypesOrUnknown1270);
                    // TEMPLATE REWRITE
                    // 262:21: -> {%{\"?\"}}
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
    // $ANTLR end "scalarTypesOrUnknown"


    public static class scalarTypes_return extends TreeRuleReturnScope
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


    // $ANTLR start "scalarTypes"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:265:1: scalarTypes : ( TypeBool -> {%{$TypeBool
    // .text}}| TypeInt -> {%{$TypeInt.text}}| TypeFloat -> {%{$TypeFloat.text}}| TypeString -> {%{$TypeString.text}});
    public final TSPHPTranslatorWalker.scalarTypes_return scalarTypes() throws RecognitionException {
        TSPHPTranslatorWalker.scalarTypes_return retval = new TSPHPTranslatorWalker.scalarTypes_return();
        retval.start = input.LT(1);

        ITSPHPAst TypeBool27 = null;
        ITSPHPAst TypeInt28 = null;
        ITSPHPAst TypeFloat29 = null;
        ITSPHPAst TypeString30 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:266:5: ( TypeBool -> {%{$TypeBool.text}}|
            // TypeInt -> {%{$TypeInt.text}}| TypeFloat -> {%{$TypeFloat.text}}| TypeString -> {%{$TypeString.text}})
            int alt21 = 4;
            switch (input.LA(1)) {
                case TypeBool: {
                    alt21 = 1;
                }
                break;
                case TypeInt: {
                    alt21 = 2;
                }
                break;
                case TypeFloat: {
                    alt21 = 3;
                }
                break;
                case TypeString: {
                    alt21 = 4;
                }
                break;
                default:
                    NoViableAltException nvae =
                            new NoViableAltException("", 21, 0, input);
                    throw nvae;
            }
            switch (alt21) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:266:9: TypeBool
                {
                    TypeBool27 = (ITSPHPAst) match(input, TypeBool, FOLLOW_TypeBool_in_scalarTypes1301);
                    // TEMPLATE REWRITE
                    // 266:21: -> {%{$TypeBool.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeBool27 != null ? TypeBool27.getText() : null));
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:267:9: TypeInt
                {
                    TypeInt28 = (ITSPHPAst) match(input, TypeInt, FOLLOW_TypeInt_in_scalarTypes1318);
                    // TEMPLATE REWRITE
                    // 267:21: -> {%{$TypeInt.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeInt28 != null ? TypeInt28.getText() : null));
                    }


                }
                break;
                case 3:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:268:9: TypeFloat
                {
                    TypeFloat29 = (ITSPHPAst) match(input, TypeFloat, FOLLOW_TypeFloat_in_scalarTypes1336);
                    // TEMPLATE REWRITE
                    // 268:21: -> {%{$TypeFloat.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeFloat29 != null ? TypeFloat29.getText() :
                                null));
                    }


                }
                break;
                case 4:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:269:9: TypeString
                {
                    TypeString30 = (ITSPHPAst) match(input, TypeString, FOLLOW_TypeString_in_scalarTypes1352);
                    // TEMPLATE REWRITE
                    // 269:21: -> {%{$TypeString.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (TypeString30 != null ? TypeString30.getText() :
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:377:1: formalParameters : ( ^( PARAMETER_LIST
    // (param= paramDeclaration )+ ) -> parameterList(declarations=declarations)| PARAMETER_LIST -> {null});
    public final TSPHPTranslatorWalker.formalParameters_return formalParameters() throws RecognitionException {
        TSPHPTranslatorWalker.formalParameters_return retval = new TSPHPTranslatorWalker.formalParameters_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope param = null;


        List<ParameterDto> parameterDtos = new ArrayList<>();
        List<StringTemplate> declarations = new ArrayList<>();

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:382:5: ( ^( PARAMETER_LIST (param=
            // paramDeclaration )+ ) -> parameterList(declarations=declarations)| PARAMETER_LIST -> {null})
            int alt23 = 2;
            int LA23_0 = input.LA(1);
            if ((LA23_0 == PARAMETER_LIST)) {
                int LA23_1 = input.LA(2);
                if ((LA23_1 == DOWN)) {
                    alt23 = 1;
                } else if ((LA23_1 == BLOCK)) {
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
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:382:9: ^( PARAMETER_LIST (param=
                    // paramDeclaration )+ )
                {
                    match(input, PARAMETER_LIST, FOLLOW_PARAMETER_LIST_in_formalParameters1388);
                    match(input, Token.DOWN, null);
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:382:26: (param= paramDeclaration )+
                    int cnt22 = 0;
                    loop22:
                    while (true) {
                        int alt22 = 2;
                        int LA22_0 = input.LA(1);
                        if ((LA22_0 == PARAMETER_DECLARATION)) {
                            alt22 = 1;
                        }

                        switch (alt22) {
                            case 1:
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:382:27: param=
                                // paramDeclaration
                            {
                                pushFollow(FOLLOW_paramDeclaration_in_formalParameters1393);
                                param = paramDeclaration();
                                state._fsp--;

                                parameterDtos.add((param != null ? ((TSPHPTranslatorWalker.paramDeclaration_return)
                                        param).parameterDto : null));
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
                    // 405:6: -> parameterList(declarations=declarations)
                    {
                        retval.st = templateLib.getInstanceOf("parameterList", new STAttrMap().put("declarations",
                                declarations));
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:406:9: PARAMETER_LIST
                {
                    match(input, PARAMETER_LIST, FOLLOW_PARAMETER_LIST_in_formalParameters1430);
                    // TEMPLATE REWRITE
                    // 406:24: -> {null}
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:410:1: block returns [List<Object> instructions] :
    // ( ^( BLOCK (instr+= instruction )+ ) | BLOCK );
    public final TSPHPTranslatorWalker.block_return block() throws RecognitionException {
        TSPHPTranslatorWalker.block_return retval = new TSPHPTranslatorWalker.block_return();
        retval.start = input.LT(1);

        List<Object> list_instr = null;
        RuleReturnScope instr = null;
        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:411:5: ( ^( BLOCK (instr+= instruction )+ )
            // | BLOCK )
            int alt25 = 2;
            int LA25_0 = input.LA(1);
            if ((LA25_0 == BLOCK)) {
                int LA25_1 = input.LA(2);
                if ((LA25_1 == DOWN)) {
                    alt25 = 1;
                } else if ((LA25_1 == UP)) {
                    alt25 = 2;
                } else {
                    int nvaeMark = input.mark();
                    try {
                        input.consume();
                        NoViableAltException nvae =
                                new NoViableAltException("", 25, 1, input);
                        throw nvae;
                    } finally {
                        input.rewind(nvaeMark);
                    }
                }

            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 25, 0, input);
                throw nvae;
            }

            switch (alt25) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:411:9: ^( BLOCK (instr+=
                    // instruction )+ )
                {
                    match(input, BLOCK, FOLLOW_BLOCK_in_block1458);
                    match(input, Token.DOWN, null);
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:411:22: (instr+= instruction )+
                    int cnt24 = 0;
                    loop24:
                    while (true) {
                        int alt24 = 2;
                        int LA24_0 = input.LA(1);
                        if ((LA24_0 == EXPRESSION || LA24_0 == If || LA24_0 == Switch || LA24_0 ==
                                VARIABLE_DECLARATION_LIST)) {
                            alt24 = 1;
                        }

                        switch (alt24) {
                            case 1:
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:411:22: instr+=
                                // instruction
                            {
                                pushFollow(FOLLOW_instruction_in_block1462);
                                instr = instruction();
                                state._fsp--;

                                if (list_instr == null) {
                                    list_instr = new ArrayList<Object>();
                                }
                                list_instr.add(instr.getTemplate());
                            }
                            break;

                            default:
                                if (cnt24 >= 1) {
                                    break loop24;
                                }
                                EarlyExitException eee = new EarlyExitException(24, input);
                                throw eee;
                        }
                        cnt24++;
                    }

                    match(input, Token.UP, null);

                    retval.instructions = list_instr;
                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:412:9: BLOCK
                {
                    match(input, BLOCK, FOLLOW_BLOCK_in_block1476);
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:472:1: paramDeclaration returns [ParameterDto
    // parameterDto] : ^( PARAMETER_DECLARATION ^( TYPE typeModifier allTypesOrUnknown ) (varId= VariableId | ^
    // (varId= VariableId defaultValue= unaryPrimitiveAtom ) ) ) ;
    public final TSPHPTranslatorWalker.paramDeclaration_return paramDeclaration() throws RecognitionException {
        TSPHPTranslatorWalker.paramDeclaration_return retval = new TSPHPTranslatorWalker.paramDeclaration_return();
        retval.start = input.LT(1);

        ITSPHPAst varId = null;
        TreeRuleReturnScope defaultValue = null;
        TreeRuleReturnScope typeModifier31 = null;
        TreeRuleReturnScope allTypesOrUnknown32 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:473:5: ( ^( PARAMETER_DECLARATION ^( TYPE
            // typeModifier allTypesOrUnknown ) (varId= VariableId | ^(varId= VariableId defaultValue=
            // unaryPrimitiveAtom ) ) ) )
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:473:9: ^( PARAMETER_DECLARATION ^( TYPE
            // typeModifier allTypesOrUnknown ) (varId= VariableId | ^(varId= VariableId defaultValue=
            // unaryPrimitiveAtom ) ) )
            {
                match(input, PARAMETER_DECLARATION, FOLLOW_PARAMETER_DECLARATION_in_paramDeclaration1503);
                match(input, Token.DOWN, null);
                match(input, TYPE, FOLLOW_TYPE_in_paramDeclaration1518);
                match(input, Token.DOWN, null);
                pushFollow(FOLLOW_typeModifier_in_paramDeclaration1520);
                typeModifier31 = typeModifier();
                state._fsp--;

                pushFollow(FOLLOW_allTypesOrUnknown_in_paramDeclaration1522);
                allTypesOrUnknown32 = allTypesOrUnknown();
                state._fsp--;

                match(input, Token.UP, null);

                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:475:13: (varId= VariableId | ^(varId=
                // VariableId defaultValue= unaryPrimitiveAtom ) )
                int alt26 = 2;
                int LA26_0 = input.LA(1);
                if ((LA26_0 == VariableId)) {
                    int LA26_1 = input.LA(2);
                    if ((LA26_1 == DOWN)) {
                        alt26 = 2;
                    } else if ((LA26_1 == UP)) {
                        alt26 = 1;
                    } else {
                        int nvaeMark = input.mark();
                        try {
                            input.consume();
                            NoViableAltException nvae =
                                    new NoViableAltException("", 26, 1, input);
                            throw nvae;
                        } finally {
                            input.rewind(nvaeMark);
                        }
                    }

                } else {
                    NoViableAltException nvae =
                            new NoViableAltException("", 26, 0, input);
                    throw nvae;
                }

                switch (alt26) {
                    case 1:
                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:475:17: varId= VariableId
                    {
                        varId = (ITSPHPAst) match(input, VariableId, FOLLOW_VariableId_in_paramDeclaration1543);
                    }
                    break;
                    case 2:
                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:476:17: ^(varId= VariableId
                        // defaultValue= unaryPrimitiveAtom )
                    {
                        varId = (ITSPHPAst) match(input, VariableId, FOLLOW_VariableId_in_paramDeclaration1564);
                        match(input, Token.DOWN, null);
                        pushFollow(FOLLOW_unaryPrimitiveAtom_in_paramDeclaration1568);
                        defaultValue = unaryPrimitiveAtom();
                        state._fsp--;

                        match(input, Token.UP, null);

                    }
                    break;

                }

                match(input, Token.UP, null);


                retval.parameterDto = new ParameterDto(
                        (typeModifier31 != null ? ((TSPHPTranslatorWalker.typeModifier_return) typeModifier31)
                                .prefixModifiers : null),
                        (allTypesOrUnknown32 != null ? ((StringTemplate) allTypesOrUnknown32.getTemplate()) : null),
                        (typeModifier31 != null ? ((TSPHPTranslatorWalker.typeModifier_return) typeModifier31)
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:489:1: functionDeclaration : ^( 'function'
    // FUNCTION_MODIFIER ^( TYPE typeModifier returnTypesOrUnknown ) Identifier formalParameters block ) -> method
    // (modifier=nullreturnType=%type(\r\n prefixModifiers={$typeModifier.prefixModifiers},
    // \r\n type={$returnTypesOrUnknown.st},\r\n suffixModifiers={$typeModifier.suffixModifiers}\r\n )
    // identifier=getMethodName($Identifier.text)params=$formalParameters.stbody=$block.instructions);
    public final TSPHPTranslatorWalker.functionDeclaration_return functionDeclaration() throws RecognitionException {
        TSPHPTranslatorWalker.functionDeclaration_return retval = new TSPHPTranslatorWalker
                .functionDeclaration_return();
        retval.start = input.LT(1);

        ITSPHPAst Identifier35 = null;
        TreeRuleReturnScope typeModifier33 = null;
        TreeRuleReturnScope returnTypesOrUnknown34 = null;
        TreeRuleReturnScope formalParameters36 = null;
        TreeRuleReturnScope block37 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:490:5: ( ^( 'function' FUNCTION_MODIFIER ^(
            // TYPE typeModifier returnTypesOrUnknown ) Identifier formalParameters block ) -> method
            // (modifier=nullreturnType=%type(\r\n prefixModifiers={$typeModifier.prefixModifiers},
            // \r\n type={$returnTypesOrUnknown.st},\r\n suffixModifiers={$typeModifier.suffixModifiers}\r\n )
            // identifier=getMethodName($Identifier.text)params=$formalParameters.stbody=$block.instructions))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:490:9: ^( 'function' FUNCTION_MODIFIER ^(
            // TYPE typeModifier returnTypesOrUnknown ) Identifier formalParameters block )
            {
                match(input, Function, FOLLOW_Function_in_functionDeclaration1623);
                match(input, Token.DOWN, null);
                match(input, FUNCTION_MODIFIER, FOLLOW_FUNCTION_MODIFIER_in_functionDeclaration1637);
                match(input, TYPE, FOLLOW_TYPE_in_functionDeclaration1652);
                match(input, Token.DOWN, null);
                pushFollow(FOLLOW_typeModifier_in_functionDeclaration1654);
                typeModifier33 = typeModifier();
                state._fsp--;

                pushFollow(FOLLOW_returnTypesOrUnknown_in_functionDeclaration1656);
                returnTypesOrUnknown34 = returnTypesOrUnknown();
                state._fsp--;

                match(input, Token.UP, null);

                Identifier35 = (ITSPHPAst) match(input, Identifier, FOLLOW_Identifier_in_functionDeclaration1671);
                pushFollow(FOLLOW_formalParameters_in_functionDeclaration1685);
                formalParameters36 = formalParameters();
                state._fsp--;

                pushFollow(FOLLOW_block_in_functionDeclaration1699);
                block37 = block();
                state._fsp--;

                match(input, Token.UP, null);

                // TEMPLATE REWRITE
                // 497:9: -> method(modifier=nullreturnType=%type(\r\n prefixModifiers={$typeModifier
                // .prefixModifiers},\r\n type={$returnTypesOrUnknown.st},\r\n suffixModifiers={$typeModifier
                // .suffixModifiers}\r\n )identifier=getMethodName($Identifier.text)params=$formalParameters
                // .stbody=$block.instructions)
                {
                    retval.st = templateLib.getInstanceOf("method", new STAttrMap().put("modifier",
                            null).put("returnType", templateLib.getInstanceOf("type",
                            new STAttrMap().put("prefixModifiers", (typeModifier33 != null ? ((TSPHPTranslatorWalker
                                    .typeModifier_return) typeModifier33).prefixModifiers : null)).put("type",
                                    (returnTypesOrUnknown34 != null ? ((StringTemplate) returnTypesOrUnknown34
                                            .getTemplate()) : null)).put("suffixModifiers",
                                    (typeModifier33 != null ? ((TSPHPTranslatorWalker.typeModifier_return)
                                            typeModifier33).suffixModifiers : null)))).put("identifier",
                            getMethodName((Identifier35 != null ? Identifier35.getText() : null))).put("params",
                            (formalParameters36 != null ? ((StringTemplate) formalParameters36.getTemplate()) : null)
                    ).put("body", (block37 != null ? ((TSPHPTranslatorWalker.block_return) block37).instructions :
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
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:510:1: instruction : ( localVariableDeclarationList
    // -> {$localVariableDeclarationList.st}| ifCondition -> {$ifCondition.st}| switchCondition -> {$switchCondition
    // .st}| ^( EXPRESSION ( expression )? ) -> expression(expression=$expression.st));
    public final TSPHPTranslatorWalker.instruction_return instruction() throws RecognitionException {
        TSPHPTranslatorWalker.instruction_return retval = new TSPHPTranslatorWalker.instruction_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope localVariableDeclarationList38 = null;
        TreeRuleReturnScope ifCondition39 = null;
        TreeRuleReturnScope switchCondition40 = null;
        TreeRuleReturnScope expression41 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:511:5: ( localVariableDeclarationList ->
            // {$localVariableDeclarationList.st}| ifCondition -> {$ifCondition.st}| switchCondition ->
            // {$switchCondition.st}| ^( EXPRESSION ( expression )? ) -> expression(expression=$expression.st))
            int alt28 = 4;
            switch (input.LA(1)) {
                case VARIABLE_DECLARATION_LIST: {
                    alt28 = 1;
                }
                break;
                case If: {
                    alt28 = 2;
                }
                break;
                case Switch: {
                    alt28 = 3;
                }
                break;
                case EXPRESSION: {
                    alt28 = 4;
                }
                break;
                default:
                    NoViableAltException nvae =
                            new NoViableAltException("", 28, 0, input);
                    throw nvae;
            }
            switch (alt28) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:511:9: localVariableDeclarationList
                {
                    pushFollow(FOLLOW_localVariableDeclarationList_in_instruction1839);
                    localVariableDeclarationList38 = localVariableDeclarationList();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 511:41: -> {$localVariableDeclarationList.st}
                    {
                        retval.st = (localVariableDeclarationList38 != null ? ((StringTemplate)
                                localVariableDeclarationList38.getTemplate()) : null);
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:512:9: ifCondition
                {
                    pushFollow(FOLLOW_ifCondition_in_instruction1856);
                    ifCondition39 = ifCondition();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 512:41: -> {$ifCondition.st}
                    {
                        retval.st = (ifCondition39 != null ? ((StringTemplate) ifCondition39.getTemplate()) : null);
                    }


                }
                break;
                case 3:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:513:9: switchCondition
                {
                    pushFollow(FOLLOW_switchCondition_in_instruction1890);
                    switchCondition40 = switchCondition();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 513:41: -> {$switchCondition.st}
                    {
                        retval.st = (switchCondition40 != null ? ((StringTemplate) switchCondition40.getTemplate()) :
                                null);
                    }


                }
                break;
                case 4:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:520:9: ^( EXPRESSION ( expression )? )
                {
                    match(input, EXPRESSION, FOLLOW_EXPRESSION_in_instruction1951);
                    if (input.LA(1) == Token.DOWN) {
                        match(input, Token.DOWN, null);
                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:520:22: ( expression )?
                        int alt27 = 2;
                        int LA27_0 = input.LA(1);
                        if (((LA27_0 >= LogicXorWeak && LA27_0 <= ShiftRightAssign) || (LA27_0 >= LogicOr && LA27_0
                                <= Modulo) || (LA27_0 >= Bool && LA27_0 <= Null) || LA27_0 == CONSTANT || LA27_0 ==
                                VariableId)) {
                            alt27 = 1;
                        }
                        switch (alt27) {
                            case 1:
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:520:22: expression
                            {
                                pushFollow(FOLLOW_expression_in_instruction1953);
                                expression41 = expression();
                                state._fsp--;

                            }
                            break;

                        }

                        match(input, Token.UP, null);
                    }

                    // TEMPLATE REWRITE
                    // 520:41: -> expression(expression=$expression.st)
                    {
                        retval.st = templateLib.getInstanceOf("expression", new STAttrMap().put("expression",
                                (expression41 != null ? ((StringTemplate) expression41.getTemplate()) : null)));
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
    // $ANTLR end "instruction"


    public static class ifCondition_return extends TreeRuleReturnScope
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


    // $ANTLR start "ifCondition"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:532:1: ifCondition : ^( 'if' expression ifBlock=
    // blockConditional (elseBlock= blockConditional )? ) -> if(condition=$expression.stifBlock=$ifBlock
    // .instructionselseBlock=$elseBlock.instructions);
    public final TSPHPTranslatorWalker.ifCondition_return ifCondition() throws RecognitionException {
        TSPHPTranslatorWalker.ifCondition_return retval = new TSPHPTranslatorWalker.ifCondition_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope ifBlock = null;
        TreeRuleReturnScope elseBlock = null;
        TreeRuleReturnScope expression42 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:533:5: ( ^( 'if' expression ifBlock=
            // blockConditional (elseBlock= blockConditional )? ) -> if(condition=$expression.stifBlock=$ifBlock
            // .instructionselseBlock=$elseBlock.instructions))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:533:9: ^( 'if' expression ifBlock=
            // blockConditional (elseBlock= blockConditional )? )
            {
                match(input, If, FOLLOW_If_in_ifCondition2031);
                match(input, Token.DOWN, null);
                pushFollow(FOLLOW_expression_in_ifCondition2045);
                expression42 = expression();
                state._fsp--;

                pushFollow(FOLLOW_blockConditional_in_ifCondition2062);
                ifBlock = blockConditional();
                state._fsp--;

                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:536:22: (elseBlock= blockConditional )?
                int alt29 = 2;
                int LA29_0 = input.LA(1);
                if ((LA29_0 == BLOCK_CONDITIONAL)) {
                    alt29 = 1;
                }
                switch (alt29) {
                    case 1:
                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:536:22: elseBlock=
                        // blockConditional
                    {
                        pushFollow(FOLLOW_blockConditional_in_ifCondition2078);
                        elseBlock = blockConditional();
                        state._fsp--;

                    }
                    break;

                }

                match(input, Token.UP, null);

                // TEMPLATE REWRITE
                // 538:9: -> if(condition=$expression.stifBlock=$ifBlock.instructionselseBlock=$elseBlock.instructions)
                {
                    retval.st = templateLib.getInstanceOf("if", new STAttrMap().put("condition",
                            (expression42 != null ? ((StringTemplate) expression42.getTemplate()) : null)).put
                            ("ifBlock", (ifBlock != null ? ((TSPHPTranslatorWalker.blockConditional_return) ifBlock)
                                    .instructions : null)).put("elseBlock",
                            (elseBlock != null ? ((TSPHPTranslatorWalker.blockConditional_return) elseBlock)
                                    .instructions : null)));
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
    // $ANTLR end "ifCondition"


    public static class blockConditional_return extends TreeRuleReturnScope
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


    // $ANTLR start "blockConditional"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:541:1: blockConditional returns [List<Object>
    // instructions] : ^( BLOCK_CONDITIONAL (instr+= instruction )* ) ;
    public final TSPHPTranslatorWalker.blockConditional_return blockConditional() throws RecognitionException {
        TSPHPTranslatorWalker.blockConditional_return retval = new TSPHPTranslatorWalker.blockConditional_return();
        retval.start = input.LT(1);

        List<Object> list_instr = null;
        RuleReturnScope instr = null;
        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:542:5: ( ^( BLOCK_CONDITIONAL (instr+=
            // instruction )* ) )
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:542:9: ^( BLOCK_CONDITIONAL (instr+=
            // instruction )* )
            {
                match(input, BLOCK_CONDITIONAL, FOLLOW_BLOCK_CONDITIONAL_in_blockConditional2139);
                if (input.LA(1) == Token.DOWN) {
                    match(input, Token.DOWN, null);
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:542:34: (instr+= instruction )*
                    loop30:
                    while (true) {
                        int alt30 = 2;
                        int LA30_0 = input.LA(1);
                        if ((LA30_0 == EXPRESSION || LA30_0 == If || LA30_0 == Switch || LA30_0 ==
                                VARIABLE_DECLARATION_LIST)) {
                            alt30 = 1;
                        }

                        switch (alt30) {
                            case 1:
                                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:542:34: instr+=
                                // instruction
                            {
                                pushFollow(FOLLOW_instruction_in_blockConditional2143);
                                instr = instruction();
                                state._fsp--;

                                if (list_instr == null) {
                                    list_instr = new ArrayList<Object>();
                                }
                                list_instr.add(instr.getTemplate());
                            }
                            break;

                            default:
                                break loop30;
                        }
                    }

                    match(input, Token.UP, null);
                }

                retval.instructions = list_instr;
            }

        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "blockConditional"


    public static class switchCondition_return extends TreeRuleReturnScope
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


    // $ANTLR start "switchCondition"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:545:1: switchCondition : ^( 'switch' expression
    // (content+= switchContent )* ) -> switch(condition=$expression.stcontent=$content);
    public final TSPHPTranslatorWalker.switchCondition_return switchCondition() throws RecognitionException {
        TSPHPTranslatorWalker.switchCondition_return retval = new TSPHPTranslatorWalker.switchCondition_return();
        retval.start = input.LT(1);

        List<Object> list_content = null;
        TreeRuleReturnScope expression43 = null;
        RuleReturnScope content = null;
        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:546:5: ( ^( 'switch' expression (content+=
            // switchContent )* ) -> switch(condition=$expression.stcontent=$content))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:546:9: ^( 'switch' expression (content+=
            // switchContent )* )
            {
                match(input, Switch, FOLLOW_Switch_in_switchCondition2171);
                match(input, Token.DOWN, null);
                pushFollow(FOLLOW_expression_in_switchCondition2173);
                expression43 = expression();
                state._fsp--;

                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:546:38: (content+= switchContent )*
                loop31:
                while (true) {
                    int alt31 = 2;
                    int LA31_0 = input.LA(1);
                    if ((LA31_0 == SWITCH_CASES)) {
                        alt31 = 1;
                    }

                    switch (alt31) {
                        case 1:
                            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:546:38: content+=
                            // switchContent
                        {
                            pushFollow(FOLLOW_switchContent_in_switchCondition2177);
                            content = switchContent();
                            state._fsp--;

                            if (list_content == null) {
                                list_content = new ArrayList<Object>();
                            }
                            list_content.add(content.getTemplate());
                        }
                        break;

                        default:
                            break loop31;
                    }
                }

                match(input, Token.UP, null);

                // TEMPLATE REWRITE
                // 547:9: -> switch(condition=$expression.stcontent=$content)
                {
                    retval.st = templateLib.getInstanceOf("switch", new STAttrMap().put("condition",
                            (expression43 != null ? ((StringTemplate) expression43.getTemplate()) : null)).put
                            ("content", list_content));
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
    // $ANTLR end "switchCondition"


    public static class switchContent_return extends TreeRuleReturnScope
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


    // $ANTLR start "switchContent"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:550:1: switchContent : ^( SWITCH_CASES (labels+=
    // caseLabel )+ ) blockConditional -> switchContent(labels=$labelsblock=$blockConditional.instructions);
    public final TSPHPTranslatorWalker.switchContent_return switchContent() throws RecognitionException {
        TSPHPTranslatorWalker.switchContent_return retval = new TSPHPTranslatorWalker.switchContent_return();
        retval.start = input.LT(1);

        List<Object> list_labels = null;
        TreeRuleReturnScope blockConditional44 = null;
        RuleReturnScope labels = null;
        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:551:5: ( ^( SWITCH_CASES (labels+=
            // caseLabel )+ ) blockConditional -> switchContent(labels=$labelsblock=$blockConditional.instructions))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:551:9: ^( SWITCH_CASES (labels+= caseLabel
            // )+ ) blockConditional
            {
                match(input, SWITCH_CASES, FOLLOW_SWITCH_CASES_in_switchContent2222);
                match(input, Token.DOWN, null);
                // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:551:30: (labels+= caseLabel )+
                int cnt32 = 0;
                loop32:
                while (true) {
                    int alt32 = 2;
                    int LA32_0 = input.LA(1);
                    if (((LA32_0 >= LogicXorWeak && LA32_0 <= ShiftRightAssign) || (LA32_0 >= LogicOr && LA32_0 <=
                            Modulo) || (LA32_0 >= Bool && LA32_0 <= Null) || LA32_0 == CONSTANT || LA32_0 == Default
                            || LA32_0 == VariableId)) {
                        alt32 = 1;
                    }

                    switch (alt32) {
                        case 1:
                            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:551:30: labels+= caseLabel
                        {
                            pushFollow(FOLLOW_caseLabel_in_switchContent2226);
                            labels = caseLabel();
                            state._fsp--;

                            if (list_labels == null) {
                                list_labels = new ArrayList<Object>();
                            }
                            list_labels.add(labels.getTemplate());
                        }
                        break;

                        default:
                            if (cnt32 >= 1) {
                                break loop32;
                            }
                            EarlyExitException eee = new EarlyExitException(32, input);
                            throw eee;
                    }
                    cnt32++;
                }

                match(input, Token.UP, null);

                pushFollow(FOLLOW_blockConditional_in_switchContent2230);
                blockConditional44 = blockConditional();
                state._fsp--;

                // TEMPLATE REWRITE
                // 552:9: -> switchContent(labels=$labelsblock=$blockConditional.instructions)
                {
                    retval.st = templateLib.getInstanceOf("switchContent", new STAttrMap().put("labels",
                            list_labels).put("block", (blockConditional44 != null ? ((TSPHPTranslatorWalker
                            .blockConditional_return) blockConditional44).instructions : null)));
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
    // $ANTLR end "switchContent"


    public static class caseLabel_return extends TreeRuleReturnScope
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


    // $ANTLR start "caseLabel"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:555:1: caseLabel : ( expression -> caseLabel
    // (label=$expression.st)| Default -> {%{$Default.text+\":\"}});
    public final TSPHPTranslatorWalker.caseLabel_return caseLabel() throws RecognitionException {
        TSPHPTranslatorWalker.caseLabel_return retval = new TSPHPTranslatorWalker.caseLabel_return();
        retval.start = input.LT(1);

        ITSPHPAst Default46 = null;
        TreeRuleReturnScope expression45 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:556:5: ( expression -> caseLabel
            // (label=$expression.st)| Default -> {%{$Default.text+\":\"}})
            int alt33 = 2;
            int LA33_0 = input.LA(1);
            if (((LA33_0 >= LogicXorWeak && LA33_0 <= ShiftRightAssign) || (LA33_0 >= LogicOr && LA33_0 <= Modulo) ||
                    (LA33_0 >= Bool && LA33_0 <= Null) || LA33_0 == CONSTANT || LA33_0 == VariableId)) {
                alt33 = 1;
            } else if ((LA33_0 == Default)) {
                alt33 = 2;
            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 33, 0, input);
                throw nvae;
            }

            switch (alt33) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:556:9: expression
                {
                    pushFollow(FOLLOW_expression_in_caseLabel2275);
                    expression45 = expression();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 556:21: -> caseLabel(label=$expression.st)
                    {
                        retval.st = templateLib.getInstanceOf("caseLabel", new STAttrMap().put("label",
                                (expression45 != null ? ((StringTemplate) expression45.getTemplate()) : null)));
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:557:9: Default
                {
                    Default46 = (ITSPHPAst) match(input, Default, FOLLOW_Default_in_caseLabel2295);
                    // TEMPLATE REWRITE
                    // 557:21: -> {%{$Default.text+\":\"}}
                    {
                        retval.st = new StringTemplate(templateLib, (Default46 != null ? Default46.getText() : null)
                                + ":");
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
    // $ANTLR end "caseLabel"


    public static class expression_return extends TreeRuleReturnScope
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


    // $ANTLR start "expression"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:623:1: expression : ( atom -> {$atom.st}| operator
    // -> {$operator.st});
    public final TSPHPTranslatorWalker.expression_return expression() throws RecognitionException {
        TSPHPTranslatorWalker.expression_return retval = new TSPHPTranslatorWalker.expression_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope atom47 = null;
        TreeRuleReturnScope operator48 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:624:5: ( atom -> {$atom.st}| operator ->
            // {$operator.st})
            int alt34 = 2;
            int LA34_0 = input.LA(1);
            if (((LA34_0 >= Bool && LA34_0 <= Null) || LA34_0 == CONSTANT || LA34_0 == VariableId)) {
                alt34 = 1;
            } else if (((LA34_0 >= LogicXorWeak && LA34_0 <= ShiftRightAssign) || (LA34_0 >= LogicOr && LA34_0 <=
                    Modulo))) {
                alt34 = 2;
            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 34, 0, input);
                throw nvae;
            }

            switch (alt34) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:624:9: atom
                {
                    pushFollow(FOLLOW_atom_in_expression2331);
                    atom47 = atom();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 624:33: -> {$atom.st}
                    {
                        retval.st = (atom47 != null ? ((StringTemplate) atom47.getTemplate()) : null);
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:625:9: operator
                {
                    pushFollow(FOLLOW_operator_in_expression2364);
                    operator48 = operator();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 625:33: -> {$operator.st}
                    {
                        retval.st = (operator48 != null ? ((StringTemplate) operator48.getTemplate()) : null);
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
    // $ANTLR end "expression"


    public static class atom_return extends TreeRuleReturnScope
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


    // $ANTLR start "atom"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:638:1: atom : ( primitiveAtomWithConstant ->
    // {$primitiveAtomWithConstant.st}| VariableId -> {%{$VariableId.text}});
    public final TSPHPTranslatorWalker.atom_return atom() throws RecognitionException {
        TSPHPTranslatorWalker.atom_return retval = new TSPHPTranslatorWalker.atom_return();
        retval.start = input.LT(1);

        ITSPHPAst VariableId50 = null;
        TreeRuleReturnScope primitiveAtomWithConstant49 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:639:5: ( primitiveAtomWithConstant ->
            // {$primitiveAtomWithConstant.st}| VariableId -> {%{$VariableId.text}})
            int alt35 = 2;
            int LA35_0 = input.LA(1);
            if (((LA35_0 >= Bool && LA35_0 <= Null) || LA35_0 == CONSTANT)) {
                alt35 = 1;
            } else if ((LA35_0 == VariableId)) {
                alt35 = 2;
            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 35, 0, input);
                throw nvae;
            }

            switch (alt35) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:639:9: primitiveAtomWithConstant
                {
                    pushFollow(FOLLOW_primitiveAtomWithConstant_in_atom2454);
                    primitiveAtomWithConstant49 = primitiveAtomWithConstant();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 639:37: -> {$primitiveAtomWithConstant.st}
                    {
                        retval.st = (primitiveAtomWithConstant49 != null ? ((StringTemplate)
                                primitiveAtomWithConstant49.getTemplate()) : null);
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:640:9: VariableId
                {
                    VariableId50 = (ITSPHPAst) match(input, VariableId, FOLLOW_VariableId_in_atom2470);
                    // TEMPLATE REWRITE
                    // 640:37: -> {%{$VariableId.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (VariableId50 != null ? VariableId50.getText() :
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
    // $ANTLR end "atom"


    public static class primitiveAtomWithConstant_return extends TreeRuleReturnScope
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


    // $ANTLR start "primitiveAtomWithConstant"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:645:1: primitiveAtomWithConstant : ( Bool ->
    // {%{$Bool.text}}| Int -> {%{$Int.text}}| Float -> {%{$Float.text}}| String -> {%{$String.text}}| Null ->
    // {%{$Null.text}}| ^( TypeArray (keyValuePairs+= arrayKeyValue )* ) -> array(content=$keyValuePairs)| CONSTANT
    // -> {%{$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)}});
    public final TSPHPTranslatorWalker.primitiveAtomWithConstant_return primitiveAtomWithConstant() throws
            RecognitionException {
        TSPHPTranslatorWalker.primitiveAtomWithConstant_return retval = new TSPHPTranslatorWalker
                .primitiveAtomWithConstant_return();
        retval.start = input.LT(1);

        ITSPHPAst Bool51 = null;
        ITSPHPAst Int52 = null;
        ITSPHPAst Float53 = null;
        ITSPHPAst String54 = null;
        ITSPHPAst Null55 = null;
        ITSPHPAst CONSTANT56 = null;
        List<Object> list_keyValuePairs = null;
        RuleReturnScope keyValuePairs = null;
        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:646:5: ( Bool -> {%{$Bool.text}}| Int ->
            // {%{$Int.text}}| Float -> {%{$Float.text}}| String -> {%{$String.text}}| Null -> {%{$Null.text}}| ^(
            // TypeArray (keyValuePairs+= arrayKeyValue )* ) -> array(content=$keyValuePairs)| CONSTANT ->
            // {%{$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)}})
            int alt37 = 7;
            switch (input.LA(1)) {
                case Bool: {
                    alt37 = 1;
                }
                break;
                case Int: {
                    alt37 = 2;
                }
                break;
                case Float: {
                    alt37 = 3;
                }
                break;
                case String: {
                    alt37 = 4;
                }
                break;
                case Null: {
                    alt37 = 5;
                }
                break;
                case TypeArray: {
                    alt37 = 6;
                }
                break;
                case CONSTANT: {
                    alt37 = 7;
                }
                break;
                default:
                    NoViableAltException nvae =
                            new NoViableAltException("", 37, 0, input);
                    throw nvae;
            }
            switch (alt37) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:646:9: Bool
                {
                    Bool51 = (ITSPHPAst) match(input, Bool, FOLLOW_Bool_in_primitiveAtomWithConstant2531);
                    // TEMPLATE REWRITE
                    // 646:53: -> {%{$Bool.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (Bool51 != null ? Bool51.getText() : null));
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:647:9: Int
                {
                    Int52 = (ITSPHPAst) match(input, Int, FOLLOW_Int_in_primitiveAtomWithConstant2584);
                    // TEMPLATE REWRITE
                    // 647:53: -> {%{$Int.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (Int52 != null ? Int52.getText() : null));
                    }


                }
                break;
                case 3:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:648:9: Float
                {
                    Float53 = (ITSPHPAst) match(input, Float, FOLLOW_Float_in_primitiveAtomWithConstant2638);
                    // TEMPLATE REWRITE
                    // 648:53: -> {%{$Float.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (Float53 != null ? Float53.getText() : null));
                    }


                }
                break;
                case 4:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:649:9: String
                {
                    String54 = (ITSPHPAst) match(input, String, FOLLOW_String_in_primitiveAtomWithConstant2690);
                    // TEMPLATE REWRITE
                    // 649:53: -> {%{$String.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (String54 != null ? String54.getText() : null));
                    }


                }
                break;
                case 5:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:650:9: Null
                {
                    Null55 = (ITSPHPAst) match(input, Null, FOLLOW_Null_in_primitiveAtomWithConstant2741);
                    // TEMPLATE REWRITE
                    // 650:53: -> {%{$Null.text}}
                    {
                        retval.st = new StringTemplate(templateLib, (Null55 != null ? Null55.getText() : null));
                    }


                }
                break;
                case 6:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:651:9: ^( TypeArray
                    // (keyValuePairs+= arrayKeyValue )* )
                {
                    match(input, TypeArray, FOLLOW_TypeArray_in_primitiveAtomWithConstant2795);
                    if (input.LA(1) == Token.DOWN) {
                        match(input, Token.DOWN, null);
                        // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:651:34: (keyValuePairs+=
                        // arrayKeyValue )*
                        loop36:
                        while (true) {
                            int alt36 = 2;
                            int LA36_0 = input.LA(1);
                            if (((LA36_0 >= LogicXorWeak && LA36_0 <= ShiftRightAssign) || (LA36_0 >= LogicOr &&
                                    LA36_0 <= Modulo) || (LA36_0 >= Bool && LA36_0 <= Null) || LA36_0 == CONSTANT ||
                                    LA36_0 == Arrow || LA36_0 == VariableId)) {
                                alt36 = 1;
                            }

                            switch (alt36) {
                                case 1:
                                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:651:34:
                                    // keyValuePairs+= arrayKeyValue
                                {
                                    pushFollow(FOLLOW_arrayKeyValue_in_primitiveAtomWithConstant2799);
                                    keyValuePairs = arrayKeyValue();
                                    state._fsp--;

                                    if (list_keyValuePairs == null) {
                                        list_keyValuePairs = new ArrayList<Object>();
                                    }
                                    list_keyValuePairs.add(keyValuePairs.getTemplate());
                                }
                                break;

                                default:
                                    break loop36;
                            }
                        }

                        match(input, Token.UP, null);
                    }

                    // TEMPLATE REWRITE
                    // 651:53: -> array(content=$keyValuePairs)
                    {
                        retval.st = templateLib.getInstanceOf("array", new STAttrMap().put("content",
                                list_keyValuePairs));
                    }


                }
                break;
                case 7:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:652:9: CONSTANT
                {
                    CONSTANT56 = (ITSPHPAst) match(input, CONSTANT, FOLLOW_CONSTANT_in_primitiveAtomWithConstant2822);
                    // TEMPLATE REWRITE
                    // 652:53: -> {%{$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)}}
                    {
                        retval.st = new StringTemplate(templateLib, (CONSTANT56 != null ? CONSTANT56.getText() :
                                null).substring(0, (CONSTANT56 != null ? CONSTANT56.getText() : null).length() - 1));
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
    // $ANTLR end "primitiveAtomWithConstant"


    public static class arrayKeyValue_return extends TreeRuleReturnScope
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


    // $ANTLR start "arrayKeyValue"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:661:1: arrayKeyValue : ( ^( '=>' key= expression
    // value= expression ) -> keyValue(key=$key.stvalue=$value.st)| expression -> {$expression.st});
    public final TSPHPTranslatorWalker.arrayKeyValue_return arrayKeyValue() throws RecognitionException {
        TSPHPTranslatorWalker.arrayKeyValue_return retval = new TSPHPTranslatorWalker.arrayKeyValue_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope key = null;
        TreeRuleReturnScope value = null;
        TreeRuleReturnScope expression57 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:662:5: ( ^( '=>' key= expression value=
            // expression ) -> keyValue(key=$key.stvalue=$value.st)| expression -> {$expression.st})
            int alt38 = 2;
            int LA38_0 = input.LA(1);
            if ((LA38_0 == Arrow)) {
                alt38 = 1;
            } else if (((LA38_0 >= LogicXorWeak && LA38_0 <= ShiftRightAssign) || (LA38_0 >= LogicOr && LA38_0 <=
                    Modulo) || (LA38_0 >= Bool && LA38_0 <= Null) || LA38_0 == CONSTANT || LA38_0 == VariableId)) {
                alt38 = 2;
            } else {
                NoViableAltException nvae =
                        new NoViableAltException("", 38, 0, input);
                throw nvae;
            }

            switch (alt38) {
                case 1:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:662:9: ^( '=>' key= expression
                    // value= expression )
                {
                    match(input, Arrow, FOLLOW_Arrow_in_arrayKeyValue2911);
                    match(input, Token.DOWN, null);
                    pushFollow(FOLLOW_expression_in_arrayKeyValue2915);
                    key = expression();
                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_arrayKeyValue2919);
                    value = expression();
                    state._fsp--;

                    match(input, Token.UP, null);

                    // TEMPLATE REWRITE
                    // 662:49: -> keyValue(key=$key.stvalue=$value.st)
                    {
                        retval.st = templateLib.getInstanceOf("keyValue", new STAttrMap().put("key",
                                (key != null ? ((StringTemplate) key.getTemplate()) : null)).put("value",
                                (value != null ? ((StringTemplate) value.getTemplate()) : null)));
                    }


                }
                break;
                case 2:
                    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:663:9: expression
                {
                    pushFollow(FOLLOW_expression_in_arrayKeyValue2944);
                    expression57 = expression();
                    state._fsp--;

                    // TEMPLATE REWRITE
                    // 663:20: -> {$expression.st}
                    {
                        retval.st = (expression57 != null ? ((StringTemplate) expression57.getTemplate()) : null);
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
    // $ANTLR end "arrayKeyValue"


    public static class operator_return extends TreeRuleReturnScope
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


    // $ANTLR start "operator"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:675:1: operator : ^( binaryOperator left=
    // expression right= expression ) -> binaryOperator(operator=$binaryOperator.stleft=$left.stright=$right
    // .stneedParentheses=$binaryOperator.needParentheses);
    public final TSPHPTranslatorWalker.operator_return operator() throws RecognitionException {
        TSPHPTranslatorWalker.operator_return retval = new TSPHPTranslatorWalker.operator_return();
        retval.start = input.LT(1);

        TreeRuleReturnScope left = null;
        TreeRuleReturnScope right = null;
        TreeRuleReturnScope binaryOperator58 = null;

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:676:5: ( ^( binaryOperator left= expression
            // right= expression ) -> binaryOperator(operator=$binaryOperator.stleft=$left.stright=$right
            // .stneedParentheses=$binaryOperator.needParentheses))
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:684:8: ^( binaryOperator left= expression
            // right= expression )
            {
                pushFollow(FOLLOW_binaryOperator_in_operator2989);
                binaryOperator58 = binaryOperator();
                state._fsp--;

                match(input, Token.DOWN, null);
                pushFollow(FOLLOW_expression_in_operator2993);
                left = expression();
                state._fsp--;

                pushFollow(FOLLOW_expression_in_operator2997);
                right = expression();
                state._fsp--;

                match(input, Token.UP, null);

                // TEMPLATE REWRITE
                // 685:9: -> binaryOperator(operator=$binaryOperator.stleft=$left.stright=$right
                // .stneedParentheses=$binaryOperator.needParentheses)
                {
                    retval.st = templateLib.getInstanceOf("binaryOperator", new STAttrMap().put("operator",
                            (binaryOperator58 != null ? ((StringTemplate) binaryOperator58.getTemplate()) : null))
                            .put("left", (left != null ? ((StringTemplate) left.getTemplate()) : null)).put("right",
                                    (right != null ? ((StringTemplate) right.getTemplate()) : null)).put
                                    ("needParentheses", (binaryOperator58 != null ? ((TSPHPTranslatorWalker
                                            .binaryOperator_return) binaryOperator58).needParentheses : false)));
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
    // $ANTLR end "operator"


    public static class binaryOperator_return extends TreeRuleReturnScope
    {
        public boolean needParentheses;
        public StringTemplate st;

        public Object getTemplate() {
            return st;
        }

        public String toString() {
            return st == null ? null : st.toString();
        }
    }

    ;


    // $ANTLR start "binaryOperator"
    // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:738:1: binaryOperator returns [boolean
    // needParentheses] : ( 'or' | 'xor' | 'and' | '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '.='
    // | '<<=' | '>>=' | '||' | '&&' | '|' | '^' | '&' | '==' | '!=' | '===' | '!==' | '<' | '<=' | '>' | '>=' | '<<'
    // | '>>' | '+' | '-' | '.' | '*' | '/' | '%' );
    public final TSPHPTranslatorWalker.binaryOperator_return binaryOperator() throws RecognitionException {
        TSPHPTranslatorWalker.binaryOperator_return retval = new TSPHPTranslatorWalker.binaryOperator_return();
        retval.start = input.LT(1);

        try {
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:743:5: ( 'or' | 'xor' | 'and' | '=' | '+='
            // | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '.=' | '<<=' | '>>=' | '||' | '&&' | '|' | '^' |
            // '&' | '==' | '!=' | '===' | '!==' | '<' | '<=' | '>' | '>=' | '<<' | '>>' | '+' | '-' | '.' | '*' |
            // '/' | '%' )
            // D:\\tins-translators-tsphp\\antlr\\TSPHPTranslatorWalker.g:
            {
                if ((input.LA(1) >= LogicXorWeak && input.LA(1) <= ShiftRightAssign) || (input.LA(1) >= LogicOr &&
                        input.LA(1) <= Modulo)) {
                    input.consume();
                    state.errorRecovery = false;
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    throw mse;
                }
            }


            retval.st = templateLib.getInstanceOf("operator", new STAttrMap().put("o",
                    ((ITSPHPAst) retval.start).getText()));
            retval.needParentheses = precedenceHelper.needParentheses(((ITSPHPAst) retval.start));

        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
            // do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "binaryOperator"

    // Delegated rules


    public static final BitSet FOLLOW_namespace_in_compilationUnit80 = new BitSet(new long[]{0x0000000000000002L,
            0x0000000000000000L, 0x0000000000000001L});
    public static final BitSet FOLLOW_Namespace_in_namespace119 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_TYPE_NAME_in_namespace124 = new BitSet(new long[]{0x0000000000000000L,
            0x8000000000000000L});
    public static final BitSet FOLLOW_DEFAULT_NAMESPACE_in_namespace126 = new BitSet(new long[]{0x0000000000000000L,
            0x8000000000000000L});
    public static final BitSet FOLLOW_namespaceBody_in_namespace129 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NAMESPACE_BODY_in_namespaceBody186 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_statement_in_namespaceBody190 = new BitSet(new long[]{0x0000000000000008L,
            0x0044008000100000L, 0x0000280002000000L});
    public static final BitSet FOLLOW_NAMESPACE_BODY_in_namespaceBody211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_useDeclarationList_in_statement239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_definition_in_statement253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_instruction_in_statement267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Use_in_useDeclarationList291 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_useDeclaration_in_useDeclarationList295 = new BitSet(new
            long[]{0x0000000000000008L, 0x0000000000000000L, 0x0000040000000000L});
    public static final BitSet FOLLOW_USE_DECLARATION_in_useDeclaration334 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_TYPE_NAME_in_useDeclaration336 = new BitSet(new long[]{0x0000000000000000L,
            0x0020000000000000L});
    public static final BitSet FOLLOW_Identifier_in_useDeclaration338 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_functionDeclaration_in_definition412 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constDeclarationList_in_definition430 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONSTANT_DECLARATION_LIST_in_constDeclarationList466 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_TYPE_in_constDeclarationList481 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_TYPE_MODIFIER_in_constDeclarationList484 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Public_in_constDeclarationList486 = new BitSet(new long[]{0x0000000000000000L,
            0x0000000000000000L, 0x0000000001000000L});
    public static final BitSet FOLLOW_Static_in_constDeclarationList488 = new BitSet(new long[]{0x0000000000000000L,
            0x0000800000000000L});
    public static final BitSet FOLLOW_Final_in_constDeclarationList490 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_scalarTypesOrUnknown_in_constDeclarationList493 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constDeclaration_in_constDeclarationList510 = new BitSet(new
            long[]{0x0000000000000008L, 0x0020000000000000L});
    public static final BitSet FOLLOW_Identifier_in_constDeclaration568 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_unaryPrimitiveAtom_in_constDeclaration570 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_primitiveAtomWithConstant_in_unaryPrimitiveAtom620 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UNARY_MINUS_in_unaryPrimitiveAtom651 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_UNARY_PLUS_in_unaryPrimitiveAtom672 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_primitiveAtomWithConstant_in_unaryPrimitiveAtom690 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_VARIABLE_DECLARATION_LIST_in_localVariableDeclarationList755 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_TYPE_in_localVariableDeclarationList770 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeModifier_in_localVariableDeclarationList772 = new BitSet(new
            long[]{0x0000000000100000L, 0x0000000000000008L, 0x0000037810000000L});
    public static final BitSet FOLLOW_allTypesOrUnknown_in_localVariableDeclarationList774 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_localVariableDeclaration_in_localVariableDeclarationList791 = new BitSet(new
            long[]{0x0000000000000008L, 0x0000000000000000L, 0x0000400000000000L});
    public static final BitSet FOLLOW_VariableId_in_localVariableDeclaration870 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VariableId_in_localVariableDeclaration893 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_unaryPrimitiveAtom_in_localVariableDeclaration897 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TYPE_MODIFIER_in_typeModifier952 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Cast_in_typeModifier967 = new BitSet(new long[]{0x0001000000100008L});
    public static final BitSet FOLLOW_LogicNot_in_typeModifier986 = new BitSet(new long[]{0x0000000000100008L});
    public static final BitSet FOLLOW_QuestionMark_in_typeModifier1009 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TYPE_MODIFIER_in_typeModifier1036 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_allTypesOrUnknown_in_returnTypesOrUnknown1057 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_allTypes_in_allTypesOrUnknown1094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QuestionMark_in_allTypesOrUnknown1108 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitiveTypes_in_allTypes1136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TYPE_NAME_in_allTypes1151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scalarTypes_in_primitiveTypes1183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeArray_in_primitiveTypes1198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeResource_in_primitiveTypes1215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeMixed_in_primitiveTypes1229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scalarTypes_in_scalarTypesOrUnknown1256 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QuestionMark_in_scalarTypesOrUnknown1270 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeBool_in_scalarTypes1301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeInt_in_scalarTypes1318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeFloat_in_scalarTypes1336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeString_in_scalarTypes1352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PARAMETER_LIST_in_formalParameters1388 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_paramDeclaration_in_formalParameters1393 = new BitSet(new
            long[]{0x0000000000000008L, 0x0000000000000000L, 0x0000000000000010L});
    public static final BitSet FOLLOW_PARAMETER_LIST_in_formalParameters1430 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BLOCK_in_block1458 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_instruction_in_block1462 = new BitSet(new long[]{0x0000000000000008L,
            0x0040008000000000L, 0x0000200002000000L});
    public static final BitSet FOLLOW_BLOCK_in_block1476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PARAMETER_DECLARATION_in_paramDeclaration1503 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_TYPE_in_paramDeclaration1518 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeModifier_in_paramDeclaration1520 = new BitSet(new
            long[]{0x0000000000100000L, 0x0000000000000008L, 0x0000037810000000L});
    public static final BitSet FOLLOW_allTypesOrUnknown_in_paramDeclaration1522 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_VariableId_in_paramDeclaration1543 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_VariableId_in_paramDeclaration1564 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_unaryPrimitiveAtom_in_paramDeclaration1568 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Function_in_functionDeclaration1623 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_FUNCTION_MODIFIER_in_functionDeclaration1637 = new BitSet(new
            long[]{0x0000000000000000L, 0x0000000000000000L, 0x0000000004000000L});
    public static final BitSet FOLLOW_TYPE_in_functionDeclaration1652 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeModifier_in_functionDeclaration1654 = new BitSet(new
            long[]{0x0000000000100000L, 0x0000000000000008L, 0x0000037810000000L});
    public static final BitSet FOLLOW_returnTypesOrUnknown_in_functionDeclaration1656 = new BitSet(new
            long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Identifier_in_functionDeclaration1671 = new BitSet(new
            long[]{0x0000000000000000L, 0x0000000000000000L, 0x0000000000000020L});
    public static final BitSet FOLLOW_formalParameters_in_functionDeclaration1685 = new BitSet(new
            long[]{0x0000000000000000L, 0x0000000000001000L});
    public static final BitSet FOLLOW_block_in_functionDeclaration1699 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_localVariableDeclarationList_in_instruction1839 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifCondition_in_instruction1856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_switchCondition_in_instruction1890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EXPRESSION_in_instruction1951 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_instruction1953 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_If_in_ifCondition2031 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_ifCondition2045 = new BitSet(new long[]{0x0000000000000000L,
            0x0000000000002000L});
    public static final BitSet FOLLOW_blockConditional_in_ifCondition2062 = new BitSet(new
            long[]{0x0000000000000008L, 0x0000000000002000L});
    public static final BitSet FOLLOW_blockConditional_in_ifCondition2078 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BLOCK_CONDITIONAL_in_blockConditional2139 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_instruction_in_blockConditional2143 = new BitSet(new
            long[]{0x0000000000000008L, 0x0040008000000000L, 0x0000200002000000L});
    public static final BitSet FOLLOW_Switch_in_switchCondition2171 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_switchCondition2173 = new BitSet(new long[]{0x0000000000000008L,
            0x0000000000000000L, 0x0000000000100000L});
    public static final BitSet FOLLOW_switchContent_in_switchCondition2177 = new BitSet(new
            long[]{0x0000000000000008L, 0x0000000000000000L, 0x0000000000100000L});
    public static final BitSet FOLLOW_SWITCH_CASES_in_switchContent2222 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_caseLabel_in_switchContent2226 = new BitSet(new long[]{0x800003FFFFE7FFF8L,
            0x000000020000005FL, 0x0000400000000000L});
    public static final BitSet FOLLOW_blockConditional_in_switchContent2230 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_caseLabel2275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Default_in_caseLabel2295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_in_expression2331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operator_in_expression2364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitiveAtomWithConstant_in_atom2454 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VariableId_in_atom2470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Bool_in_primitiveAtomWithConstant2531 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Int_in_primitiveAtomWithConstant2584 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Float_in_primitiveAtomWithConstant2638 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_String_in_primitiveAtomWithConstant2690 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Null_in_primitiveAtomWithConstant2741 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TypeArray_in_primitiveAtomWithConstant2795 = new BitSet(new
            long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_arrayKeyValue_in_primitiveAtomWithConstant2799 = new BitSet(new
            long[]{0x800003FFFFE7FFF8L, 0x000000000000025FL, 0x0000400000000000L});
    public static final BitSet FOLLOW_CONSTANT_in_primitiveAtomWithConstant2822 = new BitSet(new
            long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Arrow_in_arrayKeyValue2911 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_arrayKeyValue2915 = new BitSet(new long[]{0x800003FFFFE7FFF0L,
            0x000000000000005FL, 0x0000400000000000L});
    public static final BitSet FOLLOW_expression_in_arrayKeyValue2919 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expression_in_arrayKeyValue2944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_binaryOperator_in_operator2989 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_operator2993 = new BitSet(new long[]{0x800003FFFFE7FFF0L,
            0x000000000000005FL, 0x0000400000000000L});
    public static final BitSet FOLLOW_expression_in_operator2997 = new BitSet(new long[]{0x0000000000000008L});
}
