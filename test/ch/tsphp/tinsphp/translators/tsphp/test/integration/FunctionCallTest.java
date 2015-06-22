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
                                + "\n    function int foo2(int $x, int $y) {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    function T foo3<T>({as T} $x, {as T} $y) where [T < (float | int)] {"
                                + "\n        return $x + $y;"
                                + "\n    }"
                                + "\n"
                                + "\n    $a = foo3(false, true);"
                                + "\n    $b = foo2(1, 2);"
                                + "\n    $c = foo3(1, 2.5);"
                                + "\n    $d = foo0([1], [1, 3, 4]);"
                                + "\n}"
                },
                //direct recursive function
                {
                        "<?php function fac($n){ return $n > 0 ? $n * fac($n-1): 1;} $a = fac(6); $b = fac(5.4);",
                        "namespace{\n"
                                + "    int $b;\n"
                                + "    int $a;\n"
                                + "\n"
                                + "    function int fac0(int $n) {\n"
                                + "        return ($n > 0) ? $n * fac($n - 1) : 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (int | T) fac1<T>({as T} $n) where [T < (float | int)] {\n"
                                + "        return ($n > 0) ? $n * fac($n - 1) : 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    $a = fac0(6);\n"
                                + "    $b = fac1(5.4);\n"
                                + "}"
                },
                //indirect recursive function
                {
                        "<?php function foo($x){ if($x > 0){return bar($x-1);} return $x;}"
                                + "\nfunction bar($x){ if($x > 0){return foo($x-1);} return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function (int | T) foo0<T>(T $x) where [T < {as int}] {\n"
                                + "        if ($x > 0) {\n"
                                + "            return bar1($x - 1);\n"
                                + "        }\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int foo1(int $x) {\n"
                                + "        if ($x > 0) {\n"
                                + "            return bar1($x - 1);\n"
                                + "        }\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (int | T) bar0<T>(T $x) where [T < {as int}] {\n"
                                + "        if ($x > 0) {\n"
                                + "            return foo1($x - 1);\n"
                                + "        }\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int bar1(int $x) {\n"
                                + "        if ($x > 0) {\n"
                                + "            return foo1($x - 1);\n"
                                + "        }\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //indirect recursive function with erroneous overloads (bool x bool -> int) is no longer valid if $y
                // is restricted to Ty <: (int|float), Ty <: array respectively.
                {
                        "<?php function foo($x, $y){ return $x > 0 ?  bar($x + $y, $y) : $x; }"
                                + "\nfunction bar($x, $y){ return $x > 10 ? foo($x + $y, $y) : $y;}",
                        "namespace{\n"
                                + "\n"
                                + "    function (int | T) foo0<T>(T $x, int $y) where [T < {as int}] {\n"
                                + "        return ($x > 0) ? bar2($x + $y, $y) : $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function array foo1(array $x, array $y) {\n"
                                + "        return ($x > 0) ? bar0($x + $y, $y) : $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float foo2(float $x, float $y) {\n"
                                + "        return ($x > 0) ? bar1($x + $y, $y) : $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int foo3(int $x, int $y) {\n"
                                + "        return ($x > 0) ? bar2($x + $y, $y) : $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function array bar0(array $x, array $y) {\n"
                                + "        return ($x > 10) ? foo1($x + $y, $y) : $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float bar1(float $x, float $y) {\n"
                                + "        return ($x > 10) ? foo2($x + $y, $y) : $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int bar2(int $x, int $y) {\n"
                                + "        return ($x > 10) ? foo3($x + $y, $y) : $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int bar3({as int} $x, int $y) {\n"
                                + "        return ($x > 10) ? foo3($x + $y, $y) : $y;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //indirect recursive function which produces more overloads once the dependent function is known. An
                //erroneous one (bool x bool -> int) and a valid one (array x array -> array)
                {
                        "<?php function rec1($x, $y){ return $x > 0 ? rec2($x + $y, $y) : $x; }"
                                + "\nfunction rec2($x, $y){ return $x > 10 ? rec1($x + $y, $y) : $y; }"
                                + "\n$i = rec1(5, 8);"
                                + "\n$j = rec2([1, 2], [2]);",
                        "namespace{\n"
                                + "    array $j;\n"
                                + "    int $i;\n"
                                + "\n"
                                + "    function (int | T) rec10<T>(T $x, int $y) where [T < {as int}] {\n"
                                + "        return ($x > 0) ? rec22($x + $y, $y) : $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function array rec11(array $x, array $y) {\n"
                                + "        return ($x > 0) ? rec20($x + $y, $y) : $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float rec12(float $x, float $y) {\n"
                                + "        return ($x > 0) ? rec21($x + $y, $y) : $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int rec13(int $x, int $y) {\n"
                                + "        return ($x > 0) ? rec22($x + $y, $y) : $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function array rec20(array $x, array $y) {\n"
                                + "        return ($x > 10) ? rec11($x + $y, $y) : $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float rec21(float $x, float $y) {\n"
                                + "        return ($x > 10) ? rec12($x + $y, $y) : $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int rec22(int $x, int $y) {\n"
                                + "        return ($x > 10) ? rec13($x + $y, $y) : $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int rec23({as int} $x, int $y) {\n"
                                + "        return ($x > 10) ? rec13($x + $y, $y) : $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    $i = rec13(5, 8);\n"
                                + "    $j = rec20([1, 2], [2]);\n"
                                + "}"
                },
                //soft typing and multiple overloads apply, need to fallback to dynamic version (function call in bar
                // is fooA)
                {
                        "<?php function fooA($x){ return $x + 1;}"
                                + "\nfunction barA(array $x){ $x = 1; return fooA($x); }"
                                + "\n$i = fooA(1);"
                                + "\n$j = barA([1, 2]);",
                        "namespace{\n"
                                + "    int $j;\n"
                                + "    int $i;\n"
                                + "\n"
                                + "    function int fooA0(int $x) {\n"
                                + "        return $x + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T fooA1<T>({as T} $x) where [int < T < (float | int)] {\n"
                                + "        return $x + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int barA(array $x_0) {\n"
                                + "        (array | {as (float | int)}) $x = $x_0;\n"
                                + "        $x = 1;\n"
                                + "        return fooA(({as (float | int)}) ($x));\n"
                                + "    }\n"
                                + "\n"
                                + "    $i = fooA0(1);\n"
                                + "    $j = barA([1, 2]);\n"
                                + "}"
                }
        }));

        return collection;
    }
}
