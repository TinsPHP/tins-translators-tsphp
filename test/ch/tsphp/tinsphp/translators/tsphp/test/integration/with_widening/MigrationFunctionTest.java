/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright; license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file ExpressionTest from the translator component of the TSPHP project.
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
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class MigrationFunctionTest extends ATranslatorWithWideningTest
{

    public MigrationFunctionTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        Collection<Object[]> collection = new ArrayDeque<>();

        collection.addAll(getLogicOperator("or", true));
        collection.addAll(getLogicOperator("xor", true));
        collection.addAll(getLogicOperator("and", true));
        collection.addAll(getLogicOperator("||", false));
        collection.addAll(getLogicOperator("&&", false));

        collection.addAll(getArithmeticVariations("+", "oldSchoolAddition"));
        collection.addAll(getArithmeticVariations("-", "oldSchoolSubtraction"));
        collection.addAll(getArithmeticVariations("*", "oldSchoolMultiplication"));

        collection.addAll(getIntVariations("|", "oldSchoolBitwiseOr"));
        collection.addAll(getIntVariations("^", "oldSchoolBitwiseXor"));
        collection.addAll(getIntVariations("&", "oldSchoolBitwiseAnd"));
        collection.addAll(getIntVariations("<<", "oldSchoolShiftLeft"));
        collection.addAll(getIntVariations(">>", "oldSchoolShiftRight"));

        collection.addAll(Arrays.asList(new String[][]{
                {"<?php echo 1;", "namespace{\n    echo 1 as string;\n}"},
                {"<?php echo true;", "namespace{\n    echo true as string;\n}"},
                {
                        "<?php function foo6(Exception $x){ $x = 1; $x = 1.5; echo $x; return 1;}",
                        "namespace{\n"
                                + "\n"
                                + "    function int foo6(Exception $x_0) {\n"
                                + "        mixed $x = $x_0;\n"
                                + "        $x = 1;\n"
                                + "        $x = 1.5;\n"
                                + "        echo $x as string if float, int;\n"
                                + "        return 1;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {"<?php $a = (bool) 1;", "namespace{\n    bool $a;\n    $a = oldSchoolCast<bool>(1);\n}"},
                {"<?php $a = (int) 'hello';", "namespace{\n    int $a;\n    $a = oldSchoolCast<int>('hello');\n}"},
                {"<?php $a = (int) 1;", "namespace{\n    int $a;\n    $a = oldSchoolCast<int>(1);\n}"},
                {"<?php $a = (float) 1;", "namespace{\n    float $a;\n    $a = oldSchoolCast<float>(1);\n}"},
                {"<?php $a = (string) 1;", "namespace{\n    string $a;\n    $a = oldSchoolCast<string>(1);\n}"},
                {"<?php $a = (array) 1;", "namespace{\n    array $a;\n    $a = oldSchoolCast<array>(1);\n}"},
        }));

        return collection;
    }

    private static Collection<? extends Object[]> getIntVariations(String op, String oldSchool) {
        return Arrays.asList(new String[][]{
                {"<?php $a = 1 " + op + " 1.2;", "namespace{\n    int $a;\n    $a = " + oldSchool + "(1, 1.2);\n}"},
                {"<?php $a = 1.5 " + op + " 1;", "namespace{\n    int $a;\n    $a = " + oldSchool + "(1.5, 1);\n}"},
                {
                        "<?php $a = true " + op + " false;",
                        "namespace{\n    int $a;\n    $a = " + oldSchool + "(true, false);\n}"
                },
                {
                        "<?php $a = false " + op + " 1.6;",
                        "namespace{\n    int $a;\n    $a = " + oldSchool + "(false, 1.6);\n}"
                },
        });
    }

    private static List<String[]> getLogicOperator(String op, boolean requiresParentheses) {
        return Arrays.asList(new String[][]{
                {
                        "<?php $x = true; $x = false; $a = (1 " + op + " $x);",
                        "namespace{"
                                + "\n    bool $a;"
                                + "\n    bool $x;"
                                + "\n    $x = true;"
                                + "\n    $x = false;"
                                + "\n    $a = " + (requiresParentheses ? "(" : "")
                                + "1 as bool " + op + " $x" + (requiresParentheses ? ")" : "") + ";"
                                + "\n}"
                },
                {
                        "<?php $x = true; $x = false; $a = ($x " + op + " 1.2);",
                        "namespace{"
                                + "\n    bool $a;"
                                + "\n    bool $x;"
                                + "\n    $x = true;"
                                + "\n    $x = false;"
                                + "\n    $a = " + (requiresParentheses ? "(" : "")
                                + "$x " + op + " 1.2 as bool" + (requiresParentheses ? ")" : "") + ";"
                                + "\n}"
                },
                {
                        "<?php $a = (1 " + op + " 'a');",
                        "namespace{"
                                + "\n    bool $a;"
                                + "\n    $a = " + (requiresParentheses ? "(" : "")
                                + "1 as bool " + op + " 'a' as bool" + (requiresParentheses ? ")" : "") + ";"
                                + "\n}"
                },
                {
                        "<?php $x = true; $x = false; $a = ($x " + op + " $x);",
                        "namespace{"
                                + "\n    bool $a;"
                                + "\n    bool $x;"
                                + "\n    $x = true;"
                                + "\n    $x = false;"
                                + "\n    $a = " + (requiresParentheses ? "(" : "")
                                + "$x " + op + " $x" + (requiresParentheses ? ")" : "") + ";"
                                + "\n}"
                },
        });
    }

    private static Collection<? extends Object[]> getArithmeticVariations(
            String op, String oldSchool) {
        return Arrays.asList(new String[][]{
                {
                        "<?php $a = 'a' " + op + " 1.2;",
                        "namespace{\n    float $a;\n    $a = cast<float>(" + oldSchool + "('a', 1.2));\n}"
                },
                {
                        "<?php $a = 1.2 " + op + " true;",
                        "namespace{\n    float $a;\n    $a = cast<float>(" + oldSchool + "(1.2, true));\n}"
                },
                {
                        "<?php $a = 'a' " + op + " 1;",
                        "namespace{\n    num $a;\n    $a = " + oldSchool + "('a', 1);\n}"
                },
                {
                        "<?php $a = 'a' " + op + " true;",
                        "namespace{\n    num $a;\n    $a = " + oldSchool + "('a', true);\n}"
                },
                {
                        "<?php $a = '1' " + op + " '2';",
                        "namespace{\n    num $a;\n    $a = " + oldSchool + "('1', '2');\n}"
                },
                {
                        "<?php $a = true " + op + " false;",
                        "namespace{\n    int $a;\n    $a = cast<int>(" + oldSchool + "(true, false));\n}"
                },
                //will use the overload float x {as num} but since arguments are float x int it should use +
                {"<?php $a = 1.2 " + op + " 1;", "namespace{\n    float $a;\n    $a = 1.2 " + op + " 1;\n}"},
                //same for {as num} x float
                {"<?php $a = 1 " + op + " 1.5;", "namespace{\n    float $a;\n    $a = 1 " + op + " 1.5;\n}"},
                //float x float should not use a migration function and same for int x int
                {"<?php $a = 1.2 " + op + " 1.5;", "namespace{\n    float $a;\n    $a = 1.2 " + op + " 1.5;\n}"},
                {"<?php $a = 1 " + op + " 2;", "namespace{\n    int $a;\n    $a = 1 " + op + " 2;\n}"},
                {
                        "<?php function foo1(array $x){ $x = 1; return $x " + op + " 1;}",
                        "namespace{\n"
                                + "\n"
                                + "    function int foo1(array $x_0) {\n"
                                + "        mixed $x = $x_0;\n"
                                + "        $x = 1;\n"
                                + "        return cast<int>(" + oldSchool + "($x, 1));\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo2(array $x){ $x = 1.5; return 1.2 " + op + " $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function float foo2(array $x_0) {\n"
                                + "        mixed $x = $x_0;\n"
                                + "        $x = 1.5;\n"
                                + "        return cast<float>(" + oldSchool + "(1.2, $x));\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo3(array $x){ $x = 1.5; return 1.2 " + op + " $x " + op + " 1;}",
                        "namespace{\n"
                                + "\n"
                                + "    function float foo3(array $x_0) {\n"
                                + "        mixed $x = $x_0;\n"
                                + "        $x = 1.5;\n"
                                + "        return cast<float>(" + oldSchool + "(1.2, $x)) " + op + " 1;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo4(array $x){ $x = 1; return $x " + op + " '1';}",
                        "namespace{\n"
                                + "\n"
                                + "    function num foo4(array $x_0) {\n"
                                + "        mixed $x = $x_0;\n"
                                + "        $x = 1;\n"
                                + "        return " + oldSchool + "($x, '1');\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo5(array $x){ $x = 1.2; return $x " + op + " '1';}",
                        "namespace{\n"
                                + "\n"
                                + "    function float foo5(array $x_0) {\n"
                                + "        mixed $x = $x_0;\n"
                                + "        $x = 1.2;\n"
                                + "        return cast<float>(" + oldSchool + "($x, '1'));\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo6($x, $y){ return $x + $y;}",
                        "namespace{\n"
                                + "\n"
                                + "    function array foo60(array $x, array $y) {\n"
                                + "        return $x + $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function float foo61(float $x, float $y) {\n"
                                + "        return $x + $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function int foo62(int $x, int $y) {\n"
                                + "        return $x + $y;\n"
                                + "    }\n"
                                + "\n"
                                + "    function T foo63<T>({as T} $x, {as T} $y) where [T <: num] {\n"
                                + "        return cast<T>(oldSchoolAddition($x, $y));\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
        });
    }
}
