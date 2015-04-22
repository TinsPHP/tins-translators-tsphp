/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.tinsphp.common.translation.IPrecedenceHelper;
import ch.tsphp.tinsphp.symbols.gen.TokenTypes;
import ch.tsphp.tinsphp.translators.tsphp.PrecedenceHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class PrecedenceHelperSameGroupTest
{
    private int first;
    private int second;

    public PrecedenceHelperSameGroupTest(int theFirst, int theSecond) {
        first = theFirst;
        second = theSecond;
    }

    @Test
    public void needParentheses_FirstIsParentOfSecond_ReturnsFalse() {
        ITSPHPAst ast = mock(ITSPHPAst.class);
        when(ast.getType()).thenReturn(first);
        ITSPHPAst parent = mock(ITSPHPAst.class);
        when(ast.getParent()).thenReturn(parent);
        when(parent.getType()).thenReturn(second);

        IPrecedenceHelper precedenceHelper = createPrecedenceHelper();
        boolean result = precedenceHelper.needParentheses(ast);

        assertThat(result, is(false));
    }

    @Test
    public void needParentheses_SecondIsParentOfFirst_ReturnsFalse() {
        ITSPHPAst ast = mock(ITSPHPAst.class);
        when(ast.getType()).thenReturn(second);
        ITSPHPAst parent = mock(ITSPHPAst.class);
        when(ast.getParent()).thenReturn(parent);
        when(parent.getType()).thenReturn(first);

        IPrecedenceHelper precedenceHelper = createPrecedenceHelper();
        boolean result = precedenceHelper.needParentheses(ast);

        assertThat(result, is(false));
    }

    @Test
    public void needParentheses_FirstIsParentOfFirst_ReturnsFalse() {
        ITSPHPAst ast = mock(ITSPHPAst.class);
        when(ast.getType()).thenReturn(first);
        ITSPHPAst parent = mock(ITSPHPAst.class);
        when(ast.getParent()).thenReturn(parent);
        when(parent.getType()).thenReturn(first);

        IPrecedenceHelper precedenceHelper = createPrecedenceHelper();
        boolean result = precedenceHelper.needParentheses(ast);

        assertThat(result, is(false));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {TokenTypes.Assign, TokenTypes.PlusAssign},
                {TokenTypes.PlusAssign, TokenTypes.MinusAssign},
                {TokenTypes.MinusAssign, TokenTypes.DotAssign},
                {TokenTypes.DotAssign, TokenTypes.MultiplyAssign},
                {TokenTypes.MultiplyAssign, TokenTypes.DivideAssign},
                {TokenTypes.DivideAssign, TokenTypes.BitwiseAndAssign},
                {TokenTypes.BitwiseAndAssign, TokenTypes.BitwiseOrAssign},
                {TokenTypes.BitwiseOrAssign, TokenTypes.BitwiseXorAssign},
                {TokenTypes.BitwiseXorAssign, TokenTypes.ModuloAssign},
                {TokenTypes.ModuloAssign, TokenTypes.ShiftLeftAssign},
                {TokenTypes.ShiftLeftAssign, TokenTypes.ShiftRightAssign},
                {TokenTypes.ShiftRightAssign, TokenTypes.Assign},

                {TokenTypes.Equal, TokenTypes.NotEqual},
                {TokenTypes.NotEqual, TokenTypes.Identical},
                {TokenTypes.Identical, TokenTypes.NotIdentical},
                {TokenTypes.NotIdentical, TokenTypes.Equal},

                {TokenTypes.LessThan, TokenTypes.LessEqualThan},
                {TokenTypes.LessEqualThan, TokenTypes.GreaterThan},
                {TokenTypes.GreaterThan, TokenTypes.GreaterEqualThan},
                {TokenTypes.GreaterEqualThan, TokenTypes.LessThan},

                {TokenTypes.ShiftLeft, TokenTypes.ShiftRight},

                {TokenTypes.Plus, TokenTypes.Minus},
                {TokenTypes.Minus, TokenTypes.Dot},
                {TokenTypes.Dot, TokenTypes.Plus},

                {TokenTypes.Multiply, TokenTypes.Divide},
                {TokenTypes.Divide, TokenTypes.Modulo},
                {TokenTypes.Modulo, TokenTypes.Multiply},

                {TokenTypes.CAST, TokenTypes.PRE_INCREMENT},
                {TokenTypes.PRE_INCREMENT, TokenTypes.PRE_DECREMENT},
                {TokenTypes.PRE_DECREMENT, TokenTypes.At},
                {TokenTypes.At, TokenTypes.BitwiseNot},
                {TokenTypes.BitwiseNot, TokenTypes.LogicNot},
                {TokenTypes.LogicNot, TokenTypes.UNARY_MINUS},
                {TokenTypes.UNARY_MINUS, TokenTypes.UNARY_PLUS},
                {TokenTypes.UNARY_PLUS, TokenTypes.CAST},

                {TokenTypes.New, TokenTypes.Clone},

                {TokenTypes.POST_INCREMENT, TokenTypes.POST_DECREMENT},
        });
    }

    protected IPrecedenceHelper createPrecedenceHelper() {
        return new PrecedenceHelper();
    }
}
