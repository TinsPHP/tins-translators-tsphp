/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.tinsphp.common.gen.TokenTypes;
import ch.tsphp.tinsphp.common.inference.constraints.IFunctionType;

import java.util.HashMap;
import java.util.Map;

public class OperatorHelper implements IOperatorHelper
{
    private final Map<Integer, Map<String, String>> migrationFunctions = new HashMap<>();
    private final Map<Integer, String> dynamicFunctions = new HashMap<>();

    public OperatorHelper() {
        init();
    }

    private void init() {
        dynamicFunctions.put(TokenTypes.Plus, "oldSchoolAddition");
    }

    @Override
    public String getMigrationFunction(int operatorType, IFunctionType overload) {
        String migrationFunction = null;

        if (overload != null) {
            if (migrationFunctions.containsKey(operatorType)) {

                Map<String, String> overloadsWhichNeedMigration = migrationFunctions.get(operatorType);
                String signature = overload.getSignature();
                migrationFunction = overloadsWhichNeedMigration.get(signature);
            }
        } else {
            migrationFunction = dynamicFunctions.get(operatorType);
        }
        return migrationFunction;
    }
}
