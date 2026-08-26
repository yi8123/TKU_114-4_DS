public class RecursiveArrayStatistics {

    public static int maximum(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        return maxHelper(arr, 0);
    }
    // helper 不複製 array，只傳 index
    private static int maxHelper(int[] arr, int index) {
        if (index == arr.length - 1) return arr[index];
        int rest = maxHelper(arr, index + 1);
        return Math.max(arr[index], rest);
    }

    public static int minimum(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        return minHelper(arr, 0);
    }
    private static int minHelper(int[] arr, int index) {
        if (index == arr.length - 1) return arr[index];
        int rest = minHelper(arr, index + 1);
        return Math.min(arr[index], rest);
    }

    public static int countAbove(int[] arr, int threshold) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        return countAboveHelper(arr, 0, threshold);
    }
    private static int countAboveHelper(int[] arr, int index, int threshold) {
        if (index == arr.length) return 0;
        int rest = countAboveHelper(arr, index + 1, threshold);
        return (arr[index] > threshold ? 1 : 0) + rest;
    }

    public static void main(String[] args) {
        int[] data = {3, 9, -2, 15, 7, 0, 15};
        System.out.println("maximum: " + maximum(data));
        System.out.println("minimum: " + minimum(data));
        System.out.println("countAbove(5): " + countAbove(data, 5));

        try {
            maximum(null);
        } catch (IllegalArgumentException e) {
            System.out.println("null array -> caught: " + e.getMessage());
        }
        try {
            minimum(new int[0]);
        } catch (IllegalArgumentException e) {
            System.out.println("empty array -> caught: " + e.getMessage());
        }
    }
}