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
import ch.tsphp.tinsphp.common.symbols.PrimitiveTypeNames;
import ch.tsphp.tinsphp.common.utils.Pair;
import ch.tsphp.tinsphp.translators.tsphp.ITypeTransformer;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPTypeTransformer;
import ch.tsphp.tinsphp.translators.tsphp.TsphpUnionTypeSymbol;
import ch.tsphp.tinsphp.translators.tsphp.test.unit.testutils.ATypeTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TSPHPTypeTransformerTest extends ATypeTest
{

    @Test
    public void getTypeName_NullType_ReturnsBool() {
        //no arrange necessary

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(nullType);

        assertThat(result.first.getAbsoluteName(), is("mixed"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_NullTypeInIntersection_ReturnsBool() {
        IIntersectionTypeSymbol intersectionTypeSymbol = createIntersectionTypeSymbol(nullType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(intersectionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("mixed"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_NullOrFalseTypeOrTrueType_ReturnsNullableBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(boolType, nullType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("bool?"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_NullOrIntOrFloat_ReturnsNullableBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, numType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("num?"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_NullOrFalseTypeOrTrueTypeOrInt_ReturnsNullableScalar() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, boolType, intType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("scalar?"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_NullOrFalseTypeOrTrueTypeOrIntOrArray_ReturnsMixed() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, boolType, intType, arrayType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("mixed"));
        assertThat(result.second, is(true));
    }

    //TODO TINS-332 introduce object pseudo type
//    @Test
//    public void getTypeName_NullOrIA_ReturnsIA() {
//        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, interfaceAType);
//
//        ITypeTransformer typeTransformer = createTypeTransformer();
//        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);
//
//        assertThat(result.first.getAbsoluteName(), is("IA"));
//        assertThat(result.second, is(false));
//    }

    @Test
    public void getTypeName_NullOrIAOrIB_ReturnsMixed() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, interfaceAType, interfaceBType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("mixed"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_NullTypeOrFalseType_ReturnsNullableBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(nullType, falseType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("bool?"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_FalseType_ReturnsBool() {
        //no arrange necessary

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(falseType);

        assertThat(result.first.getAbsoluteName(), is("bool"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_FalseTypeInUnion_ReturnsBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("bool"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_FalseTypeInUnionInIntersection_ReturnsBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType);
        IIntersectionTypeSymbol intersectionTypeSymbol = createIntersectionTypeSymbol(unionTypeSymbol);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(intersectionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("bool"));
        assertThat(result.second, is(true));
    }


    @Test
    public void getTypeName_TrueType_ReturnsBool() {
        //no arrange necessary

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(trueType);

        assertThat(result.first.getAbsoluteName(), is("bool"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_FalseTypeOrTrueType_ReturnsBool() {
        IUnionTypeSymbol unionTypeSymbol = boolType;

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("bool"));
        assertThat(result.second, is(false));
    }


    @Test
    public void getTypeName_FalseTypeOrTrueTypeInIntersection_ReturnsBool() {
        IUnionTypeSymbol unionTypeSymbol = boolType;
        IIntersectionTypeSymbol intersectionTypeSymbol = createIntersectionTypeSymbol(unionTypeSymbol);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(intersectionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("bool"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrInt_ReturnsFalseableInt() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, intType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("int!"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrFloat_ReturnsFalseableFloat() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, floatType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("float!"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrIntOrFloat_ReturnsFalseableInt() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, intType, floatType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("num!"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrTrueTypeOrInt_ReturnsScalar() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, trueType, intType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("scalar"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_FalseTypeOrArray_ReturnsFalseableArray() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, arrayType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("array!"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrArrayOrString_ReturnsMixed() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, arrayType, stringType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("mixed"));
        assertThat(result.second, is(true));
    }

    @Test
    public void getTypeName_FalseTypeOrNullOrInt_ReturnsNullableFalseableInt() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, nullType, intType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("int!?"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrFloatOrNullOrInt_ReturnsNullableFalseableNum() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, floatType, nullType, intType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("num!?"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_FalseTypeOrNullOrString_ReturnsNullableFalseableString() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, nullType, stringType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("string!?"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_IntOrFloat_ReturnsNum() {
        IUnionTypeSymbol unionTypeSymbol = numType;

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("num"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_AsInt_ReturnsAsInt() {
        IConvertibleTypeSymbol convertibleTypeSymbol = createConvertibleTypeSymbol(intType);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(convertibleTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("{as int}"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_AsFalseTypeOrTrueType_ReturnsAsBool() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(falseType, trueType);
        IConvertibleTypeSymbol convertibleTypeSymbol = createConvertibleTypeSymbol(unionTypeSymbol);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(convertibleTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("{as bool}"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_AsFalseTypeOrTrueTypeInUnion_ReturnsAsBool() {
        IUnionTypeSymbol unionTypeSymbol1 = createUnionTypeSymbol(falseType, trueType);
        IConvertibleTypeSymbol convertibleTypeSymbol = createConvertibleTypeSymbol(unionTypeSymbol1);
        IUnionTypeSymbol unionTypeSymbol2 = createUnionTypeSymbol(convertibleTypeSymbol);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(unionTypeSymbol2);

        assertThat(result.first.getAbsoluteName(), is("{as bool}"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_AsIntOrFloat_ReturnsAsNum() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(intType, floatType);
        IConvertibleTypeSymbol convertibleTypeSymbol = createConvertibleTypeSymbol(unionTypeSymbol);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(convertibleTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("{as num}"));
        assertThat(result.second, is(false));
    }

    @Test
    public void getTypeName_AsIntOrFloatInIntersection_ReturnsAsNum() {
        IUnionTypeSymbol unionTypeSymbol = createUnionTypeSymbol(intType, floatType);
        IConvertibleTypeSymbol convertibleTypeSymbol = createConvertibleTypeSymbol(unionTypeSymbol);
        IIntersectionTypeSymbol intersectionTypeSymbol = createIntersectionTypeSymbol(convertibleTypeSymbol);

        ITypeTransformer typeTransformer = createTypeTransformer();
        Pair<ITypeSymbol, Boolean> result = typeTransformer.getType(intersectionTypeSymbol);

        assertThat(result.first.getAbsoluteName(), is("{as num}"));
        assertThat(result.second, is(false));
    }

    protected ITypeTransformer createTypeTransformer() {
        IUnionTypeSymbol unionTypeSymbol = (IUnionTypeSymbol) primitiveTypes.get(PrimitiveTypeNames.BOOL);
        ITypeSymbol tsphpBoolTypeSymbol = new TsphpUnionTypeSymbol("bool", unionTypeSymbol);
        unionTypeSymbol = (IUnionTypeSymbol) primitiveTypes.get(PrimitiveTypeNames.NUM);
        ITypeSymbol tsphpNumTypeSymbol = new TsphpUnionTypeSymbol("num", unionTypeSymbol);
        unionTypeSymbol = (IUnionTypeSymbol) primitiveTypes.get(PrimitiveTypeNames.SCALAR);
        ITypeSymbol tsphpScalarTypeSymbol = new TsphpUnionTypeSymbol("scalar", unionTypeSymbol);
        return new TSPHPTypeTransformer(
                symbolFactory,
                typeHelper,
                primitiveTypes,
                tsphpBoolTypeSymbol,
                tsphpNumTypeSymbol,
                tsphpScalarTypeSymbol);
    }
}
