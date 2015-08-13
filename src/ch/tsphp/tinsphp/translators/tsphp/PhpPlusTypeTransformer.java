/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.symbols.IContainerTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IConvertibleTypeSymbol;
import ch.tsphp.tinsphp.common.utils.Pair;

import java.util.Collection;
import java.util.TreeSet;

import static ch.tsphp.tinsphp.common.utils.Pair.pair;

public class PhpPlusTypeTransformer implements ITypeTransformer
{

    @Override
    public Pair<ITypeSymbol, Boolean> getType(IBindingCollection bindingCollection, String typeVariable) {
        IContainerTypeSymbol containerTypeSymbol;
        if (bindingCollection.hasUpperTypeBounds(typeVariable)) {
            containerTypeSymbol = bindingCollection.getUpperTypeBounds(typeVariable);
        } else {
            containerTypeSymbol = bindingCollection.getLowerTypeBounds(typeVariable);
        }
        return new Pair<ITypeSymbol, Boolean>(containerTypeSymbol, false);
    }

    @Override
    public Pair<ITypeSymbol, Boolean> getType(ITypeSymbol typeSymbol) {
        return pair(typeSymbol, false);
    }

    @Override
    public Pair<ITypeSymbol, Boolean> getType(IContainerTypeSymbol typeSymbol) {
        return new Pair<ITypeSymbol, Boolean>(typeSymbol, false);
    }

    @Override
    public Pair<ITypeSymbol, Boolean> getType(IConvertibleTypeSymbol convertibleTypeSymbol) {
        return new Pair<ITypeSymbol, Boolean>(convertibleTypeSymbol, false);
    }

    @Override
    public Collection<String> getTypeBounds(IContainerTypeSymbol typeBounds) {
        return new TreeSet<>(typeBounds.getTypeSymbols().keySet());
    }

    @Override
    public Collection<String> getLowerRefBounds(IBindingCollection bindingCollection, String typeVariable) {
        return new TreeSet<>(bindingCollection.getLowerRefBounds(typeVariable));
    }
}
