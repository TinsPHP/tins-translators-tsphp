/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file ITempVariableHelper from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.IScope;
import ch.tsphp.common.ITSPHPAst;

/**
 * A Helper which provides variable names which can be used for temp variables.
 */
public interface ITempVariableHelper
{
    String getTempNameIfAlreadyExists(String identifier, IScope scope);

    String getTempVariableName(ITSPHPAst expression);

    String getTempVariableNameIfNotVariable(ITSPHPAst expression);
}
