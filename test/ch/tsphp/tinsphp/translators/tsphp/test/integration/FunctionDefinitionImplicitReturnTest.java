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


import ch.tsphp.tinsphp.common.IInferenceEngine;
import ch.tsphp.tinsphp.common.issues.EIssueSeverity;
import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorInferenceTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

@RunWith(Parameterized.class)
public class FunctionDefinitionImplicitReturnTest extends ATranslatorInferenceTest
{

    public FunctionDefinitionImplicitReturnTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Override
    protected void checkReferencePhase(IInferenceEngine inferenceEngine) {
        Assert.assertTrue(testString + " failed. no return / partial return from function expected but not reported",
                inferenceEngine.hasFound(EnumSet.allOf(EIssueSeverity.class)));
    }

    @Override
    protected void checkInferencePhase(IInferenceEngine inferenceEngine) {
        Assert.assertTrue(testString + " failed. no return / partial return from function expected but not reported",
                inferenceEngine.hasFound(EnumSet.allOf(EIssueSeverity.class)));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();
        collection.addAll(Arrays.asList(new Object[][]{
                //no return no param
                {
                        "<?php function foo(){} ?>",
                        "namespace{\n\n    function nullType foo() {\n        return null;\n    }\n\n}"
                },
                //no return with param
                {
                        "<?php function foo($x){} ?>",
                        "namespace{\n\n    function nullType foo(mixed $x) {\n        return null;\n    }\n\n}"
                },
                //see TINS-449 unused ad-hoc polymorphic parameters
                {
                        "<?php function foo($x, $y){$a = $x + $y;}",
                        "namespace{\n"
                                + ""
                                + "\n    function nullType foo0(array $x, array $y) {"
                                + "\n        array $a;"
                                + "\n        $a = $x + $y;"
                                + "\n        return null;"
                                + "\n    }"
                                + "\n"
                                + "\n    function nullType foo1(float $x, float $y) {"
                                + "\n        float $a;"
                                + "\n        $a = $x + $y;"
                                + "\n        return null;"
                                + "\n    }"
                                + "\n"
                                + "\n    function nullType foo2(float $x, {as (float | int)} $y) {"
                                + "\n        float $a;"
                                + "\n        $a = $x + $y;"
                                + "\n        return null;"
                                + "\n    }"
                                + "\n"
                                + "\n    function nullType foo3(int $x, int $y) {"
                                + "\n        int $a;"
                                + "\n        $a = $x + $y;"
                                + "\n        return null;"
                                + "\n    }"
                                + "\n"
                                + "\n    function nullType foo4({as (float | int)} $x, float $y) {"
                                + "\n        float $a;"
                                + "\n        $a = $x + $y;"
                                + "\n        return null;"
                                + "\n    }"
                                + "\n"
                                + "\n    function nullType foo5({as (float | int)} $x, {as (float | int)} $y) {"
                                + "\n        (float | int) $a;"
                                + "\n        $a = $x + $y;"
                                + "\n        return null;"
                                + "\n    }"
                                + "\n"
                                + "\n}"
                },
                //partial return no param
                {
                        "<?php function foo(){if(true){return 1;}} ?>",
                        "namespace{"
                                + "\n"
                                + "\n    function (int | nullType) foo() {"
                                + "\n        if (true) {"
                                + "\n            return 1;"
                                + "\n        }"
                                + "\n        return null;"
                                + "\n    }"
                                + "\n"
                                + "\n}"
                },
                //partial return with param
                {
                        "<?php function foo($x){if($x){return 1;}} ?>",
                        "namespace{"
                                + "\n"
                                + "\n    function (int | nullType) foo0((falseType | trueType) $x) {"
                                + "\n        if ($x) {"
                                + "\n            return 1;"
                                + "\n        }"
                                + "\n        return null;"
                                + "\n    }"
                                + "\n"
                                + "\n    function (int | nullType) foo1({as (falseType | trueType)} $x) {"
                                + "\n        if ($x) {"
                                + "\n            return 1;"
                                + "\n        }"
                                + "\n        return null;"
                                + "\n    }"
                                + "\n"
                                + "\n}"
                },
        }));

        return collection;
    }
}
