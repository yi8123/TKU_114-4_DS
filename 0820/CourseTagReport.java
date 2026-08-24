import java.util.*;

public class CourseTagReport {
    public static void main(String[] args) {
        String[] rawTags = {"Java", "Backend", "Database", "Java", "Spring", "Backend", "Java"};

        List<String> tagList = new ArrayList<>();
        Set<String> tagSet = new LinkedHashSet<>();
        Map<String, Integer> tagCountMap = new HashMap<>();

        for (String tag : rawTags) {
            tagList.add(tag);
            tagSet.add(tag);
            tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
        }

        System.out.println("1. List (保留原始順序與重複值，適用於時間軸/操作紀錄):");
        System.out.println("   " + tagList);

        System.out.println("\n2. Set (去除重複標籤，適用於選項清單/唯一分類檢視):");
        System.out.println("   " + tagSet);

        System.out.println("\n3. Map (統計各標籤出現頻率，適用於熱門度分析/權重計算):");
        for (Map.Entry<String, Integer> entry : tagCountMap.entrySet()) {
            System.out.println("   " + entry.getKey() + ": " + entry.getValue() + " 次");
        }
    }
}