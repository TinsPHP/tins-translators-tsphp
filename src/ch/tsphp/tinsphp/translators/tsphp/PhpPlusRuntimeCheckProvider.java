/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.translation.dtos.TranslationScopeDto;

public class PhpPlusRuntimeCheckProvider implements IRuntimeCheckProvider
{

    @Override
    public boolean addParameterCheck(
            String identifier,
            TranslationScopeDto translationScopeDto, IVariable parameter,
            int parameterIndex) {
        return false;
    }

    @Override
    public Object getTypeCheck(
            TranslationScopeDto translationScopeDto, ITSPHPAst argumentAst, Object argument, ITypeSymbol argumentType) {
        return "cast<" + argumentType.getAbsoluteName() + ">(" + argument.toString() + ")";
    }
}
