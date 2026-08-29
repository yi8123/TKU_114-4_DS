import java.util.*;

public class BstOperationAudit {

    static class Node {
        int val;
        Node left, right;
        Node(int val) { this.val = val; }
    }

    private Node root;

    public boolean add(int val) {
        if (contains(root, val)) {
            audit("ADD " + val, false);
            return false;
        }
        root = insertRec(root, val);
        audit("ADD " + val, true);
        return true;
    }

    private Node insertRec(Node node, int val) {
        if (node == null) return new Node(val);
        if (val < node.val) node.left = insertRec(node.left, val);
        else node.right = insertRec(node.right, val);
        return node;
    }

    public boolean remove(int val) {
        if (!contains(root, val)) {
            audit("REMOVE " + val, false);
            return false;
        }
        root = removeRec(root, val);
        audit("REMOVE " + val, true);
        return true;
    }

    private Node removeRec(Node node, int val) {
        if (node == null) return null;
        if (val < node.val) node.left = removeRec(node.left, val);
        else if (val > node.val) node.right = removeRec(node.right, val);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node successor = findMin(node.right);
            node.val = successor.val;
            node.right = removeRec(node.right, successor.val);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private boolean contains(Node node, int val) {
        if (node == null) return false;
        if (node.val == val) return true;
        return val < node.val ? contains(node.left, val) : contains(node.right, val);
    }

    public int size(Node node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }

    public int height(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public boolean isValidBST(Node node, Integer min, Integer max) {
        if (node == null) return true;
        if ((min != null && node.val <= min) || (max != null && node.val >= max)) return false;
        return isValidBST(node.left, min, node.val) && isValidBST(node.right, node.val, max);
    }

    public List<Integer> getInorder(Node node) {
        List<Integer> res = new ArrayList<>();
        inorderRec(node, res);
        return res;
    }

    private void inorderRec(Node node, List<Integer> res) {
        if (node != null) {
            inorderRec(node.left, res);
            res.add(node.val);
            inorderRec(node.right, res);
        }
    }

    private void audit(String op, boolean result) {
        System.out.printf("OP: %-12s | Res: %-5b | Inorder: %-25s | Size: %2d | Height: %2d | Valid: %b%n",
                op, result, getInorder(root), size(root), height(root), isValidBST(root, null, null));
    }

    public static void main(String[] args) {
        BstOperationAudit tree = new BstOperationAudit();
        tree.add(50);
        tree.add(30);
        tree.add(70);
        tree.add(20);
        tree.add(40);
        tree.add(60);
        tree.add(80);
        tree.add(30);
        tree.remove(999);
        tree.remove(20);
        tree.remove(60);
        tree.remove(70);
        tree.remove(50);
    }
}