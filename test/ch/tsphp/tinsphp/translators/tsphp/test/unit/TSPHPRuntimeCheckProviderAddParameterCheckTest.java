/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit;

import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.symbols.IIntersectionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.translators.tsphp.IRuntimeCheckProvider;
import ch.tsphp.tinsphp.translators.tsphp.ITempVariableHelper;
import ch.tsphp.tinsphp.translators.tsphp.test.unit.testutils.ATSPHPRuntimeCheckProviderTest;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TSPHPRuntimeCheckProviderAddParameterCheckTest extends ATSPHPRuntimeCheckProviderTest
{
    @Test
    public void addParameterCheck_NullType_ReturnsCheckForNull() {
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        Deque<String> statements = new ArrayDeque<>();
        IBindingCollection bindingCollection = mock(IBindingCollection.class);
        when(bindingCollection.getTypeVariable("$x")).thenReturn("T");
        IIntersectionTypeSymbol upperTypeBounds = createIntersectionTypeSymbol(nullType);
        when(bindingCollection.getUpperTypeBounds("T")).thenReturn(upperTypeBounds);
        IVariable parameter = mock(IVariable.class);
        when(parameter.getName()).thenReturn("$x");
        when(parameter.getAbsoluteName()).thenReturn("$x");

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        boolean result = runtimeCheckProvider.addParameterCheck(
                "foo()", statements, bindingCollection, parameter, 0);

        assertThat(result, is(true));
        assertThat(statements.size(), is(1));
        assertThat(statements.removeFirst(), is("if (!($x === null)) {\n    "
                + "\\trigger_error('Argument 1 passed to foo() (parameter $x) must be a value of type [nullType].', "
                + "\\E_USER_ERROR);\n"
                + "}"));
    }

    @Test
    public void addParameterCheck_FalseType_ReturnsCheckForFalse() {
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        Deque<String> statements = new ArrayDeque<>();
        IBindingCollection bindingCollection = mock(IBindingCollection.class);
        when(bindingCollection.getTypeVariable("$x")).thenReturn("T");
        IIntersectionTypeSymbol upperTypeBounds = createIntersectionTypeSymbol(falseType);
        when(bindingCollection.getUpperTypeBounds("T")).thenReturn(upperTypeBounds);
        IVariable parameter = mock(IVariable.class);
        when(parameter.getName()).thenReturn("$x");
        when(parameter.getAbsoluteName()).thenReturn("$x");

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        boolean result = runtimeCheckProvider.addParameterCheck(
                "foo()", statements, bindingCollection, parameter, 0);

        assertThat(result, is(true));
        assertThat(statements.size(), is(1));
        assertThat(statements.removeFirst(), is("if (!($x === false)) {\n    "
                + "\\trigger_error('Argument 1 passed to foo() (parameter $x) must be a value of type [falseType].', "
                + "\\E_USER_ERROR);\n"
                + "}"));
    }

    @Test
    public void addParameterCheck_TrueType_ReturnsCheckForTrue() {
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        Deque<String> statements = new ArrayDeque<>();
        IBindingCollection bindingCollection = mock(IBindingCollection.class);
        when(bindingCollection.getTypeVariable("$x")).thenReturn("T");
        IIntersectionTypeSymbol upperTypeBounds = createIntersectionTypeSymbol(trueType);
        when(bindingCollection.getUpperTypeBounds("T")).thenReturn(upperTypeBounds);
        IVariable parameter = mock(IVariable.class);
        when(parameter.getName()).thenReturn("$x");
        when(parameter.getAbsoluteName()).thenReturn("$x");

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        boolean result = runtimeCheckProvider.addParameterCheck(
                "foo()", statements, bindingCollection, parameter, 0);

        assertThat(result, is(true));
        assertThat(statements.size(), is(1));
        assertThat(statements.removeFirst(), is("if (!($x === true)) {\n    "
                + "\\trigger_error('Argument 1 passed to foo() (parameter $x) must be a value of type [trueType].', "
                + "\\E_USER_ERROR);\n"
                + "}"));
    }

    @Test
    public void addParameterCheck_FalseTypeOrTrueType_ReturnsCheckForBool() {
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        Deque<String> statements = new ArrayDeque<>();
        IBindingCollection bindingCollection = mock(IBindingCollection.class);
        when(bindingCollection.getTypeVariable("$x")).thenReturn("T");
        IIntersectionTypeSymbol upperTypeBounds = createIntersectionTypeSymbol(boolType);
        when(bindingCollection.getUpperTypeBounds("T")).thenReturn(upperTypeBounds);
        IVariable parameter = mock(IVariable.class);
        when(parameter.getName()).thenReturn("$x");
        when(parameter.getAbsoluteName()).thenReturn("$x");

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        boolean result = runtimeCheckProvider.addParameterCheck(
                "foo()", statements, bindingCollection, parameter, 0);

        assertThat(result, is(true));
        assertThat(statements.size(), is(1));
        assertThat(statements.removeFirst(), is("if (!($x <: bool)) {\n    "
                + "\\trigger_error('Argument 1 passed to foo() (parameter $x) must be a value of type [bool].', "
                + "\\E_USER_ERROR);\n"
                + "}"));
    }

    @Test
    public void addParameterCheck_FalseTypeOrTrueTypeOrInt_ReturnsCheckForBoolAndInt() {
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        Deque<String> statements = new ArrayDeque<>();
        IBindingCollection bindingCollection = mock(IBindingCollection.class);
        when(bindingCollection.getTypeVariable("$x")).thenReturn("T");
        IUnionTypeSymbol boolOrInt = createUnionTypeSymbol(boolType, intType);
        IIntersectionTypeSymbol upperTypeBounds = createIntersectionTypeSymbol(boolOrInt);
        when(bindingCollection.getUpperTypeBounds("T")).thenReturn(upperTypeBounds);
        IVariable parameter = mock(IVariable.class);
        when(parameter.getName()).thenReturn("$x");
        when(parameter.getAbsoluteName()).thenReturn("$x");

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        boolean result = runtimeCheckProvider.addParameterCheck(
                "foo()", statements, bindingCollection, parameter, 0);

        assertThat(result, is(true));
        assertThat(statements.size(), is(1));
        assertThat(statements.removeFirst(), is("if (!($x <: bool) && !($x <: int)) {\n    "
                + "\\trigger_error('Argument 1 passed to foo() (parameter $x) must be a value of type [bool, int].', "
                + "\\E_USER_ERROR);\n"
                + "}"));
    }

    @Test
    public void addParameterCheck_FalseTypeOrTrueTypeOrIntOrFloat_ReturnsCheckForBoolAndIntAndFloat() {
        ITempVariableHelper tempVariableHelper = createTempVariableHelperIsVariable();
        Deque<String> statements = new ArrayDeque<>();
        IBindingCollection bindingCollection = mock(IBindingCollection.class);
        when(bindingCollection.getTypeVariable("$x")).thenReturn("T");
        IUnionTypeSymbol boolOrInt = createUnionTypeSymbol(boolType, numType);
        IIntersectionTypeSymbol upperTypeBounds = createIntersectionTypeSymbol(boolOrInt);
        when(bindingCollection.getUpperTypeBounds("T")).thenReturn(upperTypeBounds);
        IVariable parameter = mock(IVariable.class);
        when(parameter.getName()).thenReturn("$x");
        when(parameter.getAbsoluteName()).thenReturn("$x");

        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(tempVariableHelper);
        boolean result = runtimeCheckProvider.addParameterCheck(
                "foo()", statements, bindingCollection, parameter, 0);

        assertThat(result, is(true));
        assertThat(statements.size(), is(1));
        assertThat(statements.removeFirst(), is("if (!($x <: bool) && !($x <: float) && !($x <: int)) {\n    "
                + "\\trigger_error('Argument 1 passed to foo() (parameter $x) "
                + "must be a value of type [bool, float, int].', "
                + "\\E_USER_ERROR);\n"
                + "}"));
    }
}
