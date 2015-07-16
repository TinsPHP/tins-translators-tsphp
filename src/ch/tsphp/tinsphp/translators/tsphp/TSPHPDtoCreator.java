/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.symbols.ISymbol;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.TinsPHPConstants;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.IFunctionType;
import ch.tsphp.tinsphp.common.inference.constraints.ITypeVariableReference;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.symbols.IMethodSymbol;
import ch.tsphp.tinsphp.common.translation.IDtoCreator;
import ch.tsphp.tinsphp.common.translation.dtos.OverloadDto;
import ch.tsphp.tinsphp.common.translation.dtos.ParameterDto;
import ch.tsphp.tinsphp.common.translation.dtos.TypeDto;
import ch.tsphp.tinsphp.common.translation.dtos.TypeParameterDto;
import ch.tsphp.tinsphp.common.translation.dtos.VariableDto;
import ch.tsphp.tinsphp.common.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import static ch.tsphp.tinsphp.common.utils.Pair.pair;

public class TSPHPDtoCreator implements IDtoCreator
{
    private final ITempVariableHelper tempVariableHelper;

    public TSPHPDtoCreator(ITempVariableHelper theTempVariableHelper) {
        tempVariableHelper = theTempVariableHelper;
    }

    @Override
    public List<OverloadDto> createOverloadDtos(IMethodSymbol methodSymbol) {
        List<OverloadDto> methodDtos = new ArrayList<>();
        String name = methodSymbol.getName();
        //remove () at the end
        name = name.substring(0, name.length() - 2);
        int count = 0;
        Collection<IFunctionType> overloads = methodSymbol.getOverloads();

        int numberOfOverloads = overloads.size();

        //mainly due to testing, we want a deterministic output in order that we can test
        SortedMap<String, IFunctionType> sortedOverloads = new TreeMap<>();
        for (IFunctionType overload : overloads) {
            sortedOverloads.put(overload.getSignature(), overload);
        }

        for (IFunctionType overload : sortedOverloads.values()) {
            Pair<OverloadDto, Integer> pair = createOverloadDto(name, count, overload, methodSymbol, numberOfOverloads);
            count = pair.second;
            methodDtos.add(pair.first);
        }

        return methodDtos;
    }

    private Pair<OverloadDto, Integer> createOverloadDto(
            String name, int count, IFunctionType overload, IMethodSymbol methodSymbol, int numberOfOverloads) {
        Map<String, List<ISymbol>> symbols = methodSymbol.getDefinitionScope().getSymbols();
        IBindingCollection bindings = overload.getBindingCollection();
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

        List<ParameterDto> parameterDtos = new ArrayList<>();
        for (IVariable parameter : parameters) {
            ParameterDto parameterDto = createParameterDto(
                    methodSymbol, bindings, typeVariablesAdded, typeParameters, parameter);
            parameterDtos.add(parameterDto);
        }

        Set<String> nonFixedTypeParameters = overload.getNonFixedTypeParameters();
        VariableDto returnVariable = createReturnVariable(
                bindings, typeVariablesAdded, typeParameters, nonFixedTypeParameters);

        for (String typeParameter : nonFixedTypeParameters) {
            if (!typeVariablesAdded.contains(typeParameter)) {
                TypeParameterDto typeParameterDto = createTypeParameterDto(bindings, typeParameter);
                typeParameters.add(typeParameterDto);
            }
        }

        if (typeParameters.isEmpty()) {
            typeParameters = null;
        }
        OverloadDto methodDto = new OverloadDto(returnVariable, newName, typeParameters, parameterDtos, bindings);
        return pair(methodDto, numbering);
    }

    private ParameterDto createParameterDto(IMethodSymbol methodSymbol, IBindingCollection bindings, Set<String>
            typeVariablesAdded, List<TypeParameterDto> typeParameters, IVariable parameter) {
        ParameterDto parameterDto;
        String parameterName = parameter.getName();
        ITypeSymbol typeSymbol = parameter.getType();
        String absoluteName = parameter.getAbsoluteName();
        if (typeSymbol == null) {
            parameterDto = new ParameterDto(
                    createTypeDto(absoluteName, bindings, typeParameters, typeVariablesAdded),
                    parameterName,
                    null,
                    null,
                    null
            );
        } else {
            String tempVariableName
                    = tempVariableHelper.getTempNameIfAlreadyExists(parameterName + "_0", methodSymbol);
            parameterDto = new ParameterDto(
                    new TypeDto(null, typeSymbol.getAbsoluteName(), null),
                    tempVariableName,
                    null,
                    createTypeDto(absoluteName, bindings, typeParameters, typeVariablesAdded),
                    parameterName
            );
        }
        return parameterDto;
    }

    private VariableDto createReturnVariable(IBindingCollection bindings, Set<String> typeVariablesAdded, List
            <TypeParameterDto> typeParameters, Set<String> nonFixedTypeParameters) {
        ITypeVariableReference reference = bindings.getTypeVariableReference(TinsPHPConstants.RETURN_VARIABLE_NAME);
        String returnTypeVariable = reference.getTypeVariable();
        VariableDto returnVariable;
        TypeParameterDto typeParamDto = createTypeParameterDto(bindings, returnTypeVariable);
        if (nonFixedTypeParameters.contains(returnTypeVariable)) {
            if (!typeVariablesAdded.contains(returnTypeVariable)) {
                typeVariablesAdded.add(returnTypeVariable);
                typeParameters.add(typeParamDto);
            }
            returnVariable = new VariableDto(null, new TypeDto(null, returnTypeVariable, null), null);
        } else {
            returnVariable = new VariableDto(typeParamDto, null, null);
        }
        return returnVariable;
    }

    private TypeDto createTypeDto(
            String variableId,
            IBindingCollection bindings,
            List<TypeParameterDto> typeParameters,
            Set<String> typeVariablesAdded) {

        ITypeVariableReference reference = bindings.getTypeVariableReference(variableId);
        TypeDto typeDto = createParameterTypeDto(reference, bindings);
        if (!reference.hasFixedType()) {
            String typeVariable = typeDto.type;
            if (!typeVariablesAdded.contains(typeVariable)) {
                typeVariablesAdded.add(typeVariable);
                TypeParameterDto typeParameterDto = createTypeParameterDto(bindings, typeVariable);
                typeParameters.add(typeParameterDto);
            }
        }
        return typeDto;
    }

    private TypeParameterDto createTypeParameterDto(IBindingCollection bindings, String typeVariable) {
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
        return new TypeParameterDto(lowerBounds, typeVariable, upperBounds);
    }

    private TypeDto createParameterTypeDto(ITypeVariableReference reference, IBindingCollection bindings) {
        String typeVariable = reference.getTypeVariable();
        String type;
        if (reference.hasFixedType()) {
            ITypeSymbol typeSymbol;
            if (bindings.hasUpperTypeBounds(typeVariable)) {
                typeSymbol = bindings.getUpperTypeBounds(typeVariable);
            } else {
                typeSymbol = bindings.getLowerTypeBounds(typeVariable);
            }
            //TODO rstoll TINS-379 find least upper bound
            type = typeSymbol.toString();
        } else {
            type = typeVariable;
        }
        return new TypeDto(null, type, null);
    }

    @Override
    public VariableDto createVariableDtoForConstant(IBindingCollection bindings, IVariable constant) {
        String name = constant.getName();
        name = name.substring(0, name.length() - 1);

        return createVariableDto(bindings, constant.getAbsoluteName(), name);
    }

    private VariableDto createVariableDto(IBindingCollection bindings, String absoluteName, String name) {
        ITypeVariableReference reference = bindings.getTypeVariableReference(absoluteName);
        String typeVariable = reference.getTypeVariable();
        TypeParameterDto typeParameterDto = null;
        TypeDto typeDto = null;
        if (typeVariable.startsWith("T")) {
            typeDto = new TypeDto(null, typeVariable, null);
        } else if (reference.hasFixedType()) {
            ITypeSymbol typeSymbol;
            if (bindings.hasLowerTypeBounds(typeVariable)) {
                typeSymbol = bindings.getLowerTypeBounds(typeVariable);
            } else {
                typeSymbol = bindings.getUpperTypeBounds(typeVariable);
            }
            typeDto = new TypeDto(null, typeSymbol.toString(), null);
        } else {
            typeParameterDto = createTypeParameterDto(bindings, typeVariable);
        }
        return new VariableDto(typeParameterDto, typeDto, name);
    }

    @Override
    public VariableDto createVariableDto(IBindingCollection bindings, IVariable variable) {
        return createVariableDto(bindings, variable.getAbsoluteName(), variable.getName());
    }
}
