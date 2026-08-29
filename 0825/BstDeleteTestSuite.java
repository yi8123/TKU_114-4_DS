public class BstDeleteTestSuite {

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

    public boolean contains(int key) {
        Node current = root;
        while (current != null) {
            if (key == current.key) return true;
            else if (key < current.key) current = current.left;
            else current = current.right;
        }
        return false;
    }

    public boolean delete(int key) {
        if (!contains(key)) return false;
        root = delete(root, key);
        return true;
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
        System.out.print("[ ");
        inorder(root);
        System.out.println("]");
    }

    private void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    public static void main(String[] args) {
        System.out.println("========== BST Delete Test Suite ==========\n");

        BstDeleteTestSuite tree1 = new BstDeleteTestSuite();
        System.out.println("[Test 1] Delete on Empty Tree:");
        boolean res1 = tree1.delete(10);
        System.out.println("Delete 10 result: " + res1 + ", Size: " + tree1.size());

        BstDeleteTestSuite tree2 = new BstDeleteTestSuite();
        tree2.insert(50);
        tree2.insert(30);
        System.out.println("\n[Test 2] Delete Missing Key:");
        boolean res2 = tree2.delete(99);
        System.out.println("Delete 99 result: " + res2 + ", Tree unchanged: ");
        tree2.printInorder();

        BstDeleteTestSuite tree3 = new BstDeleteTestSuite();
        tree3.insert(50);
        System.out.println("\n[Test 3] Delete Single Root (50):");
        tree3.delete(50);
        System.out.println("Size after delete: " + tree3.size() + ", Inorder: ");
        tree3.printInorder();

        BstDeleteTestSuite tree4 = new BstDeleteTestSuite();
        tree4.insert(50);
        tree4.insert(70);
        tree4.insert(80);
        System.out.println("\n[Test 4] Delete Root with Single Child (50):");
        tree4.delete(50);
        System.out.print("New Root & Tree: ");
        tree4.printInorder();

        BstDeleteTestSuite tree5 = new BstDeleteTestSuite();
        int[] keys5 = {50, 30, 70, 20, 40, 60, 80};
        for (int k : keys5) tree5.insert(k);
        System.out.println("\n[Test 5] Delete Root with Two Children (50):");
        tree5.delete(50);
        System.out.print("Inorder after root delete: ");
        tree5.printInorder();

        BstDeleteTestSuite tree6 = new BstDeleteTestSuite();
        int[] keys6 = {40, 20, 10, 30, 60, 50, 70};
        for (int k : keys6) tree6.insert(k);
        System.out.println("\n[Test 6] Sequential Delete to Empty:");
        for (int k : keys6) {
            tree6.delete(k);
            System.out.printf("Deleted %2d | Size: %d | Remaining: ", k, tree6.size());
            tree6.printInorder();
        }
    }
}