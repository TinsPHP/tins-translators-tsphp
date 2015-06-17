/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright; license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file ExpressionTest from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration;


import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorInferenceTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MigrationFunctionTest extends ATranslatorInferenceTest
{

    public MigrationFunctionTest(String testString, String expectedResult) {
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
                        "<?php function foo(array $x){ $x = 1; return $x + 1;}",
                        "namespace{\n"
                                + "\n"
                                + "    function int foo(array $x_0) {\n"
                                + "        (array | int) $x = $x_0;\n"
                                + "        $x = 1;\n"
                                + "        return (int) oldSchoolAddition((int) $x, 1);\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo(array $x){ $x = 1.5; return 1.2 + $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function float foo(array $x_0) {\n"
                                + "        (array | float) $x = $x_0;\n"
                                + "        $x = 1.5;\n"
                                + "        return (float) oldSchoolAddition(1.2, (float) $x);\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo(array $x){ $x = 1.5; return 1.2 + $x + 1;}",
                        "namespace{\n"
                                + "\n"
                                + "    function float foo(array $x_0) {\n"
                                + "        (array | float) $x = $x_0;\n"
                                + "        $x = 1.5;\n"
                                + "        return (float) oldSchoolAddition(1.2, (float) $x) + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                }
        });
    }

}
