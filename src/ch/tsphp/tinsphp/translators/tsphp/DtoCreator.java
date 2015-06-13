/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.symbols.ISymbol;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.TinsPHPConstants;
import ch.tsphp.tinsphp.common.inference.constraints.IFunctionType;
import ch.tsphp.tinsphp.common.inference.constraints.IOverloadBindings;
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

public class DtoCreator implements IDtoCreator
{
    private final ITempVariableHelper tempVariableHelper;

    public DtoCreator(ITempVariableHelper theTempVariableHelper) {
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
        IOverloadBindings bindings = overload.getOverloadBindings();
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


            parameterDtos.add(parameterDto);
        }

        if (typeParameters.isEmpty()) {
            typeParameters = null;
        }
        OverloadDto methodDto = new OverloadDto(returnType, newName, typeParameters, parameterDtos, bindings);
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
    public VariableDto createVariableDtoForConstant(IOverloadBindings bindings, IVariable constant) {
        ITypeVariableReference reference = bindings.getTypeVariableReference(constant.getAbsoluteName());
        String name = constant.getName();
        name = name.substring(0, name.length() - 1);
        return new VariableDto(createTypeDto(reference, bindings), name);
    }


    @Override
    public VariableDto createVariableDto(IOverloadBindings bindings, IVariable variable) {
        ITypeVariableReference reference = bindings.getTypeVariableReference(variable.getAbsoluteName());
        return new VariableDto(createTypeDto(reference, bindings), variable.getName());
    }
}
