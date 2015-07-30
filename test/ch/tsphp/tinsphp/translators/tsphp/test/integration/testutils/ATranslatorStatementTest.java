/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file UseTest from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils;


import org.junit.Ignore;

@Ignore
public abstract class ATranslatorStatementTest extends ATranslatorTest
{

    public ATranslatorStatementTest(String theTestString, String theExpectedResult) {
        super("<?php " + theTestString + " ?>", "namespace{\n    " + theExpectedResult + "\n}");
    }
}
