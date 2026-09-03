import java.util.*;

public class IntegratedStructureAudit {
    public enum AuditStatus { REASONABLE, QUESTIONABLE }

    public static class AuditFinding {
        public final String scenario;
        public final String structure;
        public final AuditStatus status;
        public final String reason;

        public AuditFinding(String sc, String st, AuditStatus status, String reason) {
            this.scenario = sc;
            this.structure = st;
            this.status = status;
            this.reason = reason;
        }

        @Override
        public String toString() {
            return String.format("[%s] Scenario: %-35s | Structure: %-12s | Reason: %s",
                    status, scenario, structure, reason);
        }
    }

    public static List<AuditFinding> runAudit() {
        List<AuditFinding> list = new ArrayList<>();
        list.add(new AuditFinding("百萬筆會員 ID 高頻單筆查詢", "List", AuditStatus.QUESTIONABLE,
                "List 線性搜尋為 O(N)，在此情境會嚴重延遲，應替換為 Hash Table (O(1))。"));
        list.add(new AuditFinding("排程器維持前三優先度任務", "PriorityQueue", AuditStatus.REASONABLE,
                "Heap 能以 O(log N) 快速取得最高優先度，非常合適。"));
        list.add(new AuditFinding("記錄樹狀部門階層並查詢路徑", "Queue", AuditStatus.QUESTIONABLE,
                "Queue 僅為線性走訪結構，無法直觀表達非線性父子關係，應改用 Tree/Graph。"));
        list.add(new AuditFinding("即時過濾傳入的黑名單 IP", "HashSet", AuditStatus.REASONABLE,
                "集合查詢平攤 O(1)，能以極高效能阻擋惡意請求。"));
        return list;
    }

    public static void main(String[] args) {
        List<AuditFinding> findings = runAudit();
        for (AuditFinding f : findings) {
            System.out.println(f);
        }
    }
}