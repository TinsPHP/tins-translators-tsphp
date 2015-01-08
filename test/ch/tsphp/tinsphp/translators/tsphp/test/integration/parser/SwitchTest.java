/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file SwitchTest from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.parser;

import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorParserTest;
import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ExpressionHelper;
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
public class SwitchTest extends ATranslatorParserTest
{

    public SwitchTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();

        List<String[]> expressions = ExpressionHelper.getAllExpressions(7);
        for (Object[] expression : expressions) {
            collection.add(new Object[]{
                    "switch(" + expression[0] + "){}",
                    "switch (" + expression[1] + ") {\n}"
            });
        }

        for (String[] expression : expressions) {
            collection.add(new Object[]{
                    "switch(true){ case " + expression[0] + ":}",
                    "switch (true) {\n    case " + expression[1] + ":\n\n}"
            });
        }

        collection.addAll(Arrays.asList(new Object[][]{
                {
                        "switch($a){ case 1: $a=1; }",
                        "switch ($a) {\n    case 1:\n        $a = 1;\n}"
                },

                //TODO rstoll TINS-270 translator procedural - instructions
//                    {
//                        "switch($a){ case 1: case 2: $a=1; break; }",
//                        "switch ($a) {\n    case 1:\n    case 2:\n        $a = 1;\n        break;\n}"
//                    },
                {
                        "switch($a){ case 1: $a=1; case 2: $c=1;}",
                        "switch ($a) {\n    case 1:\n        $a = 1;\n    case 2:\n        $c = 1;\n}"
                },
                {
                        "switch($a){ case 1: $a=1; case 2: case 3: $a=1; }",
                        "switch ($a) {\n    case 1:\n        $a = 1;\n    case 2:\n    case 3:\n        $a = 1;\n}"
                },
                {
                        "switch($a){ case 1: $a=1; $b=2; }",
                        "switch ($a) {\n    case 1:\n        $a = 1;\n        $b = 2;\n}"
                },
                {
                        "switch($a){ case 1: $a=1; case 2: case 3: $a=2; default: $c=2; }",
                        "switch ($a) {\n    case 1:\n        $a = 1;\n    case 2:\n    case 3:\n        $a = 2;"
                                + "\n    default:\n        $c = 2;\n}"
                },
                {
                        "switch($a){ case 1: $a=1; case 2: $a=1; default: $c=2; case 3: $a=2; }",
                        "switch ($a) {\n    case 1:\n        $a = 1;\n    case 2:\n        $a = 1;"
                                + "\n    default:\n        $c = 2;\n    case 3:\n        $a = 2;\n}"
                },
                {
                        "switch($a){ case 1: {$a=1; $b=2; } case 2: case 3: {$a=1;} }",
                        "switch ($a) {\n    case 1:\n        $a = 1;\n        $b = 2;"
                                + "\n    case 2:\n    case 3:\n        $a = 1;\n}"
                },
                {
                        "switch($a){ case 1: {$a=1; $b=2; } {$a=1;} case 2: case 3: {$a=1;} }",
                        "switch ($a) {\n    case 1:\n        $a = 1;\n        $b = 2;\n        $a = 1;"
                                + "\n    case 2:\n    case 3:\n        $a = 1;\n}"
                },
                //due to the design decision that empty cases are allowed
                {
                        "switch($a){ case 1: $a=1; case 1+1: default: case 2: $b=2; case 2: case 3: {$a=1;} }",
                        "switch ($a) {\n    case 1:\n        $a = 1;"
                                + "\n    case 1 + 1:\n    case 2:\n    default:\n        $b = 2;"
                                + "\n    case 2:\n    case 3:\n        $a = 1;\n}"
                }
        }));
        return collection;
    }
}
