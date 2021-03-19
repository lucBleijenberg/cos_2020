// Generated from C:/Users/lsfb1/Documents/S/year_2/quarter_3/compiler/29/src/custom_language\LeftRightLanguage.g4 by ANTLR 4.8
package custom_language;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LeftRightLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LeftRightLanguageVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(LeftRightLanguageParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod(LeftRightLanguageParser.MethodContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(LeftRightLanguageParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#methodCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCall(LeftRightLanguageParser.MethodCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(LeftRightLanguageParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(LeftRightLanguageParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(LeftRightLanguageParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(LeftRightLanguageParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(LeftRightLanguageParser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(LeftRightLanguageParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#when}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhen(LeftRightLanguageParser.WhenContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#whenClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenClause(LeftRightLanguageParser.WhenClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(LeftRightLanguageParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intLiteral}
	 * labeled alternative in {@link LeftRightLanguageParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiteral(LeftRightLanguageParser.IntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code floatLiteral}
	 * labeled alternative in {@link LeftRightLanguageParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(LeftRightLanguageParser.FloatLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code textLiteral}
	 * labeled alternative in {@link LeftRightLanguageParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextLiteral(LeftRightLanguageParser.TextLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code booleanLiteral}
	 * labeled alternative in {@link LeftRightLanguageParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(LeftRightLanguageParser.BooleanLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code referenceExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceExpression(LeftRightLanguageParser.ReferenceExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodExpression(LeftRightLanguageParser.MethodExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiplyExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplyExpression(LeftRightLanguageParser.MultiplyExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalExpression(LeftRightLanguageParser.LogicalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code divideExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivideExpression(LeftRightLanguageParser.DivideExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code groupExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupExpression(LeftRightLanguageParser.GroupExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code readValueExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadValueExpression(LeftRightLanguageParser.ReadValueExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpression(LeftRightLanguageParser.OrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code minExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinExpression(LeftRightLanguageParser.MinExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(LeftRightLanguageParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpression(LeftRightLanguageParser.AddExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literalExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpression(LeftRightLanguageParser.LiteralExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negateExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegateExpression(LeftRightLanguageParser.NegateExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#readExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadExpression(LeftRightLanguageParser.ReadExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#printStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStatement(LeftRightLanguageParser.PrintStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#printLineStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintLineStatement(LeftRightLanguageParser.PrintLineStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link LeftRightLanguageParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(LeftRightLanguageParser.TypeContext ctx);
}