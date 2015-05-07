/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.tinsphp.common.gen.TokenTypes;
import ch.tsphp.tinsphp.translators.tsphp.IPrecedenceHelper;
import ch.tsphp.tinsphp.translators.tsphp.PrecedenceHelper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrecedenceHelperTest
{
    @Test
    public void needParentheses_ParentIsNotOperator_ReturnsFalse() {
        ITSPHPAst ast = mock(ITSPHPAst.class);
        when(ast.getType()).thenReturn(TokenTypes.Plus);
        ITSPHPAst parent = mock(ITSPHPAst.class);
        when(ast.getParent()).thenReturn(parent);
        when(parent.getType()).thenReturn(TokenTypes.EXPRESSION);

        IPrecedenceHelper precedenceHelper = createPrecedenceHelper();
        boolean result = precedenceHelper.needParentheses(ast);

        assertThat(result, is(false));
    }

    protected IPrecedenceHelper createPrecedenceHelper() {
        return new PrecedenceHelper();
    }
}
