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

    // ------------------------------
    // Variabelen
    // ------------------------------

    // Hier maak ik een HanStack aan waarin ik alle variabelen met hun waardes bijhoud.
    // Elke scope (zoals binnen een if-statement of selector) heeft zijn eigen HashMap.
    // Dit betekent dat ik variabelen lokaal kan opslaan en terugvinden.
    private HanStack<HashMap<String, Literal>> variableValues;

    // ------------------------------
    // Startpunt (wordt aangeroepen door de pipeline)
    // ------------------------------

    // Deze methode start het evaluatieproces.
    // Eerst maak ik een lege stack met een globale scope.
    // Daarna loop ik recursief door de hele AST heen.
    @Override
    public void apply(AST ast) {
        variableValues = new HanStack<>();
        variableValues.push(new HashMap<>()); // globale scope (buitenste niveau)

        evaluateNode(ast.root); // begin met de root van de AST
    }

    // Extra constructor (niet per se nodig, maar handig als hij zonder parameters wordt aangemaakt)
    public Evaluator() {
        variableValues = new HanStack<>();
    }

    // ------------------------------
    // Hoofdcontrole voor nodes
    // ------------------------------

    // Deze methode bepaalt welk type node ik moet evalueren (zoals variabele, if, declaration, etc.).

    private void evaluateNode(ASTNode node) {
        if (node == null) return;

        // Stylesheet: gewoon door alle kinderen heen lopen
        if (node instanceof Stylesheet) {
            for (ASTNode child : node.getChildren()) {
                evaluateNode(child);
            }

            // Variabele toewijzing (zoals: Width := 200px;)
        } else if (node instanceof VariableAssignment) {
            evaluateVariableAssignment((VariableAssignment) node);

            // Stylerule (zoals p { ... }) krijgt een eigen scope
        } else if (node instanceof Stylerule) {
            variableValues.push(new HashMap<>()); // nieuwe scope maken
            for (ASTNode child : node.getChildren()) {
                evaluateNode(child);
            }
            variableValues.pop(); // scope verwijderen na de regel

            // Eigenschap in een selector (zoals width: 100px;)
        } else if (node instanceof Declaration) {
            evaluateDeclaration((Declaration) node);

            // If-statement
        } else if (node instanceof IfClause) {
            evaluateIfClause((IfClause) node);

            // Alles wat niet specifiek herkend wordt
        } else {
            for (ASTNode child : node.getChildren()) {
                evaluateNode(child);
            }
        }
    }

    // ------------------------------
    // Variabelen
    // ------------------------------

    // Deze methode zorgt ervoor dat een variabele een waarde krijgt.
    // Bijvoorbeeld: Width := 20px;
    private void evaluateVariableAssignment(VariableAssignment assignment) {
        Literal value = evaluateExpression(assignment.expression);
        // sla op in de huidige (bovenste) scope
        variableValues.peek().put(assignment.name.name, value);
    }

    // Deze functie zoekt de waarde van een variabele op in de stack van scopes.
    private Literal resolveVariable(String name) {
        for (int i = 0; i < variableValues.size(); i++) {
            HashMap<String, Literal> scope = variableValues.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null; // niet gevonden → null teruggeven
    }

    // ------------------------------
    // Declaraties (zoals width: 100px;)
    // ------------------------------

    // Hier wordt de waarde van een declaratie berekend (bijv. width: 10px + 5px;)
    private void evaluateDeclaration(Declaration declaration) {
        Literal literalValue = evaluateExpression(declaration.expression);
        // vervang de expressie door het resultaat (zodat er later direct een waarde staat)
        declaration.expression = literalValue;
    }

    // ------------------------------
    // If / Else afhandeling
    // ------------------------------

    // Deze methode bepaalt of de if-body of de else-body wordt uitgevoerd.
    private void evaluateIfClause(IfClause clause) {
        Literal condition = evaluateExpression(clause.conditionalExpression);
        boolean conditionValue = false;

        // Alleen als het echt een BoolLiteral is, gebruiken we de boolean waarde
        if (condition instanceof BoolLiteral) {
            conditionValue = ((BoolLiteral) condition).value;
        }

        if (conditionValue) {
            // If is true → verwijder de else-body en evalueer de body
            if (clause.elseClause != null) {
                clause.elseClause.body.clear();
                clause.elseClause = null;
            }
            for (ASTNode child : clause.body) {
                evaluateNode(child);
            }

        } else {
            // If is false → gebruik de else-body (als die bestaat)
            if (clause.elseClause != null) {
                clause.body = clause.elseClause.body;
                clause.elseClause = null;
                for (ASTNode child : clause.body) {
                    evaluateNode(child);
                }
            } else {
                // Geen else → body leegmaken
                clause.body.clear();
            }
        }
    }

    // ------------------------------
    // Expressies (zoals 10px + 5px)
    // ------------------------------

    private Literal evaluateExpression(Expression expr) {
        // Als het al een literal is (zoals 20px of #ff0000), dan is het resultaat gewoon dat zelfde literal
        if (expr instanceof Literal) {
            return (Literal) expr;
        }

        // Als het een variabele is, zoek dan de waarde ervan op in de scope-stack
        if (expr instanceof VariableReference) {
            Literal value = resolveVariable(((VariableReference) expr).name);
            if (value == null) {
                return new ScalarLiteral(0); // fallback om fouten te voorkomen
            }
            return value;
        }

        // Als het een operatie is (zoals +, -, *), dan bereken ik het resultaat
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
            // Dit is optioneel toegestaan in deze evaluator
            if (left instanceof PixelLiteral && right instanceof PercentageLiteral) {
                int l = ((PixelLiteral) left).value;
                int r = ((PercentageLiteral) right).value;
                // Kies de linkerunit als dominante eenheid
                return calculateOperation(op, l, r, "px");
            }
            if (left instanceof PercentageLiteral && right instanceof PixelLiteral) {
                int l = ((PercentageLiteral) left).value;
                int r = ((PixelLiteral) right).value;
                return calculateOperation(op, l, r, "%");
            }
        }

        // Als niets matched → geef standaard 0 terug
        return new ScalarLiteral(0);
    }

    // ------------------------------
    // Berekeningen uitvoeren (+, -, *)
    // ------------------------------

    // Deze methode voert de eigenlijke wiskundige berekening uit.
    // Hij neemt twee integers en de operator (Add/Subtract/Multiply),
    // en maakt er het juiste Literal-object van met de juiste eenheid.
    private Literal calculateOperation(Operation op, int l, int r, String unit) {
        int result = 0;

        if (op instanceof AddOperation) result = l + r;
        else if (op instanceof SubtractOperation) result = l - r;
        else if (op instanceof MultiplyOperation) result = l * r;

        // Maak het juiste Literal-type aan afhankelijk van de unit
        if (unit.equals("px")) return new PixelLiteral(result);
        if (unit.equals("%")) return new PercentageLiteral(result);
        return new ScalarLiteral(result);
    }
}
