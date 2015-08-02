/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.IFunctionType;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.inference.constraints.OverloadApplicationDto;
import ch.tsphp.tinsphp.common.symbols.IExpressionVariableSymbol;
import ch.tsphp.tinsphp.common.symbols.IMethodSymbol;
import ch.tsphp.tinsphp.common.symbols.IMinimalMethodSymbol;
import ch.tsphp.tinsphp.common.translation.IDtoCreator;
import ch.tsphp.tinsphp.common.translation.ITranslatorController;
import ch.tsphp.tinsphp.common.translation.dtos.FunctionApplicationDto;
import ch.tsphp.tinsphp.common.translation.dtos.OverloadDto;
import ch.tsphp.tinsphp.common.translation.dtos.VariableDto;
import ch.tsphp.tinsphp.common.utils.Pair;
import ch.tsphp.tinsphp.translators.tsphp.issues.IOutputIssueMessageProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslatorController implements ITranslatorController
{
    private final IPrecedenceHelper precedenceHelper;
    private final ITempVariableHelper tempVariableHelper;
    private final IOperatorHelper operatorHelper;
    private final IDtoCreator dtoCreator;
    private final IOutputIssueMessageProvider messageProvider;
    private final IRuntimeCheckProvider runtimeCheckProvider;
    private Map<String, Collection<OverloadDto>> methodDtosMap;

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
    public Collection<OverloadDto> getOverloadDtos(ITSPHPAst identifier) {
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
            IBindingCollection bindings, ITSPHPAst functionCall, List<Object> arguments) {

        FunctionApplicationDto dto = null;

        String absoluteName = functionCall.getSymbol().getAbsoluteName();
        OverloadApplicationDto appliedOverload = bindings.getAppliedOverload(absoluteName);
        if (appliedOverload != null) {
            ITSPHPAst identifier = functionCall.getChild(0);
            String name = identifier.getText();
            name = name.substring(0, name.length() - 2);
            if (appliedOverload.overload != null) {
                String suffix = appliedOverload.overload.getSuffix(TSPHPTranslator.TRANSLATOR_ID);
                if (suffix != null) {
                    name += suffix;
                }
            }

            dto = new FunctionApplicationDto(name);
            dto.arguments = arguments;
            applyRuntimeChecks(dto, functionCall.getChild(1), appliedOverload);
        }

        return dto;
    }

    private void applyRuntimeChecks(
            FunctionApplicationDto dto, ITSPHPAst argumentsAst, OverloadApplicationDto appliedOverload) {
        List<Object> arguments = dto.arguments;
        if (appliedOverload.runtimeChecks != null) {
            for (Map.Entry<Integer, Pair<ITypeSymbol, List<ITypeSymbol>>> entry
                    : appliedOverload.runtimeChecks.entrySet()) {
                int argumentIndex = entry.getKey();
                if (dto.argumentsRequiringConversion == null
                        || !dto.argumentsRequiringConversion.contains(argumentIndex)) {
                    Object argument = runtimeCheckProvider.getTypeCheck(
                            argumentsAst.getChild(argumentIndex), arguments.get(argumentIndex), entry.getValue().first);
                    arguments.set(argumentIndex, argument);
                }
            }
        }
    }

    @Override
    public FunctionApplicationDto getOperatorApplication(
            IBindingCollection bindings, ITSPHPAst operator, List<Object> arguments) {
        FunctionApplicationDto dto = null;

        String absoluteName = operator.getSymbol().getAbsoluteName();
        OverloadApplicationDto appliedOverload = bindings.getAppliedOverload(absoluteName);
        if (appliedOverload != null) {
            dto = new FunctionApplicationDto();
            dto.arguments = arguments;
            operatorHelper.turnIntoMigrationFunctionIfRequired(dto, appliedOverload, bindings, operator, operator);
            applyRuntimeChecks(dto, operator, appliedOverload);
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
