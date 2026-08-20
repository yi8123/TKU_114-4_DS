class Account {
    private final String accountId;
    private final String owner;
    private int balance;

    Account(String accountId, String owner, int balance) {
        this.accountId = accountId;
        this.owner = owner;
        this.balance = Math.max(0, balance);
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return "Account{" + accountId + ", owner='" + owner + "', balance=" + balance + "}";
    }
}

class TransferService {
    public static boolean transfer(Account source, Account target, int amount) {
        if (source == null || target == null) {
            return false;
        }
        if (source == target) {
            return false;
        }
        if (amount <= 0 || source.getBalance() < amount) {
            return false;
        }

        source.withdraw(amount);
        target.deposit(amount);
        return true;
    }
}

public class AccountTransferService {
    public static void main(String[] args) {
        Account accA = new Account("A01", "Alice", 1000);
        Account accB = new Account("A02", "Bob", 500);

        System.out.println("1. 正常轉帳 300: " + TransferService.transfer(accA, accB, 300));
        System.out.println("2. 餘額不足轉帳 900: " + TransferService.transfer(accA, accB, 900));
        System.out.println("3. 同帳戶自我轉帳: " + TransferService.transfer(accA, accA, 100));
        System.out.println("4. 轉帳至 null 目標: " + TransferService.transfer(accA, null, 100));

        System.out.println("\n最終帳戶狀態：");
        System.out.println(accA);
        System.out.println(accB);
    }
}