// Generated from org/tiger/Tiger.g4 by ANTLR 4.13.1
package org.tiger;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TigerParser}.
 */
public interface TigerListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TigerParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(TigerParser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(TigerParser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#packageDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPackageDeclaration(TigerParser.PackageDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#packageDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPackageDeclaration(TigerParser.PackageDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterImportDeclaration(TigerParser.ImportDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitImportDeclaration(TigerParser.ImportDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTypeDeclaration(TigerParser.TypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTypeDeclaration(TigerParser.TypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(TigerParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(TigerParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceDeclaration(TigerParser.InterfaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceDeclaration(TigerParser.InterfaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(TigerParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(TigerParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceBody(TigerParser.InterfaceBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceBody(TigerParser.InterfaceBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#interfaceBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceBodyDeclaration(TigerParser.InterfaceBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#interfaceBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceBodyDeclaration(TigerParser.InterfaceBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMethodDeclaration(TigerParser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMethodDeclaration(TigerParser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#interfaceFieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceFieldDeclaration(TigerParser.InterfaceFieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#interfaceFieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceFieldDeclaration(TigerParser.InterfaceFieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassBodyDeclaration(TigerParser.ClassBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassBodyDeclaration(TigerParser.ClassBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaration(TigerParser.MemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaration(TigerParser.MemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(TigerParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(TigerParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void enterMethodBody(TigerParser.MethodBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void exitMethodBody(TigerParser.MethodBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(TigerParser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(TigerParser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclaration(TigerParser.ConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclaration(TigerParser.ConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#constructorBody}.
	 * @param ctx the parse tree
	 */
	void enterConstructorBody(TigerParser.ConstructorBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#constructorBody}.
	 * @param ctx the parse tree
	 */
	void exitConstructorBody(TigerParser.ConstructorBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameters(TigerParser.FormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameters(TigerParser.FormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameterList(TigerParser.FormalParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameterList(TigerParser.FormalParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameter(TigerParser.FormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameter(TigerParser.FormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(TigerParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(TigerParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(TigerParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(TigerParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclaration(TigerParser.LocalVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableDeclaration(TigerParser.LocalVariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(TigerParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(TigerParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#forControl}.
	 * @param ctx the parse tree
	 */
	void enterForControl(TigerParser.ForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#forControl}.
	 * @param ctx the parse tree
	 */
	void exitForControl(TigerParser.ForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(TigerParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(TigerParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void enterForUpdate(TigerParser.ForUpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void exitForUpdate(TigerParser.ForUpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void enterParExpression(TigerParser.ParExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void exitParExpression(TigerParser.ParExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(TigerParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(TigerParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(TigerParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(TigerParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreator(TigerParser.CreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreator(TigerParser.CreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#createdName}.
	 * @param ctx the parse tree
	 */
	void enterCreatedName(TigerParser.CreatedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#createdName}.
	 * @param ctx the parse tree
	 */
	void exitCreatedName(TigerParser.CreatedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#arrayCreatorRest}.
	 * @param ctx the parse tree
	 */
	void enterArrayCreatorRest(TigerParser.ArrayCreatorRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#arrayCreatorRest}.
	 * @param ctx the parse tree
	 */
	void exitArrayCreatorRest(TigerParser.ArrayCreatorRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#classCreatorRest}.
	 * @param ctx the parse tree
	 */
	void enterClassCreatorRest(TigerParser.ClassCreatorRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#classCreatorRest}.
	 * @param ctx the parse tree
	 */
	void exitClassCreatorRest(TigerParser.ClassCreatorRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(TigerParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(TigerParser.MethodCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(TigerParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(TigerParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(TigerParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(TigerParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#classModifier}.
	 * @param ctx the parse tree
	 */
	void enterClassModifier(TigerParser.ClassModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#classModifier}.
	 * @param ctx the parse tree
	 */
	void exitClassModifier(TigerParser.ClassModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#interfaceModifier}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceModifier(TigerParser.InterfaceModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#interfaceModifier}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceModifier(TigerParser.InterfaceModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#methodModifier}.
	 * @param ctx the parse tree
	 */
	void enterMethodModifier(TigerParser.MethodModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#methodModifier}.
	 * @param ctx the parse tree
	 */
	void exitMethodModifier(TigerParser.MethodModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#interfaceMethodModifier}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMethodModifier(TigerParser.InterfaceMethodModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#interfaceMethodModifier}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMethodModifier(TigerParser.InterfaceMethodModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#fieldModifier}.
	 * @param ctx the parse tree
	 */
	void enterFieldModifier(TigerParser.FieldModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#fieldModifier}.
	 * @param ctx the parse tree
	 */
	void exitFieldModifier(TigerParser.FieldModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#constructorModifier}.
	 * @param ctx the parse tree
	 */
	void enterConstructorModifier(TigerParser.ConstructorModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#constructorModifier}.
	 * @param ctx the parse tree
	 */
	void exitConstructorModifier(TigerParser.ConstructorModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void enterVariableModifier(TigerParser.VariableModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void exitVariableModifier(TigerParser.VariableModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(TigerParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(TigerParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(TigerParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(TigerParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#classType}.
	 * @param ctx the parse tree
	 */
	void enterClassType(TigerParser.ClassTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#classType}.
	 * @param ctx the parse tree
	 */
	void exitClassType(TigerParser.ClassTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#typeList}.
	 * @param ctx the parse tree
	 */
	void enterTypeList(TigerParser.TypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#typeList}.
	 * @param ctx the parse tree
	 */
	void exitTypeList(TigerParser.TypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void enterTypeTypeOrVoid(TigerParser.TypeTypeOrVoidContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void exitTypeTypeOrVoid(TigerParser.TypeTypeOrVoidContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(TigerParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(TigerParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedNameList(TigerParser.QualifiedNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedNameList(TigerParser.QualifiedNameListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarators(TigerParser.VariableDeclaratorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarators(TigerParser.VariableDeclaratorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(TigerParser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(TigerParser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaratorId(TigerParser.VariableDeclaratorIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaratorId(TigerParser.VariableDeclaratorIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitializer(TigerParser.VariableInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitializer(TigerParser.VariableInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitializer(TigerParser.ArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitializer(TigerParser.ArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(TigerParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(TigerParser.LiteralContext ctx);
}