/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file WhileTest from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class WhileTest extends ATranslatorParserTest
{

    public WhileTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();
        List<String[]> expressions = ExpressionHelper.getAllExpressions(6);
        for (Object[] expression : expressions) {
            collection.add(new Object[]{
                    "while(" + expression[0] + ") $a=1;",
                    "while (" + expression[1] + ") {\n    $a = 1;\n}"
            });
        }
        expressions = ExpressionHelper.getAllExpressions(15);
        for (Object[] expression : expressions) {
            collection.add(new Object[]{
                    "do $a=1; while(" + expression[0] + ");",
                    "do {\n    $a = 1;\n} while (" + expression[1] + ");"
            });
        }
        collection.addAll(Arrays.asList(new Object[][]{
                {"while( true  ) $a=1;", "while (true) {\n    $a = 1;\n}"},
                {"while( true  ){$a=1;}", "while (true) {\n    $a = 1;\n}"},
                {
                        "while( true  ){$a=1; $b=2;}",
                        "while (true) {\n    $a = 1;\n    $b = 2;\n}"
                },
                {"do $a=1; while( true  );", "do {\n    $a = 1;\n} while (true);"},
                {"do {$a=1;} while( true  );", "do {\n    $a = 1;\n} while (true);"},
                {"do {$a=1;$b=2;}while( true  );", "do {\n    $a = 1;\n    $b = 2;\n} while (true);"}
        }));
        return collection;
    }
}
