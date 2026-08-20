abstract class EmployeeBase {
    private final String id;
    private final String name;

    EmployeeBase(String id, String name) {
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id;
        this.name = (name == null || name.isBlank()) ? "Anonymous" : name;
        System.out.println("[Constructor] EmployeeBase initialized for ID: " + this.id);
    }

    public String getInfo() {
        return id + " - " + name;
    }

    abstract int calculatePay();
}

class FullTimeEmployee extends EmployeeBase {
    private final int monthlySalary;

    FullTimeEmployee(String id, String name, int monthlySalary) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary);
        System.out.println("[Constructor] FullTimeEmployee initialized with salary: " + this.monthlySalary);
    }

    @Override
    int calculatePay() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends EmployeeBase {
    private final int hourlyRate;
    private final int hours;

    PartTimeEmployee(String id, String name, int hourlyRate, int hours) {
        super(id, name);
        this.hourlyRate = Math.max(0, hourlyRate);
        this.hours = Math.max(0, hours);
        System.out.println("[Constructor] PartTimeEmployee initialized with rate: " + this.hourlyRate + ", hours: " + this.hours);
    }

    @Override
    int calculatePay() {
        return hourlyRate * hours;
    }
}

public class EmployeeConstructorChain {
    public static void main(String[] args) {
        System.out.println("=== 建立 FullTimeEmployee ===");
        EmployeeBase ft = new FullTimeEmployee("FT01", "Alice", 60000);
        System.out.println(ft.getInfo() + " | Pay: $" + ft.calculatePay());

        System.out.println("\n=== 建立 PartTimeEmployee ===");
        EmployeeBase pt = new PartTimeEmployee("PT01", "Bob", 200, 80);
        System.out.println(pt.getInfo() + " | Pay: $" + pt.calculatePay());
    }
}