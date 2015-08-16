/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file ForeachTest from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration;

import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ForeachTest extends ATranslatorTest
{

    public ForeachTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {
                        "<?php foreach([1,2] as \n$k => \n$v)$a=1;",
                        "namespace{"
                                + "\n    int $a;"
                                + "\n    (int | string) $k;"
                                + "\n    mixed $v;"
                                + "\n    foreach ([1, 2] as (int | string) $k2_0 => mixed $v3_0) {"
                                + "\n        $k = $k2_0;"
                                + "\n        $v = $v3_0;"
                                + "\n        $a = 1;"
                                + "\n    }"
                                + "\n}"
                },
                {
                        "<?php foreach([1,2] as \n$v) $a=1;",
                        "namespace{"
                                + "\n    int $a;"
                                + "\n    mixed $v;"
                                + "\n    foreach ([1, 2] as mixed $v2_0) {"
                                + "\n        $v = $v2_0;"
                                + "\n        $a = 1;"
                                + "\n    }"
                                + "\n}"
                },
                {
                        "<?php foreach([1, 2] as \n$k => \n$v){$a=1;}",
                        "namespace{"
                                + "\n    int $a;"
                                + "\n    (int | string) $k;"
                                + "\n    mixed $v;"
                                + "\n    foreach ([1, 2] as (int | string) $k2_0 => mixed $v3_0) {"
                                + "\n        $k = $k2_0;"
                                + "\n        $v = $v3_0;"
                                + "\n        $a = 1;"
                                + "\n    }"
                                + "\n}"
                },
                {
                        "<?php foreach([1, 2] as \n$v) {$a=1;}",
                        "namespace{"
                                + "\n    int $a;"
                                + "\n    mixed $v;"
                                + "\n    foreach ([1, 2] as mixed $v2_0) {"
                                + "\n        $v = $v2_0;"
                                + "\n        $a = 1;"
                                + "\n    }"
                                + "\n}"
                },
                {
                        "<?php foreach([1, 2] as \n$k=> \n$v){$a=1; $b=2;}",
                        "namespace{"
                                + "\n    int $b;"
                                + "\n    int $a;"
                                + "\n    (int | string) $k;"
                                + "\n    mixed $v;"
                                + "\n    foreach ([1, 2] as (int | string) $k2_0 => mixed $v3_0) {"
                                + "\n        $k = $k2_0;"
                                + "\n        $v = $v3_0;"
                                + "\n        $a = 1;"
                                + "\n        $b = 2;"
                                + "\n    }"
                                + "\n}"
                },
                {
                        "<?php foreach([1, 2] as \n$v) {$a=1; $b=3;}",
                        "namespace{"
                                + "\n    int $b;"
                                + "\n    int $a;"
                                + "\n    mixed $v;"
                                + "\n    foreach ([1, 2] as mixed $v2_0) {"
                                + "\n        $v = $v2_0;"
                                + "\n        $a = 1;"
                                + "\n        $b = 3;"
                                + "\n    }"
                                + "\n}"
                },
                //in different namespace
                {
                        "<?php namespace b; foreach([1, 2] as \n$v) {$a=1; $b=3;}",
                        "namespace b{"
                                + "\n    int $b;"
                                + "\n    int $a;"
                                + "\n    mixed $v;"
                                + "\n    foreach ([1, 2] as mixed $v2_0) {"
                                + "\n        $v = $v2_0;"
                                + "\n        $a = 1;"
                                + "\n        $b = 3;"
                                + "\n    }"
                                + "\n}"
                },
                //TODO TINS-602 runtime check for convertible types
                //see TINS-247 translator procedural - foreach header
                {
                        "<?php $v = null;"
                                + "\n foreach([1,2] as \n$k => \n$v){"
                                + "\n }"
                                + "\n if($v != null){"
                                + "\n   echo $v;"
                                + "\n }"
                        ,
                        "namespace{"
                                + "\n    (int | string) $k;"
                                + "\n    mixed $v;"
                                + "\n    $v = null;"
                                + "\n    foreach ([1, 2] as (int | string) $k3_0 => mixed $v4_0) {"
                                + "\n        $k = $k3_0;"
                                + "\n        $v = $v4_0;"
                                + "\n    }"
                                + "\n    if ($v != null) {"
                                + "\n        echo cast<{as string}>($v);"
                                + "\n    }"
                                + "\n}"
                }
        });
    }
}
