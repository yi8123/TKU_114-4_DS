public class BinaryTreeStatistics {

    static class Node {
        int value;
        Node left, right;
        Node(int value) { this.value = value; }
    }

    static int size(Node root) {
        if (root == null) return 0;
        return 1 + size(root.left) + size(root.right);
    }

    static int sum(Node root) {
        if (root == null) return 0;
        return root.value + sum(root.left) + sum(root.right);
    }

    // 明確處理 empty tree，不可一律用 0 代表空樹最大值
    static int maximum(Node root) {
        if (root == null) {
            throw new IllegalStateException("Cannot compute maximum of an empty tree");
        }
        return maximumHelper(root);
    }
    private static int maximumHelper(Node node) {
        int result = node.value;
        if (node.left != null) {
            result = Math.max(result, maximumHelper(node.left));
        }
        if (node.right != null) {
            result = Math.max(result, maximumHelper(node.right));
        }
        return result;
    }

    static int leafCount(Node root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        return leafCount(root.left) + leafCount(root.right);
    }

    static int height(Node root) {
        if (root == null) return -1;
        return 1 + Math.max(height(root.left), height(root.right));
    }

    static boolean contains(Node root, int target) {
        if (root == null) return false;
        if (root.value == target) return true;
        return contains(root.left, target) || contains(root.right, target);
    }

    public static void main(String[] args) {
        Node n1 = new Node(10);
        Node n2 = new Node(5);
        Node n3 = new Node(20);
        Node n4 = new Node(3);
        Node n5 = new Node(7);
        n1.left = n2; n1.right = n3;
        n2.left = n4; n2.right = n5;

        System.out.println("=== General tree ===");
        System.out.println("size: " + size(n1));
        System.out.println("sum: " + sum(n1));
        System.out.println("maximum: " + maximum(n1));
        System.out.println("leaf count: " + leafCount(n1));
        System.out.println("height: " + height(n1));
        System.out.println("contains(7): " + contains(n1, 7));
        System.out.println("contains(99): " + contains(n1, 99));

        System.out.println("=== Empty tree ===");
        System.out.println("size: " + size(null));
        System.out.println("sum: " + sum(null));
        try {
            maximum(null);
        } catch (IllegalStateException e) {
            System.out.println("maximum threw expected exception: " + e.getMessage());
        }
        System.out.println("leaf count: " + leafCount(null));
        System.out.println("height: " + height(null));

        System.out.println("=== Single-node tree ===");
        Node single = new Node(42);
        System.out.println("maximum: " + maximum(single));
        System.out.println("height: " + height(single));
    }
}