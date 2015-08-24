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
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.inference.constraints.OverloadApplicationDto;
import ch.tsphp.tinsphp.common.symbols.IConvertibleTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IIntersectionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.ISymbolFactory;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.PrimitiveTypeNames;
import ch.tsphp.tinsphp.common.translation.dtos.FunctionApplicationDto;
import ch.tsphp.tinsphp.common.translation.dtos.TranslationScopeDto;
import ch.tsphp.tinsphp.common.utils.ERelation;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.common.utils.Pair;
import ch.tsphp.tinsphp.common.utils.TypeHelperDto;

import java.util.ArrayList;
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
    private final ISymbolFactory symbolFactory;
    private final ITypeHelper typeHelper;
    private final Map<String, ITypeSymbol> primitiveTypes;
    private final ITypeTransformer typeTransformer;
    private final IRuntimeCheckProvider runtimeCheckProvider;

    private final Map<Integer, Map<String, Pair<String, ITypeSymbol>>> migrationFunctions = new HashMap<>();
    private final Map<Integer, Pair<String, ITypeSymbol>> dynamicFunctions = new HashMap<>();

    public TSPHPOperatorHelper(
            ISymbolFactory theSymbolFactory,
            ITypeHelper theTypeHelper,
            Map<String, ITypeSymbol> thePrimitiveTypes,
            IRuntimeCheckProvider theRuntimeCheckProvider,
            ITypeTransformer theTypeTransformer) {
        symbolFactory = theSymbolFactory;
        typeHelper = theTypeHelper;
        primitiveTypes = thePrimitiveTypes;
        runtimeCheckProvider = theRuntimeCheckProvider;
        typeTransformer = theTypeTransformer;
        init();
    }

    private void init() {
        Map<String, Pair<String, ITypeSymbol>> map;
        Pair<String, ITypeSymbol> pair;

        int[] tokens = new int[]{TokenTypes.Plus, TokenTypes.Minus, TokenTypes.Multiply};
        String[] migrationFunctionNames = new String[]{
                "oldSchoolAddition", "oldSchoolSubtraction", "oldSchoolMultiplication"
        };

        for (int i = 0; i < tokens.length; ++i) {
            int token = tokens[i];
            map = new HashMap<>();
            pair = pair(migrationFunctionNames[i], primitiveTypes.get(PrimitiveTypeNames.NUM));
            map.put("{as T} x {as T} -> T \\ T <: (float | int)", pair);
            migrationFunctions.put(token, map);
            dynamicFunctions.put(token, pair);
        }

        map = new HashMap<>();
        IUnionTypeSymbol intOrFalse = symbolFactory.createUnionTypeSymbol();
        intOrFalse.addTypeSymbol(primitiveTypes.get(PrimitiveTypeNames.FLOAT));
        intOrFalse.addTypeSymbol(primitiveTypes.get(PrimitiveTypeNames.FALSE_TYPE));
        pair = new Pair<String, ITypeSymbol>("oldSchoolIntDivide", intOrFalse);
        map.put("int x int -> (falseType | float | int)", pair);
        IUnionTypeSymbol floatOrFalse = symbolFactory.createUnionTypeSymbol();
        floatOrFalse.addTypeSymbol(primitiveTypes.get(PrimitiveTypeNames.FLOAT));
        floatOrFalse.addTypeSymbol(primitiveTypes.get(PrimitiveTypeNames.FALSE_TYPE));
        pair = new Pair<String, ITypeSymbol>("oldSchoolFloatDivide", floatOrFalse);
        map.put("float x {as (float | int)} -> (falseType | float)", pair);
        map.put("{as (float | int)} x float -> (falseType | float)", pair);
        IUnionTypeSymbol numOrFalse = symbolFactory.createUnionTypeSymbol();
        numOrFalse.addTypeSymbol(primitiveTypes.get(PrimitiveTypeNames.NUM));
        numOrFalse.addTypeSymbol(primitiveTypes.get(PrimitiveTypeNames.FALSE_TYPE));
        pair = new Pair<String, ITypeSymbol>("oldSchoolDivide", numOrFalse);
        map.put("{as (float | int)} x {as (float | int)} -> (falseType | float | int)", pair);
        migrationFunctions.put(TokenTypes.Divide, map);
        dynamicFunctions.put(TokenTypes.Divide, pair);

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
            map = new HashMap<>();
            pair = pair(migrationFunctionNames[i], primitiveTypes.get(PrimitiveTypeNames.INT));
            map.put("(array | {as int}) x (array | {as int}) -> int", pair);
            migrationFunctions.put(token, map);
            dynamicFunctions.put(token, pair);
        }

        map = new HashMap<>();
        pair = pair("oldSchoolArrayAccess", primitiveTypes.get(PrimitiveTypeNames.MIXED));
        map.put("array x {as int} -> mixed", pair);
        migrationFunctions.put(TokenTypes.ARRAY_ACCESS, map);
        dynamicFunctions.put(TokenTypes.ARRAY_ACCESS, pair);
    }

    @Override
    public void turnIntoMigrationFunctionIfRequired(
            TranslationScopeDto translationScopeDto,
            FunctionApplicationDto dto,
            OverloadApplicationDto overloadApplicationDto,
            ITSPHPAst leftHandSide,
            ITSPHPAst argumentsAst) {

        int operatorType = leftHandSide.getType();

        IFunctionType overload = overloadApplicationDto.overload;
        if (overload != null) {
            if (migrationFunctions.containsKey(operatorType)) {
                Map<String, Pair<String, ITypeSymbol>> overloads = migrationFunctions.get(operatorType);
                String signature = overload.getSignature();
                Pair<String, ITypeSymbol> pair = overloads.get(signature);
                switchToMigrationFunctionIfRequired(translationScopeDto, dto, leftHandSide, argumentsAst, pair);
            } else if (operatorType == TokenTypes.CAST) {
                switchToMigrationFunction(
                        translationScopeDto, dto, leftHandSide, new Pair<String, ITypeSymbol>("oldSchoolCast", null));
                dto.typeParameters = new ArrayList<>();
                ITSPHPAst typeAst = (ITSPHPAst) dto.arguments.remove(0);
                dto.typeParameters.add(typeTransformer.getType(typeAst.getEvalType()).first.getAbsoluteName());
            }

            if (dto.name == null && overload.hasConvertibleParameterTypes()) {
                handleConvertibleTypes(translationScopeDto, dto, overloadApplicationDto, argumentsAst);
            }
        } else {
            Pair<String, ITypeSymbol> pair = dynamicFunctions.get(operatorType);
            switchToMigrationFunctionIfRequired(translationScopeDto, dto, leftHandSide, argumentsAst, pair);
        }
    }

    private void handleConvertibleTypes(
            TranslationScopeDto translationScopeDto,
            FunctionApplicationDto functionApplicationDto,
            OverloadApplicationDto overloadApplicationDto,
            ITSPHPAst argumentsAst) {

        IBindingCollection rightBindings = overloadApplicationDto.overload.getBindingCollection();
        List<IVariable> parameters = overloadApplicationDto.overload.getParameters();
        int numberOfParameters = parameters.size();

        Map<Integer, Pair<ITypeSymbol, List<ITypeSymbol>>> runtimeChecks = overloadApplicationDto.runtimeChecks;

        for (int i = 0; i < numberOfParameters; ++i) {
            IVariable variable = parameters.get(i);
            String typeVariable = rightBindings.getTypeVariable(variable.getAbsoluteName());
            if (rightBindings.hasUpperTypeBounds(typeVariable)) {
                IIntersectionTypeSymbol upperTypeBounds = rightBindings.getUpperTypeBounds(typeVariable);
                ITypeSymbol next = upperTypeBounds.getTypeSymbols().values().iterator().next();
                if (next instanceof IConvertibleTypeSymbol) {
                    IConvertibleTypeSymbol convertibleTypeSymbol = (IConvertibleTypeSymbol) next;
                    IIntersectionTypeSymbol targetType = convertibleTypeSymbol.getUpperTypeBounds();
                    ITypeSymbol argumentType = getTypeSymbol(
                            translationScopeDto.bindingCollection, argumentsAst.getChild(i));
                    TypeHelperDto result = typeHelper.isFirstSameOrSubTypeOfSecond(argumentType, targetType, false);
                    //no conversion required if it is already a subtype
                    if (result.relation != ERelation.HAS_RELATION) {
                        if (runtimeChecks == null) {
                            runtimeChecks = getRuntimeChecksForConversion(
                                    i, argumentType, convertibleTypeSymbol, targetType);
                        }
                        addExplicitConversion(functionApplicationDto, runtimeChecks, i, targetType);
                    }
                }
            }
        }
    }

    private Map<Integer, Pair<ITypeSymbol, List<ITypeSymbol>>> getRuntimeChecksForConversion(
            int argumentIndex,
            ITypeSymbol argumentType,
            IConvertibleTypeSymbol convertibleTypeSymbol,
            IIntersectionTypeSymbol targetType) {

        Map<Integer, Pair<ITypeSymbol, List<ITypeSymbol>>> runtimeChecks;
        runtimeChecks = new HashMap<>(0);
        Pair<ITypeSymbol, Boolean> pair = typeTransformer.getType(argumentType);
        if (pair.second) {
            //must be a union type - otherwise we would have had a relation
            IUnionTypeSymbol unionTypeSymbol = (IUnionTypeSymbol) argumentType;
            Map<String, ITypeSymbol> typeSymbols = unionTypeSymbol.getTypeSymbols();
            int size = typeSymbols.size();
            if (size > 1) {
                List<ITypeSymbol> ifTypes = new ArrayList<>(size);
                for (ITypeSymbol typeSymbol : typeSymbols.values()) {
                    TypeHelperDto result = typeHelper.isFirstSameOrSubTypeOfSecond(typeSymbol, targetType, false);
                    if (result.relation == ERelation.HAS_NO_RELATION) {
                        result = typeHelper.isFirstSameOrSubTypeOfSecond(typeSymbol, convertibleTypeSymbol, false);
                        if (result.relation == ERelation.HAS_RELATION) {
                            ifTypes.add(typeSymbol);
                        }
                    }
                }
                if (!ifTypes.isEmpty()) {
                    runtimeChecks.put(argumentIndex, new Pair<ITypeSymbol, List<ITypeSymbol>>(null, ifTypes));
                }
            }
        }
        return runtimeChecks;
    }

    private void addExplicitConversion(
            FunctionApplicationDto dto,
            Map<Integer, Pair<ITypeSymbol, List<ITypeSymbol>>> runtimeChecks,
            int argumentIndex,
            IIntersectionTypeSymbol targetType) {
        SortedSet<String> ifTypes = new TreeSet<>();
        if (runtimeChecks.containsKey(argumentIndex)) {
            Pair<ITypeSymbol, List<ITypeSymbol>> pair = runtimeChecks.get(argumentIndex);
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
        StringBuilder stringBuilder = new StringBuilder(dto.arguments.get(argumentIndex).toString())
                .append(" as ").append(nameTransformerPair.first.getAbsoluteName());
        Iterator<String> iterator = ifTypes.iterator();
        if (iterator.hasNext()) {
            stringBuilder.append(" if ").append(iterator.next());
        }
        while (iterator.hasNext()) {
            stringBuilder.append(", ").append(iterator.next());
        }
        dto.arguments.set(argumentIndex, stringBuilder);
        if (dto.checkedArguments == null) {
            dto.checkedArguments = new HashSet<>();
        }
        dto.checkedArguments.add(argumentIndex);
    }

    private void switchToMigrationFunctionIfRequired(
            TranslationScopeDto translationScopeDto,
            FunctionApplicationDto functionApplicationDto,
            ITSPHPAst leftHandSide,
            ITSPHPAst arguments,
            Pair<String, ITypeSymbol> pair) {

        if (pair != null) {
            IBindingCollection bindingCollection = translationScopeDto.bindingCollection;
            if (isNotExceptionalCase(leftHandSide, arguments, bindingCollection)) {
                switchToMigrationFunction(translationScopeDto, functionApplicationDto, leftHandSide, pair);
            }
        }
    }

    private boolean isNotExceptionalCase(ITSPHPAst leftHandSide, ITSPHPAst arguments, IBindingCollection
            bindingCollection) {
        boolean needMigrationFunction = true;

        int operatorType = leftHandSide.getType();
        if (operatorType == TokenTypes.Plus
                || operatorType == TokenTypes.Minus
                || operatorType == TokenTypes.Multiply
                || operatorType == TokenTypes.Divide) {
            ITypeSymbol lhs = getTypeSymbol(bindingCollection, arguments.getChild(0));
            ITypeSymbol rhs = getTypeSymbol(bindingCollection, arguments.getChild(1));
            ITypeSymbol intTypeSymbol = primitiveTypes.get(PrimitiveTypeNames.INT);
            ITypeSymbol floatTypeSymbol = primitiveTypes.get(PrimitiveTypeNames.FLOAT);
            if (typeHelper.areSame(lhs, intTypeSymbol) && typeHelper.areSame(rhs, floatTypeSymbol)
                    || typeHelper.areSame(lhs, floatTypeSymbol) && typeHelper.areSame(rhs, intTypeSymbol)) {
                needMigrationFunction = false;
            }
        }
        return needMigrationFunction;
    }

    private void switchToMigrationFunction(
            TranslationScopeDto translationScopeDto,
            FunctionApplicationDto functionApplicationDto,
            ITSPHPAst leftHandSide,
            Pair<String, ITypeSymbol> pair) {
        functionApplicationDto.name = pair.first;
        ITypeSymbol returnType = pair.second;
        runtimeCheckProvider.addReturnValueCheckIfRequired(
                translationScopeDto,
                functionApplicationDto,
                leftHandSide,
                returnType,
                true);
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
