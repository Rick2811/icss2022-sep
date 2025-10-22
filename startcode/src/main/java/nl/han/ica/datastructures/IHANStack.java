package nl.han.ica.datastructures;

public interface IHANStack<T> {

    /**
     * Pushes a value to the top of the stack.
     * @param value Value to push
     */
    void push(T value);

    /**
     * Pops (and removes) value at the top of the stack.
     * @return Popped value
     */
    T pop();

    /**
     * Peeks at the top of the stack without removing it.
     * @return Value at the top of the stack
     */
    T peek();

    /**
     * Returns the current size of the stack.
     * @return Number of elements in the stack
     */
    int size();
}
