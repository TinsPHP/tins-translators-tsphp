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


import ch.tsphp.common.AstHelper;
import ch.tsphp.common.IAstHelper;
import ch.tsphp.common.ITSPHPAstAdaptor;
import ch.tsphp.tinsphp.common.IInferenceEngine;
import ch.tsphp.tinsphp.common.config.IInferenceEngineInitialiser;
import ch.tsphp.tinsphp.common.issues.EIssueSeverity;
import ch.tsphp.tinsphp.core.config.HardCodedCoreInitialiser;
import ch.tsphp.tinsphp.inference_engine.config.HardCodedInferenceEngineInitialiser;
import ch.tsphp.tinsphp.parser.antlr.TinsPHPParser;
import ch.tsphp.tinsphp.symbols.config.HardCodedSymbolsInitialiser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Ignore;

import java.util.EnumSet;

@Ignore
public abstract class ATranslatorInferenceTest extends ATest
{

    public ATranslatorInferenceTest(String theTestString, String theExpectedResult) {
        super(theTestString, theExpectedResult);
    }

    @Override
    protected void inferTypes() {
        IAstHelper astHelper = new AstHelper(astAdaptor);
        IInferenceEngineInitialiser inferenceEngineInitialiser = createInferenceInitialiser(astAdaptor, astHelper);

        IInferenceEngine inferenceEngine = inferenceEngineInitialiser.getEngine();
        inferenceEngine.registerIssueLogger(this);

        inferenceEngine.enrichWithDefinitions(ast, commonTreeNodeStream);
        checkDefinitionPhase(inferenceEngine);

        inferenceEngine.enrichWithReferences(ast, commonTreeNodeStream);
        checkReferencePhase(inferenceEngine);

        inferenceEngine.solveMethodSymbolConstraints();
        inferenceEngine.solveGlobalDefaultNamespaceConstraints();
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
    protected ParserRuleReturnScope parserRun(TinsPHPParser parser) throws RecognitionException {
        return parser.compilationUnit();
    }

    @Override
    protected void run() throws RecognitionException {
        result = translator.compilationUnit();
    }

    private IInferenceEngineInitialiser createInferenceInitialiser(ITSPHPAstAdaptor astAdaptor, IAstHelper astHelper) {

        HardCodedSymbolsInitialiser symbolsInitialiser = new HardCodedSymbolsInitialiser();
        HardCodedCoreInitialiser coreInitialiser = new HardCodedCoreInitialiser(astHelper, symbolsInitialiser);

        return new HardCodedInferenceEngineInitialiser(astAdaptor, astHelper, symbolsInitialiser, coreInitialiser);
    }
}
