/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.gen.TokenTypes;
import ch.tsphp.tinsphp.common.inference.constraints.IFunctionType;
import ch.tsphp.tinsphp.common.inference.constraints.IOverloadBindings;
import ch.tsphp.tinsphp.common.inference.constraints.OverloadApplicationDto;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.PrimitiveTypeNames;
import ch.tsphp.tinsphp.common.translation.dtos.FunctionApplicationDto;
import ch.tsphp.tinsphp.common.utils.ERelation;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.common.utils.Pair;
import ch.tsphp.tinsphp.common.utils.TypeHelperDto;

import java.util.HashMap;
import java.util.Map;

import static ch.tsphp.tinsphp.common.utils.Pair.pair;

public class TSPHPOperatorHelper implements IOperatorHelper
{
    private final ITypeHelper typeHelper;

    private final Map<Integer, Map<String, Pair<String, ITypeSymbol>>> migrationFunctions = new HashMap<>();
    private final Map<Integer, Pair<String, ITypeSymbol>> dynamicFunctions = new HashMap<>();
    private final Map<String, ITypeSymbol> primitiveTypes;

    public TSPHPOperatorHelper(ITypeHelper theTypeHelper, Map<String, ITypeSymbol> thePrimitiveTypes) {
        typeHelper = theTypeHelper;
        primitiveTypes = thePrimitiveTypes;
        init();
    }

    private void init() {

        Map<String, Pair<String, ITypeSymbol>> map = new HashMap<>();
        Pair<String, ITypeSymbol> pair =
                pair("oldSchoolAddition", primitiveTypes.get(PrimitiveTypeNames.NUM));
        map.put("{as (float | int)} x float -> float", pair);
        map.put("float x {as (float | int)} -> float", pair);
        map.put("{as T} x {as T} -> T \\ T <: (float | int)", pair);
        migrationFunctions.put(TokenTypes.Plus, map);
        dynamicFunctions.put(TokenTypes.Plus, pair);

        map = new HashMap<>();
        pair = pair("oldSchoolSubtraction", primitiveTypes.get(PrimitiveTypeNames.NUM));
        map.put("{as (float | int)} x float -> float", pair);
        map.put("float x {as (float | int)} -> float", pair);
        map.put("{as T} x {as T} -> T \\ T <: (float | int)", pair);
        migrationFunctions.put(TokenTypes.Minus, map);
        dynamicFunctions.put(TokenTypes.Minus, pair);

        map = new HashMap<>();
        pair = pair("oldSchoolMultiplication", primitiveTypes.get(PrimitiveTypeNames.NUM));
        map.put("{as (float | int)} x float -> float", pair);
        map.put("float x {as (float | int)} -> float", pair);
        map.put("{as T} x {as T} -> T \\ T <: (float | int)", pair);
        migrationFunctions.put(TokenTypes.Multiply, map);
        dynamicFunctions.put(TokenTypes.Multiply, pair);
    }

    @Override
    public void turnIntoMigrationFunctionIfRequired(
            FunctionApplicationDto functionApplicationDto,
            OverloadApplicationDto dto,
            IOverloadBindings currentBindings,
            ITSPHPAst leftHandSide,
            ITSPHPAst arguments) {

        int operatorType = leftHandSide.getType();

        IFunctionType overload = dto.overload;
        if (overload != null) {
            if (migrationFunctions.containsKey(operatorType)) {
                Map<String, Pair<String, ITypeSymbol>> overloads = migrationFunctions.get(operatorType);
                String signature = overload.getSignature();
                Pair<String, ITypeSymbol> pair = overloads.get(signature);
                switchToMigrationFunction(functionApplicationDto, currentBindings, leftHandSide, pair);
            }

            if (functionApplicationDto.name != null
                    && (functionApplicationDto.name.equals("oldSchoolAddition")
                    || functionApplicationDto.name.equals("oldSchoolSubtraction")
                    || functionApplicationDto.name.equals("oldSchoolMultiplication"))) {
                handleOldSchoolFloatArithmetic(functionApplicationDto, dto, currentBindings, arguments);
            }
        } else {
            Pair<String, ITypeSymbol> pair = dynamicFunctions.get(operatorType);
            switchToMigrationFunction(functionApplicationDto, currentBindings, leftHandSide, pair);
        }
    }

    private void switchToMigrationFunction(
            FunctionApplicationDto functionApplicationDto,
            IOverloadBindings currentBindings,
            ITSPHPAst leftHandSide,
            Pair<String, ITypeSymbol> pair) {

        if (pair != null) {
            functionApplicationDto.name = pair.first;

            String leftHandSideId = leftHandSide.getSymbol().getAbsoluteName();
            String lhsTypeVariable = currentBindings.getTypeVariable(leftHandSideId);
            IUnionTypeSymbol lowerTypeBounds = currentBindings.getLowerTypeBounds(lhsTypeVariable);
            ITypeSymbol returnType = pair.second;
            TypeHelperDto result = typeHelper.isFirstSameOrSubTypeOfSecond(returnType, lowerTypeBounds);
            //if the left hand side is more specific than the return type then we need to cast
            if (result.relation == ERelation.HAS_NO_RELATION) {
                functionApplicationDto.returnRuntimeCheck = lowerTypeBounds.getAbsoluteName();
            }
        }
    }

    private void handleOldSchoolFloatArithmetic(
            FunctionApplicationDto functionApplicationDto,
            OverloadApplicationDto dto,
            IOverloadBindings currentBindings,
            ITSPHPAst arguments) {

        ITypeSymbol lhsTypeSymbol = getTypeSymbol(dto, 0, currentBindings, arguments.getChild(0));
        ITypeSymbol rhsTypeSymbol = getTypeSymbol(dto, 1, currentBindings, arguments.getChild(1));
        String lhsAbsoluteName = lhsTypeSymbol.getAbsoluteName();
        String rhsAbsoluteName = rhsTypeSymbol.getAbsoluteName();

        //we can use the regular operator for float x int or int x float
        if (isFloatAndIntOrIntAndFloat(lhsAbsoluteName, rhsAbsoluteName)) {
            functionApplicationDto.name = null;
            functionApplicationDto.returnRuntimeCheck = null;
        }
    }

    private boolean isFloatAndIntOrIntAndFloat(String lhsAbsoluteName, String rhsAbsoluteName) {
        return (lhsAbsoluteName.equals(PrimitiveTypeNames.INT) || lhsAbsoluteName.equals(PrimitiveTypeNames.FLOAT))
                && (rhsAbsoluteName.equals(PrimitiveTypeNames.INT) || rhsAbsoluteName.equals(PrimitiveTypeNames.FLOAT));
    }

    private ITypeSymbol getTypeSymbol(
            OverloadApplicationDto dto, int index, IOverloadBindings currentBindings, ITSPHPAst ast) {

        ITypeSymbol typeSymbol = null;
        if (dto.runtimeChecks != null && dto.runtimeChecks.containsKey(index)) {
            typeSymbol = dto.runtimeChecks.get(index).first;
        }
        if (typeSymbol == null) {
            String argumentId = ast.getSymbol().getAbsoluteName();
            String typeVariable = currentBindings.getTypeVariable(argumentId);
            typeSymbol = currentBindings.getLowerTypeBounds(typeVariable);
            if (typeSymbol == null) {
                typeSymbol = currentBindings.getUpperTypeBounds(typeVariable);
            }
        }
        return typeSymbol;
    }

}
