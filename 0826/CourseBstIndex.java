import java.util.ArrayList;
import java.util.List;

public class CourseBstIndex {

    static class Course {
        String courseCode;
        String title;
        String instructor;
        int credits;

        Course(String courseCode, String title, String instructor, int credits) {
            this.courseCode = courseCode;
            this.title = title;
            this.instructor = instructor;
            this.credits = credits;
        }

        @Override
        public String toString() {
            return String.format("[Code: %-7s | Title: %-22s | Instructor: %-12s | Credits: %d]",
                    courseCode, title, instructor, credits);
        }
    }

    static class Node {
        Course course;
        Node left, right;
        Node(Course course) { this.course = course; }
    }

    private Node root;

    public boolean add(Course course) {
        if (course == null || course.courseCode == null || course.courseCode.trim().isEmpty()) {
            System.out.println("[ADD FAIL] Course or CourseCode cannot be empty.");
            return false;
        }
        if (course.credits < 1 || course.credits > 6) {
            System.out.printf("[ADD FAIL] Invalid credits for %s: %d (Must be 1 to 6)%n", 
                    course.courseCode, course.credits);
            return false;
        }
        if (find(course.courseCode) != null) {
            System.out.printf("[ADD FAIL] Duplicate courseCode: %s%n", course.courseCode);
            return false;
        }
        root = insertRec(root, course);
        return true;
    }

    private Node insertRec(Node node, Course course) {
        if (node == null) return new Node(course);
        int cmp = course.courseCode.compareTo(node.course.courseCode);
        if (cmp < 0) node.left = insertRec(node.left, course);
        else if (cmp > 0) node.right = insertRec(node.right, course);
        return node;
    }

    public Course find(String courseCode) {
        if (courseCode == null) return null;
        return findRec(root, courseCode);
    }

    private Course findRec(Node node, String courseCode) {
        if (node == null) return null;
        int cmp = courseCode.compareTo(node.course.courseCode);
        if (cmp == 0) return node.course;
        return (cmp < 0) ? findRec(node.left, courseCode) : findRec(node.right, courseCode);
    }

    public boolean updateCredit(String courseCode, int newCredits) {
        if (newCredits < 1 || newCredits > 6) {
            System.out.printf("[UPDATE FAIL] Credits must be between 1 and 6 (Attempted: %d)%n", newCredits);
            return false;
        }
        Course target = find(courseCode);
        if (target == null) {
            System.out.printf("[UPDATE FAIL] Course code not found: %s%n", courseCode);
            return false;
        }
        target.credits = newCredits;
        System.out.printf("[UPDATE SUCCESS] %s credits updated to %d%n", courseCode, newCredits);
        return true;
    }

    public boolean remove(String courseCode) {
        if (find(courseCode) == null) {
            System.out.printf("[REMOVE FAIL] Course not found: %s%n", courseCode);
            return false;
        }
        root = removeRec(root, courseCode);
        System.out.printf("[REMOVE SUCCESS] Course %s removed.%n", courseCode);
        return true;
    }

    private Node removeRec(Node node, String courseCode) {
        if (node == null) return null;
        int cmp = courseCode.compareTo(node.course.courseCode);
        if (cmp < 0) {
            node.left = removeRec(node.left, courseCode);
        } else if (cmp > 0) {
            node.right = removeRec(node.right, courseCode);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node successor = findMin(node.right);
            node.course = successor.course;
            node.right = removeRec(node.right, successor.course.courseCode);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public List<Course> queryByCodeRange(String low, String high) {
        List<Course> result = new ArrayList<>();
        if (low == null || high == null || low.compareTo(high) > 0) {
            return result;
        }
        collectRange(root, low, high, result);
        return result;
    }

    private void collectRange(Node node, String low, String high, List<Course> result) {
        if (node == null) return;
        if (node.course.courseCode.compareTo(low) > 0) {
            collectRange(node.left, low, high, result);
        }
        if (node.course.courseCode.compareTo(low) >= 0 && node.course.courseCode.compareTo(high) <= 0) {
            result.add(node.course);
        }
        if (node.course.courseCode.compareTo(high) < 0) {
            collectRange(node.right, low, high, result);
        }
    }

    public void printSortedReport() {
        System.out.println("================ 全校課程代碼排序報表 ================");
        inorderRec(root);
        System.out.println("=====================================================");
    }

    private void inorderRec(Node node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.println(node.course);
            inorderRec(node.right);
        }
    }

    public static void main(String[] args) {
        CourseBstIndex index = new CourseBstIndex();
        index.add(new Course("CS301", "Operating Systems", "Dr. Lin", 3));
        index.add(new Course("CS101", "Intro to CS", "Dr. Chen", 3));
        index.add(new Course("CS501", "Machine Learning", "Dr. Wang", 4));
        index.add(new Course("CS201", "Data Structures", "Dr. Lin", 3));
        index.add(new Course("CS401", "Compiler Design", "Dr. Hsu", 3));
        index.add(new Course("CS101", "Duplicate CS", "Dr. Fake", 3));
        index.add(new Course("CS999", "Invalid Course", "Dr. Error", 0));
        index.add(new Course("CS999", "Invalid Course", "Dr. Error", 7));
        index.printSortedReport();
        index.updateCredit("CS201", 4);
        index.updateCredit("CS201", 9);

        System.out.println("\n--- 課程代碼範圍查詢 [CS200 ~ CS450] ---");
        List<Course> rangeResults = index.queryByCodeRange("CS200", "CS450");
        for (Course c : rangeResults) {
            System.out.println(c);
        }

        System.out.println("\n--- 刪除測試 ---");
        index.remove("CS999");
        index.remove("CS301");
        index.printSortedReport();
    }
}