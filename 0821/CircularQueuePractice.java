import java.util.Arrays;

@SuppressWarnings("unchecked")
class CircularQueue<T> {
    private Object[] array;
    private int front = 0;
    private int rear = 0;
    private int size = 0;
    private final int capacity;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.array = new Object[capacity];
    }

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(T item) {
        if (isFull()) {
            System.out.println("Queue 滿了，無法加入: " + item);
            return;
        }
        array[rear] = item;
        rear = (rear + 1) % capacity; // modulo 循環 index
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Queue 為空，無法 dequeue");
            return null;
        }
        T item = (T) array[front];
        array[front] = null;
        front = (front + 1) % capacity; // modulo 循環 index
        size--;
        return item;
    }

    public void printState(String step) {
        System.out.printf("%-20s -> Array: %-20s | front: %d | rear: %d | size: %d\n",
                step, Arrays.toString(array), front, rear, size);
    }

    public void dequeueAllFIFO() {
        System.out.print("FIFO 順序取出剩餘元素: ");
        while (!isEmpty()) {
            System.out.print(dequeue() + " ");
        }
        System.out.println();
    }
}

public class CircularQueuePractice {
    public static void main(String[] args) {
        CircularQueue<String> queue = new CircularQueue<>(4);

        System.out.println("=== Circular Queue 操作追蹤 ===");
        queue.enqueue("A"); queue.printState("enqueue A");
        queue.enqueue("B"); queue.printState("enqueue B");
        queue.enqueue("C"); queue.printState("enqueue C");

        queue.dequeue();    queue.printState("dequeue");
        queue.dequeue();    queue.printState("dequeue");

        queue.enqueue("D"); queue.printState("enqueue D");
        queue.enqueue("E"); queue.printState("enqueue E");
        queue.enqueue("F"); queue.printState("enqueue F"); // 隊列在此時會滿

        queue.dequeue();    queue.printState("dequeue");
        queue.enqueue("G"); queue.printState("enqueue G");

        System.out.println("\n=== 清空隊列 ===");
        queue.dequeueAllFIFO();
    }
}