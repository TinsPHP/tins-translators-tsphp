/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.gen.TokenTypes;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.IFunctionType;
import ch.tsphp.tinsphp.common.inference.constraints.ITypeVariableReference;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.inference.constraints.OverloadApplicationDto;
import ch.tsphp.tinsphp.common.symbols.IConvertibleTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IIntersectionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.PrimitiveTypeNames;
import ch.tsphp.tinsphp.common.translation.dtos.FunctionApplicationDto;
import ch.tsphp.tinsphp.common.utils.ERelation;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.common.utils.Pair;
import ch.tsphp.tinsphp.common.utils.TypeHelperDto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static ch.tsphp.tinsphp.common.utils.Pair.pair;

public class TSPHPOperatorHelper implements IOperatorHelper
{
    private final ITypeHelper typeHelper;
    private final Map<String, ITypeSymbol> primitiveTypes;
    private final ITypeTransformer typeTransformer;
    private final IRuntimeCheckProvider runtimeCheckProvider;

    private final Map<Integer, Map<String, Pair<String, ITypeSymbol>>> migrationFunctions = new HashMap<>();
    private final Map<Integer, Pair<String, ITypeSymbol>> dynamicFunctions = new HashMap<>();

    public TSPHPOperatorHelper(
            ITypeHelper theTypeHelper,
            Map<String, ITypeSymbol> thePrimitiveTypes,
            IRuntimeCheckProvider theRuntimeCheckProvider,
            ITypeTransformer theTypeTransformer) {
        typeHelper = theTypeHelper;
        primitiveTypes = thePrimitiveTypes;
        runtimeCheckProvider = theRuntimeCheckProvider;
        typeTransformer = theTypeTransformer;
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
            map.put("(array | {as int}) x (array | {as int}) -> int", pair);
            migrationFunctions.put(token, map);
            dynamicFunctions.put(token, pair);
        }
    }

    @Override
    public void turnIntoMigrationFunctionIfRequired(
            FunctionApplicationDto dto,
            OverloadApplicationDto overloadApplicationDto,
            IBindingCollection currentBindings,
            ITSPHPAst leftHandSide,
            ITSPHPAst argumentsAst) {

        int operatorType = leftHandSide.getType();

        IFunctionType overload = overloadApplicationDto.overload;
        if (overload != null) {
            if (migrationFunctions.containsKey(operatorType)) {
                Map<String, Pair<String, ITypeSymbol>> overloads = migrationFunctions.get(operatorType);
                String signature = overload.getSignature();
                Pair<String, ITypeSymbol> pair = overloads.get(signature);
                switchToMigrationFunction(dto, currentBindings, leftHandSide, argumentsAst, pair);
            }

            if (dto.name == null && overload.hasConvertibleParameterTypes()) {
                handleConvertibleTypes(dto, overloadApplicationDto, currentBindings, argumentsAst);
            }
        } else {
            Pair<String, ITypeSymbol> pair = dynamicFunctions.get(operatorType);
            switchToMigrationFunction(dto, currentBindings, leftHandSide, argumentsAst, pair);
        }
    }

    private void handleConvertibleTypes(
            FunctionApplicationDto dto,
            OverloadApplicationDto overloadApplicationDto,
            IBindingCollection currentBindings,
            ITSPHPAst argumentsAst) {

        IBindingCollection rightBindings = overloadApplicationDto.overload.getBindingCollection();
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
                    ITypeSymbol argumentType = getTypeSymbol(currentBindings, argumentsAst.getChild(i));
                    TypeHelperDto result = typeHelper.isFirstSameOrSubTypeOfSecond(argumentType, targetType);
                    //no conversion required if it is already a subtype
                    if (result.relation != ERelation.HAS_RELATION) {
                        SortedSet<String> ifTypes = new TreeSet<>();
                        if (runtimeChecks.containsKey(i)) {
                            Pair<ITypeSymbol, List<ITypeSymbol>> pair = runtimeChecks.get(i);
                            if (pair.second != null) {
                                //runtime checks are covered in the as operator via if types
                                for (ITypeSymbol typeSymbol : pair.second) {
                                    String typeName;
                                    if (typeSymbol instanceof IConvertibleTypeSymbol) {
                                        Pair<ITypeSymbol, Boolean> nameTransformerPair = typeTransformer.getType(
                                                (IConvertibleTypeSymbol) typeSymbol);
                                        typeName = nameTransformerPair.first.getAbsoluteName();
                                    } else {
                                        typeName = typeSymbol.getAbsoluteName();
                                    }
                                    ifTypes.add(typeName);
                                }
                            }
                        }
                        Pair<ITypeSymbol, Boolean> nameTransformerPair = typeTransformer.getType(targetType);
                        StringBuilder stringBuilder = new StringBuilder(dto.arguments.get(i).toString())
                                .append(" as ").append(nameTransformerPair.first.getAbsoluteName());
                        Iterator<String> iterator = ifTypes.iterator();
                        if (iterator.hasNext()) {
                            stringBuilder.append(" if ").append(iterator.next());
                        }
                        while (iterator.hasNext()) {
                            stringBuilder.append(", ").append(iterator.next());
                        }
                        dto.arguments.set(i, stringBuilder);
                        if (dto.argumentsRequiringConversion == null) {
                            dto.argumentsRequiringConversion = new HashSet<>();
                        }
                        dto.argumentsRequiringConversion.add(i);
                    }
                }
            }
        }
    }

    private void switchToMigrationFunction(
            FunctionApplicationDto functionApplicationDto,
            IBindingCollection currentBindings,
            ITSPHPAst leftHandSide,
            ITSPHPAst arguments, Pair<String, ITypeSymbol> pair) {

        if (pair != null) {
            boolean needMigrationFunction = true;

            int operatorType = leftHandSide.getType();
            if (operatorType == TokenTypes.Plus
                    || operatorType == TokenTypes.Minus
                    || operatorType == TokenTypes.Multiply) {
                ITypeSymbol lhs = getTypeSymbol(currentBindings, arguments.getChild(0));
                ITypeSymbol rhs = getTypeSymbol(currentBindings, arguments.getChild(1));
                ITypeSymbol intTypeSymbol = primitiveTypes.get(PrimitiveTypeNames.INT);
                ITypeSymbol floatTypeSymbol = primitiveTypes.get(PrimitiveTypeNames.FLOAT);
                if ((typeHelper.areSame(lhs, intTypeSymbol) || typeHelper.areSame(lhs, floatTypeSymbol))
                        && (typeHelper.areSame(rhs, intTypeSymbol) || typeHelper.areSame(rhs, floatTypeSymbol))) {
                    needMigrationFunction = false;
                }
            }

            if (needMigrationFunction) {
                functionApplicationDto.name = pair.first;
                String leftHandSideId = leftHandSide.getSymbol().getAbsoluteName();
                ITypeVariableReference reference = currentBindings.getTypeVariableReference(leftHandSideId);
                String lhsTypeVariable = reference.getTypeVariable();
                ITypeSymbol returnType = pair.second;
                if (reference.hasFixedType()) {
                    ITypeSymbol typeSymbol = currentBindings.getLowerTypeBounds(lhsTypeVariable);
                    if (typeSymbol == null) {
                        typeSymbol = currentBindings.getUpperTypeBounds(lhsTypeVariable);
                    }
                    TypeHelperDto result = typeHelper.isFirstSameOrSubTypeOfSecond(returnType, typeSymbol);
                    //if the left hand side is more specific than the return type then we need to cast
                    if (result.relation == ERelation.HAS_NO_RELATION) {
                        functionApplicationDto.returnRuntimeCheck = runtimeCheckProvider.getTypeCheck(
                                leftHandSide, "%returnRuntimeCheck%", typeSymbol).toString();
                    }
                } else {
                    //if overload is parametric polymorphic then we need to cast to the parametric type
                    functionApplicationDto.returnRuntimeCheck = "cast(%returnRuntimeCheck%, " + lhsTypeVariable + ")";
                }
            }
        }
    }

    private ITypeSymbol getTypeSymbol(IBindingCollection currentBindings, ITSPHPAst ast) {
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
