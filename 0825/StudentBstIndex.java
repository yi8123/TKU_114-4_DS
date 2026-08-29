public class StudentBstIndex {

    static class Student {
        int studentId;
        String name;
        String department;

        public Student(int studentId, String name, String department) {
            this.studentId = studentId;
            this.name = name;
            this.department = department;
        }

        @Override
        public String toString() {
            return String.format("[%d] %s (%s)", studentId, name, department);
        }
    }

    static class Node {
        Student student;
        Node left, right;

        Node(Student student) {
            this.student = student;
        }
    }

    private Node root;

    public boolean insert(Student student) {
        if (student == null) return false;
        if (contains(student.studentId)) {
            System.out.println("Insert Failed: Duplicate student ID " + student.studentId);
            return false;
        }
        root = insert(root, student);
        return true;
    }

    private Node insert(Node current, Student student) {
        if (current == null) return new Node(student);
        if (student.studentId < current.student.studentId) {
            current.left = insert(current.left, student);
        } else if (student.studentId > current.student.studentId) {
            current.right = insert(current.right, student);
        }
        return current;
    }

    public boolean contains(int studentId) {
        return search(studentId) != null;
    }

    public Student search(int studentId) {
        Node current = root;
        while (current != null) {
            if (studentId == current.student.studentId) {
                return current.student;
            } else if (studentId < current.student.studentId) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public boolean delete(int studentId) {
        if (!contains(studentId)) {
            System.out.println("Delete Failed: Student ID " + studentId + " not found.");
            return false;
        }
        root = delete(root, studentId);
        return true;
    }

    private Node delete(Node current, int studentId) {
        if (current == null) return null;

        if (studentId < current.student.studentId) {
            current.left = delete(current.left, studentId);
        } else if (studentId > current.student.studentId) {
            current.right = delete(current.right, studentId);
        } else {
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            Node successor = findMin(current.right);
            current.student = successor.student;
            current.right = delete(current.right, successor.student.studentId);
        }
        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public void printInorder() {
        System.out.println("--- Student Index (Inorder) ---");
        inorder(root);
        System.out.println("-------------------------------");
    }

    private void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.println(node.student);
            inorder(node.right);
        }
    }

    public static void main(String[] args) {
        StudentBstIndex index = new StudentBstIndex();

        index.insert(new Student(103, "Alice", "CS"));
        index.insert(new Student(101, "Bob", "EE"));
        index.insert(new Student(105, "Charlie", "BA"));
        index.insert(new Student(102, "David", "CS"));
        index.insert(new Student(104, "Eva", "Math"));
        index.insert(new Student(103, "DuplicateAlice", "CS"));
        index.printInorder();

        System.out.println("Search 102: " + index.search(102));
        System.out.println("Search 999: " + index.search(999));
        System.out.println("\nDeleting 101 (Leaf)...");
        index.delete(101);
        System.out.println("Deleting 103 (Root with two children)...");
        index.delete(103);
        System.out.println("Deleting 999 (Missing)...");
        index.delete(999);

        index.printInorder();
    }
}