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


import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorWithReductionTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FunctionDefinitionNeedReductionTest extends ATranslatorWithReductionTest
{

    public FunctionDefinitionNeedReductionTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                //TODO TINS-602 runtime check for convertible types
                {
                        "<?php function foo($x){ if(true){return $x + 1;} return $x + [1];} ?>",
                        "namespace{\n"
                                + "\n    function mixed foo(mixed $x) {"
                                + "\n        if (true) {"
                                + "\n            return oldSchoolAddition(({as (float | int)}) ($x), 1);"
                                + "\n        }"
                                + "\n        return (array) ($x) + [1];"
                                + "\n    }"
                                + "\n"
                                + "\n}"
                },
                //TODO TINS-604 runtime check with union type
                {
                        "<?php function foo($x){if(true){return ~$x;} return $x + [1];}",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed foo(mixed $x) {\n"
                                //TODO TINS-603 enhance collecting types in soft typing
//                                + "        if (!is_array($x) && !is_float($x) && !is_int($x) && !is_string($x)) {\n"
//                                + "            \\trigger_error('Argument 1 passed to foo() must be a value of type "
//                                + "array, float, int or string', \\E_USER_ERROR);\n"
//                                + "        }\n"
                                + "        if (true) {\n"
                                + "            return ~((float | int | string)) ($x);\n"
                                + "        }\n"
                                + "        return (array) ($x) + [1];\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                //TODO TINS-604 runtime check with union type
                {
                        "<?php function foo($x){~$x; try{}catch(Exception $x){} return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function mixed foo(mixed $x) {\n"
                                + "        if (!($x instanceof Exception) && !is_float($x) && !is_int($x) "
                                + "&& !is_string($x)) {\n"
                                + "            \\trigger_error('Argument 1 passed to foo() (parameter $x) must be a "
                                + "value of type Exception, float, int or string', \\E_USER_ERROR);\n"
                                + "        }\n"
                                + "        ~((float | int | string)) ($x);\n"
                                + "        try {\n"
                                + "        } catch (Exception $x1_49) {\n"
                                + "            $x = $x1_49;\n"
                                + "        }\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
                {
                        "<?php function foo($x){~$x; foreach([1] as $x => $v){} return $x;}",
                        "namespace{\n"
                                + "\n"
                                + "    function scalar foo(scalar $x) {\n"
                                + "        if (!is_float($x) && !is_int($x) && !is_string($x)) {\n"
                                + "            \\trigger_error('Argument 1 passed to foo() (parameter $x) must be a "
                                + "value of type float, int or string', \\E_USER_ERROR);\n"
                                + "        }\n"
                                + "        mixed $v;\n"
                                + "        ~((float | int | string)) ($x);\n"
                                + "        foreach ([1] as (int | string) $x1_43 => mixed $v1_49) {\n"
                                + "            $x = $x1_43;\n"
                                + "            $v = $v1_49;\n"
                                + "        }\n"
                                + "        return $x;\n"
                                + "    }\n"
                                + "\n"
                                + "}"
                },
        });
    }
}
