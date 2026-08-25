import java.util.*;

class ServiceTicket {
    private String id;
    private String serviceName;

    public ServiceTicket(String id, String serviceName) {
        this.id = id;
        this.serviceName = serviceName;
    }

    public String getId() { return id; }
    public String getServiceName() { return serviceName; }

    @Override
    public String toString() {
        return "Ticket{" + "id='" + id + '\'' + ", service='" + serviceName + '\'' + '}';
    }
}

public class ServiceCenterWorkflow {
    private Map<String, ServiceTicket> ticketMap = new HashMap<>();
    private Deque<ServiceTicket> waitingQueue = new ArrayDeque<>();
    private Deque<ServiceTicket> completedStack = new ArrayDeque<>();
    private Set<String> activeIds = new HashSet<>(); // 防止重複 ID

    // 1. 建立票券
    public boolean createTicket(String id, String serviceName) {
        if (activeIds.contains(id)) {
            System.out.println("建立失敗：Ticket ID 重複 - " + id);
            return false;
        }
        ServiceTicket ticket = new ServiceTicket(id, serviceName);
        ticketMap.put(id, ticket);
        waitingQueue.offerLast(ticket);
        activeIds.add(id);
        System.out.println("取號成功: " + ticket);
        return true;
    }

    // 2. 叫號處理
    public ServiceTicket processNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println("目前無等待中的票券");
            return null;
        }
        ServiceTicket ticket = waitingQueue.pollFirst();
        completedStack.push(ticket);
        System.out.println("服務完成: " + ticket);
        return ticket;
    }

    // 3. 取消等待中票券 (只能作用於尚未處理的 ticket)
    public boolean cancelWaiting(String id) {
        if (!activeIds.contains(id)) {
            System.out.println("取消失敗：找不到該票券或已被取消/處理 - " + id);
            return false;
        }

        // 檢查是否在 waitingQueue 中
        Iterator<ServiceTicket> it = waitingQueue.iterator();
        while (it.hasNext()) {
            ServiceTicket t = it.next();
            if (t.getId().equals(id)) {
                it.remove();
                activeIds.remove(id);
                ticketMap.remove(id);
                System.out.println("成功取消等待票券: " + t);
                return true;
            }
        }

        System.out.println("取消失敗：票券已進入處理完成階段，無法取消 - " + id);
        return false;
    }

    // 4. Undo 最後一次完成 (放入 waiting queue 前端)
    public boolean undoLastCompletion() {
        if (completedStack.isEmpty()) {
            System.out.println("Undo 失敗：無已完成歷程");
            return false;
        }
        ServiceTicket ticket = completedStack.pop();
        waitingQueue.addFirst(ticket); // 放回前端
        System.out.println("Undo 成功，將票券放回等待隊列前端: " + ticket);
        return true;
    }

    // 5. 依 ID 查詢
    public ServiceTicket findById(String id) {
        return ticketMap.get(id);
    }

    // 6. 印出摘要
    public void printSummary() {
        System.out.println("\n=== 服務中心數據統計 ===");
        System.out.println("總登記數量: " + ticketMap.size());
        System.out.println("等待佇列: " + waitingQueue);
        System.out.println("完成歷程: " + completedStack);
        System.out.println("---------------------------\n");
    }

    public static void main(String[] args) {
        ServiceCenterWorkflow center = new ServiceCenterWorkflow();

        System.out.println("=== 測試 1: 空 Queue 處理與 Undo ===");
        center.processNext();
        center.undoLastCompletion();

        System.out.println("\n=== 測試 2: 建立票券與重複 ID 阻擋 ===");
        center.createTicket("A001", "開戶");
        center.createTicket("A002", "存款");
        center.createTicket("A003", "貸款");
        center.createTicket("A001", "外匯"); // 重複 ID 應失敗

        System.out.println("\n=== 測試 3: 取消不存在/已處理 ID ===");
        center.cancelWaiting("A999"); // 不存在 ID

        System.out.println("\n=== 測試 4: 服務與取消 ===");
        center.cancelWaiting("A002"); // 取消等待中的 A002
        center.processNext(); // 處理 A001

        System.out.println("\n=== 測試 5: 連續兩次 Undo ===");
        center.processNext(); // 處理 A003
        center.printSummary();

        center.undoLastCompletion(); // Undo A003
        center.undoLastCompletion(); // Undo A001
        center.undoLastCompletion(); // 失敗

        center.printSummary();
    }
}