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


import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorInferenceTest;
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
public class FunctionCallTest extends ATranslatorInferenceTest
{

    public FunctionCallTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();
        collection.addAll(Arrays.asList(new Object[][]{
                {
                        "<?php function foo($x, $y){ return $x+$y;}\n"
                                + "$a = foo(false,true); $b = foo(1, 2); $c = foo(1, 2.5); $d = foo([1], [1, 3, 4]);",
                        "namespace{"
                                + "\n    array $d;"
                                + "\n    (float | int) $c;"
                                + "\n    int $b;"
                                + "\n    int $a;"
                                + "\n    function T1 foo0[T1 < num](T1 $x, T1 $y) {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function int foo1(bool $x, bool $y) {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function array foo2(array $x, array $y) {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n    $a = foo1(false, true);"
                                + "\n    $b = foo0(1, 2);"
                                + "\n    $c = foo0(1, 2.5);"
                                + "\n    $d = foo2([1], [1, 3, 4]);"
                                + "\n}"
                }
        }));

        return collection;
    }
}
