import java.util.ArrayList;
import java.util.List;

public class OrderManagementBst {

    enum OrderStatus {
        PENDING, PAID, SHIPPED, COMPLETED, CANCELLED
    }

    static class Order {
        int orderId;
        String customer;
        double amount;
        OrderStatus status;

        Order(int orderId, String customer, double amount, OrderStatus status) {
            this.orderId = orderId;
            this.customer = customer;
            this.amount = amount;
            this.status = status;
        }

        @Override
        public String toString() {
            return String.format("[ID: %05d | Customer: %-12s | Amount: $%9.2f | Status: %-9s]",
                    orderId, customer, amount, status);
        }
    }

    static class Node {
        Order order;
        Node left, right;
        Node(Order order) { this.order = order; }
    }

    private Node root;

    public boolean add(Order order) {
        if (order == null || order.customer == null || order.customer.trim().isEmpty()) {
            System.out.println("[ADD FAIL] Invalid order data or empty customer name.");
            return false;
        }
        if (order.amount < 0) {
            System.out.printf("[ADD FAIL] Amount cannot be negative: $%.2f%n", order.amount);
            return false;
        }
        if (find(order.orderId) != null) {
            System.out.printf("[ADD FAIL] Duplicate Order ID: %d%n", order.orderId);
            return false;
        }
        root = insertRec(root, order);
        return true;
    }

    private Node insertRec(Node node, Order order) {
        if (node == null) return new Node(order);
        if (order.orderId < node.order.orderId) node.left = insertRec(node.left, order);
        else if (order.orderId > node.order.orderId) node.right = insertRec(node.right, order);
        return node;
    }

    public Order find(int orderId) {
        return findRec(root, orderId);
    }

    private Order findRec(Node node, int orderId) {
        if (node == null) return null;
        if (node.order.orderId == orderId) return node.order;
        return (orderId < node.order.orderId) ? findRec(node.left, orderId) : findRec(node.right, orderId);
    }

    public boolean updateStatus(int orderId, OrderStatus newStatus) {
        Order order = find(orderId);
        if (order == null) {
            System.out.println("[UPDATE FAIL] Order not found: " + orderId);
            return false;
        }
        order.status = newStatus;
        System.out.printf("[UPDATE SUCCESS] Order %d status changed to %s%n", orderId, newStatus);
        return true;
    }

    public boolean cancel(int orderId) {
        return updateStatus(orderId, OrderStatus.CANCELLED);
    }

    public boolean remove(int orderId) {
        Order target = find(orderId);
        if (target == null) {
            System.out.println("[REMOVE FAIL] Order not found: " + orderId);
            return false;
        }
        if (target.status != OrderStatus.CANCELLED) {
            System.out.printf("[REMOVE REJECTED] Cannot remove non-cancelled order #%d (Current Status: %s)%n",
                    orderId, target.status);
            return false;
        }
        root = removeRec(root, orderId);
        System.out.printf("[REMOVE SUCCESS] Cancelled order #%d purged from system.%n", orderId);
        return true;
    }

    private Node removeRec(Node node, int orderId) {
        if (node == null) return null;
        if (orderId < node.order.orderId) {
            node.left = removeRec(node.left, orderId);
        } else if (orderId > node.order.orderId) {
            node.right = removeRec(node.right, orderId);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node successor = findMin(node.right);
            node.order = successor.order;
            node.right = removeRec(node.right, successor.order.orderId);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public List<Order> idRangeReport(int lowId, int highId) {
        List<Order> list = new ArrayList<>();
        if (lowId > highId) return list;
        collectRange(root, lowId, highId, list);
        return list;
    }

    private void collectRange(Node node, int low, int high, List<Order> list) {
        if (node == null) return;
        if (node.order.orderId > low) collectRange(node.left, low, high, list);
        if (node.order.orderId >= low && node.order.orderId <= high) list.add(node.order);
        if (node.order.orderId < high) collectRange(node.right, low, high, list);
    }

    public double calculateTotalAmount() {
        return sumRec(root);
    }

    private double sumRec(Node node) {
        if (node == null) return 0.0;
        return node.order.amount + sumRec(node.left) + sumRec(node.right);
    }

    public void inorderReport() {
        System.out.println("================ Order Management System Report ================");
        inorderRec(root);
        System.out.println("----------------------------------------------------------------");
        System.out.printf("System Total Active Orders Amount: $%.2f%n", calculateTotalAmount());
        System.out.println("================================================================");
    }

    private void inorderRec(Node node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.println(node.order);
            inorderRec(node.right);
        }
    }

    public static void main(String[] args) {
        OrderManagementBst manager = new OrderManagementBst();
        manager.add(new Order(1003, "Alice", 1250.0, OrderStatus.PENDING));
        manager.add(new Order(1001, "Bob", 450.5, OrderStatus.PAID));
        manager.add(new Order(1005, "Charlie", 890.0, OrderStatus.SHIPPED));
        manager.add(new Order(1002, "David", 310.0, OrderStatus.PENDING));
        manager.add(new Order(1004, "Eva", 2100.0, OrderStatus.COMPLETED));
        manager.add(new Order(1006, "Frank", -50.0, OrderStatus.PENDING));
        manager.inorderReport();
        manager.remove(1001);
        manager.cancel(1001);
        manager.remove(1001);

        System.out.println("\n--- Order Range Query [1002 ~ 1004] ---");
        for (Order ord : manager.idRangeReport(1002, 1004)) {
            System.out.println(ord);
        }

        manager.inorderReport();
    }
}