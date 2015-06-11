/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.tinsphp.common.inference.constraints.IFunctionType;
import ch.tsphp.tinsphp.common.inference.constraints.IOverloadBindings;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.symbols.IMethodSymbol;
import ch.tsphp.tinsphp.common.translation.IDtoCreator;
import ch.tsphp.tinsphp.common.translation.ITranslatorController;
import ch.tsphp.tinsphp.common.translation.dtos.OverloadDto;
import ch.tsphp.tinsphp.common.translation.dtos.VariableDto;

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
    public VariableDto createVariableDtoForConstant(IOverloadBindings bindings, ITSPHPAst constant) {
        return dtoCreator.createVariableDtoForConstant(bindings, (IVariable) constant.getSymbol());
    }

    @Override
    public VariableDto createVariableDto(IOverloadBindings bindings, ITSPHPAst variableId) {
        return dtoCreator.createVariableDto(bindings, (IVariable) variableId.getSymbol());
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
