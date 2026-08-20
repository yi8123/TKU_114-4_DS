// 檔名：DeliveryStrategySystem.java
interface DeliveryMethod {
    int calculateFee(int weightKg, int orderAmount);
    String getEstimateDescription();
}

class HomeDelivery implements DeliveryMethod {
    @Override
    public int calculateFee(int weightKg, int orderAmount) {
        if (orderAmount >= 2000) return 0; // 滿額免運
        return 100 + Math.max(0, weightKg) * 10;
    }

    @Override
    public String getEstimateDescription() {
        return "宅配到府（預估 1-2 個工作天，黑貓物流）";
    }
}

class ConvenienceStorePickup implements DeliveryMethod {
    @Override
    public int calculateFee(int weightKg, int orderAmount) {
        if (weightKg > 5) return 120; // 超重加價
        return 60;
    }

    @Override
    public String getEstimateDescription() {
        return "超商取貨（預估 2-3 天送達指定門市）";
    }
}

class SelfPickup implements DeliveryMethod {
    @Override
    public int calculateFee(int weightKg, int orderAmount) {
        return 0; // 自取免運
    }

    @Override
    public String getEstimateDescription() {
        return "實體門市自取（下單後 1 小時可於營業時間領取）";
    }
}

class OrderService {
    private final String orderId;
    private final int orderAmount;
    private final int weightKg;
    private final DeliveryMethod deliveryMethod; // Composition

    OrderService(String orderId, int orderAmount, int weightKg, DeliveryMethod deliveryMethod) {
        this.orderId = orderId;
        this.orderAmount = Math.max(0, orderAmount);
        this.weightKg = Math.max(0, weightKg);
        this.deliveryMethod = (deliveryMethod != null) ? deliveryMethod : new SelfPickup();
    }

    public void printDeliverySummary() {
        int fee = deliveryMethod.calculateFee(weightKg, orderAmount);
        System.out.println("訂單編號: " + orderId);
        System.out.println("商品金額: $" + orderAmount + " | 重量: " + weightKg + "kg");
        System.out.println("配送方式說明: " + deliveryMethod.getEstimateDescription());
        System.out.println("運費: $" + fee + " | 總計: $" + (orderAmount + fee));
        System.out.println("--------------------------------------------------");
    }
}

public class DeliveryStrategySystem {
    public static void main(String[] args) {
        OrderService o1 = new OrderService("ORD-001", 2500, 3, new HomeDelivery());
        OrderService o2 = new OrderService("ORD-002", 800, 2, new ConvenienceStorePickup());
        OrderService o3 = new OrderService("ORD-003", 500, 8, new SelfPickup());

        o1.printDeliverySummary();
        o2.printDeliverySummary();
        o3.printDeliverySummary();
    }
}