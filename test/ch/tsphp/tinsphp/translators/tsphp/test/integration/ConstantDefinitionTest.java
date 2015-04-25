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


import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorInferenceStatementTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ConstantDefinitionTest extends ATranslatorInferenceStatementTest
{

    public ConstantDefinitionTest(String theTestString, String theExpectedResult) {
        super(theTestString, theExpectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {"const a = false;", "const false a = false;"},
                {"const a = true;", "const true a = true;"},
                {"const a = 1;", "const int a = 1;"},
                {"const a = 1.2;", "const float a = 1.2;"},
                {"const a = 'hi';", "const string a = 'hi';"},
                {"const a = null;", "const null a = null;"},
                {"const b = 1; const a = b;", "const int b = 1;\n    const int a = b;"},
                {"const a = [1,2,3];", "const array a = [1, 2, 3];"},
                {"const a = ['a'=> 1, 'b' =>2, 2 => 3];", "const array a = ['a' => 1, 'b' => 2, 2 => 3];"},
                {"const a = 1, b = 2;", "const int a = 1;\n    const int b = 2;"},
                {
                        "const a = false, b = true, c=1, d=2.1, e='hi', f=null, g=f, i=[1,2], j=['a'=>'b'];",
                        "const false a = false;"
                                + "\n    const true b = true;"
                                + "\n    const int c = 1;"
                                + "\n    const float d = 2.1;"
                                + "\n    const string e = 'hi';"
                                + "\n    const null f = null;"
                                + "\n    const null g = f;"
                                + "\n    const array i = [1, 2];"
                                + "\n    const array j = ['a' => 'b'];"
                },
                //unary primitive atom
                {"const a = +1, b = -2;", "const int a = +1;\n    const int b = -2;"},
        });
    }
}
