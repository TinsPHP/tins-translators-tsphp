/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.translation.dtos.FunctionApplicationDto;
import ch.tsphp.tinsphp.common.translation.dtos.TranslationScopeDto;

public interface IRuntimeCheckProvider
{
    /**
     * Adds a check to the given parameterRuntimeChecks if required and indicates whether a check can be performed.
     */
    boolean addParameterCheck(
            String identifier,
            TranslationScopeDto translationScopeDto,
            IVariable parameter,
            int parameterIndex);

    Object getTypeCheck(
            TranslationScopeDto translationScopeDto,
            ITSPHPAst argumentAst,
            Object argument,
            ITypeSymbol argumentType);

    void addReturnValueCheckIfRequired(
            TranslationScopeDto translationScopeDto,
            FunctionApplicationDto functionApplicationDto,
            ITSPHPAst leftHandSide,
            ITypeSymbol returnType,
            boolean isConstantReturnType);

}
