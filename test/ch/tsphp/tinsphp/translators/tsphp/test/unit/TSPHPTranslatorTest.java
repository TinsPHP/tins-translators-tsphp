/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit;

import ch.tsphp.common.exceptions.TSPHPException;
import ch.tsphp.tinsphp.common.ITranslator;
import ch.tsphp.tinsphp.common.issues.EIssueSeverity;
import ch.tsphp.tinsphp.common.issues.IIssueLogger;
import ch.tsphp.tinsphp.common.scopes.IGlobalNamespaceScope;
import ch.tsphp.tinsphp.common.translation.ITranslatorController;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPTranslator;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.EnumSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class TSPHPTranslatorTest
{

    @Test
    public void hasFoundError_NoIssueOccurred_ReturnsFalse() {
        //no arrange necessary

        ITranslator translator = createTranslator();
        boolean result = translator.hasFound(EnumSet.allOf(EIssueSeverity.class));

        assertThat(result, is(false));
    }

    @Test
    public void hasFound_PassedTemplateLoadingExceptionAndCheckForFatal_ReturnsTrue() {
        Exception exception = mock(Exception.class);

        ITranslator translator = createTranslator(exception);
        translator.translate(mock(TreeNodeStream.class));
        boolean result = translator.hasFound(EnumSet.of(EIssueSeverity.FatalError));

        assertThat(result, is(true));
    }

    @Test
    public void translate_PassedTemplateLoadingException_NoInteractionWithStream() {
        Exception exception = mock(Exception.class);
        TreeNodeStream treeNodeStream = mock(TreeNodeStream.class);

        ITranslator translator = createTranslator(exception);
        translator.translate(treeNodeStream);

        verifyZeroInteractions(treeNodeStream);
    }

    @Test
    public void translate_PassedTemplateLoadingException_InformsRegisteredIssueLoggers() {
        Exception exception = mock(Exception.class);
        TreeNodeStream treeNodeStream = mock(TreeNodeStream.class);
        IIssueLogger logger1 = mock(IIssueLogger.class);
        IIssueLogger logger2 = mock(IIssueLogger.class);

        ITranslator translator = createTranslator(exception);
        translator.registerIssueLogger(logger1);
        translator.registerIssueLogger(logger2);
        translator.translate(treeNodeStream);

        ArgumentCaptor<TSPHPException> captor = ArgumentCaptor.forClass(TSPHPException.class);
        verify(logger1).log(captor.capture(), eq(EIssueSeverity.FatalError));
        assertThat(captor.getValue().getCause(), is((Throwable) exception));
        captor = ArgumentCaptor.forClass(TSPHPException.class);
        verify(logger2).log(captor.capture(), eq(EIssueSeverity.FatalError));
        assertThat(captor.getValue().getCause(), is((Throwable) exception));
    }

    @Test
    public void reset_HasIssueFoundBefore_HasNoIssueFoundAgain() {
        Exception exception = mock(Exception.class);

        ITranslator translator = createTranslator(exception);
        translator.translate(mock(TreeNodeStream.class));
        assertThat(translator.hasFound(EnumSet.allOf(EIssueSeverity.class)), is(true));
        translator.reset();

        assertThat(translator.hasFound(EnumSet.allOf(EIssueSeverity.class)), is(false));
    }

    private ITranslator createTranslator() {
        return createTranslator(mock(Exception.class));
    }

    private ITranslator createTranslator(Exception exception) {
        return createTranslator(
                mock(StringTemplateGroup.class),
                mock(ITranslatorController.class),
                mock(IGlobalNamespaceScope.class),
                exception);
    }

    protected ITranslator createTranslator(
            StringTemplateGroup templateGroup,
            ITranslatorController controller,
            IGlobalNamespaceScope globalNamespaceScope, Exception exception) {
        return new TSPHPTranslator(templateGroup, controller, globalNamespaceScope, exception);
    }

}
