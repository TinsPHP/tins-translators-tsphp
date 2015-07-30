/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.symbols.IContainerTypeSymbol;

import java.util.Collection;

/**
 * Transforms type names and type variable names according to rules of the translation target.
 */
public interface INameTransformer
{
    String getTypeName(IBindingCollection bindingCollection, String typeVariable);

    String getTypeName(ITypeSymbol typeSymbol);

    String getTypeName(IContainerTypeSymbol typeSymbol);

    Collection<String> getTypeBounds(IContainerTypeSymbol typeBounds);

    Collection<String> getLowerRefBounds(IBindingCollection bindingCollection, String typeVariable);
}
