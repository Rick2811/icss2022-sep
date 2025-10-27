package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.implementaties.HanStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.*;

public class Checker {

    // ------------------------------
    // Variabelen en setup
    // ------------------------------

    // Hier maak ik een stack aan die bijhoudt in welke "scope" ik zit.
    // Elke scope bevat een lijst van variabelen met hun type (zoals PIXEL, COLOR, SCALAR, enz.)
    // Als ik bijvoorbeeld in een if-statement zit, komt daar een nieuwe scope bovenop.
    private HanStack<HashMap<String, ExpressionType>> variableTypes;


    // Startpunt van de checker. Deze methode wordt aangeroepen door de pipeline.
    // Hier begin ik met een lege stack en een globale scope (de bovenste laag).
    public void check(AST ast) {
        variableTypes = new HanStack<>();
        variableTypes.push(new HashMap<>()); // globale scope
        checkNode(ast.root); // begin met het controleren van de root van de AST
    }

    // ------------------------------
    // Algemeen node-checksysteem
    // ------------------------------
    // Deze methode bepaalt welk type node er wordt gecheckt.
    // Afhankelijk van het type (zoals Declaration of Operation) wordt de juiste methode aangeroepen.
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
            // Als het een node is die niet direct herkend wordt,
            // worden alle kinderen van die node gecontroleerd.
            for (ASTNode child : node.getChildren()) {
                checkNode(child);
            }
        }
    }

    // ------------------------------
    // Specifieke node-checks
    // ------------------------------

    // Controleert een hele stylesheet (het hoogste niveau).
    // Gaat gewoon elk kind langs (zoals regels, variabelen, etc.).
    private void checkStylesheet(Stylesheet stylesheet) {
        for (ASTNode child : stylesheet.getChildren()) {
            checkNode(child);
        }
    }

    // Controle van een variabele toewijzing, zoals:
    // MyWidth := 100px;
    // Hierbij wordt het type opgeslagen in de huidige scope.
    private void checkVariableAssignment(VariableAssignment assignment) {
        if (assignment.expression == null || assignment.name == null) return;

        Expression expr = assignment.expression;
        ExpressionType exprType = getExpressionType(expr);

        // Variabele opslaan in de huidige scope (bovenste laag van de stack)
        assert variableTypes.peek() != null;
        variableTypes.peek().put(assignment.name.name, exprType);

        // Controleer ook de waarde zelf (voor eventuele fouten in berekeningen)
        checkNode(expr);
    }

    // Controleert of een variabele die gebruikt wordt ook bestaat.
    // Zo niet, dan geven we een foutmelding terug.
    private void checkVariableReference(VariableReference reference) {
        ExpressionType type = resolveVariableType(reference.name);
        if (type == ExpressionType.UNDEFINED) {
            reference.setError("Variable " + reference.name + " is not defined in this scope.");
        }
    }

    // Controleert berekeningen (zoals optellen, aftrekken of vermenigvuldigen)
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

        // ----------------------
        // Controle: optellen of aftrekken
        // ----------------------
        // Alleen toegestaan als beide types hetzelfde zijn
        if (operation instanceof AddOperation || operation instanceof SubtractOperation) {
            if (leftType != rightType) {
                operation.setError("Addition/Subtraction requires operands of the same type. Found: "
                        + leftType + " and " + rightType);
            }
        }

        // ----------------------
        // Controle: vermenigvuldigen
        // ----------------------
        // Alleen toegestaan als één van beide kanten een SCALAR is.
        if (operation instanceof MultiplyOperation) {
            boolean scalarSide = leftType == ExpressionType.SCALAR || rightType == ExpressionType.SCALAR;
            if (!scalarSide) {
                operation.setError("Multiplication must involve a SCALAR.");
            }
        }

        // Controleer ook de linker- en rechterzijde van de operatie
        checkNode(left);
        checkNode(right);
    }

    // Controleert of een declaratie een geldig type heeft voor het gebruikte CSS-eigenschap.
    private void checkDeclaration(Declaration decl) {
        if (decl.expression == null) {
            decl.setError("Declaration missing expression value.");
            return;
        }

        Expression expr = decl.expression;
        ExpressionType exprType = getExpressionType(expr);
        String propertyName = decl.property != null ? decl.property.name : "unknown";

        // width en height moeten pixels of percentages zijn
        if (propertyName.equals("width") || propertyName.equals("height")) {
            if (exprType != ExpressionType.PIXEL && exprType != ExpressionType.PERCENTAGE) {
                decl.setError("Property '" + propertyName + "' must be a pixel or percentage value.");
            }
        }

        // color en background-color moeten een kleurtype zijn
        if (propertyName.equals("color") || propertyName.equals("background-color")) {
            if (exprType != ExpressionType.COLOR) {
                decl.setError("Property '" + propertyName + "' must be a color value.");
            }
        }

        // Controleer ook de expressie binnen deze declaratie
        checkNode(expr);
    }

    // Controle voor if-statements:
    // bij een if-statement komt een nieuwe scope op de stack,
    // zodat variabelen binnen de if hun eigen context hebben.
    private void checkIfClause(IfClause clause) {
        variableTypes.push(new HashMap<>());
        for (ASTNode child : clause.getChildren()) {
            checkNode(child);
        }
        variableTypes.pop();
    }

    // Zelfde principe als bij if, maar dan voor else-blokken.
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

    // Bepaalt het type (zoals PIXEL, COLOR, BOOL, etc.) van een expressie.
    // Deze methode is belangrijk voor type checking van variabelen en operaties.
    private ExpressionType getExpressionType(Expression expr) {
        if (expr instanceof ColorLiteral) return ExpressionType.COLOR;
        if (expr instanceof PixelLiteral) return ExpressionType.PIXEL;
        if (expr instanceof PercentageLiteral) return ExpressionType.PERCENTAGE;
        if (expr instanceof ScalarLiteral) return ExpressionType.SCALAR;
        if (expr instanceof BoolLiteral) return ExpressionType.BOOL;
        if (expr instanceof VariableReference)
            return resolveVariableType(((VariableReference) expr).name);

        // Bij operaties wordt gekeken naar het type van de linker- en rechteroperand
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

        // Als niks past, dan is het type onbekend (undefined)
        return ExpressionType.UNDEFINED;
    }

    // Zoekt in alle scopes (van binnen naar buiten) of een variabele al is gedefinieerd.
    // Als hij hem vindt, geeft hij het type terug, anders UNDEFINED.
    private ExpressionType resolveVariableType(String name) {
        for (HashMap<String, ExpressionType> scope : variableTypes) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return ExpressionType.UNDEFINED;
    }
}
