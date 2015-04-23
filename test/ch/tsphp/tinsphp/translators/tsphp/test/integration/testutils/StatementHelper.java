/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file StatementHelper from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatementHelper
{
    private StatementHelper() {
    }

    public static List<Object[]> getStatements(String prefix, String appendix,
            String prefixExpected, String instruction, String indent, String appendixExpected) {
        List<Object[]> collection = new ArrayList<>();

        collection.addAll(getControlStructures(prefix, "$a=1;", appendix,
                prefixExpected + "\n" + indent + "? $a;", instruction, indent, indent, "$a = 1;", appendixExpected));

        collection.addAll(Arrays.asList(new Object[][]{
                //localVariableDeclaration not possible to define via PHP
//                {prefix + "int $a;" + appendix, prefixExpected + "\n" + indent + instruction +"\n"+indent +"$a;" +
// appendixExpected},
                {prefix + ";" + appendix, prefixExpected + "\n" + indent + instruction + appendixExpected},
                {prefix + "return;" + appendix, prefixExpected + "\n" + indent + instruction + "\n" + indent
                        + "return null;" + appendixExpected},
                //TODO rstoll TINS-395 add new operator
//                {
//                        prefix + "throw new Exception();" + appendix,
//                        prefixExpected + "\n" + indent + instruction +"\n"+indent+ "throw new Exception();" +
// appendixExpected
//                },
                {prefix + "break;" + appendix, prefixExpected + "\n" + indent + instruction + "\n" + indent + "break;"
                        + "" + appendixExpected},
                {prefix + "continue;" + appendix, prefixExpected + "\n" + indent + instruction + "\n" + indent
                        + "continue;" + appendixExpected},
                {
                        prefix + "echo 'hello';" + appendix,
                        prefixExpected + "\n" + indent + instruction + "\n" + indent
                                + "echo 'hello';" + appendixExpected
                },
                {
                        prefix + "const a=1;" + appendix,
                        prefixExpected + "\n" + indent + instruction + "\n" + indent
                                + "const int a = 1;" + appendixExpected
                },
                //TODO rstoll TINS-267 translator OOP - classes
//                {
//                        prefix + "class A{}" + appendix,
//                        prefixExpected + "\n" + indent + instruction +"\n"+indent +"class A {}" + appendixExpected
//                },
                {
                        prefix + "function foo(){return;}" + appendix,
                        prefixExpected + "\n" + indent + instruction + "\n" + indent
                                + "function null foo() {\n" + indent + indent + "return null;\n" + indent + "}"
                                + appendixExpected
                },
                //TODO rstoll TINS-268 translator OOP - interfaces
//                {
//                        prefix + "interface A{}" + appendix,
//                        prefixExpected + "\n" + indent + instruction +"\n"+indent+ "interface A {}" + appendixExpected
//                },
                {
                        prefix + "use \\A;" + appendix,
                        prefixExpected + "\n" + indent + instruction + "\n" + indent + "use \\A as A;"
                                + appendixExpected
                }
        }));

        List<String[]> expressions = ExpressionHelper.getConstantExpressions();
        for (String[] expression : expressions) {
            collection.add(new Object[]{
                    prefix + expression[0] + ";" + appendix,
                    prefixExpected + "\n" + indent + instruction
                            + "\n" + indent + expression[1] + ";" + appendixExpected
            });
        }
        return collection;
    }


    public static List<Object[]> getControlStructures(String scopePrefix, String instruction, String scopeAppendix,
            String scopePrefixExpected, String prefixExpected, String indent, String indent2,
            String instructionExpected,
            String scopeAppendixExpected) {
        return Arrays.asList(new Object[][]{
                {
                        scopePrefix + instruction + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + instructionExpected + scopeAppendixExpected
                },
                {
                        scopePrefix + "{" + instruction + "}" + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + instructionExpected + scopeAppendixExpected
                },
                {
                        scopePrefix + "{ {" + instruction + "} }" + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + instructionExpected + scopeAppendixExpected
                },
                {
                        scopePrefix + "if(true)" + instruction + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + "if (true) {\n" + indent2 + "    " + instructionExpected + "\n" + indent2 + "}"
                                + scopeAppendixExpected
                },
                {
                        scopePrefix + "if(true){" + instruction + "}" + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + "if (true) {\n" + indent2 + "    " + instructionExpected + "\n" + indent2 + "}"
                                + scopeAppendixExpected
                },
                {
                        scopePrefix + "if(true) 1; else " + instruction + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + "if (true) {\n"
                                + indent2 + "    1;\n"
                                + indent2 + "} else {\n"
                                + indent2 + "    " + instructionExpected + "\n"
                                + indent2 + "}"
                                + scopeAppendixExpected
                },

                {
                        scopePrefix + "if(true){1;}else{" + instruction + "}" + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + "if (true) {\n"
                                + indent2 + "    1;\n"
                                + indent2 + "} else {\n"
                                + indent2 + "    " + instructionExpected + "\n"
                                + indent2 + "}"
                                + scopeAppendixExpected
                },
                {
                        scopePrefix + "switch(1){case 1: " + instruction + "}" + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + "switch (1) {\n"
                                + indent2 + "    case 1:\n"
                                + indent2 + "        " + instructionExpected + "\n"
                                + indent2 + "}"
                                + scopeAppendixExpected
                },
                {
                        scopePrefix + "switch(1){"
                                + "    case 1: 2; " + instruction
                                + "    default: 3; " + instruction
                                + "}" + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + "switch (1) {\n"
                                + indent2 + "    case 1:\n"
                                + indent2 + "        2;\n"
                                + indent2 + "        " + instructionExpected + "\n"
                                + indent2 + "    default:\n"
                                + indent2 + "        3;\n"
                                + indent2 + "        " + instructionExpected + "\n"
                                + indent2 + "}"
                                + scopeAppendixExpected
                },
                {
                        scopePrefix + "switch(1){"
                                + "    case 1:{ 3; " + instruction + "}"
                                + "    default: 4; { " + instruction + "} "
                                + "}" + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + "switch (1) {\n"
                                + indent2 + "    case 1:\n"
                                + indent2 + "        3;\n"
                                + indent2 + "        " + instructionExpected + "\n"
                                + indent2 + "    default:\n"
                                + indent2 + "        4;\n"
                                + indent2 + "        " + instructionExpected + "\n"
                                + indent2 + "}"
                                + scopeAppendixExpected
                },
                {
                        scopePrefix + "for(;;) " + instruction + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + "for (; ; ) {\n" + indent2 + "    " + instructionExpected + "\n" + indent2 + "}"
                                + scopeAppendixExpected
                },
                {
                        scopePrefix + "for(;;){ " + instruction + "}" + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + "for (; ; ) {\n" + indent2 + "    " + instructionExpected + "\n" + indent2 + "}"
                                + scopeAppendixExpected
                },
                //TODO rstoll TINS-247 translate procedural - foreach header
//                {
//                        prefix + "foreach([] as int $k)" + instruction + appendix,
//                        prefixExpected + "\n" + indent
//                                + "foreach ([] as $k) {\n" + indent + "    " + instructionExpected + "\n" + indent
// + "}"
//                                + appendixExpected
//                },
//                {
//                        prefix + "foreach([] as int $k){" + instruction + "}" + appendix,
//                        prefixExpected + "\n" + indent
//                                + "foreach ([] as $k) {\n" + indent + "    " + instructionExpected + "\n" + indent
// + "}"
//                                + appendixExpected
//                },
                {
                        scopePrefix + "while(true)" + instruction + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + "while (true) {\n" + indent2 + "    " + instructionExpected + "\n" + indent2 + "}"
                                + scopeAppendixExpected
                },
                {
                        scopePrefix + "while(true){" + instruction + "}" + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + "while (true) {\n" + indent2 + "    " + instructionExpected + "\n" + indent2 + "}"
                                + scopeAppendixExpected
                },
                {
                        scopePrefix + "do " + instruction + " while(true);" + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + "do {\n" + indent2 + "    " + instructionExpected + "\n" + indent2 + "} while (true);"
                                + scopeAppendixExpected
                },
                {
                        scopePrefix + "do{ " + instruction + "}while(true);" + scopeAppendix,
                        scopePrefixExpected + "\n" + indent + prefixExpected + "\n" + indent2
                                + "do {\n" + indent2 + "    " + instructionExpected + "\n" + indent2 + "} while (true);"
                                + scopeAppendixExpected
                },
                //TODO rstoll TINS-248 translate procedural - catch header
//                {
//                        prefix + "try{" + instruction + "}catch(\\Exception $e){}" + appendix,
//                        prefixExpected + "\n" + indent
//                                + "try {\n"
//                                + indent + "    " + instructionExpected + "\n"
//                                + indent + "} catch (\\Exception $e) {\n"
//                                + indent + "}"
//                                + appendixExpected
//                },
//                {
//                        prefix + "try{$a=1;}catch(\\Exception $e){" + instruction + "}" + appendix,
//                        prefixExpected + "\n" + indent
//                                + "try {\n"
//                                + indent + "    $a = 1;\n"
//                                + indent + "} catch (\\Exception $e) {\n"
//                                + indent + "    " + instructionExpected + "\n"
//                                + indent + "}"
//                                + appendixExpected
//                }
        });
    }
}
