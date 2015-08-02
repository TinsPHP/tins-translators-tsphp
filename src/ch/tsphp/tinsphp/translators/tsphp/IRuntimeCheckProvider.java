/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.translation.dtos.ParameterDto;

import java.util.Deque;

public interface IRuntimeCheckProvider
{
    /**
     * Adds a check to the given parameterRuntimeChecks if required and indicates whether a check can be performed.
     */
    boolean addParameterCheck(
            String identifier,
            Deque<String> statements,
            IBindingCollection bindings,
            IVariable parameter,
            int parameterIndex,
            ParameterDto parameterDto);

    Object getTypeCheck(Deque<String> statements, ITSPHPAst argumentAst, Object argument, ITypeSymbol argumentType);
}
