public class OrderBstSystem {

    static class Order {
        int orderId;
        String customerName;
        double totalAmount;
        String status;

        public Order(int orderId, String customerName, double totalAmount) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.totalAmount = totalAmount;
            this.status = "ACTIVE";
        }

        @Override
        public String toString() {
            return String.format("OrderID: %-5d | Customer: %-10s | Amount: $%8.2f | Status: %s",
                    orderId, customerName, totalAmount, status);
        }
    }

    static class Node {
        Order order;
        Node left, right;
        Node(Order order) { this.order = order; }
    }

    private Node root;

    public boolean addOrder(Order order) {
        if (order == null || findOrder(order.orderId) != null) {
            System.out.println("Add Order Failed: Duplicate or null order (ID: " + (order != null ? order.orderId : "null") + ")");
            return false;
        }
        root = insert(root, order);
        return true;
    }

    private Node insert(Node current, Order order) {
        if (current == null) return new Node(order);
        if (order.orderId < current.order.orderId) current.left = insert(current.left, order);
        else current.right = insert(current.right, order);
        return current;
    }

    public Order findOrder(int orderId) {
        Node current = root;
        while (current != null) {
            if (orderId == current.order.orderId) return current.order;
            else if (orderId < current.order.orderId) current = current.left;
            else current = current.right;
        }
        return null;
    }

    public boolean cancelOrder(int orderId) {
        Order o = findOrder(orderId);
        if (o == null) {
            System.out.println("Cancel Failed: Order ID " + orderId + " not found.");
            return false;
        }
        o.status = "CANCELLED";
        System.out.println("Order " + orderId + " has been successfully CANCELLED.");
        return true;
    }

    public boolean updateAmount(int orderId, double newAmount) {
        Order o = findOrder(orderId);
        if (o == null) {
            System.out.println("Update Failed: Order ID " + orderId + " not found.");
            return false;
        }
        o.totalAmount = newAmount;
        System.out.printf("Order %d amount updated to $%.2f%n", orderId, newAmount);
        return true;
    }

    public void printOrderRange(int lowId, int highId) {
        System.out.printf("%n--- Orders in Range [%d, %d] ---%n", lowId, highId);
        if (lowId > highId) {
            System.out.println("Invalid range.");
            return;
        }
        rangeSearch(root, lowId, highId);
        System.out.println("---------------------------------");
    }

    private void rangeSearch(Node node, int lowId, int highId) {
        if (node == null) return;
        if (node.order.orderId > lowId) rangeSearch(node.left, lowId, highId);
        if (node.order.orderId >= lowId && node.order.orderId <= highId) {
            System.out.println(node.order);
        }
        if (node.order.orderId < highId) rangeSearch(node.right, lowId, highId);
    }

    public void printSummary() {
        SummaryResult result = new SummaryResult();
        computeSummary(root, result);
        System.out.println("\n=============== Business Summary ===============");
        System.out.println("Total Orders      : " + result.totalCount);
        System.out.println("Active Orders     : " + result.activeCount);
        System.out.println("Cancelled Orders  : " + result.cancelledCount);
        System.out.printf("Total Active Rev  : $%.2f%n", result.activeRevenue);
        System.out.println("================================================");
    }

    private static class SummaryResult {
        int totalCount = 0;
        int activeCount = 0;
        int cancelledCount = 0;
        double activeRevenue = 0.0;
    }

    private void computeSummary(Node node, SummaryResult res) {
        if (node != null) {
            computeSummary(node.left, res);
            res.totalCount++;
            if ("ACTIVE".equals(node.order.status)) {
                res.activeCount++;
                res.activeRevenue += node.order.totalAmount;
            } else {
                res.cancelledCount++;
            }
            computeSummary(node.right, res);
        }
    }

    public static void main(String[] args) {
        OrderBstSystem system = new OrderBstSystem();

        system.addOrder(new Order(1005, "Alice", 250.0));
        system.addOrder(new Order(1002, "Bob", 120.0));
        system.addOrder(new Order(1008, "Charlie", 580.0));
        system.addOrder(new Order(1001, "David", 75.0));
        system.addOrder(new Order(1004, "Eva", 310.0));
        system.addOrder(new Order(1007, "Frank", 430.0));
        system.printOrderRange(1002, 1006);
        system.updateAmount(1002, 150.0);
        system.cancelOrder(1004);
        system.printSummary();
    }
}