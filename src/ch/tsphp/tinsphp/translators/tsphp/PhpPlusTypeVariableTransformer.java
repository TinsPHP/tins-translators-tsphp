/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.IFunctionType;
import ch.tsphp.tinsphp.common.translation.dtos.TypeParameterDto;

import java.util.ArrayList;
import java.util.List;

public class PhpPlusTypeVariableTransformer implements ITypeVariableTransformer
{
    private final ITypeTransformer typeTransformer;

    public PhpPlusTypeVariableTransformer(ITypeTransformer theTypeTransformer) {
        typeTransformer = theTypeTransformer;
    }

    @Override
    public IFunctionType rewriteOverload(IFunctionType overload) {
        return overload;
    }

    @Override
    public TypeParameterDto createTypeParameterDto(IBindingCollection bindings, String typeVariable) {
        List<String> lowerBounds = null;
        if (bindings.hasLowerBounds(typeVariable)) {
            lowerBounds = new ArrayList<>();
            if (bindings.hasLowerTypeBounds(typeVariable)) {
                lowerBounds.addAll(typeTransformer.getTypeBounds(bindings.getLowerTypeBounds(typeVariable)));
            }
            if (bindings.hasLowerRefBounds(typeVariable)) {
                lowerBounds.addAll(typeTransformer.getLowerRefBounds(bindings, typeVariable));
            }
        }
        List<String> upperBounds = null;
        if (bindings.hasUpperTypeBounds(typeVariable)) {
            upperBounds = new ArrayList<>();
            upperBounds.addAll(typeTransformer.getTypeBounds(bindings.getUpperTypeBounds(typeVariable)));
        }
        return new TypeParameterDto(lowerBounds, typeVariable, upperBounds);
    }
}
