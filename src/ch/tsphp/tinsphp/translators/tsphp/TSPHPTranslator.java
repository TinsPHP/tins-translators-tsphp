/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file TSPHPTranslator from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp;


import ch.tsphp.common.exceptions.TSPHPException;
import ch.tsphp.tinsphp.common.ITranslator;
import ch.tsphp.tinsphp.common.issues.EIssueSeverity;
import ch.tsphp.tinsphp.common.issues.IIssueLogger;
import ch.tsphp.tinsphp.common.issues.IssueReporterHelper;
import ch.tsphp.tinsphp.common.translation.ITranslatorController;
import ch.tsphp.tinsphp.translators.tsphp.antlrmod.ErrorReportingTSPHPTranslatorWalker;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.stringtemplate.StringTemplateGroup;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.EnumSet;

/**
 * The translator which translates TSPHP to PHP 5.4.
 */
public class TSPHPTranslator implements ITranslator, IIssueLogger
{
    public static final String TRANSLATOR_ID = "tsphp";

    private final StringTemplateGroup templateGroup;
    private final ITranslatorController controller;
    private Collection<IIssueLogger> issueLoggers = new ArrayDeque<>();
    private EnumSet<EIssueSeverity> foundIssues = EnumSet.noneOf(EIssueSeverity.class);
    private Exception loadingTemplateException;

    public TSPHPTranslator(
            StringTemplateGroup theTemplateGroup,
            ITranslatorController theController,
            Exception exception) {
        templateGroup = theTemplateGroup;
        controller = theController;
        loadingTemplateException = exception;
    }

    @Override
    public String translate(TreeNodeStream stream) {
        String translation = null;
        if (loadingTemplateException == null) {
            stream.reset();
            ErrorReportingTSPHPTranslatorWalker translator =
                    new ErrorReportingTSPHPTranslatorWalker(stream, controller);

            for (IIssueLogger logger : issueLoggers) {
                translator.registerIssueLogger(logger);
            }
            translator.registerIssueLogger(this);

            translator.setTemplateLib(templateGroup);

            try {
                translation = translator.compilationUnit().getTemplate().toString();
            } catch (RecognitionException ex) {
                informIssueLoggers(ex);
            }
        } else {
            informIssueLoggers(loadingTemplateException);
        }

        return translation;
    }

    private void informIssueLoggers(Exception ex) {
        foundIssues.add(EIssueSeverity.FatalError);
        for (IIssueLogger logger : issueLoggers) {
            logger.log(new TSPHPException(ex), EIssueSeverity.FatalError);
        }
    }

    @Override
    public boolean hasFound(EnumSet<EIssueSeverity> severities) {
        return IssueReporterHelper.hasFound(foundIssues, severities);
    }

    @Override
    public void registerIssueLogger(IIssueLogger iIssueLogger) {
        issueLoggers.add(iIssueLogger);
    }

    @Override
    public void reset() {
        foundIssues = EnumSet.noneOf(EIssueSeverity.class);
    }

    @Override
    public void log(TSPHPException exception, EIssueSeverity severity) {
        foundIssues.add(severity);
    }
}