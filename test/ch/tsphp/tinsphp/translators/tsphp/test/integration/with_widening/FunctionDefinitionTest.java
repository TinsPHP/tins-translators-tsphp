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

package ch.tsphp.tinsphp.translators.tsphp.test.integration.with_widening;


import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorWithWideningTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FunctionDefinitionTest extends ATranslatorWithWideningTest
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
                                + "    function T fooB3<T>({as T} $x, {as T} $y) where [T <: num] {\n"
                                + "        return cast<T>(oldSchoolAddition($x, $y));\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-584 find least upper reference bound - initially we had (int | T) as return type of fooC2
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
                                + "    function T fooC2<T>({as T} $x, {as T} $y) where [int <: T <: num] {\n"
                                + "        return cast<T>(oldSchoolAddition($x, $y)) + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //return but without expression
                // see TINS-404 return without expression and implicit null
                {
                        "<?php function fooD(){ return;} ?>",
                        "namespace{\n\n    function mixed fooD() {\n        return null;\n    }\n\n}"
                },
                {
                        "<?php function fooE($x){ if($x){ return; } return 1;} ?>",
                        "namespace{\n"
                                + "\n"
                                + "    function int? fooE0(bool $x) {\n"
                                + "        if ($x) {\n"
                                + "            return null;\n"
                                + "        }\n"
                                + "        return 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int? fooE1({as bool} $x) {\n"
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
                                + "    function int? fooF0(bool $x) {\n"
                                + "        if ($x) {\n"
                                + "            return null;\n"
                                + "        } else {\n"
                                + "            return 1;\n"
                                + "        }\n"
                                + "    }\n"
                                + "\n"
                                + "    function int? fooF1({as bool} $x) {\n"
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
                                + "    function T fooH3<T>({as T} $x, {as T} $y) where [T <: num] {\n"
                                + "        T $a;\n"
                                + "        $a = cast<T>(oldSchoolAddition($x, $y));\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TODO TINS-579 as operator and compound assignments
                {
                        "<?php function fooI($x){ return $x /= false;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T fooI<T>(T $x) where [num! <: T <: {as num}] {\n"
                                + "        return $x as num /= false as num;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TODO TINS-579 as operator and compound assignments
                {
                        "<?php function fooJ($x, $y){ return $x /= $y;}",
                        "namespace{\n"
                                + "\n"
                                + "    function float! fooJ0(float! $x, float $y) {\n"
                                + "        return $x /= $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T fooJ1<T>(T $x, float $y) where [float! <: T <: {as num}] {\n"
                                + "        return $x as num /= $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T fooJ2<T>(T $x, {as num} $y) where [num! <: T <: {as num}] {\n"
                                + "        return $x as num /= $y as num;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-479 local vars with multiple lower ref to params
                {
                        "<?php function fooK($x, $y){ $a = $x / $y; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function float! fooK0(float $x, float $y) {\n"
                                + "        float! $a;\n"
                                + "        $a = $x / $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float! fooK1(float $x, {as num} $y) {\n"
                                + "        float! $a;\n"
                                + "        $a = $x / $y as num;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function num! fooK2(int $x, int $y) {\n"
                                + "        num! $a;\n"
                                + "        $a = $x / $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float! fooK3({as num} $x, float $y) {\n"
                                + "        float! $a;\n"
                                + "        $a = $x as num / $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function num! fooK4({as num} $x, {as num} $y) {\n"
                                + "        num! $a;\n"
                                + "        $a = $x as num / $y as num;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}" +
                                ""
                },
                //see TINS-479 local vars with multiple lower ref to params
                //see TINS-584 find least upper reference bound - initially the return type was (int | T)
                {
                        "<?php function fooM($x, $y){ $a = 1; $a = $x + $y; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed fooM0(array $x, array $y) {\n"
                                + "        mixed $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function num fooM1(float $x, float $y) {\n"
                                + "        num $a;\n"
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
                                + "    function T fooM3<T>({as T} $x, {as T} $y) where [int <: T <: num] {\n"
                                + "        T $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = cast<T>(oldSchoolAddition($x, $y));\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooN($x){echo $x; return $x && true; }",
                        "namespace{\n"
                                + "\n"
                                + "    function bool fooN0(({as bool} & {as string}) $x) {\n"
                                + "        echo $x as string;\n"
                                + "        return $x as bool && true;\n"
                                + "    }\n"
                                + "\n"
                                + "    function bool fooN1(string $x) {\n"
                                + "        echo $x;\n"
                                + "        return $x as bool && true;\n"
                                + "    }\n"
                                + "\n"
                                + "}" +
                                ""
                },
                //see TINS-584 find least upper reference bound - initially we had the return type (int | T)
                {
                        "<?php function fooO($x){if(true){return $x;} return 1;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T fooO<T>(T $x) where [int <: T] {\n"
                                + "        if (true) {\n"
                                + "            return $x;\n"
                                + "        }\n"
                                + "        return 1;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-584 find least upper reference bound - initially we had the return type (string | T)
                {
                        "<?php function fooP($x){if(true){return $x + 1;} return 'hello';} $a = fooP(2); $b = ~$a;",
                        "namespace{\n"
                                + "    scalar $b;\n"
                                + "    scalar $a;\n"
                                + "\n"
                                + "    function scalar fooP0(int $x) {\n"
                                + "        if (true) {\n"
                                + "            return $x + 1;\n"
                                + "        }\n"
                                + "        return 'hello';\n"
                                + "    }\n"
                                + "\n"
                                + "    function scalar fooP1({as num} $x) {\n"
                                + "        if (true) {\n"
                                + "            return oldSchoolAddition($x, 1);\n"
                                + "        }\n"
                                + "        return 'hello';\n"
                                + "    }\n"
                                + "\n"
                                + "    $a = fooP0(2);\n"
                                + "    $b = ~($a <: int ? cast<int>($a) : $a <: string ? cast<string>($a) : "
                                + "\\trigger_error('The variable $a must hold a value of type int or string.',"
                                + " \\E_USER_ERROR));\n"
                                + "}"
                },
                //see TINS-584 find least upper reference bound - initially we had the return type (float | int | T)
                {
                        "<?php function barQ($x){$x . 1; return $x;} "
                                + "function fooQ($x, $y){return 1; return $x + '1'; return barQ($y);}",
                        "namespace{\n"
                                + "\n"
                                + "    function T barQ<T>(T $x) where [T <: {as string}] {\n"
                                + "        $x as string . 1 as string;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T fooQ<T>({as num} $x, T $y) where [num <: T <: {as string}] {\n"
                                + "        return 1;\n"
                                + "        return oldSchoolAddition($x, '1');\n"
                                + "        return barQ($y);\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-584 find least upper reference bound - initially we had (int | T2) <: T1
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
                                + "where [T2 <: T1, int <: T2 <: num] {\n"
                                + "        $x = cast<T2>(oldSchoolAddition(1, $y));\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-584 find least upper reference bound - initially $a is of type (string | T)
                {
                        "<?php function fooS($x){$a = $x; $a = 'hello'; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T fooS<T>(T $x) {\n"
                                + "        mixed $a;\n"
                                + "        $a = $x;\n"
                                + "        $a = 'hello';\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-584 find least upper reference bound - initially we have (T2 | T3) <: T1
                {
                        "<?php function fooU($x, $y, $z){$x = $y; $x = $z; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T fooU<T>(T $x, T $y, T $z) {\n"
                                + "        $x = $y;\n"
                                + "        $x = $z;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-584 find least upper reference bound - initially we have (T2 | T3) <: T1, T1 <: T4
                {
                        "<?php function fooU2($x, $y, $z, $a){$a = $x; $x = $y; $x = $z; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T2 fooU2<T1, T2>(T1 $x, T1 $y, T1 $z, T2 $a) where [T1 <: T2] {\n"
                                + "        $a = $x;\n"
                                + "        $x = $y;\n"
                                + "        $x = $z;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-584 find least upper reference bound - initially we have (int | T2 | T3) <: T1
                {
                        "<?php function fooU3($x, $y, $z){$x = $y; $x = $z; $x = 1; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T fooU3<T>(T $x, T $y, T $z) where [int <: T] {\n"
                                + "        $x = $y;\n"
                                + "        $x = $z;\n"
                                + "        $x = 1;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-584 find least upper reference bound - initially we have (int | T2) <: T1
                {
                        "<?php function fooU4($x, $y){$x = $y; $y = 1; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T1 fooU4<T1, T2>(T1 $x, T2 $y) where [T2 <: T1, int <: T2] {\n"
                                + "        $x = $y;\n"
                                + "        $y = 1;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-584 find least upper reference bound - initially we have (int | string | T2) <: T1
                {
                        "<?php function fooU5($x, $y){$x = $y; $x = 1; $y = 'hello'; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T fooU5<T>(T $x, T $y) where [scalar <: T] {\n"
                                + "        $x = $y;\n"
                                + "        $x = 1;\n"
                                + "        $y = 'hello';\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-584 find least upper reference bound - initially $a is (int | T1 | T2)
                {
                        "<?php function fooV($x, $y){$a = $x; $a = $y; $a = 1; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T fooV<T>(T $x, T $y) where [int <: T] {\n"
                                + "        T $a;\n"
                                + "        $a = $x;\n"
                                + "        $a = $y;\n"
                                + "        $a = 1;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-584 find least upper reference bound - initially return was (int | T1 | T2)
                //moreover, $x is not fix but T1 <: float in the first overload and  T1 <: string in the second
                {
                        "<?php function fooV2($x, $y){$a = $x; ~$x; $a = $y; $a = 1; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T fooV20<T>(float $x, T $y) where [num <: T] {\n"
                                + "        T $a;\n"
                                + "        $a = $x;\n"
                                + "        ~$x;\n"
                                + "        $a = $y;\n"
                                + "        $a = 1;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T fooV21<T>(string $x, T $y) where [scalar <: T] {\n"
                                + "        T $a;\n"
                                + "        $a = $x;\n"
                                + "        ~$x;\n"
                                + "        $a = $y;\n"
                                + "        $a = 1;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T fooV22<T>(int $x, T $y) where [int <: T] {\n"
                                + "        T $a;\n"
                                + "        $a = $x;\n"
                                + "        ~$x;\n"
                                + "        $a = $y;\n"
                                + "        $a = 1;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TINS-553 isFinal, container types and upper type bounds
                {
                        "<?php function fac($x){return $x > 0 ? $x * fac($x) : $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T fac0<T>(T $x) where [num <: T <: {as num}] {\n"
                                + "        return ($x > 0) ? oldSchoolMultiplication($x, fac($x)) : $x;\n"
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
                //see TINS-641 type variable transformer and recursive functions
                {
                        "<?php function fooW($x){ return $x != '' ? $x . '1': $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T fooW0<T>(T $x) where [string <: T <: {as string}] {\n"
                                + "        return ($x != '') ? $x . '1' : $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function string fooW1(string $x) {\n"
                                + "        return ($x != '') ? $x . '1' : $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function fooX($x, $y){$x . 1; $y & 1.2; return $x; return $y;}\n"
                                + "function barX($x){ return fooX($x, $x);}"
                                + "$a = fooX(1, 2);\n $b = fooX(1.2, 2.3);\n "
                                + "$c = fooX('hello', 'hi');\n $d = fooX(1, 2.5);",
                        "namespace{\n"
                                + "    num $d;\n"
                                + "    string $c;\n"
                                + "    float $b;\n"
                                + "    int $a;\n"
                                + "\n"
                                + "    function mixed fooX(mixed $x, mixed $y) {\n"
                                + "        $x as string . 1 as string;\n"
                                + "        oldSchoolBitwiseAnd($y, 1.2);\n"
                                + "        return $x;\n"
                                + "        return $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T barX<T>(T $x) where [T <: mixed] {\n"
                                + "        return cast<T>(fooX($x, $x));\n"
                                + "    }\n"
                                + "\n"
                                + "    $a = cast<int>(fooX(1, 2));\n"
                                + "    $b = cast<float>(fooX(1.2, 2.3));\n"
                                + "    $c = cast<string>(fooX('hello', 'hi'));\n"
                                + "    $d = (($_t5_6 = (fooX(1, 2.5))) <: float ? cast<float>($_t5_6) : $_t5_6 <: int" +
                                " ? cast<int>($_t5_6) : \\trigger_error('The variable $_t5_6 must hold a value of " +
                                "type float or int.', \\E_USER_ERROR));\n"
                                + "}\n"
                                + "namespace{\n"
                                + "    mixed $_t5_6;\n"
                                + "}"
                },
        });
    }
}
