public class BstInvariantChecker {

    static class Node {
        int key;
        Node left, right;
        Node(int key) { this.key = key; }
    }

    public static boolean isValidBST(Node root) {
        return validate(root, null, null);
    }

    private static boolean validate(Node node, Integer min, Integer max) {
        if (node == null) return true;

        if ((min != null && node.key <= min) || (max != null && node.key >= max)) {
            return false;
        }
        return validate(node.left, min, node.key) && validate(node.right, node.key, max);
    }

    public static void main(String[] args) {
        Node validTree = new Node(50);
        validTree.left = new Node(30);
        validTree.right = new Node(70);
        validTree.left.left = new Node(20);
        validTree.left.right = new Node(40);
        validTree.right.left = new Node(60);
        validTree.right.right = new Node(80);

        System.out.println("Tree 1 (Valid Tree) is valid? -> " + isValidBST(validTree));

        Node invalidTree1 = new Node(50);
        invalidTree1.left = new Node(30);
        invalidTree1.right = new Node(70);
        invalidTree1.right.left = new Node(45);
        invalidTree1.right.right = new Node(80);

        System.out.println("Tree 2 (Right subtree contains key < root) is valid? -> " + isValidBST(invalidTree1));

        Node invalidTree2 = new Node(50);
        invalidTree2.left = new Node(30);
        invalidTree2.right = new Node(70);
        invalidTree2.left.left = new Node(20);
        invalidTree2.left.right = new Node(55);

        System.out.println("Tree 3 (Left subtree contains key > root) is valid? -> " + isValidBST(invalidTree2));

        Node invalidTree3 = new Node(50);
        invalidTree3.left = new Node(50);
        invalidTree3.right = new Node(70);

        System.out.println("Tree 4 (Duplicate key in subtree) is valid? -> " + isValidBST(invalidTree3));
    }
}