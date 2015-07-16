/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.antlrmod;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.translation.ITranslatorController;

public class FunctionErrorMessageCaller implements IErrorMessageCaller
{
    private ITranslatorController controller;

    public FunctionErrorMessageCaller(ITranslatorController theController) {
        controller = theController;
    }

    @Override
    public String getErrMessage(IBindingCollection currentBindings, ITSPHPAst rootNode, ITSPHPAst identifier) {
        return controller.getErrMessageFunctionApplication(currentBindings, rootNode, identifier);
    }
}
