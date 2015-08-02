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

package ch.tsphp.tinsphp.translators.tsphp.test.integration.with_reduction;


import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorWithWideningTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ConstantDefinitionTest extends ATranslatorWithWideningTest
{

    public ConstantDefinitionTest(String theTestString, String theExpectedResult) {
        super("<?php " + theTestString + " ?>", "namespace{\n    " + theExpectedResult + "\n}");
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {"const a = null;", "const mixed a = null;"},
                {"const a = false;", "const bool a = false;"},
                {"const a = true;", "const bool a = true;"},
                {"const a = 1;", "const int a = 1;"},
                {"const a = 1.2;", "const float a = 1.2;"},
                {"const a = 'hi';", "const string a = 'hi';"},
                {"const b = 1; const a = b;", "const int b = 1;\n    const int a = b;"},
                {"const a = [1,2,3];", "const array a = [1, 2, 3];"},
                {"const a = ['a'=> 1, 'b' =>2, 2 => 3];", "const array a = ['a' => 1, 'b' => 2, 2 => 3];"},
                {"const a = 1, b = 2;", "const int a = 1;\n    const int b = 2;"},
                {
                        "const a = null, b = false, c = true, d=1, e=2.1, f='hi', g=f, i=[1,2], j=['a'=>'b'];",
                        "const mixed a = null;"
                                + "\n    const bool b = false;"
                                + "\n    const bool c = true;"
                                + "\n    const int d = 1;"
                                + "\n    const float e = 2.1;"
                                + "\n    const string f = 'hi';"
                                + "\n    const string g = f;"
                                + "\n    const array i = [1, 2];"
                                + "\n    const array j = ['a' => 'b'];"
                },
                //unary primitive atom
                {"const a = +1, b = -2;", "const int a = +1;\n    const int b = -2;"},
        });
    }
}
