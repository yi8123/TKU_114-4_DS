import java.util.*;

public class TopSellingProducts {

    public static class Product {
        private final String id;
        private final int sales;

        public Product(String id, int sales) {
            this.id = id;
            this.sales = sales;
        }

        public String getId() { return id; }
        public int getSales() { return sales; }

        @Override
        public String toString() {
            return "{" + id + ": " + sales + "}";
        }
    }

    public static List<Product> getTopKProducts(List<Product> rawList, int k) {
        if (rawList == null || k <= 0) return Collections.emptyList();

        Map<String, Integer> salesMap = new HashMap<>();
        for (Product p : rawList) {
            if (p != null && p.getId() != null && p.getSales() >= 0) {
                salesMap.merge(p.getId(), p.getSales(), Integer::sum);
            }
        }

        Comparator<Product> worstInTopKFirst = (a, b) -> {
            if (a.getSales() != b.getSales()) {
                return Integer.compare(a.getSales(), b.getSales());
            }
            return b.getId().compareTo(a.getId());
        };

        PriorityQueue<Product> minHeap = new PriorityQueue<>(worstInTopKFirst);

        for (Map.Entry<String, Integer> entry : salesMap.entrySet()) {
            Product current = new Product(entry.getKey(), entry.getValue());
            if (minHeap.size() < k) {
                minHeap.offer(current);
            } else if (worstInTopKFirst.compare(current, minHeap.peek()) > 0) {
                minHeap.poll();
                minHeap.offer(current);
            }
        }

        List<Product> result = new ArrayList<>(minHeap);
        result.sort((a, b) -> {
            if (a.getSales() != b.getSales()) {
                return Integer.compare(b.getSales(), a.getSales());
            }
            return a.getId().compareTo(b.getId());
        });

        return result;
    }

    public static void main(String[] args) {
        List<Product> records = Arrays.asList(
            new Product("PROD-C", 50),
            new Product("PROD-A", 100),
            new Product("PROD-B", 80),
            new Product("PROD-C", 70),
            new Product("PROD-D", 100),
            new Product("PROD-E", 30),
            new Product("PROD-F", 100)
        );

        int k = 3;
        List<Product> topK = getTopKProducts(records, k);
        System.out.println("Top " + k + " Products: " + topK);
    }
}