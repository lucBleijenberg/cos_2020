// Generated from C:/Users/lsfb1/Documents/S/year_2/quarter_3/compiler/29/src/custom_language\LeftRightLanguage.g4 by ANTLR 4.8
package custom_language;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LeftRightLanguageParser}.
 */
public interface LeftRightLanguageListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(LeftRightLanguageParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(LeftRightLanguageParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#method}.
	 * @param ctx the parse tree
	 */
	void enterMethod(LeftRightLanguageParser.MethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#method}.
	 * @param ctx the parse tree
	 */
	void exitMethod(LeftRightLanguageParser.MethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(LeftRightLanguageParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(LeftRightLanguageParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(LeftRightLanguageParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(LeftRightLanguageParser.MethodCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(LeftRightLanguageParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(LeftRightLanguageParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(LeftRightLanguageParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(LeftRightLanguageParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(LeftRightLanguageParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(LeftRightLanguageParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(LeftRightLanguageParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(LeftRightLanguageParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(LeftRightLanguageParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(LeftRightLanguageParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(LeftRightLanguageParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(LeftRightLanguageParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#when}.
	 * @param ctx the parse tree
	 */
	void enterWhen(LeftRightLanguageParser.WhenContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#when}.
	 * @param ctx the parse tree
	 */
	void exitWhen(LeftRightLanguageParser.WhenContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#whenClause}.
	 * @param ctx the parse tree
	 */
	void enterWhenClause(LeftRightLanguageParser.WhenClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#whenClause}.
	 * @param ctx the parse tree
	 */
	void exitWhenClause(LeftRightLanguageParser.WhenClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(LeftRightLanguageParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(LeftRightLanguageParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLiteral}
	 * labeled alternative in {@link LeftRightLanguageParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterIntLiteral(LeftRightLanguageParser.IntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLiteral}
	 * labeled alternative in {@link LeftRightLanguageParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitIntLiteral(LeftRightLanguageParser.IntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code floatLiteral}
	 * labeled alternative in {@link LeftRightLanguageParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(LeftRightLanguageParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code floatLiteral}
	 * labeled alternative in {@link LeftRightLanguageParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(LeftRightLanguageParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code textLiteral}
	 * labeled alternative in {@link LeftRightLanguageParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterTextLiteral(LeftRightLanguageParser.TextLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code textLiteral}
	 * labeled alternative in {@link LeftRightLanguageParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitTextLiteral(LeftRightLanguageParser.TextLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanLiteral}
	 * labeled alternative in {@link LeftRightLanguageParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(LeftRightLanguageParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanLiteral}
	 * labeled alternative in {@link LeftRightLanguageParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(LeftRightLanguageParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code referenceExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterReferenceExpression(LeftRightLanguageParser.ReferenceExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code referenceExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitReferenceExpression(LeftRightLanguageParser.ReferenceExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code methodExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMethodExpression(LeftRightLanguageParser.MethodExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code methodExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMethodExpression(LeftRightLanguageParser.MethodExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplyExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplyExpression(LeftRightLanguageParser.MultiplyExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplyExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplyExpression(LeftRightLanguageParser.MultiplyExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalExpression(LeftRightLanguageParser.LogicalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalExpression(LeftRightLanguageParser.LogicalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code divideExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDivideExpression(LeftRightLanguageParser.DivideExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code divideExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDivideExpression(LeftRightLanguageParser.DivideExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code groupExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGroupExpression(LeftRightLanguageParser.GroupExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code groupExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGroupExpression(LeftRightLanguageParser.GroupExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code readValueExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterReadValueExpression(LeftRightLanguageParser.ReadValueExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code readValueExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitReadValueExpression(LeftRightLanguageParser.ReadValueExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(LeftRightLanguageParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(LeftRightLanguageParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code minExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMinExpression(LeftRightLanguageParser.MinExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code minExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMinExpression(LeftRightLanguageParser.MinExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(LeftRightLanguageParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(LeftRightLanguageParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddExpression(LeftRightLanguageParser.AddExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddExpression(LeftRightLanguageParser.AddExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literalExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpression(LeftRightLanguageParser.LiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literalExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpression(LeftRightLanguageParser.LiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code negateExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNegateExpression(LeftRightLanguageParser.NegateExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negateExpression}
	 * labeled alternative in {@link LeftRightLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNegateExpression(LeftRightLanguageParser.NegateExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#readExpression}.
	 * @param ctx the parse tree
	 */
	void enterReadExpression(LeftRightLanguageParser.ReadExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#readExpression}.
	 * @param ctx the parse tree
	 */
	void exitReadExpression(LeftRightLanguageParser.ReadExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(LeftRightLanguageParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(LeftRightLanguageParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#printLineStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintLineStatement(LeftRightLanguageParser.PrintLineStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#printLineStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintLineStatement(LeftRightLanguageParser.PrintLineStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeftRightLanguageParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(LeftRightLanguageParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeftRightLanguageParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(LeftRightLanguageParser.TypeContext ctx);
}