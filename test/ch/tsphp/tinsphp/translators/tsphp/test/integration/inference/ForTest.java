/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file ForTest from the translator component of the TSPHP project.
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
public class ForTest extends ATranslatorInferenceStatementTest
{

    public ForTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();
        collection.addAll(Arrays.asList(new Object[][]{
                {
                        "for($i=1     ; true ; ++$i  ) $i=1;",
                        "int $i;\n    for ($i = 1; true; ++$i) {\n        $i = 1;\n    }"
                },
                {
                        "$i = 0;for(         ; true ; ++$i  ) $i=1;",
                        "int $i;\n    $i = 0;\n    for (; true; ++$i) {\n        $i = 1;\n    }"
                },
                {
                        "$i = 0;for(         ;      ; $i+=1 ) $i=1;",
                        "int $i;\n    $i = 0;\n    for (; ; $i += 1) {\n        $i = 1;\n    }"
                },
                {
                        "for(         ; true ;       ) $a=1;",
                        "int $a;\n    for (; true; ) {\n        $a = 1;\n    }"
                },
                {
                        "for(         ;      ;       ) $a=1;",
                        "int $a;\n    for (; ; ) {\n        $a = 1;\n    }"
                },
                {
                        "for($i=0;$i<10;++$i){}",
                        "int $i;\n    for ($i = 0; $i < 10; ++$i) {\n    }"
                }
        }));

        List<String[]> expressions = ExpressionHelper.getConstantExpressions();
        for (Object[] expression : expressions) {
            collection.add(new Object[]{
                    "for(" + expression[0] + ";" + expression[0] + ", true;" + expression[0] + ") $a=1;",
                    "int $a;\n    for (" + expression[1] + "; " + expression[1] + ", true; " + expression[1] + ") "
                            + "{\n        $a = 1;\n    }"
            });
            collection.add(new Object[]{
                    "for("
                            + expression[0] + "," + expression[0] + ";"
                            + expression[0] + "," + expression[0] + ", true;"
                            + expression[0] + "," + expression[0] + " "
                            + ") $a=1;",
                    "int $a;\n    for (" + expression[1] + ", " + expression[1] + "; "
                            + expression[1] + ", " + expression[1] + ", true; "
                            + expression[1] + ", " + expression[1] + ") {\n        $a = 1;\n    }"
            });
        }

        //TODO rstoll TINS-276 conversions and casts
//        List<String[]> castExpressions1 = ExpressionHelper.getCastToTypeExpressions(4);
//        for (String[] castExpression1 : castExpressions1) {
//            List<String[]> castExpressions2 = ExpressionHelper.getCastToTypeExpressions(4 + castExpression1[0].length
//                    () + 1);
//            for (String[] castExpression2 : castExpressions2) {
//                List<String[]> castExpressions3 = ExpressionHelper.getCastToTypeExpressions(
//                        4 + castExpression1[0].length() + 1 + castExpression2[0].length() + 1);
//                for (String[] castExpression3 : castExpressions3) {
//
//                    collection.add(new Object[]{
//                            "for("
//                                    + castExpression1[0] + ";"
//                                    + castExpression2[0] + ";"
//                                    + castExpression3[0]
//                                    + ") $a=1;",
//                            "for ("
//                                    + castExpression1[1] + "; "
//                                    + castExpression2[1] + "; "
//                                    + castExpression3[1]
//                                    + ") {\n    $a = 1;\n}"
//                    });
//                }
//            }
//        }

        return collection;
    }
}
