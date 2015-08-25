/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.IFunctionType;
import ch.tsphp.tinsphp.common.translation.dtos.TypeParameterDto;

/**
 * Transforms overloads, its type parameters and type variables respectively, according to the rules of the target
 * language, e.g., reducing multiple lower ref bounds to one type variable.
 */
public interface ITypeVariableTransformer
{
    IFunctionType rewriteOverload(IFunctionType overload);

    TypeParameterDto createTypeParameterDto(IBindingCollection bindings, String typeVariable);
}
