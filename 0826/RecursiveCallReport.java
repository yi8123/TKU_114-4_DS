public class RecursiveCallReport {

    public static int sum(int[] data, int index) {
        if (data == null || index >= data.length) {
            System.out.printf("Base Case reached | index: %d -> return 0%n", index);
            return 0;
        }

        int currentValue = data[index];
        System.out.printf("Entering sum() | index: %d, current value: %d%n", index, currentValue);

        int recursiveResult = sum(data, index + 1);
        int returnValue = currentValue + recursiveResult;

        System.out.printf("Returning sum()| index: %d, current value: %d, recursive result: %d, return value: %d%n",
                index, currentValue, recursiveResult, returnValue);

        return returnValue;
    }

    public static void main(String[] args) {
        System.out.println("=== Test 1: General Array ===");
        int[] arr1 = {3, 5, 2, 8};
        System.out.println("Total Sum: " + sum(arr1, 0) + "\n");

        System.out.println("=== Test 2: Single Element Array ===");
        int[] arr2 = {42};
        System.out.println("Total Sum: " + sum(arr2, 0) + "\n");

        System.out.println("=== Test 3: Empty Array ===");
        int[] arr3 = {};
        System.out.println("Total Sum: " + sum(arr3, 0) + "\n");
    }
}