/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file ReturnTest from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.inference;

import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorInferenceStatementTest;
import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ExpressionHelper;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class ReturnTest extends ATranslatorInferenceStatementTest
{

    public ReturnTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();

        List<String[]> expressions = ExpressionHelper.getAllExpressions(7);
        for (String[] expression : expressions) {
            collection.add(new Object[]{"return " + expression[0] + ";", "return " + expression[1] + ";"});
        }

        return collection;
    }

}
