import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayMinHeap {
    private int[] data;
    private int size;

    public ArrayMinHeap() {
        this(8);
    }

    public ArrayMinHeap(int capacity) {
        this.data = new int[Math.max(capacity, 4)];
        this.size = 0;
    }

    public void add(int val) {
        if (size == data.length) {
            grow();
        }
        data[size] = val;
        siftUp(size);
        size++;
    }

    public int peek() {
        if (isEmpty()) throw new NoSuchElementException("Heap is empty");
        return data[0];
    }

    public int remove() {
        if (isEmpty()) throw new NoSuchElementException("Heap is empty");
        int min = data[0];
        data[0] = data[size - 1];
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return min;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int[] snapshot() {
        return Arrays.copyOf(data, size);
    }

    private void grow() {
        int newCap = data.length * 2;
        data = Arrays.copyOf(data, newCap);
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (data[index] < data[parent]) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        while (index * 2 + 1 < size) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int smallest = left;

            if (right < size && data[right] < data[left]) {
                smallest = right;
            }

            if (data[index] > data[smallest]) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    public static void main(String[] args) {
        ArrayMinHeap heap = new ArrayMinHeap(4);
        int[] testData = {48, 12, 74, 9, 33, 2, 85, 61, 19, 53, 3, 99, 14, 28, 41, 6, 88, 37, 7, 50, 1};

        System.out.println("--- Adding 21 elements (Testing Auto-grow) ---");
        for (int val : testData) {
            heap.add(val);
        }

        System.out.println("Heap Size: " + heap.size());
        System.out.println("Heap Snapshot: " + Arrays.toString(heap.snapshot()));
        System.out.println("Root (Min): " + heap.peek());

        System.out.println("\n--- Removing all elements ---");
        while (!heap.isEmpty()) {
            System.out.print(heap.remove() + " ");
        }
        System.out.println();
    }
}