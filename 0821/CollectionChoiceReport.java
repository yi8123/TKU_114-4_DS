import java.util.*;

public class CollectionChoiceReport {

    // 需求一：保留搜尋紀錄且允許重複
    public static void requirement1() {
        System.out.println("--- 需求一：保留搜尋紀錄且允許重複 ---");
        System.out.println("Interface: List | Implementation: ArrayList");
        System.out.println("理由: 需維持插入順序且允許重複值，ArrayList 能提供高效的尾端追加與順序走訪。");

        List<String> searchHistory = new ArrayList<>();
        searchHistory.add("Java SE 17");
        searchHistory.add("ArrayList vs LinkedList");
        searchHistory.add("Java SE 17"); // 重複紀錄

        System.out.println("實作結果: " + searchHistory);
        System.out.println();
    }

    // 需求二：保存不重複會員編號
    public static void requirement2() {
        System.out.println("--- 需求二：保存不重複會員編號 ---");
        System.out.println("Interface: Set | Implementation: HashSet");
        System.out.println("理由: 確保元素不重複，HashSet 提供平均 O(1) 的高效新增與存在性檢查。");

        Set<String> memberIds = new HashSet<>();
        memberIds.add("M001");
        memberIds.add("M002");
        memberIds.add("M001"); // 重複加入無效

        System.out.println("實作結果: " + memberIds);
        System.out.println();
    }

    // 需求三：以學號查詢成績
    public static void requirement3() {
        System.out.println("--- 需求三：以學號查詢成績 ---");
        System.out.println("Interface: Map | Implementation: HashMap");
        System.out.println("理由: Key-Value 對應需求，HashMap 可依學號(Key)在 O(1) 時間內取得成績(Value)。");

        Map<String, Integer> studentGrades = new HashMap<>();
        studentGrades.put("S101", 85);
        studentGrades.put("S102", 92);

        System.out.println("查詢 S101 成績: " + studentGrades.get("S101"));
        System.out.println();
    }

    // 需求四：依到達順序處理列印工作
    public static void requirement4() {
        System.out.println("--- 需求四：依到達順序處理列印工作 ---");
        System.out.println("Interface: Queue/Deque | Implementation: ArrayDeque");
        System.out.println("理由: 先進先出 (FIFO) 佇列行為，ArrayDeque 比 LinkedList 在 Queue 操作上記憶體與速度表現更好。");

        Deque<String> printQueue = new ArrayDeque<>();
        printQueue.offerLast("Doc1.pdf");
        printQueue.offerLast("Image2.png");

        System.out.println("列印處理: " + printQueue.pollFirst());
        System.out.println("剩餘佇列: " + printQueue);
        System.out.println();
    }

    // 需求五：復原最近操作
    public static void requirement5() {
        System.out.println("--- 需求五：復原最近操作 ---");
        System.out.println("Interface: Deque | Implementation: ArrayDeque");
        System.out.println("理由: 後進先出 (LIFO) Stack 行為，ArrayDeque 是 Java 官方推薦替代舊 Stack 的類別。");

        Deque<String> undoStack = new ArrayDeque<>();
        undoStack.push("Move Item");
        undoStack.push("Delete Text");

        System.out.println("復原最近操作: " + undoStack.pop());
        System.out.println("剩餘 Stack: " + undoStack);
        System.out.println();
    }

    public static void main(String[] args) {
        requirement1();
        requirement2();
        requirement3();
        requirement4();
        requirement5();
    }
}