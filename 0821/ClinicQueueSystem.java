import java.util.*;

class Patient {
    private String id;
    private String name;

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "Patient{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
    }
}

public class ClinicQueueSystem {
    private Deque<Patient> waitingQueue = new ArrayDeque<>();
    private List<Patient> completedList = new ArrayList<>();

    // 一般掛號
    public void register(Patient patient) {
        waitingQueue.offerLast(patient);
        System.out.println("掛號成功: " + patient);
    }

    // 取消指定病歷號
    public boolean cancel(String patientId) {
        Iterator<Patient> iterator = waitingQueue.iterator();
        while (iterator.hasNext()) {
            Patient p = iterator.next();
            if (p.getId().equals(patientId)) {
                iterator.remove();
                System.out.println("已取消掛號: " + p);
                return true;
            }
        }
        System.out.println("取消失敗，找不到病歷號: " + patientId);
        return false;
    }

    // 叫號 (FIFO)
    public Patient callNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println("目前沒有等待中的病患");
            return null;
        }
        Patient p = waitingQueue.pollFirst();
        completedList.add(p);
        System.out.println("請診: " + p);
        return p;
    }

    // 查看下一位
    public Patient peekNext() {
        return waitingQueue.peekFirst();
    }

    // 當日完成清單
    public void printCompletedList() {
        System.out.println("=== 今日已看診完成清單 ===");
        for (Patient p : completedList) {
            System.out.println(p);
        }
    }

    public static void main(String[] args) {
        ClinicQueueSystem clinic = new ClinicQueueSystem();

        clinic.register(new Patient("P001", "張三"));
        clinic.register(new Patient("P002", "李四"));
        clinic.register(new Patient("P003", "王五"));

        System.out.println("下一位準備看診: " + clinic.peekNext());

        clinic.cancel("P002"); // 中間取消

        clinic.callNext(); // P001
        clinic.callNext(); // P003
        clinic.callNext(); // 空

        clinic.printCompletedList();
    }
}