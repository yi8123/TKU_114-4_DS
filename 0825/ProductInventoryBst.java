public class ProductInventoryBst {

    static class Product {
        int productId;
        String name;
        double price;
        int stock;

        public Product(int productId, String name, double price, int stock) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.stock = stock;
        }

        @Override
        public String toString() {
            return String.format("ID: %-4d | Name: %-12s | Price: $%7.2f | Stock: %d",
                    productId, name, price, stock);
        }
    }

    static class Node {
        Product product;
        Node left, right;

        Node(Product product) {
            this.product = product;
        }
    }

    private Node root;

    public void addProduct(Product product) {
        if (product == null) return;
        root = insert(root, product);
    }

    private Node insert(Node current, Product product) {
        if (current == null) return new Node(product);
        if (product.productId < current.product.productId) {
            current.left = insert(current.left, product);
        } else if (product.productId > current.product.productId) {
            current.right = insert(current.right, product);
        } else {
            System.out.println("Product ID " + product.productId + " already exists. Skipped.");
        }
        return current;
    }

    public Product findProduct(int productId) {
        Node current = root;
        while (current != null) {
            if (productId == current.product.productId) return current.product;
            else if (productId < current.product.productId) current = current.left;
            else current = current.right;
        }
        return null;
    }

    public boolean restock(int productId, int amount) {
        if (amount <= 0) return false;
        Product p = findProduct(productId);
        if (p == null) {
            System.out.println("Restock failed: Product not found.");
            return false;
        }
        p.stock += amount;
        System.out.printf("Restocked %s (+%d). New Stock: %d%n", p.name, amount, p.stock);
        return true;
    }

    public boolean reduceStock(int productId, int amount) {
        if (amount <= 0) return false;
        Product p = findProduct(productId);
        if (p == null) {
            System.out.println("Reduce stock failed: Product not found.");
            return false;
        }
        if (p.stock < amount) {
            System.out.printf("Insufficient stock for %s! Requested: %d, Available: %d%n", p.name, amount, p.stock);
            return false;
        }
        p.stock -= amount;
        System.out.printf("Reduced stock for %s (-%d). Remaining: %d%n", p.name, amount, p.stock);
        return true;
    }

    public boolean deleteProduct(int productId) {
        if (findProduct(productId) == null) return false;
        root = delete(root, productId);
        return true;
    }

    private Node delete(Node current, int productId) {
        if (current == null) return null;

        if (productId < current.product.productId) {
            current.left = delete(current.left, productId);
        } else if (productId > current.product.productId) {
            current.right = delete(current.right, productId);
        } else {
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            Node successor = findMin(current.right);
            current.product = successor.product;
            current.right = delete(current.right, successor.product.productId);
        }
        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public void printInventoryReport() {
        System.out.println("================= Inventory Report =================");
        inorder(root);
        System.out.println("====================================================");
    }

    private void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.println(node.product);
            inorder(node.right);
        }
    }

    public static void main(String[] args) {
        ProductInventoryBst inventory = new ProductInventoryBst();

        inventory.addProduct(new Product(201, "Keyboard", 1200.0, 15));
        inventory.addProduct(new Product(105, "Mouse", 600.0, 30));
        inventory.addProduct(new Product(305, "Monitor", 6500.0, 8));
        inventory.addProduct(new Product(102, "USB Cable", 150.0, 50));
        inventory.addProduct(new Product(205, "Headset", 1800.0, 10));

        inventory.printInventoryReport();

        System.out.println("\n--- Testing Stock Operations ---");
        inventory.restock(105, 10);
        inventory.reduceStock(305, 3);
        inventory.reduceStock(305, 10);

        System.out.println("\n--- Testing Deletion ---");
        inventory.deleteProduct(201);

        inventory.printInventoryReport();
    }
}