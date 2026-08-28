import java.util.ArrayList;
import java.util.List;

public class Q12_StudentBstSystem {
    public static class Student {
        private final int id;
        private final String name;
        private int score;

        public Student(int id, String name, int score) {
            if (id <= 0) {
                throw new IllegalArgumentException("Student ID must be positive");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be null or blank");
            }
            this.id = id;
            this.name = name;
            this.score = Math.max(0, Math.min(100, score));
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = Math.max(0, Math.min(100, score));
        }

        @Override
        public String toString() {
            return id + "|" + name + "|" + score;
        }
    }

    private static class Node {
        Student student;
        Node left;
        Node right;

        Node(Student student) {
            this.student = student;
        }
    }

    private Node root;

    public boolean add(Student student) {
        if (student == null) {
            return false;
        }
        if (root == null) {
            root = new Node(student);
            return true;
        }
        Node curr = root;
        while (true) {
            if (student.getId() == curr.student.getId()) {
                return false;
            } else if (student.getId() < curr.student.getId()) {
                if (curr.left == null) {
                    curr.left = new Node(student);
                    return true;
                }
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    curr.right = new Node(student);
                    return true;
                }
                curr = curr.right;
            }
        }
    }

    public Student find(int id) {
        Node curr = root;
        while (curr != null) {
            if (id == curr.student.getId()) {
                return curr.student;
            } else if (id < curr.student.getId()) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return null;
    }

    public boolean updateScore(int id, int score) {
        Student s = find(id);
        if (s == null) {
            return false;
        }
        s.setScore(score);
        return true;
    }

    public boolean remove(int id) {
        if (find(id) == null) {
            return false;
        }
        root = deleteNode(root, id);
        return true;
    }

    private Node deleteNode(Node node, int id) {
        if (node == null) return null;
        if (id < node.student.getId()) {
            node.left = deleteNode(node.left, id);
        } else if (id > node.student.getId()) {
            node.right = deleteNode(node.right, id);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node successor = findMin(node.right);
            node.student = successor.student;
            node.right = deleteNode(node.right, successor.student.getId());
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Student> studentsBetween(int lowId, int highId) {
        List<Student> result = new ArrayList<>();
        if (lowId > highId) {
            return result;
        }
        rangeHelper(root, lowId, highId, result);
        return result;
    }

    private void rangeHelper(Node node, int lowId, int highId, List<Student> result) {
        if (node == null) return;
        if (node.student.getId() > lowId) {
            rangeHelper(node.left, lowId, highId, result);
        }
        if (node.student.getId() >= lowId && node.student.getId() <= highId) {
            result.add(node.student);
        }
        if (node.student.getId() < highId) {
            rangeHelper(node.right, lowId, highId, result);
        }
    }

    public List<Student> inorder() {
        List<Student> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(Node node, List<Student> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.student);
        inorderHelper(node.right, result);
    }
}