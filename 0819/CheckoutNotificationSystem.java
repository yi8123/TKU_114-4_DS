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
        return Math.max(0, originalPrice) * 85 / 100;
    }
}

class StudentPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice) * 90 / 100;
    }
}

interface NotificationChannel {
    boolean send(String receiver, String message);
}

class EmailChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || !receiver.contains("@")) {
            return false;
        }
        System.out.println("EMAIL " + receiver + " -> " + message);
        return true;
    }
}

class ConsoleChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        System.out.println("CONSOLE " + receiver + " -> " + message);
        return true;
    }
}

class SmsChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || !receiver.matches("^09\\d{8}$")) {
            return false;
        }
        System.out.println("SMS " + receiver + " -> " + message);
        return true;
    }
}

class CheckoutService {
    private final PricingPolicy pricing;
    private final NotificationChannel channel;

    CheckoutService(PricingPolicy pricing, NotificationChannel channel) {
        this.pricing = pricing;
        this.channel = channel;
    }

    boolean checkout(String orderId, int originalPrice, String receiver) {
        if (orderId == null || orderId.isBlank() || originalPrice < 0) {
            return false;
        }
        int amount = pricing.finalPrice(originalPrice);
        return channel.send(receiver, "order=" + orderId + ", amount=" + amount);
    }
}

public class CheckoutNotificationSystem {
    public static void main(String[] args) {
        CheckoutService studentSms = new CheckoutService(new StudentPricing(), new SmsChannel());

        System.out.println("sent=" + studentSms.checkout("O201", 1000, "0912345678"));
        System.out.println("sent=" + studentSms.checkout("O202", 1000, "12345"));
    }
}