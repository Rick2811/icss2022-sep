// Generated from C:/Users/ricks/OneDrive/Bureaublad/Alles/SCHOOL/BioscoopKaartjes/icss2022-sep/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.13.2
package nl.han.ica.icss.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ICSSParser}.
 */
public interface ICSSListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void enterStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void exitStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ICSSParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ICSSParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#varAssign}.
	 * @param ctx the parse tree
	 */
	void enterVarAssign(ICSSParser.VarAssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#varAssign}.
	 * @param ctx the parse tree
	 */
	void exitVarAssign(ICSSParser.VarAssignContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#ruleset}.
	 * @param ctx the parse tree
	 */
	void enterRuleset(ICSSParser.RulesetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#ruleset}.
	 * @param ctx the parse tree
	 */
	void exitRuleset(ICSSParser.RulesetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ICSSParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ICSSParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#blockItem}.
	 * @param ctx the parse tree
	 */
	void enterBlockItem(ICSSParser.BlockItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#blockItem}.
	 * @param ctx the parse tree
	 */
	void exitBlockItem(ICSSParser.BlockItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#ifClause}.
	 * @param ctx the parse tree
	 */
	void enterIfClause(ICSSParser.IfClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#ifClause}.
	 * @param ctx the parse tree
	 */
	void exitIfClause(ICSSParser.IfClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void enterElseClause(ICSSParser.ElseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void exitElseClause(ICSSParser.ElseClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(ICSSParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(ICSSParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#boolValue}.
	 * @param ctx the parse tree
	 */
	void enterBoolValue(ICSSParser.BoolValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#boolValue}.
	 * @param ctx the parse tree
	 */
	void exitBoolValue(ICSSParser.BoolValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ColorLiteral}
	 * labeled alternative in {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterColorLiteral(ICSSParser.ColorLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ColorLiteral}
	 * labeled alternative in {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitColorLiteral(ICSSParser.ColorLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BoolLiteral}
	 * labeled alternative in {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BoolLiteral}
	 * labeled alternative in {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumericOrVarExpr}
	 * labeled alternative in {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterNumericOrVarExpr(ICSSParser.NumericOrVarExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumericOrVarExpr}
	 * labeled alternative in {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitNumericOrVarExpr(ICSSParser.NumericOrVarExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Mul}
	 * labeled alternative in {@link ICSSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMul(ICSSParser.MulContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Mul}
	 * labeled alternative in {@link ICSSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMul(ICSSParser.MulContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link ICSSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(ICSSParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link ICSSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(ICSSParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryMinus}
	 * labeled alternative in {@link ICSSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinus(ICSSParser.UnaryMinusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryMinus}
	 * labeled alternative in {@link ICSSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinus(ICSSParser.UnaryMinusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AtomExpr}
	 * labeled alternative in {@link ICSSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(ICSSParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AtomExpr}
	 * labeled alternative in {@link ICSSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(ICSSParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PixelLiteral}
	 * labeled alternative in {@link ICSSParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PixelLiteral}
	 * labeled alternative in {@link ICSSParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PercentLiteral}
	 * labeled alternative in {@link ICSSParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterPercentLiteral(ICSSParser.PercentLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PercentLiteral}
	 * labeled alternative in {@link ICSSParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitPercentLiteral(ICSSParser.PercentLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ScalarLiteral}
	 * labeled alternative in {@link ICSSParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterScalarLiteral(ICSSParser.ScalarLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ScalarLiteral}
	 * labeled alternative in {@link ICSSParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableRef}
	 * labeled alternative in {@link ICSSParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterVariableRef(ICSSParser.VariableRefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableRef}
	 * labeled alternative in {@link ICSSParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitVariableRef(ICSSParser.VariableRefContext ctx);
}