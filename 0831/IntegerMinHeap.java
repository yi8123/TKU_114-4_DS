import java.util.Arrays;
import java.util.NoSuchElementException;

public class IntegerMinHeap {
    private int[] heap;
    private int size;

    public IntegerMinHeap() {
        this(10);
    }

    public IntegerMinHeap(int capacity) {
        this.heap = new int[Math.max(capacity, 10)];
        this.size = 0;
    }

    public void add(int val) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
        heap[size] = val;
        siftUp(size);
        size++;
    }

    public int peek() {
        if (isEmpty()) throw new NoSuchElementException("Heap is empty");
        return heap[0];
    }

    public int removeMin() {
        if (isEmpty()) throw new NoSuchElementException("Heap is empty");
        int min = heap[0];
        heap[0] = heap[size - 1];
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

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index] < heap[parent]) {
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

            if (right < size && heap[right] < heap[left]) {
                smallest = right;
            }

            if (heap[index] > heap[smallest]) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public static void main(String[] args) {
        IntegerMinHeap minHeap = new IntegerMinHeap();
        int[] data = {45, 12, 85, 32, 89, 39, 69, 22};
        for (int x : data) minHeap.add(x);

        System.out.print("Removed in order: ");
        int prev = Integer.MIN_VALUE;
        boolean nonDecreasing = true;

        while (!minHeap.isEmpty()) {
            int current = minHeap.removeMin();
            System.out.print(current + " ");
            if (current < prev) nonDecreasing = false;
            prev = current;
        }
        System.out.println("\nIs Non-decreasing order? " + nonDecreasing);
    }
}