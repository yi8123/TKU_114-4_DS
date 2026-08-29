public class BstRangeReport {

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

    public Integer findMin() {
        if (root == null) return null;
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.key;
    }

    public Integer findMax() {
        if (root == null) return null;
        Node current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.key;
    }

    public void printRange(int low, int high) {
        System.out.print("Range [" + low + ", " + high + "]: ");
        if (low > high) {
            System.out.println("(Invalid range: low > high)");
            return;
        }
        printRange(root, low, high);
        System.out.println();
    }

    private void printRange(Node node, int low, int high) {
        if (node == null) return;

        if (node.key > low) {
            printRange(node.left, low, high);
        }

        if (node.key >= low && node.key <= high) {
            System.out.print(node.key + " ");
        }

        if (node.key < high) {
            printRange(node.right, low, high);
        }
    }

    public static void main(String[] args) {
        BstRangeReport tree = new BstRangeReport();
        int[] keys = {50, 30, 70, 20, 40, 60, 80};
        for (int k : keys) tree.insert(k);

        System.out.println("Min Key: " + tree.findMin());
        System.out.println("Max Key: " + tree.findMax());

        tree.printRange(25, 65);
        tree.printRange(20, 50);
        tree.printRange(70, 30);
    }
}