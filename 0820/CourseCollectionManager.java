import java.util.*;
import java.util.stream.Collectors;

public class CourseCollectionManager {

    public static class StudentRecord {
        private final String studentId;
        private int score;
        private final String tag;

        public StudentRecord(String studentId, int score, String tag) {
            this.studentId = studentId;
            this.score = score;
            this.tag = (tag == null) ? "" : tag.trim();
        }

        public String getStudentId() { return studentId; }
        public int getScore() { return score; }
        public void setScore(int score) { this.score = score; }
        public String getTag() { return tag; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StudentRecord that = (StudentRecord) o;
            return Objects.equals(studentId, that.studentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentId);
        }

        @Override
        public String toString() {
            return String.format("[ID: %s, Score: %3d, Tag: '%s']", studentId, score, tag);
        }
    }

    public static class CourseManager {
        private final List<StudentRecord> recordList = new ArrayList<>();
        private final Set<StudentRecord> recordSet = new HashSet<>();
        private final Map<String, StudentRecord> recordMap = new HashMap<>();

        public boolean enroll(StudentRecord record) {
            if (record == null || recordMap.containsKey(record.getStudentId())) {
                return false; // 重複學號不加入
            }
            recordList.add(record);
            recordSet.add(record);
            recordMap.put(record.getStudentId(), record);
            return true;
        }

        public boolean updateScore(String studentId, int score) {
            StudentRecord record = recordMap.get(studentId);
            if (record != null) {
                record.setScore(score);
                return true;
            }
            return false;
        }

        public List<StudentRecord> findByTag(String tag) {
            String targetTag = (tag == null) ? "" : tag.trim();
            return recordList.stream()
                    .filter(r -> r.getTag().equalsIgnoreCase(targetTag))
                    .collect(Collectors.toList());
        }

        public Map<String, Integer> scoreDistribution() {
            Map<String, Integer> dist = new LinkedHashMap<>();
            dist.put("A", 0); // 90-100
            dist.put("B", 0); // 80-89
            dist.put("C", 0); // 70-79
            dist.put("D", 0); // 60-69
            dist.put("F", 0); // <60

            for (StudentRecord r : recordList) {
                int s = r.getScore();
                if (s >= 90) dist.put("A", dist.get("A") + 1);
                else if (s >= 80) dist.put("B", dist.get("B") + 1);
                else if (s >= 70) dist.put("C", dist.get("C") + 1);
                else if (s >= 60) dist.put("D", dist.get("D") + 1);
                else dist.put("F", dist.get("F") + 1);
            }
            return dist;
        }

        public List<StudentRecord> top(int count) {
            List<StudentRecord> sorted = new ArrayList<>(recordList);
            sorted.sort(Comparator.comparingInt(StudentRecord::getScore).reversed());
            if (count <= 0) return Collections.emptyList();
            return sorted.subList(0, Math.min(count, sorted.size()));
        }

        public void removeBelow(int minimum) {
            // 保持 List, Set, Map 一致性
            recordList.removeIf(r -> {
                if (r.getScore() < minimum) {
                    recordSet.remove(r);
                    recordMap.remove(r.getStudentId());
                    return true;
                }
                return false;
            });
        }

        public void printState() {
            System.out.println("List Size: " + recordList.size() + ", Set Size: " + recordSet.size() + ", Map Size: " + recordMap.size());
            recordList.forEach(System.out::println);
        }
    }

    public static void main(String[] args) {
        CourseManager manager = new CourseManager();

        // 測試六筆資料（含重複學號、同分、空白 tag）
        StudentRecord[] sampleData = {
            new StudentRecord("S101", 85, "Java"),
            new StudentRecord("S102", 92, "Spring"),
            new StudentRecord("S103", 58, "Java"),
            new StudentRecord("S104", 85, ""),         // 同分且空白 tag
            new StudentRecord("S105", 74, "Database"),
            new StudentRecord("S101", 99, "AI"),       // 重複學號
            new StudentRecord("S106", 65, null)        // null tag
        };

        for (StudentRecord r : sampleData) {
            boolean success = manager.enroll(r);
            System.out.println("Enroll " + r.getStudentId() + ": " + (success ? "Success" : "Failed (Duplicate)"));
        }

        System.out.println("\n=== 初始資料狀態 ===");
        manager.printState();

        System.out.println("\n=== 更新分數 ===");
        manager.updateScore("S104", 88);

        System.out.println("\n=== 依標籤搜尋 (Java) ===");
        manager.findByTag("Java").forEach(System.out::println);

        System.out.println("\n=== 等級分佈 ===");
        System.out.println(manager.scoreDistribution());

        System.out.println("\n=== 前 3 名學生 ===");
        manager.top(3).forEach(System.out::println);

        System.out.println("\n=== 移除未達 70 分者 (清理後驗證同步) ===");
        manager.removeBelow(70);
        manager.printState();
    }
}