/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file TempVariableHelperTest from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit;

import ch.tsphp.common.IScope;
import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.ITSPHPAstAdaptor;
import ch.tsphp.common.symbols.ISymbol;
import ch.tsphp.tinsphp.common.gen.TokenTypes;
import ch.tsphp.tinsphp.translators.tsphp.ITempVariableHelper;
import ch.tsphp.tinsphp.translators.tsphp.TempVariableHelper;
import org.antlr.runtime.Token;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TempVariableHelperTest
{
    private ITSPHPAstAdaptor astAdaptor;

    @Before
    public void setUp() {
        astAdaptor = mock(ITSPHPAstAdaptor.class);
    }

    @Test
    public void getTempVariableName_IsVariable_ReturnTempName() {
        ITSPHPAst ast = createAst(TokenTypes.VariableId, "$dummy");
        IScope scope = mock(IScope.class);
        when(ast.getScope()).thenReturn(scope);
        ITSPHPAst tmpVariable = mock(ITSPHPAst.class);
        when(astAdaptor.create(anyInt(), anyString())).thenReturn(tmpVariable);
        when(scope.resolve(tmpVariable)).thenReturn(null);
        when(tmpVariable.getText()).thenReturn("$_t12_14");

        ITempVariableHelper tempVariableHelper = createTempVariableHelper();
        String name = tempVariableHelper.getTempVariableName(ast);

        assertThat(name, is("$_t12_14"));
    }

    @Test
    public void getTempVariableName_IsNotVariable_ReturnTempName() {
        ITSPHPAst ast = createAst(TokenTypes.Plus, "+");
        IScope scope = mock(IScope.class);
        when(ast.getScope()).thenReturn(scope);
        ITSPHPAst tmpVariable = mock(ITSPHPAst.class);
        when(astAdaptor.create(anyInt(), anyString())).thenReturn(tmpVariable);
        when(scope.resolve(tmpVariable)).thenReturn(null);
        when(tmpVariable.getText()).thenReturn("$_t12_14");

        ITempVariableHelper tempVariableHelper = createTempVariableHelper();
        String name = tempVariableHelper.getTempVariableName(ast);

        assertThat(name, is("$_t12_14"));
    }

    @Test
    public void getTempVariableName_IsNotVariableAndScopeIsNull_ReturnTempName() {
        ITSPHPAst ast = createAst(TokenTypes.Plus, "+");
        when(ast.getScope()).thenReturn(null);
        ITSPHPAst tmpVariable = mock(ITSPHPAst.class);
        when(astAdaptor.create(anyInt(), anyString())).thenReturn(tmpVariable);
        when(tmpVariable.getText()).thenReturn("$_t12_14");

        ITempVariableHelper tempVariableHelper = createTempVariableHelper();
        String name = tempVariableHelper.getTempVariableName(ast);

        assertThat(name, is("$_t12_14"));
    }

    @Test
    public void getTempVariableName_IsNotVariableTempAlreadyDefined_ReturnTempNameWithAppendix() {
        ITSPHPAst ast = createAst(TokenTypes.Plus, "+");
        when(ast.getLine()).thenReturn(12);
        when(ast.getCharPositionInLine()).thenReturn(14);
        IScope scope = mock(IScope.class);
        when(ast.getScope()).thenReturn(scope);
        ITSPHPAst tmpVariable = mock(ITSPHPAst.class);
        when(astAdaptor.create(anyInt(), anyString())).thenReturn(tmpVariable);
        when(scope.resolve(tmpVariable)).thenReturn(mock(ISymbol.class)).thenReturn(null);
        when(tmpVariable.getText()).thenReturn("$_t12_14_0");

        ITempVariableHelper tempVariableHelper = createTempVariableHelper();
        String name = tempVariableHelper.getTempVariableName(ast);

        assertThat(name, is("$_t12_14_0"));
        verify(tmpVariable).setText("$_t12_14_0");
    }

    @Test
    public void getTempVariableName_IsNotVariableTempAlreadyDefinedTwice_ReturnTempNameWithAppendixCount1() {
        ITSPHPAst ast = createAst(TokenTypes.Plus, "+");
        when(ast.getLine()).thenReturn(12);
        when(ast.getCharPositionInLine()).thenReturn(14);
        IScope scope = mock(IScope.class);
        when(ast.getScope()).thenReturn(scope);
        ITSPHPAst tmpVariable = mock(ITSPHPAst.class);
        when(astAdaptor.create(anyInt(), anyString())).thenReturn(tmpVariable);
        when(scope.resolve(tmpVariable)).thenReturn(mock(ISymbol.class))
                .thenReturn(mock(ISymbol.class))
                .thenReturn(null);
        when(tmpVariable.getText()).thenReturn("$_t12_14_1");

        ITempVariableHelper tempVariableHelper = createTempVariableHelper();
        String name = tempVariableHelper.getTempVariableName(ast);

        assertThat(name, is("$_t12_14_1"));
        verify(tmpVariable).setText("$_t12_14_1");
    }

    @Test
    public void getTempVariableNameIfNotVariable_IsVariable_ReturnVariableName() {
        ITSPHPAst ast = createAst(TokenTypes.VariableId, "$dummy");

        ITempVariableHelper tempVariableHelper = createTempVariableHelper();
        String name = tempVariableHelper.getTempVariableNameIfNotVariable(ast);

        assertThat(name, is("$dummy"));
    }

    @Test
    public void getTempVariableNameIfNotVariable_IsNotVariable_ReturnTempName() {
        ITSPHPAst ast = createAst(TokenTypes.Plus, "+");
        IScope scope = mock(IScope.class);
        when(ast.getScope()).thenReturn(scope);
        ITSPHPAst tmpVariable = mock(ITSPHPAst.class);
        when(astAdaptor.create(anyInt(), anyString())).thenReturn(tmpVariable);
        when(scope.resolve(tmpVariable)).thenReturn(null);
        when(tmpVariable.getText()).thenReturn("$_t12_14");

        ITempVariableHelper tempVariableHelper = createTempVariableHelper();
        String name = tempVariableHelper.getTempVariableNameIfNotVariable(ast);

        assertThat(name, is("$_t12_14"));
    }

    @Test
    public void getTempVariableNameIfNotVariable_IsNotVariableAndScopeIsNull_ReturnTempName() {
        ITSPHPAst ast = createAst(TokenTypes.Plus, "+");
        when(ast.getScope()).thenReturn(null);
        ITSPHPAst tmpVariable = mock(ITSPHPAst.class);
        when(astAdaptor.create(anyInt(), anyString())).thenReturn(tmpVariable);
        when(tmpVariable.getText()).thenReturn("$_t12_14");

        ITempVariableHelper tempVariableHelper = createTempVariableHelper();
        String name = tempVariableHelper.getTempVariableNameIfNotVariable(ast);

        assertThat(name, is("$_t12_14"));
    }

    @Test
    public void getTempVariableNameIfNotVariable_IsNotVariableTempAlreadyDefined_ReturnTempNameWithAppendix() {
        ITSPHPAst ast = createAst(TokenTypes.Plus, "+");
        when(ast.getLine()).thenReturn(12);
        when(ast.getCharPositionInLine()).thenReturn(14);
        IScope scope = mock(IScope.class);
        when(ast.getScope()).thenReturn(scope);
        ITSPHPAst tmpVariable = mock(ITSPHPAst.class);
        when(astAdaptor.create(anyInt(), anyString())).thenReturn(tmpVariable);
        when(scope.resolve(tmpVariable)).thenReturn(mock(ISymbol.class)).thenReturn(null);
        when(tmpVariable.getText()).thenReturn("$_t12_14_0");

        ITempVariableHelper tempVariableHelper = createTempVariableHelper();
        String name = tempVariableHelper.getTempVariableNameIfNotVariable(ast);

        assertThat(name, is("$_t12_14_0"));
        verify(tmpVariable).setText("$_t12_14_0");
    }

    @Test
    public void
    getTempVariableNameIfNotVariable_IsNotVariableTempAlreadyDefinedTwice_ReturnTempNameWithAppendixCount1() {
        ITSPHPAst ast = createAst(TokenTypes.Plus, "+");
        when(ast.getLine()).thenReturn(12);
        when(ast.getCharPositionInLine()).thenReturn(14);
        IScope scope = mock(IScope.class);
        when(ast.getScope()).thenReturn(scope);
        ITSPHPAst tmpVariable = mock(ITSPHPAst.class);
        when(astAdaptor.create(anyInt(), anyString())).thenReturn(tmpVariable);
        when(scope.resolve(tmpVariable)).thenReturn(mock(ISymbol.class))
                .thenReturn(mock(ISymbol.class))
                .thenReturn(null);
        when(tmpVariable.getText()).thenReturn("$_t12_14_1");

        ITempVariableHelper tempVariableHelper = createTempVariableHelper();
        String name = tempVariableHelper.getTempVariableNameIfNotVariable(ast);

        assertThat(name, is("$_t12_14_1"));
        verify(tmpVariable).setText("$_t12_14_1");
    }

    private ITSPHPAst createAst(int tokenType, String name) {
        ITSPHPAst ast = mock(ITSPHPAst.class);
        Token token = mock(Token.class);
        when(ast.getToken()).thenReturn(token);
        when(token.getType()).thenReturn(tokenType);
        when(ast.getText()).thenReturn(name);
        return ast;
    }

    protected ITempVariableHelper createTempVariableHelper() {
        return new TempVariableHelper(astAdaptor);
    }
}
