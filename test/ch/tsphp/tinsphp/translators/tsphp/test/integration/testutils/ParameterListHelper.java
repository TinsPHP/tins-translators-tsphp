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

        String[] types = TypeHelper.getClassInterfaceTypes();
        for (String type : types) {
            collection.add(new Object[]{
                    prefix + type + " $a" + appendix,
                    prefixExpected + type + " $a" + appendixExpected
            });
            collection.add(new Object[]{
                    prefix + type + " $a, " + type + " $b" + appendix,
                    prefixExpected + type + " $a, " + type + " $b" + appendixExpected
            });
        }
        collection.add(new Object[]{
                prefix + "array $a" + appendix,
                prefixExpected + "array $a" + appendixExpected
        });
        collection.add(new Object[]{
                prefix + "array $a, $b, \\Exception $e" + appendix,
                prefixExpected + "array $a, ? $b, \\Exception $e" + appendixExpected
        });

        //normal
        addVariations(
                prefix, "\\Exception", appendix,
                prefixExpected, "\\Exception ", "", appendixExpected);

        addVariationsForOptional(prefix, appendix, prefixExpected, appendixExpected);

        //empty params
        collection.add(new Object[]{prefix + appendix, prefixExpected + appendixExpected});
        return collection;
    }

    private static void addVariations(String prefix, String type, String appendix,
            String prefixExpected, String typeExpectedPrefix, String typeExpectedAppendix, String appendixExpected) {


        String paramStat1 = "$x";
        String paramStat2 = "array $y";
        String paramStat1Expected = "? $x";
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
                        prefix + "$i = 10" + appendix,
                        prefixExpect + "? $i = 10" + appendixExpect
                },
                {
                        prefix + "$a, $b='hallo'" + appendix,
                        prefixExpect + "? $a, ? $b = 'hallo'" + appendixExpect
                },
                {
                        prefix + "$a, $i, $b=+1" + appendix,
                        prefixExpect + "? $a, ? $i, ? $b = +1" + appendixExpect
                },
                {
                        prefix + "$a=null, $b=true, $c=E_ALL" + appendix,
                        prefixExpect + "? $a = null, ? $b = true, ? $c = E_ALL" + appendixExpect
                },
                {
                        prefix + "$a, $b=false, $d=null" + appendix,
                        prefixExpect + "? $a, ? $b = false, ? $d = null" + appendixExpect
                },
                //pseudo optional parameters are allowed in PHP but not in TSPHP
                {
                        prefix + "$a=true, $b, $d=true" + appendix,
                        prefixExpect + "? $a, ? $b, ? $d = true" + appendixExpect
                },
                {
                        prefix + "$a=1, $b=2, $d" + appendix,
                        prefixExpect + "? $a, ? $b, ? $d" + appendixExpect
                },
                {
                        prefix + "$a, array $i = null, $b, $d=2.0" + appendix,
                        prefixExpect + "? $a, array? $i, ? $b, ? $d = 2.0" + appendixExpect
                },
                {
                        prefix + "$a=null, $b, $d=2.0" + appendix,
                        prefixExpect + "?? $a, ? $b, ? $d = 2.0" + appendixExpect
                },
        }));

        //TODO rstoll TINS-271 - translator OOP - expressions
//        String[] types = TypeHelper.getClassInterfaceTypes();
//        for (String type : types) {
//            collection.add(new Object[]{
//                    prefix + "$a=" + type + "::a" + appendix,
//                    prefixExpect + "? $a = " + type + "::a" + appendixExpect
//            });
//        }
    }
}