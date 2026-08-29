public class BstDuplicateCounter {

    static class Node {
        int key;
        int count;
        Node left;
        Node right;

        Node(int key) {
            this.key = key;
            this.count = 1;
        }
    }

    private Node root;

    public void insert(int key) {
        root = insert(root, key);
    }

    private Node insert(Node current, int key) {
        if (current == null) {
            return new Node(key);
        }

        if (key < current.key) {
            current.left = insert(current.left, key);
        } else if (key > current.key) {
            current.right = insert(current.right, key);
        } else {
            current.count++;
        }
        return current;
    }

    public void printInorder() {
        System.out.print("Inorder Traversal: ");
        inorder(root);
        System.out.println();
    }

    private void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + "(" + node.count + ") ");
            inorder(node.right);
        }
    }

    public void delete(int key) {
        root = delete(root, key);
    }

    private Node delete(Node current, int key) {
        if (current == null) {
            return null;
        }

        if (key < current.key) {
            current.left = delete(current.left, key);
        } else if (key > current.key) {
            current.right = delete(current.right, key);
        } else {
            if (current.count > 1) {
                current.count--;
                return current;
            }

            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            Node successor = findMin(current.right);
            current.key = successor.key;
            current.count = successor.count;
            successor.count = 1;
            current.right = delete(current.right, successor.key);
        }
        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static void main(String[] args) {
        BstDuplicateCounter tree = new BstDuplicateCounter();

        System.out.println("=== 1. 插入含重複值的資料 ===");
        int[] input = {50, 30, 20, 40, 70, 60, 80, 30, 50, 50, 80};
        for (int val : input) {
            System.out.println("Inserting: " + val);
            tree.insert(val);
        }

        System.out.println("\n=== 2. 查看 Inorder 輸出 (格式：key(count)) ===");
        tree.printInorder();

        System.out.println("\n=== 3. 測試刪除重複節點 (減少 count) ===");
        System.out.println("Deleting 50 (count 應從 3 降為 2):");
        tree.delete(50);
        tree.printInorder();

        System.out.println("\nDeleting 30 (count 應從 2 降為 1):");
        tree.delete(30);
        tree.printInorder();

        System.out.println("\n=== 4. 測試完全移除節點 (count 從 1 降為 0，實體刪除) ===");
        System.out.println("Deleting 30 (應完全自樹中移除):");
        tree.delete(30);
        tree.printInorder();
    }
}