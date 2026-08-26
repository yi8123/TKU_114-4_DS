import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TraversalTestReport {

    // =========================
    // Binary Tree Node
    // =========================
    static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
        }
    }

    // =========================
    // Preorder: Root -> Left -> Right
    // =========================
    static void preorder(Node node, List<Integer> result) {
        // Base case
        if (node == null) {
            return;
        }

        // Root
        result.add(node.data);

        // Left
        preorder(node.left, result);

        // Right
        preorder(node.right, result);
    }

    // =========================
    // Inorder: Left -> Root -> Right
    // =========================
    static void inorder(Node node, List<Integer> result) {
        // Base case
        if (node == null) {
            return;
        }

        // Left
        inorder(node.left, result);

        // Root
        result.add(node.data);

        // Right
        inorder(node.right, result);
    }

    // =========================
    // Postorder: Left -> Right -> Root
    // =========================
    static void postorder(Node node, List<Integer> result) {
        // Base case
        if (node == null) {
            return;
        }

        // Left
        postorder(node.left, result);

        // Right
        postorder(node.right, result);

        // Root
        result.add(node.data);
    }

    // =========================
    // Level-order: BFS
    // 使用 Queue FIFO
    // =========================
    static void levelOrder(Node root, List<Integer> result) {
        if (root == null) {
            return;
        }

        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            result.add(current.data);

            if (current.left != null) {
                queue.offer(current.left);
            }

            if (current.right != null) {
                queue.offer(current.right);
            }
        }
    }

    // =========================
    // Tree Statistics
    // =========================

    // Size：節點總數
    static int size(Node node) {
        if (node == null) {
            return 0;
        }

        return 1 + size(node.left) + size(node.right);
    }

    // Leaf：葉節點數量
    static int leafCount(Node node) {
        if (node == null) {
            return 0;
        }

        if (node.left == null && node.right == null) {
            return 1;
        }

        return leafCount(node.left) + leafCount(node.right);
    }

    // Height：
    // Empty tree = -1
    // Leaf = 0
    static int height(Node node) {
        if (node == null) {
            return -1;
        }

        return 1 + Math.max(height(node.left), height(node.right));
    }

    // =========================
    // 取得四種 Traversal 結果
    // =========================
    static List<Integer> getPreorder(Node root) {
        List<Integer> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }

    static List<Integer> getInorder(Node root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    static List<Integer> getPostorder(Node root) {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }

    static List<Integer> getLevelOrder(Node root) {
        List<Integer> result = new ArrayList<>();
        levelOrder(root, result);
        return result;
    }

    // =========================
    // 測試單一 Tree
    // =========================
    static void testTree(
            String treeName,
            Node root,
            List<Integer> expectedPreorder,
            List<Integer> expectedInorder,
            List<Integer> expectedPostorder,
            List<Integer> expectedLevelOrder) {

        System.out.println("========================================");
        System.out.println("Tree: " + treeName);
        System.out.println("========================================");

        List<Integer> actualPreorder = getPreorder(root);
        List<Integer> actualInorder = getInorder(root);
        List<Integer> actualPostorder = getPostorder(root);
        List<Integer> actualLevelOrder = getLevelOrder(root);

        // Preorder
        System.out.println("Preorder");
        System.out.println("  Expected: " + expectedPreorder);
        System.out.println("  Actual  : " + actualPreorder);
        System.out.println("  Match   : " + expectedPreorder.equals(actualPreorder));

        // Inorder
        System.out.println("Inorder");
        System.out.println("  Expected: " + expectedInorder);
        System.out.println("  Actual  : " + actualInorder);
        System.out.println("  Match   : " + expectedInorder.equals(actualInorder));

        // Postorder
        System.out.println("Postorder");
        System.out.println("  Expected: " + expectedPostorder);
        System.out.println("  Actual  : " + actualPostorder);
        System.out.println("  Match   : " + expectedPostorder.equals(actualPostorder));

        // Level-order
        System.out.println("Level-order");
        System.out.println("  Expected: " + expectedLevelOrder);
        System.out.println("  Actual  : " + actualLevelOrder);
        System.out.println("  Match   : " + expectedLevelOrder.equals(actualLevelOrder));

        // Tree statistics
        System.out.println();
        System.out.println("Tree Statistics");
        System.out.println("  Size      : " + size(root));
        System.out.println("  Leaf Count: " + leafCount(root));
        System.out.println("  Height    : " + height(root));

        System.out.println();
    }

    // =========================
    // 建立 Empty Tree
    // =========================
    static Node createEmptyTree() {
        return null;
    }

    // =========================
    // 建立 Single-node Tree
    //
    //       1
    // =========================
    static Node createSingleNodeTree() {
        return new Node(1);
    }

    // =========================
    // 建立 Only-left Tree
    //
    //       1
    //      /
    //     2
    //    /
    //   3
    // =========================
    static Node createOnlyLeftTree() {
        Node root = new Node(1);
        root.left = new Node(2);
        root.left.left = new Node(3);

        return root;
    }

    // =========================
    // 建立 Only-right Tree
    //
    //   1
    //    \
    //     2
    //      \
    //       3
    // =========================
    static Node createOnlyRightTree() {
        Node root = new Node(1);
        root.right = new Node(2);
        root.right.right = new Node(3);

        return root;
    }

    // =========================
    // 建立 Complete Tree
    //
    //          1
    //       /     \
    //      2       3
    //     / \     / \
    //    4   5   6   7
    // =========================
    static Node createCompleteTree() {
        Node root = new Node(1);

        root.left = new Node(2);
        root.right = new Node(3);

        root.left.left = new Node(4);
        root.left.right = new Node(5);

        root.right.left = new Node(6);
        root.right.right = new Node(7);

        return root;
    }

    // =========================
    // 建立 Irregular Tree
    //
    //          1
    //        /   \
    //       2     3
    //        \   /
    //         4 5
    //            \
    //             6
    // =========================
    static Node createIrregularTree() {
        Node root = new Node(1);

        root.left = new Node(2);
        root.right = new Node(3);

        root.left.right = new Node(4);
        root.right.left = new Node(5);

        root.right.left.right = new Node(6);

        return root;
    }

    // =========================
    // Main
    // =========================
    public static void main(String[] args) {

        System.out.println("Traversal Test Report");
        System.out.println("Binary Tree Traversal Testing");
        System.out.println();

        // ---------------------------------
        // 1. Empty Tree
        // ---------------------------------
        testTree(
                "Empty Tree",
                createEmptyTree(),

                List.of(),
                List.of(),
                List.of(),
                List.of()
        );

        // ---------------------------------
        // 2. Single-node Tree
        //       1
        // ---------------------------------
        testTree(
                "Single-node Tree",
                createSingleNodeTree(),

                List.of(1),
                List.of(1),
                List.of(1),
                List.of(1)
        );

        // ---------------------------------
        // 3. Only-left Tree
        //
        //       1
        //      /
        //     2
        //    /
        //   3
        // ---------------------------------
        testTree(
                "Only-left Tree",
                createOnlyLeftTree(),

                List.of(1, 2, 3),
                List.of(3, 2, 1),
                List.of(3, 2, 1),
                List.of(1, 2, 3)
        );

        // ---------------------------------
        // 4. Only-right Tree
        //
        //   1
        //    \
        //     2
        //      \
        //       3
        // ---------------------------------
        testTree(
                "Only-right Tree",
                createOnlyRightTree(),

                List.of(1, 2, 3),
                List.of(1, 2, 3),
                List.of(3, 2, 1),
                List.of(1, 2, 3)
        );

        // ---------------------------------
        // 5. Complete Tree
        //
        //          1
        //       /     \
        //      2       3
        //     / \     / \
        //    4   5   6   7
        // ---------------------------------
        testTree(
                "Complete Tree",
                createCompleteTree(),

                List.of(1, 2, 4, 5, 3, 6, 7),
                List.of(4, 2, 5, 1, 6, 3, 7),
                List.of(4, 5, 2, 6, 7, 3, 1),
                List.of(1, 2, 3, 4, 5, 6, 7)
        );

        // ---------------------------------
        // 6. Irregular Tree
        //
        //          1
        //        /   \
        //       2     3
        //        \   /
        //         4 5
        //            \
        //             6
        // ---------------------------------
        testTree(
                "Irregular Tree",
                createIrregularTree(),

                List.of(1, 2, 4, 3, 5, 6),
                List.of(2, 4, 1, 5, 6, 3),
                List.of(4, 2, 6, 5, 3, 1),
                List.of(1, 2, 3, 4, 5, 6)
        );

        System.out.println("========================================");
        System.out.println("All traversal tests completed.");
        System.out.println("========================================");
    }
}