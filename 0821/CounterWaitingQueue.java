import java.util.ArrayDeque;
import java.util.Deque;

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer{" + "name='" + name + '\'' + '}';
    }
}

public class CounterWaitingQueue {
    // 使用 Deque 作為 FIFO Queue (入隊使用 offerLast，出隊使用 pollFirst)
    private Deque<Customer> queue = new ArrayDeque<>();

    public void addCustomer(Customer customer) {
        queue.offerLast(customer);
        System.out.println("顧客加入排隊: " + customer.getName());
    }

    public Customer peekNext() {
        if (queue.isEmpty()) {
            System.out.println("目前無等待顧客");
            return null;
        }
        return queue.peekFirst();
    }

    public Customer serveNext() {
        if (queue.isEmpty()) {
            System.out.println("無顧客可服務");
            return null;
        }
        Customer served = queue.pollFirst();
        System.out.println("正在服務顧客: " + served.getName());
        return served;
    }

    public int getWaitingCount() {
        return queue.size();
    }

    public static void main(String[] args) {
        CounterWaitingQueue counter = new CounterWaitingQueue();

        // 空隊列測試
        counter.peekNext();
        counter.serveNext();

        // 加入顧客
        counter.addCustomer(new Customer("Alice"));
        counter.addCustomer(new Customer("Bob"));
        counter.addCustomer(new Customer("Charlie"));

        System.out.println("等候人數: " + counter.getWaitingCount());
        System.out.println("下一位顧客: " + counter.peekNext().getName());

        // 服務顧客
        counter.serveNext();
        counter.serveNext();

        System.out.println("等候人數: " + counter.getWaitingCount());

        counter.serveNext();
        counter.serveNext(); // 再次測試空隊列
    }
}