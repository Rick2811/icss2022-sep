package nl.han.ica.datastructures.implementaties;

import nl.han.ica.datastructures.IHANStack;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HanStack<T> implements IHANStack<T> {

    private List<T> items;

    // ✅ Juiste constructor
    public HanStack() {
        this.items = new ArrayList<>();
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

    @Override
    public int size() {
        return items.size();
    }


    public T get(int index) {
        return items.get(index);
    }

    @Override
    public String toString() {
        return "HanStack" + items.toString();
    }
}
