/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.system;

import ch.tsphp.common.ParserUnitDto;
import ch.tsphp.common.TSPHPAstAdaptor;
import ch.tsphp.tinsphp.common.IParser;
import ch.tsphp.tinsphp.common.ITranslator;
import ch.tsphp.tinsphp.common.issues.EIssueSeverity;
import ch.tsphp.tinsphp.inference_engine.InferenceEngine;
import ch.tsphp.tinsphp.parser.ParserFacade;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPTranslatorInitialiser;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.junit.Test;

import java.util.EnumSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TSPHPTranslatorInitialiserTest
{
    @Test
    public void allFine_DoesNotFindIssues() {
        IParser parser = new ParserFacade();
        ParserUnitDto parserUnitDto = parser.parse("<?php $a = 1;?>");
        CommonTreeNodeStream commonTreeNodeStream =
                new CommonTreeNodeStream(new TSPHPAstAdaptor(), parserUnitDto.compilationUnit);
        commonTreeNodeStream.setTokenStream(parserUnitDto.tokenStream);

        InferenceEngine inferenceEngine = new InferenceEngine();
        inferenceEngine.enrichWithDefinitions(parserUnitDto.compilationUnit, commonTreeNodeStream);
        inferenceEngine.enrichWithReferences(parserUnitDto.compilationUnit, commonTreeNodeStream);
        inferenceEngine.enrichtWithTypes(parserUnitDto.compilationUnit, commonTreeNodeStream);


        //act
        TSPHPTranslatorInitialiser translatorFactory = new TSPHPTranslatorInitialiser();
        ITranslator translator = translatorFactory.build();
        String output = translator.translate(commonTreeNodeStream);


        assertThat(output.replaceAll("\r", ""), is("namespace{\n    ? $a;\n    $a = 1;\n}"));
        assertThat(translator.hasFound(EnumSet.allOf(EIssueSeverity.class)), is(false));
    }
}
