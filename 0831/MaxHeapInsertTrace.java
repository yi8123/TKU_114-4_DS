import java.util.Arrays;

public class MaxHeapInsertTrace {
    private int[] heap;
    private int size;

    public MaxHeapInsertTrace(int capacity) {
        this.heap = new int[capacity];
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

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index] > heap[parent]) {
                int temp = heap[index];
                heap[index] = heap[parent];
                heap[parent] = temp;
                index = parent;
            } else {
                break;
            }
        }
    }

    public int peekMax() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        return heap[0];
    }

    public int[] snapshot() {
        return Arrays.copyOf(heap, size);
    }

    public static void main(String[] args) {
        int[] input = {25, 40, 10, 50, 30, 50};
        MaxHeapInsertTrace maxHeap = new MaxHeapInsertTrace(input.length);

        for (int val : input) {
            maxHeap.add(val);
            System.out.println("Insert " + val + " -> " + Arrays.toString(maxHeap.snapshot()));
        }

        System.out.println("Final Root (Max): " + maxHeap.peekMax());
    }
}