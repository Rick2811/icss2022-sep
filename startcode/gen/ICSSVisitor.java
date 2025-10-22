// Generated from C:/Users/ricks/OneDrive/Bureaublad/Alles/SCHOOL/BioscoopKaartjes/icss2022-sep/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ICSSParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ICSSVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ICSSParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#varAssign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarAssign(ICSSParser.VarAssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#ruleset}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleset(ICSSParser.RulesetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#blockItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockItem(ICSSParser.BlockItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#ifClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfClause(ICSSParser.IfClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(ICSSParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#boolValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolValue(ICSSParser.BoolValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ColorLiteral}
	 * labeled alternative in {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColorLiteral(ICSSParser.ColorLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolLiteral}
	 * labeled alternative in {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLiteral(ICSSParser.BoolLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumericOrVarExpr}
	 * labeled alternative in {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericOrVarExpr(ICSSParser.NumericOrVarExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(ICSSParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link ICSSParser#addExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(ICSSParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Mul}
	 * labeled alternative in {@link ICSSParser#mulExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMul(ICSSParser.MulContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryMinus}
	 * labeled alternative in {@link ICSSParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinus(ICSSParser.UnaryMinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AtomExpr}
	 * labeled alternative in {@link ICSSParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpr(ICSSParser.AtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PixelLiteral}
	 * labeled alternative in {@link ICSSParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPixelLiteral(ICSSParser.PixelLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PercentLiteral}
	 * labeled alternative in {@link ICSSParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPercentLiteral(ICSSParser.PercentLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ScalarLiteral}
	 * labeled alternative in {@link ICSSParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalarLiteral(ICSSParser.ScalarLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableRef}
	 * labeled alternative in {@link ICSSParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableRef(ICSSParser.VariableRefContext ctx);
}