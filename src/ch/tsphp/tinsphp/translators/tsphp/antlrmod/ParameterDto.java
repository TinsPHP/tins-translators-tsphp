/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.antlrmod;

import org.antlr.stringtemplate.StringTemplate;

import java.util.Set;

public class ParameterDto
{
    public Set<String> prefixModifiers;
    public StringTemplate type;
    public Set<String> suffixModifiers;
    public String variableId;
    public String defaultValue;

    public ParameterDto(
            Set<String> thePrefixModifiers,
            StringTemplate theType,
            Set<String> theSuffixModifiers,
            String theVariableId,
            String theDefaultValue) {
        prefixModifiers = thePrefixModifiers;
        type = theType;
        suffixModifiers = theSuffixModifiers;
        variableId = theVariableId;
        defaultValue = theDefaultValue;
    }
}
