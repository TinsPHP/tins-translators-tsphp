/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file WriteExceptionToConsole from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils;

import ch.tsphp.common.IErrorLogger;
import ch.tsphp.common.exceptions.TSPHPException;

public class WriteExceptionToConsole implements IErrorLogger
{
    @Override
    public void log(TSPHPException exception) {
        System.out.println(exception.getMessage());
    }
}
