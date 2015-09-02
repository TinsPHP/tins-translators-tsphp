/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */
 
/*
 * This file was created based on PHP54TranslatorWalker.g - the tree grammar file of TSPHP's translator component
 * and reuses TSPHP's AST class as well as other classes/files related to the AST generation.
 * TSPHP is also licenced under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TSPHP/License
 */

tree grammar TSPHPTranslatorWalker;

options {
    tokenVocab = TinsPHP;
    ASTLabelType = ITSPHPAst;
    output = template;
}

@header{
/*
 * This file is part of the TinsPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.tinsphp.translators.tsphp.antlr;

import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ISymbol;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.inference.constraints.IBindingCollection;
import ch.tsphp.tinsphp.common.scopes.IGlobalNamespaceScope;
import ch.tsphp.tinsphp.common.symbols.IArrayTypeSymbol;
import ch.tsphp.tinsphp.common.symbols.IContainerTypeSymbol;
import ch.tsphp.tinsphp.common.translation.ITranslatorController;
import ch.tsphp.tinsphp.common.translation.dtos.FunctionApplicationDto;
import ch.tsphp.tinsphp.common.translation.dtos.OverloadDto;
import ch.tsphp.tinsphp.common.translation.dtos.ParameterDto;
import ch.tsphp.tinsphp.common.translation.dtos.TranslationScopeDto;
import ch.tsphp.tinsphp.common.translation.dtos.TypeParameterDto;
import ch.tsphp.tinsphp.common.translation.dtos.VariableDto;
import ch.tsphp.tinsphp.translators.tsphp.antlrmod.IErrorMessageCaller;
import ch.tsphp.tinsphp.translators.tsphp.antlrmod.FunctionErrorMessageCaller;
import ch.tsphp.tinsphp.translators.tsphp.antlrmod.OperatorErrorMessageCaller;

import java.util.SortedMap;
import java.util.ArrayDeque;

}

@members{
private ITranslatorController controller;
private TranslationScopeDto translationScopeDto;
private boolean isFunctionBefore;
private IErrorMessageCaller functionErrorMessageCaller;
private IErrorMessageCaller operatorErrorMessageCaller;

public TSPHPTranslatorWalker(
        TreeNodeStream input, 
        ITranslatorController theController, 
        IGlobalNamespaceScope globalDefaultNamespaceScope) {
    this(input);
    controller = theController;
    List<IBindingCollection> bindingCollections = globalDefaultNamespaceScope.getBindings();
    IBindingCollection currentBindings = null;
    if (bindingCollections != null && !bindingCollections.isEmpty()){
        currentBindings = bindingCollections.get(0);
    }
    translationScopeDto = new TranslationScopeDto(currentBindings, new ArrayDeque<String>());
    functionErrorMessageCaller = new FunctionErrorMessageCaller(controller);
    operatorErrorMessageCaller = new OperatorErrorMessageCaller(controller);
}

private String getMethodName(String name) {
    return name.substring(0, name.length() - 2);
}

private StringTemplate getType(VariableDto dto) {
    StringTemplate type;
    if(dto.type!=null){
        type = new StringTemplate(templateLib, dto.type);
    } else {
        type = %bound(
            bounds={dto.typeParameter.lowerBounds},
            moreThanOne={
                dto.typeParameter.lowerBounds != null && dto.typeParameter.lowerBounds.size() > 1
            },
            sep={" | "}
        );
    }
    return type;  
}

private StringTemplate getFunctionApplication(
        FunctionApplicationDto dto,
        ITSPHPAst functionCall,
        ITSPHPAst identifier,
        IErrorMessageCaller errorMessageCaller){

    StringTemplate stringTemplate;

    if (dto != null) {
        stringTemplate = %functionCall(identifier={dto.name}, typeParams={dto.typeParameters}, arguments={dto.arguments});
        if (dto.returnRuntimeCheck != null) {
            stringTemplate = %out(o={dto.returnRuntimeCheck.replace("\%returnRuntimeCheck\%", stringTemplate.toString())});
        }
    } else {
        stringTemplate = getErrorMessage(functionCall, identifier, errorMessageCaller);
    }
    return stringTemplate;
}

private StringTemplate getOperatorOrFunctionApplication(
        FunctionApplicationDto dto,
        List<String> argumentNames,
        ITSPHPAst operatorAst,
        String operatorFunction,
        StringTemplate operator,
        boolean needParentheses) {

    StringTemplate stringTemplate;
    if (dto != null) {
        if (dto.name != null) {
            stringTemplate = getFunctionApplication(dto, operatorAst, operatorAst, operatorErrorMessageCaller);
        } else {
            stringTemplate = getOperatorApplication(dto, argumentNames, operatorFunction, operator, needParentheses);
            if (dto.returnRuntimeCheck != null) {
                stringTemplate = %out(o={dto.returnRuntimeCheck.replace("\%returnRuntimeCheck\%", stringTemplate.toString())});
            }
        }
    } else {
        stringTemplate = getErrorMessage(operatorAst, operatorAst, operatorErrorMessageCaller);
    }

    return stringTemplate;
}

private StringTemplate getOperatorApplication(
        FunctionApplicationDto dto,
        List<String> argumentNames,
        String operatorFunction,
        StringTemplate operator,
        boolean needParentheses) {

    int numberOfArguments = dto.arguments.size();
    STAttrMap map = new STAttrMap().put("operator", operator).put("needParentheses", needParentheses);
    for(int i = 0; i < numberOfArguments; ++i){
        String argumentName = argumentNames.get(i);
        Object argument = dto.arguments.get(i);
        map.put(argumentName, argument);
    }
    return templateLib.getInstanceOf(operatorFunction, map);
}

private StringTemplate getErrorMessage(
        ITSPHPAst functionCall, ITSPHPAst identifier, IErrorMessageCaller errorMessageCaller) {
        
    StringTemplate stringTemplate;
    String functionName = "\\trigger_error";
    List<String> functionArguments = new ArrayList<>(2);
    String errMessage = errorMessageCaller.getErrMessage(translationScopeDto.bindingCollection, functionCall, identifier);
    functionArguments.add(errMessage);
    functionArguments.add("\\E_USER_ERROR");
    stringTemplate = %functionCall(identifier={functionName}, arguments={functionArguments});
    return stringTemplate;
}

}

compilationUnit
    :   (n+=namespace+)
        {
            List<Object> namespaces = $n;
            if (!translationScopeDto.statements.isEmpty()) {
                StringTemplate body = %body(statements={translationScopeDto.statements});
                namespaces.add(%namespace(body={body}));
            }
            $st = %file(namespaces={namespaces});
        }
    ;
    
namespace
@init{String namespaceName = null;}
    :   ^('namespace' (name=TYPE_NAME|DEFAULT_NAMESPACE) namespaceBody)
        {
            if(name != null){
                namespaceName=name.getText().substring(1,name.getText().length()-1);
            }
        }

        -> namespace(name={namespaceName},body={$namespaceBody.st})
    ;
    
namespaceBody
    :   ^(NAMESPACE_BODY statements+=statement*) -> body(statements={$statements})
    ;

statement
    :   useDeclarationList -> {$useDeclarationList.st}
    |   definition -> {$definition.st}
    |   instruction -> {$instruction.st}
    ;

useDeclarationList
    :   ^('use' declarations+=useDeclaration+)
        -> useDeclarationList(useDeclarations={$declarations})
    ;

useDeclaration
    :   ^(USE_DECLARATION TYPE_NAME Identifier)
        -> useDeclaration(type={$TYPE_NAME}, alias={$Identifier})
    ;

definition
    :   //TODO rstoll TINS-267 translator OOP - classes
        //classDeclaration        -> {$classDeclaration.st}
        //TODO rstoll TINS-268 translator OOP - interfaces
    //|   interfaceDeclaration    -> {$interfaceDeclaration.st}
        functionDefinition     -> {$functionDefinition.st}
    |   constantDefinitionList    -> {$constantDefinitionList.st}
    ;


//TODO rstoll TINS-267 translator OOP - classes
/*    
classDeclaration
    :   ^('class' classModifier Identifier extendsDeclaration implementsDeclaration classBody)
        -> class(
            modifier={$classModifier.st},
            identifier={$Identifier}, 
            ext={$extendsDeclaration.st},
            impl={$implementsDeclaration.st},
            body={$classBody.st}
        )
    ;
    
classModifier
    :   ^(CLASS_MODIFIER list+=classModifierNames) -> modifier(modifiers={$list})
    |   CLASS_MODIFIER -> {null}
    ;

classModifierNames
@after {$st = %out(o={$text});
    :   Final
    |   Abstract
    ;
    
extendsDeclaration
    :   ^('extends' identifiers+=TYPE_NAME+)    -> extends(identifiers={$identifiers})
    |   'extends'                               -> {null}
    ;


implementsDeclaration
    :   ^('implements' identifiers+=TYPE_NAME+) -> impl(identifiers={$identifiers})
    |   'implements'                            -> {null}
    ;
    
classBody
    :   ^(CLASS_BODY def+=classBodyDefinition*) -> body(statements={$def})
    |   CLASS_BODY                              -> body(statements={null})
    ;
    
classBodyDefinition
    :   constDeclarationList            -> {$constDeclarationList.st}
    |   fieldDeclaration                -> {$fieldDeclaration.st}
    |   abstractConstructDeclaration    -> {$abstractConstructDeclaration.st}
    |   constructDeclaration            -> {$constructDeclaration.st}
    |   abstractMethodDeclaration       -> {$abstractMethodDeclaration.st}
    |   methodDeclaration               -> {$methodDeclaration.st}
    ;
*/    

constantDefinitionList
    :   ^(CONSTANT_DECLARATION_LIST type=. identifiers+=constantDefinition+)
        -> list(statements={$identifiers})
    ;
    
constantDefinition
    :   ^(Identifier unaryPrimitiveAtom)
        {
             VariableDto dto = controller.createVariableDtoForConstant(translationScopeDto.bindingCollection, $Identifier);
        }
        -> constant(type={dto.type}, identifier={dto.variableId}, value={$unaryPrimitiveAtom.st})
    ;
    
unaryPrimitiveAtom
@init{
    String unary="";
}
    :   primitiveAtomWithConstant
        -> {$primitiveAtomWithConstant.st}

    |   ^(  (    UNARY_MINUS {unary="-";}
            |    UNARY_PLUS {unary="+";}
            ) primitiveAtomWithConstant
        ) 
        -> unaryPreOperator(operator = {unary}, expression = {$primitiveAtomWithConstant.st})
    ;

//TODO rstoll TINS-267 translator OOP - classes
/*
fieldDeclaration
    :   ^(FIELD variableDeclarationList) -> {$variableDeclarationList.st}
    ;
variableDeclarationList
    :   ^(VARIABLE_DECLARATION_LIST
            ^(TYPE typeModifier allTypes)
            identifiers+=variableDeclaration+
        )
        -> variableDeclarationList(modifier={$typeModifier.st},identifiers={$identifiers})
    ;
    
variableDeclaration
    :   ^(VariableId expression)    -> assign(id={$VariableId},value={$expression.st})
    |   VariableId                  -> out(o={$VariableId.text})
    ;
*/
    
localVariableDefinitionList
    :   ^(VARIABLE_DECLARATION_LIST type=. variables+=localVariableDefinition+)
        -> list(statements={$variables})
    ;  

localVariableDefinition
    :   ^(VariableId defaultValue=unaryPrimitiveAtom?)
        {
            VariableDto dto = controller.createVariableDto(translationScopeDto.bindingCollection, $VariableId);
            StringTemplate type = getType(dto);
        }
        -> variable(type={type}, variableId={dto.variableId}, defaultValue={$unaryPrimitiveAtom.st})
    ;

//TODO rstoll TINS-267 translator OOP - classes
/*    
abstractConstructDeclaration
    :   ^(identifier='__construct'
            ^(METHOD_MODIFIER abstractMethodModifier)
            ^(TYPE typeModifier returnTypes)
            formalParameters
            BLOCK
        )    
        -> abstractMethod(
            modifier={$abstractMethodModifier.st},
            identifier={getMethodName($identifier.text)},
            params={$formalParameters.st}
        )
    ;
    
abstractMethodModifier
    :   (    list+=abstractToken                list+=accessModifierWithoutPrivate
        |    list+=accessModifierWithoutPrivate list+=abstractToken
        )
        -> modifier(modifiers={$list})
    ;

abstractToken
    :   Abstract -> out(o={$Abstract.text})
    ;
    
constructDeclaration
    :   ^(identifier='__construct'
            ^(METHOD_MODIFIER constructDestructModifier)
            ^(TYPE typeModifier returnTypes)
            formalParameters
            block
        )    
        -> method(
            modifier={$constructDestructModifier.st},
            identifier={getMethodName($identifier.text)},
            params={$formalParameters.st},
            body={$block.instructions}
        )
    ;

constructDestructModifier
    :   (    list+=finalToken       list+=accessModifier
        |    list+=finalToken
        |    list+=accessModifier   list+=finalToken
        |    list+=accessModifier
        )
        -> modifier(modifiers={$list})
    ;

abstractMethodDeclaration
    :   ^(METHOD_DECLARATION
            ^(METHOD_MODIFIER abstractMethodModifier)
            ^(TYPE typeModifier returnTypes)
            (identifier=Identifier|identifier=Destruct)
            formalParameters
            BLOCK
        )
        -> abstractMethod(
            modifier={$abstractMethodModifier.st},
            identifier={getMethodName($identifier.text)},
            params={$formalParameters.st}
        )
    ;

methodDeclaration
    :   ^(METHOD_DECLARATION
            ^(METHOD_MODIFIER methodModifier)
            ^(TYPE typeModifier returnTypes)
            (identifier=Identifier|identifier=Destruct)
            formalParameters
            block
        )
        -> method(
            modifier={$methodModifier.st},
            identifier={getMethodName($identifier.text)},
            params={$formalParameters.st},
            body={$block.instructions}
        )
    ;

methodModifier
    :   (   list+=staticToken list+=finalToken      list+=accessModifier
        |   list+=staticToken list+=accessModifier  list+=finalToken
        |   list+=staticToken list+=accessModifier
        
        |   list+=finalToken list+=staticToken      list+=accessModifier
        |   list+=finalToken list+=accessModifier   list+=staticToken
        |   list+=finalToken list+=accessModifier
        
        
        |   list+=accessModifier list+=finalToken   list+=staticToken
        |   list+=accessModifier list+=staticToken  list+=finalToken
        |   list+=accessModifier list+=staticToken
        |   list+=accessModifier list+=finalToken
        |   list+=accessModifier
        )
        -> modifier(modifiers={$list})
    ;
    
finalToken
    :   Final -> out(o={$Final.text})
    ;
*/    

block returns[List<Object> instructions]
    :   ^(BLOCK instr+=instruction*) {$instructions=$instr;}
    ;

//TODO rstoll TINS-268 translator OOP - interfaces
/*
interfaceDeclaration
    :   ^('interface'
            ^(CLASS_MODIFIER Abstract)
            Identifier 
            extendsDeclaration
            interfaceBody
        )
        -> interface(
            identifier={$Identifier}, 
            ext={$extendsDeclaration.st},
            body={$interfaceBody.st}
        )
    ;
    
interfaceBody
    :   ^(INTERFACE_BODY def+=interfaceBodyDefinition*) -> body(statements={$def})
    |   INTERFACE_BODY -> body(statements={null})
    ;

interfaceBodyDefinition
    :   constDeclarationList            -> {$constDeclarationList.st}
    |   interfaceConstructDeclaration   -> {$interfaceConstructDeclaration.st}
    |   interfaceMethodDeclaration      -> {$interfaceMethodDeclaration.st}
    ;

interfaceConstructDeclaration
    :   ^(identifier='__construct'
            ^(METHOD_MODIFIER abstractMethodModifier)
            ^(TYPE typeModifier returnTypes)
            formalParameters
            block
        )    
        -> abstractMethod(
            modifier={"public"},
            identifier={getMethodName($identifier.text)},
            params={$formalParameters.st}
        )
    ;
    
interfaceMethodDeclaration
    :   ^(METHOD_DECLARATION
            ^(METHOD_MODIFIER abstractMethodModifier)
            ^(TYPE typeModifier returnTypes)
            Identifier
            formalParameters
            BLOCK
        )
        -> abstractMethod(
            modifier={"public"},
            identifier={getMethodName($Identifier.text)},
            params={$formalParameters.st}
        )
    ;
*/

functionDefinition
@init{
    List<StringTemplate> methods = new ArrayList<>();
    TranslationScopeDto tmp = translationScopeDto;
}
    :   ^(Function
            FUNCTION_MODIFIER
            typeAst=.
            Identifier
            params=.
            {
                ITSPHPAst function = $Function;
                int childIndex = function.getChildIndex();
                boolean isNotMethodBefore = true;
                if (childIndex != 0) {
                    ITSPHPAst parent = (ITSPHPAst) function.getParent();
                    isNotMethodBefore = parent.getChild(childIndex - 1).getType() != Function;
                }

                SortedMap<String, OverloadDto> dtos = controller.getOverloadDtos($Identifier);
                for (OverloadDto dto : dtos.values()) {
                    int index = input.mark();                
                    StringTemplate returnType = getType(dto.returnVariable);
                    
                    List<String> typeParameters = null;
                    List<StringTemplate> constraints = null;
                    if (dto.typeParameters != null) {
                        typeParameters = new ArrayList<>();
                        constraints = new ArrayList<>();
                        for (TypeParameterDto typeParamDto : dto.typeParameters) {
                            typeParameters.add(typeParamDto.typeVariable);
                            if (typeParamDto.lowerBounds != null || typeParamDto.upperBounds != null) {
                                constraints.add(%typeParameter(
                                    lowerBounds={typeParamDto.lowerBounds},
                                    lowerMoreThanOne={
                                        typeParamDto.lowerBounds != null && typeParamDto.lowerBounds.size() > 1
                                    },
                                    typeVariable={typeParamDto.typeVariable},
                                    upperBounds={typeParamDto.upperBounds},
                                    upperMoreThanOne={
                                        typeParamDto.upperBounds != null && typeParamDto.upperBounds.size() > 1
                                    }
                                ));
                            }
                        }
                    }
                    

                    translationScopeDto = dto.translationScopeDto;
                    List<Object> instructions = block().instructions;
                    
                    List<StringTemplate> parameters = new ArrayList<>();
                    for (ParameterDto paramDto : dto.parameters) {
                        parameters.add(%parameter(
                            type={paramDto.type},
                            variableId={paramDto.parameterId}, 
                            defaultValue={paramDto.defaultValue}
                        ));
                        
                        if (paramDto.localVariableType != null) {
                            StringTemplate localVariable = %variable(
                                type={paramDto.localVariableType},
                                variableId={paramDto.localVariableId}, 
                                defaultValue={paramDto.parameterId}
                            );
                            instructions.add(0, localVariable);
                        }
                    }
                 
                    if (translationScopeDto.statements != null) {
                        for (String statement : translationScopeDto.statements) {
                            instructions.add(0, statement);
                        }
                    }

                    methods.add(%method(
                        modifier={null},
                        returnType={returnType},
                        identifier={dto.identifier},
                        typeParams={typeParameters},
                        params={parameters},
                        constraints={constraints},
                        body={instructions},
                        isNotMethodBefore={isNotMethodBefore}
                    ));
                    input.rewind(index);
                    isNotMethodBefore = false;
                }
            }
            .
        )
        
        -> methods(methods={methods})
    ;
finally{
    translationScopeDto = tmp;
}

instruction
    :   localVariableDefinitionList    -> {$localVariableDefinitionList.st}
    |   ifCondition                     -> {$ifCondition.st}
    |   switchCondition                 -> {$switchCondition.st}
    |   forLoop                         -> {$forLoop.st}
    |   foreachLoop                     -> {$foreachLoop.st}
    |   whileLoop                       -> {$whileLoop.st}
    |   doWhileLoop                     -> {$doWhileLoop.st}
    |   tryCatch                        -> {$tryCatch.st}
    |   ^(EXPRESSION expression?)       -> expression(expression={$expression.st})
    |   ^('return' expression)          -> return(expression = {$expression.st})
    |   'return'                        -> return(expression = {"null"})
    |   ^('throw' expression)           -> throw(expression = {$expression.st})
    |   ^(Echo exprs+=expression+)
        {
            List<String> argumentNames = new ArrayList<>(1);
            List<Object> arguments = new ArrayList<>(1);
            argumentNames.add("expressions");
            arguments.add($exprs.get(0));            
            FunctionApplicationDto dto = controller.getOperatorApplication(translationScopeDto, $Echo, arguments);
            $st = getOperatorOrFunctionApplication(
                dto, 
                argumentNames,
                $Echo,
                "echo",
                null,
                false);        
        }         
    
    |   ^('break' index=Int?)           -> break(index={$index.text})
    |   ^('continue' index=Int?)        -> continue(index={$index.text})
    ;

ifCondition
    :   ^(If
            expression 
            ifBlock=blockConditional
            elseBlock=blockConditional?
        )
        {
            List<String> argumentNames = new ArrayList<>(3);
            List<Object> arguments = new ArrayList<>(3);
            argumentNames.add("condition");
            arguments.add($expression.st);
            argumentNames.add("ifBlock");
            arguments.add($ifBlock.instructions);
            argumentNames.add("elseBlock");
            arguments.add($elseBlock.instructions);
            FunctionApplicationDto dto = controller.getOperatorApplication(translationScopeDto, $If, arguments);
            $st = getOperatorOrFunctionApplication(
                dto,
                argumentNames,
                $If,
                "if",
                null,
                false);
        }
    ;

blockConditional returns[List<Object> instructions]
    :   ^(BLOCK_CONDITIONAL instr+=instruction*) {$instructions=$instr;}
    ;
    
switchCondition
    :   ^(Switch expression content+=switchContent*)
        -> switch(condition={$expression.st}, content={$content})
    ;

switchContent
    :   ^(SWITCH_CASES labels+=caseLabel+) blockConditional
        -> switchContent(labels={$labels}, block={$blockConditional.instructions})
    ;
    
caseLabel
    :   expression  -> caseLabel(label={$expression.st})
    |   Default     -> out(o={$Default.text+":"})
    ;
    
forLoop
    :   ^(For
            (init=expressionList[true])
            ^(EXPRESSION_LIST condition+=expression*)
            update=expressionList[false]
            blockConditional
        )
        {
            if($condition != null){
                int lastElement = $condition.size() - 1;
                Object expression = $condition.get(lastElement);
                List<Object> operatorArguments = new ArrayList<>(1);
                operatorArguments.add(expression);
                FunctionApplicationDto dto = controller.getOperatorApplication(translationScopeDto, $For, operatorArguments);
                
                List<String> argumentNames = new ArrayList<>(4);
                argumentNames.add("init");
                argumentNames.add("condition");
                argumentNames.add("update");
                argumentNames.add("block");
                if (dto != null) {
                    List<Object> arguments = new ArrayList<>(4);
                    arguments.add($init.st);
                    $condition.set(lastElement, dto.arguments.get(0));
                    StringTemplate conditionTemplate = %expressionList(expressions={$condition}, semicolonAtTheEnd={true});
                    arguments.add(conditionTemplate);
                    arguments.add($update.st);
                    arguments.add($blockConditional.instructions);
                    dto.arguments = arguments;
                }
                $st = getOperatorOrFunctionApplication(
                    dto,
                    argumentNames,
                        $For,
                    "for",
                    null,
                    false);
            } else {
                StringTemplate conditionTemplate = %expressionList(expressions={$condition}, semicolonAtTheEnd={true});
                $st = %for(init={$init.st}, condition={conditionTemplate}, update={$update.st}, block={$blockConditional.instructions});
            }
        }
    ;

expressionList[boolean semicolonAtTheEnd]
    :   ^(EXPRESSION_LIST expr+=expression*)
        -> expressionList(expressions={$expr}, semicolonAtTheEnd={semicolonAtTheEnd})
    ;
    
foreachLoop
    :   ^(Foreach
            expression
            valueVariableId=VariableId
            keyVariableId=VariableId?
            blockConditional
        )
        {
            ISymbol symbol = $expression.start.getSymbol();
            String typeVariable =  translationScopeDto.bindingCollection.getTypeVariable(symbol.getAbsoluteName());
            IContainerTypeSymbol containerTypeSymbol = translationScopeDto.bindingCollection.getLowerTypeBounds(typeVariable);
            if(containerTypeSymbol == null){
               containerTypeSymbol = translationScopeDto.bindingCollection.getUpperTypeBounds(typeVariable);
            }
            IArrayTypeSymbol arrayTypeSymbol = null;
            for (ITypeSymbol typeSymbol : containerTypeSymbol.getTypeSymbols().values()){
                 if(typeSymbol instanceof IArrayTypeSymbol){
                     arrayTypeSymbol = (IArrayTypeSymbol) typeSymbol;
                 }
            }
            String keyVariableIdType = null;
            String keyVariableIdTemp = null;
            String valueVariableIdType = null;
            String valueVariableIdTemp = null;
            if(arrayTypeSymbol != null) {
                valueVariableIdType = controller.getTransformedTypeName(arrayTypeSymbol.getValueTypeSymbol());
                valueVariableIdTemp = controller.getTempVariableName(valueVariableId);        
                keyVariableIdType = controller.getTransformedTypeName(arrayTypeSymbol.getKeyTypeSymbol());
                keyVariableIdTemp = keyVariableId != null ? controller.getTempVariableName(keyVariableId) : null;
                
            }
            
            List<String> argumentNames = new ArrayList<>(8);
            List<Object> arguments = new ArrayList<>(8);
            argumentNames.add("array");
            arguments.add($expression.st);
            argumentNames.add("keyVariableIdType");
            arguments.add(keyVariableIdType);
            argumentNames.add("keyVariableId");
            arguments.add($keyVariableId != null ? $keyVariableId.getText() : null);
            argumentNames.add("keyVariableIdTemp");
            arguments.add(keyVariableIdTemp);
            argumentNames.add("valueVariableIdType");
            arguments.add(valueVariableIdType);
            argumentNames.add("valueVariableId");
            arguments.add($valueVariableId.getText());
            argumentNames.add("valueVariableIdTemp");
            arguments.add(valueVariableIdTemp);
            argumentNames.add("block");
            arguments.add($blockConditional.instructions);
            FunctionApplicationDto dto = controller.getOperatorApplication(translationScopeDto, $Foreach, arguments);
            if (keyVariableIdTemp != null && dto != null) {
                // we might calculate a runtime check for the variable but since we use a temp variable anyway, 
                // it is unnecessary, would result in (k <: int ? cast<int>(k) ...) = k_0;
                dto.arguments.set(2, $keyVariableId);
            }
            $st = getOperatorOrFunctionApplication(
                dto,
                argumentNames,
                $Foreach,
                "foreach",
                null,
                false);
        }
    ;

whileLoop
    :   ^('while' expression blockConditional) 
        -> while(condition={$expression.st}, block={$blockConditional.instructions})
    ;

doWhileLoop
    :   ^('do' block expression) 
        -> doWhile(block={$block.instructions}, condition={$expression.st})
    ;

tryCatch
    :   ^('try' blockConditional catchBlocks+=catchBlock+)
        -> tryCatch(tryBlock={$blockConditional.instructions}, catchBlocks={$catchBlocks})
    ;
    
catchBlock
    :   ^('catch' TYPE_NAME VariableId blockConditional)
        
        -> catchBlock(
            type={$TYPE_NAME.text}, 
            variableId={$VariableId.text},
            variableIdTemp={controller.getTempVariableName($VariableId)},
            block={$blockConditional.instructions})
    ;

expression
    :   atom                       -> {$atom.st}
    |   operator                   -> {$operator.st}
    |   functionCall               -> {$functionCall.st}
    //TODO rstoll TINS-271 - translator OOP - expressions 
    //|   methodCall                 -> {$methodCall.st}
    //|   methodCallSelfOrParent     -> {$methodCallSelfOrParent.st}
    //|   methodCallStatic           -> {$methodCallStatic.st}
    //|   classStaticAccess          -> {$classStaticAccess.st}
    |   postFixExpression          -> {$postFixExpression.st}
    |   ^('exit' ex=expression?)   -> exit(expression={$ex.st})
    ;
  
atom
    :   primitiveAtomWithConstant   -> {$primitiveAtomWithConstant.st}
    |   VariableId                  -> out(o={$VariableId.text})
    //TODO rstoll TINS-271 - translator OOP - expressions 
    //|   This                        -> out(o={$This.text})
    ;
           
primitiveAtomWithConstant
    :   (   v=Null
        |   v=False
        |   v=True
        |   v=Int   
        |   v=Float
        |   v=String
        ) -> out(o={$v.text})
    |   ^(TypeArray keyValuePairs+=arrayKeyValue*)  -> array(content ={$keyValuePairs})
    |   CONSTANT                                    -> out(o={$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)})
    //TODO rstoll TINS-271 - translator OOP - expressions 
    //|   ^(CLASS_STATIC_ACCESS staticAccess CONSTANT)
    //    -> fieldAccessStatic(
    //        accessor={$staticAccess.st},
    //        identifier={$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)}
    //    )
    ;

arrayKeyValue
    :   ^('=>' key=expression value=expression) -> keyValue(key={$key.st}, value={$value.st})
    |   expression -> {$expression.st}
    ;
    
//TODO rstoll TINS-271 - translator OOP - expressions    
/*
staticAccess
    :   TYPE_NAME   -> out(o={$TYPE_NAME.text})
    |   Self        -> out(o={$Self.text})
    |   Parent      -> out(o={$Parent.text})
    ;
*/
 
operator
    :   ^(unaryPreOperator expr=expression)
        {
            List<String> argumentNames = new ArrayList<>(1);
            List<Object> arguments = new ArrayList<>(1);
            argumentNames.add("expression");
            arguments.add($expr.st);
            FunctionApplicationDto dto = controller.getOperatorApplication(
                    translationScopeDto, $unaryPreOperator.start, arguments);
            $st = getOperatorOrFunctionApplication(
                dto, 
                argumentNames,
                $unaryPreOperator.start, 
                "unaryPreOperator",
                $unaryPreOperator.st,
                false);
        }

    |   ^(unaryPostOperator expr=expression)
        {
            List<String> argumentNames = new ArrayList<>(1);
            List<Object> arguments = new ArrayList<>(1);
            argumentNames.add("expression");
            arguments.add($expr.st);
            FunctionApplicationDto dto = controller.getOperatorApplication(
                    translationScopeDto, $unaryPostOperator.start, arguments);
            $st = getOperatorOrFunctionApplication(
                dto, 
                argumentNames,
                $unaryPostOperator.start, 
                "unaryPostOperator",
                $unaryPostOperator.st,
                false);
        }
    
    |   ^(binaryOperator left=expression right=expression)
        {
            List<String> argumentNames = new ArrayList<>(2);
            List<Object> arguments = new ArrayList<>(2);
            argumentNames.add("left");
            arguments.add($left.st);
            argumentNames.add("right");
            arguments.add($right.st);
            FunctionApplicationDto dto = controller.getOperatorApplication(
                    translationScopeDto, $binaryOperator.start, arguments);
           $st = getOperatorOrFunctionApplication(
                dto, 
                argumentNames,
                $binaryOperator.start, 
                "binaryOperator",
                $binaryOperator.st,
                $binaryOperator.needParentheses);
        }

    |   ^(QuestionMark cond=expression ifCase=expression elseCase=expression)
        {
            List<String> argumentNames = new ArrayList<>(3);
            List<Object> arguments = new ArrayList<>(3);
            argumentNames.add("cond");
            arguments.add($cond.st);
            argumentNames.add("ifCase");
            arguments.add($ifCase.st);
            argumentNames.add("elseCase");
            arguments.add($elseCase.st);
            FunctionApplicationDto dto = controller.getOperatorApplication(
                    translationScopeDto, $QuestionMark, arguments);
            $st = getOperatorOrFunctionApplication(
                dto, 
                argumentNames,
                $QuestionMark, 
                "ternaryOperator",
                null,
                controller.needParentheses($QuestionMark));        
        }
        
    |    ^(CAST ^(TYPE tMod=. castType=.) expr=expression)
        {
            List<String> argumentNames = new ArrayList<>(2);
            List<Object> arguments = new ArrayList<>(2);
            argumentNames.add("type");
            arguments.add($castType);
            argumentNames.add("expression");
            arguments.add($expr.st);
            FunctionApplicationDto dto = controller.getOperatorApplication(
                    translationScopeDto, $CAST, arguments);
            $st = getOperatorOrFunctionApplication(
                dto, 
                argumentNames,
                $CAST,
                "cast",
                null,
                controller.needParentheses($CAST));
        }
    
    |   ^(Instanceof expr=expression (type=TYPE_NAME|type=VariableId))
        {
            List<String> argumentNames = new ArrayList<>(2);
            List<Object> arguments = new ArrayList<>(2);
            argumentNames.add("expression");
            arguments.add($expr.st);
            argumentNames.add("type");
            arguments.add($type.text);
            FunctionApplicationDto dto = controller.getOperatorApplication(
                    translationScopeDto, $Instanceof, arguments);
            $st = getOperatorOrFunctionApplication(
                dto, 
                argumentNames,
                $Instanceof,
                "instanceof",
                null,
                controller.needParentheses($Instanceof));        
        }    
           
    //TODO rstoll TINS-271 - translator OOP - expressions
    /* 
    |   newOperator
        -> {$newOperator.st}
    */
    
    |   ^('clone' expr=expression)
        //clone does not have a migration function
        -> clone(expression={$expr.st})
    ;     

unaryPreOperator 
    :   PRE_INCREMENT   -> out(o={"++"})
    |   PRE_DECREMENT   -> out(o={"--"})
    |   '@'             -> out(o={"@"})
    |   '~'             -> out(o={"~"})
    |   '!'             -> out(o={"!"})
    |   UNARY_MINUS     -> out(o={"-"})
    |   UNARY_PLUS      -> out(o={"+"})
    ;

unaryPostOperator  
    :   POST_INCREMENT -> out(o={"++"})
    |   POST_DECREMENT -> out(o={"--"})
    ;

binaryOperator returns[boolean needParentheses]
@after {
    $st = %operator(o={$start.getText()});
    $needParentheses = controller.needParentheses($start);
}
    :   'or'
    |   'xor'
    |   'and'
    
    |   '=' 
    |   '+='
    |   '-='
    |   '*='
    |   '/=' 
    |   '&='
    |   '|='
    |   '^='
    |   '%='
    |   '.='
    |   '<<='
    |   '>>='
    
    |   '||'
    |   '&&'
    |   '|'
    |   '^'
    |   '&'
    
    |   '=='
    |   '!='
    |   '==='
    |   '!=='
    
    |   '<'
    |   '<='
    |   '>'
    |   '>='
    
    |   '<<'
    |   '>>'
    
    |   '+'
    |   '-'
    |   '.'
    
    |   '*'
    |   '/'
    |   '%'
    ;

//TODO rstoll TINS-276 conversions and casts
/*
castOperator
    :   ^(CAST
            ^(TYPE tMod=. (type=scalarTypes|type=arrayType)
            expr=expression
        )
        {$st = castHelper.getCast(templateLib, $type.start, $expr.start, $expr.st);}
    ;
*/
//TODO rstoll TINS-271 - translator OOP - expressions 
/*    
newOperator
    :   ^('new'
            type=TYPE_NAME 
            actualParameters
        )    
        -> new(type={$type.text}, parameters={$actualParameters.parameters})
    ;
*/

functionCall
    :   ^(FUNCTION_CALL identifier=TYPE_NAME actualParameters)
        {
            FunctionApplicationDto dto = controller.getFunctionApplication(
                translationScopeDto, $FUNCTION_CALL, $actualParameters.parameters);
            $st = getFunctionApplication(
                dto, 
                $FUNCTION_CALL, 
                $identifier, 
                functionErrorMessageCaller);
        }
    ;

actualParameters returns[List<Object> parameters]
    :   ^(ACTUAL_PARAMETERS params+=expression*) {$parameters=$params;}
    ;

//TODO rstoll TINS-271 - translator OOP - expressions 
/*
methodCall
    :   ^(METHOD_CALL (callee=This|callee=VariableId) identifier=Identifier actualParameters)
        -> methodCall(
            callee={$callee.text},
            identifier={getMethodName($identifier.text)},
            parameters={$actualParameters.parameters}
        )
    ;

methodCallSelfOrParent
    :   ^(METHOD_CALL (callee=Self|callee=Parent) identifier=Identifier actualParameters)
        -> methodCallStatic(
            callee={$callee.text},
            identifier={getMethodName($identifier.text)},
            parameters={$actualParameters.parameters}
        )
    ;

methodCallStatic
    :   ^(METHOD_CALL_STATIC TYPE_NAME identifier=Identifier actualParameters)
        -> methodCallStatic(
            callee={$TYPE_NAME.text},
            identifier={getMethodName($identifier.text)},
            parameters={$actualParameters.parameters}
        )
    ;

classStaticAccess
    :   ^(CLASS_STATIC_ACCESS staticAccess identifier=CLASS_STATIC_ACCESS_VARIABLE_ID)
        -> fieldAccessStatic(accessor={$staticAccess.st}, identifier={$identifier.text})
    ;    
*/

postFixExpression
    :   //TODO rstoll TINS-271 - translator OOP - expressions 
        //^(FIELD_ACCESS expression Identifier)
        //-> fieldAccess(expression={$expression.st}, identifier={$Identifier.text})

       ^(ARRAY_ACCESS expr=expression key=expression)
       {
            List<String> argumentNames = new ArrayList<>(2);
            List<Object> arguments = new ArrayList<>(2);
            argumentNames.add("expression");
            argumentNames.add("key");
            arguments.add($expr.st);
            arguments.add($key.st);
            FunctionApplicationDto dto = controller.getOperatorApplication(
                    translationScopeDto, $ARRAY_ACCESS, arguments);
            $st = getOperatorOrFunctionApplication(
                dto, 
                                argumentNames,
                $ARRAY_ACCESS, 
                "arrayAccess",
                null,//not required for template arrayAccess
                false);
        }

    //TODO rstoll TINS-271 - translator OOP - expressions 
    //|   ^(METHOD_CALL_POSTFIX expression Identifier actualParameters)
    //    -> postFixCall(
    //        expression={$expression.st},
    //        identifier={getMethodName($Identifier.text)},
    //        parameters={$actualParameters.parameters}
    //    )
    ;
