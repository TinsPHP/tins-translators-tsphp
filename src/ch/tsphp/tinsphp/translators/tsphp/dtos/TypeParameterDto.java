/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.dtos;

public class TypeParameterDto
{
    public String lowerBound;
    public String typeVariable;
    public String upperBound;

    public TypeParameterDto(String theLowerBound, String theTypeVariable, String theUpperBound) {
        lowerBound = theLowerBound;
        typeVariable = theTypeVariable;
        upperBound = theUpperBound;
    }
}
