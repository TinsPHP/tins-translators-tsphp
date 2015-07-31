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
import ch.tsphp.tinsphp.common.symbols.IIntersectionTypeSymbol;
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

public class TSPHPNameTransformer implements INameTransformer
{

    private final ITypeHelper typeHelper;
    private final Map<String, ITypeSymbol> primitiveTypes;

    private final ITypeSymbol mixedTypeSymbol;
    private final ITypeSymbol nullTypeSymbol;
    private final ITypeSymbol falseTypeSymbol;
    private final ITypeSymbol tsphpBoolTypeSymbol;
    private final ITypeSymbol tsphpNumTypeSymbol;
    private final ITypeSymbol tsphpScalarTypeSymbol;

    public TSPHPNameTransformer(
            ITypeHelper theTypeHelper,
            Map<String, ITypeSymbol> thePrimitiveTypes,
            ITypeSymbol theTsphpBoolTypeSymbol,
            ITypeSymbol theTsphpNumTypeSymbol,
            ITypeSymbol theTsphpScalarTypeSymbol) {
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
    public Pair<String, Boolean> getTypeName(IBindingCollection bindingCollection, String typeVariable) {
        IContainerTypeSymbol containerTypeSymbol;
        if (bindingCollection.hasUpperTypeBounds(typeVariable)) {
            containerTypeSymbol = bindingCollection.getUpperTypeBounds(typeVariable);
        } else {
            containerTypeSymbol = bindingCollection.getLowerTypeBounds(typeVariable);
        }
        return getTypeName(containerTypeSymbol);
    }

    /**
     * Reduces the given typeSymbol to a single type and returns its absolute name.
     *
     * @param typeSymbol
     * @return The absolute name of the calculated type.
     */
    @Override
    public Pair<String, Boolean> getTypeName(ITypeSymbol typeSymbol) {
        if (typeSymbol instanceof IContainerTypeSymbol) {
            return getTypeName((IContainerTypeSymbol) typeSymbol);
        }
        return getTypeFromNonContainer(typeSymbol);
    }

    private Pair<String, Boolean> getTypeFromNonContainer(ITypeSymbol typeSymbol) {
        Pair<String, Boolean> pair;
        if (typeSymbol instanceof IConvertibleTypeSymbol) {
            pair = getTypeName((IConvertibleTypeSymbol) typeSymbol);
        } else if (typeHelper.areSame(typeSymbol, primitiveTypes.get(PrimitiveTypeNames.FALSE_TYPE))) {
            pair = pair("bool", true);
        } else if (typeHelper.areSame(typeSymbol, primitiveTypes.get(PrimitiveTypeNames.TRUE_TYPE))) {
            pair = pair("bool", true);
        } else if (typeHelper.areSame(typeSymbol, primitiveTypes.get(PrimitiveTypeNames.NULL_TYPE))) {
            pair = pair("mixed", true);
        } else {
            pair = pair(typeSymbol.getAbsoluteName(), false);
        }
        return pair;
    }

    /**
     * Reduces the given containerTypeSymbol to a single type and returns its absolute name.
     *
     * @param containerTypeSymbol
     * @return The absolute name of the calculated type.
     */
    @Override
    public Pair<String, Boolean> getTypeName(IContainerTypeSymbol containerTypeSymbol) {
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

        Pair<String, Boolean> pair;
        if (containerTypeSymbol.getTypeSymbols().size() > 1) {
            if (containerTypeSymbol instanceof IIntersectionTypeSymbol) {
                ITypeSymbol firstType = containerTypeSymbol.getTypeSymbols().values().iterator().next();
                pair = getTypeName(firstType);
            } else {
                pair = reduceToOneTypeOnly((IUnionTypeSymbol) containerTypeSymbol);
            }
        } else {
            pair = getTypeFromNonContainer(containerTypeSymbol.getTypeSymbols().values().iterator().next());
        }
        return pair;
    }

    @Override
    public Pair<String, Boolean> getTypeName(IConvertibleTypeSymbol convertibleTypeSymbol) {
        if (convertibleTypeSymbol.isFixed()) {
            Pair<String, Boolean> pair = getTypeName(
                    convertibleTypeSymbol.getBindingCollection(), convertibleTypeSymbol.getTypeVariable());
            return pair("{as " + pair.first + "}", pair.second);
        }
        return pair(convertibleTypeSymbol.getAbsoluteName(), false);
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
        Pair<String, Boolean> pair = getTypeName(typeBounds);
        list.add(pair.first);
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

    private Pair<String, Boolean> reduceToOneTypeOnly(IUnionTypeSymbol containerTypeSymbol) {
        Pair<ITypeSymbol, Boolean> leastUpperTypeBoundPair = calculateLeastUpperTypeBound(containerTypeSymbol);
        ITypeSymbol leastUpperTypeBound = leastUpperTypeBoundPair.first;
        boolean wasWidened = leastUpperTypeBoundPair.second;

        Map<String, ITypeSymbol> typeSymbols = containerTypeSymbol.getTypeSymbols();
        String suffix = "";
        //identity check is enough for tsphpXyTypeSymbol since they are created in this run
        if (typeSymbols.containsKey(falseTypeSymbol.getAbsoluteName())) {
            if (leastUpperTypeBound != tsphpBoolTypeSymbol
                    && leastUpperTypeBound != tsphpScalarTypeSymbol
                    && !typeHelper.areSame(leastUpperTypeBound, mixedTypeSymbol)) {
                suffix = "!";
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
            suffix += "?";
//            }
        }

        Pair<String, Boolean> pair = getTypeFromNonContainer(leastUpperTypeBound);
        return pair(pair.first + suffix, wasWidened);
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
