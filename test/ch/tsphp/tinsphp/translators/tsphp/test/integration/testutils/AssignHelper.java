/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file AssignHelper from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils;

import java.util.Collection;

public class AssignHelper
{

    private static Collection<Object[]> collection;
    private static boolean isDeclaration;

    public static void addCastingAssignment(Collection<Object[]> theCollection, boolean isDeclaration) {
        collection = theCollection;
        AssignHelper.isDeclaration = isDeclaration;

        String[][] noCastNeededTypes = new String[][]{
                {"bool", "false"}
        };
        String[][] castTypes = new String[][]{
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"array", "null"},
                {"array", "null"},
                {"resource", "null"},
                {"resource", "null"},
                {"mixed", "null"},
                {"Exception", "null"},
                {"Exception!", "null"},
                {"ErrorException", "null"},
                {"ErrorException!", "null"}
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "bool");

        noCastNeededTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
        };
        castTypes = new String[][]{
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"array", "null"},
                {"array", "null"},
                {"resource", "null"},
                {"resource", "null"},
                {"mixed", "null"},
                {"Exception", "null"},
                {"Exception!", "null"},
                {"ErrorException", "null"},
                {"ErrorException!", "null"}
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "bool!");

        noCastNeededTypes = new String[][]{
                {"bool", "false"},
                {"bool?", "null"},
        };
        castTypes = new String[][]{
                {"bool!", "false"},
                {"bool!?", "null"},
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"array", "null"},
                {"array", "null"},
                {"resource", "null"},
                {"resource", "null"},
                {"mixed", "null"},
                {"Exception", "null"},
                {"Exception!", "null"},
                {"ErrorException", "null"},
                {"ErrorException!", "null"}
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "bool?");

        noCastNeededTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
        };
        castTypes = new String[][]{
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"array", "null"},
                {"array", "null"},
                {"resource", "null"},
                {"resource", "null"},
                {"mixed", "null"},
                {"Exception", "null"},
                {"Exception!", "null"},
                {"ErrorException", "null"},
                {"ErrorException!", "null"}
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "bool!?");

        noCastNeededTypes = new String[][]{
                {"int", "0"},
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"mixed", "null"},
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "int");

        noCastNeededTypes = new String[][]{
                {"int", "0"},
                {"int!", "false"},
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"mixed", "null"},
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "int!");

        noCastNeededTypes = new String[][]{
                {"int", "0"},
                {"int?", "null"},
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int!", "false"},
                {"int!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"mixed", "null"},
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "int?");

        noCastNeededTypes = new String[][]{
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"mixed", "null"},
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "int!?");

        noCastNeededTypes = new String[][]{
                {"float", "0.0"},
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"mixed", "null"},
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "float");

        noCastNeededTypes = new String[][]{
                {"float", "0.0"},
                {"float!", "false"},
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"mixed", "null"},
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "float!");

        noCastNeededTypes = new String[][]{
                {"float", "0.0"},
                {"float?", "null"},
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float!", "false"},
                {"float!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"mixed", "null"},
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "float?");

        noCastNeededTypes = new String[][]{
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"mixed", "null"},
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "float!?");

        noCastNeededTypes = new String[][]{
                {"string", "''"},
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"mixed", "null"},
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "string");

        noCastNeededTypes = new String[][]{
                {"string", "''"},
                {"string!", "false"},
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string?", "null"},
                {"string!?", "null"},
                {"mixed", "null"},
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "string!");

        noCastNeededTypes = new String[][]{
                {"string", "''"},
                {"string?", "null"},
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string!", "false"},
                {"string!?", "null"},
                {"mixed", "null"},
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "string?");

        noCastNeededTypes = new String[][]{
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"mixed", "null"},
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "string!?");

        noCastNeededTypes = new String[][]{
                {"array", "null"}
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"array!", "null"},
                {"resource", "null"},
                {"resource!", "null"},
                {"mixed", "null"},
                {"Exception", "null"},
                {"Exception!", "null"},
                {"ErrorException", "null"},
                {"ErrorException!", "null"}
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "array");

        noCastNeededTypes = new String[][]{
                {"array", "null"},
                {"array!", "null"}
        };
        castTypes = new String[][]{
                {"bool", "false"},
                {"bool!", "false"},
                {"bool?", "null"},
                {"bool!?", "null"},
                {"int", "0"},
                {"int!", "false"},
                {"int?", "null"},
                {"int!?", "null"},
                {"float", "0.0"},
                {"float!", "false"},
                {"float?", "null"},
                {"float!?", "null"},
                {"string", "''"},
                {"string!", "false"},
                {"string?", "null"},
                {"string!?", "null"},
                {"resource", "null"},
                {"resource!", "null"},
                {"mixed", "null"},
                {"Exception", "null"},
                {"Exception!", "null"},
                {"ErrorException", "null"},
                {"ErrorException!", "null"}
        };
        addPrimitiveVariations(noCastNeededTypes, castTypes, "array!");

        noCastNeededTypes = new String[][]{
                {"Exception", "null", "Exception"},
                {"ErrorException", "null", "Exception"},
        };
        castTypes = new String[][]{
                {"mixed", "null", "Exception"},
                {"Exception!", "null", "Exception"},
                {"ErrorException!", "null", "ErrorException"}
        };
        addClassInterfaceVariations(noCastNeededTypes, castTypes, "Exception");

        noCastNeededTypes = new String[][]{
                {"Exception", "null", "Exception"},
                {"Exception!", "null", "Exception"},
        };
        castTypes = new String[][]{
                {"mixed", "null", "Exception"},
                {"ErrorException", "null", "Exception"},
                //TODO rstoll TSPHP-291 Generics - implement falseable as internal generic class with contravariant
                //{"ErrorException!", "null", "ErrorException"}
        };
        addClassInterfaceVariations(noCastNeededTypes, castTypes, "Exception!");

        noCastNeededTypes = new String[][]{
                {"ErrorException", "null", "ErrorException"},
        };
        castTypes = new String[][]{
                {"mixed", "null", "ErrorException"},
                {"Exception", "null", "ErrorException"},
                //TODO rstoll TSPHP-291 Generics - implement falseable as internal generic class with contravariant
                //{"Exception!", "null", "ErrorException"},
                {"ErrorException!", "null", "ErrorException"}
        };
        addClassInterfaceVariations(noCastNeededTypes, castTypes, "ErrorException");

        noCastNeededTypes = new String[][]{
                {"ErrorException", "null", "ErrorException"},
                {"ErrorException!", "null", "ErrorException"}
        };
        castTypes = new String[][]{
                {"mixed", "null", "ErrorException"},
                //TODO rstoll TSPHP-291 Generics - implement falseable as internal generic class with contravariant
                //{"Exception", "null", "ErrorException"},
                //{"Exception!", "null", "Exception"}
        };
        addClassInterfaceVariations(noCastNeededTypes, castTypes, "ErrorException!");
    }

    private static void addClassInterfaceVariations(String[][] noCastTypes, String[][] castTypes,
            String typeName) {

        String assign = isDeclaration ? " = " : "; $a = ";
        String outputAssign = isDeclaration ? " = " : ";\n    $a = ";
        String castAssign = isDeclaration ? " =() " : "; $a =() ";

        for (String type2[] : noCastTypes) {
            String test = "($b instanceof " + type2[2] + " ? $b : "
                    + "\\trigger_error('Cast failed, the evaluation type of $b must be " + type2[2]
                    + ".', \\E_RECOVERABLE_ERROR))";
            String cast = "($b !== null ? ($b !== false ? " + test + " : false) : null)";

            collection.add(new Object[]{
                    type2[0] + " $b=" + type2[1] + "; cast " + typeName + " $a " + assign + " $b;",
                    "<?php\nnamespace{\n    $b = " + type2[1] + ";\n    $a" + outputAssign + "$b;\n}\n?>"
            });
            collection.add(new Object[]{
                    type2[0] + " $b=" + type2[1] + ";" + typeName + " $a " + castAssign + "$b;",
                    "<?php\nnamespace{\n    $b = " + type2[1] + ";\n    $a" + outputAssign + cast + ";\n}\n?>"
            });
        }

        for (String type2[] : castTypes) {
            String test = "($b instanceof " + type2[2] + " ? $b : "
                    + "\\trigger_error('Cast failed, the evaluation type of $b must be " + type2[2]
                    + ".', \\E_RECOVERABLE_ERROR))";
            String cast = "($b !== null ? ($b !== false ? " + test + " : false) : null)";
            collection.add(new Object[]{
                    type2[0] + " $b=" + type2[1] + ";cast " + typeName + " $a " + assign + " $b;",
                    "<?php\nnamespace{\n    $b = " + type2[1] + ";\n    $a" + outputAssign + cast + ";\n}\n?>"
            });
            collection.add(new Object[]{
                    type2[0] + " $b=" + type2[1] + ";" + typeName + " $a " + castAssign + "$b;",
                    "<?php\nnamespace{\n    $b = " + type2[1] + ";\n    $a" + outputAssign + cast + ";\n}\n?>"
            });
        }
    }

    private static void addPrimitiveVariations(String[][] noCastTypes, String[][] castTypes,
            String typeName) {
        String typeNameWithoutNullable = getTypeNameWithoutModifier(typeName);

        String test = "(" + typeNameWithoutNullable + ") $b";
        String cast = "($b !== null ? ($b !== false ? " + test + " : false) : null)";

        String assign = isDeclaration ? " = " : "; $a = ";
        String outputAssign = isDeclaration ? " = " : ";\n    $a = ";
        String castAssign = isDeclaration ? " =() " : "; $a =() ";

        for (String type2[] : noCastTypes) {
            collection.add(new Object[]{
                    type2[0] + " $b=" + type2[1] + "; cast " + typeName + " $a " + assign + " $b;",
                    "<?php\nnamespace{\n    $b = " + type2[1] + ";\n    $a" + outputAssign + "$b;\n}\n?>"
            });
            collection.add(new Object[]{
                    type2[0] + " $b=" + type2[1] + ";" + typeName + " $a " + castAssign + "$b;",
                    "<?php\nnamespace{\n    $b = " + type2[1] + ";\n    $a" + outputAssign + cast + ";\n}\n?>"
            });
        }

        for (String type2[] : castTypes) {
            collection.add(new Object[]{
                    type2[0] + " $b=" + type2[1] + ";cast " + typeName + " $a " + assign + " $b;",
                    "<?php\nnamespace{\n    $b = " + type2[1] + ";\n    $a" + outputAssign + cast + ";\n}\n?>"
            });
            collection.add(new Object[]{
                    type2[0] + " $b=" + type2[1] + ";" + typeName + " $a " + castAssign + "$b;",
                    "<?php\nnamespace{\n    $b = " + type2[1] + ";\n    $a" + outputAssign + cast + ";\n}\n?>"
            });
        }
    }

    private static String getTypeNameWithoutModifier(String typeName) {
        String typeNameWithoutNullable = typeName;
        if (typeNameWithoutNullable.endsWith("?")) {
            typeNameWithoutNullable = typeNameWithoutNullable.substring(0, typeNameWithoutNullable.length() - 1);
        }
        if (typeNameWithoutNullable.endsWith("!")) {
            typeNameWithoutNullable = typeNameWithoutNullable.substring(0, typeNameWithoutNullable.length() - 1);
        }
        return typeNameWithoutNullable;
    }


}
