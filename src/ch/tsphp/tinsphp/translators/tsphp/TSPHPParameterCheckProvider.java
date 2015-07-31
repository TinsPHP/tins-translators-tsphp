/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.symbols.IConvertibleTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IIntersectionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.PrimitiveTypeNames;
import ch.tsphp.tinsphp.common.translation.dtos.ParameterDto;
import ch.tsphp.tinsphp.common.utils.Pair;
import ch.tsphp.tinsphp.translators.tsphp.issues.IOutputIssueMessageProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class TSPHPParameterCheckProvider implements IParameterCheckProvider
{
    private final IOutputIssueMessageProvider messageProvider;
    private final ITypeSymbol tsphpBoolTypeSymbol;

    public TSPHPParameterCheckProvider(
            IOutputIssueMessageProvider theMessageProvider,
            ITypeSymbol theTsphpBoolTypeSymbol) {
        messageProvider = theMessageProvider;
        tsphpBoolTypeSymbol = theTsphpBoolTypeSymbol;
    }

    @Override
    public boolean addParameterChecks(
            String identifier,
            List<Pair<String, String>> parameterRuntimeChecks,
            IBindingCollection bindings,
            IVariable parameter,
            int parameterIndex,
            ParameterDto parameterDto) {

        StringBuilder stringBuilder = new StringBuilder();
        List<String> types = new ArrayList<>();
        String parameterName = parameter.getName();
        String typeVariable = bindings.getTypeVariable(parameter.getAbsoluteName());
        IIntersectionTypeSymbol upperTypeBounds = bindings.getUpperTypeBounds(typeVariable);
        boolean canPerformRuntimeChecks = appendParameterRuntimeCheck(
                parameterName, stringBuilder, upperTypeBounds, types);
        if (canPerformRuntimeChecks && !types.isEmpty()) {
            String errorMessage = messageProvider.getParameterRuntimeCheckMessage(
                    identifier, parameterName, parameterIndex + 1, types);
            parameterRuntimeChecks.add(new Pair<>(stringBuilder.toString(), errorMessage));
        }
        return canPerformRuntimeChecks;
    }

    private boolean appendParameterRuntimeCheck(
            String parameterName,
            StringBuilder stringBuilder,
            ITypeSymbol typeSymbol,
            List<String> types) {
        boolean ok = true;
        if (typeSymbol instanceof IUnionTypeSymbol) {
            ok = appendParameterRuntimeCheck(parameterName, stringBuilder, (IUnionTypeSymbol) typeSymbol, types);
        } else if (typeSymbol instanceof IIntersectionTypeSymbol) {
            stringBuilder.append("(");
            ok = appendParameterRuntimeCheck(parameterName, stringBuilder, (IIntersectionTypeSymbol) typeSymbol, types);
            stringBuilder.append(")");
        } else if (typeSymbol instanceof IConvertibleTypeSymbol) {
            ok = false;
        } else {
            String typeName = typeSymbol.getAbsoluteName();
            switch (typeName) {
                case PrimitiveTypeNames.BOOL:
                case PrimitiveTypeNames.INT:
                case PrimitiveTypeNames.FLOAT:
                case PrimitiveTypeNames.STRING:
                case PrimitiveTypeNames.ARRAY:
                case PrimitiveTypeNames.RESOURCE:
                    stringBuilder.append("!is_").append(typeName).append("(").append(parameterName).append(")");
                    break;
                case PrimitiveTypeNames.NULL_TYPE:
                    stringBuilder.append(parameterName).append(" !== null");
                    break;
                default:
                    stringBuilder.append("!(")
                            .append(parameterName).append(" instanceof ").append(typeName).append(")");
                    break;
            }
            types.add(typeName);
        }
        return ok;
    }

    private boolean appendParameterRuntimeCheck(
            String parameterName,
            StringBuilder stringBuilder,
            IIntersectionTypeSymbol intersectionTypeSymbol,
            List<String> types) {
        boolean ok = true;
        SortedMap<String, ITypeSymbol> typeSymbols = copyTypeSymbols(intersectionTypeSymbol.getTypeSymbols());
        Iterator<ITypeSymbol> iterator = typeSymbols.values().iterator();
        if (iterator.hasNext()) {
            ok = appendParameterRuntimeCheck(parameterName, stringBuilder, iterator.next(), types);
        }
        if (ok) {
            while (iterator.hasNext()) {
                stringBuilder.append(" || ");
                ok = appendParameterRuntimeCheck(parameterName, stringBuilder, iterator.next(), types);
                if (!ok) {
                    break;
                }
            }
        }
        return ok;
    }

    private SortedMap<String, ITypeSymbol> copyTypeSymbols(Map<String, ITypeSymbol> typeSymbols) {
        SortedMap<String, ITypeSymbol> copiedTypeSymbols = new TreeMap<>(typeSymbols);
        boolean containsFalseType = copiedTypeSymbols.remove(PrimitiveTypeNames.FALSE_TYPE) != null;
        boolean containsTrueType = copiedTypeSymbols.remove(PrimitiveTypeNames.TRUE_TYPE) != null;
        if (containsFalseType || containsTrueType) {
            copiedTypeSymbols.put(tsphpBoolTypeSymbol.getAbsoluteName(), tsphpBoolTypeSymbol);
        }
        return copiedTypeSymbols;
    }

    private boolean appendParameterRuntimeCheck(
            String parameterName,
            StringBuilder stringBuilder,
            IUnionTypeSymbol unionTypeSymbol,
            List<String> types) {
        boolean ok = true;
        SortedMap<String, ITypeSymbol> typeSymbols = copyTypeSymbols(unionTypeSymbol.getTypeSymbols());
        Iterator<ITypeSymbol> iterator = typeSymbols.values().iterator();
        if (iterator.hasNext()) {
            ok = appendParameterRuntimeCheck(parameterName, stringBuilder, iterator.next(), types);
        }
        if (ok) {
            while (iterator.hasNext()) {
                stringBuilder.append(" && ");
                ok = appendParameterRuntimeCheck(parameterName, stringBuilder, iterator.next(), types);
                if (!ok) {
                    break;
                }
            }
        }
        return ok;
    }
}
