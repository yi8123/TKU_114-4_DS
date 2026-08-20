class Customer {
    private final String customerId;
    private final String name;

    Customer(String customerId, String name) {
        this.customerId = (customerId == null || customerId.isBlank()) ? "C_UNKNOWN" : customerId;
        this.name = (name == null || name.isBlank()) ? "Anonymous" : name;
    }

    public String getName() {
        return name;
    }

    public String getCustomerId() {
        return customerId;
    }
}

class OrderItem {
    private final String productName;
    private final int unitPrice;
    private final int quantity;

    OrderItem(String productName, int unitPrice, int quantity) {
        this.productName = productName;
        this.unitPrice = Math.max(0, unitPrice);
        this.quantity = Math.max(1, quantity);
    }

    public int getSubtotal() {
        return unitPrice * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("  - %-12s | Price: %4d x %2d = %5d",
                productName, unitPrice, quantity, getSubtotal());
    }
}

class CustomerOrder {
    private final String orderId;
    private final Customer customer;
    private final OrderItem[] items;
    private int itemCount;

    CustomerOrder(String orderId, Customer customer, int capacity) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new OrderItem[Math.max(1, capacity)];
        this.itemCount = 0;
    }

    public boolean addItem(OrderItem item) {
        if (item == null || itemCount >= items.length) return false;
        items[itemCount++] = item;
        return true;
    }

    public int calculateTotal() {
        int total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].getSubtotal();
        }
        return total;
    }

    public int getTotalQuantity() {
        int count = 0;
        for (int i = 0; i < itemCount; i++) {
            count += items[i].getQuantity();
        }
        return count;
    }

    public void printSummary() {
        System.out.println("========================================");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customer.getName() + " (" + customer.getCustomerId() + ")");
        System.out.println("Items:");
        for (int i = 0; i < itemCount; i++) {
            System.out.println(items[i]);
        }
        System.out.println("Total Items Count: " + getTotalQuantity());
        System.out.println("Order Grand Total: $" + calculateTotal());
        System.out.println("========================================");
    }
}

public class CustomerOrderSystem {
    public static void main(String[] args) {
        Customer amy = new Customer("C001", "Amy");
        CustomerOrder order = new CustomerOrder("ORD-2026-001", amy, 5);

        order.addItem(new OrderItem("Keyboard", 1200, 1));
        order.addItem(new OrderItem("Mouse", 600, 2));
        order.addItem(new OrderItem("USB Cable", 150, 3));

        order.printSummary();
    }
}