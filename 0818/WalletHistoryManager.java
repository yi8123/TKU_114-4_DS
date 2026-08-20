import java.util.Objects;

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

    public int getSequence() {
        return sequence;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("#%d %-13s $%4d | BalanceAfter: $%d", sequence, type, amount, balanceAfter);
    }
}

class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private final WalletTransaction[] transactions;
    private int transactionCount;

    DigitalWallet(String walletId, String owner, int capacity) {
        this.walletId = (walletId == null || walletId.isBlank()) ? "UNKNOWN" : walletId;
        this.owner = (owner == null || owner.isBlank()) ? "Unknown" : owner;
        this.balance = 0;
        this.transactions = new WalletTransaction[Math.max(1, capacity)];
        this.transactionCount = 0;
    }

    public boolean deposit(int amount) {
        if (amount <= 0 || hasNoCapacity()) return false;
        balance += amount;
        record("DEPOSIT", amount);
        return true;
    }

    public boolean pay(int amount) {
        if (amount <= 0 || amount > balance || hasNoCapacity()) return false;
        balance -= amount;
        record("PAY", amount);
        return true;
    }

    public boolean transferTo(DigitalWallet target, int amount) {
        if (target == null || target == this || amount <= 0) return false;
        if (this.balance < amount || this.hasNoCapacity() || target.hasNoCapacity()) {
            return false;
        }
        this.balance -= amount;
        this.record("TRANSFER_OUT", amount);

        target.balance += amount;
        target.record("TRANSFER_IN", amount);
        return true;
    }

    public WalletTransaction findTransaction(int sequence) {
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getSequence() == sequence) {
                return transactions[i];
            }
        }
        return null;
    }

    public int totalByType(String type) {
        int sum = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (Objects.equals(transactions[i].getType(), type)) {
                sum += transactions[i].getAmount();
            }
        }
        return sum;
    }

    private boolean hasNoCapacity() {
        return transactionCount >= transactions.length;
    }

    private void record(String type, int amount) {
        transactions[transactionCount] = new WalletTransaction(
                transactionCount + 1, type, amount, balance);
        transactionCount++;
    }

    public void printStatement() {
        System.out.println("=== Statement: " + walletId + " (" + owner + ") | Balance: $" + balance + " ===");
        for (int i = 0; i < transactionCount; i++) {
            System.out.println(transactions[i]);
        }
    }
}

public class WalletHistoryManager {
    public static void main(String[] args) {
        DigitalWallet w1 = new DigitalWallet("W01", "Amy", 5);
        DigitalWallet w2 = new DigitalWallet("W02", "Bob", 5);

        w1.deposit(1000);
        w1.pay(200);
        w1.transferTo(w2, 400);

        System.out.println("搜尋 W1 序號 2 的交易: " + w1.findTransaction(2));
        System.out.println("搜尋 W1 序號 99 的交易: " + w1.findTransaction(99));

        System.out.println("W1 PAY 總額: $" + w1.totalByType("PAY"));
        System.out.println("W1 TRANSFER_OUT 總額: $" + w1.totalByType("TRANSFER_OUT"));

        System.out.println();
        w1.printStatement();
        System.out.println();
        w2.printStatement();
    }
}