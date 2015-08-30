/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit.testutils;

import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.IConversionMethod;
import ch.tsphp.tinsphp.common.core.IConversionsProvider;
import ch.tsphp.tinsphp.common.scopes.IScopeHelper;
import ch.tsphp.tinsphp.common.symbols.IConvertibleTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IIntersectionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IModifierHelper;
import ch.tsphp.tinsphp.common.symbols.ISymbolFactory;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.PrimitiveTypeNames;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.common.utils.Pair;
import ch.tsphp.tinsphp.symbols.ConvertibleTypeSymbol;
import ch.tsphp.tinsphp.symbols.IntersectionTypeSymbol;
import ch.tsphp.tinsphp.symbols.PseudoTypeSymbol;
import ch.tsphp.tinsphp.symbols.SymbolFactory;
import ch.tsphp.tinsphp.symbols.UnionTypeSymbol;
import ch.tsphp.tinsphp.symbols.constraints.BindingCollection;
import ch.tsphp.tinsphp.symbols.utils.TypeHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ch.tsphp.tinsphp.common.utils.Pair.pair;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Ignore
public abstract class ATypeTest
{
    protected static ITypeSymbol mixedType;
    protected static ITypeSymbol nullType;
    protected static ITypeSymbol falseType;
    protected static ITypeSymbol trueType;
    protected static IUnionTypeSymbol boolType;
    protected static ITypeSymbol intType;
    protected static ITypeSymbol floatType;
    protected static IUnionTypeSymbol numType;
    protected static ITypeSymbol stringType;
    protected static IUnionTypeSymbol scalarType;
    protected static ITypeSymbol arrayType;

    protected static ITypeSymbol interfaceAType;
    protected static ITypeSymbol interfaceSubAType;
    protected static ITypeSymbol interfaceBType;
    protected static ITypeSymbol fooType;

    protected static ISymbolFactory symbolFactory;
    protected static ITypeHelper typeHelper;
    protected static Map<String, ITypeSymbol> primitiveTypes;

    @BeforeClass
    public static void init() {
        mixedType = mock(ITypeSymbol.class);
        when(mixedType.getAbsoluteName()).thenReturn("mixed");

        typeHelper = new TypeHelper();
        symbolFactory = mock(ISymbolFactory.class);
        typeHelper.setMixedTypeSymbol(mixedType);
        when(symbolFactory.getMixedTypeSymbol()).thenReturn(mixedType);
        when(symbolFactory.createUnionTypeSymbol()).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return new UnionTypeSymbol(typeHelper);
            }
        });
        when(symbolFactory.createIntersectionTypeSymbol()).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return new IntersectionTypeSymbol(typeHelper);
            }
        });
        when(symbolFactory.createConvertibleTypeSymbol()).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return new ConvertibleTypeSymbol(new BindingCollection(symbolFactory, typeHelper));
            }
        });
        when(symbolFactory.createPseudoTypeSymbol(anyString(), any(ITypeSymbol.class), anyBoolean()))
                .then(new Answer<Object>()
                {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        Object[] args = invocationOnMock.getArguments();
                        return new PseudoTypeSymbol((String) args[0], (ITypeSymbol) args[1], (boolean) args[2]);
                    }
                });

        nullType = mock(ITypeSymbol.class);
        when(nullType.getParentTypeSymbols()).thenReturn(set(mixedType));
        when(nullType.getAbsoluteName()).thenReturn("nullType");

        falseType = mock(ITypeSymbol.class);
        when(falseType.getParentTypeSymbols()).thenReturn(set(mixedType));
        when(falseType.getAbsoluteName()).thenReturn("falseType");

        trueType = mock(ITypeSymbol.class);
        when(trueType.getParentTypeSymbols()).thenReturn(set(mixedType));
        when(trueType.getAbsoluteName()).thenReturn("trueType");

        boolType = new UnionTypeSymbol(typeHelper);
        boolType.addTypeSymbol(falseType);
        boolType.addTypeSymbol(trueType);

        intType = mock(ITypeSymbol.class);
        when(intType.getParentTypeSymbols()).thenReturn(set(mixedType));
        when(intType.getAbsoluteName()).thenReturn("int");

        floatType = mock(ITypeSymbol.class);
        when(floatType.getParentTypeSymbols()).thenReturn(set(mixedType));
        when(floatType.getAbsoluteName()).thenReturn("float");

        numType = new UnionTypeSymbol(typeHelper);
        numType.addTypeSymbol(intType);
        numType.addTypeSymbol(floatType);

        stringType = mock(ITypeSymbol.class);
        when(stringType.getParentTypeSymbols()).thenReturn(set(mixedType));
        when(stringType.getAbsoluteName()).thenReturn("string");

        scalarType = new UnionTypeSymbol(typeHelper);
        scalarType.addTypeSymbol(boolType);
        scalarType.addTypeSymbol(numType);
        scalarType.addTypeSymbol(stringType);

        arrayType = mock(ITypeSymbol.class);
        when(arrayType.getParentTypeSymbols()).thenReturn(set(mixedType));
        when(arrayType.getAbsoluteName()).thenReturn("array");

        interfaceAType = mock(ITypeSymbol.class);
        when(interfaceAType.getParentTypeSymbols()).thenReturn(set(mixedType));
        when(interfaceAType.getAbsoluteName()).thenReturn("IA");
        when(interfaceAType.canBeUsedInIntersection()).thenReturn(true);

        interfaceSubAType = mock(ITypeSymbol.class);
        when(interfaceSubAType.getParentTypeSymbols()).thenReturn(set(interfaceAType));
        when(interfaceSubAType.getAbsoluteName()).thenReturn("ISubA");
        when(interfaceSubAType.canBeUsedInIntersection()).thenReturn(true);

        interfaceBType = mock(ITypeSymbol.class);
        when(interfaceBType.getParentTypeSymbols()).thenReturn(set(mixedType));
        when(interfaceBType.getAbsoluteName()).thenReturn("IB");
        when(interfaceBType.canBeUsedInIntersection()).thenReturn(true);

        fooType = mock(ITypeSymbol.class);
        when(fooType.getParentTypeSymbols()).thenReturn(set(interfaceSubAType, interfaceBType));
        when(fooType.getAbsoluteName()).thenReturn("Foo");

        primitiveTypes = new HashMap<>();
        primitiveTypes.put(PrimitiveTypeNames.NULL_TYPE, nullType);
        primitiveTypes.put(PrimitiveTypeNames.FALSE_TYPE, falseType);
        primitiveTypes.put(PrimitiveTypeNames.TRUE_TYPE, trueType);
        primitiveTypes.put(PrimitiveTypeNames.BOOL, boolType);
        primitiveTypes.put(PrimitiveTypeNames.INT, intType);
        primitiveTypes.put(PrimitiveTypeNames.FLOAT, floatType);
        primitiveTypes.put(PrimitiveTypeNames.NUM, numType);
        primitiveTypes.put(PrimitiveTypeNames.STRING, stringType);
        primitiveTypes.put(PrimitiveTypeNames.SCALAR, scalarType);
        primitiveTypes.put(PrimitiveTypeNames.ARRAY, arrayType);
        primitiveTypes.put(PrimitiveTypeNames.MIXED, mixedType);
    }

    @Before
    public void setUp() {
        IConversionsProvider conversionsProvider = mock(IConversionsProvider.class);
        HashMap<String, Map<String, Pair<ITypeSymbol, IConversionMethod>>> conversions = new HashMap<>();
        when(conversionsProvider.getImplicitConversions()).thenReturn(conversions);
        when(conversionsProvider.getExplicitConversions()).thenReturn(conversions);
        typeHelper.setConversionsProvider(conversionsProvider);
    }

    @SafeVarargs
    protected static <T> Set<T> set(T... symbols) {
        return new HashSet<>(Arrays.asList(symbols));
    }

    protected IUnionTypeSymbol createUnionTypeSymbol(ITypeSymbol... typeSymbols) {
        return createUnionTypeSymbol(typeHelper, typeSymbols);
    }

    protected IUnionTypeSymbol createUnionTypeSymbol(ITypeHelper theTypeHelper, ITypeSymbol... typeSymbols) {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(theTypeHelper);
        for (ITypeSymbol typeSymbol : typeSymbols) {
            unionTypeSymbol.addTypeSymbol(typeSymbol);
        }
        return unionTypeSymbol;
    }

    protected IUnionTypeSymbol createUnionTypeSymbol() {
        return createUnionTypeSymbol(typeHelper);
    }

    protected IUnionTypeSymbol createUnionTypeSymbol(ITypeHelper typeHelper) {
        return new UnionTypeSymbol(typeHelper);
    }

    protected IIntersectionTypeSymbol createIntersectionTypeSymbol(ITypeSymbol... typeSymbols) {
        return createIntersectionTypeSymbol(typeHelper, typeSymbols);
    }

    protected IIntersectionTypeSymbol createIntersectionTypeSymbol(
            ITypeHelper theTypeHelper, ITypeSymbol... typeSymbols) {
        IIntersectionTypeSymbol intersectionTypeSymbol = createIntersectionTypeSymbol(theTypeHelper);
        for (ITypeSymbol typeSymbol : typeSymbols) {
            intersectionTypeSymbol.addTypeSymbol(typeSymbol);
        }
        return intersectionTypeSymbol;
    }

    protected IIntersectionTypeSymbol createIntersectionTypeSymbol() {
        return createIntersectionTypeSymbol(typeHelper);
    }

    protected IIntersectionTypeSymbol createIntersectionTypeSymbol(ITypeHelper theTypeHelper) {
        return new IntersectionTypeSymbol(theTypeHelper);
    }

    protected IConvertibleTypeSymbol createConvertibleType(
            ITypeSymbol typeSymbol, ISymbolFactory theSymbolFactory, ITypeHelper theTypeHelper) {
        ConvertibleTypeSymbol convertibleTypeSymbol = createConvertibleType(theSymbolFactory, theTypeHelper);
        convertibleTypeSymbol.addLowerTypeBound(typeSymbol);
        convertibleTypeSymbol.addUpperTypeBound(typeSymbol);
        return convertibleTypeSymbol;
    }

    protected IConvertibleTypeSymbol createConvertibleTypeSymbol(ITypeSymbol typeSymbol) {
        return createConvertibleType(typeSymbol, symbolFactory, typeHelper);
    }

    protected ConvertibleTypeSymbol createConvertibleType(ISymbolFactory theSymbolFactory, ITypeHelper theTypeHelper) {
        return new ConvertibleTypeSymbol(new BindingCollection(theSymbolFactory, theTypeHelper));
    }

    @SafeVarargs
    protected final Map<String, Map<String, Pair<ITypeSymbol, IConversionMethod>>> createConversions(
            Pair<ITypeSymbol, List<ITypeSymbol>>... pairs) {

        Map<String, Map<String, Pair<ITypeSymbol, IConversionMethod>>> conversionsMap = new HashMap<>();
        for (Pair<ITypeSymbol, List<ITypeSymbol>> element : pairs) {
            Map<String, Pair<ITypeSymbol, IConversionMethod>> conversions = new HashMap<>();
            for (ITypeSymbol toType : element.second) {
                conversions.put(toType.getAbsoluteName(), pair(toType, mock(IConversionMethod.class)));
            }
            conversionsMap.put(element.first.getAbsoluteName(), conversions);
        }

        return conversionsMap;
    }

    protected ISymbolFactory createSymbolFactory(
            IScopeHelper theScopeHelper,
            IModifierHelper theModifierHelper,
            ITypeHelper theTypeHelper) {
        return new SymbolFactory(theScopeHelper, theModifierHelper, theTypeHelper);
    }


    protected ITypeHelper createTypeHelperAndInit(
            Map<String, Map<String, Pair<ITypeSymbol, IConversionMethod>>> implicitConversions,
            Map<String, Map<String, Pair<ITypeSymbol, IConversionMethod>>> explicitConversions) {
        ITypeHelper typeHelper = new TypeHelper();
        IConversionsProvider conversionsProvider = mock(IConversionsProvider.class);
        when(conversionsProvider.getImplicitConversions()).thenReturn(implicitConversions);
        when(conversionsProvider.getExplicitConversions()).thenReturn(explicitConversions);
        typeHelper.setConversionsProvider(conversionsProvider);
        typeHelper.setMixedTypeSymbol(mixedType);
        return typeHelper;
    }

    protected ITypeHelper createTypeHelperAndInit() {
        return createTypeHelperAndInit(
                new HashMap<String, Map<String, Pair<ITypeSymbol, IConversionMethod>>>(),
                new HashMap<String, Map<String, Pair<ITypeSymbol, IConversionMethod>>>());
    }
}
