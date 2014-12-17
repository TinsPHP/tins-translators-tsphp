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

package ch.tsphp.tinsphp.translators.tsphp.test.integration.inference;


import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorInferenceTest;
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
public class VariableDeclarationTest extends ATranslatorInferenceTest
{

    public VariableDeclarationTest(String testString, String expectedResult) {
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
                {"<?php $a=1;", "namespace{\n    ? $a;\n    $a = 1;\n}"},
                //TODO rstoll TINS-254 translator procedural - control structures
//                {"<?php if(true){$a=1;}", "? $a;\nif(true) {\n    $a = 1;\n}"},
        }));

        //TODO rstoll TINS-255 translator procedural - expressions
//        List<String[]> expressions = ExpressionHelper.getAllExpressions(7);
//        for (String[] expression : expressions) {
//            collection.add(new Object[]{
//                    "int $a=" + expression[0] + ";",
//                    "$a = " + expression[1] + ";"
//            });
//        }
        return collection;
    }
}
