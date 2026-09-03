import java.util.*;

public class Q05_StudentHashIndex {
    private final Map<String, Set<String>> studentToCourses = new HashMap<>();
    private final Map<String, Set<String>> courseToStudents = new HashMap<>();
    private int totalEnrollments = 0;

    private String normalize(String s) {
        if (s == null) return null;
        String trimmed = s.trim();
        return trimmed.isEmpty() ? null : trimmed.toUpperCase();
    }

    public boolean enroll(String studentId, String courseId) {
        String s = normalize(studentId);
        String c = normalize(courseId);
        if (s == null || c == null) {
            return false;
        }

        Set<String> courses = studentToCourses.computeIfAbsent(s, k -> new HashSet<>());
        if (courses.contains(c)) {
            return false;
        }

        courses.add(c);
        courseToStudents.computeIfAbsent(c, k -> new HashSet<>()).add(s);
        totalEnrollments++;
        return true;
    }

    public boolean drop(String studentId, String courseId) {
        String s = normalize(studentId);
        String c = normalize(courseId);
        if (s == null || c == null) {
            return false;
        }

        Set<String> courses = studentToCourses.get(s);
        if (courses == null || !courses.contains(c)) {
            return false;
        }

        courses.remove(c);
        if (courses.isEmpty()) {
            studentToCourses.remove(s);
        }

        Set<String> students = courseToStudents.get(c);
        if (students != null) {
            students.remove(s);
            if (students.isEmpty()) {
                courseToStudents.remove(c);
            }
        }

        totalEnrollments--;
        return true;
    }

    public Set<String> coursesOf(String studentId) {
        String s = normalize(studentId);
        if (s == null || !studentToCourses.containsKey(s)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new HashSet<>(studentToCourses.get(s)));
    }

    public Set<String> studentsIn(String courseId) {
        String c = normalize(courseId);
        if (c == null || !courseToStudents.containsKey(c)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new HashSet<>(courseToStudents.get(c)));
    }

    public int enrollmentCount() {
        return totalEnrollments;
    }
}