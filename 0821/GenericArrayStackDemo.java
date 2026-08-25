@SuppressWarnings("unchecked")
class ArrayStack<T> {
    private Object[] data;
    private int top = -1; // 指向目前的 top 元素位置

    public ArrayStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("容量必須大於 0");
        }
        this.data = new Object[capacity];
    }

    public boolean isFull() {
        return top == data.length - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    public void push(T value) {
        if (isFull()) {
            System.out.println("Stack Overflow: 無法放入 " + value);
            return;
        }
        data[++top] = value;
    }

    public T pop() {
        if (isEmpty()) {
            System.out.println("Stack Underflow: Stack 為空");
            return null;
        }
        T value = (T) data[top];
        data[top--] = null; // 防止 Memory Leak
        return value;
    }

    public T peek() {
        if (isEmpty()) {
            System.out.println("Stack 為空，無法 peek");
            return null;
        }
        return (T) data[top];
    }
}

public class GenericArrayStackDemo {
    public static void main(String[] args) {
        System.out.println("=== 測試 ArrayStack<String> ===");
        ArrayStack<String> stringStack = new ArrayStack<>(2);
        stringStack.push("Java");
        stringStack.push("Python");
        stringStack.push("C++"); // Overflow

        System.out.println("Top: " + stringStack.peek());
        System.out.println("Pop: " + stringStack.pop());
        System.out.println("Pop: " + stringStack.pop());
        System.out.println("Pop: " + stringStack.pop()); // Underflow

        System.out.println("\n=== 測試 ArrayStack<Integer> ===");
        ArrayStack<Integer> intStack = new ArrayStack<>(3);
        intStack.push(100);
        intStack.push(200);
        System.out.println("Size: " + intStack.size());
        System.out.println("Pop: " + intStack.pop());
        System.out.println("Is Empty? " + intStack.isEmpty());
    }
}