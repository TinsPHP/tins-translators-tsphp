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
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.PrimitiveTypeNames;
import ch.tsphp.tinsphp.common.utils.ITypeHelper;
import ch.tsphp.tinsphp.core.config.HardCodedCoreInitialiser;
import ch.tsphp.tinsphp.symbols.config.HardCodedSymbolsInitialiser;
import ch.tsphp.tinsphp.translators.tsphp.IOperatorHelper;
import ch.tsphp.tinsphp.translators.tsphp.IRuntimeCheckProvider;
import ch.tsphp.tinsphp.translators.tsphp.ITypeTransformer;
import ch.tsphp.tinsphp.translators.tsphp.ITypeVariableTransformer;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPOperatorHelper;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPRuntimeCheckProvider;
import ch.tsphp.tinsphp.translators.tsphp.TSPHPTypeTransformer;
import ch.tsphp.tinsphp.translators.tsphp.TempVariableHelper;
import ch.tsphp.tinsphp.translators.tsphp.TsphpTypeVariableTransformer;
import ch.tsphp.tinsphp.translators.tsphp.TsphpUnionTypeSymbol;
import ch.tsphp.tinsphp.translators.tsphp.issues.HardCodedOutputIssueMessageProvider;
import org.junit.Ignore;

import java.util.Map;

@Ignore
public abstract class ATranslatorWithWideningTest extends ATranslatorTest
{
    private final ITypeHelper typeHelper;
    private final Map<String, ITypeSymbol> primitiveTypes;
    private final ITypeSymbol tsphpBoolTypeSymbol;
    private final ITypeSymbol tsphpNumTypeSymbol;
    private final ITypeSymbol tsphpScalarTypeSymbol;


    public ATranslatorWithWideningTest(String theTestString, String theExpectedResult) {
        super(theTestString, theExpectedResult);
        ISymbolsInitialiser symbolsInitialiser = new HardCodedSymbolsInitialiser();
        ICoreInitialiser coreInitialiser = new HardCodedCoreInitialiser(
                new AstHelper(new TSPHPAstAdaptor()), symbolsInitialiser);
        typeHelper = symbolsInitialiser.getTypeHelper();
        primitiveTypes = coreInitialiser.getCore().getPrimitiveTypes();
        IUnionTypeSymbol unionTypeSymbol = (IUnionTypeSymbol) primitiveTypes.get(PrimitiveTypeNames.BOOL);
        tsphpBoolTypeSymbol = new TsphpUnionTypeSymbol("bool", unionTypeSymbol);
        unionTypeSymbol = (IUnionTypeSymbol) primitiveTypes.get(PrimitiveTypeNames.NUM);
        tsphpNumTypeSymbol = new TsphpUnionTypeSymbol("num", unionTypeSymbol);
        unionTypeSymbol = (IUnionTypeSymbol) primitiveTypes.get(PrimitiveTypeNames.SCALAR);
        tsphpScalarTypeSymbol = new TsphpUnionTypeSymbol("scalar", unionTypeSymbol);
    }

    @Override
    public IOperatorHelper createOperatorHelper(
            IRuntimeCheckProvider runtimeCheckProvider, ITypeTransformer typeTransformer) {
        return new TSPHPOperatorHelper(typeHelper, primitiveTypes, runtimeCheckProvider, typeTransformer);
    }

    @Override
    public ITypeTransformer createTypeTransformer() {
        return new TSPHPTypeTransformer(
                symbolsInitialiser.getSymbolFactory(),
                typeHelper,
                primitiveTypes,
                tsphpBoolTypeSymbol,
                tsphpNumTypeSymbol,
                tsphpScalarTypeSymbol);
    }

    @Override
    public IRuntimeCheckProvider createRuntimeCheckProvider(ITypeTransformer typeTransformer, TempVariableHelper
            tempVariableHelper) {
        return new TSPHPRuntimeCheckProvider(
                typeTransformer, tempVariableHelper, new HardCodedOutputIssueMessageProvider(), tsphpBoolTypeSymbol);
    }

    @Override
    protected ITypeVariableTransformer createTypeVariableMapper(ITypeTransformer typeTransformer) {
        return new TsphpTypeVariableTransformer(
                symbolsInitialiser.getSymbolFactory(), symbolsInitialiser.getTypeHelper(), typeTransformer);
    }
}
