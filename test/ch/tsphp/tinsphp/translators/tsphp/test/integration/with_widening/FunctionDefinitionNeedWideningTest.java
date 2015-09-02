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
public class FunctionDefinitionNeedWideningTest extends ATranslatorWithWideningTest
{

    public FunctionDefinitionNeedWideningTest(String testString, String expectedResult) {
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
                        "<?php function foo0($x){ if(true){return $x + 1;} return $x + [1];} ?>",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed foo0(mixed $x) {\n"
                                + "        if (!($x <: array) && !($x <: {as num})) {\n"
                                + "            \\trigger_error('Argument 1 passed to foo0() (parameter $x) "
                                + "must be a value of type array or {as num}.', \\E_USER_ERROR);\n"
                                + "        }\n"
                                + "        if (true) {\n"
                                + "            return oldSchoolAddition($x, 1);\n"
                                + "        }\n"
                                + "        return cast<array>($x) + [1];\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo1($x){if(true){return ~$x;} return $x + [1];}",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed foo1(mixed $x) {\n"
                                //TODO TINS-603 enhance collecting types in soft typing - should only be {as num}
                                + "        if (!($x <: array) && !($x <: {as num})) {\n"
                                + "            \\trigger_error('Argument 1 passed to foo1() (parameter $x) must be a " +
                                "value of type array or {as num}.', \\E_USER_ERROR);\n"
                                + "        }\n"
                                + "        if (true) {\n"
                                + "            return ~($x <: float ? cast<float>($x) : "
                                + "$x <: int ? cast<int>($x) : $x <: string ? cast<string>($x) : "
                                + "\\trigger_error('The variable $x must hold a value of type float, int or string.', "
                                + "\\E_USER_ERROR));\n"
                                + "        }\n"
                                + "        return cast<array>($x) + [1];\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo2($x){~$x; try{}catch(Exception $x){} return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed foo2(mixed $x) {\n"
                                + "        if (!($x <: Exception) && !($x <: float) && !($x <: int) "
                                + "&& !($x <: string)) {\n"
                                + "            \\trigger_error('Argument 1 passed to foo2() (parameter $x) must be a "
                                + "value of type Exception, float, int or string.', \\E_USER_ERROR);\n"
                                + "        }\n"
                                + "        ~($x <: float ? cast<float>($x) : "
                                + "$x <: int ? cast<int>($x) : "
                                + "$x <: string ? cast<string>($x) : \\trigger_error('The variable $x must hold a "
                                + "value of type float, int or string.', \\E_USER_ERROR));\n"
                                + "        try {\n"
                                + "        } catch (Exception $x1_50) {\n"
                                + "            $x = $x1_50;\n"
                                + "        }\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo3($x){~$x; foreach([1] as $x => $v){} return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function scalar foo3(scalar $x) {\n"
                                + "        if (!($x <: float) && !($x <: int) && !($x <: string)) {\n"
                                + "            \\trigger_error('Argument 1 passed to foo3() (parameter $x) "
                                + "must be a value of type float, int or string.', \\E_USER_ERROR);\n"
                                + "        }\n"
                                + "        mixed $v;\n"
                                + "        ~($x <: float ? cast<float>($x) : "
                                + "$x <: int ? cast<int>($x) : $x <: string ? cast<string>($x) : "
                                + "\\trigger_error('The variable $x must hold a value of type float, int or string.', "
                                + "\\E_USER_ERROR));\n"
                                + "        foreach ([1] as scalar $x1_44 => mixed $v1_50) {\n"
                                + "            $x = $x1_44;\n"
                                + "            $v = $v1_50;\n"
                                + "        }\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo4($x){~($x \n+ 1); try{}catch(Exception \n$x){} return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed foo4(mixed $x) {\n"
                                + "        mixed $_t2_0;\n"
                                + "        if (!($x <: Exception) && !($x <: array) && !($x <: {as num})) {\n"
                                + "            \\trigger_error('Argument 1 passed to foo4() (parameter $x) "
                                + "must be a value of type Exception, array or {as num}.', \\E_USER_ERROR);\n"
                                + "        }\n"
                                + "        ~(($_t2_0 = (oldSchoolAddition($x, 1))) <: float ? cast<" +
                                "float>($_t2_0) : $_t2_0 <: int ? cast<int>($_t2_0) : \\trigger_error('The variable " +
                                "$_t2_0" +
                                " must hold a value of type float or int.', \\E_USER_ERROR));\n"
                                + "        try {\n"
                                + "        } catch (Exception $x3_0) {\n"
                                + "            $x = $x3_0;\n"
                                + "        }\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //see TINS-602 runtime check for convertible types
                {
                        "<?php function foo5($x){echo $x; try{}catch(Exception \n$x){} return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed foo5(mixed $x) {\n"
                                + "        if (!($x <: Exception) && !($x <: {as string})) {\n"
                                + "            \\trigger_error('Argument 1 passed to foo5() (parameter $x) must be a " +
                                "value of type Exception or {as string}.', \\E_USER_ERROR);\n"
                                + "        }\n"
                                + "        echo $x as string;\n"
                                + "        try {\n"
                                + "        } catch (Exception $x2_0) {\n"
                                + "            $x = $x2_0;\n"
                                + "        }\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function addOne($x){ if(is_array($x)){return $x + [1];} return $x + 1;}",
                        "namespace{\n"
                                + "\n    function mixed addOne(mixed $x) {"
                                + "\n        if (!($x <: array) && !($x <: {as num})) {"
                                + "\n            \\trigger_error('Argument 1 passed to addOne() (parameter $x) "
                                + "must be a value of type array or {as num}.', \\E_USER_ERROR);"
                                + "\n        }"
                                + "\n        if (is_array($x)) {"
                                + "\n            return cast<array>($x) + [1];"
                                + "\n        }"
                                + "\n        return oldSchoolAddition($x, 1);"
                                + "\n    }"
                                + "\n"
                                + "\n}"
                }
        });
    }
}
