/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.gen.TokenTypes;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.translators.tsphp.IRuntimeCheckProvider;
import ch.tsphp.tinsphp.translators.tsphp.ITempVariableHelper;
import ch.tsphp.tinsphp.translators.tsphp.ITypeTransformer;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPRuntimeCheckProvider;
import ch.tsphp.tinsphp.translators.tsphp.issues.IOutputIssueMessageProvider;
import ch.tsphp.tinsphp.translators.tsphp.test.unit.testutils.ATypeTest;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayDeque;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TSPHPRuntimeCheckProviderTest extends ATypeTest
{
    @Test
    public void getTypeCheck_IntAndIsVariable_ReturnsCastToInt() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, intType);

        assertThat(result.toString(), is("cast($x, int)"));
        assertThat(statements, empty());
    }

    @Test
    public void getTypeCheck_IntAndIsNotVariable_ReturnsCastToInt() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x + 1";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsNotVariable("$t");
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, intType);

        assertThat(result.toString(), is("cast(" + expression + ", int)"));
        assertThat(statements, empty());
    }

    @Test
    public void getTypeCheck_FloatAndIsVariable_ReturnsCastToInt() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, floatType);

        assertThat(result.toString(), is("cast($x, float)"));
        assertThat(statements, empty());
    }

    @Test
    public void getTypeCheck_FloatAndIsNotVariable_ReturnsCastToFloat() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x + 1";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsNotVariable("$t");
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, floatType);

        assertThat(result.toString(), is("cast(" + expression + ", float)"));
        assertThat(statements, empty());
    }

    @Test
    public void getTypeCheck_StringAndIsVariable_ReturnsCastToInt() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression,
                stringType);

        assertThat(result.toString(), is("cast($x, string)"));
        assertThat(statements, empty());
    }

    @Test
    public void getTypeCheck_StringAndIsNotVariable_ReturnsCastToString() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x + 1";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsNotVariable("$t");
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression,
                stringType);

        assertThat(result.toString(), is("cast(" + expression + ", string)"));
        assertThat(statements, empty());
    }

    @Test
    public void getTypeCheck_FalseTypeAndIsVariable_ReturnsCheckIfFalse() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, falseType);

        assertThat(result.toString(), is("($x === false ? false : "
                + "\\trigger_error('The variable $x must hold the value false.', \\E_USER_ERROR))"));
        assertThat(statements, empty());
    }

    @Test
    public void getTypeCheck_FalseTypeAndIsNotVariable_ReturnsCheckIfFalseWithTempVariable() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x + 1";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsNotVariable("$t");
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, falseType);

        assertThat(result.toString(), is("(($t = (" + expression + ")) === false ? false : "
                + "\\trigger_error('The variable $t must hold the value false.', \\E_USER_ERROR))"));
        assertThat(statements, contains("mixed $t;"));
    }

    @Test
    public void getTypeCheck_TrueTypeAndIsVariable_ReturnsCheckIfTrue() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, trueType);

        assertThat(result.toString(), is("($x === true ? true : "
                + "\\trigger_error('The variable $x must hold the value true.', \\E_USER_ERROR))"));
        assertThat(statements, empty());
    }

    @Test
    public void getTypeCheck_TrueTypeAndIsNotVariable_ReturnsCheckIfTrueWithTempVariable() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x + 1";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsNotVariable("$t");
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, trueType);

        assertThat(result.toString(), is("(($t = (" + expression + ")) === true ? true : "
                + "\\trigger_error('The variable $t must hold the value true.', \\E_USER_ERROR))"));
        assertThat(statements, contains("mixed $t;"));
    }

    @Test
    public void getTypeCheck_NullTypeAndIsVariable_ReturnsCheckIfNull() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, nullType);

        assertThat(result.toString(), is("($x === null ? null : "
                + "\\trigger_error('The variable $x must hold the value null.', \\E_USER_ERROR))"));
        assertThat(statements, empty());
    }

    @Test
    public void getTypeCheck_NullTypeAndIsNotVariable_ReturnsCheckIfNullWithTempVariable() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x + 1";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsNotVariable("$t");
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, nullType);

        assertThat(result.toString(), is("(($t = (" + expression + ")) === null ? null : "
                + "\\trigger_error('The variable $t must hold the value null.', \\E_USER_ERROR))"));
        assertThat(statements, contains("mixed $t;"));
    }

    @Test
    public void getTypeCheck_BoolAndIsVariable_ReturnsCastToBool() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, boolType);

        assertThat(result.toString(), is("cast($x, bool)"));
        assertThat(statements, empty());
    }

    @Test
    public void getTypeCheck_BoolAndIsNotVariable_ReturnsCastToBool() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x + 1";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsNotVariable("$t");
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, boolType);

        assertThat(result.toString(), is("cast(" + expression + ", bool)"));
        assertThat(statements, empty());
    }

    @Test
    public void getTypeCheck_NumAndIsVariable_ReturnsCheckIfFloatOrIntWithCasts() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, numType);

        assertThat(result.toString(), is("($x <: float ? cast($x, float) : $x <: int ? cast($x, int) : "
                + "\\trigger_error('The variable $x must hold a value of type [float, int].', \\E_USER_ERROR))"));
        assertThat(statements, empty());
    }

    @Test
    public void getTypeCheck_NumAndIsNotVariable_ReturnsCheckIfFloatOrIntWithCastsAndTempVariable() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x + 1";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsNotVariable("$t");
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression, numType);

        assertThat(result.toString(), is("(($t = ($x + 1)) <: float ? cast($t, float) : $t <: int ? cast($t, int) : "
                + "\\trigger_error('The variable $t must hold a value of type [float, int].', \\E_USER_ERROR))"));
        assertThat(statements, contains("mixed $t;"));
    }

    @Test
    public void getTypeCheck_NumOrArrayAndIsVariable_ReturnsCheckIfArrayOrFloatOrIntWithCasts() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        IUnionTypeSymbol numOrArray = symbolFactory.createUnionTypeSymbol();
        numOrArray.addTypeSymbol(numType);
        numOrArray.addTypeSymbol(arrayType);
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression,
                numOrArray);

        assertThat(result.toString(), is("($x <: array ? cast($x, array) : $x <: float ? cast($x, float) : "
                + "$x <: int ? cast($x, int) : \\trigger_error('The variable $x must hold a value of type "
                + "[array, float, int].', \\E_USER_ERROR))"));
        assertThat(statements, empty());
    }

    @Test
    public void getTypeCheck_NumOrArrayAndIsNotVariable_ReturnsCheckIfArrayOrFloatOrIntWithCastsAndTempVariable() {
        ITSPHPAst argumentAst = mock(ITSPHPAst.class);
        when(argumentAst.getType()).thenReturn(TokenTypes.VariableId);
        String expression = "$x + 1";
        when(argumentAst.getText()).thenReturn(expression);
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsNotVariable("$t");
        IUnionTypeSymbol numOrArray = symbolFactory.createUnionTypeSymbol();
        numOrArray.addTypeSymbol(numType);
        numOrArray.addTypeSymbol(arrayType);
        ArrayDeque<String> statements = new ArrayDeque<>();

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        Object result = runtimeCheckProvider.getTypeCheck(statements, argumentAst, expression,
                numOrArray);

        assertThat(result.toString(), is("(($t = ($x + 1)) <: array ? cast($t, array) : "
                + "$t <: float ? cast($t, float) : $t <: int ? cast($t, int) : "
                + "\\trigger_error('The variable $t must hold a value of type [array, float, int].', " +
                "\\E_USER_ERROR))"));
        assertThat(statements, contains("mixed $t;"));
    }

    private ITempVariableHelper createTempVariableHelperIsVariable() {
        ITempVariableHelper tempVariableHelper = mock(ITempVariableHelper.class);
        when(tempVariableHelper.getTempVariableNameIfNotVariable(any(ITSPHPAst.class))).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return ((ITSPHPAst) invocationOnMock.getArguments()[0]).getText();
            }
        });
        return tempVariableHelper;
    }

    private ITempVariableHelper createTempVariableHelperIsNotVariable(final String tempVariableName) {
        ITempVariableHelper tempVariableHelper = mock(ITempVariableHelper.class);
        when(tempVariableHelper.getTempVariableNameIfNotVariable(any(ITSPHPAst.class))).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return tempVariableName;
            }
        });
        return tempVariableHelper;
    }

    private IRuntimeCheckProvider createRuntimeCheckProvider(ITempVariableHelper tempVariableHelper) {
        ITypeTransformer typeTransformer = mock(ITypeTransformer.class);
        when(typeTransformer.getType(any(ITypeSymbol.class))).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArguments()[0];
            }
        });
        ITypeSymbol tsphpBoolTypeSymbol = mock(ITypeSymbol.class);
        when(tsphpBoolTypeSymbol.getAbsoluteName()).thenReturn("bool");
        IOutputIssueMessageProvider messageProvider = mock(IOutputIssueMessageProvider.class);
        when(messageProvider.getValueCheckError(anyString(), anyString())).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return "The variable " + args[0] + " must hold the value " + args[1] + ".";
            }
        });
        when(messageProvider.getTypeCheckError(anyString(), anyList())).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return "The variable " + args[0] + " must hold a value of type " + args[1] + ".";
            }
        });

        return createRuntimeCheckProvider(
                typeTransformer,
                tempVariableHelper,
                messageProvider,
                tsphpBoolTypeSymbol);
    }

    protected IRuntimeCheckProvider createRuntimeCheckProvider(
            ITypeTransformer typeTransformer,
            ITempVariableHelper tempVariableHelper,
            IOutputIssueMessageProvider messageProvider,
            ITypeSymbol tsphpBoolTypeSymbol) {
        return new TSPHPRuntimeCheckProvider(typeTransformer, tempVariableHelper, messageProvider, tsphpBoolTypeSymbol);
    }
}
