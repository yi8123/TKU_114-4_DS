import java.util.*;

public class BstRangeStatistics {

    static class Node {
        int val;
        Node left, right;
        Node(int val) { this.val = val; }
    }

    private Node root;

    public void insert(int val) {
        root = insertRec(root, val);
    }

    private Node insertRec(Node node, int val) {
        if (node == null) return new Node(val);
        if (val < node.val) node.left = insertRec(node.left, val);
        else if (val > node.val) node.right = insertRec(node.right, val);
        return node;
    }

    public List<Integer> valuesBetween(int low, int high) {
        List<Integer> res = new ArrayList<>();
        if (low > high) return res;
        collectValues(root, low, high, res);
        return res;
    }

    private void collectValues(Node node, int low, int high, List<Integer> res) {
        if (node == null) return;
        if (node.val > low) collectValues(node.left, low, high, res);
        if (node.val >= low && node.val <= high) res.add(node.val);
        if (node.val < high) collectValues(node.right, low, high, res);
    }

    public int countBetween(int low, int high) {
        if (low > high) return 0;
        return countRec(root, low, high);
    }

    private int countRec(Node node, int low, int high) {
        if (node == null) return 0;
        int count = 0;
        if (node.val >= low && node.val <= high) count = 1;
        if (node.val > low) count += countRec(node.left, low, high);
        if (node.val < high) count += countRec(node.right, low, high);
        return count;
    }

    public int sumBetween(int low, int high) {
        if (low > high) return 0;
        return sumRec(root, low, high);
    }

    private int sumRec(Node node, int low, int high) {
        if (node == null) return 0;
        int sum = 0;
        if (node.val >= low && node.val <= high) sum = node.val;
        if (node.val > low) sum += sumRec(node.left, low, high);
        if (node.val < high) sum += sumRec(node.right, low, high);
        return sum;
    }

    public static void main(String[] args) {
        BstRangeStatistics tree = new BstRangeStatistics();
        int[] vals = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45};
        for (int v : vals) tree.insert(v);

        System.out.println("=== Normal Range [25, 65] ===");
        System.out.println("Values: " + tree.valuesBetween(25, 65));
        System.out.println("Count : " + tree.countBetween(25, 65));
        System.out.println("Sum   : " + tree.sumBetween(25, 65));

        System.out.println("\n=== Empty Range [100, 200] ===");
        System.out.println("Values: " + tree.valuesBetween(100, 200));
        System.out.println("Count : " + tree.countBetween(100, 200));
        System.out.println("Sum   : " + tree.sumBetween(100, 200));

        System.out.println("\n=== Invalid Range low > high [80, 20] ===");
        System.out.println("Values: " + tree.valuesBetween(80, 20));
        System.out.println("Count : " + tree.countBetween(80, 20));
        System.out.println("Sum   : " + tree.sumBetween(80, 20));
    }
}