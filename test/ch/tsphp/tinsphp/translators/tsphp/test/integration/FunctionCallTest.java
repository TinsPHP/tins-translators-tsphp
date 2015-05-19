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
                                + "\n"
                                + "\n    function T1 foo0<T1>(T1 $x, T1 $y) where [T1 < (float | int)] {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function int foo1((falseType | trueType) $x, (falseType | trueType) $y) {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function array foo2(array $x, array $y) {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    $a = foo1(false, true);"
                                + "\n    $b = foo0(1, 2);"
                                + "\n    $c = foo0(1, 2.5);"
                                + "\n    $d = foo2([1], [1, 3, 4]);"
                                + "\n}"
                },
                //direct recursive function
                {
                        "<?php function fac($n){ return $n > 0 ? $n * fac($n-1): 1;} $a = fac(6); $b = fac(5.4);",
                        "namespace{"
                                + "\n    (float | int) $b;"
                                + "\n    int $a;"
                                + "\n"
                                + "\n    function T8 fac<T8>(T8 $n) where [int < T8 < (float | int)] {"
                                + "\n        return ($n > 0) ? $n * fac($n - 1) : 1;"
                                + "\n    }"
                                + "\n"
                                + "\n    $a = fac(6);"
                                + "\n    $b = fac(5.4);"
                                + "\n}"
                },
                //indirect recursive function
                {
                        "<?php function foo($x){ if($x > 0){return bar($x-1);} return $x;}"
                                + "function bar($x){ if($x > 0){return foo($x-1);} return $x;}",
                        "namespace{"
                                + "\n"
                                + "\n    function T4 foo<T4>(T4 $x) where [int < T4 < (float | int)] {"
                                + "\n        if ($x > 0) {"
                                + "\n            return bar($x - 1);"
                                + "\n        }"
                                + "\n        return $x;"
                                + "\n    }"
                                + "\n"
                                + "\n    function T4 bar<T4>(T4 $x) where [int < T4 < (float | int)] {"
                                + "\n        if ($x > 0) {"
                                + "\n            return foo($x - 1);"
                                + "\n        }"
                                + "\n        return $x;"
                                + "\n    }"
                                + "\n"
                                + "\n}"
                }
                //indirect recursive function with multiple overloads
                //TODO TINS-403 rename TypeVariables to reflect order of parameters - currently not testable since
                // type variables vary
//                {
//                        "<?php function rec1($x, $y){ return $x > 0 ? rec2($x + $y, $y) : $x; }\n"
//                                + "function rec2($x, $y){ return $x > 10 ? rec1($x + $y, $y) : $y; }"
//                                + "$i = rec1(5, 8);\n"
//                                + "$j = rec2([1, 2], [2]);",
//                        "namespace{\n"
//                                + "    array $j;\n"
//                                + "    int $i;\n"
//                                + "\n"
//                                + "    function T8 rec10<T8, T2, T5>(T2 $x, T5 $y) "
//                                + "where [(array | T2 | T5) < T8, T2 < array, T5 < array] {\n"
//                                + "        return ($x > 0) ? rec2($x + $y, $y) : $x;\n"
//                                + "    }\n"
//                                + "\n"
//                                + "    function T4 rec11<T4>(T4 $x, T4 $y) where [T4 < (float | int)] {\n"
//                                + "        return ($x > 0) ? rec2($x + $y, $y) : $x;\n"
//                                + "    }\n"
//                                + "\n"
//                                + "    function T4 rec20<T4>(T4 $x, T4 $y) where [T4 < (float | int)] {\n"
//                                + "        return ($x > 10) ? rec11($x + $y, $y) : $y;\n"
//                                + "    }\n"
//                                + "\n"
//                                + "    function T8 rec21<T8, T5>(array $x, T5 $y) "
//                                + "where [(array | T5) < T8, T5 < array] {\n"
//                                + "        return ($x > 10) ? rec10($x + $y, $y) : $y;\n"
//                                + "    }\n"
//                                + "\n"
//                                + "    $i = rec11(5, 8);\n"
//                                + "    $j = rec21([1, 2], [2]);\n"
//                                + "}"
//                },
        }));

        return collection;
    }
}
