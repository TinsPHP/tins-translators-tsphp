/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright; license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file ExpressionTest from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.inference;


import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorInferenceStatementTest;
import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ExpressionHelper;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class ExpressionTest extends ATranslatorInferenceStatementTest
{

    public ExpressionTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();
        List<String[]> expressions = ExpressionHelper.getAllExpressions(0);
        for (String[] expression : expressions) {
            collection.add(new Object[]{expression[0] + ";", expression[1] + ";"});
        }

        collection.addAll(Arrays.asList(new Object[][]{
                {"$a = 1;", "? $a;\n    $a = 1;"},
                {"$a = 1; $a += 1;", "? $a;\n    $a = 1;\n    $a += 1;"},
                {"$a = 1; $a -= 1;", "? $a;\n    $a = 1;\n    $a -= 1;"},
                {"$a = 1; $a *= 1;", "? $a;\n    $a = 1;\n    $a *= 1;"},
                {"$a = 1; $a /= 1;", "? $a;\n    $a = 1;\n    $a /= 1;"},
                {"$a = 1; $a &= 1;", "? $a;\n    $a = 1;\n    $a &= 1;"},
                {"$a = 1; $a |= 1;", "? $a;\n    $a = 1;\n    $a |= 1;"},
                {"$a = 1; $a ^= 1;", "? $a;\n    $a = 1;\n    $a ^= 1;"},
                {"$a = 1; $a %= 1;", "? $a;\n    $a = 1;\n    $a %= 1;"},
                {"$a = ''; $a .= 'hi';", "? $a;\n    $a = '';\n    $a .= 'hi';"},
                {"$a = 1; $a <<= 1;", "? $a;\n    $a = 1;\n    $a <<= 1;"},
                {"$a = 1; $a >>= 1;", "? $a;\n    $a = 1;\n    $a >>= 1;"},
                {
                        "$a = 1; $a = true ? $a += 1 : ($a -= 2);",
                        "? $a;\n    $a = 1;\n    $a = (true) ? ($a += 1) : ($a -= 2);"
                },
                {
                        "$a = 1; $b = 1; ($a *= 1 == 1) ? $b /= 1 ? $a &= 2 : ($a |= 3) : ($a ^= 4);",
                        "? $b;\n    ? $a;\n    $a = 1;\n    $b = 1;\n"
                                + "    (($a *= 1 == 1)) ? ($b /= (1) ? ($a &= 2) : ($a |= 3)) : ($a ^= 4);"

                },
                //TODO rstoll TINS-276 conversions and casts
//                {
//                        "$a = 2; $c = 1; $d = 2; $e = 3; $f = 4; $g = 2; $h = 1; $i = 1; $j = 2;"
//                                + "$a %= true ? $c .= $d ? $e <<= $f : ($g >>= $h) : ($i = $j);",
//                        "? $a;\n    ? $c;\n    ? $d;\n    ? $e;\n    ? $f;\n    ? $h;\n    ? $i;\n    ? $j;\n    "
//                                + "$a = 2; $c = 1; $d = 2; $e = 3; "
//                                + "$f = 4; $g = 2; $h = 1; $i = 1; $j = 2"
//                                + "$a %= (true) ? ($c .= ($d) ? ($e <<= $f) : ($g >>= $h)) : ($i = $j);"
//                },

                // = has lower precedence than ternary
                //; the following is equal to $a = (true ? 1 : ($b = 1))
                {"$a = true ? 1 : ($b = 1);", "? $b;\n    ? $a;\n    $a = (true) ? 1 : ($b = 1);"},
                // todo TINS-302 preceding helper could be improved for ternary
                {"true ? $a = 2 : false && true ? 1 : 2;", "? $a;\n    ((true) ? ($a = 2) : false && true) ? 1 : 2;"},
                {"$a = null; $a instanceof Exception;", "? $a;\n    $a = null;\n    $a instanceof Exception;"},
                {
                        "$a = null; $b = null; $a instanceof $b;",
                        "? $b;\n    ? $a;\n    $a = null;\n    $b = null;\n    $a instanceof $b;"
                },
                {"$a = null; clone $a;", "? $a;\n    $a = null;\n    clone $a;"},
                {"$a = null; $a++;", "? $a;\n    $a = null;\n    $a++;"},
                {"$a = null; $a--;", "? $a;\n    $a = null;\n    $a--;"},
                {"$a = null; ++$a;", "? $a;\n    $a = null;\n    ++$a;"},
                {"$a = null; --$a;", "? $a;\n    $a = null;\n    --$a;"},
                //TODO rstoll TINS-278 $_GET is a type instead of a variable
//                {"$_GET;", "$_GET;"},
                {"$a = [1]; $a[0];", "? $a;\n    $a = [1];\n    $a[0];"},
                //TODO rstoll TINS-271 - translator OOP - expressions
//                {"clone $a->a;","clone $a->a;"},
//                {"new Type;","new Type();"},
//                {"new Type();","new Type();"},
//                {"new Type(1,$a,'hello');","new Type(1, $a, 'hello');"},

                //                {"$a->a;","$a->a;"},
//                {"$a->foo();","$a->foo();"},
//                {"$a->foo(true || false,123*9);","$a->foo(true || false, 123 * 9);"},
                {"strpos('h','hello');", "strpos('h', 'hello');"},
                {"\\strpos('h','hello');", "\\strpos('h', 'hello');"},
                //TODO rstoll TINS-271 - translator OOP - expressions
//                {"self::foo();","self::foo();"},
//                {"parent::foo();","parent::foo();"},
//                {"Foo::foo();","Foo::foo();"},
//                {"self::$a;","self::$a;"},
//                {"parent::$a;","parent::$a;"},
//                {"Foo::$a;","Foo::$a;"},
//                {"self::a;","self::a;"},
//                {"parent::a;","parent::a;"},
//                {"Foo::a;","Foo::a;"},
//                {"a\\Foo::a;","a\\Foo::a;"},
                {
                        "namespace{const b = 1;} namespace a{ const b = 1;} namespace{a\\b;} ",
                        "const int b = 1;\n}\nnamespace a{\n    const int b = 1;\n}\nnamespace{\n    a\\b;"
                },
                {"$a = 1; $a;", "? $a;\n    $a = 1;\n    $a;"},
                {"$a = 1; (-$a + $a) * $a;", "? $a;\n    $a = 1;\n    (-$a + $a) * $a;"},
                {
                        "$a = null; !($a instanceof Exception) || $a < 1 + 2 == ~(1 | 3 & 12);",
                        "? $a;\n    $a = null;\n    !($a instanceof Exception) || $a < 1 + 2 == ~(1 | 3 & 12);"
                }
        }));
        return collection;
    }
}
