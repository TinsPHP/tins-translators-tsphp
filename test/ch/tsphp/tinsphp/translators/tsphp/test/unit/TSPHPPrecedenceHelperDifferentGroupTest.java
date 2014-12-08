/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.tinsphp.common.translation.IPrecedenceHelper;
import ch.tsphp.tinsphp.symbols.gen.TokenTypes;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPPrecedenceHelper;
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
public class TSPHPPrecedenceHelperDifferentGroupTest
{
    private int first;
    private int second;

    public TSPHPPrecedenceHelperDifferentGroupTest(int theFirst, int theSecond){
        first = theFirst;
        second = theSecond;
    }

    @Test
    public void needParentheses_FirstIsParentOfSecond_ReturnsTrue() {
        ITSPHPAst ast = mock(ITSPHPAst.class);
        when(ast.getType()).thenReturn(first);
        ITSPHPAst parent = mock(ITSPHPAst.class);
        when(ast.getParent()).thenReturn(parent);
        when(parent.getType()).thenReturn(second);

        IPrecedenceHelper precedenceHelper = createPrecedenceHelper();
        boolean result = precedenceHelper.needParentheses(ast);

        assertThat(result, is(true));
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
                {TokenTypes.LogicOrWeak, TokenTypes.LogicXorWeak},
                {TokenTypes.LogicXorWeak, TokenTypes.LogicAndWeak},
                {TokenTypes.LogicAndWeak, TokenTypes.Assign},
                {TokenTypes.Assign, TokenTypes.QuestionMark},
                {TokenTypes.QuestionMark, TokenTypes.LogicOr},
                {TokenTypes.LogicOr, TokenTypes.LogicAnd},
                {TokenTypes.LogicAnd, TokenTypes.BitwiseOr},
                {TokenTypes.BitwiseOr, TokenTypes.BitwiseXor},
                {TokenTypes.BitwiseXor, TokenTypes.BitwiseAnd},
                {TokenTypes.BitwiseAnd, TokenTypes.Equal},
                {TokenTypes.Equal, TokenTypes.LessThan},
                {TokenTypes.LessThan, TokenTypes.ShiftLeft},
                {TokenTypes.ShiftLeft, TokenTypes.Plus},
                {TokenTypes.Plus, TokenTypes.Multiply},
                {TokenTypes.Multiply, TokenTypes.Instanceof},
                {TokenTypes.Instanceof, TokenTypes.CAST},
                {TokenTypes.CAST, TokenTypes.New},
                {TokenTypes.New, TokenTypes.POST_INCREMENT},

                //one additional test to see that separated group work correctly as well
                {TokenTypes.LogicOrWeak, TokenTypes.POST_INCREMENT},
        });
    }

    protected IPrecedenceHelper createPrecedenceHelper() {
        return new TSPHPPrecedenceHelper();
    }
}
