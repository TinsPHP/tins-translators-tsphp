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

package ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils;


import ch.tsphp.tinsphp.common.IInferenceEngine;
import ch.tsphp.tinsphp.inference_engine.InferenceEngine;
import ch.tsphp.tinsphp.parser.antlr.TinsPHPParser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Ignore;

@Ignore
public abstract class ATranslatorInferenceTest extends ATest
{

    public ATranslatorInferenceTest(String theTestString, String theExpectedResult) {
        super(theTestString, theExpectedResult);
    }

    @Override
    protected void inferTypes() {
        IInferenceEngine inferenceEngine = new InferenceEngine();
        inferenceEngine.registerErrorLogger(this);

        inferenceEngine.enrichWithDefinitions(ast, commonTreeNodeStream);
        Assert.assertFalse(testString + " failed. found definition exception(s)", inferenceEngine.hasFoundError());

        inferenceEngine.enrichWithReferences(ast, commonTreeNodeStream);
        Assert.assertFalse(testString + " failed. found reference exception(s)", inferenceEngine.hasFoundError());

        inferenceEngine.enrichtWithTypes(ast, commonTreeNodeStream);
        Assert.assertFalse(testString + " failed. found type checking exception(s)", inferenceEngine.hasFoundError());
    }

    @Override
    protected ParserRuleReturnScope parserRun(TinsPHPParser parser) throws RecognitionException {
        return parser.compilationUnit();
    }

    @Override
    protected void run() throws RecognitionException {
        result = translator.compilationUnit();
    }
}
