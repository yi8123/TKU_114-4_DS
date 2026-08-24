import java.util.ArrayList;
import java.util.List;

public class GenericRepositorySystem {

    public static class Repository<T> {
        private final List<T> items = new ArrayList<>();

        public void add(T item) {
            if (item != null) items.add(item);
        }

        public T get(int index) {
            if (index < 0 || index >= items.size()) return null;
            return items.get(index);
        }

        public boolean remove(T item) {
            return items.remove(item);
        }

        public int size() {
            return items.size();
        }

        public void printAll() {
            System.out.println("Repository Count: " + items.size());
            for (int i = 0; i < items.size(); i++) {
                System.out.println(" [" + i + "] " + items.get(i));
            }
        }
    }

    public static class Product {
        private final String name;
        private final double price;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Product{name='" + name + "', price=" + price + "}";
        }
    }

    public static void main(String[] args) {
        // String Repository
        Repository<String> strRepo = new Repository<>();
        strRepo.add("Alice");
        strRepo.add("Bob");
        strRepo.add("Charlie");
        strRepo.remove("Bob");
        System.out.println("--- String Repository ---");
        strRepo.printAll();

        // Product Repository
        Repository<Product> prodRepo = new Repository<>();
        Product p1 = new Product("Book", 29.9);
        Product p2 = new Product("Pen", 1.5);
        prodRepo.add(p1);
        prodRepo.add(p2);
        System.out.println("\n--- Product Repository ---");
        prodRepo.printAll();
    }
}