package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.selectors.*;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.util.ArrayList;
import java.util.Stack;

public class ASTListener extends ICSSBaseListener {

	// ---------- interne velden ----------
	private Stylesheet stylesheet;
	private final Stack<ASTNode> stack = new Stack<>();
	private final ParseTreeProperty<ASTNode> values = new ParseTreeProperty<>();

	// ---------- resultaat ----------
	public AST getAST() {
		AST ast = new AST();
		if (stylesheet != null) {
			ast.setRoot(stylesheet);
		}
		return ast;
	}

	// ---------- stylesheet ----------
	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		stylesheet = new Stylesheet();
		stack.push(stylesheet);
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		stack.pop();
	}

	// ---------- ruleset ----------
	@Override
	public void enterRuleset(ICSSParser.RulesetContext ctx) {
		Stylerule rule = new Stylerule();

		String selectorText = ctx.selector().getText();
		Selector selector;
		if (selectorText.startsWith("#")) {
			selector = new IdSelector(selectorText);
		} else if (selectorText.startsWith(".")) {
			selector = new ClassSelector(selectorText);
		} else {
			selector = new TagSelector(selectorText);
		}

		rule.selectors = new ArrayList<>();
		rule.selectors.add(selector);
		stack.push(rule);
	}

	@Override
	public void exitRuleset(ICSSParser.RulesetContext ctx) {
		Stylerule rule = (Stylerule) stack.pop();
		addToParent(rule);
	}

	// ---------- declaration ----------
	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		Declaration decl = new Declaration();
		decl.property = new PropertyName(ctx.LOWER_IDENT().getText());
		ASTNode valueNode = getChildValue(ctx.value());
		if (valueNode != null) decl.addChild(valueNode);
		addToParent(decl);
	}

	// ---------- variable assignment ----------
	@Override
	public void exitVarAssign(ICSSParser.VarAssignContext ctx) {
		VariableAssignment varAssign = new VariableAssignment();
		varAssign.name = new VariableReference(ctx.CAPITAL_IDENT().getText());
		varAssign.addChild(varAssign.name);

		ASTNode valueNode = null;

		// 🔹 1. Directe kleurwaarde (#ff0000)
		if (ctx.value() instanceof ICSSParser.ColorLiteralContext) {
			ICSSParser.ColorLiteralContext colorCtx = (ICSSParser.ColorLiteralContext) ctx.value();
			valueNode = new ColorLiteral(colorCtx.COLOR().getText());
		}

		// 🔹 2. Booleans (TRUE/FALSE)
		else if (ctx.value() instanceof ICSSParser.BoolLiteralContext) {
			ICSSParser.BoolLiteralContext boolCtx = (ICSSParser.BoolLiteralContext) ctx.value();
			valueNode = new BoolLiteral(boolCtx.boolValue().TRUE() != null);
		}

		// 🔹 3. Numerieke/variabele expressies (500px, 50%, 10, LINKCOLOR)
		else if (ctx.value() instanceof ICSSParser.NumericOrVarExprContext) {
			ICSSParser.NumericOrVarExprContext exprCtx = (ICSSParser.NumericOrVarExprContext) ctx.value();

			if (exprCtx.expr() instanceof ICSSParser.AtomExprContext) {
				ICSSParser.AtomExprContext atomExpr = (ICSSParser.AtomExprContext) exprCtx.expr();
				ICSSParser.AtomContext atomCtx = atomExpr.atom();

				if (atomCtx instanceof ICSSParser.PixelLiteralContext) {
					ICSSParser.PixelLiteralContext pixelCtx = (ICSSParser.PixelLiteralContext) atomCtx;
					valueNode = new PixelLiteral(pixelCtx.PIXELSIZE().getText());
				} else if (atomCtx instanceof ICSSParser.PercentLiteralContext) {
					ICSSParser.PercentLiteralContext percentCtx = (ICSSParser.PercentLiteralContext) atomCtx;
					valueNode = new PercentageLiteral(percentCtx.PERCENTAGE().getText());
				} else if (atomCtx instanceof ICSSParser.ScalarLiteralContext) {
					ICSSParser.ScalarLiteralContext scalarCtx = (ICSSParser.ScalarLiteralContext) atomCtx;
					valueNode = new ScalarLiteral(scalarCtx.SCALAR().getText());
				} else if (atomCtx instanceof ICSSParser.VariableRefContext) {
					ICSSParser.VariableRefContext varCtx = (ICSSParser.VariableRefContext) atomCtx;
					valueNode = new VariableReference(varCtx.CAPITAL_IDENT().getText());
				}
			}
		}

		if (valueNode != null) {
			varAssign.addChild(valueNode);
		}

		addToParent(varAssign);
	}





	// ---------- if/else ----------
	@Override
	public void enterIfClause(ICSSParser.IfClauseContext ctx) {
		IfClause ifClause = new IfClause();
		stack.push(ifClause);
	}

	@Override
	public void exitIfClause(ICSSParser.IfClauseContext ctx) {
		IfClause ifClause = (IfClause) stack.pop();
		ifClause.conditionalExpression = (Expression) getChildValue(ctx.condition());
		addToParent(ifClause);
	}

	@Override
	public void enterElseClause(ICSSParser.ElseClauseContext ctx) {
		ElseClause elseClause = new ElseClause();
		if (!stack.isEmpty() && stack.peek() instanceof IfClause) {
			((IfClause) stack.peek()).elseClause = elseClause;
		}
		stack.push(elseClause);
	}

	@Override
	public void exitElseClause(ICSSParser.ElseClauseContext ctx) {
		ElseClause elseClause = (ElseClause) stack.pop();
		addToParent(elseClause);
	}

	// ---------- condition ----------
	@Override
	public void exitCondition(ICSSParser.ConditionContext ctx) {
		if (ctx.boolValue() != null) {
			storeValue(ctx, getChildValue(ctx.boolValue()));
		} else {
			storeValue(ctx, new VariableReference(ctx.CAPITAL_IDENT().getText()));
		}
	}

	@Override
	public void exitBoolValue(ICSSParser.BoolValueContext ctx) {
		storeValue(ctx, new BoolLiteral(ctx.TRUE() != null));
	}

	// ---------- literals ----------
	@Override
	public void exitColorLiteral(ICSSParser.ColorLiteralContext ctx) {
		storeValue(ctx, new ColorLiteral(ctx.COLOR().getText()));
	}

	@Override
	public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		storeValue(ctx, new PixelLiteral(ctx.PIXELSIZE().getText()));
	}

	@Override
	public void exitPercentLiteral(ICSSParser.PercentLiteralContext ctx) {
		storeValue(ctx, new PercentageLiteral(ctx.PERCENTAGE().getText()));
	}

	@Override
	public void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
		storeValue(ctx, new ScalarLiteral(ctx.SCALAR().getText()));
	}

	@Override
	public void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
		storeValue(ctx, new BoolLiteral(ctx.boolValue().TRUE() != null));
	}

	@Override
	public void exitVariableRef(ICSSParser.VariableRefContext ctx) {
		// Gebruik alleen CAPITAL_IDENT, anders pakt hij '#' of '.'
		storeValue(ctx, new VariableReference(ctx.CAPITAL_IDENT().getText()));
	}

	// ---------- expressions ----------
	@Override
	public void exitNumericOrVarExpr(ICSSParser.NumericOrVarExprContext ctx) {
		storeValue(ctx, getChildValue(ctx.expr()));
	}

	@Override
	public void exitAtomExpr(ICSSParser.AtomExprContext ctx) {
		storeValue(ctx, getChildValue(ctx.atom()));
	}

	@Override
	public void exitAddSub(ICSSParser.AddSubContext ctx) {
		Operation op = ctx.PLUS() != null ? new AddOperation() : new SubtractOperation();
		op.lhs = (Expression) getChildValue(ctx.expr(0));
		op.rhs = (Expression) getChildValue(ctx.expr(1));
		storeValue(ctx, op);
	}

	@Override
	public void exitMul(ICSSParser.MulContext ctx) {
		Operation op = new MultiplyOperation();
		op.lhs = (Expression) getChildValue(ctx.expr(0));
		op.rhs = (Expression) getChildValue(ctx.expr(1));
		storeValue(ctx, op);
	}

	@Override
	public void exitUnaryMinus(ICSSParser.UnaryMinusContext ctx) {
		Operation op = new SubtractOperation();
		op.lhs = new ScalarLiteral(0);
		op.rhs = (Expression) getChildValue(ctx.expr());
		storeValue(ctx, op);
	}

	// ---------- helpers ----------
	private void storeValue(org.antlr.v4.runtime.tree.ParseTree node, ASTNode value) {
		values.put(node, value);
	}

	private ASTNode getChildValue(org.antlr.v4.runtime.tree.ParseTree node) {
		return values.get(node);
	}

	private void addToParent(ASTNode node) {
		if (!stack.isEmpty()) {
			stack.peek().addChild(node);
		} else if (stylesheet != null) {
			stylesheet.addChild(node);
		}
	}
}
