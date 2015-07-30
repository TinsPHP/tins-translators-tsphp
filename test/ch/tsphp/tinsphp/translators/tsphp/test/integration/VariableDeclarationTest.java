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


import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorTest;
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
public class VariableDeclarationTest extends ATranslatorTest
{

    public VariableDeclarationTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();
        collection.addAll(Arrays.asList(new Object[][]{
                {"<?php $a=null;", "namespace{\n    nullType $a;\n    $a = null;\n}"},
                {"<?php $a=false;", "namespace{\n    falseType $a;\n    $a = false;\n}"},
                {"<?php $a=true;", "namespace{\n    trueType $a;\n    $a = true;\n}"},
                {"<?php $a=1;", "namespace{\n    int $a;\n    $a = 1;\n}"},
                {"<?php $a=1.2;", "namespace{\n    float $a;\n    $a = 1.2;\n}"},
                {"<?php $a='hi';", "namespace{\n    string $a;\n    $a = 'hi';\n}"},
                {"<?php $a=[];", "namespace{\n    array $a;\n    $a = [];\n}"},
                {
                        "<?php $a=false; $a=true;",
                        "namespace{\n    (falseType | trueType) $a;\n    $a = false;\n    $a = true;\n}"
                },
                {"<?php $a=1; $a=2.3;", "namespace{\n    (float | int) $a;\n    $a = 1;\n    $a = 2.3;\n}"},
                {
                        "<?php $a=false; $a=true; $a=1; $a=1.5; $a='hi';",
                        "namespace{\n    (falseType | float | int | string | trueType) $a;"
                                + "\n    $a = false;\n    $a = true;\n    $a = 1;\n    $a = 1.5;\n    $a = 'hi';\n}"
                },
                {"<?php if(true){$a=1;}", "namespace{\n    int $a;\n    if (true) {\n        $a = 1;\n    }\n}"},
                {
                        "<?php if(true){}else{$a=1;}",
                        "namespace{\n    int $a;\n    if (true) {\n    } else {\n        $a = 1;\n    }\n}"
                },
                {
                        "<?php if(true){}else{if(true){$a=1;}}",
                        "namespace{\n"
                                + "    int $a;\n"
                                + "    if (true) {\n"
                                + "    } else {\n"
                                + "        if (true) {\n"
                                + "            $a = 1;\n"
                                + "        }\n"
                                + "    }\n"
                                + "}"
                },
        }));

        return collection;
    }
}
