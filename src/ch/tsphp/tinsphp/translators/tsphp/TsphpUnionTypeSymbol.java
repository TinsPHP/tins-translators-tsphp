/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp;

import ch.tsphp.common.IScope;
import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.common.symbols.modifiers.IModifierSet;
import ch.tsphp.tinsphp.common.symbols.IObservableTypeListener;
import ch.tsphp.tinsphp.common.symbols.IObservableTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IParametricTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IUnionTypeSymbol;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class TsphpUnionTypeSymbol implements IUnionTypeSymbol
{

    private final IUnionTypeSymbol unionTypeSymbol;
    private String absoluteName;

    public TsphpUnionTypeSymbol(String theAbsoluteName, IUnionTypeSymbol theUnionTypeSymbol) {
        absoluteName = theAbsoluteName;
        unionTypeSymbol = theUnionTypeSymbol;
    }

    @Override
    public Map<String, ITypeSymbol> getTypeSymbols() {
        return unionTypeSymbol.getTypeSymbols();
    }

    @Override
    public boolean addTypeSymbol(ITypeSymbol typeSymbol) {
        throw new UnsupportedOperationException("You are dealing with a TsphpUnionTypeSymbol");
    }

    @Override
    public void remove(String absoluteName) {
        throw new UnsupportedOperationException("You are dealing with a TsphpUnionTypeSymbol");
    }

    @Override
    public IUnionTypeSymbol copy(Collection<IParametricTypeSymbol> parametricTypeSymbols) {
        return unionTypeSymbol.copy(parametricTypeSymbols);
    }

    @Override
    public boolean isFixed() {
        return unionTypeSymbol.isFixed();
    }

    @Override
    public void registerObservableListener(IObservableTypeListener observableTypeListener) {
        unionTypeSymbol.registerObservableListener(observableTypeListener);
    }

    @Override
    public void removeObservableListener(IObservableTypeListener observableTypeListener) {
        unionTypeSymbol.removeObservableListener(observableTypeListener);
    }

    @Override
    public Set<ITypeSymbol> getParentTypeSymbols() {
        return unionTypeSymbol.getParentTypeSymbols();
    }

    @Override
    public ITSPHPAst getDefaultValue() {
        return unionTypeSymbol.getDefaultValue();
    }

    @Override
    public boolean canBeUsedInIntersection() {
        return unionTypeSymbol.canBeUsedInIntersection();
    }

    @Override
    public boolean isFinal() {
        return unionTypeSymbol.isFinal();
    }

    @Override
    public boolean isFalseable() {
        return unionTypeSymbol.isFalseable();
    }

    @Override
    public boolean isNullable() {
        return unionTypeSymbol.isNullable();
    }

    @Override
    public void nameOfObservableHasChanged(IObservableTypeSymbol type, String oldAbsoluteName) {
        unionTypeSymbol.nameOfObservableHasChanged(type, oldAbsoluteName);
    }

    @Override
    public void observableWasFixed(IObservableTypeSymbol type) {
        unionTypeSymbol.observableWasFixed(type);
    }

    @Override
    public void addModifier(Integer integer) {
        unionTypeSymbol.addModifier(integer);
    }

    @Override
    public boolean removeModifier(Integer integer) {
        return unionTypeSymbol.removeModifier(integer);
    }

    @Override
    public IModifierSet getModifiers() {
        return unionTypeSymbol.getModifiers();
    }

    @Override
    public void setModifiers(IModifierSet modifierSet) {
        unionTypeSymbol.setModifiers(modifierSet);
    }

    @Override
    public ITSPHPAst getDefinitionAst() {
        return unionTypeSymbol.getDefinitionAst();
    }

    @Override
    public String getName() {
        return absoluteName;
    }

    @Override
    public String getAbsoluteName() {
        return absoluteName;
    }

    @Override
    public IScope getDefinitionScope() {
        return unionTypeSymbol.getDefinitionScope();
    }

    @Override
    public void setDefinitionScope(IScope iScope) {
        unionTypeSymbol.setDefinitionScope(iScope);
    }

    @Override
    public ITypeSymbol getType() {
        return unionTypeSymbol.getType();
    }

    @Override
    public void setType(ITypeSymbol typeSymbol) {
        unionTypeSymbol.setType(typeSymbol);
    }
}
