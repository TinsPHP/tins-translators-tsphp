/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.inference.constraints.EBindingCollectionMode;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.symbols.IContainerTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IConvertibleTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IIntersectionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IParametricTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.ISymbolFactory;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.PrimitiveTypeNames;
import ch.tsphp.tinsphp.common.utils.ERelation;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.common.utils.Pair;
import ch.tsphp.tinsphp.common.utils.TypeHelperDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static ch.tsphp.tinsphp.common.utils.Pair.pair;

public class TSPHPTypeTransformer implements ITypeTransformer
{

    private final ISymbolFactory symbolFactory;
    private final ITypeHelper typeHelper;
    private final Map<String, ITypeSymbol> primitiveTypes;

    private final ITypeSymbol mixedTypeSymbol;
    private final ITypeSymbol nullTypeSymbol;
    private final ITypeSymbol falseTypeSymbol;
    private final ITypeSymbol tsphpBoolTypeSymbol;
    private final ITypeSymbol tsphpNumTypeSymbol;
    private final ITypeSymbol tsphpScalarTypeSymbol;

    public TSPHPTypeTransformer(
            ISymbolFactory theSymbolFactory,
            ITypeHelper theTypeHelper,
            Map<String, ITypeSymbol> thePrimitiveTypes,
            ITypeSymbol theTsphpBoolTypeSymbol,
            ITypeSymbol theTsphpNumTypeSymbol,
            ITypeSymbol theTsphpScalarTypeSymbol) {
        symbolFactory = theSymbolFactory;
        typeHelper = theTypeHelper;
        primitiveTypes = thePrimitiveTypes;
        mixedTypeSymbol = primitiveTypes.get(PrimitiveTypeNames.MIXED);
        nullTypeSymbol = primitiveTypes.get(PrimitiveTypeNames.NULL_TYPE);
        falseTypeSymbol = primitiveTypes.get(PrimitiveTypeNames.FALSE_TYPE);
        tsphpBoolTypeSymbol = theTsphpBoolTypeSymbol;
        tsphpNumTypeSymbol = theTsphpNumTypeSymbol;
        tsphpScalarTypeSymbol = theTsphpScalarTypeSymbol;
    }

    /**
     * Retrieves the type of the corresponding type variable, reduces it to a single type and returns the
     * corresponding absolute name.
     *
     * @param bindingCollection
     * @param typeVariable
     * @return The absolute name of the calculated type.
     */
    @Override
    public Pair<ITypeSymbol, Boolean> getType(IBindingCollection bindingCollection, String typeVariable) {
        IContainerTypeSymbol containerTypeSymbol;
        if (bindingCollection.hasUpperTypeBounds(typeVariable)) {
            containerTypeSymbol = bindingCollection.getUpperTypeBounds(typeVariable);
        } else {
            containerTypeSymbol = bindingCollection.getLowerTypeBounds(typeVariable);
        }
        return getType(containerTypeSymbol);
    }

    /**
     * Reduces the given typeSymbol to a single type and returns its absolute name.
     *
     * @param typeSymbol
     * @return The absolute name of the calculated type.
     */
    @Override
    public Pair<ITypeSymbol, Boolean> getType(ITypeSymbol typeSymbol) {
        if (typeSymbol instanceof IContainerTypeSymbol) {
            return getType((IContainerTypeSymbol) typeSymbol);
        }
        return getTypeFromNonContainer(typeSymbol);
    }

    private Pair<ITypeSymbol, Boolean> getTypeFromNonContainer(ITypeSymbol typeSymbol) {
        Pair<ITypeSymbol, Boolean> pair;
        if (typeSymbol instanceof IConvertibleTypeSymbol) {
            pair = getType((IConvertibleTypeSymbol) typeSymbol);
        } else if (typeHelper.areSame(typeSymbol, primitiveTypes.get(PrimitiveTypeNames.FALSE_TYPE))) {
            pair = pair(tsphpBoolTypeSymbol, true);
        } else if (typeHelper.areSame(typeSymbol, primitiveTypes.get(PrimitiveTypeNames.TRUE_TYPE))) {
            pair = pair(tsphpBoolTypeSymbol, true);
        } else if (typeHelper.areSame(typeSymbol, primitiveTypes.get(PrimitiveTypeNames.NULL_TYPE))) {
            pair = pair(mixedTypeSymbol, true);
        } else {
            pair = pair(typeSymbol, false);
        }
        return pair;
    }

    @Override
    public Pair<ITypeSymbol, Boolean> getType(IContainerTypeSymbol containerTypeSymbol) {
        if (containerTypeSymbol.getTypeSymbols().size() == 1) {
            ITypeSymbol tmp = containerTypeSymbol.getTypeSymbols().values().iterator().next();
            while (tmp instanceof IContainerTypeSymbol) {
                Map<String, ITypeSymbol> typeSymbols = ((IContainerTypeSymbol) tmp).getTypeSymbols();
                if (typeSymbols.size() != 1) {
                    containerTypeSymbol = (IContainerTypeSymbol) tmp;
                    tmp = null;
                } else {
                    tmp = typeSymbols.values().iterator().next();
                }
            }
        }

        Pair<ITypeSymbol, Boolean> pair;
        if (containerTypeSymbol.getTypeSymbols().size() > 1) {
            if (containerTypeSymbol instanceof IIntersectionTypeSymbol) {
                pair = getType((IIntersectionTypeSymbol) containerTypeSymbol);
            } else {
                pair = reduceToOneTypeOnly((IUnionTypeSymbol) containerTypeSymbol);
            }
        } else {
            pair = getTypeFromNonContainer(containerTypeSymbol.getTypeSymbols().values().iterator().next());
        }
        return pair;
    }

    private Pair<ITypeSymbol, Boolean> getType(IIntersectionTypeSymbol containerTypeSymbol) {
        Pair<ITypeSymbol, Boolean> pair;
        IIntersectionTypeSymbol intersectionTypeSymbol = symbolFactory.createIntersectionTypeSymbol();
        ITypeSymbol tsphpType = null;
        boolean wasWidened = false;
        for (ITypeSymbol innerTypeSymbol : containerTypeSymbol.getTypeSymbols().values()) {
            Pair<ITypeSymbol, Boolean> innerTypePair = getType(innerTypeSymbol);
            if (innerTypePair.second) {
                wasWidened = true;
            }
            ITypeSymbol tsphpInnerType = innerTypePair.first;
            if (typeHelper.areSame(tsphpInnerType, mixedTypeSymbol)) {
                tsphpType = mixedTypeSymbol;
                break;
            } else {
                intersectionTypeSymbol.addTypeSymbol(tsphpInnerType);
            }
        }
        if (tsphpType == null) {
            tsphpType = intersectionTypeSymbol;
        }
        pair = pair(tsphpType, wasWidened);
        return pair;
    }

    @Override
    public Pair<ITypeSymbol, Boolean> getType(IConvertibleTypeSymbol convertibleTypeSymbol) {
        Pair<ITypeSymbol, Boolean> resultPair;
        if (convertibleTypeSymbol.isFixed()) {
            IBindingCollection copyBindingCollection;
            IConvertibleTypeSymbol copyConvertibleTypeSymbol;
            if (convertibleTypeSymbol.wasBound()) {
                copyConvertibleTypeSymbol = convertibleTypeSymbol.copy(
                        new ArrayList<IParametricTypeSymbol>(0));
                IBindingCollection bindingCollection = convertibleTypeSymbol.getBindingCollection();
                copyBindingCollection = symbolFactory.createBindingCollection(bindingCollection);
                copyConvertibleTypeSymbol.rebind(copyBindingCollection);
            } else {
                copyConvertibleTypeSymbol = symbolFactory.createConvertibleTypeSymbol();
                if (convertibleTypeSymbol.hasUpperTypeBounds()) {
                    copyConvertibleTypeSymbol.addUpperTypeBound(convertibleTypeSymbol.getUpperTypeBounds());
                } else {
                    copyConvertibleTypeSymbol.addLowerTypeBound(convertibleTypeSymbol.getLowerTypeBounds());
                }
                copyBindingCollection = copyConvertibleTypeSymbol.getBindingCollection();
            }

            String typeVariable = copyConvertibleTypeSymbol.getTypeVariable();
            if (copyBindingCollection.hasUpperTypeBounds(typeVariable)) {

                Pair<ITypeSymbol, Boolean> pair = getType(copyBindingCollection.getUpperTypeBounds(typeVariable));
                copyBindingCollection.setMode(EBindingCollectionMode.Modification);
                copyBindingCollection.removeUpperTypeBounds(typeVariable);
                IIntersectionTypeSymbol intersectionTypeSymbol = symbolFactory.createIntersectionTypeSymbol();
                intersectionTypeSymbol.addTypeSymbol(pair.first);
                copyBindingCollection.setUpperTypeBounds(typeVariable, intersectionTypeSymbol);
                copyBindingCollection.setMode(EBindingCollectionMode.Normal);
                resultPair = new Pair<ITypeSymbol, Boolean>(copyConvertibleTypeSymbol, pair.second);
            } else {
                Pair<ITypeSymbol, Boolean> pair = getType(copyBindingCollection.getLowerTypeBounds(typeVariable));
                copyBindingCollection.setMode(EBindingCollectionMode.Modification);
                copyBindingCollection.removeLowerTypeBounds(typeVariable);
                IUnionTypeSymbol unionTypeSymbol = symbolFactory.createUnionTypeSymbol();
                unionTypeSymbol.addTypeSymbol(pair.first);
                copyBindingCollection.setLowerTypeBounds(typeVariable, unionTypeSymbol);
                copyBindingCollection.setMode(EBindingCollectionMode.Normal);
                resultPair = new Pair<ITypeSymbol, Boolean>(copyConvertibleTypeSymbol, pair.second);
            }
        } else {
            resultPair = new Pair<ITypeSymbol, Boolean>(convertibleTypeSymbol, false);
        }
        return resultPair;
    }

    /**
     * Reduces the given typeBounds to a single type and returns its absolute name in a collection.
     *
     * @param typeBounds
     * @return The absolute name of the calculated type in a collection.
     */
    @Override
    public Collection<String> getTypeBounds(IContainerTypeSymbol typeBounds) {
        List<String> list = new ArrayList<>(1);
        Pair<ITypeSymbol, Boolean> pair = getType(typeBounds);
        list.add(pair.first.getAbsoluteName());
        return list;
    }

    /**
     * Retrieves the lower ref bounds of the given typeVariable and reduces it to one type variable only and returns
     * its name in a collection.
     *
     * @return The name of the type variable in a collection.
     */
    @Override
    public Collection<String> getLowerRefBounds(IBindingCollection bindingCollection, String typeVariable) {
        //TODO TINS-584 find least upper reference bound
        return new TreeSet<>(bindingCollection.getLowerRefBounds(typeVariable));
    }

    private Pair<ITypeSymbol, Boolean> reduceToOneTypeOnly(IUnionTypeSymbol containerTypeSymbol) {
        Pair<ITypeSymbol, Boolean> leastUpperTypeBoundPair = calculateLeastUpperTypeBound(containerTypeSymbol);
        ITypeSymbol leastUpperTypeBound = leastUpperTypeBoundPair.first;
        boolean wasWidened = leastUpperTypeBoundPair.second;

        Map<String, ITypeSymbol> typeSymbols = containerTypeSymbol.getTypeSymbols();
        //identity check is enough for tsphpXyTypeSymbol since they are created in this run
        if (typeSymbols.containsKey(falseTypeSymbol.getAbsoluteName())) {
            TypeHelperDto result = typeHelper.isFirstSameOrSubTypeOfSecond(falseTypeSymbol, leastUpperTypeBound, false);
            if (result.relation == ERelation.HAS_NO_RELATION) {
                IUnionTypeSymbol unionTypeSymbol = symbolFactory.createUnionTypeSymbol();
                unionTypeSymbol.addTypeSymbol(falseTypeSymbol);
                unionTypeSymbol.addTypeSymbol(leastUpperTypeBound);
                String absoluteName = leastUpperTypeBound.getAbsoluteName();
                leastUpperTypeBound = new TsphpUnionTypeSymbol(absoluteName + "!", unionTypeSymbol);
            }
            if (typeSymbols.containsKey(PrimitiveTypeNames.TRUE_TYPE) && leastUpperTypeBound == tsphpBoolTypeSymbol) {
                wasWidened = false;
            }
        }
        if (typeSymbols.containsKey(nullTypeSymbol.getAbsoluteName())
                && !typeHelper.areSame(leastUpperTypeBound, mixedTypeSymbol)) {
            //TODO TINS-332 introduce object pseudo type
//            TypeHelperDto result = typeHelper.isFirstSameOrSubTypeOfSecond(leastUpperTypeBound, objectType, false);
//            if (result.relation != ERelation.HAS_RELATION) {
            IUnionTypeSymbol unionTypeSymbol = symbolFactory.createUnionTypeSymbol();
            unionTypeSymbol.addTypeSymbol(nullTypeSymbol);
            unionTypeSymbol.addTypeSymbol(leastUpperTypeBound);
            String absoluteName = leastUpperTypeBound.getAbsoluteName();
            leastUpperTypeBound = new TsphpUnionTypeSymbol(absoluteName + "?", unionTypeSymbol);
//            }
        }

        return pair(leastUpperTypeBound, wasWidened);
    }

    private Pair<ITypeSymbol, Boolean> calculateLeastUpperTypeBound(IUnionTypeSymbol containerTypeSymbol) {
        Map<String, ITypeSymbol> typeSymbolsMap = new HashMap<>(containerTypeSymbol.getTypeSymbols());
        Collection<ITypeSymbol> typeSymbols = typeSymbolsMap.values();
        boolean containsFalseType = typeSymbolsMap.remove(falseTypeSymbol.getAbsoluteName()) != null;
        typeSymbolsMap.remove(nullTypeSymbol.getAbsoluteName());

        ITypeSymbol leastUpperTypeBound;
        boolean wasWidened = false;
        int size = typeSymbolsMap.size();
        if (size == 0) {
            if (containsFalseType) {
                leastUpperTypeBound = tsphpBoolTypeSymbol;
            } else {
                //only nullType, in this case we use mixed
                leastUpperTypeBound = mixedTypeSymbol;
            }
            wasWidened = true;
        } else if (size == 1) {
            leastUpperTypeBound = typeSymbols.iterator().next();
            if (leastUpperTypeBound.getAbsoluteName().equals(PrimitiveTypeNames.TRUE_TYPE)) {
                leastUpperTypeBound = tsphpBoolTypeSymbol;
                wasWidened = true;
            }
        } else {
            Pair<ITypeSymbol, Boolean> pair = lookForScalarTypes(typeSymbolsMap, size);
            if (pair == null) {
                leastUpperTypeBound = calculateLeastUpperTypeBound(typeSymbols);
                wasWidened = true;
            } else {
                leastUpperTypeBound = pair.first;
                wasWidened = pair.second;
            }
        }
        return pair(leastUpperTypeBound, wasWidened);
    }

    private Pair<ITypeSymbol, Boolean> lookForScalarTypes(Map<String, ITypeSymbol> typeSymbolsMap, int size) {
        String trueTypeName = primitiveTypes.get(PrimitiveTypeNames.TRUE_TYPE).getAbsoluteName();
        String intTypeName = primitiveTypes.get(PrimitiveTypeNames.INT).getAbsoluteName();
        String floatTypeName = primitiveTypes.get(PrimitiveTypeNames.FLOAT).getAbsoluteName();
        String stringTypeName = primitiveTypes.get(PrimitiveTypeNames.STRING).getAbsoluteName();
        boolean containsTrueType = typeSymbolsMap.remove(trueTypeName) != null;
        boolean containsInt = typeSymbolsMap.remove(intTypeName) != null;
        boolean containsFloat = typeSymbolsMap.remove(floatTypeName) != null;
        boolean containsString = typeSymbolsMap.remove(stringTypeName) != null;

        ITypeSymbol leastUpperTypeBound = null;
        boolean wasWidened = false;
        if (containsTrueType || containsInt || containsFloat || containsString) {
            if (!typeSymbolsMap.isEmpty()) {
                // if a scalar type is in the union type and the other types are not scalar as well,
                // then we need to fall back to mixed
                leastUpperTypeBound = mixedTypeSymbol;
                wasWidened = true;
            } else {
                if (containsInt && containsFloat && size == 2) {
                    leastUpperTypeBound = tsphpNumTypeSymbol;
                } else {
                    leastUpperTypeBound = tsphpScalarTypeSymbol;
                    //falseType, trueType, int, float and string
                    final int numberOfScalars = 5;
                    wasWidened = size != numberOfScalars;
                }
            }
        }
        if (leastUpperTypeBound == null) {
            return null;
        }
        return pair(leastUpperTypeBound, wasWidened);
    }

    private ITypeSymbol calculateLeastUpperTypeBound(Collection<ITypeSymbol> typeSymbols) {
        ITypeSymbol leastUpperBound = null;

        List<ITypeSymbol> parentTypeSymbols = new ArrayList<>();
        typeSymbols:
        for (ITypeSymbol typeSymbol : typeSymbols) {
            if (!(typeSymbol instanceof IConvertibleTypeSymbol)) {
                for (ITypeSymbol parentTypeSymbol : typeSymbol.getParentTypeSymbols()) {
                    if (typeHelper.areSame(parentTypeSymbol, mixedTypeSymbol) || areAllSubtypes(typeSymbols,
                            parentTypeSymbol)) {
                        leastUpperBound = parentTypeSymbol;
                        break typeSymbols;
                    }
                    parentTypeSymbols.add(parentTypeSymbol);
                }
            } else {
                //a convertible type has only mixed as parent type
                leastUpperBound = mixedTypeSymbol;
            }
        }
        if (leastUpperBound == null) {
            return calculateLeastUpperTypeBound(parentTypeSymbols);
        }
        return leastUpperBound;
    }

    private boolean areAllSubtypes(Collection<ITypeSymbol> typeSymbols, ITypeSymbol parentTypeSymbol) {
        boolean allAreSubtypes = true;
        for (ITypeSymbol typeSymbol : typeSymbols) {
            TypeHelperDto result = typeHelper.isFirstSameOrSubTypeOfSecond(typeSymbol, parentTypeSymbol, false);
            if (result.relation == ERelation.HAS_NO_RELATION) {
                allAreSubtypes = false;
                break;
            }
        }
        return allAreSubtypes;
    }
}
