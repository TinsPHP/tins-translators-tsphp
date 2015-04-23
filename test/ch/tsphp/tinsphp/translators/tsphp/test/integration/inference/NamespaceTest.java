/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file NamespaceTest from the translator component of the TSPHP project.
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
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class NamespaceTest extends ATranslatorInferenceTest
{

    public NamespaceTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {"<?php namespace a; ?>", "namespace a{}"},
                {"<?php namespace a\\a\\c; ?>", "namespace a\\a\\c{}"},
                {"<?php namespace{} ?>", "namespace{}"},
                {"<?php namespace  {} ?>", "namespace{}"},
                {"<?php namespace a{} ?>", "namespace a{}"},
                {"<?php namespace a\\b{} ?>", "namespace a\\b{}"},
                {"<?php namespace a\\b{} namespace{} ?>", "namespace a\\b{}\nnamespace{}"},
                {
                        "<?php namespace{} namespace a\\b{}  namespace{}  namespace a{} ?>",
                        "namespace{}\nnamespace a\\b{}\nnamespace{}\nnamespace a{}"
                },
                //without namespace and empty statement
                {"<?php ; ?>", "namespace{\n}"},
                //without namespace, statement and ?>
                {"<?php ", "namespace{}"},
        });
    }
}
