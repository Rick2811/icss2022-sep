package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.selectors.*;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.util.ArrayList;
import java.util.Stack;

public class ASTListener extends ICSSBaseListener {

	private Stylesheet stylesheet;
	private final Stack<ASTNode> stack = new Stack<>();
	private final ParseTreeProperty<ASTNode> values = new ParseTreeProperty<>();

	// =========================
	// RESULT
	// =========================
	public AST getAST() {
		AST ast = new AST();
		if (stylesheet != null) {
			ast.setRoot(stylesheet);
		} else {
			System.err.println("⚠️ ASTListener: Stylesheet is null (geen root).");
		}
		return ast;
	}


	// =========================
	// STRUCTURE
	// =========================

	// ---- value: expr ----
// value : expr #NumericOrVarExpr
	@Override
	public void exitNumericOrVarExpr(ICSSParser.NumericOrVarExprContext ctx) {
		// Take the computed expression from the child expr and store it on this value node
		storeValue(ctx, getChildValue(ctx.expr()));
	}

	// ---- condition (used by if) ----
// condition : boolValue | CAPITAL_IDENT
	@Override
	public void exitCondition(ICSSParser.ConditionContext ctx) {
		if (ctx.boolValue() != null) {
			// Let boolValue handler create a BoolLiteral and bubble it up
			storeValue(ctx, getChildValue(ctx.boolValue()));
		} else {
			// condition was a variable reference
			storeValue(ctx, new VariableReference(ctx.CAPITAL_IDENT().getText()));
		}
	}

	// boolValue : TRUE | FALSE
	@Override
	public void exitBoolValue(ICSSParser.BoolValueContext ctx) {
		boolean v = ctx.TRUE() != null;
		storeValue(ctx, new BoolLiteral(v));
	}

	// IMPORTANT: set condition when leaving the if
	@Override
	public void enterIfClause(ICSSParser.IfClauseContext ctx) {
		IfClause clause = new IfClause();
		stack.push(clause);
	}

	@Override
	public void exitIfClause(ICSSParser.IfClauseContext ctx) {
		IfClause clause = (IfClause) stack.pop();
		clause.conditionalExpression = (Expression) getChildValue(ctx.condition()); // now it exists
		addToParent(clause);
	}


	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		stylesheet = new Stylesheet();
		stack.push(stylesheet);
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		if (!stack.isEmpty() && stack.peek() instanceof Stylesheet) {
			stylesheet = (Stylesheet) stack.pop();
		}
	}

	// =========================
	// RULESETS (Selectors + Block)
	// =========================
	@Override
	public void enterRuleset(ICSSParser.RulesetContext ctx) {
		Stylerule rule = new Stylerule();

		String selectorText = ctx.selector().getText();
		Selector selectorNode;
		if (selectorText.startsWith("#"))
			selectorNode = new IdSelector(selectorText.substring(1));
		else if (selectorText.startsWith("."))
			selectorNode = new ClassSelector(selectorText.substring(1));
		else
			selectorNode = new TagSelector(selectorText);

		rule.selectors = new ArrayList<>();
		rule.selectors.add(selectorNode);

		stack.push(rule);
	}

	@Override
	public void exitRuleset(ICSSParser.RulesetContext ctx) {
		Stylerule rule = (Stylerule) stack.pop();
		addToParent(rule);
	}

	// =========================
	// DECLARATIONS
	// =========================
	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		Declaration decl = new Declaration();
		decl.property = new PropertyName(ctx.LOWER_IDENT().getText());
		ASTNode value = getChildValue(ctx.value());
		if (value != null) decl.addChild(value);
		addToParent(decl);
	}

	@Override
	public void exitVarAssign(ICSSParser.VarAssignContext ctx) {
		VariableAssignment a = new VariableAssignment();
		a.name = new VariableReference(ctx.CAPITAL_IDENT().getText());
		ASTNode value = getChildValue(ctx.value());
		if (value != null) a.addChild(value);
		addToParent(a);
	}



	// =========================
	// IF / ELSE CLAUSES
	// =========================



	// =========================
	// EXPRESSIONS & LITERALS
	// =========================
	@Override
	public void exitColorLiteral(ICSSParser.ColorLiteralContext ctx) {
		storeValue(ctx, new ColorLiteral(ctx.COLOR().getText()));
	}

	@Override
	public void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
		storeValue(ctx, new BoolLiteral(ctx.getText().equalsIgnoreCase("TRUE")));
	}

	@Override
	public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		storeValue(ctx, new PixelLiteral(ctx.getText()));
	}

	@Override
	public void exitPercentLiteral(ICSSParser.PercentLiteralContext ctx) {
		storeValue(ctx, new PercentageLiteral(ctx.getText()));
	}

	@Override
	public void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
		storeValue(ctx, new ScalarLiteral(ctx.getText()));
	}

	@Override
	public void exitVariableRef(ICSSParser.VariableRefContext ctx) {
		storeValue(ctx, new VariableReference(ctx.getText()));
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

	@Override
	public void exitAtomExpr(ICSSParser.AtomExprContext ctx) {
		storeValue(ctx, getChildValue(ctx.atom()));
	}

	// =========================
	// HELPERS
	// =========================
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
