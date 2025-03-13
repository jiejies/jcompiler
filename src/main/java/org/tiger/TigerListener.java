// Generated from org/tiger/Tiger.g4 by ANTLR 4.13.1
package org.tiger;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TigerParser}.
 */
public interface TigerListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TigerParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(TigerParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(TigerParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExp(TigerParser.LiteralExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExp(TigerParser.LiteralExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterWhileExp(TigerParser.WhileExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitWhileExp(TigerParser.WhileExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterForExp(TigerParser.ForExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitForExp(TigerParser.ForExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterIdExp(TigerParser.IdExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitIdExp(TigerParser.IdExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterCallExp(TigerParser.CallExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitCallExp(TigerParser.CallExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterIfExp(TigerParser.IfExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitIfExp(TigerParser.IfExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LetExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterLetExp(TigerParser.LetExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LetExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitLetExp(TigerParser.LetExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SeqExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterSeqExp(TigerParser.SeqExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SeqExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitSeqExp(TigerParser.SeqExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExp(TigerParser.BinaryExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryExp}
	 * labeled alternative in {@link TigerParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExp(TigerParser.BinaryExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(TigerParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(TigerParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#varDec}.
	 * @param ctx the parse tree
	 */
	void enterVarDec(TigerParser.VarDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#varDec}.
	 * @param ctx the parse tree
	 */
	void exitVarDec(TigerParser.VarDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#functionDec}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDec(TigerParser.FunctionDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#functionDec}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDec(TigerParser.FunctionDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#typeDec}.
	 * @param ctx the parse tree
	 */
	void enterTypeDec(TigerParser.TypeDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#typeDec}.
	 * @param ctx the parse tree
	 */
	void exitTypeDec(TigerParser.TypeDecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NameTy}
	 * labeled alternative in {@link TigerParser#type}.
	 * @param ctx the parse tree
	 */
	void enterNameTy(TigerParser.NameTyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NameTy}
	 * labeled alternative in {@link TigerParser#type}.
	 * @param ctx the parse tree
	 */
	void exitNameTy(TigerParser.NameTyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayTy}
	 * labeled alternative in {@link TigerParser#type}.
	 * @param ctx the parse tree
	 */
	void enterArrayTy(TigerParser.ArrayTyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayTy}
	 * labeled alternative in {@link TigerParser#type}.
	 * @param ctx the parse tree
	 */
	void exitArrayTy(TigerParser.ArrayTyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RecordTy}
	 * labeled alternative in {@link TigerParser#type}.
	 * @param ctx the parse tree
	 */
	void enterRecordTy(TigerParser.RecordTyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RecordTy}
	 * labeled alternative in {@link TigerParser#type}.
	 * @param ctx the parse tree
	 */
	void exitRecordTy(TigerParser.RecordTyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#typeFields}.
	 * @param ctx the parse tree
	 */
	void enterTypeFields(TigerParser.TypeFieldsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#typeFields}.
	 * @param ctx the parse tree
	 */
	void exitTypeFields(TigerParser.TypeFieldsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TigerParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterOperator(TigerParser.OperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link TigerParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitOperator(TigerParser.OperatorContext ctx);
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