/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file ATest from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils;

import ch.tsphp.common.AstHelper;
import ch.tsphp.common.IAstHelper;
import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.ITSPHPAstAdaptor;
import ch.tsphp.common.ParserUnitDto;
import ch.tsphp.common.TSPHPAstAdaptor;
import ch.tsphp.common.exceptions.TSPHPException;
import ch.tsphp.tinsphp.common.IParser;
import ch.tsphp.tinsphp.common.config.ICoreInitialiser;
import ch.tsphp.tinsphp.common.config.IInferenceEngineInitialiser;
import ch.tsphp.tinsphp.common.config.IParserInitialiser;
import ch.tsphp.tinsphp.common.config.ISymbolsInitialiser;
import ch.tsphp.tinsphp.common.issues.EIssueSeverity;
import ch.tsphp.tinsphp.common.issues.IIssueLogger;
import ch.tsphp.tinsphp.common.translation.IDtoCreator;
import ch.tsphp.tinsphp.common.translation.ITranslatorController;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.core.config.HardCodedCoreInitialiser;
import ch.tsphp.tinsphp.inference_engine.config.HardCodedInferenceEngineInitialiser;
import ch.tsphp.tinsphp.parser.config.HardCodedParserInitialiser;
import ch.tsphp.tinsphp.symbols.config.HardCodedSymbolsInitialiser;
import ch.tsphp.tinsphp.translators.tsphp.DtoCreator;
import ch.tsphp.tinsphp.translators.tsphp.IOperatorHelper;
import ch.tsphp.tinsphp.translators.tsphp.IRuntimeCheckProvider;
import ch.tsphp.tinsphp.translators.tsphp.ITypeTransformer;
import ch.tsphp.tinsphp.translators.tsphp.ITypeVariableTransformer;
import ch.tsphp.tinsphp.translators.tsphp.PhpPlusOperatorHelper;
import ch.tsphp.tinsphp.translators.tsphp.PhpPlusRuntimeCheckProvider;
import ch.tsphp.tinsphp.translators.tsphp.PhpPlusTypeTransformer;
import ch.tsphp.tinsphp.translators.tsphp.PhpPlusTypeVariableTransformer;
import ch.tsphp.tinsphp.translators.tsphp.PrecedenceHelper;
import ch.tsphp.tinsphp.translators.tsphp.TempVariableHelper;
import ch.tsphp.tinsphp.translators.tsphp.TranslatorController;
import ch.tsphp.tinsphp.translators.tsphp.antlrmod.ErrorReportingTSPHPTranslatorWalker;
import ch.tsphp.tinsphp.translators.tsphp.issues.HardCodedOutputIssueMessageProvider;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.TreeRuleReturnScope;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.junit.Assert;
import org.junit.Ignore;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertFalse;

@Ignore
public abstract class ATest implements IIssueLogger
{

    public static int numberOfThreads = 1;

    protected String testString;
    protected String expectedResult;
    protected ITSPHPAst ast;
    protected CommonTreeNodeStream commonTreeNodeStream;
    protected ErrorReportingTSPHPTranslatorWalker translator;
    protected TreeRuleReturnScope result;
    protected ITSPHPAstAdaptor astAdaptor;
    protected IParser parser;
    protected ITranslatorController controller;
    protected IInferenceEngineInitialiser inferenceEngineInitialiser;
    protected ISymbolsInitialiser symbolsInitialiser;
    protected ICoreInitialiser coreInitialiser;

    public ATest(String theTestString, String theExpectedResult) {
        testString = theTestString;
        expectedResult = theExpectedResult;
    }

    public void check() {
        Assert.assertFalse(testString + " failed. found translator exception(s). See output.",
                translator.hasFound(EnumSet.allOf(EIssueSeverity.class)));

        Assert.assertEquals(testString + " failed.", expectedResult,
                result.getTemplate().toString().replaceAll("\r", ""));
    }

    @Override
    public void log(TSPHPException exception, EIssueSeverity severity) {
        System.err.println("[" + severity.name() + "] " + exception.getMessage());
    }

    public void translate() throws IOException, RecognitionException {
        astAdaptor = new TSPHPAstAdaptor();
        IParserInitialiser parserInitialiser = new HardCodedParserInitialiser(astAdaptor);
        parser = parserInitialiser.getParser();
        parser.registerIssueLogger(new WriteExceptionToConsole());
        ParserUnitDto parserUnit = parser.parse(testString);

        checkNoIssuesDuringParsing();

        ast = parserUnit.compilationUnit;
        commonTreeNodeStream = new CommonTreeNodeStream(astAdaptor, ast);
        commonTreeNodeStream.setTokenStream(parserUnit.tokenStream);

        IAstHelper astHelper = new AstHelper(astAdaptor);
        symbolsInitialiser = createSymbolsInitialiser();
        coreInitialiser = createCoreInitialiser(astHelper, symbolsInitialiser);
        inferenceEngineInitialiser = createInferenceInitialiser(
                astAdaptor, astHelper, symbolsInitialiser, coreInitialiser,
                Executors.newFixedThreadPool(numberOfThreads));

        inferTypes();

        // LOAD TEMPLATES (via classpath)
        URL url = ClassLoader.getSystemResource("TSPHP.stg");
        FileReader fr = new FileReader(url.getFile());
        StringTemplateGroup templates = new StringTemplateGroup(fr);
        fr.close();

        commonTreeNodeStream.reset();

        ITypeHelper typeHelper = symbolsInitialiser.getTypeHelper();
        TempVariableHelper tempVariableHelper = new TempVariableHelper(astAdaptor);
        ITypeTransformer typeTransformer = createTypeTransformer();
        IRuntimeCheckProvider runtimeCheckProvider = createRuntimeCheckProvider(
                typeHelper, typeTransformer, tempVariableHelper);
        IOperatorHelper operatorHelper = createOperatorHelper(runtimeCheckProvider, typeTransformer);
        ITypeVariableTransformer typeVariableMapper = createTypeVariableMapper(typeTransformer);

        IDtoCreator dtoCreator = new DtoCreator(
                tempVariableHelper, typeTransformer, typeVariableMapper, runtimeCheckProvider);

        controller = new TranslatorController(
                astAdaptor,
                symbolsInitialiser.getSymbolFactory(),
                new PrecedenceHelper(),
                tempVariableHelper,
                operatorHelper,
                dtoCreator,
                runtimeCheckProvider,
                new HardCodedOutputIssueMessageProvider(),
                typeTransformer);
        controller.setMethodSymbols(inferenceEngineInitialiser.getMethodSymbols());
        translator = new ErrorReportingTSPHPTranslatorWalker(
                commonTreeNodeStream, controller, inferenceEngineInitialiser.getGlobalDefaultNamespace());
        translator.registerIssueLogger(this);
        translator.setTemplateLib(templates);

        run();

        check();
    }

    protected ITypeVariableTransformer createTypeVariableMapper(ITypeTransformer typeTransformer) {
        return new PhpPlusTypeVariableTransformer(typeTransformer);
    }

    protected IRuntimeCheckProvider createRuntimeCheckProvider(
            ITypeHelper typeHelper, ITypeTransformer typeTransformer, TempVariableHelper tempVariableHelper) {
        return new PhpPlusRuntimeCheckProvider(typeHelper);
    }

    protected ITypeTransformer createTypeTransformer() {
        return new PhpPlusTypeTransformer();
    }

    protected ICoreInitialiser createCoreInitialiser(IAstHelper astHelper, ISymbolsInitialiser symbolsInitialiser) {
        return new HardCodedCoreInitialiser(astHelper, symbolsInitialiser);
    }

    protected IOperatorHelper createOperatorHelper(
            IRuntimeCheckProvider runtimeCheckProvider, ITypeTransformer typeTransformer) {
        return new PhpPlusOperatorHelper();
    }

    protected void checkNoIssuesDuringParsing() {
        assertFalse(testString.replaceAll("\n", " ") + " failed - parser throw exception",
                parser.hasFound(EnumSet.allOf(EIssueSeverity.class)));
    }

    protected void inferTypes() {
    }

    protected void run() throws RecognitionException {
        result = translator.statement();
    }

    protected IInferenceEngineInitialiser createInferenceInitialiser(
            ITSPHPAstAdaptor astAdaptor,
            IAstHelper astHelper,
            ISymbolsInitialiser theSymbolsInitialiser,
            ICoreInitialiser theCoreInitialiser,
            ExecutorService theExecutorService) {

        return new HardCodedInferenceEngineInitialiser(
                astAdaptor, astHelper, theSymbolsInitialiser, theCoreInitialiser, theExecutorService);
    }

    protected ISymbolsInitialiser createSymbolsInitialiser() {
        return new HardCodedSymbolsInitialiser();
    }
}
