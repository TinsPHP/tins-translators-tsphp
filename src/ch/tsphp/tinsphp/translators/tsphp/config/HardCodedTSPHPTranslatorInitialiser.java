/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file PHP54TranslatorFactory from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.config;

import ch.tsphp.common.ITSPHPAstAdaptor;
import ch.tsphp.common.exceptions.TSPHPException;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.ITranslator;
import ch.tsphp.tinsphp.common.config.ICoreInitialiser;
import ch.tsphp.tinsphp.common.config.IInferenceEngineInitialiser;
import ch.tsphp.tinsphp.common.config.ISymbolsInitialiser;
import ch.tsphp.tinsphp.common.config.ITranslatorInitialiser;
import ch.tsphp.tinsphp.common.symbols.ISymbolFactory;
import ch.tsphp.tinsphp.common.translation.IDtoCreator;
import ch.tsphp.tinsphp.common.translation.ITranslatorController;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.translators.tsphp.DtoCreator;
import ch.tsphp.tinsphp.translators.tsphp.INameTransformer;
import ch.tsphp.tinsphp.translators.tsphp.IOperatorHelper;
import ch.tsphp.tinsphp.translators.tsphp.IPrecedenceHelper;
import ch.tsphp.tinsphp.translators.tsphp.ITempVariableHelper;
import ch.tsphp.tinsphp.translators.tsphp.PrecedenceHelper;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPNameTransformer;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPOperatorHelper;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPTranslator;
import ch.tsphp.tinsphp.translators.tsphp.TempVariableHelper;
import ch.tsphp.tinsphp.translators.tsphp.TranslatorController;
import org.antlr.stringtemplate.StringTemplateGroup;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * The factory which builds the PHP54Translator.
 * <p/>
 * It loads a StringTemplate which is used by the PHP54Translator.
 */
public class HardCodedTSPHPTranslatorInitialiser implements ITranslatorInitialiser
{

    private final ITranslatorController controller;
    private final IInferenceEngineInitialiser inferenceEngineInitialiser;

    private StringTemplateGroup templateGroup;
    private Exception loadingTemplateException;

    public HardCodedTSPHPTranslatorInitialiser(
            ITSPHPAstAdaptor anAstAdaptor,
            ISymbolsInitialiser theSymbolsInitialiser,
            ICoreInitialiser theCoreInitialiser,
            IInferenceEngineInitialiser theInferenceEngineInitialiser) {
        inferenceEngineInitialiser = theInferenceEngineInitialiser;

        IPrecedenceHelper precedenceHelper = new PrecedenceHelper();
        ITempVariableHelper tempVariableHelper = new TempVariableHelper(anAstAdaptor);

        Map<String, ITypeSymbol> primitiveTypes = theCoreInitialiser.getCore().getPrimitiveTypes();
        ITypeHelper typeHelper = theSymbolsInitialiser.getTypeHelper();
        ISymbolFactory symbolFactory = theSymbolsInitialiser.getSymbolFactory();
        INameTransformer nameTransformer = new TSPHPNameTransformer(symbolFactory, typeHelper, primitiveTypes);
        IOperatorHelper operatorHelper = new TSPHPOperatorHelper(typeHelper, primitiveTypes, nameTransformer);
        IDtoCreator dtoCreator = new DtoCreator(tempVariableHelper, nameTransformer);
        controller = new TranslatorController(
                precedenceHelper,
                tempVariableHelper,
                operatorHelper,
                dtoCreator,
                nameTransformer);

        loadStringTemplate();
    }

    private void loadStringTemplate() {
        InputStreamReader streamReader = null;
        try {
            // LOAD TEMPLATES (via classpath)
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            final InputStream inputStream = classLoader.getResourceAsStream("TSPHP.stg");
            if (inputStream != null) {
                streamReader = new InputStreamReader(inputStream);
                templateGroup = new StringTemplateGroup(streamReader);
                streamReader.close();
            } else {
                loadingTemplateException = new TSPHPException("TSPHP.stg could not be resolved");
            }
        } catch (IOException ex) {
            loadingTemplateException = ex;
        } finally {
            try {
                if (streamReader != null) {
                    streamReader.close();
                }
            } catch (IOException ex) {
                //no further exception handling needed
            }
        }
    }

    @Override
    public ITranslator build() {
        return new TSPHPTranslator(
                templateGroup,
                controller,
                inferenceEngineInitialiser,
                loadingTemplateException);
    }

    @Override
    public void reset() {
        //nothing to reset
    }
}
