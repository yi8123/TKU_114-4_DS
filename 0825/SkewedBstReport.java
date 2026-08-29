public class SkewedBstReport {

    static class Node {
        int key;
        Node left, right;
        Node(int key) { this.key = key; }
    }

    static class BST {
        Node root;

        void insert(int key) {
            root = insert(root, key);
        }

        private Node insert(Node current, int key) {
            if (current == null) return new Node(key);
            if (key < current.key) current.left = insert(current.left, key);
            else if (key > current.key) current.right = insert(current.right, key);
            return current;
        }

        int size() {
            return size(root);
        }

        private int size(Node node) {
            if (node == null) return 0;
            return 1 + size(node.left) + size(node.right);
        }

        int height() {
            return height(root);
        }

        private int height(Node node) {
            if (node == null) return 0;
            return 1 + Math.max(height(node.left), height(node.right));
        }

        int getSearchComparisons(int target) {
            Node current = root;
            int count = 0;
            while (current != null) {
                count++;
                if (target == current.key) return count;
                else if (target < current.key) current = current.left;
                else current = current.right;
            }
            return count;
        }
    }

    public static void main(String[] args) {
        int[] sortedData = {1, 2, 3, 4, 5, 6, 7};
        int[] balancedData = {4, 2, 6, 1, 3, 5, 7};

        BST skewedTree = new BST();
        for (int k : sortedData) skewedTree.insert(k);

        BST balancedTree = new BST();
        for (int k : balancedData) balancedTree.insert(k);

        System.out.printf("%-18s %-8s %-8s %-16s %-16s%n", "Tree Type", "Size", "Height", "Total Comparisons", "Avg Comparisons");
        System.out.println("----------------------------------------------------------------------");

        int totalSkewedComp = 0;
        for (int k : sortedData) totalSkewedComp += skewedTree.getSearchComparisons(k);
        double avgSkewed = (double) totalSkewedComp / sortedData.length;
        System.out.printf("%-18s %-8d %-8d %-16d %-16.2f%n", "Skewed BST", skewedTree.size(), skewedTree.height(), totalSkewedComp, avgSkewed);

        int totalBalancedComp = 0;
        for (int k : sortedData) totalBalancedComp += balancedTree.getSearchComparisons(k);
        double avgBalanced = (double) totalBalancedComp / sortedData.length;
        System.out.printf("%-18s %-8d %-8d %-16d %-16.2f%n", "Balanced BST", balancedTree.size(), balancedTree.height(), totalBalancedComp, avgBalanced);
    }
}