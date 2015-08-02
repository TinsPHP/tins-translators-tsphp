/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.symbols.IContainerTypeSymbol;
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

public class TSPHPRuntimeCheckProvider implements IRuntimeCheckProvider
{
    private final ITypeTransformer typeTransformer;
    private final IOutputIssueMessageProvider messageProvider;
    private final ITypeSymbol tsphpBoolTypeSymbol;
    private final ITempVariableHelper tempVariableHelper;


    public TSPHPRuntimeCheckProvider(
            ITypeTransformer theTypeTransformer,
            ITempVariableHelper theTempVariableHelper,
            IOutputIssueMessageProvider theMessageProvider,
            ITypeSymbol theTsphpBoolTypeSymbol) {
        typeTransformer = theTypeTransformer;
        messageProvider = theMessageProvider;
        tsphpBoolTypeSymbol = theTsphpBoolTypeSymbol;
        tempVariableHelper = theTempVariableHelper;
    }

    @Override
    public boolean addParameterCheck(
            String identifier,
            List<Pair<String, String>> parameterRuntimeChecks,
            IBindingCollection bindings,
            IVariable parameter,
            int parameterIndex,
            ParameterDto parameterDto) {

        StringBuilder stringBuilder = new StringBuilder();
        String parameterId = parameter.getName();
        String typeVariable = bindings.getTypeVariable(parameter.getAbsoluteName());
        IIntersectionTypeSymbol upperTypeBounds = bindings.getUpperTypeBounds(typeVariable);
        List<String> types = new ArrayList<>();
        boolean canPerformRuntimeChecks = appendParameterRuntimeCheck(
                stringBuilder, parameterId, upperTypeBounds, types);

        if (canPerformRuntimeChecks && !types.isEmpty()) {
            String errorMessage = messageProvider.getParameterRuntimeCheckMessage(
                    identifier, parameterId, parameterIndex + 1, types);
            parameterRuntimeChecks.add(new Pair<>(stringBuilder.toString(), errorMessage));
        }
        return canPerformRuntimeChecks;
    }

    private boolean appendParameterRuntimeCheck(
            StringBuilder stringBuilder,
            String parameterId,
            ITypeSymbol typeSymbol,
            List<String> types) {
        boolean ok = true;
        if (typeSymbol instanceof IUnionTypeSymbol) {
            ok = appendParameterRuntimeCheck(stringBuilder, parameterId, (IUnionTypeSymbol) typeSymbol, types);
        } else if (typeSymbol instanceof IIntersectionTypeSymbol) {
            stringBuilder.append("(");
            ok = appendParameterRuntimeCheck(stringBuilder, parameterId, (IIntersectionTypeSymbol) typeSymbol, types);
            stringBuilder.append(")");
        } else if (typeSymbol instanceof IConvertibleTypeSymbol) {
            ok = false;
        } else {
            String typeName = typeSymbol.getAbsoluteName();
            stringBuilder.append("!").append(getTypeCheckExpression(typeName, parameterId));
            types.add(typeName);
        }
        return ok;
    }

    private String getTypeCheckExpression(String typeName, String variableId) {
        String typeCheckExpression;
        switch (typeName) {
            case PrimitiveTypeNames.FALSE_TYPE:
                typeCheckExpression = "(" + variableId + " !== false)";
                break;
            case PrimitiveTypeNames.TRUE_TYPE:
                typeCheckExpression = "(" + variableId + " !== true)";
                break;
            case PrimitiveTypeNames.NULL_TYPE:
                typeCheckExpression = "(" + variableId + " !== null)";
                break;
            default:
                typeCheckExpression = "(" + variableId + " <: " + typeName + ")";
                break;
        }
        return typeCheckExpression;
    }


    private boolean appendParameterRuntimeCheck(
            StringBuilder stringBuilder,
            String parameterName,
            IIntersectionTypeSymbol intersectionTypeSymbol,
            List<String> types) {
        boolean ok = true;
        SortedMap<String, ITypeSymbol> typeSymbols = copyTypeSymbols(intersectionTypeSymbol.getTypeSymbols());
        Iterator<ITypeSymbol> iterator = typeSymbols.values().iterator();
        if (iterator.hasNext()) {
            ok = appendParameterRuntimeCheck(stringBuilder, parameterName, iterator.next(), types);
        }
        if (ok) {
            while (iterator.hasNext()) {
                stringBuilder.append(" || ");
                ok = appendParameterRuntimeCheck(stringBuilder, parameterName, iterator.next(), types);
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
            StringBuilder stringBuilder,
            String parameterId,
            IUnionTypeSymbol unionTypeSymbol,
            List<String> types) {
        boolean ok = true;
        SortedMap<String, ITypeSymbol> typeSymbols = copyTypeSymbols(unionTypeSymbol.getTypeSymbols());
        Iterator<ITypeSymbol> iterator = typeSymbols.values().iterator();
        if (iterator.hasNext()) {
            ok = appendParameterRuntimeCheck(stringBuilder, parameterId, iterator.next(), types);
        }
        if (ok) {
            while (iterator.hasNext()) {
                stringBuilder.append(" && ");
                ok = appendParameterRuntimeCheck(stringBuilder, parameterId, iterator.next(), types);
                if (!ok) {
                    break;
                }
            }
        }
        return ok;
    }


    @Override
    public Object getTypeCheck(ITSPHPAst expressionAst, Object expressionTemplate, ITypeSymbol typeSymbol) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> types = new ArrayList<>();
        String firstExpression = expressionAst.getText();
        String tempVariable = tempVariableHelper.getTempVariableNameIfNotVariable(expressionAst);
        if (!firstExpression.equals(tempVariable)) {
            //need to use a temp variable, hence the first expression needs to be the assignment
            firstExpression = "(" + tempVariable + " = (" + expressionTemplate.toString() + "))";
        }
        boolean canPerformRuntimeChecks = appendTypeCheck(
                stringBuilder, firstExpression, tempVariable, typeSymbol, "", types);

        Object newArgument = expressionTemplate;
        if (canPerformRuntimeChecks) {
            if (types.size() == 1) {
                newArgument = getTypeCast(types.get(0), expressionTemplate.toString());
            } else {
                stringBuilder.insert(0, '(');
                String errorMessage = messageProvider.getTypeCheckError(tempVariable, types);
                stringBuilder.append("\\trigger_error('").append(errorMessage).append("', \\E_USER_ERROR)");
                stringBuilder.append(')');
                newArgument = stringBuilder;
            }
        }
        return newArgument;
    }

    private boolean appendTypeCheck(
            StringBuilder stringBuilder,
            String firstExpression,
            String tempVariable,
            ITypeSymbol typeSymbol,
            String suffixCheck,
            List<String> types) {

        boolean ok = true;
        if (typeSymbol instanceof IUnionTypeSymbol) {
            IUnionTypeSymbol unionTypeSymbol = (IUnionTypeSymbol) typeSymbol;
            SortedMap<String, ITypeSymbol> typeSymbols = new TreeMap<>(unionTypeSymbol.getTypeSymbols());
            boolean allOk = true;
            for (ITypeSymbol innerTypeSymbols : typeSymbols.values()) {
                if (!appendTypeCheck(
                        stringBuilder, firstExpression, tempVariable, innerTypeSymbols, suffixCheck, types)) {
                    allOk = false;
                    break;
                }

            }
            ok = allOk;
        } else if (typeSymbol instanceof IIntersectionTypeSymbol) {
            ok = appendTypeCheck(
                    stringBuilder,
                    firstExpression,
                    tempVariable,
                    (IIntersectionTypeSymbol) typeSymbol,
                    suffixCheck,
                    types);
            //TODO TINS-602 runtime check for convertible types
//        } else if (typeSymbol instanceof IConvertibleTypeSymbol) {
//            String typeName = typeSymbol.getAbsoluteName();
//            appendTypeCheck(stringBuilder, firstExpression, tempVariable, suffixCheck, types, typeName);
//        } else {
        } else {
            String typeName = typeSymbol.getAbsoluteName();
            appendTypeCheck(stringBuilder, firstExpression, tempVariable, suffixCheck, types, typeName);
        }
        return ok;
    }

    private void appendTypeCheck(
            StringBuilder stringBuilder,
            String firstExpression,
            String tempVariable,
            String suffixCheck,
            List<String> types,
            String typeName) {
        stringBuilder.append(getTypeCheckExpression(typeName, firstExpression)).append(suffixCheck)
                .append(" ? ").append(getTypeCast(typeName, tempVariable)).append(" : ");
        types.add(typeName);
    }

    private String getTypeCast(String typeName, String variableId) {
        switch (typeName) {
            case PrimitiveTypeNames.FALSE_TYPE:
                return "false";
            case PrimitiveTypeNames.TRUE_TYPE:
                return "true";
            case PrimitiveTypeNames.NULL_TYPE:
                return "null";
            default:
                return "cast(" + variableId + ", " + typeName + ")";
        }
    }

    private boolean appendTypeCheck(
            StringBuilder stringBuilder,
            String firstExpression,
            String tempVariable,
            IIntersectionTypeSymbol intersectionTypeSymbol,
            String suffixCheck,
            List<String> types) {
        boolean ok = true;

        boolean hadNoContainerTypes = true;
        for (ITypeSymbol typeSymbol : intersectionTypeSymbol.getTypeSymbols().values()) {
            if (typeSymbol instanceof IContainerTypeSymbol) {
                hadNoContainerTypes = false;
                break;
            }
        }

        if (hadNoContainerTypes) {
            Pair<ITypeSymbol, Boolean> pair = typeTransformer.getType(intersectionTypeSymbol);
            appendTypeCheck(
                    stringBuilder, firstExpression, tempVariable, suffixCheck, types, pair.first.getAbsoluteName());
        } else {
            //TODO TINS-604 runtime check with container types
        }
        return ok;
    }
}
