import java.util.*;

public class EnrollmentCleanup {
    public static void main(String[] args) {
        List<String> rawList = new ArrayList<>(Arrays.asList(
            "Alice", "", "Bob", null, "Charlie", "Alice", "   ", "David", "Bob", null, "Eva"
        ));

        System.out.println("清理前名單: " + rawList);

        // 1. 使用 Iterator 移除 null 與空白字串
        Iterator<String> it = rawList.iterator();
        while (it.hasNext()) {
            String item = it.next();
            if (item == null || item.trim().isEmpty()) {
                it.remove();
            }
        }

        // 2. 使用 Set 找出重複姓名
        Set<String> seen = new HashSet<>();
        Set<String> duplicates = new HashSet<>();
        for (String name : rawList) {
            if (!seen.add(name)) {
                duplicates.add(name);
            }
        }

        System.out.println("清理後名單: " + rawList);
        System.out.println("重複出現的姓名報告: " + duplicates);
    }
}
