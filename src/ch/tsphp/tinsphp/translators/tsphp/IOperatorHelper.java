/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.inference.constraints.OverloadApplicationDto;
import ch.tsphp.tinsphp.common.translation.dtos.FunctionApplicationDto;

public interface IOperatorHelper
{
    void turnIntoMigrationFunctionIfRequired(
            FunctionApplicationDto functionApplicationDto,
            OverloadApplicationDto dto,
            IBindingCollection currentBindings,
            ITSPHPAst leftHandSide,
            ITSPHPAst argumentsAst);
}
