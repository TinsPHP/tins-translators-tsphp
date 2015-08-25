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


import ch.tsphp.common.exceptions.TSPHPException;
import ch.tsphp.tinsphp.common.IInferenceEngine;
import ch.tsphp.tinsphp.common.issues.EIssueSeverity;
import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

@RunWith(Parameterized.class)
public class ExpressionErrorTest extends ATranslatorTest
{

    public ExpressionErrorTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                //wrong operator usage should be reported in code as well
                {
                        "<?php function foo(){ return 1 + [0];}",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed foo() {\n"
                                + "        return \\trigger_error('"
                                + "No applicable overload found for the operator +'.PHP_EOL.'"
                                + "Given argument types: int x array'.PHP_EOL.'"
                                + "Existing overloads:'.PHP_EOL.'"
                                + "int x int -> int'.PHP_EOL.'"
                                + "float x float -> float'.PHP_EOL.'"
                                + "{as T} x {as T} -> T \\ T <: (float | int)'.PHP_EOL.'"
                                + "array x array -> array', \\E_USER_ERROR);\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TODO TINS-607 type hint and bounded polymorphic function
                //wrong function usage
                {
                        "<?php function foo(array $x){ return $x; } function bar(){ return foo(1);}",
                        "namespace{\n"
                                + "\n"
                                + "    function T foo<T>(array $x_0) where [T <: array] {\n"
                                + "        T $x = $x_0;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function mixed bar() {\n"
                                + "        return \\trigger_error('"
                                + "No applicable overload found for the function foo()'.PHP_EOL.'"
                                + "Given argument types: int'.PHP_EOL.'"
                                + "Existing overloads:'.PHP_EOL.'"
                                + "T -> T \\ T <: array', \\E_USER_ERROR);\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php $a = 2; foreach($a as $v){}",
                        "namespace{\n"
                                + "    mixed $v;\n"
                                + "    int $a;\n"
                                + "    $a = 2;\n"
                                + "    \\trigger_error('No applicable overload found for the operator foreach'" +
                                ".PHP_EOL.'Given argument types: int x mixed x int'.PHP_EOL.'Existing overloads:'" +
                                ".PHP_EOL.'array x mixed x (int | string) -> mixed', \\E_USER_ERROR)\n"
                                + "}"
                },
        });
    }

    @Override
    public void log(TSPHPException exception, EIssueSeverity severity) {
        if (severity != EIssueSeverity.Error) {
            System.err.println(exception.getMessage());
        }
    }

    @Override
    protected void checkInferencePhase(IInferenceEngine inferenceEngine) {
        Assert.assertTrue(testString + " failed. We expected an issue of severity error but none happened",
                inferenceEngine.hasFound(EnumSet.of(EIssueSeverity.Error)));
    }
}
