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
import ch.tsphp.tinsphp.common.translation.dtos.FunctionApplicationDto;
import ch.tsphp.tinsphp.common.translation.dtos.TranslationScopeDto;
import ch.tsphp.tinsphp.common.utils.ERelation;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.common.utils.TypeHelperDto;

public class PhpPlusRuntimeCheckProvider implements IRuntimeCheckProvider
{
    private ITypeHelper typeHelper;

    public PhpPlusRuntimeCheckProvider(ITypeHelper theTypeHelper) {
        typeHelper = theTypeHelper;
    }

    @Override
    public boolean addParameterCheck(
            String identifier,
            TranslationScopeDto translationScopeDto, IVariable parameter,
            int parameterIndex) {
        return false;
    }

    @Override
    public Object getTypeCheck(
            TranslationScopeDto translationScopeDto, ITSPHPAst argumentAst, Object argument, ITypeSymbol argumentType) {
        return "cast<" + argumentType.getAbsoluteName() + ">(" + argument.toString() + ")";
    }

    //Warning! start code duplication - same as in TSPHPRuntimeCheckProvider#addReturnValueCheck
    @Override
    public void addReturnValueCheck(
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
    //Warning! end code duplication - same as in TSPHPRuntimeCheckProvider#addReturnValueCheck
}
