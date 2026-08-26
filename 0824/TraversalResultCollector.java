import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TraversalResultCollector {

    static class Node {
        int value;
        Node left, right;
        Node(int value) { this.value = value; }
    }

    static List<String> preorder(Node root) {
        List<String> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }
    private static void preorderHelper(Node node, List<String> result) {
        if (node == null) return;
        result.add(String.valueOf(node.value));
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }

    static List<String> inorder(Node root) {
        List<String> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }
    private static void inorderHelper(Node node, List<String> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(String.valueOf(node.value));
        inorderHelper(node.right, result);
    }

    static List<String> postorder(Node root) {
        List<String> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }
    private static void postorderHelper(Node node, List<String> result) {
        if (node == null) return;
        postorderHelper(node.left, result);
        postorderHelper(node.right, result);
        result.add(String.valueOf(node.value));
    }

    static List<String> levelOrder(Node root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            result.add(String.valueOf(current.value));
            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }
        return result;
    }

    static void report(String label, Node root) {
        System.out.println("=== " + label + " ===");
        System.out.println("preorder: " + preorder(root));
        System.out.println("inorder: " + inorder(root));
        System.out.println("postorder: " + postorder(root));
        System.out.println("levelOrder: " + levelOrder(root));
        System.out.println();
    }

    public static void main(String[] args) {
        report("Empty tree", null);

        Node single = new Node(1);
        report("Single-node tree", single);

        // Left-skewed tree: 3 -> 2 -> 1
        Node l3 = new Node(3);
        Node l2 = new Node(2);
        Node l1 = new Node(1);
        l3.left = l2;
        l2.left = l1;
        report("Left-skewed tree", l3);

        // Complete tree
        Node c1 = new Node(1);
        Node c2 = new Node(2);
        Node c3 = new Node(3);
        Node c4 = new Node(4);
        Node c5 = new Node(5);
        Node c6 = new Node(6);
        Node c7 = new Node(7);
        c1.left = c2; c1.right = c3;
        c2.left = c4; c2.right = c5;
        c3.left = c6; c3.right = c7;
        report("Complete tree", c1);
    }
}