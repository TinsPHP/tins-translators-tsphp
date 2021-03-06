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
import ch.tsphp.tinsphp.common.issues.EIssueSeverity;
import org.antlr.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Ignore;

import java.util.EnumSet;

@Ignore
public abstract class ATranslatorTest extends ATest
{

    public ATranslatorTest(String theTestString, String theExpectedResult) {
        super(theTestString, theExpectedResult);
    }

    @Override
    protected void inferTypes() {
        IInferenceEngine inferenceEngine = inferenceEngineInitialiser.getEngine();
        inferenceEngine.registerIssueLogger(this);

        inferenceEngine.enrichWithDefinitions(ast, commonTreeNodeStream);
        checkDefinitionPhase(inferenceEngine);

        inferenceEngine.enrichWithReferences(ast, commonTreeNodeStream);
        checkReferencePhase(inferenceEngine);

        inferenceEngine.solveConstraints();
        checkInferencePhase(inferenceEngine);
    }

    protected void checkDefinitionPhase(IInferenceEngine inferenceEngine) {
        Assert.assertFalse(testString + " failed. found issue(s) during the definition phase",
                inferenceEngine.hasFound(EnumSet.allOf(EIssueSeverity.class)));
    }

    protected void checkReferencePhase(IInferenceEngine inferenceEngine) {
        Assert.assertFalse(testString + " failed. found issue(s) during the reference phase",
                inferenceEngine.hasFound(EnumSet.allOf(EIssueSeverity.class)));
    }

    protected void checkInferencePhase(IInferenceEngine inferenceEngine) {
        Assert.assertFalse(testString + " failed. found issue(s) during the inference phase",
                inferenceEngine.hasFound(EnumSet.allOf(EIssueSeverity.class)));
    }

    @Override
    protected void run() throws RecognitionException {
        result = translator.compilationUnit();
    }

}
