/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit.coverage;

import ch.tsphp.tinsphp.translators.tsphp.antlr.TSPHPTranslatorWalker;
import org.antlr.runtime.tree.TreeRuleReturnScope;
import org.antlr.stringtemplate.StringTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

@RunWith(Parameterized.class)
public class RulesGetTemplateTest
{

    private static final String WALKER_NAME = TSPHPTranslatorWalker.class.getName() + "$";

    private String ruleName;

    public RulesGetTemplateTest(String theRuleName) {
        ruleName = theRuleName;
    }

    @Test
    public void getTemplate_IsDefined_ReturnsTemplate() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, NoSuchFieldException {
        StringTemplate stringTemplate = mock(StringTemplate.class);

        TreeRuleReturnScope treeRuleReturnScope =
                (TreeRuleReturnScope) Class.forName(WALKER_NAME + ruleName + "_return").newInstance();
        Field field = treeRuleReturnScope.getClass().getField("st");
        field.set(treeRuleReturnScope, stringTemplate);
        StringTemplate result = (StringTemplate) treeRuleReturnScope.getTemplate();

        assertEquals(ruleName + "- failed. ", stringTemplate, result);
    }

    @Test
    public void getTemplate_IsNull_ReturnsNull() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, NoSuchFieldException {
        //no arrange necessary

        TreeRuleReturnScope treeRuleReturnScope =
                (TreeRuleReturnScope) Class.forName(WALKER_NAME + ruleName + "_return").newInstance();
        StringTemplate result = (StringTemplate) treeRuleReturnScope.getTemplate();

        assertNull(ruleName + "- failed. ", result);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        Collection<Object[]> names = NotCorrectStartNodeTypeTest.testStrings();
        List<Object[]> collection = new ArrayList<>();
        for (Object[] name : names) {
            collection.add(new Object[]{name[0]});
        }
        return collection;
    }
}
