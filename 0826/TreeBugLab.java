import java.util.*;

public class TreeBugLab {

    static class Node {
        int val;
        Node left, right;
        Node(int val) { this.val = val; }
    }

    static boolean buggySearch(Node node, int key) {
        if (node == null) return false;
        if (node.val == key) return true;
        return key < node.val ? buggySearch(node.right, key) : buggySearch(node.left, key);
    }
    static boolean fixedSearch(Node node, int key) {
        if (node == null) return false;
        if (node.val == key) return true;
        return key < node.val ? fixedSearch(node.left, key) : fixedSearch(node.right, key);
    }

    static void buggyInorder(Node node, List<Integer> res) {
        if (node == null) return;
        buggyInorder(node.right, res);
        res.add(node.val);
        buggyInorder(node.left, res);
    }
    static void fixedInorder(Node node, List<Integer> res) {
        if (node == null) return;
        fixedInorder(node.left, res);
        res.add(node.val);
        fixedInorder(node.right, res);
    }

    static Node buggyDeleteOneChild(Node node, int key) {
        if (node == null) return null;
        if (key < node.val) node.left = buggyDeleteOneChild(node.left, key);
        else if (key > node.val) node.right = buggyDeleteOneChild(node.right, key);
        else {
            return null;
        }
        return node;
    }
    static Node fixedDelete(Node node, int key) {
        if (node == null) return null;
        if (key < node.val) node.left = fixedDelete(node.left, key);
        else if (key > node.val) node.right = fixedDelete(node.right, key);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node min = node.right;
            while (min.left != null) min = min.left;
            node.val = min.val;
            node.right = fixedDelete(node.right, min.val);
        }
        return node;
    }

    static boolean buggyValidate(Node node) {
        if (node == null) return true;
        if (node.left != null && node.left.val >= node.val) return false;
        if (node.right != null && node.right.val <= node.val) return false;
        return buggyValidate(node.left) && buggyValidate(node.right);
    }
    static boolean fixedValidate(Node node, Integer min, Integer max) {
        if (node == null) return true;
        if ((min != null && node.val <= min) || (max != null && node.val >= max)) return false;
        return fixedValidate(node.left, min, node.val) && fixedValidate(node.right, node.val, max);
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Testing Search Bug ===");
        Node tree1 = new Node(10);
        tree1.left = new Node(5);
        tree1.right = new Node(15);
        System.out.println("Buggy search(5): " + buggySearch(tree1, 5) + " (Expected: true, Got false)");
        System.out.println("Fixed search(5): " + fixedSearch(tree1, 5));

        System.out.println("\n=== 2. Testing Inorder Bug ===");
        List<Integer> bIn = new ArrayList<>(), fIn = new ArrayList<>();
        buggyInorder(tree1, bIn);
        fixedInorder(tree1, fIn);
        System.out.println("Buggy Inorder: " + bIn + " (Expected: [5, 10, 15])");
        System.out.println("Fixed Inorder: " + fIn);

        System.out.println("\n=== 3. Testing Delete Bug ===");
        Node tree2 = new Node(10);
        tree2.left = new Node(5);
        tree2.left.left = new Node(2);
        Node bDel = buggyDeleteOneChild(tree2, 5);
        System.out.println("Buggy Delete(5), left of root: " + bDel.left + " (Child 2 lost!)");
        Node tree3 = new Node(10);
        tree3.left = new Node(5);
        tree3.left.left = new Node(2);
        Node fDel = fixedDelete(tree3, 5);
        System.out.println("Fixed Delete(5), left of root val: " + fDel.left.val);

        System.out.println("\n=== 4. Testing Validation Bug ===");
        Node tree4 = new Node(10);
        tree4.left = new Node(5);
        tree4.left.right = new Node(12);
        System.out.println("Buggy Validate: " + buggyValidate(tree4) + " (Passed locally but invalid globally!)");
        System.out.println("Fixed Validate: " + fixedValidate(tree4, null, null));
    }
}