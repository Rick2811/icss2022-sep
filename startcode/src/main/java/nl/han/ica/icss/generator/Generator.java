package nl.han.ica.icss.generator;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;

public class Generator {

	public String generate(AST ast) {
		if (ast == null || ast.root == null) {
			return "";
		}

		StringBuilder css = new StringBuilder();
		generateNode(ast.root, css, 0);
		return css.toString();
	}

	// ---------------------------
	// Recursive helper
	// ---------------------------
	private void generateNode(ASTNode node, StringBuilder css, int indentLevel) {
		String indent = "    ".repeat(indentLevel); // 4 spaties per niveau

		if (node instanceof Stylesheet) {
			for (ASTNode child : node.getChildren()) {
				generateNode(child, css, indentLevel);
			}
		}

		else if (node instanceof Stylerule) {
			Stylerule rule = (Stylerule) node;

			// selector (p, #id, .class)
			if (!rule.selectors.isEmpty()) {
				css.append(rule.selectors.get(0).toString()).append(" {\n");
			}


			// regels/declaraties binnen de selector
			for (ASTNode child : rule.body) {
				generateNode(child, css, indentLevel + 1);
			}

			css.append("}\n\n");
		}

		else if (node instanceof Declaration) {
			Declaration decl = (Declaration) node;

			css.append(indent)
					.append(decl.property.name)
					.append(": ")
					.append(expressionToString(decl.expression))
					.append(";\n");
		}

		else {
			for (ASTNode child : node.getChildren()) {
				generateNode(child, css, indentLevel);
			}
		}
	}

	// ---------------------------
	// Expression → tekst
	// ---------------------------
	private String expressionToString(Expression expr) {
		if (expr instanceof ColorLiteral) {
			return ((ColorLiteral) expr).value; // "#ffffff"
		} else if (expr instanceof PixelLiteral) {
			return ((PixelLiteral) expr).value + "px"; // "500px"
		} else if (expr instanceof PercentageLiteral) {
			return ((PercentageLiteral) expr).value + "%";
		} else if (expr instanceof ScalarLiteral) {
			return Integer.toString(((ScalarLiteral) expr).value);
		} else if (expr instanceof BoolLiteral) {
			return ((BoolLiteral) expr).value ? "true" : "false";
		} else {
			return "undefined";
		}
	}


}
