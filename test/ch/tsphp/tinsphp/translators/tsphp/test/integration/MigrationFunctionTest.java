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

package ch.tsphp.tinsphp.translators.tsphp.test.integration;


import ch.tsphp.common.AstHelper;
import ch.tsphp.common.TSPHPAstAdaptor;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.config.ICoreInitialiser;
import ch.tsphp.tinsphp.common.config.ISymbolsInitialiser;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.core.config.HardCodedCoreInitialiser;
import ch.tsphp.tinsphp.symbols.config.HardCodedSymbolsInitialiser;
import ch.tsphp.tinsphp.translators.tsphp.IOperatorHelper;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPOperatorHelper;
import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorInferenceTest;
import org.antlr.runtime.RecognitionException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RunWith(Parameterized.class)
public class MigrationFunctionTest extends ATranslatorInferenceTest
{
    private static ITypeHelper typeHelper;
    private static Map<String, ITypeSymbol> primitiveTypes;

    public MigrationFunctionTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @BeforeClass
    public static void init() {
        ISymbolsInitialiser symbolsInitialiser = new HardCodedSymbolsInitialiser();
        ICoreInitialiser coreInitialiser = new HardCodedCoreInitialiser(
                new AstHelper(new TSPHPAstAdaptor()), symbolsInitialiser);
        typeHelper = symbolsInitialiser.getTypeHelper();
        primitiveTypes = coreInitialiser.getCore().getPrimitiveTypes();
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Override
    public IOperatorHelper createOperatorHelper() {
        return new TSPHPOperatorHelper(typeHelper, primitiveTypes);
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
                                + "        (Exception | float | int) $x = $x_0;\n"
                                + "        $x = 1;\n"
                                + "        $x = 1.5;\n"
                                + "        echo $x as string if float, int;\n"
                                + "        return 1;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                }
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
                                + "\n    (falseType | trueType) $a;"
                                + "\n    (falseType | trueType) $x;"
                                + "\n    $x = true;"
                                + "\n    $x = false;"
                                + "\n    $a = " + (requiresParentheses ? "(" : "")
                                + "1 as (falseType | trueType) " + op + " $x" + (requiresParentheses ? ")" : "") + ";"
                                + "\n}"
                },
                {
                        "<?php $x = true; $x = false; $a = ($x " + op + " 1.2);",
                        "namespace{"
                                + "\n    (falseType | trueType) $a;"
                                + "\n    (falseType | trueType) $x;"
                                + "\n    $x = true;"
                                + "\n    $x = false;"
                                + "\n    $a = " + (requiresParentheses ? "(" : "")
                                + "$x " + op + " 1.2 as (falseType | trueType)" + (requiresParentheses ? ")" : "") + ";"
                                + "\n}"
                },
                {
                        "<?php $a = (1 " + op + " 'a');",
                        "namespace{"
                                + "\n    (falseType | trueType) $a;"
                                + "\n    $a = " + (requiresParentheses ? "(" : "")
                                + "1 as (falseType | trueType) "
                                + op + " 'a' as (falseType | trueType)" + (requiresParentheses ? ")" : "") + ";"
                                + "\n}"
                },
                {
                        "<?php $x = true; $x = false; $a = ($x " + op + " $x);",
                        "namespace{"
                                + "\n    (falseType | trueType) $a;"
                                + "\n    (falseType | trueType) $x;"
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
                        "namespace{\n    float $a;\n    $a = (float) (" + oldSchool + "('a', 1.2));\n}"
                },
                {
                        "<?php $a = 1.2 " + op + " true;",
                        "namespace{\n    float $a;\n    $a = (float) (" + oldSchool + "(1.2, true));\n}"
                },
                {
                        "<?php $a = 'a' " + op + " 1;",
                        "namespace{\n    (float | int) $a;\n    $a = " + oldSchool + "('a', 1);\n}"
                },
                {
                        "<?php $a = 'a' " + op + " true;",
                        "namespace{\n    (float | int) $a;\n    $a = " + oldSchool + "('a', true);\n}"
                },
                {
                        "<?php $a = '1' " + op + " '2';",
                        "namespace{\n    (float | int) $a;\n    $a = " + oldSchool + "('1', '2');\n}"
                },
                {
                        "<?php $a = true " + op + " false;",
                        "namespace{\n    int $a;\n    $a = (int) (" + oldSchool + "(true, false));\n}"
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
                                + "        (array | int) $x = $x_0;\n"
                                + "        $x = 1;\n"
                                + "        return (int) (" + oldSchool + "((int) ($x), 1));\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo2(array $x){ $x = 1.5; return 1.2 " + op + " $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function float foo2(array $x_0) {\n"
                                + "        (array | float) $x = $x_0;\n"
                                + "        $x = 1.5;\n"
                                + "        return (float) (" + oldSchool + "(1.2, (float) ($x)));\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo3(array $x){ $x = 1.5; return 1.2 " + op + " $x " + op + " 1;}",
                        "namespace{\n"
                                + "\n"
                                + "    function float foo3(array $x_0) {\n"
                                + "        (array | float) $x = $x_0;\n"
                                + "        $x = 1.5;\n"
                                + "        return (float) (" + oldSchool + "(1.2, (float) ($x))) " + op + " 1;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo4(array $x){ $x = 1; return $x " + op + " '1';}",
                        "namespace{\n"
                                + "\n"
                                + "    function (float | int) foo4(array $x_0) {\n"
                                + "        (array | int) $x = $x_0;\n"
                                + "        $x = 1;\n"
                                + "        return " + oldSchool + "((int) ($x), '1');\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo5(array $x){ $x = 1.2; return $x " + op + " '1';}",
                        "namespace{\n"
                                + "\n"
                                + "    function float foo5(array $x_0) {\n"
                                + "        (array | float) $x = $x_0;\n"
                                + "        $x = 1.2;\n"
                                + "        return (float) (" + oldSchool + "((float) ($x), '1'));\n"
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
                                + "    function T foo63<T>({as T} $x, {as T} $y) where [T <: (float | int)] {\n"
                                + "        return (T) (oldSchoolAddition($x, $y));\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
        });
    }
}
