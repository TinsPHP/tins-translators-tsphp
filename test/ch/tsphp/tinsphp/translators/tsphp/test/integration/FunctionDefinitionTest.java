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
import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ParameterListHelper;
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
public class FunctionDefinitionTest extends ATranslatorInferenceTest
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
        List<Object[]> collection = new ArrayList<>();

        collection.addAll(ParameterListHelper.getTestStrings(
                "<?php function set", "{return 1;}?>",
                "namespace{\n\n    function int set", " {\n        return 1;\n    }\n\n}"));

        collection.addAll(Arrays.asList(new Object[][]{
                {
                        "<?php function foo($x){return $x;} ?>",
                        "namespace{\n\n    function T foo<T>(T $x) {\n        return $x;\n    }\n\n}"
                },
                {
                        "<?php function foo($x, $y){return $x + $y;} ?>",
                        "namespace{"
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
                                + "\n}"
                },
                {
                        "<?php function fooA($x, $y){return $x + $y + 1;} ?>",
                        "namespace{\n"
                                + "\n"
                                + "    function float fooA0(float $x, float $y) {\n"
                                + "        return $x + $y + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float fooA1(float $x, {as (float | int)} $y) {\n"
                                + "        return $x + $y + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int fooA2(int $x, int $y) {\n"
                                + "        return $x + $y + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float fooA3({as (float | int)} $x, float $y) {\n"
                                + "        return $x + $y + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int fooA4({as int} $x, {as int} $y) {\n"
                                + "        return $x + $y + 1;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //return but without expression
                // see TINS-404 return without expression and implicit null
                {
                        "<?php function foo(){ return;} ?>",
                        "namespace{\n\n    function nullType foo() {\n        return null;\n    }\n\n}"
                },
                {
                        "<?php function foo($x){ if($x){ return; } return 1;} ?>",
                        "namespace{\n"
                                + "\n"
                                + "    function (int | nullType) foo0((falseType | trueType) $x) {\n"
                                + "        if ($x) {\n"
                                + "            return null;\n"
                                + "        }\n"
                                + "        return 1;\n"
                                + "    }\n"
                                + "\n"
                                + "    function (int | nullType) foo1({as (falseType | trueType)} $x) {\n"
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
                                + "    function (int | nullType) foo0((falseType | trueType) $x) {\n"
                                + "        if ($x) {\n"
                                + "            return null;\n"
                                + "        } else {\n"
                                + "            return 1;\n"
                                + "        }\n"
                                + "    }\n"
                                + "\n"
                                + "    function (int | nullType) foo1({as (falseType | trueType)} $x) {\n"
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
                                + "    function T1 foo<T1, T2>(T1 $x, T2 $y) where [T2 < T1] {\n"
                                + "        $x = $y;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo($x, $y){ $x = 1+$y; return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T foo0<T>(T $x, float $y) where [float < T] {\n"
                                + "        $x = 1 + $y;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T foo1<T>(T $x, int $y) where [int < T] {\n"
                                + "        $x = 1 + $y;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T1 foo2<T1>(T1 $x, {as T2} $y) where [(int | T2) < T1] {\n"
                                + "        $x = 1 + $y;\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo($x, $y){ $a = $x+$y; return $a;}",
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
                                + "    function float foo2(float $x, {as (float | int)} $y) {\n"
                                + "        float $a;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int foo3(int $x, int $y) {\n"
                                + "        int $a;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float foo4({as (float | int)} $x, float $y) {\n"
                                + "        float $a;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T foo5<T>({as T} $x, {as T} $y) where [T < (float | int)] {\n"
                                + "        T $a;\n"
                                + "        $a = $x + $y;\n"
                                + "        return $a;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo($x){ return $x /= false;}",
                        "namespace{\n"
                                + "\n"
                                + "    function T foo<T>(T $x) where [(falseType | float | int) < T < {as (float | " +
                                "int)}] {\n"
                                + "        return $x /= false;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo($x, $y){ return $x /= $y;}",
                        "namespace{\n"
                                + "\n"
                                + "    function (falseType | float) foo0((falseType | float) $x, float $y) {\n"
                                + "        return $x /= $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T foo1<T>(T $x, float $y) where [(falseType | float) < T < {as (float" +
                                " | int)}] {\n"
                                + "        return $x /= $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T foo2<T>(T $x, {as (float | int)} $y) where [(falseType | float | " +
                                "int) < T < {as (float | int)}] {\n"
                                + "        return $x /= $y;\n"
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
                }
        }));

        return collection;
    }
}
