import java.util.*;

public class ListImplementationLab {

    // 撰寫只接收 List<Integer> 的 method
    public static void processList(List<Integer> list) {
        // 1. 尾端新增
        list.add(10);
        list.add(20);
        list.add(30);

        // 2. 指定位置插入
        list.add(1, 15); // [10, 15, 20, 30]

        // 3. 搜尋
        int index = list.indexOf(20);

        // 4. 刪除 (依 index 刪除)
        list.remove(2); // 刪除 index 2 (即 20) -> [10, 15, 30]

        // 5. 總和
        int sum = 0;
        for (int num : list) {
            sum += num;
        }

        System.out.println("最終 List 內容: " + list);
        System.out.println("搜尋 20 的索引: " + index);
        System.out.println("元素總和: " + sum);
    }

    public static void main(String[] args) {
        System.out.println("=== ArrayList 測試 ===");
        List<Integer> arrayList = new ArrayList<>();
        processList(arrayList);

        System.out.println("\n=== LinkedList 測試 ===");
        List<Integer> linkedList = new LinkedList<>();
        processList(linkedList);

        /*
         * 【ArrayList vs LinkedList 內部成本差異說明】
         * 1. 隨機存取 (Random Access - get/set):
         *    - ArrayList: O(1)。底層為連續記憶體陣列，可透過 index 直接計算記憶體位址。
         *    - LinkedList: O(n)。底層為雙向鏈結串列，必須從 head 或 tail 開始逐點走訪 (traversal)。
         * 
         * 2. 中間插入/刪除 (Insert/Delete in Middle):
         *    - ArrayList: O(n)。找到位置後，後方所有元素必須往後或往前位移 (System.arraycopy)。
         *    - LinkedList: O(n) 或 O(1)。若已有 Node 參照則修改指標只需 O(1)；若指定 index 則仍需 O(n) 走訪找到位置。
         * 
         * 3. 尾端新增 (Add to End):
         *    - ArrayList: 均攤 O(1)。容量滿時需觸發擴容（複製陣列）。
         *    - LinkedList: O(1)。直接建立新 Node 並更新 tail 指標。
         * 
         * 4. 記憶體開銷 (Memory Overhead):
         *    - ArrayList: 較低，僅額外消耗未使用的預留陣列空間。
         *    - LinkedList: 較高，每個節點都需要額外保存 prev 與 next 兩個參考指標。
         */
    }
}