/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit.testutils;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.translators.tsphp.IRuntimeCheckProvider;
import ch.tsphp.tinsphp.translators.tsphp.ITempVariableHelper;
import ch.tsphp.tinsphp.translators.tsphp.ITypeTransformer;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPRuntimeCheckProvider;
import ch.tsphp.tinsphp.translators.tsphp.issues.IOutputIssueMessageProvider;
import org.junit.Ignore;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Ignore
public class ATSPHPRuntimeCheckProviderTest extends ATypeTest
{

    protected ITempVariableHelper createTempVariableHelperIsVariable() {
        ITempVariableHelper tempVariableHelper = mock(ITempVariableHelper.class);
        when(tempVariableHelper.getTempVariableNameIfNotVariable(any(ITSPHPAst.class))).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return ((ITSPHPAst) invocationOnMock.getArguments()[0]).getText();
            }
        });
        return tempVariableHelper;
    }

    protected ITempVariableHelper createTempVariableHelperIsNotVariable(final String tempVariableName) {
        ITempVariableHelper tempVariableHelper = mock(ITempVariableHelper.class);
        when(tempVariableHelper.getTempVariableNameIfNotVariable(any(ITSPHPAst.class))).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return tempVariableName;
            }
        });
        return tempVariableHelper;
    }

    protected IRuntimeCheckProvider createRuntimeCheckProvider(ITempVariableHelper tempVariableHelper) {
        ITypeTransformer typeTransformer = mock(ITypeTransformer.class);
        when(typeTransformer.getType(any(ITypeSymbol.class))).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArguments()[0];
            }
        });
        ITypeSymbol tsphpBoolTypeSymbol = mock(ITypeSymbol.class);
        when(tsphpBoolTypeSymbol.getAbsoluteName()).thenReturn("bool");
        IOutputIssueMessageProvider messageProvider = mock(IOutputIssueMessageProvider.class);
        when(messageProvider.getValueCheckError(anyString(), anyString())).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return "The variable " + args[0] + " must hold the value " + args[1] + ".";
            }
        });
        when(messageProvider.getTypeCheckError(anyString(), anyList())).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return "The variable " + args[0] + " must hold a value of type " + args[1] + ".";
            }
        });
        when(messageProvider.getParameterRuntimeCheckMessage(anyString(), anyString(), anyInt(), anyList()))
                .then(new Answer<Object>()
                {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        Object[] args = invocationOnMock.getArguments();
                        return "Argument " + args[2] + " passed to " + args[0] + " (parameter " + args[1] + ") "
                                + "must be a value of type " + args[3] + ".";
                    }
                });

        return createRuntimeCheckProvider(
                typeTransformer,
                tempVariableHelper,
                messageProvider,
                tsphpBoolTypeSymbol);
    }

    protected IRuntimeCheckProvider createRuntimeCheckProvider(
            ITypeTransformer typeTransformer,
            ITempVariableHelper tempVariableHelper,
            IOutputIssueMessageProvider messageProvider,
            ITypeSymbol tsphpBoolTypeSymbol) {
        return new TSPHPRuntimeCheckProvider(
                typeHelper, typeTransformer, tempVariableHelper, messageProvider, tsphpBoolTypeSymbol);
    }
}
