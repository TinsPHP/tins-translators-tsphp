/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

/*
 * This file is based on the file PHP54PrecedenceHelper from the translator component of the TSPHP project.
 * TSPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.tinsphp.common.translation.IPrecedenceHelper;
import ch.tsphp.tinsphp.symbols.gen.TokenTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * A precedence helper which orients itself by PHP 5.4.
 */
public class PrecedenceHelper implements IPrecedenceHelper
{

    @SuppressWarnings("checkstyle:magicnumber")
    private Map<Integer, Integer> precedenceRules = new HashMap<>(50);

    public PrecedenceHelper() {
        initPrecedenceRules();
    }

    @Override
    public boolean needParentheses(ITSPHPAst operatorAst) {
        int operatorType = operatorAst.getType();
        int parentOperatorType = operatorAst.getParent().getType();
        return precedenceRules.containsKey(parentOperatorType)
                && precedenceRules.get(parentOperatorType) > precedenceRules.get(operatorType);
    }

    @SuppressWarnings("checkstyle:magicnumber")
    private void initPrecedenceRules() {
        precedenceRules.put(TokenTypes.LogicOrWeak, 10);
        precedenceRules.put(TokenTypes.LogicXorWeak, 11);
        precedenceRules.put(TokenTypes.LogicAndWeak, 12);

        precedenceRules.put(TokenTypes.Assign, 20);
        precedenceRules.put(TokenTypes.PlusAssign, 20);
        precedenceRules.put(TokenTypes.MinusAssign, 20);
        precedenceRules.put(TokenTypes.DotAssign, 20);
        precedenceRules.put(TokenTypes.MultiplyAssign, 20);
        precedenceRules.put(TokenTypes.DivideAssign, 20);
        precedenceRules.put(TokenTypes.BitwiseAndAssign, 20);
        precedenceRules.put(TokenTypes.BitwiseOrAssign, 20);
        precedenceRules.put(TokenTypes.BitwiseXorAssign, 20);
        precedenceRules.put(TokenTypes.ModuloAssign, 20);
        precedenceRules.put(TokenTypes.ShiftLeftAssign, 20);
        precedenceRules.put(TokenTypes.ShiftRightAssign, 20);

        precedenceRules.put(TokenTypes.QuestionMark, 30);

        precedenceRules.put(TokenTypes.LogicOr, 40);
        precedenceRules.put(TokenTypes.LogicAnd, 41);

        precedenceRules.put(TokenTypes.BitwiseOr, 50);
        precedenceRules.put(TokenTypes.BitwiseXor, 51);
        precedenceRules.put(TokenTypes.BitwiseAnd, 52);

        precedenceRules.put(TokenTypes.Equal, 60);
        precedenceRules.put(TokenTypes.NotEqual, 60);
        precedenceRules.put(TokenTypes.Identical, 60);
        precedenceRules.put(TokenTypes.NotIdentical, 60);


        precedenceRules.put(TokenTypes.LessThan, 70);
        precedenceRules.put(TokenTypes.LessEqualThan, 70);
        precedenceRules.put(TokenTypes.GreaterThan, 70);
        precedenceRules.put(TokenTypes.GreaterEqualThan, 70);

        precedenceRules.put(TokenTypes.ShiftLeft, 80);
        precedenceRules.put(TokenTypes.ShiftRight, 80);

        precedenceRules.put(TokenTypes.Plus, 90);
        precedenceRules.put(TokenTypes.Minus, 90);
        precedenceRules.put(TokenTypes.Dot, 90);

        precedenceRules.put(TokenTypes.Multiply, 100);
        precedenceRules.put(TokenTypes.Divide, 100);
        precedenceRules.put(TokenTypes.Modulo, 100);

        precedenceRules.put(TokenTypes.Instanceof, 110);

        precedenceRules.put(TokenTypes.CAST, 120);
        precedenceRules.put(TokenTypes.PRE_INCREMENT, 120);
        precedenceRules.put(TokenTypes.PRE_DECREMENT, 120);
        precedenceRules.put(TokenTypes.At, 120);
        precedenceRules.put(TokenTypes.BitwiseNot, 120);
        precedenceRules.put(TokenTypes.LogicNot, 120);
        precedenceRules.put(TokenTypes.UNARY_MINUS, 120);
        precedenceRules.put(TokenTypes.UNARY_PLUS, 120);


        precedenceRules.put(TokenTypes.New, 130);
        precedenceRules.put(TokenTypes.Clone, 130);

        precedenceRules.put(TokenTypes.POST_INCREMENT, 150);
        precedenceRules.put(TokenTypes.POST_DECREMENT, 150);
    }
}
