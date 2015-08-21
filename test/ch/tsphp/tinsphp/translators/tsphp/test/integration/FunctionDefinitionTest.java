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


import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FunctionDefinitionTest extends ATranslatorTest
{

    public FunctionDefinitionTest(String testString, String expectedResult) {
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
                        "<?php function fooA($x){return $x;} ?>",
                        "namespace{\n\n    function T fooA<T>(T $x) {\n        return $x;\n    }\n\n}"
                },
                {
                        "<?php function fooB($x, $y){return $x + $y;} ?>",
                        "namespace{\n"
                                + "\n"
                                + "    function array fooB0(array $x, array $y) {\n"
                                + "        return $x + $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float fooB1(float $x, float $y) {\n"
                                + "        return $x + $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int fooB2(int $x, int $y) {\n"
                                + "        return $x + $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T fooB3<T>({as T} $x, {as T} $y) where [T <: (float | int)] {\n"
                                + "        return $x + $y;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooC($x, $y){return $x + $y + 1;} ?>",
                        "namespace{\n"
                                + "\n"
                                + "    function float fooC0(float $x, float $y) {\n"
                                + "        return $x + $y + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int fooC1(int $x, int $y) {\n"
                                + "        return $x + $y + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (int | T) fooC2<T>({as T} $x, {as T} $y) where [T <: (float | int)] {\n"
                                + "        return $x + $y + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //return but without expression
                // see TINS-404 return without expression and implicit null
                {
                        "<?php function fooD(){ return;} ?>",
                        "namespace{\n\n    function nullType fooD() {\n        return null;\n    }\n\n}"
                },
                {
                        "<?php function fooE($x){ if($x){ return; } return 1;} ?>",
                        "namespace{\n"
                                + "\n"
                                + "    function (int | nullType) fooE0((falseType | trueType) $x) {\n"
                                + "        if ($x) {\n"
                                + "            return null;\n"
                                + "        }\n"
                                + "        return 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (int | nullType) fooE1({as (falseType | trueType)} $x) {\n"
                                + "        if ($x) {\n"
                                + "            return null;\n"
                                + "        }\n"
                                + "        return 1;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooF($x){ if($x){ return; } else { return 1;}} ?>",
                        "namespace{\n"
                                + "\n"
                                + "    function (int | nullType) fooF0((falseType | trueType) $x) {\n"
                                + "        if ($x) {\n"
                                + "            return null;\n"
                                + "        } else {\n"
                                + "            return 1;\n"
                                + "        }\n"
                                + "    }\n"
                                + "\n"
                                + "    function (int | nullType) fooF1({as (falseType | trueType)} $x) {\n"
                                + "        if ($x) {\n"
                                + "            return null;\n"
                                + "        } else {\n"
                                + "            return 1;\n"
                                + "        }\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooG($x, $y){ $x = $y; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T1 fooG<T1, T2>(T1 $x, T2 $y) where [T2 <: T1] {\n"
                                + "        $x = $y;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooH($x, $y){ $a = $x + $y; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function array fooH0(array $x, array $y) {\n"
                                + "        array $a;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float fooH1(float $x, float $y) {\n"
                                + "        float $a;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int fooH2(int $x, int $y) {\n"
                                + "        int $a;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T fooH3<T>({as T} $x, {as T} $y) where [T <: (float | int)] {\n"
                                + "        T $a;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooI($x){ return $x /= false;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T fooI<T>(T $x) "
                                + "where [(falseType | float | int) <: T <: {as (float | int)}] {\n"
                                + "        return $x /= false;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooJ($x, $y){ return $x /= $y;}",
                        "namespace{\n"
                                + "\n"
                                + "    function (falseType | float) fooJ0((falseType | float) $x, float $y) {\n"
                                + "        return $x /= $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T fooJ1<T>(T $x, float $y) "
                                + "where [(falseType | float) <: T <: {as (float | int)}] {\n"
                                + "        return $x /= $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T fooJ2<T>(T $x, {as (float | int)} $y) "
                                + "where [(falseType | float | int) <: T <: {as (float | int)}] {\n"
                                + "        return $x /= $y;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TINS-479 local vars with multiple lower ref to params
                {
                        "<?php function fooK($x, $y){ $a = $x / $y; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function (falseType | float) fooK0(float $x, float $y) {\n"
                                + "        (falseType | float) $a;\n"
                                + "        $a = $x / $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (falseType | float) fooK1(float $x, {as (float | int)} $y) {\n"
                                + "        (falseType | float) $a;\n"
                                + "        $a = $x / $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (falseType | float | int) fooK2(int $x, int $y) {\n"
                                + "        (falseType | float | int) $a;\n"
                                + "        $a = $x / $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (falseType | float) fooK3({as (float | int)} $x, float $y) {\n"
                                + "        (falseType | float) $a;\n"
                                + "        $a = $x / $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (falseType | float | int) fooK4("
                                + "{as (float | int)} $x, {as (float | int)} $y) {\n"
                                + "        (falseType | float | int) $a;\n"
                                + "        $a = $x / $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}" +
                                ""
                },
                //TINS-479 local vars with multiple lower ref to params
                {
                        "<?php function fooM($x, $y){ $a = 1; $a = $x + $y; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function (array | int) fooM0(array $x, array $y) {\n"
                                + "        (array | int) $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (float | int) fooM1(float $x, float $y) {\n"
                                + "        (float | int) $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int fooM2(int $x, int $y) {\n"
                                + "        int $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (int | T) fooM3<T>({as T} $x, {as T} $y) where [T <: (float | int)] {\n"
                                + "        (int | T) $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },

                //see TINS-442 blank line around functions
                {
                        "<?php function foo(){return 1;} function bar(){return 2;}",
                        "namespace{"
                                + "\n"
                                + "\n    function int foo() {"
                                + "\n        return 1;"
                                + "\n    }"
                                + "\n"
                                + "\n    function int bar() {"
                                + "\n        return 2;"
                                + "\n    }"
                                + "\n"
                                + "\n}"
                },
                //see TINS-442 blank line around functions
                {
                        "<?php function foo(){return 1;} $a = 'hello'; "
                                + "function bar(){return 2;} function baz(){return 3;}",
                        "namespace{"
                                + "\n    string $a;"
                                + "\n"
                                + "\n    function int foo() {"
                                + "\n        return 1;"
                                + "\n    }"
                                + "\n"
                                + "\n    $a = 'hello';"
                                + "\n"
                                + "\n    function int bar() {"
                                + "\n        return 2;"
                                + "\n    }"
                                + "\n"
                                + "\n    function int baz() {"
                                + "\n        return 3;"
                                + "\n    }"
                                + "\n"
                                + "\n}"
                },
                //TINS-396 create local variable for parameters with type hints
                {
                        "<?php function typeHint(array \n$a){ return 1;}",
                        "namespace{"
                                + "\n"
                                + "\n    function int typeHint(array $a_0) {"
                                + "\n        mixed $a = $a_0;"
                                + "\n        return 1;"
                                + "\n    }"
                                + "\n"
                                + "\n}"
                },
                {
                        "<?php function typeHint(array \n$a, array \n$b){return $a + $b;}",
                        "namespace{"
                                + "\n"
                                + "\n    function array typeHint(array $a_0, array $b_0) {"
                                + "\n        array $b = $b_0;"
                                + "\n        array $a = $a_0;"
                                + "\n        return $a + $b;"
                                + "\n    }"
                                + "\n"
                                + "\n}"
                },
                {
                        "<?php function foo($x){if(true){return ~$x;} return $x + [1];}",
                        "namespace{\n"
                                + "\n"
                                + "    function (array | int | string) foo((array | {as (float | int)}) $x) {\n"
                                + "        if (true) {\n"
                                + "            return ~cast<(float | int | string)>($x);\n"
                                + "        }\n"
                                + "        return cast<array>($x) + [1];\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooN($x){echo $x; return $x && true; }",
                        "namespace{\n"
                                + "\n"
                                + "    function (falseType | trueType) fooN0("
                                + "({as (falseType | trueType)} & {as string}) $x) {\n"
                                + "        echo $x;\n"
                                + "        return $x && true;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (falseType | trueType) fooN1(string $x) {\n"
                                + "        echo $x;\n"
                                + "        return $x && true;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooO($x){if(true){return $x;} return 1;}",
                        "namespace{\n"
                                + "\n"
                                + "    function (int | T) fooO<T>(T $x) {\n"
                                + "        if (true) {\n"
                                + "            return $x;\n"
                                + "        }\n"
                                + "        return 1;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooP($x){if(true){return $x + 1;} return 'hello';} $a = fooP(2); $b = ~$a;",
                        "namespace{\n"
                                + "    (int | string) $b;\n"
                                + "    (int | string) $a;\n"
                                + "\n"
                                + "    function (int | string) fooP0(int $x) {\n"
                                + "        if (true) {\n"
                                + "            return $x + 1;\n"
                                + "        }\n"
                                + "        return 'hello';\n"
                                + "    }\n"
                                + "\n"
                                + "    function (int | string | T) fooP1<T>({as T} $x) where [int <: T <: (float | " +
                                "int)] {\n"
                                + "        if (true) {\n"
                                + "            return $x + 1;\n"
                                + "        }\n"
                                + "        return 'hello';\n"
                                + "    }\n"
                                + "\n"
                                + "    $a = fooP0(2);\n"
                                + "    $b = ~cast<(int | string)>($a);\n"
                                + "}"
                },
                {
                        "<?php function barQ($x){$x . 1; return $x;} "
                                + "function fooQ($x, $y){return 1; return $x + '1'; return barQ($y);}",
                        "namespace{\n"
                                + "\n"
                                + "    function T barQ<T>(T $x) where [T <: {as string}] {\n"
                                + "        $x . 1;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (float | int | T) fooQ<T>({as (float | int)} $x, T $y) "
                                + "where [T <: {as string}] {\n"
                                + "        return 1;\n"
                                + "        return $x + '1';\n"
                                + "        return barQ($y);\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooR($x, $y){ $x = 1 + $y; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T fooR0<T>(T $x, int $y) where [int <: T] {\n"
                                + "        $x = 1 + $y;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T1 fooR1<T1, T2>(T1 $x, {as T2} $y) "
                                + "where [(int | T2) <: T1, int <: T2 <: (float | int)] {\n"
                                + "        $x = 1 + $y;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooS($x){$a = $x; $a = 'hello'; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T fooS<T>(T $x) {\n"
                                + "        (string | T) $a;\n"
                                + "        $a = $x;\n"
                                + "        $a = 'hello';\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooU($x, $y, $z){$x = $y; $x = $z; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T1 fooU<T1, T2, T3>(T1 $x, T2 $y, T3 $z) where [(T2 | T3) <: T1] {\n"
                                + "        $x = $y;\n"
                                + "        $x = $z;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooU2($x, $y, $z, $a){$a = $x; $x = $y; $x = $z; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T4 fooU2<T1, T2, T3, T4>(T1 $x, T2 $y, T3 $z, T4 $a) "
                                + "where [(T2 | T3) <: T1, T1 <: T4] {\n"
                                + "        $a = $x;\n"
                                + "        $x = $y;\n"
                                + "        $x = $z;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooU3($x, $y, $z){$x = $y; $x = $z; $x = 1; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T1 fooU3<T1, T2, T3>(T1 $x, T2 $y, " +
                                "T3 $z) where [(int | T2 | T3) <: T1] {\n"
                                + "        $x = $y;\n"
                                + "        $x = $z;\n"
                                + "        $x = 1;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooU4($x, $y){$x = $y; $y = 1; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T1 fooU4<T1, T2>(T1 $x, T2 $y) where [(int | T2) <: T1, int <: T2] {\n"
                                + "        $x = $y;\n"
                                + "        $y = 1;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooU5($x, $y){$x = $y; $x = 1; $y = 'hello'; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T1 fooU5<T1, T2>(T1 $x, T2 $y) "
                                + "where [(int | string | T2) <: T1, string <: T2] {\n"
                                + "        $x = $y;\n"
                                + "        $x = 1;\n"
                                + "        $y = 'hello';\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooV($x, $y){$a = $x; $a = $y; $a = 1; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function (int | T1 | T2) fooV<T1, T2>(T1 $x, T2 $y) {\n"
                                + "        (int | T1 | T2) $a;\n"
                                + "        $a = $x;\n"
                                + "        $a = $y;\n"
                                + "        $a = 1;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooV2($x, $y){$a = $x; ~$x; $a = $y; $a = 1; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function (int | T1 | T2) fooV20<T1, T2>(T1 $x, T2 $y) where [T1 <: float] {\n"
                                + "        (int | T1 | T2) $a;\n"
                                + "        $a = $x;\n"
                                + "        ~$x;\n"
                                + "        $a = $y;\n"
                                + "        $a = 1;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (int | T1 | T2) fooV21<T1, T2>(T1 $x, T2 $y) where [T1 <: string] {\n"
                                + "        (int | T1 | T2) $a;\n"
                                + "        $a = $x;\n"
                                + "        ~$x;\n"
                                + "        $a = $y;\n"
                                + "        $a = 1;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (int | T) fooV22<T>(int $x, T $y) {\n"
                                + "        (int | T) $a;\n"
                                + "        $a = $x;\n"
                                + "        ~$x;\n"
                                + "        $a = $y;\n"
                                + "        $a = 1;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fac($x){return $x > 0 ? $x * fac($x) : $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function (T1 | T2) fac0<T1, T2>(T1 $x) "
                                + "where [T1 <: {as T2}, T2 <: (float | int)] {\n"
                                + "        return ($x > 0) ? $x * fac($x) : $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float fac1(float $x) {\n"
                                + "        return ($x > 0) ? $x * fac($x) : $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int fac2(int $x) {\n"
                                + "        return ($x > 0) ? $x * fac($x) : $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooW($x, $y){$x . 1; $y & 1.2; return $x; return $y;}",
                        "namespace{\n"
                                + "\n"
                                + "    function (T1 | T2) fooW<T1, T2>(T1 $x, T2 $y) "
                                + "where [T1 <: {as string}, T2 <: (array | {as int})] {\n"
                                + "        $x . 1;\n"
                                + "        $y & 1.2;\n"
                                + "        return $x;\n"
                                + "        return $y;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                }
        });
    }
}
