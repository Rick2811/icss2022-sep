package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.implementaties.HANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.transforms.Transform;

import java.util.HashMap;

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;

    public Evaluator(IHANLinkedList<HashMap<String, Literal>> linkedListImpl) {
        // we krijgen hier een lege implementatie van IHANLinkedList binnen
        this.variableValues = linkedListImpl;
    }

    @Override
    public void apply(AST ast) {
        variableValues.clear();
        variableValues.addFirst(new HashMap<>()); // globale scope
        evaluateNode(ast.root);
    }
    public Evaluator() {
        this.variableValues = new HANLinkedList<>();
    }


    private void evaluateNode(ASTNode node) {
        if (node == null) return;

        if (node instanceof Stylesheet) {
            for (ASTNode child : node.getChildren()) {
                evaluateNode(child);
            }

        } else if (node instanceof VariableAssignment) {
            evaluateVariableAssignment((VariableAssignment) node);

        } else if (node instanceof Stylerule) {
            variableValues.addFirst(new HashMap<>()); // nieuwe scope
            for (ASTNode child : node.getChildren()) {
                evaluateNode(child);
            }
            variableValues.removeFirst();

        } else if (node instanceof Declaration) {
            evaluateDeclaration((Declaration) node);

        } else if (node instanceof IfClause) {
            evaluateIfClause((IfClause) node);

        } else {
            for (ASTNode child : node.getChildren()) {
                evaluateNode(child);
            }
        }
    }

    // --------------------------
    // Variabelen
    // --------------------------
    private void evaluateVariableAssignment(VariableAssignment assignment) {
        Literal value = evaluateExpression(assignment.expression);
        // sla op in huidige scope
        variableValues.getFirst().put(assignment.name.name, value);
    }

    private Literal resolveVariable(String name) {
        for (int i = 0; i < variableValues.getSize(); i++) {
            HashMap<String, Literal> scope = variableValues.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null;
    }

    // --------------------------
    // Declaraties
    // --------------------------
    private void evaluateDeclaration(Declaration declaration) {
        Literal literalValue = evaluateExpression(declaration.expression);
        declaration.expression = literalValue; // vervang expressie door berekend resultaat
    }

    // --------------------------
    // If-Else
    // --------------------------
    private void evaluateIfClause(IfClause clause) {
        Literal condition = evaluateExpression(clause.conditionalExpression);

        boolean conditionValue = false;
        if (condition instanceof BoolLiteral) {
            conditionValue = ((BoolLiteral) condition).value;
        }

        if (conditionValue) {
            // alleen het if-gedeelte behouden
            clause.elseClause = null;
            for (ASTNode child : clause.body) {
                evaluateNode(child);
            }
        } else if (clause.elseClause != null) {
            // vervang de body van de if door de else
            clause.body = clause.elseClause.body;
            clause.elseClause = null;
            for (ASTNode child : clause.body) {
                evaluateNode(child);
            }
        } else {
            // condition false en geen else -> body verwijderen
            clause.body.clear();
        }
    }

    // --------------------------
    // Expressies
    // --------------------------
    private Literal evaluateExpression(Expression expr) {
        if (expr instanceof Literal) {
            return (Literal) expr;
        }

        if (expr instanceof VariableReference) {
            Literal value = resolveVariable(((VariableReference) expr).name);
            if (value == null) {
                return new ScalarLiteral(0); // fallback om fouten te voorkomen
            }
            return value;
        }

        if (expr instanceof Operation) {
            Operation op = (Operation) expr;
            Literal left = evaluateExpression((Expression) op.lhs);
            Literal right = evaluateExpression((Expression) op.rhs);

            if (left instanceof PixelLiteral && right instanceof PixelLiteral) {
                int l = ((PixelLiteral) left).value;
                int r = ((PixelLiteral) right).value;
                return calculateOperation(op, l, r, "px");
            }

            if (left instanceof PercentageLiteral && right instanceof PercentageLiteral) {
                int l = ((PercentageLiteral) left).value;
                int r = ((PercentageLiteral) right).value;
                return calculateOperation(op, l, r, "%");
            }

            if (left instanceof ScalarLiteral && right instanceof ScalarLiteral) {
                int l = ((ScalarLiteral) left).value;
                int r = ((ScalarLiteral) right).value;
                return calculateOperation(op, l, r, "");
            }

            if (left instanceof ScalarLiteral && right instanceof PixelLiteral) {
                int l = ((ScalarLiteral) left).value;
                int r = ((PixelLiteral) right).value;
                return calculateOperation(op, l, r, "px");
            }

            if (left instanceof PixelLiteral && right instanceof ScalarLiteral) {
                int l = ((PixelLiteral) left).value;
                int r = ((ScalarLiteral) right).value;
                return calculateOperation(op, l, r, "px");
            }

            if (left instanceof ScalarLiteral && right instanceof PercentageLiteral) {
                int l = ((ScalarLiteral) left).value;
                int r = ((PercentageLiteral) right).value;
                return calculateOperation(op, l, r, "%");
            }

            if (left instanceof PercentageLiteral && right instanceof ScalarLiteral) {
                int l = ((PercentageLiteral) left).value;
                int r = ((ScalarLiteral) right).value;
                return calculateOperation(op, l, r, "%");
            }
        }

        return new ScalarLiteral(0);
    }

    private Literal calculateOperation(Operation op, int l, int r, String unit) {
        int result = 0;

        if (op instanceof AddOperation) result = l + r;
        else if (op instanceof SubtractOperation) result = l - r;
        else if (op instanceof MultiplyOperation) result = l * r;

        if (unit.equals("px")) return new PixelLiteral(result);
        if (unit.equals("%")) return new PercentageLiteral(result);
        return new ScalarLiteral(result);
    }
}
