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
import ch.tsphp.tinsphp.common.symbols.ISymbolFactory;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.core.config.HardCodedCoreInitialiser;
import ch.tsphp.tinsphp.symbols.config.HardCodedSymbolsInitialiser;
import ch.tsphp.tinsphp.translators.tsphp.IOperatorHelper;
import ch.tsphp.tinsphp.translators.tsphp.IRuntimeCheckProvider;
import ch.tsphp.tinsphp.translators.tsphp.ITypeTransformer;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPOperatorHelper;
import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorTest;
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
public class MigrationFunctionTest extends ATranslatorTest
{
    private static ISymbolFactory symbolFactory;
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
        symbolFactory = symbolsInitialiser.getSymbolFactory();
        typeHelper = symbolsInitialiser.getTypeHelper();
        primitiveTypes = coreInitialiser.getCore().getPrimitiveTypes();
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Override
    public IOperatorHelper createOperatorHelper(
            IRuntimeCheckProvider runtimeCheckProvider, ITypeTransformer typeTransformer) {
        return new TSPHPOperatorHelper(
                symbolFactory, typeHelper, primitiveTypes, runtimeCheckProvider, typeTransformer);
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
        collection.addAll(getDivideVariations());

        collection.addAll(getIntVariations("|", "oldSchoolBitwiseOr"));
        collection.addAll(getIntVariations("^", "oldSchoolBitwiseXor"));
        collection.addAll(getIntVariations("&", "oldSchoolBitwiseAnd"));
        collection.addAll(getIntVariations("<<", "oldSchoolShiftLeft"));
        collection.addAll(getIntVariations(">>", "oldSchoolShiftRight"));

        collection.addAll(getArrayAccessVariants());

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
                },
                {
                        "<?php $a = (bool) 1;",
                        "namespace{"
                                + "\n    (falseType | trueType) $a;"
                                + "\n    $a = oldSchoolCast<(falseType | trueType)>(1);"
                                + "\n}"
                },
                {"<?php $a = (int) 'hello';", "namespace{\n    int $a;\n    $a = oldSchoolCast<int>('hello');\n}"},
                {"<?php $a = (int) 1;", "namespace{\n    int $a;\n    $a = oldSchoolCast<int>(1);\n}"},
                {"<?php $a = (float) 1;", "namespace{\n    float $a;\n    $a = oldSchoolCast<float>(1);\n}"},
                {"<?php $a = (string) 1;", "namespace{\n    string $a;\n    $a = oldSchoolCast<string>(1);\n}"},
                {"<?php $a = (array) 1;", "namespace{\n    array $a;\n    $a = oldSchoolCast<array>(1);\n}"},
                {
                        "<?php function typeHintAndDataPolymorphism2(array $x, $key){$x = $key & 1.2; return $x + 1;}",
                        "namespace{\n"
                                + "\n"
                                + "    function (float | int) typeHintAndDataPolymorphism2("
                                + "array $x_0, (array | {as int}) $key) {\n"
                                + "        (array | int | string) $x = $x_0;\n"
                                + "        $x = oldSchoolBitwiseAnd($key, 1.2);\n"
                                + "        return oldSchoolAddition($x, 1);\n"
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
                        "namespace{\n    float $a;\n    $a = cast<float>(" + oldSchool + "('a', 1.2));\n}"
                },
                {
                        "<?php $a = 1.2 " + op + " true;",
                        "namespace{\n    float $a;\n    $a = cast<float>(" + oldSchool + "(1.2, true));\n}"
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
                                + "        (array | int) $x = $x_0;\n"
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
                                + "        (array | float) $x = $x_0;\n"
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
                                + "        (array | float) $x = $x_0;\n"
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
                                + "    function (float | int) foo4(array $x_0) {\n"
                                + "        (array | int) $x = $x_0;\n"
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
                                + "        (array | float) $x = $x_0;\n"
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
                                + "    function T foo63<T>({as T} $x, {as T} $y) where [T <: (float | int)] {\n"
                                + "        return cast<T>(oldSchoolAddition($x, $y));\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
        });
    }


    private static Collection<? extends Object[]> getDivideVariations() {
        return Arrays.asList(new String[][]{
                //int / int is not a int division in PHP
                {
                        "<?php $a = 1 / 1;",
                        "namespace{\n    (falseType | float | int) $a;\n    $a = oldSchoolIntDivide(1, 1);\n}"
                },
                // int x float and float x int will choose {as num} x float, float x {as num} respectively but do not
                // require a migration function in TSPHP due to an implicit conversion from int to float
                {"<?php $a = 1 / 2.2;", "namespace{\n    (falseType | float) $a;\n    $a = 1 / 2.2;\n}"},
                {"<?php $a = 1.3 / 2;", "namespace{\n    (falseType | float) $a;\n    $a = 1.3 / 2;\n}"},
                {
                        "<?php $a = 1.3 / '2';",
                        "namespace{\n    (falseType | float) $a;\n    $a = oldSchoolFloatDivide(1.3, '2');\n}"
                },
                {
                        "<?php $a = 1.3 / true;",
                        "namespace{\n    (falseType | float) $a;\n    $a = oldSchoolFloatDivide(1.3, true);\n}"
                },
                {
                        "<?php $x = false; $x = true; $a = 1.3 / $x;",
                        "namespace{"
                                + "\n    (falseType | float) $a;"
                                + "\n    (falseType | trueType) $x;"
                                + "\n    $x = false;"
                                + "\n    $x = true;"
                                + "\n    $a = oldSchoolFloatDivide(1.3, $x);"
                                + "\n}"
                },
                {
                        "<?php $a = '2' / 1.3;",
                        "namespace{\n    (falseType | float) $a;\n    $a = oldSchoolFloatDivide('2', 1.3);\n}"
                },
                {
                        "<?php $a = true / 1.3;",
                        "namespace{\n    (falseType | float) $a;\n    $a = oldSchoolFloatDivide(true, 1.3);\n}"
                },
                {
                        "<?php $x = false; $x = true; $a = $x / 1.3;",
                        "namespace{"
                                + "\n    (falseType | float) $a;"
                                + "\n    (falseType | trueType) $x;"
                                + "\n    $x = false;"
                                + "\n    $x = true;"
                                + "\n    $a = oldSchoolFloatDivide($x, 1.3);"
                                + "\n}"
                },
                {
                        "<?php $a = 2 / true;",
                        "namespace{\n    (falseType | float | int) $a;\n    $a = oldSchoolDivide(2, true);\n}"
                },
                {
                        "<?php $a = '2' / true;",
                        "namespace{\n    (falseType | float | int) $a;\n    $a = oldSchoolDivide('2', true);\n}"
                },
                {
                        "<?php $a = false / '2';",
                        "namespace{\n    (falseType | float | int) $a;\n    $a = oldSchoolDivide(false, '2');\n}"
                },
                {
                        "<?php $a = false / 2;",
                        "namespace{\n    (falseType | float | int) $a;\n    $a = oldSchoolDivide(false, 2);\n}"
                },
        });
    }


    private static Collection<? extends Object[]> getArrayAccessVariants() {
        return Arrays.asList(new String[][]{
                {"<?php $a = [1]; $a[0];", "namespace{\n    array $a;\n    $a = [1];\n    $a[0];\n}"},
                {"<?php $a = [1]; $a['hello'];", "namespace{\n    array $a;\n    $a = [1];\n    $a['hello'];\n}"},
                //see TINS-653 migration function for array access
                {
                        "<?php $a = [1]; $a[1.2];",
                        "namespace{\n    array $a;\n    $a = [1];\n    oldSchoolArrayAccess($a, 1.2);\n}"
                },
                //see TINS-653 migration function for array access
                {
                        "<?php function typeHintAndDataPolymorphism(array $x, $key){$x = $x[$key]; return $x + 1;}",
                        "namespace{\n"
                                + "\n"
                                + "    function (float | int) typeHintAndDataPolymorphism("
                                + "array $x_0, {as int} $key) {\n"
                                + "        mixed $x = $x_0;\n"
                                + "        $x = oldSchoolArrayAccess($x, $key);\n"
                                + "        return oldSchoolAddition($x, 1);\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
        });
    }

}
