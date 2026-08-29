public class TreeShapeComparison {

    static class Node {
        int val;
        Node left, right;
        Node(int val) { this.val = val; }
    }

    static class SearchResult {
        boolean found;
        int comparisons;
        SearchResult(boolean found, int comparisons) {
            this.found = found;
            this.comparisons = comparisons;
        }
    }

    static class BST {
        Node root;

        public void insert(int val) {
            root = insertRec(root, val);
        }

        private Node insertRec(Node node, int val) {
            if (node == null) return new Node(val);
            if (val < node.val) node.left = insertRec(node.left, val);
            else if (val > node.val) node.right = insertRec(node.right, val);
            return node;
        }

        public int getHeight() {
            return heightRec(root);
        }

        private int heightRec(Node node) {
            if (node == null) return -1;
            return 1 + Math.max(heightRec(node.left), heightRec(node.right));
        }

        public SearchResult searchWithCost(int key) {
            int[] comps = new int[]{0};
            boolean found = searchRec(root, key, comps);
            return new SearchResult(found, comps[0]);
        }

        private boolean searchRec(Node node, int key, int[] comps) {
            if (node == null) return false;
            comps[0]++;
            if (node.val == key) return true;
            if (key < node.val) return searchRec(node.left, key, comps);
            else return searchRec(node.right, key, comps);
        }
    }

    private static void buildBalancedOrder(int[] sorted, int left, int right, int[] result, int[] index) {
        if (left > right) return;
        int mid = left + (right - left) / 2;
        result[index[0]++] = sorted[mid];
        buildBalancedOrder(sorted, left, mid - 1, result, index);
        buildBalancedOrder(sorted, mid + 1, right, result, index);
    }

    public static void main(String[] args) {
        int[] ascKeys = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        
        int[] descKeys = new int[15];
        for (int i = 0; i < 15; i++) {
            descKeys[i] = ascKeys[14 - i];
        }

        int[] balancedKeys = new int[15];
        buildBalancedOrder(ascKeys, 0, 14, balancedKeys, new int[]{0});

        BST ascTree = new BST();
        BST descTree = new BST();
        BST balTree = new BST();

        for (int k : ascKeys) ascTree.insert(k);
        for (int k : descKeys) descTree.insert(k);
        for (int k : balancedKeys) balTree.insert(k);

        int[] missingKeys = {0, 16, 99};

        System.out.println("========================== Tree Shape Comparison ==========================");
        System.out.printf("%-18s | %-6s | %-20s | %-16s | %-14s%n",
                "Tree Type", "Height", "Total Search Comps", "Avg Search Comps", "Missing Comps");
        System.out.println("---------------------------------------------------------------------------");

        printStats("Ascending Order", ascTree, ascKeys, missingKeys);
        printStats("Descending Order", descTree, ascKeys, missingKeys);
        printStats("Near Balanced", balTree, ascKeys, missingKeys);
        System.out.println("===========================================================================");
    }

    private static void printStats(String type, BST tree, int[] allKeys, int[] missingKeys) {
        int totalComps = 0;
        for (int k : allKeys) {
            totalComps += tree.searchWithCost(k).comparisons;
        }
        double avgComps = (double) totalComps / allKeys.length;

        StringBuilder missingStr = new StringBuilder();
        for (int mk : missingKeys) {
            missingStr.append(tree.searchWithCost(mk).comparisons).append(" ");
        }

        System.out.printf("%-18s | %-6d | %-20d | %-16.2f | %-14s%n",
                type, tree.getHeight(), totalComps, avgComps, missingStr.toString().trim());
    }
}