/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file TempVariableHelper from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.IScope;
import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.ITSPHPAstAdaptor;
import ch.tsphp.common.symbols.ISymbol;
import ch.tsphp.tinsphp.common.translation.ITempVariableHelper;
import ch.tsphp.tinsphp.symbols.gen.TokenTypes;

/**
 * A temp variable helper which uses the format "$_t + line + char position".
 */
public class TempVariableHelper implements ITempVariableHelper
{

    private final ITSPHPAstAdaptor astAdaptor;

    public TempVariableHelper(ITSPHPAstAdaptor theAstAdaptor) {
        astAdaptor = theAstAdaptor;
    }

    @Override
    public String getTempVariableName(ITSPHPAst expression) {
        String tokenText = "$_t";
        if (expression.getToken().getType() == TokenTypes.VariableId) {
            tokenText = expression.getText();
        }
        tokenText += expression.getLine() + "_" + expression.getCharPositionInLine();
        ITSPHPAst tmpVariable = astAdaptor.create(TokenTypes.VariableId, tokenText);
        IScope scope = expression.getScope();
        ISymbol symbol = scope != null ? scope.resolve(tmpVariable) : null;
        int count = 0;
        while (symbol != null) {
            tmpVariable.setText(tokenText + "_" + count);
            symbol = scope.resolve(tmpVariable);
            ++count;
        }
        return tmpVariable.getText();
    }

    @Override
    public String getTempVariableNameIfNotVariable(ITSPHPAst expression) {
        if (expression.getToken().getType() == TokenTypes.VariableId) {
            return expression.getText();
        }
        return getTempVariableName(expression);
    }
}
