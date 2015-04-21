/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.dtos;

public class ParameterDto
{
    public TypeDto type;
    public String variableId;
    public String defaultValue;

    public ParameterDto(
            TypeDto theType,
            String theVariableId,
            String theDefaultValue) {
        type = theType;
        variableId = theVariableId;
        defaultValue = theDefaultValue;
    }
}
