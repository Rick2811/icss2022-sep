package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.datastructures.implementaties.HanStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;

public class Checker {

    // Stack van scopes met variabelen en hun types
    private IHANStack<HashMap<String, ExpressionType>> variableTypes;

    // ========= ENTRY POINT =========
    public void check(AST ast) {
        variableTypes = new HanStack<>();
        variableTypes.push(new HashMap<>()); // globale scope

        if (ast.root instanceof Stylesheet) {
            checkStylesheet((Stylesheet) ast.root);
        }
    }

    // ========= STYLESHEET =========
    private void checkStylesheet(Stylesheet stylesheet) {
        for (ASTNode child : stylesheet.getChildren()) {
            if (child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            } else if (child instanceof Stylerule) {
                checkStylerule((Stylerule) child);
            }
        }
    }

    // ========= VARIABLE ASSIGNMENT =========
    private void checkVariableAssignment(VariableAssignment assignment) {
        if (assignment.getChildren().isEmpty()) return;

        Expression value = (Expression) assignment.getChildren().get(0);
        ExpressionType type = determineType(value);

        // Sla variabele + type op in huidige scope
        variableTypes.peek().put(assignment.name.name, type);
    }

    // ========= STYLERULE =========
    private void checkStylerule(Stylerule rule) {
        variableTypes.push(new HashMap<>()); // nieuwe lokale scope

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

    // ========= DECLARATION =========
    private void checkDeclaration(Declaration declaration) {
        if (declaration.getChildren().size() < 2) return;

        // Eerste kind = PropertyName, tweede kind = waarde (Expression)
        Expression expr = (Expression) declaration.getChildren().get(1);
        ExpressionType type = determineType(expr);
        String property = declaration.property.name;

        // Width, height, padding → pixel of scalar
        if (property.equals("width") || property.equals("height") || property.equals("padding")) {
            if (!(type == ExpressionType.PIXEL || type == ExpressionType.SCALAR)) {
                declaration.setError("Property '" + property + "' expects a PIXEL or SCALAR, but got " + type);
            }
        }

        // Color, background-color → color
        if (property.equals("color") || property.equals("background-color")) {
            if (type != ExpressionType.COLOR) {
                declaration.setError("Property '" + property + "' expects a COLOR, but got " + type);
            }
        }
    }

    // ========= IF CLAUSE =========
    private void checkIfClause(IfClause clause) {
        // Controleer de conditie
        Expression cond = clause.conditionalExpression;
        ExpressionType condType = determineType(cond);

        if (condType != ExpressionType.BOOL) {
            clause.setError("If condition must be a BOOLEAN, but got " + condType);
        }

        // Nieuwe scope voor de if-body
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

        // Check ook de else-body (indien aanwezig)
        if (clause.elseClause != null) {
            variableTypes.push(new HashMap<>());
            for (ASTNode child : clause.elseClause.getChildren()) {
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
    }

    // ========= TYPE DETERMINATION =========
    private ExpressionType determineType(Expression expr) {
        if (expr == null) return ExpressionType.UNDEFINED;

        // --- LITERALS ---
        if (expr instanceof ColorLiteral) return ExpressionType.COLOR;
        if (expr instanceof PixelLiteral) return ExpressionType.PIXEL;
        if (expr instanceof PercentageLiteral) return ExpressionType.PERCENTAGE;
        if (expr instanceof ScalarLiteral) return ExpressionType.SCALAR;
        if (expr instanceof BoolLiteral) return ExpressionType.BOOL;

        // --- VARIABLE REFERENCE ---
        if (expr instanceof VariableReference) {
            String name = ((VariableReference) expr).name;

            // Zoek variabele in stack (van boven naar beneden)
            HanStack<HashMap<String, ExpressionType>> tmpStack = (HanStack<HashMap<String, ExpressionType>>) variableTypes;
            for (int i = tmpStack.size() - 1; i >= 0; i--) {
                HashMap<String, ExpressionType> scope = tmpStack.get(i);
                if (scope.containsKey(name)) {
                    return scope.get(name);
                }
            }

            // Niet gevonden
            expr.setError("Undefined variable: " + name);
            return ExpressionType.UNDEFINED;
        }

        // --- OPERATIONS (+, -, *) ---
        if (expr instanceof Operation) {
            Operation op = (Operation) expr;
            ExpressionType left = determineType(op.lhs);
            ExpressionType right = determineType(op.rhs);

            // Kleur-operaties zijn ongeldig
            if (left == ExpressionType.COLOR || right == ExpressionType.COLOR) {
                expr.setError("Cannot perform operations on COLOR values.");
                return ExpressionType.UNDEFINED;
            }

            // Bool-operaties niet toegestaan
            if (left == ExpressionType.BOOL || right == ExpressionType.BOOL) {
                expr.setError("Cannot perform operations on BOOLEAN values.");
                return ExpressionType.UNDEFINED;
            }

            // Zelfde types behouden
            if (left == right) return left;

            // Pixel * Scalar of Scalar * Pixel → Pixel
            if ((left == ExpressionType.PIXEL && right == ExpressionType.SCALAR)
                    || (left == ExpressionType.SCALAR && right == ExpressionType.PIXEL))
                return ExpressionType.PIXEL;

            // Percentage * Scalar of Scalar * Percentage → Percentage
            if ((left == ExpressionType.PERCENTAGE && right == ExpressionType.SCALAR)
                    || (left == ExpressionType.SCALAR && right == ExpressionType.PERCENTAGE))
                return ExpressionType.PERCENTAGE;

            // Anders ongeldig
            expr.setError("Incompatible types in operation: " + left + " and " + right);
            return ExpressionType.UNDEFINED;
        }

        // --- Default ---
        return ExpressionType.UNDEFINED;
    }
}
