public class BinaryTreeStructureReport {

    static class Node {
        int value;
        Node left, right;
        Node(int value) { this.value = value; }
    }

    static int size(Node root) {
        if (root == null) return 0;
        return 1 + size(root.left) + size(root.right);
    }

    static int leafCount(Node root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        return leafCount(root.left) + leafCount(root.right);
    }

    // 固定：empty = -1, leaf = 0
    static int height(Node root) {
        if (root == null) return -1;
        return 1 + Math.max(height(root.left), height(root.right));
    }

    static void printLeaves(Node root) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            System.out.print(root.value + " ");
            return;
        }
        printLeaves(root.left);
        printLeaves(root.right);
    }

    static void report(String label, Node root) {
        System.out.println("=== " + label + " ===");
        System.out.println("root: " + (root == null ? "null" : root.value));
        System.out.print("leaves: ");
        printLeaves(root);
        System.out.println();
        System.out.println("size: " + size(root));
        System.out.println("leaf count: " + leafCount(root));
        System.out.println("height: " + height(root));
        System.out.println();
    }

    public static void main(String[] args) {
        // 建立至少 7 個 node 的樹
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

        report("General tree (7 nodes)", n1);
        report("Empty tree", null);

        Node single = new Node(100);
        report("Single-node tree", single);
    }
}