/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.issues;

import java.util.Iterator;
import java.util.List;

public class HardCodedOutputIssueMessageProvider implements IOutputIssueMessageProvider
{
    @Override
    public String getParameterRuntimeCheckMessage(
            String identifier, String parameterName, int parameterIndex, List<String> types) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Argument ").append(parameterIndex).append(" passed to ").append(identifier)
                .append(" (parameter ").append(parameterName).append(") must be a value of type ");
        appendTypes(stringBuilder, types);
        stringBuilder.append(".");
        return stringBuilder.toString();
    }

    private void appendTypes(StringBuilder stringBuilder, List<String> types) {
        stringBuilder.append(types.get(0));
        int size = types.size();
        int typeIndex = 1;
        while (typeIndex < size - 1) {
            stringBuilder.append(", ").append(types.get(typeIndex));
            ++typeIndex;
        }
        if (typeIndex < size) {
            stringBuilder.append(" or ").append(types.get(typeIndex));
        }
    }

    @Override
    public String getTypeCheckError(String variableId, List<String> types) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The variable " + variableId + " must hold a value of type ");
        appendTypes(stringBuilder, types);
        stringBuilder.append(".");
        return stringBuilder.toString();
    }

    @Override
    public String getWrongApplication(String key, String identifier, List<String> arguments, List<String> overloads) {
        String kind;
        switch (key) {
            case "wrongFunctionUsage":
                kind = "function";
                break;
            case "wrongOperatorUsage":
                kind = "operator";
                break;
            default:
                kind = "application";
                break;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'No applicable overload found for the ")
                .append(kind).append(" ").append(identifier).append("'.PHP_EOL.'Given argument types: ");
        Iterator<String> iterator = arguments.iterator();
        stringBuilder.append(iterator.next());
        while (iterator.hasNext()) {
            stringBuilder.append(" x ").append(iterator.next());
        }
        stringBuilder.append("'.PHP_EOL.'Existing overloads:");
        for (String overload : overloads) {
            stringBuilder.append("'.PHP_EOL.'").append(overload);
        }
        stringBuilder.append("'");
        return stringBuilder.toString();
    }
}
