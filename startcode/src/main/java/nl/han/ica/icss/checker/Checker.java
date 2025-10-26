package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.implementaties.HanStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.*;

public class Checker {

    // Stack van scopes (voor variabelen)

    private HanStack<HashMap<String, ExpressionType>> variableTypes;


    public void check(AST ast) {
        variableTypes = new HanStack<>();
        variableTypes.push(new HashMap<>()); // globale scope

        checkNode(ast.root);
    }

    // ------------------------------
    // Algemeen node-checksysteem
    // ------------------------------
    private void checkNode(ASTNode node) {
        if (node == null) return;

        if (node instanceof Stylesheet) {
            checkStylesheet((Stylesheet) node);

        } else if (node instanceof VariableAssignment) {
            checkVariableAssignment((VariableAssignment) node);

        } else if (node instanceof VariableReference) {
            checkVariableReference((VariableReference) node);

        } else if (node instanceof Declaration) {
            checkDeclaration((Declaration) node);

        } else if (node instanceof Operation) {
            checkOperation((Operation) node);

        } else if (node instanceof IfClause) {
            checkIfClause((IfClause) node);

        } else if (node instanceof ElseClause) {
            checkElseClause((ElseClause) node);

        } else {
            for (ASTNode child : node.getChildren()) {
                checkNode(child);
            }
        }
    }

    // ------------------------------
    // Specifieke node-checks
    // ------------------------------

    private void checkStylesheet(Stylesheet stylesheet) {
        for (ASTNode child : stylesheet.getChildren()) {
            checkNode(child);
        }
    }

    private void checkVariableAssignment(VariableAssignment assignment) {
        if (assignment.expression == null || assignment.name == null) return;

        Expression expr = assignment.expression;
        ExpressionType exprType = getExpressionType(expr);

        // sla variabele op in huidige scope
        assert variableTypes.peek() != null;
        variableTypes.peek().put(assignment.name.name, exprType);

        // check de waarde zelf
        checkNode(expr);
    }

    private void checkVariableReference(VariableReference reference) {
        ExpressionType type = resolveVariableType(reference.name);
        if (type == ExpressionType.UNDEFINED) {
            reference.setError("Variable " + reference.name + " is not defined in this scope.");
        }
    }

    private void checkOperation(Operation operation) {
        List<Expression> exprChildren = new ArrayList<>();
        for (ASTNode child : operation.getChildren()) {
            if (child instanceof Expression) {
                exprChildren.add((Expression) child);
            }
        }

        if (exprChildren.size() < 2) {
            operation.setError("Operation must have two operands.");
            return;
        }

        Expression left = exprChildren.get(0);
        Expression right = exprChildren.get(1);

        ExpressionType leftType = getExpressionType(left);
        ExpressionType rightType = getExpressionType(right);

        // Addition / subtraction rules
        if (operation instanceof AddOperation || operation instanceof SubtractOperation) {
            if (leftType != rightType) {
                operation.setError("Addition/Subtraction requires operands of the same type. Found: "
                        + leftType + " and " + rightType);
            }
        }

        // Multiplication rules
        if (operation instanceof MultiplyOperation) {
            boolean scalarSide = leftType == ExpressionType.SCALAR || rightType == ExpressionType.SCALAR;
            if (!scalarSide) {
                operation.setError("Multiplication must involve a SCALAR.");
            }
        }

        checkNode(left);
        checkNode(right);
    }

    private void checkDeclaration(Declaration decl) {
        if (decl.expression == null) {
            decl.setError("Declaration missing expression value.");
            return;
        }

        Expression expr = decl.expression;
        ExpressionType exprType = getExpressionType(expr);
        String propertyName = decl.property != null ? decl.property.name : "unknown";

        // Width/height type checks
        if (propertyName.equals("width") || propertyName.equals("height")) {
            if (exprType != ExpressionType.PIXEL && exprType != ExpressionType.PERCENTAGE) {
                decl.setError("Property '" + propertyName + "' must be a pixel or percentage value.");
            }
        }

        // Color checks
        if (propertyName.equals("color") || propertyName.equals("background-color")) {
            if (exprType != ExpressionType.COLOR) {
                decl.setError("Property '" + propertyName + "' must be a color value.");
            }
        }

        checkNode(expr);
    }

    private void checkIfClause(IfClause clause) {
        variableTypes.push(new HashMap<>());
        for (ASTNode child : clause.getChildren()) {
            checkNode(child);
        }
        variableTypes.pop();
    }

    private void checkElseClause(ElseClause clause) {
        variableTypes.push(new HashMap<>());
        for (ASTNode child : clause.getChildren()) {
            checkNode(child);
        }
        variableTypes.pop();
    }

    // ------------------------------
    // Type-hulpmethoden
    // ------------------------------

    private ExpressionType getExpressionType(Expression expr) {
        if (expr instanceof ColorLiteral) return ExpressionType.COLOR;
        if (expr instanceof PixelLiteral) return ExpressionType.PIXEL;
        if (expr instanceof PercentageLiteral) return ExpressionType.PERCENTAGE;
        if (expr instanceof ScalarLiteral) return ExpressionType.SCALAR;
        if (expr instanceof BoolLiteral) return ExpressionType.BOOL;
        if (expr instanceof VariableReference)
            return resolveVariableType(((VariableReference) expr).name);

        if (expr instanceof Operation) {
            Operation op = (Operation) expr;
            List<Expression> exprChildren = new ArrayList<>();
            for (ASTNode child : op.getChildren()) {
                if (child instanceof Expression) exprChildren.add((Expression) child);
            }

            if (exprChildren.size() < 2) return ExpressionType.UNDEFINED;
            Expression left = exprChildren.get(0);
            Expression right = exprChildren.get(1);
            ExpressionType leftType = getExpressionType(left);
            ExpressionType rightType = getExpressionType(right);

            if (op instanceof AddOperation || op instanceof SubtractOperation) return leftType;
            if (op instanceof MultiplyOperation) {
                if (leftType == ExpressionType.SCALAR) return rightType;
                if (rightType == ExpressionType.SCALAR) return leftType;
            }
        }

        return ExpressionType.UNDEFINED;
    }

    private ExpressionType resolveVariableType(String name) {
        for (HashMap<String, ExpressionType> scope : variableTypes) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return ExpressionType.UNDEFINED;
    }
}
