abstract class Worker {
    private final String id;
    private final String name;

    Worker(String id, String name) {
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id;
        this.name = (name == null || name.isBlank()) ? "Unknown" : name;
        System.out.println("Worker constructor: " + this.id);
    }

    String label() {
        return id + " " + name;
    }

    abstract int monthlyPay();
}

class SalariedWorker extends Worker {
    private final int salary;

    SalariedWorker(String id, String name, int salary) {
        super(id, name); // 第一行呼叫 superclass constructor
        this.salary = Math.max(0, salary);
        System.out.println("SalariedWorker constructor: " + this.salary);
    }

    @Override
    int monthlyPay() {
        return salary;
    }
}

class HourlyWorker extends Worker {
    private final int hourlyRate;
    private final int hours;

    HourlyWorker(String id, String name, int hourlyRate, int hours) {
        super(id, name);
        this.hourlyRate = Math.max(0, hourlyRate);
        this.hours = Math.max(0, hours);
        System.out.println("HourlyWorker constructor: " + (this.hourlyRate * this.hours));
    }

    @Override
    int monthlyPay() {
        return hourlyRate * hours;
    }
}

public class ConstructorChainDemo {
    public static void main(String[] args) {
        Worker w1 = new SalariedWorker("E01", "Amy", 50000);
        System.out.println(w1.label() + " pay=" + w1.monthlyPay());

        System.out.println();

        Worker w2 = new HourlyWorker("E02", "Bob", 200, 160);
        System.out.println(w2.label() + " pay=" + w2.monthlyPay());
    }
}