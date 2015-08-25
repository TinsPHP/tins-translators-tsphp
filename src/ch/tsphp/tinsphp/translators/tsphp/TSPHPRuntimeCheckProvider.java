/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.ITypeVariableReference;
import ch.tsphp.tinsphp.common.inference.constraints.IVariable;
import ch.tsphp.tinsphp.common.symbols.IConvertibleTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IIntersectionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.PrimitiveTypeNames;
import ch.tsphp.tinsphp.common.translation.dtos.FunctionApplicationDto;
import ch.tsphp.tinsphp.common.translation.dtos.TranslationScopeDto;
import ch.tsphp.tinsphp.common.utils.ERelation;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.common.utils.Pair;
import ch.tsphp.tinsphp.common.utils.TypeHelperDto;
import ch.tsphp.tinsphp.translators.tsphp.issues.IOutputIssueMessageProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class TSPHPRuntimeCheckProvider implements IRuntimeCheckProvider
{
    private final ITypeHelper typeHelper;
    private final ITypeTransformer typeTransformer;
    private final IOutputIssueMessageProvider messageProvider;
    private final ITypeSymbol tsphpBoolTypeSymbol;
    private final ITempVariableHelper tempVariableHelper;

    @SuppressWarnings("checkstyle:parameternumber")
    public TSPHPRuntimeCheckProvider(
            ITypeHelper theTypeHelper,
            ITypeTransformer theTypeTransformer,
            ITempVariableHelper theTempVariableHelper,
            IOutputIssueMessageProvider theMessageProvider,
            ITypeSymbol theTsphpBoolTypeSymbol) {
        typeHelper = theTypeHelper;
        typeTransformer = theTypeTransformer;
        messageProvider = theMessageProvider;
        tsphpBoolTypeSymbol = theTsphpBoolTypeSymbol;
        tempVariableHelper = theTempVariableHelper;
    }

    @Override
    public boolean addParameterCheck(
            String identifier,
            TranslationScopeDto translationScopeDto,
            IVariable parameter,
            int parameterIndex) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("if (");
        String parameterId = parameter.getName();
        IBindingCollection bindingCollection = translationScopeDto.bindingCollection;
        String typeVariable = bindingCollection.getTypeVariable(parameter.getAbsoluteName());
        IIntersectionTypeSymbol upperTypeBounds = bindingCollection.getUpperTypeBounds(typeVariable);
        List<String> types = new ArrayList<>();
        boolean canPerformRuntimeChecks = appendParameterRuntimeCheck(
                stringBuilder, parameterId, upperTypeBounds, types);

        if (canPerformRuntimeChecks && !types.isEmpty()) {
            stringBuilder.append(") {\n")
                    .append("    \\trigger_error('")
                    .append(messageProvider.getParameterRuntimeCheckMessage(
                            identifier, parameterId, parameterIndex + 1, types))
                    .append("', \\E_USER_ERROR);\n");
            stringBuilder.append("}");
            translationScopeDto.statements.add(stringBuilder.toString());
        }
        return canPerformRuntimeChecks;
    }

    private boolean appendParameterRuntimeCheck(
            StringBuilder stringBuilder,
            String parameterId,
            ITypeSymbol typeSymbol,
            List<String> types) {
        boolean ok = true;
        if (typeSymbol instanceof IUnionTypeSymbol && typeSymbol != tsphpBoolTypeSymbol) {
            ok = appendParameterRuntimeCheck(stringBuilder, parameterId, (IUnionTypeSymbol) typeSymbol, types);
        } else if (typeSymbol instanceof IIntersectionTypeSymbol) {
            stringBuilder.append("(");
            ok = appendParameterRuntimeCheck(stringBuilder, parameterId, (IIntersectionTypeSymbol) typeSymbol, types);
            stringBuilder.append(")");
        } else if (typeSymbol instanceof IConvertibleTypeSymbol) {
            ok = false;
        } else {
            String typeName = typeSymbol.getAbsoluteName();
            stringBuilder.append("!(").append(getTypeCheckExpression(typeName, parameterId)).append(")");
            types.add(typeName);
        }
        return ok;
    }

    private String getTypeCheckExpression(String typeName, String variableId) {
        String typeCheckExpression;
        switch (typeName) {
            case PrimitiveTypeNames.FALSE_TYPE:
                typeCheckExpression = "" + variableId + " === false";
                break;
            case PrimitiveTypeNames.TRUE_TYPE:
                typeCheckExpression = "" + variableId + " === true";
                break;
            case PrimitiveTypeNames.NULL_TYPE:
                typeCheckExpression = "" + variableId + " === null";
                break;
            default:
                typeCheckExpression = "" + variableId + " <: " + typeName;
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
        if (copiedTypeSymbols.containsKey(PrimitiveTypeNames.FALSE_TYPE)
                && copiedTypeSymbols.containsKey(PrimitiveTypeNames.TRUE_TYPE)) {
            copiedTypeSymbols.remove(PrimitiveTypeNames.FALSE_TYPE);
            copiedTypeSymbols.remove(PrimitiveTypeNames.TRUE_TYPE);
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
    public Object getTypeCheck(
            TranslationScopeDto translationScopeDto,
            ITSPHPAst expressionAst,
            Object expressionTemplate,
            ITypeSymbol typeSymbol) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> types = new ArrayList<>();
        String firstExpression = expressionTemplate.toString();
        String tempVariable = tempVariableHelper.getTempVariableNameIfNotVariable(expressionAst);
        boolean usesTempVariable = false;
        if (!firstExpression.equals(tempVariable)) {
            usesTempVariable = true;
            //need to use a temp variable, hence the first expression needs to be the assignment
            firstExpression = "(" + tempVariable + " = (" + expressionTemplate.toString() + "))";
        }
        boolean canPerformRuntimeChecks = appendTypeCheck(
                stringBuilder, firstExpression, tempVariable, typeSymbol, "", types);

        Object newArgument = expressionTemplate;
        if (canPerformRuntimeChecks) {
            if (types.size() == 1) {
                String typeName = types.get(0);
                if (!typeName.equals(PrimitiveTypeNames.FALSE_TYPE)
                        && !typeName.equals(PrimitiveTypeNames.TRUE_TYPE)
                        && !typeName.equals(PrimitiveTypeNames.NULL_TYPE)) {
                    firstExpression = expressionTemplate.toString();
                    usesTempVariable = false;
                }
                if (!typeName.startsWith("{as")) {
                    newArgument = getTypeCast(typeName, firstExpression, tempVariable);
                } else {
                    //convertible types are different, see TINS-602 runtime check for convertible types
                    String targetTypeName = typeName.substring("{as ".length(), typeName.length() - 1);
                    newArgument = getAsOperation(firstExpression, targetTypeName);
                }
            } else {
                stringBuilder.insert(0, '(');
                String errorMessage = messageProvider.getTypeCheckError(tempVariable, types);
                stringBuilder.append("\\trigger_error('").append(errorMessage).append("', \\E_USER_ERROR)");
                stringBuilder.append(')');
                newArgument = stringBuilder;
            }
        }

        if (usesTempVariable) {
            translationScopeDto.statements.add("mixed " + tempVariable + ";");
        }
        return newArgument;
    }

    //Warning! start code duplication - same as in PhpPlusRuntimeCheckProvider#addReturnValueCheckIfRequired
    @Override
    public void addReturnValueCheckIfRequired(
            TranslationScopeDto translationScopeDto,
            FunctionApplicationDto functionApplicationDto,
            ITSPHPAst leftHandSide,
            ITypeSymbol returnType,
            boolean isConstantReturnType) {

        IBindingCollection bindingCollection = translationScopeDto.bindingCollection;
        String leftHandSideId = leftHandSide.getSymbol().getAbsoluteName();
        ITypeVariableReference reference = bindingCollection.getTypeVariableReference(leftHandSideId);
        String lhsTypeVariable = reference.getTypeVariable();

        ITypeSymbol typeSymbol = bindingCollection.getUpperTypeBounds(lhsTypeVariable);
        if (typeSymbol == null) {
            typeSymbol = bindingCollection.getLowerTypeBounds(lhsTypeVariable);
        }

        //TODO TINS-394 introduce nothing as own type - then we do not need this check
        if (returnType != null) {
            //if the left hand side is more specific than the return type then we need to cast
            if (isConstantReturnType && !reference.hasFixedType()) {
                //if overload is parametric polymorphic then we need to cast to the parametric type
                functionApplicationDto.returnRuntimeCheck = "cast<" + lhsTypeVariable + ">(%returnRuntimeCheck%)";
            } else {
                TypeHelperDto result = typeHelper.isFirstSameOrSubTypeOfSecond(returnType, typeSymbol);
                if (result.relation == ERelation.HAS_NO_RELATION) {
                    if (reference.hasFixedType()) {
                        functionApplicationDto.returnRuntimeCheck = getTypeCheck(
                                translationScopeDto,
                                leftHandSide,
                                "%returnRuntimeCheck%",
                                typeSymbol).toString();
                    } else {
                        //if overload is parametric polymorphic then we need to cast to the parametric type
                        functionApplicationDto.returnRuntimeCheck
                                = "cast<" + lhsTypeVariable + ">(%returnRuntimeCheck%)";
                    }
                }
            }
        }
    }
    //Warning! end code duplication - same as in PhpPlusRuntimeCheckProvider#addReturnValueCheckIfRequired


    private String getAsOperation(String expression, String targetTypeName) {
        return expression + " as " + targetTypeName;
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
            ok = appendTypeCheck(
                    stringBuilder,
                    firstExpression,
                    tempVariable,
                    (IUnionTypeSymbol) typeSymbol,
                    suffixCheck,
                    types);
        } else if (typeSymbol instanceof IIntersectionTypeSymbol) {
            ok = appendTypeCheck(
                    stringBuilder,
                    firstExpression,
                    tempVariable,
                    (IIntersectionTypeSymbol) typeSymbol,
                    suffixCheck,
                    types);
        } else if (typeSymbol instanceof IConvertibleTypeSymbol) {
            appendConversion(
                    stringBuilder,
                    firstExpression,
                    suffixCheck,
                    (IConvertibleTypeSymbol) typeSymbol,
                    types);
        } else {
            String typeName = typeSymbol.getAbsoluteName();
            String typeCast = getTypeCast(typeName, tempVariable, tempVariable);
            appendTypeCheck(stringBuilder, firstExpression, suffixCheck, types, typeName, typeCast);
        }
        return ok;
    }

    private void appendConversion(
            StringBuilder stringBuilder,
            String firstExpression,
            String suffixCheck,
            IConvertibleTypeSymbol convertibleTypeSymbol,
            List<String> types) {
        String targetTypeName;
        if (convertibleTypeSymbol.isFixed()) {
            if (convertibleTypeSymbol.hasUpperTypeBounds()) {
                IIntersectionTypeSymbol upperTypeBounds = convertibleTypeSymbol.getUpperTypeBounds();
                targetTypeName = typeTransformer.getType(upperTypeBounds).first.getAbsoluteName();
            } else {
                IUnionTypeSymbol lowerTypeBounds = convertibleTypeSymbol.getLowerTypeBounds();
                targetTypeName = typeTransformer.getType(lowerTypeBounds).first.getAbsoluteName();
            }
        } else {
            targetTypeName = convertibleTypeSymbol.getTypeVariable();
        }
        String typeName = "{as " + targetTypeName + "}";
        String typeCast = getAsOperation(firstExpression, targetTypeName);
        appendTypeCheck(stringBuilder, firstExpression, suffixCheck, types, typeName, typeCast);
    }

    private boolean appendTypeCheck(
            StringBuilder stringBuilder,
            String firstExpression,
            String tempVariable,
            IUnionTypeSymbol unionTypeSymbol,
            String suffixCheck,
            List<String> types) {
        boolean ok;
        SortedMap<String, ITypeSymbol> typeSymbols = new TreeMap<>(unionTypeSymbol.getTypeSymbols());
        if (typeSymbols.containsKey(PrimitiveTypeNames.FALSE_TYPE)
                && typeSymbols.containsKey(PrimitiveTypeNames.TRUE_TYPE)) {
            typeSymbols.remove(PrimitiveTypeNames.FALSE_TYPE);
            typeSymbols.remove(PrimitiveTypeNames.TRUE_TYPE);
            typeSymbols.put(tsphpBoolTypeSymbol.getAbsoluteName(), tsphpBoolTypeSymbol);
        }
        boolean allOk = true;
        String expression = firstExpression;
        for (ITypeSymbol innerTypeSymbols : typeSymbols.values()) {
            if (!appendTypeCheck(stringBuilder, expression, tempVariable, innerTypeSymbols, suffixCheck, types)) {
                allOk = false;
                break;
            }
            expression = tempVariable;
        }
        ok = allOk;
        return ok;
    }

    private void appendTypeCheck(
            StringBuilder stringBuilder,
            String firstExpression,
            String suffixCheck,
            List<String> types,
            String typeName,
            String typeCast) {
        stringBuilder.append(getTypeCheckExpression(typeName, firstExpression)).append(suffixCheck)
                .append(" ? ").append(typeCast).append(" : ");
        types.add(typeName);
    }

    private String getTypeCast(String typeName, String firstExpression, String tempVariable) {
        String typeCast = null;
        String value = null;
        switch (typeName) {
            case PrimitiveTypeNames.FALSE_TYPE:
                value = "false";
                break;
            case PrimitiveTypeNames.TRUE_TYPE:
                value = "true";
                break;
            case PrimitiveTypeNames.NULL_TYPE:
                value = "null";
                break;
            default:
                typeCast = "cast<" + typeName + ">(" + firstExpression + ")";
        }
        if (typeCast == null) {
            typeCast = "(" + firstExpression + " === " + value + " ? " + value + " : \\trigger_error('"
                    + messageProvider.getValueCheckError(tempVariable, value) + "', \\E_USER_ERROR))";
        }
        return typeCast;
    }

    private boolean appendTypeCheck(
            StringBuilder stringBuilder,
            String firstExpression,
            String tempVariable,
            IIntersectionTypeSymbol intersectionTypeSymbol,
            String suffixCheck,
            List<String> types) {
        boolean ok = true;

        boolean needToHandleUnionType = true;
        boolean hadNoContainerTypes = true;
        Iterator<ITypeSymbol> iterator = intersectionTypeSymbol.getTypeSymbols().values().iterator();
        ITypeSymbol firstTypeSymbol = iterator.next();
        if (iterator.hasNext()) {
            hadNoContainerTypes = !(firstTypeSymbol instanceof IUnionTypeSymbol);
            while (hadNoContainerTypes || iterator.hasNext()) {
                hadNoContainerTypes = !(iterator.next() instanceof IUnionTypeSymbol);
            }
        } else if (firstTypeSymbol instanceof IUnionTypeSymbol) {
            appendTypeCheck(
                    stringBuilder,
                    firstExpression,
                    tempVariable,
                    (IUnionTypeSymbol) firstTypeSymbol,
                    suffixCheck,
                    types);
            hadNoContainerTypes = false;
            needToHandleUnionType = false;
        }

        if (hadNoContainerTypes) {
            Pair<ITypeSymbol, Boolean> pair = typeTransformer.getType(intersectionTypeSymbol);
            String typeName = pair.first.getAbsoluteName();
            String typeCast = getTypeCast(typeName, firstExpression, tempVariable);
            appendTypeCheck(stringBuilder, firstExpression, suffixCheck, types, typeName, typeCast);
        } else if (needToHandleUnionType) {
            //TODO TINS-604 runtime check with container types
            throw new UnsupportedOperationException("type check for intersection and combined union");
        }
        return ok;
    }
}
