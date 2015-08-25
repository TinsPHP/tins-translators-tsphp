/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.TinsPHPConstants;
import ch.tsphp.tinsphp.common.inference.constraints.EBindingCollectionMode;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.IFunctionType;
import ch.tsphp.tinsphp.common.inference.constraints.ITypeVariableReference;
import ch.tsphp.tinsphp.common.symbols.IIntersectionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.ISymbolFactory;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.PrimitiveTypeNames;
import ch.tsphp.tinsphp.common.translation.dtos.TypeParameterDto;
import ch.tsphp.tinsphp.common.utils.ERelation;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.common.utils.Pair;
import ch.tsphp.tinsphp.common.utils.TypeHelperDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ch.tsphp.tinsphp.common.utils.Pair.pair;

public class TsphpTypeVariableTransformer implements ITypeVariableTransformer
{
    private final ISymbolFactory symbolFactory;
    private final ITypeHelper typeHelper;
    private final ITypeTransformer typeTransformer;

    public TsphpTypeVariableTransformer(
            ISymbolFactory theSymbolFactory,
            ITypeHelper theTypeHelper,
            ITypeTransformer theTypeTransformer) {
        symbolFactory = theSymbolFactory;
        typeHelper = theTypeHelper;
        typeTransformer = theTypeTransformer;
    }

    @Override
    public IFunctionType rewriteOverload(IFunctionType overload) {
        IBindingCollection originalBindings = overload.getBindingCollection();
        IFunctionType rewrittenOverload = rewriteReturnTypeVariable(overload, originalBindings);
        return rewriteTypeParameters(rewrittenOverload, originalBindings);
    }

    private IFunctionType rewriteReturnTypeVariable(IFunctionType overload, IBindingCollection originalBindings) {
        ITypeVariableReference reference
                = originalBindings.getTypeVariableReference(TinsPHPConstants.RETURN_VARIABLE_NAME);
        String returnTypeVariable = reference.getTypeVariable();
        if (!reference.hasFixedType() && returnTypeVariable.charAt(0) != 'T') {
            IBindingCollection newBindings = unifyWithLowerRefs(originalBindings, returnTypeVariable, true);
            return createNewOverload(overload, newBindings);
        }
        return overload;
    }


    private IFunctionType rewriteTypeParameters(IFunctionType overload, IBindingCollection originalBindings) {
        IBindingCollection newBindings = overload.getBindingCollection();
        boolean isStillOriginal = newBindings == originalBindings;
        boolean wasModified = false;

        for (String typeParameter : overload.getNonFixedTypeParameters()) {
            if (newBindings.hasLowerRefBounds(typeParameter)) {
                Set<String> lowerRefBounds = newBindings.getLowerRefBounds(typeParameter);
                boolean unify = lowerRefBounds.size() > 1;
                if (!unify && newBindings.hasLowerTypeBounds(typeParameter)) {
                    String refTypeVariable = lowerRefBounds.iterator().next();
                    if (newBindings.hasLowerTypeBounds(refTypeVariable)) {
                        IUnionTypeSymbol lowerTypeBounds = newBindings.getLowerTypeBounds(typeParameter);
                        IUnionTypeSymbol refLowerTypeBounds = newBindings.getLowerTypeBounds(refTypeVariable);
                        unify = !typeHelper.areSame(lowerTypeBounds, refLowerTypeBounds);
                    }
                }
                if (unify) {
                    newBindings = unifyWithLowerRefs(newBindings, typeParameter, isStillOriginal);
                    isStillOriginal = false;
                    wasModified = true;
                }
            }
            Pair<IBindingCollection, Boolean> pair
                    = widenTypeBoundsIfNecessary(newBindings, isStillOriginal, typeParameter);
            wasModified = wasModified || pair.second;
            newBindings = pair.first;
        }

        if (wasModified) {
            return createNewOverload(overload, newBindings);
        }
        return overload;
    }

    private IFunctionType createNewOverload(IFunctionType overload, IBindingCollection newBindings) {
        for (String typeParameter : overload.getNonFixedTypeParameters()) {
            if (newBindings.containsTypeVariable(typeParameter)) {
                newBindings.renameTypeVariableToNextFreeName(typeParameter);
            }
        }
        IFunctionType newOverload = symbolFactory.createFunctionType(
                overload.getName(), newBindings, overload.getParameters());
        newOverload.simplify();
        return newOverload;
    }

    private IBindingCollection unifyWithLowerRefs(
            IBindingCollection originalBindings, String typeVariable, boolean copyBindings) {
        IBindingCollection newBindings = originalBindings;
        if (copyBindings) {
            newBindings = symbolFactory.createBindingCollection(originalBindings);
        }

        Set<String> lowerRefBounds = newBindings.getLowerRefBounds(typeVariable);
        Set<String> copyLowerRefBounds = new HashSet<>(lowerRefBounds);
        for (String refTypeVariable : copyLowerRefBounds) {
            IIntersectionTypeSymbol refUpperTypeBounds = null;

            boolean refHasUpperTypeBounds = newBindings.hasUpperTypeBounds(refTypeVariable);
            if (refHasUpperTypeBounds) {
                refUpperTypeBounds = newBindings.getUpperTypeBounds(refTypeVariable);
            }
            boolean canMerge = !refHasUpperTypeBounds;
            if (!canMerge) {
                if (newBindings.hasUpperTypeBounds(typeVariable)) {
                    canMerge = typeHelper.areSame(refUpperTypeBounds, newBindings.getUpperTypeBounds(typeVariable));
                } else {
                    IUnionTypeSymbol lowerTypeBounds = newBindings.getLowerTypeBounds(typeVariable);
                    canMerge = lowerTypeBounds == null;
                    if (!canMerge) {
                        TypeHelperDto result = typeHelper.isFirstSameOrSubTypeOfSecond(
                                lowerTypeBounds, refUpperTypeBounds, false);
                        canMerge = result.relation == ERelation.HAS_RELATION;
                    }
                }
            }

            if (canMerge) {
                newBindings.mergeFirstIntoSecond(refTypeVariable, typeVariable);
                lowerRefBounds.remove(typeVariable);
                newBindings.getUpperRefBounds(typeVariable).remove(typeVariable);
            } else {
                newBindings.addLowerTypeBound(typeVariable, refUpperTypeBounds);
                //disconnect the relation
                lowerRefBounds.remove(refTypeVariable);
                newBindings.getUpperRefBounds(refTypeVariable).remove(typeVariable);
            }
        }
        return newBindings;
    }

    private Pair<IBindingCollection, Boolean> widenTypeBoundsIfNecessary(
            IBindingCollection bindingCollection, boolean isStillOriginal, String typeParameter) {

        IBindingCollection newBindings = bindingCollection;
        boolean wasModified = false;

        boolean needToTransformUpperTypeBound = true;
        if (newBindings.hasLowerTypeBounds(typeParameter)) {
            Pair<ITypeSymbol, Boolean> pair
                    = typeTransformer.getType(newBindings.getLowerTypeBounds(typeParameter));
            if (pair.second) {
                wasModified = true;
                if (isStillOriginal) {
                    newBindings = symbolFactory.createBindingCollection(newBindings);
                }
                if (newBindings.hasUpperTypeBounds(typeParameter)) {
                    TypeHelperDto resultDto = typeHelper.isFirstSameOrSubTypeOfSecond(
                            pair.first, newBindings.getUpperTypeBounds(typeParameter), false);
                    if (resultDto.relation == ERelation.HAS_NO_RELATION) {
                        needToTransformUpperTypeBound = false;
                        newBindings.setMode(EBindingCollectionMode.Modification);
                        newBindings.removeUpperTypeBounds(typeParameter);
                        newBindings.addUpperTypeBound(typeParameter, pair.first);
                        newBindings.setMode(EBindingCollectionMode.Normal);
                    }
                }
                newBindings.addLowerTypeBound(typeParameter, pair.first);
            }
        }
        if (needToTransformUpperTypeBound && newBindings.hasUpperTypeBounds(typeParameter)) {
            Pair<ITypeSymbol, Boolean> pair
                    = typeTransformer.getType(newBindings.getUpperTypeBounds(typeParameter));
            if (pair.second) {
                wasModified = true;
                if (isStillOriginal) {
                    newBindings = symbolFactory.createBindingCollection(newBindings);
                }
                newBindings.addUpperTypeBound(typeParameter, pair.first);
            }
        }
        return pair(newBindings, wasModified);
    }

    @Override
    public TypeParameterDto createTypeParameterDto(IBindingCollection bindings, String typeVariable) {
        List<String> lowerBounds;
        if (!bindings.hasLowerTypeBounds(typeVariable) || !bindings.hasLowerRefBounds(typeVariable)) {
            lowerBounds = getLowerBoundsStandard(bindings, typeVariable);
        } else {
            lowerBounds = getLowerTypeBoundsWidened(bindings, typeVariable);
        }
        List<String> upperBounds = null;
        if (bindings.hasUpperTypeBounds(typeVariable)) {
            upperBounds = new ArrayList<>();
            upperBounds.addAll(typeTransformer.getTypeBounds(bindings.getUpperTypeBounds(typeVariable)));
        }
        return new TypeParameterDto(lowerBounds, typeVariable, upperBounds);
    }

    private List<String> getLowerBoundsStandard(IBindingCollection bindings, String typeVariable) {
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
        return lowerBounds;
    }

    private List<String> getLowerTypeBoundsWidened(IBindingCollection bindings, String typeVariable) {
        List<String> lowerBounds;

        IUnionTypeSymbol lowerTypeBounds = bindings.getLowerTypeBounds(typeVariable);
        Set<String> lowerRefBounds = bindings.getLowerRefBounds(typeVariable);
        boolean notOwnLowerTypeBounds = lowerRefBounds.size() == 1;
        String refTypeVariable = null;
        if (notOwnLowerTypeBounds) {
            refTypeVariable = lowerRefBounds.iterator().next();
            notOwnLowerTypeBounds = bindings.hasLowerTypeBounds(refTypeVariable)
                    && typeHelper.areSame(lowerTypeBounds, bindings.getLowerTypeBounds(refTypeVariable));
        }

        if (notOwnLowerTypeBounds) {
            //all lower type bounds are provided by the lower ref, hence we can use it as lower type bound
            lowerBounds = new ArrayList<>(1);
            lowerBounds.add(refTypeVariable);
        } else {
            lowerBounds = new ArrayList<>(1);
            String type = null;
            IUnionTypeSymbol unionTypeSymbol = symbolFactory.createUnionTypeSymbol();
            unionTypeSymbol.addTypeSymbol(lowerTypeBounds);
            for (String lowerRefBound : lowerRefBounds) {
                if (bindings.hasUpperTypeBounds(lowerRefBound)) {
                    unionTypeSymbol.addTypeSymbol(bindings.getUpperTypeBounds(lowerRefBound));
                } else {
                    type = PrimitiveTypeNames.MIXED;
                    break;
                }
            }
            if (type == null) {
                Pair<ITypeSymbol, Boolean> pair = typeTransformer.getType(unionTypeSymbol);
                type = pair.first.getAbsoluteName();
            }
            lowerBounds.add(type);
        }
        return lowerBounds;
    }

}
