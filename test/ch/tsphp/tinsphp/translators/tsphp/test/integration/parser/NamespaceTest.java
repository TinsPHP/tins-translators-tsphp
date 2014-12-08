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

package ch.tsphp.tinsphp.translators.tsphp.test.integration.parser;

import ch.tsphp.tinsphp.parser.antlr.TinsPHPParser;
import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorParserTest;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class NamespaceTest extends ATranslatorParserTest
{

    public NamespaceTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Override
    public ParserRuleReturnScope parserRun(TinsPHPParser parser) throws RecognitionException {
        return parser.compilationUnit();
    }

    @Override
    public void run() throws RecognitionException {
        result = translator.compilationUnit();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {"namespace a;", "namespace a{}"},
                {"namespace a\\a\\c;", "namespace a\\a\\c{}"},
                {"namespace{}", "namespace{}"},
                {"namespace  {}", "namespace{}"},
                {"namespace a{}", "namespace a{}"},
                {"namespace a\\b{}", "namespace a\\b{}"},
                {"namespace a\\b{} namespace{}", "namespace a\\b{}\nnamespace{}"},
                {
                        " namespace{} namespace a\\b{}  namespace{}  namespace a{}",
                        "namespace{}\nnamespace a\\b{}\nnamespace{}\nnamespace a{}"
                },
        });
    }
}
