import java.util.ArrayList;
import java.util.List;

public class CompleteBstTestSuite {

    static class SimpleBST {
        static class Node {
            int val;
            Node left, right;
            Node(int val) { this.val = val; }
        }

        Node root;
        private int count = 0;

        public boolean insert(int val) {
            if (contains(val)) return false;
            root = insertRec(root, val);
            count++;
            return true;
        }

        private Node insertRec(Node node, int val) {
            if (node == null) return new Node(val);
            if (val < node.val) node.left = insertRec(node.left, val);
            else node.right = insertRec(node.right, val);
            return node;
        }

        public boolean contains(int val) {
            return containsRec(root, val);
        }

        private boolean containsRec(Node node, int val) {
            if (node == null) return false;
            if (node.val == val) return true;
            return val < node.val ? containsRec(node.left, val) : containsRec(node.right, val);
        }

        public boolean delete(int val) {
            if (!contains(val)) return false;
            root = deleteRec(root, val);
            count--;
            return true;
        }

        private Node deleteRec(Node node, int val) {
            if (node == null) return null;
            if (val < node.val) node.left = deleteRec(node.left, val);
            else if (val > node.val) node.right = deleteRec(node.right, val);
            else {
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;
                Node min = node.right;
                while (min.left != null) min = min.left;
                node.val = min.val;
                node.right = deleteRec(node.right, min.val);
            }
            return node;
        }

        public int size() { return count; }

        public int height() { return heightRec(root); }
        private int heightRec(Node node) {
            if (node == null) return -1;
            return 1 + Math.max(heightRec(node.left), heightRec(node.right));
        }

        public boolean isValid() { return isValidRec(root, null, null); }
        private boolean isValidRec(Node node, Integer min, Integer max) {
            if (node == null) return true;
            if ((min != null && node.val <= min) || (max != null && node.val >= max)) return false;
            return isValidRec(node.left, min, node.val) && isValidRec(node.right, node.val, max);
        }

        public List<Integer> range(int low, int high) {
            List<Integer> list = new ArrayList<>();
            rangeRec(root, low, high, list);
            return list;
        }
        private void rangeRec(Node node, int low, int high, List<Integer> list) {
            if (node == null) return;
            if (node.val > low) rangeRec(node.left, low, high, list);
            if (node.val >= low && node.val <= high) list.add(node.val);
            if (node.val < high) rangeRec(node.right, low, high, list);
        }
    }

    private static int passCount = 0;
    private static int failCount = 0;

    public static void check(String description, boolean condition) {
        if (condition) {
            System.out.printf("[PASS] %s%n", description);
            passCount++;
        } else {
            System.err.printf("[FAIL] %s%n", description);
            failCount++;
        }
    }

    public static void main(String[] args) {
        System.out.println("========== BST COMPLETE TEST SUITE ==========");

        SimpleBST bst = new SimpleBST();

        check("1. Empty tree size should be 0", bst.size() == 0);
        check("2. Empty tree height should be -1", bst.height() == -1);
        check("3. Empty tree should be valid BST", bst.isValid());
        check("4. Insert 50 into empty tree", bst.insert(50) && bst.size() == 1);
        check("5. Duplicate insert 50 should return false", !bst.insert(50) && bst.size() == 1);
        check("6. Insert multiple elements", bst.insert(30) && bst.insert(70) && bst.insert(20) && bst.insert(40) && bst.insert(60) && bst.insert(80));
        check("7. Size after 7 inserts should be 7", bst.size() == 7);
        check("8. Height of balanced 7-node tree should be 2", bst.height() == 2);
        check("9. Tree invariant remains valid", bst.isValid());
        check("10. Find existing element (40)", bst.contains(40));
        check("11. Find missing element (99)", !bst.contains(99));
        check("12. Delete non-existing key (99) returns false", !bst.delete(99));
        check("13. Delete Leaf node (20)", bst.delete(20) && !bst.contains(20) && bst.size() == 6);
        bst.insert(25);
        bst.delete(40);
        check("14. Delete node with One Child (30)", bst.delete(30) && !bst.contains(30) && bst.contains(25));
        check("15. Delete node with Two Children (70)", bst.delete(70) && !bst.contains(70) && bst.contains(60) && bst.contains(80));
        check("16. Delete Root node with Two Children (50)", bst.delete(50) && !bst.contains(50));
        check("17. Invariant still valid after multiple deletions", bst.isValid());
        List<Integer> r1 = bst.range(25, 80);
        check("18. Range query inclusive [25, 80] contains all remaining keys", r1.size() == 4);
        List<Integer> rEmpty = bst.range(100, 200);
        check("19. Range query with no matches returns empty list", rEmpty.isEmpty());
        List<Integer> rInvalid = bst.range(90, 10);
        check("20. Range query with low > high returns empty list", rInvalid.isEmpty());
        bst.delete(25);
        bst.delete(60);
        bst.delete(80);
        check("21. Delete until empty: size should be 0", bst.size() == 0);
        check("22. Final empty tree height is -1 and valid", bst.height() == -1 && bst.isValid());

        System.out.println("=============================================");
        System.out.printf("Test Summary: Total %d assertions | PASSED: %d | FAILED: %d%n",
                (passCount + failCount), passCount, failCount);
    }
}