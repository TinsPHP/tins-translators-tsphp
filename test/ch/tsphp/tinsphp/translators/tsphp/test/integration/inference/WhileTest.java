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

package ch.tsphp.tinsphp.translators.tsphp.test.integration.inference;

import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorInferenceStatementTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class WhileTest extends ATranslatorInferenceStatementTest
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
        return Arrays.asList(new Object[][]{
                {"while( true  ) $a=1;", "? $a;\n    while (true) {\n        $a = 1;\n    }"},
                {"while( true  ){$a=1;}", "? $a;\n    while (true) {\n        $a = 1;\n    }"},
                {
                        "while( true  ){$a=1; $b=2;}",
                        "? $b;\n    ? $a;\n    while (true) {\n        $a = 1;\n        $b = 2;\n    }"
                },
                {"do $a=1; while( true  );", "? $a;\n    do {\n        $a = 1;\n    } while (true);"},
                {"do {$a=1;} while( true  );", "? $a;\n    do {\n        $a = 1;\n    } while (true);"},
                {
                        "do {$a=1;$b=2;}while( true  );",
                        "? $b;\n    ? $a;\n    do {\n        $a = 1;\n        $b = 2;\n    } while (true);"
                }
        });
    }
}
