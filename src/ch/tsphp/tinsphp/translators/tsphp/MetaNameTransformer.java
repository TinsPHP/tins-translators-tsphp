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
import java.util.TreeSet;

public class MetaNameTransformer implements INameTransformer
{

    @Override
    public String getTypeName(IBindingCollection bindingCollection, String typeVariable) {
        IContainerTypeSymbol containerTypeSymbol;
        if (bindingCollection.hasUpperTypeBounds(typeVariable)) {
            containerTypeSymbol = bindingCollection.getUpperTypeBounds(typeVariable);
        } else {
            containerTypeSymbol = bindingCollection.getLowerTypeBounds(typeVariable);
        }
        return containerTypeSymbol.getAbsoluteName();
    }

    @Override
    public String getTypeName(IContainerTypeSymbol typeSymbol) {
        return typeSymbol.getAbsoluteName();
    }

    @Override
    public String getTypeName(ITypeSymbol typeSymbol) {
        return typeSymbol.getAbsoluteName();
    }

    @Override
    public Collection<String> getTypeBounds(IContainerTypeSymbol typeBounds) {
        return new TreeSet<>(typeBounds.getTypeSymbols().keySet());
    }

    @Override
    public Collection<String> getLowerRefBounds(IBindingCollection bindingCollection, String typeVariable) {
        //TODO TINS-584 find least upper reference bound
        return new TreeSet<>(bindingCollection.getLowerRefBounds(typeVariable));
    }
}
