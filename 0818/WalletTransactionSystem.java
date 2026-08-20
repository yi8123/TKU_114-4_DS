final class WalletTransaction {
    private final int sequence;
    private final String type;
    private final int amount;
    private final int balanceAfter;

    WalletTransaction(int sequence, String type, int amount, int balanceAfter) {
        this.sequence = sequence;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        return sequence + " " + type + " " + amount + " balance=" + balanceAfter;
    }
}

class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private final WalletTransaction[] transactions;
    private int transactionCount;

    DigitalWallet(String walletId, String owner, int historyCapacity) {
        this.walletId = (walletId == null || walletId.isBlank()) ? "UNKNOWN" : walletId;
        this.owner = (owner == null || owner.isBlank()) ? "Unknown" : owner;
        this.balance = 0;
        this.transactions = new WalletTransaction[Math.max(1, historyCapacity)];
        this.transactionCount = 0;
    }

    boolean deposit(int amount) {
        if (amount <= 0 || hasNoCapacity()) {
            return false;
        }
        balance += amount;
        record("DEPOSIT", amount);
        return true;
    }

    boolean pay(int amount) {
        if (amount <= 0 || amount > balance || hasNoCapacity()) {
            return false;
        }
        balance -= amount;
        record("PAY", amount);
        return true;
    }

    boolean refund(int amount) {
        if (amount <= 0 || hasNoCapacity()) {
            return false;
        }
        balance += amount;
        record("REFUND", amount);
        return true;
    }

    // 實作變化：跨物件原子轉帳
    boolean transferTo(DigitalWallet target, int amount) {
        if (target == null || target == this || amount <= 0) {
            return false;
        }
        // 同時檢查：來源餘額足夠、來源有空間、目標有空間
        if (this.balance < amount || this.hasNoCapacity() || target.hasNoCapacity()) {
            return false;
        }

        // 條件全數通過後才執行狀態異動
        this.balance -= amount;
        this.record("TRANSFER_OUT", amount);

        target.balance += amount;
        target.record("TRANSFER_IN", amount);

        return true;
    }

    private boolean hasNoCapacity() {
        return transactionCount >= transactions.length;
    }

    private void record(String type, int amount) {
        transactions[transactionCount] = new WalletTransaction(
                transactionCount + 1, type, amount, balance);
        transactionCount++;
    }

    void printStatement() {
        System.out.println(walletId + " owner=" + owner + " balance=" + balance);
        for (int i = 0; i < transactionCount; i++) {
            System.out.println(transactions[i]);
        }
    }
}

public class WalletTransactionSystem {
    public static void main(String[] args) {
        DigitalWallet amy = new DigitalWallet("W001", "Amy", 5);
        DigitalWallet bob = new DigitalWallet("W002", "Bob", 2);

        amy.deposit(1000);
        
        // 測試成功轉帳
        System.out.println("Transfer 300 to Bob: " + amy.transferTo(bob, 300));
        
        // 測試容量不足轉帳失敗（Bob 容量只有 2，已存滿）
        bob.deposit(100); // Bob 筆數達上限 2
        System.out.println("Transfer 200 to full Bob: " + amy.transferTo(bob, 200));

        System.out.println("\n--- Amy Statement ---");
        amy.printStatement();

        System.out.println("\n--- Bob Statement ---");
        bob.printStatement();
    }
}