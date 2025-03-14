// Generated from org/tiger/Tiger.g4 by ANTLR 4.13.1
package org.tiger;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link TigerParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface TigerVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link TigerParser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilationUnit(TigerParser.CompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#packageDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageDeclaration(TigerParser.PackageDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDeclaration(TigerParser.ImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#typeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDeclaration(TigerParser.TypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(TigerParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceDeclaration(TigerParser.InterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(TigerParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#interfaceBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceBody(TigerParser.InterfaceBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#interfaceBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceBodyDeclaration(TigerParser.InterfaceBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMethodDeclaration(TigerParser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#interfaceFieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceFieldDeclaration(TigerParser.InterfaceFieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBodyDeclaration(TigerParser.ClassBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#memberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberDeclaration(TigerParser.MemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(TigerParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#methodBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodBody(TigerParser.MethodBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(TigerParser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(TigerParser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#constructorBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorBody(TigerParser.ConstructorBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#formalParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameters(TigerParser.FormalParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#formalParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameterList(TigerParser.FormalParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#formalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameter(TigerParser.FormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(TigerParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(TigerParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclaration(TigerParser.LocalVariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(TigerParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#forControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForControl(TigerParser.ForControlContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(TigerParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#forUpdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForUpdate(TigerParser.ForUpdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#parExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpression(TigerParser.ParExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(TigerParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(TigerParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreator(TigerParser.CreatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#createdName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreatedName(TigerParser.CreatedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#arrayCreatorRest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayCreatorRest(TigerParser.ArrayCreatorRestContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#classCreatorRest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassCreatorRest(TigerParser.ClassCreatorRestContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#methodCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCall(TigerParser.MethodCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(TigerParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(TigerParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#classModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassModifier(TigerParser.ClassModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#interfaceModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceModifier(TigerParser.InterfaceModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#methodModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodModifier(TigerParser.MethodModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#interfaceMethodModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMethodModifier(TigerParser.InterfaceMethodModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#fieldModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldModifier(TigerParser.FieldModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#constructorModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorModifier(TigerParser.ConstructorModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#variableModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableModifier(TigerParser.VariableModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(TigerParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(TigerParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#classType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassType(TigerParser.ClassTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#typeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeList(TigerParser.TypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeTypeOrVoid(TigerParser.TypeTypeOrVoidContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(TigerParser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedNameList(TigerParser.QualifiedNameListContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#variableDeclarators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarators(TigerParser.VariableDeclaratorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(TigerParser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaratorId(TigerParser.VariableDeclaratorIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#variableInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitializer(TigerParser.VariableInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#arrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitializer(TigerParser.ArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(TigerParser.LiteralContext ctx);
}