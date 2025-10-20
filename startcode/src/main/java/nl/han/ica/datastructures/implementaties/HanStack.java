package nl.han.ica.datastructures.implementaties;

import nl.han.ica.datastructures.IHANStack;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HanStack<T> implements IHANStack<T> {

    private List<T> items;

    public void HANStack() {
        this.items = new ArrayList<>();
    }

    public HanStack(List<T> items) {
        this.items = items;
    }

    @Override
    public void push(T value) {
        items.add(value);
    }

    @Override
    public T pop() {
        if (items.isEmpty()) {
            throw new NoSuchElementException("Stack is leeg, kan niet poppen!");
        }
        return items.remove(items.size() - 1);
    }

    @Override
    public T peek() {
        if (items.isEmpty()) {
            throw new NoSuchElementException("Stack is leeg, kan niet peek-en!");
        }
        return items.get(items.size() - 1);
    }

    // Extra (optioneel): handig voor debuggen
    @Override
    public String toString() {
        return "HANStack" + items.toString();
    }
}