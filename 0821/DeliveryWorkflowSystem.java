import java.util.*;

class Package {
    private String id;
    private String destination;

    public Package(String id, String destination) {
        this.id = id;
        this.destination = destination;
    }

    public String getId() { return id; }
    public String getDestination() { return destination; }

    @Override
    public String toString() {
        return "Package{" + "id='" + id + '\'' + ", dest='" + destination + '\'' + '}';
    }
}

public class DeliveryWorkflowSystem {
    private Map<String, Package> packageMap = new HashMap<>(); // 快速查詢
    private Deque<Package> waitingQueue = new ArrayDeque<>();  // 等待配送 Queue
    private Deque<Package> completedStack = new ArrayDeque<>();// 已完成歷史 Stack

    // 新增包裹 (重複 id 不得加入)
    public boolean addPackage(String id, String destination) {
        if (packageMap.containsKey(id)) {
            System.out.println("新增失敗：包裹 ID 已存在 - " + id);
            return false;
        }
        Package pkg = new Package(id, destination);
        packageMap.put(id, pkg);
        waitingQueue.offerLast(pkg);
        System.out.println("包裹已收件進入等候區: " + pkg);
        return true;
    }

    // 處理配送
    public Package processNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println("無等待配送之包裹");
            return null;
        }
        Package pkg = waitingQueue.pollFirst();
        completedStack.push(pkg);
        System.out.println("完成配送: " + pkg);
        return pkg;
    }

    // Undo 復原最近一次完成配送
    public boolean undoLastDelivery() {
        if (completedStack.isEmpty()) {
            System.out.println("無已完成的操作可 Undo");
            return false;
        }
        Package pkg = completedStack.pop();
        waitingQueue.addFirst(pkg); // 放回 Queue 的前端優先處理
        System.out.println("Undo 配送，包裹放回等待佇列前端: " + pkg);
        return true;
    }

    // 依配送編號查詢
    public Package findById(String id) {
        return packageMap.get(id);
    }

    // 統計狀態
    public void printSummary() {
        System.out.println("=== 物流系統狀態統計 ===");
        System.out.println("總包裹數: " + packageMap.size());
        System.out.println("等候配送數: " + waitingQueue.size());
        System.out.println("已完成配送數: " + completedStack.size());
    }

    public static void main(String[] args) {
        DeliveryWorkflowSystem system = new DeliveryWorkflowSystem();

        system.addPackage("PKG001", "台北");
        system.addPackage("PKG002", "台中");
        system.addPackage("PKG001", "高雄"); // 重複 ID 應失敗

        system.processNext(); // 處理 PKG001
        system.printSummary();

        system.undoLastDelivery(); // Undo PKG001
        system.printSummary();

        System.out.println("查詢 PKG002: " + system.findById("PKG002"));
    }
}