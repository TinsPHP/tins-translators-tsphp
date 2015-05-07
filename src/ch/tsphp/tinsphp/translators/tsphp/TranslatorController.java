/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ISymbol;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.TinsPHPConstants;
import ch.tsphp.tinsphp.common.inference.constraints.IFunctionType;
import ch.tsphp.tinsphp.common.inference.constraints.IOverloadBindings;
import ch.tsphp.tinsphp.common.inference.constraints.ITypeVariableReference;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.symbols.IMethodSymbol;
import ch.tsphp.tinsphp.common.symbols.IVariableSymbol;
import ch.tsphp.tinsphp.common.translation.ITranslatorController;
import ch.tsphp.tinsphp.common.translation.dtos.MethodDto;
import ch.tsphp.tinsphp.common.translation.dtos.ParameterDto;
import ch.tsphp.tinsphp.common.translation.dtos.TypeDto;
import ch.tsphp.tinsphp.common.translation.dtos.TypeParameterDto;
import ch.tsphp.tinsphp.common.translation.dtos.VariableDto;
import ch.tsphp.tinsphp.common.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static ch.tsphp.tinsphp.common.utils.Pair.pair;

public class TranslatorController implements ITranslatorController
{
    private final IPrecedenceHelper precedenceHelper;
    private final ITempVariableHelper tempVariableHelper;
    private final IOperatorHelper operatorHelper;

    public TranslatorController(IPrecedenceHelper thePrecedenceHelper, ITempVariableHelper theTempVariableHelper,
            IOperatorHelper theOperatorHelper) {
        precedenceHelper = thePrecedenceHelper;
        tempVariableHelper = theTempVariableHelper;
        operatorHelper = theOperatorHelper;
    }

    @Override
    public boolean needParentheses(ITSPHPAst expression) {
        return precedenceHelper.needParentheses(expression);
    }

    @Override
    public String getTempVariableName(ITSPHPAst expression) {
        return tempVariableHelper.getTempVariableName(expression);
    }

    @Override
    public String getTempVariableNameIfNotVariable(ITSPHPAst expression) {
        return tempVariableHelper.getTempVariableNameIfNotVariable(expression);
    }

    @Override
    public List<MethodDto> createMethodDtos(ITSPHPAst identifier) {
        List<MethodDto> methodDtos = new ArrayList<>();

        IMethodSymbol methodSymbol = (IMethodSymbol) identifier.getSymbol();
        String name = methodSymbol.getName();
        //remove () at the end
        name = name.substring(0, name.length() - 2);
        int count = 0;
        List<IFunctionType> overloads = methodSymbol.getOverloads();
        int numberOfOverloads = overloads.size();
        for (IFunctionType overload : overloads) {
            Pair<MethodDto, Integer> pair = createMethodDto(name, count, overload, methodSymbol, numberOfOverloads);
            count = pair.second;
            methodDtos.add(pair.first);
        }
        return methodDtos;
    }

    private Pair<MethodDto, Integer> createMethodDto(
            String name, int count, IFunctionType overload, IMethodSymbol methodSymbol, int numberOfOverloads) {
        Map<String, List<ISymbol>> symbols = methodSymbol.getDefinitionScope().getSymbols();
        IOverloadBindings bindings = overload.getBindings();
        List<IVariable> parameters = overload.getParameters();
        int numberOfParameters = parameters.size();

        String newName = name;
        int numbering = count;
        if (numberOfOverloads != 1) {
            while (symbols.containsKey(name + numbering)) {
                ++numbering;
            }
            newName = name + numbering;
            overload.addSuffix(TSPHPTranslator.TRANSLATOR_ID, String.valueOf(numbering));
            symbols.put(newName, Arrays.asList((ISymbol) methodSymbol));
        }

        Set<String> typeVariablesAdded = new HashSet<>(numberOfParameters + 1);
        List<TypeParameterDto> typeParameters = new ArrayList<>(numberOfParameters + 1);
        TypeDto returnType = createTypeDto(
                TinsPHPConstants.RETURN_VARIABLE_NAME, bindings, typeParameters, typeVariablesAdded);

        List<ParameterDto> parameterDtos = new ArrayList<>();
        for (IVariable parameter : parameters) {
            parameterDtos.add(new ParameterDto(
                    createTypeDto(parameter.getAbsoluteName(), bindings, typeParameters, typeVariablesAdded),
                    parameter.getName(),
                    null
            ));
        }

        if (typeParameters.isEmpty()) {
            typeParameters = null;
        }
        MethodDto methodDto = new MethodDto(returnType, newName, typeParameters, parameterDtos, bindings);
        return pair(methodDto, numbering);
    }

    private TypeDto createTypeDto(
            String variableId,
            IOverloadBindings bindings,
            List<TypeParameterDto> typeParameters,
            Set<String> typeVariablesAdded) {

        ITypeVariableReference reference = bindings.getTypeVariableReference(variableId);
        TypeDto typeDto = createTypeDto(reference, bindings);
        if (!reference.hasFixedType()) {
            String typeVariable = typeDto.type;
            if (!typeVariablesAdded.contains(typeVariable)) {
                typeVariablesAdded.add(typeVariable);
                List<String> lowerBounds = null;
                if (bindings.hasLowerBounds(typeVariable)) {
                    lowerBounds = new ArrayList<>();
                    if (bindings.hasLowerTypeBounds(typeVariable)) {
                        SortedSet<String> sortedSet = new TreeSet<>(
                                bindings.getLowerTypeBounds(typeVariable).getTypeSymbols().keySet());
                        lowerBounds.addAll(sortedSet);
                    }
                    if (bindings.hasLowerRefBounds(typeVariable)) {
                        SortedSet<String> sortedSet = new TreeSet<>(bindings.getLowerRefBounds(typeVariable));
                        lowerBounds.addAll(sortedSet);
                    }
                }
                List<String> upperBounds = null;
                if (bindings.hasUpperTypeBounds(typeVariable)) {
                    upperBounds = new ArrayList<>();
                    upperBounds.addAll(bindings.getUpperTypeBounds(typeVariable).getTypeSymbols().keySet());
                }
                typeParameters.add(new TypeParameterDto(lowerBounds, typeVariable, upperBounds));
            }
        }
        return typeDto;
    }

    private TypeDto createTypeDto(ITypeVariableReference reference, IOverloadBindings bindings) {
        String typeVariable = reference.getTypeVariable();
        String type;
        if (reference.hasFixedType()) {
            ITypeSymbol typeSymbol;
            if (bindings.hasUpperTypeBounds(typeVariable)) {
                typeSymbol = bindings.getUpperTypeBounds(typeVariable);
            } else {
                typeSymbol = bindings.getLowerTypeBounds(typeVariable);
            }
            type = typeSymbol.toString();
        } else {
            type = typeVariable;
        }
        //TODO rstoll TINS-379 find least upper bound
        return new TypeDto(null, type, null);
    }

    @Override
    public VariableDto createVariableDtoForConstant(IOverloadBindings bindings, ITSPHPAst constantId) {
        IVariableSymbol variableSymbol = (IVariableSymbol) constantId.getSymbol();
        ITypeVariableReference reference = bindings.getTypeVariableReference(variableSymbol.getAbsoluteName());
        String name = variableSymbol.getName();
        name = name.substring(0, name.length() - 1);
        return new VariableDto(createTypeDto(reference, bindings), name);
    }


    @Override
    public VariableDto createVariableDto(IOverloadBindings bindings, ITSPHPAst variableId) {
        IVariableSymbol variableSymbol = (IVariableSymbol) variableId.getSymbol();
        ITypeVariableReference reference = bindings.getTypeVariableReference(variableSymbol.getAbsoluteName());
        return new VariableDto(createTypeDto(reference, bindings), variableSymbol.getName());
    }

    @Override
    public String getOverloadName(IOverloadBindings bindings, ITSPHPAst functionCall, ITSPHPAst identifier) {
        IFunctionType appliedOverload = bindings.getAppliedOverload(functionCall.getSymbol().getAbsoluteName());
        String suffix = appliedOverload.getSuffix(TSPHPTranslator.TRANSLATOR_ID);

        String name = identifier.getText();
        name = name.substring(0, name.length() - 2);
        if (suffix != null) {
            name += suffix;
        }
        return name;
    }

    @Override
    public String getMigrationFunction(IOverloadBindings bindings, ITSPHPAst operator) {
        IFunctionType appliedOverload = bindings.getAppliedOverload(operator.getSymbol().getAbsoluteName());
        return operatorHelper.getMigrationFunction(operator.getType(), appliedOverload);
    }
}
