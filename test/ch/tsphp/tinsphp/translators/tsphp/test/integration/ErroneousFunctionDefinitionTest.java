/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file UseTest from the translator component of the TSPHP project.
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
public class ErroneousFunctionDefinitionTest extends ATranslatorTest
{

    public ErroneousFunctionDefinitionTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Override
    protected void checkInferencePhase(IInferenceEngine inferenceEngine) {
        Assert.assertFalse(testString + " failed. found a fatal error during the inference phase",
                inferenceEngine.hasFound(EnumSet.of(EIssueSeverity.FatalError)));

        Assert.assertTrue(testString + " failed. Expected an error but non occurred during the inference phase",
                inferenceEngine.hasFound(EnumSet.of(EIssueSeverity.Error)));
    }

    @Override
    public void log(TSPHPException exception, EIssueSeverity severity) {
        if (severity == EIssueSeverity.FatalError) {
            System.err.println("[" + severity.name() + "] " + exception.getMessage());
        }
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                //see TINS-678 NullPointer getErrMessage TranslatorController
                {
                        "<?php function foo($x){ return bar($x+1);}\nfunction bar(array $x){$x + [1];return [0];}\n",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed foo((array | {as (float | int)}) $x) {\n"
                                + "        return \\trigger_error('No applicable overload found for the function bar()"
                                + "'.PHP_EOL.'Given argument types: (float | int)'.PHP_EOL.'Existing overloads:'"
                                + ".PHP_EOL.'array -> array', \\E_USER_ERROR);\n"
                                + "    }\n"
                                + "\n"
                                + "    function array bar(array $x_0) {\n"
                                + "        array $x = $x_0;\n"
                                + "        $x + [1];\n"
                                + "        return [0];\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-678 NullPointer getErrMessage TranslatorController
                {
                        "<?php function foo($x){ return ~bar($x);}\nfunction bar(array $x){$x + [1];return [0];}\n",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed foo(array $x) {\n"
                                + "        return \\trigger_error('No applicable overload found for the operator ~'" +
                                ".PHP_EOL.'Given argument types: array'.PHP_EOL.'Existing overloads:'.PHP_EOL.'int ->" +
                                " int'.PHP_EOL.'float -> int'.PHP_EOL.'string -> string', \\E_USER_ERROR);\n"
                                + "    }\n"
                                + "\n"
                                + "    function array bar(array $x_0) {\n"
                                + "        array $x = $x_0;\n"
                                + "        $x + [1];\n"
                                + "        return [0];\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                }
        });
    }
}

