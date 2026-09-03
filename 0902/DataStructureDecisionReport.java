import java.util.*;

public class DataStructureDecisionReport {
    public static class Decision {
        String requirement;
        String choice;
        String reason;
        String complexity;

        public Decision(String req, String choice, String reason, String complexity) {
            this.requirement = req;
            this.choice = choice;
            this.reason = reason;
            this.complexity = complexity;
        }
    }

    public static List<Decision> generateReport() {
        List<Decision> list = new ArrayList<>();
        list.add(new Decision("1. 高頻 Key-Value 隨機查詢", "HashMap", "雜湊映射提供平攤常數時間取值", "Lookup: O(1)"));
        list.add(new Decision("2. 排程器動態取最小/最大權重工作", "PriorityQueue (Min-Heap)", "堆積能以對數時間維持極值存取", "Extract-Min: O(log N)"));
        list.add(new Decision("3. 瀏覽器上一頁/下一頁紀錄", "ArrayDeque (雙 Stack)", "LIFO 行為精確對應單步回溯與重做", "Push/Pop: O(1)"));
        list.add(new Decision("4. 印表機工作佇列 (先到先服務)", "Queue (LinkedList/ArrayDeque)", "FIFO 行為維持請求順序", "Offer/Poll: O(1)"));
        list.add(new Decision("5. 維持隨機存取且元素尾端頻繁增長", "ArrayList", "連續記憶體支援下標定址，動態平攤擴容", "Access: O(1), Append: O(1)"));
        list.add(new Decision("6. 頻繁在資料流正中間插入/刪除", "Doubly LinkedList", "節點指針重組免除平移開銷", "Insert/Delete(node): O(1)"));
        list.add(new Decision("7. 詞彙前綴自動補完 (Autocomplete)", "Trie", "字元樹枝結構共享前綴字串", "Search Prefix: O(L)"));
        list.add(new Decision("8. 區間資料查詢且需隨時維持有序", "TreeMap (Red-Black Tree)", "平衡搜尋樹保證範圍搜尋與排序", "Search/Insert: O(log N)"));
        list.add(new Decision("9. 社交網路好友關聯與最短關係鏈", "Graph (Adjacency List)", "稀疏邊結構兼顧空間與 BFS 連通走訪", "BFS: O(V + E)"));
        list.add(new Decision("10. 排除重複資料且不需順序", "HashSet", "基於 Hash Table 實作單元鍵集合", "Insert/Contains: O(1)"));
        list.add(new Decision("11. 快取替換策略 (LRU Cache)", "LinkedHashMap", "結合 Hash 快速尋址與雙向鏈結維持存取順序", "Get/Put: O(1)"));
        list.add(new Decision("12. 集合不相交連通性快速合併 (Kruskal/動態連通)", "Union-Find (Disjoint Set)", "路徑壓縮與秩合併達到近乎常數開銷", "Union/Find: O(α(N))"));
        return list;
    }

    public static void main(String[] args) {
        List<Decision> report = generateReport();
        System.out.printf("%-2s | %-32s | %-25s | %-20s%n", "#", "需求情境", "建議資料結構", "主要 Big-O");
        System.out.println("-".repeat(85));
        for (Decision d : report) {
            System.out.printf("%-2s | %-30s | %-23s | %-20s%n", d.requirement.substring(0, 2), d.choice, d.complexity, d.reason);
        }
    }
}