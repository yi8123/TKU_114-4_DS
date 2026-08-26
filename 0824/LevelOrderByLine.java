import java.util.ArrayDeque;
import java.util.Queue;

public class LevelOrderByLine {

    static class Node {
        int value;
        Node left, right;
        Node(int value) { this.value = value; }
    }

    static void levelOrderByLine(Node root) {
        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int countInLevel = queue.size();
            StringBuilder line = new StringBuilder();
            // 每次 poll 只加入目前 node 的 child，避免重複
            for (int i = 0; i < countInLevel; i++) {
                Node current = queue.poll();
                line.append(current.value).append(" ");
                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
            System.out.println("Level " + level + " (count=" + countInLevel + "): " + line.toString().trim());
            level++;
        }
    }

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        n1.left = n2; n1.right = n3;
        n2.left = n4; n2.right = n5;
        n3.left = n6; n3.right = n7;

        System.out.println("=== General tree ===");
        levelOrderByLine(n1);

        System.out.println("=== Empty tree ===");
        levelOrderByLine(null);
    }
}