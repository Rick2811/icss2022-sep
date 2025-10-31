package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.selectors.*;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.ArrayList;
import java.util.Stack;

public class ASTListener extends ICSSBaseListener {

	// -----------------------------
	// velden die we nodig hebben
	// -----------------------------
	// dit is basically waar alles tijdelijk in wordt opgeslage
	// stylesheet = de root van de AST (dus het hele bestand)
	// stack = houd bij waar we zitten (in een if ofzo)
	// values = tijdelijk opslaan van waardes tijdens het parsen
	private Stylesheet stylesheet;
	private final Stack<ASTNode> stack = new Stack<>();
	private final ParseTreeProperty<ASTNode> values = new ParseTreeProperty<>();

	// -----------------------------
	// resultaat ophalen
	// -----------------------------
	// als ANTLR klaar is met parsen dan kunnen we met deze functie de AST terug krijgen
	public AST getAST() {
		AST ast = new AST();
		if (stylesheet != null) {
			ast.setRoot(stylesheet);
		}
		return ast;
	}

	// -----------------------------
	// Stylesheet
	// -----------------------------
	// begin van het document -> maak nieuwe stylesheet
	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		stylesheet = new Stylesheet();
		stack.push(stylesheet);
	}

	// einde van document -> 1 stap omhoog
	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		stack.pop();
	}

	// -----------------------------
	// Ruleset (zoals p { ... } of #id { ... })
	// -----------------------------
	@Override
	public void enterRuleset(ICSSParser.RulesetContext ctx) {
		Stylerule rule = new Stylerule();

		// check welke soort selector het is
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

	// als de ruleset klaar is -> voeg toe aan parent
	@Override
	public void exitRuleset(ICSSParser.RulesetContext ctx) {
		Stylerule rule = (Stylerule) stack.pop();
		addToParent(rule);
	}

	// -----------------------------
	// Declaration
	// -----------------------------
	// bijv. color: red; of width: 50px;
	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		Declaration decl = new Declaration();
		decl.property = new PropertyName(ctx.LOWER_IDENT().getText());
		ASTNode valueNode = getChildValue(ctx.value());
		if (valueNode != null) decl.addChild(valueNode);
		addToParent(decl);
	}

	// -----------------------------
	// Variable assignment
	// -----------------------------
	// bijv. LinkColor := #ff0000;
	@Override
	public void exitVarAssign(ICSSParser.VarAssignContext ctx) {
		VariableAssignment varAssign = new VariableAssignment();

		// links = naam vd variabele
		varAssign.name = new VariableReference(ctx.CAPITAL_IDENT().getText());

		// rechts = de value
		ICSSParser.ValueContext valueCtx = ctx.value();

		if (valueCtx instanceof ICSSParser.ColorLiteralContext) {
			varAssign.expression = new ColorLiteral(valueCtx.getText());
		} else if (valueCtx instanceof ICSSParser.BoolLiteralContext) {
			String text = valueCtx.getText().toLowerCase();
			varAssign.expression = new BoolLiteral(text.equals("true"));
		} else if (valueCtx instanceof ICSSParser.NumericOrVarExprContext) {
			// kan ook een berekening zijn
			varAssign.expression = (Expression) extractExpr(((ICSSParser.NumericOrVarExprContext) valueCtx).expr());
		}

		addToParent(varAssign);
	}

	// -----------------------------
	// expressie parser
	// -----------------------------
	// checkt of iets een pixel of variable of scalar is enz
	private ASTNode extractExpr(ICSSParser.ExprContext ctx) {
		if (ctx instanceof ICSSParser.AtomExprContext) {
			ICSSParser.AtomContext atom = ((ICSSParser.AtomExprContext) ctx).atom();

			if (atom instanceof ICSSParser.PixelLiteralContext) {
				return new PixelLiteral(atom.getText());
			} else if (atom instanceof ICSSParser.PercentLiteralContext) {
				return new PercentageLiteral(atom.getText());
			} else if (atom instanceof ICSSParser.ScalarLiteralContext) {
				return new ScalarLiteral(atom.getText());
			} else if (atom instanceof ICSSParser.VariableRefContext) {
				return new VariableReference(atom.getText());
			}
		}
		// fallback (voor als het iets raars is)
		return new VariableReference(ctx.getText());
	}

	// -----------------------------
	// If/Else
	// -----------------------------
	// bouwt de if/else structuur op in de AST
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

	// -----------------------------
	// Condition
	// -----------------------------
	// if[ ... ] -> haalt de waarde tussen [] eruit
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

	// -----------------------------
	// Literals
	// -----------------------------
	// zet textuele waardes om naar objecten (zoals ColorLiteral of PixelLiteral)
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
		storeValue(ctx, new VariableReference(ctx.CAPITAL_IDENT().getText()));
	}

	// -----------------------------
	// Rekeningedoe (expressies +, -, *)
	// -----------------------------
	// maakt nodes voor berekeningen
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
		// negatief getal maken, basically 0 - x
		Operation op = new SubtractOperation();
		op.lhs = new ScalarLiteral(0);
		op.rhs = (Expression) getChildValue(ctx.expr());
		storeValue(ctx, op);
	}

	// -----------------------------
	// Helpers
	// -----------------------------
	// kleine hulp functies om waardes te bewaren en nodes te koppelen
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
