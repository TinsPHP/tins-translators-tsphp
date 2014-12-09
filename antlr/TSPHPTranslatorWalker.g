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
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.translation.IPrecedenceHelper;

}

@members{
private IPrecedenceHelper precedenceHelper;

public TSPHPTranslatorWalker(TreeNodeStream input, IPrecedenceHelper thePrecedenceHelper) {
    this(input);
    precedenceHelper = thePrecedenceHelper;
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
            if(name!=null){
                namespaceName=name.getText().substring(1,name.getText().length()-1);
            }
        }

        -> namespace(name={namespaceName},body={$namespaceBody.st})
    ;
    
namespaceBody
    :   ^(NAMESPACE_BODY statements+=statement*) -> body(statements={$statements})
    |   NAMESPACE_BODY -> body(statements={null})
    ;

statement
    :   useDeclarationList -> {$useDeclarationList.st}
        //TODO rstoll TINS-253 translator procedural - definitions
    //|   definition -> {$definition.st}
    //|   instruction -> {$instruction.st}
    //TODO rstoll, remove as soon as above alternatives are uncommented
    ;

useDeclarationList
    :   ^('use' declarations+=useDeclaration+)
        -> useDeclarationList(useDeclarations={$declarations})
    ;

useDeclaration
    :   ^(USE_DECLARATION TYPE_NAME Identifier)
        -> useDeclaration(type={$TYPE_NAME}, alias={$Identifier})
    ;

 //TODO rstoll TINS-253 translator procedural - definitions
 /*
definition
    :   //TODO rstoll TINS-267 translator OOP - classes
        //classDeclaration        -> {$classDeclaration.st}
        //TODO rstoll TINS-268 translator OOP - interfaces
    //|   interfaceDeclaration    -> {$interfaceDeclaration.st}
    
    |   functionDeclaration     -> {$functionDeclaration.st}
    |   constDeclarationList    -> {$constDeclarationList.st}
    ;
*/

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

//TODO rstoll TINS-253 translator procedural - definitions
/*
constDeclarationList
    :   ^(CONSTANT_DECLARATION_LIST
            ^(TYPE ^(TYPE_MODIFIER Public Static Final) scalarTypes)
            identifiers+=constDeclaration+
        ) 
        -> const(identifiers={$identifiers})
    ;
    
constDeclaration
    :   ^(Identifier unaryPrimitiveAtom)
        -> assign(id={$Identifier.text.substring(0,$Identifier.text.length()-1)}, value={$unaryPrimitiveAtom.st})
    ;
    
unaryPrimitiveAtom
    :   primitiveAtomWithConstant
        -> {$primitiveAtomWithConstant.st}

    |   ^(  (    unary=UNARY_MINUS
            |    unary=UNARY_PLUS
            ) primitiveAtomWithConstant
        ) 
        -> unaryPostOperator(operator = {$unary.text}, expression = {$primitiveAtomWithConstant.st})
    ;
*/

//TODO rstoll TINS-267 translator OOP - classes
/*
fieldDeclaration
    :   ^(FIELD variableDeclarationList) -> {$variableDeclarationList.st}
    ;
*/    
//TODO rstoll TINS-253 translator procedural - definitions
/*
variableDeclarationList
    :   ^(VARIABLE_DECLARATION_LIST
            ^(TYPE typeModifier allTypes)
            identifiers+=variableDeclaration+
        )
        -> variableDeclarationList(modifier={$typeModifier.st},identifiers={$identifiers})
    ;
    
localVariableDeclarationList
    :   ^(VARIABLE_DECLARATION_LIST
            ^(TYPE typeModifier allTypes) 
            variables+=localVariableDeclaration[$typeModifier.st]+
        )
        -> localVariableDeclarationList(variables={$variables})
    ;
    
typeModifier returns[boolean isCast,boolean isFalseable, boolean isNullable]
    :   ^(TYPE_MODIFIER cast='cast'? falseable='!'? nullable='?'? variableModifier?)
        {
            $isCast=cast!=null; 
            $isNullable=nullable!=null;
            $isFalseable=falseable!=null;
        } 
        -> {$variableModifier.st}

    |   TYPE_MODIFIER
        -> {null}
    ;
    
variableModifier
    :   list+=staticToken list+=accessModifier  -> modifier(modifiers={$list})
    |   list+=accessModifier list+=staticToken  -> modifier(modifiers={$list})
    |   accessModifier                          -> {$accessModifier.st}
    ;
    
staticToken
    :   Static -> {%{$Static.text}}
    ;

accessModifierWithoutPrivate
    :   Protected   -> {%{$Protected.text}}
    |   Public      -> {%{$Public.text}}
    ;    
    
accessModifier
    :   accessModifierWithoutPrivate -> {$accessModifierWithoutPrivate.st}
    |   Private                      -> {%{$Private.text}}
    ;

variableDeclaration
    :   ^(VariableId expression)    -> assign(id={$VariableId},value={$expression.st})
    |   VariableId                  -> {%{$VariableId.text}}
    ;
    
localVariableDeclaration[StringTemplate modifier]
    :   ^(VariableId v=expression)
        -> localVariableDeclaration(modifier={modifier}, variableId={$VariableId.text}, initValue={v})

    |   VariableId
        -> localVariableDeclaration(modifier={modifier}, variableId={$VariableId.text}, initValue={null})
    ;

allTypes
    :   primitiveTypes
    |   TYPE_NAME
    ;
    
primitiveTypes
    :   scalarTypes
    |   TypeArray
    |   TypeResource
    |   TypeMixed
    ;
    
primitiveTypesWithoutArray
    :   scalarTypes     -> {$scalarTypes.st}
    |   TypeResource    -> {%{$TypeResource.text}}
    |   TypeMixed       -> {%{$TypeMixed.text}}
    ;
    
scalarTypes
    :   TypeBool    -> {%{$TypeBool.text}}
    |   TypeInt     -> {%{$TypeInt.text}}
    |   TypeFloat   -> {%{$TypeFloat.text}}
    |   TypeString  -> {%{$TypeString.text}}
    ;
*/

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
    
returnTypes
    :   allTypes
    |   Void
    ;
    
formalParameters
    :   ^(PARAMETER_LIST params+=paramDeclaration+) -> parameterList(declarations={$params})
    |   PARAMETER_LIST -> {null}
    ;

paramDeclaration
@init{String defaultValue =null;}
    :   ^(PARAMETER_DECLARATION
            ^(TYPE typeModifier
                (   scalarAndResource
                |   typeName=arrayType
                |   TypeMixed
                |   typeName=classInterfaceType
                )
            )
            parameterNormalOrOptional
        )
        {
            defaultValue = $typeModifier.isNullable && typeName!=null
                ? "null"
                : $parameterNormalOrOptional.defaultValue;
        }
        -> parameter(type={$typeName.text}, variableId={$parameterNormalOrOptional.variableId}, defaultValue={defaultValue})
    ;

arrayType
    :   TypeArray -> {%{$TypeArray.text}}
    ;

classInterfaceType
    :   TYPE_NAME -> {%{$TYPE_NAME.text}}
    ;

scalarAndResource
    :   scalarTypes -> {$scalarTypes.st}
    |   TypeResource -> {%{$TypeResource.text}}
    ;

parameterNormalOrOptional returns[String variableId,String defaultValue]
    :   VariableId {$variableId=$VariableId.text;}
    |   ^(VariableId v=unaryPrimitiveAtom) {$variableId=$VariableId.text; $defaultValue=$v.text;}
    ;

block returns[List<Object> instructions]
    :   ^(BLOCK instr+=instruction*) {$instructions=$instr;}
    |   BLOCK
    ;
*/

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
//TODO rstoll TINS-253 translator procedural - definitions
/*
functionDeclaration
    :   ^('function'
            FUNCTION_MODIFIER
            ^(TYPE typeModifier returnTypes)
            Identifier
            formalParameters
            block
        )    
        -> method(
            modifier={null},
            identifier={getMethodName($Identifier.text)},
            params={$formalParameters.st},
            body={$block.instructions}
        )
    ;
*/
//TODO rstoll TINS-270 translator procedural - instructions
/*
instruction
        //TINS-253 translator procedural - definitions
    :   //localVariableDeclarationList    -> {$localVariableDeclarationList.st}
        //TODO rstoll TINS-254 translator procedural - control structures
    //|   ifCondition                     -> {$ifCondition.st}
    //|   switchCondition                 -> {$switchCondition.st}
    //|   forLoop                         -> {$forLoop.st}
    //|   foreachLoop                     -> {$foreachLoop.st}
    //|   whileLoop                       -> {$whileLoop.st}
    //|   doWhileLoop                     -> {$doWhileLoop.st}
    //|   tryCatch                        -> {$tryCatch.st}
    //TODO rstoll TINS-255 translator procedural - expressions
    //|   ^(EXPRESSION expression?)       -> expression(expression={$expression.st})
    |   ^('return' expression?)         -> return(expression = {$expression.st})
    |   ^('throw' expression)           -> throw(expression = {$expression.st})
    |   ^('echo' exprs+=expression+)    -> echo(expressions = {$exprs})
    |   ^('break' index=Int)            -> break(index={$index.text})
    |   'break'                         -> break(index={null})
    |   ^('continue' index=Int)         -> continue(index={$index.text})
    |   'continue'                      -> continue(index={null})
    ;
*/

//TODO rstoll TINS-254 translator procedural - control structures
/*
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
    :   ^('switch' expression content+=switchContent*) -> switch(condition={$expression.st}, content={$content})
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
            (init=variableDeclarationList|init=expressionList[true])
            condition=expressionList[true]
            update=expressionList[false]
            blockConditional
        )
        -> for(init={$init.st}, condition={$condition.st}, update={$update.st}, block={$blockConditional.instructions})
    ;

expressionList[boolean semicolonAtTheEnd]
    :   ^(EXPRESSION_LIST expr+=expression*)
        -> expressionList(expressions={$expr}, semicolonAtTheEnd={semicolonAtTheEnd})

    |   EXPRESSION_LIST
        -> expressionList(expressions={null}, semicolonAtTheEnd={semicolonAtTheEnd})
    ;

foreachLoop
    :   ^('foreach'
            expression
            
            //key 
            (    ^(VARIABLE_DECLARATION_LIST
                    ^(TYPE TYPE_MODIFIER scalarTypes) 
                    keyVariableId=VariableId
                )
            )?
                
            ^(VARIABLE_DECLARATION_LIST ^(TYPE TYPE_MODIFIER allTypes) valueVariableId=VariableId) 
            blockConditional
        )
        -> foreach(array={$expression.st}, keyVariableId={$keyVariableId.text}, valueVariableId={$valueVariableId.text}, block={$blockConditional.instructions})
    ;

whileLoop
    :   ^('while' expression blockConditional) -> while(condition={$expression.st}, block={$blockConditional.instructions})
    ;

doWhileLoop
    :   ^('do' block expression) -> doWhile(block={$block.instructions}, condition={$expression.st})
    ;

tryCatch
    :   ^('try' blockConditional catchBlocks+=catchBlock+)
        -> tryCatch(tryBlock={$blockConditional.instructions}, catchBlocks={$catchBlocks})
    ;
    
catchBlock
    :   ^('catch'
            ^(VARIABLE_DECLARATION_LIST
                ^(TYPE TYPE_MODIFIER TYPE_NAME)
                VariableId
            )
            blockConditional
        )
        -> catchBlock(type={$TYPE_NAME.text}, variableId={$VariableId.text}, block={$blockConditional.instructions})
    ;
*/

 //TODO rstoll TINS-255 translator procedural - expressions
 /*
expression
    :   atom                    -> {$atom.st}
    |   operator                -> {$operator.st}
    |   functionCall            -> {$functionCall.st}
    |   methodCall              -> {$methodCall.st}
    |   methodCallSelfOrParent  -> {$methodCallSelfOrParent.st}
    |   methodCallStatic        -> {$methodCallStatic.st}
    |   classStaticAccess       -> {$classStaticAccess.st}
    |   postFixExpression       -> {$postFixExpression.st}
    |   exit                    -> {$exit.st}
    ;
  
atom
    :   primitiveAtomWithConstant   -> {$primitiveAtomWithConstant.st}
    |   VariableId                  -> {%{$VariableId.text}}
    |   This                        -> {%{$This.text}}
    ;
        
primitiveAtomWithConstant
    :   Bool                                        -> {%{$Bool.text}}
    |   Int                                         -> {%{$Int.text}}
    |   Float                                       -> {%{$Float.text}}
    |   String                                      -> {%{$String.text}}
    |   Null                                        -> {%{$Null.text}}
    |   ^(TypeArray keyValuePairs+=arrayKeyValue*)  -> array(content ={$keyValuePairs})
    |   CONSTANT                                    -> {%{$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)}}

    |   ^(CLASS_STATIC_ACCESS staticAccess CONSTANT)
        -> fieldAccessStatic(
            accessor={$staticAccess.st},
            identifier={$CONSTANT.text.substring(0,$CONSTANT.text.length()-1)}
        )
    ;

arrayKeyValue
    :   ^('=>' key=expression value=expression) -> keyValue(key={$key.st}, value={$value.st})
    |   expression -> {$expression.st}
    ;
    
staticAccess
    :   TYPE_NAME   -> {%{$TYPE_NAME.text}}
    |   Self        -> {%{$Self.text}}
    |   Parent      -> {%{$Parent.text}}
    ;
 
operator
    :   ^(unaryPreOperator expr=expression)
        -> unaryPreOperator(operator ={$unaryPreOperator.st}, expression = {$expr.st})

    |   ^(unaryPostOperator expr=expression)
        -> unaryPostOperator(operator = {$unaryPostOperator.st}, expression = {$expr.st})

    |   ^(binaryOperatorWithoutDivision left=expression right=expression)
        -> binaryOperator(
            operator={$binaryOperatorWithoutDivision.st},
            left={$left.st}, right={$right.st},
            needParentheses={$binaryOperatorWithoutDivision.needParentheses}
        )

    |   division
        -> {$division.st}

    |   ^(QuestionMark cond=expression ifCase=expression elseCase=expression)
        -> ternaryOperator(
            cond={$cond.st},
            ifCase={$ifCase.st},
            elseCase={$elseCase.st},
            needParentheses={precedenceHelper.needParentheses($QuestionMark)}
        )

    |   castOperator
        -> {$castOperator.st}

    |   ^(Instanceof expr=expression (type=TYPE_NAME|type=VariableId))
        -> instanceof(
            expression={$expr.st},
            type={$type.text},
            needParentheses={precedenceHelper.needParentheses($Instanceof)}
        )

    |   newOperator
        -> {$newOperator.st}

    |   ^('clone' expr=expression)
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

binaryOperatorWithoutDivision returns[boolean needParentheses]
@after {
    $st = %operator(o={$start.getText()});
    $needParentheses = precedenceHelper.needParentheses($start);
}
    :   'or'
    |   'xor'
    |   'and'
    
    |   '='
    |   '+='
    |   '-='
    |   '*='
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
    |   '%'
    ;

division returns[boolean needParentheses]
@init {
    $needParentheses = precedenceHelper.needParentheses($start);
}
    :   ^('/' left=expression right=expression)
        {
            if(evaluatesToInt($left.start) && evaluatesToInt($right.start)){
                $st = %intDivision(
                    operator={$start.getText()},
                    left={$left.st},
                    right={$right.st},
                    needParentheses={$needParentheses});
            } else {
                $st = %binaryOperator(
                    operator={$start.getText()},
                    left={$left.st},
                    right={$right.st},
                    needParentheses={$needParentheses});
            }
        }

    |   ^('/=' left=expression right=expression)
        {
            if(evaluatesToInt($left.start) && evaluatesToInt($right.start)){
                $st = %intDivision(
                    operator={$start.getText()},
                    left={$left.st},
                    right={$right.st},
                    needParentheses={false});

                $st = %binaryOperator(
                    operator={"="},
                    left={$left.st},
                    right={$right.st},
                    needParentheses={$needParentheses});
            } else {
                $st = %binaryOperator(
                    operator={$start.getText()},
                    left={$left.st},
                    right={$right.st},
                    needParentheses={$needParentheses});
            }
        }
    ;

castOperator
    :   ^(CAST
            ^(TYPE tMod=. (type=scalarTypes|type=arrayType|type=classInterfaceType))
            expr=expression
        )
        {$st = castHelper.getCast(templateLib, $type.start, $expr.start, $expr.st);}
    ;
    
newOperator
    :   ^('new'
            type=TYPE_NAME 
            actualParameters
        )    
        -> new(type={$type.text}, parameters={$actualParameters.parameters})
    ;

actualParameters returns[List<Object> parameters]
    :   ^(ACTUAL_PARAMETERS params+=expression+) {$parameters=$params;}
    |   ACTUAL_PARAMETERS
    ;

functionCall
    :   ^(FUNCTION_CALL    identifier=TYPE_NAME actualParameters)
        -> functionCall(identifier={getMethodName($identifier.text)}, parameters={$actualParameters.parameters})
    ;
*/

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

 //TODO rstoll TINS-255 translator procedural - expressions
/*
postFixExpression
    :   //TODO rstoll TINS-271 - translator OOP - expressions 
        //^(FIELD_ACCESS expression Identifier)
        //-> fieldAccess(expression={$expression.st}, identifier={$Identifier.text})

    |   ^(ARRAY_ACCESS expr=expression index=expression)
        -> arrayAccess(expression={$expr.st}, index={$index.st})

    //TODO rstoll TINS-271 - translator OOP - expressions 
    //|   ^(METHOD_CALL_POSTFIX expression Identifier actualParameters)
    //    -> postFixCall(
    //        expression={$expression.st},
    //        identifier={getMethodName($Identifier.text)},
    //        parameters={$actualParameters.parameters}
    //    )
    ;

exit
    :   ^('exit' expression?)   -> exit(expression={$expression.st})
    |   'exit'                  -> exit(expression={null})
    ;
*/    