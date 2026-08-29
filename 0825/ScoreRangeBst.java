public class ScoreRangeBst {

    static class StudentScore implements Comparable<StudentScore> {
        int score;
        int studentId;
        String name;

        public StudentScore(int score, int studentId, String name) {
            this.score = score;
            this.studentId = studentId;
            this.name = name;
        }

        @Override
        public int compareTo(StudentScore other) {
            if (this.score != other.score) {
                return Integer.compare(this.score, other.score);
            }
            return Integer.compare(this.studentId, other.studentId);
        }

        @Override
        public String toString() {
            return String.format("Score: %3d | ID: %d | Name: %s", score, studentId, name);
        }
    }

    static class Node {
        StudentScore data;
        Node left, right;

        Node(StudentScore data) {
            this.data = data;
        }
    }

    private Node root;

    public void insert(int score, int studentId, String name) {
        StudentScore item = new StudentScore(score, studentId, name);
        root = insert(root, item);
    }

    private Node insert(Node current, StudentScore item) {
        if (current == null) return new Node(item);

        int cmp = item.compareTo(current.data);
        if (cmp < 0) {
            current.left = insert(current.left, item);
        } else if (cmp > 0) {
            current.right = insert(current.right, item);
        }
        return current;
    }

    public void printScoreRange(int minScore, int maxScore) {
        System.out.printf("--- Students with Score between [%d, %d] ---%n", minScore, maxScore);
        if (minScore > maxScore) {
            System.out.println("Invalid score range: minScore > maxScore.");
            return;
        }
        rangeSearch(root, minScore, maxScore);
        System.out.println("----------------------------------------------");
    }

    private void rangeSearch(Node node, int minScore, int maxScore) {
        if (node == null) return;

        if (node.data.score > minScore) {
            rangeSearch(node.left, minScore, maxScore);
        }

        if (node.data.score >= minScore && node.data.score <= maxScore) {
            System.out.println(node.data);
        }

        if (node.data.score < maxScore) {
            rangeSearch(node.right, minScore, maxScore);
        }
    }

    public static void main(String[] args) {
        ScoreRangeBst rankTree = new ScoreRangeBst();

        rankTree.insert(85, 1001, "Alice");
        rankTree.insert(92, 1002, "Bob");
        rankTree.insert(78, 1003, "Charlie");
        rankTree.insert(85, 1004, "David");
        rankTree.insert(60, 1005, "Eva");
        rankTree.insert(85, 1006, "Frank");
        rankTree.insert(95, 1007, "Grace");
        rankTree.insert(72, 1008, "Helen");

        rankTree.printScoreRange(80, 95);

        rankTree.printScoreRange(60, 75);
    }
}