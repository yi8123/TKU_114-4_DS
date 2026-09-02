import java.util.*;

public class CollisionBucketReport {

    public static void generateReport(int[] keys, int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("Bucket count must be positive.");
        }

        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        if (keys != null) {
            for (int key : keys) {
                int index = Math.floorMod(key, bucketCount);
                buckets.get(index).add(key);
            }
        }

        int totalCollisions = 0;
        int maxChainLength = 0;

        for (int i = 0; i < bucketCount; i++) {
            List<Integer> chain = buckets.get(i);
            int size = chain.size();
            int collisions = Math.max(0, size - 1);
            totalCollisions += collisions;
            maxChainLength = Math.max(maxChainLength, size);

            System.out.println("Bucket " + i + ": " + chain + " | Collisions: " + collisions);
        }

        System.out.println("--- Summary ---");
        System.out.println("Total Collisions: " + totalCollisions);
        System.out.println("Max Chain Length: " + maxChainLength);
    }

    public static void main(String[] args) {
        int[] keys = {-15, 2, 12, -5, 2, 7, 17, 22, -3};
        int numBuckets = 5;
        generateReport(keys, numBuckets);
    }
}