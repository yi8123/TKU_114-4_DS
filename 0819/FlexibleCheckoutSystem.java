// 檔名：FlexibleCheckoutSystem.java
interface PricingPolicy {
    int finalPrice(int originalPrice);
}

class StandardPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice);
    }
}

class VipPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice) * 85 / 100; // 85 折
    }
}

class ThresholdDiscountPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        int price = Math.max(0, originalPrice);
        return price >= 2000 ? price - 300 : price; // 滿 2000 折 300
    }
}

interface NotificationChannel {
    boolean send(String receiver, String message);
}

class EmailChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || !receiver.contains("@")) return false;
        System.out.println("[EMAIL] " + receiver + " -> " + message);
        return true;
    }
}

class SmsChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || !receiver.matches("^09\\d{8}$")) return false;
        System.out.println("[SMS] " + receiver + " -> " + message);
        return true;
    }
}

class ConsoleChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank()) return false;
        System.out.println("[CONSOLE] " + receiver + " -> " + message);
        return true;
    }
}

final class CheckoutResult {
    private final String orderId;
    private final int originalPrice;
    private final int finalPrice;
    private final boolean notificationSent;

    CheckoutResult(String orderId, int originalPrice, int finalPrice, boolean notificationSent) {
        this.orderId = orderId;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.notificationSent = notificationSent;
    }

    @Override
    public String toString() {
        return String.format("Result: Order=%s | Orig=$%d | Final=$%d | Notified=%s",
                orderId, originalPrice, finalPrice, notificationSent);
    }
}

class CheckoutService {
    private final PricingPolicy pricing;
    private final NotificationChannel channel;

    CheckoutService(PricingPolicy pricing, NotificationChannel channel) {
        this.pricing = pricing;
        this.channel = channel;
    }

    public CheckoutResult checkout(String orderId, int originalPrice, String receiver) {
        int validPrice = Math.max(0, originalPrice);
        int amount = pricing.finalPrice(validPrice);
        boolean sent = channel.send(receiver, "Order " + orderId + " confirmed, charged $" + amount);
        return new CheckoutResult(orderId, validPrice, amount, sent);
    }
}

public class FlexibleCheckoutSystem {
    public static void main(String[] args) {
        PricingPolicy standard = new StandardPricing();
        PricingPolicy vip = new VipPricing();
        PricingPolicy threshold = new ThresholdDiscountPricing();

        NotificationChannel email = new EmailChannel();
        NotificationChannel sms = new SmsChannel();
        NotificationChannel console = new ConsoleChannel();

        // 測試 6 種不同組合
        CheckoutService[] combinations = {
            new CheckoutService(standard, email),   // 1. 原價 + Email
            new CheckoutService(standard, sms),     // 2. 原價 + SMS
            new CheckoutService(vip, email),        // 3. VIP + Email
            new CheckoutService(vip, console),      // 4. VIP + Console
            new CheckoutService(threshold, sms),    // 5. 滿額折 + SMS
            new CheckoutService(threshold, console) // 6. 滿額折 + Console
        };

        String[] receivers = {
            "amy@example.com",
            "0912345678",
            "bob@test.com",
            "TERMINAL-1",
            "0987654321",
            "STDOUT"
        };

        int[] prices = {1000, 1500, 3000, 2000, 2500, 1800};

        System.out.println("=== 執行 6 種策略組合結帳測試 ===");
        for (int i = 0; i < combinations.length; i++) {
            String orderId = "ORD-2026-" + (i + 1);
            CheckoutResult result = combinations[i].checkout(orderId, prices[i], receivers[i]);
            System.out.println(result);
            System.out.println();
        }
    }
}