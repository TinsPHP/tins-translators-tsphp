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
import ch.tsphp.common.AstHelperRegistry;
import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.ITSPHPAstAdaptor;
import ch.tsphp.common.TSPHPAstAdaptor;
import ch.tsphp.common.exceptions.TSPHPException;
import ch.tsphp.parser.common.ANTLRNoCaseStringStream;
import ch.tsphp.tinsphp.common.issues.EIssueSeverity;
import ch.tsphp.tinsphp.common.issues.IIssueLogger;
import ch.tsphp.tinsphp.parser.antlr.TinsPHPParser;
import ch.tsphp.tinsphp.parser.antlrmod.ErrorReportingTinsPHPLexer;
import ch.tsphp.tinsphp.parser.antlrmod.ErrorReportingTinsPHPParser;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPPrecedenceHelper;
import ch.tsphp.tinsphp.translators.tsphp.TempVariableHelper;
import ch.tsphp.tinsphp.translators.tsphp.antlrmod.ErrorReportingTSPHPTranslatorWalker;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.ParserRuleReturnScope;
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

@Ignore
public abstract class ATest implements IIssueLogger
{

    protected String testString;
    protected String expectedResult;
    protected ITSPHPAst ast;
    protected CommonTreeNodeStream commonTreeNodeStream;
    protected ErrorReportingTSPHPTranslatorWalker translator;
    protected TreeRuleReturnScope result;
    protected ITSPHPAstAdaptor astAdaptor;

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
        System.err.println(exception.getMessage());
    }

    public void parse() throws RecognitionException {

        astAdaptor = new TSPHPAstAdaptor();
        AstHelperRegistry.set(new AstHelper(astAdaptor));

        CharStream stream = new ANTLRNoCaseStringStream(testString);
        ErrorReportingTinsPHPLexer lexer = new ErrorReportingTinsPHPLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        ErrorReportingTinsPHPParser parser = new ErrorReportingTinsPHPParser(tokens);
        parser.setTreeAdaptor(astAdaptor);
        parser.registerIssueLogger(new WriteExceptionToConsole());

        ParserRuleReturnScope parserResult = parserRun(parser);
        ast = (ITSPHPAst) parserResult.getTree();

        Assert.assertFalse(testString.replaceAll("\n", " ") + " failed - lexer throw exception",
                lexer.hasFound(EnumSet.allOf(EIssueSeverity.class)));
        Assert.assertFalse(testString.replaceAll("\n", " ") + " failed - parser throw exception",
                parser.hasFound(EnumSet.allOf(EIssueSeverity.class)));

        commonTreeNodeStream = new CommonTreeNodeStream(astAdaptor, ast);
        commonTreeNodeStream.setTokenStream(parser.getTokenStream());
    }

    protected void inferTypes() {
    }

    public void translate() throws IOException, RecognitionException {
        parse();

        inferTypes();

        // LOAD TEMPLATES (via classpath)
        URL url = ClassLoader.getSystemResource("TSPHP.stg");
        FileReader fr = new FileReader(url.getFile());
        StringTemplateGroup templates = new StringTemplateGroup(fr);
        fr.close();

        commonTreeNodeStream.reset();
        translator = new ErrorReportingTSPHPTranslatorWalker(
                commonTreeNodeStream, new TSPHPPrecedenceHelper(), new TempVariableHelper(astAdaptor));
        translator.registerIssueLogger(this);
        translator.setTemplateLib(templates);

        run();

        check();
    }

    protected ParserRuleReturnScope parserRun(TinsPHPParser parser) throws RecognitionException {
        return parser.statement();
    }

    protected void run() throws RecognitionException {
        result = translator.statement();
    }
}
