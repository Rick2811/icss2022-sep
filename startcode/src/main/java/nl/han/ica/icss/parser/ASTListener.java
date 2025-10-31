package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.selectors.*;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.util.ArrayList;
import java.util.Stack;

public class ASTListener extends ICSSBaseListener {

	// ------------------------------
	// Interne velden
	// ------------------------------

	// Stylesheet is de root van onze AST (Abstract Syntax Tree)
	private Stylesheet stylesheet;

	// De stack houdt bij in welke node we momenteel zitten.
	// Wanneer we een nieuwe node binnengaan, pushen we hem op de stack.
	// Wanneer we klaar zijn, poppen we hem er weer af.
	private final Stack<ASTNode> stack = new Stack<>();

	// Met values kunnen we waarden koppelen aan parser nodes (ParseTree-nodes),
	// zodat we ze later kunnen ophalen (handig bij expressies en literalen).
	private final ParseTreeProperty<ASTNode> values = new ParseTreeProperty<>();

	// =========================
	// RESULT
	// =========================
	// Deze functie geeft de uiteindelijke AST terug aan de pipeline.
	// De AST bevat de root (stylesheet) met alle child-nodes erin.
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
	// Hier worden de verschillende onderdelen van de ICSS-code omgezet
	// naar de juiste AST-structuren. Dit wordt allemaal aangeroepen door ANTLR.

	// ---- value: expr ----
	// Wordt gebruikt voor variabelen of berekeningen die een waarde hebben.
	@Override
	public void exitNumericOrVarExpr(ICSSParser.NumericOrVarExprContext ctx) {
		storeValue(ctx, getChildValue(ctx.expr()));
	}

	// ---- condition (used by if) ----
	// Een condition kan een boolean zijn (TRUE/FALSE) of een variabele.
	@Override
	public void exitCondition(ICSSParser.ConditionContext ctx) {
		if (ctx.boolValue() != null) {
			storeValue(ctx, getChildValue(ctx.boolValue()));
		} else {
			storeValue(ctx, new VariableReference(ctx.CAPITAL_IDENT().getText()));
		}
	}

	// boolValue : TRUE | FALSE
	@Override
	public void exitBoolValue(ICSSParser.BoolValueContext ctx) {
		boolean v = ctx.TRUE() != null;
		storeValue(ctx, new BoolLiteral(v));
	}

	// ------------------------------
	// IF / ELSE handling
	// ------------------------------
	// Hier wordt een if-clause aangemaakt wanneer we deze tegenkomen in de parser.
	@Override
	public void enterIfClause(ICSSParser.IfClauseContext ctx) {
		IfClause clause = new IfClause();
		stack.push(clause);
	}

	// Wanneer we de if-clause verlaten, vullen we de condition in en voegen we de node toe aan de parent.
	@Override
	public void exitIfClause(ICSSParser.IfClauseContext ctx) {
		IfClause clause = (IfClause) stack.pop();
		clause.conditionalExpression = (Expression) getChildValue(ctx.condition());

		// Voeg pas toe aan de parent als het geen onderdeel is van een else
		addToParent(clause);
	}


	@Override
	public void enterElseClause(ICSSParser.ElseClauseContext ctx) {
		// Maak een nieuwe ElseClause aan en koppel die aan de laatste IfClause
		ElseClause elseClause = new ElseClause();

		// Kijk naar het laatste if-blok dat we hebben verwerkt
		if (!stack.isEmpty() && stack.peek() instanceof IfClause) {
			((IfClause) stack.peek()).elseClause = elseClause;
		}

		// Push de elseClause op de stack om child-nodes (zoals declarations) te kunnen toevoegen
		stack.push(elseClause);
	}

	@Override
	public void exitElseClause(ICSSParser.ElseClauseContext ctx) {
		// Wanneer we uit de else komen, poppen we hem
		ElseClause elseClause = (ElseClause) stack.pop();
		addToParent(elseClause);
	}


	// ------------------------------
	// STYLESHEET (root)
	// ------------------------------

	// Wanneer we aan het begin van de stylesheet komen, maken we een nieuwe Stylesheet-node aan.
	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		stylesheet = new Stylesheet();
		stack.push(stylesheet);
	}

	// Wanneer we klaar zijn met de stylesheet, halen we hem weer van de stack af.
	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		if (!stack.isEmpty() && stack.peek() instanceof Stylesheet) {
			stylesheet = (Stylesheet) stack.pop();
		}
	}

	// =========================
	// RULESETS (Selectors + Block)
	// =========================
	// Een ruleset is iets als:
	// p { width: 100px; }
	// Hierbij maken we een nieuwe Stylerule aan met een selector.
	@Override
	public void enterRuleset(ICSSParser.RulesetContext ctx) {
		Stylerule rule = new Stylerule();

		// Bepaal wat voor soort selector het is (#id, .class of tag)
		String selectorText = ctx.selector().getText();
		Selector selectorNode;
		if (selectorText.startsWith("#"))
			selectorNode = new IdSelector(selectorText.substring(1));
		else if (selectorText.startsWith("."))
			selectorNode = new ClassSelector(selectorText.substring(1));
		else
			selectorNode = new TagSelector(selectorText);

		// Voeg de selector toe aan de lijst van selectors
		rule.selectors = new ArrayList<>();
		rule.selectors.add(selectorNode);

		// Push de stylerule op de stack
		stack.push(rule);
	}

	// Als we klaar zijn met de ruleset, voegen we deze toe aan de parent (meestal de stylesheet)
	@Override
	public void exitRuleset(ICSSParser.RulesetContext ctx) {
		Stylerule rule = (Stylerule) stack.pop();
		addToParent(rule);
	}

	// =========================
	// DECLARATIONS
	// =========================
	// Een declaratie is iets als:
	// color: #ff0000; of width: 20px;
	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		Declaration decl = new Declaration();
		decl.property = new PropertyName(ctx.LOWER_IDENT().getText());
		ASTNode value = getChildValue(ctx.value());
		if (value != null) decl.addChild(value);
		addToParent(decl);
	}

	// Variabele declaratie:
	// MyColor := #ff0000;
	@Override
	public void exitVarAssign(ICSSParser.VarAssignContext ctx) {
		VariableAssignment a = new VariableAssignment();
		a.name = new VariableReference(ctx.CAPITAL_IDENT().getText());
		ASTNode value = getChildValue(ctx.value());
		if (value != null) a.addChild(value);
		addToParent(a);
	}

	// =========================
	// EXPRESSIONS & LITERALS
	// =========================
	// Alle soorten waarden zoals kleuren, pixels, percentages en scalars.

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

	// Optellingen en aftrekkingen (zoals 10px + 5px)
	@Override
	public void exitAddSub(ICSSParser.AddSubContext ctx) {
		Operation op = ctx.PLUS() != null ? new AddOperation() : new SubtractOperation();
		op.lhs = (Expression) getChildValue(ctx.expr(0));
		op.rhs = (Expression) getChildValue(ctx.expr(1));
		storeValue(ctx, op);
	}

	// Vermenigvuldiging (zoals 10px * 2)
	@Override
	public void exitMul(ICSSParser.MulContext ctx) {
		Operation op = new MultiplyOperation();
		op.lhs = (Expression) getChildValue(ctx.expr(0));
		op.rhs = (Expression) getChildValue(ctx.expr(1));
		storeValue(ctx, op);
	}

	// Unary minus (zoals -10px)
	@Override
	public void exitUnaryMinus(ICSSParser.UnaryMinusContext ctx) {
		Operation op = new SubtractOperation();
		op.lhs = new ScalarLiteral(0);
		op.rhs = (Expression) getChildValue(ctx.expr());
		storeValue(ctx, op);
	}

	// Atomaire expressie (zoals een enkele waarde)
	@Override
	public void exitAtomExpr(ICSSParser.AtomExprContext ctx) {
		storeValue(ctx, getChildValue(ctx.atom()));
	}

	// =========================
	// HELPERS
	// =========================

	// Kleine helper om een waarde op te slaan in de ParseTreeProperty.
	private void storeValue(org.antlr.v4.runtime.tree.ParseTree node, ASTNode value) {
		values.put(node, value);
	}

	// Haalt eerder opgeslagen waarde van een child op.
	private ASTNode getChildValue(org.antlr.v4.runtime.tree.ParseTree node) {
		return values.get(node);
	}

	// Voeg een node toe aan zijn parent in de AST.
	private void addToParent(ASTNode node) {
		if (!stack.isEmpty()) {
			stack.peek().addChild(node);
		} else if (stylesheet != null) {
			stylesheet.addChild(node);
		}
	}
}
