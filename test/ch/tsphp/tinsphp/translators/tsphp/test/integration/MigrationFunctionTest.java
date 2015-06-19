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

        collection.addAll(getArithmeticVariations("+", "oldSchoolAddition"));
        collection.addAll(getArithmeticVariations("-", "oldSchoolSubtraction"));
        collection.addAll(getArithmeticVariations("*", "oldSchoolMultiplication"));

        return collection;
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
                                //TODO TINS-537 most specific overload in soft typing
                                //+ "    function float foo5(array $x_0) {\n"
                                + "    function (float | int) foo5(array $x_0) {\n"
                                + "        (array | float) $x = $x_0;\n"
                                + "        $x = 1.2;\n"
                                //TODO TINS-537 most specific overload in soft typing
                                //+ "        return (float) (oldSchoolAddition((float) ($x), '1'));\n"
                                + "        return " + oldSchool + "((float) ($x), '1');\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
        });
    }
}
