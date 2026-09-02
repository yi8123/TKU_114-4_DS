import java.util.Arrays;
import java.util.List;

public class HeapPropertyValidator {

    public static boolean isMinHeap(List<Integer> list) {
        if (list == null) return false;
        if (list.size() <= 1) return true;

        int n = list.size();
        for (int i = 0; i <= (n - 2) / 2; i++) {
            Integer parent = list.get(i);
            if (parent == null) return false;

            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n) {
                Integer leftChild = list.get(left);
                if (leftChild == null || parent > leftChild) return false;
            }
            if (right < n) {
                Integer rightChild = list.get(right);
                if (rightChild == null || parent > rightChild) return false;
            }
        }
        return true;
    }

    public static boolean isMaxHeap(List<Integer> list) {
        if (list == null) return false;
        if (list.size() <= 1) return true;

        int n = list.size();
        for (int i = 0; i <= (n - 2) / 2; i++) {
            Integer parent = list.get(i);
            if (parent == null) return false;

            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n) {
                Integer leftChild = list.get(left);
                if (leftChild == null || parent < leftChild) return false;
            }
            if (right < n) {
                Integer rightChild = list.get(right);
                if (rightChild == null || parent < rightChild) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("isMinHeap: " + isMinHeap(Arrays.asList(10, 15, 20, 40, 50, 30)));
        System.out.println("isMinHeap: " + isMinHeap(Arrays.asList(10, 25, 20, 15)));
        System.out.println("isMaxHeap: " + isMaxHeap(Arrays.asList(50, 40, 50, 25, 30, 10)));
        System.out.println("Null check: " + isMinHeap(null));
    }
}