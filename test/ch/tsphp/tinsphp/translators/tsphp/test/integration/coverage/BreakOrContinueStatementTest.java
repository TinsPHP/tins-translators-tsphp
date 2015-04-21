/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file BreakOrContinueStatementTest from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.coverage;


import ch.tsphp.tinsphp.parser.antlr.TinsPHPParser;
import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorParserTest;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BreakOrContinueStatementTest extends ATranslatorParserTest
{

    public BreakOrContinueStatementTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    protected ParserRuleReturnScope parserRun(TinsPHPParser parser) throws RecognitionException {
        return parser.compilationUnit();
    }

    protected void run() throws RecognitionException {
        result = translator.compilationUnit();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        Collection<Object[]> collection = new ArrayList<>();
        //TODO rstoll TINS-397 translator parser based tests
//        collection.addAll(
//                StatementHelper.getStatements("<?php break;", "?>", "namespace{\n    break;", "    ", "\n}"));
//        collection.addAll(
//                StatementHelper.getStatements("<?php continue;", "?>", "namespace{\n    continue;", "    ", "\n}"));
        return collection;
    }
}
