/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */


package ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InstructionHelper
{

    public static Collection<Object[]> getInstructions(String prefix, String appendix,
            String prefixExpected, String instruction, String indent, String indent2, String appendixExpected) {


        List<Object[]> collection = new ArrayList<>();

        collection.addAll(StatementHelper.getControlStructures(
                prefix, "$a=1;", appendix,
                prefixExpected + "\n    int $a;", instruction, indent, indent2, "$a = 1;", appendixExpected));

        List<String[]> expressions = ExpressionHelper.getAllExpressions(prefix.length());
        for (Object[] expression : expressions) {
            collection.add(new String[]{
                    prefix + expression[0] + ";" + appendix,
                    prefixExpected + "\n" + indent + instruction
                            + "\n" + indent2 + expression[1] + ";" + appendixExpected
            });
        }
        return collection;
    }
}
