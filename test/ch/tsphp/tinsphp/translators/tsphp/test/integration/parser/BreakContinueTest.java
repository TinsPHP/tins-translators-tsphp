/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is part of the TSPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.parser;

import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorParserTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BreakContinueTest extends ATranslatorParserTest
{

    public BreakContinueTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {"switch(true){case 1: break;}", "switch (true) {\n    case 1:\n        break;\n}"},
                {"switch(true){case 1: break 1;}", "switch (true) {\n    case 1:\n        break 1;\n}"},
                {"switch(true){case 1: continue;}", "switch (true) {\n    case 1:\n        continue;\n}"},
                {"switch(true){case 1: continue 1;}", "switch (true) {\n    case 1:\n        continue 1;\n}"},
        });
    }
}
