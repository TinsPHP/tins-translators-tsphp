/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file ExpressionHelper from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpressionHelper
{
    /**
     * @param offset defines how many characters will be on the left of the expression
     *               is used for the casting operator, TempVariableHelper respectively (getCharPositionInLine)
     * @return The expressions
     */
    public static List<String[]> getAllExpressions(int offset) {
        List<String[]> list = new ArrayList<>();
        list.addAll(getCastToTypeExpressions(offset));
        list.addAll(getConstantExpressions());
        return list;
    }

    /**
     * @param offset defines how many characters will be on the left of the expression
     *               is used for the casting operator, TempVariableHelper respectively (getCharPositionInLine)
     * @return The cast expressions
     */
    public static List<String[]> getCastToTypeExpressions(int offset) {
        String $a = "($a !== null ? ($a !== false ? ($a instanceof MyClass ? $a : "
                + "\\trigger_error('Cast failed, the evaluation type of $a must be MyClass.', \\E_RECOVERABLE_ERROR))"
                + " : false)"
                + " : null)";
        return Arrays.asList(new String[][]{
                //TODO rstoll TINS-276 conversions and casts
//                {"(MyClass) $a", $a},
//                {
//                        "(Type) (MyClass) $a",
//                        "(($_t1_" + (offset + 7) + " = " + $a + ") !== null ? "
//                                + "($_t1_" + (offset + 7) + " !== false ? "
//                                + "($_t1_" + (offset + 7) + " instanceof Type ? "
//                                + "$_t1_" + (offset + 7) + " "
//                                + ": \\trigger_error('Cast failed, the evaluation type of "
//                                + $a + " must be Type.', \\E_RECOVERABLE_ERROR)" +
//                                ")"
//                                + " : false)"
//                                + " : null)"
//                },
//                {"~~$a", "~~$a"},
//                {"@@$a", "@@$a"},
//                {
//                        "@(Type) ~$a",
//                        "@(($_t1_" + (offset + 8) + " = ~$a) !== null ? "
//                                + "($_t1_" + (offset + 8) + " !== false ? "
//                                + "($_t1_" + (offset + 8) + " instanceof Type ? "
//                                + "$_t1_" + (offset + 8) + " "
//                                + ": \\trigger_error('Cast failed, the evaluation type of "
//                                + "~$a must be Type.', \\E_RECOVERABLE_ERROR)" +
//                                ")"
//                                + " : false)"
//                                + " : null)"
//                },
        });
    }

    public static List<String[]> getConstantExpressions() {

        return Arrays.asList(new String[][]{
                {"true or false", "true or false"},
                {"true or false or true", "true or false or true"},
                {"true xor false", "true xor false"},
                {"true xor false xor true", "true xor false xor true"},
                {"true and false", "true and false"},
                {"true and false and true", "true and false and true"},
                {"true and false or true xor false", "true and false or true xor false"},
                {"true or false and true xor false", "true or false and true xor false"},

                //assignment above

                {"true ? 1 : 2", "(true) ? 1 : 2"},
                {"true ? (false ? 2 : 3) : 4", "(true) ? (false) ? 2 : 3 : 4"},
                {"true ? 1 : (false ? 2 : 3)", "(true) ? 1 : (false) ? 2 : 3"},
                // see TINS-249 ? operator has to stay left associative
                {"true ? 0 : true ? 1 : 2", "((true) ? 0 : true) ? 1 : 2"},

                {"false || true", "false || true"},
                {"false || true || false", "false || true || false"},
                {"false && true", "false && true"},
                {"false && true && false", "false && true && false"},
                {"false && true || false", "false && true || false"},
                {"false || true && false", "false || true && false"},
                {"false || true && false ? true : false", "(false || true && false) ? true : false"},

                {"1 | 2", "1 | 2"},
                {"1 | 2 | 3", "1 | 2 | 3"},
                {"1 ^ 2", "1 ^ 2"},
                {"1 ^ 2 ^ 3", "1 ^ 2 ^ 3"},
                {"1 & 2", "1 & 2"},
                {"1 & 2 & 3", "1 & 2 & 3"},
                {"1 & 2 | 3 ^ 4", "1 & 2 | 3 ^ 4"},
                {"(1 | 2) & 3 ^ 4", "(1 | 2) & 3 ^ 4"},

                {"1 == 2", "1 == 2"},
                {"1 === 2", "1 === 2"},
                {"1 != 2", "1 != 2"},
                {"1 !== 2", "1 !== 2"},

                {"1 < 2", "1 < 2"},
                {"1 <= 2", "1 <= 2"},
                {"1 > 2", "1 > 2"},
                {"1 >= 2", "1 >= 2"},
                {
                        "1 == 2 | 3 < 4 & 5 ? 6 != 7 : 8 === 9",
                        "(1 == 2 | 3 < 4 & 5) ? 6 != 7 : 8 === 9"
                },
                {"1 << 2", "1 << 2"},
                {"1 << 2 << 3", "1 << 2 << 3"},
                {"1 >> 2", "1 >> 2"},
                {"1 >> 2 >> 3", "1 >> 2 >> 3"},
                {"1 >> 2 << 3 >> 5", "1 >> 2 << 3 >> 5"},

                {"1 + 2", "1 + 2"},
                {"1 + 2 + 3", "1 + 2 + 3"},
                {"1 - 2", "1 - 2"},
                {"1 - 2 - 3", "1 - 2 - 3"},
                {"'hi' . \"hello\"", "'hi' . \"hello\""},
                {"'hi' . \"hello\" . 'ciao'", "'hi' . \"hello\" . 'ciao'"},
                //TODO rstoll TINS-276 conversions and casts
//                {"$a + $b . $c + $d - $f", "$a + $b . $c + $d - $f"},

                {"1 * 2", "1 * 2"},
                {"1 * 2 * 3", "1 * 2 * 3"},
                {"1 / 2", "1 / 2"},
                //TODO rstoll TINS-276 conversions and casts
//                {"1 / 2 / 3", "1 / 2 / 3"},
                {"1 % 2", "1 % 2"},
                //TODO rstoll TINS-276 conversions and casts
//                {"1 % 2 % 3", "1 % 2 % 3"},
//                {"1 % 2 / 3 % 4 * 5", "1 % 2 / 3 % 4 * 5"},

                //TODO rstoll TINS-276 conversions and casts
//                {
//                        "(Type) $a",
//                        "($a !== null ? ("
//                                + "$a !== false ? ("
//                                + "$a instanceof Type ? $a "
//                                + ": \\trigger_error('Cast failed, the evaluation type of $a must be Type.', " +
//                                "\\E_RECOVERABLE_ERROR))"
//                                + " : false)"
//                                + " : null)"
//                },

                {"~1", "~1"},
                {"@1", "@1"},
                {"!true", "!true"},
                {"!!true", "!!true"},
                {"!!! true", "!!!true"},
                {"+1", "+1"},
                {"-2", "-2"},
                {"+1 + 2", "+1 + 2"},
                {"-3 - 4", "-3 - 4"},


                {"exit", "exit"},
                {"exit(1)", "exit(1)"},
                {"(2)", "2"},
                //TODO rstoll TINS-271 - translator OOP - expressions
                {"null", "null"},
                {"true", "true"},
                {"false", "false"},
                {"1", "1"},
                {"2.123", "2.123"},
                {"'a'", "'a'"},
                {"\"asdf\"", "\"asdf\""},
                {"[1,2,'a'=>3]", "[1, 2, 'a' => 3]"},
        });

    }

}

