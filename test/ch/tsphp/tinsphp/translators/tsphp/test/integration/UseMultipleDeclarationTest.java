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


import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorInferenceTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class UseMultipleDeclarationTest extends ATranslatorInferenceTest
{

    public UseMultipleDeclarationTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    protected void run() throws RecognitionException {
        result = translator.compilationUnit();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {
                        "<?php use \\a, \\b; ?>",
                        "namespace{\n    use \\a as a, \\b as b;\n}"
                },
                {
                        "<?php use \\a\\a, \\b\\b, \\c as c; ?>",
                        "namespace{\n    use \\a\\a as a, \\b\\b as b, \\c as c;\n}"
                },
                {
                        "<?php use \\a, \\a\\b as c, \\a\\d; ?>",
                        "namespace{\n    use \\a as a, \\a\\b as c, \\a\\d as d;\n}"
                }
        });
    }
}
