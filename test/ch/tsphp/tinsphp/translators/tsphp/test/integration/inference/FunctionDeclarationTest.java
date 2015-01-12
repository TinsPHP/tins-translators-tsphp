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


import ch.tsphp.tinsphp.common.IInferenceEngine;
import ch.tsphp.tinsphp.common.issues.EIssueSeverity;
import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorInferenceTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

@RunWith(Parameterized.class)
public class FunctionDeclarationTest extends ATranslatorInferenceTest
{

    public FunctionDeclarationTest(String testString, String expectedResult) {
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
                {"<?php function foo(){} ?>", "namespace{\n    function ? foo() {\n        return null;\n    }\n}"},
                {
                        "<?php function foo(){if(true){return 1;}} ?>",
                        "namespace{" +
                                "\n    function ? foo() {"
                                + "\n        if (true) {"
                                + "\n            return 1;"
                                + "\n        }"
                                + "\n        return null;"
                                + "\n    }"
                                + "\n}"
                },
        }));

        return collection;
    }

    @Override
    protected void checkReferencePhase(IInferenceEngine inferenceEngine) {
        Assert.assertTrue(testString + " failed. no return / partial return from function expected but not reported",
                inferenceEngine.hasFound(EnumSet.allOf(EIssueSeverity.class)));
    }

    @Override
    protected void checkInferencePhase(IInferenceEngine inferenceEngine) {
        Assert.assertTrue(testString + " failed. no return / partial return from function expected but not reported",
                inferenceEngine.hasFound(EnumSet.allOf(EIssueSeverity.class)));
    }
}