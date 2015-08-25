/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file ForTest from the translator component of the TSPHP project.
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
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ForTest extends ATranslatorWithWideningTest
{

    public ForTest(String testString, String expectedResult) {
        super("<?php " + testString, "namespace{\n    " + expectedResult + "\n}");
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {
                        "for($i=1     ; true ; ++$i  ) $i=1;",
                        "int $i;\n    for ($i = 1; true; ++$i) {\n        $i = 1;\n    }"
                },
                {
                        "$i = 0;for(         ; true ; ++$i  ) $i=1;",
                        "int $i;\n    $i = 0;\n    for (; true; ++$i) {\n        $i = 1;\n    }"
                },
                {
                        "$i = 0;for(         ;      ; $i+=1 ) $i=1;",
                        "int $i;\n    $i = 0;\n    for (; ; $i += 1) {\n        $i = 1;\n    }"
                },
                {
                        "for(         ; true ;       ) $a=1;",
                        "int $a;\n    for (; true; ) {\n        $a = 1;\n    }"
                },
                {
                        "for(         ;      ;       ) $a=1;",
                        "int $a;\n    for (; ; ) {\n        $a = 1;\n    }"
                },
                {
                        "for($i=0;$i<10;++$i){}",
                        "int $i;\n    for ($i = 0; $i < 10; ++$i) {\n    }"
                },
                {"for(;1, true;){}", "for (; 1, true; ) {\n    }"},
                {"for(;false, 1;){}", "for (; false, 1 as bool; ) {\n    }"},
                {
                        "$x = 1; $x = 'hello'; $x = false; for(;$x;){}",
                        "scalar $x;"
                                + "\n    $x = 1;"
                                + "\n    $x = 'hello';"
                                + "\n    $x = false;"
                                + "\n    for (; $x as bool if int, string; ) {\n    }"
                },
        });
    }
}
