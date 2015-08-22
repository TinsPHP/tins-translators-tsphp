/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.TinsPHPConstants;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.IFunctionType;
import ch.tsphp.tinsphp.common.inference.constraints.ITypeVariableReference;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.inference.constraints.OverloadApplicationDto;
import ch.tsphp.tinsphp.common.symbols.IExpressionVariableSymbol;
import ch.tsphp.tinsphp.common.symbols.IMethodSymbol;
import ch.tsphp.tinsphp.common.symbols.IMinimalMethodSymbol;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.common.translation.IDtoCreator;
import ch.tsphp.tinsphp.common.translation.ITranslatorController;
import ch.tsphp.tinsphp.common.translation.dtos.FunctionApplicationDto;
import ch.tsphp.tinsphp.common.translation.dtos.OverloadDto;
import ch.tsphp.tinsphp.common.translation.dtos.TranslationScopeDto;
import ch.tsphp.tinsphp.common.translation.dtos.VariableDto;
import ch.tsphp.tinsphp.common.utils.Pair;
import ch.tsphp.tinsphp.translators.tsphp.issues.IOutputIssueMessageProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class TranslatorController implements ITranslatorController
{
    private final IPrecedenceHelper precedenceHelper;
    private final ITempVariableHelper tempVariableHelper;
    private final IOperatorHelper operatorHelper;
    private final IDtoCreator dtoCreator;
    private final IOutputIssueMessageProvider messageProvider;
    private final IRuntimeCheckProvider runtimeCheckProvider;
    private Map<String, SortedMap<String, OverloadDto>> methodDtosMap;

    public TranslatorController(
            IPrecedenceHelper thePrecedenceHelper,
            ITempVariableHelper theTempVariableHelper,
            IOperatorHelper theOperatorHelper,
            IDtoCreator theDtoCreator,
            IRuntimeCheckProvider theRuntimeCheckProvider,
            IOutputIssueMessageProvider theMessageProvider) {
        precedenceHelper = thePrecedenceHelper;
        tempVariableHelper = theTempVariableHelper;
        operatorHelper = theOperatorHelper;
        dtoCreator = theDtoCreator;
        runtimeCheckProvider = theRuntimeCheckProvider;
        messageProvider = theMessageProvider;
    }

    @Override
    public void setMethodSymbols(List<IMethodSymbol> methodSymbols) {
        methodDtosMap = new HashMap<>();
        for (IMethodSymbol methodSymbol : methodSymbols) {
            methodDtosMap.put(methodSymbol.getAbsoluteName(), dtoCreator.createOverloadDtos(methodSymbol));
        }
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
    public SortedMap<String, OverloadDto> getOverloadDtos(ITSPHPAst identifier) {
        IMethodSymbol methodSymbol = (IMethodSymbol) identifier.getSymbol();
        return methodDtosMap.get(methodSymbol.getAbsoluteName());
    }

    @Override
    public VariableDto createVariableDtoForConstant(IBindingCollection bindings, ITSPHPAst constant) {
        return dtoCreator.createVariableDtoForConstant(bindings, (IVariable) constant.getSymbol());
    }

    @Override
    public VariableDto createVariableDto(IBindingCollection bindings, ITSPHPAst variableId) {
        return dtoCreator.createVariableDto(bindings, (IVariable) variableId.getSymbol());
    }

    @Override
    public FunctionApplicationDto getFunctionApplication(
            TranslationScopeDto translationScopeDto, ITSPHPAst functionCall, List<Object> arguments) {

        FunctionApplicationDto dto = null;
        String absoluteName = functionCall.getSymbol().getAbsoluteName();
        OverloadApplicationDto appliedOverloadDto
                = translationScopeDto.bindingCollection.getAppliedOverload(absoluteName);
        if (appliedOverloadDto != null) {
            ITSPHPAst identifier = functionCall.getChild(0);
            String name = identifier.getText();
            name = name.substring(0, name.length() - 2);
            if (appliedOverloadDto.overload != null) {
                String suffix = appliedOverloadDto.overload.getSuffix(TSPHPTranslator.TRANSLATOR_ID);
                if (suffix != null) {
                    name += suffix;
                }
            }

            dto = new FunctionApplicationDto(name);
            dto.arguments = arguments;
            applyRuntimeChecks(translationScopeDto, dto, functionCall.getChild(1), appliedOverloadDto);
            String functionName = identifier.getSymbol().getAbsoluteName();
            //TODO TINS-545 dynamic version of functions
            //null check because soft typing might choose the dynamic version
            if (appliedOverloadDto.overload != null && methodDtosMap.containsKey(functionName)) {
                addReturnValueCheckIfRequired(translationScopeDto, dto, functionCall, appliedOverloadDto, functionName);
            }
        }

        return dto;
    }

    private void applyRuntimeChecks(
            TranslationScopeDto translationScopeDto,
            FunctionApplicationDto dto,
            ITSPHPAst argumentsAst,
            OverloadApplicationDto appliedOverloadDto) {

        List<Object> arguments = dto.arguments;
        if (appliedOverloadDto.runtimeChecks != null) {
            for (Map.Entry<Integer, Pair<ITypeSymbol, List<ITypeSymbol>>> entry
                    : appliedOverloadDto.runtimeChecks.entrySet()) {
                int argumentIndex = entry.getKey();
                if (dto.checkedArguments == null || !dto.checkedArguments.contains(argumentIndex)) {
                    Object argument = runtimeCheckProvider.getTypeCheck(
                            translationScopeDto,
                            argumentsAst.getChild(argumentIndex),
                            arguments.get(argumentIndex),
                            entry.getValue().first);
                    arguments.set(argumentIndex, argument);
                }
            }
        }
    }

    private void addReturnValueCheckIfRequired(
            TranslationScopeDto translationScopeDto,
            FunctionApplicationDto dto,
            ITSPHPAst leftHandSide,
            OverloadApplicationDto appliedOverloadDto,
            String functionName) {
        String signature = appliedOverloadDto.overload.getSignature();
        SortedMap<String, OverloadDto> overloads = methodDtosMap.get(functionName);
        // a direct recursive function will use the assign operator as overload and
        // hence is not part of the rewritten overloads
        if (overloads.containsKey(signature)) {
            IBindingCollection rewrittenBindingCollection
                    = overloads.get(signature).translationScopeDto.bindingCollection;
            ITypeVariableReference returnReference
                    = rewrittenBindingCollection.getTypeVariableReference(TinsPHPConstants.RETURN_VARIABLE_NAME);
            IUnionTypeSymbol returnType
                    = rewrittenBindingCollection.getLowerTypeBounds(returnReference.getTypeVariable());
            runtimeCheckProvider.addReturnValueCheck(
                    translationScopeDto, dto, leftHandSide, returnType, returnReference.hasFixedType());
        }
    }

    @Override
    public FunctionApplicationDto getOperatorApplication(
            TranslationScopeDto translationScopeDto, ITSPHPAst operator, List<Object> arguments) {
        FunctionApplicationDto dto = null;

        String absoluteName = operator.getSymbol().getAbsoluteName();
        OverloadApplicationDto appliedOverloadDto
                = translationScopeDto.bindingCollection.getAppliedOverload(absoluteName);
        if (appliedOverloadDto != null) {
            dto = new FunctionApplicationDto();
            dto.arguments = arguments;
            operatorHelper.turnIntoMigrationFunctionIfRequired(
                    translationScopeDto, dto, appliedOverloadDto, operator, operator);
            //runtime checks are not required if a migration function is used.
            if (dto.name == null) {
                applyRuntimeChecks(translationScopeDto, dto, operator, appliedOverloadDto);
            }
        }

        return dto;
    }

    @Override
    public String getErrMessageFunctionApplication(
            IBindingCollection bindings, ITSPHPAst functionCall, ITSPHPAst identifier) {
        List<String> arguments = getArguments(bindings, functionCall.getChild(1));
        List<String> overloads = getOverloads(functionCall);
        return messageProvider.getWrongApplication("wrongFunctionUsage", identifier.getText(), arguments, overloads);
    }

    private List<String> getOverloads(ITSPHPAst functionCall) {
        List<String> overloads = new ArrayList<>();
        IMinimalMethodSymbol methodSymbol = ((IExpressionVariableSymbol) functionCall.getSymbol()).getMethodSymbol();
        for (IFunctionType functionType : methodSymbol.getOverloads()) {
            overloads.add(functionType.getSignature());
        }
        return overloads;
    }

    private List<String> getArguments(IBindingCollection bindings, ITSPHPAst argumentsAst) {
        List<String> arguments = new ArrayList<>();
        int numberOfArguments = argumentsAst.getChildCount();
        if (numberOfArguments == 0) {
            arguments.add("void");
        } else {
            String typeVariable = bindings.getTypeVariable(argumentsAst.getChild(0).getSymbol().getAbsoluteName());
            arguments.add(bindings.getLowerTypeBounds(typeVariable).getAbsoluteName());
            for (int i = 1; i < numberOfArguments; ++i) {
                typeVariable = bindings.getTypeVariable(argumentsAst.getChild(i).getSymbol().getAbsoluteName());
                arguments.add(bindings.getLowerTypeBounds(typeVariable).getAbsoluteName());
            }
        }
        return arguments;
    }

    @Override
    public String getErrMessageOperatorApplication(IBindingCollection bindings, ITSPHPAst operator) {
        List<String> arguments = getArguments(bindings, operator);
        List<String> overloads = getOverloads(operator);
        return messageProvider.getWrongApplication("wrongOperatorUsage", operator.getText(), arguments, overloads);
    }
}
