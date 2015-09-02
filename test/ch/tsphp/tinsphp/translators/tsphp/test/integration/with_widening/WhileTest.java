/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file IfTest from the translator component of the TSPHP project.
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
public class WhileTest extends ATranslatorWithWideningTest
{

    public WhileTest(String testString, String expectedResult) {
        super("<?php " + testString, "namespace{\n    " + expectedResult + "\n}");
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {"$x = 1; while($x){}", "int $x;\n    $x = 1;\n    while ($x as bool) {\n    }"},
                {"while('hello, '.'world'){}", "while ('hello, ' . 'world' as bool) {\n    }"},
                {"$x = 1; do{}while($x);", "int $x;\n    $x = 1;\n    do {\n    } while ($x as bool);"},
                {"do{}while('hello, '.'world');", "do {\n    } while ('hello, ' . 'world' as bool);"}
        });
    }
}
