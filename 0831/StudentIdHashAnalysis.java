import java.util.*;

public class StudentIdHashAnalysis {

    public static void analyze(int[] studentIds, int bucketCount) {
        if (bucketCount <= 0) throw new IllegalArgumentException("Bucket count must be positive");

        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int id : studentIds) {
            int idx = Math.floorMod(id, bucketCount);
            buckets.get(idx).add(id);
        }

        int totalCollisions = 0;
        int maxChain = 0;
        int nonEmptyBuckets = 0;

        System.out.println("--- Analysis Report (Buckets = " + bucketCount + ") ---");
        for (int i = 0; i < bucketCount; i++) {
            int chainLen = buckets.get(i).size();
            int collisions = Math.max(0, chainLen - 1);
            totalCollisions += collisions;
            maxChain = Math.max(maxChain, chainLen);
            if (chainLen > 0) nonEmptyBuckets++;

            System.out.printf("Bucket %2d: count = %2d, collisions = %2d\n", i, chainLen, collisions);
        }

        double avgChainAll = (double) studentIds.length / bucketCount;
        double avgChainNonEmpty = nonEmptyBuckets == 0 ? 0 : (double) studentIds.length / nonEmptyBuckets;

        System.out.println("Summary:");
        System.out.println("  Total IDs: " + studentIds.length);
        System.out.println("  Total Collisions: " + totalCollisions);
        System.out.println("  Max Chain Length: " + maxChain);
        System.out.printf("  Avg Chain (All Buckets): %.2f\n", avgChainAll);
        System.out.printf("  Avg Chain (Non-empty): %.2f\n\n", avgChainNonEmpty);
    }

    public static void main(String[] args) {
        int[] studentIds = {
            110501, 110502, 110515, 110528, 110541, 110601,
            110614, 110627, 110705, 110718, 110802, 110815,
            110901, 110914, 110927, 111001, 111014, 111027
        };

        analyze(studentIds, 10);
        analyze(studentIds, 13);
    }
}