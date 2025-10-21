package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.datastructures.implementaties.HanStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;

public class Checker {

    private IHANStack<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new HanStack<>();
        variableTypes.push(new HashMap<>());

        if (ast.root instanceof Stylesheet) {
            checkStylesheet((Stylesheet) ast.root);
        }
    }

    private void checkStylesheet(Stylesheet stylesheet) {
        for (ASTNode child : stylesheet.getChildren()) {
            if (child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            } else if (child instanceof Stylerule) {
                checkStylerule((Stylerule) child);
            }
        }
    }

    private void checkVariableAssignment(VariableAssignment assignment) {
        Expression value = (Expression) assignment.getChildren().get(0);
        ExpressionType type = determineType(value);
        variableTypes.peek().put(assignment.name.name, type);
    }

    private void checkStylerule(Stylerule rule) {
        variableTypes.push(new HashMap<>());

        for (ASTNode child : rule.body) {
            if (child instanceof Declaration) {
                checkDeclaration((Declaration) child);
            } else if (child instanceof IfClause) {
                checkIfClause((IfClause) child);
            } else if (child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            }
        }

        variableTypes.pop();
    }

    private void checkDeclaration(Declaration declaration) {
        Expression expr = (Expression) declaration.getChildren().get(0);
        ExpressionType type = determineType(expr);

        String property = declaration.property.name;

        if (property.equals("width") || property.equals("height") || property.equals("padding")) {
            if (!(type == ExpressionType.PIXEL || type == ExpressionType.SCALAR)) {
                declaration.setError("Property '" + property + "' expects a pixel or scalar, but got " + type);
            }
        }

        if (property.equals("color") || property.equals("background-color")) {
            if (type != ExpressionType.COLOR) {
                declaration.setError("Property '" + property + "' expects a color, but got " + type);
            }
        }
    }

    private void checkIfClause(IfClause clause) {
        Expression cond = clause.conditionalExpression;
        ExpressionType condType = determineType(cond);

        if (condType != ExpressionType.BOOL) {
            clause.setError("If condition must be boolean, but got " + condType);
        }

        variableTypes.push(new HashMap<>());

        for (ASTNode child : clause.getChildren()) {
            if (child instanceof Declaration) {
                checkDeclaration((Declaration) child);
            } else if (child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            } else if (child instanceof IfClause) {
                checkIfClause((IfClause) child);
            }
        }

        variableTypes.pop();
    }

    private ExpressionType determineType(Expression expr) {
        if (expr instanceof ColorLiteral) return ExpressionType.COLOR;
        if (expr instanceof PixelLiteral) return ExpressionType.PIXEL;
        if (expr instanceof PercentageLiteral) return ExpressionType.PERCENTAGE;
        if (expr instanceof ScalarLiteral) return ExpressionType.SCALAR;
        if (expr instanceof BoolLiteral) return ExpressionType.BOOL;

        if (expr instanceof VariableReference) {
            String name = ((VariableReference) expr).name;

            // Zoek variabele in stack
            HanStack<HashMap<String, ExpressionType>> tmpStack = (HanStack<HashMap<String, ExpressionType>>) variableTypes;
            for (int i = tmpStack.size() - 1; i >= 0; i--) {
                HashMap<String, ExpressionType> scope = tmpStack.get(i);
                if (scope.containsKey(name)) {
                    return scope.get(name);
                }
            }

            // Niet gevonden → foutmelding
            expr.setError("Undefined variable: " + name);
            return ExpressionType.UNDEFINED;
        }

        if (expr instanceof Operation) {
            Operation op = (Operation) expr;
            ExpressionType left = determineType(op.lhs);
            ExpressionType right = determineType(op.rhs);

            if (left == ExpressionType.COLOR || right == ExpressionType.COLOR) {
                expr.setError("Cannot operate on color values");
                return ExpressionType.UNDEFINED;
            }

            if (left == right) return left;
            if ((left == ExpressionType.PIXEL && right == ExpressionType.SCALAR)
                    || (left == ExpressionType.SCALAR && right == ExpressionType.PIXEL))
                return ExpressionType.PIXEL;

            return ExpressionType.UNDEFINED;
        }

        return ExpressionType.UNDEFINED;
    }
}

