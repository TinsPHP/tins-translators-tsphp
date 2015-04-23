/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file ErrorReportingPHP54TranslatorWalker from the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.antlrmod;

import ch.tsphp.tinsphp.common.issues.EIssueSeverity;
import ch.tsphp.tinsphp.common.issues.IIssueLogger;
import ch.tsphp.tinsphp.common.issues.IIssueReporter;
import ch.tsphp.tinsphp.common.issues.IssueReporterHelper;
import ch.tsphp.tinsphp.common.scopes.IGlobalNamespaceScope;
import ch.tsphp.tinsphp.common.translation.ITranslatorController;
import ch.tsphp.tinsphp.translators.tsphp.antlr.TSPHPTranslatorWalker;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.TreeNodeStream;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.EnumSet;

/**
 * Extends PHP54TranslatorWalker by IErrorReporter.
 */
public class ErrorReportingTSPHPTranslatorWalker extends TSPHPTranslatorWalker implements IIssueReporter
{

    private Collection<IIssueLogger> errorLoggers = new ArrayDeque<>();
    private boolean hasFoundError;

    public ErrorReportingTSPHPTranslatorWalker(
            TreeNodeStream input, ITranslatorController controller, IGlobalNamespaceScope globalNamespaceScope) {
        super(input, controller, globalNamespaceScope);
    }

    @Override
    public boolean hasFound(EnumSet<EIssueSeverity> severities) {
        return hasFoundError;
    }

    @Override
    public void reportError(RecognitionException exception) {
        hasFoundError = true;
        IssueReporterHelper.reportIssue(errorLoggers, exception, "translating to tsphp");
    }

    @Override
    public void registerIssueLogger(IIssueLogger errorLogger) {
        errorLoggers.add(errorLogger);
    }
}
