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
	 * Visit a parse tree produced by {@link TigerParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(TigerParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExp(TigerParser.LiteralExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WhileExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileExp(TigerParser.WhileExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForExp(TigerParser.ForExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExp(TigerParser.IdExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExp(TigerParser.CallExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfExp(TigerParser.IfExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LetExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetExp(TigerParser.LetExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SeqExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeqExp(TigerParser.SeqExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BinaryExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExp(TigerParser.BinaryExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(TigerParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#varDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDec(TigerParser.VarDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#functionDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDec(TigerParser.FunctionDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#typeDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDec(TigerParser.TypeDecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NameTy}
	 * labeled alternative in {@link TigerParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameTy(TigerParser.NameTyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayTy}
	 * labeled alternative in {@link TigerParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayTy(TigerParser.ArrayTyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RecordTy}
	 * labeled alternative in {@link TigerParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordTy(TigerParser.RecordTyContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#typeFields}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeFields(TigerParser.TypeFieldsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperator(TigerParser.OperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link TigerParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(TigerParser.LiteralContext ctx);
}