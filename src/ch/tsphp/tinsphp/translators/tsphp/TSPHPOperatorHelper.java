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
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.inference.constraints.OverloadApplicationDto;
import ch.tsphp.tinsphp.common.symbols.IConvertibleTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IIntersectionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.PrimitiveTypeNames;
import ch.tsphp.tinsphp.common.translation.dtos.FunctionApplicationDto;
import ch.tsphp.tinsphp.common.utils.ERelation;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.common.utils.Pair;
import ch.tsphp.tinsphp.common.utils.TypeHelperDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

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


        int[] tokens = new int[]{TokenTypes.Plus, TokenTypes.Minus, TokenTypes.Multiply};
        String[] migrationFunctionNames = new String[]{
                "oldSchoolAddition", "oldSchoolSubtraction", "oldSchoolMultiplication"
        };

        for (int i = 0; i < tokens.length; ++i) {
            int token = tokens[i];
            Map<String, Pair<String, ITypeSymbol>> map = new HashMap<>();
            Pair<String, ITypeSymbol> pair =
                    pair(migrationFunctionNames[i], primitiveTypes.get(PrimitiveTypeNames.NUM));
            map.put("{as (float | int)} x float -> float", pair);
            map.put("float x {as (float | int)} -> float", pair);
            map.put("{as T} x {as T} -> T \\ T <: (float | int)", pair);
            migrationFunctions.put(token, map);
            dynamicFunctions.put(token, pair);
        }

        tokens = new int[]{
                TokenTypes.BitwiseOr, TokenTypes.BitwiseXor, TokenTypes.BitwiseAnd,
                TokenTypes.ShiftLeft, TokenTypes.ShiftRight, TokenTypes.Modulo
        };
        migrationFunctionNames = new String[]{
                "oldSchoolBitwiseOr", "oldSchoolBitwiseXor", "oldSchoolBitwiseAnd",
                "oldSchoolShiftLeft", "oldSchoolShiftRight", "oldSchoolModulo"
        };

        for (int i = 0; i < tokens.length; ++i) {
            int token = tokens[i];
            Map<String, Pair<String, ITypeSymbol>> map = new HashMap<>();
            Pair<String, ITypeSymbol> pair =
                    pair(migrationFunctionNames[i], primitiveTypes.get(PrimitiveTypeNames.INT));
            map.put("{as (float | int)} x {as (float | int)} -> int", pair);
            migrationFunctions.put(token, map);
            dynamicFunctions.put(token, pair);
        }
    }

    @Override
    public void turnIntoMigrationFunctionIfRequired(
            FunctionApplicationDto dto,
            OverloadApplicationDto overloadApplicationDto,
            IOverloadBindings currentBindings,
            ITSPHPAst leftHandSide,
            ITSPHPAst arguments) {

        int operatorType = leftHandSide.getType();

        IFunctionType overload = overloadApplicationDto.overload;
        if (overload != null) {
            if (migrationFunctions.containsKey(operatorType)) {
                Map<String, Pair<String, ITypeSymbol>> overloads = migrationFunctions.get(operatorType);
                String signature = overload.getSignature();
                Pair<String, ITypeSymbol> pair = overloads.get(signature);
                switchToMigrationFunction(dto, currentBindings, leftHandSide, pair);
            }

            if (dto.name != null) {
                if (dto.name.equals("oldSchoolAddition")
                        || dto.name.equals("oldSchoolSubtraction")
                        || dto.name.equals("oldSchoolMultiplication")) {
                    handleOldSchoolFloatArithmetic(dto, overloadApplicationDto, currentBindings, arguments);
                }
            } else if (overload.hasConvertibleParameterTypes()) {
                handleConvertibleTypes(dto, overloadApplicationDto, currentBindings, arguments);
            }
        } else {
            Pair<String, ITypeSymbol> pair = dynamicFunctions.get(operatorType);
            switchToMigrationFunction(dto, currentBindings, leftHandSide, pair);
        }
    }

    private void handleConvertibleTypes(
            FunctionApplicationDto dto,
            OverloadApplicationDto overloadApplicationDto,
            IOverloadBindings currentBindings,
            ITSPHPAst arguments) {

        IOverloadBindings rightBindings = overloadApplicationDto.overload.getOverloadBindings();
        dto.conversions = new HashMap<>();
        List<IVariable> parameters = overloadApplicationDto.overload.getParameters();
        int numberOfParameters = parameters.size();

        Map<Integer, Pair<ITypeSymbol, List<ITypeSymbol>>> runtimeChecks = overloadApplicationDto.runtimeChecks;
        if (runtimeChecks == null) {
            runtimeChecks = new HashMap<>(0);
        }

        for (int i = 0; i < numberOfParameters; ++i) {
            IVariable variable = parameters.get(i);
            String typeVariable = rightBindings.getTypeVariable(variable.getAbsoluteName());
            if (rightBindings.hasUpperTypeBounds(typeVariable)) {
                IIntersectionTypeSymbol upperTypeBounds = rightBindings.getUpperTypeBounds(typeVariable);
                ITypeSymbol next = upperTypeBounds.getTypeSymbols().values().iterator().next();
                if (next instanceof IConvertibleTypeSymbol) {
                    IConvertibleTypeSymbol convertibleTypeSymbol = (IConvertibleTypeSymbol) next;
                    IIntersectionTypeSymbol targetType = convertibleTypeSymbol.getUpperTypeBounds();
                    ITypeSymbol argumentType = getTypeSymbol(currentBindings, arguments.getChild(i));
                    TypeHelperDto result = typeHelper.isFirstSameOrSubTypeOfSecond(argumentType, targetType);
                    if (result.relation != ERelation.HAS_RELATION) {
                        SortedSet<String> ifTypes = new TreeSet<>();
                        if (runtimeChecks.containsKey(i)) {
                            Pair<ITypeSymbol, List<ITypeSymbol>> pair = runtimeChecks.get(i);
                            if (pair.second != null) {
                                //runtime checks are covered in the as operator via if types
                                dto.runtimeChecks.remove(i);
                                for (ITypeSymbol typeSymbol : pair.second) {
                                    ifTypes.add(typeSymbol.getAbsoluteName());
                                }
                            }
                        }
                        dto.conversions.put(i, pair(targetType.toString(), ifTypes));
                    } else {
                        //no conversion required since it is already a subtype
                    }
                }
            }
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
            FunctionApplicationDto dto,
            OverloadApplicationDto overloadApplicationDto,
            IOverloadBindings currentBindings,
            ITSPHPAst arguments) {

        ITypeSymbol lhsTypeSymbol = getTypeSymbol(overloadApplicationDto, 0, currentBindings, arguments.getChild(0));
        ITypeSymbol rhsTypeSymbol = getTypeSymbol(overloadApplicationDto, 1, currentBindings, arguments.getChild(1));
        String lhsAbsoluteName = lhsTypeSymbol.getAbsoluteName();
        String rhsAbsoluteName = rhsTypeSymbol.getAbsoluteName();

        //we can use the regular operator for float x int or int x float
        if (isFloatAndIntOrIntAndFloat(lhsAbsoluteName, rhsAbsoluteName)) {
            dto.name = null;
            dto.returnRuntimeCheck = null;
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
            typeSymbol = getTypeSymbol(currentBindings, ast);
        }
        return typeSymbol;
    }

    private ITypeSymbol getTypeSymbol(IOverloadBindings currentBindings, ITSPHPAst ast) {
        ITypeSymbol typeSymbol;
        String argumentId = ast.getSymbol().getAbsoluteName();
        String typeVariable = currentBindings.getTypeVariable(argumentId);
        typeSymbol = currentBindings.getLowerTypeBounds(typeVariable);
        if (typeSymbol == null) {
            typeSymbol = currentBindings.getUpperTypeBounds(typeVariable);
        }
        return typeSymbol;
    }

}
