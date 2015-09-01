/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration;

import ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils.ATest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FunctionCallTest.class,
        FunctionDefinitionImplicitReturnTest.class,
        FunctionDefinitionTest.class,
        GlobalFunctionTest.class,
        MigrationFunctionTest.class,
        ch.tsphp.tinsphp.translators.tsphp.test.integration.with_widening.FunctionDefinitionImplicitReturnTest.class,
        ch.tsphp.tinsphp.translators.tsphp.test.integration.with_widening.FunctionDefinitionNeedWideningTest.class,
        ch.tsphp.tinsphp.translators.tsphp.test.integration.with_widening.FunctionDefinitionTest.class,
        ch.tsphp.tinsphp.translators.tsphp.test.integration.with_widening.MigrationFunctionTest.class
})
public class ParallelTest
{
    @BeforeClass
    public static void init() {
        ATest.numberOfThreads = 4;
    }

    @AfterClass
    public static void tearDown() {
        ATest.numberOfThreads = 1;
    }
}
