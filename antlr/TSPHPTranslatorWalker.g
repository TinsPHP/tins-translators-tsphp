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
import ch.tsphp.common.symbols.ITypeSymbol;
import ch.tsphp.tinsphp.common.translation.IPrecedenceHelper;
import ch.tsphp.tinsphp.translators.tsphp.antlrmod.ParameterDto;

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
        functionDeclaration     -> {$functionDeclaration.st}
    |   constDeclarationList    -> {$constDeclarationList.st}
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

constDeclarationList
    :   ^(CONSTANT_DECLARATION_LIST
            ^(TYPE ^(TYPE_MODIFIER Public Static Final) scalarTypesOrUnknown)
            identifiers+=constDeclaration+
        ) 
        -> const(type={$scalarTypesOrUnknown.st}, identifiers={$identifiers})
    ;
    
constDeclaration
    :   ^(Identifier unaryPrimitiveAtom)
        -> assign(id={$Identifier.text.substring(0,$Identifier.text.length()-1)}, value={$unaryPrimitiveAtom.st})
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
    
localVariableDeclarationList
    :   ^(VARIABLE_DECLARATION_LIST
            ^(TYPE typeModifier allTypesOrUnknown)
            variables+=localVariableDeclaration+
        )
        -> localVariableDeclarationList(
            type={%type(
                prefixModifiers={$typeModifier.prefixModifiers},
                type={$allTypesOrUnknown.st},
                suffixModifiers={$typeModifier.suffixModifiers}
            )},
            variables={$variables})
    ;  

localVariableDeclaration
    :   VariableId
        -> {%{$VariableId.text}}
    |   ^(VariableId defaultValue=unaryPrimitiveAtom)
         -> assign(id={$VariableId.text}, value={$unaryPrimitiveAtom.st})
    ;
    
typeModifier returns[Set<String> prefixModifiers, Set<String> suffixModifiers]
@init{
    $prefixModifiers = new HashSet<String>();
    $suffixModifiers = new HashSet<String>();
}
    :   ^(TYPE_MODIFIER
            ('cast' {$prefixModifiers.add("cast");})?
            ('!'    {$suffixModifiers.add("!");})? 
            ('?'    {$suffixModifiers.add("?");})?
        )
    |   TYPE_MODIFIER
    ;
  
returnTypesOrUnknown
    :   allTypesOrUnknown -> {$allTypesOrUnknown.st}
    //not yet supported by PHP
    //|   Void
    ;
    
allTypesOrUnknown
    :   allTypes -> {$allTypes.st}
    |   '?'      -> {%{"?"}}
    ;

allTypes
    :   primitiveTypes -> {$primitiveTypes.st} 
    |   TYPE_NAME      -> {%{$TYPE_NAME.text}}
    ;
    
primitiveTypes
    :   scalarTypes  -> {$scalarTypes.st}
    |   TypeArray    -> {%{$TypeArray.text}}
    |   TypeResource -> {%{$TypeResource.text}}
    |   TypeMixed    -> {%{$TypeMixed.text}}
    ;
/*
primitiveTypesWithoutArray
    :   scalarTypes     -> {$scalarTypes.st}
    |   TypeResource    -> {%{$TypeResource.text}}
    |   TypeMixed       -> {%{$TypeMixed.text}}
    ;
*/
scalarTypesOrUnknown
    :   scalarTypes -> {$scalarTypes.st}
    |   '?'         -> {%{"?"}}
    ;

scalarTypes
@init{
    $st = %{$start.getText()};
}
    :   TypeBool
    |   TypeInt
    |   TypeFloat
    |   TypeString
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
formalParameters
@init{
    List<ParameterDto> parameterDtos = new ArrayList<>();
    List<StringTemplate> declarations = new ArrayList<>();
}
    :   ^(PARAMETER_LIST (param=paramDeclaration {parameterDtos.add($param.parameterDto);})*) 
        {

           boolean canBeDefaultValue = true;
           for(int i=parameterDtos.size()-1; i >= 0; --i){
               ParameterDto dto = parameterDtos.get(i);
               String defaultValue = canBeDefaultValue ? dto.defaultValue : null;
               canBeDefaultValue &= defaultValue != null; 
               if(!canBeDefaultValue && !dto.suffixModifiers.contains("?") && dto.defaultValue != null && dto.defaultValue.toLowerCase().equals("null")){
                   dto.suffixModifiers.add("?");
               }
               StringTemplate type = %type(
                       prefixModifiers={dto.prefixModifiers}, 
                       type={dto.type},
                       suffixModifiers={dto.suffixModifiers}
               );
               declarations.add(0, %parameter(
                   type={type},
                   variableId={dto.variableId}, 
                   defaultValue={defaultValue}
               ));
           }
        }
        -> parameterList(declarations={declarations})
    ;


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

paramDeclaration returns[ParameterDto parameterDto]
    :   ^(PARAMETER_DECLARATION
            ^(TYPE typeModifier allTypesOrUnknown)
            (   varId=VariableId
            |   ^(varId=VariableId defaultValue=unaryPrimitiveAtom)
            )
        )
        {
            $parameterDto = new ParameterDto(
                $typeModifier.prefixModifiers,
                $allTypesOrUnknown.st,
                $typeModifier.suffixModifiers,
                $varId.text,
                $defaultValue.text);
        }
    ;

functionDeclaration
    :   ^('function'
            FUNCTION_MODIFIER
            ^(TYPE typeModifier returnTypesOrUnknown)
            Identifier
            formalParameters
            block
        )    
        -> method(
            modifier={null},
            returnType={%type(
                prefixModifiers={$typeModifier.prefixModifiers},
                type={$returnTypesOrUnknown.st},
                suffixModifiers={$typeModifier.suffixModifiers}
            )},
            identifier={getMethodName($Identifier.text)},
            params={$formalParameters.st},
            body={$block.instructions}
        )
    ;

instruction
    :   localVariableDeclarationList    -> {$localVariableDeclarationList.st}
    |   ifCondition                     -> {$ifCondition.st}
    |   switchCondition                 -> {$switchCondition.st}
    |   forLoop                         -> {$forLoop.st}
    //TODO rstoll TINS-247 translate procedural - foreach header
    //|   foreachLoop                     -> {$foreachLoop.st}
    |   whileLoop                       -> {$whileLoop.st}
    |   doWhileLoop                     -> {$doWhileLoop.st}
    //TODO rstoll TINS-248 translate procedural - catch header
    //|   tryCatch                        -> {$tryCatch.st}
    |   ^(EXPRESSION expression?)       -> expression(expression={$expression.st})
    |   ^('return' expression?)         -> return(expression = {$expression.st})
    |   ^('throw' expression)           -> throw(expression = {$expression.st})
    //TODO rstoll TINS-270 translator procedural - instructions
    //|   ^('echo' exprs+=expression+)    -> echo(expressions = {$exprs})
    //|   ^('break' index=Int)            -> break(index={$index.text})
    //|   'break'                         -> break(index={null})
    //|   ^('continue' index=Int)         -> continue(index={$index.text})
    //|   'continue'                      -> continue(index={null})
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
    
//TODO rstoll TINS-247 translate procedural - foreach header
/*  
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
*/

whileLoop
    :   ^('while' expression blockConditional) 
        -> while(condition={$expression.st}, block={$blockConditional.instructions})
    ;

doWhileLoop
    :   ^('do' block expression) 
        -> doWhile(block={$block.instructions}, condition={$expression.st})
    ;

//TODO rstoll TINS-248 translate procedural - catch header
/*
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
        -> unaryPreOperator(operator ={$unaryPreOperator.st}, expression = {$expr.st})

    |   ^(unaryPostOperator expr=expression)
        -> unaryPostOperator(operator = {$unaryPostOperator.st}, expression = {$expr.st})

    |   ^(binaryOperator left=expression right=expression)
        -> binaryOperator(
            operator={$binaryOperator.st},
            left={$left.st}, right={$right.st},
            needParentheses={$binaryOperator.needParentheses}
        )

    |   ^(QuestionMark cond=expression ifCase=expression elseCase=expression)
        -> ternaryOperator(
            cond={$cond.st},
            ifCase={$ifCase.st},
            elseCase={$elseCase.st},
            needParentheses={precedenceHelper.needParentheses($QuestionMark)}
        )
        
    //TODO rstoll TINS-276 conversions and casts
    /* 
    |   castOperator
        -> {$castOperator.st}
    */
    |   ^(Instanceof expr=expression (type=TYPE_NAME|type=VariableId))
        -> instanceof(
            expression={$expr.st},
            type={$type.text},
            needParentheses={precedenceHelper.needParentheses($Instanceof)}
        )

    //TODO rstoll TINS-271 - translator OOP - expressions
    /* 
    |   newOperator
        -> {$newOperator.st}
    */
    
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

binaryOperator returns[boolean needParentheses]
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
        -> functionCall(identifier={getMethodName($identifier.text)}, parameters={$actualParameters.parameters})
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