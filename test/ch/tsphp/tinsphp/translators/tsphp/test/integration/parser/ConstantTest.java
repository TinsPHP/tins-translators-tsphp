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
public class ConstantTest extends ATranslatorParserTest
{

    public ConstantTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {"const a = false;", "const ? a = false;"},
                {"const a = true;", "const ? a = true;"},
                {"const a = 1;", "const ? a = 1;"},
                {"const a = 1.2;", "const ? a = 1.2;"},
                {"const a = 'hi';", "const ? a = 'hi';"},
                {"const a = null;", "const ? a = null;"},
                {"const a = b;", "const ? a = b;"},
                {"const a = [1,2,3];", "const ? a = [1, 2, 3];"},
                {"const a = ['a'=> 1, 'b' =>2, 2 => 3];", "const ? a = ['a' => 1, 'b' => 2, 2 => 3];"},
                // multiple constants -> here in same line since we do not use inference engine which would split
                // the constants according its type
                {"const a = 1, b = 2;", "const ? a = 1, b = 2;"},
                {
                        "const a = false, b = true, c=1, d=2.1, e='hi', f=null, g=h, i=[1,2], j=['a'=>'b'];",
                        "const ? a = false, b = true, c = 1, d = 2.1, e = 'hi', "
                                + "f = null, g = h, i = [1, 2], j = ['a' => 'b'];"
                },
                //unary primitive atom
                {"const a = +1, b = -2;", "const ? a = +1, b = -2;"},
        });
    }
}
