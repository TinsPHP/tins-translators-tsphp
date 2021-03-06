/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit;

import ch.tsphp.common.IScope;
import ch.tsphp.common.symbols.ISymbol;
import ch.tsphp.tinsphp.common.TinsPHPConstants;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.IFunctionType;
import ch.tsphp.tinsphp.common.inference.constraints.ITypeVariableReference;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.symbols.IMethodSymbol;
import ch.tsphp.tinsphp.common.translation.IDtoCreator;
import ch.tsphp.tinsphp.common.translation.dtos.OverloadDto;
import ch.tsphp.tinsphp.translators.tsphp.DtoCreator;
import ch.tsphp.tinsphp.translators.tsphp.IRuntimeCheckProvider;
import ch.tsphp.tinsphp.translators.tsphp.ITempVariableHelper;
import ch.tsphp.tinsphp.translators.tsphp.ITypeTransformer;
import ch.tsphp.tinsphp.translators.tsphp.ITypeVariableTransformer;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPTranslator;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DtoCreatorTest
{

    //see TINS-621 DtoCreator should not define functions
    @Test
    public void createOverloadDtos_CreatesTwoOverloads_DoesNotAffectOtherTranslators() {
        //arrange
        IMethodSymbol methodSymbol = mock(IMethodSymbol.class);
        String name = "foo()";
        when(methodSymbol.getName()).thenReturn(name);
        IScope scope = mock(IScope.class);
        when(methodSymbol.getDefinitionScope()).thenReturn(scope);
        Map<String, List<ISymbol>> map = new HashMap<>();
        map.put("foo()", new ArrayList<ISymbol>());
        when(scope.getSymbols()).thenReturn(map);

        ITypeVariableReference returnTypeVariableReference = mock(ITypeVariableReference.class);
        when(returnTypeVariableReference.getTypeVariable()).thenReturn("V1");
        when(returnTypeVariableReference.hasFixedType()).thenReturn(true);
        IBindingCollection bindingCollection = mock(IBindingCollection.class);
        when(bindingCollection.getTypeVariableReference(TinsPHPConstants.RETURN_VARIABLE_NAME))
                .thenReturn(returnTypeVariableReference);

        IFunctionType overload1 = mock(IFunctionType.class);
        when(overload1.getSignature()).thenReturn("() -> int");
        when(overload1.getBindingCollection()).thenReturn(bindingCollection);
        when(overload1.getParameters()).thenReturn(new ArrayList<IVariable>());
        when(overload1.getNonFixedTypeParameters()).thenReturn(Collections.<String>emptySet());
        IFunctionType overload2 = mock(IFunctionType.class);
        when(overload2.getSignature()).thenReturn("() -> float");
        when(overload2.getBindingCollection()).thenReturn(bindingCollection);
        when(overload2.getParameters()).thenReturn(new ArrayList<IVariable>());
        when(overload2.getNonFixedTypeParameters()).thenReturn(Collections.<String>emptySet());
        when(methodSymbol.getOverloads()).thenReturn(asList(overload1, overload2));


        //act
        IDtoCreator dtoCreator1 = createDtoCreator();
        IDtoCreator dtoCreator2 = createDtoCreator();
        SortedMap<String, OverloadDto> overloadDtos1 = dtoCreator1.createOverloadDtos(methodSymbol);
        SortedMap<String, OverloadDto> overloadDtos2 = dtoCreator2.createOverloadDtos(methodSymbol);


        //assert
        verify(overload1, times(2)).addSuffix(TSPHPTranslator.TRANSLATOR_ID, "1");
        verify(overload2, times(2)).addSuffix(TSPHPTranslator.TRANSLATOR_ID, "0");

        assertThat(overloadDtos1.size(), is(2));
        Iterator<OverloadDto> iterator = overloadDtos1.values().iterator();
        assertThat(iterator.next().identifier, is("foo0"));
        assertThat(iterator.next().identifier, is("foo1"));

        assertThat(overloadDtos2.size(), is(2));
        iterator = overloadDtos2.values().iterator();
        assertThat(iterator.next().identifier, is("foo0"));
        assertThat(iterator.next().identifier, is("foo1"));
    }

    private IDtoCreator createDtoCreator() {
        ITypeVariableTransformer typeVariableMapper = mock(ITypeVariableTransformer.class);
        when(typeVariableMapper.rewriteOverload(any(IFunctionType.class))).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArguments()[0];
            }
        });
        return createDtoCreator(
                mock(ITempVariableHelper.class),
                mock(ITypeTransformer.class),
                mock(IRuntimeCheckProvider.class),
                typeVariableMapper
        );
    }

    protected IDtoCreator createDtoCreator(
            ITempVariableHelper tempVariableHelper,
            ITypeTransformer typeTransformer,
            IRuntimeCheckProvider runtimeCheckProvider,
            ITypeVariableTransformer typeVariableMapper) {
        return new DtoCreator(
                tempVariableHelper,
                typeTransformer,
                typeVariableMapper, runtimeCheckProvider
        );
    }
}
