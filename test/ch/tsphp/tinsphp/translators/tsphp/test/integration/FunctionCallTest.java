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
                        "<?php function foo($x, $y){ return $x+$y;}"
                                + "\n$a = foo(false,true); $b = foo(1, 2); $c = foo(1, 2.5); $d = foo([1], [1, 3, 4]);",
                        "namespace{"
                                + "\n    array $d;"
                                + "\n    float $c;"
                                + "\n    int $b;"
                                + "\n    int $a;"
                                + "\n"
                                + "\n    function array foo0(array $x, array $y) {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function float foo1(float $x, float $y) {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function float foo2(float $x, {as (float | int)} $y) {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function int foo3(int $x, int $y) {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function float foo4({as (float | int)} $x, float $y) {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function T foo5<T>({as T} $x, {as T} $y) where [T < (float | int)] {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    $a = foo5(false, true);"
                                + "\n    $b = foo3(1, 2);"
                                + "\n    $c = foo4(1, 2.5);"
                                + "\n    $d = foo0([1], [1, 3, 4]);"
                                + "\n}"
                },
                //TODO TINS-496 not all overloads calculated
                //direct recursive function
//                {
//                        "<?php function fac($n){ return $n > 0 ? $n * fac($n-1): 1;} $a = fac(6); $b = fac(5.4);",
//                        "namespace{"
//                                + "\n\n    (float | int) $b;"
//                                + "\n\n    int $a;"
//                                + "\n"
//                                + "\n\n    function T8 fac<T8>(T8 $n) where [int < T8 < (float | int)] {"
//                                + "\n\n        return ($n > 0) ? $n * fac($n - 1) : 1;"
//                                + "\n\n    }"
//                                + "\n"
//                                + "\n\n    $a = fac(6);"
//                                + "\n\n    $b = fac(5.4);"
//                                + "\n\n}"
//                },
//                //indirect recursive function
//                {
//                        "<?php function foo($x){ if($x > 0){return bar($x-1);} return $x;}"
//                                + "\nfunction bar($x){ if($x > 0){return foo($x-1);} return $x;}",
//                        "namespace{"
//                                + ""
//                                + "\n    function T4 foo<T4>(T4 $x) where [int < T4 < (float | int)] {"
//                                + "\n        if ($x > 0) {"
//                                + "\n            return bar($x - 1);"
//                                + "\n        }"
//                                + "\n        return $x;"
//                                + "\n    }"
//                                + ""
//                                + "\n    function T4 bar<T4>(T4 $x) where [int < T4 < (float | int)] {"
//                                + "\n        if ($x > 0) {"
//                                + "\n            return foo($x - 1);"
//                                + "\n        }"
//                                + "\n        return $x;"
//                                + "\n    }"
//                                + ""
//                                + "\n}"
//                },
                //indirect recursive function with erroneous overloads (bool x bool -> int) is no longer valid if $y
                // is restricted to Ty <: (int|float), Ty <: array respectively.
                {
                        "<?php function foo($x, $y){ return $x > 0 ?  bar($x + $y, $y) : $x; }"
                                + "\nfunction bar($x, $y){ return $x > 10 ? foo($x + $y, $y) : $y;}",
                        "namespace{"
                                + "\n"
                                + "\n    function V9 foo0<V9, T>(T $x, float $y) where [(float | T) < V9, " +
                                "T < {as (float | int)}] {"
                                + "\n        return ($x > 0) ? bar1($x + $y, $y) : $x;"
                                + "\n    }"
                                + "\n"
                                + "\n    function V9 foo1<V9, T>(T $x, int $y) where [(int | T) < V9, T < {as int}] {"
                                + "\n        return ($x > 0) ? bar2($x + $y, $y) : $x;"
                                + "\n    }"
                                + "\n"
                                + "\n    function array foo2(array $x, array $y) {"
                                + "\n        return ($x > 0) ? bar0($x + $y, $y) : $x;"
                                + "\n    }"
                                + "\n"
                                + "\n    function float foo3(float $x, float $y) {"
                                + "\n        return ($x > 0) ? bar1($x + $y, $y) : $x;"
                                + "\n    }"
                                + "\n"
                                + "\n    function int foo4(int $x, int $y) {"
                                + "\n        return ($x > 0) ? bar2($x + $y, $y) : $x;"
                                + "\n    }"
                                + "\n"
                                + "\n    function array bar0(array $x, array $y) {"
                                + "\n        return ($x > 10) ? foo2($x + $y, $y) : $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function float bar1(float $x, float $y) {"
                                + "\n        return ($x > 10) ? foo3($x + $y, $y) : $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function int bar2(int $x, int $y) {"
                                + "\n        return ($x > 10) ? foo4($x + $y, $y) : $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function float bar3({as (float | int)} $x, float $y) {"
                                + "\n        return ($x > 10) ? foo3($x + $y, $y) : $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function int bar4({as int} $x, int $y) {"
                                + "\n        return ($x > 10) ? foo4($x + $y, $y) : $y;"
                                + "\n    }"
                                + "\n"
                                + "\n}" +
                                ""
                },
                //indirect recursive function which produces more overloads once the dependent function is known. An
                //erroneous one (bool x bool -> int) and a valid one (array x array -> array)
                {
                        "<?php function rec1($x, $y){ return $x > 0 ? rec2($x + $y, $y) : $x; }"
                                + "\nfunction rec2($x, $y){ return $x > 10 ? rec1($x + $y, $y) : $y; }"
                                + "\n$i = rec1(5, 8);"
                                + "\n$j = rec2([1, 2], [2]);",
                        "namespace{"
                                + "\n    array $j;"
                                + "\n    int $i;"
                                + "\n"
                                + "\n    function V9 rec10<V9, T>(T $x, float $y) where [(float | T) < V9, " +
                                "T < {as (float | int)}] {"
                                + "\n        return ($x > 0) ? rec21($x + $y, $y) : $x;"
                                + "\n    }"
                                + "\n"
                                + "\n    function V9 rec11<V9, T>(T $x, int $y) where [(int | T) < V9, T < {as int}] {"
                                + "\n        return ($x > 0) ? rec22($x + $y, $y) : $x;"
                                + "\n    }"
                                + "\n"
                                + "\n    function array rec12(array $x, array $y) {"
                                + "\n        return ($x > 0) ? rec20($x + $y, $y) : $x;"
                                + "\n    }"
                                + "\n"
                                + "\n    function float rec13(float $x, float $y) {"
                                + "\n        return ($x > 0) ? rec21($x + $y, $y) : $x;"
                                + "\n    }"
                                + "\n"
                                + "\n    function int rec14(int $x, int $y) {"
                                + "\n        return ($x > 0) ? rec22($x + $y, $y) : $x;"
                                + "\n    }"
                                + "\n"
                                + "\n    function array rec20(array $x, array $y) {"
                                + "\n        return ($x > 10) ? rec12($x + $y, $y) : $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function float rec21(float $x, float $y) {"
                                + "\n        return ($x > 10) ? rec13($x + $y, $y) : $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function int rec22(int $x, int $y) {"
                                + "\n        return ($x > 10) ? rec14($x + $y, $y) : $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function float rec23({as (float | int)} $x, float $y) {"
                                + "\n        return ($x > 10) ? rec13($x + $y, $y) : $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function int rec24({as int} $x, int $y) {"
                                + "\n        return ($x > 10) ? rec14($x + $y, $y) : $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    $i = rec14(5, 8);"
                                + "\n    $j = rec20([1, 2], [2]);"
                                + "\n}" +
                                ""
                },
        }));

        return collection;
    }
}
