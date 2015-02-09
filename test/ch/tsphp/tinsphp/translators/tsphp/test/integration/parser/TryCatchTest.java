/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file TryCatchTest from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */
package ch.tsphp.tinsphp.translators.tsphp.test.integration.parser;

import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATranslatorParserTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TryCatchTest extends ATranslatorParserTest
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
                        "try{$a=1;}catch(\\Exception \n$e){}",
                        "try {\n    $a = 1;\n} catch (\\Exception $e2_0) {\n    $e = $e2_0;\n}"
                },
                {
                        "try{$a=1;}catch(\\Exception \n$e){} catch(\\a\\MyException \n$e){$a=1;$b=2;}",
                        "try {\n    $a = 1;\n} catch (\\Exception $e2_0) {\n    $e = $e2_0;\n} "
                                + "catch (\\a\\MyException $e3_0) {\n    $e = $e3_0;\n    $a = 1;\n    $b = 2;\n}"
                },
                {
                        "try{$a=1;}catch(a \n$e){} catch(b \n $e){$a=1;$b=2;}catch(c \n  $e){}",
                        "try {\n    $a = 1;\n} "
                                + "catch (a $e2_0) {\n    $e = $e2_0;\n} "
                                + "catch (b $e3_1) {\n    $e = $e3_1;\n    $a = 1;\n    $b = 2;\n} "
                                + "catch (c $e4_2) {\n    $e = $e4_2;\n}"
                }
        });
    }
}
