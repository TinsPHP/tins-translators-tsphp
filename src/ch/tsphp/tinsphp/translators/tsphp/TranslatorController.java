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
import ch.tsphp.tinsphp.common.symbols.IContainerTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IConvertibleTypeSymbol;
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
    private final INameTransformer nameTransformer;
    private final IOutputIssueMessageProvider messageProvider;
    private Map<String, Collection<OverloadDto>> methodDtosMap;

    public TranslatorController(
            IPrecedenceHelper thePrecedenceHelper,
            ITempVariableHelper theTempVariableHelper,
            IOperatorHelper theOperatorHelper,
            IDtoCreator theDtoCreator,
            INameTransformer theNameTransformer,
            IOutputIssueMessageProvider theMessageProvider) {
        precedenceHelper = thePrecedenceHelper;
        tempVariableHelper = theTempVariableHelper;
        operatorHelper = theOperatorHelper;
        dtoCreator = theDtoCreator;
        nameTransformer = theNameTransformer;
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
            IBindingCollection bindings, ITSPHPAst functionCall, ITSPHPAst identifier) {

        FunctionApplicationDto dto = null;

        String absoluteName = functionCall.getSymbol().getAbsoluteName();
        OverloadApplicationDto appliedOverload = bindings.getAppliedOverload(absoluteName);
        if (appliedOverload != null) {

            String name = identifier.getText();
            name = name.substring(0, name.length() - 2);
            if (appliedOverload.overload != null) {
                String suffix = appliedOverload.overload.getSuffix(TSPHPTranslator.TRANSLATOR_ID);
                if (suffix != null) {
                    name += suffix;
                }
            }

            Map<Integer, String> runtimeChecks = getRuntimeChecks(appliedOverload);
            dto = new FunctionApplicationDto(name, runtimeChecks, null);
        }

        return dto;
    }

    private Map<Integer, String> getRuntimeChecks(OverloadApplicationDto appliedOverload) {
        Map<Integer, String> runtimeChecks = null;
        if (appliedOverload.runtimeChecks != null) {
            runtimeChecks = new HashMap<>();
            for (Map.Entry<Integer, Pair<ITypeSymbol, List<ITypeSymbol>>> entry
                    : appliedOverload.runtimeChecks.entrySet()) {
                addToRuntimeChecks(runtimeChecks, entry.getKey(), entry.getValue().first);
            }
        }
        return runtimeChecks;
    }

    private void addToRuntimeChecks(Map<Integer, String> runtimeChecks, int argumentNumber, ITypeSymbol typeSymbol) {
        if (typeSymbol instanceof IContainerTypeSymbol) {
            //TODO TINS-604 runtime check with union type
//            IContainerTypeSymbol containerTypeSymbol = (IContainerTypeSymbol) typeSymbol;
//            for (ITypeSymbol innerTypeSymbol : containerTypeSymbol.getTypeSymbols().values()) {
//                addToRuntimeChecks(runtimeChecks, argumentNumber, innerTypeSymbol);
//            }
            runtimeChecks.put(argumentNumber, typeSymbol.getAbsoluteName());
        } else {
            String typeName;
            if (typeSymbol instanceof IConvertibleTypeSymbol) {
                Pair<String, Boolean> pair = nameTransformer.getTypeName((IConvertibleTypeSymbol) typeSymbol);
                typeName = pair.first;
            } else {
                typeName = typeSymbol.getAbsoluteName();
            }
            runtimeChecks.put(argumentNumber, typeName);
        }
    }

    @Override
    public FunctionApplicationDto getOperatorApplication(IBindingCollection bindings, ITSPHPAst operator) {
        FunctionApplicationDto dto = null;

        String absoluteName = operator.getSymbol().getAbsoluteName();
        OverloadApplicationDto appliedOverload = bindings.getAppliedOverload(absoluteName);
        if (appliedOverload != null) {
            dto = new FunctionApplicationDto();
            dto.runtimeChecks = getRuntimeChecks(appliedOverload);
            operatorHelper.turnIntoMigrationFunctionIfRequired(dto, appliedOverload, bindings, operator, operator);
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
