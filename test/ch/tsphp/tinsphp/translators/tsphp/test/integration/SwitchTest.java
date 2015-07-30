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

package ch.tsphp.tinsphp.translators.tsphp.test.integration;

import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorStatementTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SwitchTest extends ATranslatorStatementTest
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
        return Arrays.asList(new Object[][]{
                {
                        "$a = 1; switch($a){ case 1: $a=1; }",
                        "int $a;\n    $a = 1;\n    switch ($a) {\n        case 1:\n            $a = 1;\n    }"
                },
                {
                        "$a = 1; switch($a){ case 1: case 2: $a=1; break; }",
                        "int $a;\n    $a = 1;\n    switch ($a) {\n        case 1:\n        "
                                + "case 2:\n            $a = 1;\n            break;\n    }"
                },
                {
                        "$a = 1; switch($a){ case 1: $a=1; case 2: $a=1;}",
                        "int $a;\n    $a = 1;\n    switch ($a) {\n        case 1:\n            $a = 1;\n        "
                                + "case 2:\n            $a = 1;\n    }"
                },
                {
                        "$a = 1; switch($a){ case 1: $a=1; case 2: case 3: $a=1; }",
                        "int $a;\n    $a = 1;\n    switch ($a) {\n        case 1:\n            $a = 1;\n        "
                                + "case 2:\n        case 3:\n            $a = 1;\n    }"
                },
                {
                        "$a = 1; switch($a){ case 1: $a=1; $a=2; }",
                        "int $a;\n    $a = 1;\n    switch ($a) {\n        case 1:\n            "
                                + "$a = 1;\n            $a = 2;\n    }"
                },
                {
                        "$a = 1; switch($a){ case 1: $a=1; case 2: case 3: $a=2; default: $a=2; }",
                        "int $a;\n    $a = 1;\n    switch ($a) {\n        case 1:\n            $a = 1;\n        "
                                + "case 2:\n        case 3:\n            $a = 2;\n        "
                                + "default:\n            $a = 2;\n    }"
                },
                {
                        "$a = 1; switch($a){ case 1: $a=1; case 2: $a=1; default: $a=2; case 3: $a=2; }",
                        "int $a;\n    $a = 1;\n    switch ($a) {\n        case 1:\n            $a = 1;\n        "
                                + "case 2:\n            $a = 1;\n        default:\n            $a = 2;\n        "
                                + "case 3:\n            $a = 2;\n    }"
                },
                {
                        "$a = 1; switch($a){ case 1: {$a=1; $a=2; } case 2: case 3: {$a=1;} }",
                        "int $a;\n    $a = 1;\n    switch ($a) {\n        case 1:\n            $a = 1;\n            "
                                + "$a = 2;\n        case 2:\n        case 3:\n            $a = 1;\n    }"
                },
                {
                        "$a = 1; switch($a){ case 1: {$a=1; $a=2; } {$a=1;} case 2: case 3: {$a=1;} }",
                        "int $a;\n    $a = 1;\n    switch ($a) {\n        case 1:\n            $a = 1;\n            "
                                + "$a = 2;\n            $a = 1;\n        case 2:\n        "
                                + "case 3:\n            $a = 1;\n    }"
                },
                {
                        "$a = 1; switch($a){ case 1: $a=1; case 1+1: default: case 2: $a=2; case 2: case 3: {$a=1;} }",
                        "int $a;\n    $a = 1;\n    switch ($a) {\n        case 1:\n            $a = 1;"
                                + "\n        case 1 + 1:\n        case 2:\n        default:\n            $a = 2;"
                                + "\n        case 2:\n        case 3:\n            $a = 1;\n    }"
                }
        });
    }
}
