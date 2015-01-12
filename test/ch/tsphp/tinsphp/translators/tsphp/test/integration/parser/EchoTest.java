/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is part of the TSPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.parser;

import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorParserTest;
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
public class EchoTest extends ATranslatorParserTest
{

    public EchoTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();
        List<String[]> expressions = ExpressionHelper.getExpressions();

        for (String[] expression : expressions) {
            collection.add(new Object[]{
                    "echo " + expression[0] + ";",
                    "echo " + expression[1] + ";"
            });
            collection.add(new Object[]{
                    "echo " + expression[0] + "," + expression[0] + ";",
                    "echo " + expression[1] + ", " + expression[1] + ";"
            });
        }

        List<String[]> castExpressions1 = ExpressionHelper.getCastToTypeExpressions(5);
        for (String[] castExpression1 : castExpressions1) {
            collection.add(new Object[]{
                    "echo " + castExpression1[0] + ";",
                    "echo " + castExpression1[1] + ";"
            });

            List<String[]> castExpressions2 =
                    ExpressionHelper.getCastToTypeExpressions(5 + castExpression1[0].length() + 1);
            for (String[] castExpression2 : castExpressions2) {
                collection.add(new Object[]{
                        "echo " + castExpression1[0] + "," + castExpression2[0] + ";",
                        "echo " + castExpression1[1] + ", " + castExpression2[1] + ";"
                });
            }
        }
        return collection;
    }
}
