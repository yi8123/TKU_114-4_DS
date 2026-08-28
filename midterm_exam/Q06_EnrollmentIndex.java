import java.util.*;

public class Q06_EnrollmentIndex {
    private final Map<String, Set<String>> enrollmentMap = new HashMap<>();

    public boolean enroll(String courseCode, String studentId) {
        if (courseCode == null || courseCode.isBlank() || studentId == null || studentId.isBlank()) {
            return false;
        }
        Set<String> students = enrollmentMap.computeIfAbsent(courseCode, k -> new HashSet<>());
        return students.add(studentId);
    }

    public boolean drop(String courseCode, String studentId) {
        if (courseCode == null || courseCode.isBlank() || studentId == null || studentId.isBlank()) {
            return false;
        }
        Set<String> students = enrollmentMap.get(courseCode);
        if (students == null || !students.remove(studentId)) {
            return false;
        }
        if (students.isEmpty()) {
            enrollmentMap.remove(courseCode);
        }
        return true;
    }

    public int courseSize(String courseCode) {
        if (courseCode == null) return 0;
        Set<String> students = enrollmentMap.get(courseCode);
        return students == null ? 0 : students.size();
    }

    public List<String> studentsOf(String courseCode) {
        if (courseCode == null) return new ArrayList<>();
        Set<String> students = enrollmentMap.get(courseCode);
        if (students == null) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>(students);
        Collections.sort(list);
        return list;
    }

    public List<String> coursesOf(String studentId) {
        if (studentId == null || studentId.isBlank()) return new ArrayList<>();
        List<String> courses = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : enrollmentMap.entrySet()) {
            if (entry.getValue().contains(studentId)) {
                courses.add(entry.getKey());
            }
        }
        Collections.sort(courses);
        return courses;
    }

    public Map<String, Integer> summary() {
        Map<String, Integer> res = new TreeMap<>();
        for (Map.Entry<String, Set<String>> entry : enrollmentMap.entrySet()) {
            res.put(entry.getKey(), entry.getValue().size());
        }
        return Collections.unmodifiableMap(res);
    }
}