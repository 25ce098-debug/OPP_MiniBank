import java.util.EmptyStackException;

class ArrayStack<T> {
    private final Object[] elements;
    private int top = 0;

    public ArrayStack(int capacity) {
        this.elements = new Object[capacity];
    }

    public void push(T item) {
        if (top == elements.length) {
            throw new IllegalStateException("Stack is full (capacity=" + elements.length + ")");
        }
        elements[top++] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T item = (T) elements[--top];
        elements[top] = null;
        return item;
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int size() {
        return top;
    }
}

public class GenericStackDemo {
    public static void main(String[] args) {
        System.out.println("--- 1. Reusing ArrayStack<String> ---");
        ArrayStack<String> strStack = new ArrayStack<>(5);
        strStack.push("25CE098");
        strStack.push("Bhavy Pan");
        strStack.push("CHARUSAT");
        while (!strStack.isEmpty()) {
            System.out.println("  Popped String : " + strStack.pop());
        }

        System.out.println("\n--- 2. Reusing ArrayStack<Integer> ---");
        ArrayStack<Integer> intStack = new ArrayStack<>(5);
        intStack.push(101);
        intStack.push(202);
        System.out.println("  Popped Integer: " + intStack.pop());
        System.out.println("  Popped Integer: " + intStack.pop());

        System.out.println("\n--- 3. Handling Empty Stack Case ---");
        try {
            intStack.pop();
        } catch (EmptyStackException e) {
            System.out.println("  Handled EmptyStackException sensibly: Cannot pop from an empty stack.");
        }
    }
}
