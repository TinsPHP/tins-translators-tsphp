/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file ParameterListHelper from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ParameterListHelper
{

    private static List<Object[]> collection;

    private ParameterListHelper() {
    }

    public static Collection<Object[]> getTestStrings(String prefix, String appendix,
            String prefixExpected, String appendixExpected) {

        collection = new ArrayList<>();
        prefix += "(";
        prefixExpected += "(";
        appendix = ")" + appendix;
        appendixExpected = ")" + appendixExpected;
        String[] types = TypeHelper.getScalarTypes();
        for (String type : types) {
            collection.add(new Object[]{
                    prefix + type + " $a" + appendix,
                    prefixExpected + "$a" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + type + " $a," + type + " $b" + appendix,
                    prefixExpected + "$a, $b" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + "cast " + type + " $a" + appendix,
                    prefixExpected + "$a" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + "cast " + type + " $a, cast " + type + " $b" + appendix,
                    prefixExpected + "$a, $b" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + type + "? $a" + appendix,
                    prefixExpected + "$a" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + type + "? $a, " + type + "? $b" + appendix,
                    prefixExpected + "$a, $b" + appendixExpected
            });

            collection.add(new Object[]{
                    prefix + "cast " + type + "? $a" + appendix,
                    prefixExpected + "$a" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + "cast " + type + "? $a, cast " + type + "? $b" + appendix,
                    prefixExpected + "$a, $b" + appendixExpected
            });
        }
        types = TypeHelper.getClassInterfaceTypes();

        //class / interfaceType can also have the ? modifier
        for (String type : types) {
            collection.add(new Object[]{
                    prefix + type + " $a" + appendix,
                    prefixExpected + type + " $a" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + type + " $a, " + type + " $b" + appendix,
                    prefixExpected + type + " $a, " + type + " $b" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + "cast " + type + " $a" + appendix,
                    prefixExpected + type + " $a" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + "cast " + type + " $a, cast " + type + " $b" + appendix,
                    prefixExpected + type + " $a, " + type + " $b" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + type + "? $a" + appendix,
                    prefixExpected + type + " $a = null" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + type + "? $a, " + type + "? $b" + appendix,
                    prefixExpected + type + " $a = null, " + type + " $b = null" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + "cast " + type + "? $a" + appendix,
                    prefixExpected + type + " $a = null" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + "cast " + type + "? $a, cast " + type + "? $b" + appendix,
                    prefixExpected + type + " $a = null, " + type + " $b = null" + appendixExpected
            });
        }
        collection.add(new Object[]{
                prefix + "array $a" + appendix,
                prefixExpected + "array $a" + appendixExpected
        });
        collection.add(new Object[]{
                prefix + "cast array $a" + appendix,
                prefixExpected + "array $a" + appendixExpected
        });
        collection.add(new Object[]{
                prefix + "array? $a" + appendix,
                prefixExpected + "array $a = null" + appendixExpected
        });
        collection.add(new Object[]{
                prefix + "cast array $a" + appendix,
                prefixExpected + "array $a" + appendixExpected
        });

        collection.add(new Object[]{
                prefix + "resource $a" + appendix,
                prefixExpected + "$a" + appendixExpected
        });
        collection.add(new Object[]{
                prefix + "resource? $a" + appendix,
                prefixExpected + "$a" + appendixExpected
        });
        collection.add(new Object[]{
                prefix + "mixed $a" + appendix,
                prefixExpected + "$a" + appendixExpected
        });
        collection.add(new Object[]{
                prefix + "mixed? $a" + appendix,
                prefixExpected + "$a" + appendixExpected
        });

        //normal
        addVariations(
                prefix, "int", appendix,
                prefixExpected, "", "", appendixExpected);
        //cast
        addVariations(
                prefix, "cast array", appendix,
                prefixExpected, "array ", "", appendixExpected);
        //?
        addVariations(
                prefix, "\\Exception?", appendix,
                prefixExpected, "\\Exception ", " = null", appendixExpected);
        //cast and ? mixed
        addVariations(
                prefix, "cast int?", appendix,
                prefixExpected, "", "", appendixExpected);

        addVariationsForOptional(prefix, appendix, prefixExpected, appendixExpected);

        //empty params
        collection.add(new Object[]{prefix + appendix, prefixExpected + appendixExpected});
        return collection;
    }

    private static void addVariations(String prefix, String type, String appendix,
            String prefixExpected, String typeExpectedPrefix, String typeExpectedAppendix, String appendixExpected) {


        String paramStat1 = "int $x";
        String paramStat2 = "array $y";
        String paramStat1Expected = "$x";
        String paramStat2Expected = "array $y";

        collection.addAll(Arrays.asList(new Object[][]{
                {
                        prefix + type + " $a" + appendix,
                        prefixExpected + typeExpectedPrefix + "$a" + typeExpectedAppendix + appendixExpected
                },
                {
                        prefix + type + " $a" + "," + paramStat1 + appendix,
                        prefixExpected
                                + typeExpectedPrefix + "$a" + typeExpectedAppendix + ", "
                                + paramStat1Expected + appendixExpected
                },
                {
                        prefix + paramStat1 + "," + type + " $a" + appendix,
                        prefixExpected
                                + paramStat1Expected + ", "
                                + typeExpectedPrefix + "$a" + typeExpectedAppendix + appendixExpected
                },
                {
                        prefix + type + " $a" + ", " + paramStat1 + ", " + paramStat2 + appendix,
                        prefixExpected
                                + typeExpectedPrefix + "$a" + typeExpectedAppendix + ", "
                                + paramStat1Expected + ", "
                                + paramStat2Expected + appendixExpected
                },
                {
                        prefix + type + " $a" + ", " + type + " $b" + ", " + paramStat1 + appendix,
                        prefixExpected
                                + typeExpectedPrefix + "$a" + typeExpectedAppendix + ", "
                                + typeExpectedPrefix + "$b" + typeExpectedAppendix + ", "
                                + paramStat1Expected + appendixExpected
                },
                {
                        prefix + type + " $a" + ", " + paramStat1 + "," + type + " $b" + "" + appendix,
                        prefixExpected
                                + typeExpectedPrefix + "$a" + typeExpectedAppendix + ", "
                                + paramStat1Expected + ", "
                                + typeExpectedPrefix + "$b" + typeExpectedAppendix + appendixExpected
                },
                {
                        prefix + paramStat1 + "," + type + " $a" + ", " + paramStat2 + appendix,
                        prefixExpected
                                + paramStat1Expected + ", "
                                + typeExpectedPrefix + "$a" + typeExpectedAppendix + ", "
                                + paramStat2Expected + appendixExpected
                },
                {
                        prefix + paramStat1 + "," + type + " $a" + ", " + type + " $b" + "" + appendix,
                        prefixExpected
                                + paramStat1Expected + ", "
                                + typeExpectedPrefix + "$a" + typeExpectedAppendix + ", "
                                + typeExpectedPrefix + "$b" + typeExpectedAppendix + appendixExpected
                },
                {
                        prefix + type + " $a, " + type + " $b , " + type + " $c" + appendix,
                        prefixExpected
                                + typeExpectedPrefix + "$a" + typeExpectedAppendix + ", "
                                + typeExpectedPrefix + "$b" + typeExpectedAppendix + ", "
                                + typeExpectedPrefix + "$c" + typeExpectedAppendix + appendixExpected
                }
        }));
    }

    private static void addVariationsForOptional(String prefix, String appendix,
            String prefixExpect, String appendixExpect) {
        collection.addAll(Arrays.asList(new Object[][]{
                //optional parameter
                {
                        prefix + "int $i = 10" + appendix,
                        prefixExpect + "$i = 10" + appendixExpect
                },
                {
                        prefix + "int $a, int $b='hallo'" + appendix,
                        prefixExpect + "$a, $b = 'hallo'" + appendixExpect
                },
                {
                        prefix + "int $a, int? $i, int $b=+1" + appendix,
                        prefixExpect + "$a, $i, $b = +1" + appendixExpect
                },
                {
                        prefix + "int $a,cast int? $i = null, int $b=-10, int $d=2.0" + appendix,
                        prefixExpect + "$a, $i = null, $b = -10, $d = 2.0" + appendixExpect
                },
                {
                        prefix + "int? $a=null,int? $b=true, int $c=E_ALL" + appendix,
                        prefixExpect + "$a = null, $b = true, $c = E_ALL" + appendixExpect
                },
                {
                        prefix + "int $a, int $b=false, int $d=null" + appendix,
                        prefixExpect + "$a, $b = false, $d = null" + appendixExpect
                },
                {
                        prefix + "int $a, int $b, int $d=true" + appendix,
                        prefixExpect + "$a, $b, $d = true" + appendixExpect
                },
                {
                        prefix + "cast int $a=1, int? $b=2, cast int $d=3" + appendix,
                        prefixExpect + "$a = 1, $b = 2, $d = 3" + appendixExpect
                }
        }));


        String[] types = TypeHelper.getClassInterfaceTypes();

        for (String type : types) {
            collection.add(new Object[]{
                    prefix + "int $a=" + type + "::a" + appendix,
                    prefixExpect + "$a = " + type + "::a" + appendixExpect
            });
        }
    }
}