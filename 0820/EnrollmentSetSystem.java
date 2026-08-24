import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EnrollmentSetSystem {

    public static class Enrollment {
        private final String studentId;
        private final String courseCode;

        public Enrollment(String studentId, String courseCode) {
            this.studentId = studentId;
            this.courseCode = courseCode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Enrollment that = (Enrollment) o;
            return Objects.equals(studentId, that.studentId) &&
                   Objects.equals(courseCode, that.courseCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentId, courseCode);
        }

        @Override
        public String toString() {
            return "Enrollment(" + studentId + " -> " + courseCode + ")";
        }
    }

    public static void main(String[] args) {
        Set<Enrollment> enrollments = new HashSet<>();

        // 同一人加入不同課程 (應為 true)
        System.out.println("加入 S01 -> CS101: " + enrollments.add(new Enrollment("S01", "CS101")));
        System.out.println("加入 S01 -> CS102: " + enrollments.add(new Enrollment("S01", "CS102")));

        // 同一人重複加入同一課程 (應為 false)
        System.out.println("重複加入 S01 -> CS101: " + enrollments.add(new Enrollment("S01", "CS101")));

        // 測試以「新建立但身分相同的 Object」進行 contains 與 remove
        Enrollment queryTarget = new Enrollment("S01", "CS102");
        System.out.println("Contains S01-CS102: " + enrollments.contains(queryTarget));

        System.out.println("取消/移除 S01-CS102: " + enrollments.remove(queryTarget));
        System.out.println("移除後 Contains S01-CS102: " + enrollments.contains(queryTarget));
    }
}
