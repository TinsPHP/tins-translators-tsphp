/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
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
public class GlobalFunctionTest extends ATranslatorStatementTest
{

    public GlobalFunctionTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {"echo 'hi';", "echo 'hi';"},
                {"echo 'hi', \"hello\";", "echo 'hi', \"hello\";"},
                {"exit;", "exit;"},
                {"exit(1);", "exit(1);"},
                {"exit('hi');", "exit('hi');"},
                //TODO TINS-395 add new operator
//                {"throw new Exception();", "throw new Exception();"}
        });
    }
}
