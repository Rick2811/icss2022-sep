package nl.han.ica.icss.transforms;
import nl.han.ica.datastructures.implementaties.HanStack;

import nl.han.ica.datastructures.IHANLinkedList;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.transforms.Transform;
import java.util.HashMap;

public class Evaluator implements Transform {


    private HanStack<HashMap<String, Literal>> variableValues;


    @Override
    public void apply(AST ast) {
        variableValues = new HanStack<>();
        variableValues.push(new HashMap<>()); // globale scope

        evaluateNode(ast.root);
    }
    public Evaluator() {
        variableValues = new HanStack<>();
        ;
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
            variableValues.push(new HashMap<>()); // nieuwe scope
            for (ASTNode child : node.getChildren()) {
                evaluateNode(child);
            }
            variableValues.pop();

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


    private void evaluateVariableAssignment(VariableAssignment assignment) {
        Literal value = evaluateExpression(assignment.expression);
        // sla op in huidige scope
        variableValues.peek().put(assignment.name.name, value);
    }

    private Literal resolveVariable(String name) {
        for (int i = 0; i < variableValues.size(); i++) {
            HashMap<String, Literal> scope = variableValues.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null;
    }

    private void evaluateDeclaration(Declaration declaration) {
        Literal literalValue = evaluateExpression(declaration.expression);
        declaration.expression = literalValue; // vervang expressie door berekend resultaat
    }

    private void evaluateIfClause(IfClause clause) {
        Literal condition = evaluateExpression(clause.conditionalExpression);
        boolean conditionValue = false;

        if (condition instanceof BoolLiteral) {
            conditionValue = ((BoolLiteral) condition).value;
        }

        if (conditionValue) {
            // If true → verwijder else + evalueer body
            if (clause.elseClause != null) {
                clause.elseClause.body.clear();
                clause.elseClause = null;
            }
            for (ASTNode child : clause.body) {
                evaluateNode(child);
            }
        } else {
            // If false → vervang body door else-body (als die bestaat)
            if (clause.elseClause != null) {
                clause.body = clause.elseClause.body;
                clause.elseClause = null;
                for (ASTNode child : clause.body) {
                    evaluateNode(child);
                }
            } else {
                // Geen else, dus maak body leeg
                clause.body.clear();
            }
        }
    }

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
            Literal left = evaluateExpression(op.lhs);
            Literal right = evaluateExpression(op.rhs);

            // === 1. PIXEL + PIXEL ===
            if (left instanceof PixelLiteral && right instanceof PixelLiteral) {
                int l = ((PixelLiteral) left).value;
                int r = ((PixelLiteral) right).value;
                return calculateOperation(op, l, r, "px");
            }

            // === 2. PERCENTAGE + PERCENTAGE ===
            if (left instanceof PercentageLiteral && right instanceof PercentageLiteral) {
                int l = ((PercentageLiteral) left).value;
                int r = ((PercentageLiteral) right).value;
                return calculateOperation(op, l, r, "%");
            }

            // === 3. SCALAR + SCALAR ===
            if (left instanceof ScalarLiteral && right instanceof ScalarLiteral) {
                int l = ((ScalarLiteral) left).value;
                int r = ((ScalarLiteral) right).value;
                return calculateOperation(op, l, r, "");
            }

            // === 4. SCALAR ↔ PIXEL combinaties ===
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

            // === 5. SCALAR ↔ PERCENTAGE combinaties ===
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

            // === 6. GEMENGDE PIXEL ↔ PERCENTAGE combinaties ===
            if (left instanceof PixelLiteral && right instanceof PercentageLiteral) {
                int l = ((PixelLiteral) left).value;
                int r = ((PercentageLiteral) right).value;
                // Kies de *linkerunit* als dominante eenheid
                return calculateOperation(op, l, r, "px");
            }
            if (left instanceof PercentageLiteral && right instanceof PixelLiteral) {
                int l = ((PercentageLiteral) left).value;
                int r = ((PixelLiteral) right).value;
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
