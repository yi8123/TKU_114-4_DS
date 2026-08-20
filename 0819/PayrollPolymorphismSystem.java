// 檔名：PayrollPolymorphismSystem.java
abstract class Employee {
    private final String id;
    private final String name;

    Employee(String id, String name) {
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id;
        this.name = (name == null || name.isBlank()) ? "Anonymous" : name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    abstract int calculatePay();

    @Override
    public String toString() {
        return String.format("[%s] %-8s | Pay: $%d", id, name, calculatePay());
    }
}

class SalariedEmployee extends Employee {
    private final int monthlySalary;

    SalariedEmployee(String id, String name, int monthlySalary) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary);
    }

    @Override
    int calculatePay() {
        return monthlySalary;
    }
}

class HourlyEmployee extends Employee {
    private final int hourlyRate;
    private final int hours;

    HourlyEmployee(String id, String name, int hourlyRate, int hours) {
        super(id, name);
        this.hourlyRate = Math.max(0, hourlyRate);
        this.hours = Math.max(0, hours);
    }

    @Override
    int calculatePay() {
        return hourlyRate * hours;
    }
}

class CommissionEmployee extends Employee {
    private final int baseSalary;
    private final int salesAmount;
    private final double commissionRate;

    CommissionEmployee(String id, String name, int baseSalary, int salesAmount, double commissionRate) {
        super(id, name);
        this.baseSalary = Math.max(0, baseSalary);
        this.salesAmount = Math.max(0, salesAmount);
        this.commissionRate = Math.max(0.0, commissionRate);
    }

    @Override
    int calculatePay() {
        return baseSalary + (int) (salesAmount * commissionRate);
    }
}

public class PayrollPolymorphismSystem {
    public static void main(String[] args) {
        Employee[] employees = {
            new SalariedEmployee("E01", "Alice", 60000),
            new HourlyEmployee("E02", "Bob", 200, 160),
            new CommissionEmployee("E03", "Charlie", 30000, 500000, 0.08),
            new SalariedEmployee("E04", "Daisy", 75000)
        };

        int totalPayroll = 0;
        Employee highestPaid = employees[0];

        System.out.println("=== 薪資清單 ===");
        for (Employee emp : employees) {
            System.out.println(emp);
            int pay = emp.calculatePay();
            totalPayroll += pay;
            if (pay > highestPaid.calculatePay()) {
                highestPaid = emp;
            }
        }

        System.out.println("\n薪資總支出: $" + totalPayroll);
        System.out.println("最高薪員工: " + highestPaid.getName() + " ($" + highestPaid.calculatePay() + ")");
    }
}