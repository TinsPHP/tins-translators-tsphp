/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.system;

import ch.tsphp.common.AstHelper;
import ch.tsphp.common.IAstHelper;
import ch.tsphp.common.ITSPHPAstAdaptor;
import ch.tsphp.common.ParserUnitDto;
import ch.tsphp.common.TSPHPAstAdaptor;
import ch.tsphp.tinsphp.common.IInferenceEngine;
import ch.tsphp.tinsphp.common.IParser;
import ch.tsphp.tinsphp.common.ITranslator;
import ch.tsphp.tinsphp.common.config.IInferenceEngineInitialiser;
import ch.tsphp.tinsphp.common.config.ITranslatorInitialiser;
import ch.tsphp.tinsphp.common.issues.EIssueSeverity;
import ch.tsphp.tinsphp.core.config.HardCodedCoreInitialiser;
import ch.tsphp.tinsphp.inference_engine.config.HardCodedInferenceEngineInitialiser;
import ch.tsphp.tinsphp.parser.ParserFacade;
import ch.tsphp.tinsphp.symbols.config.HardCodedSymbolsInitialiser;
import ch.tsphp.tinsphp.translators.tsphp.config.HardCodedTSPHPTranslatorInitialiser;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.junit.Test;

import java.util.EnumSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HardCodedTSPHPTranslatorInitialiserTest
{
    @Test
    public void allFine_DoesNotFindIssues() {
        IParser parser = new ParserFacade();
        ParserUnitDto parserUnitDto = parser.parse("<?php $a = 1;?>");
        CommonTreeNodeStream commonTreeNodeStream =
                new CommonTreeNodeStream(new TSPHPAstAdaptor(), parserUnitDto.compilationUnit);
        commonTreeNodeStream.setTokenStream(parserUnitDto.tokenStream);

        ITSPHPAstAdaptor astAdaptor = new TSPHPAstAdaptor();
        IAstHelper astHelper = new AstHelper(astAdaptor);

        IInferenceEngineInitialiser inferenceInitialiser = createInferenceInitialiser(astAdaptor, astHelper);
        IInferenceEngine inferenceEngine = inferenceInitialiser.getEngine();
        inferenceEngine.enrichWithDefinitions(parserUnitDto.compilationUnit, commonTreeNodeStream);
        inferenceEngine.enrichWithReferences(parserUnitDto.compilationUnit, commonTreeNodeStream);
        inferenceEngine.solveMethodSymbolConstraints();
        inferenceEngine.solveGlobalDefaultNamespaceConstraints();

        //act
        ITranslatorInitialiser translatorInitialiser = createTranslatorInitialiser(astAdaptor, inferenceInitialiser);
        ITranslator translator = translatorInitialiser.build();
        String output = translator.translate(commonTreeNodeStream);


        assertThat(output.replaceAll("\r", ""), is("namespace{\n    ? $a;\n    $a = 1;\n}"));
        assertThat(translator.hasFound(EnumSet.allOf(EIssueSeverity.class)), is(false));
    }

    private IInferenceEngineInitialiser createInferenceInitialiser(ITSPHPAstAdaptor astAdaptor, IAstHelper astHelper) {

        HardCodedSymbolsInitialiser symbolsInitialiser = new HardCodedSymbolsInitialiser();
        HardCodedCoreInitialiser coreInitialiser = new HardCodedCoreInitialiser(astHelper, symbolsInitialiser);

        return new HardCodedInferenceEngineInitialiser(astAdaptor, astHelper, symbolsInitialiser, coreInitialiser);
    }

    protected ITranslatorInitialiser createTranslatorInitialiser(
            ITSPHPAstAdaptor astAdaptor, IInferenceEngineInitialiser inferenceInitialiser) {
        return new HardCodedTSPHPTranslatorInitialiser(astAdaptor, inferenceInitialiser);
    }
}
