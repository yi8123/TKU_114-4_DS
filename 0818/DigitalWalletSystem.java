// 檔名：DigitalWalletSystem.java
class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private int transactionCount;

    DigitalWallet(String walletId, String owner) {
        this.walletId = (walletId == null || walletId.isBlank()) ? "UNKNOWN" : walletId;
        this.owner = (owner == null || owner.isBlank()) ? "Unknown" : owner;
        this.balance = 0;
        this.transactionCount = 0;
    }

    public boolean deposit(int amount) {
        if (amount <= 0) return false;
        balance += amount;
        transactionCount++;
        return true;
    }

    public boolean pay(int amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        transactionCount++;
        return true;
    }

    public boolean refund(int amount) {
        if (amount <= 0) return false;
        balance += amount;
        transactionCount++;
        return true;
    }

    public int getBalance() {
        return balance;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    @Override
    public String toString() {
        return String.format("[%s] Owner: %s | Balance: $%d | Transactions: %d",
                walletId, owner, balance, transactionCount);
    }
}

public class DigitalWalletSystem {
    public static void main(String[] args) {
        DigitalWallet wallet = new DigitalWallet("W101", "Alice");

        System.out.println("正常儲值 1000: " + wallet.deposit(1000)); // true
        System.out.println("正常付款 300: " + wallet.pay(300));        // true
        System.out.println("餘額不足付款 800: " + wallet.pay(800));   // false (維持 700)
        System.out.println("負數儲值 -100: " + wallet.deposit(-100));  // false
        System.out.println("正常退款 150: " + wallet.refund(150));    // true (變為 850)

        System.out.println("\n最終錢包狀態：");
        System.out.println(wallet);
    }
}