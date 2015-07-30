/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.symbols.IContainerTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IIntersectionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.ISymbolFactory;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.PrimitiveTypeNames;
import ch.tsphp.tinsphp.common.utils.ERelation;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.common.utils.TypeHelperDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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
            ISymbolFactory theSymbolFactory,
            ITypeHelper theTypeHelper,
            Map<String, ITypeSymbol> thePrimitiveTypes) {
        typeHelper = theTypeHelper;
        primitiveTypes = thePrimitiveTypes;
        mixedTypeSymbol = primitiveTypes.get(PrimitiveTypeNames.MIXED);
        nullTypeSymbol = primitiveTypes.get(PrimitiveTypeNames.NULL_TYPE);
        falseTypeSymbol = primitiveTypes.get(PrimitiveTypeNames.FALSE_TYPE);
        tsphpScalarTypeSymbol = theSymbolFactory.createPseudoTypeSymbol("scalar", mixedTypeSymbol);
        tsphpBoolTypeSymbol = theSymbolFactory.createPseudoTypeSymbol("bool", mixedTypeSymbol);
        tsphpNumTypeSymbol = theSymbolFactory.createPseudoTypeSymbol("num", mixedTypeSymbol);
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
    public String getTypeName(IBindingCollection bindingCollection, String typeVariable) {
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
    public String getTypeName(ITypeSymbol typeSymbol) {
        if (typeSymbol instanceof IContainerTypeSymbol) {
            return getTypeName((IContainerTypeSymbol) typeSymbol);
        }
        return getTypeFromNonContainer(typeSymbol);
    }

    private String getTypeFromNonContainer(ITypeSymbol typeSymbol) {
        String typeName;
        if (typeHelper.areSame(typeSymbol, primitiveTypes.get(PrimitiveTypeNames.FALSE_TYPE))) {
            typeName = "bool";
        } else if (typeHelper.areSame(typeSymbol, primitiveTypes.get(PrimitiveTypeNames.TRUE_TYPE))) {
            typeName = "bool";
        } else if (typeHelper.areSame(typeSymbol, primitiveTypes.get(PrimitiveTypeNames.NULL_TYPE))) {
            typeName = "mixed";
        } else {
            typeName = typeSymbol.getAbsoluteName();
        }
        return typeName;
    }

    /**
     * Reduces the given containerTypeSymbol to a single type and returns its absolute name.
     *
     * @param containerTypeSymbol
     * @return The absolute name of the calculated type.
     */
    @Override
    public String getTypeName(IContainerTypeSymbol containerTypeSymbol) {
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

        String typeName;
        if (containerTypeSymbol.getTypeSymbols().size() > 1) {
            if (containerTypeSymbol instanceof IIntersectionTypeSymbol) {
                ITypeSymbol firstType = containerTypeSymbol.getTypeSymbols().values().iterator().next();
                typeName = getTypeName(firstType);
            } else {
                typeName = reduceToOneTypeOnly((IUnionTypeSymbol) containerTypeSymbol);
            }
        } else {
            typeName = getTypeFromNonContainer(containerTypeSymbol.getTypeSymbols().values().iterator().next());
        }
        return typeName;
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
        String type = getTypeName(typeBounds);
        list.add(type);
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

    private String reduceToOneTypeOnly(IUnionTypeSymbol containerTypeSymbol) {
        ITypeSymbol leastUpperTypeBound = calculateLeastUpperTypeBound(containerTypeSymbol);
        Map<String, ITypeSymbol> typeSymbols = containerTypeSymbol.getTypeSymbols();
        String suffix = "";
        //identity check is enough for tsphpXyTypeSymbol since they are created in this run
        if (typeSymbols.containsKey(falseTypeSymbol.getAbsoluteName())
                && leastUpperTypeBound != tsphpBoolTypeSymbol
                && leastUpperTypeBound != tsphpScalarTypeSymbol
                && !typeHelper.areSame(leastUpperTypeBound, mixedTypeSymbol)) {
            suffix = "!";
        }
        if (typeSymbols.containsKey(nullTypeSymbol.getAbsoluteName())
                && !typeHelper.areSame(leastUpperTypeBound, mixedTypeSymbol)) {
            //TODO TINS-332 introduce object pseudo type
//            TypeHelperDto result = typeHelper.isFirstSameOrSubTypeOfSecond(leastUpperTypeBound, objectType, false);
//            if (result.relation != ERelation.HAS_RELATION) {
            suffix += "?";
//            }
        }
        return leastUpperTypeBound.getAbsoluteName() + suffix;
    }

    private ITypeSymbol calculateLeastUpperTypeBound(IUnionTypeSymbol containerTypeSymbol) {
        Map<String, ITypeSymbol> typeSymbolsMap = new HashMap<>(containerTypeSymbol.getTypeSymbols());
        Collection<ITypeSymbol> typeSymbols = typeSymbolsMap.values();
        boolean containsFalseType = typeSymbolsMap.remove(falseTypeSymbol.getAbsoluteName()) != null;
        typeSymbolsMap.remove(nullTypeSymbol.getAbsoluteName());

        ITypeSymbol leastUpperTypeBound;
        int size = typeSymbolsMap.size();
        if (size == 0) {
            if (containsFalseType) {
                leastUpperTypeBound = tsphpBoolTypeSymbol;
            } else {
                //only nullType, in this case we use mixed
                leastUpperTypeBound = mixedTypeSymbol;
            }
        } else if (size == 1) {
            leastUpperTypeBound = typeSymbols.iterator().next();
            ITypeSymbol trueTypeSymbol = primitiveTypes.get(PrimitiveTypeNames.TRUE_TYPE);
            if (typeHelper.areSame(leastUpperTypeBound, trueTypeSymbol)) {
                leastUpperTypeBound = tsphpBoolTypeSymbol;
            }
        } else {
            leastUpperTypeBound = lookForScalarTypes(typeSymbolsMap, size);
            if (leastUpperTypeBound == null) {
                leastUpperTypeBound = calculateLeastUpperTypeBound(typeSymbols);
            }
        }
        return leastUpperTypeBound;
    }

    private ITypeSymbol lookForScalarTypes(Map<String, ITypeSymbol> typeSymbolsMap, int size) {
        String trueTypeName = primitiveTypes.get(PrimitiveTypeNames.TRUE_TYPE).getAbsoluteName();
        String intTypeName = primitiveTypes.get(PrimitiveTypeNames.INT).getAbsoluteName();
        String floatTypeName = primitiveTypes.get(PrimitiveTypeNames.FLOAT).getAbsoluteName();
        String stringTypeName = primitiveTypes.get(PrimitiveTypeNames.STRING).getAbsoluteName();
        boolean containsTrueType = typeSymbolsMap.remove(trueTypeName) != null;
        boolean containsInt = typeSymbolsMap.remove(intTypeName) != null;
        boolean containsFloat = typeSymbolsMap.remove(floatTypeName) != null;
        boolean containsString = typeSymbolsMap.remove(stringTypeName) != null;

        ITypeSymbol leastUpperTypeBound = null;
        if (containsTrueType || containsInt || containsFloat || containsString) {
            if (!typeSymbolsMap.isEmpty()) {
                // if a scalar type is in the union type and the other types are not scalar as well,
                // then we need to fall back to mixed
                leastUpperTypeBound = mixedTypeSymbol;
            } else {
                if (containsInt && containsFloat && size == 2) {
                    leastUpperTypeBound = tsphpNumTypeSymbol;
                } else {
                    leastUpperTypeBound = tsphpScalarTypeSymbol;
                }
            }
        }
        return leastUpperTypeBound;
    }

    private ITypeSymbol calculateLeastUpperTypeBound(Collection<ITypeSymbol> typeSymbols) {
        ITypeSymbol leastUpperBound = null;

        List<ITypeSymbol> parentTypeSymbols = new ArrayList<>();
        typeSymbols:
        for (ITypeSymbol typeSymbol : typeSymbols) {
            for (ITypeSymbol parentTypeSymbol : typeSymbol.getParentTypeSymbols()) {
                if (typeHelper.areSame(parentTypeSymbol, mixedTypeSymbol) || areAllSubtypes(typeSymbols,
                        parentTypeSymbol)) {
                    leastUpperBound = parentTypeSymbol;
                    break typeSymbols;
                }
                parentTypeSymbols.add(parentTypeSymbol);
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
