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

import java.util.Set;
import java.util.HashSet;
import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.symbols.ISymbol;
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.inference.constraints.IOverloadBindings;
import ch.tsphp.tinsphp.common.scopes.IGlobalNamespaceScope;
import ch.tsphp.tinsphp.common.symbols.IArrayTypeSymbol;
import ch.tsphp.tinsphp.common.translation.ITranslatorController;
import ch.tsphp.tinsphp.common.translation.dtos.MethodDto;
import ch.tsphp.tinsphp.common.translation.dtos.ParameterDto;
import ch.tsphp.tinsphp.common.translation.dtos.TypeParameterDto;
import ch.tsphp.tinsphp.common.translation.dtos.VariableDto;

}

@members{
private ITranslatorController controller;
private IOverloadBindings currentBindings;

public TSPHPTranslatorWalker(
        TreeNodeStream input, 
        ITranslatorController theController, 
        IGlobalNamespaceScope globalDefaultNamespaceScope) {
    this(input);
    controller = theController;
    List<IOverloadBindings> bindingsList = globalDefaultNamespaceScope.getBindings();
    if (bindingsList != null && !bindingsList.isEmpty()){
        currentBindings = bindingsList.get(0);
    }
}

private String getMethodName(String name) {
    return name.substring(0, name.length() - 2);
}

}

compilationUnit    
    :   (n+=namespace+) -> file(namespaces={$n})
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
@after {$st = %{$text};}
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
             VariableDto dto = controller.createVariableDtoForConstant(currentBindings, $Identifier);
             StringTemplate type = %type(
                 prefixModifiers={dto.type.prefixModifiers}, 
                 type={dto.type.type},
                 suffixModifiers={dto.type.suffixModifiers}
             );
        }
        -> constant(type={type}, identifier={dto.variableId}, value={$unaryPrimitiveAtom.st})
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
    |   VariableId                  -> {%{$VariableId.text}}
    ;
*/
    
localVariableDefinitionList
    :   ^(VARIABLE_DECLARATION_LIST type=. variables+=localVariableDefinition+)
        -> list(statements={$variables})
    ;  

localVariableDefinition
    :   ^(VariableId defaultValue=unaryPrimitiveAtom?)
        {
            VariableDto dto = controller.createVariableDto(currentBindings, $VariableId);
            StringTemplate type = %type(
                prefixModifiers={dto.type.prefixModifiers}, 
                type={dto.type.type},
                suffixModifiers={dto.type.suffixModifiers}
            );
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
    :   Abstract -> {%{$Abstract.text}}
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
    :   Final -> {%{$Final.text}}
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
    IOverloadBindings tmp = currentBindings;
}
    :   ^('function'
            FUNCTION_MODIFIER
            typeAst=.
            Identifier
            params=.
            {
                List<MethodDto> dtos = controller.createMethodDtos($Identifier);
                for(MethodDto dto : dtos){
                    int index = input.mark();                
                     StringTemplate returnType = %type(
                        prefixModifiers={dto.returnType.prefixModifiers},
                        type={dto.returnType.type},
                        suffixModifiers={dto.returnType.suffixModifiers}
                    );
                    
                    List<StringTemplate> typeParameters = null;
                    if(dto.typeParameters != null){
                        typeParameters = new ArrayList<>();
                        for(TypeParameterDto typeParamDto : dto.typeParameters){
                            typeParameters.add(%typeParameter(
                                lowerBounds={typeParamDto.lowerBounds},
                                lowerMoreThanOne={typeParamDto.lowerBounds != null && typeParamDto.lowerBounds.size() > 1},
                                typeVariable={typeParamDto.typeVariable},
                                upperBounds={typeParamDto.upperBounds},
                                upperMoreThanOne={typeParamDto.upperBounds != null && typeParamDto.upperBounds.size() > 1}
                            ));
                        }
                    }
                    
                    List<StringTemplate> parameters = new ArrayList<>();
                    for(ParameterDto paramDto : dto.parameters){
                        StringTemplate type = %type(
                            prefixModifiers={paramDto.type.prefixModifiers}, 
                            type={paramDto.type.type},
                            suffixModifiers={paramDto.type.suffixModifiers}
                        );
                        parameters.add(%parameter(
                            type={type},
                            variableId={paramDto.variableId}, 
                            defaultValue={paramDto.defaultValue}
                        ));
                    }
                                      
                    currentBindings = dto.bindings;
                    TSPHPTranslatorWalker.block_return block = block();

                    methods.add(%method(
                        modifier={null},
                        returnType={returnType},
                        identifier={dto.identifier},
                        typeParams={typeParameters},
                        params={parameters},
                        body={block.instructions}
                    ));
                    input.rewind(index);
                }
            }
            .
        )
        
        -> methods(methods={methods})
    ;
finally{
    currentBindings = tmp;
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
    |   ^('echo' exprs+=expression+)    -> echo(expressions = {$exprs})
    |   ^('break' index=Int?)           -> break(index={$index.text})
    |   ^('continue' index=Int?)        -> continue(index={$index.text})
    ;

ifCondition
    :   ^('if'
            expression 
            ifBlock=blockConditional
            elseBlock=blockConditional?
        )
        -> if(condition={$expression.st}, ifBlock={$ifBlock.instructions}, elseBlock={$elseBlock.instructions})
    ;

blockConditional returns[List<Object> instructions]
    :   ^(BLOCK_CONDITIONAL instr+=instruction*) {$instructions=$instr;}
    ;
    
switchCondition
    :   ^('switch' expression content+=switchContent*) 
        -> switch(condition={$expression.st}, content={$content})
    ;

switchContent
    :   ^(SWITCH_CASES labels+=caseLabel+) blockConditional
        -> switchContent(labels={$labels}, block={$blockConditional.instructions})
    ;
    
caseLabel
    :   expression  -> caseLabel(label={$expression.st})
    |   Default     -> {%{$Default.text+":"}}
    ;
    
forLoop
    :   ^('for'
            (init=expressionList[true])
            condition=expressionList[true]
            update=expressionList[false]
            blockConditional
        )
        -> for(init={$init.st}, condition={$condition.st}, update={$update.st}, block={$blockConditional.instructions})
    ;

expressionList[boolean semicolonAtTheEnd]
    :   ^(EXPRESSION_LIST expr+=expression*)
        -> expressionList(expressions={$expr}, semicolonAtTheEnd={semicolonAtTheEnd})
    ;
    
foreachLoop
    :   ^('foreach'
            expression
            keyVariableId=VariableId?
            valueVariableId=VariableId
            blockConditional
        )
        {
            IArrayTypeSymbol arrayTypeSymbol = (IArrayTypeSymbol) $expression.start.getEvalType();            
            String keyVariableIdType = arrayTypeSymbol != null ? arrayTypeSymbol.getKeyTypeSymbol().getAbsoluteName() : "?";
            String keyVariableIdTemp = $keyVariableId != null ? controller.getTempVariableName($keyVariableId) : null;
            String valueVariableIdType = arrayTypeSymbol != null ? arrayTypeSymbol.getValueTypeSymbol().getAbsoluteName() : "?";
            String valueVariableIdTemp = controller.getTempVariableName($valueVariableId);
        }
        -> foreach(
            array={$expression.st}, 
            keyVariableIdType={keyVariableIdType},
            keyVariableId={$keyVariableId != null ? $keyVariableId.getText() : null}, 
            keyVariableIdTemp={keyVariableIdTemp}, 
            valueVariableIdType={valueVariableIdType},            
            valueVariableId={$valueVariableId.getText()},
            valueVariableIdTemp={valueVariableIdTemp},
            block={$blockConditional.instructions})
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
    |   VariableId                  -> {%{$VariableId.text}}
    //TODO rstoll TINS-271 - translator OOP - expressions 
    //|   This                        -> {%{$This.text}}
    ;
           
primitiveAtomWithConstant
    :   (   v=Null
        |   v=False
        |   v=True
        |   v=Int   
        |   v=Float
        |   v=String
        ) -> {%{$v.text}}
    |   ^(TypeArray keyValuePairs+=arrayKeyValue*)  -> array(content ={$keyValuePairs})
    |   CONSTANT                                    -> {%{$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)}}
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
    :   TYPE_NAME   -> {%{$TYPE_NAME.text}}
    |   Self        -> {%{$Self.text}}
    |   Parent      -> {%{$Parent.text}}
    ;
*/
 
operator
    :   ^(unaryPreOperator expr=expression)
        {
            String migrationFunction = controller.getMigrationFunction(currentBindings, $unaryPreOperator.start);
            if(migrationFunction == null){
                retval.st = %unaryPreOperator(operator={$unaryPreOperator.st}, expression={$expr.st});
            }else{
                retval.st = %functionCall(identifier={migrationFunction}, parameters={$expr.st});
            }
        }

    |   ^(unaryPostOperator expr=expression)
        {
            String migrationFunction = controller.getMigrationFunction(currentBindings, $unaryPostOperator.start);
            if(migrationFunction == null){
                retval.st = %unaryPostOperator(operator={$unaryPostOperator.st}, expression={$expr.st});
            }else{
                retval.st = %functionCall(identifier={migrationFunction}, parameters={$expr.st});
            }
        }
    
    |   ^(binaryOperator left=expression right=expression)
        {
            String migrationFunction = controller.getMigrationFunction(currentBindings, $binaryOperator.start);
            if(migrationFunction == null){
                retval.st = %binaryOperator(
                    operator={$binaryOperator.st}, 
                    left={$left.st}, 
                    right={$right.st},
                    needParentheses={$binaryOperator.needParentheses});
            }else{
                List<StringTemplate> parameters = new ArrayList<>();
                parameters.add($left.st);
                parameters.add($right.st);
                retval.st = %functionCall(identifier={migrationFunction}, parameters={parameters});
            }
        }

    |   ^(QuestionMark cond=expression ifCase=expression elseCase=expression)
        //ternary does not have a migration function
        -> ternaryOperator(
            cond={$cond.st},
            ifCase={$ifCase.st},
            elseCase={$elseCase.st},
            needParentheses={controller.needParentheses($QuestionMark)}
        )
        
    //TODO rstoll TINS-276 conversions and casts
    /* 
    |   castOperator
        -> {$castOperator.st}
    */
    |   ^(Instanceof expr=expression (type=TYPE_NAME|type=VariableId))
        //instanceof does not have a migration function
        -> instanceof(
            expression={$expr.st},
            type={$type.text},
            needParentheses={controller.needParentheses($Instanceof)}
        )

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
    :   PRE_INCREMENT   -> {%{"++"}}
    |   PRE_DECREMENT   -> {%{"--"}}
    |   '@'             -> {%{"@"}}
    |   '~'             -> {%{"~"}}
    |   '!'             -> {%{"!"}}
    |   UNARY_MINUS     -> {%{"-"}}
    |   UNARY_PLUS      -> {%{"+"}}
    ;

unaryPostOperator  
    :   POST_INCREMENT -> {%{"++"}}
    |   POST_DECREMENT -> {%{"--"}}
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
            String overloadName = controller.getOverloadName(currentBindings, $FUNCTION_CALL, $identifier);
        }
        -> functionCall(identifier={overloadName}, parameters={$actualParameters.parameters})
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

       ^(ARRAY_ACCESS expr=expression index=expression)
        -> arrayAccess(expression={$expr.st}, index={$index.st})

    //TODO rstoll TINS-271 - translator OOP - expressions 
    //|   ^(METHOD_CALL_POSTFIX expression Identifier actualParameters)
    //    -> postFixCall(
    //        expression={$expression.st},
    //        identifier={getMethodName($Identifier.text)},
    //        parameters={$actualParameters.parameters}
    //    )
    ;
