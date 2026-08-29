public class BstDeleteCases {

    static class Node {
        int key;
        Node left, right;
        Node(int key) { this.key = key; }
    }

    private Node root;

    public void insert(int key) {
        root = insert(root, key);
    }

    private Node insert(Node current, int key) {
        if (current == null) return new Node(key);
        if (key < current.key) current.left = insert(current.left, key);
        else if (key > current.key) current.right = insert(current.right, key);
        return current;
    }

    public void delete(int key) {
        root = delete(root, key);
    }

    private Node delete(Node current, int key) {
        if (current == null) return null;

        if (key < current.key) {
            current.left = delete(current.left, key);
        } else if (key > current.key) {
            current.right = delete(current.right, key);
        } else {
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            Node successor = findMin(current.right);
            current.key = successor.key;
            current.right = delete(current.right, successor.key);
        }
        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }

    public void printInorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    public boolean isValidBST() {
        return isValidBST(root, null, null);
    }

    private boolean isValidBST(Node node, Integer min, Integer max) {
        if (node == null) return true;
        if ((min != null && node.key <= min) || (max != null && node.key >= max)) return false;
        return isValidBST(node.left, min, node.key) && isValidBST(node.right, node.key, max);
    }

    private void printState(String action) {
        System.out.println("Action: " + action);
        System.out.print("Inorder: ");
        printInorder();
        System.out.println("Size: " + size() + ", Valid BST: " + isValidBST());
        System.out.println("----------------------------------------------");
    }

    public static void main(String[] args) {
        BstDeleteCases tree = new BstDeleteCases();
        
        int[] keys = {50, 30, 70, 20, 40, 80};
        for (int k : keys) tree.insert(k);
        tree.printState("Initial Tree Setup");
        tree.delete(20);
        tree.printState("Delete Leaf (20)");
        tree.delete(70);
        tree.printState("Delete Single-Child (70)");
        tree.delete(50);
        tree.printState("Delete Two-Child Root (50)");
    }
}