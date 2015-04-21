/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.dtos;

import java.util.Set;

public class TypeDto
{
    public Set<String> prefixModifiers;
    public String type;
    public Set<String> suffixModifiers;

    public TypeDto(Set<String> thePrefixModifiers,
            String theType,
            Set<String> theSuffixModifiers) {
        prefixModifiers = thePrefixModifiers;
        type = theType;
        suffixModifiers = theSuffixModifiers;
    }
}
