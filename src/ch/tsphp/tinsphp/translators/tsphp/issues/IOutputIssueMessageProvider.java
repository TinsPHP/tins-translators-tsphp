/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.issues;

import java.util.List;

public interface IOutputIssueMessageProvider
{
    String getParameterRuntimeCheckMessage(
            String identifier, String parameterName, int parameterIndex, List<String> types);

    String getWrongApplication(String key, String text, List<String> arguments, List<String> overloads);
}
