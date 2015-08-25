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
                                + "        return cast<T>(oldSchoolAddition(cast<T>(oldSchoolAddition($x, $y)), 1));\n"
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
                                + "        if ($x as bool) {\n"
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
                                + "        if ($x as bool) {\n"
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
                                + "        $a = oldSchoolFloatDivide($x, $y);\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function num! fooK2(int $x, int $y) {\n"
                                + "        num! $a;\n"
                                + "        $a = oldSchoolIntDivide($x, $y);\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float! fooK3({as num} $x, float $y) {\n"
                                + "        float! $a;\n"
                                + "        $a = oldSchoolFloatDivide($x, $y);\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function num! fooK4({as num} $x, {as num} $y) {\n"
                                + "        num! $a;\n"
                                + "        $a = oldSchoolDivide($x, $y);\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
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
                                //TODO TINS-666 soft typing erroneous for local/global variables
                                //should only be cast<int> ... cast<string>
                                + "    $b = ~($a <: float ? cast<float>($a) : "
                                + "$a <: int ? cast<int>($a) : $a <: string ? cast<string>($a) : "
                                + "\\trigger_error('The variable $a must hold a value of type float, int or string.', "
                                + "\\E_USER_ERROR));\n"
                                + "}" +
                                ""
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
                //see TINS-648 conversions in TSPHP miss post condition
                {
                        "<?php function foo($x){ $a = 1; $a ='hello'; if ($x){ $a = 1.2;} echo $a; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function scalar foo0(bool $x) {\n"
                                + "        scalar $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = 'hello';\n"
                                + "        if ($x) {\n"
                                + "            $a = 1.2;\n"
                                + "        }\n"
                                + "        echo $a as string if float, int;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function scalar foo1({as bool} $x) {\n"
                                + "        scalar $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = 'hello';\n"
                                + "        if ($x as bool) {\n"
                                + "            $a = 1.2;\n"
                                + "        }\n"
                                + "        echo $a as string if float, int;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-648 conversions in TSPHP miss post condition
                {
                        "<?php function foo($x){ $a = 1; $a ='hello'; if ($x){ $a = [1.2];} echo $a; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed foo0(bool $x) {\n"
                                + "        mixed $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = 'hello';\n"
                                + "        if ($x) {\n"
                                + "            $a = [1.2];\n"
                                + "        }\n"
                                + "        echo $a as string if array, int;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function mixed foo1({as bool} $x) {\n"
                                + "        mixed $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = 'hello';\n"
                                + "        if ($x as bool) {\n"
                                + "            $a = [1.2];\n"
                                + "        }\n"
                                + "        echo $a as string if array, int;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function identity($x){return $x;}"
                                + "function identityWithBound($x){ echo $x; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T identity<T>(T $x) {\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T identityWithBound0<T>(T $x) where [T <: string] {\n"
                                + "        echo $x;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T identityWithBound1<T>(T $x) where [T <: {as string}] {\n"
                                + "        echo $x as string;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TODO TINS-661 reduce overloads if parameters are not ad-hoc polymorphic
                {
                        "<?php function countValue(array $arr, $value) {\n"
                                + "    $count = 0;\n"
                                + "    for ($i = 0; $i < count($arr); ++$i) {\n"
                                + "        if ($arr[$i] == $value) {\n"
                                + "            ++$count;\n"
                                + "        }\n"
                                + "    }\n"
                                + "    return $count;\n"
                                + "}",
                        "namespace{\n"
                                + "\n"
                                + "    function scalar countValue0(array $arr_0, mixed $value) {\n"
                                + "        array $arr = $arr_0;\n"
                                + "        int $i;\n"
                                + "        scalar $count;\n"
                                + "        $count = 0;\n"
                                + "        for ($i = 0; $i < count($arr); ++$i) {\n"
                                + "            if ($arr[$i] == $value) {\n"
                                + "                ++$count;\n"
                                + "            }\n"
                                + "        }\n"
                                + "        return $count;\n"
                                + "    }\n"
                                + "\n"
                                + "    function num countValue1(array $arr_0, mixed $value) {\n"
                                + "        array $arr = $arr_0;\n"
                                + "        int $i;\n"
                                + "        num $count;\n"
                                + "        $count = 0;\n"
                                + "        for ($i = 0; $i < count($arr); ++$i) {\n"
                                + "            if ($arr[$i] == $value) {\n"
                                + "                ++$count;\n"
                                + "            }\n"
                                + "        }\n"
                                + "        return $count;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int? countValue2(array $arr_0, mixed $value) {\n"
                                + "        array $arr = $arr_0;\n"
                                + "        int $i;\n"
                                + "        int? $count;\n"
                                + "        $count = 0;\n"
                                + "        for ($i = 0; $i < count($arr); ++$i) {\n"
                                + "            if ($arr[$i] == $value) {\n"
                                + "                ++$count;\n"
                                + "            }\n"
                                + "        }\n"
                                + "        return $count;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int countValue3(array $arr_0, mixed $value) {\n"
                                + "        array $arr = $arr_0;\n"
                                + "        int $i;\n"
                                + "        int $count;\n"
                                + "        $count = 0;\n"
                                + "        for ($i = 0; $i < count($arr); ++$i) {\n"
                                + "            if ($arr[$i] == $value) {\n"
                                + "                ++$count;\n"
                                + "            }\n"
                                + "        }\n"
                                + "        return $count;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function isEven($x) {\n   return $x % 2 == 0;\n}",
                        "namespace{\n"
                                + "\n"
                                + "    function bool isEven0(mixed $x) {\n"
                                + "        return $x % 2 == 0;\n"
                                + "    }\n"
                                + "\n"
                                + "    function bool isEven1(int $x) {\n"
                                + "        return $x % 2 == 0;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function isOdd($x) {\n   return $x % 2 == 1;\n}",
                        "namespace{\n"
                                + "\n"
                                + "    function bool isOdd0(mixed $x) {\n"
                                + "        return $x % 2 == 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function bool isOdd1(int $x) {\n"
                                + "        return $x % 2 == 1;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function someLogic($x, $y, $z){\n"
                                + "    $a = $x || $y && !$z;\n"
                                + "    $b = !$x or $y xor $z and $a;\n"
                                + "    return $a == $b;\n"
                                + "}",
                        "namespace{\n"
                                + "\n"
                                + "    function bool someLogic0(bool $x, bool $y, bool $z) {\n"
                                + "        bool $b;\n"
                                + "        bool $a;\n"
                                + "        $a = $x || $y && !$z;\n"
                                + "        $b = !$x or $y xor $z and $a;\n"
                                + "        return $a == $b;\n"
                                + "    }\n"
                                + "\n"
                                + "    function bool someLogic1({as bool} $x, {as bool} $y, {as bool} $z) {\n"
                                + "        bool $b;\n"
                                + "        bool $a;\n"
                                + "        $a = $x as bool || $y as bool && !$z as bool;\n"
                                + "        $b = !$x as bool or $y as bool xor $z as bool and $a;\n"
                                + "        return $a == $b;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function orExit($x){\n    $x or exit(-1);\n    return null;\n}",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed orExit({as bool} $x) {\n"
                                + "        $x as bool or exit(-1) as bool;\n"
                                + "        return null;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function andExit($x){\n    $x and exit(0);\n    return null;\n}",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed andExit({as bool} $x) {\n"
                                + "        $x as bool and exit(0) as bool;\n"
                                + "        return null;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TODO TINS-664 - as operator with convertible type in post condition
                {
                        "<?php /**\n"
                                + " * copied from\n"
                                + " * http://webdeveloperplus.com/php/21-really-useful-handy-php-code-snippets/\n"
                                + " */\n"
                                + "function readable_random_string($length){\n"
                                + "     $conso=array('b','c','d','f','g','h','j','k','l',\n"
                                + "     'm','n','p','r','s','t','v','w','x','y','z');\n"
                                + "     $vocal=array('a','e','i','o','u');\n"
                                + "     $password='';\n"
                                + "     srand((double)microtime()*1000000);\n"
                                + "     $max = $length/2;\n"
                                + "\n"
                                + "     for($i=1; $i <= $max; $i++){\n"
                                + "        $password .= $conso[rand(0,19)];\n"
                                + "        $password .= $vocal[rand(0,4)];\n"
                                + "     }\n"
                                + "\n"
                                + "     return $password;\n"
                                + " }",
                        // e.g., the return type should be string, $password as well
                        "namespace{\n"
                                + "\n"
                                + "    function {as string} readable_random_string({as num} $length) {\n"
                                + "        scalar? $i;\n"
                                + "        num! $max;\n"
                                + "        {as string} $password;\n"
                                + "        array $vocal;\n"
                                + "        array $conso;\n"
                                + "        $conso = ['b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'r'," +
                                " 's', 't', 'v', 'w', 'x', 'y', 'z'];\n"
                                + "        $vocal = ['a', 'e', 'i', 'o', 'u'];\n"
                                + "        $password = '';\n"
                                + "        srand(oldSchoolCast<float>(microtime()) * 1000000);\n"
                                + "        $max = oldSchoolDivide($length, 2);\n"
                                + "        for ($i = 1; $i <= $max; $i++) {\n"
                                + "            $password as string .= oldSchoolArrayAccess($conso, rand(0, 19)) "
                                + "as string if {as string};\n"
                                + "            $password as string .= oldSchoolArrayAccess($vocal, rand(0, 4)) "
                                + "as string if {as string};\n"
                                + "        }\n"
                                + "        return $password;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TODO TINS-664 - as operator with convertible type in post condition
                {
                        "<?php /**\n"
                                + " * adapted from\n"
                                + " * http://webdeveloperplus.com/php/21-really-useful-handy-php-code-snippets/\n"
                                + " */\n"
                                + "function generate_rand($l){\n"
                                + "  $c= ['A','B','C','D','E','F','G','H','I','J','K', 'L','M',\n"
                                + "    'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',\n"
                                + "    'a','b','c','d','e','f','g','h','i','j','k','l','m',\n"
                                + "    'n','o','p','q','r','s','t','u','v','w','x','y','z',\n"
                                + "    '0','1','2','3','4','5','6','7','8','9'];\n"
                                + "  srand((double)microtime()*1000000);\n"
                                + "  $rand = '';\n"
                                + "  for($i=0; $i<$l; $i++) {\n"
                                + "      $rand.= $c[rand(0,1)%count($c)];\n"
                                + "  }\n"
                                + "  return $rand;\n"
                                + "}",
                        //TODO TINS-666 soft typing erroneous for local/global variables
                        // e.g., the return type should be string, $password as well
                        "namespace{\n"
                                + "\n"
                                + "    function {as string} generate_rand(mixed $l) {\n"
                                + "        scalar? $i;\n"
                                + "        {as string} $rand;\n"
                                + "        array $c;\n"
                                + "        $c = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', "
                                + "'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', "
                                + "'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', "
                                + "'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', "
                                + "'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];\n"
                                + "        srand(oldSchoolCast<float>(microtime()) * 1000000);\n"
                                + "        $rand = '';\n"
                                + "        for ($i = 0; $i < $l; $i++) {\n"
                                + "            $rand as string .= oldSchoolArrayAccess($c, rand(0, " +
                                "1) % count($c)) as string if {as string};\n"
                                + "        }\n"
                                + "        return $rand;\n"
                                + "    }\n"
                                + "\n"
                                + "}" +
                                ""
                },
                //see TINS-667 anonymous template is evaluated
                {
                        "<?php function encode_email($email, $linkText, $attrs) {\n"
                                + "    $email = str_replace('@', '&#64;', $email);\n"
                                + "    $email = str_replace('.', '&#46;', $email);\n"
                                + "    $email = str_split($email, 5);\n"
                                + "\n"
                                + "    $linkText = str_replace('@', '&#64;', $linkText);\n"
                                + "    $linkText = str_replace('.', '&#46;', $linkText);\n"
                                + "    $linkText = str_split($linkText, 5);\n"
                                + "\n"
                                + "    $part1 = '<a href=\"ma';\n"
                                + "    $part2 = 'ilto&#58;';\n"
                                + "    $part3 = '\" '. $attrs .' >';\n"
                                + "    $part4 = '</a>';\n"
                                + "\n"
                                + "    $encoded = '<script type=\"text/javascript\">';\n"
                                + "    $encoded .= 'document.write(\\''.$part1.'\\');';\n"
                                + "    $encoded .= 'document.write(\\''.$part2.'\\');';\n"
                                + "\n"
                                + "    foreach(str_split($email, 1) as $e)\n"
                                + "    {\n"
                                + "        $encoded .= 'document.write(\\''.$e.'\\');';\n"
                                + "    }\n"
                                + "\n"
                                + "    $encoded .= 'document.write(\\''.$part3.'\\');';\n"
                                + "    foreach(str_split($linkText, 1) as $l)\n"
                                + "    {\n"
                                + "        $encoded .= 'document.write(\\''.$l.'\\');';\n"
                                + "    }\n"
                                + "\n"
                                + "    $encoded .= 'document.write(\\''.$part4.'\\');';\n"
                                + "    $encoded .= '</script>';\n"
                                + "\n"
                                + "    return $encoded;\n"
                                + "}",
                        //TODO TINS-666 soft typing erroneous for local/global variables
                        // e.g., the return type should be string, $password as well
                        "namespace{\n"
                                + "\n"
                                + "    function {as string} encode_email({as string} $email, {as string} $linkText, " +
                                "{as string} $attrs) {\n"
                                + "        mixed $l;\n"
                                + "        mixed $e;\n"
                                + "        {as string} $encoded;\n"
                                + "        {as string} $part4;\n"
                                + "        {as string} $part3;\n"
                                + "        {as string} $part2;\n"
                                + "        {as string} $part1;\n"
                                + "        $email = str_replace('@', '&#64;', ($email <: array ? cast<array>($email) " +
                                ": $email <: string ? cast<string>($email) : \\trigger_error('The variable $email " +
                                "must hold a value of type array or string.', \\E_USER_ERROR)));\n"
                                + "        $email = str_replace('.', '&#46;', ($email <: array ? cast<array>($email) " +
                                ": $email <: string ? cast<string>($email) : \\trigger_error('The variable $email " +
                                "must hold a value of type array or string.', \\E_USER_ERROR)));\n"
                                + "        $email = str_split($email, 5);\n"
                                + "        $linkText = str_replace('@', '&#64;', ($linkText <: array ? cast<array>" +
                                "($linkText) : $linkText <: string ? cast<string>($linkText) : \\trigger_error('The " +
                                "variable $linkText must hold a value of type array or string.', \\E_USER_ERROR)));\n"
                                + "        $linkText = str_replace('.', '&#46;', ($linkText <: array ? cast<array>" +
                                "($linkText) : $linkText <: string ? cast<string>($linkText) : \\trigger_error('The " +
                                "variable $linkText must hold a value of type array or string.', \\E_USER_ERROR)));\n"
                                + "        $linkText = str_split($linkText, 5);\n"
                                + "        $part1 = '<a href=\"ma';\n"
                                + "        $part2 = 'ilto&#58;';\n"
                                + "        $part3 = '\" ' . $attrs . ' >';\n"
                                + "        $part4 = '</a>';\n"
                                + "        $encoded = '<script type=\"text/javascript\">';\n"
                                + "        $encoded .= 'document.write(\\'' . $part1 . '\\');';\n"
                                + "        $encoded .= 'document.write(\\'' . $part2 . '\\');';\n"
                                + "        foreach (cast<array>(str_split($email, 1)) as mixed $e19_36) {\n"
                                + "            $e = $e19_36;\n"
                                + "            $encoded .= 'document.write(\\'' . $e as string . '\\');';\n"
                                + "        }\n"
                                + "        $encoded .= 'document.write(\\'' . $part3 . '\\');';\n"
                                + "        foreach (cast<array>(str_split($linkText, 1)) as mixed $l25_39) {\n"
                                + "            $l = $l25_39;\n"
                                + "            $encoded .= 'document.write(\\'' . $l as string . '\\');';\n"
                                + "        }\n"
                                + "        $encoded .= 'document.write(\\'' . $part4 . '\\');';\n"
                                + "        $encoded .= '</script>';\n"
                                + "        return $encoded;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function myTruncate($string, $limit, $break=\".\", $pad=\"...\") {\n"
                                + "    // return with no change if string is shorter than $limit\n"
                                + "    if(strlen($string) <= $limit)\n"
                                + "        return $string;\n"
                                + "\n"
                                + "    // is $break present between $limit and the end of the string?\n"
                                + "    if(false !== ($breakpoint = strpos($string, $break, $limit))) {\n"
                                + "        if($breakpoint < strlen($string) - 1) {\n"
                                + "            $string = substr($string, 0, $breakpoint) . $pad;\n"
                                + "        }\n"
                                + "    }\n"
                                + "    return $string;\n"
                                + "}",
                        "namespace{\n"
                                + "\n"
                                + "    function string myTruncate(string $string, mixed $limit, string $break, " +
                                "{as string} $pad) {\n"
                                + "        int! $breakpoint;\n"
                                + "        if (strlen($string) <= $limit) {\n"
                                + "            return $string;\n"
                                + "        }\n"
                                + "        if (false !== ($breakpoint = strpos($string, $break, $limit))) {\n"
                                + "            if ($breakpoint < "
                                + "cast<int>(oldSchoolSubtraction(strlen($string), 1))) {\n"
                                + "                $string = substr($string, 0, $breakpoint) as string "
                                + ". $pad as string;\n"
                                + "            }\n"
                                + "        }\n"
                                + "        return $string;\n"
                                + "    }\n"
                                + "\n"
                                + "}" +
                                ""
                },
                {
                        "<?php function bitwise1($x, $y, $z){\n    return $x & $y | $z;\n}",
                        "namespace{\n"
                                + "\n"
                                + "    function int bitwise10(mixed $x, mixed $y, mixed $z) {\n"
                                + "        return oldSchoolBitwiseOr(oldSchoolBitwiseAnd($x, $y), $z);\n"
                                + "    }\n"
                                + "\n"
                                + "    function int bitwise11(int $x, int $y, int $z) {\n"
                                + "        return $x & $y | $z;\n"
                                + "    }\n"
                                + "\n"
                                + "    function string bitwise12(string $x, string $y, string $z) {\n"
                                + "        return $x & $y | $z;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function bitwise2($x, $y, $z){\n    return $x << 2 & ($y >> 1) ^ $z;\n}",
                        "namespace{\n"
                                + "\n"
                                + "    function int bitwise20(mixed $x, mixed $y, mixed $z) {\n"
                                + "        return oldSchoolBitwiseXor(oldSchoolBitwiseAnd(oldSchoolShiftLeft($x, 2), " +
                                "oldSchoolShiftRight($y, 1)), $z);\n"
                                + "    }\n"
                                + "\n"
                                + "    function int bitwise21(int $x, int $y, int $z) {\n"
                                + "        return $x << 2 & $y >> 1 ^ $z;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TODO TINS-668 AmbiguousOverloadException
                {
                        "<?php function arithmetic1($x, $y, $z){\n    return ($x + $y) * $z / 2;\n}",
                        "namespace{\n"
                                + "\n"
                                + "    function float! arithmetic10(float $x, float $y, float $z) {\n"
                                + "        return ($x + $y) * $z / 2;\n"
                                + "    }\n"
                                + "\n"
                                + "    function num! arithmetic11(int $x, int $y, int $z) {\n"
                                + "        return oldSchoolIntDivide(($x + $y) * $z, 2);\n"
                                + "    }\n"
                                + "\n"
                                + "    function float! arithmetic12({as float} $x, {as float} $y, {as float} $z) {\n"
                                + "        return cast<float>(oldSchoolMultiplication("
                                + "cast<float>(oldSchoolAddition($x, $y)), $z)) / 2;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function arithmetic2($x, $y, $z){\n    return abs(+($x / -$y) % $z);\n}",
                        "namespace{\n"
                                + "\n"
                                + "    function int arithmetic20(float $x, float $y, mixed $z) {\n"
                                + "        return abs(+oldSchoolFloatDivide($x, -$y) % $z);\n"
                                + "    }\n"
                                + "\n"
                                + "    function int arithmetic21(float $x, float $y, int $z) {\n"
                                + "        return abs(+($x / -$y) % $z);\n"
                                + "    }\n"
                                + "\n"
                                + "    function int arithmetic22(int $x, scalar? $y, int $z) {\n"
                                + "        if (!($y <: bool) && !($y <: float) && !($y <: int) && !($y === null) && !" +
                                "($y <: string)) {\n"
                                + "            \\trigger_error('Argument 2 passed to arithmetic22() (parameter $y) " +
                                "must be a value of type bool, float, int, nullType or string.', \\E_USER_ERROR);\n"
                                + "        }\n"
                                + "        return abs(+oldSchoolIntDivide($x, -$y) % $z);\n"
                                + "    }\n"
                                + "\n"
                                + "    function int arithmetic23({as num} $x, float $y, mixed $z) {\n"
                                + "        return abs(+oldSchoolFloatDivide($x, -$y) % $z);\n"
                                + "    }\n"
                                + "\n"
                                + "}" +
                                ""
                },
                {
                        "<?php function eo_php2xdate($phpformat){\n"
                                + "  $php2xdate = array(\n"
                                + "    'Y'=>'yyyy','y'=>'yy','L'=>''/*Not Supported*/,'o'=>'I',\n"
                                + "    'j'=>'d','d'=>'dd','D'=>'ddd','l'=>'dddd','N'=>'', /*NS*/ 'S'=>'S',\n"
                                + "    'w'=>'', /*NS*/ 'z'=>'',/*NS*/ 'W'=>'w',\n"
                                + "    'F'=>'MMMM','m'=>'MM','M'=>'MMM','n'=>'M','t'=>'',/*NS*/\n"
                                + "    'a'=>'tt','A'=>'TT',\n"
                                + "    'B'=>'',/*NS*/'g'=>'h','G'=>'H','h'=>'hh','H'=>'HH','u'=>'fff',\n"
                                + "    'i'=>'mm','s'=>'ss',\n"
                                + "    'O'=>'zz ', 'P'=>'zzz',\n"
                                + "    'c'=>'u'\n"
                                + "  );\n"
                                + "\n"
                                + "  $xdateformat=\"\";\n"
                                + "  for($i=0;  $i< strlen($phpformat); $i++){\n"
                                + "    //Handle backslash excape\n"
                                + "    if($phpformat[$i]==\"\\\\\"){\n"
                                + "      $xdateformat .= \"'\".$phpformat[$i+1].\"'\";\n"
                                + "      $i++;\n"
                                + "      continue;\n"
                                + "    }\n"
                                + "    if(isset($php2xdate[$phpformat[$i]])){\n"
                                + "      $xdateformat .= $php2xdate[$phpformat[$i]];\n"
                                + "    }else{\n"
                                + "      $xdateformat .= $phpformat[$i];\n"
                                + "    }\n"
                                + "  }\n"
                                + "  return $xdateformat;\n"
                                + "}",
                        "namespace{\n"
                                + "\n"
                                + "    function {as string} eo_php2xdate(mixed $phpformat) {\n"
                                + "        if (!($phpformat <: array) && !($phpformat <: string)) {\n"
                                + "            \\trigger_error('Argument 1 passed to eo_php2xdate() (parameter " +
                                "$phpformat) must be a value of type array or string.', \\E_USER_ERROR);\n"
                                + "        }\n"
                                + "        mixed $i;\n"
                                + "        {as string} $xdateformat;\n"
                                + "        array $php2xdate;\n"
                                + "        $php2xdate = ['Y' => 'yyyy', 'y' => 'yy', 'L' => '', 'o' => 'I', " +
                                "'j' => 'd', 'd' => 'dd', 'D' => 'ddd', 'l' => 'dddd', 'N' => '', 'S' => 'S', " +
                                "'w' => '', 'z' => '', 'W' => 'w', 'F' => 'MMMM', 'm' => 'MM', 'M' => 'MMM', " +
                                "'n' => 'M', 't' => '', 'a' => 'tt', 'A' => 'TT', 'B' => '', 'g' => 'h', 'G' => 'H', " +
                                "'h' => 'hh', 'H' => 'HH', 'u' => 'fff', 'i' => 'mm', 's' => 'ss', 'O' => 'zz ', " +
                                "'P' => 'zzz', 'c' => 'u'];\n"
                                + "        $xdateformat = \"\";\n"
                                + "        for ($i = 0; $i < strlen(cast<string>($phpformat)); $i++) {\n"
                                + "            if (oldSchoolArrayAccess($phpformat, $i) == \"\\\\\") {\n"
                                + "                $xdateformat .= \"'\" . oldSchoolArrayAccess($phpformat, " +
                                "oldSchoolAddition($i, 1)) as string . \"'\";\n"
                                + "                $i++;\n"
                                + "                continue;\n"
                                + "            }\n"
                                + "            if (isset(oldSchoolArrayAccess($php2xdate, " +
                                "oldSchoolArrayAccess($phpformat, $i)))) {\n"
                                + "                $xdateformat .= oldSchoolArrayAccess($php2xdate, " +
                                "oldSchoolArrayAccess($phpformat, $i)) as string;\n"
                                + "            } else {\n"
                                + "                $xdateformat .= oldSchoolArrayAccess($phpformat, $i) as string;\n"
                                + "            }\n"
                                + "        }\n"
                                + "        return $xdateformat;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php /**\n"
                                + " * adapted from https://gist.github.com/ev3rywh3re/4573482\n"
                                + " */\n"
                                + "function fnbx_html_tag( $html ) {\n"
                                + "\n"
                                + "    if ( empty( $html ) ) return;\n"
                                + "\n"
                                + "    $attributes = '';\n"
                                + "    $composite = '';\n"
                                + "    $spacer = '';\n"
                                + "    if ( !isset( $html['return'] ) ) $html['return'] = false;\n"
                                + "    $reserved = array(\n"
                                + "        'tag', 'tag_type', 'attributes', 'tag_content', 'tag_content_before', " +
                                "'tag_content_after', 'return'\n"
                                + "    );\n"
                                + "\n"
                                + "    foreach ( $html as $name => $option ) {\n"
                                + "        if ( in_array( $name, $reserved ) ) continue;\n"
                                + "        $attributes .= $name . '=\"' . $option . '\" ';\n"
                                + "    }\n"
                                + "\n"
                                + "    if ( isset( $html['attributes'] ) ) $attributes .= $html['attributes'] . ' ' ." +
                                " $attributes;\n"
                                + "\n"
                                + "    if ( $attributes != '' ) {\n"
                                + "        $attributes = rtrim( $attributes );\n"
                                + "        $spacer = ' ';\n"
                                + "    }\n"
                                + "\n"
                                + "    if ( !isset( $html['tag_type'] ) ) $html['tag_type'] = 'default';\n"
                                + "\n"
                                + "    if ( isset( $html['tag_content_before'] ) ) $composite .= " +
                                "$html['tag_content_before'];\n"
                                + "\n"
                                + "    $tmp = $html['tag_type'];\n"
                                + "    if($tmp  == 'single' ) {\n"
                                + "        if ( isset( $html['tag_content'] ) ) $composite .= $html['tag_content'];\n"
                                + "        if ( isset( $html['tag'] ) ) $composite .= '<' . $html['tag'] . $spacer . " +
                                "$attributes . '/>';\n"
                                + "    } else if($tmp == 'open'){\n"
                                + "        if ( isset( $html['tag'] ) ) $composite .= '<' . $html['tag'] . $spacer . " +
                                "$attributes . '>';\n"
                                + "        if ( isset( $html['tag_content'] ) ) $composite .= $html['tag_content'];\n"
                                + "\n"
                                + "    } else if($tmp == 'close'){\n"
                                + "        if ( isset( $html['tag_content'] ) ) $composite .= $html['tag_content'];\n"
                                + "        if ( isset( $html['tag'] ) ) $composite .= '</' . $html['tag'] . '>';\n"
                                + "    } else if($tmp ==  'attributes'){\n"
                                + "        $composite = $attributes;\n"
                                + "    } else {\n"
                                + "        if ( isset( $html['tag'] ) ) $composite .= '<' . $html['tag'] . $spacer . " +
                                "$attributes . '>';\n"
                                + "        if ( isset( $html['tag_content'] ) ) $composite .= $html['tag_content'];\n"
                                + "        if ( isset( $html['tag'] ) ) $composite .= '</' . $html['tag'] . '>';\n"
                                + "    }\n"
                                + "\n"
                                + "    if ( isset( $html['tag_content_after'] ) ) $composite .= " +
                                "$html['tag_content_after'];\n"
                                + "\n"
                                + "    if ( $html['return'] == true ) return $composite ;\n"
                                + "\n"
                                + "    echo $composite;\n"
                                + "    return null;"
                                + "}",
                        "namespace{\n"
                                + "\n"
                                + "    function {as string} fnbx_html_tag(array $html) {\n"
                                + "        mixed $tmp;\n"
                                + "        {as string} $name;\n"
                                + "        mixed $option;\n"
                                + "        array $reserved;\n"
                                + "        {as string} $spacer;\n"
                                + "        {as string} $composite;\n"
                                + "        {as string} $attributes;\n"
                                + "        if (empty($html)) {\n"
                                + "            return null;\n"
                                + "        }\n"
                                + "        $attributes = '';\n"
                                + "        $composite = '';\n"
                                + "        $spacer = '';\n"
                                + "        if (!isset($html['return'])) {\n"
                                + "            $html['return'] = false;\n"
                                + "        }\n"
                                + "        $reserved = ['tag', 'tag_type', 'attributes', 'tag_content', " +
                                "'tag_content_before', 'tag_content_after', 'return'];\n"
                                + "        foreach ($html as scalar $name16_23 => mixed $option16_32) {\n"
                                + "            $name = $name16_23;\n"
                                + "            $option = $option16_32;\n"
                                + "            if (in_array($name, $reserved)) {\n"
                                + "                continue;\n"
                                + "            }\n"
                                + "            $attributes .= $name . '=\"' . $option as string . '\" ';\n"
                                + "        }\n"
                                + "        if (isset($html['attributes'])) {\n"
                                + "            $attributes .= $html['attributes'] as string . ' ' . $attributes;\n"
                                + "        }\n"
                                + "        if ($attributes != '') {\n"
                                + "            $attributes = rtrim(cast<string>($attributes));\n"
                                + "            $spacer = ' ';\n"
                                + "        }\n"
                                + "        if (!isset($html['tag_type'])) {\n"
                                + "            $html['tag_type'] = 'default';\n"
                                + "        }\n"
                                + "        if (isset($html['tag_content_before'])) {\n"
                                + "            $composite .= $html['tag_content_before'] as string;\n"
                                + "        }\n"
                                + "        $tmp = $html['tag_type'];\n"
                                + "        if ($tmp == 'single') {\n"
                                + "            if (isset($html['tag_content'])) {\n"
                                + "                $composite .= $html['tag_content'] as string;\n"
                                + "            }\n"
                                + "            if (isset($html['tag'])) {\n"
                                + "                $composite .= '<' . $html['tag'] as string . $spacer . $attributes" +
                                " . '/>';\n"
                                + "            }\n"
                                + "        } else {\n"
                                + "            if ($tmp == 'open') {\n"
                                + "                if (isset($html['tag'])) {\n"
                                + "                    $composite .= '<' . $html['tag'] as string . $spacer . " +
                                "$attributes . '>';\n"
                                + "                }\n"
                                + "                if (isset($html['tag_content'])) {\n"
                                + "                    $composite .= $html['tag_content'] as string;\n"
                                + "                }\n"
                                + "            } else {\n"
                                + "                if ($tmp == 'close') {\n"
                                + "                    if (isset($html['tag_content'])) {\n"
                                + "                        $composite .= $html['tag_content'] as string;\n"
                                + "                    }\n"
                                + "                    if (isset($html['tag'])) {\n"
                                + "                        $composite .= '</' . $html['tag'] as string . '>';\n"
                                + "                    }\n"
                                + "                } else {\n"
                                + "                    if ($tmp == 'attributes') {\n"
                                + "                        $composite = $attributes;\n"
                                + "                    } else {\n"
                                + "                        if (isset($html['tag'])) {\n"
                                + "                            $composite .= '<' . $html['tag'] as string . $spacer ." +
                                " $attributes . '>';\n"
                                + "                        }\n"
                                + "                        if (isset($html['tag_content'])) {\n"
                                + "                            $composite .= $html['tag_content'] as string;\n"
                                + "                        }\n"
                                + "                        if (isset($html['tag'])) {\n"
                                + "                            $composite .= '</' . $html['tag'] as string . '>';\n"
                                + "                        }\n"
                                + "                    }\n"
                                + "                }\n"
                                + "            }\n"
                                + "        }\n"
                                + "        if (isset($html['tag_content_after'])) {\n"
                                + "            $composite .= $html['tag_content_after'] as string;\n"
                                + "        }\n"
                                + "        if ($html['return'] == true) {\n"
                                + "            return $composite;\n"
                                + "        }\n"
                                + "        echo $composite;\n"
                                + "        return null;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function array1($x, $y){\n    $x[0] = $y;\n    return $x;\n}",
                        "namespace{\n"
                                + "\n"
                                + "    function T array1<T>(T $x, mixed $y) where [T <: array] {\n"
                                + "        $x[0] = $y;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function array2(array $x, $y){\n    $x + $y;\n    return $x[3];\n}",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed array2(array $x_0, array $y) {\n"
                                + "        array $x = $x_0;\n"
                                + "        $x + $y;\n"
                                + "        return $x[3];\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                }
        });
    }
}
