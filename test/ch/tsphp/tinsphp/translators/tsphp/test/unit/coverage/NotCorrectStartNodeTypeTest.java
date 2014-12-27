/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file NotCorrectStartNodeTypeTest from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit.coverage;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.tinsphp.symbols.gen.TokenTypes;
import ch.tsphp.tinsphp.translators.tsphp.antlrmod.ErrorReportingTSPHPTranslatorWalker;
import ch.tsphp.tinsphp.translators.tsphp.test.unit.testutils.AWalkerTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.ArgumentCaptor;
import org.mockito.exceptions.base.MockitoAssertionError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(Parameterized.class)
public class NotCorrectStartNodeTypeTest extends AWalkerTest
{
    private String methodName;
    private int tokenType;

    public NotCorrectStartNodeTypeTest(String theMethodName, int theTokenType) {
        methodName = theMethodName;
        tokenType = theTokenType;
    }

    @Test
    public void standard_reportRecognitionException()
            throws RecognitionException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ITSPHPAst ast = createAst(tokenType);

        ErrorReportingTSPHPTranslatorWalker walker = spy(createWalker(ast));
        Method method = ErrorReportingTSPHPTranslatorWalker.class.getMethod(methodName);
        method.invoke(walker);

        try {
            ArgumentCaptor<RecognitionException> captor = ArgumentCaptor.forClass(RecognitionException.class);
            verify(walker).reportError(captor.capture());
            assertThat(methodName + " - failed. Wrong token type", captor.getValue().token.getType(), is(tokenType));
        } catch (MockitoAssertionError e) {
            fail(methodName + " failed - verify caused exception:\n" + e.getClass().getName() + e.getMessage());
        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                //TODO rstoll uncomment if supported
//                {"abstractConstructDeclaration", TokenTypes.Else},
//                {"abstractMethodDeclaration", TokenTypes.Else},
//                {"abstractMethodModifier", TokenTypes.Else},
//                {"abstractToken", TokenTypes.Else},
//                {"accessModifier", TokenTypes.Else},
//                {"accessModifierWithoutPrivate", TokenTypes.Else},
//                {"actualParameters", TokenTypes.Else},
                {"allTypes", TokenTypes.Else},
                {"allTypesOrUnknown", TokenTypes.Else},
                {"arrayKeyValue", TokenTypes.Else},
//                {"arrayType", TokenTypes.Else},
                {"atom", TokenTypes.Else},
                {"binaryOperator", TokenTypes.Else},
                {"block", TokenTypes.Else},
                {"blockConditional", TokenTypes.Else},
//                {"caseLabel", TokenTypes.Else},
//                {"castOperator", TokenTypes.Else},
//                {"catchBlock", TokenTypes.Else},
//                {"classBody", TokenTypes.Else},
//                {"classBodyDefinition", TokenTypes.Else},
//                {"classDeclaration", TokenTypes.Else},
//                {"classInterfaceType", TokenTypes.Else},
//                {"classModifier", TokenTypes.Else},
//                {"classModifierNames", TokenTypes.Else},
//                {"classStaticAccess", TokenTypes.Else},
                {"compilationUnit", TokenTypes.Else},
                {"constDeclaration", TokenTypes.Else},
                {"constDeclarationList", TokenTypes.Else},
//                {"constructDeclaration", TokenTypes.Else},
//                {"constructDestructModifier", TokenTypes.Else},
                {"definition", TokenTypes.Else},
//                {"division", TokenTypes.Else},
//                {"doWhileLoop", TokenTypes.Else},
//                {"exit", TokenTypes.Else},
                {"expression", TokenTypes.Else},
//                {"extendsDeclaration", TokenTypes.Else},
//                {"fieldDeclaration", TokenTypes.Else},
//                {"finalToken", TokenTypes.Else},
//                {"foreachLoop", TokenTypes.Else},
//                {"forLoop", TokenTypes.Else},
                {"formalParameters", TokenTypes.Else},
//                {"functionCall", TokenTypes.Else},
                {"functionDeclaration", TokenTypes.Else},
                {"ifCondition", TokenTypes.Else},
//                {"implementsDeclaration", TokenTypes.Else},
                {"instruction", TokenTypes.Else},
//                {"interfaceBody", TokenTypes.Else},
//                {"interfaceBodyDefinition", TokenTypes.Else},
//                {"interfaceConstructDeclaration", TokenTypes.Else},
//                {"interfaceDeclaration", TokenTypes.Else},
//                {"interfaceMethodDeclaration", TokenTypes.Else},
                {"localVariableDeclarationList", TokenTypes.Else},
                {"localVariableDeclaration", TokenTypes.Else},
//                {"methodCall", TokenTypes.Else},
//                {"methodCallSelfOrParent", TokenTypes.Else},
//                {"methodCallStatic", TokenTypes.Else},
//                {"methodDeclaration", TokenTypes.Else},
//                {"methodModifier", TokenTypes.Else},
                {"namespace", TokenTypes.Else},
                {"namespaceBody", TokenTypes.Else},
//                {"newOperator", TokenTypes.Else},
                //reports currently two exceptions, one binary exception
//                {"operator", TokenTypes.Else},
                {"paramDeclaration", TokenTypes.Else},
//                {"postFixExpression", TokenTypes.Else},
                {"primitiveAtomWithConstant", TokenTypes.Else},
                {"primitiveTypes", TokenTypes.Else},
//                {"primitiveTypesWithoutArray", TokenTypes.Else},
                {"returnTypesOrUnknown", TokenTypes.Else},
//                {"scalarAndResource", TokenTypes.Else},
                {"scalarTypes", TokenTypes.Else},
                {"scalarTypesOrUnknown", TokenTypes.Else},
                {"statement",  TokenTypes.Else},
//                {"staticAccess", TokenTypes.Else},
//                {"staticToken", TokenTypes.Else},
//                {"switchCondition", TokenTypes.Else},
//                {"switchContent", TokenTypes.Else},
//                {"tryCatch",  TokenTypes.Else},
                {"typeModifier", TokenTypes.Else},
//                {"unaryPostOperator", TokenTypes.Else},
//                {"unaryPreOperator", TokenTypes.Else},
                {"unaryPrimitiveAtom", TokenTypes.Else},
                {"useDeclaration", TokenTypes.Else},
                {"useDeclarationList", TokenTypes.Else},
//                {"variableDeclaration", TokenTypes.Else},
//                {"variableDeclarationList", TokenTypes.Else},
//                {"variableModifier", TokenTypes.Else},
//                {"whileLoop", TokenTypes.Else},
        });
    }
}

