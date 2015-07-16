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
    private Map<String, Collection<OverloadDto>> methodDtosMap;

    public TranslatorController(
            IPrecedenceHelper thePrecedenceHelper,
            ITempVariableHelper theTempVariableHelper,
            IOperatorHelper theOperatorHelper,
            IDtoCreator theDtoCreator) {
        precedenceHelper = thePrecedenceHelper;
        tempVariableHelper = theTempVariableHelper;
        operatorHelper = theOperatorHelper;
        dtoCreator = theDtoCreator;
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
                runtimeChecks.put(entry.getKey(), entry.getValue().first.getAbsoluteName());
            }
        }
        return runtimeChecks;
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
        StringBuilder stringBuilder = new StringBuilder("'No applicable overload found for the function ");
        stringBuilder.append(identifier.getText()).append(".\\n");
        ITSPHPAst arguments = functionCall.getChild(1);
        appendArgumentTypes(stringBuilder, bindings, arguments);
        appendOverloads(stringBuilder, functionCall);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }

    private void appendArgumentTypes(
            StringBuilder stringBuilder, IBindingCollection bindings, ITSPHPAst arguments) {
        stringBuilder.append("Given argument types: ");
        int numberOfArguments = arguments.getChildCount();
        if (numberOfArguments == 0) {
            stringBuilder.append("void");
        } else {
            String typeVariable = bindings.getTypeVariable(arguments.getChild(0).getSymbol().getAbsoluteName());
            stringBuilder.append(bindings.getLowerTypeBounds(typeVariable));
            for (int i = 1; i < numberOfArguments; ++i) {
                typeVariable = bindings.getTypeVariable(arguments.getChild(i).getSymbol().getAbsoluteName());
                stringBuilder.append(" x ").append(bindings.getLowerTypeBounds(typeVariable));
            }
        }
    }

    private void appendOverloads(StringBuilder stringBuilder, ITSPHPAst functionCall) {
        stringBuilder.append("\\nExisting overloads:");
        IMinimalMethodSymbol methodSymbol = ((IExpressionVariableSymbol) functionCall.getSymbol()).getMethodSymbol();
        for (IFunctionType functionType : methodSymbol.getOverloads()) {
            stringBuilder.append("\\n").append(functionType.getSignature());
        }
    }

    @Override
    public String getErrMessageOperatorApplication(IBindingCollection bindings, ITSPHPAst operator) {
        StringBuilder stringBuilder = new StringBuilder("'No applicable overload found for the ")
                .append(operator.getText()).append(" operator.\\n");
        appendArgumentTypes(stringBuilder, bindings, operator);
        appendOverloads(stringBuilder, operator);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }
}
