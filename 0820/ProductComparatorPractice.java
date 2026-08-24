import java.util.*;

public class ProductComparatorPractice {

    public static class StoreProduct implements Comparable<StoreProduct> {
        private final int id;
        private final String name;
        private final double price;
        private final int stock;

        public StoreProduct(int id, String name, double price, int stock) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }

        @Override
        public int compareTo(StoreProduct o) {
            return Integer.compare(this.id, o.id);
        }

        @Override
        public String toString() {
            return String.format("[ID: %d, Name: %-6s, Price: %.1f, Stock: %d]", id, name, price, stock);
        }
    }

    public static void main(String[] args) {
        List<StoreProduct> products = Arrays.asList(
            new StoreProduct(105, "Mouse", 25.0, 50),
            new StoreProduct(102, "Cable", 15.0, 100),
            new StoreProduct(104, "Pad", 15.0, 50),
            new StoreProduct(101, "Screen", 150.0, 20),
            new StoreProduct(103, "Keycap", 25.0, 100)
        );

        System.out.println("=== Original List ===");
        products.forEach(System.out::println);

        // 1. Natural Order (ID 升冪)
        List<StoreProduct> naturalSorted = new ArrayList<>(products);
        Collections.sort(naturalSorted);
        System.out.println("\n=== Natural Order (ID Asc) ===");
        naturalSorted.forEach(System.out::println);

        // 2. Comparator 1: Price 升冪，同價時 Name
        List<StoreProduct> priceSorted = new ArrayList<>(products);
        priceSorted.sort(Comparator.comparingDouble(StoreProduct::getPrice)
                                   .thenComparing(StoreProduct::getName));
        System.out.println("\n=== Comparator 1 (Price Asc -> Name Asc) ===");
        priceSorted.forEach(System.out::println);

        // 3. Comparator 2: Stock 降冪，同庫存時 ID
        List<StoreProduct> stockSorted = new ArrayList<>(products);
        stockSorted.sort(Comparator.comparingInt(StoreProduct::getStock).reversed()
                                   .thenComparingInt(StoreProduct::getId));
        System.out.println("\n=== Comparator 2 (Stock Desc -> ID Asc) ===");
        stockSorted.forEach(System.out::println);
    }
}