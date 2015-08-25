/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
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
public class ExpressionsNeedWideningTest extends ATranslatorWithWideningTest
{

    public ExpressionsNeedWideningTest(String testString, String expectedResult) {
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
                        "<?php $a = 1; $a = true; ~$a; ?>",
                        "namespace{\n"
                                + "    scalar $a;\n"
                                + "    $a = 1;\n"
                                + "    $a = true;\n"
                                //TODO TINS-666 soft typing erroneous for local/global variables
                                //should only be ~cast<int>($a)
                                + "    ~($a <: float ? cast<float>($a) : "
                                + "$a <: int ? cast<int>($a) : $a <: string ? cast<string>($a) : "
                                + "\\trigger_error('The variable $a must hold a value of type float, int or string.', "
                                + "\\E_USER_ERROR));\n"
                                + "}"
                },
                {
                        "<?php $a = 1; $a = 'hello'; ~($a \n+ 1); ?>",
                        "namespace{\n"
                                //TODO TINS-666 soft typing erroneous for local/global variables - should be scalar
                                + "    mixed $a;\n"
                                + "    $a = 1;\n"
                                + "    $a = 'hello';\n"
                                + "    ~(($_t2_0 = (oldSchoolAddition($a, 1))) <: float ? "
                                + "cast<float>($_t2_0) : $_t2_0 <: int ? cast<int>($_t2_0) : "
                                + "\\trigger_error('The variable $_t2_0 must hold a value of type float or int.', "
                                + "\\E_USER_ERROR));\n"
                                + "}\n"
                                + "namespace{\n"
                                + "    mixed $_t2_0;\n"
                                + "}"
                },
                {
                        "<?php $a = 1; $a = 'hello'; ~($a \n/ 1); ?>",
                        "namespace{\n"
                                //TODO TINS-666 soft typing erroneous for local/global variables - should be scalar
                                + "    {as num} $a;\n"
                                + "    $a = 1;\n"
                                + "    $a = 'hello';\n"
                                + "    ~(($_t2_0 = (oldSchoolDivide($a, 1))) <: float ? "
                                + "cast<float>($_t2_0) : $_t2_0 <: int ? cast<int>($_t2_0) : "
                                + "\\trigger_error('The variable $_t2_0 must hold a value of type float or int.', "
                                + "\\E_USER_ERROR));\n"
                                + "}\n"
                                + "namespace{\n"
                                + "    mixed $_t2_0;\n"
                                + "}"
                }
        });
    }
}
