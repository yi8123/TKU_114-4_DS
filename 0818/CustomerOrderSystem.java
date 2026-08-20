class Customer {
    private final String customerId;
    private final String name;

    public Customer(String customerId, String name) {
        this.customerId = (customerId == null || customerId.isBlank())
                ? "C_UNKNOWN"
                : customerId;

        this.name = (name == null || name.isBlank())
                ? "Anonymous"
                : name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}


class OrderItem {
    private final String productName;
    private final int unitPrice;
    private final int quantity;

    public OrderItem(String productName, int unitPrice, int quantity) {
        this.productName = (productName == null || productName.isBlank())
                ? "Unknown Product"
                : productName;

        this.unitPrice = Math.max(0, unitPrice);
        this.quantity = Math.max(1, quantity);
    }

    public String getProductName() {
        return productName;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSubtotal() {
        return unitPrice * quantity;
    }

    @Override
    public String toString() {
        return String.format(
                "  - %-12s | Price: %4d x %2d = %5d",
                productName,
                unitPrice,
                quantity,
                getSubtotal()
        );
    }
}


class CustomerOrder {
    private final String orderId;
    private final Customer customer;
    private final OrderItem[] items;
    private int itemCount;

    public CustomerOrder(String orderId, Customer customer, int capacity) {
        this.orderId = (orderId == null || orderId.isBlank())
                ? "ORDER_UNKNOWN"
                : orderId;

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }

        this.customer = customer;

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }

        this.items = new OrderItem[capacity];
        this.itemCount = 0;
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean addItem(OrderItem item) {
        if (item == null) {
            return false;
        }

        if (itemCount >= items.length) {
            return false;
        }

        items[itemCount] = item;
        itemCount++;

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
        int totalQuantity = 0;

        for (int i = 0; i < itemCount; i++) {
            totalQuantity += items[i].getQuantity();
        }

        return totalQuantity;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void printSummary() {
        System.out.println("========================================");
        System.out.println("Order ID: " + orderId);
        System.out.println(
                "Customer: " + customer.getName()
                + " (" + customer.getCustomerId() + ")"
        );

        System.out.println("Items:");

        for (int i = 0; i < itemCount; i++) {
            System.out.println(items[i]);
        }

        System.out.println("Item Types: " + itemCount);
        System.out.println("Total Items Count: " + getTotalQuantity());
        System.out.println("Order Grand Total: $" + calculateTotal());

        System.out.println("========================================");
    }
}


public class CustomerOrderSystem {

    public static void main(String[] args) {

        Customer amy = new Customer("C001", "Amy");

        CustomerOrder order =
                new CustomerOrder("ORD-2026-001", amy, 5);

        order.addItem(new OrderItem("Keyboard", 1200, 1));
        order.addItem(new OrderItem("Mouse", 600, 2));
        order.addItem(new OrderItem("USB Cable", 150, 3));

        order.printSummary();
    }
}