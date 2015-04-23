/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file IfTest from the translator component of the TSPHP project.
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
public class IfTest extends ATranslatorInferenceStatementTest
{

    public IfTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {
                        "if(true){$a=1;}",
                        "? $a;\n    if (true) {\n        $a = 1;\n    }"
                },
                {
                        "if(true) $a=1; else if(false) $b=1; else $c=2;",
                        "? $c;\n    ? $b;\n    ? $a;\n    "
                                + "if (true) {\n        $a = 1;\n    } else {\n        "
                                + "if (false) {\n            $b = 1;\n        } else {\n            $c = 2;\n        }"
                                + "\n    }"
                },
                {
                        "if(true) $a=1; else if(false) $b=1; else if(false && true) $c=2; else $d=1;",
                        "? $d;\n    ? $c;\n    ? $b;\n    ? $a;\n    "
                                + "if (true) {\n        $a = 1;\n    } else {\n        "
                                + "if (false) {\n            $b = 1;\n        } else {\n            "
                                + "if (false && true) {\n                $c = 2;\n            } "
                                + "else {\n                $d = 1;\n            }"
                                + "\n        }"
                                + "\n    }"
                },
                {"$x = true; if($x){}", "? $x;\n    $x = true;\n    if ($x) {\n    }"}
        });
    }
}
