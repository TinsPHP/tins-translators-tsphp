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
import ch.tsphp.tinsphp.common.ITranslator;
import ch.tsphp.tinsphp.common.config.IInferenceEngineInitialiser;
import ch.tsphp.tinsphp.common.config.ITranslatorInitialiser;
import ch.tsphp.tinsphp.common.scopes.IGlobalNamespaceScope;
import ch.tsphp.tinsphp.common.translation.IPrecedenceHelper;
import ch.tsphp.tinsphp.common.translation.ITempVariableHelper;
import ch.tsphp.tinsphp.common.translation.ITranslatorController;
import ch.tsphp.tinsphp.translators.tsphp.PrecedenceHelper;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPTranslator;
import ch.tsphp.tinsphp.translators.tsphp.TempVariableHelper;
import ch.tsphp.tinsphp.translators.tsphp.TranslatorController;
import org.antlr.stringtemplate.StringTemplateGroup;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The factory which builds the PHP54Translator.
 * <p/>
 * It loads a StringTemplate which is used by the PHP54Translator.
 */
public class HardCodedTSPHPTranslatorInitialiser implements ITranslatorInitialiser
{

    private final ITranslatorController controller;
    private final IGlobalNamespaceScope globalDefaultNamespace;

    private StringTemplateGroup templateGroup;
    private Exception loadingTemplateException;

    public HardCodedTSPHPTranslatorInitialiser(
            ITSPHPAstAdaptor anAstAdaptor, IInferenceEngineInitialiser inferenceEngineInitialiser) {

        IPrecedenceHelper precedenceHelper = new PrecedenceHelper();
        ITempVariableHelper tempVariableHelper = new TempVariableHelper(anAstAdaptor);
        controller = new TranslatorController(precedenceHelper, tempVariableHelper);
        globalDefaultNamespace = inferenceEngineInitialiser.getGlobalDefaultNamespace();

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
        return new TSPHPTranslator(templateGroup, controller, globalDefaultNamespace, loadingTemplateException);
    }

    @Override
    public void reset() {
        //nothing to reset
    }
}