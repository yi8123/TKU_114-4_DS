import java.util.*;

public class EnrollmentConflictSet {
    public static class EnrollmentRecord {
        private final String studentId;
        private final String courseId;

        public EnrollmentRecord(String studentId, String courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }

        public String getStudentId() { return studentId; }
        public String getCourseId() { return courseId; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EnrollmentRecord)) return false;
            EnrollmentRecord that = (EnrollmentRecord) o;
            return Objects.equals(studentId, that.studentId) &&
                   Objects.equals(courseId, that.courseId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentId, courseId);
        }

        @Override
        public String toString() {
            return String.format("(%s, %s)", studentId, courseId);
        }
    }

    public static void processEnrollments(List<EnrollmentRecord> rawRecords) {
        Set<EnrollmentRecord> uniqueRecords = new HashSet<>();
        List<EnrollmentRecord> duplicates = new ArrayList<>();

        Map<String, Set<String>> studentCourses = new TreeMap<>();
        Map<String, Integer> courseEnrollmentCount = new TreeMap<>();

        for (EnrollmentRecord record : rawRecords) {
            if (!uniqueRecords.add(record)) {
                duplicates.add(record);
            } else {
                studentCourses.computeIfAbsent(record.getStudentId(), k -> new TreeSet<>())
                              .add(record.getCourseId());
                courseEnrollmentCount.put(record.getCourseId(),
                        courseEnrollmentCount.getOrDefault(record.getCourseId(), 0) + 1);
            }
        }

        System.out.println("=== Duplicate Records Found ===");
        if (duplicates.isEmpty()) {
            System.out.println("None");
        } else {
            for (EnrollmentRecord d : duplicates) {
                System.out.println("Duplicate: " + d);
            }
        }

        System.out.println("\n=== Student Registered Courses ===");
        for (Map.Entry<String, Set<String>> entry : studentCourses.entrySet()) {
            System.out.printf("Student [%s]: %s%n", entry.getKey(), entry.getValue());
        }

        System.out.println("\n=== Course Enrollment Headcount ===");
        for (Map.Entry<String, Integer> entry : courseEnrollmentCount.entrySet()) {
            System.out.printf("Course [%s]: %d students%n", entry.getKey(), entry.getValue());
        }
    }
}