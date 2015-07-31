/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit;

import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IConvertibleTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IIntersectionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.common.utils.Pair;
import ch.tsphp.tinsphp.translators.tsphp.INameTransformer;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPNameTransformer;
import ch.tsphp.tinsphp.translators.tsphp.test.unit.testutils.ATypeTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TSPHPNameTransformerTest extends ATypeTest
{

    @Test
    public void getTypeName_NullType_ReturnsBool() {
        //no arrange necessary

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(nullType);

        assertThat(result.first, is("mixed"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_NullTypeInIntersection_ReturnsBool() {
        IIntersectionTypeSymbol intersectionTypeSymbol = createIntersectionTypeSymbol(nullType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(intersectionTypeSymbol);

        assertThat(result.first, is("mixed"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_NullOrFalseTypeOrTrueType_ReturnsNullableBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(boolType, nullType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("bool?"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_NullOrIntOrFloat_ReturnsNullableBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, numType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("num?"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_NullOrFalseTypeOrTrueTypeOrInt_ReturnsNullableScalar() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, boolType, intType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("scalar?"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_NullOrFalseTypeOrTrueTypeOrIntOrArray_ReturnsMixed() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, boolType, intType, arrayType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("mixed"));
        assertThat(result.second, is(true));
    }

    //TODO TINS-332 introduce object pseudo type
//    @Test
//    public void getTypeName_NullOrIA_ReturnsIA() {
//        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, interfaceAType);
//
//        ITypeReducer nameTransformer = createNameTransformer();
//        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);
//
//        assertThat(result.first, is("IA"));
//        assertThat(result.second, is(false));
//    }

    @Test
    public void getTypeName_NullOrIAOrIB_ReturnsMixed() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, interfaceAType, interfaceBType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("mixed"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_NullTypeOrFalseType_ReturnsNullableBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, falseType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("bool?"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_FalseType_ReturnsBool() {
        //no arrange necessary

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(falseType);

        assertThat(result.first, is("bool"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_FalseTypeInUnion_ReturnsBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("bool"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_FalseTypeInUnionInIntersection_ReturnsBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType);
        IIntersectionTypeSymbol intersectionTypeSymbol = createIntersectionTypeSymbol(unionTypeSymbol);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(intersectionTypeSymbol);

        assertThat(result.first, is("bool"));
        assertThat(result.second, is(true));
    }


    @Test
    public void getTypeName_TrueType_ReturnsBool() {
        //no arrange necessary

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(trueType);

        assertThat(result.first, is("bool"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_FalseTypeOrTrueType_ReturnsBool() {
        IUnionTypeSymbol unionTypeSymbol = boolType;

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("bool"));
        assertThat(result.second, is(false));
    }


    @Test
    public void getTypeName_FalseTypeOrTrueTypeInIntersection_ReturnsBool() {
        IUnionTypeSymbol unionTypeSymbol = boolType;
        IIntersectionTypeSymbol intersectionTypeSymbol = createIntersectionTypeSymbol(unionTypeSymbol);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(intersectionTypeSymbol);

        assertThat(result.first, is("bool"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrInt_ReturnsFalseableInt() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, intType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("int!"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrFloat_ReturnsFalseableFloat() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, floatType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("float!"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrIntOrFloat_ReturnsFalseableInt() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, intType, floatType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("num!"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrTrueTypeOrInt_ReturnsScalar() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, trueType, intType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("scalar"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_FalseTypeOrArray_ReturnsFalseableArray() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, arrayType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("array!"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrArrayOrString_ReturnsMixed() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, arrayType, stringType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("mixed"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_FalseTypeOrNullOrInt_ReturnsNullableFalseableInt() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, nullType, intType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("int!?"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrFloatOrNullOrInt_ReturnsNullableFalseableNum() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, floatType, nullType, intType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("num!?"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrNullOrString_ReturnsNullableFalseableString() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, nullType, stringType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("string!?"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_IntOrFloat_ReturnsNum() {
        IUnionTypeSymbol unionTypeSymbol = numType;

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result.first, is("num"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_AsInt_ReturnsAsInt() {
        IConvertibleTypeSymbol convertibleTypeSymbol = createConvertibleTypeSymbol(intType);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(convertibleTypeSymbol);

        assertThat(result.first, is("{as int}"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_AsFalseTypeOrTrueType_ReturnsAsBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, trueType);
        IConvertibleTypeSymbol convertibleTypeSymbol = createConvertibleTypeSymbol(unionTypeSymbol);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(convertibleTypeSymbol);

        assertThat(result.first, is("{as bool}"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_AsFalseTypeOrTrueTypeInUnion_ReturnsAsBool() {
        IUnionTypeSymbol unionTypeSymbol1 = createUnionTypeSymbol(falseType, trueType);
        IConvertibleTypeSymbol convertibleTypeSymbol = createConvertibleTypeSymbol(unionTypeSymbol1);
        IUnionTypeSymbol unionTypeSymbol2 = createUnionTypeSymbol(convertibleTypeSymbol);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(unionTypeSymbol2);

        assertThat(result.first, is("{as bool}"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_AsIntOrFloat_ReturnsAsNum() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(intType, floatType);
        IConvertibleTypeSymbol convertibleTypeSymbol = createConvertibleTypeSymbol(unionTypeSymbol);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(convertibleTypeSymbol);

        assertThat(result.first, is("{as num}"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_AsIntOrFloatInIntersection_ReturnsAsNum() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(intType, floatType);
        IConvertibleTypeSymbol convertibleTypeSymbol = createConvertibleTypeSymbol(unionTypeSymbol);
        IIntersectionTypeSymbol intersectionTypeSymbol = createIntersectionTypeSymbol(convertibleTypeSymbol);

        INameTransformer nameTransformer = createNameTransformer();
        Pair<String, Boolean> result = nameTransformer.getTypeName(intersectionTypeSymbol);

        assertThat(result.first, is("{as num}"));
        assertThat(result.second, is(false));
    }

    protected INameTransformer createNameTransformer() {
        ITypeSymbol tsphpBoolTypeSymbol = symbolFactory.createPseudoTypeSymbol("bool", mixedType);
        ITypeSymbol tsphpNumTypeSymbol = symbolFactory.createPseudoTypeSymbol("num", mixedType);
        ITypeSymbol tsphpScalarTypeSymbol = symbolFactory.createPseudoTypeSymbol("scalar", mixedType);
        return new TSPHPNameTransformer(
                typeHelper, primitiveTypes, tsphpBoolTypeSymbol, tsphpNumTypeSymbol, tsphpScalarTypeSymbol);
    }
}
