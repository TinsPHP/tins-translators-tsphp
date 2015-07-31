/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.test.integration.testutils;

import ch.tsphp.common.AstHelper;
import ch.tsphp.common.TSPHPAstAdaptor;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.config.ICoreInitialiser;
import ch.tsphp.tinsphp.common.config.ISymbolsInitialiser;
import ch.tsphp.tinsphp.common.symbols.ISymbolFactory;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.core.config.HardCodedCoreInitialiser;
import ch.tsphp.tinsphp.symbols.config.HardCodedSymbolsInitialiser;
import ch.tsphp.tinsphp.translators.tsphp.INameTransformer;
import ch.tsphp.tinsphp.translators.tsphp.IOperatorHelper;
import ch.tsphp.tinsphp.translators.tsphp.IParameterCheckProvider;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPNameTransformer;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPOperatorHelper;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPParameterCheckProvider;
import ch.tsphp.tinsphp.translators.tsphp.issues.HardCodedOutputIssueMessageProvider;
import org.junit.Ignore;

import java.util.Map;

@Ignore
public abstract class ATranslatorWithReductionTest extends ATranslatorTest
{
    private final ITypeHelper typeHelper;
    private final Map<String, ITypeSymbol> primitiveTypes;
    private final ITypeSymbol tsphpBoolTypeSymbol;
    private final ITypeSymbol tsphpNumTypeSymbol;
    private final ITypeSymbol tsphpScalarTypeSymbol;


    public ATranslatorWithReductionTest(String theTestString, String theExpectedResult) {
        super(theTestString, theExpectedResult);
        ISymbolsInitialiser symbolsInitialiser = new HardCodedSymbolsInitialiser();
        ICoreInitialiser coreInitialiser = new HardCodedCoreInitialiser(
                new AstHelper(new TSPHPAstAdaptor()), symbolsInitialiser);
        typeHelper = symbolsInitialiser.getTypeHelper();
        primitiveTypes = coreInitialiser.getCore().getPrimitiveTypes();
        ISymbolFactory symbolFactory = symbolsInitialiser.getSymbolFactory();
        ITypeSymbol mixedTypeSymbol = symbolFactory.getMixedTypeSymbol();
        tsphpBoolTypeSymbol = symbolFactory.createPseudoTypeSymbol("bool", mixedTypeSymbol);
        tsphpNumTypeSymbol = symbolFactory.createPseudoTypeSymbol("num", mixedTypeSymbol);
        tsphpScalarTypeSymbol = symbolFactory.createPseudoTypeSymbol("scalar", mixedTypeSymbol);
    }

    @Override
    public IOperatorHelper createOperatorHelper(INameTransformer nameTransformer) {
        return new TSPHPOperatorHelper(typeHelper, primitiveTypes, nameTransformer);
    }

    @Override
    public INameTransformer createNameTransformer() {
        return new TSPHPNameTransformer(
                typeHelper, primitiveTypes, tsphpBoolTypeSymbol, tsphpNumTypeSymbol, tsphpScalarTypeSymbol);
    }

    @Override
    public IParameterCheckProvider createParameterCheckProvider() {
        return new TSPHPParameterCheckProvider(new HardCodedOutputIssueMessageProvider(), tsphpBoolTypeSymbol);
    }
}
