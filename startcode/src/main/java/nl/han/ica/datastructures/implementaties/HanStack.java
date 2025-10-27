package nl.han.ica.datastructures.implementaties;

import nl.han.ica.datastructures.IHANStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class HanStack<T> implements IHANStack<T>, Iterable<T> {

    // ------------------------------
    // Interne opslag
    // ------------------------------

    // Hier wordt de eigenlijke stack opgeslagen.
    // Ik gebruik een ArrayList om de data in op te slaan,
    // waarbij het laatste element in de lijst de "bovenkant" van de stack is.
    private List<T> items;

    // ------------------------------
    // Constructor
    // ------------------------------

    // Maakt een nieuwe, lege stack aan.
    public HanStack() {
        this.items = new ArrayList<>();
    }

    // ------------------------------
    // Basis stack-functionaliteit
    // ------------------------------

    // Push voegt een nieuw element toe bovenop de stack.
    @Override
    public void push(T value) {
        items.add(value);
    }

    // Pop verwijdert het bovenste element van de stack en geeft dat terug.
    // Als de stack leeg is, wordt er een foutmelding gegooid.
    @Override
    public T pop() {
        if (items.isEmpty()) {
            throw new NoSuchElementException("Stack is leeg, kan niet poppen!");
        }
        return items.remove(items.size() - 1);
    }

    // Peek kijkt alleen naar het bovenste element zonder het te verwijderen.
    @Override
    public T peek() {
        if (items.isEmpty()) {
            throw new NoSuchElementException("Stack is leeg, kan niet peek-en!");
        }
        return items.get(items.size() - 1);
    }

    // Geeft het aantal elementen in de stack terug.
    @Override
    public int size() {
        return items.size();
    }

    // Handige extra functie (niet in de interface):
    // hiermee kun je een specifiek element uit de stack opvragen.
    // Bijvoorbeeld: get(0) geeft het eerste (onderste) element.
    public T get(int index) {
        return items.get(index);
    }

    // ------------------------------
    // Extra hulpmethoden
    // ------------------------------

    // ToString maakt een nette tekstweergave van de stack,
    // handig om te debuggen of de inhoud te printen.
    @Override
    public String toString() {
        return "HanStack" + items.toString();
    }

    // Door Iterable te implementeren kun je met een foreach-lus
    // door de stack heen lopen.
    // Bijvoorbeeld:
    // for (var item : mijnStack) { ... }
    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }
}
