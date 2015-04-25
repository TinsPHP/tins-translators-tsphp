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

import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorInferenceTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TryCatchTest extends ATranslatorInferenceTest
{

    public TryCatchTest(String testString, String expectedResult) {
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
                        "<?php try{$a=1;}catch(\\Exception \n$e){}",
                        "namespace{"
                                + "\n    Exception $e;"
                                + "\n    int $a;"
                                + "\n    try {"
                                + "\n        $a = 1;"
                                + "\n    } catch (\\Exception $e2_0) {"
                                + "\n        $e = $e2_0;"
                                + "\n    }"
                                + "\n}"
                },
                {
                        "<?php namespace a; try{$a=1;}catch(\\ErrorException \n$e){} catch(\\Exception \n$e){$a=1;" +
                                "$b=2;}",
                        "namespace a{"
                                + "\n    int $b;"
                                + "\n    Exception $e;"
                                + "\n    int $a;"
                                + "\n    try {"
                                + "\n        $a = 1;"
                                + "\n    } catch (\\ErrorException $e2_0) {"
                                + "\n        $e = $e2_0;"
                                + "\n    } catch (\\Exception $e3_0) {"
                                + "\n        $e = $e3_0;"
                                + "\n        $a = 1;"
                                + "\n        $b = 2;"
                                + "\n    }"
                                + "\n}"
                },
                {
                        "<?php try{$a=1;}"
                                + "catch(Exception \n$e){}"
                                + "catch(Exception \n $e){$a=1;$b=2;}"
                                + "catch(Exception \n  $e){}",
                        "namespace{"
                                + "\n    int $b;"
                                + "\n    Exception $e;"
                                + "\n    int $a;"
                                + "\n    try {"
                                + "\n        $a = 1;"
                                + "\n    } catch (Exception $e2_0) {"
                                + "\n        $e = $e2_0;"
                                + "\n    } catch (Exception $e3_1) {"
                                + "\n        $e = $e3_1;"
                                + "\n        $a = 1;"
                                + "\n        $b = 2;"
                                + "\n    } catch (Exception $e4_2) {"
                                + "\n        $e = $e4_2;"
                                + "\n    }"
                                + "\n}"
                },
                //also see TINS-248
                //TODO rstoll TINS-395 add new operator
//                {
//                        "<?php $e = new Exception(); try{}catch(Exception \n$e){} throw $e;",
//                        "namespace{"
//                                + "\n    Exception $e;"
//                                + "\n    $e = new Exception();"
//                                + "\n    try {"
//                                + "\n    } catch (Exception $e2_0) {"
//                                + "\n        $e = $e2_0;"
//                                + "\n    }"
//                                + "\n    throw $e;"
//                                + "\n}"
//                }
        });
    }
}
