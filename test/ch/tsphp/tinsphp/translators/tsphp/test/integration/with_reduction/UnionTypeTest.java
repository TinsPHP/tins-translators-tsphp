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

package ch.tsphp.tinsphp.translators.tsphp.test.integration.with_reduction;


import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorWithReductionTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class UnionTypeTest extends ATranslatorWithReductionTest
{

    public UnionTypeTest(String testString, String expectedResult) {
        super("<?php " + testString + " ?>", "namespace{\n    " + expectedResult + "\n}");
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {"$a = null;", "mixed $a;\n    $a = null;"},
                {"$a = false;", "bool $a;\n    $a = false;"},
                {"$a = true;", "bool $a;\n    $a = true;"},
                {"$a = false; $a = true;", "bool $a;\n    $a = false;\n    $a = true;"},
                {"$a = true; $a = false;", "bool $a;\n    $a = true;\n    $a = false;"},

                {"$a = null; $a = false;", "bool? $a;\n    $a = null;\n    $a = false;"},
                {"$a = null; $a = true;", "bool? $a;\n    $a = null;\n    $a = true;"},
                {"$a = null; $a = true; $a = false;", "bool? $a;\n    $a = null;\n    $a = true;\n    $a = false;"},
                {"$a = null; $a = 1;", "int? $a;\n    $a = null;\n    $a = 1;"},
                {"$a = null; $a = 1.2;", "float? $a;\n    $a = null;\n    $a = 1.2;"},
                {"$a = null; $a = 1; $a = 2.3;", "num? $a;\n    $a = null;\n    $a = 1;\n    $a = 2.3;"},
                {"$a = null; $a = 'hello';", "string? $a;\n    $a = null;\n    $a = 'hello';"},
                {"$a = null; $a = [1];", "array? $a;\n    $a = null;\n    $a = [1];"},

                {"$a = false; $a = null;", "bool? $a;\n    $a = false;\n    $a = null;"},
                {"$a = false; $a = 1;", "int! $a;\n    $a = false;\n    $a = 1;"},
                {"$a = false; $a = 1.2;", "float! $a;\n    $a = false;\n    $a = 1.2;"},
                {"$a = false; $a = 1; $a = 2.3;", "num! $a;\n    $a = false;\n    $a = 1;\n    $a = 2.3;"},
                {"$a = false; $a = 'hello';", "string! $a;\n    $a = false;\n    $a = 'hello';"},
                {"$a = false; $a = [1];", "array! $a;\n    $a = false;\n    $a = [1];"},

                {"$a = true; $a = null;", "bool? $a;\n    $a = true;\n    $a = null;"},
                {"$a = true; $a = 1;", "scalar $a;\n    $a = true;\n    $a = 1;"},
                {"$a = true; $a = 1.2;", "scalar $a;\n    $a = true;\n    $a = 1.2;"},
                {"$a = true; $a = 1; $a = 2.3;", "scalar $a;\n    $a = true;\n    $a = 1;\n    $a = 2.3;"},
                {"$a = true; $a = 'hello';", "scalar $a;\n    $a = true;\n    $a = 'hello';"},
                {"$a = true; $a = [1];", "mixed $a;\n    $a = true;\n    $a = [1];"},
                {
                        "$a = false; $a = true; $a = 1; $a = 1.2; $a = 'hello';",
                        "scalar $a;\n    $a = false;\n    $a = true;\n    $a = 1;\n    $a = 1.2;\n    $a = 'hello';"
                },


                {"$a = null; $a = false; $a = true;", "bool? $a;\n    $a = null;\n    $a = false;\n    $a = true;"},
                {"$a = null; $a = false; $a = 1;", "int!? $a;\n    $a = null;\n    $a = false;\n    $a = 1;"},
                {"$a = null; $a = false; $a = 1.2;", "float!? $a;\n    $a = null;\n    $a = false;\n    $a = 1.2;"},
                {
                        "$a = null; $a = false; $a = 1; $a = 2.3;",
                        "num!? $a;\n    $a = null;\n    $a = false;\n    $a = 1;\n    $a = 2.3;"
                },
                {
                        "$a = null; $a = false; $a = 'hello';",
                        "string!? $a;\n    $a = null;\n    $a = false;\n    $a = 'hello';"
                },
                {"$a = null; $a = false; $a = [1];", "array!? $a;\n    $a = null;\n    $a = false;\n    $a = [1];"},
        });
    }
}
