public class BstShapeExperiment {

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
        int[] dataset = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        int[] orderSorted = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        int[] orderBalanced = {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};

        int[] orderMixed = {10, 3, 14, 1, 7, 12, 15, 2, 5, 9, 11, 13, 4, 6, 8};

        BST treeSorted = new BST();
        for (int k : orderSorted) treeSorted.insert(k);

        BST treeBalanced = new BST();
        for (int k : orderBalanced) treeBalanced.insert(k);

        BST treeMixed = new BST();
        for (int k : orderMixed) treeMixed.insert(k);

        System.out.println("================================ BST Shape Experiment Report ================================");
        System.out.printf("%-24s %-8s %-10s %-22s %-18s%n",
                "Insertion Order", "Nodes", "Height", "Total Search Comps", "Avg Comparisons");
        System.out.println("---------------------------------------------------------------------------------------------");

        reportRow("1. Sorted (Ascending)", treeSorted, dataset);
        reportRow("2. Balanced (Median)", treeBalanced, dataset);
        reportRow("3. Mixed / Random", treeMixed, dataset);
        System.out.println("=============================================================================================");
    }

    private static void reportRow(String label, BST tree, int[] dataset) {
        int totalComps = 0;
        for (int k : dataset) {
            totalComps += tree.getSearchComparisons(k);
        }
        double avg = (double) totalComps / dataset.length;
        System.out.printf("%-24s %-8d %-10d %-22d %-18.2f%n", label, dataset.length, tree.height(), totalComps, avg);
    }
}