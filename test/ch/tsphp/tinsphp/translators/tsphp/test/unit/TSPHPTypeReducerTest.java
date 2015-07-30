/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.unit;

import ch.tsphp.tinsphp.common.symbols.IIntersectionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.translators.tsphp.INameTransformer;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPNameTransformer;
import ch.tsphp.tinsphp.translators.tsphp.test.unit.testutils.ATypeTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TSPHPTypeReducerTest extends ATypeTest
{

    @Test
    public void getTypeName_NullType_ReturnsBool() {
        //no arrange necessary

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(nullType);

        assertThat(result, is("mixed"));
    }

    @Test
    public void getTypeName_NullTypeInIntersection_ReturnsBool() {
        IIntersectionTypeSymbol intersectionTypeSymbol = createIntersectionTypeSymbol(nullType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(intersectionTypeSymbol);

        assertThat(result, is("mixed"));
    }

    @Test
    public void getTypeName_NullOrFalseOrTrue_ReturnsNullableBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(boolType, nullType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("bool?"));
    }

    @Test
    public void getTypeName_NullOrIntOrFloat_ReturnsNullableBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, numType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("num?"));
    }

    @Test
    public void getTypeName_NullOrFalseOrTrueOrInt_ReturnsNullableScalar() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, boolType, intType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("scalar?"));
    }

    @Test
    public void getTypeName_NullOrFalseOrTrueOrIntOrArray_ReturnsMixed() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, boolType, intType, arrayType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("mixed"));
    }

    //TODO TINS-332 introduce object pseudo type
//    @Test
//    public void getTypeName_NullOrIA_ReturnsIA() {
//        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, interfaceAType);
//
//        ITypeReducer nameTransformer = createNameTransformer();
//        String result = nameTransformer.getTypeName(unionTypeSymbol);
//
//        assertThat(result, is("IA"));
//    }

    @Test
    public void getTypeName_NullOrIAOrIB_ReturnsMixed() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, interfaceAType, interfaceBType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("mixed"));
    }

    @Test
    public void getTypeName_NullTypeOrFalseType_ReturnsNullableBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, falseType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("bool?"));
    }

    @Test
    public void getTypeName_FalseType_ReturnsBool() {
        //no arrange necessary

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(falseType);

        assertThat(result, is("bool"));
    }

    @Test
    public void getTypeName_FalseTypeInUnion_ReturnsBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("bool"));
    }

    @Test
    public void getTypeName_FalseTypeInUnionInIntersection_ReturnsBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType);
        IIntersectionTypeSymbol intersectionTypeSymbol = createIntersectionTypeSymbol(unionTypeSymbol);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(intersectionTypeSymbol);

        assertThat(result, is("bool"));
    }


    @Test
    public void getTypeName_TrueType_ReturnsBool() {
        //no arrange necessary

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(trueType);

        assertThat(result, is("bool"));
    }

    @Test
    public void getTypeName_FalseOrTrue_ReturnsBool() {
        IUnionTypeSymbol unionTypeSymbol = boolType;

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("bool"));
    }


    @Test
    public void getTypeName_FalseOrTrueInIntersection_ReturnsBool() {
        IUnionTypeSymbol unionTypeSymbol = boolType;
        IIntersectionTypeSymbol intersectionTypeSymbol = createIntersectionTypeSymbol(unionTypeSymbol);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(intersectionTypeSymbol);

        assertThat(result, is("bool"));
    }

    @Test
    public void getTypeName_FalseOrInt_ReturnsFalseableInt() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, intType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("int!"));
    }

    @Test
    public void getTypeName_FalseOrFloat_ReturnsFalseableFloat() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, floatType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("float!"));
    }

    @Test
    public void getTypeName_FalseOrIntOrFloat_ReturnsFalseableInt() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, intType, floatType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("num!"));
    }

    @Test
    public void getTypeName_FalseOrTrueOrInt_ReturnsScalar() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, trueType, intType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("scalar"));
    }

    @Test
    public void getTypeName_FalseOrArray_ReturnsFalseableArray() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, arrayType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("array!"));
    }

    @Test
    public void getTypeName_FalseOrArrayOrString_ReturnsMixed() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, arrayType, stringType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("mixed"));
    }

    @Test
    public void getTypeName_FalseOrNullOrInt_ReturnsNullableFalseableInt() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, nullType, intType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("int!?"));
    }

    @Test
    public void getTypeName_FalseOrFloatOrNullOrInt_ReturnsNullableFalseableNum() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, floatType, nullType, intType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("num!?"));
    }

    @Test
    public void getTypeName_FalseOrNullOrString_ReturnsNullableFalseableString() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, nullType, stringType);

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("string!?"));
    }

    @Test
    public void getTypeName_IntOrFloat_ReturnsNum() {
        IUnionTypeSymbol unionTypeSymbol = numType;

        INameTransformer nameTransformer = createNameTransformer();
        String result = nameTransformer.getTypeName(unionTypeSymbol);

        assertThat(result, is("num"));
    }

    protected INameTransformer createNameTransformer() {
        return new TSPHPNameTransformer(symbolFactory, typeHelper, primitiveTypes);
    }
}
