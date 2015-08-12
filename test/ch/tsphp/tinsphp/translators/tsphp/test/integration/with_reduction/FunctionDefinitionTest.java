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

package ch.tsphp.tinsphp.translators.tsphp.test.integration.with_reduction;


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
                        "<?php function foo($x){return $x;} ?>",
                        "namespace{\n\n    function T foo<T>(T $x) {\n        return $x;\n    }\n\n}"
                },
                {
                        "<?php function foo($x, $y){return $x + $y;} ?>",
                        "namespace{\n"
                                + "\n"
                                + "    function array foo0(array $x, array $y) {\n"
                                + "        return $x + $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float foo1(float $x, float $y) {\n"
                                + "        return $x + $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int foo2(int $x, int $y) {\n"
                                + "        return $x + $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T foo3<T>({as T} $x, {as T} $y) where [T <: num] {\n"
                                + "        return cast(oldSchoolAddition($x, $y), T);\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TODO TINS-584 find least upper reference bound
                {
                        "<?php function fooA($x, $y){return $x + $y + 1;} ?>",
                        "namespace{\n"
                                + "\n"
                                + "    function float fooA0(float $x, float $y) {\n"
                                + "        return $x + $y + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int fooA1(int $x, int $y) {\n"
                                + "        return $x + $y + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (int | T) fooA2<T>({as T} $x, {as T} $y) where [T <: num] {\n"
                                + "        return cast(oldSchoolAddition(cast(oldSchoolAddition($x, $y), T), 1), V4);\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //return but without expression
                // see TINS-404 return without expression and implicit null
                {
                        "<?php function foo(){ return;} ?>",
                        "namespace{\n\n    function mixed foo() {\n        return null;\n    }\n\n}"
                },
                {
                        "<?php function foo($x){ if($x){ return; } return 1;} ?>",
                        "namespace{\n"
                                + "\n"
                                + "    function int? foo0(bool $x) {\n"
                                + "        if ($x) {\n"
                                + "            return null;\n"
                                + "        }\n"
                                + "        return 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int? foo1({as bool} $x) {\n"
                                + "        if ($x) {\n"
                                + "            return null;\n"
                                + "        }\n"
                                + "        return 1;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo($x){ if($x){ return; } else { return 1;}} ?>",
                        "namespace{\n"
                                + "\n"
                                + "    function int? foo0(bool $x) {\n"
                                + "        if ($x) {\n"
                                + "            return null;\n"
                                + "        } else {\n"
                                + "            return 1;\n"
                                + "        }\n"
                                + "    }\n"
                                + "\n"
                                + "    function int? foo1({as bool} $x) {\n"
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
                        "<?php function foo($x, $y){ $x = $y; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T1 foo<T1, T2>(T1 $x, T2 $y) where [T2 <: T1] {\n"
                                + "        $x = $y;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo($x, $y){ $x = 1 + $y; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T foo0<T>(T $x, int $y) where [int <: T] {\n"
                                + "        $x = 1 + $y;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T1 foo1<T1, T2>(T1 $x, {as T2} $y) "
                                + "where [(int | T2) <: T1, int <: T2 <: num] {\n"
                                + "        $x = cast(oldSchoolAddition(1, $y), T2);\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo($x, $y){ $a = $x + $y; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function array foo0(array $x, array $y) {\n"
                                + "        array $a;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float foo1(float $x, float $y) {\n"
                                + "        float $a;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int foo2(int $x, int $y) {\n"
                                + "        int $a;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T foo3<T>({as T} $x, {as T} $y) where [T <: num] {\n"
                                + "        T $a;\n"
                                + "        $a = cast(oldSchoolAddition($x, $y), T);\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TODO TINS-579 as operator and compound assignments
                {
                        "<?php function foo($x){ return $x /= false;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T foo<T>(T $x) where [num! <: T <: {as num}] {\n"
                                + "        return $x as num /= false as num;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TODO TINS-579 as operator and compound assignments
                {
                        "<?php function foo($x, $y){ return $x /= $y;}",
                        "namespace{\n"
                                + "\n"
                                + "    function float! foo0(float! $x, float $y) {\n"
                                + "        return $x /= $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T foo1<T>(T $x, float $y) where [float! <: T <: {as num}] {\n"
                                + "        return $x as num /= $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T foo2<T>(T $x, {as num} $y) where [num! <: T <: {as num}] {\n"
                                + "        return $x as num /= $y as num;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TINS-479 local vars with multiple lower ref to params
                {
                        "<?php function foo($x, $y){ $a = $x / $y; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function float! foo0(float $x, float $y) {\n"
                                + "        float! $a;\n"
                                + "        $a = $x / $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float! foo1(float $x, {as num} $y) {\n"
                                + "        float! $a;\n"
                                + "        $a = $x / $y as num;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function num! foo2(int $x, int $y) {\n"
                                + "        num! $a;\n"
                                + "        $a = $x / $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float! foo3({as num} $x, float $y) {\n"
                                + "        float! $a;\n"
                                + "        $a = $x as num / $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function num! foo4({as num} $x, {as num} $y) {\n"
                                + "        num! $a;\n"
                                + "        $a = $x as num / $y as num;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}" +
                                ""
                },
                //TODO TINS-584 find least upper reference bound
                //TINS-479 local vars with multiple lower ref to params
                {
                        "<?php function fooB($x, $y){ $a = 1; $a = $x + $y; return $a;}",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed fooB0(array $x, array $y) {\n"
                                + "        mixed $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function num fooB1(float $x, float $y) {\n"
                                + "        num $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int fooB2(int $x, int $y) {\n"
                                + "        int $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (int | T) fooB3<T>({as T} $x, {as T} $y) where [T <: num] {\n"
                                + "        (int | T) $a;\n"
                                + "        $a = 1;\n"
                                + "        $a = cast(oldSchoolAddition($x, $y), T);\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TODO TINS-413 remove falseType and trueType overloads
                {
                        "<?php function fooC($x){echo $x; return $x && true; }",
                        "namespace{\n"
                                + "\n"
                                + "    function bool fooC0(({as bool} & {as string}) $x) {\n"
                                + "        echo $x as string;\n"
                                + "        return $x as bool && true;\n"
                                + "    }\n"
                                + "\n"
                                + "    function bool fooC1(bool $x) {\n"
                                + "        if (!($x === false)) {\n"
                                + "            \\trigger_error('Argument 1 passed to fooC1() (parameter $x) must be a" +
                                " value of type falseType.', \\E_USER_ERROR);\n"
                                + "        }\n"
                                + "        echo $x as string;\n"
                                + "        return $x && true;\n"
                                + "    }\n"
                                + "\n"
                                + "    function bool fooC2(string $x) {\n"
                                + "        echo $x;\n"
                                + "        return $x as bool && true;\n"
                                + "    }\n"
                                + "\n"
                                + "}" +
                                ""
                },
        });
    }
}
