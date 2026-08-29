public class BstSearchTrace {

    static class Node {
        int key;
        Node left;
        Node right;

        Node(int key) {
            this.key = key;
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
        }
        return current;
    }

    public boolean searchWithTrace(int target) {
        System.out.println("==================================================");
        System.out.println("Searching for key: " + target);
        System.out.printf("%-10s %-15s %-15s %-15s%n", "Step", "Current Key", "Direction", "Comparison Count");
        System.out.println("--------------------------------------------------");

        Node current = root;
        int compCount = 0;
        int step = 1;

        while (current != null) {
            compCount++;
            if (target == current.key) {
                System.out.printf("%-10d %-15d %-15s %-15d%n", step, current.key, "Found (Match)", compCount);
                System.out.println("Result: Key " + target + " FOUND with " + compCount + " comparison(s).\n");
                return true;
            } else if (target < current.key) {
                System.out.printf("%-10d %-15d %-15s %-15d%n", step, current.key, "Go Left (<)", compCount);
                current = current.left;
            } else {
                System.out.printf("%-10d %-15d %-15s %-15d%n", step, current.key, "Go Right (>)", compCount);
                current = current.right;
            }
            step++;
        }

        System.out.println("Result: Key " + target + " NOT FOUND with " + compCount + " comparison(s).\n");
        return false;
    }

    public static void main(String[] args) {
        BstSearchTrace tree = new BstSearchTrace();

        int[] keys = {50, 30, 70, 20, 40, 60, 80};
        for (int k : keys) {
            tree.insert(k);
        }

        tree.searchWithTrace(50);
        tree.searchWithTrace(30);
        tree.searchWithTrace(80);
        tree.searchWithTrace(65);
    }
}